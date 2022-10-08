package pojoWithInterface.dtar027.data;
 /*
  * *------------- Keep this notice in your final code ---------------
  * *   This code was generated by JRecord projects CodeGen program
  * *            on the: 10 Aug 2017 9:58:18 
  * *     from Copybook: DTAR027.cbl
  * *          Template: pojoWithInterface
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
  * v01  CodeGen        10 Aug 2017  Initial version
  *     (Bruce Martin)
  */

import java.math.BigDecimal;

import pojoWithInterface.dtar027.def.IDtar027;
import pojoWithInterface.dtar027.def.IDtar027Pojo;



public class LineDtar027Pojo  implements IDtar027Pojo {

    private String keycodeNo;
    private int qtySold;
    private BigDecimal salePrice;



    @Override
    public String getKeycodeNo() {
        return keycodeNo;
    }
    
    @Override
    public void setKeycodeNo(String value) {
        this.keycodeNo = value;
    }

    @Override
    public int getQtySold() {
        return qtySold;
    }
    
    @Override
    public void setQtySold(int value) {
        this.qtySold = value;
    }

    @Override
    public BigDecimal getSalePrice() {
        return salePrice;
    }
    
    @Override
    public void setSalePrice(BigDecimal value) {
        this.salePrice = value;
    }



	
    public void set(IDtar027 pojo) {
       keycodeNo = pojo.getKeycodeNo();
       qtySold = pojo.getQtySold();
       salePrice = pojo.getSalePrice();
    
    }

}