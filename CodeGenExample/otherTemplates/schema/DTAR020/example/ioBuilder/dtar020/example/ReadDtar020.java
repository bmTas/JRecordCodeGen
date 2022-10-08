package example.ioBuilder.dtar020.example;
 /*
  *   Example program for DTAR020.cbl
  *   This class uses the JRecord project (https://sourceforge.net/projects/jrecord/)
  * -------------------------------------------------------------------
  *        
  * *------------- Keep this notice in your final code ---------------
  * *   This code was generated by JRecord projects CodeGen program
  * *            on the: 6 Nov 2018 12:47:8 
  * *     from Copybook: DTAR020.cbl
  * *          Template: schemaClass
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


import net.sf.JRecord.JRecordInterface1;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.IO.AbstractLineReader;
import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.External.CopybookLoader;
import net.sf.JRecord.def.IO.builders.ICobolIOBuilder;
                                         

import example.ioBuilder.dtar020.schema.RecordDtar020;
import example.ioBuilder.dtar020.schema.SchemaDtar020;
	

/**
 * Read Cobol file using a Cobol Copybook (Dtar020).
 *
 * This Generated program is intended as an example of using JRecord
 * rather than a useful program (that compiles - it wont).
 * You should regard it as a starting point and modify 
 * it according to needs
 */
public final class ReadDtar020 {

    private String testDir        = "G:/temp/";
    private String salesFile      = testDir + "DTAR020.bin";

    private String copybookName   = "DTAR020.cbl"; 
 
    /**
     * Example of LineReader  classes
     */
    public ReadDtar020() {
        super();

        AbstractLine line;
        int lineNum = 0;

        try {
            ICobolIOBuilder iob = JRecordInterface1.COBOL
                                .newIOBuilder(copybookName)
                                   .setFont("cp037") 
                                   .setFileOrganization(Constants.IO_FIXED_LENGTH)
                                   .setSplitCopybook(CopybookLoader.SPLIT_NONE)
                                   .setDropCopybookNameFromFields(true)
                                       ;  
            SchemaDtar020 schemaDtar020
                    = new SchemaDtar020(iob.getLayout()); 
            RecordDtar020 rDtar020 = schemaDtar020.recordDtar020;
            AbstractLineReader reader = iob.newReader(salesFile);
            while ((line = reader.read()) != null) {
                lineNum += 1;
                System.out.println(
                              line.getFieldValue(rDtar020.fldKeycodeNo).asString()
                      + " " + line.getFieldValue(rDtar020.fldStoreNo).asString()
                      + " " + line.getFieldValue(rDtar020.fldDate).asString()
                      + " " + line.getFieldValue(rDtar020.fldDeptNo).asString()
                      + " " + line.getFieldValue(rDtar020.fldQtySold).asString()
                      + " " + line.getFieldValue(rDtar020.fldSalePrice).asString()
                   );
            }

            reader.close();
        } catch (Exception e) {
            System.out.println("~~> " + lineNum + " " + e);
            System.out.println();

            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        new ReadDtar020();
    }
}

