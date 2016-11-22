# Ratios of variables for each single run.
print("Enter Ratio - Single Plot()")
lower_bound<-data_ratio[j,5]
upper_bound<-data_ratio[j,6]
tmin<-data_ratio[j,7]
tmax<-data_ratio[j,8]

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
	
	# For each run.
	for(r in 1:runs)
	{	
		# Get variable data.			
		A<-eval(parse(text=eval(paste(variable1,"_",parameter,"_",r,sep=""))))
		A<-as.vector(A)
		# Get variable data.			
		B<-eval(parse(text=eval(paste(variable2,"_",parameter,"_",r,sep=""))))
		B<-as.vector(B)

		# Calculate ratio.
		temp_ratio<-A/B

		# Max and Min.
		max<-max(temp_ratio,na.rm=TRUE)
		min<-min(temp_ratio,na.rm=TRUE)

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
		pdf(paste(plot_directory,"Time_Series/Single/batch_",parameter,"/run_",r,"/",data_ratio[j,9],".pdf",sep=""))
		plot(temp_ratio,type="l",xlab="Months",xlim=c(tmin,tmax),ylim=c(min,max),ylab=paste(data_ratio[j,9]),col=1,lty=1,lwd=2)
		dev.off()

		rm(A)
		rm(B)
		rm(temp_ratio)
	}
}

rm(tmin)
rm(tmax)
rm(lower_bound)
rm(upper_bound)

