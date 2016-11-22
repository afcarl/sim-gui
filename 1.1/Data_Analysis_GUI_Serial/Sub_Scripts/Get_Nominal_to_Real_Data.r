# Data in the transition phase is ignored.
if(delete_transition == 1)
{
	# Get data for "deflator" from table IGFirm.
	deflator<-dbGetQuery(con, paste(eval(paste("SELECT price_index from Eurostat where _ITERATION_NO+0.0>",transition_phase,sep="")))) 

}else # Data in transition phase is not ignored.
{
	# Get data for "deflator" from table IGFirm.
	deflator<-dbGetQuery(con, paste(eval(paste("SELECT price_index from Eurostat",sep=""))))	
}



# Transform list deflator in vector deflator.
deflator<-as.vector(as.numeric(deflator[,1])) 


# Transform nominal data in Z to real data.
for(z in 1:length(Z[,1]))
{
	Z[z,]<-Z[z,]/deflator
}


#new_name<-paste(new_name,"_real",sep="")


