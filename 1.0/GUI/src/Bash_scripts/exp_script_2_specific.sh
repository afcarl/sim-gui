#!/bin/bash
# Script to create the file specific.xml that contains the specific parameter settings
# - Enter folder ./value_x
# - Write xml tags and parameter settings to the file specific.xml

echo '  Creating specific.xml files in folder hierarchy...'

cd ./its


if (($NUM_PARS==0)); then
	echo '	Case Only one batch: Do nothing in exp_script_2_specific.sh'
	
	cd Batch
	echo ''>>specific.xml
	cp $BASE/environment.xml $PWD


	cd ..
	cd ..
fi

if (($NUM_PARS==1)); then
	echo 'In exp_script_2_specific.sh these values are used:'
	echo '[' $F1_values ']'
	
	cd ./$PARAMETER_1

	for f1 in $F1_values; do
        cd $f1
	cp $BASE/environment.xml  $PWD
        rm -f specific.xml
        echo '<environment>'>>specific.xml

        #Variable:
        echo -n '<'$PARAMETER_1'>'>>specific.xml
        echo -n $f1>>specific.xml
        echo '</'$PARAMETER_1'>'>>specific.xml

        echo '</environment>'>>specific.xml
        
        cd -
	done
	cd ..
fi


if (($NUM_PARS==2)); then
	echo 'In exp_script_2_specific.sh these values are used:'
	echo '[' $F1_values ']'
	echo '[' $F2_values ']'

	cd ./$PARAMETER_1

	for f1 in $F1_values; do
        
		cd $f1
		cd ./$PARAMETER_2

		for f2 in $F2_values; do
            cd $f2
		cp $BASE/environment.xml  $PWD
            rm -f specific.xml
            echo '<environment>'>>specific.xml

            #Variable:
            echo -n '<'$PARAMETER_1'>'>>specific.xml
            echo -n $f1>>specific.xml
            echo '</'$PARAMETER_1'>'>>specific.xml


		 	#Variable:
            echo -n '<'$PARAMETER_2'>'>>specific.xml
            echo -n $f2>>specific.xml
            echo '</'$PARAMETER_2'>'>>specific.xml

            echo '</environment>'>>specific.xml
                
         	cd ..
		done
		cd ..
		cd ..
	done
cd ..
fi

echo '  Finished creation of specific.xml files.'

