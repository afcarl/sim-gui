for(p in 1:number_of_parameters)
{
	# Value of parameter in order to give variables distinguishable names.
	parameter<-parameter_values[p]
	
	# For each run.
	for(r in 1:runs)
	{	
		# Get variable_matrix data for boxplots over population.			
		A<-eval(parse(text=eval(paste(variable,"_matrix_",parameter,"_",r,sep=""))))

		if(r==1)
		{
			temp_mat<-matrix(0,length(A[,1]),length(A[1,]))
			temp_mat<-A
		}
		else{
			temp_mat<-rbind(temp_mat,matrix(A,length(A[,1]),length(A[1,])))
		}
		
		rm(A)
	}

	temp_mat<-apply(temp_mat,2,quantile,na.rm=TRUE)

	# Min and Max over all batch runs
	min=min(temp_mat,na.rm=TRUE)
	max=max(temp_mat,na.rm=TRUE)

	# Quantile in einer Graphik ausgeben.
	pdf(paste(plot_directory,"Quantiles/Batch/batch_",parameter,"/batch_",variable,"_quant.pdf",sep=""))
	plot(temp_mat[1,],type="l",xlab="Months",ylim=c(min,max),ylab=paste(variable,"_quantiles",sep=""),col=1,lty=1,lwd=1)
	for(y in 2:5)
	{
		if(y==2 || y==4)
		type<-2

		if(y==3)
		type<-3

		if(y==5)
		type<-1
		
		lines(temp_mat[y,],col=1,lty=1,lwd=type)
	}
	dev.off()

	rm(temp_mat)
}

	
