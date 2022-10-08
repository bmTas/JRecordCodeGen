package net.sf.JRecord.cg.walker.jrecord;

import net.sf.JRecord.cg.walker.jrecord.param.GenerateParameters;
import net.sf.JRecord.cg.walker.jrecord.param.IClassDetails;
import net.sf.JRecord.cg.walker.jrecord.param.IGenerateParameters;

public interface IUpdateGenerateParamters extends IGenerateParameters {

	/**
	 * Set the package name of the generated class
	 * @param packageName package name
	 * @return this parameter class for more updates
	 */
	IClassDetails setPackageName(String packageName);

	/**
	 * Set the class name for the class to be generated. Normally you can
	 * leave blank and let the system generate a name
	 * @param className class name of class to be generated
	 * @return this parameter class for more updates
	 */
	IClassDetails setClassName(String className);

	/**
	 * Set where the generated class is to be written
	 * @param writer writer where the generated class is written
	 * @return
	 */
	GenerateParameters setWriter(Appendable writer);

	/**
	 * set directory where the generated java code is to be written
	 * @param outputDirectory set directory where the generated java code is to be written
	 */
	GenerateParameters setOutputDirectory(String outputDirectory);

	/**
	 * 
	 * Set where the generated class is to be written
	 * @param writer writerManager where the generated class is written
	 */
	GenerateParameters setWriter(IWriterMgr writer);
}