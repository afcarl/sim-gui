# Histogram over population for each parameter.

# Multi dimensional.
if(agent_number>1)
{
	# Fill temp_mat with chosen columns/iterations of variable_matrix.
	for(b in 1: length(histogram_iteration_vector))
	{
	
		for(p in 1:number_of_parameters)
		{
			# Value of parameter in order to give variables distinguishable names.
			parameter<-parameter_values[p]
	
		
			# For each run.
			for(r in 1:runs)
			{	
				# Get variable_matrix data for histograms over population.			
				A<-eval(parse(text=eval(paste(variable,"_matrix_",parameter,"_",r,sep=""))))

				# Get distribution over agents in period
				x<-A[,histogram_iteration_vector[b]]
				# Sort x: decreasing. From large to small.
				x <- sort(x,decreasing=TRUE)
				# Rank sorted observations. Largest value has largest rank.
				y <- rank(x,ties.method="first")
				# Sort ranks decreasing. From large to small. If two or more values are equal ranks may not be decreasing. Sort them in order to be sure that ranks are decreasing.
				y <- sort(y,decreasing=TRUE)
				# Sort x: increasing. Smallest value has largest rank.
				x <- sort(x)
				
				# Create matrix to store x and y values for each run in each batch.
				if(r == 1)
				{
					temp_mat<-matrix(as.numeric("Na"),length(A[,1]),runs)
					temp_mat2<-matrix(as.numeric("Na"),length(A[,1]),runs)

					temp<-matrix(as.numeric("Na"),length(A[,1]),1)
			
					#temp_mat<-matrix(0,length(A[,1]),runs)
					#temp_mat2<-matrix(0,length(A[,1]),runs)

				}

				temp_mat[(length(temp_mat[,1])-length(x)+1):length(temp_mat[,1]),r]<-x#log(x)
				temp_mat2[(length(temp_mat2[,1])-length(x)+1):length(temp_mat2[,1]),r]<-y#log(y)
				
				rm(x)
				rm(y)

				if(r == runs)
				{

					#temp_mat[temp_mat==0]<-"NA"
					#temp_mat<-matrix(as.numeric(temp_mat),length(A[,1]),runs)

					#temp_mat2[temp_mat2==0]<-"NA"
					#temp_mat2<-matrix(as.numeric(temp_mat2),length(A[,1]),runs)

					temp_mat[temp_mat==-Inf]<-"NA"
					temp_mat<-matrix(as.numeric(temp_mat),length(A[,1]),runs)

					temp_mat2[temp_mat2==-Inf]<-"NA"
					temp_mat2<-matrix(as.numeric(temp_mat2),length(A[,1]),runs)
			
					rm(A)					

					if(save_batch_data==1)
					{
						# Because the number of Na's (bankrupt firms) can differ between runs the mean over sorted firms of each single run might not be sorted anymore. Here the mean is sorted.
						temp_sort<-sort(rowMeans(temp_mat,na.rm=TRUE))

						temp[(length(temp[,1])-length(temp_sort)+1):length(temp[,1]),1]<-temp_sort
						

						eval(call("<-",(paste("batch_",variable,"_distribution_X_",parameter,"_",histogram_iteration_vector[b],sep="")),temp))
						eval(call("<-",(paste("batch_",variable,"_distribution_Y_",parameter,"_",histogram_iteration_vector[b],sep="")),rowMeans(temp_mat2,na.rm=TRUE)))
					}
	
					if(p == 1 && save_parameter_data == 1)
					{
						par_temp_mat<-matrix(0,length(temp_mat[,1]),number_of_parameters)
						par_temp_mat2<-matrix(0,length(temp_mat[,1]),number_of_parameters)
					}
			
					if(save_parameter_data == 1)
					{
						temp_sort<-sort(rowMeans(temp_mat,na.rm=TRUE))

						temp[(length(temp[,1])-length(temp_sort)+1):length(temp[,1]),1]<-temp_sort

						par_temp_mat[,p]<-temp
						par_temp_mat2[,p]<-rowMeans(temp_mat2,na.rm=TRUE)
		
						rm(temp_mat)
						rm(temp_mat2)	
					}
			
					if(p == number_of_parameters && save_parameter_data == 1)
					{
						eval(call("<-",(paste("par_",variable,"_distribution_X_",histogram_iteration_vector[b],sep="")),par_temp_mat))
						eval(call("<-",(paste("par_",variable,"_distribution_Y_",histogram_iteration_vector[b],sep="")),par_temp_mat2))

						rm(par_temp_mat)
						rm(par_temp_mat2)
					}
				}
			}
		}
	}
}
