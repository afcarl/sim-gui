# Plot for parameter runs.

print("Enter Growth - Parameter Plot()")

# Auxiliary vector for naming.
time<-c("x","monthly","quarterly","yearly")

time_plot<-c("x","Months","Quarters","Years")

# Set bounds
lower_bound<-data_growth_rate[j,6]
upper_bound<-data_growth_rate[j,7]
tmin<-data_growth_rate[j,8]
tmax<-data_growth_rate[j,9]

#Set tmin tmax
if(tmin != "No" && tmax != "No")
{
	# Vector of iterations for the x-axis
	tmin<-as.numeric(tmin)
	tmax<-as.numeric(tmax)
}

if(tmin == "No" && tmax != "No")
{
	# Vector of iterations for the x-axis
	tmin<-1
	tmax<-as.numeric(tmax)
}

if(tmin != "No" && tmax == "No")
{
	# Vector of iterations for the x-axis
	end<-number_xml
	tmin<-as.numeric(tmin)
	tmax<-end
}

if(tmin == "No" && tmax == "No")
{
	# Vector of iterations for the x-axis
	end<-number_xml
	tmin<-1
	tmax<-end
}

# Main
for(k in 2:4)
{
	if(data_growth_rate[j,k] == "Yes")
	{
	
	
	############### Plot mean of variable over runs for each parameter value in one common graph.
			A<-eval(parse(text=eval(paste("par_",variable,"_growth_",time[k],sep=""))))
	
			# Max and Min
			max<-max(A,na.rm=TRUE)
			min<-min(A,na.rm=TRUE)

			# Avoid outlier
			if(upper_bound != "No")
			{
				max<-as.numeric(upper_bound)
			}

			if(lower_bound != "No")
			{
				min<-as.numeric(lower_bound)
			}

			# Plot.
			pdf(paste(plot_directory,"Growth_Rate/Parameter/par_",variable,"_growth_",time[k],".pdf",sep=""))
			plot(A[,1],type="l",xlab=time_plot[k],xlim=c(tmin,tmax),ylim=c(min,max),
			ylab=paste(variable,"_growth_",time[k],sep=""),col=1,lty=1,lwd=2)
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
				#abline(0,0,lty=2,lwd=2)
				#abline(0.00625,0,lty=2,lwd=2)
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

				legend(xpd=TRUE,(0.5*(tmax - tmin)),(max+(max-min)*0.18),parameter_values,lty=c(1:number_of_parameters),col=color,horiz=TRUE,title=experiment_name, x.intersp=0.5,xjust=0.5)
			}
			dev.off()

			rm(A)
	
	}
}
rm(tmin)
rm(tmax)
rm(lower_bound)
rm(upper_bound)

