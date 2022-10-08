package net.sf.JRecord.zcg.zTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import net.sf.JRecord.JRecordInterface1;
import net.sf.JRecord.Common.IFieldDetail;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Types.Type;
import net.sf.JRecord.cg.schema.ArrayDetails;
import net.sf.JRecord.cg.schema.FieldDef;
import net.sf.JRecord.cg.schema.LayoutDef;
import net.sf.JRecord.cgen.support.ArrayGenField;
import net.sf.JRecord.def.IO.builders.ICobolIOBuilder;

public class TstArrayGenField {

	String array1Dim = "        03 array-1        pic x(3) occurs 7.";
	String array1Dim2 = "       03  occurs 7.\n" + 
			"             05 field-1        pic x(3).\n" + 
			"             05 field-2        pic s9(5)V99.";
	
	String array3Dim = 
			"         03  occurs 7.\n" + 
			"             05 field-1        pic x(3).\n" + 
			"             05 field-2        pic s9(4)v999.\n" + 
			"             05 occurs 5.\n" + 
			"                10 field-3     pic s9(7) comp-3.\n" + 
			"                10 field-4     pic s9(7) comp occurs 4.\n" + 
			"             05 field-5        pic xxx.\n"; 
	@Test
	public void test1fld() throws IOException {
		ArrayField fieldDef = new ArrayField("array-1", 1, 3, Type.ftChar, 0, "");
		tstCobolArray(array1Dim, "Array-1-dim", fieldDef);
	}
	
	@Test
	public void test2fld() throws IOException {
		ArrayField fieldDef1 = new ArrayField("field-1", 1, 3, Type.ftChar, 0, "");
		@SuppressWarnings("deprecation") // depreciated for internal JREcord use
		ArrayField fieldDef2 = new ArrayField("field-2", 4, 7, Type.ftZonedAsciiSmall, 2, "");
		tstCobolArray(array1Dim2, "Array-2-dim", fieldDef1, fieldDef2);
	}

	@Test
	public void test3fld() throws IOException {
		@SuppressWarnings("deprecation") // depreciated for internal JREcord use
		ArrayField[] fieldDefs = {	new ArrayField("field-1", 1, 3, Type.ftChar, 0, ""),
									new ArrayField("field-2", 4, 7, Type.ftZonedAsciiSmall, 3, ""),
									new ArrayField("field-3", 11, 4, Type.ftPackedDecimalSmall, 0, ""),
									new ArrayField("field-4", 15, 4, Type.ftIntBigEndianSmall, 0, ""),
									new ArrayField("field-5", 31, 3, Type.ftChar, 0, ""),
								};
		tstCobolArray(array3Dim, "Array-3-dim", fieldDefs);
	}

	/**
	 * @param cobol
	 * @param schemaName
	 * @param fieldDef
	 * @throws IOException
	 */
	private void tstCobolArray(String cobol, String schemaName, ArrayField... fieldDefs) throws IOException {
		ICobolIOBuilder newIOBuilder = JRecordInterface1.COBOL.newIOBuilder(new StringReader(cobol), schemaName);
		LayoutDetail layout = newIOBuilder.getLayout();
		LayoutDef schemaDef = new LayoutDef(layout, schemaName, "a1");
		List<ArrayDetails> arrayDetailsList = schemaDef.getRecords().get(0).getArrayDetailsList();
		HashMap<String, ArrayField> fieldMap = new HashMap<>();
		for (ArrayField fd :fieldDefs) {
			fieldMap.put(fd.name, fd);
		}
		
		//int length = 3;
		for (ArrayDetails ad : arrayDetailsList) {
			ArrayGenField.ArraySizeDef[] arrayDimensions = new ArrayGenField.ArraySizeDef[ad.sizes.length];
			for (int i = 0; i < arrayDimensions.length; i++) {
				arrayDimensions[i] = new ArrayGenField.ArraySizeDef(ad.sizes[i], ad.indexPositionIncrement[i]);
			}
			
			new CheckArray(schemaDef, layout, fieldMap).check();
		}
	}
	
	private static class ArrayField{
		final String name;
		final int length, type, decimal;
		
		
		public ArrayField(String name, int position, int length, int type, int decimal, String font) {
			super();
			this.name = name;
			//this.position = position;
			this.length = length;
			this.type = type;
			this.decimal = decimal;
			//this.font = font;
		}
		
		
	}

	private static class CheckArray {
		final LayoutDetail layout;
		final LayoutDef schemaDef;
		final HashMap<String, ArrayField> fieldMap;
		//final int[] indexs;
		
		public CheckArray(LayoutDef schemaDef, LayoutDetail layout, HashMap<String, ArrayField> fieldMap) {
			super();
			this.schemaDef = schemaDef;
			this.layout = layout;
			this.fieldMap = fieldMap;
		}
		
		void check() {
			List<ArrayDetails> arrayDetailsList = schemaDef.getRecords().get(0).getArrayDetailsList();
			for (ArrayDetails ad : arrayDetailsList) {
				ArrayField fieldDef = fieldMap.get(ad.arrayName);
				System.out.println(ad.arrayName + " " + fieldDef.name);
				assertTrue(ad.arrayName, fieldDef != null);
				ArrayGenField.ArraySizeDef[] arrayDimensions = new ArrayGenField.ArraySizeDef[ad.sizes.length];
				for (int i = 0; i < arrayDimensions.length; i++) {
					arrayDimensions[i] = new ArrayGenField.ArraySizeDef(ad.sizes[i], ad.indexPositionIncrement[i]);
				}
//				ArrayGenField arrayField = new ArrayGenField(ad.arrayName, 1, length, Type.ftChar, 0, "", arrayDimensions);
				
				FieldDef firstField = ad.getFirstField();
				ArrayGenField arrayField = new ArrayGenField(ad.arrayName,
						firstField.getPos(), firstField.getLen(),
						firstField.getFieldDetail().getType(), firstField.getFieldDetail().getDecimal(), 
						firstField.getFieldDetail().getFontName(), arrayDimensions);
				checkItem(fieldDef, arrayField, arrayDimensions, new int[arrayDimensions.length], new int[arrayDimensions.length], 0);
			}
		}
		private void checkItem(
				ArrayField fieldDef,
				ArrayGenField arrayField, ArrayGenField.ArraySizeDef[] arrayDimensions, 
				int[] indexs, int[] positions,
				int currentLevel) {
			if (currentLevel >= indexs.length) {
				IFieldDetail genField = arrayField.getField(indexs);
				//System.out.println(genField.getName() + " " + indexs[currentLevel-1]);
				IFieldDetail layoutField = layout.getFieldFromName(genField.getName());
				
				assertEquals(genField.getName(), positions[currentLevel-1], genField.getPos());
				assertEquals(genField.getName(), layoutField.getPos(), genField.getPos());
				assertEquals(genField.getName(), layoutField.getLen(), genField.getLen());
				assertEquals(genField.getName(), layoutField.getType(), genField.getType());
				assertEquals(genField.getName(), layoutField.getFontName(), genField.getFontName());
				assertEquals(genField.getName(), layoutField.getDecimal(), genField.getDecimal());
				
				assertEquals(genField.getName() + " " + fieldDef.name, fieldDef.length, genField.getLen());
				assertEquals(genField.getName(), fieldDef.type, genField.getType());
//				assertEquals(genField.getName(), fieldDef.font, genField.getFontName());
				assertEquals(genField.getName(), fieldDef.decimal, genField.getDecimal());
			} else {
				for (int i = 0; i < arrayDimensions[currentLevel].arraySize; i++) {
					indexs[currentLevel] = i;
					if (i == 0) {
						positions[currentLevel] = currentLevel == 0 
								? arrayField.getPosition() 
								: positions[currentLevel-1] ;
					} else {
						positions[currentLevel] +=  arrayDimensions[currentLevel].inc;
					}
					
					checkItem(fieldDef, arrayField, arrayDimensions, indexs, positions, currentLevel+1);
				}
			}
		}
	}
	//private void checkItem(int[] indexs, currentLevel)
}
