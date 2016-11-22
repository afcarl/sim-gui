import java.util.ArrayList;


public class ShadowModelXML {
	
	
	public static ArrayList<xagent> agents = new ArrayList<xagent>();
	
	
	ShadowModelXML(){
		
		
	}
	

	public class xagent {
		
		String name;
		ArrayList<variable> memory;
		
		
		xagent(String na){
			
			name = na;
			memory = new ArrayList<variable>();
		}
	
	}

	public class variable{
		
		
		String name;
		String type;
		
		variable(String na, String ty){
			
			name = na;
			type = ty;
		}

	}
	
	
	void setFilterAndWeights(){
		
		for(int i=0; i< AgentSettings.agents.size();i++){
			
			
			for(int j=0; j <PlottingSettings.listOfAgentsVariableInstances.size();j++ ){
				
				if(AgentSettings.agents.get(i).agentName.equals(PlottingSettings.listOfAgentsVariableInstances.get(j).agentName)){
					
					
						
						for(int k=0; k < AgentSettings.agents.get(i).variableList.size();k++){
							
							AgentSettings.agents.get(i).variableList.get(k).isSelectedFilter = false;
							
							for(int l=0; l< PlottingSettings.listOfAgentsVariableInstances.get(j).filter.size();l++){
							
								if(AgentSettings.agents.get(i).variableList.get(k).name.equals(PlottingSettings.listOfAgentsVariableInstances.get(j).filter.get(l).variableName)){
								
								AgentSettings.agents.get(i).variableList.get(k).isSelectedFilter = true;
								
								break;
							}
							
							
							
						}
						
						
						
					}
						
						
						
						for(int k=0; k < AgentSettings.agents.get(i).variableList.size();k++){
							
							AgentSettings.agents.get(i).variableList.get(k).isSelectedWeighted = false;
							
							for(int l=0; l< PlottingSettings.listOfAgentsVariableInstances.get(j).weighting.size();l++){
							
								if(AgentSettings.agents.get(i).variableList.get(k).name.equals(PlottingSettings.listOfAgentsVariableInstances.get(j).weighting.get(l).weightingVariable)){
								
								AgentSettings.agents.get(i).variableList.get(k).isSelectedWeighted = true;
								
								break;
							}
							
							
							
						}
						
						
						
					}
					
					
				}
				
				
			}
			
			
		}
		
		
		
	}
	
	
	

}
