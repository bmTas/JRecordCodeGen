package example.ioBuilder.dtar020.schema;
 /*
  * *------------- Keep this notice in your final code ---------------
  * *   This code was generated by JRecord projects CodeGen program
  * *            on the: 6 Nov 2018 12:47:8 
  * *     from Copybook: DTAR020.cbl
  * *          Template: schemaClass
  * *             Split: None   
  * * File Organization: FixedWidth   
  * *              Font: cp037
  * *   
  * *    CodeGen Author: Bruce Martin
  * *-----------------------------------------------------------------
  *
  *   This Code should not be changed you should, either:
  *   * Rerun CodeGen to regenerate it 
  *   * Fix CodeGen and rerun CodeGen
  *
  *   Please supply any program fixes/enhancements/documentation
  *   back to the JRecord project (https://sourceforge.net/projects/jrecord/)
  *   so other people can benefit !!!
  * 
  *
  *          Bruce Martin (JRecord / CodeGen Author) 
  *
  * ------------------------------------------------------------------
  * v01  CodeGen        6 Nov 2018  Initial version
  *     (Bruce Martin)
  */
import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Common.FieldDetail;
import net.sf.JRecord.Common.IFieldDetail;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.RecordDetail;
import net.sf.JRecord.Types.Type;

public class RecordDtar020 {

    public static final String RECORD_NAME = "DTAR020";
    
    public final IFieldDetail fldKeycodeNo;
    public final IFieldDetail fldStoreNo;
    public final IFieldDetail fldDate;
    public final IFieldDetail fldDeptNo;
    public final IFieldDetail fldQtySold;
    public final IFieldDetail fldSalePrice;


    public final RecordDetail record;
    
    private final String font;
    
    public RecordDtar020() {
       FieldDetail[] flds = new FieldDetail[6];
       int i = 0;
       
       font = "cp037";
       fldKeycodeNo = createField(flds, i++, "KEYCODE-NO", Type.ftChar, 1, 8, 0);
       fldStoreNo = createField(flds, i++, "STORE-NO", Type.ftPackedDecimal, 9, 2, 0);
       fldDate = createField(flds, i++, "DATE", Type.ftPackedDecimal, 11, 4, 0);
       fldDeptNo = createField(flds, i++, "DEPT-NO", Type.ftPackedDecimal, 15, 2, 0);
       fldQtySold = createField(flds, i++, "QTY-SOLD", Type.ftPackedDecimal, 17, 5, 0);
       fldSalePrice = createField(flds, i++, "SALE-PRICE", Type.ftPackedDecimal, 22, 6, 2);

      String t = "	"; 	
      if (t == "\t") {
          t = "<tab>";
      }
      record = new RecordDetail(RECORD_NAME,
                   null,
                   Constants.rtBinaryRecord, t, "",
                   font, flds, 0);
      record.setParentRecordIndex( -1);
         


    }
    
    public RecordDtar020(LayoutDetail l, RecordDetail r) {
    
        font = l.getFontName();
        record = r;
        
        fldKeycodeNo = l.getFieldFromName("KEYCODE-NO");        
        fldStoreNo = l.getFieldFromName("STORE-NO");        
        fldDate = l.getFieldFromName("DATE");        
        fldDeptNo = l.getFieldFromName("DEPT-NO");        
        fldQtySold = l.getFieldFromName("QTY-SOLD");        
        fldSalePrice = l.getFieldFromName("SALE-PRICE");        


    }
    
    public void updateRecordSelection(LayoutDetail l) {
    }
    
    private FieldDetail createField(
                FieldDetail[] flds, int idx,
                String name,
                int type,
                int pos,
                int len,
                int decimal) {
        flds[idx] = FieldDetail.newFixedWidthField(name, type, pos, len, decimal, font);
        return flds[idx];
    }
}
