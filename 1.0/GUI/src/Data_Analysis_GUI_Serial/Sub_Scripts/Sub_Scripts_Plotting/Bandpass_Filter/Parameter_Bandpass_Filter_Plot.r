# Multipe plot for parameter.

if(as.numeric(data_bandpass_filter[j,10]) == 1)
{	
	var<-data_bandpass_filter[j,1]
			

	A<-eval(parse(text=eval(paste("par_",var,"_bandpass",sep=""))))

	# Max and Min.
	max<-max(A,na.rm=TRUE)
	min<-min(A,na.rm=TRUE)

	if(data_bandpass_filter[j,4] == "No")
	{
		Y_name<-var
	}else
	{
		Y_name<-paste("log(",var,")",sep="")
	}

	# Plot.
	pdf(paste(plot_directory,
	"Bandpass/Parameter/par_bandpass_",var,".pdf",sep=""))
	plot(A[,1],type="l",xlab="Quarters",ylim=c(min,max),ylab=paste(Y_name),col=1,lty=1,lwd=2)
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
		abline(0,0,lty=2,lwd=2)
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
rm(max)
rm(min)	
}
		
			
	

		

		
		

		


