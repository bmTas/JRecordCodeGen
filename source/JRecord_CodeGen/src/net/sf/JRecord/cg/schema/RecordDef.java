/*  -------------------------------------------------------------------------
 *
 *            Sub-Project: JRecord CodeGen
 *    
 *    Sub-Project purpose: Generate Java - JRecord source code 
 *                        to read/write cobol data files.
 *    
 *                 Author: Bruce Martin
 *    
 *                License: GPL 3 or later
 *                
 *    Copyright (c) 2016, Bruce Martin, All Rights Reserved.
 *   
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU General Public License
 *    as published by the Free Software Foundation; either
 *    version 3.0 of the License, or (at your option) any later version.
 *   
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 * ------------------------------------------------------------------------ */
      
package net.sf.JRecord.cg.schema;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import net.sf.JRecord.Common.FieldDetail;
import net.sf.JRecord.Common.IFieldDetail;
import net.sf.JRecord.External.Def.DependingOnDtls;
import net.sf.JRecord.ExternalRecordSelection.ExternalFieldSelection;
import net.sf.JRecord.ExternalRecordSelection.ExternalGroupSelection;
import net.sf.JRecord.ExternalRecordSelection.ExternalSelection;
import net.sf.JRecord.Option.Options;
import net.sf.JRecord.cg.schema.classDefinitions.IClassDef;
import net.sf.JRecord.cg.schema.type.IJavaFieldType;
import net.sf.JRecord.cgen.def.IArrayAnyDimension;
import net.sf.JRecord.cgen.def.IRecordDetail4gen;
import net.sf.JRecord.cgen.def.rename.IUpdateField;
import net.sf.JRecord.cgen.def.rename.IUpdateRecord;
import net.sf.JRecord.cgen.support.Code2JRecordConstants;
import net.sf.JRecord.detailsBasic.IItemDetails;


/**
 * Class to describe one record type in a file for use in code generation
 * @author Bruce Martin
 *
 */
public class RecordDef extends JavaDetails implements IUpdateRecord {
	private final IRecordDetail4gen record;
	
	private final ArrayList<FieldDef> fields = new ArrayList<FieldDef>();
	private final List<CobolItemDef> cobolItemDefs, cobolItemDefList;
	//private final List<IJavaFieldType> typesUsed = new ArrayList<IJavaFieldType>();
	
	private final String RecordSelectionStr, recordPositionOptionStr;
	private final List<ArrayDetails> arrayDetailsList = new ArrayList<ArrayDetails>();
	private final List<List<ArrayDetails>> arraySameSize = new ArrayList<List<ArrayDetails>>();
	private final List<String> importList, classImports, conversionImports, javaCode;
	private final ExternalSelection recordSelection;
	private final boolean defaultRecord;
	private final Set<String> validOps = new HashSet<String>(Arrays.asList(
			"=", "!=", ">", "<", ">=", "<="
	));
	private final FieldDef endField;
	

	private final boolean shortNumber, dependingOnPresent;
	
	/**
	 * Class to describe one record type in a file for use in code generation
	 * 
	 * @param record standard Record Description
	 */
	public RecordDef(IRecordDetail4gen record, String schemaName, String className, boolean isCsv) {
		super(record.getRecordName(), schemaName, className); 
		this.record = record;
		
		int fieldCount = record.getFieldCount();
		HashMap<String, Integer> fieldsUsed = new HashMap<String, Integer>(fieldCount * 3);
		FieldDetail field;
		String fldName, lcFldName;
		ArrayElement ai;
		HashMap<String, ArrayDetails> arrayMap = new HashMap<String, ArrayDetails>();
		HashMap<IFieldDetail, ArrayDetails> fieldArrayMap = new HashMap<IFieldDetail, ArrayDetails>();
		FieldDef fieldDef;
		//ArrayDetails ad = null;
		TreeSet<String> importSet = new TreeSet<String>();
		
		boolean shortNumbers = false;
		HashSet<IClassDef> classesDefined = new HashSet<IClassDef>();

		
		fields.ensureCapacity(fieldCount);
		
		FieldList fieldList = new FieldList(fields);
		boolean dependingOnPresent = false;
		FieldDef lastField = null;
		
		for (int i = 0; i < record.getFieldCount(); i++) {
			field = record.getField(i);
			fldName = field.getName();
			lcFldName = field.getName().toLowerCase();
			if (! "filler".equals(lcFldName)){
				Integer num = fieldsUsed.get(lcFldName);
				if (num == null) {
					num = Integer.valueOf(1);
				} else {
					fldName = fldName + num; 
					num = num + 1;
				}
				fieldsUsed.put(lcFldName, num);
				
				
				if (fldName.indexOf('(') > 0) {
					ai = ArrayElement.newArrayItem(fldName, schemaName, field);
					
					if (arrayMap.containsKey(ai.keyName)) {
						ArrayDetails ad = arrayMap.get(ai.keyName);
						fieldDef = new FieldDef(fldName, field, ad, ai, schemaName, isCsv);
						ad.addDetails(ai, fieldDef);
					} else {
						DependingOnDtls dependingOnDtls = field.getDependingOnDtls();
						
						DependingOnDef dependingOnDef = fieldList.createDependingOnDefinition(dependingOnDtls);
						if (dependingOnDtls != null)  {
							dependingOnPresent = true;
						}
//							String variableName = dependingOnDtls.getDependingOn().getVariableName();
//							if (variableName != null && variableName.length() > 0) {
//								for (FieldDef f : fields) {
//									if (variableName.equalsIgnoreCase(f.getFieldDetail().getName())) {
//										dependingOnDef = new DependingOnDef(dependingOnDtls, f, ai.getIndexCount());
//									}
//								}
//							}
//						}
						ArrayDetails ad = new ArrayDetails(ai, dependingOnDef);
						fieldDef = new FieldDef(fldName, field, ad, ai, schemaName, isCsv);
						ad.addFirstField(fieldDef);

						arrayMap.put(ai.keyName, ad);
						fieldArrayMap.put(field, ad);
						arrayDetailsList.add(ad);
					}
				} else {
					fieldDef = new FieldDef(fldName, field, null, null, schemaName, isCsv);
					if (lastField == null || lastField.getFieldDetail().getEnd() < field.getEnd()) {
						lastField = fieldDef;
					}
				}
				
				fields.add(fieldDef);
				if (fieldDef.classDef != null) {
					classesDefined.add(fieldDef.classDef);
				}
//				if (fieldDef.javaTypeInfo != null && ! typesUsed.contains(fieldDef.javaTypeInfo)) {
//					typesUsed.add(fieldDef.javaTypeInfo);
//				}
				shortNumbers |= fieldDef.shortNumber;
				
				String jType = fieldDef.getJavaType();
				if ("BigDecimal".equals(jType)) {
					importSet.add("java.math.BigDecimal");
				} else if ("BigInteger".equals(jType)) {
					importSet.add("java.math.BigInteger");
				}
			}
		}
		
		this.shortNumber = shortNumbers;
		this.dependingOnPresent = dependingOnPresent;
		this.endField = lastField;
		
		importList = new ArrayList<String>(importSet);
		
		StringBuilder b = new StringBuilder(30);
		expandSel(b, record.getRecordSelection().getRecSel(), 0);
		this.RecordSelectionStr = b.toString();
		this.recordPositionOptionStr = decodePositionInFile(record);
		
		if (arrayDetailsList.size() > 0) {
			SameSizeLists currList = new SameSizeLists();
			currList.add(arrayDetailsList.get(0));
			for (int i = 1; i < arrayDetailsList.size(); i++) {
				if (! currList.get(0).sizesEqual(arrayDetailsList.get(i))) {
					arraySameSize.add(currList);
					currList = new SameSizeLists();
				}
				currList.add(arrayDetailsList.get(i));
			}
			arraySameSize.add(currList);
		}
		
		this.recordSelection = parseRecSel(record.getRecordSelection().getRecSel());
		this.defaultRecord = record.getRecordSelection().isDefaultRecord();
		
		List<? extends IItemDetails> cobolItems = record.getCobolItems();
		
		List<CobolItemDef> cblItemDefs = null, cblItemDefList = null;
		if (cobolItems != null) {
			cblItemDefs = new FieldMaps(fieldArrayMap, fields, schemaName, className)
					.processCobolItems(cobolItems, 0) ;
			//processCobolItems(cobolItems, bldFieldMap(), fieldArrayMap, 0, schemaName, className);
			
			cblItemDefList = expandCobolItems(cblItemDefs);
		}
		this.cobolItemDefs = cblItemDefs;
		this.cobolItemDefList = cblItemDefList;
		
		TreeSet<String> classImportSet = new TreeSet<String>(),
				conversionImportSet = new TreeSet<String>(),
				codeSet = new TreeSet<String>();
		if (classesDefined.size() > 0) {
			for (IClassDef cd :classesDefined) {
				updateSet(classImportSet, cd.getDataImport());
				updateSet(codeSet, cd.getCode());
				String[] conversionImports = cd.getConversionImport();
				if (conversionImports != null) {
					for (String s : conversionImports) {
						updateSet(conversionImportSet, s);
					}
				}
			}
		}
		classImports = new ArrayList<String>(classImportSet);
		conversionImports = new ArrayList<String>(conversionImportSet);
		javaCode = new ArrayList<String>(codeSet);
	}
	
	
	private void updateSet(TreeSet<String> set, String s) {
		if (hasData(s)) {
			set.add(s);
		}
	}
	
	private boolean hasData(String s) {
		return s != null && s.length() > 0;
	}


	/**
	 * @param record
	 * @return
	 */
	protected String decodePositionInFile(IRecordDetail4gen record) {
		String s = "null";
		if (record.getRecordPositionOption() == Options.RP_FIRST_RECORD_IN_FILE) {
			s = "Options.RP_FIRST_RECORD_IN_FILE"; 
		} else if (record.getRecordPositionOption() == Options.RP_MIDDLE_RECORDS) {
			s = "Options.RP_MIDDLE_RECORDS"; 
		} else if (record.getRecordPositionOption() == Options.RP_LAST_RECORD_IN_FILE) {
			s = "Options.RP_LAST_RECORD_IN_FILE"; 
		}
		return s;
	}

//	/**
//	 * @return
//	 */
//	protected HashMap<IFieldDetail, FieldDef> bldFieldMap() {
//		HashMap<IFieldDetail, FieldDef> fieldMap = new HashMap<IFieldDetail, FieldDef>(Math.max(50, fields.size() * 3 / 2));
//		for (FieldDef fd : fields) {
//			if ((! fd.isArrayItem()) || fd.getArrayIndexDetails().isFirstIndex()) {
//				fieldMap.put(fd.getFieldDetail(), fd);
//			}
//		}
//		return fieldMap;
//	}

	/**
	 * @param cblItemDefs
	 * @param cblItemDefList
	 */
	private ArrayList<CobolItemDef> expandCobolItems(List<CobolItemDef> cblItemDefs) {
		HashMap<IItemDetails, CobolItemDef> itemToDefMap = new HashMap<IItemDetails, CobolItemDef>(Math.max(50, cblItemDefs.size() * 4));
		ArrayList<CobolItemDef> cblItemDefList = new ArrayList<CobolItemDef>(Math.max(25, cblItemDefs.size() * 2));

		expand(cblItemDefList, itemToDefMap, cblItemDefs);

		for (FieldDef fd : fields) {
			IItemDetails cobolItem = fd.getFieldDetail().getCobolItem();
			if (cobolItem != null) {
				fd.setCobolItemDef(itemToDefMap.get(cobolItem));
			}
		}
		return cblItemDefList;
	}
	
//	}
	
	private void expand(
			List<CobolItemDef> cblItmsList, HashMap<IItemDetails, CobolItemDef> itemToDefMap, 
			List<CobolItemDef> cblItms) {
		if (cblItms != null) {
			for (CobolItemDef ci : cblItms) {
				cblItmsList.add(ci);
				itemToDefMap.put(ci.getCobolItem(), ci);
				expand(cblItmsList, itemToDefMap, ci.getChildItems());
			}
		}
	}

	public void expandSel(StringBuilder b, ExternalSelection sel, int level) {
		char[] indent = new char[level * 3 + 5];
		Arrays.fill(indent, ' ');
		
		b.append(indent);
		if (sel == null) {
			
		} else if (sel instanceof ExternalGroupSelection) {
			@SuppressWarnings("rawtypes")
			ExternalGroupSelection eg = (ExternalGroupSelection) sel;
			char sep = '(';
			
			if (eg.getType() == ExternalSelection.TYPE_AND) {
				b.append("net.sf.JRecord.ExternalRecordSelection.ExternalGroupSelection.newAnd\n");
			} else {
				b.append("net.sf.JRecord.ExternalRecordSelection.ExternalGroupSelection.newOr\n");
			}
			for (int i = 0; i < eg.getElementCount(); i++) {
				expandSel(b.append(sep), eg.get(i), level+1);
				sep = ',';
			}
			b.append(indent).append("   )\n");
		} else if (sel instanceof ExternalFieldSelection) {
			ExternalFieldSelection ef = (ExternalFieldSelection) sel;
			b.append("net.sf.JRecord.ExternalRecordSelection.ExternalFieldSelection.newFieldSelection(\n" )
			 .append(indent).append("false, \"" )
			 .append( ef.getFieldName() + "\", \"" + ef.getOperator() + "\", \"" + ef.getFieldValue() + "\")\n");
		} else {
			throw new RuntimeException("Invalid Record Selection class: " + sel.getClass().getName());
		}
	}
	
	
	/**
	 * @return the record
	 */
	public final IRecordDetail4gen getRecord() {
		return record;
	}

	/**
	 * @return the fields
	 */
	public final List<FieldDef> getFields() {
		return fields;
	}
	
	/**
	 * @return the fields
	 */
	public final List<FieldDef> getFields(int limit) {
		if (fields.size() <= limit) {
			return fields;
		}
		ArrayList<FieldDef> r = new ArrayList<FieldDef>(limit);
		for (int i = 0; i < limit; i++) {
			r.add(fields.get(i));
		}
		return r;
	}

	
	/**
	 * @return the lastField
	 */
	public FieldDef getEndField() {
		return endField;
	}


	public List<IJavaFieldType> getTypesUsed() {
		List<IJavaFieldType> typesUsed = new ArrayList<IJavaFieldType>();
		IJavaFieldType last = null;
		
		for (FieldDef fieldDef : fields) {
			if (fieldDef.javaTypeInfo != null 
			&& last != fieldDef.javaTypeInfo 
			&& ! typesUsed.contains(fieldDef.javaTypeInfo)) {
				typesUsed.add(fieldDef.javaTypeInfo);
				last = fieldDef.javaTypeInfo;
			}
		}

		return typesUsed;
	}


	public String getJRecordRecordType() {
		return Code2JRecordConstants.getRecordTypeName(record.getRecordType());
	}
	
	public String getParserName() {
		return Code2JRecordConstants.getParserName(record.getRecordStyle());
	}

	/**
	 * @return the recordSelectionStr
	 */
	public final String getRecordSelectionStr() {
		return RecordSelectionStr;
	}

	/**
	 * @return the recordPositionOptionStr
	 */
	public final String getRecordPositionOptionStr() {
		return recordPositionOptionStr;
	}

	/**
	 * @return the arrayDetailsList
	 */
	public final List<ArrayDetails> getArrayDetailsList() {
		return arrayDetailsList;
	}

	/**
	 * @return the arraySameSize
	 */
	public final List<List<ArrayDetails>> getArraySameSize() {
		return arraySameSize;
	}

	/**
	 * @return the importList
	 */
	public final List<String> getImportList() {
		return importList;
	}
	
	/**
	 * @return the recordSelection
	 */
	public final ExternalSelection getRecordSelection() {
		return recordSelection;
	}

	/**
	 * @return the defaultRecord
	 */
	public final boolean isDefaultRecord() {
		return defaultRecord;
	}

	/**
	 * @return the dependingOnPresent
	 */
	public boolean isDependingOnPresent() {
		return dependingOnPresent;
	}


	private ExternalSelection parseRecSel(ExternalSelection sel) {
		if (sel == null) {return null;}
		ExternalSelection ret = null;
		switch (sel.getType()) {
		case ExternalSelection.TYPE_ATOM:
			ExternalFieldSelection f = (ExternalFieldSelection) sel;
			String op = f.getOperator();
			FieldDef field = null;
			for (FieldDef fld : fields) {
				if (f.getFieldName().equalsIgnoreCase(fld.getFieldDetail().getName())) {
					field = fld;
					break;
				}
			}
			if (field != null && validOps.contains(op)) {
				if (field.getValue() == null) {
					field.setValue(f.getFieldValue());
				}
				ret = new FieldSelection(field, op, f.getFieldValue());
			}
			break;
		case ExternalSelection.TYPE_AND:
			ret = parseGroup(sel, "&&");
			break;
		case ExternalSelection.TYPE_OR:
			ret = parseGroup(sel, "||");
			break;
		
		}
		
		return ret;
	}
	
	private ExternalSelection parseGroup(ExternalSelection sel, String boolOp) {
		@SuppressWarnings("unchecked")
		ExternalGroupSelection<? extends ExternalSelection> gs = (ExternalGroupSelection<? extends ExternalSelection>) sel;
		ArrayList<ExternalSelection> list = new ArrayList<ExternalSelection>(gs.getSize());
		ExternalSelection childSel;
		
		for (int i = 0; i < gs.getSize(); i++ ) {
			childSel = parseRecSel(gs.get(i));
			if (childSel != null) {
				list.add(childSel);
			}
		}
		
		switch (list.size()) {
		case 0: return null;
		case 1: return list.get(0);
		}
		return new GroupSelection(gs.getType(), boolOp, list);
	}

	/**
	 * @return the shortNumbers
	 */
	public boolean isShortNumber() {
		return shortNumber;
	}

	/**
	 * @return the cobolItemDefs
	 */
	public List<CobolItemDef> getCobolItemDefs() {
		return cobolItemDefs;
	}

	/**
	 * @return the cobolItemDefList
	 */
	public List<CobolItemDef> getCobolItemDefList() {
		return cobolItemDefList;
	}


	/**
	 * @return the classImports
	 */
	public final List<String> getClassImports() {
		return classImports;
	}


	/**
	 * @return the conversionImports
	 */
	public final List<String> getConversionImports() {
		return conversionImports;
	}


	/**
	 * @return the javaCode
	 */
	public final List<String> getJavaCode() {
		return javaCode;
	}
	
	

	@Override
	public String getRecordName() {
		return record.getRecordName();
	}


	@Override
	public void fieldUpdated() {
	}


	@Override
	public List<? extends IUpdateField> getUpdateFields() {
		return fields;
	}



	private static class FieldMaps {
		
		final HashMap<IFieldDetail, FieldDef> fieldMap;
		final HashMap<String, FieldDef> fieldNameMap;
		final HashMap<IFieldDetail, ArrayDetails> fieldArrayMap;
		final String schemaName, className;


		public FieldMaps(
				HashMap<IFieldDetail, ArrayDetails> fieldArrayMap, 
				ArrayList<FieldDef> fields,
				String schemaName, String className) {
			super();
			this.fieldMap = new HashMap<IFieldDetail, FieldDef>(Math.max(50, fields.size() * 3 / 2));
			this.fieldNameMap = new HashMap<String, FieldDef>(Math.max(50, fields.size() * 3 / 2));
			this.fieldArrayMap = fieldArrayMap;
			this.schemaName = schemaName;
			this.className = className;

			for (FieldDef fd : fields) {
				if ((! fd.isArrayItem()) || fd.getArrayIndexDetails().isFirstIndex()) {
					fieldMap.put(fd.getFieldDetail(), fd);
					if (! fd.isArrayItem()) {
						fieldNameMap.put(fd.getCobolName().toUpperCase(), fd);
					}
				}
			}
		}
		
		
		List<CobolItemDef> processCobolItems(
				List<? extends IItemDetails> cobolItems, 
				int level) {
			List<CobolItemDef> cblItms = null;
			
			if (cobolItems != null && cobolItems.size() > 0) {
				cblItms = new ArrayList<CobolItemDef>(cobolItems.size());
				
				for (IItemDetails itm : cobolItems) {
					FieldDef fieldDef = null;
					ArrayDetails ad = null;
					
					if (itm.isLeaf()) {
						IFieldDetail f = itm.getFieldDefinition();
						IArrayAnyDimension arrayDefinition = itm.getArrayDefinition();
						
						if (arrayDefinition != null) {
							f = arrayDefinition.getFirstField();
							ad = fieldArrayMap.get(f);
						}
						fieldDef = fieldMap.get(f);
						//fieldArrayMap.g
					}
					CobolItemDef cblItemDef = new CobolItemDef(
							itm, schemaName, 
							className == null ? null : itm.getFieldName(), 
							level, 
							processCobolItems(itm.getChildItems(), level+1), 
							fieldDef, ad);
					cblItms.add(cblItemDef);
					
					String dependingOn = itm.getDependingOn();
					if (dependingOn != null && dependingOn.length() > 0 && cblItemDef.getOccursDependingField() == null) {
						cblItemDef.setOccursDependingField(fieldNameMap.get(dependingOn.toUpperCase()));
					}
				}
			}
			
			return cblItms;
		}


	}
	
	
	
	public static class SameSizeLists extends ArrayList<ArrayDetails> {

		private FieldDef endFieldDef;
		private ArrayElement endElement;
		@Override
		public boolean add(ArrayDetails e) {
			FieldDef eField = e.getFirstField();
			if (endFieldDef == null
			|| eField.getFieldDetail().getEnd() > endFieldDef.getFieldDetail().getEnd()) {
				endFieldDef = eField;
				endElement = e.getFirstElement();
			}
			return super.add(e);
		}
		
		/**
		 * @return the endFieldDef
		 */
		public ArrayElement getEndElement() {
			return endElement;
		}
	}
	
}
