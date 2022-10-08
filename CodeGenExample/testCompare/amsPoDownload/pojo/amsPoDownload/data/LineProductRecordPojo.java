package pojo.amsPoDownload.data;
 /*
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
 * This class is the Java representation of one record in the file
 */

import java.math.BigDecimal;


public class LineProductRecordPojo extends LineAmspodownloadSchema 
 {

    private String recordType;
    private BigDecimal packQty;
    private BigDecimal packCost;
    private long apn;
    private int product;
    private String pmgDtlTechKey;
    private String casePackId;
    private String productName;


    public LineProductRecordPojo() {
        super( pojo.amsPoDownload.def.DefsAmspodownload
                      .Records.PRODUCT_RECORD );
                      
	
    }

    
    public String getRecordType() {
        return recordType;
    }
    
    
    public void setRecordType(String value) {
        this.recordType = value;
    }

    
    public BigDecimal getPackQty() {
        return packQty;
    }
    
    
    public void setPackQty(BigDecimal value) {
        this.packQty = value;
    }

    
    public BigDecimal getPackCost() {
        return packCost;
    }
    
    
    public void setPackCost(BigDecimal value) {
        this.packCost = value;
    }

    
    public long getApn() {
        return apn;
    }
    
    
    public void setApn(long value) {
        this.apn = value;
    }

    
    public int getProduct() {
        return product;
    }
    
    
    public void setProduct(int value) {
        this.product = value;
    }

    
    public String getPmgDtlTechKey() {
        return pmgDtlTechKey;
    }
    
    
    public void setPmgDtlTechKey(String value) {
        this.pmgDtlTechKey = value;
    }

    
    public String getCasePackId() {
        return casePackId;
    }
    
    
    public void setCasePackId(String value) {
        this.casePackId = value;
    }

    
    public String getProductName() {
        return productName;
    }
    
    
    public void setProductName(String value) {
        this.productName = value;
    }



	
	
	public LineProductRecordPojo asProductRecord() {
	    return this;
	}

}
