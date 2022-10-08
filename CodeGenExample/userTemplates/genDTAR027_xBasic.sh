#! /bin/sh
## ---------------------------------------------------------------------------
##  Purpose: Generate JRecord code for a Single Record Cobol Copybook: DTAR027.cbl
##           using the xBasic Template
##  Parameters:
##       -userWrittenTemplates: Directory holding the Template (xBasic) 
##       -Template             : Code template to use      ~ xBasic
##       -schema               : Cobol Copybook            ~ amsPoDownload.cbl
##       -FileOrganisation     : File Type                 ~ FixedWidth - Constant (fixed) length records with no line separator
##       -font                 : Character set of the file ~ CP037      - US Ebcdic  
##
##   Script Author: Bruce Martin
##  --------------------------------------------------------------------------

java -jar ../../lib/JRecordCodeGen.jar  \
               -TemplateDirectory  userWrittenTemplates \
               -Template           xBasic \
               -Schema             DTAR027.cbl   \
                 -FileOrganisation FixedWidth \
                 -font             cp037 \
               -outputDirectory    DTAR027_xBasic
               

java -jar ../../lib/JRecordCodeGen.jar  \
               -TemplateDirectory  userWrittenTemplates/xBasic \
               -Schema             DTAR027.cbl   \
                 -FileOrganisation FixedWidth \
                 -font             cp037 \
               -outputDirectory    DTAR027_xBasic1
