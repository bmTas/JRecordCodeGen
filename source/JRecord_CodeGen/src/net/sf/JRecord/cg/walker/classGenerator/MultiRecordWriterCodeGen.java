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
public class MultiRecordWriterCodeGen extends SchemaWalkerAdapter {
	final ImportManager importMgr = new ImportManager(Arrays.asList(
			"java.io.IOException",
			"net.sf.JRecord.cgen.def.IWriter",
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
	 * @param writer where to write the output
	 * @param params  {@link ClassGeneratorWalker} parameters
	 */
	public MultiRecordWriterCodeGen(Appendable writer, IGenerateParameters dataClassParams) {
		this(dataClassParams, new GenerateParameters().setWriter(writer));
	}

//	/**
//	 * This class Will Generate a sample Example read program for
//	 * for {@link ClassGeneratorWalker}
//	 * It is used in the {@link SchemaWalker}
//	 * 
//	 * @param writer where to write the output
//	 * @param params  {@link ClassGeneratorWalker} parameters
//	 * @param packageId package name of the generated class
//	 * @param className Name of the generated class
//	 */
//	public MultiRecordWriterCodeGen(Appendable writer, IGenerateParameters dataClassParams, IGenerateParameters readClassParams) {
//		this(new WriterMgr(writer), dataClassParams, readClassParams);
//	}

	/**
	 * This class Will Generate a sample Example read program for
	 * for {@link ClassGeneratorWalker}
	 * It is used in the {@link SchemaWalker}
	 * 
	 * @param writer where to write the output
	 * @param params  {@link ClassGeneratorWalker} parameters
	 */
	public MultiRecordWriterCodeGen(IGenerateParameters dataClassParams, IGenerateParameters writeClassParams) {
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
				"       // You need to create a stream for you output file\n" + 
				"		IWriter<" + dataClass + ".I" + dataClass + "Children" + "> writer = " + dataClass + ".getIoProvider().newWriter(\"filename???\");\n\n" + 
				"		for (int i = 0; i < 5; i++) {" + 
				"");
		
		for (RecordDef r : schema.getRecords()) {
			writer.writeln("\t\t\twriter.write(generate" + r.getExtensionName() + "(i));");
		}

		writer.writeln(
				"\t\t}\n" + 
				"\t\twriter.close();\n" + 
				"\t}\n");
	}

	@Override
	public void endSchema(LayoutDef schema) {
		writer.writeln("}\n");
		
		writer.close();
	}
	

	/**
	 * Create a print method for every record Type
	 * @param schema file schema definition
	 * @param record definition of a Record Type
	 */
	@Override
	public void startRecord(LayoutDef schema, RecordDef record) {
		String recordName = dataClassParams.getClassName() + ".Rec" + record.getExtensionName();
		writer.writeln("");
		writer.writeln(
				"\tprivate static " + recordName + " generate"  + record.getExtensionName() + "(int i) {\n" 
				+ "\t\t" + recordName + " " + record.getJavaName() + " = new " + recordName + "();\n" );
	}

	/**
	 * End the method started in startRecord method
	 */
	@Override
	public void endRecord(LayoutDef schema, RecordDef record) {
		writer.writeln("\n\t\treturn " +  record.getJavaName() + ";\n"
				+ "\t}");
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
				writer.writeln("\t\t" + record.getJavaName() + "." + 
						fieldDtls.getArrayIndexDetails().getJavaName() + ".get(" + fieldDtls.getArrayDefinition().getZeroArrayIndex()
						+ ").set(" + val + ");");
			}
		} else {
			writer.writeln("\t\t" + record.getJavaName() + "." + fieldDtls.getJavaName() + ".set(" + val + ");");
		}
	}
}
