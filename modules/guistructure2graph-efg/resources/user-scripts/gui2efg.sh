#!/bin/bash

base_dir=`dirname $0`
guitar_lib=$base_dir/jars
classpath="$guitar_lib/gui-model-core.jar"
for file in `find $guitar_lib -name '*.jar'` 
do
	classpath=$classpath:$file
done
classpath=$classpath:$base_dir

# Run with a clean log4j by default
JAVA_CMD_PREFIX="java -Dlog4j.configuration=edu/umd/cs/guitar/log/guitar-clean.glc"

main_class=edu.umd.cs.guitar.graph.GUIStructure2GraphConverter

$JAVA_CMD_PREFIX -cp $classpath $main_class  EFGConverter $@




