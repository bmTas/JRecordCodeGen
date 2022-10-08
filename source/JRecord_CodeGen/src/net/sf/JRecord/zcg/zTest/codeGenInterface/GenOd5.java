package net.sf.JRecord.zcg.zTest.codeGenInterface;

import java.io.IOException;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLStreamException;

import net.sf.JRecord.cg.CodeGenInterface;
import net.sf.JRecord.cg.details.IBasicTemplateBuilder;
import net.sf.JRecord.cg.details.codes.StandardTemplates;
import net.sf.cb2xml.copybookReader.ReadCobolCopybook;

public class GenOd5 {
	
	private static String copybook = ""
			+ "     03  od-count-1                             pic s99.\n"
			+ "     03  space-1                                pic x.\n"
			+ "     03  occurs 5 to 20 depending on od-count-1.\n"
			+ "         05  space-2                            pic x.\n"
			+ "         05  od-count-2                         pic s99.\n"
			+ "         05  space-3                            pic x.\n"
			+ "         05 tbl occurs   5 to 25"
			+ "            depending on od-count-2.\n"
			+ "            10 Field-1                          pic 99.\n"
			;
	
	public static void main(String[] args) throws IOException, XMLStreamException, FactoryConfigurationError {
		ReadCobolCopybook copybookReader = new ReadCobolCopybook()
				.setCopybookName("OD-Schema")
				.addFreeFormatCobolText(copybook);
		//LayoutDetail layout = JRecordInterface1.COBOL.newIOBuilder(copybookReader).getLayout();
		IBasicTemplateBuilder templateBldr = CodeGenInterface.TEMPLATES.newTempateBuilder(copybookReader);
		
		//LayoutDef schema = templateBldr.getCodeGenLayoutDefiniton();
		//
		//RecordDef recordDef = schema.getRecords().get(0);
		//System.out.print(recordDef.getFields()
		//.get(0).getName());
		
		templateBldr
			.setOutputDirectory("/home/bruce/work/temp/codeGen/")
			.setTemplate(StandardTemplates.POJO)
			.setPackageName("od5.pojo")
			.generateJava()
//			.setTemplate(StandardTemplates.POJO_INTERFACE)
//			.setPackageName("od5.PojoInterface")
//			.generateJava()
//			.setTemplate(StandardTemplates.LINE_WRAPPER)
//			.setPackageName("od5.LineWrapper")
//			.generateJava()
//			.setTemplate(StandardTemplates.LINE_WRAPPER_POJO)
//			.setPackageName("od5.LineWrapperPojo")
//			.generateJava()
			;	
	}

}
