number_bins <- as.numeric(data_heat_maps_2V[j,13])

lower_bound1<-data_heat_maps_2V[j,5]
upper_bound1<-data_heat_maps_2V[j,6]

lower_bound2<-data_heat_maps_2V[j,11]
upper_bound2<-data_heat_maps_2V[j,12]

# If no limits -> heat maps over all iterations
if(data_heat_maps_2V[j,14] == "No")
{
	tmin<-1
}else
{
	tmin<-as.numeric(data_heat_maps_2V[j,14])
}

if(data_heat_maps_2V[j,15] == "No")
{
	tmax<-number_xml
}else
{
	tmax<-as.numeric(data_heat_maps_2V[j,15])
}

for(p in 1:number_of_parameters)
{
	# Value of parameter in order to give variables distinguishable names.
	parameter<-parameter_values[p]
	
	# For each run.
	for(r in 1:runs)
	{	
		# Get variable_matrix data for boxplots over population.			
		A<-eval(parse(text=eval(paste(name1,"_matrix_",parameter,"_",r,sep=""))))

		B<-eval(parse(text=eval(paste(name2,"_matrix_",parameter,"_",r,sep=""))))

		if(r==1)
		{
			temp_mat1<-matrix(0,length(A[,1]),(tmax-tmin+1))
			temp_mat1<-A[,tmin:tmax]

			temp_mat2<-matrix(0,length(B[,1]),(tmax-tmin+1))
			temp_mat2<-B[,tmin:tmax]
		}
		else{
			temp_mat1<-rbind(temp_mat1,matrix(A[,tmin:tmax],length(A[,1]),length(temp_mat1[1,])))
			temp_mat2<-rbind(temp_mat2,matrix(B[,tmin:tmax],length(B[,1]),length(temp_mat2[1,])))
		}
		
		rm(A)
	}

	# Min and Max over all batch runs
	min=min(temp_mat1,na.rm=TRUE)
	max=max(temp_mat1,na.rm=TRUE)

	min2=min(temp_mat2,na.rm=TRUE)
	max2=max(temp_mat2,na.rm=TRUE)


	# Avoid outlier
	if(upper_bound1 != "No")
	{
		max= min(as.numeric(upper_bound1),max)
	}

	if(lower_bound1 != "No")
	{
		min= max(as.numeric(lower_bound1),min)
	}

	# Avoid outlier
	if(upper_bound2 != "No")
	{
		max2= min(as.numeric(upper_bound2),max2)
	}
	

	if(lower_bound2 != "No")
	{
		min2= max(as.numeric(lower_bound2),min2)
	}



	# Vector of iterations for the x-axis
	x=c(tmin:tmax)

	#x=c(1:(tmax-tmin+1))

	# Vector with the limits of the bins for the y-axis
	y=rep(0,number_bins)

	# Calculate the limits of the bins: +1 because for b bins one you need b+1 limits
	for(b in 1:(number_bins+1))
	{
		y[b]= min +(b-1)*(max-min)/(number_bins)
	}

	# Interval width
	interval<-(max-min)/(number_bins)

	# Matrix: bins and iterations
	heat_mat <- matrix(0,number_bins,length(temp_mat1[1,]))
	count_mat <- matrix(0,number_bins,length(temp_mat1[1,]))

	# For each column
	for(c in 1:length(temp_mat1[1,]))
	{
		# For each row
		for(r in 1:length(temp_mat1[,1]))
		{
			# if temp_mat1[r,c] is a number and is lower than the max and is larger than the min
			# if temp_mat2[r,c] is a number and is lower than the max2 and is larger than the min2
			if(!is.na(temp_mat1[r,c]) && temp_mat1[r,c] <= max && temp_mat1[r,c] >= min  && !is.na(temp_mat2[r,c]) && temp_mat2[r,c] <= max2 && temp_mat2[r,c] >= min2)
			{
				# Which bin
				b <- floor(((temp_mat1[r,c])-min)/interval + 1)
	
				# Max into the highest bin
				if(temp_mat1[r,c] == max)
				{
					b<-number_bins
				}

				# Count
				heat_mat[b,c]<-heat_mat[b,c]+temp_mat2[r,c]
				count_mat[b,c]<-count_mat[b,c]+1
			}	
		}
	}

	heat_mat = heat_mat/count_mat

	heat_mat[heat_mat==Inf]<-"Na"
	heat_mat[heat_mat==-Inf]<-"Na"
	heat_mat[heat_mat==NaN]<-"Na"

	heat_mat<-matrix(as.numeric(heat_mat),length(count_mat[,1]),length(count_mat[1,]))

	rm(temp_mat1)
	rm(temp_mat2)
	rm(min)
	rm(max)
	rm(interval)
	rm(count_mat)
	rm(min2)
	rm(max2)

	if(data_heat_maps_2V[j,16] != "No")
	{
		name<-data_heat_maps_2V[j,16]
	}

	pdf(paste(plot_directory,"Heat_Maps/Batch/batch_",parameter,"/2Var/batch_heat_2V_",name,".pdf",sep=""))
	#image.plot(x,y,t(heat_mat),zlim=c(1,max(heat_mat)),add=FALSE,ylab=name,xlab="Months") 
image.plot( x,y,t(heat_mat),zlim=c(min(heat_mat,na.rm=TRUE),max(heat_mat,na.rm=TRUE)),legend.width=2, legend.shrink=1, horizontal=FALSE,ylab=name,xlab="Months") 
	
	dev.off()

	rm(heat_mat)

}


