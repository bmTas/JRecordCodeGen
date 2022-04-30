java -jar ../../lib/JRecordCodeGen.jar  ^
               -Template           lineWrapperPojo ^
               -package            lineWrapperPojo.AmsPoDownload ^
               -Schema             amsPoDownload.cbl   ^
                 -FileOrganisation Text ^
                 -split 01 ^
               -outputDirectory    amsPoDownload
pause