package pojoWithInterface.amsPoDownload.io;

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

import java.io.InputStream;
import java.io.IOException;

import net.sf.JRecord.JRecordInterface1;
import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.External.CopybookLoader;
/** This requires JRecord Version 0.81.5 or later */
import net.sf.JRecord.cgen.impl.IoBuilder;
import net.sf.JRecord.def.IO.builders.ICobolIOBuilder;

import pojoWithInterface.amsPoDownload.def.DefsAmspodownload;


import pojoWithInterface.amsPoDownload.data.ConvertPoRecord;
import pojoWithInterface.amsPoDownload.data.ConvertProductRecord;
import pojoWithInterface.amsPoDownload.data.ConvertLocationRecord;
import net.sf.JRecord.cgen.impl.BasePojoConverter;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.LayoutDetail;
import pojoWithInterface.amsPoDownload.data.LineAmspodownloadSchema;

public class IoBuilderAmspodownload  {

   public static IoBuilder<LineAmspodownloadSchema> newIoBuilder(String copybookName) {

        return createIoBuilder(updateIoBuilder(
                                       JRecordInterface1.COBOL
                                            .newIOBuilder(copybookName)));
    }
    
   public static IoBuilder<LineAmspodownloadSchema> newIoBuilder(InputStream copybookStream, String copybookName) {

        return createIoBuilder(updateIoBuilder(
                                       JRecordInterface1.COBOL
                                            .newIOBuilder(copybookStream, copybookName)));
   }
    
   private static IoBuilder<LineAmspodownloadSchema> createIoBuilder(ICobolIOBuilder iob) {
    
       try {
            /** IoBuilder requires JRecord Version 0.81.5 or later */
           return new IoBuilder<LineAmspodownloadSchema>(new ConvertSchemaAmspodownload(iob), iob);
       } catch (IOException e) {
           throw new RuntimeException(e);
       }
    }


   private static ICobolIOBuilder updateIoBuilder(ICobolIOBuilder iob) {
    
        return iob
                                   //.setFont("") // Think about specifying an encoding !!!
                                   .setFileOrganization(Constants.IO_BIN_TEXT)
                                   .setSplitCopybook(CopybookLoader.SPLIT_01_LEVEL)
         //TODO   --    Setup/Check record Selection !!!
         //TODO     .setRecordSelection( "PO-Record", ExternalFieldSelection.newFieldSelection("field-name", "value to test"))  
         //TODO     .setRecordSelection( "Product-Record", ExternalFieldSelection.newFieldSelection("field-name", "value to test"))  
         //TODO     .setRecordSelection( "Location-Record", ExternalFieldSelection.newFieldSelection("field-name", "value to test"))  
          //TODO Update the record Selection if required
        ;
   }
   private static class ConvertSchemaAmspodownload 
   extends BasePojoConverter<LineAmspodownloadSchema> {

        private final ICobolIOBuilder iob;
        private LayoutDetail schema;
     
        private final ConvertPoRecord convertPoRecord;
        private final ConvertProductRecord convertProductRecord;
        private final ConvertLocationRecord convertLocationRecord;

        public ConvertSchemaAmspodownload(ICobolIOBuilder iob) throws IOException  {
            super(iob);
        
            this.iob = iob;
            this.schema = iob.getLayout();
           
            this.convertPoRecord = new ConvertPoRecord(iob);
            this.convertProductRecord = new ConvertProductRecord(iob);
            this.convertLocationRecord = new ConvertLocationRecord(iob);
        }
        
        @Override
        public LineAmspodownloadSchema toPojo(AbstractLine line) { 
         	int recordIdx = line.getPreferredLayoutIdxAlt();
        	
        	if (recordIdx >= 0) {
				switch (DefsAmspodownload.recordFromString(
	        				schema.getRecord(recordIdx)
	        				         .getRecordName())) {
	        	case PO_RECORD :
	        		return convertPoRecord.toPojo(line);
	        	case PRODUCT_RECORD :
	        		return convertProductRecord.toPojo(line);
	        	case LOCATION_RECORD :
	        		return convertLocationRecord.toPojo(line);
	        	}
        	}
        	
        	throw new RuntimeException("Unkown Record Type for line: " + line.getFullLine() );       
        }
   
        
        @Override
        public void updateLine(AbstractLine line, LineAmspodownloadSchema pojo) {
            switch (pojo.generatedRecordType()) {
            case PO_RECORD :
                convertPoRecord.updateLine(line, pojo.asPoRecord());
                break;
            case PRODUCT_RECORD :
                convertProductRecord.updateLine(line, pojo.asProductRecord());
                break;
            case LOCATION_RECORD :
                convertLocationRecord.updateLine(line, pojo.asLocationRecord());
                break;
           }
        }
    }
    
}