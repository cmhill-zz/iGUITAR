#!/bin/bash

# This is a sample script to demonstrate 
# GUITAR general workflow 
# The output can be found in Demo directory  

#------------------------
# Running in script dir 
SCRIPT_DIR=`dirname $0`

#------------------------
# application directory 
aut_dir=$SCRIPT_DIR/iph-aut/ButtonTest

# application classpath 
aut_classpath=$aut_dir/bin

# server host name
#aut_serverhost="localhost"

# server port number
port=8082

# application main class
mainclass="ButtonTest"

# Change the following 2 lines for the classpath and the main class of your 
# application. The example is for CrosswordSage, another real world example
# in the jfc-aut directory (http://crosswordsage.sourceforge.net/)

#aut_classpath=$SCRIPT_DIR/iph-aut/ButtonTest/
#aut_classpath=$SCRIPT_DIR/jfc-aut/CrosswordSage/bin:$SCRIPT_DIR/jfc-aut/CrosswordSage/bin/CrosswordSage.jar
#mainclass="crosswordsage.MainScreen"

#------------------------
# Sample command line arguments 
args=""
jvm_options=""

# configuration for the application
# you can specify widgets to ignore during ripping 
# and terminal widgets 
configuration="$aut_dir/guitar-config/configuration.xml"

# intial waiting time
# change this if your application need more time to start
intial_wait=100

# delay time between two events during ripping 
ripper_delay=100

# the length of test suite
tc_length=2

# delay time between two events during replaying  
# this number is generally smaller than the $ripper_delay
relayer_delay=1000

#------------------------
# Output artifacts 
#------------------------

# Directory to store all output of the workflow 
output_dir="./Demo"

# GUI structure file
gui_file="$output_dir/Demo.GUI"

# EFG file 
efg_file="$output_dir/Demo.EFG"

# Log file for the ripper 
# You can examine this file to get the widget 
# signature to ignore during ripping 
log_file="$output_dir/Demo.log"

# Test case directory  
testcases_dir="$output_dir/testcases"

# GUI states directory  
states_dir="$output_dir/states"

# Replaying log directory 
logs_dir="$output_dir/logs"

#------------------------
# Main workflow 
#------------------------

# Preparing output directories
rm -rf ./Demo &> /dev/null
mkdir -p $output_dir
mkdir -p $testcases_dir
mkdir -p $states_dir
mkdir -p $logs_dir

# Ripping
echo ""
echo "About to rip the application " 
read -p "Press ENTER to continue..."
#cmd="$SCRIPT_DIR/jfc-ripper.sh -cp $aut_classpath -c $mainclass -g  $gui_file -cf $configuration -d $ripper_delay -i $intial_wait -l $log_file"
# Moved -sp localhost
echo "Running ripper."
cmd="$SCRIPT_DIR/iph-ripper.sh -cp $aut_classpath -p $port -g  $gui_file -cf $configuration -d $ripper_delay -i $intial_wait -l $log_file &> ripper.out &"

# Adding application arguments if needed 
if [ ! -z $args ] 
then 
	cmd="$cmd -a \"$args\"" 
fi
echo $cmd
eval $cmd

# Execute the iphone program.
sleep 1
echo "Building iphone client..."
cmd="xcodebuild -configuration \"Debug\" -target \"TestScriptRunner\" -sdk iphonesimulator4.3 &> iph_ripper.out &"
echo $cmd
eval $cmd
echo

sleep 5

kill_iph_skd="ps aux | grep iphone | awk '{print \$2}' | xargs -n 1 -I {} kill -9 {} &> /dev/null"
echo "Cleaning up iphone sdk/client."
#echo $kill_iph_skd
eval $kill_iph_skd
sleep 1
# Converting GUI structure to EFG
#echo ""
#echo "About to convert GUI structure file to Event Flow Graph (EFG) file" 
read -p "Press ENTER to continue..."
cmd="$SCRIPT_DIR/gui2efg.sh $gui_file $efg_file"
echo $cmd
eval $cmd

# Generating test cases
echo ""
echo "About to generate test cases to cover all possible $tc_length-way event interactions" 
read -p "Press ENTER to continue..."
cmd="$SCRIPT_DIR/tc-gen-sq.sh -e $efg_file -l $tc_length -m 0 -d $testcases_dir"
echo $cmd
eval $cmd 	
# Replaying generated test cases
echo ""
echo "About to replay test case(s)" 
echo "Enter the number of test case(s): "
read testcase_num
    
for testcase in `find $testcases_dir -name "*.tst"| head -n$testcase_num`  
    do
    # getting test name 
   	test_name=`basename $testcase`
   	test_name=${test_name%.*}
   
    # Removed -c
    
   	cmd="$SCRIPT_DIR/iph-replayer.sh -cp $aut_classpath -g $gui_file -e $efg_file -t $testcase -i $intial_wait -d $relayer_delay -so 1000 -l $logs_dir/$test_name.log -gs $states_dir/$test_name.sta -cf $SCRIPT_DIR/configuration.xml &> replayer.out &"
   	# adding application arguments if needed 
   	if [ ! -z $args ] 
   	then 
   		cmd="$cmd -a \"$args\" " 
   	fi	
   	echo $cmd 
   	eval $cmd


	# Execute the iphone program.
	sleep 2
	echo "Building iphone client..."
	cmd="xcodebuild -configuration \"Debug\" -target \"TestScriptRunner\" -sdk iphonesimulator4.3 &> iph_replayer.out &"
	echo $cmd
	eval $cmd

	sleep 4
	
	echo "Cleaning up iphone sdk/client."
	#echo $kill_iph_skd
	eval $kill_iph_skd
	sleep 1
	grep "TERMINATED" replayer.out
	echo
done

echo "For ripper details, check file: ./ripper.out"
echo "For replayer details, check file: ./replayer.out"
echo "For iphone output, check file: ./errorLogFromLastBuild.txt"
