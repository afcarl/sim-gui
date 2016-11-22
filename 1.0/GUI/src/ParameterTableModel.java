import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;



public class ParameterTableModel extends AbstractTableModel {
	
	int noOfRows, noOfColumns;
	
	final ArrayList<SimulationSettings.Value>  data;  
	final String columnName;
	
	boolean setEditable ;
	
	ParameterTableModel(String colName, ArrayList<SimulationSettings.Value> values, boolean editable){
		
		setEditable = editable;
		this.data = values; 
		columnName = colName;
		
	}
	
	
	public int getRowCount()
    {
    return data.size();
  }
    public int getColumnCount()
    {
    return 1;
  }
    public Object getValueAt( int row, int col)
    {
    
      return data.get(row).value ;
    }
    
    public String getColumnName() {  
        return columnName; 
    }
    
    public void setValueAt(String value, int row, int col) {  
    
    data.add(row, (new SimulationSettings()).new Value(value)) ;
    this.fireTableDataChanged();  
      
      
}  
    
    public void updatetable() {  
        
        this.fireTableDataChanged();    
          
    }  
    
    public boolean isCellEditable(int row, int column){ 
       
    
    		return false;
    	
    }
    
    public void addRow(String newValue) {  
        
        data.add((new SimulationSettings()).new Value(newValue));  
        this.fireTableDataChanged();  

}
    
    public void delRow(int row) {  
        
        data.remove(row);         
        this.fireTableDataChanged();  
          
        }  
    
}
