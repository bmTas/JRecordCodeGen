package standard.dtar027.example;
 /*
  *   Example program for DTAR027.cbl
  *   This class uses the JRecord project (https://sourceforge.net/projects/jrecord/)
  * -------------------------------------------------------------------
  *        
  * *------------- Keep this notice in your final code ---------------
  * *   This code was generated by JRecord projects CodeGen program
  * *            on the: 6 Nov 2018 9:13:55 
  * *     from Copybook: DTAR027.cbl
  * *          Template: standard
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

import standard.dtar027.schema.FieldNamesDtar027;


/**
 * Write Cobol file using a Cobol Copybook (Dtar027).
 *
 * This Generated program is intended as an example of using JRecord
 * rather than a useful program (that compiles - it wont).
 * You should regard it as a starting point and modify
 * it according to needs
 */
public final class WriteDtar027 {


    private String outputFileName = "";
    private String copybookName   = "DTAR027.cbl";

    private FieldNamesDtar027.RecordDtar027 rDtar027 = FieldNamesDtar027.RECORD_DTAR027;


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
            AbstractLineWriter writer = iob.newWriter(outputFileName);

            writer.write(createDtar027(iob, data));

            writer.close();
        } catch (Exception e) {
             System.out.println();

            e.printStackTrace();
        }
    }


   /*
   *   The following code contains sample assignments for every 
   * field in the Cobol File. You should modify the code to suit
   * your needs.
   *   As I do not know where the data is coming from, I have used
   * SourceOfDataForTheCobolFile instead. You should replace this with your
   * class / classes  or remove it as needed.
   *   To put it another way, it time for you to start Coding
   */


    private AbstractLine createDtar027(ICobolIOBuilder iob, SourceOfDataForTheCobolFile data) 
    throws IOException {
        AbstractLine line = iob.newLine();

        line.getFieldValue(rDtar027.keycodeNo).set(data. ...);
        line.getFieldValue(rDtar027.qtySold).set(data. ...);
        line.getFieldValue(rDtar027.salePrice).set(data. ...);


        return line;
    }

    public static void main(String[] args) {
        new WriteDtar027();
    }
}

