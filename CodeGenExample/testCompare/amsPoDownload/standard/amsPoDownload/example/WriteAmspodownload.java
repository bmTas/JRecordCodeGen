package standard.amsPoDownload.example;
 /*
  *   Example program for amsPoDownload.cbl
  *   This class uses the JRecord project (https://sourceforge.net/projects/jrecord/)
  * -------------------------------------------------------------------
  *        
  * *------------- Keep this notice in your final code ---------------
  * *   This code was generated by JRecord projects CodeGen program
  * *            on the: 9.Jun.2001 0:0:0 
  * *     from Copybook: amsPoDownload.cbl
  * *          Template: standard
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

import standard.amsPoDownload.schema.FieldNamesAmspodownload;


/**
 * Write Cobol file using a Cobol Copybook (Amspodownload).
 *
 * This Generated program is intended as an example of using JRecord
 * rather than a useful program (that compiles - it wont).
 * You should regard it as a starting point and modify
 * it according to needs
 */
public final class WriteAmspodownload {


    private String outputFileName = "";
    private String copybookName   = "amsPoDownload.cbl";

    private FieldNamesAmspodownload.RecordPoRecord rPoRecord = FieldNamesAmspodownload.RECORD_PO_RECORD;

    private FieldNamesAmspodownload.RecordProductRecord rProductRecord = FieldNamesAmspodownload.RECORD_PRODUCT_RECORD;

    private FieldNamesAmspodownload.RecordLocationRecord rLocationRecord = FieldNamesAmspodownload.RECORD_LOCATION_RECORD;


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
            AbstractLineWriter writer = iob.newWriter(outputFileName);

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
        AbstractLine line = iob.newLine();

        line.getFieldValue(rPoRecord.recordType).set(data. ...);
        line.getFieldValue(rPoRecord.sequenceNumber).set(data. ...);
        line.getFieldValue(rPoRecord.vendor).set(data. ...);
        line.getFieldValue(rPoRecord.po).set(data. ...);
        line.getFieldValue(rPoRecord.entryDate).set(data. ...);
        line.getFieldValue(rPoRecord.beg01Code).set(data. ...);
        line.getFieldValue(rPoRecord.beg02Code).set(data. ...);
        line.getFieldValue(rPoRecord.department).set(data. ...);
        line.getFieldValue(rPoRecord.expectedRecieptDate).set(data. ...);
        line.getFieldValue(rPoRecord.cancelByDate).set(data. ...);
        line.getFieldValue(rPoRecord.ediType).set(data. ...);
        line.getFieldValue(rPoRecord.addDate).set(data. ...);
        line.getFieldValue(rPoRecord.departmentName).set(data. ...);
        line.getFieldValue(rPoRecord.prcoessType).set(data. ...);
        line.getFieldValue(rPoRecord.orderType).set(data. ...);


        return line;
    }

    private AbstractLine createProductRecord(ICobolIOBuilder iob, SourceOfDataForTheCobolFile data) 
    throws IOException {
        AbstractLine line = iob.newLine();

        line.getFieldValue(rProductRecord.recordType).set(data. ...);
        line.getFieldValue(rProductRecord.packQty).set(data. ...);
        line.getFieldValue(rProductRecord.packCost).set(data. ...);
        line.getFieldValue(rProductRecord.apn).set(data. ...);
        line.getFieldValue(rProductRecord.product).set(data. ...);
        line.getFieldValue(rProductRecord.pmgDtlTechKey).set(data. ...);
        line.getFieldValue(rProductRecord.casePackId).set(data. ...);
        line.getFieldValue(rProductRecord.productName).set(data. ...);


        return line;
    }

    private AbstractLine createLocationRecord(ICobolIOBuilder iob, SourceOfDataForTheCobolFile data) 
    throws IOException {
        AbstractLine line = iob.newLine();

        line.getFieldValue(rLocationRecord.recordType).set(data. ...);

        line.getFieldValue(rLocationRecord.dcNumber.get(0)).set(data. ...);
        line.getFieldValue(rLocationRecord.packQuantity.get(0)).set(data. ...);

        return line;
    }

    public static void main(String[] args) {
        new WriteAmspodownload();
    }
}

