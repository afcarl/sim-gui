#!/bin/bash

################################################################################################################
# This script is for setting up and running the experiment:
# Steps:
# - 1 : experiment settings
# - 2 : creation of experiment folder hierarchy
# - 3 : creation of the specific settings xml file
# - 4 : creation of a job list
# - 5 : launching the job list
# - 6 : start plotting routines using R
################################################################################################################

echo 'Starting top-level experiment script...'

######### STEP 1: EXPERIMENT SETTINGS ##################################################################
bash ./exp_settings.sh

######### STEP 2: CREATION OF EXPERIMENT FOLDER HIERARCHY 
bash ./exp_script_1_setup.sh

######### STEP 3: CREATION OF THE SPECIFIC SETTINGS XML FILE 
bash ./exp_script_2_specific.sh

######### STEP 4: CREATING  JOB SCRIPTS 
bash ./create_job_list.sh

######### STEP 5: LAUNCHING  JOB SCRIPTS 
bash ./launch_job_list.sh

wait
######### STEP 6: R scripts for plotting data
# Parallel R
bash ./r_serial.sh

echo 'Finished top-level experiment script.'
