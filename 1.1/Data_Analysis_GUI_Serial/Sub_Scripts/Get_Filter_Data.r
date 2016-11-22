# Data in the transition phase is ignored.
if(delete_transition == 1)
{
	# Get data for "variable" from table "agent".
	filter_var<-dbGetQuery(con, paste(eval(paste("SELECT ",filter," from ",agent," where _ITERATION_NO+0.0>",transition_phase,sep="")))) 
}else # Data in transition phase is not ignored.
{
	# Get data for "variable" from table "agent".
	filter_var<-dbGetQuery(con, paste(eval(paste("SELECT ",filter," from ",agent,sep=""))))	
}

# Transform list X in vector Y.
filter_vec<-as.vector(as.numeric(filter_var[,1])) 

rm(filter_var)

# Create matrix to store Y. Rows: Agents and Columns: Number of xml-files.
filter_mat<-matrix(filter_vec,agent_number,number_xml)

rm(filter_vec)

if(operator == "=")
{
	#filter_mat[filter_mat!=as.numeric(filter_value)]<-1
	#filter_mat<-matrix(as.numeric(filter_mat),agent_number,number_xml)

	# Transform filter value in "Na".
	#filter_mat[filter_mat==as.numeric(filter_value)]<-"Na"
	#filter_mat<-matrix(as.numeric(filter_mat),agent_number,number_xml)


#17.2.2012 Try new style for filter

	# Transfirm filter values into Na
	filter_mat[filter_mat==as.numeric(filter_value)]<-"Na"
	filter_mat<-matrix(as.numeric(filter_mat),agent_number,number_xml)

	#Transform matrix values != Na into 1's
	filter_mat[filter_mat!="Na"]<-1

}

if(operator == ">")
{


	#filter_mat[filter_mat<=as.numeric(filter_value)]<-1
	#filter_mat<-matrix(as.numeric(filter_mat),agent_number,number_xml)

	# Transform values larger than filter value in "Na".
	#filter_mat[filter_mat!=1]<-"Na"
	#filter_mat<-matrix(as.numeric(filter_mat),agent_number,number_xml)

#17.2.2012 Try new style for filter

	# Transfirm filter values into Na
	filter_mat[filter_mat>as.numeric(filter_value)]<-"Na"
	filter_mat<-matrix(as.numeric(filter_mat),agent_number,number_xml)

	#Transform matrix values != Na into 1's
	filter_mat[filter_mat!="Na"]<-1
}


if(operator == "<")
{
	#filter_mat[filter_mat>=as.numeric(filter_value)]<-1
	#filter_mat<-matrix(as.numeric(filter_mat),agent_number,number_xml)

	# Transform values smaller than filter value in "Na"..
	#filter_mat[filter_mat!=1]<-"Na"
	#filter_mat<-matrix(as.numeric(filter_mat),agent_number,number_xml)

#17.2.2012 Try new style for filter


	# Transfirm filter values into Na
	filter_mat[filter_mat<as.numeric(filter_value)]<-"Na"
	filter_mat<-matrix(as.numeric(filter_mat),agent_number,number_xml)

	#Transform matrix values != Na into 1's
	filter_mat[filter_mat!="Na"]<-1
	

}




if(operator == "!=")
{
	#filter_mat[filter_mat>=as.numeric(filter_value)]<-1
	#filter_mat<-matrix(as.numeric(filter_mat),agent_number,number_xml)

	# Transform values smaller than filter value in "Na"..
	#filter_mat[filter_mat!=1]<-"Na"
	#filter_mat<-matrix(as.numeric(filter_mat),agent_number,number_xml)

#17.2.2012 Try new style for filter


	# Transfirm filter values into Na
	filter_mat[filter_mat!=as.numeric(filter_value)]<-"Na"
	filter_mat<-matrix(as.numeric(filter_mat),agent_number,number_xml)

	#Transform matrix values != Na into 1's
	filter_mat[filter_mat!="Na"]<-1
	

}




# Multiply original Z with filter:-> filtered Z (for example bankrupt firms are filtered out.)

Z<-Z*filter_mat

# Multiply original Weight with filter:-> filtered Weight (for example bankrupt firms are filtered out.)
if(method == "mean" && weight != "No")
{
	W<-W*filter_mat
}

rm(filter_mat)



