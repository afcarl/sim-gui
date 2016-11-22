library("RSQLite")
library("mFilter")
library("fields")

########################################Input-Section: Path Locations ###############################################################
save_snapshots<-0
# Experiment name = name of Parameter
experiment_name<-"Batch"
# Path to the root directory of the databases.
database_location<-paste("D:\projects\abm\Bohdan_GUI/its/Batch/",sep="")
# Path to the root directory plots.
plot_directory<-"D:\projects\abm\Bohdan_GUI/Plots/"
dir.create(plot_directory, showWarnings = FALSE, mode = "0777")
# Path to the root directory of scripts.
#root_directory<-"/home/sgemkow/Desktop/Data_Analysis_GUI_Serial/Sub_Scripts/"
root_directory<-paste(system('echo $PATH_R_SCRIPTS', intern=TRUE),"Sub_Scripts/", sep="")
# Path to the root directory of input files data_*.txt.
data_file_directory<-paste(system('echo $PATH_R_SCRIPTS', intern=TRUE),"Data_Files/", sep="")
########################################Input-Section: Data Analysis Options ###############################################################
# Execute single run analysis?
single_analysis<-1
# Execute batch run analysis?
batch_analysis<-1
# Execute parameter analysis?
parameter_analysis<-1
# Add legend to plot.
make_legend<-0
# Lines in plot have different colors.
colored_lines<-0
########################################Input-Section: Simulation Settings ###############################################################
# Number of runs per parameter value.
runs<-10
# Values of parameter.
parameter_values<-c("")
print("Parameter values: ")
print(parameter_values)

# Number of parameter values.
number_of_parameters<-length(parameter_values)
# Number of iterations.
iterations =5000
frequency =20
# Number of iterations in transition phase.
transition_phase = 0
# Delete transition phase. 1 = Yes or 0 = No
delete_transition = 0
# Number of xml-files.
if(delete_transition == 1)
{
number_xml = (iterations - transition_phase)/frequency
if (save_snapshots) number_xml = ceiling(number_xml)	#correct for fractional number_xml
}else
{
number_xml = iterations/frequency
if (save_snapshots) number_xml = ceiling(number_xml)
}
print(paste("Number xml files: ", number_xml))

########################################Input-Section: Agents###############################################################


# Number of Agent types.
Firm = 80
Household = 1600
Mall = 1
IGFirm = 1
Eurostat = 1
Bank = 20
Government = 1
CentralBank = 1
ClearingHouse = 1
########################################Input-Section: Settings for Analysis #########################################################
# Create time series graphs? 1 = Yes or 0 = No
time_series<-0
	# Create time series of correlation of two variables? 1 = Yes or 0 = No
correlation_distribution<-0
	# Plot ratios.
ratio<-0
	# Create boxplots? 1 = Yes or 0 = No
boxplot<-0
boxplot_iteration_vector<-c( 5000)
print("Boxplots_iteration_vector: ")
print(as.numeric(boxplot_iteration_vector))
# Choose number of last observations. Boxplots for parameter comparison are based on the average of the last observations in each run.
last_observations<-20# Create histograms? 1 = Yes or 0 = No
histogram<-0
# Choose the iterations for histograms.
histogram_iteration_vector<-c( 5000)
# Choose number of bins for batch run.
bins<-10
print("Histogram_iteration_vector: ")
print(as.numeric(histogram_iteration_vector))
# Create growth rate plots? 1 = Yes or 0 = No
growth_rate<-0
# Create scatter plots? 1 = Yes or 0 = No
scatter<-0
# Calculate correlation? 1 = Yes or 0 = No
correlation = 0
# Plot multiple time series.
multiple_time_series<-0
# Create bandpass filtered plots. 1 = Yes or 0 = No
bandpass_filter<-0
# Create 0,25,50,75,100 quantiles time series  1 = Yes or 0 = No
quantiles = 0
# Calculate Herfindahl Index? 1 = Yes or 0 = No
herfindahl = 0
# Variables
cross_correlation_function<-0
#Create heat maps
heat_maps<-0
# Create heat maps with two variables
heat_maps_2V<-0
