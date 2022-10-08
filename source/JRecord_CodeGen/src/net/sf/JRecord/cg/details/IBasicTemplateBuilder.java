package net.sf.JRecord.cg.details;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLStreamException;

import net.sf.JRecord.Details.IGetLayout;
import net.sf.JRecord.cg.details.codes.JRecordVersion;
import net.sf.JRecord.cg.details.codes.StandardTemplates;
import net.sf.JRecord.cg.schema.LayoutDef;

public interface IBasicTemplateBuilder extends IGetLayout {

	/**
	 * @param template the template to set
	 */
	IBasicTemplateBuilder setTemplate(StandardTemplates standardTemplates);

	/**
	 * @param userTemplate the userTemplate to set
	 */
	IBasicTemplateBuilder setUserTemplate(String userTemplate);

	/**
	 * Set the basic package name for the generated classes. CodeGen will
	 * add extensions as required
	 *
	 * @param packageName the packageName of the generated classes. The generated
	 * classes will add to this base package
	 */
	IBasicTemplateBuilder setPackageName(String packageName);

	/**
	 * @param outputDirectory the output-Directory for the generated code
	 */
	IBasicTemplateBuilder setOutputDirectory(String outputDirectory);

	/**
	 * @param jRecordVersion the jRecordVersion to set
	 */
	IBasicTemplateBuilder setJRecordVersion(BigDecimal jRecordVersion);

	/**
	 * @param jRecordVersion the jRecordVersion to set
	 */
	IBasicTemplateBuilder setJRecordVersion(JRecordVersion jRecordVersion);

	/**
	 * @param generateDate the generateDate to set
	 */
	IBasicTemplateBuilder setGenerateDate(LocalDate generateDate);

	/**
	 * Controls whether to rename Duplicate field-names so there are no duplicates
	 * @param renameDuplicateFields Whether to rename Duplicate field-names so there are no duplicates
	 * @return TemplateBuilder for more updates
	 */
	IBasicTemplateBuilder setRenameDuplicateFields(boolean renameDuplicateFields);

	/**
	 * Generate Java interface code for the supplied Cobol copybook
	 * @return The Builder to allow further updates
	 * 
	 * @throws IOException
	 * @throws XMLStreamException
	 * @throws FactoryConfigurationErrorpackageId
	 */
	IBasicTemplateBuilder generateJava() throws IOException, XMLStreamException, FactoryConfigurationError;

	/**
	 * 
	 * @return CodeGen Schema definition
	 * @throws IOException
	 */
	LayoutDef getCodeGenLayoutDefiniton() throws IOException;


}