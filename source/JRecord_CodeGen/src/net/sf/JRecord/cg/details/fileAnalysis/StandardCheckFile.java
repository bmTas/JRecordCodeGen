package net.sf.JRecord.cg.details.fileAnalysis;

import java.io.ByteArrayInputStream;

import net.sf.JRecord.ByteIO.AbstractByteReader;
import net.sf.JRecord.ByteIO.ByteIOProvider;
import net.sf.JRecord.cg.details.codes.FileOrganisation;

public class StandardCheckFile implements ICheckFile {
	private FileOrganisation structure;
	private String font = "";
	protected int linesRead = 0;

   	/**
	 * @param fileStructure
	 */
	public StandardCheckFile(FileOrganisation fileStructure) {
		this.structure = fileStructure;
	}

	public StandardCheckFile(FileOrganisation fileStructure, String fontName) {
		this.structure = fileStructure;
		this.font = fontName;
	}

	public boolean check(byte[] data) {
   		boolean ret = true;
   		try {
   			int len = 0;
   			byte[] bytes;
   			@SuppressWarnings("deprecation")
			AbstractByteReader reader = ByteIOProvider.getInstance().getByteReader(structure.getFileOrganisationCode());
   			reader.open(new ByteArrayInputStream(data));

   			for (int i = 0;
   					i < 20 && len < data.length && ((bytes = reader.read()) != null);
   					i++) {
   				len += bytes.length;
   				linesRead = i;
   			}
   			reader.close();
   		} catch (Exception e) {
			ret = false;
		}
   		return ret && linesRead > 1;
   	}

	public FileOrganisation getFileStructure() {
		return structure;
	}

	public String getFontName() {
		return font;
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.layoutWizard.FileStructureAnalyser.checkFile#getLinesRead()
	 */
	@Override
	public int getLinesRead() {
		return linesRead;
	}

}
