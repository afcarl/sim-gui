
quarterly_data <- function(x) 
{
	return(x[3]);
}

yearly_data <- function(x) 
{
	return(x[12]);
}

################################################################################################################################

lags<-as.numeric(data_cross_correlation_function[j,7])

aggregation1<-data_cross_correlation_function[j,2]

aggregation2<-data_cross_correlation_function[j,5]

for(p in 1:number_of_parameters)
{
	# Value of parameter in order to give variables distinguishable names.
	parameter<-parameter_values[p]
	
	# For each run.
	for(r in 1:runs)
	{		
		# Get variable data.	
		A<-eval(parse(text=eval(paste(variable1,"_",parameter,"_",r,sep=""))))
		
		# Get variable data.			
		B<-eval(parse(text=eval(paste(variable2,"_",parameter,"_",r,sep=""))))
		
		# Use mean or sum of three months.
		

		# Transform A to a time series with monthly data.
		A<-ts(as.ts(as.matrix(A)),frequency=12)

		if(data_cross_correlation_function[j,2] != "No")
		{
			A<-aggregate(A,nfrequency=4,FUN=aggregation1)
		}
		else{
			A<-aggregate(A,nfrequency=4,FUN=quarterly_data)
		}

		A<-c(A)		
		
		# Transform A to a time series with monthly data.
		B<-ts(as.ts(as.matrix(B)),frequency=12)

		if(data_cross_correlation_function[j,5] != "No")
		{
			B<-aggregate(B,nfrequency=4,FUN=aggregation2)
		}
		else{
			B<-aggregate(B,nfrequency=4,FUN=quarterly_data)
		}				
		B<-c(B)		
		
	
		#If multiple lags
		if(lags>0)
		{
			# Create matrix with one column, length(ccf)=2*lags rows.
			m<-matrix(0,2*lags,1)

			#Correlation plot
			#ccf(A,B, lag.max = lags, type = c("correlation"), plot = TRUE,main=paste(name1,name2),ylab="Cross-Correlation Function")
			a<-ccf(A,B, lag.max = lags, type = c("correlation"), plot = FALSE)


			#Fill m with ccf data
			m<-as.matrix(a$acf)

			# Transform matrix to a vector.
			m<-c(m)


			if(r == 1)
			{
				# Create matrix to store CCFs for batch analysis.
				temp_mat<-matrix(0,length(m),runs)
			}

			if(save_batch_data == 1 || save_parameter_data == 1)
			{
				#Store ccf in matrix
				# r-th column is filled m. 
				temp_mat[,r]<-m
			}
		
			if(r == runs && save_batch_data == 1)
			{
				# Row means of temp mat: means over runs.
				M<-rowMeans(temp_mat,na.rm=TRUE)
			}
		}
	}#runs

	#If multiple lags
	if(lags>0)
	{

		#Plot ccf_matrix
		# Max and Min.
		max<-max(M,na.rm=TRUE)*1.10
		min<-min(M,na.rm=TRUE)*0.90

		pdf(paste(plot_directory,"Cross_Correlation_Function/Batch/batch_",parameter,"/batch_mean_ccf_",variable1,"_",variable2,".pdf",sep=""))
		# Plot.			
		plot(M,type="h",axes=F,xlab="lag",ylim=c(min,max),ylab="Cross-Correlation-Function",main=paste(variable1," vs. ",variable2,sep=""),col=1,lty=1,lwd=2)
		#x-axis
		axis(1,labels=c(-lags:lags),at=c(1:(2*lags+1)))
		#y-axis
		axis(2)
		abline(h=0)
		dev.off()

	}

}
rm(A)
rm(B)
rm(m)
rm(M)
rm(temp_mat)


