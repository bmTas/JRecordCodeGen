package fieldRename;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLStreamException;

import net.sf.JRecord.JRecordInterface1;
import net.sf.JRecord.Common.IFileStructureConstants;
import net.sf.JRecord.External.CopybookLoader;
import net.sf.JRecord.cg.CodeGenInterface;
import net.sf.JRecord.cg.details.codes.StandardTemplates;
import net.sf.JRecord.def.IO.builders.ICobolIOBuilder;
import net.sf.JRecord.def.IO.builders.recordDeciders.ISingleFieldDecider;

public class CodeGenRenameTst3 {
	
	public static void main(String[] args) throws IOException, XMLStreamException, FactoryConfigurationError {
		ISingleFieldDecider recDecider = JRecordInterface1.RECORD_DECIDER_BUILDER.singleFieldDeciderBuilder("record-type", false)
				.addRecord("1", "dup-name-1")
				.addRecord("2", "dup-name-2")
				.build();
		
		ICobolIOBuilder iob = JRecordInterface1.COBOL.newIOBuilder("FieldRenameTst.Cbl")
				.setFileOrganization(IFileStructureConstants.IO_BIN_TEXT)
				.setSplitCopybook(CopybookLoader.SPLIT_01_LEVEL)
				.setRecordDecider(recDecider);
	
		CodeGenInterface.TEMPLATES.newTempateBuilder(iob)
				.setRenameDuplicateFields(true)
				.setPackageName("tstr.pojo.rename")
				.setOutputDirectory("CodeGenOut/v93")
				.setTemplate(StandardTemplates.POJO)
				.generateJava();
//
//		CodeGenInterface.TEMPLATES.newTempateBuilder(iob)
//				.setRenameDuplicateFields(true)
//				.setPackageName("tst.pojoInterface.rename5")
//				.setOutputDirectory("CodeGenOut/v93")
//				.setTemplate(StandardTemplates.POJO_INTERFACE)
//				.generateJava();
//
//
//		CodeGenInterface.TEMPLATES.newTempateBuilder(iob)
//				.setRenameDuplicateFields(true)
//				.setPackageName("tst.lineWraper.rename4")
//				.setOutputDirectory("CodeGenOut/v93")
//				.setTemplate(StandardTemplates.LINE_WRAPPER)
//				.generateJava();
//
//
//		CodeGenInterface.TEMPLATES.newTempateBuilder(iob)
//				.setRenameDuplicateFields(true)
//				.setPackageName("tst.lineWraperPojo.rename6")
//				.setOutputDirectory("CodeGenOut/v93")
//				.setTemplate(StandardTemplates.LINE_WRAPPER_POJO)
//				.generateJava();
                         
//		CodeGenInterface.TEMPLATES.newTempateBuilder(iob)
//				.setRenameDuplicateFields(true)
//				.setPackageName("tst.standard.rename6")
//				.setOutputDirectory("CodeGenOut/v93")
//				.setTemplate(StandardTemplates.STANDARD)
//				.generateJava();
	}

}
