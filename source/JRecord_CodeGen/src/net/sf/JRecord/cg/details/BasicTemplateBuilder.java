package net.sf.JRecord.cg.details;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.FileSystems;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLStreamException;

import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.External.base.CobolConversionOptions;
import net.sf.JRecord.cg.details.codes.ArgumentOption;
import net.sf.JRecord.cg.details.codes.CobolDialects;
import net.sf.JRecord.cg.details.codes.CopybookSplit;
import net.sf.JRecord.cg.details.codes.FileOrganisation;
import net.sf.JRecord.cg.details.codes.JRecordVersion;
import net.sf.JRecord.cg.details.codes.StandardTemplates;
import net.sf.JRecord.cg.schema.LayoutDef;
import net.sf.JRecord.cg.velocity.GenerateVelocity;

public class BasicTemplateBuilder implements IBasicTemplateBuilder {
	private GenOptValues optValues = new GenOptValues();
	
	private StandardTemplates template = StandardTemplates.STANDARD;
	private BigDecimal jRecordVersion = null;
	private LocalDate generateDate = LocalDate.now();
	
	private String userTemplate = "";
	
	public BasicTemplateBuilder(LayoutDetail layout) {
		optValues.setSchemaDefinition(layout, layout.getLayoutName());
		
		optValues.fullBuilder = false;
		optValues.font = layout.getFontName();
		
		CobolConversionOptions cobolConversionOption = layout.getCobolConversionOption();
		if (cobolConversionOption != null) {
			optValues.dropCopybookName = cobolConversionOption.dropCopybookName;
			for (CobolDialects dialect : CobolDialects.values()) {
				if (dialect.option.id == cobolConversionOption.cobolDialect) {
					optValues.dialect = dialect.option;
					break;
				}
			}
			for (CopybookSplit split : CopybookSplit.values()) {
				if (cobolConversionOption.splitCopybookOption == split.option.id) {
					optValues.splitOption = split.option;
					break;			
				}
			}
		}
		
		for (FileOrganisation fileOrg : FileOrganisation.values()) {
			if (layout.getFileStructure() == fileOrg.option.getId()) {
				optValues.setFileOrganisation(fileOrg.option);
				break;
			}
		}
		
		for (FileOrganisation fileOrg : FileOrganisation.values()) {
			if (layout.getFileStructure() == fileOrg.option.getId()) {
				optValues.setFileOrganisation(fileOrg.option);
				break;
			}
		}

		
//		for (CopybookSplit split : CopybookSplit.values()) {
//			if (layout.spl
//		}
	}
	

	/**
	 * @param template the template to set
	 */
	@Override
	public BasicTemplateBuilder setTemplate(StandardTemplates standardTemplates) {
		this.template = standardTemplates;
		this.userTemplate = null;
		return this;
	}

	/**
	 * @param userTemplate the userTemplate to set
	 */
	@Override
	public BasicTemplateBuilder setUserTemplate(String userTemplate) {
		this.userTemplate = userTemplate;
		this.template = null;
		return this;
	}

	


	/**
	 * @param packageName the packageName of the generated classes. The generated
	 * classes will add to this base package
	 */
	@Override
	public BasicTemplateBuilder setPackageName(String packageName) {
		this.optValues.setPackageId(packageName);
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
	public BasicTemplateBuilder setFont(String font) {
		this.optValues.font = font;
		return this;
	}

	/**
	 * @param outputDirectory the output-Directory for the generated code
	 */
	@Override
	public BasicTemplateBuilder setOutputDirectory(String outputDirectory) {
		this.optValues.outputDir = outputDirectory;
		return this;
	}

	/**
	 * @param dropCopybookName the dropCopybookName to set
	 */
	public BasicTemplateBuilder setDropCopybookName(boolean dropCopybookName) {
		this.optValues.dropCopybookName = dropCopybookName;
		return this;
	}
	
	/**
	 * @param jRecordVersion the jRecordVersion to set
	 */
	@Override
	public BasicTemplateBuilder setJRecordVersion(BigDecimal jRecordVersion) {
		this.jRecordVersion = jRecordVersion;
		return this;
	}
	/**
	 * @param jRecordVersion the jRecordVersion to set
	 */
	@Override
	public BasicTemplateBuilder setJRecordVersion(JRecordVersion jRecordVersion) {
		this.jRecordVersion = jRecordVersion.jrecordVersion;
		return this;
	}

	/**
	 * @param generateDate the generateDate to set
	 */
	@Override
	public BasicTemplateBuilder setGenerateDate(LocalDate generateDate) {
		this.generateDate = generateDate;
		return this;
	}

	/**
	 * @param renameDuplicateFields
	 * @see net.sf.JRecord.cg.details.GenOptValues#setRenameDuplicateFields(boolean)
	 */
	@Override
	public BasicTemplateBuilder setRenameDuplicateFields(boolean renameDuplicateFields) {
		optValues.setRenameDuplicateFields(renameDuplicateFields);
		return this;
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
	 * Generate Java interface code for the supplied Cobol copybook
	 * @return The Builder to allow further updates
	 * 
	 * @throws IOException
	 * @throws XMLStreamException
	 * @throws FactoryConfigurationErrorpackageId
	 */
	@Override
	public BasicTemplateBuilder generateJava() throws IOException, XMLStreamException, FactoryConfigurationError {
		int version = jRecordVersion == null 
				? TemplateDtls.DEFAULT_JREC_VERSION 
				: jRecordVersion.multiply(BigDecimal.valueOf(1000)).intValue() ;

		this.optValues.outputDir = evaluateOutputDir(this.optValues.outputDir);
		this.optValues.setTemplateDtls( new TemplateDtls(
				userTemplate,
				decodeTemplate(userTemplate, template == null ? null : template.option.code), 
				TemplateDtls.DEFAULT_TEMPLATE_BASE,
				this.optValues.isMultiRecord(),
				version > 0 ? version : TemplateDtls.DEFAULT_JREC_VERSION,
				generateDate.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))));
		
		
		new GenerateVelocity(new GenerateOptions(optValues), "CodeGen");
		
		return this;
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
