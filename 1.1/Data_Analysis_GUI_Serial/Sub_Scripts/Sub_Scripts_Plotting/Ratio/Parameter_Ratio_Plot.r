# Ratios of variables for each single run.
print("Enter Ratio - Parameter Plot()")
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
	

# Get variable data.			
A<-eval(parse(text=eval(paste("par_",variable1,sep=""))))

# Get variable data.			
B<-eval(parse(text=eval(paste("par_",variable2,sep=""))))
	

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
pdf(paste(plot_directory,"Time_Series/Parameter/par_",data_ratio[j,9],".pdf",sep=""))
plot(temp_ratio[,1],type="l",xlab="Months",xlim=c(tmin,tmax),ylim=c(min,max),ylab=paste(data_ratio[j,9]),col=1,lty=1,lwd=2)
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

	lines(temp_ratio[,y],col=x,lty=y,lwd=2)
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
rm(B)
rm(min)
rm(max)
rm(temp_ratio)

rm(tmin)
rm(tmax)
rm(lower_bound)
rm(upper_bound)


