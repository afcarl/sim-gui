#!/bin/bash
#Script to generate a set of run scripts (job_list_*.sh)
#Each script runs a set of jobs in sequence
#Jobs are assigned to the lists by a round-robin shuffle algorithm
 
echo '  Starting creation of job scripts in:'$BASE

#Cycle through the number of procs
let count=0

#Remove old scripts
rm -f job_list_*.sh


cd ./its


if (($NUM_PARS==0)); then

	echo 'exp_script_1_setup.sh for only one batch'

	mkdir -p 'Batch'
	cd ./Batch
	
	for run in $RUNS; do
            cd 'run_'$run

		echo $PWD

		#Add a line for this run
       		echo 'cd '$PWD>>$BASE/job_list_$count.sh
       		echo 'bash ./compress_db.sh'>>$BASE/job_list_$count.sh
		echo 'cd -'>>$BASE/job_list_$count.sh
		echo 'Assigned job to list '$count

            cd ..

		let "count = $count + 1"
		if [ $count -eq $NUM_PROCS ]; then let count=0; fi

        done
        cd ..

fi




if (($NUM_PARS==1)); then
	
cd ./$PARAMETER_1

for folder1 in $F1_values; do



	cd $folder1

        for run in $RUNS; do
            cd 'run_'$run

		#Add a line for this run
       		echo 'cd '$PWD>>$BASE/job_list_$count.sh
       		echo 'bash ./compress_db.sh'>>$BASE/job_list_$count.sh
		echo 'cd -'>>$BASE/job_list_$count.sh
		echo 'Assigned job to list '$count

            cd ..

		let "count = $count + 1"
		if [ $count -eq $NUM_PROCS ]; then let count=0; fi

        done
        cd ..
done

cd ..

fi







if (($NUM_PARS==2)); then

cd ./$PARAMETER_1
	
for folder1 in $F1_values; do

	cd $folder1

	cd ./$PARAMETER_2
	
	for folder2 in $F2_values; do
	
		cd $folder2
		for run in $RUNS; do
		    cd 'run_'$run

			#Add a line for this run
	       		echo 'cd '$PWD>>$BASE/job_list_$count.sh
	       		echo 'bash ./compress_db.sh'>>$BASE/job_list_$count.sh
			echo 'cd -'>>$BASE/job_list_$count.sh
			echo 'Assigned job to list '$count

		    cd ..

		let "count = $count + 1"
		if [ $count -eq $NUM_PROCS ]; then let count=0; fi
		done
		
		cd ..

        done
        cd ..
	cd ..
done

fi





cd ..



echo '  Finished creation of job scripts.'

