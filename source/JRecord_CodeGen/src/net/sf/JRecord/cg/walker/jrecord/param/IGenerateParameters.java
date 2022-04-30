package net.sf.JRecord.cg.walker.jrecord.param;

import net.sf.JRecord.cg.walker.jrecord.IWriterMgr;

public interface IGenerateParameters extends IClassDetails {

	/**
	 * 
	 * @return Wether the user has define an actual Writter;
	 */
	boolean isUserDefinedWriter();
	/**
	 * Where the generated code is going to written
	 * @return 
	 */
	IWriterMgr getWriter();

	/**
	 * Create new Writer
	 * @return new Writer
	 */
	IWriterMgr getNewWriter();

	/**
	 * @return Output directory, could be null (if not set by the user)
	 */
	String getOutputDirectory();
}