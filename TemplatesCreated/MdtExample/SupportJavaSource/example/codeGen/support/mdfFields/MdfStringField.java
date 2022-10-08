package example.codeGen.support.mdfFields;

import example.codeGen.support.interfaces.ICblField;
import net.sf.JRecord.Details.fieldValue.IFieldValue;

public class MdfStringField extends MdfBaseField implements ICblField<String> {
	
	private final IFieldValue valueField;
	public MdfStringField(IFieldValue mdfField, IFieldValue valueField) {
		super(mdfField);
		
		this.valueField = valueField;
	}

	@Override
	public String get() {
		return valueField.asString();
	}

	@Override
	public void set(String value) {
		valueField.set(value);
	}

}
