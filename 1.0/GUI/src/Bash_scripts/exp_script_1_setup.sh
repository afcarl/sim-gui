#!/bin/bash

######### CREATION OF EXPERIMENT FOLDERS ###############################################
# Script to create the folder hierarchy
# - Create folders ./duration_x/intensity_y/frequency_z
# - Create folders for multiple batch runs: its_"batch"
# - Copy output.xml and run.sh from top-level to these run folders
########################################################################################

echo '  Creating folder hierarchy...'



rm -f STATUS STATUS_itersdb
mkdir -p 'its'
cd ./its

if (($NUM_PARS==0)); then

	echo 'exp_script_1_setup.sh for only one batch'

	mkdir -p 'Batch'
	cd ./Batch
	
	for run in $RUNS; do
        mkdir -p 'run_'$run
        echo '          Created folder: ./Batch/run_'$run
        echo './Batch/run_'$run:CREATED>> $BASE/STATUS
        cp $BASE/output.xml ./'run_'$run
	cp $BASE/run.sh ./'run_'$run	
	cp $BASE/compress_db.sh ./'run_'$run
	done

fi


if (($NUM_PARS==1)); then
	echo 'In exp_script_1_setup.sh these values are used:'
	echo '[' $F1_values ']'

	mkdir -p $PARAMETER_1
	cd ./$PARAMETER_1

	for folder1 in $F1_values; do
	    mkdir -p $folder1
	    echo '    Created folder:' $folder1
	    cd $folder1
		    for run in $RUNS; do
		        mkdir -p 'run_'$run
		        echo '          Created folder:' $folder1/'run_'$run
		        echo $folder1/run_$run:CREATED>> $BASE/STATUS
		        cp $BASE/output.xml  ./'run_'$run
			 cp $BASE/run.sh ./'run_'$run
			cp $BASE/compress_db.sh ./'run_'$run
			done
			cd ..
	done
	cd ..

fi




if (($NUM_PARS==2)); then

	echo 'In exp_script_1_setup.sh these values are used:'
	echo '[' $F1_values ']'
	echo '[' $F2_values ']'

	mkdir -p $PARAMETER_1
	cd ./$PARAMETER_1

	for folder1 in $F1_values; do
		mkdir -p $folder1
		echo '    Created folder:' $folder1
		cd $folder1

		mkdir -p $PARAMETER_2
		cd ./$PARAMETER_2

			for folder2 in $F2_values; do
				mkdir -p $folder2
				echo '    Created folder:' $folder2
				cd $folder2
		

				for run in $RUNS; do
				    mkdir -p 'run_'$run
				    echo '          Created folder:' $folder1/'run_'$run
				    echo $folder1/run_$run:CREATED>> $BASE/STATUS
				    cp $BASE/output.xml  ./'run_'$run
 					cp $BASE/run.sh ./'run_'$run
					cp $BASE/compress_db.sh ./'run_'$run
			
			 	done
				cd ..
		
			done
			cd ..
		cd ..
	done
	cd ..

fi

echo '  Finished creation of folder hierarchy.'
echo '  Returned to:'$PWD
