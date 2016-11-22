# Histogram over population for each batch run.

# Multi dimensional.
if(agent_number>1)
{
	for(p in 1:number_of_parameters)
	{
		# Value of parameter in order to give variables distinguishable names.
		parameter<-parameter_values[p]

	
		# Fill temp_mat with chosen columns/iterations of variable_matrix.
		for(b in 1: length(histogram_iteration_vector))
		{
			# Get variable_matrix data for histograms over population.			
			A<-eval(parse(text=eval(paste("batch_",variable,"_counts_",parameter,"_",histogram_iteration_vector[b],sep=""))))
			B<-eval(parse(text=eval(paste("batch_",variable,"_bins_",parameter,"_",histogram_iteration_vector[b],sep=""))))
			C<-eval(parse(text=eval(paste("batch_",variable,"_bins_mean_",parameter,"_",histogram_iteration_vector[b],sep=""))))
	
			

			# Plot.
			pdf(paste(plot_directory,
			"Histogram/Batch/batch_",parameter,"/batch_hist_",variable,"_period_",histogram_iteration_vector[b]*frequency/20,".pdf",sep=""))
			boxplot(A,ylab="Density",xlab=paste(variable,"_period_",histogram_iteration_vector[b]*frequency/20,sep=""),ylim=c(0,1))
			lines(seq(1,bins,1),C,type="l")
			axis(1,at=1:bins, labels=paste(B))
			axis(2)
			dev.off()

			rm(A)
			rm(B)
			rm(C)	


			A<-eval(parse(text=eval(paste("batch_",variable,"_distribution_X_",parameter,"_",histogram_iteration_vector[b],sep=""))))
			B<-eval(parse(text=eval(paste("batch_",variable,"_distribution_Y_",parameter,"_",histogram_iteration_vector[b],sep=""))))
			
			pdf(paste(plot_directory,"Histogram/Batch/batch_",parameter,"/batch_distribution_",variable,"_period_",histogram_iteration_vector[b]*frequency/20,".pdf",sep=""))
			plot(A,B,,xlab=paste("log(",variable,")_period_",histogram_iteration_vector[b]*frequency/20,sep=""),ylab=paste("log(Rank)",sep=""))
			dev.off()
		
			rm(A)
			rm(B)
		}
	}
}


