#!/bin/bash

######### BASE RUN ################################################################
# Script for a base run:
# Usage:
# - NR_NODES: number of nodes to use
# - ITS: number of iterations
# - MAIN: location of executable file
# Steps:
# - run
# - join the node-*.xml files to single xml files
# - remove the node-*.xml files
###################################################################################
echo 'In run.sh these values are used:'
echo 'BASE=' $BASE
echo 'DO_RUN=' $DO_RUN
echo 'SAVE_SNAPSHOTS=' $SAVE_SNAPSHOTS
echo 'FREQ_SNAPSHOTS=' $FREQ_SNAPSHOTS
echo 'DO_COMPRESS_KEEP_ORIGINAL=' $DO_COMPRESS_KEEP_ORIGINAL
echo 'DO_COMPRESS_REMOVE_ORIGINAL=' $DO_COMPRESS_REMOVE_ORIGINAL
echo 'DO_DECOMPRESS=' $DO_DECOMPRESS
echo 'DO_REMOVE_DB=' $DO_REMOVE_DB

############# Running
if (($DO_RUN)); then

	#switch on core dump for debugging
	ulimit -c unlimited

	$MAIN_S $ITS output.xml >stdout.txt 2>stderr.txt

	#Snapshots: move xml files
	if (($SAVE_SNAPSHOTS)); then
		echo '+ Moving xml files for snapshots: 1+n*'$FREQ_SNAPSHOTS
		mkdir snapshots
		for i in *.xml; do
		   filebase=`basename $i .xml`
		   if (( (filebase -1)% $FREQ_SNAPSHOTS == 0 )); then
		       echo "Moving $i"
		       mv $i ./snapshots
		   fi
		done
	fi

	#Create the SQL database
	if (($CREATE_SQL)); then
		python $BASE/gendb.py $MODEL_XML_FILE ./ >gendb_stdout.txt 2>gendb_stderr.txt

		#Snapshots: Create the SQL database
		if (($SAVE_SNAPSHOTS)); then
			cd snapshots
			python $BASE/gendb.py $MODEL_XML_DIR ./
			cd ..
		fi
	fi

	#print total disc usage of XML files
	echo 'XML files:' >> $BASE/STATUS_itersdb
	du -h ./ >> $BASE/STATUS_itersdb

	#Just remove the xml files
	echo '+ Removing the xml files'
	rm *.xml

	if (($SAVE_SNAPSHOTS)); then
		rm ./snapshots/*.xml
	fi

fi


