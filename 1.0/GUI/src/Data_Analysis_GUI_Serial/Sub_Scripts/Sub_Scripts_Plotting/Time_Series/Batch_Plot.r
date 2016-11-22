# Plot for batch runs.
print("Enter Batch Plot()")
lower_bound<-data_time_series[j,2]
upper_bound<-data_time_series[j,3]
tmin<-data_time_series[j,4]
tmax<-data_time_series[j,5]

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
	
#FULL/DIST	
############### Plot variable for single runs per parameter value in one common graph.
	A<-eval(parse(text=eval(paste("batch_",variable,"_",parameter,sep=""))))


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
	pdf(paste(plot_directory,"Time_Series/Batch/batch_",parameter,"/Full/batch_",variable,".pdf",sep=""))
	plot(A[,1],type="l",xlab="Months",xlim=c(tmin,tmax),ylim=c(min,max),ylab=paste(variable),col=1,lty=1,lwd=2)
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
	A<-eval(parse(text=eval(paste("batch_mean_",variable,"_",parameter,sep=""))))

	# Max and Min.
	max<-max(A,na.rm=TRUE)
	min<-min(A,na.rm=TRUE)

	# Avoid outlier
	if(upper_bound != "No"){
		max<-as.numeric(upper_bound)
	}

	if(lower_bound != "No")
	{
		min<-as.numeric(lower_bound)
	}
	
	# Plot.
	pdf(paste(plot_directory,"Time_Series/Batch/batch_",parameter,"/Mean/batch_mean_",variable,".pdf",sep=""))
	plot(A,type="l",xlab="Months",xlim=c(tmin,tmax),ylim=c(min,max),ylab=paste(variable),col=1,lty=1,lwd=2)
	dev.off()

	rm(A)
	rm(min)
	rm(max)
	
}

rm(tmin)
rm(tmax)
rm(lower_bound)
rm(upper_bound)
