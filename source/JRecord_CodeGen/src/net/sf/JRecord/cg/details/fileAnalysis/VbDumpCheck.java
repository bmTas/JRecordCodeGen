package net.sf.JRecord.cg.details.fileAnalysis;

import java.math.BigInteger;

import net.sf.JRecord.cg.details.codes.FileOrganisation;

public class VbDumpCheck extends StandardCheckFile {
	private static final int LAST_7_BITS_SET = 127;

	public VbDumpCheck() {
		super(FileOrganisation.MAINFRAME_VB_DUMP, "cp037");
	}

	@Override
	public boolean check(byte[] data) {
		boolean ret = false;
		if (data == null || data.length < 8) {
		} else if (data[6] == 0 && data[7] ==0) {
			int blockLength;
			byte[] bdwLength;
   			if (data[0] >= 0) {
   				bdwLength = new byte[2];
	            bdwLength[0] = data[0];
	            bdwLength[1] = data[1];
	            if (data[2] != 0 || data[3] != 0) {
	            	return false;
	            }
			} else {
  				bdwLength = new byte[4];
  				bdwLength[0] = (byte) (data[0] & LAST_7_BITS_SET);
   	            bdwLength[1] = data[1];
   	            bdwLength[2] = data[2];
   	            bdwLength[3] = data[3];
			}
   			blockLength = (new BigInteger(bdwLength)).intValue();
   			if (blockLength + 8 < data.length) {
   				if (data[blockLength + 6] == 0 && data[blockLength + 7] == 0) {
   					ret = super.check(data);
   				}
   			} else {
   				ret = super.check(data);
   			}
		}
		return ret;
	}
}

