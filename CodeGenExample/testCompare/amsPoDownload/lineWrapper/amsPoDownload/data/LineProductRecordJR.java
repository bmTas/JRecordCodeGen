package lineWrapper.amsPoDownload.data;

 /*
  * *------------- Keep this notice in your final code ---------------
  * *   This code was generated by JRecord projects CodeGen program
  * *            on the: 9.Jun.2001 0:0:0 
  * *     from Copybook: amsPoDownload.cbl
  * *          Template: lineWrapper
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

/**
 *  This is a <i>LineWrapper</i> class - it wraps JRecord Line
 *  makes it look like a Java Bean and implements the relavent
 *  interfaces
 */
 

import java.math.BigDecimal;

import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.IGetByteData;
import net.sf.JRecord.Details.Line;
import net.sf.JRecord.Details.CsvLine;


public class LineProductRecordJR implements IGetByteData {
   
    private AbstractLine line; 

    private static FieldNamesAmspodownload.RecordProductRecord fn
                   = FieldNamesAmspodownload.RECORD_PRODUCT_RECORD;

        

    
    public String getRecordType() {
        return line.getFieldValue(fn.recordType).asString();
    }
    
    
    public void setRecordType(String value) {
        this.line.getFieldValue(fn.recordType).set(value);
    }

    
    public BigDecimal getPackQty() {
        return line.getFieldValue(fn.packQty).asBigDecimal();
    }
    
    
    public void setPackQty(BigDecimal value) {
        this.line.getFieldValue(fn.packQty).set(value);
    }

    
    public BigDecimal getPackCost() {
        return line.getFieldValue(fn.packCost).asBigDecimal();
    }
    
    
    public void setPackCost(BigDecimal value) {
        this.line.getFieldValue(fn.packCost).set(value);
    }

    
    public long getApn() {
        return line.getFieldValue(fn.apn).asLong();
    }
    
    
    public void setApn(long value) {
        this.line.getFieldValue(fn.apn).set(value);
    }

    
    public int getProduct() {
        return line.getFieldValue(fn.product).asInt();
    }
    
    
    public void setProduct(int value) {
        this.line.getFieldValue(fn.product).set(value);
    }

    
    public String getPmgDtlTechKey() {
        return line.getFieldValue(fn.pmgDtlTechKey).asString();
    }
    
    
    public void setPmgDtlTechKey(String value) {
        this.line.getFieldValue(fn.pmgDtlTechKey).set(value);
    }

    
    public String getCasePackId() {
        return line.getFieldValue(fn.casePackId).asString();
    }
    
    
    public void setCasePackId(String value) {
        this.line.getFieldValue(fn.casePackId).set(value);
    }

    
    public String getProductName() {
        return line.getFieldValue(fn.productName).asString();
    }
    
    
    public void setProductName(String value) {
        this.line.getFieldValue(fn.productName).set(value);
    }


	


    @Override
    public byte[] getData() {
        return line.getData();  
    }

    @Override
    public void setData(byte[] data) {

        if (line instanceof Line || line instanceof CsvLine) {
            line.setData(data);
        } else {
            throw new RuntimeException("Invalid line for setdata");
        }
    }
    
    public LineProductRecordJR setLine(AbstractLine l) {
    	line = l;
    	return this;
    }
    
    public AbstractLine getLine() {
        return line;
    }
}

