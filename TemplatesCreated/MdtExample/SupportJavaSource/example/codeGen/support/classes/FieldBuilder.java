package example.codeGen.support.classes;

import java.math.BigDecimal;

import example.codeGen.support.interfaces.ICblField;
import example.codeGen.support.interfaces.ICblFieldLong;
import example.codeGen.support.mdfFields.MdfStringField;
import net.sf.JRecord.Common.IFieldDetail;
import net.sf.JRecord.Details.AbstractLine;

/**
 * 
 * This class will build / create fields for
 * the generated code. The idea is to keep the
 * velocity-skeltons as simple as possible and keep
 * the logic in the support classes
 * 
 * This a skelton builder for MDF Fields. You no
 * @author Bruce Martin
 *
 */
public class FieldBuilder {

	IFieldDetail mdfField, field;
	
	private final AbstractLine line;
	
	
	public FieldBuilder(AbstractLine line) {
		this.line = line;
	}
	
//	public void setFields(IFieldDetail field) {
//		this.mtfField = null;
//		this.field = field;
//	}
//	
	
	public FieldBuilder setFields(IFieldDetail mdfField, IFieldDetail field) {
		this.mdfField = mdfField;
		this.field = field;
		return this;
	}

	public ICblField<String> stringField() {
		return new MdfStringField(
				mdfField == null ? null : line.getFieldValue(mdfField),
				line.getFieldValue(field));
	}
	

	public ICblField<BigDecimal> decimalField() {
		//TODO implement decimal field
		return null;
	}
	

	public ICblFieldLong longField() {
		//TODO implement long field
		return null;
	}
}
