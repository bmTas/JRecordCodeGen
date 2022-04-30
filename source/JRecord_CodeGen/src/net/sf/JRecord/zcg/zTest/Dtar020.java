package net.sf.JRecord.zcg.zTest;

import net.sf.JRecord.ByteIO.AbstractByteReader;
import net.sf.JRecord.ByteIO.AbstractByteWriter;
import net.sf.JRecord.ByteIO.ByteIOProvider;
import net.sf.JRecord.Common.BasicFileSchema;
import net.sf.JRecord.Common.FieldDetail;
import net.sf.JRecord.Common.IBasicFileSchema;
import net.sf.JRecord.Common.IFileStructureConstants;
import net.sf.JRecord.Details.IGetByteData;
import net.sf.JRecord.Details.fieldValue.IDecimalField;
import net.sf.JRecord.Details.fieldValue.IIntField;
import net.sf.JRecord.Details.fieldValue.IStringField;
import net.sf.JRecord.Types.Type;
import net.sf.JRecord.cgen.impl.fields.FieldValueCG;

public class Dtar020 {

	public static RecDtar020 newDtar020() { return new RecDtar020();}

	private static IBasicFileSchema createBasicSchema() {
		return BasicFileSchema.newFixedSchema(IFileStructureConstants.IO_FIXED_LENGTH_RECORDS, true, 27, "cp037");
	}
	public static  AbstractByteReader newReader() {
		return ByteIOProvider.getInstance().getByteReader(createBasicSchema());
	}
	public static  AbstractByteWriter newWriter() {
		return ByteIOProvider.getInstance().getByteWriter(createBasicSchema());
	}

	public static class RecDtar020 implements IGetByteData {

		public byte[] cobolData = new byte[27];

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

}
