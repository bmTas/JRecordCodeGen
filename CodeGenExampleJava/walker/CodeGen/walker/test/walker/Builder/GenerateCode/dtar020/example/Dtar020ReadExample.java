package test.walker.Builder.GenerateCode.dtar020.example;

import java.io.IOException;
import net.sf.JRecord.cgen.def.IReader;
import test.walker.Builder.GenerateCode.dtar020.data.Dtar020;

public class Dtar020ReadExample {

	public static void main(String[] args) throws IOException {
       // You need provide a file/stream for your input file
		IReader<Dtar020> reader = Dtar020.getIoProvider().newReader("filename???");
		Dtar020 line;
		
		while ((line = reader.read()) != null) {
			System.out.println(""
				+ line.keycodeNo.asString() + "\t"
				+ line.storeNo.asInt() + "\t"
				+ line.date.asInt() + "\t"
				+ line.deptNo.asInt() + "\t"
				+ line.qtySold.asInt() + "\t"
				+ line.salePrice.asBigDecimal() + "\t"
			);
		}
		reader.close();
	}
}

