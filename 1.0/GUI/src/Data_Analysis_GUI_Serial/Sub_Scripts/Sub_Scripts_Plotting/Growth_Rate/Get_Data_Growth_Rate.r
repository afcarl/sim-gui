##################################Auxiliary-Functions#########################################################################
quarterly_data <- function(x) 
{
	return(x[3]);
}

yearly_data <- function(x) 
{
	return(x[12]);
}
################################################################################################################################

# Growth rates of variables for each single run.

# Mean or sum in order to transform monthly data to quarterly/yearly data.
aggregation<- data_growth_rate[j,5]

# Auxiliary vector for naming.
time<-c("x","monthly","quarterly","yearly")


# 2: monthly, 3: quarterly, 4: yearly
for(k in 2:4)
{
	# If 3,4,5 should be calculated.
	if(data_growth_rate[j,k] == "Yes")
	{
		# For each parameter value
		for(p in 1:number_of_parameters)
		{
			# Value of parameter in order to give variables distinguishable names.
			parameter<-parameter_values[p]
	
			# For each run.
			for(r in 1:runs)
			{	
				# Single data is available.
				if(save_single_data == 1)
				{		
					# Get variable data.			
					A<-eval(parse(text=eval(paste(variable,"_",parameter,"_",r,sep=""))))	
				}

				# Single data is not available.
				if(save_single_data == 0 && save_batch_data == 1)
				{
					# Get variable data.			
					A<-eval(parse(text=eval(paste("batch_",variable,"_",parameter,sep=""))))
					A<-A[,r]
				}		

				# Transform A to a time series with monthly data.
				B<-ts(as.ts(as.matrix(A)),frequency=12)

				# If quarterly.
				if(k == 3)
				{
					# Transform monthly time series to quarterly time series.
					if(data_growth_rate[j,5]!= "No")
					{
						# Use mean or sum of three months.
						B<-aggregate(B,nfrequency=4,FUN=aggregation)		
					}else
					{
						# Omit two months to get quarterly data.
						B<-aggregate(B,nfrequency=4,FUN=quarterly_data)
					}
				}

				if(k == 4)
				{
					# Transform monthly time series to yearly time series.
					if(data_growth_rate[j,5] != "No")
					{
						# Use mean or sum of twelve months.
						B<-aggregate(B,nfrequency=1,FUN=aggregation)		
					
					}else
					{
						# Omit eleven months to get yearly data.
						B<-aggregate(B,nfrequency=1,FUN=yearly_data)
					}
				}

				# Create matrix with one column xml_files-1 rows.
				m<-matrix(0,length(B)-1,1)

				# Calculate growth rates.
				
				for(n in 2:(length(B)))
				{
					#print(n)
					if(B[n-1] == 0 || (-1e-5<B[n-1] && B[n-1]<1e-5))
					{
						m[n-1]<-0
					}else
					{
						m[n-1]<-(B[n]-B[n-1])/B[n-1]
					}

				}
				
				# Transform matrix to a vector.
				m<-c(m)
		
				if(save_single_data == 1)
				{	
					# Growth rates are named "variable"_growth_"time".
					eval(call("<-",(paste(variable,"_growth_",time[k],"_",parameter,"_",r,sep="")),m))	
				}

				if(r == 1)
				{
					if(save_batch_data == 1 || save_parameter_data == 1)
					{
						# Create matrix to store grwoth rates for batch analysis.
						temp_mat<-matrix(0,length(m),runs)
						
						# Temp_mean is named batch_"variable"_dist_"parameter_value" and stored.
						#eval(call("<-",(paste("batch_",variable,"_growth_",time[k],"_",parameter,sep="")),temp_mat))						
					}

					if(save_parameter_data == 1 && p == 1)
					{
						# Create matrix to store grwoth rates for parameter analysis.
						par_temp_mat<-matrix(0,length(m),number_of_parameters)
		
						# Temp_mean is named par_"variable"_"parameter_value" and stored.
						#eval(call("<-",(paste("par_",variable,"_growth_",time[k],sep="")),par_temp_mat))	
					}
				}
		
				
				if(save_batch_data == 1 || save_parameter_data == 1)
				{
					
					# Temp_matrix is filled with batch_"variable"_"parameter" data.
					#temp_mat<-eval(parse(text=eval(paste("batch_",variable,"_growth_",time[k],"_",parameter,sep=""))))

					# r-th column is filled m. 
					temp_mat[,r]<-m

					# temp_mat is stored again as batch_"variable"_"parameter".
					#eval(call("<-",(paste("batch_",variable,"_growth_",time[k],"_",parameter,sep="")),temp_mat))
				}
			
				if(r == runs && save_batch_data == 1)
				{
					# Full
					eval(call("<-",(paste("batch_",variable,"_growth_",time[k],"_",parameter,sep="")),temp_mat))

					# Row means of temp mat: means over runs.
					eval(call("<-",(paste("batch_mean_",variable,"_growth_",time[k],"_",parameter,sep="")),
					rowMeans(temp_mat,na.rm=TRUE)))	
				}

				if(r == runs && save_parameter_data == 1)
				{
					
					# Temp_matrix is filled with par_"variable"_ data.
					#par_temp_mat<-eval(parse(text=eval(paste("par_",variable,"_growth_",time[k],sep=""))))

					# p-th column is filled rowMeans of temp_mat. Mean over runs per parameter value.
					par_temp_mat[,p]<-rowMeans(temp_mat,na.rm=TRUE)

					# par_temp_mat is stored again as par_"variable".
					#eval(call("<-",(paste("par_",variable,"_growth_",time[k],sep="")),par_temp_mat))

						
					if(p==number_of_parameters)
					{
						# par_temp_mat is stored as par_"variable".
						eval(call("<-",(paste("par_",variable,"_growth_",time[k],sep="")),par_temp_mat))
						rm(par_temp_mat)
					}
					
				}
			}#end for(r in 1:runs)
		}#end for(p in 1:number_parameters)
	}#end if(data_growth_rate[j,k] == "Yes")
}#end for(k 2:4)
rm(A)
rm(B)
rm(m)
rm(temp_mat)

