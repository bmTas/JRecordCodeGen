package example.ioBuilder.amsPoDownload.data;

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

import java.io.IOException;


import net.sf.JRecord.cgen.impl.BasePojoConverter;
import net.sf.JRecord.def.IO.builders.ISchemaIOBuilder;

import net.sf.JRecord.Common.IFieldDetail;

import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.fieldValue.FieldValue;

public class ConvertPoRecord extends BasePojoConverter<LinePoRecordPojo> {
 
    public final IFieldDetail fldRecordType;
    public final IFieldDetail fldSequenceNumber;
    public final IFieldDetail fldVendor;
    public final IFieldDetail fldPo;
    public final IFieldDetail fldEntryDate;
    public final IFieldDetail fldBeg01Code;
    public final IFieldDetail fldBeg02Code;
    public final IFieldDetail fldDepartment;
    public final IFieldDetail fldExpectedRecieptDate;
    public final IFieldDetail fldCancelByDate;
    public final IFieldDetail fldEdiType;
    public final IFieldDetail fldAddDate;
    public final IFieldDetail fldDepartmentName;
    public final IFieldDetail fldPrcoessType;
    public final IFieldDetail fldOrderType;

                  
    public ConvertPoRecord(ISchemaIOBuilder lineCreator) throws IOException {
        super(lineCreator);
        
        LayoutDetail schema = lineCreator.getLayout();
        FieldNamesAmspodownload.RecordPoRecord fn
                   = FieldNamesAmspodownload.RECORD_PO_RECORD;
                  
        fldRecordType = schema.getFieldFromName(fn.recordType);
        fldSequenceNumber = schema.getFieldFromName(fn.sequenceNumber);
        fldVendor = schema.getFieldFromName(fn.vendor);
        fldPo = schema.getFieldFromName(fn.po);
        fldEntryDate = schema.getFieldFromName(fn.entryDate);
        fldBeg01Code = schema.getFieldFromName(fn.beg01Code);
        fldBeg02Code = schema.getFieldFromName(fn.beg02Code);
        fldDepartment = schema.getFieldFromName(fn.department);
        fldExpectedRecieptDate = schema.getFieldFromName(fn.expectedRecieptDate);
        fldCancelByDate = schema.getFieldFromName(fn.cancelByDate);
        fldEdiType = schema.getFieldFromName(fn.ediType);
        fldAddDate = schema.getFieldFromName(fn.addDate);
        fldDepartmentName = schema.getFieldFromName(fn.departmentName);
        fldPrcoessType = schema.getFieldFromName(fn.prcoessType);
        fldOrderType = schema.getFieldFromName(fn.orderType);


    }

    /*
     * This method setField was introduced for use in Generated Code.
     * It is marked as depreciated to discourage its use outside generated code.
     * Suppressing `deprecation` because this is where the method is supposed to
     * be used. 
     */        
    @SuppressWarnings("deprecation")
    @Override
    public LinePoRecordPojo toPojo(AbstractLine line) { 
        
        LinePoRecordPojo pojo = new LinePoRecordPojo();
        FieldValue u = new FieldValue(line, null);
        
        pojo.setRecordType(u.setField(fldRecordType).asString());   
        pojo.setSequenceNumber(u.setField(fldSequenceNumber).asBigDecimal());   
        pojo.setVendor(u.setField(fldVendor).asLong());   
        pojo.setPo(u.setField(fldPo).asLong());   
        pojo.setEntryDate(u.setField(fldEntryDate).asString());   
        pojo.setBeg01Code(u.setField(fldBeg01Code).asString());   
        pojo.setBeg02Code(u.setField(fldBeg02Code).asString());   
        pojo.setDepartment(u.setField(fldDepartment).asString());   
        pojo.setExpectedRecieptDate(u.setField(fldExpectedRecieptDate).asString());   
        pojo.setCancelByDate(u.setField(fldCancelByDate).asString());   
        pojo.setEdiType(u.setField(fldEdiType).asString());   
        pojo.setAddDate(u.setField(fldAddDate).asString());   
        pojo.setDepartmentName(u.setField(fldDepartmentName).asString());   
        pojo.setPrcoessType(u.setField(fldPrcoessType).asString());   
        pojo.setOrderType(u.setField(fldOrderType).asString());   
    


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
    public void updateLine(AbstractLine line, LinePoRecordPojo pojo) {
        
        FieldValue u = new FieldValue(line, null);
        
       u.setField(fldRecordType).set(pojo.getRecordType());
       u.setField(fldSequenceNumber).set(pojo.getSequenceNumber());
       u.setField(fldVendor).set(pojo.getVendor());
       u.setField(fldPo).set(pojo.getPo());
       u.setField(fldEntryDate).set(pojo.getEntryDate());
       u.setField(fldBeg01Code).set(pojo.getBeg01Code());
       u.setField(fldBeg02Code).set(pojo.getBeg02Code());
       u.setField(fldDepartment).set(pojo.getDepartment());
       u.setField(fldExpectedRecieptDate).set(pojo.getExpectedRecieptDate());
       u.setField(fldCancelByDate).set(pojo.getCancelByDate());
       u.setField(fldEdiType).set(pojo.getEdiType());
       u.setField(fldAddDate).set(pojo.getAddDate());
       u.setField(fldDepartmentName).set(pojo.getDepartmentName());
       u.setField(fldPrcoessType).set(pojo.getPrcoessType());
       u.setField(fldOrderType).set(pojo.getOrderType());
    
    
    }
}
