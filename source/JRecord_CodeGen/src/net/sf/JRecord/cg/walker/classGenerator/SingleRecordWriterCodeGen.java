package net.sf.JRecord.cg.walker.classGenerator;

import java.util.Arrays;

import net.sf.JRecord.cg.schema.FieldDef;
import net.sf.JRecord.cg.schema.LayoutDef;
import net.sf.JRecord.cg.schema.RecordDef;
import net.sf.JRecord.cg.schema.type.IJavaFieldType;
import net.sf.JRecord.cg.schema.type.JavaTypes;
import net.sf.JRecord.cg.walker.jrecord.IWriterMgr;
import net.sf.JRecord.cg.walker.jrecord.ImportManager;
import net.sf.JRecord.cg.walker.jrecord.SchemaWalker;
import net.sf.JRecord.cg.walker.jrecord.SchemaWalkerAdapter;
import net.sf.JRecord.cg.walker.jrecord.param.ExampleParams;
import net.sf.JRecord.cg.walker.jrecord.param.GenerateParameters;
import net.sf.JRecord.cg.walker.jrecord.param.IGenerateParameters;


/**
 * This class Will Generate a sample Example read program for
 * for {@link ClassGeneratorWalker}
 * It is used in the {@link SchemaWalker}
 * 
 * @author Bruce Martin
 *
 */
public class SingleRecordWriterCodeGen extends SchemaWalkerAdapter {
	final ImportManager importMgr = new ImportManager(Arrays.asList(
			"java.io.IOException",
			"net.sf.JRecord.cgen.def.IWriter"));
	private final IGenerateParameters dataClassParams;
	private final IWriterMgr writer;
//	private String className, packageId;
	private final ExampleParams exampleParams;
	

	/**
	 * This class Will Generate a sample Example read program for
	 * for {@link ClassGeneratorWalker}
	 * It is used in the {@link SchemaWalker}
	 * 
	 * @param writer  Where to write the generated class
	 * @param dataClassParams  Cobol interface class details {@link ClassGeneratorWalker} parameters
	 */
	public SingleRecordWriterCodeGen(Appendable writer, IGenerateParameters dataClassParams) {
		this(dataClassParams, new GenerateParameters()	.setWriter(writer));
	}


	/**
	 * This class Will Generate a sample Example read program for
	 * for {@link ClassGeneratorWalker}
	 * It is used in the {@link SchemaWalker}
	 * 
	 * @param dataClassParams data-class {@link ClassGeneratorWalker} parameters
	 * @param writeClassParams example-write-class {@link ClassGeneratorWalker} parameters
	 */
	public SingleRecordWriterCodeGen(IGenerateParameters dataClassParams, IGenerateParameters writeClassParams) {
		super();
		this.dataClassParams = dataClassParams;
		this.exampleParams = ExampleParams.newWriteExampleParam(dataClassParams, writeClassParams);
		this.writer = exampleParams.getWriter();
		
		exampleParams.updateImportList(importMgr);
	}

	@Override
	public void startSchema(LayoutDef schema) {
		String dataClass = dataClassParams.getClassName();
		
		for (IJavaFieldType jt : schema.getTypesUsed()) {
			if (jt.getJavaTypeImport().length() > 0) {
				importMgr.addImport(jt.getJavaTypeImport());
			}
		}

		writer.writeln("package " + exampleParams.getPackageName() + ";\n" );
		writer.writeln(importMgr.generateImports());
		writer.writeln("public class " + exampleParams.getClassName() + " {\n" + 
				"\n" + 
				"	public static void main(String[] args) throws IOException {\n" +
				"\n" + 
				"		" + dataClass + " line = new " + dataClass + "();\n" + 
				"       // You need to create a stream for you output file\n" + 
				"		IWriter<" + dataClass + "> writer = " + dataClass + ".getIoProvider().newWriter(\"filename???\");\n\n" + 
				"		for (int i = 0; i < 5; i++) {" + 
				"");
	}

	@Override
	public void endSchema(LayoutDef schema) {
		writer.writeln(
				"\t\t\twriter.write(line);\n" + 
				"\t\t}\n" + 
				"\t\twriter.close();\n" + 
				"\t}\n" + 
				"}\n");
		
		writer.close();
	}

	@SuppressWarnings("incomplete-switch")
	@Override
	public void processField(LayoutDef schema, RecordDef record, FieldDef fieldDtls) {
		String val = fieldDtls.isNumeric() ? "i" : "\"\" + i";
		if (fieldDtls.javaTypeInfo instanceof JavaTypes) {
			switch ((JavaTypes) fieldDtls.javaTypeInfo) {
			case DECIMAL: val = "BigDecimal.valueOf(i)";			break;
			case BIG_INTEGER: val = "BigInteger.valueOf(i)";		break;
			}
		}
		
		if (fieldDtls.isArrayItem()) {
			if (fieldDtls.getArrayIndexDetails().isFirstIndex()) {
				writer.writeln("\t\t\tline." + 
						fieldDtls.getArrayIndexDetails().getJavaName() + ".get(" + fieldDtls.getArrayDefinition().getZeroArrayIndex()
						+ ").set(" + val + ");");
			}
		} else {
			writer.writeln("\t\t\tline." + fieldDtls.getJavaName() + ".set(" + val + ");");
		}
	}
}
