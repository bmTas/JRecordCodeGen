#! /bin/sh
## ---------------------------------------------------------------------------
##  Purpose: Generate JRecord code for a Single Record Cobol Copybook: DTAR027.cbl
##           using the pojo Template
##  Parameters:
##       -Template         : Code template to use      ~ pojo
##       -schema           : Cobol Copybook            ~ amsPoDownload.cbl
##       -FileOrganisation : File Type                 ~ FixedWidth - Constant (fixed) length records with no line separator
##       -font             : Character set of the file ~ CP037      - US Ebcdic  
##
##   Script Author: Bruce Martin
##  --------------------------------------------------------------------------

java -jar ../../lib/JRecordCodeGen.jar  \
               -Template           pojo \
               -package            pojo.dtar027 \
               -Schema             DTAR027.cbl   \
                 -FileOrganisation FixedWidth \
                 -font cp037 \
                -outputDirectory    DTAR027
               