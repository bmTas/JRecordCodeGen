package net.sf.JRecord.zcg.zTest.codeGenInterface;

import java.io.IOException;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLStreamException;

import org.junit.Test;

import net.sf.JRecord.cg.CodeGenInterface;
import net.sf.JRecord.cg.details.IBasicTemplateBuilder;
import net.sf.JRecord.cg.details.codes.StandardTemplates;
import net.sf.cb2xml.copybookReader.ReadCobolCopybook;

public class TstOccursDepending1 {

	private String copybook = ""
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
			+ "                   20 Field-2                   pic s9(4).\n";

	@Test
	public void test() throws IOException, XMLStreamException, FactoryConfigurationError {
		//fail("Not yet implemented");
		
		ReadCobolCopybook copybookReader = new ReadCobolCopybook()
												.setCopybookName("OD-Schema")
												.addFreeFormatCobolText(copybook);
//		LayoutDetail layout = JRecordInterface1.COBOL.newIOBuilder(copybookReader).getLayout();
		IBasicTemplateBuilder templateBldr = CodeGenInterface.TEMPLATES.newTempateBuilder(copybookReader);
		
//		LayoutDef schema = templateBldr.getCodeGenLayoutDefiniton();
//		
//		RecordDef recordDef = schema.getRecords().get(0);
//		System.out.print(recordDef.getFields()
//				.get(0).getName());
		
		templateBldr.setTemplate(StandardTemplates.POJO)
					.setOutputDirectory("/home/bruce/work/temp/codeGen/od1")
					.generateJava();
	}

}
