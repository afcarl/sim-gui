if(get_var %in% data[,2])
{
	g=match(get_var,data[,2])

	# Get variable name.
	get_var <- data[g,1]

	# Name of the variable in R memory.
	new_name <- data[g,2]
	
	# Get agent type.
	agent <- data[g,3]

	# Evaluates the number of the agent type. 
	agent_number<-eval(parse(text=eval(agent)))

	# Sum or Mean over agents in one iteration.
	method<-data[g,4]

	# Name of the filter variable
	filter_1<-data[g,5]

	# Value of the filter variable
	filter_1_value<-data[g,6]

	# Comparison operator
	operator_1<-data[g,7]

	# Name of the filter variable
	filter_2<-data[g,8]

	# Value of the filter variable
	filter_2_value<-data[g,9]

	# Comparison operator
	operator_2<-data[g,10]	

	# Weight
	weight = data[g,11]

	# Indicator for real data
	#real = data[g,12]


	print(paste("  Get_Sub_Data_Matrix: Process Variable: ",variable,"  Sub Variable: ",get_var,sep=""))
	# For each parameter value. 
	for(p in 1:number_of_parameters)
	{
		# Value of parameter in order to give variables distinguishable names.
		parameter<-parameter_values[p]
	
		# To find database.
		parameter_p<-parameter_values[p]

		#print(paste("Parameter: ",parameter_p,sep=""))

		# For each run.
		for(r in 1:runs)
		{
			# Connection to SQL-database.
			con<-dbConnect(dbDriver("SQLite"),paste(database_location,parameter_p,"/run_",r,"/iters.db",sep=""))
	
			#print(paste("RUN: ",r,sep=""))
			#print(paste("Database location: ",database_location,parameter_p,"/run_",r,"/iters.db", sep=""))
			
		
			# Data in the transition phase is ignored.
			if(delete_transition == 1)
			{
				# Get data for "variable" from table "agent".
				X<-dbGetQuery(con, paste(eval(paste("SELECT ",get_var," from ",agent, 
				" where _ITERATION_NO+0.0>",transition_phase,sep="")))) 

			}else # Data in transition phase is not ignored.
			{
				# Get data for "variable" from table "agent".
				X<-dbGetQuery(con, paste(eval(paste("SELECT ",get_var," from ",agent,sep=""))))	
			}

			# Transform list X in vector Y.
			Y<-as.vector(as.numeric(X[,1])) 

			rm(X)

			# Create matrix to store Y. Rows: Agents and Columns: Number of xml-files.
			Z<-matrix(Y,agent_number,number_xml)

			rm(Y)

			#if(real == "Yes")
			#{
				# Start getting and processing "nominal to real" data.
				#source(paste(root_directory,"Get_Nominal_to_Real_Data.r",sep=""))	
			#}
		

			if(agent_number > 1)
			{

				if(method == "mean" && weight != "No")
				{
					# Start getting and processing weigtht data.
					source(paste(root_directory,"Get_Weight_Data.r",sep=""))
				}

				# If Filter is used.
				if(filter_1 != "No")
				{
					#Set filter information with first filter
					filter<-filter_1
					filter_value<-filter_1_value
					operator<-operator_1

					# Start getting and processing filter data.
					source(paste(root_directory,"Get_Filter_Data.r",sep=""))

					# If second Filter is used.
					if(filter_2 != "No")
					{
						#Set filter information with second filter
						filter<-filter_2
						filter_value<-filter_2_value
						operator<-operator_2

						# Start getting and processing filter data.
						source(paste(root_directory,"Get_Filter_Data.r",sep=""))
					}
				}

				# Calculate weighted original data.
				if(method == "mean" && weight != "No")
				{
					#Calculate Weight matrix
					for(i in 1:length(W[1,]))
					{
						if(sum(W[,i],na.rm=TRUE)>0)
						{
							W[,i]=W[,i]/sum(W[,i],na.rm=TRUE)
						}
					}
				
					# Multiply original data Z with weight W.
					Z<-Z*W
	
					# Sum over the columns of the weighted data is the weighted average
					temp_transformation<-apply(Z,2,sum,na.rm=TRUE)

					rm(W)
				}
				else{
					temp_transformation<-apply(Z,2,method,na.rm=TRUE)
				}
			}
			else{
				temp_transformation<-as.vector(Z)
			}

				
			# Raw data matrix:  Rows: agents   Columns: iterations (Number xml-files)
			eval(call("<-",(paste(new_name,"_matrix_",parameter,"_",r,sep="")),Z))

			rm(Z)
			rm(temp_transformation)

			dbDisconnect(con)
		
		}# close r-loop

	}# Close p-loop

	get_var = new_name
}else
{
	print(paste("Error in Get_Sub_Data_Matrix: ",get_var," is not defined in variables.txt",sep=""))

}







