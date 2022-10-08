#! /bin/sh
## ---------------------------------------------------------------------------
##  Purpose: Generate JRecord code for a Single Record Cobol Copybook: DTAR020.cbl
##           using the standard Template
##  Parameters:
##       -Template         : Code template to use      ~ schemaClass & javaPojo
##       -schema           : Cobol Copybook            ~ amsPoDownload.cbl
##       -FileOrganisation : File Type                 ~ FixedWidth - Constant (fixed) length records with no line separator
##       -font             : Character set of the file ~ CP037      - US Ebcdic
##       -DropCopybookName : Remove the copybook name from the front of the field name.
##                           e.g. DTAR020-STORE is converted to STORE
##
##   Script Author: Bruce Martin
##  --------------------------------------------------------------------------

java -jar ../../lib/JRecordCodeGen.jar  \
               -Template           schemaClass  \
               -package            example.ioBuilder.dtar020 \
               -Schema             DTAR020.cbl   \
                 -FileOrganisation FixedWidth \
                 -font cp037 \
                 -DropCopybookName   true \
               -outputDirectory    schema/DTAR020

java -jar ../../lib/JRecordCodeGen.jar  \
               -Template           javaPojo  \
               -package            example.ioBuilder.dtar020 \
               -Schema             DTAR020.cbl   \
                 -FileOrganisation FixedWidth \
                 -font cp037 \
                 -DropCopybookName   true \
               -outputDirectory    pojo/DTAR020

pause
               