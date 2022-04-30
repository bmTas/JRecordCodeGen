package net.sf.JRecord.cg.schema;

import java.util.ArrayList;
import java.util.List;

import net.sf.JRecord.External.Def.DependingOnDtls;

public class DependingOnDef {
	private final DependingOnDtls dependingOnDtls;
	private final FieldDef dependingOnField;
	private final int arrayIndexNumber;
	private final DependingOnDef parent; 
	private List<DependingOnDef> children; 
	
	public DependingOnDef(DependingOnDef parent, DependingOnDtls dependingOnDtls, FieldDef dependingOnField) {
		super();
		this.dependingOnDtls = dependingOnDtls;
		this.dependingOnField = dependingOnField;
		this.arrayIndexNumber = dependingOnDtls.arrayIndexNumber;
		this.parent = parent;
		this.children =  new ArrayList<DependingOnDef>(3);
		
		dependingOnField.addDependingOnDefinition(this);
		if (parent != null) {
			parent.children.add(this);
		}
	}

	/**
	 * @return the parent
	 */
	public DependingOnDef getParent() {
		return parent;
	}

	/**
	 * @return the dependingOnDtls
	 */
	public DependingOnDtls getDependingOnDtls() {
		return dependingOnDtls;
	}


	/**
	 * @return the dependingOnField
	 */
	public FieldDef getDependingOnField() {
		return dependingOnField;
	}
	public boolean isDependingOnFieldAnArrayField() {
		return dependingOnField != null
				&& dependingOnField.isArrayItem();
	}

	
	public boolean isThereaDependOnArrayField() {
		return (dependingOnField != null && dependingOnField.isArrayItem())
			|| (parent != null && parent.isThereaDependOnArrayField());
	}

	/**
	 * @return the indexCount
	 */
	public int getArrayIndexNumber() {
		return arrayIndexNumber;
	}

	/**
	 * @return the children
	 */
	public List<DependingOnDef> getChildren() {
		return children;
	}
	
	public boolean hasChildren() {
		return children.size() > 0;
	}
}
