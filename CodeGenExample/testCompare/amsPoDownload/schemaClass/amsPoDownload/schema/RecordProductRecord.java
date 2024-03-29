package schemaClass.amsPoDownload.schema;
 /*
  * *------------- Keep this notice in your final code ---------------
  * *   This code was generated by JRecord projects CodeGen program
  * *            on the: 9.Jun.2001 0:0:0 
  * *     from Copybook: amsPoDownload.cbl
  * *          Template: schemaClass
  * *             Split: 01   
  * * File Organization: Text   
  * *              Font: 
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
  * v01  CodeGen        9.Jun.2001  Initial version
  *     (Bruce Martin)
  */
import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Common.FieldDetail;
import net.sf.JRecord.Common.IFieldDetail;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.RecordDetail;
import net.sf.JRecord.Types.Type;

public class RecordProductRecord {

    public static final String RECORD_NAME = "Product-Record";
    
    public final IFieldDetail fldRecordType;
    public final IFieldDetail fldPackQty;
    public final IFieldDetail fldPackCost;
    public final IFieldDetail fldApn;
    public final IFieldDetail fldProduct;
    public final IFieldDetail fldPmgDtlTechKey;
    public final IFieldDetail fldCasePackId;
    public final IFieldDetail fldProductName;


    public final RecordDetail record;
    
    private final String font;
    
    public RecordProductRecord() {
       FieldDetail[] flds = new FieldDetail[8];
       int i = 0;
       
       font = "";
       fldRecordType = createField(flds, i++, "Record-Type~1", Type.ftChar, 1, 2, 0);
       fldPackQty = createField(flds, i++, "Pack-Qty", Type.ftAssumedDecimalPositive, 3, 9, 4);
       fldPackCost = createField(flds, i++, "Pack-Cost", Type.ftAssumedDecimalPositive, 12, 13, 4);
       fldApn = createField(flds, i++, "APN", Type.ftNumZeroPaddedPositive, 25, 13, 0);
       fldProduct = createField(flds, i++, "Product", Type.ftNumZeroPaddedPositive, 39, 8, 0);
       fldPmgDtlTechKey = createField(flds, i++, "pmg-dtl-tech-key", Type.ftChar, 72, 15, 0);
       fldCasePackId = createField(flds, i++, "Case-Pack-id", Type.ftChar, 87, 15, 0);
       fldProductName = createField(flds, i++, "Product-Name", Type.ftChar, 102, 50, 0);

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
    
    public RecordProductRecord(LayoutDetail l, RecordDetail r) {
    
        font = l.getFontName();
        record = r;
        
        fldRecordType = l.getFieldFromName("Record-Type~1");        
        fldPackQty = l.getFieldFromName("Pack-Qty");        
        fldPackCost = l.getFieldFromName("Pack-Cost");        
        fldApn = l.getFieldFromName("APN");        
        fldProduct = l.getFieldFromName("Product");        
        fldPmgDtlTechKey = l.getFieldFromName("pmg-dtl-tech-key");        
        fldCasePackId = l.getFieldFromName("Case-Pack-id");        
        fldProductName = l.getFieldFromName("Product-Name");        


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
