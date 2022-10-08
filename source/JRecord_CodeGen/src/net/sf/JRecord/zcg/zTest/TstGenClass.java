package net.sf.JRecord.zcg.zTest;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.math.BigDecimal;

import org.junit.Test;

import net.sf.JRecord.Details.AbstractLine;

public class TstGenClass {


	@Test
	public void testSetGenClass() throws IOException {
		
		DoCheck chk = new DoCheck();
		
		for (chk.idx1 = 0; chk.idx1 < chk.arrayTst.field1.getArrayLength(0); chk.idx1++) {
			chk.arrayTst.field1.get(chk.idx1).set(chk.idx1);
			BigDecimal field1Val = new BigDecimal("10" + chk.idx1 + "." + chk.idx1 + "00");
			chk.arrayTst.field2.get(chk.idx1).set(field1Val);
			chk.arrayTst.field6.get(chk.idx1).set(chk.idx1);
			
			chk.tstLevel1(field1Val);
			
			
			for (chk.idx2 = 0; chk.idx2 < 5; chk.idx2++) {
				int id = 10 * (chk.idx1 + 1) + chk.idx2;
				chk.arrayTst.field3.get(chk.idx1, chk.idx2).set(id);
				
				chk.tstLevel2(id);
				
				for (chk.idx3 = 0; chk.idx3< 4; chk.idx3++) {
					int id3 = id * 10 + chk.idx3;
					BigDecimal idBd = new BigDecimal(id3 + "." + chk.idx3 + "" + chk.idx2);
					chk.arrayTst.field4.get(chk.idx1, chk.idx2, chk.idx3).set(id3);
					chk.arrayTst.field5.get(chk.idx1, chk.idx2, chk.idx3).set(idBd);
					
					chk.tstLevel3(id3, idBd);
				}
			}
		}
	}


	@Test
	public void testSetLine() throws IOException {
		
		DoCheck chk = new DoCheck();
		
		for (chk.idx1 = 0; chk.idx1 < chk.arrayTst.field1.getArrayLength(0); chk.idx1++) {
			BigDecimal field1Val = new BigDecimal("10" + chk.idx1 + "." + chk.idx1 + "00");
			
			chk.line.getFieldValue("field-1 (" + chk.idx1 + ")").set(chk.idx1);
			chk.line.getFieldValue("field-2 (" + chk.idx1 + ")").set(field1Val);
			chk.line.getFieldValue("field-6 (" + chk.idx1 + ")").set(chk.idx1);
			
			chk.tstLevel1(field1Val);
			
			
			for (chk.idx2 = 0; chk.idx2 < 5; chk.idx2++) {
				int id = 10 * (chk.idx1 + 1) + chk.idx2;
				chk.line.getFieldValue("field-3 (" + chk.idx1 + ", " + chk. idx2 + ")").set(id);
				
				chk.tstLevel2(id);
				
				for (chk.idx3 = 0; chk.idx3< 4; chk.idx3++) {
					int id3 = id * 10 + chk.idx3;
					BigDecimal idBd = new BigDecimal(id3 + "." + chk.idx3 + "" + chk.idx2);
					
					chk.line.getFieldValue("field-4 (" + chk.idx1 + ", " + chk.idx2 + ", " + chk.idx3 + ")").set(id3);
					chk.line.getFieldValue("field-5 (" + chk.idx1 + ", " + chk.idx2 + ", " + chk.idx3 + ")").set(idBd);
					
					chk.tstLevel3(id3, idBd);
				}
			}
		}
	}

	
	private static class DoCheck {
		int idx1, idx2, idx3;
		ArrayTst.RecArrayTst arrayTst = ArrayTst.newArrayTst();
		
		AbstractLine line;
		
		public DoCheck() throws IOException {
			line = BldGenClass.getCobolBldr().newLine(arrayTst.getData());
		}
		
		private void tstLevel1(BigDecimal field1Val) {
			String msg = "" + idx1;
			assertEquals(msg, msg, arrayTst.field1.get(idx1).asString());
			assertEquals(msg, msg, line.getFieldValue("field-1 (" + idx1 + ")").asString());

			assertEquals(msg, field1Val, arrayTst.field2.get(idx1).asBigDecimal());
			assertEquals(msg, field1Val, line.getFieldValue("field-2 (" + idx1 + ")").asBigDecimal());
			
			assertEquals(msg, msg, arrayTst.field6.get(idx1).asString());
			assertEquals(msg, msg, line.getFieldValue("field-6 (" + idx1 + ")").asString());
			
		}
		
		private void tstLevel2(int id) {
			String msg = "" + id;
			assertEquals(msg, id, arrayTst.field3.get(idx1, idx2).asInt());
			assertEquals(msg, id, line.getFieldValue("field-3 (" + idx1 + ", " + idx2 + ")").asInt());
			
		}
		
		private void tstLevel3(int id3, BigDecimal idBd) {
			
			String msg = id3 + " " + idBd;
			assertEquals(msg, id3, arrayTst.field4.get(idx1, idx2, idx3).asInt());
			assertEquals(msg, id3, line.getFieldValue("field-4 (" + idx1 + ", " + idx2 + ", " + idx3 + ")").asInt());
			
			assertEquals(msg, idBd, arrayTst.field5.get(idx1, idx2, idx3).asBigDecimal());
			assertEquals(msg, idBd, line.getFieldValue("field-5 (" + idx1 + ", " + idx2 + ", " + idx3 + ")").asBigDecimal());
		}
	}
}
