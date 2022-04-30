package net.sf.JRecord.cg.walker.jrecord.param;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Paths;

import net.sf.JRecord.Common.Conversion;
import net.sf.JRecord.cg.walker.jrecord.IUpdateGenerateParamters;
import net.sf.JRecord.cg.walker.jrecord.IWriterMgr;
import net.sf.JRecord.cg.walker.jrecord.WriterMgr;

/**
 * Because the Code-Generator-Class have fairly complicated Complicated parameter requirements,
 * I created a separate `Parameter` class.
 * The class basically provides<ul>
 * <li>Writer to write the generated class
 * <li>Package-Name can be blank
 * <li>Class-Name can be blank, the Code0Writer will generate one
 * </ul>
 * 
 * Class containing parameters for Generating Java-Classes (from a Cobol Copybook)
 *  
 * @author Bruce Martin
 *
 */
public class GenerateParameters implements IUpdateGenerateParamters {
	private String packageName = "", className = "", outputDirectory;
	private IWriterMgr writer;
	boolean userDefinedWriter = false;
	
	/**
	 * Create a new a new Generate parameter for use in the standard 
	 * schema-walker classes. With a GenerateParameter the user can 
	 * <ul>
	 *  <li>Set the output destintion (where the code is written by either
	 *  a writer or a directory.
	 *  <li>Set the package-name of the generated class
	 *  <set the class name of the class to be generated.
	 * </ul>
	 * 
	 * @return
	 */
	public static GenerateParameters newParam() { return new GenerateParameters(); }
	
	public GenerateParameters() {
		super();
	}
	
	public GenerateParameters(IGenerateParameters parameters) {
		super();
		setParameters(parameters);
	}

	/**
	 * Set values from an existing IGenerateParameters class
	 * @param parameters
	 * @return
	 */
	public GenerateParameters setParameters(IGenerateParameters parameters) {
		outputDirectory = parameters.getOutputDirectory();
		userDefinedWriter = parameters.isUserDefinedWriter();
		
		if (parameters.isUserDefinedWriter()) {
			writer = parameters.getWriter();
		}
		packageName = parameters.getPackageName();
		className = parameters.getClassName();
		return this;
	}
	
	public static String toFileName(String outputDirectory, String packageName, String className) {
		return outputDirectory
				+ "/" + Conversion.replace(packageName, ".", "/") 
				+ "/" + className + ".java";
	}
	
	/**
	 * Create a `Writer` from a directory, java-package-name and class name. The file is basically
	 * written to directory/package-name/classname.java where the '.' in the package-name are replace by `/`
	 * 
	 * @param outputDirectory base directory where the class is to be written
	 * @param packageName package name of the class.
	 * @param className clas name to be written
	 * 
	 * @return writer to write the class
	 */
	public static Writer toClassWriter(String outputDirectory, String packageName, String className) {
		if (outputDirectory == null) {
			throw new RuntimeException("You must supply either `OutputDirectory` or a `Writer` where the code is to be written");
		} else if (outputDirectory.length() == 0 || ".".equals(outputDirectory)) {
			outputDirectory = FileSystems.getDefault().getPath(".").toAbsolutePath().toString();
		}
		if (outputDirectory.endsWith(".")) {
			outputDirectory = outputDirectory.substring(0, outputDirectory.length() - 1);
		}
		String fname = toFileName(outputDirectory, packageName, className);
		try {
			Files.createDirectories(Paths.get(fname).getParent());
			return new FileWriter(fname);
		} catch (IOException e) {
			throw new RuntimeException("Can not create Output File: " + fname, e);
		}
	}

	
	@Override
	public boolean isUserDefinedWriter() {
		return userDefinedWriter;
	}

	@Override
	public IWriterMgr getWriter()  {
		if (writer == null) {
			return getNewWriter();
		}

		return writer;
	}
	
	@Override
	public IWriterMgr getNewWriter()  {
		writer = new WriterMgr(toClassWriter(outputDirectory, packageName, className));

		return writer;
	}

	@Override
	public String getOutputDirectory() {
		return outputDirectory;
	}

	@Override
	public String getPackageName() {
		return packageName;
	}
	
	@Override
	public GenerateParameters setOutputDirectory(String outputDirectory) {
		this.outputDirectory = outputDirectory;
		return this;
	}

	@Override
	public GenerateParameters setWriter(Appendable writer) {
		setWriter(new WriterMgr(writer));
		return this;
	}

	/**
	 * @param writer the writerManage use to write the generated class
	 */
	@Override
	public GenerateParameters setWriter(IWriterMgr writer) {
		this.writer = writer;
		this.userDefinedWriter = true;
		return this;
	}

	@Override
	public GenerateParameters setPackageName(String packageName) {
		this.packageName = packageName;
		return this;
	}
	
	@Override
	public String getClassName() {
		return className;
	}
	
	@Override
	public GenerateParameters setClassName(String className) {
		this.className = className;
		return this;
	}
}
