package pojo.dtar027.data;
 /*
  * *------------- Keep this notice in your final code ---------------
  * *   This code was generated by JRecord projects CodeGen program
  * *            on the: 5 Nov 2018 8:54:56 
  * *     from Copybook: DTAR027.cbl
  * *          Template: pojo
  * *             Split: None   
  * * File Organization: FixedWidth   
  * *              Font: cp037
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
  * v01  CodeGen        5 Nov 2018  Initial version
  *     (Bruce Martin)
  */
	
/**
 * This class is the Java representation of one record in the file
 */

import java.math.BigDecimal;


public class LineDtar027Pojo 
 {

    private String keycodeNo;
    private int qtySold;
    private BigDecimal salePrice;



    
    public String getKeycodeNo() {
        return keycodeNo;
    }
    
    
    public void setKeycodeNo(String value) {
        this.keycodeNo = value;
    }

    
    public int getQtySold() {
        return qtySold;
    }
    
    
    public void setQtySold(int value) {
        this.qtySold = value;
    }

    
    public BigDecimal getSalePrice() {
        return salePrice;
    }
    
    
    public void setSalePrice(BigDecimal value) {
        this.salePrice = value;
    }



	

}
