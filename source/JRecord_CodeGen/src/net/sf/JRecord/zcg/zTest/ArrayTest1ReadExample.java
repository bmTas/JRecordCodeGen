package net.sf.JRecord.zcg.zTest;

import java.io.FileInputStream;
import java.io.IOException;
import net.sf.JRecord.cgen.def.IReader;

public class ArrayTest1ReadExample {

	public static void main(String[] args) throws IOException {
       // You need to create a stream for you input file
		IReader<ArrayTest1> reader = ArrayTest1.newReader(new FileInputStream("???"));
		ArrayTest1 line;
		
		while ((line = reader.read()) != null) {
			System.out.println(""
				+ line.field1.get(0).asString() + "\t"
				+ line.field2.get(0).asBigDecimal() + "\t"
				+ line.field3.get(0, 0).asInt() + "\t"
				+ line.field4.get(0, 0, 0).asInt() + "\t"
				+ line.field5.get(0, 0, 0).asBigDecimal() + "\t"
				+ line.field6.get(0).asString() + "\t"
			);
		}
		reader.close();
	}
}


