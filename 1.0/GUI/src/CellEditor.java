import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellEditor;


public class CellEditor extends AbstractCellEditor implements TableCellEditor {
	
	 JComponent component = new JTextField();
	 
	int editedRow, editedCol;
	
	

	    // This method is called when a cell value is edited by the user.
	    public Component getTableCellEditorComponent(JTable table, Object value,
	            boolean isSelected, int rowIndex, int vColIndex) {
	        // 'value' is value contained in the cell located at (rowIndex, vColIndex
	    	
	    	editedRow = rowIndex;
	    	editedCol = vColIndex;
	    	
	        if (isSelected) {
	            // cell (and perhaps other cells) are selected
	        }

	        //Configure the component with the specified value 
	        
	        try {
	          
	        	
	        	value =Integer.parseInt(value.toString());
	            ((JTextField)component).setText(value.toString());
	        }
	        catch(NumberFormatException nFE) {
	        	((JTextField)component).setText((String)value);
	        }
	      
	  
	      
	        return component;
	      
	        
	    }
	 
	    

	    // This method is called when editing is completed.
	    // It must return the new value to be stored in the cell.
	    public Object getCellEditorValue() {
	    	
	    	System.out.println("((JTextField)component).getText()"+((JTextField)component).getText());
	    	
	        return ((JTextField)component).getText();
	    }
	  
	    public int getRow(){
	    	
	    	return editedRow;
	    }
	    
	    
	    public int getColumn(){
	    	
	    	return editedCol;
	    }

}




