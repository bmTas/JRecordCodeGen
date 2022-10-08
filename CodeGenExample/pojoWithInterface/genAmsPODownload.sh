#! /bin/sh
## ---------------------------------------------------------------------------
##  Purpose: Generate JRecord code for a Multi Record Cobol Copybook: amsPoDownload.cbl
##  Parameters:
##       -Template         : Code template to use      ~ pojoWithInterface
##       -schema           : Cobol Copybook            ~ amsPoDownload.cbl
##       -FileOrganisation : File Type                 ~ Text - pojoWithInterface text file with \n line seperators
##       -split            : How to split the Copybook ~ 01 
##                           into separate records
##       -package          : Java Package for the "Field-Name" class
##       -outputDirectory  : where to write the generated code
##
##   Script Author: Bruce Martin
## --------------------------------------------------------------------------

java -jar ../../lib/JRecordCodeGen.jar  \
               -Template         pojoWithInterface \
               -package          example.ioBuilder.amsPoDownload \
               -Schema           amsPoDownload.cbl   \
                 -FileOrganisation Text \
                 -split 01 \
               -outputDirectory  amsPoDownload
                