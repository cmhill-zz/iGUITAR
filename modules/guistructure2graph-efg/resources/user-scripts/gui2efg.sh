#!/bin/bash

base_dir=`dirname $0`
guitar_lib=$base_dir/jars
classpath="$guitar_lib/gui-model-core.jar"
for file in `find $guitar_lib -name '*.jar'` 
do
	classpath=$classpath:$file
done
classpath=$classpath:$base_dir

# Change GUITAR_OPTS variable to run with the clean log file  
GUITAR_OPTS="$GUITAR_OPTS -Dlog4j.configuration=log/guitar-clean.glc"

JAVA_CMD_PREFIX="java"

main_class=edu.umd.cs.guitar.graph.GUIStructure2GraphConverter

$JAVA_CMD_PREFIX $GUITAR_OPTS -cp $classpath $main_class  EFGConverter $@




