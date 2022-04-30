package net.sf.JRecord.cg.walker.classGenerator;

import java.util.Arrays;

import net.sf.JRecord.cg.schema.FieldDef;
import net.sf.JRecord.cg.schema.LayoutDef;
import net.sf.JRecord.cg.schema.RecordDef;
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
public class MultiRecordReaderCodeGen extends SchemaWalkerAdapter {
	final ImportManager importMgr = new ImportManager(Arrays.asList(
			"java.io.IOException",
			"net.sf.JRecord.cgen.def.IReader"));
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
	public MultiRecordReaderCodeGen(Appendable writer, IGenerateParameters params) {
		this(	params, 
				new GenerateParameters()
					.setWriter(writer));
	}


	/**
	 * This class Will Generate a sample Example read program for
	 * for {@link ClassGeneratorWalker}
	 * It is used in the {@link SchemaWalker}
	 * 
	 * @param dataClassParams definition of Cobol-interface Class {@link ClassGeneratorWalker} parameters
	 * @param readClassParams details of name and where to write it {@link ClassGeneratorWalker}
	 */
	public MultiRecordReaderCodeGen(IGenerateParameters dataClassParams, IGenerateParameters readClassParams) {
		super();
		this.dataClassParams = dataClassParams;
		
		this.exampleParams = ExampleParams.newReadExampleParam(dataClassParams, readClassParams);
		this.writer = exampleParams.getWriter();
		
		exampleParams.updateImportList(importMgr);
	}

	@Override
	public void startSchema(LayoutDef schema) {
		String dataClass = dataClassParams.getClassName();
		
		writer.writeln("package " + exampleParams.getPackageName() + ";\n" );
		writer.writeln(importMgr.generateImports());
		
		writer.writeln(
				"public class " + exampleParams.getClassName() + " {\n" + 
				"\n" + 
				"	public static void main(String[] args) throws IOException {\n" +
				"       // You need to provide a filename / stream for your cobol data\n" + 
				"		IReader<" + dataClass + "> reader = " + dataClass + ".getIoProvider().newReader(\"filename???\");\n" + 
				"		"  + dataClass + " line;\n" + 
				"		\n" + 
				"		while ((line = reader.read()) != null) {\n" + 
				"");
		
		writer.writeln("\n\t\t// You need to setup a if / switch statements to determine the Record Type"
				+ "\n\t\t// and callthe appropriate process??? method"
				+ " ");
		String comment = "//if (???) {";
		for (RecordDef r : schema.getRecords()) {
			writer.writeln(""
					+ "\t\t\t" + comment + "\n"
					+ "\t\t\t\tprocess" + r.getExtensionName() + "(" 
							+ "line." + r.getJavaName() + ");");
			comment = "//} else if (???) {";
		}
		writer.writeln(
				"			//}\n" + 
				"		}\n" + 
				"		reader.close();\n" + 
				"	}\n");
	}

	@Override
	public void endSchema(LayoutDef schema) {
		writer.writeln("}");
		
		writer.close();
	}

	/**
	 * Create a print method for every record Type
	 * @param schema file schema definition
	 * @param record definition of a Record Type
	 */
	@Override
	public void startRecord(LayoutDef schema, RecordDef record) {
		writer.writeln("");
		writer.writeln(
				"\tprivate static void process"  + record.getExtensionName() 
						+ "(" + dataClassParams.getClassName() 
						+ ".Rec" + record.getExtensionName() + " " + record.getJavaName()
						+ ") {\n\n"
				+ "\t\tSystem.out.println(\"\"");
	}

	/**
	 * End the method started in startRecord method
	 */
	@Override
	public void endRecord(LayoutDef schema, RecordDef record) {
		writer.writeln("\t\t);");
		writer.writeln("\t}");
	}

	/**
	 * Write a single field
	 * @param schema file schema definition
	 * @param record definition of a Record Type
	 * @param fieldDtls field definition
	 */
	@Override
	public void processField(LayoutDef schema, RecordDef record, FieldDef fieldDtls) {
		if (fieldDtls.isArrayItem()) {
			if (fieldDtls.getArrayIndexDetails().isFirstIndex()) {
				writer.writeln("\t\t\t\t+ " + record.getJavaName() + 
						"." + fieldDtls.getArrayIndexDetails().getJavaName() + ".get(" + fieldDtls.getArrayDefinition().getZeroArrayIndex() + ")." +
						fieldDtls.getAsType() + "() + \"\\t\"");
			}
		} else {
			writer.writeln("\t\t\t\t+ " + record.getJavaName() + 
					"." + fieldDtls.getJavaName() + "." + fieldDtls.getAsType() + "() + \"\\t\"");
		}
	}
}
