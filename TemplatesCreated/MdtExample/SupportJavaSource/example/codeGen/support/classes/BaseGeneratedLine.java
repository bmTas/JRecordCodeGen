package example.codeGen.support.classes;

import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.Line;

/**
 * This is will serve as a Base Class to the Generated
 * Cobol classes. 
 * 
 * The idea is to keep the elocity-skeltons as simple as possible and keep
 *  the logic in the support classes
 *  
 *  @author Bruce Martin
 *
 */
public class BaseGeneratedLine {

	protected final Line line;
	protected final LayoutDetail schema;
	private final CompressedData compress;
	
	public BaseGeneratedLine(LayoutDetail schema) {
		this.line = new Line(schema);
		this.schema = schema;
		this.compress = new CompressedData(schema);
	}
	
	
	public byte[] getData() {
		return line.getData();
	}
	
	//TODO add getCompressedData method !!!
	
	public void setCompressedData(byte[] data) {
		line.setData(compress.expand(data));
	}

	public void setData(byte[] data) {
		line.setData(data);
	}
}
