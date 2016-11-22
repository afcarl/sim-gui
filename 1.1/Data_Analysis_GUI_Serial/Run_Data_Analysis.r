delete.all<-function()
rm(list=ls(pos=.GlobalEnv),pos=.GlobalEnv)
delete.all()

graphics.off()

# Start the configure script. 
#source("/home/sgemkow/Desktop/Data_Analysis_GUI_Serial/Configure.r")
source(paste(system('echo $PATH_R_SCRIPTS', intern=TRUE),"/Configure.r",sep=""))

# Create folders for plots.
#source(paste(root_directory,"Folder_creation/Setup_Folders.r",sep=""))

# Run settings.
source(paste(root_directory,"Settings.r",sep=""))

# Start getting and processing data.
source(paste(root_directory,"Get_Data.r",sep=""))

# Start plotting.
source(paste(root_directory,"Plot_Head.r",sep=""))



