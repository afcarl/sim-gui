data<-as.matrix(read.csv(paste(data_file_directory,"variables.txt",sep=""),header=TRUE,sep=""))	
if(time_series == 1)
{
	# Get time_series_data.txt: variables
	data_time_series<-as.matrix(read.csv(paste(data_file_directory,"time_series_data.txt",sep=""),header=TRUE,sep=""))	

	# For each variable.
	# Check variable name in data_time_series.
	if(variable %in% data_time_series[,1])
	{
		print(" ")
		print("Plot time series")
		print(paste(variable,sep=""))
	
		j=match(variable,data_time_series[,1])

		# Create plots of variable for each single run.
		if(single_analysis == 1)
		{
			source(paste(root_directory,"Sub_Scripts_Plotting/Time_Series/Single_Plot.r",sep=""))
		}

		# Create plots of variable for each parameter value.
		if(batch_analysis == 1)
		{
			source(paste(root_directory,"Sub_Scripts_Plotting/Time_Series/Batch_Plot.r",sep=""))
		}

		# Create plots of variable for each parameter value in common graph.
		if(parameter_analysis == 1)
		{
			source(paste(root_directory,"Sub_Scripts_Plotting/Time_Series/Parameter_Plot.r",sep=""))
		}
	}
}


if(boxplot == 1)
{	
	# Get boxplot_data.txt: variables
	data_boxplot<-as.matrix(read.csv(paste(data_file_directory,"boxplot_data.txt",sep=""),header=TRUE,sep=""))	
	
	# For each variable.
	# Check variable name in data_boxplot.
	if(variable %in% data_boxplot[,1])
	{
		print(" ")
		print("Plot boxplots")	
		print(paste(variable,sep=""))

		j=match(variable,data_boxplot[,1])

		# Create boxplots of variable over population for each single run in chosen iterations.
		if(single_analysis == 1)
		{
			source(paste(root_directory,"Sub_Scripts_Plotting/Boxplot/Single_Boxplot.r",sep=""))
		}

		# Create boxplots of variable over runs for each parameter value in chosen iterations.
		if(batch_analysis == 1)
		{
			source(paste(root_directory,"Sub_Scripts_Plotting/Boxplot/Batch_Boxplot.r",sep=""))
		}

		# Create boxplots of variable over runs for each parameter value in a chosen iteration in a common graph.
		if(parameter_analysis == 1)
		{
			source(paste(root_directory,"Sub_Scripts_Plotting/Boxplot/Parameter_Boxplot.r",sep=""))
		}
	}
}


if(histogram == 1)
{		
	# Get histogram_data.txt: variables
	data_histogram<-as.matrix(read.csv(paste(data_file_directory,"histogram_data.txt",sep=""),header=TRUE,sep=""))	
	

	# For each variable.
	# Check variable name in data_hiistogram.
	if(variable %in% data_histogram[,1])
	{
		print(" ")
		print("Plot histograms")
		print(paste(variable,sep=""))

		j=match(variable,data_histogram[,1])

		# Calculate data for histograms.
		source(paste(root_directory,"Sub_Scripts_Plotting/Histogram/Get_Data_Histogram.r",sep=""))
		# Calculate data for distribution.
		source(paste(root_directory,"Sub_Scripts_Plotting/Histogram/Get_Data_Distribution.r",sep=""))	

		# Create Histograms of variable over population for each single run in chosen iterations.
		if(single_analysis == 1)
		{
			source(paste(root_directory,"Sub_Scripts_Plotting/Histogram/Single_Histogram.r",sep=""))
		}

		# Create Histograms of variable over runs for each parameter value in chosen iterations.
		if(batch_analysis == 1)
		{
			source(paste(root_directory,"Sub_Scripts_Plotting/Histogram/Batch_Histogram.r",sep=""))
		}

		# Create Histograms of variable over runs for each parameter value in chosen iterations.
		if(parameter_analysis == 1)
		{
			source(paste(root_directory,"Sub_Scripts_Plotting/Histogram/Parameter_Histogram.r",sep=""))
		}


	}
}





if(growth_rate == 1)
{	
	# Get growth_rate_data.txt: variables
	data_growth_rate<-as.matrix(read.csv(paste(data_file_directory,"growth_rate_data.txt",sep=""),header=TRUE,sep=""))	
	
	# For each variable.
	if(variable %in% data_growth_rate[,1])
	{
		print(" ")
		print("Plot growth_rate plots")
		print(paste(variable,sep=""))

		j=match(variable,data_growth_rate[,1])

		# Calculate data for growth rates.
		source(paste(root_directory,"Sub_Scripts_Plotting/Growth_Rate/Get_Data_Growth_Rate.r",sep=""))		

		# Create growth rates plots of variables for each single run.
		if(single_analysis == 1)
		{
			source(paste(root_directory,"Sub_Scripts_Plotting/Growth_Rate/Single_Growth_Rate_Plot.r",sep=""))
		}

		# Create growth rates plots of variables for each parameter value.
		if(batch_analysis == 1)
		{
			source(paste(root_directory,"Sub_Scripts_Plotting/Growth_Rate/Batch_Growth_Rate_Plot.r",sep=""))
		}

		# Create growth rates of variable over runs for each parameter value in a chosen iteration in a common graph.
		if(parameter_analysis == 1)
		{
			source(paste(root_directory,"Sub_Scripts_Plotting/Growth_Rate/Parameter_Growth_Rate_Plot.r",sep=""))
		}
	}
}

if(herfindahl == 1)
{
	# Get Time_Series_data.txt: variables
	data_herfindahl<-as.matrix(read.csv(paste(data_file_directory,"herfindahl_data.txt",sep=""),header=TRUE,sep=""))	

	# For each variable.
	if(variable %in% data_herfindahl[,1])
	{
		print(" ")
		print("Plot Herfindahl")
		print(paste(variable,sep=""))

		j=match(variable,data_herfindahl[,1])

		source(paste(root_directory,"Sub_Scripts_Plotting/Herfindahl/Get_Data_Herfindahl.r",sep=""))

		# Create plots of variable for each single run.
		if(single_analysis == 1)
		{
			source(paste(root_directory,"Sub_Scripts_Plotting/Herfindahl/Single_Herfindahl.r",sep=""))
		}

		# Create plots of variable for each parameter value.
		if(batch_analysis == 1)
		{
			source(paste(root_directory,"Sub_Scripts_Plotting/Herfindahl/Batch_Herfindahl.r",sep=""))
		}

		# Create plots of variable for each parameter value in common graph.
		if(parameter_analysis == 1)
		{
			source(paste(root_directory,"Sub_Scripts_Plotting/Herfindahl/Parameter_Herfindahl.r",sep=""))
		}
	
	}
}


if(ratio == 1)
{	
	# Get ratio_data.txt: variables
	data_ratio<-as.matrix(read.csv(paste(data_file_directory,"ratio_data.txt",sep=""),header=TRUE,sep=""))	
	
	# For each variable.
	if(variable %in% data_ratio[,1] || variable %in% data_ratio[,3]) 
	{
		print(" ")
		print("Plot ratios")
		print(paste(variable,sep=""))
		
		for(j in 1: length(data_ratio[,1]))
		{
			indicator<-0

			# Variable is in row j
			if(data_ratio[j,1] == variable || data_ratio[j,3] == variable)
			{
				# Variable is Process Var in Column 1
				if(data_ratio[j,1] == variable && data_ratio[j,2] == 1)
				{
					# Get variable name.
					variable1<-data_ratio[j,1]
					variable2<-data_ratio[j,3]
					get_var<-variable2
					indicator<-1
				}
				# Variable is Process Var in Column 3
				if(data_ratio[j,3] == variable && data_ratio[j,4] == 1 && indicator == 0)
				{
					# Get variable name.
					variable1<-data_ratio[j,1]
					variable2<-data_ratio[j,3]
					get_var<-variable1
					indicator<-1
				}
			
				if(indicator == 1)
				{
					# If get_var is not in environment ->get it
					if((paste(get_var,"_",parameter_values[1],"_",1,sep="") %in% ls())!=TRUE)
					{
						source(paste(root_directory,"Get_Sub_Data.r",sep=""))
					}

					# Ratio of variables for each single run.
					if(single_analysis == 1)
					{
						source(paste(root_directory,"Sub_Scripts_Plotting/Ratio/Single_Ratio_Plot.r",sep=""))
					}

					# Ratio of variables for each parameter value.
					if(batch_analysis == 1)
					{
						source(paste(root_directory,"Sub_Scripts_Plotting/Ratio/Batch_Ratio_Plot.r",sep=""))
					}

					# Ratio of variables for each parameter value in one commen graph.
					if(parameter_analysis == 1)
					{
						source(paste(root_directory,"Sub_Scripts_Plotting/Ratio/Parameter_Ratio_Plot.r",sep=""))
					}
				}
			}
		}
	}
}


if(multiple_time_series == 1)
{	
	# Get multiple_time_series_data.txt: variables
	data_multiple_time_series<-as.matrix(read.csv(paste(data_file_directory,"multiple_time_series_data.txt",sep=""),header=TRUE,sep=""))	
	

	print("MTS")

	if(variable %in% data_multiple_time_series[,1])
	{
		print(" ")
		print("Plot Multiple Time Series")
		print(paste(variable,sep=""))

		# For each variable.
		for(j in 1:length(data_multiple_time_series[,1]))
		{
			# Get variable name.
			if(variable == data_multiple_time_series[j,1] && data_multiple_time_series[j,2]==1 && data_multiple_time_series[j,3] != "0")
			{
				# How many variables should be plotted in one plot.
				number_variables<-as.numeric(data_multiple_time_series[j,3])

				for(n in 1:(number_variables-1))
				{
					get_var <- data_multiple_time_series[(j+n),1]

					# If get_var is not in environment ->get it
					if((paste(get_var,"_",parameter_values[1],"_",1,sep="") %in% ls())!=TRUE)
					{
						source(paste(root_directory,"Get_Sub_Data.r",sep=""))
					}
				}

				# Multiple Time Series of variables for each single run.
				if(single_analysis == 1)
				{
					source(paste(root_directory,"Sub_Scripts_Plotting/Multiple_Time_Series/Single_Multiple_Time_Series_Plot.r",sep=""))
				}

				# Multiple Time Series of variables for each parameter value.
				if(batch_analysis == 1)
				{
					source(paste(root_directory,"Sub_Scripts_Plotting/Multiple_Time_Series/Batch_Multiple_Time_Series_Plot.r",sep=""))
				}
			}
		}
	}
}

if(bandpass_filter == 1)
{	


	# Get multiple_time_series_data.txt: variables
	data_bandpass_filter<-as.matrix(read.csv(paste(data_file_directory,"bandpass_filter_data.txt",sep=""),header=TRUE,sep=""))	
	
	if(variable %in% data_bandpass_filter[,1])
	{
		print(" ")
		print("Bandpass Filter")
		print(paste(variable,sep=""))

		# For each variable.
		for(j in 1:length(data_bandpass_filter[,1]))
		{
			# Data for variable. Only one time.
			if(variable == data_bandpass_filter[j,1] && data_bandpass_filter[j,2]==1 && as.numeric(data_bandpass_filter[j,10]) > 0)
			{

				# Calculate data for trend.
				var<-variable
				source(paste(root_directory,"Sub_Scripts_Plotting/Bandpass_Filter/Get_Data_Bandpass_Filter.r",sep=""))
				break
			}
		}

		# For each variable.
		for(j in 1:length(data_bandpass_filter[,1]))
		{
			# Data for variable. Only one time.
			if(variable == data_bandpass_filter[j,1] && data_bandpass_filter[j,2]==1 && as.numeric(data_bandpass_filter[j,10]) > 1)
			{
				number_variables = as.numeric(data_bandpass_filter[j,10])
				for(n in 1:(number_variables-1))
				{
					#Get sub data
					get_var <- data_bandpass_filter[j+n,1]

					# If get_var is not in environment ->get it
					if((paste(get_var,"_",parameter_values[1],"_",1,sep="") %in% ls())!=TRUE)
					{
						source(paste(root_directory,"Get_Sub_Data.r",sep=""))
					}

					# Calculate data for trend.
					var<-get_var
					source(paste(root_directory,"Sub_Scripts_Plotting/Bandpass_Filter/Get_Data_Bandpass_Filter.r",sep=""))
				}
				
			}
		}

	
		# For each variable.
		for(j in 1:length(data_bandpass_filter[,1]))
		{
			# Data for variable. Only one time.
			if(variable == data_bandpass_filter[j,1] && data_bandpass_filter[j,2]==1 && as.numeric(data_bandpass_filter[j,10]) > 0)
			{
				# Multiple Time Series of variables for each single run.
				if(single_analysis == 1)
				{
					source(paste(root_directory,"Sub_Scripts_Plotting/Bandpass_Filter/Single_Bandpass_Filter_Plot.r",sep=""))
				}

				# Multiple Time Series of variables for each parameter value.
				if(batch_analysis == 1)
				{
					source(paste(root_directory,"Sub_Scripts_Plotting/Bandpass_Filter/Batch_Bandpass_Filter_Plot.r",sep=""))
				}

				# Multiple Time Series of variables for each parameter value.
				if(parameter_analysis == 1)
				{
					source(paste(root_directory,"Sub_Scripts_Plotting/Bandpass_Filter/Parameter_Bandpass_Filter_Plot.r",sep=""))
				}
			}
		}
	}
}


if(correlation_distribution == 1)
{	
	# Get correlation_data.txt: variables
	data_correlation_distribution<-as.matrix(read.csv(paste(data_file_directory,"correlation_distribution_data.txt",sep=""),header=TRUE,sep=""))	
	
	# For each variable.
	if(variable %in% data_correlation_distribution[,1] || variable %in% data_correlation_distribution[,3])
	{
		print(" ")
		print("Correlation Distribution")
		print(paste(variable,sep=""))
		
		for(j in 1:length(data_correlation_distribution[,1]))
		{
			indicator <- 0

			if(variable %in% data_correlation_distribution[j,1] && as.numeric(data_correlation_distribution[j,2] == 1))
			{
				# Get variable name.
				variable1<-data_correlation_distribution[j,1]
				variable2<-data_correlation_distribution[j,3]
				get_var<-variable2
				indicator <- 1
			}

			if(variable %in% data_correlation_distribution[j,3] && as.numeric(data_correlation_distribution[j,4] == 1))
			{
				# Get variable name.
				variable1<-data_correlation_distribution[j,1]
				variable2<-data_correlation_distribution[j,3]
				get_var<-variable1
				indicator <- 1
			}

			if(indicator == 1)
			{
				# If get_var is not in environment ->get it
				if((paste(get_var,"_",parameter_values[1],"_",1,sep="") %in% ls())!=TRUE)
				{
					source(paste(root_directory,"Get_Sub_Data.r",sep=""))
				}

				source(paste(root_directory,"Sub_Scripts_Plotting/Correlation_Distribution/Get_Correlation_Distribution_Data.r",sep=""))

	
				# Correlation of variables for each single run.
				if(single_analysis == 1)
				{
					source(paste(root_directory,"Sub_Scripts_Plotting/Correlation_Distribution/Single_Correlation_Distribution_Plot.r",sep=""))
				}

				# Correlation of variables for each parameter value.
				if(batch_analysis == 1)
				{
					source(paste(root_directory,"Sub_Scripts_Plotting/Correlation_Distribution/Batch_Correlation_Distribution_Plot.r",sep=""))

				}

				# Correlation of variables for each parameter value.
				if(parameter_analysis == 1)
				{
					source(paste(root_directory,"Sub_Scripts_Plotting/Correlation_Distribution/Parameter_Correlation_Distribution_Plot.r",sep=""))
				}
			}
		}
	}
}


if(scatter == 1)
{	
	print("Plot scatter plots")

	# Get scatter_data.txt: variables
	data_scatter<-as.matrix(read.csv(paste(data_file_directory,"scatter_data.txt",sep=""),header=TRUE,sep=""))	
	
	# For each variable.
	if(variable %in% data_scatter[,1] || variable %in% data_scatter[,3])
	{
		print(" ")
		print("Scatter Plots")
		print(paste(variable,sep=""))
		
		for(j in 1:length(data_scatter[,1]))
		{
			indicator <- 0

			if(variable %in% data_scatter[j,1] && as.numeric(data_scatter[j,2] == 1))
			{
				# Get variable name.
				variable1<-data_scatter[j,1]
				variable2<-data_scatter[j,3]
				get_var<-variable2
				indicator <- 1
			}

			if(variable %in% data_scatter[j,3] && as.numeric(data_scatter[j,4] == 1))
			{
				# Get variable name.
				variable1<-data_scatter[j,1]
				variable2<-data_scatter[j,3]
				get_var<-variable1
				indicator <- 1
			}

			if(indicator == 1)
			{
				# If get_var is not in environment ->get it
				if((paste(get_var,"_",parameter_values[1],"_",1,sep="") %in% ls())!=TRUE)
				{
					source(paste(root_directory,"Get_Sub_Data.r",sep=""))
				}

				# Correlation of variables for each single run.
				if(single_analysis == 1)
				{
					source(paste(root_directory,"Sub_Scripts_Plotting/Scatter/Single_Scatter.r",sep=""))
				}

				# Correlation of variables for each parameter value.
				if(batch_analysis == 1)
				{
					source(paste(root_directory,"Sub_Scripts_Plotting/Scatter/Batch_Scatter.r",sep=""))
				}

				# Correlation of variables for each parameter value.
				if(parameter_analysis == 1)
				{
					#source(paste(root_directory,"Sub_Scripts_Plotting/Scatter/Parameter_Scatter.r",sep=""))
				}
			}
		}
	}
}


if(cross_correlation_function == 1)
{	
	# Get correlation_data.txt: variables
	data_cross_correlation_function<-as.matrix(read.csv(paste(data_file_directory,"cross_correlation_function.txt",sep=""),header=TRUE,sep=""))	
	
	# For each variable.
	if(variable %in% data_cross_correlation_function[,1] || variable %in% data_cross_correlation_function[,4])
	{
		print(" ")
		print("Cross Correlation Function")
		print(paste(variable,sep=""))
		
		for(j in 1:length(data_cross_correlation_function[,1]))
		{
			indicator <- 0

			if(variable %in% data_cross_correlation_function[j,1] && as.numeric(data_cross_correlation_function[j,3] == 1))
			{
				# Get variable name.
				variable1<-data_cross_correlation_function[j,1]
				variable2<-data_cross_correlation_function[j,4]
				get_var<-variable2
				indicator <- 1
			}

			if(variable %in% data_cross_correlation_function[j,4] && as.numeric(data_cross_correlation_function[j,6] == 1))
			{
				# Get variable name.
				variable1<-data_cross_correlation_function[j,1]
				variable2<-data_cross_correlation_function[j,4]
				get_var<-variable1
				indicator <- 1
			}

			if(indicator == 1)
			{
				# If get_var is not in environment ->get it
				if((paste(get_var,"_",parameter_values[1],"_",1,sep="") %in% ls())!=TRUE)
				{
					source(paste(root_directory,"Get_Sub_Data.r",sep=""))
				}

				# Correlation of variables for each single run.
				if(single_analysis == 1)
				{
					source(paste(root_directory,"Sub_Scripts_Plotting/Cross_Correlation_Function/Single_Cross_Correlation_Function.r",sep=""))
				}

				# Correlation of variables for each parameter value.
				if(batch_analysis == 1)
				{
					source(paste(root_directory,"Sub_Scripts_Plotting/Cross_Correlation_Function/Batch_Cross_Correlation_Function.r",sep=""))
				}

				# Correlation of variables for each parameter value.
				if(parameter_analysis == 1)
				{
					#source(paste(root_directory,"Sub_Scripts_Plotting/Correlation_Distribution/Parameter_Correlation_Distribution_Plot.r",sep=""))
				}
			}
		}
	}
}


if(heat_maps == 1)
{
	# Get Time_Series_data.txt: variables
	data_heat_maps<-as.matrix(read.csv(paste(data_file_directory,"heat_maps_data.txt",sep=""),header=TRUE,sep=""))	

	# For each variable.
	if(variable %in% data_heat_maps[,1] || variable %in% data_heat_maps[,3])
	{
		print(" ")
		print("Plot Heat Maps")
		print(paste(variable,sep=""))

		for(j in 1:length(data_heat_maps[,1]))
		{
			indicator<-0

			if(data_heat_maps[j,3] != "No")
			{

				if(variable %in% data_heat_maps[j,1] && as.numeric(data_heat_maps[j,2] == 1))
				{
					# Get variable name.
					variable1<-data_heat_maps[j,1]
					variable2<-data_heat_maps[j,3]
					get_var<-variable2

					# If get_var is not in environment ->get it
					if((paste(get_var,"_matrix_",parameter_values[1],"_",1,sep="") %in% ls())!=TRUE)
					{
						source(paste(root_directory,"Get_Sub_Data_Matrix.r",sep=""))
					}

					name<-data_heat_maps[j,10]

					source(paste(root_directory,"Get_Data_Matrix_Division.r",sep=""))

					indicator<-1
					
				}

				if(variable %in% data_heat_maps[j,3] && as.numeric(data_heat_maps[j,4] == 1))
				{
					# Get variable name.
					variable1<-data_heat_maps[j,1]
					variable2<-data_heat_maps[j,3]
					get_var<-variable1


					# If get_var is not in environment ->get it
					if((paste(get_var,"_matrix_",parameter_values[1],"_",1,sep="") %in% ls())!=TRUE)
					{
						source(paste(root_directory,"Get_Sub_Data_Matrix.r",sep=""))
					}

					name<-data_heat_maps[j,10]

					source(paste(root_directory,"Get_Data_Matrix_Division.r",sep=""))

					indicator<-1
				}

			}
			else{

	
				if(variable %in% data_heat_maps[j,1] && as.numeric(data_heat_maps[j,2] == 1))
				{
					name<-variable
					indicator<-1
					
				}
			}

			if(indicator == 1)
			{
				# Create plots of variable for each single run.
				if(single_analysis == 1)
				{
					#source(paste(root_directory,"Sub_Scripts_Plotting/Heat_Maps/Single_Heat_Maps.r",sep=""))
				}

				# Create plots of variable for each parameter value.
				if(batch_analysis == 1)
				{
					source(paste(root_directory,"Sub_Scripts_Plotting/Heat_Maps/Batch_Heat_Maps.r",sep=""))
				}
			}
		}
	}
}


if(heat_maps_2V == 1)
{
	# Get Time_Series_data.txt: variables
	data_heat_maps_2V<-as.matrix(read.csv(paste(data_file_directory,"heat_maps_data_2V.txt",sep=""),header=TRUE,sep=""))	

	# For each variable.
	if(variable %in% data_heat_maps_2V[,1] || variable %in% data_heat_maps_2V[,3] || variable %in% data_heat_maps_2V[,7] || variable %in% data_heat_maps_2V[,9])
	{
		print(" ")
		print("Plot Heat Maps 2 Variables")
		print(paste(variable,sep=""))

		# For each row
		for(j in 1:length(data_heat_maps_2V[,1]))
		{	
			indicator<-0

			#Check if variable is in row j and is a process variable
			for(c in c(1,3,7,9))
			{
				if(data_heat_maps_2V[j,c] == variable && as.numeric(data_heat_maps_2V[j,(c+1)])==1)
				{
					#variable is in row j and is a process variable
					indicator<-1
					break
				}
			}

			# Go on and get data for variables in row j
			if(indicator == 1)
			{
				# For each var in row j
				for(c in c(1,3,7,9))
				{
					#If there is a var in (jc) and it is not the actual variable and is no process variable and is not in the environment yet
					if(data_heat_maps_2V[j,c] != "No" && data_heat_maps_2V[j,c] != variable && as.numeric(data_heat_maps_2V[j,(c+1)])!=1 && (paste(data_heat_maps_2V[j,c],"_matrix_",parameter_values[1],"_",1,sep="") %in% ls())!=TRUE)
					{
						# Get the raw matrix data of var
						get_var<-data_heat_maps_2V[j,c]
						source(paste(root_directory,"Get_Sub_Data_Matrix.r",sep=""))
					}
				}#Now all data is available

				# There is a denominator
				if(data_heat_maps_2V[j,3]!="No")
				{
					variable1<-data_heat_maps_2V[j,1]
					variable2<-data_heat_maps_2V[j,3]
			
					name1<-paste(variable1,"_",variable2,sep="")
					name<-paste(variable1,"_",variable2,sep="")

					source(paste(root_directory,"Get_Data_Matrix_Division.r",sep=""))
				}
				else{
					name1<-data_heat_maps_2V[j,1]
				}

				# There is a denominator2
				if(data_heat_maps_2V[j,9]!="No")
				{
					variable1<-data_heat_maps_2V[j,7]
					variable2<-data_heat_maps_2V[j,9]
			
					name2<-paste(variable1,"_",variable2,sep="")
					name<-paste(variable1,"_",variable2,sep="")

					source(paste(root_directory,"Get_Data_Matrix_Division.r",sep=""))
				}
				else{
					name2<-data_heat_maps_2V[j,7]
				}

				# Create plots of variable for each single run.
				if(single_analysis == 1)
				{
					#source(paste(root_directory,"Sub_Scripts_Plotting/Heat_Maps/Single_Heat_Maps.r",sep=""))
				}

				# Create plots of variable for each parameter value.
				if(batch_analysis == 1)
				{
					source(paste(root_directory,"Sub_Scripts_Plotting/Heat_Maps/Batch_Heat_Maps_2V.r",sep=""))
				}
			}	
		}
	}
}


if(quantiles == 1)
{
	# Get time_series_data.txt: variables
	data_quantiles<-as.matrix(read.csv(paste(data_file_directory,"quantiles_data.txt",sep=""),header=TRUE,sep=""))	

	# For each variable.
	# Check variable name in data_time_series.
	if(variable %in% data_quantiles[,1])
	{
		print(" ")
		print("Plot Quantiles")
		print(paste(variable,sep=""))
	
		j=match(variable,data_quantiles[,1])

		# Create plots of variable for each single run.
		if(single_analysis == 1)
		{
			source(paste(root_directory,"Sub_Scripts_Plotting/Quantiles/Single_Quantiles.r",sep=""))
		}

		# Create plots of variable for each parameter value.
		if(batch_analysis == 1)
		{
			source(paste(root_directory,"Sub_Scripts_Plotting/Quantiles/Batch_Quantiles.r",sep=""))
		}

		# Create plots of variable for each parameter value in common graph.
		if(parameter_analysis == 1)
		{
			#source(paste(root_directory,"Sub_Scripts_Plotting/Time_Series/Parameter_Plot.r",sep=""))
		}
	}
}
