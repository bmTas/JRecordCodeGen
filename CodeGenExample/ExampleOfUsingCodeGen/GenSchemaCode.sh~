rem * ---------------------------------------------------------------------------
rem *  Purpose: Generate JRecord code for a Single Record Cobol Copybook: JR_Schema_Test.cbl
rem *           using the lineWrapper Template
rem *  Parameters:
rem *       -Template         : Code template to use      ~ lineWrapper
rem *       -schema           : Cobol Copybook            ~ JR_Schema_Test.cbl
rem *       -FileOrganisation : File Type                 ~ FixedWidth - Constant (fixed) length records with no line separator
rem *       -splir            : split copybook            ~ on 01 level
rem *
rem *   Script Author: Bruce Martin
rem * ---------------------------------------------------------------------------

java -jar ../../lib/JRecordCodeGen.jar  ^
              -Template           lineWrapper ^
              -package            net.sf.JRecord.test.schema.cobol.gen ^
              -Schema             JR_Schema_Test.cbl   ^
                -FileOrganisation Text ^
                -split 01 ^
              -outputDirectory    JR_Schema_Test
pause       