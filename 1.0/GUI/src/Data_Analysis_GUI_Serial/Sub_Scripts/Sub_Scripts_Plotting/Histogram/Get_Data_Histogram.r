# Histogram over population for each parameter.

# Multi dimensional.
if(agent_number>1)
{
	for(p in 1:number_of_parameters)
	{
		# Value of parameter in order to give variables distinguishable names.
		parameter<-parameter_values[p]

	
		# For each chosen iteration.
		for(b in 1: length(histogram_iteration_vector))
		{	
			# For each run.
			for(r in 1:runs)
			{
				# Get variable_matrix data for histograms over population.			
				A<-eval(parse(text=eval(paste(variable,"_matrix_",parameter,"_",r,sep=""))))

				if(r == 1)
				{
					# Create matrix: Rows: Number of Agents Columns: runs
					temp_mat<-matrix(0,length(A[,1]),runs)	
					la<-length(A[,1])	
				}
			
				
				# Fill matrix with distribution over agents in chosen iteration for each single run.
				temp_mat[,r]<-A[,histogram_iteration_vector[b]]

				rm(A)	
			}

			# Delete zeros. Bankrupt firms.
			temp_mat[temp_mat==0]<-"NA"
			temp_mat<-matrix(as.numeric(temp_mat),la,runs)

			# Store matrix for one chosen iteration for one paramter value. One column contains the data over all agents in the chosen iteration in one run.
			if(save_single_data == 1)
			{
				eval(call("<-",(paste("single_",variable,"_hist_",parameter,"_",histogram_iteration_vector[b],sep="")),temp_mat))
			}

			# Max and Min.
			min<-min(temp_mat,na.rm=TRUE)
			max<-max(temp_mat,na.rm=TRUE)

			# Create matrix: Rows: runs Columns: bins of histograms of single runs.
			temp_hist_mat<-matrix(0,runs,bins)

			for(c in 1:runs)
			{
				# Get histogramm information of each single run. Histogram has ten bins which are the same for each single 
				# run histogram.
				temp_hist<-hist(temp_mat[,c],breaks=c(seq(min,max,(max-min)/bins)),plot=FALSE)
		
				temp_hist_mat[c,]<-temp_hist$counts/sum(temp_hist$count)

			}

			rm(temp_mat)

			# Delete column names of temp_mat.
			colnames(temp_hist_mat)<-rep("",ncol(temp_hist_mat));temp_hist_mat

			if(save_batch_data == 1 || save_parameter_data == 1)
			{
				# Store the counts/density for each run in each bin to plot for each bin a boxplot over the counts of each run.
				eval(call("<-",(paste("batch_",variable,"_counts_",parameter,"_",histogram_iteration_vector[b],sep="")),temp_hist_mat))
				# Store the bins for each batch.
				eval(call("<-",(paste("batch_",variable,"_bins_",parameter,"_",histogram_iteration_vector[b],sep="")),round(temp_hist$mids,digits=2)))
				# Store the means of counts in each bin.
				eval(call("<-",(paste("batch_",variable,"_bins_mean_",parameter,"_",histogram_iteration_vector[b],sep="")),colMeans(temp_hist_mat,na.rm=TRUE)))				

			}

			if(p == 1)
			{
				temp_mat<-matrix(0,bins,number_of_parameters)
				eval(call("<-",(paste("par_",variable,"_bins_",histogram_iteration_vector[b],sep="")),temp_mat))
				eval(call("<-",(paste("par_",variable,"_bins_mean_",histogram_iteration_vector[b],sep="")),temp_mat))
			}

			if(save_parameter_data== 1)
			{
				# Store the bins for each batch for each parameter
				temp_par_mat<-eval(parse(text=eval(paste("par_",variable,"_bins_",histogram_iteration_vector[b],sep=""))))
				temp_par_mat[,p]<-round(temp_hist$mids,digits=2)
				eval(call("<-",(paste("par_",variable,"_bins_",histogram_iteration_vector[b],sep="")),temp_par_mat))

				# Store the means of counts in each bin for each parameter.
				temp_par_mat<-eval(parse(text=eval(paste("par_",variable,"_bins_mean_",histogram_iteration_vector[b],sep=""))))
				temp_par_mat[,p]<-colMeans(temp_hist_mat,na.rm=TRUE)
				eval(call("<-",(paste("par_",variable,"_bins_mean_",histogram_iteration_vector[b],sep="")),temp_par_mat))
			}

			if(p == 1)
			rm(temp_mat)

			rm(temp_hist_mat)
			rm(temp_hist)
			rm(max)
			rm(min)
			rm(temp_par_mat)		
		}
	}
}
