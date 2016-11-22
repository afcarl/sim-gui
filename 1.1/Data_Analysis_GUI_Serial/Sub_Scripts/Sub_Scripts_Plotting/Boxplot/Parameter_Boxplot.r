
	# For each chosen iteration.
	for(b in 1:length(boxplot_iteration_vector))
	{
		# Create matrix: Rows: number of agents and Columns: iterations for boxplots.
		temp_mat<-matrix(0,runs,number_of_parameters)

		# For each parameter values.
		for(c in 1:number_of_parameters)
		{
			# Get parameter value.
			parameter<-parameter_values[c]

			# Get batch_variable_dist data for each parameter value. Rows: iterations and Columns: Runs			
			A<-eval(parse(text=eval(paste("batch_",variable,"_",parameter,sep=""))))


			if(runs > 1)
			{
				if(last_observations >0)
				{
					# c-th column is filled with chosen/row iteration from batch_variable_dist
					temp_mat[,c]<-colMeans(A[(boxplot_iteration_vector[b]-last_observations):boxplot_iteration_vector[b],])
				}else
				{
					temp_mat[,c]<-A[boxplot_iteration_vector[b],]
				}
			}else
			{
				if(last_observations >0)
				{
					temp_mat[,c]<-mean(A[(boxplot_iteration_vector[b]-last_observations):boxplot_iteration_vector[b],])
				}else
				{
					temp_mat[,c]<-A[boxplot_iteration_vector[b],]
				}
			}
		}

		# Delete column names of temp_mat.
		colnames(temp_mat)<-rep("",ncol(temp_mat));temp_mat

		# Plot.
		pdf(paste(plot_directory,"Boxplot/Parameter/par_box_",variable,"_period_",boxplot_iteration_vector[b]*frequency/20,".pdf",sep=""))
		boxplot(temp_mat,ylab=variable,xlab="Parameters")
		axis(1,at=1:number_of_parameters,labels=paste(parameter_values))
		axis(2)
		dev.off()

		rm(temp_mat)
		rm(A)
	}		








