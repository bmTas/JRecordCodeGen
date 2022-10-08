package example.ioBuilder.dtar020.io;

 /*
  * *------------- Keep this notice in your final code ---------------
  * *   This code was generated by JRecord projects CodeGen program
  * *            on the: 5 Nov 2018 16:39:53 
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
  * v01  CodeGen        5 Nov 2018  Initial version
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



import example.ioBuilder.dtar020.data.ConvertDtar020;
import example.ioBuilder.dtar020.data.LineDtar020Pojo;

public class IoBuilderDtar020  {

   public static IoBuilder<LineDtar020Pojo> newIoBuilder(String copybookName) {

        return createIoBuilder(updateIoBuilder(
                                       JRecordInterface1.COBOL
                                            .newIOBuilder(copybookName)));
    }
    
   public static IoBuilder<LineDtar020Pojo> newIoBuilder(InputStream copybookStream, String copybookName) {

        return createIoBuilder(updateIoBuilder(
                                       JRecordInterface1.COBOL
                                            .newIOBuilder(copybookStream, copybookName)));
   }
    
   private static IoBuilder<LineDtar020Pojo> createIoBuilder(ICobolIOBuilder iob) {
    
       try {
            /** IoBuilder requires JRecord Version 0.81.5 or later */
           return new IoBuilder<LineDtar020Pojo>(new ConvertDtar020(iob), iob);
       } catch (IOException e) {
           throw new RuntimeException(e);
       }
    }


   private static ICobolIOBuilder updateIoBuilder(ICobolIOBuilder iob) {
    
        return iob
                                   .setFont("cp037") 
                                   .setFileOrganization(Constants.IO_FIXED_LENGTH)
                                   .setSplitCopybook(CopybookLoader.SPLIT_NONE)
                                   .setDropCopybookNameFromFields(true)
        ;
   }
    
}