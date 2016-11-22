number_bins <- as.numeric(data_heat_maps[j,5])

lower_bound<-data_heat_maps[j,6]
upper_bound<-data_heat_maps[j,7]

# If no limits -> heat maps over all iterations
if(data_heat_maps[j,8] == "No")
{
	tmin<-1
}else
{
	tmin<-as.numeric(data_heat_maps[j,8])
}

if(data_heat_maps[j,9] == "No")
{
	tmax<-number_xml
}else
{
	tmax<-as.numeric(data_heat_maps[j,9])
}


for(p in 1:number_of_parameters)
{
	# Value of parameter in order to give variables distinguishable names.
	parameter<-parameter_values[p]
	
	# For each run.
	for(r in 1:runs)
	{	
		# Get variable_matrix data for boxplots over population.			
		A<-eval(parse(text=eval(paste(name,"_matrix_",parameter,"_",r,sep=""))))

		if(r==1)
		{
			temp_mat<-matrix(0,length(A[,1]),(tmax-tmin+1))
			temp_mat<-A[,tmin:tmax]
		}
		else{
			temp_mat<-rbind(temp_mat,matrix(A[,tmin:tmax],length(A[,1]),length(temp_mat[1,])))
		}
		
		rm(A)
	}

	# Min and Max over all batch runs
	min=min(temp_mat,na.rm=TRUE)
	max=max(temp_mat,na.rm=TRUE)

	# Avoid outlier
	if(upper_bound != "No")
	{
		max= min(as.numeric(upper_bound),max)
	}

	if(lower_bound != "No")
	{
		min= max(as.numeric(lower_bound),min)
	}


	# Vector of iterations for the x-axis
	x=c(tmin:tmax)

	#x=c(1:(tmax-tmin+1))

	# Vector with the limits of the bins for the y-axis
	y=rep(0,number_bins)

	# Calculate the limits of the bins: +1 because for b bins you need b+1 limits
	for(b in 1:(number_bins+1))
	{
		y[b]= min +(b-1)*(max-min)/(number_bins)
	}

	# Interval width
	interval<-(max-min)/(number_bins)

	# Matrix: bins and iterations
	heat_mat <- matrix(0,number_bins,length(temp_mat[1,]))

	# For each column
	for(c in 1:length(temp_mat[1,]))
	{
		# For each row
		for(r in 1:length(temp_mat[,1]))
		{
			# if temp_mat[r,c] is a number and is lower than the max and is larger than the min
			if(!is.na(temp_mat[r,c]) && temp_mat[r,c] <= max && temp_mat[r,c] >= min)
			{
				# Which bin
				b <- floor(((temp_mat[r,c])-min)/interval + 1)
	
				# Max into the highest bin
				if(temp_mat[r,c] == max)
				{
					b<-number_bins
				}

				# Count
				heat_mat[b,c]<-heat_mat[b,c]+1
			}	
		}
	}

	median_x = apply(temp_mat,2,FUN=median,na.rm=TRUE)

	rm(temp_mat)
	rm(min)
	rm(max)
	rm(interval)

	if(data_heat_maps[j,10] != "No" && data_heat_maps[j,3] != "No")
	{
		name<-data_heat_maps[j,10]
	}

	pdf(paste(plot_directory,"Heat_Maps/Batch/batch_",parameter,"/1Var/batch_heat_",name,".pdf",sep=""))
	image.plot(x,y,t(heat_mat),zlim=c(1,max(heat_mat)),add=FALSE,ylab=name,xlab="Months") 
	lines(median_x,col=1,lwd=2)
	dev.off()


	rm(heat_mat)

}


