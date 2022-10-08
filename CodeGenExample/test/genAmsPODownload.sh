#! /bin/sh
## ---------------------------------------------------------------------------
##  Purpose: Generate JRecord code for a Multi Record Cobol Copybook: amsPoDownload.cbl
##  Parameters:
##       -Template         : Code template to use      ~ pojo
##       -schema           : Cobol Copybook            ~ amsPoDownload.cbl
##       -FileOrganisation : File Type                 ~ Text - pojo text file with \n line seperators
##       -split            : How to split the Copybook ~ 01 
##                           into separate records
##       -package          : Java Package for the "Field-Name" class
##       -outputDirectory  : where to write the generated code
##
##   Script Author: Bruce Martin
## --------------------------------------------------------------------------

java -jar ../../lib/JRecordCodeGen.jar  \
               -Template         pojo \
               -package          pojo.amsPoDownload \
               -Schema           amsPoDownload.cbl   \
                 -FileOrganisation Text \
                 -split 01 \
               -outputDirectory  amsPoDownload \
               -GenerateDate     9.Jun.2001
 
java -jar ../../lib/JRecordCodeGen.jar  \
               -Template         pojo \
               -package          pojo.amsPoDownload090 \
               -Schema           amsPoDownload.cbl   \
                 -FileOrganisation Text \
                 -split 01 \
               -outputDirectory  amsPoDownload \
               -GenerateDate     9.Jun.2001 \
               -JRecordVersion   0.90
              
java -jar ../../lib/JRecordCodeGen.jar  \
               -Template         standard \
               -package          standard.amsPoDownload \
               -Schema           amsPoDownload.cbl   \
                 -FileOrganisation Text \
                 -split 01 \
               -outputDirectory  amsPoDownload \
               -GenerateDate     9.Jun.2001               
     
                 
java -jar ../../lib/JRecordCodeGen.jar  \
               -Template         lineWrapper \
               -package          lineWrapper.amsPoDownload \
               -Schema           amsPoDownload.cbl   \
                 -FileOrganisation Text \
                 -split 01 \
               -outputDirectory  amsPoDownload \
               -GenerateDate     9.Jun.2001               
     
               
java -jar ../../lib/JRecordCodeGen.jar  \
               -Template         lineWrapperPojo \
               -package          lineWrapperPojo.amsPoDownload \
               -Schema           amsPoDownload.cbl   \
                 -FileOrganisation Text \
                 -split 01 \
               -outputDirectory  amsPoDownload \
               -GenerateDate     9.Jun.2001               
     
              
java -jar ../../lib/JRecordCodeGen.jar  \
               -Template         pojoWithInterface \
               -package          pojoWithInterface.amsPoDownload \
               -Schema           amsPoDownload.cbl   \
                 -FileOrganisation Text \
                 -split 01 \
               -outputDirectory  amsPoDownload \
               -GenerateDate     9.Jun.2001               
     
              
java -jar ../../lib/JRecordCodeGen.jar  \
               -Template         schemaClass \
               -package          schemaClass.amsPoDownload \
               -Schema           amsPoDownload.cbl   \
                 -FileOrganisation Text \
                 -split 01 \
               -outputDirectory  amsPoDownload \
               -GenerateDate     9.Jun.2001               
     
              
