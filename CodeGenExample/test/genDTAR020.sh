#! /bin/sh
## ---------------------------------------------------------------------------
##  Purpose: Generate JRecord code for a Single Record Cobol Copybook: DTAR020.cbl
##           using the pojo Template
##  Parameters:
##       -Template         : Code template to use      ~ pojo
##       -schema           : Cobol Copybook            ~ amsPoDownload.cbl
##       -FileOrganisation : File Type                 ~ FixedWidth - Constant (fixed) length records with no line separator
##       -font             : Character set of the file ~ CP037      - US Ebcdic  
##       -DropCopybookName : Remove the copybook name from the front of the field name.
##                           e.g. DTAR020-STORE is converted to STORE
##
##   Script Author: Bruce Martin
##  --------------------------------------------------------------------------

java -jar ../../lib/JRecordCodeGen.jar  \
               -Template           pojo \
               -package            pojo.dtar020 \
               -Schema             DTAR020.cbl   \
                 -FileOrganisation FixedWidth \
                 -font cp037 \
                 -DropCopybookName   true \
               -outputDirectory    DTAR020 \
               -GenerateDate     9.Jun.2001
 java -jar ../../lib/JRecordCodeGen.jar  \
               -Template           pojo \
               -package            pojo.dtar020v90 \
               -Schema             DTAR020.cbl   \
                 -FileOrganisation FixedWidth \
                 -font cp037 \
                 -DropCopybookName   true \
               -outputDirectory    DTAR020 \
               -GenerateDate     9.Jun.2001 \
               -JRecordVersion 0.90
              
java -jar ../../lib/JRecordCodeGen.jar  \
               -Template           standard \
               -package            standard.dtar020 \
               -Schema             DTAR020.cbl   \
                 -FileOrganisation FixedWidth \
                 -font cp037 \
                 -DropCopybookName   true \
               -outputDirectory    DTAR020 \
               -GenerateDate     9.Jun.2001
               
java -jar ../../lib/JRecordCodeGen.jar  \
               -Template           lineWrapper \
               -package            lineWrapper.dtar020 \
               -Schema             DTAR020.cbl   \
                 -FileOrganisation FixedWidth \
                 -font cp037 \
                 -DropCopybookName   true \
               -outputDirectory    DTAR020 \
               -GenerateDate     9.Jun.2001
                                            
java -jar ../../lib/JRecordCodeGen.jar  \
               -Template           lineWrapperPojo \
               -package            lineWrapperPojo.dtar020 \
               -Schema             DTAR020.cbl   \
                 -FileOrganisation FixedWidth \
                 -font cp037 \
                 -DropCopybookName   true \
               -outputDirectory    DTAR020 \
               -GenerateDate     9.Jun.2001

                                            
java -jar ../../lib/JRecordCodeGen.jar  \
               -Template           pojoWithInterface \
               -package            pojoWithInterface.dtar020 \
               -Schema             DTAR020.cbl   \
                 -FileOrganisation FixedWidth \
                 -font cp037 \
                 -DropCopybookName   true \
               -outputDirectory    DTAR020 \
               -GenerateDate     9.Jun.2001

                                            
java -jar ../../lib/JRecordCodeGen.jar  \
               -Template           schemaClass \
               -package            schemaClass.dtar020 \
               -Schema             DTAR020.cbl   \
                 -FileOrganisation FixedWidth \
                 -font cp037 \
                 -DropCopybookName   true \
               -outputDirectory    DTAR020 \
               -GenerateDate     9.Jun.2001
                                                           
