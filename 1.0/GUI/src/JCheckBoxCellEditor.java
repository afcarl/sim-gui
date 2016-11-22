import java.awt.Component;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellEditor;


public class JCheckBoxCellEditor extends DefaultCellEditor implements TableCellEditor{
	
	JCheckBox cBox;
	
	int editedRow, editedCol;
	
	JCheckBoxCellEditor (JCheckBox checkBox){
	super (checkBox);
	cBox = checkBox;
	}
	
	
	// This method is called when a cell value is edited by the user.
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int rowIndex, int vColIndex) {
        // 'value' is value contained in the cell located at (rowIndex, vColIndex
    	
    	editedRow = rowIndex;
    	editedCol = vColIndex;
    	
    	System.out.println("NDD");
    	
        if (isSelected) {
            // cell (and perhaps other cells) are selected
        }

        // Configure the component with the specified value 
        if(value.equals(true))
        {
        ((JCheckBox)cBox).setSelected(true);
        }else
        {
        	((JCheckBox)cBox).setSelected(false);
        }
        return cBox;
      
        
    }
    
    
    // This method is called when editing is completed.
    // It must return the new value to be stored in the cell.
    public Object getCellEditorValue() {
    	
    	if(((JCheckBox)cBox).isSelected()){
    		return true;
    		
    	}else
    	{
    		return false;
    	}
    }
  
	
	public int getRow(){
		
		return editedRow;
		
	}
	
	
public int getColumn(){
		
		return editedCol;
		
	}
	
	
}
	

