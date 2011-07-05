#!/bin/bash

# This is a sample script to demonstrate 
# GUITAR's workflow 
# The output can be found in Demo directory  

#------------------------
# Running script dir 
SCRIPT_DIR=`dirname $0`

#------------------------
# application classpath 
aut_classpath=$SCRIPT_DIR/jfc-aut

#------------------------
# Sample command line arguments 
mainclass="Project"
args=""
jvm_options=""
configuration="$SCRIPT_DIR/configuration.xml"
intial_wait=1000
ripper_delay=500

tc_length=2
testcase_num=2

relayer_delay=200

# output 

output_dir="Demo"

gui_file="$output_dir/Demo.GUI"
efg_file="$output_dir/Demo.EFG"
log_file="$output_dir/Demo.log"
states_dir="$output_dir/Demo.states"
testcases_dir="$output_dir/Demo.testcases"
logs_dir="$output_dir/Demo.logs"

#------------------------
# Main workflow 

# directory prepartion
mkdir -p $output_dir

# rip
echo ""
echo "About to rip application " 
read -p "Press ENTER to continue..."
$SCRIPT_DIR/jfc-ripper.sh -cp $aut_classpath -c $mainclass -g  $gui_file -cf $configuration -d $ripper_delay -i $intial_wait -l $log_file

# convert GUI to EFG
echo ""
echo "About to convert GUI to EFG " 
read -p "Press ENTER to continue..."
$SCRIPT_DIR/gui2efg.sh $gui_file $efg_file 

# generate test case 
echo ""
echo "About to generate test cases" 
read -p "Press ENTER to continue..."
$SCRIPT_DIR/tc-gen-sq.sh $efg_file $tc_length $testcase_num  $testcases_dir

# replay
echo ""
echo "About to replay test cases " 
read -p "Press ENTER to continue..."

for testcase in `find $testcases_dir -name "*.tst"`  
do
	$SCRIPT_DIR/jfc-replayer.sh 
done

