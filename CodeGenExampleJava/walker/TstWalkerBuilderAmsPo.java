
import java.io.IOException;

import net.sf.JRecord.JRecordInterface1;
import net.sf.JRecord.Common.IFileStructureConstants;
import net.sf.JRecord.External.CopybookLoader;
import net.sf.JRecord.cg.CodeGenInterface;
import net.sf.JRecord.cg.walker.ISchemaWalkerInterface;
import net.sf.JRecord.def.IO.builders.ICobolIOBuilder;
import net.sf.JRecord.def.IO.builders.recordDeciders.ISingleFieldDecider;

public class TstWalkerBuilderAmsPo {

	public static void main(String[] args) throws IOException {
		ISingleFieldDecider deciderDescription = JRecordInterface1.RECORD_DECIDER_BUILDER
				.singleFieldDeciderBuilder("Record-Type", false)
					.addRecord("h1", "PO-Record")
					.addRecord("d1", "Product-Record")
					.addRecord("s1", "Location-Record")
				.build();


		String packagePref = "test.walker.Builder.GenerateCode.amsPo";
		String examplePackage = packagePref + ".example";
		ICobolIOBuilder ioBuilder = JRecordInterface1.COBOL
				.newIOBuilder("amsPoDownload.cbl")
						.setFileOrganization(IFileStructureConstants.IO_BIN_TEXT)
						.setSplitCopybook(CopybookLoader.SPLIT_01_LEVEL)
						.setRecordDecider(deciderDescription)
						;
		
		ISchemaWalkerInterface walker = CodeGenInterface.WALKER;
		walker.newBuilder(ioBuilder)
					.setOutputDirectory("CodeGen/walker")
					.setCobolDataClassPackageName(packagePref + ".data")
					.setReadClassGenerateParameters(walker.newClassDetailsParam()
														.setPackageName(examplePackage))
					.setWriteClassGenerateParameters(walker.newClassDetailsParam()
														.setPackageName(examplePackage))
				.generate();
	}

}
