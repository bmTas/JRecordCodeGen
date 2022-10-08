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

import net.sf.JRecord.Common.CommonBits;
import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.RecordDetail;

public class SchemaAmspodownload {

    public final RecordPoRecord recordPoRecord;
    public final RecordProductRecord recordProductRecord;
    public final RecordLocationRecord recordLocationRecord;
    
    public final LayoutDetail schema;
    
    public SchemaAmspodownload() {
    
 	recordPoRecord = new RecordPoRecord();
 	recordProductRecord = new RecordProductRecord();
 	recordLocationRecord = new RecordLocationRecord();
        
        RecordDetail[] recs = {
                recordPoRecord.record,
                recordProductRecord.record,
                recordLocationRecord.record,
        };

        String recordSepString = ""; 
        byte[] recordSep = CommonBits.getEolBytes( null, recordSepString, "");
        schema = new LayoutDetail(
                      "amsPoDownload", recs, "", 
                      Constants.rtGroupOfBinaryRecords, recordSep, recordSepString, 
                      "", null, Constants.IO_BIN_TEXT);
        
        recordPoRecord.updateRecordSelection(schema);
        recordProductRecord.updateRecordSelection(schema);
        recordLocationRecord.updateRecordSelection(schema);
    }
    
    public SchemaAmspodownload(LayoutDetail schema) {
        this.recordPoRecord = new RecordPoRecord(schema, schema.getRecord("PO-Record"));
        this.recordProductRecord = new RecordProductRecord(schema, schema.getRecord("Product-Record"));
        this.recordLocationRecord = new RecordLocationRecord(schema, schema.getRecord("Location-Record"));

        this.schema = schema;
    }
}

