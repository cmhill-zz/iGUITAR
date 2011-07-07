#!/bin/bash

# This is a sample script to demonstrate 
# GUITAR's workflow 
# The output can be found in Demo directory  

#------------------------
# Running in script dir 
SCRIPT_DIR=`dirname $0`

#------------------------
# application directory 
aut_dir=$SCRIPT_DIR/jfc-aut/RadioButton/

# application classpath 
aut_classpath=$aut_dir/bin

# application main class
mainclass="Project"

# Comment out the following 2 lines to change the 
# classpath and main class to run the demo 
# with a real world application call CrosswordSage 
# (http://crosswordsage.sourceforge.net/)

#aut_classpath=$SCRIPT_DIR/jfc-aut/CrosswordSage/:$SCRIPT_DIR/jfc-aut/CrosswordSage/CrosswordSage.jar
#mainclass="crosswordsage.MainScreen"

#------------------------
# Sample command line arguments 
args=""
jvm_options=""
configuration="$aut_dir/guitar-config/configuration.xml"
intial_wait=1000
ripper_delay=500

tc_length=2
testcase_num=3

relayer_delay=200

# output 

output_dir="$SCRIPT_DIR/Demo"

gui_file="$output_dir/Demo.GUI"
efg_file="$output_dir/Demo.EFG"
log_file="$output_dir/Demo.log"
states_dir="$output_dir/states"
testcases_dir="$output_dir/testcases"
logs_dir="$output_dir/logs"

#------------------------
# Main workflow 

# directory prepartion
mkdir -p $output_dir
mkdir -p $testcases_dir
mkdir -p $states_dir
mkdir -p $logs_dir

# rip
echo ""
echo "About to rip the application " 
read -p "Press ENTER to continue..."
cmd="$SCRIPT_DIR/jfc-ripper.sh -cp $aut_classpath -c $mainclass -g  $gui_file -cf $configuration -d $ripper_delay -i $intial_wait -l $log_file"
echo $cmd
eval $cmd

# convert GUI to EFG
echo ""
echo "About to convert GUI structure file to Event Flow Graph (EFG) file" 
read -p "Press ENTER to continue..."
cmd="$SCRIPT_DIR/gui2efg.sh $gui_file $efg_file"
echo $cmd
eval $cmd

# generate test case 
echo ""
echo "About to generate test cases to cover all possible $tc_length-way event interactions" 
read -p "Press ENTER to continue..."
cmd="$SCRIPT_DIR/tc-gen-sq.sh $efg_file $tc_length 0 $testcases_dir"
echo $cmd
eval $cmd 

# replay
echo ""
echo "About to replay $testcase_num sample test case(s)" 
read -p "Press ENTER to continue..."

for testcase in `find $testcases_dir -name "*.tst"| head -n$testcase_num`  
do
	# get test name 
	test_name=`basename $testcase`
	test_name=${test_name%.*}

	cmd="$SCRIPT_DIR/jfc-replayer.sh -cp $aut_classpath -c  $mainclass -g $gui_file -e $efg_file -t $testcase -i $intial_wait -d $relayer_delay -l $logs_dir/$test_name.log -gs $states_dir/$test_name.sta" 
	echo $cmd 
	$cmd
done

