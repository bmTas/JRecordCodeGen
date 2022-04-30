package net.sf.JRecord.zcg.zTest.codeGenInterface;

import java.io.IOException;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLStreamException;

import net.sf.JRecord.JRecordInterface1;
import net.sf.JRecord.Common.IFileStructureConstants;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.External.CopybookLoader;
import net.sf.JRecord.cg.CodeGenInterface;
import net.sf.JRecord.cg.details.IBasicTemplateBuilder;
import net.sf.JRecord.cg.details.codes.StandardTemplates;
import net.sf.cb2xml.copybookReader.ReadCobolCopybook;

public class GenOd4 {
	
	private static String copybook = ""
			+ " 01  record-1.  "
			+ "     03  od-count-1                             pic s99 comp.\n"
			+ "     03  od-count-2                             pic s99 comp.\n"
			+ "     03  occurs 5.\n"
			+ "         05 occurs 5 to 20 depending on od-count-1.\n"
			+ "            10 occurs 4.\n"
//			+ "                15  od-count-2                  pic s99 comp.\n"
			+ "                15 occurs 3."
			+ "                   17 tbl occurs   5 to 25"
			+ "                      depending on od-count-2.\n"
			+ "                   20 Field-1                   pic x(4).\n"
			+ "                   20 Field-2                   pic s9(4).\n"
			+ "     03  od-count-3                             pic s99 comp.\n"
			+ "     03  occurs 5.\n"
			+ "         05 occurs 5 to 20 depending on od-count-3.\n"
			+ "            10 occurs 4.\n"
			+ "                15  od-count-4                  pic s99 comp.\n"
			+ "                15 occurs 3."
			+ "                   17 tbl occurs   5 to 25"
			+ "                      depending on od-count-4.\n"
			+ "                   20 Field-3                   pic x(4).\n"
			+ "                   20 Field-4                   pic s9(4).\n"
			+ " 01  record-2. "
			+ "     03  field-21                               pic x(80)."

			;
	
	public static void main(String[] args) throws IOException, XMLStreamException, FactoryConfigurationError {
		ReadCobolCopybook copybookReader = new ReadCobolCopybook()
				.setCopybookName("OD-Schema4")
				.addFreeFormatCobolText(copybook);
		LayoutDetail layout = JRecordInterface1.COBOL.newIOBuilder(copybookReader)
				.setSplitCopybook(CopybookLoader.SPLIT_01_LEVEL)
				.setFileOrganization(IFileStructureConstants.IO_VB)
				.getLayout();
		
		
		IBasicTemplateBuilder templateBldr = CodeGenInterface.TEMPLATES.newTempateBuilder(layout);
		
		//LayoutDef schema = templateBldr.getCodeGenLayoutDefiniton();
		//
		//RecordDef recordDef = schema.getRecords().get(0);
		//System.out.print(recordDef.getFields()
		//.get(0).getName());
		
		templateBldr
			.setOutputDirectory("/home/bruce/work/temp/codeGen/")
			
//			.setTemplate(StandardTemplates.POJO)
//			.setPackageName("od4.pojo")
//			.generateJava()
			.setTemplate(StandardTemplates.POJO_INTERFACE)
			.setPackageName("od4.pojoInterface")
			.generateJava()
			.setTemplate(StandardTemplates.LINE_WRAPPER)
			.setPackageName("od4.lineWrapper")
			.generateJava()
//			.setTemplate(StandardTemplates.LINE_WRAPPER_POJO)
//			.setPackageName("od4.lineWrapperPojo")
//			.generateJava()
			;	
	}

}
