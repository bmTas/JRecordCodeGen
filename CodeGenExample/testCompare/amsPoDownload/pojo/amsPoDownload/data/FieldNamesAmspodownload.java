package pojo.amsPoDownload.data;
 /*
  *          Field Name's for amsPoDownload.cbl
  * ------------------------------------------------------------------   
  *
  *   This class holds the field names of Cobol-Copybook amsPoDownload.cbl
  *   It will allow you use code completion on <i>Cobol Field Names</i> in your
  *   java Programs 
  *        
  * *------------- Keep this notice in your final code ---------------
  * *   This code was generated by JRecord projects CodeGen program
  * *            on the: 9.Jun.2001 0:0:0 
  * *     from Copybook: amsPoDownload.cbl
  * *          Template: pojo
  * *             Split: 01   
  * * File Organization: Text   
  * *              Font: 
  * *   
  * *    CodeGen Author: Bruce Martin
  * *-----------------------------------------------------------------
  *
  *
  *   Please supply any program fixes/enhancements/documentation
  *   back to the JRecord project (https://sourceforge.net/projects/jrecord/)
  *   so other people can benefit !!!
  * 
  *
  *          Bruce Martin (JRecord / CodeGen Author) 
  *
  *
  * ------------------------------------------------------------------
  * v01  CodeGen        9.Jun.2001  Initial version
  *     (Bruce Martin)
  */

import net.sf.JRecord.cgen.impl.ArrayFieldName;
import net.sf.JRecord.cgen.def.*;

public class FieldNamesAmspodownload {

    public static final RecordPoRecord RECORD_PO_RECORD = new RecordPoRecord();
    public static final RecordProductRecord RECORD_PRODUCT_RECORD = new RecordProductRecord();
    public static final RecordLocationRecord RECORD_LOCATION_RECORD = new RecordLocationRecord();

    public static class RecordPoRecord {
       public final String recordType = "Record-Type";
       public final String sequenceNumber = "Sequence-Number";
       public final String vendor = "Vendor";
       public final String po = "PO";
       public final String entryDate = "Entry-Date";
       public final String beg01Code = "beg01-code";
       public final String beg02Code = "beg02-code";
       public final String department = "Department";
       public final String expectedRecieptDate = "Expected-Reciept-Date";
       public final String cancelByDate = "Cancel-by-date";
       public final String ediType = "EDI-Type";
       public final String addDate = "Add-Date";
       public final String departmentName = "Department-Name";
       public final String prcoessType = "Prcoess-Type";
       public final String orderType = "Order-Type";
	
    }


    public static class RecordProductRecord {
       public final String recordType = "Record-Type~1";
       public final String packQty = "Pack-Qty";
       public final String packCost = "Pack-Cost";
       public final String apn = "APN";
       public final String product = "Product";
       public final String pmgDtlTechKey = "pmg-dtl-tech-key";
       public final String casePackId = "Case-Pack-id";
       public final String productName = "Product-Name";
	
    }


    public static class RecordLocationRecord {
       public final String recordType = "Record-Type~2";

   // Array fields follow:	

       public final IFieldName1Dimension dcNumber  = new ArrayFieldName("DC-Number");
       public final IFieldName1Dimension packQuantity  = new ArrayFieldName("Pack-Quantity");
	
    }


}

