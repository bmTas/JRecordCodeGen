package example.ioBuilder.amsPoDownload.example;
 /*
  *   Example program for amsPoDownload.cbl
  *   This class uses the JRecord project (https://sourceforge.net/projects/jrecord/)
  * -------------------------------------------------------------------
  *        
  * *------------- Keep this notice in your final code ---------------
  * *   This code was generated by JRecord projects CodeGen program
  * *            on the: 6 Nov 2018 8:27:10 
  * *     from Copybook: amsPoDownload.cbl
  * *          Template: pojo
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

	

import java.io.IOException;

import net.sf.JRecord.cgen.def.IReader;
import net.sf.JRecord.cgen.impl.IoBuilder;

import example.ioBuilder.amsPoDownload.data.LineAmspodownloadSchema;

import example.ioBuilder.amsPoDownload.io.IoBuilderAmspodownload;
import example.ioBuilder.amsPoDownload.data.LinePoRecordPojo;
import example.ioBuilder.amsPoDownload.data.LineProductRecordPojo;
import example.ioBuilder.amsPoDownload.data.LineLocationRecordPojo;


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
     * Example of Using a PojoReader  classes
     */
    public ReadAmspodownload() {
        super();

        IReader<LineAmspodownloadSchema> reader = null;
        int lineNum = 0;
        try {
            IoBuilder<LineAmspodownloadSchema> iob = IoBuilderAmspodownload.newIoBuilder(copybookName);
            LineAmspodownloadSchema line;

            //TODO Check the Record Selection code (in IoBuilderAmspodownload) is correct
            //TODO (or set a RecordDecider/RecordSelection !!!).  e.g.
            //TODO
            //TODO        iob.setRecordDecider(...);
            //TODO   or   iob.setRecordSelection(...)
            //TODO
            //TODO You need to tell JRecord how to decide what
            //TODO Records have been read !!!
            
            reader = iob.newReader(dataFile);
            
            while ((line = reader.read()) != null) {
               lineNum += 1;
               
               switch (line.generatedRecordType()) {
               case PO_RECORD :
                   LinePoRecordPojo pojoPoRecord = line.asPoRecord();
                   System.out.println(
                              pojoPoRecord.getRecordType()
                      + " " + pojoPoRecord.getSequenceNumber()
                      + " " + pojoPoRecord.getVendor()
                      + " " + pojoPoRecord.getPo()
                      + " " + pojoPoRecord.getEntryDate()
                      + " " + pojoPoRecord.getBeg01Code()
                      + " " + pojoPoRecord.getBeg02Code()
                      + " " + pojoPoRecord.getDepartment()
                      + " " + pojoPoRecord.getExpectedRecieptDate()
                      + " " + pojoPoRecord.getCancelByDate()
                      + " " + pojoPoRecord.getEdiType()
                       );
                   break;
               case PRODUCT_RECORD :
                   LineProductRecordPojo pojoProductRecord = line.asProductRecord();
                   System.out.println(
                              pojoProductRecord.getRecordType()
                      + " " + pojoProductRecord.getPackQty()
                      + " " + pojoProductRecord.getPackCost()
                      + " " + pojoProductRecord.getApn()
                      + " " + pojoProductRecord.getProduct()
                      + " " + pojoProductRecord.getPmgDtlTechKey()
                      + " " + pojoProductRecord.getCasePackId()
                      + " " + pojoProductRecord.getProductName()
                       );
                   break;
               case LOCATION_RECORD :
                   LineLocationRecordPojo pojoLocationRecord = line.asLocationRecord();
                   System.out.println(
                              pojoLocationRecord.getRecordType()
                      + " " + pojoLocationRecord.getDcNumber(0)
                      + " " + pojoLocationRecord.getPackQuantity(0)
                       );
                   break;
               }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        new ReadAmspodownload();
    }
}

