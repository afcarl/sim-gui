import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.table.AbstractTableModel;


public class AgentTableModel extends AbstractTableModel{
	
	private ArrayList<Agent>  agentList;
	
	JCheckBox checkBox = new JCheckBox();
	
	int noOfRows, noOfColumns;
	
	final String[] columnName;
	
	
	AgentTableModel(String[] colName, ArrayList<Agent>  list ){
		
		columnName = colName;
		
		agentList = list;
		
	}
	
 public Class<?> getColumnClass(int col) {  
     if (col == 1) {  
        return Boolean.class;  
     }  
     return getValueAt(0, col).getClass();  
  }  

	public int getRowCount()
    {
    return agentList.size()+1;
  }
    public int getColumnCount()
    {
    return 4;
  }
    public Object getValueAt( int row, int col)
    {
    
    	if(row<agentList.size())
    	{
		      if(col==0) {
		    	  return agentList.get(row).agentName ;
		      }
		      else if (col==1){
		    	  
		    	  
		    	  
		    	  return agentList.get(row).dataStorageSettings.isSelected; 
		    	  
		      
		      
		      }
		      else if(col==2){
		    	  if(agentList.get(row).dataStorageSettings.isSelected)
		    		  return agentList.get(row).dataStorageSettings.period;
		    	  else
		    		 return "";
		      }
		      else{ 
		    	  if(agentList.get(row).dataStorageSettings.isSelected)
		    		  return agentList.get(row).dataStorageSettings.phase;
		    	  else
		    		  return "";
		      }
    	}else{
    		
    		if(col==0) {
		    	  return "snapshot" ;
		      }
		      else if (col==1){
		    	  
		    	  return SimulationSettings.SNAPSHOTS; 
		    	  }
		      else if(col==2){
		    	  if(SimulationSettings.SNAPSHOTS)
		    		  return SimulationSettings.SNAPSHOTS_FREQUENCY;
		    	  else
		    		  return "";
		      }
		      else{ 
		    	  if(SimulationSettings.SNAPSHOTS)
		    		  return 1;
		    	  else
		    		  return "";
		      }
    		
    		
    	}
      
    }
    
    public String getColumnName(int column) {  
        return columnName[column]; 
    }
    
    
    public void changeValueAt(String value, int row, int col) {  
        
    	System.out.println("row "+row);
    	
    	if(row<agentList.size())
    	{
	    	if(col==0)
	    	{
	    		agentList.get(row).agentName = value;
	    	}else if(col==1)
	    	{
	    		if(value.equals("true"))
	    			{
	    				agentList.get(row).dataStorageSettings.isSelected = true;	
	    			}else{
	    				agentList.get(row).dataStorageSettings.isSelected = false;
	    			}
	    	} else if(col==2)
	    		agentList.get(row).dataStorageSettings.period = value;
	        else 
	        	agentList.get(row).dataStorageSettings.phase = value;
    	}else{
    		
    		if(col==0) {
		    	 
		      }
		      else if (col==1){
			    if(value.equals("true"))
			    {
			    	SimulationSettings.SNAPSHOTS= true;
			    }else
			    {
			    	SimulationSettings.SNAPSHOTS= false;
			    }
		    	 
		      }
		      else if(col==2){
		    	  try{
		    		  SimulationSettings.SNAPSHOTS_FREQUENCY = Integer.parseInt(value);
		    	  }catch(Exception e){
		    		 
		    	  }
		    	 
		      }
    	}
    	this.fireTableDataChanged();  
          
          
    }  
    
    
    public void updatetable() {  
        
        this.fireTableDataChanged();    
          
    }  
    
    public boolean isCellEditable(int row, int column){ 
       
    	if(row<agentList.size()){
	    	if(column==0){
	    		return false;
	    	}else if(column==1){
	    		
	    		return true;
	    	}
	    	else{
	    		if(agentList.get(row).dataStorageSettings.isSelected)
		    	{
		    		return true;
		    	}else
		    	{
		    		return false;
		    	}
	    	}
    	}else{
    		if(column==0 || column ==3)
    			return false;
    		else
    			return true;
	    		
	    	}
    }
    
    public void addRow(String newValue) {  
        
        this.fireTableDataChanged();  

}
    
    public void delRow(int row) {  
        
    	agentList.remove(row);         
        this.fireTableDataChanged();  
          
        }  
    
}


