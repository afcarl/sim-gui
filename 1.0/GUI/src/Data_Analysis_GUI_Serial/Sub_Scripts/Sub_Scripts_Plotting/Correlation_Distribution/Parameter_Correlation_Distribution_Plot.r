	A<-eval(parse(text=eval(paste("par_cor_",variable1,"_",variable2,sep=""))))

	# Max and Min
	max<-max(A,na.rm=TRUE)
	min<-min(A,na.rm=TRUE)

	# Zeitreihen der einzelnen runs in einen Graphik ausgeben.
	pdf(paste(plot_directory,"Correlation_Distribution/Parameter/par_cor_",variable1,"_",variable2,".pdf",sep=""))
	plot(A[,1],type="l",xlab="Months",ylim=c(min,max),ylab=paste("cor_",variable1,"_",variable2,sep=""),col=1,lty=1,lwd=2)
	for(y in 2:number_of_parameters)
	{
		if(colored_lines == 1)
		{
			# Colored lines.
			x<-y
		}else
		{
			# Black lines.
			x<-1
		}

		lines(A[,y],col=x,lty=y,lwd=2)
	}

	# Add legend.
	if(make_legend == 1)
	{
		if(colored_lines == 1)
		{
			# Colors in legend.
			color<-c(1:number_of_parameters)
		}else
		{	
			# Only black in legend
			color<-c(rep(1,number_of_parameters))
		}

		legend(xpd=TRUE,(0.5*length(A[,1])),(max+(max-min)*0.18),parameter_values,lty=c(1:number_of_parameters),col=color,horiz=TRUE,title=experiment_name, x.intersp=0.5,xjust=0.5)
	}

	dev.off()

	
rm(A)
