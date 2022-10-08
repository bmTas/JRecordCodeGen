package example.ioBuilder.dtar020.data;
 /*
  * *------------- Keep this notice in your final code ---------------
  * *   This code was generated by JRecord projects CodeGen program
  * *            on the: 9 Aug 2017   :  :   
  * *     from Copybook: DTAR020.cbl
  * *          Template: javaPojo
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
  * v01  CodeGen        9 Aug 2017  Initial version
  *     (Bruce Martin)
  */

import java.math.BigDecimal;
                                                                             
import net.sf.JRecord.Details.IGetByteData;
import net.sf.JRecord.cgen.def.IAsPojoSetData;
import net.sf.JRecord.cgen.def.ILineToBytes;
import net.sf.JRecord.cgen.impl.FieldValueCG;
	
import example.ioBuilder.dtar020.def.IDtar020;
import example.ioBuilder.dtar020.def.IDtar020Pojo;  
import example.ioBuilder.dtar020.schema.RecordDtar020;  

public class LineDtar020JR 
implements IDtar020Pojo, IGetByteData, ILineToBytes<IDtar020>, IAsPojoSetData<LineDtar020Pojo> {

    public static final byte[] EMPTY_BYTE_ARRAY = {};
    
    private byte[] data = EMPTY_BYTE_ARRAY;
    private final RecordDtar020 record;

    private FieldValueCG keycodeNo;
    private FieldValueCG storeNo;
    private FieldValueCG date;
    private FieldValueCG deptNo;
    private FieldValueCG qtySold;
    private FieldValueCG salePrice;
                                                                             

    public LineDtar020JR(RecordDtar020 rec) {
    
        record = rec;

        keycodeNo = new FieldValueCG(this, rec.fldKeycodeNo);
        storeNo = new FieldValueCG(this, rec.fldStoreNo);
        date = new FieldValueCG(this, rec.fldDate);
        deptNo = new FieldValueCG(this, rec.fldDeptNo);
        qtySold = new FieldValueCG(this, rec.fldQtySold);
        salePrice = new FieldValueCG(this, rec.fldSalePrice);
    }

    @Override
    public String getKeycodeNo() {
        return keycodeNo.asString();
    }
    
    @Override
    public void setKeycodeNo(String value) {
        this.keycodeNo.set(value);
    }

    @Override
    public short getStoreNo() {
        return (short) storeNo.asInt();                                 
    }
    
    @Override
    public void setStoreNo(short value) {
        this.storeNo.set(value);
    }

    @Override
    public int getDate() {
        return date.asInt();
    }
    
    @Override
    public void setDate(int value) {
        this.date.set(value);
    }

    @Override
    public short getDeptNo() {
        return (short) deptNo.asInt();                                 
    }
    
    @Override
    public void setDeptNo(short value) {
        this.deptNo.set(value);
    }

    @Override
    public int getQtySold() {
        return qtySold.asInt();
    }
    
    @Override
    public void setQtySold(int value) {
        this.qtySold.set(value);
    }

    @Override
    public BigDecimal getSalePrice() {
        return salePrice.asBigDecimal();
    }
    
    @Override
    public void setSalePrice(BigDecimal value) {
        this.salePrice.set(value);
    }


	
	

    @Override
    public byte[] getData() {
        return data;  
    }

    @Override
    public void setData(byte[] data) {
        this.data = data;
    }
    
    public LineDtar020Pojo asPojo() {
        LineDtar020Pojo l = new LineDtar020Pojo();
        l.set(this);
        return l;
    }
    
    public void set(IDtar020 value) {
        CodeDtar020
            .assignDtar020(
                this, value);
    }
}

