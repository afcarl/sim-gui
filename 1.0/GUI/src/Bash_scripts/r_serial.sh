#!/bin/bash

#NOTE:
#export DATA_FILES is now set in exp_settings.sh
export DATA_FILES=$PATH_R_SCRIPTS"/Data_Files/"

echo 'r_serial.sh: PATH_R_SCRIPTS='$PATH_R_SCRIPTS

R --slave --vanilla --quiet --no-save <<EEE

# Start the configure script. 
print(paste("r_serial.sh Running configure script: ",system('echo $PATH_R_SCRIPTS', intern=TRUE),"Configure.r",sep=""))
source(paste(system('echo $PATH_R_SCRIPTS', intern=TRUE),"Configure.r",sep=""))

cat(root_directory)
# Create folders for plots.
source(paste(root_directory,"Folder_creation/Setup_Folders.r",sep=""))

EEE


echo "DATA_FILES="$DATA_FILES

C1_values=($(awk '{print $1}' $DATA_FILES"variables.txt"))
echo '[' $C1_values ']'

C2_values=($(awk '{print $2}' $DATA_FILES"variables.txt"))
echo '[' $C2_values ']'

C3_values=($(awk '{print $3}' $DATA_FILES"variables.txt"))
echo '[' $C3_values ']'

C4_values=($(awk '{print $4}' $DATA_FILES"variables.txt"))
echo '[' $C4_values ']'

C5_values=($(awk '{print $5}' $DATA_FILES"variables.txt"))
echo '[' $C5_values ']'

C6_values=($(awk '{print $6}' $DATA_FILES"variables.txt"))
echo '[' $C6_values ']'

C7_values=($(awk '{print $7}' $DATA_FILES"variables.txt"))
echo '[' $C7_values ']'

C8_values=($(awk '{print $8}' $DATA_FILES"variables.txt"))
echo '[' $C8_values ']'

C9_values=($(awk '{print $9}' $DATA_FILES"variables.txt"))
echo '[' $C9_values ']'

C10_values=($(awk '{print $10}' $DATA_FILES"variables.txt"))
echo '[' $C10_values ']'

C11_values=($(awk '{print $11}' $DATA_FILES"variables.txt"))
echo '[' $C11_values ']'

C12_values=($(awk '{print $12}' $DATA_FILES"variables.txt"))
echo '[' $C12_values ']'



for ((i=0; i < ${#C1_values[@]}; i++)); do
	
	if((${C12_values[i]}==1)); then
		export var=${C1_values[i]}
		export name=${C2_values[i]}
		export agent=${C3_values[i]}
		export method=${C4_values[i]}
		export filter=${C5_values[i]}
		export filter_value=${C6_values[i]}
		export operator=${C7_values[i]}
		export filter_2=${C8_values[i]}
		export filter_2_value=${C9_values[i]}
		export operator_2=${C10_values[i]}
		export weight=${C11_values[i]}
		export process_variable=${C12_values[i]}
		echo "var" $var
		echo "name" $name
		echo "agent" $agent

		#Logging: directs both stdout and stderr to "$agent_$name.log"
		mkdir -p 'log'
		#rm ./log/*
		bash R <$PATH_R_SCRIPTS/Run_Data_Analysis.r --no-save >& ./log/$agent_$name.log &
		#bash R <$PATH_R_SCRIPTS/Run_Data_Analysis.r --no-save >

	fi

done
echo 'DONE'
wait

#Catch errors from R
echo "Writing errors to R log file."
grep "Error" ./log/* >./log/errors.txt
