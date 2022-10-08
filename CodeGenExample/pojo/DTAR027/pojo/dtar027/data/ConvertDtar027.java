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

import java.io.IOException;


import net.sf.JRecord.cgen.impl.BasePojoConverter;
import net.sf.JRecord.def.IO.builders.ISchemaIOBuilder;

import net.sf.JRecord.Common.IFieldDetail;

import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.FieldValue;

public class ConvertDtar027 extends BasePojoConverter<LineDtar027Pojo> {
 
    public final IFieldDetail fldKeycodeNo;
    public final IFieldDetail fldQtySold;
    public final IFieldDetail fldSalePrice;

                  
    public ConvertDtar027(ISchemaIOBuilder lineCreator) throws IOException {
        super(lineCreator);
        
        LayoutDetail schema = lineCreator.getLayout();
        FieldNamesDtar027.RecordDtar027 fn
                   = FieldNamesDtar027.RECORD_DTAR027;
                  
        fldKeycodeNo = schema.getFieldFromName(fn.keycodeNo);
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
    public LineDtar027Pojo toPojo(AbstractLine line) { 
        
        LineDtar027Pojo pojo = new LineDtar027Pojo();
        FieldValue u = new FieldValue(line, null);
        
        pojo.setKeycodeNo(u.setField(fldKeycodeNo).asString());   
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
    public void updateLine(AbstractLine line, LineDtar027Pojo pojo) {
        
        FieldValue u = new FieldValue(line, null);
        
       u.setField(fldKeycodeNo).set(pojo.getKeycodeNo());
       u.setField(fldQtySold).set(pojo.getQtySold());
       u.setField(fldSalePrice).set(pojo.getSalePrice());
    
    
    }
}
