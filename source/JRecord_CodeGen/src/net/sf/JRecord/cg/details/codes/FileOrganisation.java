package net.sf.JRecord.cg.details.codes;

import net.sf.JRecord.Common.Constants;

/**
 * 
 * @author Bruce Martin
 *
 */
public enum FileOrganisation {

	/** 
	 * Standard Text file
	 */
	TEXT(ArgumentOption.TEXT),
	/**
	 * All records are the same length, there is no \n 
	 * end of line character at the end of the line.
	 */
	FIXED_LENGTH(ArgumentOption.FIXED_LENGTH),
	/**
	 * Mainframe VB format, each record consists of<ul>
	 * <li>Record Descriptor Word (2 byte length followed by 2 bytes hex zero's. The Record-Length
	 * <b>excludes</b> the 4 bytes RDW)
	 * <li>Data
	 * </ul>
	 */
	MAINFRAME_VB(ArgumentOption.MAINFRAME_VB),
	/**
	 * GNU VB format very similar to the Mainframe VB format, each record consists of<ul>
	 * <li>Record Descriptor Word (2 byte length followed by 2 bytes hex zero's.  The Record-Length
	 * <b>includes</b> the 4 bytes RDW)
	 * <li>Data
	 * </ul>
	 */
	GNU_COBOL_VB(ArgumentOption.GNU_COBOL_VB),
	FUJITSU_VB(ArgumentOption.FUJITSU_VB),
	MAINFRAME_VB_DUMP(ArgumentOption.MAINFRAME_VB_DUMP),
	
	
	UNNOWN(null),
	
	;
	
	;
	
	/**
	 * Ensure option.codeGenCode is setup
	 */
	public static void init() {
		
	}
	
	public final ArgumentOption option;

	private FileOrganisation(ArgumentOption option) {
		this.option = option;
		if (option != null) {
			option.setCodeGenCode(this.getClass().getSimpleName() + "." + this.name());
		}
	}

	public int getFileOrganisationCode() {
		return option == null ? Constants.NULL_INTEGER : option.getId();
	}
}
