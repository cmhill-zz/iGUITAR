#!/bin/bash
##################################
# JFC GUITAR Ripper
##################################
function usage {
	echo "Usage: jfcripper.sh.sh -cp <aut classpath> [guitar arguments]"
}
base_dir=`dirname $0`
guitar_lib=$base_dir/jars


if [ $# -lt 2 ] 
then
	echo "Missing parameter(s)"
	usage
	exit
fi

if [ $1 == "-cp" ]
then
	addtional_classpath=$2
else
	echo "Invalid parameter(s)"
	usage
	exit
	
fi


guitar_args=${@:3}

classpath=$addtional_classpath:$classpath

# Main classes 
ripper_launcher=edu.umd.cs.guitar.ripper.JFCRipperMain


for file in `find $guitar_lib/ -name "*.jar"`
do
    guitar_classpath=${file}:${guitar_classpath}
done


# Change GUITAR_OPTS variable to run with the clean log file  
GUITAR_OPTS="$GUITAR_OPTS -Dlog4j.configuration=log/guitar-clean.glc"

if [ -z "$JAVA_CMD_PREFIX" ];
then
    # Run with clean log file 
    JAVA_CMD_PREFIX="java"
fi

classpath=$base_dir:$guitar_classpath

if [ ! -z $addtional_classpath ] 
then
	classpath=$classpath:$addtional_classpath
else
	classpath=$classpath
fi

RIPPER_CMD="$JAVA_CMD_PREFIX $GUITAR_OPTS -cp $classpath $ripper_launcher $guitar_args"
exec $RIPPER_CMD

