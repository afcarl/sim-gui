for(p in 1:number_of_parameters)
{
	# Value of parameter in order to give variables distinguishable names.
	parameter<-parameter_values[p]
	
	# For each run.
	for(r in 1:runs)
	{				
		A<-colSums(eval(parse(text=eval(paste(variable,"_matrix_",parameter,"_",r,sep="")))),na.rm=TRUE)
			
		B<-eval(parse(text=eval(paste(variable,"_matrix_",parameter,"_",r,sep=""))))
			
		C<-matrix(0,length(B[,1]),length(B[1,]))
			
		for(a in 1:length(B[,1]))
		{
			C[a,]<-B[a,]/A
		}
	
		D<-C*C

		E<-colSums(D,na.rm=TRUE)

		eval(call("<-",(paste("herf_",variable,"_",parameter,"_",r,sep="")),E))

		if(save_batch_data == 1)
		{
			if(r==1)
			{
				temp_mat<-matrix(0,length(B[1,]),runs)
			}

			temp_mat[,r]<-E

			if(r==runs)
			{
				eval(call("<-",(paste("batch_herf_",variable,"_",parameter,sep="")),temp_mat))
				eval(call("<-",(paste("batch_mean_herf_",variable,"_",parameter,sep="")),rowMeans(temp_mat,na.rm=TRUE)))
			}
		}

		if(save_parameter_data == 1)
		{
			if(p==1 && r==1)
			{
				par_temp_mat<-matrix(0,length(B[1,]),number_of_parameters)
			}
			
			if(r==runs)
			{
				par_temp_mat[,p]<-rowMeans(temp_mat,na.rm=TRUE)

				if(p==number_of_parameters)
				{
					eval(call("<-",(paste("par_herf_",variable,sep="")),par_temp_mat))	
				}
			}

			
		}

		
	}
}

