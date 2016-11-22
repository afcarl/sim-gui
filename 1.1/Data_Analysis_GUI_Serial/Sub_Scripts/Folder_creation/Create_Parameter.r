#Assumptions: plot_directory has been set in Configure.r

########################################Eingabebereich-Folders###############################################################
#Creation of plot folders
#Form: dir.create(path, showWarnings = TRUE, recursive = FALSE, mode = "0777")

    #Main plot folder
    param_directory<-paste(graph_directory,"/Parameter", sep="")
    dir.create(param_directory, showWarnings = FALSE, mode = "0777")
    print(paste("Created Parameter folder:", param_directory))

    #For Time_Series we need extra subfolders:
    print(current_folder)
    #if(current_folder == "Time_Series" || current_folder == "Trend" || current_folder == "Bandpass" || current_folder == "Growth_Rate")
    #if(current_folder == "Trend" || current_folder == "Bandpass" )
    #{

	#if(current_folder == "Time_Series")
	#{
	#	#Subfolders for Time series
	#	folder_names_sub<-c("Mean", "Median", "Variance", "Ratio")
	#}

	#if(current_folder == "Trend" || current_folder == "Bandpass")
	#{
		#Subfolders for Time series
		#folder_names_sub<-c("Scalar", "Dist")
	#}

	#if(current_folder == "Growth_Rate")
	#{
		#folder_names_sub<-c("Scalar", "Dist","Variance")
	#}

	#for( j in 1:length(folder_names_sub))
	#{	
	    #dir.create(paste(param_directory,"/",folder_names_sub[j], sep=""), showWarnings = FALSE, mode = "0777")
	   # print(paste("Created Subfolder:","/",folder_names_sub[j], sep=""))
	#}
    #}
