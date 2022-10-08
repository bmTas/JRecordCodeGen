#! /bin/sh
## ---------------------------------------------------------------------------
##  Purpose: Generate JRecord code for a Single Record Cobol Copybook: DTAR020.cbl
##           using the stdPojo Template
##  Parameters:
##       -Template         : Code template to use      ~ stdPojo
##       -schema           : Cobol Copybook            ~ amsPoDownload.cbl
##       -FileOrganisation : File Type                 ~ FixedWidth - Constant (fixed) length records with no line separator
##       -font             : Character set of the file ~ CP037      - US Ebcdic  
##
##   Script Author: Bruce Martin
##  --------------------------------------------------------------------------

java -jar ../../lib/JRecordCodeGen.jar  \
               -Template           lineWrapperPojo \
               -package            lineWrapperPojo.AmsPoDownload \
               -Schema             amsPoDownload.cbl   \
                 -FileOrganisation Text \
                 -split 01 \
               -outputDirectory    amsPoDownload
               
