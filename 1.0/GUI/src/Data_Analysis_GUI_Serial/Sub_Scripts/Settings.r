save_single_data<-single_analysis
save_batch_data<-batch_analysis
save_parameter_data<-parameter_analysis

if(number_of_parameters == 1)
{
	parameter_analysis <- 0
	save_parameter_data <- 0
}

if(parameter_analysis == 0)
{
	save_parameter_data<-0
}


if(parameter_analysis == 0 && batch_analysis == 0)
{
	save_parameter_data<-0
	save_batch_data<-0
}




