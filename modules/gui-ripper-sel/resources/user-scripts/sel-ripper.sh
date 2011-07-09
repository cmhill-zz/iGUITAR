#!/bin/bash
base_dir=`dirname $0`
guitar_lib=$base_dir/jars


for file in `find $guitar_lib -name '*.jar'` 
do
	classpath=$classpath:$file
done
classpath=$classpath:$base_dir


cmd="java"
main_class=
plugin=WebPluginInfo
domain=edu.umd.cs.guitar.ripper

$cmd -cp $classpath $domain.Launcher $domain.$plugin $@




