package net.sf.JRecord.cg.walker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.cg.schema.LayoutDef;
import net.sf.JRecord.cg.walker.classGenerator.ClassGeneratorWalker;
import net.sf.JRecord.cg.walker.classGenerator.MultiRecordReaderCodeGen;
import net.sf.JRecord.cg.walker.classGenerator.MultiRecordWriterCodeGen;
import net.sf.JRecord.cg.walker.classGenerator.SingleRecordReaderCodeGen;
import net.sf.JRecord.cg.walker.classGenerator.SingleRecordWriterCodeGen;
import net.sf.JRecord.cg.walker.jrecord.ISchemaWalkerObserver;
import net.sf.JRecord.cg.walker.jrecord.IUpdateGenerateParamters;
import net.sf.JRecord.cg.walker.jrecord.IWriterMgr;
import net.sf.JRecord.cg.walker.jrecord.SchemaWalker;
import net.sf.JRecord.cg.walker.jrecord.param.GenerateParameters;
import net.sf.JRecord.cg.walker.jrecord.param.IClassDetails;
import net.sf.JRecord.cg.walker.jrecord.param.IGenerateParameters;

/**
 * This <b>Builder</b> will generate a Cobol Data interface class + 
 * optionally example reader / writer example programs from a <b>Cobol</b>
 * copybook.
 * 
 * @author Bruce Martin
 *
 */
public class SchemaWalkerBuilder implements ISchemaWalkerBuilder {

	private final LayoutDef schema;
//	private String outputDirectory;
	private IUpdateGenerateParamters dataClassGenerateParameters = new GenerateParameters();
	private IGenerateParameters readExample, writeExample;
	private final List<ISchemaWalkerObserver> walkerListners = new ArrayList<ISchemaWalkerObserver>();
	
	
	/**
	 * This <b>Builder</b> will generate a Cobol Data interface class + 
	 * optionally example reader / writer example programs from a <b>Cobol</b>
	 * copybook.
	 * @param layout JRecord File-chema
	 */
	public SchemaWalkerBuilder(LayoutDetail layout) {
		this(new LayoutDef(layout, layout.getLayoutName(), null));
	}

	/**
	 * This <b>Builder</b> will generate a Cobol Data interface class + 
	 * optionally example reader / writer example programs from a <b>Cobol</b>
	 * copybook.
	 * @param schema CodeGen schema definition
	 */
	public SchemaWalkerBuilder(LayoutDef schema) {
		super();
		this.schema = schema;
		dataClassGenerateParameters.setClassName(this.schema.getExtensionName());
	}


	/**
	 * @param outputDirectory the outputDirectory to set
	 */
	@Override
	public ISchemaWalkerBuilder setOutputDirectory(String outputDirectory) {
		this.dataClassGenerateParameters.setOutputDirectory(outputDirectory);
		return this;
	}


	/**
	 * Get the package id, class name for generated from the Cobol Copybook
	 * @return the dataClassGenerateParameters
	 */
	@Override
	public IClassDetails getCobolDataClassGenerateParameters() {
		return dataClassGenerateParameters;
	}


	/**
	 * Set the package id, class name for generated from the Cobol Copybook
	 * @param dataClassGenerateParameters the dataClassGenerateParameters to set
	 */
	@Override
	public ISchemaWalkerBuilder setCobolDataClassGenerateParameters(IUpdateGenerateParamters dataClassGenerateParameters) {
		this.dataClassGenerateParameters = dataClassGenerateParameters;
		return this;
	}
	
	/**
	 * Set the Data Class (generated from the Cobol Copybook package name
	 * @param packageName
	 * @return
	 */
	@Override
	public ISchemaWalkerBuilder setCobolDataClassPackageName(String packageName) {
		this.dataClassGenerateParameters.setPackageName(packageName);
		return this;
	}
	
	
	/**
	 * Set the Data Class (generated from the Cobol Copybook) class name
	 * @param className class name touse in the generated parameters
	 * @return
	 */
	@Override
	public ISchemaWalkerBuilder setCobolDataClassName(String className) {
		this.dataClassGenerateParameters.setClassName(className);
		return this;
	}
	
	/**
	 * Run the generate reader example class with default parameters
	 * @return builder for more updates
	 */
	@Override
	public ISchemaWalkerBuilder setGenerateReadExampleToTrue() {
		this.readExample = setParameter(readExample, true);
		
		return this;
	}


	/**
	 * @param generate
	 */
	protected IGenerateParameters setParameter(IGenerateParameters param, boolean generate) {
		if (generate) {
			param = param == null ? new GenerateParameters() : param;
		} else {
			param = null;
		}
		return param;
	}

	/**
	 * Set the package id, class name for generated from the Cobol Copybook
	 * @param dataClassGenerateParameters the dataClassGenerateParameters to set
	 */
	@Override
	public ISchemaWalkerBuilder setReadClassGenerateParameters(IUpdateGenerateParamters readExample) {
		this.readExample = readExample;
		return this;
	}

	
	/**
	 * Run the generate reader example class with default parameters
	 * @return builder for more updates
	 */
	@Override
	public ISchemaWalkerBuilder setGenerateWriteExampleToTrue() {
		this.writeExample = setParameter(writeExample, true);
		
		return this;
	}

	/**
	 * Set the package id, class name for generated from the Cobol Copybook
	 * @param dataClassGenerateParameters the dataClassGenerateParameters to set
	 */
	@Override
	public ISchemaWalkerBuilder setWriteClassGenerateParameters(IUpdateGenerateParamters writeExample) {
		this.writeExample = writeExample;
		return this;
	}
	
	/**
	 * Add your own `schemaWalker` to be executed
	 * @param sw schema Walker to be executed.
	 * @return Builder for more updates
	 */
	@Override
	public ISchemaWalkerBuilder addSchemaWalker(ISchemaWalkerObserver sw) {
		walkerListners.add(sw);
		return this;
	}

	/**
	 * Generate the Requested classes
	 * @return Builder in case you want to generate more
	 */
	@Override
	public ISchemaWalkerBuilder generate() throws IOException {
		
//		if (this.outputDirectory == null || this.outputDirectory.length() == 0) {
//			this.outputDirectory = FileSystems.getDefault().getPath(".").toAbsolutePath().toString();
//			System.out.println(">> Output Directory: " + outputDirectory);
//		}
//		if (outputDirectory.endsWith(".")) {
//			outputDirectory = outputDirectory.substring(0, outputDirectory.length() - 1);
//		}
	
		//Files.createDirectories(Paths.get(outputFile).getParent());
		
//		GenerateParameters dataClassParams = new GenerateParameters().setPackageName("test.userGen.gen");
		
		StringBuilder b = new StringBuilder();
		SchemaWalker schemaWalker = new SchemaWalker(schema)
			.walk(new ClassGeneratorWalker(dataClassGenerateParameters.setWriter(b)));
		
		IWriterMgr writer = dataClassGenerateParameters.getNewWriter();
		writer.write(b.toString());
		writer.close();
		
		int recordCount = schema.getRecords().size();
		if (this.readExample != null) {
			if (recordCount == 1) {
				schemaWalker.walk(new SingleRecordReaderCodeGen(dataClassGenerateParameters, readExample));
			} else {
				schemaWalker.walk(new MultiRecordReaderCodeGen(dataClassGenerateParameters, readExample));
			}
		}
		
		
		if (this.writeExample != null) {
			if (recordCount == 1) {
				schemaWalker.walk(new SingleRecordWriterCodeGen(dataClassGenerateParameters, writeExample));
			} else {
				schemaWalker.walk(new MultiRecordWriterCodeGen(dataClassGenerateParameters, writeExample));
			}
		}
		
		for (ISchemaWalkerObserver walkerListners : walkerListners) {
			schemaWalker.walk(walkerListners);
		}
		
		return this;
	}
//	
//	private Writer createWriter(IClassDetails dataClassParams, IGenerateParameters genParams) throws IOException { 
//		if (genParams == null) { return null; }
//		
//		String packageId = genParams.getPackageName();
//		String className = genParams.getClassName() + ".java";
//		String packageFileName = Conversion.replace(packageId, ".", "/").toString();
//		
//		Path pkgPath = Paths.get(outputDirectory).resolve(packageFileName);
//		Path classPath = pkgPath.resolve(className);
//
//		Files.createDirectories(pkgPath);
//				
//		return Files.newBufferedWriter(classPath);
//	}
}
