package lineWrapperPojo.dtar020.def;
 /*
  * *------------- Keep this notice in your final code ---------------
  * *   This code was generated by JRecord projects CodeGen program
  * *            on the: 5 Nov 2018 22:37:32 
  * *     from Copybook: DTAR020.cbl
  * *          Template: lineWrapperPojo
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

import java.math.BigDecimal;

public interface IDtar020Upd {

    public abstract void setKeycodeNo(String value);
    public abstract void setStoreNo(short value);
    public abstract void setDate(int value);
    public abstract void setDeptNo(short value);
    public abstract void setQtySold(int value);
    public abstract void setSalePrice(BigDecimal value);




    public abstract void set(IDtar020 value);
}

