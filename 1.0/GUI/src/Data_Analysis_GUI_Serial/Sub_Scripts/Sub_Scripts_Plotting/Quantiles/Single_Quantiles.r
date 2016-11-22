for(p in 1:number_of_parameters)
{
	# Value of parameter in order to give variables distinguishable names.
	parameter<-parameter_values[p]
	
	# For each run.
	for(r in 1:runs)
	{	
		A<-eval(parse(text=eval(paste(variable,"_matrix_",parameter,"_",r,sep=""))))

		A<-apply(A,2,quantile,na.rm=TRUE)
		
		# Max and Min.
		max<-max(A,na.rm=TRUE)
		min<-min(A,na.rm=TRUE)
	
		# Quantile in einer Graphik ausgeben.
		pdf(paste(plot_directory,"Quantiles/Single/batch_",parameter,"/run_",r,"/",variable,"_quant.pdf",sep=""))
		plot(A[1,],type="l",xlab="Months",ylim=c(min,max),ylab=paste(variable,"_quantiles",sep=""),col=1,lty=1,lwd=1)
		for(y in 2:5)
		{
			if(y==2 || y==4)
			type<-2

			if(y==3)
			type<-3

			if(y==5)
			type<-1
		
			lines(A[y,],col=1,lty=1,lwd=type)
		}
		dev.off()

		rm(A)
	}
}
