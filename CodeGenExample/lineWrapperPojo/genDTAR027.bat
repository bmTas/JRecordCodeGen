rem * ---------------------------------------------------------------------------
rem *  Purpose: Generate JRecord code for a Single Record Cobol Copybook: DTAR027.cbl
rem *           using the stdPojo Template
rem *  Parameters:
rem *       -Template         : Code template to use      ~ stdPojo
rem *       -schema           : Cobol Copybook            ~ DTAR027.cbl
rem *       -FileOrganisation : File Type                 ~ FixedWidth - Constant (fixed) length records with no line separator
rem *       -font             : Character set of the file ~ CP037      - US Ebcdic
rem *
rem *   Script Author: Bruce Martin
rem * ---------------------------------------------------------------------------

java -jar ../../lib/JRecordCodeGen.jar  ^
              -Template           lineWrapperPojo ^
              -package            lineWrapperPojo.dtar027 ^
              -Schema             DTAR027.cbl   ^
                -FileOrganisation FixedWidth ^
                -font cp037 ^
              -outputDirectory    DTAR027

pause
^              
