#Assumptions: 
#- graph_root_directory has been set in Configure.r


########################################Eingabebereich-Folders###############################################################
#Creation of plot folders
#Form: dir.create(path, showWarnings = TRUE, recursive = FALSE, mode = "0777")
#
#Pre-existing folders:
#  plot_directory/parameter_name
#############################################################################################################################

    #Main plot folder
    batch_directory<-paste(graph_directory,"/Batch", sep="")
    dir.create(batch_directory, showWarnings = FALSE, mode = "0777")
    print(paste("Batch folder:", batch_directory))

    #Batch plot folder
    for(p in 1:length(parameter_values))
    {
	#Reset name to root Batch folder:
	#batch_directory<-paste(graph_directory,"/Batch", sep="")
	
	#Update name
	batch_sub_directory<-paste(batch_directory,"/batch_",parameter_values[p], sep="")
	dir.create(batch_sub_directory, showWarnings = FALSE, mode = "0777")
	print(paste("Created Batch sub folder: ","/batch_",parameter_values[p], sep=""))

	#For Time_Series we need extra subfolders:
	if (current_folder == "Time_Series" || current_folder == "Heat_Maps"|| current_folder == "Correlation_Distribution" || current_folder == "Growth_Rate" || current_folder == "Bandpass" || current_folder == "Herfindahl" )
	{

	    if(current_folder == "Time_Series" || current_folder == "Herfindahl")
	    {
		    #Subfolders for Time_Series
		    folder_names_sub<-c("Full", "Mean", "Multiple")
		    #folder_names_sub_sub<-c("Scalar", "Dist", "Variance")
	    }

	    if(current_folder == "Trend")
	    {
		    #Subfolders for Time_Series
		    folder_names_sub<-c("Full", "Mean")
		    folder_names_sub_sub<-c("Scalar", "Dist")
	    }

	    if(current_folder == "Heat_Maps")
	    {
		    #Subfolders for Time_Series
		    folder_names_sub<-c("1Var", "2Var")
	
	    }

		if(current_folder == "Growth_Rate" || current_folder =="Correlation_Distribution")
	    {
		    #Subfolders for Time_Series
		    folder_names_sub<-c("Full", "Mean")
		    #folder_names_sub_sub<-c("Scalar", "Dist")
	    }

		#if(current_folder == "Bandpass")
	    #{
		    #Subfolders for Time_Series
		   # folder_names_sub<-c("Scalar","Dist","Multiple")
		   # folder_names_sub_sub<-c("Scalar", "Dist")
	    #}
	

	    for( i in 1:length(folder_names_sub))
	    {
		#Update name
		sub_directory<-paste(batch_sub_directory,"/",folder_names_sub[i], sep="")
		dir.create(sub_directory, showWarnings = FALSE, mode = "0777")
		print(paste("Created Batch sub folder: ",folder_names_sub[i], sep=""))

		#if(current_folder == "Time_Series")
		#{
			#if(folder_names_sub[i]== "Mean")
			#{
				#folder_names_sub_sub<-c("Scalar", "Dist", "Variance", "Ratio")	
			#}
		#}

		
		#for( j in 1:length(folder_names_sub_sub))
		#{	
			#Update name
			#if(folder_names_sub[i] != "Multiple" && current_folder != "Bandpass")
			#{
				#subsub_directory<-paste(sub_directory,"/",folder_names_sub_sub[j], sep="")
				#dir.create(subsub_directory, showWarnings = FALSE, mode = "0777")
				#print(paste("Created Batch subsub folder: ",folder_names_sub_sub[j], sep=""))
			#}
		#}

		#if(current_folder == "Time_Series" || current_folder == "Growth_Rate")
		#{
			#if(folder_names_sub[i]== "Mean")
			#{
				#folder_names_sub_sub<-c("Scalar", "Dist", "Variance")	
			#}
		#}
	    }
	}
    }

#############################################################################################################################
    #Subfolders for TSAnalysis
#    dir.create(paste(graph_directory,"/TS_Analysis", sep=""), showWarnings = FALSE, mode = "0777")

#    folder_names<-c("Growth_Rate", "Detrend")

#    for(i in 1:length(folder_names))
#    {
#	    graph_directory_tmp<-paste(graph_directory, "/TSAnalysis/",folder_names[i],sep="")
#	    dir.create(paste(graph_directory_tmp,folder_names[i], sep=""), showWarnings = FALSE, mode = "0777")
#    }
