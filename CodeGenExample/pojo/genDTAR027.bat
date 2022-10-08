rem * ---------------------------------------------------------------------------
rem *  Purpose: Generate JRecord code for a Single Record Cobol Copybook: DTAR027.cbl
rem *           using the pojo Template
rem *  Parameters:
rem *       -Template         : Code template to use      ~ pojo
rem *       -schema           : Cobol Copybook            ~ amsPoDownload.cbl
rem *       -FileOrganisation : File Type                 ~ FixedWidth - Constant (fixed) length records with no line separator
rem *       -font             : Character set of the file ~ CP037      - US Ebcdic
rem *
rem *   Script Author: Bruce Martin
rem * ---------------------------------------------------------------------------

java -jar ../../lib/JRecordCodeGen.jar  ^
              -Template           pojo ^
              -package            pojo.dtar027 ^
              -Schema             DTAR027.cbl   ^
                -FileOrganisation FixedWidth ^
                -font cp037 ^
              -outputDirectory    DTAR027

pause
^              