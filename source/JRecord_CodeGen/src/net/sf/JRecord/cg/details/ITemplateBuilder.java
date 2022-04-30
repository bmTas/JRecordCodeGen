package net.sf.JRecord.cg.details;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.math.BigDecimal;
import java.time.LocalDate;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLStreamException;

import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.ExternalRecordSelection.ExternalSelection;
import net.sf.JRecord.cg.details.codes.CobolDialects;
import net.sf.JRecord.cg.details.codes.CopybookSplit;
import net.sf.JRecord.cg.details.codes.FileOrganisation;
import net.sf.JRecord.cg.details.codes.JRecordVersion;
import net.sf.JRecord.cg.details.codes.StandardTemplates;
import net.sf.JRecord.cg.schema.LayoutDef;
import net.sf.JRecord.cgen.def.gen.ICodeGenDefinition;
import net.sf.JRecord.def.IO.builders.recordDeciders.ISingleFieldDecider;
import net.sf.cb2xml.copybookReader.CopybookColumns;

/**
 * This class will generate a **CodeGen-Template** using a Cobol
 * copybook. It users a Builder style interface to guide you through the process
 * 
 * <b>Note:</b> Enum Parameter names have been chosen so you can add
 * a <b>.</b> to get Autocompletion in Eclipse.
 * 
 * @author Bruce Martin  
 *
 */
public interface ITemplateBuilder extends IBasicTemplateBuilder {

	/**
	 * Provide the Template builder with a Sample Cobol data file (as a stream) to
	 * be analyzed.
	 * @param sampleDataStream Cobol data to be analyzed. You can use the<ul>
	 * <li><b>analyseDataFileSetAttributes()</b> method to set the File-Structure/Organization  
	 * <li><b>createRecordDecider()</b> and <b>createRecordDecider(recordTypeFieldName)</b> 
	 * to try and create a <i>Record-Decider</i> for a Multi-Record file.
	 * </ul>
	 * 
	 * @return Template builder for further updates.
	 * 
	 * @throws IOException Any IOerror that occurs will processing the file
	 */
	TemplateBuilder setDataFile(InputStream sampleDataStream) throws IOException;

	/**
	 * Provide the Template builder with a Sample Cobol data file (as a file) to
	 * be analyzed.
	 * @param filename Cobol file to be analyzed. You can use the<ul>
	 * <li><b>analyseDataFileSetAttributes()</b> method to set the File-Structure/Organization  
	 * <li><b>createRecordDecider()</b> and <b>createRecordDecider(recordTypeFieldName)</b> 
	 * to try and create a <i>Record-Decider</i> for a Multi-Record file.
	 * </ul>
	 * 
	 * @return Template builder for further updates
	 * @throws IOException Any IOerror that occurs will processing the file
	 */
	TemplateBuilder setDataFile(String filename) throws IOException;

	/**
	 * Analyze data file and try and determine the file attributes
	 * @param sampleDataStream to read and analyze
	 * @return this builder for further updates
	 * @throws IOException 
	 */
	TemplateBuilder analyseDataFileSetAttributes(InputStream sampleDataStream) throws IOException;

	/**
	 * Set a CodeGen Template that is to be built {@link StandardTemplates}. Options<ul>
	 * <li><b>StandardTemplates.STANDARD</b> JRecord Example read/write program + Class with
	 * the Cobol field names
	 * <li><b>StandardTemplates.LINE_WRAPPER</b> Create a *wrapper* class where you can get/set the Cobol Fields. 
	 * Example Read/Write programs are also generated.
	 * <li><b>StandardTemplates.POJO</b> Generate<ul>
	 *  <li>Java Pojo's for the Cobol Records
	 *  <li>Conversion code to convert the Cobol Records to/from the Java pojo's
	 *  <li>Example Read/Write programs.
	 * </ul>
	 * <li><b>StandardTemplates.POJO_INTERFACE</b> Sames as <b>POJO</b> bu also generate Interfaces for the Pojo.
	 * </ul>
	 * @param template the template to set
	 */
	ITemplateBuilder setTemplate(StandardTemplates standardTemplates);

	/**
	 * Set a user defined Template (This is a directory containing Control files
	 * and Velocity templates).
	 * @param userTemplate the userTemplate to set
	 */
	ITemplateBuilder setUserTemplate(String userTemplate);

	/**
	 * Set the Cobol dialect (defaults to mainframe). {@link CobolDialects}
	 * @param cobolDialect the cobolDialect to set. Dialects include:<ul>
	 * <li><b>CobolDialects.MAINFRAME</b>
	 * <li><b>CobolDialects.FUJITSU</b> Old Fujitsu PC format
	 * <li><b>CobolDialectsGNU_COBOL</b>
	 * </ul>
	 */
	ITemplateBuilder setCobolDialect(CobolDialects cobolDialects);

	/**
	 * @param copybookSplitOption the copybookSplitOption to set. This controls how
	 * the Cobol Copybook is split into records. See {@link CopybookColumns} Options include<ul>
	 * <li><b>CopybookSplit.SPLIT_NONE</b> - No Split, leave as a single record
	 * <li><b>CopybookSplit.SPLIT_01_LEVEL</b> Split on 01 records
	 * <li><b>CopybookSplit.SPLIT_REDEFINE</b> Split on the highest level redefines.
	 * <li><b>CopybookSplit.SPLIT_HIGHEST_REPEATING</b> Split on highest repeating level. Similar to 
	 * SPLIT_01_LEVEL but works on the lowest repeating level number in the copybook.
	 * </ul>
	 */
	ITemplateBuilder setCopybookSplitOption(CopybookSplit copybookSplit);

	/**
	 * 
	 * @param copybookColumns what are the Columns used in the copybook<ul>
	 * <li><b>CopybookColumns.STANDARD_COLUMNS</b> Standard Cobol columns (7 -> 72). 1->6 and/or 73 -> 80 hold line numbers
	 * <li><b>CopybookColumns.COLUMNS_7_TO_80</b> Columns 7 to 80
	 * <li><b>CopybookColumns.LONG_LINE</b> Start at Columns 7 ....
	 * <li><b>CopybookColumns.FREE_FORMAT</b> Free format cobol (Start at column 1
	 * </ul>
	 * @return builder for more updates
	 */
	ITemplateBuilder setCopybookFormat(CopybookColumns copybookColumns);

	/**
	 * Set the <i>File-
	 * @param fileOrganisation the file Organisation to set
	 */
	ITemplateBuilder setFileOrganisation(FileOrganisation fileOrganisation);

	/**
	 * Set the basic package name for the generated classes. CodeGen will
	 * add extensions as required
	 * @param packageName the packageName of the generated classes. The generated
	 * classes will add to this base package
	 */
	ITemplateBuilder setPackageName(String packageName);

	/**
	 * Set the data file encoding (font)
	 * @param font Set the file font
	 */
	ITemplateBuilder setFont(String font);

	/**
	 * Set the output directory where the generated code is to be written
	 * @param outputDirectory the output-Directory for the generated code
	 */
	ITemplateBuilder setOutputDirectory(String outputDirectory);

	/**
	 * Wether to drop copybook0names from field names in the copybook.
	 * It Cobol copybooks it is quite common to include the copybook name
	 * at the start/end of the field name. This  option controls wether the copybook name
	 * is included in the Generated java code (and the JRecord Schema)
	 * 
	 * @param dropCopybookName the dropCopybookName to set
	 */
	ITemplateBuilder setDropCopybookName(boolean dropCopybookName);

	/**
	 * Which JRecord version to generate code for.
	 * @param jRecordVersion the jRecordVersion to set
	 */
	ITemplateBuilder setJRecordVersion(BigDecimal jRecordVersion);

	/**
	 * @param jRecordVersion the jRecordVersion to set. Possible values<ul>
	 * <li><b>JRecordVersion.VERSION_0_81_5</b>
	 * <li><b>JRecordVersion.VERSION_0_90_0</b>
	 * <li><b>JRecordVersion.VERSION_1_20_0</b>
	 * </ul>
	 * <br/>
	 * In eclipse you can add a . to <b>jRecordVersion</b> to get
	 * auto completion
	 * 
	 */
	ITemplateBuilder setJRecordVersion(JRecordVersion jRecordVersion);

	/**
	 * Set the generate date (useful for testing)
	 * @param generateDate the generateDate to set
	 */
	ITemplateBuilder setGenerateDate(LocalDate generateDate);

	/**
	 * Generate Java interface code for the supplied Cobol copybook
	 * @return The Builder to allow further updates
	 * 
	 * @throws IOException
	 * @throws XMLStreamException
	 * @throws FactoryConfigurationErrorpackageId
	 */
	ITemplateBuilder generateJava() throws IOException, XMLStreamException, FactoryConfigurationError;

	/**
	 * Get the JRecord layout (File Schema)
	 */
	LayoutDetail getLayout() throws IOException;

	/**
	 * Get the CodeGen Extended Schema Definition
	 */
	LayoutDef getCodeGenLayoutDefiniton() throws IOException;

	/**
	 * For Multi-Record Cobol files, the Cobol Copybook does not provide
	 * any way to determine which Record-Layout to use for each data line in the file.
	 * Ways of selecting record-types<ul>
	 * <li>Via your java code.
	 * <li>Using the <b>Record Selection</b> interface (this method)
	 * <li>Nia a <b>RecordDecider</b> class
	 * </ul>
	 * 
	 * Add Record Selection Criteria
	 * @param recordName <i>Record-Name</i> to which this <i>record-selection criteria</i> applies to
	 * @param recordSelection Selection Criteria for the record
	 * @return Builder for more updates
	 */
	ITemplateBuilder addRecordSelection(String recordName, ExternalSelection recordSelection);

	/**
	 * Assign a class that can write a Record-Decider in the generated code.
	 * 
	 * @param generateDecider  class that can write a Record-Decider in the generated code.
	 * @return Builder for more updates
	 */
	ITemplateBuilder setRecordDecider(ICodeGenDefinition generateDecider);

	/**
	 * Assign a <i>Single Field Record decider</i> to the LayoutDef (CodeGen
	 * schema definition)
	 * @param generateDecider  <i>Single Field Record decider</i>, this can be converted to 
	 * a {@link ICodeGenDefinition}.
	 * 
	 * @return Builder for more updates
	 */
	ITemplateBuilder setRecordDecider(ISingleFieldDecider generateDecider);

	/**
	 * Write CodeGen call code
	 * 
	 * @param writer class to write generated code 
	 * @param packageId package name for the generated code
	 * @param className class name for the generated code
	 * 
	 * @return The Builder to allow further updates
	 * @throws IOException
	 */
	ITemplateBuilder writeTemplateBuilderCode(Writer writer, String packageId, String className) throws IOException;

	/**
	 * Write CodeGen call code
	 * 
	 * @param packageId package name for the generated code
	 * 
	 * @return The Builder to allow further updates
	 */
	ITemplateBuilder writeTemplateBuilderCode(String packageId) throws IOException;

}