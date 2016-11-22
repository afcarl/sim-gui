
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.table.AbstractTableModel;


public class VariableListTableModel extends AbstractTableModel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7823581644519761186L;
	JCheckBox checkBox = new JCheckBox();
	String [] columnName;
	
	
	
	
	
	int numFilters;
	boolean partitioningSelected , filterSelected;
	
	ArrayList<PlottingSettings.VariableInstance> tempVariableInstances = new ArrayList<PlottingSettings.VariableInstance>();
	
	
	PlottingSettings.Agent variableInstances;
	
	
	VariableListTableModel(String[] colName, PlottingSettings.Agent agentVariableInstances){

		variableInstances = agentVariableInstances;
		
		for(int i=0; i<variableInstances.listOfVariableInstances.size();i++ ){
			
			if(variableInstances.listOfVariableInstances.get(i).isSpecialVariableInstance ){
				
				tempVariableInstances.add(variableInstances.listOfVariableInstances.get(i));
			
			}
			
		}

		columnName = colName;
	
		
	}
	
	
	
	public int getRowCount()
   {
	
	return variableInstances.listOfVariableInstances.size();
	
 }
	
	
   public int getColumnCount()
   {
   return 7;
 }
   public Object getValueAt( int row, int col)
   {
	   
	
	   
	  
		 //  System.out.println("getValueAt timeSeriesSelection.get(i).isntancesOfvariable.size()"+timeSeriesSelection.get(i).isntancesOfvariable.size());
		
				   if(col==0)
					   return variableInstances.listOfVariableInstances.get(row).getName();
				   else if(col==1)
					   return variableInstances.listOfVariableInstances.get(row).instanceName;
				   else if(col==2)
					   return variableInstances.listOfVariableInstances.get(row).instanceMethod;
				   else if(col==3)
					   return variableInstances.listOfVariableInstances.get(row).whichFilter1;
				   else if(col==4)
					   return variableInstances.listOfVariableInstances.get(row).whichFilter2;
				   else if(col==5)
					   return variableInstances.listOfVariableInstances.get(row).whichPartitioning;
				   else if(col==6)
					   return variableInstances.listOfVariableInstances.get(row).growthRate;
				   else
					   return null;
			   
			
   }
	   
	   
	   
	   
	   
	   
   
   
   public String getColumnName(int column) {  
       return columnName[column]; 
   }
   
   
   public void changeValueAt(String value, int row, int col) {  
       
	   System.out.println("changeValueAt");
	  

	   if(col==1)
		   variableInstances.listOfVariableInstances.get(row).instanceName=value;
	   if(col==2)
		   variableInstances.listOfVariableInstances.get(row).instanceMethod=value;
	   if(col==3)
		   variableInstances.listOfVariableInstances.get(row).whichFilter1=value;
	   if(col==4)
		   variableInstances.listOfVariableInstances.get(row).whichFilter2=value;
	   if(col==5)
		   variableInstances.listOfVariableInstances.get(row).whichPartitioning=value;
	   if(col==6)
		   variableInstances.listOfVariableInstances.get(row).growthRate=value;

	 
       this.fireTableDataChanged();  
         
         
   }  
   
   
   
 public void RemoveRowAt(int row) {  
       
	   
	 variableInstances.listOfVariableInstances.remove(row);
	   	   
	   this.fireTableDataChanged();  
	   
        

}
   
   
   public void updatetable() {  
       
       this.fireTableDataChanged();    
         
   }  
   
   public boolean isCellEditable(int row, int column){ 
      
	   if(column==0){
   		return false;
   	}else {
   		
   		return true;
   	}
   }
   
   
	   
	   
   
   
   
    
}
