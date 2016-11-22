
print(paste("  Get_Sub_Data_Matrix_Division: Nominator: ",variable1,"  Denominator: ",variable2," Name:",name,sep=""))
for(p in 1:number_of_parameters)
{
	# Value of parameter in order to give variables distinguishable names.
	parameter<-parameter_values[p]
	
	# For each run.
	for(r in 1:runs)
	{	
		# Get variable_matrix data for boxplots over population.			
		A<-eval(parse(text=eval(paste(variable1,"_matrix_",parameter,"_",r,sep=""))))

		# Get variable_matrix data for boxplots over population.			
		B<-eval(parse(text=eval(paste(variable2,"_matrix_",parameter,"_",r,sep=""))))

		C<-A/B

		C[C==Inf]<-"Na"
		C[C==-Inf]<-"Na"
		C[C==NaN]<-"Na"

		C<-matrix(as.numeric(C),length(A[,1]),length(A[1,]))

		eval(call("<-",(paste(name,"_matrix_",parameter,"_",r,sep="")),C))
	
		rm(A)
		rm(B)
		rm(C)
	}
}
