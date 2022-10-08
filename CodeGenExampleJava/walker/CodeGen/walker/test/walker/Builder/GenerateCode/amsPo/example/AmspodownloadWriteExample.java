package test.walker.Builder.GenerateCode.amsPo.example;

import java.io.IOException;
import java.math.BigDecimal;
import net.sf.JRecord.cgen.def.IWriter;
import test.walker.Builder.GenerateCode.amsPo.data.Amspodownload;

public class AmspodownloadWriteExample {

	public static void main(String[] args) throws IOException {

       // You need to create a stream for you output file
		IWriter<Amspodownload.IAmspodownloadChildren> writer = Amspodownload.getIoProvider().newWriter("filename???");

		for (int i = 0; i < 5; i++) {
			writer.write(generatePoRecord(i));
			writer.write(generateProductRecord(i));
			writer.write(generateLocationRecord(i));
		}
		writer.close();
	}


	private static Amspodownload.RecPoRecord generatePoRecord(int i) {
		Amspodownload.RecPoRecord poRecord = new Amspodownload.RecPoRecord();

		poRecord.recordType.set("" + i);
		poRecord.sequenceNumber.set(BigDecimal.valueOf(i));
		poRecord.vendor.set(i);
		poRecord.po.set(i);
		poRecord.entryDate.set(i);
		poRecord.beg01Code.set("" + i);
		poRecord.beg02Code.set("" + i);
		poRecord.department.set(i);
		poRecord.expectedRecieptDate.set(i);
		poRecord.cancelByDate.set(i);
		poRecord.ediType.set("" + i);
		poRecord.addDate.set(i);
		poRecord.departmentName.set("" + i);
		poRecord.prcoessType.set("" + i);
		poRecord.orderType.set("" + i);

		return poRecord;
	}

	private static Amspodownload.RecProductRecord generateProductRecord(int i) {
		Amspodownload.RecProductRecord productRecord = new Amspodownload.RecProductRecord();

		productRecord.recordType.set("" + i);
		productRecord.packQty.set(BigDecimal.valueOf(i));
		productRecord.packCost.set(BigDecimal.valueOf(i));
		productRecord.apn.set(i);
		productRecord.product.set(i);
		productRecord.pmgDtlTechKey.set("" + i);
		productRecord.casePackId.set("" + i);
		productRecord.productName.set("" + i);

		return productRecord;
	}

	private static Amspodownload.RecLocationRecord generateLocationRecord(int i) {
		Amspodownload.RecLocationRecord locationRecord = new Amspodownload.RecLocationRecord();

		locationRecord.recordType.set("" + i);
		locationRecord.dcNumber.get(0).set(i);
		locationRecord.packQuantity.get(0).set(i);

		return locationRecord;
	}
}

