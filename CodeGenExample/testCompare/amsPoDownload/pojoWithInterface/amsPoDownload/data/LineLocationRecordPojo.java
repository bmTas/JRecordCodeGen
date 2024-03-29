package pojoWithInterface.amsPoDownload.data;
 /*
  * *------------- Keep this notice in your final code ---------------
  * *   This code was generated by JRecord projects CodeGen program
  * *            on the: 9.Jun.2001 0:0:0 
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
  * v01  CodeGen        9.Jun.2001  Initial version
  *     (Bruce Martin)
  */
	
/**
 * This class is the Java representation of one record in the file
 */

import java.util.Arrays;

import pojoWithInterface.amsPoDownload.def.ILocationRecord;
import pojoWithInterface.amsPoDownload.def.ILocationRecordPojo;



public class LineLocationRecordPojo extends LineAmspodownloadSchema 
implements ILocationRecordPojo {

    private String recordType;

    private short[] dcNumber;        
    private int[] packQuantity;        

    private int[] lengthDcNumber = {10};
    public LineLocationRecordPojo() {
        super( pojoWithInterface.amsPoDownload.def.DefsAmspodownload
                      .Records.LOCATION_RECORD );
                      
	
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
    public void set(ILocationRecord pojo) {
       recordType = pojo.getRecordType();
    
        for (int idx1 = 0; idx1 < pojo.getDcNumber(0); idx1++) {    
           dcNumber[idx1] = pojo.getDcNumber(idx1);
           packQuantity[idx1] = pojo.getPackQuantity(idx1);
        }
    
    }
	
	public LineLocationRecordPojo asLocationRecord() {
	    return this;
	}

}
