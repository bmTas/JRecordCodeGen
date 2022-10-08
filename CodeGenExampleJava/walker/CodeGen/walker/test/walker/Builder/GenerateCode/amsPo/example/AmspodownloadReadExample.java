package test.walker.Builder.GenerateCode.amsPo.example;

import java.io.IOException;
import net.sf.JRecord.cgen.def.IReader;
import test.walker.Builder.GenerateCode.amsPo.data.Amspodownload;

public class AmspodownloadReadExample {

	public static void main(String[] args) throws IOException {
       // You need to provide a filename / stream for your cobol data
		IReader<Amspodownload> reader = Amspodownload.getIoProvider().newReader("filename???");
		Amspodownload line;
		
		while ((line = reader.read()) != null) {


		// You need to setup a if / switch statements to determine the Record Type
		// and callthe appropriate process??? method 
			//if (???) {
				processPoRecord(line.poRecord);
			//} else if (???) {
				processProductRecord(line.productRecord);
			//} else if (???) {
				processLocationRecord(line.locationRecord);
			//}
		}
		reader.close();
	}


	private static void processPoRecord(Amspodownload.RecPoRecord poRecord) {

		System.out.println(""
				+ poRecord.recordType.asString() + "\t"
				+ poRecord.sequenceNumber.asBigDecimal() + "\t"
				+ poRecord.vendor.asLong() + "\t"
				+ poRecord.po.asLong() + "\t"
				+ poRecord.entryDate.asInt() + "\t"
				+ poRecord.beg01Code.asString() + "\t"
				+ poRecord.beg02Code.asString() + "\t"
				+ poRecord.department.asInt() + "\t"
				+ poRecord.expectedRecieptDate.asInt() + "\t"
				+ poRecord.cancelByDate.asInt() + "\t"
				+ poRecord.ediType.asString() + "\t"
				+ poRecord.addDate.asInt() + "\t"
				+ poRecord.departmentName.asString() + "\t"
				+ poRecord.prcoessType.asString() + "\t"
				+ poRecord.orderType.asString() + "\t"
		);
	}

	private static void processProductRecord(Amspodownload.RecProductRecord productRecord) {

		System.out.println(""
				+ productRecord.recordType.asString() + "\t"
				+ productRecord.packQty.asBigDecimal() + "\t"
				+ productRecord.packCost.asBigDecimal() + "\t"
				+ productRecord.apn.asLong() + "\t"
				+ productRecord.product.asInt() + "\t"
				+ productRecord.pmgDtlTechKey.asString() + "\t"
				+ productRecord.casePackId.asString() + "\t"
				+ productRecord.productName.asString() + "\t"
		);
	}

	private static void processLocationRecord(Amspodownload.RecLocationRecord locationRecord) {

		System.out.println(""
				+ locationRecord.recordType.asString() + "\t"
				+ locationRecord.dcNumber.get(0).asInt() + "\t"
				+ locationRecord.packQuantity.get(0).asInt() + "\t"
		);
	}
}
