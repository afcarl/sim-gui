# Plot for single run.
print("Enter Multiple Time Series - Single Plot()")
print(paste("data_time_series size ",dim(data_time_series),sep=""))

lower_bound<-data_multiple_time_series[j,4]
upper_bound<-data_multiple_time_series[j,5]
tmin<-data_multiple_time_series[j,6]
tmax<-data_multiple_time_series[j,7]


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
		# Create matrix: store the data of the variables. 
		temp_mat<-matrix(0,number_xml,number_variables)
			
		for(n in 0:(number_variables-1))
		{	

			print("BP 1")
			var<-data_multiple_time_series[(j+n),1]

			# Variable names in a character vector for the legend

			if(make_legend==1){
				if(n==0 )
				{
					legend_content<-c(var)
				}else
				{
					legend_content<-c(legend_content,var)
				}
			}
			
			A<-eval(parse(text=eval(paste(var,"_",parameter,"_",r,sep=""))))
			
		
			temp_mat[,(n+1)]<-A
		
			rm(A)

			print("BP 2")
		}

		# Max and Min.
		max<-max(temp_mat,na.rm=TRUE)
		min<-min(temp_mat,na.rm=TRUE)

		# Avoid outlier
		if(upper_bound != "No")
		{
			max<-as.numeric(upper_bound)
		}

		if(lower_bound != "No")
		{
			min<-as.numeric(lower_bound)
		}

		print(max)
		print(min)
	
		# Plot.
		pdf(paste(plot_directory,
		"Time_Series/Single/batch_",parameter,"/run_",r,"/multiple_",data_multiple_time_series[j,8],".pdf",sep=""))
		plot(temp_mat[,1],type="l",xlab="Months",xlim=c(tmin,tmax),ylim=c(min,max),ylab=paste(data_multiple_time_series[j,8]),col=1,lty=1,lwd=2)
		for(y in 2:number_variables)
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

			lines(temp_mat[,y],col=x,lty=y,lwd=2)
		}

		# Add legend.
		if(make_legend == 1)
		{
			if(colored_lines == 1)
			{
				# Colors in legend.
				color<-c(1:number_variables)
			}else
			{	
				# Only black in legend
				color<-c(rep(1,number_variables))
			}

			if(number_variables < 4)
			{
				legend(xpd=TRUE,(0.5*(tmax -tmin)),(max+(max-min)*0.18),legend_content,lty=c(1:number_variables),col=color,horiz=TRUE, x.intersp=0.5,xjust=0.5)
			}else
			{
				legend(xpd=TRUE,(0.5*(tmax -tmin)),(max+(max-min)*0.18),legend_content,lty=c(1:number_variables),col=color,horiz=FALSE,ncol=3, x.intersp=0.2,xjust=0.5)
			}

			rm(legend_content)
		}
		dev.off()

		rm(temp_mat)
		rm(min)
		rm(max)

	}

	print("End MTS")

}

rm(tmin)
rm(tmax)
rm(lower_bound)
rm(upper_bound)
