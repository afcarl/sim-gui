# Histogram over population for each parameter.

# Multi dimensional.
if(agent_number >1)
{
	
	for(b in 1: length(histogram_iteration_vector))
	{
		A<-eval(parse(text=eval(paste("par_",variable,"_bins_",histogram_iteration_vector[b],sep=""))))
		B<-eval(parse(text=eval(paste("par_",variable,"_bins_mean_",histogram_iteration_vector[b],sep=""))))

		xmax<-max(A,na.rm=TRUE)
		xmin<-min(A,na.rm=TRUE)

		ymax<-max(B,na.rm=TRUE)
		ymin<-min(B,na.rm=TRUE)

		pdf(paste(plot_directory,"Histogram/Parameter/par_hist_",variable,"_period_",histogram_iteration_vector[b]*frequency/20,".pdf",sep=""))
		plot(A[,1],B[,1],type="l",xlab=paste(variable,"_period_",histogram_iteration_vector[b]*frequency/20,sep=""),xlim=c(xmin,xmax),ylim=c(0,1),ylab="Density",col=1,lty=1,lwd=2)		
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

			lines(A[,y],B[,y],col=x,lty=y,lwd=2)
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

			legend(xpd=TRUE,(xmin+0.5*(xmax-xmin)),(1+0.18),parameter_values,lty=c(1:number_of_parameters),col=color,horiz=TRUE,title=experiment_name, x.intersp=0.5,xjust=0.5)
		}
		dev.off()

		rm(A)
		rm(B)
		rm(xmax)
		rm(ymax)
		rm(xmin)
		rm(ymin)


# Log Distribution plots
####################
		A<-eval(parse(text=eval(paste("par_",variable,"_distribution_X_",histogram_iteration_vector[b],sep=""))))
		B<-eval(parse(text=eval(paste("par_",variable,"_distribution_Y_",histogram_iteration_vector[b],sep=""))))

		xmax<-max(A,na.rm=TRUE)
		xmin<-min(A,na.rm=TRUE)

		ymax<-max(B,na.rm=TRUE)
		ymin<-min(B,na.rm=TRUE)

		pdf(paste(plot_directory,"Histogram/Parameter/par_distribution_",variable,"_period_",histogram_iteration_vector[b]*frequency/20,".pdf",sep=""))
		plot(A[,1],B[,1],type="l",xlab=paste("log(",variable,")_period_",histogram_iteration_vector[b]*frequency/20,sep=""),ylab=paste("log(Rank)",sep=""),xlim=c(xmin,xmax),col=1)		
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

			lines(A[,y],B[,y],col=x,lty=y,lwd=2)
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

			legend(xpd=TRUE,(xmin+0.5*(xmax-xmin)),(ymax+(ymax-ymin)*0.18),parameter_values,lty=c(1:number_of_parameters),col=color,horiz=TRUE,title=experiment_name, x.intersp=0.5,xjust=0.5)
		}
		dev.off()

		rm(A)
		rm(B)
		rm(xmax)
		rm(ymax)
		rm(xmin)
		rm(ymin)
	}

}
