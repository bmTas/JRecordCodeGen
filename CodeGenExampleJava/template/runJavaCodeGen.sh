#!/bin/sh

echo ">>${1}<<"
java -cp ../../lib/JRecordCodeGen.jar  ${1}
