package example.codeGen.support.classes;
/**
 * 
 * This class shows how to `expand` compressed data; to compress 
 * do the reverse;
 * 
 */
import java.util.Comparator;
import java.util.List;

import net.sf.JRecord.Common.FieldDetail;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.RecordDetail;

public class CompressedData {

	public static final byte NOT_PRESSENT = 0; // assuming it is hex zero and not the character '0'
	public static final Comparator<FieldDetail> comparator = new Comparator<FieldDetail>() {

		@Override public int compare(FieldDetail o1, FieldDetail o2) {
			return o1.getPos() - o2.getPos();
		}
	};
	
	private final List<FieldDetail> fields;
	private int expandedLength;
	public CompressedData(LayoutDetail layout) {
		RecordDetail record = layout.getRecord(0); // assuming it is first record we want
		expandedLength = record.getMinumumPossibleLength();
		fields = record.getFields();
		fields.sort(comparator);
	}
	
	/**
	 * Expand the compressed mainframe data
	 * 
	 * @param data compressed data
	 * @return expanded Cobol record
	 */
	public byte[] expand(byte data[]) {
		byte[] expanded = new byte[expandedLength];
		boolean processField = true;
		int pos = 0;
		
		for (FieldDetail field : fields) {
			if (pos >= data.length) { break; }
			byte test = data[pos];
			if (processField) {
				System.arraycopy(data, pos, expanded, field.getPos() - 1, field.getLen());
				pos += field.getLen();
			}
			processField = ! (test == NOT_PRESSENT && field.getName().toUpperCase().endsWith("-MDT"));
		}
		return expanded;
	}
	
	// TODO implement a compress
}
