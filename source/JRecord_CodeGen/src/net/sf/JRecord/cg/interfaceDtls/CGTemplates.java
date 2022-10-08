package net.sf.JRecord.cg.interfaceDtls;

import java.io.IOException;
import java.io.Reader;

import net.sf.JRecord.Details.IGetLayout;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.External.ExternalRecord;
import net.sf.JRecord.cg.details.BasicTemplateBuilder;
import net.sf.JRecord.cg.details.IBasicTemplateBuilder;
import net.sf.JRecord.cg.details.ITemplateBuilder;
import net.sf.JRecord.cg.details.TemplateBuilder;
import net.sf.cb2xml.copybookReader.ICobolCopybookTextSource;

/**
 * This class will generate Java/JRecord code from 
 * a Cobol copybook so that you can access Cobol Data / Data files  
 * 
 * @author Bruce Martin
 *
 */
public class CGTemplates {
	
	/**
	 * Create Template builder from JRecord-IOBuilder (or anything that implements IGetLayout)
	 * @param ioBuilder JRecord-IOBuilder (or anything that implements IGetLayout
	 * @return Template Builder
	 * @throws IOException
	 */
	public final IBasicTemplateBuilder newTempateBuilder(IGetLayout ioBuilder) throws IOException {
		return new BasicTemplateBuilder(ioBuilder.getLayout());
	}

	/**
	 * Create Template builder from external Record
	 * @param xRecord external-record
	 * @return Template Builder
	 * @throws IOException
	 */
	public final IBasicTemplateBuilder newTempateBuilder(ExternalRecord xRecord) throws IOException {
		return new BasicTemplateBuilder(xRecord.asLayoutDetail());
	}

	/**
	 * Create Template builder from JRecord Layout
	 * @param schema JRecord Layout definition
	 * @return Template Builder
	 */
	public final IBasicTemplateBuilder newTempateBuilder(LayoutDetail schema) {
		return new BasicTemplateBuilder(schema);
	}

	/**
	 * Create a <i>Template Builder</i> for a Cobol Copybook File
	 * @param cobolCopybookFileName Cobol Copybook file name
	 * @return Template builder
	 * @throws IOException 
	 */
	public final ITemplateBuilder newTempateBuilder(String cobolCopybookFileName) throws IOException {
		return new TemplateBuilder(cobolCopybookFileName);
	}
	
	/**
	 * Create a <i>Template Builder</i> for a Cobol Copybook Reader
	 * @param cobolCopybookReader Copybook Reader
	 * @param cobolCopybookName Copybook name, normally this would be the <i>File Name</i>
	 * @return
	 * @throws IOException 
	 */
	public final ITemplateBuilder newTempateBuilder(Reader cobolCopybookReader, String cobolCopybookName) throws IOException {
		return new TemplateBuilder(cobolCopybookReader, cobolCopybookName);
	}
	
	/**
	 * Create  Template builder  from a cb2xml copybookReader
	 * 
	 * @param cobolCopybookReader
	 * @return Template Builder
	 * @throws IOException
	 */
	public final ITemplateBuilder newTempateBuilder(ICobolCopybookTextSource cobolCopybookTextSource) { 
		return new TemplateBuilder(cobolCopybookTextSource);
	}

}
