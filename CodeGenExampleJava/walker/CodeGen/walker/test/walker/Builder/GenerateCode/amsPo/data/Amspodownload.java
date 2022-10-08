package test.walker.Builder.GenerateCode.amsPo.data;

import net.sf.JRecord.Common.BasicFileSchema;
import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Common.FieldDetail;
import net.sf.JRecord.Details.IGetByteData;
import net.sf.JRecord.Details.fieldValue.IDecimalField;
import net.sf.JRecord.Details.fieldValue.IIntField;
import net.sf.JRecord.Details.fieldValue.ILongField;
import net.sf.JRecord.Details.fieldValue.IStringField;
import net.sf.JRecord.Types.Type;
import net.sf.JRecord.cgen.def.IDeserializer;
import net.sf.JRecord.cgen.def.array.IArrayField;
import net.sf.JRecord.cgen.def.array.IArrayField1Dimension;
import net.sf.JRecord.cgen.def.array.IArrayField2Dimension;
import net.sf.JRecord.cgen.def.array.IArrayField3Dimension;
import net.sf.JRecord.cgen.def.array.IArrayFieldGeneric;
import net.sf.JRecord.cgen.impl.array.ArrayFieldValue;
import net.sf.JRecord.cgen.impl.fields.FieldValueCG;
import net.sf.JRecord.cgen.impl.io.IoProvider;
import net.sf.JRecord.cgen.support.ArrayGenField;


public class Amspodownload {


	private static IoProvider<Amspodownload, IAmspodownloadChildren> provider = new IoProvider<Amspodownload, IAmspodownloadChildren>(
			BasicFileSchema.newFixedSchema(Constants.IO_BIN_TEXT, true, 151, "cp1252"), 
			new IDeserializer<Amspodownload>() {
				@Override public Amspodownload deserialize(byte[] rec) { return new Amspodownload(rec); }
			});
	
	public static IoProvider<Amspodownload, IAmspodownloadChildren> getIoProvider() { return provider;};

	public final RecPoRecord poRecord;
	public final RecProductRecord productRecord;
	public final RecLocationRecord locationRecord;


	public Amspodownload() {
		poRecord = new RecPoRecord();
		productRecord = new RecProductRecord();
		locationRecord = new RecLocationRecord();

	}


	public Amspodownload(byte[] data) {
		poRecord = new RecPoRecord(data);
		productRecord = new RecProductRecord(data);
		locationRecord = new RecLocationRecord(data);

	}

	public static interface IAmspodownloadChildren extends IGetByteData {}
	public static class RecPoRecord implements IAmspodownloadChildren {


		private byte[] cobolData = new byte[88];


		public RecPoRecord (){}

		public RecPoRecord (byte[] cobolData){
			this.cobolData = cobolData;
		}

		@Override public byte[] getData() {
			return cobolData;
		}

		@Override public void setData(byte[] data) {
			this.cobolData = data;
		}

		public final IStringField recordType = new FieldValueCG(this, FieldDetail.newFixedWidthField("Record-Type", Type.ftChar, 1, 2, 0, "cp1252"));
		public final IDecimalField sequenceNumber = new FieldValueCG(this, FieldDetail.newFixedWidthField("Sequence-Number", Type.ftAssumedDecimalPositive, 3, 5, 3, "cp1252"));
		public final ILongField vendor = new FieldValueCG(this, FieldDetail.newFixedWidthField("Vendor", Type.ftNumZeroPaddedPositive, 8, 10, 0, "cp1252"));
		public final ILongField po = new FieldValueCG(this, FieldDetail.newFixedWidthField("PO", Type.ftNumZeroPaddedPositive, 18, 12, 0, "cp1252"));
		public final IIntField entryDate = new FieldValueCG(this, FieldDetail.newFixedWidthField("Entry-Date", Type.ftNumZeroPaddedPositive, 30, 6, 0, "cp1252"));
		public final IStringField beg01Code = new FieldValueCG(this, FieldDetail.newFixedWidthField("beg01-code", Type.ftChar, 44, 2, 0, "cp1252"));
		public final IStringField beg02Code = new FieldValueCG(this, FieldDetail.newFixedWidthField("beg02-code", Type.ftChar, 46, 2, 0, "cp1252"));
		public final IIntField department = new FieldValueCG(this, FieldDetail.newFixedWidthField("Department", Type.ftNumZeroPaddedPositive, 48, 4, 0, "cp1252"));
		public final IIntField expectedRecieptDate = new FieldValueCG(this, FieldDetail.newFixedWidthField("Expected-Reciept-Date", Type.ftNumZeroPaddedPositive, 52, 6, 0, "cp1252"));
		public final IIntField cancelByDate = new FieldValueCG(this, FieldDetail.newFixedWidthField("Cancel-by-date", Type.ftNumZeroPaddedPositive, 62, 6, 0, "cp1252"));
		public final IStringField ediType = new FieldValueCG(this, FieldDetail.newFixedWidthField("EDI-Type", Type.ftChar, 68, 1, 0, "cp1252"));
		public final IIntField addDate = new FieldValueCG(this, FieldDetail.newFixedWidthField("Add-Date", Type.ftNumZeroPaddedPositive, 69, 6, 0, "cp1252"));
		public final IStringField departmentName = new FieldValueCG(this, FieldDetail.newFixedWidthField("Department-Name", Type.ftChar, 76, 10, 0, "cp1252"));
		public final IStringField prcoessType = new FieldValueCG(this, FieldDetail.newFixedWidthField("Prcoess-Type", Type.ftChar, 86, 1, 0, "cp1252"));
		public final IStringField orderType = new FieldValueCG(this, FieldDetail.newFixedWidthField("Order-Type", Type.ftChar, 87, 2, 0, "cp1252"));
	}

	public static class RecProductRecord implements IAmspodownloadChildren {


		private byte[] cobolData = new byte[151];


		public RecProductRecord (){}

		public RecProductRecord (byte[] cobolData){
			this.cobolData = cobolData;
		}

		@Override public byte[] getData() {
			return cobolData;
		}

		@Override public void setData(byte[] data) {
			this.cobolData = data;
		}

		public final IStringField recordType = new FieldValueCG(this, FieldDetail.newFixedWidthField("Record-Type", Type.ftChar, 1, 2, 0, "cp1252"));
		public final IDecimalField packQty = new FieldValueCG(this, FieldDetail.newFixedWidthField("Pack-Qty", Type.ftAssumedDecimalPositive, 3, 9, 4, "cp1252"));
		public final IDecimalField packCost = new FieldValueCG(this, FieldDetail.newFixedWidthField("Pack-Cost", Type.ftAssumedDecimalPositive, 12, 13, 4, "cp1252"));
		public final ILongField apn = new FieldValueCG(this, FieldDetail.newFixedWidthField("APN", Type.ftNumZeroPaddedPositive, 25, 13, 0, "cp1252"));
		public final IIntField product = new FieldValueCG(this, FieldDetail.newFixedWidthField("Product", Type.ftNumZeroPaddedPositive, 39, 8, 0, "cp1252"));
		public final IStringField pmgDtlTechKey = new FieldValueCG(this, FieldDetail.newFixedWidthField("pmg-dtl-tech-key", Type.ftChar, 72, 15, 0, "cp1252"));
		public final IStringField casePackId = new FieldValueCG(this, FieldDetail.newFixedWidthField("Case-Pack-id", Type.ftChar, 87, 15, 0, "cp1252"));
		public final IStringField productName = new FieldValueCG(this, FieldDetail.newFixedWidthField("Product-Name", Type.ftChar, 102, 50, 0, "cp1252"));
	}

	public static class RecLocationRecord implements IAmspodownloadChildren {


		private byte[] cobolData = new byte[122];


		public RecLocationRecord (){}

		public RecLocationRecord (byte[] cobolData){
			this.cobolData = cobolData;
		}

		@Override public byte[] getData() {
			return cobolData;
		}

		@Override public void setData(byte[] data) {
			this.cobolData = data;
		}

		public final IStringField recordType = new FieldValueCG(this, FieldDetail.newFixedWidthField("Record-Type", Type.ftChar, 1, 2, 0, "cp1252"));
		public final IArrayField1Dimension<IIntField> dcNumber = new ArrayFieldValue.IntArrayField(new ArrayGenField("DC-Number", 3, 4, Type.ftNumZeroPaddedPositive, 0, "cp1252", new ArrayGenField.ArraySizeDef(9, 12)), this);
		public final IArrayField1Dimension<IIntField> packQuantity = new ArrayFieldValue.IntArrayField(new ArrayGenField("Pack-Quantity", 7, 8, Type.ftNumZeroPaddedPositive, 0, "cp1252", new ArrayGenField.ArraySizeDef(9, 12)), this);
	}

}
