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
      
package net.sf.JRecord.cg.details.codes;

import net.sf.JRecord.Common.IFileStructureConstants;
import net.sf.JRecord.External.Def.BasicConversion;
import net.sf.JRecord.Numeric.ICopybookDialects;
import net.sf.JRecord.Option.ICobolSplitOptions;


/**
 * This class represents a JRecord-code in a variety of different formats
 * 
 * @author Bruce Martin
 *
 */
public class ArgumentOption {
	private static final String CODE_GEN_VALUE_NOT_SET_MESSAGE = "CodeGen Value Not Set";
	public static final ArgumentOption MAINFRAME_DIALECT = new ArgumentOption("Mainframe",      "ICopybookDialects.FMT_MAINFRAME",     "Mainframe Cobol", ICopybookDialects.FMT_MAINFRAME);
	public static final String OPT_SCHEMA   = "-Schema";
	public static final String OPT_TEMPLATE_DIRECTORY = "-TemplateDirectory";
	public static final String OPT_TEMPLATE = "-Template";
	public static final String OPT_SPLIT    = "-split";
	public static final String OPT_GENERATE = "-Generate";
	public static final String OPT_DIALECT  = "-Dialect";
	public static final String OPT_FILE_ORGANISATION = "-FileOrganisation";
	public static final String OPT_LOAD_SCHEMA = "-loadSchema";
	public static final String OPT_PACKAGE  = "-package";
	public static final String OPT_GEN_DATE = "-GenerateDate";
	public static final String OPT_JREC_VERSION = "-JRecordVersion";
	
	public static final String OPT_DROP_COPYBOOK_NAME = "-DropCopybookName";
	public static final String OPT_FONT_NAME = "-font";
	
	public static final String OPT_OUTPUT_DIR = "-outputDirectory";
	public static final String OPT_RECSEL  = "-recordSelection";

	
	public static final String JAVA_POJO_TEMPLATE = "javaPojo";
	public static final String LINE_WRAPPER_TEMPLATE = "lineWrapper";
	
	public static final ArgumentOption TEMPLATE_JAVA_POJO = stdTemplateArg(JAVA_POJO_TEMPLATE,   "Generate java classes for each Cobol Record");
	public static final ArgumentOption TEMPLATE_BASIC = stdTemplateArg("basic",  "Generate example code using JRecord IO Builders");
	public static final ArgumentOption TEMPLATE_WRAPPER_POJO = stdTemplateArg("lineWrapperPojo",   "Generate java wrapper & pojo classes for each Cobol Record");
	public static final ArgumentOption TEMPLATE_SCHEMA_CLASS = stdTemplateArg("schemaClass", "Generate example code using JRecord IO Builders + Schema details");
	public static final ArgumentOption TEMPLATE_LINE_WRAPPER = stdTemplateArg(LINE_WRAPPER_TEMPLATE, "Generate Wrapper classes for JRecord-Lines");
	public static final ArgumentOption TEMPLATE_LINE_WRAPPER_POJO = stdTemplateArg("lineWrapperPojo", "Generate Wrapper classes for JRecord-Lines + conversion to Pojo's");
	public static final ArgumentOption TEMPLATE_STANDARD = stdTemplateArg("standard",  "Generate example code using JRecord IO Builders + Field Name Class");
	public static final ArgumentOption TEMPLATE_POJO = stdTemplateArg("pojo",  "Generate pojo classes for each Cobol Record + IoBuilder");
	public static final ArgumentOption TEMPLATE_POJO_INTERFACE = stdTemplateArg("pojoWithInterface",  "Generate pojo classes (with interfaces) for each Cobol Record + IoBuilder");

	public static final ArgumentOption[] TEMPLATE_OPTIONS = {
		TEMPLATE_STANDARD,
		TEMPLATE_LINE_WRAPPER,
		TEMPLATE_SCHEMA_CLASS,
		TEMPLATE_WRAPPER_POJO,
		TEMPLATE_POJO,
		TEMPLATE_POJO_INTERFACE,
		TEMPLATE_JAVA_POJO,
		TEMPLATE_BASIC,
	};


	public static final ArgumentOption SPLIT_NONE = new ArgumentOption("None",      "CopybookLoader.SPLIT_NONE", "No Copybook split", ICobolSplitOptions.SPLIT_NONE );
	public static final ArgumentOption SPLIT_01_LEVEL = new ArgumentOption("01",  "CopybookLoader.SPLIT_01_LEVEL", "Split on 01 level", ICobolSplitOptions.SPLIT_01_LEVEL);
	public static final ArgumentOption SPLIT_REDEFINE = new ArgumentOption("redefine",  "CopybookLoader.SPLIT_REDEFINE", "Split on redefine", ICobolSplitOptions.SPLIT_REDEFINE);
	public static final ArgumentOption SPLIT_HIGHEST_REPEATING = new ArgumentOption("highest",   "CopybookLoader.SPLIT_HIGHEST_REPEATING", "Hightest Level", ICobolSplitOptions.SPLIT_HIGHEST_REPEATING);
	
	public static final ArgumentOption[] SPLIT_OPTS = {
		SPLIT_NONE, SPLIT_01_LEVEL, SPLIT_REDEFINE, SPLIT_HIGHEST_REPEATING,
	};
	
	public static final ArgumentOption TEXT = 		   newFileStructureOpt("Text",         "Constants.IO_BIN_TEXT",     "Standard Windows/Unix text file (single byte characterset)", 	IFileStructureConstants.IO_BIN_TEXT);
	public static final ArgumentOption FIXED_LENGTH = newFileStructureOpt("FixedWidth",   "Constants.IO_FIXED_LENGTH", "File where lines (records) are the same length no \\n", IFileStructureConstants.IO_FIXED_LENGTH);
	static final ArgumentOption MAINFRAME_VB = newFileStructureOpt("Mainframe_VB", "Constants.IO_VB",           "Mainframe VB, file consists of <record-length><record-data>", IFileStructureConstants.IO_VB);
	static final ArgumentOption GNU_COBOL_VB = newFileStructureOpt("GNU_COBOL_VB", "Constants.IO_VB_GNU_COBOL", "Gnu Cobol VB, file consists of <record-length><record-data>", IFileStructureConstants.IO_VB_GNU_COBOL);
	static final ArgumentOption FUJITSU_VB = newFileStructureOpt("FUJITSU_VB", "Constants.IO_VB_FUJITSU", "Fujitsu VB, file consists of <record-length><record-data><record-length>", IFileStructureConstants.IO_VB_FUJITSU);
	static final ArgumentOption MAINFRAME_VB_DUMP = newFileStructureOpt("Mainframe_VB_DUMP", "Constants.IO_VB_DUMP2",           "Mainframe VB, file consists of <record-length><record-data> + block length", IFileStructureConstants.IO_VB_DUMP2);;
	
	public static final ArgumentOption[] FILE_ORGANISATION_OPTS = {
			TEXT, FIXED_LENGTH, MAINFRAME_VB, GNU_COBOL_VB, MAINFRAME_VB_DUMP,
	};

	static final ArgumentOption FUJITSU 		= new ArgumentOption("Fujitsu",        "ICopybookDialects.FMT_FUJITSU",       "Fujitsu Cobol",   ICopybookDialects.FMT_FUJITSU);
	static final ArgumentOption GNU_COBOL 		= new ArgumentOption("GNU",            "ICopybookDialects.FMT_GNU_COBOL",     "Gnu Cobol",       ICopybookDialects.FMT_GNU_COBOL);
	static final ArgumentOption GNU_COBOL_MF	= new ArgumentOption("GNU_Microfocus", "ICopybookDialects.FMT_GNU_COBOL_MF",  "Gnu Cobol (Micro Focus mode)", ICopybookDialects.FMT_GNU_COBOL_MF);
	static final ArgumentOption GNU_COBOL_FS2000= new ArgumentOption("GNU_FS2000",     "ICopybookDialects.FMT_FS2000",        "Gnu Cobol (FS2000 mode)", ICopybookDialects.FMT_FS2000);
	;
	
	public static final ArgumentOption[] DIALECT_OPTS = {
		MAINFRAME_DIALECT, FUJITSU, GNU_COBOL, GNU_COBOL_MF, GNU_COBOL_FS2000,
	};

	public final String option, code, utlCode, description, cbl2csvCode;
	public final int id;
	private String codeGenCode = CODE_GEN_VALUE_NOT_SET_MESSAGE;
	
	
	public static ArgumentOption stdTemplateArg(String option, String description) {
		return new ArgumentOption(option, option, description, 0);
	}
	
	public static ArgumentOption newFileStructureOpt(String option, String code, String description, int id) {
		return new ArgumentOption(option, code, description, id, BasicConversion.getStructureName(id));
	}
	public ArgumentOption(String option, String code, String description) {
		this(option, code, description, 0);
	}
	public ArgumentOption(String option, String code, String description, int id) {
		this(option, code, description, id, "");
	}
	public ArgumentOption(String option, String code, String description, int id, String cbl2csvCode) {
		super();
		this.option = option;
		this.code = code;
		this.description = description;
		this.id = id;
		this.cbl2csvCode = cbl2csvCode;
		
		String u = code;
		int idx;
		if (u != null && (idx = u.indexOf('.')) >= 0) {
			u = u.substring(idx + 1);
		}
		utlCode = u;
	}
	
	/**
	 * CodeGen name for the option
	 * @return the option
	 */
	public final String getOption() {
		return option;
	}
	
	/**
	 * JRecord name for the option
	 * @return the JRecord name for the option
	 */
	public final String getCode() {
		return code;
	}
	
	/**
	 * Utility name (e.g. Cobol2Xml) for the object.
	 * 
	 * @return the Utility name (e.g. Cobol2Xml) for the object.
	 */
	public final String getUtlCode() {
		return utlCode;
	}
	/**
	 * Get the description for the option
	 * 
	 * @return the description
	 */
	public final String getDescription() {
		return description;
	}
	
	/**
	 * HGet the JRecord Integer code for the object
	 * 
	 * @return the JRecord Integer code
	 */
	public final int getId() {
		return id;
	}
	
	/**
	 * Get the Cobol2Csv name for the option
	 * 
	 * @return the cbl2csvCode
	 */
	public final String getCbl2csvCode() {
		return cbl2csvCode;
	}
	
	public boolean codeGenCodePresent() {
		return codeGenCode != CODE_GEN_VALUE_NOT_SET_MESSAGE;
	}
	/**
	 * @return the codeGenCode
	 */
	public String getCodeGenCode() {
		return codeGenCode;
	}

	/**
	 * @param codeGenCode the codeGenCode to set
	 */
	void setCodeGenCode(String codeGenCode) {
		this.codeGenCode = codeGenCode;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return option;
	}
}
