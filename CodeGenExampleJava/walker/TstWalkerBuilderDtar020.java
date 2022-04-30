
import java.io.IOException;

import net.sf.JRecord.JRecordInterface1;
import net.sf.JRecord.Common.IFileStructureConstants;
import net.sf.JRecord.cg.CodeGenInterface;
import net.sf.JRecord.cg.walker.ISchemaWalkerInterface;
import net.sf.JRecord.def.IO.builders.ICobolIOBuilder;

public class TstWalkerBuilderDtar020 {

	public static void main(String[] args) throws IOException {
		ICobolIOBuilder ioBuilder = JRecordInterface1.COBOL
				.newIOBuilder("DTAR020.cbl")
						.setFileOrganization(IFileStructureConstants.IO_FIXED_LENGTH)
						.setFont("cp037");
		
		String packagePref = "test.walker.Builder.GenerateCode.dtar020";
		String examplePackage = packagePref + ".example";
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
