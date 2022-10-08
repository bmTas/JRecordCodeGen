package net.sf.JRecord.cg.details;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import net.sf.JRecord.JRecordInterface1;
import net.sf.JRecord.Common.Conversion;
import net.sf.JRecord.Details.IGetLayout;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.RecordDecider;
import net.sf.JRecord.Details.RecordDetail;
import net.sf.JRecord.cg.copybook.CobolCopybookSource;
import net.sf.JRecord.cg.details.codes.ArgumentOption;
import net.sf.JRecord.cg.details.codes.CobolDialects;
import net.sf.JRecord.cg.details.codes.FileOrganisation;
import net.sf.JRecord.cg.schema.LayoutDef;
import net.sf.JRecord.cg.update.duplicateFieldNames.RenameDuplicates;
import net.sf.JRecord.cgen.def.gen.ICodeGenDefinition;
import net.sf.JRecord.def.IO.builders.ICobolIOBuilder;
import net.sf.JRecord.def.recordSelection.ISingleFieldDeciderDetails;
import net.sf.cb2xml.copybookReader.CopybookColumns;
import net.sf.cb2xml.copybookReader.ICobolCopybookTextSource;

public class GenOptValues implements IGetLayout {
	
	
	static {
		CobolDialects.init();
		FileOrganisation.init();
	}

	private String packageId="";
	String packageDir="", font="", outputDir="";
	
	boolean fullBuilder = true;
	private ArgumentOption io;
	ArgumentOption
			splitOption = ArgumentOption.SPLIT_NONE,
			dialect = ArgumentOption.MAINFRAME_DIALECT;
	LayoutDetail layout;
	private LayoutDef schemaDefinition;
	
	boolean dropCopybookName=false;
	
	private TemplateDtls templateDtls;
	
	private RecordDecider recordDecider;
	private ICodeGenDefinition generateDecider;
	
	private List<RecordSelect> recordSelections = new ArrayList<RecordSelect>(10);
	
	private boolean renameDuplicateFields = false, doRename = true;
	
	private String copybookFileName="";
	private ICobolCopybookTextSource cobolCopybookTextSource;
	private final CobolCopybookSource cobolCopybook = new CobolCopybookSource();
	private boolean reloadCopybook = true;


	/**
	 * @return the packageId
	 */
	public String getPackageId() {
		return packageId;
	}

	/**
	 * @param packageId the packageId to set
	 */
	public void setPackageId(String packageId) {
		this.packageId = packageId;
		this.packageDir = packageId == null ? "" : Conversion.replace(packageId, ".", "/") + "/";
	}

	/**
	 * @return the File-Organisation
	 */
	public ArgumentOption getFileOrganisation() {
		return io;
	}

	/**
	 * @param io the File-Organisation to set
	 */
	public void setFileOrganisation(ArgumentOption io) {
		this.io = io;
		this.reloadCopybook = true;
	}

	/**
	 * @param copybookFileFormat the copybookFileFormat to set
	 */
	public void setCopybookFileFormat(CopybookColumns copybookColumns) {
		this.cobolCopybook.setCopybookColumns(copybookColumns);
	}

	/**
	 * @param cobolCopybookTextSource the cobolCopybookTextSource to set
	 */
	public void setCobolCopybookSource(ICobolCopybookTextSource cobolCopybookTextSource) {
		this.cobolCopybookTextSource = cobolCopybookTextSource;
	}


	void readCobolCopybook(String schemaName) throws IOException {
		copybookFileName = schemaName;
		cobolCopybook.readCobolCopybook(schemaName);
		cobolCopybookTextSource = cobolCopybook;
		reloadCopybook = true;
	}


	void readCobolCopybook(Reader cblReader, String schemaName) throws IOException {

		cobolCopybook 
						.setCopybookName(schemaName)
						.readCobolCopybook(cblReader);
		cobolCopybookTextSource = cobolCopybook;
		reloadCopybook = true;
	}

	/**
	 * @return the copybookFileName
	 */
	public String getCopybookFileName() {
		return copybookFileName;
	}

	/**
	 * @param copybookFileName the copybookFileName to set
	 */
	public void setCopybookFileName(String copybookFileName) {
		this.copybookFileName = copybookFileName;
	}

	void setSchemaDefinition(LayoutDetail schema, String schemaName) {
		
		if (templateDtls != null) {
			templateDtls.setMultiRecord(schema.getRecordCount() > 1);
			if (renameDuplicateFields) {
				
			} else if (! templateDtls.hasOption(TemplateDtls.T_DUPLICATE_FIELD_NAMES)) {
				check4DuplicateFieldNames(schema);
			} else {
				check4DuplicateArrayFieldNames(schema);
			}
		}

		layout = schema;
		
		schemaDefinition = new LayoutDef(schema, schemaName, null, generateDecider);
		
		doRename = true;
		renameDupFields();
		copybookFileName = schemaName;
		reloadCopybook = false;
	}

	private void renameDupFields() {
		if (doRename && renameDuplicateFields) {
			new RenameDuplicates(schemaDefinition)
					.updateDuplicateNamesInEachRecord2();
			doRename = false;
		}
	}
	
	/**
	 * @param recordDecider the recordDecider to set
	 */
	public void setRecordDecider(RecordDecider recordDecider) {
		this.recordDecider = recordDecider;
		this.reloadCopybook = true;
	}
	
	/**
	 * @param generateDecider class to create RecordDecider
	 */
	public void setRecordDecider(ICodeGenDefinition generateDecider) {
		this.generateDecider = generateDecider;
		this.reloadCopybook = true;
	}

	/**
	 * @param renameDuplicateFields the renameDuplicateFields to set
	 */
	public void setRenameDuplicateFields(boolean renameDuplicateFields) {
		this.renameDuplicateFields = renameDuplicateFields;
		this.reloadCopybook = true;
		renameDupFields();
	}

	/**
	 * @param recordSelection Record name and selection criteria
	 * @return
	 * @see java.util.List#add(java.lang.Object)
	 */
	public void addRecordSelection(RecordSelect recordSelection) {
		this.recordSelections.add(recordSelection);
	}

	/**
	 * @return the templateDtls
	 */
	public TemplateDtls getTemplateDtls() {
		return templateDtls;
	}

	/**
	 * @param templateDtls the templateDtls to set
	 */
	public void setTemplateDtls(TemplateDtls templateDtls) {
		this.templateDtls = templateDtls;
		
		if (templateDtls != null) {
			templateDtls.setMultiRecord(isMultiRecord());
		}
	}
	
	public boolean isMultiRecord() {
		return schemaDefinition != null && schemaDefinition.getRecords() != null && schemaDefinition.getRecords().size() > 1;
	}

	/**
	 * @return the layout
	 */
	public LayoutDetail getLayout() {
		if (reloadCopybook && cobolCopybookTextSource != null) {
			try {
				setSchemaDefinition(createIoBuilder().getLayout(), cobolCopybookTextSource.getCopybookName());
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			reloadCopybook = false;
		}
		
		return layout;
	}

	/**
	 * @return the schemaDefinition
	 */
	public LayoutDef getSchemaDefinition() {
		getLayout();
		return schemaDefinition;
	}

	/**
	 * @param schema
	 * @throws RuntimeException
	 */
	private void check4DuplicateFieldNames(LayoutDetail schema)
			throws RuntimeException {
		Set<String> duplicateFieldNames = schema.getDuplicateFieldNames();
		HashSet<String> dups = new HashSet<String>();
		if (duplicateFieldNames != null && duplicateFieldNames.size() > 0) {
			for (int i = 0; i < schema.getRecordCount(); i++) {
				HashMap<String, DuplicateDetails> dupDtls = new HashMap<String, DuplicateDetails>();
				RecordDetail record = schema.getRecord(i);
				for (int fldNum = 0; fldNum < record.getFieldCount(); fldNum++) {
					String name = record.getField(fldNum).getName().toLowerCase();
					DuplicateDetails d;
					if ((d = dupDtls.get(name)) != null) {
						d.count += 1;
					} else if (duplicateFieldNames.contains(name)) {
						dupDtls.put(name, new DuplicateDetails(name));
					}
				}
				
				 Collection<DuplicateDetails> dupDtlsValues = dupDtls.values();
				 
				 for (DuplicateDetails dd : dupDtlsValues) {
						String s = dd.fieldName;
						int indexOf = s.indexOf('(');
						if (indexOf > 0) {
							s = s.substring(0, indexOf - 1);
						}
						dups.add(s);
				 }
			}
			StringBuilder b = new StringBuilder("Duplicate Field Names:");
			
			Iterator<String> dupIterator = dups.iterator();
			int count = 0;
			for (int i = 0; dupIterator.hasNext(); i++) {
				if (i % 3 == 0) {
					b.append('\n');
				}
				b.append('\t').append(dupIterator.next());
				count += 1;
			}
			
			if (count > 0) {
				System.err.println( b.toString() );
				System.err.println();
				throw new RuntimeException("Duplicate Cobol FieldNames are not allowed for this Template");				
			}
//			HashSet<String> dups = new HashSet<String>(duplicateFieldNames.size() * 3 / 2);
//			Iterator<String> dupIterator = duplicateFieldNames.iterator();
//			while (dupIterator.hasNext()) {
//				String s = dupIterator.next();
//				int indexOf = s.indexOf('(');
//				if (indexOf > 0) {
//					s = s.substring(0, indexOf - 1);
//				}
//				dups.add(s);
//			}
//			
//			StringBuilder b = new StringBuilder("Duplicate Field Names:");
//			
//			dupIterator = dups.iterator();
//			for (int i = 0; dupIterator.hasNext(); i++) {
//				if (i % 3 == 0) {
//					b.append('\n');
//				}
//				b.append('\t').append(dupIterator.next());
//			}
//			System.err.println( b.toString() );
//			System.err.println();
//			throw new RuntimeException("Duplicate Cobol FieldNames are not allowed for this Template");				
		}
	}
	
	
	private void check4DuplicateArrayFieldNames(LayoutDetail schema)
			throws RuntimeException {
		Set<String> duplicateFieldNames = schema.getDuplicateFieldNames();
		if (duplicateFieldNames != null && duplicateFieldNames.size() > 0) {
			HashSet<String> dups = new HashSet<String>(duplicateFieldNames.size() * 3 / 2);
			Iterator<String> dupIterator = duplicateFieldNames.iterator();
			while (dupIterator.hasNext()) {
				String s = dupIterator.next();
				int indexOf = s.indexOf('(');
				if (indexOf > 0) {
					dups.add( s.substring(0, indexOf - 1));
				}
			}
			
			if (dups.size() > 0) {
				StringBuilder b = new StringBuilder("Duplicate Array Field Names:");
				
				dupIterator = dups.iterator();
				for (int i = 0; dupIterator.hasNext(); i++) {
					if (i % 3 == 0) {
						b.append('\n');
					}
					b.append('\t').append(dupIterator.next());
				}
				System.err.println( b.toString() );
				System.err.println();
				throw new RuntimeException("Duplicate Cobol Array FieldNames ar not allowed");
			}
		}
	}
	
	/**
	 * create the IoBuilder
	 * @param genOptVals
	 * @param schemaName
	 * @return
	 * @throws IOException 
	 */
	protected ICobolIOBuilder createIoBuilder(String schemaName) throws IOException {
		cobolCopybook.readCobolCopybook(schemaName);
		cobolCopybookTextSource = cobolCopybook;
		return createIoBuilder();
	}

	/**
	 * @param schemaName
	 * @return
	 * @throws IOException
	 */
	protected CobolCopybookSource createCopybookSource(String schemaName) throws IOException {
		return new CobolCopybookSource().readCobolCopybook(schemaName);
	}


//	private ICobolIOBuilder createIoBuilder(Reader cblReader, String schemaName) throws IOException {
//		return createIoBuilder(createCopybookSrc()
//								.setCopybookName(schemaName)
//								.addCobolCopybook(cblReader));
//	}

//	/**
//	 * 
//	 */
//	private ReadCobolCopybook createCopybookSrc() {
//		ReadCobolCopybook cblSrc = new ReadCobolCopybook();
//		if (copybookFileFormat != null) {
//			cblSrc.setColumns(copybookFileFormat);
//		}
//		return cblSrc;
//	}

	private ICobolIOBuilder createIoBuilder() { //ICobolIOBuilder ioBldr) {
		ICobolIOBuilder ioBldr = JRecordInterface1.COBOL.newIOBuilder(cobolCopybookTextSource);
		if (io == null) {
			io = Conversion.isEbcidic(font) ? ArgumentOption.FIXED_LENGTH : ArgumentOption.TEXT;
		}
//		if (copybookFileFormat != null) {
//			ioBldr.setCopybookFileFormat(copybookFileFormat.columnTypeConstant);
//		}
		if (recordDecider != null) {
			ioBldr.setRecordDecider(recordDecider);
		}
		
		for (RecordSelect rs : recordSelections) {
			ioBldr.setRecordSelection(rs.recordName, rs.recordSelection);
		}
		

		return ioBldr
				.setDialect(dialect.id)
				.setSplitCopybook(splitOption.id)
				.setFileOrganization(io.id)
				.setDropCopybookNameFromFields(dropCopybookName)
				//.setCopybookFileFormat(copybookFileFormat)
				.setFont(font);
	}
	
	public void writeTemplateBuilderCode(
			String packageId,
			String className) throws IOException {
		if (templateDtls == null) {
			throw new RuntimeException("You must set the template details before making this call");
		}
		
		WriteCodeGenCall writeCodeGenCall = new WriteCodeGenCall(new GenerateOptions(this), copybookFileName);

		if (this.getLayout().getDecider() instanceof ISingleFieldDeciderDetails) {
			writeCodeGenCall.setDeciderDetails(
					"deciderDescription", 
					"\t\tISingleFieldDecider deciderDescription = " 
						+ this.getSchemaDefinition().createDeciderClass()
						+ ";\n\n");
		}

		writeCodeGenCall.writeTemplateBuilderCode(
				new FileWriter(outputDir + "/" + className + ".java"), 
				packageId, className, 
				templateDtls.getTemplateNameDtls());
	}
//	
//	public void writeTemplateBuilderCode(
//			Writer writer,
//			String packageId,
//			String className,
//			TemplateName templateName) throws IOException {
//		LayoutDetail layout = getLayout();
//		LayoutDef schemaDef = getSchemaDefinition();
//		RecordDecider decider = layout.getDecider();
//		
//		
//		writer.write("" +
//				"package " + packageId + ";\n" + 
//				"\n" + 
//				"import java.io.IOException;\n" + 
//				"\n" + 
//				"import javax.xml.stream.FactoryConfigurationError;\n" + 
//				"import javax.xml.stream.XMLStreamException;\n" + 
//				"\n" + 
//				"import net.sf.JRecord.JRecordInterface1;\n" + 
//				"import net.sf.JRecord.cg.CodeGenInterface;\n" + 
//				"import net.sf.JRecord.cg.details.codes.CobolDialects;\n" + 
//				"import net.sf.JRecord.cg.details.codes.CopybookSplit;\n" +
//				"import net.sf.JRecord.cg.details.codes.FileOrganisation;\n" + 
//				"import net.sf.JRecord.cg.details.codes.StandardTemplates;\n" + 
//				"import net.sf.JRecord.def.IO.builders.recordDeciders.ISingleFieldDecider;\n" + 
//				"\n" + 
//				"public class " + className +" {\n\n" + 
//				"	public static void main(String[] args) throws IOException, XMLStreamException, FactoryConfigurationError {\n\n");
//		String deciderName = null;
//		if (decider instanceof ISingleFieldDeciderDetails) {
//			writer.write("\t\tISingleFieldDecider deciderDescription = " + schemaDef.createDeciderClass() + ";\n\n");
//			deciderName = "deciderDescription";
//		}
//
//		writer.write("\t\tCodeGenInterface.TEMPLATES.newTempateBuilder(\"" + copybookFileName + "\")\n" +
//				"\t\t\t.setCopybookSplitOption(CopybookSplit.SPLIT_01_LEVEL)\n");
//		
////		if (dialect !=) {
//		printVal(writer, "setCobolDialect", dialect);
////		for (CobolDialects c : CobolDialects.values()) {
////			if (c.option == dialect) {
////				printVal(writer, "setCobolDialect", c);
////			}
////		}
//		printVal(writer, "setFileOrganisation", io);
//		printVal(writer, "setCopybookSplitOption", splitOption);
//		writer.write("\t\t\t.setFont(\"" + font + "\")\n");
//		
//		if (deciderName != null) {
//			writer.write("\t\t\t.setRecordDecider(" + deciderName + ")\n");
//		} else if (layout.getRecordCount() > 1) {
//			for (RecordDef r : schemaDef.getRecords()) {
//				String recordSelectionStr = r.getRecordSelectionStr();
//				if (recordSelectionStr == null || recordSelectionStr.length() == 0) {
//					writer.write("\t\t\t//TODO .setRecordSelection(\"" + r.getCobolName() + "\", )\n");
//				} else {
//					writer.write("\t\t\t.setRecordSelection(\"" + r.getCobolName() + "\",\n"
//							+ "/t/t/t/t" + recordSelectionStr + ")\n");
//				}
//			}
//		}
//		
//		if (templateName.template == null) {
//			writer.write("\t\t\t.setUserTemplate(\"" + templateName.userTemplate + "\")\n");
//		} else {
//			printVal(writer, "setTemplate", templateName.template);
//		}
//		writer.write("" +
//				"\t\t\t.setPackageName(\"" + getPackageId() +"\")\n" + 
//				"\t\t\t.setOutputDirectory(\"" + outputDir +"\")\n" + 
//				"\t\t\t.generateJava();\n" + 
//				"" +
//				"\t}\n" + 
//				"}");
//		
//		writer.close();
//	}

//	
//	private void printVal(Writer writer, String methodName, Enum<?> enumValue) throws IOException {
//		writer.write("\t\t\t." + methodName + "(" + enumValue.getClass().getSimpleName() + "." + enumValue.name() + ")\n");
//	}
//	
//	private void printVal(Writer writer, String methodName, ArgumentOption opt) throws IOException {
////		if (opt.codeGenCodePresent()) {
//		writer.write("\t\t\t." + methodName + "(" + opt.getCodeGenCode() + ")\n");
////		} else {
////			
////		}
//	}
	

	
	private static class DuplicateDetails {
		final String fieldName;
		int count=1;
		public DuplicateDetails(String fieldName) {
			super();
			this.fieldName = fieldName;
		}
	}
}
