package net.sf.JRecord.cg.details.fileAnalysis;

import java.util.Arrays;

public class TextDetails {
	
	public static final boolean[] ASCII_CHAR = new boolean[128];
	public static final String TEXT_CHARS = "+-.,abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
			+ "\n\t ;:?{}<>()[]\"'%$";
	
	static {
		Arrays.fill(ASCII_CHAR, false);
		for (int i = 0; i < TEXT_CHARS.length(); i++) {
			ASCII_CHAR[TEXT_CHARS.charAt(i)] = true;
		}
		ASCII_CHAR['\r'] = true;
	}

	public final static int countTextChars(byte[] data, boolean[] check) {
 		int count = 0;
 		int run = 1;
 		int j;
 		boolean last = false;
 		int cc = 0;

 //		maxRun = 0;
 		for (int i = 0; i < data.length; i++) {
 			j = toInt(data[i]);
 			if (j >= check.length) {
 				last = false;
 			} else {
     			if (last && check[j]) {
     				count += 1;
     				run += 1;
 //    				maxRun = Math.max(maxRun, run);
     				if (cc == 0) {
     					count += 1;
     				}

     				cc += 1;
     			}
     			last = check[j];
 			}
 			if (! last) {
 				cc = 0;
 				run = 1;
 			}
 		}

 		return count;
     }
	
	   public static int toInt(byte b) {
		   	int i = b;
		    	if (i < 0) {
		    		i += 256;
		    	}
		    	return i;
		    }


}
