#!/bin/sh

dir="../tests"
DIFF=""
tput setaf 4;tput bold;echo Compile
mkdir temp
javac -d temp src/* 2>&1 > /dev/null
cd temp
for t in 1 2 3 4 5
do
	tput setaf 4;tput bold;echo Test $t
	java ShipmentPlanner $dir/test$t.txt > ${dir}/output$t.txt
	# DIFF=$(diff $dir/output$t.txt ${dir}/expected$t.txt)
	# if [ "$DIFF" != "" ]
	# then
	# 	tput setaf 1;tput bold;echo FAIL
	# else
	# 	tput setaf 2;tput bold;echo PASS
	# fi
done
tput setaf 4;tput bold;echo clean
#rm $dir/output* > /dev/null
cd ..
rm -r temp > /dev/null
