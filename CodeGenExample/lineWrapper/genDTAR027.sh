#! /bin/sh

## ---------------------------------------------------------------------------
##  Purpose: Generate JRecord code for a Single Record Cobol Copybook: DTAR027.cbl
##           using the lineWrapper Template
##  Parameters:
##       -Template         : Code template to use      ~ lineWrapper
##       -schema           : Cobol Copybook            ~ amsPoDownload.cbl
##       -FileOrganisation : File Type                 ~ FixedWidth - Constant (fixed) length records with no line separator
##       -font             : Character set of the file ~ CP037      - US Ebcdic  
##
##   Script Author: Bruce Martin
##  --------------------------------------------------------------------------

java -jar ../../lib/JRecordCodeGen.jar  \
               -Template           lineWrapper \
               -package            lineWrapper.dtar027 \
               -Schema             DTAR027.cbl   \
                 -FileOrganisation FixedWidth \
                 -font cp037 \
                -outputDirectory    DTAR027
               