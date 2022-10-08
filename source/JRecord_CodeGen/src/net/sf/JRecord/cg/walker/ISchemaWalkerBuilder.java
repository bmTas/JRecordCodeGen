package net.sf.JRecord.cg.walker;

import java.io.IOException;

import net.sf.JRecord.cg.walker.jrecord.ISchemaWalkerObserver;
import net.sf.JRecord.cg.walker.jrecord.IUpdateGenerateParamters;
import net.sf.JRecord.cg.walker.jrecord.param.IClassDetails;

/**
 * This class will run the standard Schema-Walker code generation classes
 * usage:<pre>
 * 
 * 		ICobolIOBuilder ioBuilder = JRecordInterface1.COBOL
 *				.newIOBuilder(TstIoBldrGen01.class.getResource("DTAR020.cbl").getFile())
 *						.setFileOrganization(IFileStructureConstants.IO_FIXED_LENGTH)
 *						.setFont("cp037");
 *		
 *		ISchemaWalkerInterface walker = CodeGenInterface.WALKER;
 *		walkers.newBuilder(ioBuilder)
 *					.setOutputDirectory("/home/bruce/work/JRecord/CodeGen/walker")
 *					.setCobolDataClassPackageName("test.walker.Builder.GenerateCode.data")
 *					.setReadClassGenerateParameters(walker.newClassDetailsParam()
 *														.setPackageName("test.walker.Builder.GenerateCode.example"))
 *					.setWriteClassGenerateParameters(walker.newClassDetailsParam()
 *														.setPackageName("test.walker.Builder.GenerateCode.example")
 *														.setClassName("MyWriteExample");
 * </pre>
 * 
 * @author Bruce Walker
 *
 */
public interface ISchemaWalkerBuilder {

//	/**
//	 * @param outputDirectory the outputDirectory where the generated code is to be written
//	 */
//	ISchemaWalkerBuilder setOutputDirectory(String outputDirectory);
//
	/**
	 * Get the GeneratedParameter that holds package id, class name etc to
	 * be used to generate Java from a Cobol Copybook
	 * 
	 * @return the dataClassGenerateParameters
	 */
	IClassDetails getCobolDataClassGenerateParameters();

	/**
	 * Set the GeneratedParameter that holds package id, class name etc to
	 * be used to generate Java from a Cobol Copybook. Typical usage:
	 * 
	 * <pre>
	 *     builder.setCobolDataClassGenerateParameters(new GenerateParameters()
	 *     							.setPackageName("my.package.name"));
	 * </pre>
	 *  
	 * @param dataClassGenerateParameters the dataClassGenerateParameters generated parameter
	 */
	ISchemaWalkerBuilder setCobolDataClassGenerateParameters(IUpdateGenerateParamters dataClassGenerateParameters);

	/**
	 * Set the <i>DataClass</I> package name. The <i>dataClass</i> is generated from the Cobol copybook
	 * and provides an interface between your java code and the Cobol-Data 
	 * 
	 * @param packageName package name of the generated class
	 * @return builder for more updates
	 */
	ISchemaWalkerBuilder setCobolDataClassPackageName(String packageName);

	/**
	 * Set the <i>DataClass</I> The <i>dataClass</i> is generated from the Cobol copybook
	 * and provides a java interface to the Cobol-Data. By default this will be
	 * based on the Cobol-Copybook-Name but you can override this name.
	 * 
	 * @param className class name to use in the generated parameters
	 * 
	 * @return builder for more updates
	 */
	ISchemaWalkerBuilder setCobolDataClassName(String className);

	/**
	 * Request that an example reader class be generated
	 * 
	 * @return builder for more updates
	 */
	ISchemaWalkerBuilder setGenerateReadExampleToTrue();

	/**
	 * Request that an example reader class be generated
	 * with supplied package name, class name (via a GenerateParamters class)
	 * usage:
	 * <pre>
	 *     builder.setReadClassGenerateParameters(new GenerateParameters()
	 *     							.setPackageName("my.example.package.name")
	 *     							.setClassName("MyReadExample"));
	 * </pre>
	 * 
	 * @param dataClassGenerateParameters the dataClassGenerateParameters to set
	 */
	ISchemaWalkerBuilder setReadClassGenerateParameters(IUpdateGenerateParamters readExample);

	/**
	 *  Request that an example write class be generated
	 * 
	 * @return builder for more updates
	 */
	ISchemaWalkerBuilder setGenerateWriteExampleToTrue();

	/**
	 * Request that an example writer class be generated
	 * with supplied package name, class name (via a GenerateParamters class)
	 * usage:
	 * <pre>
	 *     builder.setWriteClassGenerateParameters(new GenerateParameters()
	 *     							.setPackageName("my.example.package.name")
	 *     							.setClassName("MyReadExample"));
	 * </pre>
	 * 
	 * @param dataClassGenerateParameters the dataClassGenerateParameters to set
	 */
	ISchemaWalkerBuilder setWriteClassGenerateParameters(IUpdateGenerateParamters writeExample);

	/**
	 * Add your own `schemaWalker` to be executed
	 * @param sw schema Walker to be executed.
	 * @return Builder for more updates
	 */
	ISchemaWalkerBuilder addSchemaWalker(ISchemaWalkerObserver sw);

	/**
	 * Generate the Requested classes
	 * 
	 * @return Builder in case you want to generate more
	 */
	ISchemaWalkerBuilder generate() throws IOException;

	/**
	 * @param outputDirectory the outputDirectory to set
	 */
	ISchemaWalkerBuilder setOutputDirectory(String outputDirectory);

}