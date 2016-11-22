import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;


/*This class sets up the table where we can set the data storage options*/
class DrawStoreOptionTable extends JTable{
	
	AgentTableModel tableModel;
	
	private TableColumn  col_2, col_3, col_4;	
	
	DrawStoreOptionTable(AgentTableModel tableMod, String[] colHeaders){
		
		super(tableMod);
		tableModel = tableMod;

	    
	try{
	
for(int i=0; i< 4; i++ ){
		
		
	System.out.println(colHeaders[i]);
	
		this.getColumnModel().getColumn(i).setHeaderValue(colHeaders[i]);
		System.out.println(colHeaders[i]);
	}
	
	/*Set Column No 2: JCheckbox for setting whether the agent should be recorded*/
	 col_2 = this.getColumnModel().getColumn(1);  
	 
	 final JCheckBox check = new JCheckBox();
	 
	 col_2.setCellEditor(new JCheckBoxCellEditor(check));
	 col_2.getCellEditor().getClass();
	 
	 col_2.getCellEditor().addCellEditorListener(new EditorListener(){
		 
		 
		 public void editingStopped(ChangeEvent e) {
			 
			 if( col_2.getCellEditor().getCellEditorValue().equals(true))
			 {
				 /*Set the check box*/
				 tableModel.changeValueAt("true",((JCheckBoxCellEditor) col_2.getCellEditor()).getRow(),1 );
				 
				 if(((JCheckBoxCellEditor) col_2.getCellEditor()).getRow()<AgentSettings.agents.size()){
				 
					 AgentSettings.agents.get(((JCheckBoxCellEditor) col_2.getCellEditor()).getRow()).dataStorageSettings.setSelected(true);
					 
				 }else{
					 SimulationSettings.SNAPSHOTS = true;
				 }
				 
				 tableModel.updatetable();
		
				 
				 
			 }else{
				 /*Set the check box*/
				 tableModel.changeValueAt("false",((JCheckBoxCellEditor) col_2.getCellEditor()).getRow(), 1);
				 if(((JCheckBoxCellEditor) col_2.getCellEditor()).getRow()<AgentSettings.agents.size()){
					 
					 AgentSettings.agents.get(((JCheckBoxCellEditor) col_2.getCellEditor()).getRow()).dataStorageSettings.setSelected(false);
					
				 }else{
					 
					 SimulationSettings.SNAPSHOTS = false;
				 }
				 tableModel.updatetable();
				
			 }
			 
		    }

		 
	 });
	 
	 
	 col_2.setCellRenderer(new DefaultTableCellRenderer() {
		 public Component getTableCellRendererComponent(JTable table,
		 Object value, boolean isSelected, boolean hasFocus, int row, int
		 column) {
		 check.setSelected(((Boolean)value).booleanValue()) ;
		 return check;
		 }
		 
		 
		 
		 });
	 
	 /*Here we set the column no 3:*/
	 
	 col_3 = this.getColumnModel().getColumn(2);
	 col_3.setCellEditor(new CellEditor());
	 
	 col_3.getCellEditor().addCellEditorListener(new EditorListener(){
    		
    		public void editingStopped(ChangeEvent e) {
    	        
    			System.out.println("A cell has been edited.");
    			
    			try{
					System.out.println("Out: "+col_3.getCellEditor().getCellEditorValue());
    				/*Set the table cell*/
					Integer.parseInt(col_3.getCellEditor().getCellEditorValue().toString());
					tableModel.changeValueAt(col_3.getCellEditor().getCellEditorValue().toString(), ((CellEditor) col_3.getCellEditor()).getRow(), 2);
					tableModel.updatetable();
					
					/*Update the memory variable*/
					if(((CellEditor) col_3.getCellEditor()).getRow()<AgentSettings.agents.size())
						AgentSettings.agents.get(((CellEditor) col_3.getCellEditor()).getRow()).dataStorageSettings.period = col_3.getCellEditor().getCellEditorValue().toString();
					else
						SimulationSettings.SNAPSHOTS_FREQUENCY = Integer.parseInt(col_3.getCellEditor().getCellEditorValue().toString());
				}catch(Exception ex)
				{
					JOptionPane.showMessageDialog(null,"Parameter must be an integer!");
					
				}
    	        
    		}
 	
    	});
	 
	 
	 
	 
/*Here we set the column no 3:*/
	 
	 col_4 = this.getColumnModel().getColumn(3);
	 col_4.setCellEditor(new CellEditor());
	 
	 col_4.getCellEditor().addCellEditorListener(new EditorListener(){
    		
    		public void editingStopped(ChangeEvent e) {
    	        
    			System.out.println("A cell has been edited.");
    			
    			try{
					System.out.println("Out: "+col_4.getCellEditor().getCellEditorValue());
					/*Set the table cell*/
					Integer.parseInt(col_4.getCellEditor().getCellEditorValue().toString());
					tableModel.changeValueAt(col_4.getCellEditor().getCellEditorValue().toString(), ((CellEditor) col_4.getCellEditor()).getRow(), 3);
					tableModel.updatetable();
					
					/*Update the memory variable*/
					AgentSettings.agents.get(((CellEditor) col_4.getCellEditor()).getRow()).dataStorageSettings.phase = col_4.getCellEditor().getCellEditorValue().toString();
					
					
				}catch(Exception ex)
				{
					JOptionPane.showMessageDialog(null,"Parameter must be an integer!");
					
				}
    	        
    		}
 	
    	});
	 
	 
	 
	 tableModel.updatetable();
	 
	
}catch(Exception ex){
	
	System.out.println("read storage option settings: "+ex);
}
	
        
}

}
