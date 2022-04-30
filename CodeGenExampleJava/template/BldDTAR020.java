import java.io.IOException;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLStreamException;

import net.sf.JRecord.cg.CodeGenInterface;
import net.sf.JRecord.cg.details.codes.StandardTemplates;

public class BldDTAR020 {
	
	private static String dtar020Copybook = "DTAR020.cbl";
	private static String dtar020data = "DTAR020.bin";

	public static void main(String[] args) throws IOException, XMLStreamException, FactoryConfigurationError {

		String outputDirectory = "generatedCode/dtar020";
		CodeGenInterface.TEMPLATES.newTempateBuilder(dtar020Copybook)
				.setDataFile(dtar020data)
				.analyseDataFileSetAttributes()
				.setTemplate(StandardTemplates.STANDARD)
				.setPackageName("dtar020.standard")
				.setOutputDirectory(outputDirectory)
				.generateJava()
				.writeTemplateBuilderCode("build")
				
				;
				

	}

}
