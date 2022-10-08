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
import net.sf.JRecord.cgen.impl.ArrayFieldDefinition;
import net.sf.JRecord.cgen.impl.ArrayFieldDefinition1;
import net.sf.JRecord.cgen.def.*;
import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Common.FieldDetail;
import net.sf.JRecord.Common.IFieldDetail;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.RecordDetail;
import net.sf.JRecord.Types.Type;

public class RecordLocationRecord {

    public static final String RECORD_NAME = "Location-Record";
    
    public final IFieldDetail fldRecordType;
    public final IFieldDetail fldDcNumber0;
    public final IFieldDetail fldPackQuantity0;
    public final IFieldDetail fldDcNumber1;
    public final IFieldDetail fldPackQuantity1;

    public final IArray1Dimension arrayDcNumber;
    public final IArray1Dimension arrayPackQuantity;

    public final RecordDetail record;
    
    private final String font;
    
    public RecordLocationRecord() {
       FieldDetail[] flds = new FieldDetail[21];
       int i = 0;
       
       font = "";
       fldRecordType = createField(flds, i++, "Record-Type~2", Type.ftChar, 1, 2, 0);
       fldDcNumber0 = createField(flds, i++, "DC-Number (0)", Type.ftNumZeroPaddedPositive, 3, 4, 0);
       fldPackQuantity0 = createField(flds, i++, "Pack-Quantity (0)", Type.ftNumZeroPaddedPositive, 7, 8, 0);
       fldDcNumber1 = createField(flds, i++, "DC-Number (1)", Type.ftNumZeroPaddedPositive, 15, 4, 0);
       fldPackQuantity1 = createField(flds, i++, "Pack-Quantity (1)", Type.ftNumZeroPaddedPositive, 19, 8, 0);

      String t = "	"; 	
      if (t == "\t") {
          t = "<tab>";
      }
      record = new RecordDetail(RECORD_NAME,
                   null,
                   Constants.rtBinaryRecord, t, "",
                   font, flds, 0);
      record.setParentRecordIndex( -1);
         

        arrayDcNumber = new ArrayFieldDefinition(
                   record,
                   9,
                   fldDcNumber1
                 , fldDcNumber0
        );
        arrayPackQuantity = new ArrayFieldDefinition(
                   record,
                   9,
                   fldPackQuantity1
                 , fldPackQuantity0
        );

    }
    
    public RecordLocationRecord(LayoutDetail l, RecordDetail r) {
    
        font = l.getFontName();
        record = r;
        
        fldRecordType = l.getFieldFromName("Record-Type~2");        
        fldDcNumber0 = l.getFieldFromName("DC-Number (0)");        
        fldPackQuantity0 = l.getFieldFromName("Pack-Quantity (0)");        
        fldDcNumber1 = l.getFieldFromName("DC-Number (1)");        
        fldPackQuantity1 = l.getFieldFromName("Pack-Quantity (1)");        

        arrayDcNumber = new ArrayFieldDefinition1(
                   new int[] {10},
                   record.getArrayFields(
                       (FieldDetail)fldDcNumber0,
                       "DC-Number")      
        );
        arrayPackQuantity = new ArrayFieldDefinition1(
                   new int[] {10},
                   record.getArrayFields(
                       (FieldDetail)fldPackQuantity0,
                       "Pack-Quantity")      
        );

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
