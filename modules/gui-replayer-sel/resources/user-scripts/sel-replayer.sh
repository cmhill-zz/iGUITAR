#!/bin/bash

classpath=.
for file in `find jars -name '*.jar'` 
do
	classpath=$classpath:$file
done


cmd="java"
main_class=
plugin=WebPluginInfo
domain=edu.umd.cs.guitar.replayer

java -cp $classpath $domain.Launcher $domain.$plugin $@




