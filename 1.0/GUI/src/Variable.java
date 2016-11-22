
	 
class Variable implements Cloneable {
    

	
	/****************************************/
	
	public Variable clone()  {
        try {
			return (Variable) super.clone();
		} catch (CloneNotSupportedException e) {
			
			e.printStackTrace();
			return null;
		}
		
        
    }
		
		String type;
		String name; 
		String description;
		boolean isSelectedForPlotting;
		boolean isSelectedForHistograms =false;
		boolean isSelectedForBoxplots =false;
		boolean isSelectedForHeatmaps =false;
		boolean isSelectedForCorrelation = false;
		boolean isSelectedForHeatmaps2V = false;
		
		Variable(String varName, String varType, String varDescription, boolean isSelected){
			
			name = varName;
			type = varType; 
			description = varDescription;
			isSelectedForPlotting = isSelected;
	
		}	
		
		
		
	

	
	
	
}
