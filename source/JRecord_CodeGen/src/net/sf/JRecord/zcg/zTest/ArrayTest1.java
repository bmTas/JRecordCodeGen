package net.sf.JRecord.zcg.zTest;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import net.sf.JRecord.Common.BasicFileSchema;
import net.sf.JRecord.Common.IBasicFileSchema;
import net.sf.JRecord.Common.IFileStructureConstants;
import net.sf.JRecord.Details.IGetByteData;
import net.sf.JRecord.Details.fieldValue.IDecimalField;
import net.sf.JRecord.Details.fieldValue.IIntField;
import net.sf.JRecord.Details.fieldValue.IStringField;
import net.sf.JRecord.Types.Type;
import net.sf.JRecord.cgen.def.IDeserializer;
import net.sf.JRecord.cgen.def.IReader;
import net.sf.JRecord.cgen.def.IWriter;
import net.sf.JRecord.cgen.def.array.IArrayField1Dimension;
import net.sf.JRecord.cgen.def.array.IArrayField2Dimension;
import net.sf.JRecord.cgen.def.array.IArrayField3Dimension;
import net.sf.JRecord.cgen.impl.array.ArrayFieldValue;
import net.sf.JRecord.cgen.impl.io.ReadFromBytes;
import net.sf.JRecord.cgen.impl.io.WriteAsBytes;
import net.sf.JRecord.cgen.support.ArrayGenField;


public class ArrayTest1 implements IGetByteData {


	private byte[] cobolData;

	public ArrayTest1() {
		cobolData = new byte[1491];
	}

	public ArrayTest1(byte[] cobolData) {
		this.cobolData = cobolData;
	}

	private static IBasicFileSchema createBasicSchema() {
		return BasicFileSchema.newFixedSchema(IFileStructureConstants.IO_FIXED_LENGTH_RECORDS, true, 1491, "cp037");
	}
	public static  IReader<ArrayTest1> newReader(InputStream in) throws IOException {
		return ReadFromBytes.newReader(createBasicSchema(), new IDeserializer<ArrayTest1>() {
			@Override public ArrayTest1 deserialize(byte[] rec) {
				return new ArrayTest1(rec);
			}
		},
		in);
	}


	public static IWriter<IGetByteData> newWriter(OutputStream out) throws IOException {
		return WriteAsBytes.newGetBytesWriter(createBasicSchema(), out);
	}

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

