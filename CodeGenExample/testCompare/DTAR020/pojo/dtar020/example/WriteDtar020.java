package pojo.dtar020.example;
 /*
  *   Example program for DTAR020.cbl
  *   This class uses the JRecord project (https://sourceforge.net/projects/jrecord/)
  * -------------------------------------------------------------------
  *        
  * *------------- Keep this notice in your final code ---------------
  * *   This code was generated by JRecord projects CodeGen program
  * *            on the: 9.Jun.2001 0:0:0 
  * *     from Copybook: DTAR020.cbl
  * *          Template: pojo
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
  * v01  CodeGen        9.Jun.2001  Initial version
  *     (Bruce Martin)
  *
  * ------------------------------------------------------------------ 
  */


import java.io.IOException;

import net.sf.JRecord.cgen.def.IWriter;
import net.sf.JRecord.cgen.impl.IoBuilder;


import pojo.dtar020.io.IoBuilderDtar020;
import pojo.dtar020.data.LineDtar020Pojo;


/**
 * Write Cobol file using a Cobol Copybook (Dtar020).
 *
 * This Generated program is intended as an example of using JRecord
 * rather than a useful program (that compiles - it wont).
 * You should regard it as a starting point and modify
 * it according to needs
 */
public final class WriteDtar020 {


    private String outputFileName = "";
    private String copybookName   = "DTAR020.cbl";

    
    /**
     * Example of LineReader  classes
     */
    public WriteDtar020() {
        super();

        try {
            IoBuilder<LineDtar020Pojo> iob = IoBuilderDtar020.newIoBuilder(copybookName);
            IWriter<LineDtar020Pojo> writer = iob.newWriter(outputFileName);
 
            writer.write(createDtar020(data));

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

    private LineDtar020Pojo createDtar020(SourceOfDataForTheCobolFile data)
    throws IOException {

        LineDtar020Pojo line = new LineDtar020Pojo();

        line.setKeycodeNo(data. ...);
        line.setStoreNo(data. ...);
        line.setDate(data. ...);
        line.setDeptNo(data. ...);
        line.setQtySold(data. ...);
        line.setSalePrice(data. ...);


        return line;
    }

    public static void main(String[] args) {
        new WriteDtar020();
    }
}

