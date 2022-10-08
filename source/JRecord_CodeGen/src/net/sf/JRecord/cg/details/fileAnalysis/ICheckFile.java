package net.sf.JRecord.cg.details.fileAnalysis;

import net.sf.JRecord.cg.details.codes.FileOrganisation;

public interface ICheckFile {
	public boolean check(byte[] data);
	public FileOrganisation getFileStructure();
	public String getFontName();
	public int getLinesRead();
}
