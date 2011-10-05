#!/bin/bash
##################################
# GUITAR tc-gen.sh
##################################
function usage {
	echo "Usage: tc-gen.sh -e <EFG file> -l <length> -m <maximum number> -d
        <tc-dir> [-D no-duplicate-event] [-T treat-terminal-event-specially]"
}

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

plugin=SequenceLengthCoverage

classpath=$guitar_classpath:$base_dir

TCGEN_CMD="$JAVA_CMD_PREFIX $GUITAR_OPTS -cp $classpath $tcgen_launcher -p $plugin $@"
$TCGEN_CMD





