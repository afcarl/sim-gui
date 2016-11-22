
	import java.awt.Color;
	import java.awt.Component;
	import java.awt.Dimension;
	import java.awt.GridBagConstraints;
	import java.awt.GridBagLayout;
	import java.awt.Insets;
	import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.EventListener;

import javax.swing.DefaultCellEditor;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;	
import javax.swing.event.ChangeEvent;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;








	public class JDialogBatchExperiments extends JDialog{
		
		/*model parameter list with values from from 0.xml file*/
		JTable parameterTable;
		
		
		JMenuBar menuBar;
	
		RunExperiment run;
		
		private JMenu menuBatch;
		private JMenuItem addBatch, removeBatch, exit;
		
		JPanel container;
		JScrollPane scrollPane;
		
		JScrollPane scrollExperimentBatchTable;
		BatchExperimentTable experimentBatchTable;
		GridBagConstraints ts = new GridBagConstraints();
		
		JDialogBatchExperiments(){
			
			
			container = new JPanel();

			container.setSize(800, 850);
			
			this.setTitle("Experiment Batch");
			this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

			container.setLayout(new GridBagLayout());
			
			ts.insets = new Insets( 5, 5, 5, 5);
			
		    Toolkit toolkit =  Toolkit.getDefaultToolkit ();
		    Dimension dim = toolkit.getScreenSize();
		    
		    int dimy = Math.min(dim.height,400);
		    int dimx = Math.min(dim.width,850);
			
			this.setSize(dimx,dimy);
			this.setBackground(Color.white);
			this.setModal(true);
		     
			
		
			/*Menus*/
			menuBar = new JMenuBar();
			
			
			 menuBatch = new JMenu("Experiment Batch");
			 
			 addBatch = new JMenuItem("Add Experiment");
			 
			 addBatch.addActionListener(new ActionListener(){
		    		
		    		public void actionPerformed(ActionEvent evt) {
				 
		    			try {
							
		    				
							FileChooserFromMenuList chooseFile = new FileChooserFromMenuList(SimulationSettings.WORKING_DIRECTORY,false, true, "experiment.xml", false);
			    			chooseFile.openFileChooser();
			    			
			    			String file = chooseFile.getDirectoryOrFile();
			   
			    			File settingsFile = new File(chooseFile.getDirectoryOrFile());
			
							
							if(!settingsFile.exists()){
								
								throw new FileNotFoundException();
								
							}
							
							if(file.endsWith("experiment.xml")){
							
								BatchExperiments.Experiment tempExp = (new BatchExperiments()).new  Experiment();
								
								
								
								
								ReadXMLFile defValues = new ReadXMLFile(file);
								defValues.new ReadClassFromXML(tempExp,"experiment");
								
								BatchExperiments.experimentBatch.add(tempExp);
								
								drawTable();
							}
							
							}catch(Exception ex){
								
								
								System.out.println(ex);
								
								
							}
							
		    			}
				 
		    		
				 
			 });
			 
			 
			 removeBatch = new JMenuItem("Remove Experiment");
			 
			 removeBatch.addActionListener(new ActionListener(){
		    		
				 JDialog removeExpDialog;
				 JList removeExperimentList;
				 
		    		public void actionPerformed(ActionEvent evt) {
		    			
		    			
		    			
		    			removeExpDialog = new JDialog();
		    			removeExpDialog.setSize(300,300);
		    			removeExpDialog.setLayout(new GridBagLayout());
						
						GridBagConstraints fA = new GridBagConstraints();
						fA.insets = new Insets( 5, 5, 5, 5);
						
						JLabel label1 = new JLabel("Delete Agent from Plotting:");
						fA.gridx =0;fA.gridy =0;
						removeExpDialog.add(label1,fA);
						
						DefaultListModel dlmExpToremove = new DefaultListModel();
						
						removeExperimentList = new JList(dlmExpToremove);
						
						for(int i=0; i<BatchExperiments.experimentBatch.size();i++){
							
							
								
							dlmExpToremove.addElement(BatchExperiments.experimentBatch.get(i).path);
								
						
							
						}
						
						removeExperimentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
						removeExperimentList.setVisibleRowCount(6);
						
						removeExperimentList.addMouseListener(new MouseListener(){
							
							public void mouseClicked(MouseEvent e) {

								System.out.println("One Click in row");
								
								if(e.getClickCount()==2){
									
									System.out.println("Double Click in row");
									
									for(int i=0; i<BatchExperiments.experimentBatch.size();i++){
										
										if(BatchExperiments.experimentBatch.get(i).path.equals(removeExperimentList.getModel().getElementAt(removeExperimentList.locationToIndex(e.getPoint())))){
											 //Create the tab pages for the agent that is selected for plotting
							
											

											BatchExperiments.experimentBatch.remove(i);
										
					
											removeExpDialog.dispose();
											removeExpDialog.setVisible(false);
											
											drawTable();
											
										
											break;	
										}
										
									}
								}
							}

							public void mouseEntered(MouseEvent arg0) {
						
							}
							public void mouseExited(MouseEvent arg0) {
								
							}

							public void mousePressed(MouseEvent arg0) {
							
							}

							public void mouseReleased(MouseEvent arg0) {
								
							}
						});
						
						fA.gridx =0;fA.gridy =1;
						
						removeExpDialog.getContentPane().add(new JScrollPane(removeExperimentList),fA);
						
						removeExpDialog.setModal(true);
						removeExpDialog.setVisible(true);
					}
			
				 
			 });
			 
			 exit  = new JMenuItem("Exit");
			
			 exit.addActionListener(new ActionListener(){
		    		
		    		public void actionPerformed(ActionEvent evt) {
		    			
		    			dispose();
		    			setVisible(false);
				 
				 
		    		}
				 
			 });
			
			 menuBatch.add(addBatch);
			 
			 menuBatch.add(removeBatch);
			 
			 menuBatch.add(exit);
			 
			 
			 menuBar.add(menuBatch);
			 
			setJMenuBar(menuBar);
			 
			experimentBatchTable = new  BatchExperimentTable();
			
			scrollExperimentBatchTable = experimentBatchTable.listScrollVariables;
			scrollExperimentBatchTable.setPreferredSize(new Dimension(700, 179)); 
			ts.gridx=0; 
			ts.gridy=0;
			container.add(scrollExperimentBatchTable,ts);
			
			
			run = new RunExperiment("Run Batch");
			run.setToolTipText("This runs the experiment batch.");
			ts.gridx=1; ts.gridy=1;
			container.add(run,ts);
			
			run.setEnabled(true);
			scrollPane = new JScrollPane(container);  
			scrollPane.setPreferredSize(new Dimension(820, 860)); 
			
			add(scrollPane);
			
			//setModal(true);
			setVisible(true);
			
		 
	 }
			
		
		private void drawTable(){
			
			/*Redraw the table*/
			container.remove(scrollExperimentBatchTable);
			validate();
			
			experimentBatchTable = new  BatchExperimentTable();
			
			scrollExperimentBatchTable = experimentBatchTable.listScrollVariables;
			scrollExperimentBatchTable.setPreferredSize(new Dimension(700, 179)); 
			ts.gridx=0; 
			ts.gridy=0;
			container.add(scrollExperimentBatchTable,ts);

			validate();
			
			
		}
		

		
		private class BatchExperimentTable extends JScrollPane{
			
			
			BatchExperimentTableModel batchExperimentTableModel;
			JTable batchExperimentTable;
			
			JScrollPane listScrollVariables;  
			

			BatchExperimentTable(){
				

				
				String[] columnName = {"Path","Parameter 1", "Values", "Parameter 2", "Values"};
				
		
				
		
				
				
				batchExperimentTableModel = new BatchExperimentTableModel(columnName );
				batchExperimentTable = new JTable(batchExperimentTableModel);

				
				batchExperimentTable.setRowHeight(20);
				
				batchExperimentTable.getColumnModel().getColumn(0).setPreferredWidth(140);
				batchExperimentTable.getColumnModel().getColumn(0).setCellEditor(new CellEditor());
	
				
				
				batchExperimentTable.getColumnModel().getColumn(1).setPreferredWidth(140);
				batchExperimentTable.getColumnModel().getColumn(1).setCellEditor(new CellEditor());
				
				
				
				batchExperimentTable.getColumnModel().getColumn(2).setPreferredWidth(140);
				batchExperimentTable.getColumnModel().getColumn(2).setCellEditor(new CellEditor());
			
				
				batchExperimentTable.getColumnModel().getColumn(2).setCellRenderer(new SpecificComboBoxRenderer1());
				batchExperimentTable.getColumnModel().getColumn(2).setCellEditor(new SpecificComboBoxEditor1());
			    	      
				
					
				batchExperimentTable.getColumnModel().getColumn(3).setPreferredWidth(140);
				batchExperimentTable.getColumnModel().getColumn(3).setCellEditor(new CellEditor());
				
				
				
				batchExperimentTable.getColumnModel().getColumn(4).setPreferredWidth(140);
				batchExperimentTable.getColumnModel().getColumn(4).setCellEditor(new CellEditor());
			
				
				batchExperimentTable.getColumnModel().getColumn(4).setCellRenderer(new SpecificComboBoxRenderer2());
				batchExperimentTable.getColumnModel().getColumn(4).setCellEditor(new SpecificComboBoxEditor2());
				
				
				

				
				batchExperimentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
				
				
				System.out.println("draw table");
				batchExperimentTableModel.updatetable();
				
				listScrollVariables = new JScrollPane(batchExperimentTable);
				
				
			}
			
			
			
			public class SpecificComboBoxEditor1 extends DefaultCellEditor {
				
				int editedRow;
				int editedCol;
				
				JComboBox cComboBox;
				

			    public SpecificComboBoxEditor1() {
			        super(new JComboBox());
			        
			        cComboBox = new JComboBox();
			    
			    }
			    
			    
			    
			 // This method is called when a cell value is edited by the user.
			    public Component getTableCellEditorComponent(JTable table, Object value,
			            boolean isSelected, int rowIndex, int vColIndex) {
			        // 'value' is value contained in the cell located at (rowIndex, vColIndex
			    	
		
			    	editedRow = rowIndex;
			    	editedCol = vColIndex;
			    	
			    	cComboBox.removeAllItems();
			    	
			    	cComboBox.addItem("Values");
			    	
		    	
			    	for(int i=0; i<BatchExperiments.experimentBatch.size();i++){

						 if(BatchExperiments.experimentBatch.get(i).path.equals(batchExperimentTableModel.getValueAt(editedRow, 0)) )
						 {
								for(int j=0; j<BatchExperiments.experimentBatch.get(i).parameter1.values.size();j++){
									
									cComboBox.addItem(BatchExperiments.experimentBatch.get(i).parameter1.values.get(j).value);
		
								}
						 }

			    	}
			    	
			    	
			    
			        System.out.println("value here "+BatchExperiments.experimentBatch.get(0).parameter1.values.size());
			        
			       for(int i=0; i<cComboBox.getItemCount();i++ )
			        {
			    	  
			        	if(cComboBox.getItemAt(i).toString().equals(batchExperimentTableModel.getValueAt(editedRow, editedCol))){

			        		 cComboBox.setSelectedIndex(i);
			        		
			        	}
			        	try{
				        	if(batchExperimentTableModel.getValueAt(editedRow, editedCol).equals("")){

				        		 cComboBox.setSelectedIndex(0);
				        		
				        	}
			        	}catch(Exception e){
			        		
			        		/*catch null pointer exception*/
			        		 cComboBox.setSelectedIndex(0);
			        	}
			        	
			        }
			       
			       
			       System.out.println(editedRow);

			        if (isSelected) {
			        	
			        }
			       
			        return cComboBox;
			      
			        
			    }
			    
			}
			
			
			
			public class SpecificComboBoxEditor2 extends DefaultCellEditor {
				
				int editedRow;
				int editedCol;
				
				JComboBox cComboBox;
				

			    public SpecificComboBoxEditor2() {
			        super(new JComboBox());
			        
			        cComboBox = new JComboBox();
			    
			    }
			    
			    
			    
			 // This method is called when a cell value is edited by the user.
			    public Component getTableCellEditorComponent(JTable table, Object value,
			            boolean isSelected, int rowIndex, int vColIndex) {
			        // 'value' is value contained in the cell located at (rowIndex, vColIndex
			    	
		
			    	editedRow = rowIndex;
			    	editedCol = vColIndex;
			    	
			    	cComboBox.removeAllItems();
			    	
			    	cComboBox.addItem("Values");
			    	
		    	
			    	for(int i=0; i<BatchExperiments.experimentBatch.size();i++){

						 if(BatchExperiments.experimentBatch.get(i).path.equals(batchExperimentTableModel.getValueAt(editedRow, 0)) )
						 {
								for(int j=0; j<BatchExperiments.experimentBatch.get(i).parameter2.values.size();j++){
									
									cComboBox.addItem(BatchExperiments.experimentBatch.get(i).parameter2.values.get(j).value);
		
								}
						 }

			    	}
			    	
			    	
			    
			        System.out.println("value here "+value);
			        
			       for(int i=0; i<cComboBox.getItemCount();i++ )
			        {
			    	  
			        	if(cComboBox.getItemAt(i).toString().equals(batchExperimentTableModel.getValueAt(editedRow, editedCol))){

			        		 cComboBox.setSelectedIndex(i);
			        		
			        	}
			        	try{
				        	if(batchExperimentTableModel.getValueAt(editedRow, editedCol).equals("")){

				        		 cComboBox.setSelectedIndex(0);
				        		
				        	}
			        	}catch(Exception e){
			        		
			        		/*catch null pointer exception*/
			        		 cComboBox.setSelectedIndex(0);
			        	}
			        	
			        }
			       
			       
			       System.out.println(editedRow);

			        if (isSelected) {
			        	
			        }
			       
			        return cComboBox;
			      
			        
			    }
			    
			}
			

			
			
			public class SpecificComboBoxRenderer1 extends JComboBox implements TableCellRenderer {
				
			    public SpecificComboBoxRenderer1() {
			        super();
			    }

			    public Component getTableCellRendererComponent(JTable table, Object value,
			            boolean isSelected, boolean hasFocus, int row, int column) {
			    	
			    	super.addItem("Values");
			    	super.setEditable(false);
			    	
			    	
			    	for(int i=0; i<BatchExperiments.experimentBatch.size();i++){

						 if(BatchExperiments.experimentBatch.get(i).path.equals(batchExperimentTableModel.getValueAt(row, 0)) )
						 {
								for(int j=0; j<BatchExperiments.experimentBatch.get(i).parameter1.values.size();j++){
									
									super.addItem(BatchExperiments.experimentBatch.get(i).parameter1.values.get(j).value);
		
								}
						 }

			    	}
			    	

			     	
			    
			        return this;
			    }
			    
			}
			
			
			public class SpecificComboBoxRenderer2 extends JComboBox implements TableCellRenderer {
				
			    public SpecificComboBoxRenderer2() {
			        super();
			    }

			    public Component getTableCellRendererComponent(JTable table, Object value,
			            boolean isSelected, boolean hasFocus, int row, int column) {
			    	
			    	super.addItem("Values");
			    	super.setEditable(false);
			    	
			    	
			    	for(int i=0; i<BatchExperiments.experimentBatch.size();i++){

						 if(BatchExperiments.experimentBatch.get(i).path.equals(batchExperimentTableModel.getValueAt(row, 0)) )
						 {
								for(int j=0; j<BatchExperiments.experimentBatch.get(i).parameter2.values.size();j++){
									
									super.addItem(BatchExperiments.experimentBatch.get(i).parameter2.values.get(j).value);
		
								}
						 }

			    	}
			    	

			     	
			    
			        return this;
			    }
			    
			}
			
			
			
		}
		


	public class BatchExperimentTableModel extends AbstractTableModel{
		
		JCheckBox checkBox = new JCheckBox();
		String [] columnName;
		
		
		
		
		
		int numFilters;
		boolean partitioningSelected , filterSelected;
		
		
		PlottingSettings.Agent variableInstances;
		
		
		BatchExperimentTableModel(String[] colName){


			columnName = colName;
		
			
		}
		
		
		
		public int getRowCount()
	   {
		
		return BatchExperiments.experimentBatch.size();
		
	 }
		
		
	   public int getColumnCount()
	   {
	   return 5;
	 }
	   public Object getValueAt( int row, int col)
	   {
		   
		
		   
		  
			 //  System.out.println("getValueAt timeSeriesSelection.get(i).isntancesOfvariable.size()"+timeSeriesSelection.get(i).isntancesOfvariable.size());
			
					   if(col==0)
						   return BatchExperiments.experimentBatch.get(row).path;
					   else if(col==1)
						   return BatchExperiments.experimentBatch.get(row).parameter1.name;
					   else if(col==2)
						   return "Values";
					   else if(col==3)
						   return BatchExperiments.experimentBatch.get(row).parameter2.name;
					   else if(col==4){
						   return "Values";
					   }else{
						   return null;
					   }
					  
				   
				
	   }
		   
		   
		   
		   
		   
		   
	   
	   
	   public String getColumnName(int column) {  
	       return columnName[column]; 
	   }
	   
	 
	   
	   public void changeValueAt(String value, int row, int col) {  
	       
		   System.out.println("changeValueAt");
		  

		 
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
	      
		   if(column== 2 || column==4)
	   			return true;
		   else
			   return false;
	   	
	   }
	   

	}
	
	
	class RunExperiment  extends JButton{
		
		RunExperiment(String text){
			
			super(text);
			 this.addActionListener(new ActionListener(){
				 
				 public void actionPerformed(ActionEvent evt) {
					 
					 
						String PathToFile = new String("launch_exps.sh");
		    			
		    			
		    			writeGeneralSettingsToFile launchExps = new  writeGeneralSettingsToFile(PathToFile, true);
		    			
		    		
		    			launchExps.writeToFile("####################################################");
		    			launchExps.writeToFile("# This script is automatically generated by the GUI.\n");
		    			
		    			launchExps.writeToFileWithoutNewLine("export exps=\"");
		    			
		    			for(int i=0; i<BatchExperiments.experimentBatch.size();i++ ){
		    				
		    				
		    				launchExps.writeToFileWithoutNewLine(BatchExperiments.experimentBatch.get(i).path+" ");
		    				
		    				
		    			}
				 
		    			launchExps.writeToFileWithoutNewLine(" \"\n\n\n");
		    			
		    			launchExps.writeToFile("for exp in $exps; do\n");
		    			
		    			launchExps.writeToFileWithoutNewLine("\t cd $exp \n");
		    			
		    			launchExps.writeToFileWithoutNewLine("\t cp $exp'/variables.txt' "+SimulationSettings.PATH_TO_RSCRIPTS+"/Data_Files/variables.txt\n");
		    			launchExps.writeToFileWithoutNewLine("\t cp $exp'/time_series_data.txt' "+SimulationSettings.PATH_TO_RSCRIPTS+"/Data_Files/time_series_data.txt\n");
		    			launchExps.writeToFileWithoutNewLine("\t cp $exp'/multiple_time_series_data.txt' "+SimulationSettings.PATH_TO_RSCRIPTS+"/Data_Files/multiple_time_series_data.txt\n");
		    			launchExps.writeToFileWithoutNewLine("\t cp $exp'/growth_rate_data.txt' "+SimulationSettings.PATH_TO_RSCRIPTS+"/Data_Files/growth_rate_data.txt\n");
		    			launchExps.writeToFileWithoutNewLine("\t cp $exp'/ratio_data.txt' "+SimulationSettings.PATH_TO_RSCRIPTS+"/Data_Files/ratio_data.txt\n");
		    			launchExps.writeToFileWithoutNewLine("\t cp $exp'/boxplot_data.txt' "+SimulationSettings.PATH_TO_RSCRIPTS+"/Data_Files/boxplot_data.txt\n");
		    			launchExps.writeToFileWithoutNewLine("\t cp $exp'/histogram_data.txt' "+SimulationSettings.PATH_TO_RSCRIPTS+"/Data_Files/histogram_data.txt\n");
		    			launchExps.writeToFileWithoutNewLine("\t cp $exp'/heat_maps_data.txt' "+SimulationSettings.PATH_TO_RSCRIPTS+"/Data_Files/heat_maps_data.txt\n");
		    			launchExps.writeToFileWithoutNewLine("\t cp $exp'/scatter_data.txt' "+SimulationSettings.PATH_TO_RSCRIPTS+"/Data_Files/scatter_data.txt\n");
		    			launchExps.writeToFileWithoutNewLine("\t cp $exp'/Configure.r' "+SimulationSettings.PATH_TO_RSCRIPTS+"/Configure.r\n");
		    			
		    			launchExps.writeToFileWithoutNewLine(" \n\n");
		    			
		    			launchExps.writeToFileWithoutNewLine("\t bash run_exp.sh \n");
		    			launchExps.writeToFileWithoutNewLine("\t echo '$EXPERIMENT_NAME=' $exp \n");
		    			launchExps.writeToFileWithoutNewLine("\t cd .. \n");
		    			
		    			launchExps.writeToFile("done\n");
		    			
		    			launchExps.writeToFile("echo '  Done.'");
		    			
					 
						
						 ExecuteSimulations exeSim = new ExecuteSimulations();
						 exeSim.start();
					 
					 
				 }
				 
			 });
		}
		
	}
	
	
	
	class ExecuteSimulations extends Thread {
	       
        ExecuteSimulations() {
          
        }

        public void run() {
        	
        	
        	try {
				  

				String[] args = {"/bin/bash","launch_exps.sh"};
				
				run.setEnabled(false);
				

				 Process process = new ProcessBuilder(args).start();
				       InputStream is = process.getInputStream();
				       InputStreamReader isr = new InputStreamReader(is);
				       BufferedReader br = new BufferedReader(isr);
				       String line;

				     // System.out.printf("Output of running %s is:", 
					 //Arrays.toString(args));

				       while ((line = br.readLine()) != null) {
					 System.out.println(line);
				       }
				       
				       
				       
				       ProcessExitDetector processExitDetector = new ProcessExitDetector(process);
				       processExitDetector .addProcessListener(new ProcessListener() {
				           public void processFinished(Process process) {
				               System.out.println("The subprocess has finished.");
				               run.setEnabled(true);
				           }
				       });
				       processExitDetector.start();
	        
	      }catch (IOException e) {
	        e.printStackTrace();
	      }
            
           
        }
    }
	
	
	
	
	/**
	 * Detects when a process is finished and invokes the associated listeners.
	 */
	public class ProcessExitDetector extends Thread {

	    /** The process for which we have to detect the end. */
	    private Process process;
	    /** The associated listeners to be invoked at the end of the process. */
	    private ArrayList<ProcessListener> listeners = new ArrayList<ProcessListener>();

	    /**
	     * Starts the detection for the given process
	     * @param process the process for which we have to detect when it is finished
	     */
	    public ProcessExitDetector(Process process) {
	        try {
	            // test if the process is finished
	            process.exitValue();
	            throw new IllegalArgumentException("The process is already ended");
	        } catch (IllegalThreadStateException exc) {
	            this.process = process;
	        }
	    }

	    /** @return the process that it is watched by this detector. */
	    public Process getProcess() {
	        return process;
	    }

	    public void run() {
	        try {
	            // wait for the process to finish
	            process.waitFor();
	            // invokes the listeners
	            for (ProcessListener listener : listeners) {
	                listener.processFinished(process);
	            }
	        } catch (InterruptedException e) {
	        }
	    }

	    /** Adds a process listener.
	     * @param listener the listener to be added
	     */
	    public void addProcessListener(ProcessListener listener) {
	        listeners.add(listener);
	    }

	    /** Removes a process listener.
	     * @param listener the listener to be removed
	     */
	    public void removeProcessListener(ProcessListener listener) {
	        listeners.remove(listener);
	    }
	}
	
	
	public interface ProcessListener extends EventListener {
	    void processFinished(Process process);
	}
		

}
