rem * ---------------------------------------------------------------------------
rem *  Purpose: Generate JRecord code for a Single Record Cobol Copybook: DTAR020.cbl
rem *           using the lineWrapper Template
rem *  Parameters:
rem *       -Template         : Code template to use      ~ lineWrapper
rem *       -schema           : Cobol Copybook            ~ DTAR020.cbl
rem *       -FileOrganisation : File Type                 ~ FixedWidth - Constant (fixed) length records with no line separator
rem *       -font             : Character set of the file ~ CP037      - US Ebcdic
rem *
rem *   Script Author: Bruce Martin
rem * ---------------------------------------------------------------------------

java -jar ../../lib/JRecordCodeGen.jar  ^
              -Template           lineWrapper ^
              -package            lineWrapper.dtar020 ^
              -Schema             ../DTAR020.cbl   ^
                -FileOrganisation FixedWidth ^
                -font cp037 ^
              -outputDirectory    DTAR020

pause
^              