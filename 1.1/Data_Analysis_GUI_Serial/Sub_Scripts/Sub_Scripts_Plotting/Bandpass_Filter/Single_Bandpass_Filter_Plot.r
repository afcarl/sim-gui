# Plot for single run.
print("Enter Bandpass - Single Plot()")
lower_bound<-data_bandpass_filter[j,11]
upper_bound<-data_bandpass_filter[j,12]
tmin<-data_bandpass_filter[j,13]
tmax<-data_bandpass_filter[j,14]

#Set tmin tmax
if(tmin != "No" && tmax != "No")
{
	# Vector of iterations for the x-axis
	tmin<-as.numeric(tmin)
	tmax<-as.numeric(tmax)
}

if(tmin == "No" && tmax != "No")
{
	# Vector of iterations for the x-axis
	tmin<-1
	tmax<-as.numeric(tmax)
}

if(tmin != "No" && tmax == "No")
{
	# Vector of iterations for the x-axis
	end<-number_xml
	tmin<-as.numeric(tmin)
	tmax<-end
}

if(tmin == "No" && tmax == "No")
{
	# Vector of iterations for the x-axis
	end<-number_xml
	tmin<-1
	tmax<-end
}

# How many variables should be plotted in one plot.
number_variables<-as.numeric(data_bandpass_filter[j,10])

for(p in 1:number_of_parameters)
{
	# Value of parameter in order to give variables distinguishable names.
	parameter<-parameter_values[p]
	
	# For each run.
	for(r in 1:runs)
	{
		# Create matrix: store the data of the variables. 
		temp_mat<-matrix(0,floor(number_xml/3),number_variables)
			
		for(n in 1:number_variables)
		{	
			var<-data_bandpass_filter[j+(n-1),1]
	
			# Variable names in a character vector for the legend
			if(n==1 && make_legend==1)
			{
				legend_content<-c(var)
			}else
			{
				legend_content<-c(legend_content,var)
			}
			
			A<-eval(parse(text=eval(paste(var,"_bandpass_",parameter,"_",r,sep=""))))
			
			temp_mat[,n]<-A
		
			# Calculation of correlation. 
			if(number_variables == 2 && data_bandpass_filter[j,3] == "Yes")
			{
				
				# First of the two variables.
				if(n == 1)
				{
					# Number of observations used for moving average to detrend the data.
					nfix<-as.numeric(data_bandpass_filter[j+(n-1),8])
					
					# First/Last "nfix" observations are "Na". Delete them.
					var1<-A[-c(1:nfix)]
					var1<-var1[-c((length(var1)-nfix+1):length(var1))]
				}

				# Second of the two variables.
				if(n == 2)
				{
					# Number of observations used for moving average to detrend the data.
					nfix<-as.numeric(data_bandpass_filter[j+(n-1),8])

					# First/Last "nfix" observations are "Na". Delete them.
					var2<-A[-c(1:nfix)]
					var2<-var2[-c((length(var2)-nfix+1):length(var2))]

					# Create matrix: One row and 14 columns: 1 Standard deviation 2-14 lead/lag for correlation.
					temp_cor_mat<-matrix(0,1,14)
					temp_cor_mat[1,1]<-round(sqrt(var(var2,na.rm=TRUE)*(length(var2)-1)/length(var2)),digits=4)
				

					# Counter. Column of temp_cor_mat.
					l<-1
					#-6 to -1: lag;   0: no lead/lag;   1 to 6:lead
					for(c in -6:6)
					{
						l<-l+1
						
						# If lagging.
						if(c < 0)
						{
							# Delete last "c" observations of first variable.
							aux1<-var1[-c((length(var1)-abs(c)+1):length(var1))]
							# Delete first "c" observations of second variable.
							aux2<-var2[-c(1:(abs(c)))]
						}

						# In no lead/lag.
						if(c == 0)
						{
							aux1<-var1
							aux2<-var2
						}
				
						# If leading.
						if(c > 0)
						{
							# Delete first "c" observations of first variable.
							aux1<-var1[-c(1:(abs(c)))]
							# Delete last "c" observations of second variable.
							aux2<-var2[-c((length(var2)-abs(c)+1):length(var2))]
						}
					
					
						temp_cor_mat[1,l]<-round(cor(aux1,aux2),digits=4)

						
					}
				
					# Formatting matrix temp_cor_mat.
					# Left-aligned.
					temp_cor_mat<-format(temp_cor_mat,justify = c("left"))
					# Delete quotes.
					temp_cor_mat<-noquote(temp_cor_mat)
					# Delete indices.
					#rownames(temp_cor_mat)<-rep("",nrow(temp_cor_mat));temp_cor_mat
					# Transpose in order to use write-command..
					temp_cor_mat<-t(temp_cor_mat)
				}
				rm(A)				
			}
		}

		# Max and Min.
		max<-max(temp_mat,na.rm=TRUE)
		min<-min(temp_mat,na.rm=TRUE)

		# Avoid outlier
		if(upper_bound != "No")
		{
			max<-as.numeric(upper_bound)
		}

		if(lower_bound != "No")
		{
			min<-as.numeric(lower_bound)
		}
	
	
		if(number_variables == 1)
		{
			if(data_bandpass_filter[j,4] == "No")
			{
				Y_name<-var
			}else
			{
				Y_name<-paste("log(",var,")",sep="")
			}
		
			
			# Plot.
			pdf(paste(plot_directory,
			"Bandpass/Single/batch_",parameter,"/run_",r,"/bandpass_",var,".pdf",sep=""))
			plot(temp_mat[,1],type="l",xlab="Quarters",xlim=c(tmin,tmax),ylim=c(min,max),ylab=paste(Y_name),col=1,lty=1,lwd=4)
			abline(0,0,lty=2,lwd=2)
			dev.off()
			

		}else
		{
			if(data_bandpass_filter[j,4] == "No")
			{
				Y_name<-data_bandpass_filter[j,15]
			}else
			{
				Y_name<-paste("log of ",data_bandpass_filter[j,15],sep="")
			}

			# Plot.
			pdf(paste(plot_directory,
			"Bandpass/Single/batch_",parameter,"/run_",r,"/bandpass_",data_bandpass_filter[j,15],".pdf",sep=""))
			plot(temp_mat[,1],type="l",xlab="Quarters",xlim=c(tmin,tmax),ylim=c(min,max),ylab=paste(Y_name),col=1,lty=1,lwd=4)
			for(y in 2:number_variables)
			{
				if(colored_lines == 1)
				{
					# Colored lines.
					x<-y
				}else
				{
					# Black lines.
					x<-1
				}

				lines(temp_mat[,y],col=x,lty=y,lwd=4)
				abline(0,0,lty=2,lwd=2)
			}

			# Add legend.
			if(make_legend == 1)
			{
				if(colored_lines == 1)
				{
					# Colors in legend.
					color<-c(1:number_variables)
				}else
				{	
					# Only black in legend
					color<-c(rep(1,number_variables))
				}

				legend(xpd=TRUE,(0.5*(tmax - tmin)),(max+(max-min)*0.18),legend_content,lty=c(1:number_variables),col=color,horiz=TRUE, x.intersp=0.5,xjust=0.5)

				rm(legend_content)
			}
			dev.off()
		}

		rm(temp_mat)
		

		
		

		# Create txt-files in in run_r folder
		if(j==1)
		{
			# Write in"Correlation_Result_parameter_run.txt".
			write(c("  Std "," -6"," -5"," -4"," -3"," -2"," -1"," 0"," 1"," 2"," 3"," 4"," 5"," 6"),
			file=paste(plot_directory,"Bandpass/Single/batch_",parameter,"/run_",r,
			"/bandpass_correlation_",parameter,"_",r,".txt",sep=""),ncolumns=14,append=FALSE,sep="\t")

			# Line.
			write(c("-------------------------------------------------------------------------------------------------------------------"),
			file=paste(plot_directory,"Bandpass/Single/batch_",parameter,"/run_",r,
			"/bandpass_correlation_",parameter,"_",r,".txt",sep=""),ncolumns=14,append=TRUE,sep="")
		}
		
		if(data_bandpass_filter[j,3]=="Yes" && n==2)
		{
			# Write m.
			write(temp_cor_mat,file=paste(plot_directory,"Bandpass/Single/batch_",parameter,"/run_",r,
			"/bandpass_correlation_",parameter,"_",r,".txt",sep=""),ncolumns=14,append=TRUE,sep="\t")

			rm(temp_cor_mat)
		}

	}
}

rm(tmin)
rm(tmax)
rm(lower_bound)
rm(upper_bound)
