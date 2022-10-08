package schemaClass.amsPoDownload.example;
 /*
  *   Example program for amsPoDownload.cbl
  *   This class uses the JRecord project (https://sourceforge.net/projects/jrecord/)
  * -------------------------------------------------------------------
  *        
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
  *   This class should be used as an example of reading/writing files
  *   using JRecord. You will need to modify the code to meet your
  *   needs. The Author of CodeGen (Bruce Martin) program takes no 
  *   responsibility for the accuracy of the generated code. 
  *
  *   Good Luck
  *              Bruce Martin
  *
  * ------------------------------------------------------------------
  * v01  CodeGen        9.Jun.2001  Initial version
  *     (Bruce Martin)
  *
  * ------------------------------------------------------------------ 
  */


import java.io.IOException;

import net.sf.JRecord.JRecordInterface1;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.External.CopybookLoader;
import net.sf.JRecord.def.IO.builders.ICobolIOBuilder;

import net.sf.JRecord.IO.AbstractLineWriter;

import schemaClass.amsPoDownload.schema.RecordPoRecord;
import schemaClass.amsPoDownload.schema.RecordProductRecord;
import schemaClass.amsPoDownload.schema.RecordLocationRecord;
import schemaClass.amsPoDownload.schema.SchemaAmspodownload;


/**
 * Write Cobol file using a Cobol Copybook (Amspodownload).
 *
 * This Generated program is intended as an example of using JRecord
 * rather than a useful program (that compiles - it wont).
 * You should regard it as a starting point and modify 
 * it according to needs
 */
public final class WriteAmspodownload {

    private String testDir        = "G:/temp/";
    private String salesFileOut   = testDir + "DTAR020out.bin";
    private String copybookName   = "amsPoDownload.cbl"; 

    private RecordPoRecord rPoRecord;
    private RecordProductRecord rProductRecord;
    private RecordLocationRecord rLocationRecord;
    
    /**
     * Example of LineReader  classes
     */
    public WriteAmspodownload() {
        super();

        try {
            ICobolIOBuilder iob = JRecordInterface1.COBOL
                                .newIOBuilder(copybookName)
                                   //.setFont("") // Think about specifying an encoding !!!
                                   .setFileOrganization(Constants.IO_BIN_TEXT)
                                   .setSplitCopybook(CopybookLoader.SPLIT_01_LEVEL)
         //TODO   --    Setup/Check record Selection !!!
         //TODO     .setRecordSelection( "PO-Record", ExternalFieldSelection.newFieldSelection("field-name", "value to test"))  
         //TODO     .setRecordSelection( "Product-Record", ExternalFieldSelection.newFieldSelection("field-name", "value to test"))  
         //TODO     .setRecordSelection( "Location-Record", ExternalFieldSelection.newFieldSelection("field-name", "value to test"))  
                                       ;  
            SchemaAmspodownload schemaAmspodownload
                    = new SchemaAmspodownload(iob.getLayout()); 
            rPoRecord = schemaAmspodownload.recordPoRecord;
            rProductRecord = schemaAmspodownload.recordProductRecord;
            rLocationRecord = schemaAmspodownload.recordLocationRecord;
            AbstractLineWriter writer = iob.newWriter(salesFileOut);
 
            writer.write(createPoRecord(iob, data));
            writer.write(createProductRecord(iob, data));
            writer.write(createLocationRecord(iob, data));

            writer.close();
        } catch (Exception e) {
             System.out.println();

            e.printStackTrace();
        }
    }


  /*
   *   The following code contains sample assignments for every 
   * field in the Cobol File. You should modify the code to suit
   * your needs.
   *   As I do not know where the data is coming from, I have used
   * SourceOfDataForTheCobolFile instead. You should replace this with your
   * class / classes  or remove it as needed.
   *   To put it another way, it time for you to start Coding
   */

    private AbstractLine createPoRecord(ICobolIOBuilder iob, SourceOfDataForTheCobolFile data) 
    throws IOException {
        AbstractLine l = iob.newLine();    
        
        l.getFieldValue(rPoRecord.fldRecordType).set(data. ...);
        l.getFieldValue(rPoRecord.fldSequenceNumber).set(data. ...);
        l.getFieldValue(rPoRecord.fldVendor).set(data. ...);
        l.getFieldValue(rPoRecord.fldPo).set(data. ...);
        l.getFieldValue(rPoRecord.fldEntryDate).set(data. ...);
        l.getFieldValue(rPoRecord.fldBeg01Code).set(data. ...);
        l.getFieldValue(rPoRecord.fldBeg02Code).set(data. ...);
        l.getFieldValue(rPoRecord.fldDepartment).set(data. ...);
        l.getFieldValue(rPoRecord.fldExpectedRecieptDate).set(data. ...);
        l.getFieldValue(rPoRecord.fldCancelByDate).set(data. ...);
        l.getFieldValue(rPoRecord.fldEdiType).set(data. ...);
        l.getFieldValue(rPoRecord.fldAddDate).set(data. ...);
        l.getFieldValue(rPoRecord.fldDepartmentName).set(data. ...);
        l.getFieldValue(rPoRecord.fldPrcoessType).set(data. ...);
        l.getFieldValue(rPoRecord.fldOrderType).set(data. ...);
	
    
        return l;
    }

    private AbstractLine createProductRecord(ICobolIOBuilder iob, SourceOfDataForTheCobolFile data) 
    throws IOException {
        AbstractLine l = iob.newLine();    
        
        l.getFieldValue(rProductRecord.fldRecordType).set(data. ...);
        l.getFieldValue(rProductRecord.fldPackQty).set(data. ...);
        l.getFieldValue(rProductRecord.fldPackCost).set(data. ...);
        l.getFieldValue(rProductRecord.fldApn).set(data. ...);
        l.getFieldValue(rProductRecord.fldProduct).set(data. ...);
        l.getFieldValue(rProductRecord.fldPmgDtlTechKey).set(data. ...);
        l.getFieldValue(rProductRecord.fldCasePackId).set(data. ...);
        l.getFieldValue(rProductRecord.fldProductName).set(data. ...);
	
    
        return l;
    }

    private AbstractLine createLocationRecord(ICobolIOBuilder iob, SourceOfDataForTheCobolFile data) 
    throws IOException {
        AbstractLine l = iob.newLine();    
        
        l.getFieldValue(rLocationRecord.fldRecordType).set(data. ...);
	
        l.getFieldValue(rLocationRecord.arrayDcNumber.get(0)).set(data. ...);
        l.getFieldValue(rLocationRecord.arrayPackQuantity.get(0)).set(data. ...);
    
        return l;
    }

    public static void main(String[] args) {
        new WriteAmspodownload();
    }
}
