package lineWrapperPojo.amsPoDownload.example;
 /*
  *   Example program for amsPoDownload.cbl
  *   This class uses the JRecord project (https://sourceforge.net/projects/jrecord/)
  * -------------------------------------------------------------------
  *        
  * *------------- Keep this notice in your final code ---------------
  * *   This code was generated by JRecord projects CodeGen program
  * *            on the: 9.Jun.2001 0:0:0 
  * *     from Copybook: amsPoDownload.cbl
  * *          Template: lineWrapperPojo
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


import net.sf.JRecord.JRecordInterface1;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.IO.AbstractLineReader;

import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.External.CopybookLoader;
import net.sf.JRecord.def.IO.builders.ICobolIOBuilder;

import lineWrapperPojo.amsPoDownload.data.FieldNamesAmspodownload;

import lineWrapperPojo.amsPoDownload.data.LinePoRecordJR;
import lineWrapperPojo.amsPoDownload.data.LineProductRecordJR;
import lineWrapperPojo.amsPoDownload.data.LineLocationRecordJR;


/**
 * Read Cobol file using a Cobol Copybook (Amspodownload).
 *
 * This Generated program is intended as an example of using JRecord
 * rather than a useful program (that compiles - it wont).
 * You should regard it as a starting point and modify
 * it according to needs
 */
public final class ReadAmspodownload {

    private String dataFile     = "";
    private String copybookName = "amsPoDownload.cbl";

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

           LinePoRecordJR linePoRecordJR = new LinePoRecordJR();
           LineProductRecordJR lineProductRecordJR = new LineProductRecordJR();
           LineLocationRecordJR lineLocationRecordJR = new LineLocationRecordJR();

           FieldNamesAmspodownload.RecordPoRecord rPoRecord = FieldNamesAmspodownload.RECORD_PO_RECORD;
           FieldNamesAmspodownload.RecordProductRecord rProductRecord = FieldNamesAmspodownload.RECORD_PRODUCT_RECORD;
           FieldNamesAmspodownload.RecordLocationRecord rLocationRecord = FieldNamesAmspodownload.RECORD_LOCATION_RECORD;

           AbstractLineReader reader = iob.newReader(dataFile);

           while ((line = reader.read()) != null) {
               lineNum += 1;
               if (/* PO-Record */) {
                  linePoRecordJR.setLine(line);
                  System.out.println(
                              linePoRecordJR.getRecordType() 
                      + " " + linePoRecordJR.getSequenceNumber() 
                      + " " + linePoRecordJR.getVendor() 
                      + " " + linePoRecordJR.getPo() 
                      + " " + linePoRecordJR.getEntryDate() 
                      + " " + linePoRecordJR.getBeg01Code() 
                      + " " + linePoRecordJR.getBeg02Code() 
                      + " " + linePoRecordJR.getDepartment() 
                      + " " + linePoRecordJR.getExpectedRecieptDate() 
                      + " " + linePoRecordJR.getCancelByDate() 
                      + " " + linePoRecordJR.getEdiType() 
                  );
               }
               if (/* Product-Record */) {
                  lineProductRecordJR.setLine(line);
                  System.out.println(
                              lineProductRecordJR.getRecordType() 
                      + " " + lineProductRecordJR.getPackQty() 
                      + " " + lineProductRecordJR.getPackCost() 
                      + " " + lineProductRecordJR.getApn() 
                      + " " + lineProductRecordJR.getProduct() 
                      + " " + lineProductRecordJR.getPmgDtlTechKey() 
                      + " " + lineProductRecordJR.getCasePackId() 
                      + " " + lineProductRecordJR.getProductName() 
                  );
               }
               if (/* Location-Record */) {
                  lineLocationRecordJR.setLine(line);
                  System.out.println(
                              lineLocationRecordJR.getRecordType() 
                      + " " + lineLocationRecordJR.getDcNumber(0)
                      + " " + lineLocationRecordJR.getPackQuantity(0)
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

