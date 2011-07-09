#!/bin/bash

base_dir=`dirname $0`
guitar_lib=$base_dir/jars
classpath="$guitar_lib/gui-model-core.jar"
for file in `find $guitar_lib -name '*.jar'` 
do
	classpath=$classpath:$file
done
classpath=$classpath:$base_dir

cmd="java"
main_class=edu.umd.cs.guitar.graph.GUIStructure2GraphConverter

$cmd -cp $classpath $main_class  EFGConverter $@




