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
public class SingleRecordReaderCodeGen extends SchemaWalkerAdapter {
	final ImportManager importMgr = new ImportManager(Arrays.asList(
			"java.io.IOException",
			"net.sf.JRecord.cgen.def.IReader"));
	private final IGenerateParameters dataClassParams;
	private final IWriterMgr writer;
	//private String className, packageId;
	private final ExampleParams exampleParams;
	

	/**
	 * This class Will Generate a sample Example read program for
	 * for {@link ClassGeneratorWalker}
	 * It is used in the {@link SchemaWalker}
	 * 
	 * @param writer where to write the output
	 * @param dataClassParams  {@link ClassGeneratorWalker} parameters
	 */
	public SingleRecordReaderCodeGen(Appendable writer, IGenerateParameters dataClassParams) {
		this(dataClassParams, new GenerateParameters() .setWriter(writer));
	}


	/**
	 * This class Will Generate a sample Example read program for
	 * for {@link ClassGeneratorWalker}
	 * It is used in the {@link SchemaWalker}
	 * 
	 * @param dataClassParams definition of Cobol-interface Class {@link ClassGeneratorWalker} parameters
	 * @param readClassParams details of name and where to write it {@link ClassGeneratorWalker}
	 */
	public SingleRecordReaderCodeGen(IGenerateParameters dataClassParams, IGenerateParameters readClassParams) {
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
		writer.writeln("public class " + exampleParams.getClassName() + " {\n" + 
				"\n" + 
				"	public static void main(String[] args) throws IOException {\n" +
				"       // You need provide a file/stream for your input file\n" + 
				"		IReader<" + dataClass + "> reader = " + dataClass + ".getIoProvider().newReader(\"filename???\");\n" + 
				"		"  + dataClass + " line;\n" + 
				"		\n" + 
				"		while ((line = reader.read()) != null) {\n" + 
				"			System.out.println(\"\"" + 
				"");
	}

	@Override
	public void endSchema(LayoutDef schema) {
		writer.writeln(
				"			);\n" + 
				"		}\n" + 
				"		reader.close();\n" + 
				"	}\n" + 
				"}\n");
		
		writer.close();
	}

	@Override
	public void processField(LayoutDef schema, RecordDef record, FieldDef fieldDtls) {
		if (fieldDtls.isArrayItem()) {
			if (fieldDtls.getArrayIndexDetails().isFirstIndex()) {
				writer.writeln("\t\t\t\t+ line." + 
						fieldDtls.getArrayIndexDetails().getJavaName() + ".get(" + fieldDtls.getArrayDefinition().getZeroArrayIndex() + ")." +
						fieldDtls.getAsType() + "() + \"\\t\"");
			}
		} else {
			writer.writeln("\t\t\t\t+ line." + fieldDtls.getJavaName() + "." + fieldDtls.getAsType() + "() + \"\\t\"");
		}
	}
}
