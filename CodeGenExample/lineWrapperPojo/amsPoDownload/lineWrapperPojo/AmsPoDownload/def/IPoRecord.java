package lineWrapperPojo.AmsPoDownload.def;
 /*
  * *------------- Keep this notice in your final code ---------------
  * *   This code was generated by JRecord projects CodeGen program
  * *            on the: 6 Nov 2018 8:28:53 
  * *     from Copybook: amsPoDownload.cbl
  * *          Template: lineWrapperPojo
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
  * v01  CodeGen        6 Nov 2018  Initial version
  *     (Bruce Martin)
  */

import java.math.BigDecimal;

public interface IPoRecord {

    public String getRecordType();
    public BigDecimal getSequenceNumber();
    public long getVendor();
    public long getPo();
    public String getEntryDate();
    public String getBeg01Code();
    public String getBeg02Code();
    public String getDepartment();
    public String getExpectedRecieptDate();
    public String getCancelByDate();
    public String getEdiType();
    public String getAddDate();
    public String getDepartmentName();
    public String getPrcoessType();
    public String getOrderType();

	

}

