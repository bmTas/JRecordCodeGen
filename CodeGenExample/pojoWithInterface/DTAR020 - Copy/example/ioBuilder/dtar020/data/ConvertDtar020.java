package example.ioBuilder.dtar020.data;

 /*
  * *------------- Keep this notice in your final code ---------------
  * *   This code was generated by JRecord projects CodeGen program
  * *            on the: 4 Nov 2018 15:55:34 
  * *     from Copybook: DTAR020.cbl
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
  * v01  CodeGen        4 Nov 2018  Initial version
  *     (Bruce Martin)
  */

import java.io.IOException;


import net.sf.JRecord.cgen.impl.BasePojoConverter;
import net.sf.JRecord.def.IO.builders.ISchemaIOBuilder;

import net.sf.JRecord.Common.IFieldDetail;

import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.FieldValue;

public class ConvertDtar020 extends BasePojoConverter<LineDtar020Pojo> {
 
    public final IFieldDetail fldKeycodeNo;
    public final IFieldDetail fldStoreNo;
    public final IFieldDetail fldDate;
    public final IFieldDetail fldDeptNo;
    public final IFieldDetail fldQtySold;
    public final IFieldDetail fldSalePrice;

                  
    public ConvertDtar020(ISchemaIOBuilder lineCreator) throws IOException {
        super(lineCreator);
        
        LayoutDetail schema = lineCreator.getLayout();
        FieldNamesDtar020.RecordDtar020 fn
                   = FieldNamesDtar020.RECORD_DTAR020;
                  
        fldKeycodeNo = schema.getFieldFromName(fn.keycodeNo);
        fldStoreNo = schema.getFieldFromName(fn.storeNo);
        fldDate = schema.getFieldFromName(fn.date);
        fldDeptNo = schema.getFieldFromName(fn.deptNo);
        fldQtySold = schema.getFieldFromName(fn.qtySold);
        fldSalePrice = schema.getFieldFromName(fn.salePrice);


    }

    /*
     * This method setField was introduced for use in Generated Code.
     * It is marked as depreciated to discourage its use outside generated code.
     * Suppressing `deprecation` because this is where the method is supposed to
     * be used. 
     */        
    @SuppressWarnings("deprecation")
    @Override
    public LineDtar020Pojo toPojo(AbstractLine line) { 
        
        LineDtar020Pojo pojo = new LineDtar020Pojo();
        FieldValue u = new FieldValue(line, null);
        
        pojo.setKeycodeNo(u.setField(fldKeycodeNo).asString());   
        pojo.setStoreNo( (short) u.setField(fldStoreNo).asInt());   
        pojo.setDate(u.setField(fldDate).asInt());   
        pojo.setDeptNo( (short) u.setField(fldDeptNo).asInt());   
        pojo.setQtySold(u.setField(fldQtySold).asInt());   
        pojo.setSalePrice(u.setField(fldSalePrice).asBigDecimal());   
    


        return pojo;
    }

    /*
     * This method setField was introduced for use in Generated Code.
     * It is marked as depreciated to discourage its use outside generated code.
     * Suppressing `deprecation` because this is where the method is supposed to
     * be used. 
     */
    @SuppressWarnings("deprecation")
    @Override
    public void updateLine(AbstractLine line, LineDtar020Pojo pojo) {
        
        FieldValue u = new FieldValue(line, null);
        
       u.setField(fldKeycodeNo).set(pojo.getKeycodeNo());
       u.setField(fldStoreNo).set(pojo.getStoreNo());
       u.setField(fldDate).set(pojo.getDate());
       u.setField(fldDeptNo).set(pojo.getDeptNo());
       u.setField(fldQtySold).set(pojo.getQtySold());
       u.setField(fldSalePrice).set(pojo.getSalePrice());
    
    
    }
}