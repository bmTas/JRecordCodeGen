package example;
 /*
  *   Example program for DTAR027.cbl
  *   This class uses the JRecord project (https://sourceforge.net/projects/jrecord/)
  * -------------------------------------------------------------------
  *        
  * *------------- Keep this notice in your final code ---------------
  * *   This code was generated by JRecord projects CodeGen program
  * *            on the: 6 Nov 2018 10:46:43 
  * *     from Copybook: DTAR027.cbl
  * *          Template: xBasic
  * *             Split: None   
  * * File Organization: FixedWidth   
  * *              Font: cp037
  * *   
  * *    CodeGen Author: Bruce Martin
  * *-----------------------------------------------------------------
  *
  *   This class should be used as an example of reading/writing files
  *   using JRecord. You will need to modify the code to meet your
  *   needs. The Author of CodeGen (Bruce Martin) program takes no 
  *   responsibility for the accuracy of the generated code. 
  *
  *   Good Luck
  *              Bruce Martin
  *
  * ------------------------------------------------------------------
  * v01  CodeGen        6 Nov 2018  Initial version
  *     (Bruce Martin)
  *
  * ------------------------------------------------------------------ 
  */


import java.io.IOException;

import net.sf.JRecord.JRecordInterface1;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.External.CopybookLoader;
import net.sf.JRecord.def.IO.builders.ICobolIOBuilder;
                                         
import net.sf.JRecord.IO.AbstractLineWriter;

/**
 * Write Cobol file using a Cobol Copybook (Dtar027).
 *
 * This Generated program is intended as an example of using JRecord
 * rather than a useful program (that compiles - it wont).
 * You should regard it as a starting point and modify 
 * it according to needs
 */
public final class WriteDtar027 {

    private String testDir        = "G:/temp/";
    private String salesFileOut   = testDir + "DTAR020out.bin";
    private String copybookName   = "DTAR027.cbl"; 
                              
    /**
     * Example of LineReader  classes
     */
    public WriteDtar027() {
        super();

        try {
            ICobolIOBuilder iob = JRecordInterface1.COBOL
                                .newIOBuilder(copybookName)
                                   .setFont("cp037") 
                                   .setFileOrganization(Constants.IO_FIXED_LENGTH)
                                   .setSplitCopybook(CopybookLoader.SPLIT_NONE)
                                       ;  
            AbstractLineWriter writer = iob.newWriter(salesFileOut);
 
            writer.write(createDtar027(iob, data));

            writer.close();
        } catch (Exception e) {
             System.out.println();

            e.printStackTrace();
        }
    }

    private AbstractLine createDtar027(ICobolIOBuilder iob, UserData data) throws IOException {
        AbstractLine l = iob.newLine();    
        
        l.getFieldValue("DTAR027-KEYCODE-NO").set(data. ...);
        l.getFieldValue("DTAR027-QTY-SOLD").set(data. ...);
        l.getFieldValue("DTAR027-SALE-PRICE").set(data. ...);
    
        return l;
    }

    public static void main(String[] args) {
        new WriteDtar027();
    }
}

