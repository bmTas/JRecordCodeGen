package example.ioBuilder.amsPoDownload.example;
 /*
  *   Example program for amsPoDownload.cbl
  *   This class uses the JRecord project (https://sourceforge.net/projects/jrecord/)
  * -------------------------------------------------------------------
  *        
  * *------------- Keep this notice in your final code ---------------
  * *   This code was generated by JRecord projects CodeGen program
  * *            on the: 6 Nov 2018 18:59:18 
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
  * v01  CodeGen        6 Nov 2018  Initial version
  *     (Bruce Martin)
  *
  * ------------------------------------------------------------------ 
  */


import net.sf.JRecord.JRecordInterface1;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.IO.AbstractLineReader;
import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.External.CopybookLoader;
import net.sf.JRecord.def.IO.builders.ICobolIOBuilder;
                                         

import example.ioBuilder.amsPoDownload.schema.RecordPoRecord;
import example.ioBuilder.amsPoDownload.schema.RecordProductRecord;
import example.ioBuilder.amsPoDownload.schema.RecordLocationRecord;
import example.ioBuilder.amsPoDownload.schema.SchemaAmspodownload;
	

/**
 * Read Cobol file using a Cobol Copybook (Amspodownload).
 *
 * This Generated program is intended as an example of using JRecord
 * rather than a useful program (that compiles - it wont).
 * You should regard it as a starting point and modify 
 * it according to needs
 */
public final class ReadAmspodownload {

    private String testDir        = "G:/temp/";
    private String salesFile      = testDir + "DTAR020.bin";

    private String copybookName   = "amsPoDownload.cbl"; 
 
    /**
     * Example of LineReader  classes
     */
    public ReadAmspodownload() {
        super();

        AbstractLine line;
        int lineNum = 0;

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
            RecordPoRecord rPoRecord = schemaAmspodownload.recordPoRecord;
            RecordProductRecord rProductRecord = schemaAmspodownload.recordProductRecord;
            RecordLocationRecord rLocationRecord = schemaAmspodownload.recordLocationRecord;
            AbstractLineReader reader = iob.newReader(salesFile);
            while ((line = reader.read()) != null) {
                lineNum += 1;
                if (/* PO-Record */) {
                   System.out.println(
                              line.getFieldValue(rPoRecord.fldRecordType).asString()
                      + " " + line.getFieldValue(rPoRecord.fldSequenceNumber).asString()
                      + " " + line.getFieldValue(rPoRecord.fldVendor).asString()
                      + " " + line.getFieldValue(rPoRecord.fldPo).asString()
                      + " " + line.getFieldValue(rPoRecord.fldEntryDate).asString()
                      + " " + line.getFieldValue(rPoRecord.fldBeg01Code).asString()
                      + " " + line.getFieldValue(rPoRecord.fldBeg02Code).asString()
                      + " " + line.getFieldValue(rPoRecord.fldDepartment).asString()
                      + " " + line.getFieldValue(rPoRecord.fldExpectedRecieptDate).asString()
                      + " " + line.getFieldValue(rPoRecord.fldCancelByDate).asString()
                      + " " + line.getFieldValue(rPoRecord.fldEdiType).asString()
                   );
                }
                if (/* Product-Record */) {
                   System.out.println(
                              line.getFieldValue(rProductRecord.fldRecordType).asString()
                      + " " + line.getFieldValue(rProductRecord.fldPackQty).asString()
                      + " " + line.getFieldValue(rProductRecord.fldPackCost).asString()
                      + " " + line.getFieldValue(rProductRecord.fldApn).asString()
                      + " " + line.getFieldValue(rProductRecord.fldProduct).asString()
                      + " " + line.getFieldValue(rProductRecord.fldPmgDtlTechKey).asString()
                      + " " + line.getFieldValue(rProductRecord.fldCasePackId).asString()
                      + " " + line.getFieldValue(rProductRecord.fldProductName).asString()
                   );
                }
                if (/* Location-Record */) {
                   System.out.println(
                              line.getFieldValue(rLocationRecord.fldRecordType).asString()
                      + " " + line.getFieldValue(rLocationRecord.arrayDcNumber.get(0)).asString()
                      + " " + line.getFieldValue(rLocationRecord.arrayPackQuantity.get(0)).asString()
                   );
                }
            }

            reader.close();
        } catch (Exception e) {
            System.out.println("~~> " + lineNum + " " + e);
            System.out.println();

            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        new ReadAmspodownload();
    }
}

