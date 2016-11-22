import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Vector;
import org.w3c.dom.*;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.xml.parsers.*;


import java.io.*;


public class JDialogParameterSetup extends JDialog{
	
	/*model parameter list with values from from 0.xml file*/
	private ArrayList<ModelParameter> modelParameterListDefault;
	ArrayList<ModelParameter> modelParameterList;
	
	SpecificTableModel tableModel;
	JTable parameterTable;
	String[] columnName = {"Parameter","Value"};
	
	boolean zeroXMLFileNotFound = false;
	
	JPanel container;
	JScrollPane scrollPane;
	
	JDialogParameterSetup(ArrayList<ModelParameter> parameterList){
		
		
		container = new JPanel();
		
		modelParameterList = new ArrayList<ModelParameter>();
		
		for(int i=0; i<parameterList.size();i++ )
		{
			modelParameterList.add(parameterList.get(i).clone());
		}
		
		container.setSize(800, 850);
		
		this.setTitle("Parameter Set up");
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

		container.setLayout(new GridBagLayout());
		GridBagConstraints ts = new GridBagConstraints();
		ts.insets = new Insets( 5, 5, 5, 5);
		
	    Toolkit toolkit =  Toolkit.getDefaultToolkit ();
	    Dimension dim = toolkit.getScreenSize();
	    
	    int dimy = Math.min(dim.height,800);
	    int dimx = Math.min(dim.width,850);
		
		this.setSize(dimx,dimy);
		this.setBackground(Color.white);
		this.setModal(true);
	     
		
		try{
		
			
			File zeroXMLFile = new File(SimulationSettings.ZERO_XML_FILE);
			
			System.out.println(SimulationSettings.ZERO_XML_FILE+"   "+zeroXMLFile.exists());
			
        	/*For exception handling: if file is not found*/
        	if(!zeroXMLFile.exists()){
        		
        		zeroXMLFile=null;
        		
        	}
        	
        	
        	DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        	DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        	Document doc = docBuilder.parse( zeroXMLFile);
        	
        
			
			
		}
		catch(Exception ex1){
        	
       	 JOptionPane.showMessageDialog(null, "Set the path to the initial data file (0.xml)");
       	 
       	  FileChooserFromMenuList chooseFile = new FileChooserFromMenuList(SimulationSettings.ZERO_XML_FILE,false, true,"xml", false);
   		  chooseFile.openFileChooser();
   		SimulationSettings.ZERO_XML_FILE = chooseFile.getDirectoryOrFile();
   		  
   		  try{
   			  
   			  File zeroXMLFile = new File(SimulationSettings.ZERO_XML_FILE);
  			
  			System.out.println(SimulationSettings.ZERO_XML_FILE+"   "+zeroXMLFile.exists());
  			
          	/*For exception handling: if file is not found*/
          	if(!zeroXMLFile.exists()){
          		
          		zeroXMLFile=null;
        
          		
          	}
          	
          	
          	DocumentBuilderFactory docFactory2 = DocumentBuilderFactory.newInstance();
          	DocumentBuilder docBuilder2 = docFactory2.newDocumentBuilder();
          	Document doc2 = docBuilder2.parse( zeroXMLFile);
          	
          
   			  
   		  }catch(Exception ex2){
   			  
   			  return;
	  
   		  }

       }
		   
		    
		/*Take the parameter values from the 0.xml file*/
		ReadParametervaluesFromZeroXMLFile();
		
		
		/*Draw table*/
		
		tableModel = new SpecificTableModel();
		parameterTable = new JTable(tableModel);
		
		/*Set headers*/
		parameterTable.getColumnModel().getColumn(0).setHeaderValue(columnName[0]);
		parameterTable.getColumnModel().getColumn(1).setHeaderValue(columnName[1]);
		
		parameterTable.getColumnModel().getColumn(0).setCellRenderer(new SpecificCellRenderer());
		
		parameterTable.getColumnModel().getColumn(1).setCellEditor(new CellEditor());
		
		
		
		
		
		/*Add cell editor*/
		
		parameterTable.getColumnModel().getColumn(1).getCellEditor().addCellEditorListener(new EditorListener(){
			 
			 
			 public void editingStopped(ChangeEvent e) {
				 
				 
				 int index =  ((CellEditor) parameterTable.getColumnModel().getColumn(1).getCellEditor()).getRow();
				 
 
				 tableModel.changeValueAt(parameterTable.getColumnModel().getColumn(1).getCellEditor().getCellEditorValue().toString(), index, 1);
			
					for(int i=0; i< modelParameterList.size();i++){
						
							
							if(modelParameterList.get(i).name.equals(tableModel.getValueAt( index, 0))){
								
								
								if(modelParameterList.get(i).type.equals("int")){
									try{
										
										System.out.println("XXX  "+tableModel.getValueAt(index, 1).toString());
										
									Integer.parseInt( tableModel.getValueAt(index, 1).toString());
									modelParameterList.get(i).value = tableModel.getValueAt(index, 1).toString();
									
									
									
									}catch(Exception exc){
										
										JOptionPane.showMessageDialog(null,"Parameter must be an integer!");
										
									}
								}else{
									try{
										Double.parseDouble(tableModel.getValueAt(index, 1).toString());
										modelParameterList.get(i).value = tableModel.getValueAt(index, 1).toString();
									}catch(Exception exc){
										
										JOptionPane.showMessageDialog(null,"Parameter must be numeric!");
										
									}
								}
								

								
								
								
								
								
								break;
							}
							
						
						
					}
				 
			    }
			 
			 
			 
		 });
		
		
		
		
		JScrollPane listScroll = new JScrollPane(parameterTable);  
		listScroll.setPreferredSize(new Dimension(500, 700)); 
		ts.gridx=0; ts.gridy=0;
		container.add(listScroll,ts);
		
		
		JButton readyAndSave = new JButton("Confirm");
		readyAndSave.setToolTipText("Confirm edits and close window");
		ts.gridx=1; ts.gridy=1;
		container.add(readyAndSave,ts);
		
		JButton discard = new JButton("Discard");
		discard.setToolTipText("Discard edits");
		ts.gridx=2; ts.gridy=1;
		container.add(discard,ts);
		
		JButton reset = new JButton("Reset");
		reset.setToolTipText("Reset to the default values from the 0.xml file");
		ts.gridx=3; ts.gridy=1;
		container.add(reset,ts);
		
		scrollPane = new JScrollPane(container);  
		scrollPane.setPreferredSize(new Dimension(820, 860)); 
		
		add(scrollPane);
		
		readyAndSave.addActionListener(new ActionListener(){
	 		
		 	public void actionPerformed(ActionEvent evt) {
		 			
		 			
		 			ModelParameterSettings.modelParameters = modelParameterList;
	 			 setVisible(false);
	 			 dispose();
			
		 		}
			});
		   
		   
	   discard.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent evt) {
				
				setVisible(false);
				dispose();
		
			}
			
	  });
		  
	  reset.addActionListener(new ActionListener(){
				
		  public void actionPerformed(ActionEvent evt) {
					
			
			modelParameterList = modelParameterListDefault;
			tableModel.updatetable();
			
			
		}
				
	 });
		  
		  
	addWindowListener( new WindowAdapter(){
			
	public void windowClosing(WindowEvent e)
	{
		
		
	
		Object text = "Confirm settings? \n";
		
		int choice = JOptionPane.showConfirmDialog(null, text,  "Confirm settings?",JOptionPane.YES_NO_CANCEL_OPTION);
	
		if(choice==0){
	
	
			
			ModelParameterSettings.modelParameters = modelParameterList;
			
			setVisible(false);
			dispose();
			
			
		}else if(choice==1){
			
			/*Choice is no*/
			setVisible(false);
			dispose();
			
		}	
	}
	});
	
	
	setVisible(true);
	
}
	
	

	void ReadParametervaluesFromZeroXMLFile(){

		
		try {
			
			modelParameterListDefault = new ArrayList<ModelParameter> ();
			
			/*read pathes to model.xml files from eurace_model.xml*/
	 		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		 	
	 		docFactory.setAttribute("http://xml.org/sax/features/namespaces", true);
	 		docFactory.setAttribute("http://xml.org/sax/features/validation", false);
	 		docFactory.setAttribute("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
	 		docFactory.setAttribute("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

	 		docFactory.setNamespaceAware(true);
	 		docFactory.setIgnoringElementContentWhitespace(false);
	 		docFactory.setIgnoringComments(false);
	 		docFactory.setValidating(false);

	 		
	 		docFactory.setValidating(false);
	 		
	        
	        	File zeroXMLFile = new File(SimulationSettings.ZERO_XML_FILE);
	        	
	        
	        	
	       
		    	DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		        Document doc = docBuilder.parse( zeroXMLFile);
	
		        doc.getDocumentElement().normalize();
		        
		        NodeList nList = doc.getElementsByTagName("environment");
		        
		        Node environmentNode = nList.item(0);
		        Element environmentElement = (Element) environmentNode;
		        
		        /*Setup the default list*/
		        for(int i=0; i<modelParameterList.size();i++)
	        	{
	        		try{
	        	
	        				
	        			ModelParameter temPar = new  ModelParameter(modelParameterList.get(i).type, modelParameterList.get(i).name,modelParameterList.get(i).description);
	        			temPar.value = getTagValue(modelParameterList.get(i).name,environmentElement);
	        			modelParameterListDefault.add(temPar);
	        			
	        			/*If no value is stored in the settings.xml file, then set the default value*/
	        			
	        			if(modelParameterList.get(i).value==null ||modelParameterList.get(i).value.equals("") ){
	        				
	        				modelParameterList.get(i).value = getTagValue(modelParameterList.get(i).name,environmentElement);
	        				
	        			}
	        			
	        			modelParameterListDefault.get(i).value = getTagValue(modelParameterList.get(i).name,environmentElement);
	        		
	        			
	        		}catch(Exception e){
	        			
	        			System.out.println(e);
	        			System.out.println("No value found for this parameter");
	        			
	        		}
	        		
	        	}
	        

	   
	       
        	
		}catch(Exception ex){
			
			
		}
	
	}
	

	
private static String getTagValue(String sTag, Element eElement) {
		
		NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
	 
	        Node nValue = (Node) nlList.item(0);
	        
	   
		      //  System.out.println("Check:   "+sTag+"   "+nValue);
		        
		        //nValue.equals(null);
		 
		        if( nValue == null)
		        {
		        	return "";
		        }else
		        {
		        	return nValue.getNodeValue();
		        }
	        }


 class SpecificTableModel extends AbstractTableModel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	
	int noOfRows, noOfColumns;
	
	
 
 	SpecificTableModel(){
 
 		super();
 	}
	

	public int getRowCount()
    {
    return modelParameterList.size();
  }
    public int getColumnCount()
    {
    return 2;
  }
    public Object getValueAt( int row, int col)
    {
    	if(col==0)
    		return modelParameterList.get(row).name;
    	else 
    		return modelParameterList.get(row).value;
    }
    
    public String getColumnName(int column) {  
        return columnName[column]; 
    }
    
    
    public void changeValueAt(String value, int row, int col) {  
        
    	modelParameterList.get(row).value = value;
    	fireTableDataChanged();  
          
          
    }  
    
    
    public void updatetable() {  
        
        fireTableDataChanged();    
          
    }  
    
    public boolean isCellEditable(int row, int column){ 
       
    	if(column==0){
    		return false;	
    	}else{
    		return true;
    	}
    }
    
}
 
 private class SpecificCellRenderer extends JLabel implements TableCellRenderer{
	 
	 
	 public Component getTableCellRendererComponent(JTable table, Object value,
	            boolean isSelected, boolean hasFocus, int rowIndex, int vColIndex) {
	        // 'value' is value contained in the cell located at
	        // (rowIndex, vColIndex)

	        if (isSelected) {
	            // cell (and perhaps other cells) are selected
	        }

	        if (hasFocus) {
	            // this cell is the anchor and the table has the focus
	        }

	        // Configure the component with the specified value
	        setText(value.toString());

	        // Set tool tip if desired
	        
	        for(int i=0; i<modelParameterList.size();i++){
	        	
	        	if(modelParameterList.get(i).name.equals((String)value)){
	        		
	        		 setToolTipText(modelParameterList.get(i).name+" ("+modelParameterList.get(i).type+"): "+modelParameterList.get(i).description);
	        		
	        	}
	        	
	        	
	        }
	        
	       

	        // Since the renderer is a component, return itself
	        return this;
	    }

	    // The following methods override the defaults for performance reasons
	    public void validate() {}
	    public void revalidate() {}
	    protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {}
	    public void firePropertyChange(String propertyName, boolean oldValue, boolean newValue) {}
	 
	 
 }
		
	
}
