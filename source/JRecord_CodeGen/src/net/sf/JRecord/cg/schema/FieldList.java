package net.sf.JRecord.cg.schema;

import java.util.ArrayList;

import net.sf.JRecord.External.Def.DependingOnDtls;

public class FieldList {
	private final ArrayList<FieldDef> fields;

	public FieldList(ArrayList<FieldDef> fields) {
		super();
		this.fields = fields;
	}
	
	public DependingOnDef createDependingOnDefinition(DependingOnDtls dependingOnDtls) {
		DependingOnDef dependingOnDef = null;
		if (dependingOnDtls != null)  {
			String variableName = dependingOnDtls.getDependingOn().getVariableName();
			if (variableName != null && variableName.length() > 0) {
				for (FieldDef f : fields) {
					if (variableName.equalsIgnoreCase(f.getFieldDetail().getName())) {
						dependingOnDef = new DependingOnDef(
								createDependingOnDefinition(dependingOnDtls.parent),
								dependingOnDtls, 
								f);
					}
				}
			}
		}
		return dependingOnDef;
	}

}
