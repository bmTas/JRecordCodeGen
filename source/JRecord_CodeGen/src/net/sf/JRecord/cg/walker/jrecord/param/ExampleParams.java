package net.sf.JRecord.cg.walker.jrecord.param;

import net.sf.JRecord.cg.walker.jrecord.IWriterMgr;
import net.sf.JRecord.cg.walker.jrecord.ImportManager;
import net.sf.JRecord.cg.walker.jrecord.WriterMgr;

public class ExampleParams implements IGenerateParameters {
	
	public static ExampleParams newReadExampleParam(IGenerateParameters dataClassParams, IGenerateParameters readClassParams) {
		return new ExampleParams("ReadExample", dataClassParams, readClassParams);
	}
	
	public static ExampleParams newWriteExampleParam(IGenerateParameters dataClassParams, IGenerateParameters readClassParams) {
		return new ExampleParams("WriteExample", dataClassParams, readClassParams);
	}
	

	//private final String extension;
	private final IGenerateParameters classParams;
	
	private final String outputDirectory, packageName, className, dataClassImport;
	private boolean packageDiferentFromDataClass;
	private IWriterMgr writer;

	private ExampleParams(String extension, IGenerateParameters dataClassParams, IGenerateParameters genClassParams) {
		super();
		//this.extension = extension;
		this.classParams = genClassParams;
						
		
		if (dataClassParams == null || dataClassParams.getClassName() == null || dataClassParams.getClassName().length() == 0) {
			throw new RuntimeException("You must supply a data-Class parameter with a class name");
		}
		
		String pn = dataClassParams.getPackageName();
		String cn = dataClassParams.getClassName() + extension;
		String od = dataClassParams.getOutputDirectory();
		String imp = "";
		boolean dif = false;
		
		if (classParams != null) {
			if (classParams.getPackageName().length() > 0) {
				pn = classParams.getPackageName();
				if ((dif = ! pn.equals(dataClassParams.getPackageName()))) {
					imp = dataClassParams.getPackageName() + "." + dataClassParams.getClassName();
				}
			}
			if (classParams.getClassName().length() > 0) {
				cn = classParams.getClassName();
			}
			if (classParams.getOutputDirectory() != null) {
				od = classParams.getOutputDirectory();
			}
		}
		
		this.packageName = pn;
		this.className = cn;
		this.packageDiferentFromDataClass = dif;
		this.dataClassImport = imp;
		this.outputDirectory = od;
	}

	@Override
	public boolean isUserDefinedWriter() {
		return classParams != null && classParams.isUserDefinedWriter();
	}

	@Override
	public IWriterMgr getWriter() {
		if (writer == null) {
			return getNewWriter();
		}
		return writer;
	}

	@Override
	public IWriterMgr getNewWriter() {
		if (isUserDefinedWriter()) {
			writer = classParams.getWriter();
		} else {
			writer = new WriterMgr(GenerateParameters.toClassWriter(outputDirectory, packageName, className));
		}
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
	public String getClassName() {
		return className;
	}

//	/**
//	 * @return the packageDiferentFromDataClass
//	 */
//	public boolean isPackageDiferentFromDataClass() {
//		return packageDiferentFromDataClass;
//	}
//
//	/**
//	 * @return the dataClassImport
//	 */
//	public String getDataClassImport() {
//		return dataClassImport;
//	}
	
	public ExampleParams updateImportList(ImportManager importMgr) {
		if (packageDiferentFromDataClass) {
			importMgr.addImport(dataClassImport);
		}
		return this;
	}
}
