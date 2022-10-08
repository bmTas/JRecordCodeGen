rem * ---------------------------------------------------------------------------
rem *  Purpose: Generate JRecord Mdf code for a Single Record Cobol Copybook: LKSA310I_Opp101_RequestFormat.cbl
rem *           using the MdfTemplate Template
rem *  Parameters:
rem *       -userWrittenTemplates: Directory holding the Template (xBasic) 
rem *       -Template             : Code template to use      ~ MdfTemplate
rem *       -schema               : Cobol Copybook            ~ LKSA310I_Opp101_RequestFormat.cbl
rem *
rem *   Script Author: Bruce Martin
rem * ---------------------------------------------------------------------------

java -jar ../lib/JRecordCodeGen.jar  ^
              -TemplateDirectory  CodeGenExample ^
              -Template           MdfTemplate ^
              -Schema             LKSA310I_Opp101_RequestFormat.cbl  ^
              -Package            MdfTest.code  ^
              -outputDirectory    MdfOut

pause