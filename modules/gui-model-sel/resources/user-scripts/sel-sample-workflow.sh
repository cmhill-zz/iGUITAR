#!/bin/bash
#website="http://comet.unl.edu"
website="http://comet.unl.edu/feedback.php"

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

SAMPLE_TEST=find TC -name "*.tst" | tail -n1
./sel-replayer.sh --website-url $website -g GUITAR-Default.GUI -e GUITAR-Default.EFG -t TC/$SAMPLE_TEST

popd
