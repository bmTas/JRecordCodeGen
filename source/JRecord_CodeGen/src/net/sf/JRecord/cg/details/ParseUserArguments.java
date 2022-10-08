package net.sf.JRecord.cg.details;

import java.io.IOException;
import java.util.List;

import net.sf.JRecord.JRecordInterface1;
import net.sf.JRecord.Common.IFileStructureConstants;
import net.sf.JRecord.Common.RecordException;
import net.sf.JRecord.cg.details.codes.ArgumentOption;
import net.sf.JRecord.def.IO.builders.ICobolIOBuilder;
import net.sf.JRecord.def.IO.builders.IIOBuilder;
import net.sf.JRecord.utilityClasses.ParseArguments;

public class ParseUserArguments {
	private GenerateOptions generateOptions;
	private final GenOptValues optionValues;
	
	private final ParseArguments pgmArgs;
	
	private static final ArgumentOption DEFAULT_FILE_ORG = new ArgumentOption("IFileStructureConstants.IO_*", "IFileStructureConstants.IO_*", "", IFileStructureConstants.IO_DEFAULT);

	private static final ArgumentOption[] TEMPLATE_OPTIONS = ArgumentOption.TEMPLATE_OPTIONS;
	private static final ArgumentOption[] LOAD_SCHEMA_OPTS = {
		new ArgumentOption("inLine",   "", "Create a SchemaClass in code"),
		new ArgumentOption("fromFile", "", "Reload schema's from a file"),
		new ArgumentOption("both",   "", "Do both, create schema class and allow user to read from a file")};
	private static final ArgumentOption[] FILE_ORGANISATION_OPTS = ArgumentOption.FILE_ORGANISATION_OPTS;
	private static final ArgumentOption[] SPLIT_OPTS = ArgumentOption.SPLIT_OPTS;


	private boolean ok = true;
	private StringBuilder message = new StringBuilder();
	
	
	
	public ParseUserArguments(GenOptValues options, ParseArguments pa) {
		super();
		this.optionValues = options;
		this.pgmArgs = pa;
	}

	public void appendMessage(String msg) {
		System.out.println(msg);
		if (message.length() > 0) {
			message.append("\n\n");
		}
		message.append(msg);
	}

	public void appendErrorMessage(String msg) {
		appendMessage(msg);
		ok = false;
	}
	
	public void updateSchemaLoadOptions() {
		boolean inLineSchema = checkFor(ArgumentOption.OPT_LOAD_SCHEMA, false, false, "inLine", "both");
		boolean loadSchemaFromFile = checkFor(ArgumentOption.OPT_LOAD_SCHEMA, false, false, "fromFile", "both");
		
		if (inLineSchema) {
			optionValues.getTemplateDtls().generateOptions.put("inlineschema", true);
			if (loadSchemaFromFile) {
				optionValues.getTemplateDtls().generateOptions.put("loadschemafromfile", true);
			}
		} else {
			optionValues.getTemplateDtls().generateOptions.put("loadschemafromfile", true);
		}

	}
	
	public void updateGenerateOptions() {
		List<String> generateOpts = pgmArgs.getArgList(ArgumentOption.OPT_GENERATE);
		
		if (generateOpts != null) {
			for (String s : generateOpts) {
				String key = s;
				String v = s;
				int pos = s.indexOf('.');
				if (pos > 0) {
					key = s.substring(0, pos);
					v = s.substring(pos+1);
				}
				optionValues.getTemplateDtls().generateOptions.put(key.toLowerCase(), v);
			}
		} else {
			optionValues.getTemplateDtls().loadDefaultOptions();
		}

	}
	
	public void checkLoadHelper(ParseArguments pa) {
		checkFor(ArgumentOption.OPT_LOAD_SCHEMA, true, false,  LOAD_SCHEMA_OPTS);
	}

	public void checkFor(String key, boolean msg, boolean defaultVal, ArgumentOption[] opts) {
		String[] chkFor = new String[opts.length];
		int i = 0;
		for (ArgumentOption o : opts) {
			chkFor[i++] = o.option;
		}
		checkFor(key, msg, defaultVal, chkFor);
	}

	public boolean checkFor(String key, boolean msg, boolean defaultVal, String... opts) {
		String v = pgmArgs.getArg(key);
		
		if (v != null) {
			for (String o : opts) {
				if (o.equalsIgnoreCase(v)) {
					return true;
				}
			}
			if (msg) {
				System.out.println("Invalid value " + v + " for argument " + key);
				ok = false;
			}
		}
		
		return defaultVal;
	}

	
	private static String decodeTemplate(String templateDir, String template) {
		if (templateDir != null && templateDir.length() > 0) {
		} else if (template == null || template.length() == 0) {
			template = ArgumentOption.LINE_WRAPPER_TEMPLATE;
		}
		return template;
	}

	public ArgumentOption decodeFileOrganisation() {
		return decodeAsOpt(ArgumentOption.OPT_FILE_ORGANISATION, true, DEFAULT_FILE_ORG, FILE_ORGANISATION_OPTS);
	}

	public ArgumentOption decodeSplitOption(String splitVal) {
		return decodeAsOpt(splitVal, ArgumentOption.OPT_SPLIT, false, SPLIT_OPTS[0], SPLIT_OPTS);
	}

	public ArgumentOption decodeDialect(String dialectVal) {
		return decodeAsOpt(
				dialectVal, 
				ArgumentOption.OPT_DIALECT, false,
				ArgumentOption.MAINFRAME_DIALECT, 
				ArgumentOption.DIALECT_OPTS);
	}

	public ArgumentOption decodeAsOpt(String key, boolean printMsg, ArgumentOption defaultOption, ArgumentOption[] opts) {
		return decodeAsOpt(pgmArgs.getArg(key), key, printMsg, defaultOption, opts);
	}

	public ArgumentOption decodeAsOpt(String v, String key, boolean printMsg, ArgumentOption defaultOption, ArgumentOption[] opts) {
		
		if (v != null) {
			for (ArgumentOption o : opts) {
				if (o.option.equalsIgnoreCase(v)) {	
					return o;
				}
			}
			if (printMsg) {
				System.out.println("Invalid value " + v + " for argument " + key);
				ok = false;
			}
		}
		
		return defaultOption;
	}
	
	
	public void processError(Exception e) {
		System.out.println();
		System.out.println("Could not process the copybook (schema): " + e);
		System.out.println();
		ok = false;
	}
	
	public String  required(String key) {
		String r = pgmArgs.getArg(key);
		if (r == null || r.length() == 0) {
			System.out.println("Argument: " + key + " is required !!!");
			ok = false;
		}
		return r;
	}


	public static void printOptions() {
		System.out.println();
		System.out.println(" -------------------------------------------------------");
		System.out.println("Program: CodeGen");
		System.out.println("Purpose: Generate Skelton JRecord Code from a Cobol Copybook");
		System.out.println();
		System.out.println(" -------------------------------------------------------");
		System.out.println("Program Options:");
		System.out.println();
		System.out.println("    " + ArgumentOption.OPT_TEMPLATE_DIRECTORY + ":\tDirectory for user written Templates");
		System.out.println("    -Template:\tWhich template to generate");
		printList(TEMPLATE_OPTIONS);
		System.out.println("    -Schema:\tCobol copybook/Xml schema to generate code for");
		System.out.println("    " + ArgumentOption.OPT_PACKAGE + ":\tJava Package Id");
//		System.out.println("    " + ArgumentOption.OPT_LOAD_SCHEMA + ":\tWether to generate a Schema (LayoutDetail) class or not");
		printList(LOAD_SCHEMA_OPTS);
		System.out.println("    " + ArgumentOption.OPT_DIALECT + ":\tCobol Dialect ???");
		printList(ArgumentOption.DIALECT_OPTS);
		System.out.println("    " + ArgumentOption.OPT_FILE_ORGANISATION + ":\tWhat sort of file will be read ???");
		printList(FILE_ORGANISATION_OPTS);
		System.out.println("    " + ArgumentOption.OPT_SPLIT + ":\tHow to split the copybook up");
		printList(SPLIT_OPTS);
		System.out.println("    " + ArgumentOption.OPT_FONT_NAME + ":\tFont (characterset name");
		System.out.println("    " + ArgumentOption.OPT_DROP_COPYBOOK_NAME + ":\tWhether to Drop the copybook name from the start of field names");
		System.out.println("    " + ArgumentOption.OPT_LOAD_SCHEMA + ":\tWether to generate a Schema (LayoutDetail) class or not");
//		printList(LOAD_SCHEMA_OPTS);
//		System.out.println("    " + OPT_GENERATE + ":\tWhich skeltons to generate, for template=javaPojo:");
//		printList(GENERATE_OPTS);
		System.out.println("    " + ArgumentOption.OPT_OUTPUT_DIR + ":\tOutput directory");
		System.out.println();
		System.out.println("    -h -?:\tList options");
		System.out.println();
	}
	
	private static void printList(ArgumentOption[] list) {
		for (ArgumentOption o : list) {
			System.out.println("\t\t" + o.option + "\t- " + o.description );
		}
	}

	/**
	 * @return the ok
	 */
	public boolean isParseOk() {
		return ok;
	}


	public final boolean isAllOk() {
		return ok && optionValues.getTemplateDtls().isOk();
	}
	

	/**
	 * @return the generateOptions
	 */
	public GenerateOptions getGenerateOptions() {
		return generateOptions;
	}

	public static ParseUserArguments parseArgsToHelper(ParseArguments pa) {
		GenOptValues genOptVals = new GenOptValues();
		ParseUserArguments helper = new ParseUserArguments(genOptVals, pa);
//		List<String> generateOpts = pa.getArgList(ArgumentOption.OPT_GENERATE);
		
		String splitVal = pa.getArg(ArgumentOption.OPT_SPLIT);
		String dialectVal = pa.getArg(ArgumentOption.OPT_DIALECT);
		String dropVal = pa.getArg(ArgumentOption.OPT_DROP_COPYBOOK_NAME, "");
		String versionStr =  pa.getArg(ArgumentOption.OPT_JREC_VERSION, "");
		int version = 0;
		if (versionStr.length() > 0) {
			try {
				double d = Double.parseDouble(versionStr) * 1000 + 0.5;
						
				version = (int) d;
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
				
		String schemaName   = helper.required(ArgumentOption.OPT_SCHEMA);
		String templateDir  = pa.getArg(ArgumentOption.OPT_TEMPLATE_DIRECTORY, "");

		genOptVals.setTemplateDtls( new TemplateDtls(
									templateDir,
									decodeTemplate(templateDir, pa.getArg(ArgumentOption.OPT_TEMPLATE)), 
									TemplateDtls.DEFAULT_TEMPLATE_BASE,
									genOptVals.isMultiRecord(),
									version > 0 ? version : TemplateDtls.DEFAULT_JREC_VERSION,
									pa.getArg(ArgumentOption.OPT_GEN_DATE)));

//		if (BASIC_TEMPLATE.equals(template)) {
		if (genOptVals.getTemplateDtls().hasOption(TemplateDtls.T_REQUIRE_PACKAGE_ID)) {
			genOptVals.setPackageId(helper.required(ArgumentOption.OPT_PACKAGE));
		} else {
			String s = pa.getArg(ArgumentOption.OPT_PACKAGE);
			if (s == null) {
				s = "";
			}
			genOptVals.setPackageId(s);
		}
		if (! genOptVals.getTemplateDtls().hasOption(TemplateDtls.T_SPLIT_ALLOWED)) {
			if (splitVal != null && splitVal.length() > 0) {
				helper.appendErrorMessage( "Split is not supported for " + genOptVals.getTemplateDtls().template + " !!!");
			}
		}
		
		helper.updateSchemaLoadOptions();	
		
		helper.checkLoadHelper(pa);
		genOptVals.setFileOrganisation(helper.decodeFileOrganisation());
		genOptVals.splitOption = helper.decodeSplitOption(splitVal);
		genOptVals.dialect = helper.decodeDialect(dialectVal);
		
		genOptVals.dropCopybookName = dropVal != null && dropVal.toLowerCase().startsWith("t");
		genOptVals.font = pa.getArg(ArgumentOption.OPT_FONT_NAME, "");
		genOptVals.outputDir = pa.getArg(ArgumentOption.OPT_OUTPUT_DIR, ".");
		
//		List<RecordSelect> recordSelect = new ArrayList<RecordSelect>(10);

		List<String> recSelList = pa.getArgList(ArgumentOption.OPT_RECSEL);
		if (recSelList != null && recSelList.size() > 0) {
			RecordSelect recSel;

			for (String s : recSelList) {
				genOptVals.addRecordSelection((recSel = new RecordSelect(s)));
				if (! recSel.ok()) {
					helper.appendMessage("Invalid Record Selection=" + s);
				}
			}
		}

		helper.updateGenerateOptions();

		if (helper.isParseOk()) {
			IIOBuilder ioBldr;
			boolean xmlSchema = schemaName.toLowerCase().endsWith(".xml");

			try {
				if (xmlSchema) {
					ioBldr = JRecordInterface1.SCHEMA_XML.newIOBuilder(schemaName);
				} else {
					ICobolIOBuilder cblIoBldr = genOptVals.createIoBuilder(schemaName);
//				for (RecordSelect rs : recordSelect) {
//					cblIoBldr.setRecordSelection(rs.recordName, rs.recordSelection);
//				}

					ioBldr = cblIoBldr;
				}
				genOptVals.setSchemaDefinition(ioBldr.getLayout(), schemaName);
			} catch (RecordException e) {
				helper.processError(e);
			} catch (IOException e) {
				helper.processError(e);
			}
		} 				
		helper.generateOptions = new GenerateOptions(genOptVals);
		return helper;
	}

	
//	   public static ExternalFieldSelection newFieldSelection(String fieldName, String op, String value) {
//	    	ExternalFieldSelection r = new ExternalFieldSelection(fieldName, value, op);
//	    	r.setCaseSensitive(false);
//	    	return r;
//	    }
}
