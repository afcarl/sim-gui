# Histogram over population for each single run.


# Multi dimensional.
if(agent_number>1)
{
	for(p in 1:number_of_parameters)
	{
		# Value of parameter in order to give variables distinguishable names.
		parameter<-parameter_values[p]
	
		# For every chosen iteration.
		for(b in 1: length(histogram_iteration_vector))
		{
			A<-eval(parse(text=eval(paste("single_",variable,"_hist_",parameter,"_",histogram_iteration_vector[b],sep=""))))

			# For each run.
			for(r in 1:runs)
			{	
				# Max and Min.
				min<-min(A[,r],na.rm=TRUE)
				max<-max(A[,r],na.rm=TRUE)


				temp_hist<-hist(A[,r],plot=FALSE)
				temp_hist$density<-temp_hist$counts/sum(temp_hist$count)

				# Plot.
				pdf(paste(plot_directory,"Histogram/Single/batch_",parameter,"/run_",r,
				"/hist_",variable,"_period_",histogram_iteration_vector[b]*frequency/20,".pdf",sep=""))

				#hist(A[,histogram_iteration_vector[b]],xlab=paste(variable),
				plot(temp_hist,xlab=paste(variable),
				main=paste(variable,"_period_",histogram_iteration_vector[b]*frequency/20,sep=""),
				freq=FALSE,xlim=c(min,max))

				# Density line.
				lines(density(A[,r],na.rm=TRUE),col=2)
			
				dev.off()

				# Get distribution over agents in period
				x<-A[,r]
				# Sort x: decreasing. From large to small.
				x <- sort(x,decreasing=TRUE)
				# Rank sorted observations. Largest value has largest rank.
				y <- rank(x,ties.method="first")
				# Sort ranks decreasing. From large to small. If two or more values are equal ranks may not be decreasing. Sort them in order to be sure that ranks are decreasing.
				y <- sort(y,decreasing=TRUE)
				# Sort x: increasing. Smallest value has largest rank.
				x <- sort(x)

				x<-log(x)
				x[x==-Inf]<-"Na"
				x<-c(as.numeric(x))

				y<-log(y)
				y[y==-Inf]<-"Na"
				y<-c(as.numeric(y))
	
				# Plot.
				pdf(paste(plot_directory,"Histogram/Single/batch_",parameter,"/run_",r,
				"/distribution_",variable,"_period_",histogram_iteration_vector[b]*frequency/20,".pdf",sep=""))

				plot(x,y,xlab=paste("log(",variable,")",sep=""),ylab=paste("log(Rank)",sep=""))

				dev.off()
				
				rm(x)
				rm(y)

			}
			rm(A)
		}
	}
}
