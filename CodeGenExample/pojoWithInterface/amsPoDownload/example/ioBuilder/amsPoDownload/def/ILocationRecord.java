package example.ioBuilder.amsPoDownload.def;
 /*
  * *------------- Keep this notice in your final code ---------------
  * *   This code was generated by JRecord projects CodeGen program
  * *            on the: 5 Nov 2018 16:40:56 
  * *     from Copybook: amsPoDownload.cbl
  * *          Template: pojoWithInterface
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
  * v01  CodeGen        5 Nov 2018  Initial version
  *     (Bruce Martin)
  */


public interface ILocationRecord {

    public String getRecordType();

    public short  getDcNumber(int idx1);
    public int  getPackQuantity(int idx1);
	

    public int  getDcNumberArrayLength(int indexNumber);
}

