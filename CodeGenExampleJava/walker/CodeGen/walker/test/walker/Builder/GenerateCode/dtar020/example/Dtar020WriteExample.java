package test.walker.Builder.GenerateCode.dtar020.example;

import java.io.IOException;
import java.math.BigDecimal;
import net.sf.JRecord.cgen.def.IWriter;
import test.walker.Builder.GenerateCode.dtar020.data.Dtar020;

public class Dtar020WriteExample {

	public static void main(String[] args) throws IOException {

		Dtar020 line = new Dtar020();
       // You need to create a stream for you output file
		IWriter<Dtar020> writer = Dtar020.getIoProvider().newWriter("filename???");

		for (int i = 0; i < 5; i++) {
			line.keycodeNo.set("" + i);
			line.storeNo.set(i);
			line.date.set(i);
			line.deptNo.set(i);
			line.qtySold.set(i);
			line.salePrice.set(BigDecimal.valueOf(i));
			writer.write(line);
		}
		writer.close();
	}
}

