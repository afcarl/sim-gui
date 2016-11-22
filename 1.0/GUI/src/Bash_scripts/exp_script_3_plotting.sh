#!/bin/bash

######### PLOTTING EXPERIMENT FOLDERS ###############################################
# Script to traverse the folder hierarchy
# - Batch_Run_Analysis
# - Single_Run_Analysis
# - Compressing the database files
########################################################################################

echo '  Traversing the folder hierarchy...'
echo 'In exp_script_3_plotting.sh these values are used:'
echo '[' $F1_values ']'
echo 'Starting in: ' $PWD

#rm -f STATUS
cd ./its
echo '    Entering folder:' $PWD

cd ./$EXPERIMENT_NAME
echo '    Entering folder:' $PWD

#Launching R plotting scripts

	echo '     Launching R plotting scripts from folder:' $folder1
	if (($SAVE_SNAPSHOTS==1)); then
		echo $EXPERIMENT_NAME " " $SAVE_SNAPSHOTS >> $BASE/STATUS_snapshots.txt
		bash R <$PATH_R_SCRIPTS/Run_Data_Analysis.r --no-save
	fi

	#Now set SAVE_SNAPSHOTS to 0 to run normally
	export SAVE_SNAPSHOTS=0
	echo $EXPERIMENT_NAME " " $SAVE_SNAPSHOTS >> $BASE/STATUS_snapshots.txt
	bash R <$PATH_R_SCRIPTS/Run_Data_Analysis.r --no-save

#It does not matter where we end, but we could do this:
#cd ../..

echo '  Finished traversing the folder hierarchy.'
echo '  Returned to:'$PWD

