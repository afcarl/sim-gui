import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;


public class EditorListener implements CellEditorListener{
	
	
	public void editingStopped(ChangeEvent e) {
        System.out.println("A cell has been edited.");
        
       System.out.println(e.getSource().getClass());
        
     //  e.getSource().update();
    }
	

    /**
     * Listens for cells where editing has been canceled (cell data has not been
     * changed).
     */
    public void editingCanceled(ChangeEvent e) {
        System.out.println("Editing of a cell has been canceled.");
    }

}
