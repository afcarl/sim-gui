# Plot for batch runs.

for(p in 1:number_of_parameters)
{
	# Value of parameter in order to give variables distinguishable names.
	parameter<-parameter_values[p]
	
#FULL/DIST	
############### Plot variable for single runs per parameter value in one common graph.
	A<-eval(parse(text=eval(paste("batch_herf_",variable,"_",parameter,sep=""))))

	# Max and Min.
	max<-max(A,na.rm=TRUE)
	min<-min(A,na.rm=TRUE)
	
	# Plot.
	pdf(paste(plot_directory,"Herfindahl/Batch/batch_",parameter,"/Full/batch_herf",variable,".pdf",sep=""))
	plot(A[,1],type="l",xlab="Months",ylim=c(min,max),ylab=paste(variable),col=1,lty=1,lwd=2)
	if(runs > 1)
	{		
		for(y in 2:runs)
		{
			lines(A[,y],col=1,lty=1,lwd=2)
		}
	}
	dev.off()

	rm(A)
	rm(y)
	rm(min)
	rm(max)




#MEAN/DIST
############### Plot the mean of variable over single runs per parameter value.
	A<-eval(parse(text=eval(paste("batch_mean_herf_",variable,"_",parameter,sep=""))))

	# Max and Min.
	max<-max(A,na.rm=TRUE)
	min<-min(A,na.rm=TRUE)
	
	# Plot.
	pdf(paste(plot_directory,"Herfindahl/Batch/batch_",parameter,"/Mean/batch_mean_herf_",variable,".pdf",sep=""))
	plot(A,type="l",xlab="Months",ylim=c(min,max),ylab=paste(variable),col=1,lty=1,lwd=2)
	dev.off()

	rm(A)
	rm(min)
	rm(max)
	
}
