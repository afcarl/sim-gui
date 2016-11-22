	for(p in 1:number_of_parameters)
	{
		# Value of parameter in order to give variables distinguishable names.
		parameter<-parameter_values[p]
	
		# For each run.
		for(r in 1:runs)
		{
			A<-eval(parse(text=eval(paste(variable,"_",parameter,"_",r,sep=""))))
			
			B<-eval(parse(text=eval(paste(variable,"_matrix_",parameter,"_",r,sep=""))))
			
			C<-matrix(0,length(B[,1]),length(B[1,]))
			
			for(a in 1:length(B[,1]))
			{
				C[a,]<-B[a,]/A
			}
	
			D<-C*C

			E<-colSums(D,na.rm=TRUE)

			# Max and Min.
			max<-max(E,na.rm=TRUE)
			min<-min(E,na.rm=TRUE)
	
			# Plot.
			pdf(paste(plot_directory,"Herfindahl/Single/batch_",parameter,"/run_",r,"/",variable,"_herfindahl.pdf",sep=""))
			plot(E,type="l",xlab="Months",ylim=c(min,max),ylab=paste(variable),col=1,lty=1,lwd=2)
			dev.off()

		}
	}

