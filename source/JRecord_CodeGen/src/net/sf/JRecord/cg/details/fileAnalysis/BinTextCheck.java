package net.sf.JRecord.cg.details.fileAnalysis;

import net.sf.JRecord.cg.details.codes.FileOrganisation;

public class BinTextCheck extends StandardCheckFile {
	
//	private static final boolean[] ASCII_CHAR = new boolean[128];
//    private static final String TEXT_CHARS = "+-.,abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
//			+ "\n\t ;:?{}<>()[]\"'%$";
//	
//	static {
//		Arrays.fill(ASCII_CHAR, false);
//		for (int i = 0; i < TEXT_CHARS.length(); i++) {
//			ASCII_CHAR[TEXT_CHARS.charAt(i)] = true;
//		}
//		ASCII_CHAR['\r'] = true;
//	}

   	final boolean[] charMap;
	final int linesRequired;
	
	public BinTextCheck() {
		this(TextDetails.ASCII_CHAR, "", 3);
	}
	public BinTextCheck(final boolean[] chars, String font, int linesRequired) {
		super(FileOrganisation.TEXT, font);
		this.charMap = chars;
		this.linesRequired = linesRequired;
	}

	@Override
	public boolean check(byte[] data) {
		boolean ret = false;
		if (data != null) {
    		int count = TextDetails.countTextChars(data, charMap);

    		//System.out.println(" }}} " + (count * 100 / data.length));
    		if ((count * 100) / data.length >= 70) {
    			ret = super.check(data) && super.linesRead > linesRequired;
    		}
		}

		return ret;
	}
}
