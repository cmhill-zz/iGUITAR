#!/bin/bash
##################################
# GUITAR tc-gen.sh
##################################
function usage {
	echo "Usage: tc-gen.sh <EFG file> <length>  <maximum number> <tc-dir>"
}

if [ $# -lt 4 ]
then
	echo "Usage: tc-gen.sh <EFG file> <length>  <maximum number> <tc-dir>"
	exit 1
fi


base_dir=`dirname $0`
guitar_lib=$base_dir/jars

# Main classes 
tcgen_launcher=edu.umd.cs.guitar.testcase.TestCaseGenerator

for file in `find $guitar_lib/ -name "*.jar"`
do
	guitar_classpath=${file}:${guitar_classpath}
done

# Change GUITAR_OPTS variable to run with the clean log file  
GUITAR_OPTS="$GUITAR_OPTS -Dlog4j.configuration=log/guitar-clean.glc"

if [ -z "$JAVA_CMD_PREFIX" ];
then
    JAVA_CMD_PREFIX="java"
fi

efg=$1
length=$2
max=$3
tc_dir=$4
plugin=SequenceLengthCoverage

classpath=$guitar_classpath:$base_dir

TCGEN_CMD="$JAVA_CMD_PREFIX $GUITAR_OPTS -cp $classpath $tcgen_launcher -p $plugin -e $efg -m $max -d $tc_dir -l $length"
$TCGEN_CMD





