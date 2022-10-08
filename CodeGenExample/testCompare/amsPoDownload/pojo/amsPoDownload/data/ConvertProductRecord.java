package pojo.amsPoDownload.data;

 /*
  * *------------- Keep this notice in your final code ---------------
  * *   This code was generated by JRecord projects CodeGen program
  * *            on the: 9.Jun.2001 0:0:0 
  * *     from Copybook: amsPoDownload.cbl
  * *          Template: pojo
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

import java.io.IOException;


import net.sf.JRecord.cgen.impl.BasePojoConverter;
import net.sf.JRecord.def.IO.builders.ISchemaIOBuilder;

import net.sf.JRecord.Common.IFieldDetail;

import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.fieldValue.FieldValue;

public class ConvertProductRecord extends BasePojoConverter<LineProductRecordPojo> {
 
    public final IFieldDetail fldRecordType;
    public final IFieldDetail fldPackQty;
    public final IFieldDetail fldPackCost;
    public final IFieldDetail fldApn;
    public final IFieldDetail fldProduct;
    public final IFieldDetail fldPmgDtlTechKey;
    public final IFieldDetail fldCasePackId;
    public final IFieldDetail fldProductName;

                  
    public ConvertProductRecord(ISchemaIOBuilder lineCreator) throws IOException {
        super(lineCreator);
        
        LayoutDetail schema = lineCreator.getLayout();
        FieldNamesAmspodownload.RecordProductRecord fn
                   = FieldNamesAmspodownload.RECORD_PRODUCT_RECORD;
                  
        fldRecordType = schema.getFieldFromName(fn.recordType);
        fldPackQty = schema.getFieldFromName(fn.packQty);
        fldPackCost = schema.getFieldFromName(fn.packCost);
        fldApn = schema.getFieldFromName(fn.apn);
        fldProduct = schema.getFieldFromName(fn.product);
        fldPmgDtlTechKey = schema.getFieldFromName(fn.pmgDtlTechKey);
        fldCasePackId = schema.getFieldFromName(fn.casePackId);
        fldProductName = schema.getFieldFromName(fn.productName);


    }

    /*
     * This method setField was introduced for use in Generated Code.
     * It is marked as depreciated to discourage its use outside generated code.
     * Suppressing `deprecation` because this is where the method is supposed to
     * be used. 
     */        
    @SuppressWarnings("deprecation")
    @Override
    public LineProductRecordPojo toPojo(AbstractLine line) { 
        
        LineProductRecordPojo pojo = new LineProductRecordPojo();
        FieldValue u = new FieldValue(line, null);
        
        pojo.setRecordType(u.setField(fldRecordType).asString());   
        pojo.setPackQty(u.setField(fldPackQty).asBigDecimal());   
        pojo.setPackCost(u.setField(fldPackCost).asBigDecimal());   
        pojo.setApn(u.setField(fldApn).asLong());   
        pojo.setProduct(u.setField(fldProduct).asInt());   
        pojo.setPmgDtlTechKey(u.setField(fldPmgDtlTechKey).asString());   
        pojo.setCasePackId(u.setField(fldCasePackId).asString());   
        pojo.setProductName(u.setField(fldProductName).asString());   
    


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
    public void updateLine(AbstractLine line, LineProductRecordPojo pojo) {
        
        FieldValue u = new FieldValue(line, null);
        
        u.setField(fldRecordType).set(pojo.getRecordType());
        u.setField(fldPackQty).set(pojo.getPackQty());
        u.setField(fldPackCost).set(pojo.getPackCost());
        u.setField(fldApn).set(pojo.getApn());
        u.setField(fldProduct).set(pojo.getProduct());
        u.setField(fldPmgDtlTechKey).set(pojo.getPmgDtlTechKey());
        u.setField(fldCasePackId).set(pojo.getCasePackId());
        u.setField(fldProductName).set(pojo.getProductName());
    
    
    }
	
}
