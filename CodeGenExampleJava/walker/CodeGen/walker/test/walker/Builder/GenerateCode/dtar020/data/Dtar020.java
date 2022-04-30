package test.walker.Builder.GenerateCode.dtar020.data;

import net.sf.JRecord.Common.BasicFileSchema;
import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Common.FieldDetail;
import net.sf.JRecord.Details.IGetByteData;
import net.sf.JRecord.Details.fieldValue.IDecimalField;
import net.sf.JRecord.Details.fieldValue.IIntField;
import net.sf.JRecord.Details.fieldValue.IStringField;
import net.sf.JRecord.Types.Type;
import net.sf.JRecord.cgen.def.IDeserializer;
import net.sf.JRecord.cgen.impl.array.ArrayFieldValue;
import net.sf.JRecord.cgen.impl.fields.FieldValueCG;
import net.sf.JRecord.cgen.impl.io.IoProvider;


public class Dtar020 implements IGetByteData {


	private static IoProvider<Dtar020, Dtar020> provider = new IoProvider<Dtar020, Dtar020>(
			BasicFileSchema.newFixedSchema(Constants.IO_FIXED_LENGTH_RECORDS, true, 27, "cp037"), 
			new IDeserializer<Dtar020>() {
				@Override public Dtar020 deserialize(byte[] rec) { return new Dtar020(rec); }
			});
	
	public static IoProvider<Dtar020, Dtar020> getIoProvider() { return provider;};


	private byte[] cobolData;

	public Dtar020() {
		cobolData = new byte[27];
	}

	public Dtar020(byte[] cobolData) {
		this.cobolData = cobolData;
	}

		@Override public byte[] getData() {
			return cobolData;
		}

		@Override public void setData(byte[] data) {
			this.cobolData = data;
		}

	public final IStringField keycodeNo = new FieldValueCG(this, FieldDetail.newFixedWidthField("DTAR020-KEYCODE-NO", Type.ftChar, 1, 8, 0, "cp037"));
	public final IIntField storeNo = new FieldValueCG(this, FieldDetail.newFixedWidthField("DTAR020-STORE-NO", Type.ftPackedDecimal, 9, 2, 0, "cp037"));
	public final IIntField date = new FieldValueCG(this, FieldDetail.newFixedWidthField("DTAR020-DATE", Type.ftPackedDecimal, 11, 4, 0, "cp037"));
	public final IIntField deptNo = new FieldValueCG(this, FieldDetail.newFixedWidthField("DTAR020-DEPT-NO", Type.ftPackedDecimal, 15, 2, 0, "cp037"));
	public final IIntField qtySold = new FieldValueCG(this, FieldDetail.newFixedWidthField("DTAR020-QTY-SOLD", Type.ftPackedDecimal, 17, 5, 0, "cp037"));
	public final IDecimalField salePrice = new FieldValueCG(this, FieldDetail.newFixedWidthField("DTAR020-SALE-PRICE", Type.ftPackedDecimal, 22, 6, 2, "cp037"));
}
