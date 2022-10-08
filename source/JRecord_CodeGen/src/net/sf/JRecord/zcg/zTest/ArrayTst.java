package net.sf.JRecord.zcg.zTest;

import net.sf.JRecord.ByteIO.AbstractByteReader;
import net.sf.JRecord.ByteIO.AbstractByteWriter;
import net.sf.JRecord.ByteIO.ByteIOProvider;
import net.sf.JRecord.Common.BasicFileSchema;
import net.sf.JRecord.Common.IBasicFileSchema;
import net.sf.JRecord.Common.IFileStructureConstants;
import net.sf.JRecord.Details.IGetByteData;
import net.sf.JRecord.Details.fieldValue.IDecimalField;
import net.sf.JRecord.Details.fieldValue.IIntField;
import net.sf.JRecord.Details.fieldValue.IStringField;
import net.sf.JRecord.Types.Type;
import net.sf.JRecord.cgen.def.array.IArrayField1Dimension;
import net.sf.JRecord.cgen.def.array.IArrayField2Dimension;
import net.sf.JRecord.cgen.def.array.IArrayField3Dimension;
import net.sf.JRecord.cgen.impl.array.ArrayFieldValue;
import net.sf.JRecord.cgen.support.ArrayGenField;

public class ArrayTst {

	public static RecArrayTst newArrayTst() { return new RecArrayTst();}

	private static IBasicFileSchema createBasicSchema() {
		return BasicFileSchema.newFixedSchema(IFileStructureConstants.IO_FIXED_LENGTH_RECORDS, true, 1491, "cp037");
	}
	public static  AbstractByteReader newReader() {
		return ByteIOProvider.getInstance().getByteReader(createBasicSchema());
	}
	public static  AbstractByteWriter newWriter() {
		return ByteIOProvider.getInstance().getByteWriter(createBasicSchema());
	}

	public static class RecArrayTst implements IGetByteData {

		public byte[] cobolData = new byte[1491];

		@Override public byte[] getData() {
			return cobolData;
		}

		@Override public void setData(byte[] data) {
			this.cobolData = data;
		}

		public final IArrayField1Dimension<IStringField> field1 = new ArrayFieldValue.StringArrayField(new ArrayGenField("field-1", 1, 3, Type.ftChar, 0, "cp037", new ArrayGenField.ArraySizeDef(6, 213)), this);
		public final IArrayField1Dimension<IDecimalField> field2 = new ArrayFieldValue.DecimalArrayField(new ArrayGenField("field-2", 4, 7, 150, 3, "cp037", new ArrayGenField.ArraySizeDef(6, 213)), this);
		public final IArrayField2Dimension<IIntField> field3 = new ArrayFieldValue.IntArrayField(new ArrayGenField("field-3", 11, 4, Type.ftPackedDecimal, 0, "cp037", new ArrayGenField.ArraySizeDef(6, 213), new ArrayGenField.ArraySizeDef(4, 40)), this);
		public final IArrayField3Dimension<IIntField> field4 = new ArrayFieldValue.IntArrayField(new ArrayGenField("field-4", 15, 4, Type.ftBinaryBigEndian        , 0, "cp037", new ArrayGenField.ArraySizeDef(6, 213), new ArrayGenField.ArraySizeDef(4, 40), new ArrayGenField.ArraySizeDef(3, 4)), this);
		public final IArrayField3Dimension<IDecimalField> field5 = new ArrayFieldValue.DecimalArrayField(new ArrayGenField("field-5", 31, 5, Type.ftPackedDecimal, 2, "cp037", new ArrayGenField.ArraySizeDef(6, 213), new ArrayGenField.ArraySizeDef(4, 40), new ArrayGenField.ArraySizeDef(3, 5)), this);
		public final IArrayField1Dimension<IStringField> field6 = new ArrayFieldValue.StringArrayField(new ArrayGenField("field-6", 211, 3, Type.ftChar, 0, "cp037", new ArrayGenField.ArraySizeDef(6, 213)), this);
	}

}
