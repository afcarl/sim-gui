# Fill temp_mat with chosen columns/iterations of variable_matrix.
	
print(paste("   Get_Correlation_Distribution_Data: ",variable1," and ",variable2,sep="" ))

for(p in 1:number_of_parameters)
{
	# Value of parameter in order to give variables distinguishable names.
	parameter<-parameter_values[p]
	
	# For each run.
	for(r in 1:runs)
	{	
		A<-eval(parse(text=eval(paste(variable1,"_matrix_",parameter,"_",r,sep=""))))
		B<-eval(parse(text=eval(paste(variable2,"_matrix_",parameter,"_",r,sep=""))))

		cor<-matrix(0,1,length(A[1,]))
		for(y in 1: length(A[1,]))
		{
			cor[,y]<-cor(A[,y],B[,y],use="pairwise.complete.obs",method="spearman")
		}

		cor<-as.vector(cor)

		rm(B)

		eval(call("<-",(paste("cor_",variable1,"_",variable2,"_",parameter,"_",r,sep="")),cor))

		if(save_batch_data == 1)
		{
			if(r==1)
			{
				temp_mat<-matrix(0,length(A[1,]),runs)
			}

			temp_mat[,r]<-cor

			rm(cor)

			if(r==runs)
			{
				eval(call("<-",(paste("batch_cor_",variable1,"_",variable2,"_",parameter,sep="")),temp_mat))
				eval(call("<-",(paste("batch_mean_cor_",variable1,"_",variable2,"_",parameter,sep="")),rowMeans(temp_mat,na.rm=TRUE)))
			}
		}

		if(save_parameter_data == 1)
		{
			if(p==1 && r==1)
			{
				par_temp_mat<-matrix(0,length(A[1,]),number_of_parameters)
			}
			
			if(r==runs)
			{
				par_temp_mat[,p]<-rowMeans(temp_mat,na.rm=TRUE)

				rm(temp_mat)

				if(p==number_of_parameters)
				{
					eval(call("<-",(paste("par_cor_",variable1,"_",variable2,sep="")),par_temp_mat))
					rm(par_temp_mat)	
				}
			}

			
		}
		rm(A)
		
	}

}












