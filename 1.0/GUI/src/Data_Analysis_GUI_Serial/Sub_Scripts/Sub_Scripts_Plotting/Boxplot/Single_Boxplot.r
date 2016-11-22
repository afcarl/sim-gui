# Boxplot over population for each single run.
if(agent_number>1)
{
	for(p in 1:number_of_parameters)
	{
		# Value of parameter in order to give variables distinguishable names.
		parameter<-parameter_values[p]
	
		# For each run.
		for(r in 1:runs)
		{	
		
			# Get variable_matrix data for boxplots over population.			
			A<-eval(parse(text=eval(paste(variable,"_matrix_",parameter,"_",r,sep=""))))

			# Create matrix: Rows: number of agents and Columns: iterations for boxplots.
			temp_mat<-matrix(0,length(A[,1]),length(boxplot_iteration_vector))

			# Delete column names of temp_mat.
			colnames(temp_mat)<-rep("",ncol(temp_mat));temp_mat

			# Fill temp_mat with chosen columns/iterations of variable_matrix.
			for(b in 1: length(boxplot_iteration_vector))
			{
				temp_mat[,b]<-A[,boxplot_iteration_vector[b]]
			}

			# Plot.
			pdf(paste(plot_directory,"Boxplot/Single/batch_",parameter,"/run_",r,"/box_",variable,"_population.pdf",sep=""))
			boxplot(temp_mat,ylab=variable,xlab="Periods")
			axis(1,at=1:length(boxplot_iteration_vector), labels=paste(boxplot_iteration_vector))
			axis(2)
			dev.off()
		
			rm(temp_mat)
			rm(A)
		
		
		}
	}
}



