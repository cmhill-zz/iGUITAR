#!/bin/bash

# change these following configuration to your test system

#
# web site under test 
#
website="http://comet.unl.edu/feedback.php"

#
# test case directory 
#
testcases_dir=TC

#
# number of test cases to run
#
testcase_num=2

# Use clean log
export GUITAR_OPTS="-Dlog4j.configuration=log/guitar-clean.glc"
SCRIPT_DIR=`dirname $0`
pushd $SCRIPT_DIR

# rip
echo "About to rip" 
read -p "Press ENTER to continue..."
./sel-ripper.sh --website-url $website -w 1 -d 1

# convert GUI to EFG
echo "About to convert GUI to EFG " 
read -p "Press ENTER to continue..."
./gui2efg.sh GUITAR-Default.GUI GUITAR-Default.EFG

# generate test case 
echo "About to generate test cases" 
read -p "Press ENTER to continue..."
./tc-gen-sq.sh GUITAR-Default.EFG 2 0 TC

# replay
echo "About to replay" 
read -p "Press ENTER to continue..."

SAMPLE_TEST=`find TC -name "*.tst" | tail -n1`

for testcase in `find $testcases_dir -name "*.tst"| head -n$testcase_num`  
do
	# getting test name 
	test_name=`basename $testcase`
	test_name=${test_name%.*}
	
	./sel-replayer.sh --website-url $website -g GUITAR-Default.GUI -e GUITAR-Default.EFG -t $testcase -g $test_name.orc -d 1000 
done

popd # quit to the current directory
