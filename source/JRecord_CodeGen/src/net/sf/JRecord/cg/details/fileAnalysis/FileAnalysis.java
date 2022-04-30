package net.sf.JRecord.cg.details.fileAnalysis;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import net.sf.JRecord.JRecordInterface1;
import net.sf.JRecord.Common.AbstractFieldValue;
import net.sf.JRecord.Common.Conversion;
import net.sf.JRecord.Common.IFieldDetail;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.RecordDetail;
import net.sf.JRecord.Details.fieldValue.IFieldValue;
import net.sf.JRecord.IO.AbstractLineReader;
import net.sf.JRecord.IO.builders.recordDeciders.RecordTypeAndRecord;
import net.sf.JRecord.IO.builders.recordDeciders.SingleFieldDecider;
import net.sf.JRecord.Types.Type;
import net.sf.JRecord.cg.details.codes.FileOrganisation;
import net.sf.JRecord.def.IO.builders.ISchemaIOBuilder;
import net.sf.JRecord.detailsBasic.IItemDetails;



public class FileAnalysis {
	private static final String US_EBCDIC = "cp037";
	private static final int BUFFER_SIZE = 1000000;
    
    public static enum EncodingType {
    		NONE(null),
    		ASCII(Conversion.DEFAULT_ASCII_CHARSET),
    		EBCDIC(US_EBCDIC),
    		;
    		private final String encoding;
    		EncodingType(String encoding) {
    			this.encoding = encoding;
    		}
    		
    		public boolean isEncodingDefined() {
    			return encoding != null;
     		}

			/**
			 * @return the encoding
			 */
			public String getEncoding() {
				return encoding;
			}
    };

    private static final boolean[] EBCDIC_CHAR = getTextChars(US_EBCDIC);
    private static final boolean[] ASCII_CHAR = getTextChars(Conversion.DEFAULT_ASCII_CHARSET);
	
	private ICheckFile[] fileChecks = {
     		new VbDumpCheck(),
    		new StandardCheckFile(FileOrganisation.MAINFRAME_VB, US_EBCDIC),
    		new StandardCheckFile(FileOrganisation.FUJITSU_VB),
    		new StandardCheckFile(FileOrganisation.GNU_COBOL_VB),
    		new BinTextCheck(),
//      		new StandardCheckFile(IFileStructureConstants.IO_VBS, US_EBCDIC),
    		new BinTextCheck(EBCDIC_CHAR, US_EBCDIC, 7),
//    		new StandardCheckFile(IFileStructureConstants.IO_MICROFOCUS),
//    		new StandardCheckFile(IFileStructureConstants.IO_UNICODE_TEXT),
    };

	
	private final byte[] data;
	private final EncodingType encodingType;
	private final FileOrganisation fileStructure;
	
	
	public FileAnalysis(String samepleDataFile) throws IOException {
		this(new FileInputStream(samepleDataFile));
	}

	public FileAnalysis(InputStream in) throws IOException {
		byte[] buf = new byte[BUFFER_SIZE];
		int len = readBuffer(in, buf);
		if (len == buf.length) {
			data = buf;
		} else {
			data = new byte[len];
			System.arraycopy(buf, 0, data, 0, len);
		}
		
		this.encodingType = evaluateEncoding(data);
		
		FileOrganisation fileStructure = FileOrganisation.UNNOWN;
		if (len > 100) {
			for (ICheckFile fileCheck : fileChecks) {
				if (fileCheck.check(data)) {
					fileStructure = fileCheck.getFileStructure();
					break;
				}
			}
		}
		this.fileStructure = fileStructure;
	}

	/**
	 * @param buf file buffer
	 * @return
	 */
	protected EncodingType evaluateEncoding(byte[] buf) {
		int len = buf.length;
		EncodingType et = EncodingType.NONE;
		int ebcdicCount = 0, asciiCount = 0;
		for (int i = 0; i < len; i++) {
			int v = TextDetails.toInt(buf[i]);
			if (EBCDIC_CHAR[v]) {
				ebcdicCount += 1;
			}
			if (ASCII_CHAR[v]) {
				asciiCount += 1;
			}
		}
		
		if (ebcdicCount > asciiCount) {
			if (ebcdicCount > 20 && ebcdicCount * 6 > len) {
				et = EncodingType.EBCDIC;
			}
		} else {
			if (asciiCount > 20 && asciiCount * 6 > len) {
				et = EncodingType.ASCII;
			}
		}
		return et;
	}
	
	/**
	 * @return the encodingType
	 */
	public EncodingType getEncodingType() {
		return encodingType;
	}

	/**
	 * @return the fileStructure
	 */
	public FileOrganisation getFileOrganisation() {
		return fileStructure;
	}



	private final int readBuffer(InputStream in, final byte[] buf)
	throws IOException {

		int total = 0;
		int num = in.read(buf, total, buf.length - total);

		while (num >= 0 && total + num < buf.length) {
			total += num;
			num = in.read(buf, total, buf.length - total);
		}

		if (num > 0) {
			total += num;
		}

		return total;
	}
	

	public SingleFieldDecider analyseRecordDecider(LayoutDetail schema) throws IOException {
		RecordDetail rec = schema.getRecord(0);
		int count = Math.min(10, rec.getFieldCount());
		String recordTypeField = null;
		for (int i = 0; i < count; i++) {
			String name = rec.getField(i).getName();
			if (isRecordTypeField(name)) {
				recordTypeField = name;
				break;
			}
		}
		
		//TODO add recordid test
		if (recordTypeField == null) {
			for (int i = 0; i < count; i++) {
				IItemDetails cobolItem = rec.getField(i).getCobolItem();
				if (cobolItem != null && cobolItem.getConditions() != null && cobolItem.getConditions().size() > 0) {
					recordTypeField = rec.getField(i).getName();
					break;
				}
			}
		}
		
		if (recordTypeField == null) {
			throw new RuntimeException("Could not determine the record-type field");
		}
		
		return analyseRecordDecider(schema, recordTypeField);
	}
	
	private boolean isRecordTypeField(String name) {
		StringBuilder b = new StringBuilder(name.length());
		name = name.toLowerCase();
		for (int i = 0; i < name.length(); i++) {
			char c = name.charAt(i);
			if (c == '-' || c == '_') {
				
			} else {
				b.append(c);
			}
		}
		
		return     b.indexOf("recordtype") >= 0 
				|| b.indexOf("rectype") >= 0 
				|| b.indexOf("recordid") >= 0;
	}

	public SingleFieldDecider analyseRecordDecider(LayoutDetail schema, String selectionFieldName) throws IOException {
		ISchemaIOBuilder iobldr = JRecordInterface1.SCHEMA.newIOBuilder(schema);
		
		return toSingleFieldDecider(
				schema, 
				selectionFieldName, 
				getRecordTypeMapping(
						iobldr.newReader(new ByteArrayInputStream(data)), 
						selectionFieldName));
	}
	
	private SingleFieldDecider toSingleFieldDecider(LayoutDetail schema,  String selectionFieldName, List<IKeyRecord> recordSelections) {
		if (recordSelections.size() == 0) { return null;}
		
		boolean allowOthers = false;
		List<RecordTypeAndRecord> selections = new ArrayList<RecordTypeAndRecord>(recordSelections.size());
		
		for (IKeyRecord k : recordSelections) {
			if (k.getRecordNumber() < 0) {
				allowOthers = true;
			} else {
				selections.add(
						new RecordTypeAndRecord(
								k.getRecordSelectionValue(),
								schema.getRecord(k.getRecordNumber()).getRecordName()));
			}
		}
		return new SingleFieldDecider.SmallDecider(selectionFieldName, null, allowOthers, selections);
	}
	
	private List<IKeyRecord> getRecordTypeMapping(AbstractLineReader reader, String selectionFieldName) throws IOException  {
		AbstractLine l;
		HashMap<String, SelectionFieldDetails> fieldMapping = new HashMap<String, SelectionFieldDetails>();
		SelectionFieldDetails sfd;
		
		while((l = reader.read()) != null) {
			String s = l.getFieldValue(selectionFieldName).asString().toLowerCase();
			sfd = fieldMapping.get(s);
			if (sfd == null) {
				sfd = new SelectionFieldDetails(s, reader.getLayout());
				fieldMapping.put(s, sfd);
			}
			sfd.addLineDetails(l);
		}
		reader.close();
		
		
		return new ArrayList<IKeyRecord>(fieldMapping.values());
	}
	
	private static interface IKeyRecord {
		String getRecordSelectionValue();
		int getRecordNumber();
	}
	
	private static class SelectionFieldDetails implements IKeyRecord {
		final String value;
		int[] recordCount;
		//final LayoutDetail schema;
		int recordNumber = Integer.MIN_VALUE;

		public SelectionFieldDetails(String value, LayoutDetail schema) {
			super();
			this.value = value;
			//this.schema = schema;
			this.recordCount = new int[schema.getRecordCount()];
		}
		
		/**
		 * @return the recordNumber
		 */
		@Override
		public int getRecordNumber() {
			if (recordNumber == Integer.MIN_VALUE) {
				int recCount = 0;
				recordNumber = -1;
				
				for (int i = 0; i < recordCount.length; i++) {
					if (recordCount[i] > recCount) {
						recCount = recordCount[i];
						recordNumber = i;
					}					
				}
				
			}
			return recordNumber;
		}
		
		@Override
		public String getRecordSelectionValue() {
			return value;
		}

		@SuppressWarnings("deprecation")
		void addLineDetails(AbstractLine line) {
			double[] ratio = new double[recordCount.length];
			Arrays.fill(ratio, 0);
			String lineAsText = line.getFullLine();

			
			for (int recordNum = 0; recordNum < ratio.length; recordNum++) {
				int totalCount = 0;
				int validCount = 0;
				int end = 0;
				AbstractFieldValue lastFv = null;
				for (AbstractFieldValue fv : line.getFieldIterator(recordNum)) {
					int inc = 2;
					IFieldDetail fieldDetail = fv.getFieldDetail();
					lastFv = fv;
					switch (fieldDetail.getType()) {
					case Type.ftPackedDecimal:
					case Type.ftPackedDecimalPostive:
					case Type.ftPackedDecimalSmall:
					case Type.ftPackedDecimalSmallPostive:
						inc = 11;
						break;
					case Type.ftChar:
						String v = fv.asString();
						int numCount = 0, count = 0;
						for (int j = 0; j < v.length(); j++) {
							char c = v.charAt(j);
							count += 1;
							if (c >= '0' && c <= '9') {
								numCount += 1;
							}
							
						}
						if (count > 0 
						&&  ((count * 10) / fieldDetail.getLen() > 4)) {
							if (((numCount * 10) / count) < 4) {
								validCount += 2;
							}
	
							totalCount += 2;
						}
						inc = 1;
						break;
					default:
						if (fv.isNumeric() && ! fv.isBinary()) {
							inc = 7;
						}
					}
					totalCount += inc;
					if (fv.isValid()) {
						validCount += inc;
					}
					
					int pos = fieldDetail.getPos();
					if (pos > 1 && lineAsText.length() > pos ) {
						totalCount += 1;
						if (lineAsText.charAt(pos-2) == ' ' && lineAsText.charAt(pos-1) != ' ') {
							totalCount += 1;
							validCount += 2;
						}
					}
//					try {
//						System.out.println(fv.isValid() + " " + fv.getFieldDetail().getName() + " >" + fv.asString() + "< "
//								+   validCount);
//					} catch (Exception e) {
//					}
					end = fieldDetail.getEnd();
				}
//				System.out.println("    ----> " 
//						+ line.getLayout().getRecord(recordNum).getRecordName() + " " + value + " "
//						+ validCount + "\t" + totalCount + "\t" + line.getData().length
//						 + " " + end + "\n");
				
				ratio[recordNum] = totalCount > 0 ? (double) validCount / totalCount : 0;
				
				if (line.getData().length == end) {
					String fieldName = lastFv == null ? "" : lastFv.getFieldDetail().getName();
					if (fieldName.length() == 0 || "filler".equalsIgnoreCase(fieldName)) {
						if (lastFv != null && isEmpty(lastFv)) {
							RecordDetail record = line.getLayout().getRecord(recordNum);
							int fieldCount = record.getFieldCount();
							IFieldValue secondLastField = line.getFieldValue(recordNum, fieldCount-2);
							if (fieldCount > 2 && ! isEmpty(secondLastField)) {
								if (secondLastField.isValid()) {
									ratio[recordNum] += 0.21;
								} else {
									ratio[recordNum] += 0.17;
								}
							}
						}
					} else {						
						ratio[recordNum] += 0.2;
					}
				}
			}
			int maxRatioIdx = 0;
			for (int i = 1; i < ratio.length; i++) {
				if (ratio[i] > ratio[maxRatioIdx]) {
					maxRatioIdx = i;
				}
			}
			
			if (ratio[maxRatioIdx] > 0.5) {
				recordCount[maxRatioIdx] += 1;
			}
		}

		/**
		 * @param lastFv
		 * @return
		 */
		protected boolean isEmpty(AbstractFieldValue lastFv) {
			String value = lastFv.asString();
			char[] chars = value.toCharArray();
			int spaceCount = 0;
			for (int i = 0; i < chars.length; i++) {
				if (chars[i] == ' ') {
					spaceCount += 1;
				} else if (chars[i] != '0') {
					return false;
				}
			}
			return value.length() == spaceCount;
		}
	}

	
	private static boolean[] getTextChars(String font) {
		boolean[] isText = new boolean[256];
		Arrays.fill(isText, false);
		try {
			byte[] bytes;
			if (font == null || "".equals(font)) {
				bytes= TextDetails.TEXT_CHARS.getBytes();
			} else {
				bytes= TextDetails.TEXT_CHARS.getBytes(font);
			}
			int j;
			for (int i = 0; i < bytes.length; i++) {
				j = TextDetails.toInt(bytes[i]);
				if (j > 32) {
					isText[j] = true;
				}
	    	}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return isText;
	}

	
//	   private static int toInt(byte b) {
//		   	int i = b;
//		    	if (i < 0) {
//		    		i += 256;
//		    	}
//		    	return i;
//		    }


}
