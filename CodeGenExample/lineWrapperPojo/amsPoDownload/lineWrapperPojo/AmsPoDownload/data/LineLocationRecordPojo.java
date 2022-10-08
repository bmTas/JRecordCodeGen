package lineWrapperPojo.AmsPoDownload.data;
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
	
/**
 * This class is the Java representation of one record in the file
 */

import java.util.Arrays;

import lineWrapperPojo.AmsPoDownload.def.ILocationRecord;
import lineWrapperPojo.AmsPoDownload.def.ILocationRecordPojo;



public class LineLocationRecordPojo 
implements ILocationRecordPojo {

    private String recordType;

    private short[] dcNumber;        
    private int[] packQuantity;        

    private int[] lengthDcNumber = {10};
    public LineLocationRecordPojo() {
	
        dcNumber  = new short[lengthDcNumber[0]];       	
        packQuantity  = new int[lengthDcNumber[0]];       	
        Arrays.fill(dcNumber , (short) 0);      	
        Arrays.fill(packQuantity , 0);      	

    }

    @Override
    public String getRecordType() {
        return recordType;
    }
    
    @Override
    public void setRecordType(String value) {
        this.recordType = value;
    }


    @Override
    public short  getDcNumber(int idx1) {
        return dcNumber[idx1];
    }
  
    @Override
    public void setDcNumber(int idx1, short value) {
        dcNumber[idx1] = value;
    }

    @Override
    public int  getPackQuantity(int idx1) {
        return packQuantity[idx1];
    }
  
    @Override
    public void setPackQuantity(int idx1, int value) {
        packQuantity[idx1] = value;
    }


	
    public int  getDcNumberArrayLength(int indexNumber) {
        return lengthDcNumber[indexNumber];
    }
	
    public void set(ILocationRecord value) {
        CodeAmspodownload
            .assignLocationRecord(
                this, value);
    }

}
