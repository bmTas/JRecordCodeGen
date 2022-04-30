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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ArrayDetails {

	public final String arrayName;
	public final int[] sizes, indexPositionIncrement;
	private final ArrayList<FieldDef> fieldDefs = new ArrayList<FieldDef>();
	private boolean toSort = true;
	private final ArrayElement firstElement;
	private final String zeroArrayIndex, dimensionString;
	private final DependingOnDef dependingOnDefinition;
//	private FieldDef endFieldDef;
	
	public ArrayDetails(ArrayElement ai, DependingOnDef dependingOnDefinition) {
		this.arrayName = ai.arrayName;
		this.firstElement = ai;
		this.dependingOnDefinition = dependingOnDefinition;
		
		sizes = new int[ai.indexs.length];
		indexPositionIncrement = new int[ai.indexs.length];
		Arrays.fill(sizes, 1);
		
		StringBuilder b = new StringBuilder("");
		String sep = "";
		for (int i = 0; i < sizes.length; i++) {
			b.append(sep).append("0");
			sep = ", ";
		}
		
		zeroArrayIndex = b.toString();
		
		switch (sizes.length) {
		case 1:
			dimensionString = "1";
			break;
		case 2:
			dimensionString = "2";
			break;
		case 3:
			dimensionString = "3";
			break;
		default:
			dimensionString = "Any";
		}
	} 
	
	/**
	 * @return the arrayName
	 */
	public final String getArrayName() {
		return arrayName;
	}

	public FieldDef getFirstField() {
		return fieldDefs.get(0);
	}

	public final void addFirstField(FieldDef fieldDef) {
		if (fieldDefs.size() > 0) { throw new RuntimeException("Should be called first with the first field"); }
		fieldDefs.add(fieldDef);
	}

	public final void addDetails(ArrayElement ai, FieldDef fieldDef) {
		for (int i = 0; i < sizes.length; i++) {
			sizes[i] = Math.max(sizes[i], ai.indexs[i]);
		}
		
		ArrayElement arrayDetails = fieldDef.getArrayIndexDetails();
		if (arrayDetails.special) {
			fieldDefs.add(fieldDef);
			indexPositionIncrement[arrayDetails.specialLevel] = fieldDef.getPos() - fieldDefs.get(0).getPos();
		}
	}
	
	public boolean sizesEqual(ArrayDetails ai) {
		if (sizes.length != ai.sizes.length) {
			return false;
		}
		for (int i = 0; i < sizes.length; i++) {
			if (sizes[i] != ai.sizes[i]) {
				return false;
			}
		}
		boolean ret = false;
		if (dependingOnDefinition == null && ai.dependingOnDefinition == null) {
			ret = true;
		} else if (dependingOnDefinition != null && ai.dependingOnDefinition != null) {
			ret = dependingOnDefinition.getDependingOnField() == ai.dependingOnDefinition.getDependingOnField();
		}
		return ret;
	}

	/**
	 * @return the fieldDefs
	 */
	public final ArrayList<FieldDef> getFieldDefs() {
		if (toSort) {
			Collections.sort(fieldDefs, new Comparator<FieldDef>() {
				@Override public int compare(FieldDef o1, FieldDef o2) {
					if (o1.getArrayIndexDetails().specialLevel > o2.getArrayIndexDetails().specialLevel) {
						return 1;
					} else if (o1.getArrayIndexDetails().specialLevel < o2.getArrayIndexDetails().specialLevel) {
						return -1;
					}
					return 0;
				}
			});
			
			toSort = false;
		}
		return fieldDefs;
	}
	
	/**
	 * @return the dependingOnDtls
	 */
	public DependingOnDef getDependingOnDefinition() {
		return dependingOnDefinition;
	}

	public FieldDef getFirstFieldDef() {
		 ArrayList<FieldDef> fd = getFieldDefs();
		 return fd.get(fd.size() - 1);
	}

	/**
	 * @return the firstElement
	 */
	public final ArrayElement getFirstElement() {
		return firstElement;
	}

	/**
	 * @return the zeroArrayIndex
	 */
	public final String getZeroArrayIndex() {
		return zeroArrayIndex;
	}

	/**
	 * @return the dimensionString
	 */
	public final String getDimensionString() {
		return dimensionString;
	}
	
	public final int getDimensionCount() {
		return sizes.length;
	}
	
	public String getIndexParameters() {
		StringBuilder b = new StringBuilder();
		
		String sep = "";
		for (int i = 1; i <= sizes.length; i++) {
			b.append(sep).append("int idx").append(i);
			sep = ", ";
		}

		
		return b.toString();
	}
	
	
	public String getZeroIndexParameters() {
		StringBuilder b = new StringBuilder();
		
		String sep = "";
		for (int i = 1; i <= sizes.length; i++) {
			b.append(sep).append("0");
			sep = ", ";
		}

		return b.toString();
	}
	
	public String getCallIndexParameters() {
		StringBuilder b = new StringBuilder();
		
		String sep = "";
		for (int i = 1; i <= sizes.length; i++) {
			b.append(sep).append("idx").append(i);
			sep = ", ";
		}

		
		return b.toString();
	}

	public String getArrayAccessParameters() {
		StringBuilder b = new StringBuilder();
		
		for (int i = 1; i <= sizes.length; i++) {
			b.append("[idx").append(i).append(']');
		}

		
		return b.toString();
	}



	
	public String getForLoops(String destName) {
		StringBuilder b = new StringBuilder();
		
		StringBuilder indent = new StringBuilder("        ");
		if (destName == null || destName.length() == 0) {
			for (int i = 1; i <= sizes.length; i++) {
				b.append(indent).append("for (int idx" + i + " = 0; idx" + i + " < " + sizes[i - 1] + "; idx" + i + "++) {\n");
				indent.append("    ");
			}
		} else if (this.isThereaDependOnArrayField()) {
			for (int i = 1; i <= sizes.length; i++) {
				b.append(indent).append("for (int idx" + i + " = 0; idx" + i + " < " + destName + "(");
				String sep = "";
				for (int j = 1; j < i; j++) {
					b.append(sep).append("idx").append(j);
					sep = ", ";
				}
				b.append("); idx" + i + "++) {\n");
				indent.append("    ");
			}
		} else {
			for (int i = 1; i <= sizes.length; i++) {
				b.append(indent).append("for (int idx" + i + " = 0; idx" + i + " < " + destName + "(" + (i-1) + "); idx" + i + "++) {\n");
				indent.append("    ");
			}
		}

		if (b.length() > 0) {
			b.setLength(b.length() - 1);
		}
		return b.toString();
	}
	
	
	public String getEndForLoops() {
		StringBuilder b = new StringBuilder();
		
		String spaces = "                                                                                                ";
		StringBuilder indent = new StringBuilder(spaces).append(spaces).append(spaces);
		indent.setLength(sizes.length * 4 + 4);

		for (int i = 1; i <= sizes.length; i++) {
			b.append(indent).append("}\n");
			indent.setLength(indent.length() - 4);
		}

		return b.toString();
	}

	/**
	 * @return the sizes
	 */
	public final int[] getSizes() {
		return sizes;
	}

	public final String getSizesAsString() {
		StringBuilder b = new StringBuilder();
		String sep = "";
		
		for (int i = 0; i < sizes.length; i++) {
			b.append(sep).append((sizes[i]+1));
			sep = ", ";
		}
		
		return b.toString();
	}
	
	/**
	 * Wether it is an occurs depending on array or not
	 * @return is it an occurs depending on array
	 */
	public boolean isOccursDependingOn() {
		return dependingOnDefinition != null;
	}
	
	public boolean isThereaDependOnArrayField() {
		return dependingOnDefinition != null
			&& dependingOnDefinition.isThereaDependOnArrayField();
	}

	/**
	 * Return list of depending of field details
	 * @return 
	 */
	public List<DependingOnDef> getDependingOnList() {
		return updateDependingOnDtls(new ArrayList<DependingOnDef>(), dependingOnDefinition);
	}
	
	private List<DependingOnDef> updateDependingOnDtls(List<DependingOnDef> dependingList, DependingOnDef dependingOnDef) {
		if (dependingOnDef != null) {
//			if (dependingOnDefinition.getDependingOnField() != null 
//			&& dependingOnDefinition.getDependingOnField().getArrayDefinition() != null) {
//				dependingOnDefinition.getDependingOnField().getArrayDefinition().updateDependingOnDtls(dependingList);
//			}
			if (dependingOnDef.getParent() != null) {
				updateDependingOnDtls(dependingList, dependingOnDef.getParent());
			}
			dependingList.add(dependingOnDef);
		}
		return dependingList;
	}
}
