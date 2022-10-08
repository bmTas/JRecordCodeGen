## ---------------------------------------------------------------------------
##  Purpose: Generate JRecord code for a Single Record Cobol Copybook: JR_Schema_Test.cbl
##           using the lineWrapper Template
##  Parameters:
##       -Template         : Code template to use      ~ lineWrapper
##       -schema           : Cobol Copybook            ~ JR_Schema_Test.cbl
##       -FileOrganisation : File Type                 ~ FixedWidth - Constant (fixed) length records with no line separator
##       -splir            : split copybook            ~ on 01 level
##
##   Script Author: Bruce Martin
## ---------------------------------------------------------------------------

java -jar ../../lib/JRecordCodeGen.jar  \
              -Template           lineWrapper \
              -package            net.sf.JRecord.test.schema.cobol.gen \
              -Schema             JR_Schema_Test.cbl   \
                -FileOrganisation Text \
                -split 01 \
              -outputDirectory    JR_Schema_Test
