rem * ---------------------------------------------------------------------------
rem *  Purpose: Generate JRecord code for a Multi Record Cobol Copybook: amsPoDownload.cbl
rem *  Parameters:
rem *       -Template         : Code template to use      ~ standard
rem *       -schema           : Cobol Copybook            ~ amsPoDownload.cbl
rem *       -FileOrganisation : File Type                 ~ Text - standard text file with \n line seperators
rem *       -split            : How to split the Copybook ~ 01             
rem *                           into separate records
rem *       -package          : Java Package for the "Field-Name" class 
rem *       -outputDirectory  : where to write the generated code
rem *
rem *   Script Author: Bruce Martin
rem * ---------------------------------------------------------------------------

java -jar ../../lib/JRecordCodeGen.jar  ^
               -Template         standard ^
               -package          example.ioBuilder.amsPoDownload ^
               -Schema           amsPoDownload.cbl   ^
                 -FileOrganisation Text ^
                 -split 01 ^
               -outputDirectory  amsPoDownload
           

pause
               