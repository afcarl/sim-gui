#!/bin/bash

######### RUNNING THE EXPERIMENT ##################################################################
# Script to 
###################################################################################################

echo '  Launching all job scripts in:'$BASE

for ((count=0; count<NUM_PROCS; count++)); do
	echo 'LAUNCH job_list '$count >>STATUS_JOBS
	bash job_list_$count.sh &
	#Wait 10 second before launching next job (prevents identical random seeds)
    	sleep 1
done

#wait for all jobs to finish
wait
echo 'DONE job lists' >>STATUS_JOBS
echo '  Done.'

