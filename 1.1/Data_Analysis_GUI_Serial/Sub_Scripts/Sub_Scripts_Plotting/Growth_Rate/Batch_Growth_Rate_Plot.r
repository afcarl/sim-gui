# Plot growth ratesfor batch runs.
print("Enter Growth - Batch Plot()")

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

for(p in 1:number_of_parameters)
{
	# Value of parameter in order to give variables distinguishable names.
	parameter<-parameter_values[p]
	
	for(k in 2:4)
	{

		if(data_growth_rate[j,k] == "Yes")
		{	
			
			
	############### Plot variable for single runs per parameter value in one common graph.
				A<-eval(parse(text=eval(paste("batch_",variable,"_growth_",time[k],"_",parameter,sep=""))))

				# Max and Min.
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
				pdf(paste(plot_directory,"Growth_Rate/Batch/batch_",parameter,
				"/Full/batch_",variable,"_growth_",time[k],".pdf",sep=""))

				plot(A[,1],type="l",xlab=time_plot[k],xlim=c(tmin,tmax),ylim=c(min,max),
				ylab=paste(variable,"_growth_",time[k],sep=""),col=1,lty=1,lwd=2)
				if(runs > 1)
				{
					for(y in 2:runs)
					{
						lines(A[,y],col=1,lty=1,lwd=2)
					}
				}
				#abline(0,0,lty=2,lwd=2)
				#abline(0.00625,0,lty=2,lwd=2)
				dev.off()

				rm(A)


		#MEAN
	############### Plot the mean of variable over single runs per parameter value in one common graph.
				A<-eval(parse(text=eval(paste("batch_mean_",variable,"_growth_",time[k],"_",parameter,sep=""))))

				# Max and Min.
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
				pdf(paste(plot_directory,"Growth_Rate/Batch/batch_",parameter,
				"/Mean/batch_mean_",variable,"_growth_",time[k],".pdf",sep=""))

				plot(A,type="l",xlab=time_plot[k],xlim=c(tmin,tmax),ylim=c(min,max),
				ylab=paste(variable,"_growth_",time[k],sep=""),col=1,lty=1,lwd=2)
				#abline(0,0,lty=2,lwd=2)
				#abline(0.00625,0,lty=2,lwd=2)
				dev.off()
				rm(A)
		}
	}	
}

rm(tmin)
rm(tmax)
rm(lower_bound)
rm(upper_bound)

