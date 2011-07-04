#!/bin/bash

classpath=.
for file in `find jars -name '*.jar'` 
do
	classpath=$classpath:$file
done


cmd="java"
main_class=edu.umd.cs.guitar.graph.GUIStructure2GraphConverter

$cmd -cp $classpath $main_class  EFGConverter $@




