# Plot for single run.

for(p in 1:number_of_parameters)
{
	# Value of parameter in order to give variables distinguishable names.
	parameter<-parameter_values[p]
	
	# For each run.
	for(r in 1:runs)
	{	
		# Plot mean/sum of variable over population.
		A<-eval(parse(text=eval(paste("cor_",variable1,"_",variable2,"_",parameter,"_",r,sep=""))))

		# Max and Min.
		max<-max(A,na.rm=TRUE)
		min<-min(A,na.rm=TRUE)
	
		# Plot.
		pdf(paste(plot_directory,"Correlation_Distribution/Single/batch_",parameter,"/run_",r,"/","cor_",variable1,"_",variable2,".pdf",sep=""))
		plot(A,type="l",xlab="Months",ylim=c(min,max),ylab=paste("cor_",variable1,"_",variable2,sep=""),col=1,lty=1,lwd=2)
		dev.off()

		rm(A)
		rm(min)
		rm(max)
	}
}
















