##
#Create plot folder structure

#Main plot folder: set graph_directory as temp name (plot_directory is a constant)
graph_directory<-plot_directory
print("Graph_directory:")
print(graph_directory)
dir.create(graph_directory, showWarnings = FALSE, mode = "0777")

#Subfolders in Plot folder
#flag_values<-c(boxplot, correlation, histogram, scatter, standard_deviation, time_series, growth_rate, trend, beveridge, phillips, bandpass_filter,quantile)
#all_folder_names<-c("Boxplot", "Correlation", "Histogram", "Scatter", "Standard_Deviation", "Time_Series","Growth_Rate","Trend", "Beveridge", "Phillips", "Bandpass","Quantile")
flag_values<-c(time_series,boxplot,histogram,growth_rate,herfindahl,ratio,bandpass_filter,correlation_distribution,scatter,cross_correlation_function,heat_maps,heat_maps_2V,quantiles)
all_folder_names<-c("Time_Series","Boxplot","Histogram","Growth_Rate","Herfindahl","Time_Series","Bandpass","Correlation_Distribution","Scatter","Cross_Correlation_Function","Heat_Maps","Heat_Maps","Quantiles")

#Create the full list of folder names:
folder_names_top<-all_folder_names

#Create only those folders that are needed, using the flags set in Configure.r
folder_names_top<-c()
count<-0
for( i in 1:length(all_folder_names))
{
	if (flag_values[i]==1)
	{
		print(all_folder_names[i])
		folder_names_top[count+1]<-all_folder_names[i]
		count<-count+1
	}
}

#Create the full list of folder names:
print("Main folders:")
print(folder_names_top)

for( i in 1:length(folder_names_top))
{
    #Fix the name of current_folder
    #Per folder: in Create_* we check if for the current_foldeer we need to create subfolders
    current_folder<-folder_names_top[i]
    
    graph_directory<-paste(plot_directory,current_folder, sep="")
    dir.create(paste(plot_directory,current_folder, sep=""), showWarnings = FALSE, mode = "0777")

    ################### Create folders ###################

    print(paste("============= Creating plot folders for", current_folder,"=============="))
	if (parameter_analysis ==1)
		source(paste(root_directory,"/Folder_creation/Create_Parameter.r",sep=""))

	if (batch_analysis ==1)
		source(paste(root_directory,"/Folder_creation/Create_Batch.r",sep=""))

	if (single_analysis ==1)
		source(paste(root_directory,"/Folder_creation/Create_Single.r",sep=""))

    print(paste("============= Finished creating plot folders for", current_folder,"=============="))
}
