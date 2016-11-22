# Scatter plots of variables for each parameter value.

lags<-eval(parse(text = eval(data_scatter[j,5])))

for(p in 1:number_of_parameters)
{
	# Value of parameter in order to give variables distinguishable names.
	parameter<-parameter_values[p]
	
	# Get variable data.			
	A<-eval(parse(text=eval(paste("batch_mean_",variable1,"_",parameter,sep=""))))
			
	# Get variable data.			
	B<-eval(parse(text=eval(paste("batch_mean_",variable2,"_",parameter,sep=""))))
		

		if(lags == 0)
		{
			# Plot.
			pdf(paste(plot_directory,"Scatter/Batch/batch_",parameter,
			"/batch_scatter_",variable1,"_",variable2,"_lags_",lags,".pdf",sep=""))
			
			plot(A,B,xlab=paste(variable1,sep=""),
			ylab=paste(variable2,sep=""),col=1,lty=2,lwd=2)
			dev.off()

			rm(A)
			rm(B)

		}

		if(lags >0)
		{
			
			# Delete last "lags" observations of variable1.
			A<-A[-c((length(A)-lags+1):length(A))]
			# Delete first "lags" observations of variable2.
			B<-B[-c(1:lags)]

			# Plot.
			pdf(paste(plot_directory,"Scatter/Batch/batch_",parameter,
			"/batch_scatter_",variable1,"_",variable2,"_lags_",lags,".pdf",sep=""))
			
			plot(A,B,xlab=paste(variable1,"(t)",sep=""),
			ylab=paste(variable2,"(t+",lags,")",sep=""),col=1,lty=2,lwd=2)
			dev.off()

			rm(A)
			rm(B)
		}


		if(lags <0)
		{
			lags = lags*(-1)			

			# Delete last "lags" observations of variable1.
			B<-B[-c((length(B)-lags+1):length(B))]
			# Delete first "lags" observations of variable2.
			A<-A[-c(1:lags)]

			# Plot.
			pdf(paste(plot_directory,"Scatter/Batch/batch_",parameter,
			"/batch_scatter_",variable1,"_",variable2,"_lags_",lags,".pdf",sep=""))
			
			plot(A,B,xlab=paste(variable1,"(t)",sep=""),
			ylab=paste(variable2,"(t-",lags,")",sep=""),col=1,lty=2,lwd=2)
			dev.off()

			rm(A)
			rm(B)
		}
	
}
