package example.codeGen.support.mdfFields;

import net.sf.JRecord.Details.fieldValue.IFieldValue;

public class MdfBaseField {
	private final IFieldValue mdfField;

	public MdfBaseField(IFieldValue mdfField) {
		super();
		this.mdfField = mdfField;
	}
	
	public boolean isPresent() {
		return mdfField == null || ! mdfField.isLowValues(); 
	}
	
	
	public void setPresent(boolean isPresent) {
		if (mdfField != null) {
			mdfField.setHex(isPresent ? "01" : "00");
		}
	}

}
