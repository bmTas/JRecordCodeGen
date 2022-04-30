package net.sf.JRecord.zcg.zTest;

import java.io.IOException;
import java.io.StringReader;

import net.sf.JRecord.JRecordInterface1;
import net.sf.JRecord.Common.IFileStructureConstants;
import net.sf.JRecord.cg.walker.classGenerator.ClassGeneratorWalker;
import net.sf.JRecord.cg.walker.classGenerator.ClassGeneratorWalkerTry1;
import net.sf.JRecord.cg.walker.classGenerator.SingleRecordReaderCodeGen;
import net.sf.JRecord.cg.walker.jrecord.SchemaWalker;
import net.sf.JRecord.cg.walker.jrecord.param.GenerateParameters;
import net.sf.JRecord.def.IO.builders.ICobolIOBuilder;
import test.gen.TstIoBldrGen01;

public class BldGenClass {

	public static final String cobolCopybook =
			"         03  occurs 7.\n"
					+ "             05 field-1        pic x(3).\n"
					+ "             05 field-2        pic s9(4)v999.\n"
					+ "             05 occurs 5.\n"
					+ "                10 field-3     pic s9(7) comp-3.\n"
					+ "                10 field-4     pic s9(7) comp occurs 4.\n"
					+ "                10 field-5     pic s9(7)v99 comp-3 occurs 4.\n"
					+ "             05 field-6        pic xxx.\n"
					+ "\n";
	public static void main(String[] args) throws IOException {
		
		
		StringBuffer
			ArrayTstTry1Out = new StringBuffer(),
			ArrayTestOut = new StringBuffer(),
			ArrayTestReaderOut = new StringBuffer(),
			datar020Out = new StringBuffer();
		new SchemaWalker(getCobolBldr()	.getLayout())
					.walk(new ClassGeneratorWalkerTry1(
							ArrayTstTry1Out, 
							new GenerateParameters()
								.setPackageName("net.sf.JRecord.zcg.zTest")
								.setClassName("ArrayTst"), 
							true)
						);

		GenerateParameters dataClassParams = new GenerateParameters();
		new SchemaWalker(getCobolBldr()	.getLayout())
					.walk(new ClassGeneratorWalker(
							dataClassParams
								.setWriter(ArrayTestOut)
								.setPackageName("net.sf.JRecord.zcg.zTest")
								.setClassName("ArrayTest1"))
						)
					.walk(new SingleRecordReaderCodeGen(
							dataClassParams, 
							new GenerateParameters().setWriter(ArrayTestReaderOut)));
		System.out.println(ArrayTestReaderOut);

			
		new SchemaWalker(getDTAR020bldr().getLayout())
					.walk(new ClassGeneratorWalkerTry1(
							datar020Out,
							new GenerateParameters().setPackageName("net.sf.JRecord.zcg.zTest"),
							true));
	}
	
	/**
	 * @return
	 */
	static ICobolIOBuilder getCobolBldr() {
		return JRecordInterface1.COBOL
				.newIOBuilder(new StringReader(cobolCopybook), "Array-Tst")
				.setFileOrganization(IFileStructureConstants.IO_FIXED_LENGTH)
				.setFont("cp037");
	}

	/**
	 * @return
	 */
	public static ICobolIOBuilder getDTAR020bldr() {
		return JRecordInterface1.COBOL
				.newIOBuilder(TstIoBldrGen01.class.getResource("DTAR020.cbl").getFile())
						.setFileOrganization(IFileStructureConstants.IO_FIXED_LENGTH)
						.setFont("cp037");
	}


}
