package net.sf.JRecord.cg.copybook;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import net.sf.JRecord.Common.Conversion;
import net.sf.cb2xml.copybookReader.CopybookColumns;
import net.sf.cb2xml.copybookReader.ICobolCopybookTextSource;
import net.sf.cb2xml.copybookReader.ReadCobolCopybook;

public class CobolCopybookSource implements ICobolCopybookTextSource {

	private String cobolCopybookText="", cobolFreeFormatText="", copybookName="";


	private CopybookColumns copybookColumns = CopybookColumns.STANDARD_COLUMNS;
	
	
	/**
	 * @param cobolCopybookText the cobolCopybookText to set
	 */
	public void setCobolCopybookText(String cobolCopybookText) {
		this.cobolCopybookText = cobolCopybookText;
	}

	
	public CobolCopybookSource readCobolCopybook(String copybookFileName) throws IOException {
		this.copybookName = Conversion.getCopyBookId(copybookFileName);
		return readCobolCopybook(new FileReader(copybookFileName));
	}

	public CobolCopybookSource readCobolCopybook(Reader copybookReader) throws IOException {
		BufferedReader r = new BufferedReader(copybookReader);
		StringBuffer b = new StringBuffer();
		String line;
		
		while ((line = r.readLine()) != null) {
			b.append(line).append('\n');
		}
		r.close();
		
		cobolCopybookText = b.toString();
		
		return this;
	}
	/**
	 * @return the copybookColumns
	 */
	public CopybookColumns getCopybookColumns() {
		return copybookColumns;
	}

	/**
	 * @param copybookColumns the copybookColumns to set
	 */
	public CobolCopybookSource setCopybookColumns(CopybookColumns copybookColumns) {
		if (this.copybookColumns != copybookColumns) {
			this.copybookColumns = copybookColumns == null ? CopybookColumns.STANDARD_COLUMNS : copybookColumns;
			this.cobolFreeFormatText = "";
		}
		return this;
	}

	@Override
	public String getCopybookName() {
		return copybookName;
	}
	
	/**
	 * @param copybookName the copybookName to set
	 */
	public CobolCopybookSource setCopybookName(String copybookName) {
		this.copybookName = copybookName;
		return this;
	}


	@Override
	public Reader getFreeFormatCopybookReader() {
		if (cobolFreeFormatText.length() == 0) {
			try {
				cobolFreeFormatText  = new ReadCobolCopybook()
											.setColumns(copybookColumns)
											.setCopybookName(copybookName)
											.addCobolCopybook(new StringReader(cobolCopybookText))
										.getFreeFormatCopybookText();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return new StringReader(cobolFreeFormatText);
	}

	@Override
	public int length() {
		return cobolFreeFormatText.length() == 0 ? cobolCopybookText.length() : cobolFreeFormatText.length();
	}

	@Override
	public String toPositionMessage(int lineNumber, int columnNumber) {
		return "LineNumber: " + lineNumber + "\tColumn Number: " + columnNumber;
	}

}
