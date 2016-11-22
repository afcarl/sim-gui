# Data in the transition phase is ignored.
if(delete_transition == 1)
{
	# Get data for "variable" from table "agent".
	W<-dbGetQuery(con, paste(eval(paste("SELECT ",weight," from ",agent, 
	" where _ITERATION_NO+0.0>",transition_phase,sep="")))) 

}else # Data in transition phase is not ignored.
{
	# Get data for "variable" from table "agent".
	W<-dbGetQuery(con, paste(eval(paste("SELECT ",weight," from ",agent,sep=""))))	
}

# Transform list W in vector W.
W<-as.vector(as.numeric(W[,1])) 

# Create matrix to store W. Rows: Agents and Columns: Number of xml-files.
W<-matrix(W,agent_number,number_xml)


