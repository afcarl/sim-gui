##################################Auxiliary-Functions#########################################################################
quarterly_data <- function(x) 
{
	return(x[3]);
}

################################################################################################################################

# bandpass filtered data of variables for each single run.
print(paste("   Get Bandpass Data: ",var,sep=""))
# Mean or sum in order to transform monthly data to quarterly.
aggregation<- data_bandpass_filter[j,5]


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
			# Get var data.			
			A<-eval(parse(text=eval(paste(var,"_",parameter,"_",r,sep=""))))
		}
		
		# Single data is not available.
		if(save_single_data == 0 && save_batch_data == 1)
		{	
			# Get var data.			
			A<-eval(parse(text=eval(paste("batch_",var,"_",parameter,sep=""))))
			A<-A[,r]
		}
	
		# Transform A to a time series with monthly data.
		B<-ts(as.ts(as.matrix(A)),frequency=12)

		rm(A)		
		
		# Transform monthly time series to quarterly time series.
		if(data_bandpass_filter[j,5] != "No")
		{

			# Use mean or sum of three months.
			B<-aggregate(B,nfrequency=4,FUN=aggregation)
		
		}else
		{
			# Omit two months to get quarterly data.
			B<-aggregate(B,nfrequency=4,FUN=quarterly_data)
		}
		
		# a: minimum length of cycle.
		# b: maximum length of cycle.		
		a<-as.numeric(data_bandpass_filter[j,6])
		b<-as.numeric(data_bandpass_filter[j,7])

		lead_lag<-as.numeric(data_bandpass_filter[j,8])

		if(data_bandpass_filter[j,4] == "No")
		{
			# Apply bandpass filter.
			m<-bkfilter(B,pl=a,pu=b,nfix=lead_lag)
		}else
		{
			# Apply bandpass filter.
			m<-bkfilter(log(B),pl=a,pu=b,nfix=lead_lag)
		}
		
		# Isolate the cycle component.
		m<-as.vector(m$cycle)
	
				
		if(save_single_data == 1)
		{	
			# Cylce component is named "var"_bandpass.
			eval(call("<-",(paste(var,"_bandpass_",parameter,"_",r,sep="")),m))
		}

		if(r == 1)
		{
			if(save_batch_data == 1 || save_parameter_data == 1)
			{
				# Create matrix to store c<ycle components for batch analysis.
				temp_mat<-matrix(0,length(m),runs)
			}
		

			if(save_parameter_data == 1 && p == 1)
			{
				# Create matrix to store grwoth rates for parameter analysis.
				par_temp_mat<-matrix(0,length(m),number_of_parameters)
		
				# Par_temp_mat is named par_"var"_bandpass and stored.
				#eval(call("<-",(paste("par_",var,"_bandpass",sep="")),par_temp_mat))
			}			
		}
		
				
		if(save_batch_data == 1 || save_parameter_data == 1)
		{
			# r-th column is filled m. 
			temp_mat[,r]<-m
		}
		
		rm(m)
	
		if(r == runs && save_batch_data == 1)
		{
			# Temp_mat is named batch_"var"_bandpass and stored.
			eval(call("<-",(paste("batch_",var,"_bandpass_",parameter,sep="")),temp_mat))
			# Row means of temp mat: means over runs.
			eval(call("<-",(paste("batch_mean_",var,"_bandpass_",parameter,sep="")),
			rowMeans(temp_mat,na.rm=TRUE)))	
		}

		if(r == runs && save_parameter_data == 1)
		{
			# p-th column is filled rowMeans of temp_mat. Mean over runs per parameter value.
			par_temp_mat[,p]<-rowMeans(temp_mat,na.rm=TRUE)


		}

		if(r == runs && save_parameter_data == 1 && p==number_of_parameters)
		{
			# par_temp_mat is stored again as par_"var".
			eval(call("<-",(paste("par_",var,"_bandpass",sep="")),par_temp_mat))
		}		
	}
}

rm(temp_mat)
rm(par_temp_mat)
