
import java.io.IOException;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLStreamException;

import net.sf.JRecord.cg.CodeGenInterface;
import net.sf.JRecord.cg.details.codes.CopybookSplit;
import net.sf.JRecord.cg.details.codes.StandardTemplates;

public class BldAmsPo1 {
	
	private static String amsPoCopybook = "amsPoDownload.cbl";

	public static void main(String[] args) throws IOException, XMLStreamException, FactoryConfigurationError {
		System.out.println(amsPoCopybook);

		CodeGenInterface.TEMPLATES.newTempateBuilder(amsPoCopybook)
				.setDataFile("Ams_PODownload_20041231.txt")
				.setCopybookSplitOption(CopybookSplit.SPLIT_01_LEVEL)
				.analyseDataFileSetAttributes()
				.createRecordDecider()
				.setTemplate(StandardTemplates.POJO)
				.setPackageName("amsPoDownload.pojo")
				.setOutputDirectory("generatedCode/amsPoDownload")
				.generateJava()
                .writeTemplateBuilderCode("build");
	}
}
