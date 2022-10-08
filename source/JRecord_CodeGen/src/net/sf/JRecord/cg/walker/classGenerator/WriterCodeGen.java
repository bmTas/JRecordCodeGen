package net.sf.JRecord.cg.walker.classGenerator;

import net.sf.JRecord.cg.schema.LayoutDef;
import net.sf.JRecord.cg.walker.jrecord.ISchemaWalkerObserver;
import net.sf.JRecord.cg.walker.jrecord.SchemaWalker;
import net.sf.JRecord.cg.walker.jrecord.param.GenerateParameters;
import net.sf.JRecord.cg.walker.jrecord.param.IGenerateParameters;

public class WriterCodeGen extends DelegateCodeGen implements ISchemaWalkerObserver {
	
	private ISchemaWalkerObserver codeGen;

	private final IGenerateParameters dataClassParams;
	private final IGenerateParameters readClassParams;

	/**
	 * This class Will Generate a sample Example read program for
	 * for {@link ClassGeneratorWalker}
	 * It is used in the {@link SchemaWalker}
	 * 
	 * @param writer where to write the output
	 * @param dataClassParams  {@link ClassGeneratorWalker} parameters
	 */
	public WriterCodeGen(Appendable writer, IGenerateParameters dataClassParams) {
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
	public WriterCodeGen(IGenerateParameters dataClassParams, IGenerateParameters readClassParams) {
		this.dataClassParams = dataClassParams;
		this.readClassParams = readClassParams;
	}
	
	/**
	 * Create CodeGenWalker to be used as a delegate
	 * @param schema file schema
	 */
	protected ISchemaWalkerObserver getWalker(LayoutDef schema) {
		if (codeGen == null) {
			codeGen = schema.getRecords().size() > 1 
					? new MultiRecordWriterCodeGen(dataClassParams, readClassParams)
					: new SingleRecordWriterCodeGen(dataClassParams, readClassParams);
		}
		return codeGen;
	}
}
