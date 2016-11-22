# Plot for single run.
print("Enter Single Plot()")
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


print("NDD")

for(p in 1:number_of_parameters)
{
	# Value of parameter in order to give variables distinguishable names.
	parameter<-parameter_values[p]
	
	# For each run.
	for(r in 1:runs)
	{	
		# Plot mean/sum of variable over population.
		A<-eval(parse(text=eval(paste(variable,"_",parameter,"_",r,sep=""))))


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


		x_axis = tmin + seq(0,length(A)-1,1)*(tmax-tmin)/(length(A)-1)
		

		pdf(paste(plot_directory,"Time_Series/Single/batch_",parameter,"/run_",r,"/",variable,".pdf",sep=""))
		plot(x_axis,A,type="l",xlab="Time",xlim=c(tmin,tmax),ylim=c(min,max),ylab=paste(variable),col=1,lty=1,lwd=2)
		dev.off()


		print(A)
		print(tmin)
		print(tmax)
		write.csv(A ,(paste(plot_directory,"Time_Series/Single/batch_",parameter,"/run_",r,"/",variable,".csv",sep="")))

		

		rm(A)
		rm(min)
		rm(max)
	}
}

rm(tmin)
rm(tmax)
rm(lower_bound)
rm(upper_bound)















