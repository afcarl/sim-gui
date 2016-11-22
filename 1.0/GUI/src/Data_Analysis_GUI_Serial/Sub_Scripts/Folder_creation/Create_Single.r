#Assumptions: 
#- graph_root_directory has been set in Configure.r

########################################Eingabebereich-Folders###############################################################
#Creation of plot folders
#Form: dir.create(path, showWarnings = TRUE, recursive = FALSE, mode = "0777")
#
#Pre-existing folders:
#  plot_directory/parameter_name
#  plot_directory/parameter_name/parameter_values[p]

    #Main plot folder
    single_directory<-paste(graph_directory,"/Single", sep="")
    dir.create(single_directory, showWarnings = FALSE, mode = "0777")
    print(paste("Created Single folder:", single_directory))

    #Run plot folder
    for(p in 1:length(parameter_values))
    {
	param_directory<-paste(single_directory,"/batch_",parameter_values[p], sep="")
	dir.create(param_directory, showWarnings = FALSE, mode = "0777")
	print(paste("Created Batch sub folder:","/batch_",parameter_values[p], sep=""))

	#Create folders for run values
	for(r in 1:runs)
	{
	    run_directory<-paste(param_directory,"/run_",r, sep="")
	    dir.create(run_directory, showWarnings = FALSE, mode = "0777")
	    print(paste("Created Run sub folder: ","/run_",r, sep=""))
	    
	    #For Time_Series we need extra subfolders:
	    #if (current_folder == "Time_Series" || current_folder == "Trend" || current_folder == "Growth_Rate" || current_folder == "Bandpass")
	    #if ( current_folder == "Bandpass")
	    #{

			#if(current_folder == "Time_Series")
			#{
				#Subfolders for Time_Series
				#folder_names_sub<-c("Dist","Quantile","Scalar","Variance","Ratio","Multiple")
			#}

			#if(current_folder == "Trend" || current_folder == "Growth_Rate")
			#{
				#Subfolders for Time_Series
				#folder_names_sub<-c("Dist","Scalar")
			#}

			#if(current_folder == "Bandpass")
			#{
				#Subfolders for Time_Series
				#folder_names_sub<-c("Dist","Scalar","Multiple")
			#}

			
			#for( j in 1:length(folder_names_sub))
			#{	
				#dir.create(paste(run_directory,"/",folder_names_sub[j], sep=""), showWarnings = FALSE, mode = "0777")
				#print(paste("Created sub folder: ",folder_names_sub[j], sep=""))
			#}
	    #}
	}
    }
