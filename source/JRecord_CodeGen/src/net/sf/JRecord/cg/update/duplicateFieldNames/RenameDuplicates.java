package net.sf.JRecord.cg.update.duplicateFieldNames;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.JRecord.cg.schema.ArrayElement;
import net.sf.JRecord.cg.schema.FieldDef;
import net.sf.JRecord.cg.schema.RecordDef;
import net.sf.JRecord.cgen.def.rename.ISetSelectionGroupNames;
import net.sf.JRecord.cgen.def.rename.IUpdateField;
import net.sf.JRecord.cgen.def.rename.IUpdateRecord;
import net.sf.JRecord.cgen.def.rename.IUpdateSchema;

public class RenameDuplicates {
	private static final String[] NO_GROUP_NAMES = {};
	//private final IUpdateSchema schema;
	private final List<? extends IUpdateRecord> records;
	
	//private final List<ExternalRecordHelper> children;
	//private final FieldMap fieldMap;
	

	public RenameDuplicates(IUpdateSchema schema) {
		//this.schema = schema;	
		records = schema.getUpdateRecords();
	}
	
	
	
	/**
	 * @return
	 * @see net.sf.JRecord.cg.update.duplicateFieldNames.RenameDuplicates.FieldMap#getDuplicateFieldNames()
	 */
	public List<List<IFieldHelper>> getDuplicateFieldNames() {
		return fieldMapForSchema().getDuplicateFieldNames();
	}

	public void updateDuplicateNamesInEachRecord2() {
		
		//List<List<IFieldHelper>> ret = new ArrayList<>();
		FieldMap fieldMap = fieldMapForSchema();
		List<List<IFieldHelper>> duplicateFieldNames = fieldMap.getDuplicateFieldNames();
		for (List<IFieldHelper> fieldList : duplicateFieldNames) { 
			updateUniqueGroups(fieldList);
			FieldMap dfm = updateNames(fieldList, 1, 1);
			List<List<IFieldHelper>> dupFields = dfm.getDuplicateFieldNames();
			for (List<IFieldHelper> l : dupFields) { 
				checkUniqueInGroup(l);
				FieldMap dfm2 = updateNames(l, 2, 2);
				
				for (List<IFieldHelper> l2 : dfm2.getDuplicateFieldNames()) { 
					checkUniqueInGroup(l2);
//					FieldMap dfm3 = updateNames(l2, 2, 1);
//					for (List<IFieldHelper> l3 : dfm3.getDuplicateFieldNames()) { 
//						checkUniqueInGroup(l3);
						for (IFieldHelper fh : l2) {
							fh.setUniqueGroupNames(fh.getGroupNames().length - fh.firstGroup);
						}
//					}
				}
			}
		}
		
		for (List<IFieldHelper> l : fieldMap.recordFields) {
			FieldMap fm = new FieldMap(l.size() * 3 / 2 + 10);
//			System.out.println();
			for (IFieldHelper f : l) {
//				System.out.print(f.field.getName() + "\t:");
//				f.printGroupNames();
				fm.addField(f.getName(), f);
//				System.out.print("\t>\t");
//				f.printGroupNames();
//				System.out.println();
			}
			List<List<IFieldHelper>> dups = fm.getDuplicateFieldNames();
			for (List<IFieldHelper> dup : dups) {
				for (IFieldHelper f : dup) {
					String gn = f.groupNames == null || f.groupNames.length == 0 
							? "" 
							: f.groupNames[f.groupNames.length-1] + "-";
					int id = 0;
					String name = "";
					do {
						id += 1;
						name = (gn +  id + "-" + f.getOriginalName()).toLowerCase();
					} while (fm.map.containsKey(name));
					f.setName(name);
					fm.addField(name, f);
					f.setUniqueGroupNames(f.groupNames.length - f.firstGroup);
				}
			}
		}
		
//		for (List<IFieldHelper> l : fieldMap.recordFields) {
//			System.out.println();
//			for (IFieldHelper f : l) {
//				System.out.print(f.getName() + "\t:");
//				f.printGroupNames();
//				System.out.print("\t>\t");
//				f.printGroupNames();
//				System.out.println();
//			}
//		}
		//return null;
	}



	/**
	 * @param fieldList
	 */
	private void updateUniqueGroups(List<IFieldHelper> fieldList) {
		if (fieldList.size() > 2) {
			fieldList.sort(new Comparator<IFieldHelper>() {
				@Override
				public int compare(IFieldHelper h1, IFieldHelper h2) {
					GroupCompare g = new GroupCompare(h1, h2);
					
					if (g.m1 >= 0 && g.m2 >= 0) {
						 return h1.getGroupNames()[g.m1].compareToIgnoreCase(h2.getGroupNames()[g.m2]);
					}
	
					return g.m1 - g.m2;
				}
			});
		}
		
		for (int i = 1; i < fieldList.size(); i++) {
			GroupCompare g = new GroupCompare(fieldList.get(i), fieldList.get(i-1));
			fieldList.get(i).updateFirstGroup(g.m1);
			fieldList.get(i-1).updateFirstGroup(g.m2);
		}
		
		checkUniqueInGroup(fieldList);
	}



	private void checkUniqueInGroup(List<IFieldHelper> fieldList) {
		for (int idx = fieldList.size()-1; idx >= 0; idx--) {
			boolean searching = true;
			IFieldHelper currField = fieldList.get(idx);
			for (int i = 0; i < fieldList.size() && searching; i++) {
				searching = (idx == i) || fieldList.get(i).record != currField.record;
			}
			if (searching) {
				currField.insertUniqueGroupName(currField.groupNames[0]);

				fieldList.remove(idx);
			}
		}
	}

	public List<List<IFieldHelper>> updateDuplicateNamesInEachRecord() {
		List<List<IFieldHelper>> dups = new ArrayList<>();
		for (IUpdateRecord rec : records) {
			List<List<IFieldHelper>> duplicateFieldNames = fieldMapForRecord(rec).getDuplicateFieldNames();
			for (List<IFieldHelper> fieldList : duplicateFieldNames) { 
				FieldMap dfm = updateNames(fieldList, 1, 1);
				List<List<IFieldHelper>> dupFields = dfm.getDuplicateFieldNames();
				for (List<IFieldHelper> l : dupFields) { 
					updateNames(l, 2, 1);
				}
			}

			dups.addAll(fieldMapForRecord(rec).getDuplicateFieldNames());
		}
		return dups;
	}


	public boolean renameField(String newName, String... names) {
		List<IUpdateField> matchingFields = new ArrayList<>();
		String fieldName = names[names.length - 1];
		ArrayList<String> ln = new ArrayList<String>(names.length-1);
		IUpdateRecord r = null;
		int updatedCount = 0;

		for (int i = 0; i < names.length-1; i++) {
			if (names[i] != null && ! "".equals(names[i])) {
				ln.add("." + names[i].toUpperCase() + ".");
			}
		}

		for (IUpdateRecord rec : records) {
			for (IUpdateField f : rec.getUpdateFields()) {
				if (f.getName().equalsIgnoreCase(fieldName)) {
					String groupName = f.getGroup().toUpperCase();
					boolean ok = true;
					int st = 0;
					for (String n : ln) {
						if ((st = groupName.indexOf(n, st)) < 0) {
							ok = false;
							break;
						}
					}

					if (ok) {
						matchingFields.add(f);
						r = rec;
					}
				}
			}
			switch (matchingFields.size()) {
			case 0: break;
			case 1:
				matchingFields.get(0).setName(newName);
				r.fieldUpdated();
				updatedCount += 1;
				break;
			default: throw new RuntimeException("multiple matches (" + matchingFields.size() + ") found in record "
												+ rec.getRecordName());
			}
		}

		return updatedCount > 0;
	}



	/**
	 * @param dfm
	 * @param dupNamesList
	 */
	private FieldMap updateNames(List<IFieldHelper> dupNamesList, int firstDiff, int secondDiff) {
		int size = dupNamesList.size();
		FieldMap dfm = new FieldMap(size * 2);
//		int chg = 0;
		for (IFieldHelper fieldDtl : dupNamesList) {
			List<String> gnl = new ArrayList<String>(firstDiff);
			int groupNamesLength = fieldDtl.groupNames == null ? 0 : fieldDtl.groupNames.length;
			if (groupNamesLength >= firstDiff) {
				StringBuilder b = new StringBuilder();
//				ArrayList<String> groups = new ArrayList<>();
				for (int i = firstDiff; i>= secondDiff; i--) {
					int idx = fieldDtl.groupNames.length - i;
					String g = fieldDtl.groupNames[idx];
					b.append(g).append('-');
//					groups.add(g);
				}
				b.append(fieldDtl.field.getName());
				String name = b.toString();
				fieldDtl.setName(name);
				dfm.addField(name.toUpperCase(), fieldDtl);
				fieldDtl.record.fieldUpdated();
				
				
				fieldDtl.setUniqueGroupNames(firstDiff);

//				chg += 1;
//				if (chg >= size - 1) { break; }
			}
		}
		return dfm;
	}
	
	private FieldMap fieldMapForSchema() {
		int count = 0;
		for (IUpdateRecord rec : records) {
			count += rec.getUpdateFields().size();
		}
		FieldMap fieldMap = new FieldMap(Math.max(20, count * 3 / 2));

		for (IUpdateRecord rec : records) {
			fieldMapForRecord(fieldMap, rec);
		}
		
		return fieldMap;
	}
	
	
	/**
	 * @param fieldMap
	 * @param rec
	 */
	private FieldMap fieldMapForRecord(IUpdateRecord rec) {
		FieldMap fieldMap = new FieldMap(Math.max(20, rec.getUpdateFields().size() * 3 / 2));

		fieldMapForRecord(fieldMap, rec);
	
		return fieldMap;
	}
	
	
	/**
	 * @param fieldMap
	 * @param rec
	 */
	private void fieldMapForRecord(FieldMap fieldMap, IUpdateRecord rec) {
		if (rec instanceof RecordDef) {
			RecordDef record = (RecordDef) rec;
			for (FieldDef fld : record.getFields()) {
				String name = fld.getName();
				if (name != null && name.length() > 0 && ! "filler".equalsIgnoreCase(name)) {
					if (fld.isArrayItem()) {
						if (fld.getArrayIndexDetails().isFirstIndex()) {
							fieldMap.addField(name, newFieldHelper(record, fld));
						}
					} else {
						fieldMap.addField(name, newFieldHelper(record, fld));
					}
				}
			}
		} else {
			for (IUpdateField fld : rec.getUpdateFields()) {
				String name = fld.getName();
				if (name != null && name.length() > 0 && ! "filler".equalsIgnoreCase(name)) {
					fieldMap.addField(name, rec, fld);
				}
			}
		}
	}
	
	
//	private void updateForDuplicates(List<ExternalField> dups) {
//		for (ExternalRecordHelper c : children) {
//			c.updateForDuplicates(dups);
//		}
//		
//		List<List<ExternalField>> duplicateFieldNames = fieldMap.getDuplicateFieldNames();
//	}
	
	private static class FieldMap {

		Map<String, List<IFieldHelper>> map;
		IUpdateRecord lastRecord = null;
		List<IFieldHelper> currentRecordFields;
		ArrayList<List<IFieldHelper>> recordFields = new ArrayList<>();

		FieldMap(int size) {
			map = new HashMap<>(size);
		}

//		public void addField(String name, RecordDef record, FieldDef f) {
//			addAfield(name, new FieldHelper(record, f));
//		}

		public void addField(String name, IUpdateRecord record, IUpdateField f) {
			if (f instanceof FieldDef && ((FieldDef) f).isArrayItem()) {
				FieldDef fieldDef = (FieldDef) f;

				if (fieldDef.getArrayIndexDetails().isFirstIndex()) {
					addAfield(fieldDef.getArrayDefinition().arrayName, new ArrayFieldHelper(record, fieldDef));
				}
			} else {
				addAfield(name, new FieldHelper(record, f));
			}
		}

		private void addAfield(String name,  IFieldHelper fieldDetails) {

			addField(name, fieldDetails);
		}

		public void addField(String name, IFieldHelper f) {
			if (lastRecord != f.record || currentRecordFields == null) {
				lastRecord = f.record;
				currentRecordFields = new ArrayList<>();
				recordFields.add(currentRecordFields);
			}	
			
			currentRecordFields.add(f);
			
			name = name.toLowerCase();
			List<IFieldHelper> fieldList = map.get(name);
			if (fieldList == null) {
				fieldList = new ArrayList<>(1);
				map.put(name, fieldList);
			}
			fieldList.add(f);

		}

		public List<List<IFieldHelper>> getDuplicateFieldNames() {
			Collection<List<IFieldHelper>> values = map.values();
			ArrayList<List<IFieldHelper>> ret = new ArrayList<>();

			for (List<IFieldHelper> v : values) {
				if (v.size() > 1) {
					ret.add(v);
				}
			}

			return ret;
		}
	}
	
	public static IFieldHelper newFieldHelper(RecordDef rec, FieldDef field) {
		IFieldHelper ret;
		
		if (field.isArrayItem()) {
			ret = new ArrayFieldHelper(rec, field);
		} else {
			ret = new FieldHelper(rec, field);
		}
		
		return ret;
		
	}
	
//	public static interface IFieldHelper {
//
//		int getFirstGroup();
//
//		String[] getGroupNames();
//
//		void printGroupNames();
//
//		void setUniqueGroupNames(int number);
//
//		String getName();
//
//		void setUniqueGroupNames(ArrayList<String> groupNames);
//
//		void insertUniqueGroupName(String gName);
//
//		void setName(String name);
//
//		void updateFirstGroup(int start);
//		
//	}
	

	public abstract static class IFieldHelper   {
		private final IUpdateField field;
//		private FieldDef fieldDef;
		private final IUpdateRecord record;
		private final String[] groupNames;
//		private final String originalName;
		String name;
		boolean arrayField = false, firstArrayElement = false;
		int firstGroup = 9;
		List<String> uniqueGroupNames = null;
		
		public IFieldHelper(IUpdateRecord record, IUpdateField field) {
			super();
			this.record = record;
			this.field = field;
			
			String g = field.getGroup();
			if (g == null || g.length() < 2) {
				groupNames = NO_GROUP_NAMES;
				firstGroup = 0;
			} else {
				groupNames = g.substring(1).split("\\.");
				firstGroup = groupNames.length - 1;
			}
			
//			String oName = field.getName();
//			FieldDef f;
//			if (field instanceof FieldDef && (f = (FieldDef) field).isArrayItem()) {
//				fieldDef = f;
//				oName = f.getArrayIndexDetails().getCobolName();
//			}

			name = field.getName();
		}


		public int getFirstGroup() {
			return firstGroup;
		}


		public void updateFirstGroup(int start) {
			firstGroup = Math.min(firstGroup, start);
		}
		
		public String[] getGroupNames() {
			return groupNames;
		}


		public abstract String getOriginalName(); 

		public void setName(String name) {
			this.name = name;
			
			field.setName(name);
		}
		
		public String getName() {
			return name;
		}
		
		public void insertUniqueGroupName(String gName) {
			
			if (uniqueGroupNames == null) {
				uniqueGroupNames = new ArrayList<>(1);
				uniqueGroupNames.add(gName);
			} else {
				uniqueGroupNames.add(0, gName);
			}
			updateFieldGroupNames();
		}
		
		public void setUniqueGroupNames(ArrayList<String> groupNames) {
			uniqueGroupNames = groupNames;
			updateFieldGroupNames();
		}
		
		public void setUniqueGroupNames(int number) {
			int groupNamesLength = groupNames.length;
			if (number > groupNamesLength) { return; }

			if (uniqueGroupNames == null) {
				uniqueGroupNames = new ArrayList<>(number);
			} else {
				uniqueGroupNames.clear();
			}
			
			for (int i = number; i > 0; i--) {
				uniqueGroupNames.add(groupNames[groupNamesLength - i]);
			}

			updateFieldGroupNames();
		}
		
		private void updateFieldGroupNames() {
			if (uniqueGroupNames != null && field instanceof ISetSelectionGroupNames) {
				((ISetSelectionGroupNames) field).setSelectionGroupNames(uniqueGroupNames);
			}
		}
		
		public void printGroupNames() {
			if (uniqueGroupNames !=null) {
				String sep = "\t{";
				for (String gn : uniqueGroupNames) {
					System.out.print(sep + gn);
					sep = "\t";
				}
				System.out.print("}");
			}
		}
	}
	
	public static class FieldHelper extends IFieldHelper  {
		private final String originalName;
		
		public FieldHelper(IUpdateRecord record, IUpdateField field) {
			super(record, field);
			
			originalName = field.getName();
		}
		
		public String getOriginalName() {
			return originalName;
		}
	}
	
	
	public static class ArrayFieldHelper extends IFieldHelper  {
		private final FieldDef field;
		private final String originalName;
		
		public ArrayFieldHelper(IUpdateRecord record, FieldDef field) {
			super(record, field);
			this.field = field;
				
			originalName = field.getArrayIndexDetails().getCobolName();
			name = originalName;
			
			arrayField = field.isArrayItem();
			ArrayElement arrayIndexDetails = field.getArrayIndexDetails();
			firstArrayElement = arrayIndexDetails != null && arrayIndexDetails.isFirstIndex();
		}
		
		public String getOriginalName() {
			return originalName;
		}

		@Override
		public void setName(String name) {
			this.name = name;
			field.getArrayIndexDetails().setName(name);
			field.getArrayDefinition().getFirstElement().setName(name);
		}

	}


	private static class GroupCompare  {
		int m1, m2;
		private GroupCompare(IFieldHelper h1, IFieldHelper h2) {
			int m1 = h1.getGroupNames().length - 1;
			int m2 = h2.getGroupNames().length - 1;
			
			while (m1 >= 0 && m2 >= 0 && h1.groupNames[m1].equalsIgnoreCase(h2.groupNames[m2]) ) {
				m1 -= 1;
				m2 -= 1;
			}
		}
	}
}
