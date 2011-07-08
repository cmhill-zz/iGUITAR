#!/bin/bash

tool_guitar_dir=`dirname $0`
guitar_lib=$tool_guitar_dir/jars
classpath=.
for file in `find $guitar_lib -name '*.jar'` 
do
	classpath=$classpath:$file
done


cmd="java"
main_class=edu.umd.cs.guitar.graph.GUIStructure2GraphConverter

$cmd -cp $classpath $main_class  EFGConverter $@




