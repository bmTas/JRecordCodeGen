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
import java.util.List;

import net.sf.JRecord.Common.FieldDetail;
import net.sf.JRecord.Common.IFieldDetail;
import net.sf.JRecord.Types.TypeManager;
import net.sf.JRecord.cg.schema.classDefinitions.IClassDef;
import net.sf.JRecord.cg.schema.type.IJavaFieldType;
import net.sf.JRecord.cg.schema.type.JavaTypeDetails;
import net.sf.JRecord.cgen.def.rename.ISetSelectionGroupNames;
import net.sf.JRecord.cgen.def.rename.IUpdateField;
import net.sf.JRecord.cgen.support.Code2JRecordConstants;
import net.sf.JRecord.detailsBasic.IItemDetails;

/**
 * Class to describe one field in the file. It is used for Code generation 
 * @author Bruce Martin
 *
 */
public class FieldDef extends JavaDetails implements IUpdateField, ISetSelectionGroupNames {
	private final IFieldDetail fieldDetail;
	private final ArrayDetails arrayDefinition;
	private final ArrayElement arrayIndexDetails;
	private CobolItemDef cobolItemDef;
	public final String javaType, javaRawType, jrecAs;
	private String value = null;
	public final boolean shortNumber;
	public final IClassDef classDef;
	private final String jRecordFieldDefinition;
	public final IJavaFieldType javaTypeInfo;
	
	private List<DependingOnDef> usedInOccursDependingOn;
	
	private List<String> groupSelectionNames = new ArrayList<String>(0);

	


	public FieldDef(String cobolName, FieldDetail fieldDef, ArrayDetails arrayDetails, ArrayElement ai, String schemaName,
			boolean isCsv) {
		super(cobolName, schemaName, null);
		int type = fieldDef.getType();
		JavaTypeDetails td = new JavaTypeDetails(isCsv, fieldDef);

		this.fieldDetail = fieldDef;
		this.arrayDefinition = arrayDetails;
		this.arrayIndexDetails = ai;
		this.classDef = td.classDef;
		this.javaRawType = td.javaRawType;
		this.javaType = td.javaTypeName;
		this.javaTypeInfo = td.javaTypeInfo;
		this.jrecAs = "as" + (classDef == null || classDef.getJrecAs() == null 
				? "short".equals(javaType) 
						? "Int" 
						: javaType.substring(0, 1).toUpperCase() + javaType.substring(1)
				: classDef.getJrecAs());

		shortNumber = TypeManager.getInstance().getShortType(type, fieldDef.getLen(), fieldDef.getFontName()) != type;
		
		if (isCsv) {
			jRecordFieldDefinition = "FieldDetail.newCsvField("
					+ "\"" + fieldDef.getName() + "\""
					+ ", " + getJRecordTypeId() 
					+ ", " + fieldDef.getPos() 
					+ ", " + fieldDef.getDecimal() 
					+ ", \"" +  fieldDef.getFontName() + "\")";

		} else {
			jRecordFieldDefinition = "FieldDetail.newFixedWidthField("
					+ "\"" + fieldDef.getName() + "\""
					+ ", " + getJRecordTypeId() 
					+ ", " + fieldDef.getPos()
					+ ", " + fieldDef.getLen() 
					+ ", " + fieldDef.getDecimal() 
					+ ", \"" +  fieldDef.getFontName() + "\")";
		}
	}
	
	public final String getJRecordArrayDefinition(String fieldType) {
		//field.arr
		StringBuilder b = new StringBuilder(
				"new ArrayGenField(\"" + arrayDefinition.arrayName + "\", " + fieldDetail.getPos()
				+ ", " + fieldDetail.getLen() + ", " + getJRecordTypeId()
				+ ", " + fieldDetail.getDecimal()
				+ ", \"" +  fieldDetail.getFontName()  + "\"");
		for (int i = 0; i < arrayDefinition.sizes.length; i++) {
			b	.append(", new ArrayGenField.ArraySizeDef(")
				.append(arrayDefinition.sizes[i])
				.append(", ")
				.append(arrayDefinition.indexPositionIncrement[i])
				.append(")");
		}
		return	b.append(")").toString();	
	}
	
	
	public final String getJRecordFieldDefinition() {
		return jRecordFieldDefinition;
	}

	/**
	 * @return the fieldDef
	 */
	public final IFieldDetail getFieldDetail() {
		return fieldDetail;
	}
	
	/**
	 * @return the arrayDetails
	 */
	public final ArrayElement getArrayIndexDetails() {
		return arrayIndexDetails;
	}
	
	public ArrayDetails getArrayDefinition() {
		return arrayDefinition;
	}

	public boolean isArrayItem() {
		return arrayIndexDetails != null;
	}
	
	public boolean alwaysIncludeField() {
		return arrayIndexDetails == null || arrayIndexDetails.isSpecial();
	}

	public final String getJRecordTypeId() {
		return Code2JRecordConstants.getJRecordTypeName(fieldDetail.getType());
	}

	/**
	 * @return the javaType
	 */
	public final String getJavaType() {
		return  javaType ;
	}
	/**
	 * @return the javaType
	 */
	public final String getRawJavaType() {
		return javaRawType;
	}

	public final String getAsType() {
		return jrecAs;
	}
	
	public final String formatGet(String value) {
		
		if (classDef != null) {
			return classDef.generateFromPojo(value);
		}
		return value;
	}

	
	public final String formatSet(String value) {
		if (classDef != null) {
			return classDef.generateToPojo(value);
		}
		return "short".equalsIgnoreCase(javaType) ? "(short) " + value : value;
	}
	
	public final String getFieldFormat() {
		return "short".equalsIgnoreCase(javaType) ? "(short) " : "";
	}


	/**
	 * @return the value
	 */
	public final String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public final void setValue(String value) {
		this.value = value;
	}
	
	public int getDecimal() {
		return fieldDetail.getDecimal();
	}

	public int getLen() {
		return fieldDetail.getLen();
	}

	public String getName() {
		return fieldDetail.getName();
	}

	public int getPos() {
		return fieldDetail.getPos();
	}

	public String getDescription() {
		return fieldDetail.getDescription();
	}

	/**
	 * 
	 * @return is it a primitive (short / int / long / double) numeric type 
	 */
	public final boolean isPrimitiveNumeric() {
		return TypeManager.isNumeric(fieldDetail.getType()) && fieldDetail.getDecimal() == 0;
	}

	public final boolean isNumeric() {
		return TypeManager.isNumeric(fieldDetail.getType());
	}

	public final String getFieldInitialise() {
		String ret = "0";
		
		if ("String".equals(javaType)) {
			ret = "\"\"";
		} else if (javaType.startsWith("Big")) {
			ret = javaType + ".ZERO";
		}
		return ret;
	}

	/**
	 * @return the shortNumber
	 */
	public boolean isShortNumber() {
		return shortNumber;
	}

	/**
	 * @return the cobolItemDef
	 */
	public CobolItemDef getCobolItemDef() {
		return cobolItemDef;
	}

	/**
	 * Get the Cobol Definition
	 * @return Cobol Definition
	 */
	public IItemDetails getCobolItem() {
		return cobolItemDef.getCobolItem();
	}

	/**
	 * @param cobolItemDef the cobolItemDef to set
	 */
	public void setCobolItemDef(CobolItemDef cobolItemDef) {
		this.cobolItemDef = cobolItemDef;
	}
	
	public void addDependingOnDefinition(DependingOnDef dependingOn) {
		if (this.usedInOccursDependingOn == null) {
			this.usedInOccursDependingOn = new ArrayList<DependingOnDef>(4);
		}
		this.usedInOccursDependingOn.add(dependingOn);
	}
	
	public boolean isReferencedByOccursDependingOn() {
		return usedInOccursDependingOn != null;
	}

	/**
	 * @return the groupSelectionNames
	 */
	public List<String> getGroupSelectionNames() {
		return groupSelectionNames;
	}

	@Override
	public void setSelectionGroupNames(List<String> groupSelectionNames) {
		this.groupSelectionNames = groupSelectionNames;
	}

	
	public String getFieldLookup() {
		
		if (groupSelectionNames.size() == 0) {
			return ".getFieldFromName(\"" + fieldDetail.getLookupName() + "\")";
		}
		StringBuilder b = new StringBuilder(".getGroupField(");
		//String sep = ".getGroupField(";
		for (String gn : groupSelectionNames) {
			b.append('\"').append(gn).append("\", ");
		}
		
		return  b.append('\"').append(fieldDetail.getName()).append("\")").toString();
	}


	@Override
	public String getGroup() {
		return fieldDetail.getGroupName();
	}

	@Override
	public void setName(String cobolName) {
		super.setNames(cobolName);
	}
	
	
}
