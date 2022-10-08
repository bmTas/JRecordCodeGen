package net.sf.JRecord.cg.details;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.Writer;
import java.math.BigDecimal;
import java.nio.file.FileSystems;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLStreamException;

import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.RecordDecider;
import net.sf.JRecord.ExternalRecordSelection.ExternalSelection;
import net.sf.JRecord.cg.details.codes.ArgumentOption;
import net.sf.JRecord.cg.details.codes.CobolDialects;
import net.sf.JRecord.cg.details.codes.CopybookSplit;
import net.sf.JRecord.cg.details.codes.FileOrganisation;
import net.sf.JRecord.cg.details.codes.JRecordVersion;
import net.sf.JRecord.cg.details.codes.StandardTemplates;
import net.sf.JRecord.cg.details.fileAnalysis.FileAnalysis;
import net.sf.JRecord.cg.details.fileAnalysis.FileAnalysis.EncodingType;
import net.sf.JRecord.cg.schema.LayoutDef;
import net.sf.JRecord.cg.velocity.GenerateVelocity;
import net.sf.JRecord.cgen.def.gen.ICodeGenDefinition;
import net.sf.JRecord.def.IO.builders.recordDeciders.ISingleFieldDecider;
import net.sf.JRecord.def.recordSelection.ISingleFieldDeciderDetails;
import net.sf.cb2xml.copybookReader.CopybookColumns;
import net.sf.cb2xml.copybookReader.ICobolCopybookTextSource;

public class TemplateBuilder implements ITemplateBuilder {
	private GenOptValues optValues = new GenOptValues();
	private String copybookFileName = "";
	
//	private final String schemaName; //, cobolCopybook;
//	private Reader cobolCopybookReader; 
//	private CobolDialects cobolDialect = CobolDialects.MAINFRAME;
//	private CopybookSplit copybookSplitOption = CopybookSplit.SPLIT_NONE;
//	private FileOrganisation fileOrganisation;
	private StandardTemplates template = StandardTemplates.STANDARD;
	private BigDecimal jRecordVersion = null;
	private LocalDate generateDate = LocalDate.now();
	
	//private String copybookTxt;
	
	private String userTemplate = "";
	
	private FileAnalysis analysis;
	
	public TemplateBuilder(String cobolCopybook) throws IOException {
		
		if (! (new File(cobolCopybook)).exists()) {
			throw new RuntimeException("File " + cobolCopybook + " does no exist");
		}
		
		copybookFileName = cobolCopybook;

		optValues.readCobolCopybook(cobolCopybook);
	}
	
	public TemplateBuilder(Reader cobolCopybookReader, String schemaName) throws IOException {
		super();
		optValues.readCobolCopybook(cobolCopybookReader, schemaName);
		setCobolDialect(CobolDialects.MAINFRAME);
	}
	
	public TemplateBuilder(ICobolCopybookTextSource cobolCopybookTextSource) {
		optValues.setCobolCopybookSource(cobolCopybookTextSource);
	}


	@Override
	public TemplateBuilder setDataFile(InputStream sampleDataStream) throws IOException {
		analysis = new FileAnalysis(sampleDataStream);
		return this;
	}

	@Override
	public TemplateBuilder setDataFile(String filename) throws IOException {
		analysis = new FileAnalysis(new FileInputStream(filename));
		return this;
	}

	/**
	 * Analyze data file and try and determine the file attributes
	 * @param sampleDataStream to read and analyse
	 * @return this builder for further updates
	 * @throws IOException 
	 */
	@Override
	public TemplateBuilder analyseDataFileSetAttributes(InputStream sampleDataStream) throws IOException {
		analysis = new FileAnalysis(sampleDataStream);
		
		return analyseDataFileSetAttributes();
	}


	/**
	 * Analyze the supplied <b>Cobol data file</b> and try and determine the file structure from the data file
	 *  
	 * @return this builder for further updates
	 */
	public TemplateBuilder analyseDataFileSetAttributes() {
		EncodingType encodingType = analysis.getEncodingType();
		if (encodingType.isEncodingDefined()) {
			setFont(encodingType.getEncoding());
		}
		FileOrganisation fileStructure = analysis.getFileOrganisation();
		if (fileStructure != FileOrganisation.UNNOWN) {
			setFileOrganisation(fileStructure);
		}
		return this;
	}
	
	/**
	 * Analyze the <b>Cobol copybook</b> and <b>Cobol data file</b>
	 * and try and work out<ul>
	 * <li><b>Record type</b> field
	 * <li>The <b>Record-Type</b> to Cobol Record  mapping
	 * </ul>
	 * Then create a <i>Record Decider</i> 
	 *  
	 * @return this builder for further updates
	 * @throws IOException
	 */
	public TemplateBuilder createRecordDecider() throws IOException {
		this.optValues.setRecordDecider(analysis.analyseRecordDecider(getLayout()));
		return this;
	}
	

	
	/**
	 * Analyze the <b>Cobol copybook</b> and <b>Cobol data file</b>
	 * and try and work out<ul>
	 * <li>The <b>Record-Type</b> to Cobol Record  mapping
	 * </ul>
	 * Then create a <i>Record Decider</i> 
	 *  
	 * @param recordTypeField name of the Cobol <b>Record type</b> field
	 * @return this builder for further updates
	 * @throws I OException
	 */
	public TemplateBuilder createRecordDecider(String recordTypeField) throws IOException {
		analysis.analyseRecordDecider(getLayout(), recordTypeField);
		return this;
	}
	
	/**
	 * define how to create the record decider
	 * @param generateDecider class to create a recordDecider
	 * @see net.sf.JRecord.cg.details.GenOptValues#setRecordDecider(net.sf.JRecord.cgen.def.gen.ICodeGenDefinition)
	 */
	@Override
	public TemplateBuilder setRecordDecider(ICodeGenDefinition generateDecider) {
		optValues.setRecordDecider(generateDecider);
		return this;
	}
	
	@Override
	public TemplateBuilder setRecordDecider(ISingleFieldDecider generateDecider) {
		optValues.setRecordDecider(generateDecider);
		return this;
	}

	/**
	 * @param template the template to set
	 */
	@Override
	public TemplateBuilder setTemplate(StandardTemplates standardTemplates) {
		this.template = standardTemplates;
		this.userTemplate = "";
		return this;
	}

	/**
	 * @param userTemplate the userTemplate to set
	 */
	@Override
	public TemplateBuilder setUserTemplate(String userTemplate) {
		this.userTemplate = userTemplate;
		this.template = null;
		return this;
	}

	/**
	 * @param cobolDialect the cobolDialect to set
	 */
	@Override
	public TemplateBuilder setCobolDialect(CobolDialects cobolDialects) {
		this.optValues.dialect = cobolDialects.option;
		return this;
	}
	
	/**
	 * @param copybookSplitOption the copybookSplitOption to set. This controls how
	 * the Cobol Copybook is split into records
	 */
	@Override
	public TemplateBuilder setCopybookSplitOption(CopybookSplit copybookSplit) {
		this.optValues.splitOption = copybookSplit.option;
		return this;
	}
	
	@Override
	public TemplateBuilder setCopybookFormat(CopybookColumns copybookColumns) {
		this.optValues.setCopybookFileFormat(copybookColumns);
		return this;
	}
	
	/**
	 * @param fileOrganisation the fileOrganisation to set
	 */
	@Override
	public TemplateBuilder setFileOrganisation(FileOrganisation fileOrganisation) {
		this.optValues.setFileOrganisation(fileOrganisation.option);
		return this;
	}
	


//	/**
//	 * @param packageName the packageName of the generated classes. The generated
//	 * classes will add to this base package
//	 */
	@Override
	public TemplateBuilder setPackageName(String packageName) {
		this.optValues.setPackageId(packageName);
		return this;
	}

	
	/**
	 * @param renameDuplicateFields
	 * @see net.sf.JRecord.cg.details.GenOptValues#setRenameDuplicateFields(boolean)
	 */
	@Override
	public TemplateBuilder setRenameDuplicateFields(boolean renameDuplicateFields) {
		optValues.setRenameDuplicateFields(renameDuplicateFields);
		return this;
	}

//	/**
//	 * The actual
//	 * @param packageDir the packageDir to set
//	 */
//	public TemplateBuilder setPackageDirectory(String packageDir) {
//		this.optValues.packageDir = packageDir;
//		return this;
//	}



	/**
	 * @param font the font to set
	 */
	@Override
	public TemplateBuilder setFont(String font) {
		this.optValues.font = font;
		return this;
	}

	/**
	 * @param outputDirectory the output-Directory for the generated code
	 */
	@Override
	public TemplateBuilder setOutputDirectory(String outputDirectory) {
		this.optValues.outputDir = outputDirectory;
		return this;
	}

	/**
	 * @param dropCopybookName the dropCopybookName to set
	 */
	@Override
	public TemplateBuilder setDropCopybookName(boolean dropCopybookName) {
		this.optValues.dropCopybookName = dropCopybookName;
		return this;
	}
	
	/**
	 * 
	 * @param recordName Record name of Record to be updates
	 * @param recordSelection Record-Selection for the record
	 * @return this builder for more updates
	 */
	@Override
	public TemplateBuilder addRecordSelection(String recordName, ExternalSelection recordSelection) {
		optValues.addRecordSelection(new RecordSelect(recordName, recordSelection));
		return this;
	}

	/**
	 * @param jRecordVersion the jRecordVersion to set
	 */
	@Override
	public TemplateBuilder setJRecordVersion(BigDecimal jRecordVersion) {
		this.jRecordVersion = jRecordVersion;
		return this;
	}
	/**
	 * @param jRecordVersion the jRecordVersion to set
	 */
	@Override
	public TemplateBuilder setJRecordVersion(JRecordVersion jRecordVersion) {
		this.jRecordVersion = jRecordVersion.jrecordVersion;
		return this;
	}

	/**
	 * @param generateDate the generateDate to set
	 */
	@Override
	public TemplateBuilder setGenerateDate(LocalDate generateDate) {
		this.generateDate = generateDate;
		return this;
	}

	/**
	 * Generate Java interface code for the supplied Cobol copybook
	 * @return The Builder to allow further updates
	 * 
	 * @throws IOException
	 * @throws XMLStreamException
	 * @throws FactoryConfigurationErrorpackageId
	 */
	@Override
	public TemplateBuilder generateJava() throws IOException, XMLStreamException, FactoryConfigurationError {
		this.optValues.outputDir = evaluateOutputDir(this.optValues.outputDir);
		setupTemplateDetails();
		
		new GenerateVelocity(new GenerateOptions(optValues), "CodeGen");
		
		return this;
	}

	/**
	 * 
	 */
	private void setupTemplateDetails() {
		int version = jRecordVersion == null 
				? TemplateDtls.DEFAULT_JREC_VERSION 
				: jRecordVersion.multiply(BigDecimal.valueOf(1000)).intValue() ;

		this.optValues.setTemplateDtls( new TemplateDtls(
				userTemplate,
				decodeTemplate(userTemplate, template == null ? null : template.option.code), 
				TemplateDtls.DEFAULT_TEMPLATE_BASE,
				this.optValues.isMultiRecord(),
				version > 0 ? version : TemplateDtls.DEFAULT_JREC_VERSION,
				generateDate.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))));
	}
	
	@Override
	public TemplateBuilder writeTemplateBuilderCode(String packageId) throws IOException {
		WriteCodeGenCall writeCodeGenCall = createWriteCodeGenCall();

		writeCodeGenCall
				.writeTemplateBuilderCode(packageId);
		return this;
	}

	/**
	 * 
	 * @param writer class to write generated code 
	 * @param packageId package name for the generated code
	 * @param className class name for the generated code
	 * @return The Builder to allow further updates
	 * @throws IOException
	 */
	@Override
	public TemplateBuilder writeTemplateBuilderCode(Writer writer, String packageId, String className) throws IOException {
		
		WriteCodeGenCall writeCodeGenCall = createWriteCodeGenCall();

		writeCodeGenCall
				.writeTemplateBuilderCode(writer, packageId, className, new TemplateName(template, userTemplate));
		
		return this;

//		LayoutDetail layout = optValues.getLayout();
//		LayoutDef schemaDef = optValues.getSchemaDefinition();
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
////		if (optValues.dialect !=) {
//		for (CobolDialects c : CobolDialects.values()) {
//			if (c.option == optValues.dialect) {
//				printVal(writer, "setCobolDialect", c);
//			}
//		}
//		printVal(writer, "setFileOrganisation", analysis.getFileOrganisation());
//		printVal(writer, "setCopybookSplitOption", optValues.splitOption);
//		writer.write("\t\t\t.setFont(\"" + optValues.font + "\")\n");
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
//		if (template == null) {
//			writer.write("\t\t\t.setUserTemplate(\"" + userTemplate + "\")\n");
//		} else {
//			printVal(writer, "setTemplate", template);
//		}
//		writer.write("" +
//				"\t\t\t.setPackageName(\"" + optValues.getPackageId() +"\")\n" + 
//				"\t\t\t.setOutputDirectory(\"" + optValues.outputDir +"\")\n" + 
//				"\t\t\t.generateJava();\n" + 
//				"" +
//				"\t}\n" + 
//				"}");
//		
//		writer.close();
//		return this;
	}

	/**
	 * @return
	 * @throws IOException
	 */
	protected WriteCodeGenCall createWriteCodeGenCall() throws IOException {
		
		optValues.setCopybookFileName(copybookFileName);
		
		if (optValues.getTemplateDtls() == null) {
			setupTemplateDetails();
		}

		WriteCodeGenCall writeCodeGenCall = new WriteCodeGenCall(new GenerateOptions(optValues), copybookFileName);
		RecordDecider decider = getLayout().getDecider();
		if (decider instanceof ISingleFieldDeciderDetails) {
			writeCodeGenCall.setDeciderDetails(
					"deciderDescription", 
					"\t\tISingleFieldDecider deciderDescription = " 
						+ optValues.getSchemaDefinition().createDeciderClass()
						+ ";\n\n");
		}
		return writeCodeGenCall;
	}

	
	@Override
	public LayoutDetail getLayout() throws IOException {
		return optValues.getLayout();
	}
	
	@Override
	public LayoutDef getCodeGenLayoutDefiniton() throws IOException {
		return optValues.getSchemaDefinition();
	}


	/**
	 * @param outputDirpackageId
	 * @return
	 */
	protected String evaluateOutputDir(String outputDir) {
		if (outputDir == null || outputDir.length() == 0) {
			outputDir = FileSystems.getDefault().getPath(".").toAbsolutePath().toString();
		}
		if (outputDir.endsWith(".")) {
			outputDir = outputDir.substring(0, outputDir.length() - 1);
		}
		System.out.println("Writing Output java to: " + outputDir);

		return outputDir;
	}
	
	
	private static String decodeTemplate(String templateDir, String template) {
		if (templateDir != null && templateDir.length() > 0) {
		} else if (template == null || template.length() == 0) {
			template = ArgumentOption.LINE_WRAPPER_TEMPLATE;
		}
		return template;
	}
}
