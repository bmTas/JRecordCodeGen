package net.sf.JRecord.zcg.zTest;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.math.BigDecimal;

import org.junit.Test;

import net.sf.JRecord.Details.AbstractLine;

public class TstDtar020 {

	@Test
	public void testAssignGenCode() throws IOException {
		
		DoTst tst = new DoTst();
		
		for (int i = 0; i < 225; i++) {
			tst.assignGenCode(i);
			tst.chk(i);
		}
	}


	@Test
	public void testAssignLine() throws IOException {
		
		DoTst tst = new DoTst();
		
		for (int i = 0; i < 225; i++) {
			tst.assignLine(i);
			tst.chk(i);
		}
	}

	
	static class DoTst {
		
		Dtar020.RecDtar020 dtar020 = Dtar020.newDtar020();
		AbstractLine line;
		
		
		public DoTst() throws IOException {
			line = BldGenClass.getDTAR020bldr().newLine(dtar020.getData());
		}
		
		void assignGenCode(int id) {
			dtar020.keycodeNo.set("" + id);
			dtar020.storeNo.set(id);
			dtar020.date.set(id);
			dtar020.deptNo.set(id);
			dtar020.qtySold.set(id);
			dtar020.salePrice.set(genPrice(id));
		}
		
		void assignLine(int id) {
			line.getFieldValue("DTAR020-KEYCODE-NO").set("" + id);
			line.getFieldValue("DTAR020-STORE-NO").set(id);
			line.getFieldValue("DTAR020-DATE").set(id);
			line.getFieldValue("DTAR020-DEPT-NO").set(id);
			line.getFieldValue("DTAR020-QTY-SOLD").set(id);
			line.getFieldValue("DTAR020-SALE-PRICE").set(genPrice(id));
		}
		
		void chk(int id) {
			String idStr = "" + id;
			BigDecimal priceId = genPrice(id);

			assertEquals(idStr, idStr, dtar020.keycodeNo.asString());
			assertEquals(idStr, id, dtar020.storeNo.asInt());
			assertEquals(idStr, id, dtar020.date.asInt());
			assertEquals(idStr, id, dtar020.deptNo.asInt());
			assertEquals(idStr, id, dtar020.qtySold.asInt());
			assertEquals(idStr, priceId, dtar020.salePrice.asBigDecimal());

			assertEquals(idStr, idStr, line.getFieldValue("DTAR020-KEYCODE-NO").asString());
			assertEquals(idStr, id, line.getFieldValue("DTAR020-STORE-NO").asInt());
			assertEquals(idStr, id, line.getFieldValue("DTAR020-DATE").asInt());
			assertEquals(idStr, id, line.getFieldValue("DTAR020-DEPT-NO").asInt());
			assertEquals(idStr, id, line.getFieldValue("DTAR020-QTY-SOLD").asInt());
			assertEquals(idStr, priceId, line.getFieldValue("DTAR020-SALE-PRICE").asBigDecimal());

		}

		/**
		 * @param id
		 * @return
		 */
		public BigDecimal genPrice(int id) {
			String s = id + ".0" + (id % 10);
			return new BigDecimal(s);
		}
	}
}
