/*  -------------------------------------------------------------------------
 *
 *            Sub-Project: JRecord Cbl2Xml
 *    
 *    Sub-Project purpose: Convert Cobol Data files to / from Xml
 *
 *                 Author: Bruce Martin
 *    
 *                License: LGPL 2.1 or latter
 *                
 *    Copyright (c) 2016, Bruce Martin, All Rights Reserved.
 *   
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU Lesser General Public
 *    License as published by the Free Software Foundation; either
 *    version 2.1 of the License, or (at your option) any later version.
 *   
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU Lesser General Public License for more details.
 *
 * ------------------------------------------------------------------------ */

package net.sf.JRecord.cg.details;

import net.sf.JRecord.ExternalRecordSelection.ExternalFieldSelection;
import net.sf.JRecord.ExternalRecordSelection.ExternalSelection;

/**
 * Store user supplied Record Selection option
 * @author Bruce Martin
 *
 */
public class RecordSelect {

	public final String recordName;
	public final ExternalSelection recordSelection; 
//	private final String inStr; //used in testing
	private final boolean isOk;
	
	public RecordSelect(String arg) {
//		inStr = arg; //used in testing
		arg = arg.trim();
		
		String rn = arg, fieldName = null, value = null;
		int pos = arg.indexOf(' ');
		if (pos >= 0) {
			rn = arg.substring(0, pos).trim();
			arg = arg.substring(pos).trim();
			
			pos = arg.indexOf('=');
			if (pos < 0) {
				pos = arg.indexOf(' ');
			}
			if (pos >= 0) {
				fieldName = arg.substring(0, pos).trim();
				value = arg.substring(pos+1).trim();
			}
		}
		recordName = rn;
		
    	ExternalFieldSelection r = new ExternalFieldSelection(fieldName, value, "=");
    	r.setCaseSensitive(false);
    	recordSelection = r;
    	
    	isOk = fieldName != null && fieldName.length() > 0 && fieldName.indexOf(' ') < 0
    			&&	value != null && value.length() > 0; 
	}
	
	public RecordSelect(String recordName, ExternalSelection recordSelection) {
		super();
		this.recordName = recordName;
		this.recordSelection = recordSelection;
		this.isOk = true;
	}

	public final boolean ok() {
		return  isOk; 
	}
	

	
	
//  Test Code:
	
//	private void print() {
//		System.out.println("==>" + inStr + "<-\t->" + recordName + "<- ->" + fieldName + "< == >" + value + "<");
//	}
//	
//	public static void main(String[] args) {
//		(new RecordSelect(" aa ")).print();
//		(new RecordSelect("aa bb")).print();
//		(new RecordSelect("aa bb cc")).print();
//		(new RecordSelect(" aa bb cc ")).print();
//		(new RecordSelect(" aa  bb  cc ")).print();
//		(new RecordSelect("   aa    bb    cc  ")).print();
//		
//		(new RecordSelect("aa bb=cc")).print();
//		(new RecordSelect(" aa bb=cc ")).print();
//		(new RecordSelect("  aa bb =cc  ")).print();
//		(new RecordSelect("  aa bb= cc  ")).print();
//		(new RecordSelect("  aa bb = cc  ")).print();
//		(new RecordSelect("  aa bb  = cc  ")).print();
//		(new RecordSelect("  aa bb=  cc  ")).print();
//		(new RecordSelect("  aa bb  =  cc  ")).print();
//	}
}
