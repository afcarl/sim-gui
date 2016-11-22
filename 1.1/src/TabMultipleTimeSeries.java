import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.DefaultCellEditor;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;


public class TabMultipleTimeSeries extends JPanel{
	
	
	

	JMenuBar menuBar;
	JMenu multipleTimeSeries, menuSettings;
	JMenuItem addTimeSeries, editTimeSeries ,removeTimeSeries, applyChanges, exit;
	JPanel container;

	

	
	JScrollPane scrollMultipleTimeSeriesTable;
	MultipleTimeSeriesTable multipleTimeSeriesTable;
	GridBagConstraints ts;
	
	boolean tempRatiosSelectedForMultipleTimeSeries;
	
	PlottingSettings.DefaulMultipleTimeSeriesSettings tempDefaults;

	TabMultipleTimeSeries(){
		
		//agentsReturn = agentsPassed;
		
		
		tempRatiosSelectedForMultipleTimeSeries = PlottingSettings.ratiosSelectedForMultipleTimeSeries;
		
		tempDefaults = PlottingSettings.defaultsMultipleTimeSeries.clone();

		
		/*Dialog settings*/	
	  
		setSize(800,400);
		setBackground( Color.gray );

	   // setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		
		
		  setLayout(new BorderLayout());
		  
		  
		  container = new JPanel();
		  
		  
		  
		  container.setLayout(new GridBagLayout());
	    
		ts = new GridBagConstraints();
		ts.insets = new Insets( 5, 5, 5, 5);
		
		
		menuBar = new JMenuBar();
		
		
		/*1. menu*/
		
		
		multipleTimeSeries = new JMenu("Multiple Time Series");
		
		
		addTimeSeries = new JMenuItem("Add Time Series");
		
		multipleTimeSeries.add(addTimeSeries);
		
		addTimeSeries.addActionListener(new ActionListener(){
			
			
			JDialog addTimeSeriesDialog;
			GridBagConstraints fil;
			DefaultListModel selectedAgentListModel;
			JList selectedAgentList;
			JPanel panel1;
			DefaultListModel variableListModel;
			JList variableList;
			DefaultListModel multipleTimeSeriesModel;
			JList multipleTimeSeries;
			JButton okayButton;
			String tempAgentType;
			
			ArrayList <TimeSeriesElement> timeSeriesElements;
			

			public void actionPerformed(ActionEvent arg0) {

				timeSeriesElements = new ArrayList <TimeSeriesElement>();
				
				addTimeSeriesDialog = new JDialog();
				addTimeSeriesDialog.setModal(true);
					
				addTimeSeriesDialog.setSize(850,450);
				addTimeSeriesDialog.setBackground(Color.white);
		   			
					
				addTimeSeriesDialog.setTitle("Add Multiple Time Series");
				addTimeSeriesDialog.setLayout(new GridBagLayout());
				fil = new GridBagConstraints();
	   			fil.insets = new Insets( 5, 5, 5, 5);
		   			
		   			
		   			
	   			selectedAgentListModel = new DefaultListModel();
	   			selectedAgentList = new JList(selectedAgentListModel);
	   			
	   			selectedAgentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		   			
	   			variableListModel = new DefaultListModel();
	   			variableList = new JList(variableListModel);
	   			
	   			variableList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	   			
	   			multipleTimeSeriesModel = new DefaultListModel();
	   			multipleTimeSeries = new JList(multipleTimeSeriesModel);
		   			
	   			/*Add selected agents*/
   				for(int i=0; i<PlottingSettings.listOfAgentsVariableInstances.size();i++){
					
					if(PlottingSettings.listOfAgentsVariableInstances.get(i).isSelected){
						
						selectedAgentListModel.addElement(PlottingSettings.listOfAgentsVariableInstances.get(i).agentName);
						
					}
				}
   				
   				/*If ratios are selected in the variable list add ratios*/
				if(PlottingSettings.listOfRatioInstances.size()>0){
					
					selectedAgentListModel.addElement("Ratios");
				}
	   				
	   				
	   			selectedAgentList.addMouseListener(new MouseListener(){
						
						public void mouseClicked(MouseEvent e) {

							System.out.println("One Click in row");
							

							if(e.getClickCount()==2){
								
								System.out.println("Double Click in row");
								
								if(selectedAgentList.getModel().getElementAt(selectedAgentList.locationToIndex(e.getPoint())).equals("Ratios")){
									
									tempAgentType = "Ratios";
									variableListModel.removeAllElements();
									
									for(int i=0; i<PlottingSettings.listOfRatioInstances.size();i++){
										
										boolean alreadySelected = false; 
										
										for(int j=0; j < timeSeriesElements.size();j++)
										{
											if(timeSeriesElements.get(j).elementName.equals(PlottingSettings.listOfRatioInstances.get(i).ratioInstanceName)){
												
												alreadySelected = true;
												break;
												
											}
										}
										if(!alreadySelected)
											variableListModel.addElement(PlottingSettings.listOfRatioInstances.get(i).ratioInstanceName);
										
									}
									
								}else
								{
								
									for(int i=0; i<PlottingSettings.listOfAgentsVariableInstances.size();i++){
										
										if(PlottingSettings.listOfAgentsVariableInstances.get(i).agentName.equals(selectedAgentList.getModel().getElementAt(selectedAgentList.locationToIndex(e.getPoint())))){
											 //Create the tab pages for the agent that is selected for plotting
											
											tempAgentType = PlottingSettings.listOfAgentsVariableInstances.get(i).agentName;
											
											variableListModel.removeAllElements();
											
											for(int j=0; j < PlottingSettings.listOfAgentsVariableInstances.get(i).listOfVariableInstances.size(); j++){

														
														boolean alreadySelected = false;
														
														for(int l=0; l < timeSeriesElements.size();l++)
														{
															if(timeSeriesElements.get(l).elementName.equals(PlottingSettings.listOfAgentsVariableInstances.get(i).listOfVariableInstances.get(j).instanceName)){
																
																alreadySelected = true;
																break;
																
															}
														}
														if(!alreadySelected)
															variableListModel.addElement(PlottingSettings.listOfAgentsVariableInstances.get(i).listOfVariableInstances.get(j).instanceName);
													
													}
							
											break;	
										}
										
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
	   				

	   				JLabel label1 = new JLabel("Choose Agent:");
	   				
	   				fil.gridx =1;fil.gridy =0;
	   				addTimeSeriesDialog.add(label1,fil);
	  
	  
	   				fil.gridx =1;fil.gridy =1;
	   				JScrollPane agentListScroll = new JScrollPane(selectedAgentList);
	   				agentListScroll.setPreferredSize(new Dimension(75,150));
	   				
	   			
	   				
	   				
	   				addTimeSeriesDialog.add(agentListScroll,fil);
	   				
	   				
	   				JLabel label2 = new JLabel("Choose Variable Instance:");
	   				
	   				fil.gridx =2;fil.gridy =0;
	   				addTimeSeriesDialog.add(label2,fil);
	   			
	   				
	   				fil.gridx =2;fil.gridy =1;
	   				
	   				JScrollPane variableListScroll = new JScrollPane(variableList);
	   				variableListScroll.setPreferredSize(new Dimension(200,150));
	   				
	   				addTimeSeriesDialog.add(variableListScroll,fil);
	   				
	   				fil.gridx =1;fil.gridy =1;
	   				

					JPanel panel1 = new JPanel();
					
					panel1.setLayout(new GridBagLayout());
					GridBagConstraints fil1 = new GridBagConstraints();
		   			fil1.insets = new Insets( 5, 5, 5, 5);
					
		   			JButton add = new JButton("Add >>");
	   				JButton remove = new JButton("<<Remove");
		   			
					fil1.gridx =0;fil1.gridy =0;
					panel1.add(add,fil1);
		
					fil1.gridx =0;fil1.gridy =1;
					panel1.add(remove,fil1);
					
					fil.gridx =3;fil.gridy =1;
					addTimeSeriesDialog.add(panel1,fil);
					
					fil.gridx =4;fil.gridy =0;
					addTimeSeriesDialog.add(new JLabel("Selected Time Series:"),fil);
					
					fil.gridx =4;fil.gridy =1;
					JScrollPane multipleTimeSeriesScroll = new JScrollPane(multipleTimeSeries);
	   				multipleTimeSeriesScroll.setPreferredSize(new Dimension(200,150));
	   				
	   				addTimeSeriesDialog.add(multipleTimeSeriesScroll,fil);
	   				
	   				okayButton = new JButton("OK");
	   				JButton discardButton = new JButton("Discard");
	   				
	   				fil.gridx =5;fil.gridy =2;
	   				addTimeSeriesDialog.add(okayButton,fil);
	   				
	   				fil.gridx =6;fil.gridy =2;
	   				addTimeSeriesDialog.add(discardButton,fil);
	   	
					add.addActionListener(new ActionListener() {

						
						public void actionPerformed(ActionEvent e) {
							
							int [] selectedIndices = variableList.getSelectedIndices();
							for(int i=0; i < selectedIndices.length;i++){
								
								multipleTimeSeriesModel.addElement(variableListModel.get(selectedIndices[i]));

								timeSeriesElements.add(new TimeSeriesElement(variableListModel.get(selectedIndices[i]).toString(),tempAgentType));
								
							}
							for(int i=selectedIndices.length-1;i>=0;i--){
								
								variableListModel.remove(selectedIndices[i]);
								
							}
						}
					});
					/*remove item(s) from the list. Only add them to the variable list if the agent type is identical*/
					remove.addActionListener(new ActionListener() {

						
						public void actionPerformed(ActionEvent e) {
							
							int [] selectedIndices = multipleTimeSeries.getSelectedIndices();
						
							for(int i=0; i < selectedIndices.length;i++){
								
								for(int j= 0; j <timeSeriesElements.size();j++ ){
									
									if(multipleTimeSeriesModel.get(selectedIndices[i]).toString().equals(timeSeriesElements.get(j).elementName)){

										if(timeSeriesElements.get(j).elementBelongsTo.equals(tempAgentType))
											variableListModel.addElement(multipleTimeSeriesModel.get(selectedIndices[i]));
										
										timeSeriesElements.remove(j);
										j--;
									}
									
								}
			
							}
							for(int i=selectedIndices.length-1;i>=0;i--){
								
								multipleTimeSeriesModel.remove(selectedIndices[i]);
								
							}
						}
					});

	   				
	   				okayButton.addActionListener(new ActionListener(){

						public void actionPerformed(ActionEvent e) {
							
							
							
							if(timeSeriesElements.size()>1){
							
							String name = "mts_";
							
							for(int i=0; i< timeSeriesElements.size()-1;i++){
								
								name = name+timeSeriesElements.get(i).elementName+"_"; 
	
							}
							if(timeSeriesElements.size()-1>0){
								
								name = name+timeSeriesElements.get(timeSeriesElements.size()-1).elementName;
								
							}
							
							
							PlottingSettings.MultipleTimeSeries tempMTS = (new PlottingSettings()).new MultipleTimeSeries(name);

							
							for(int i=0; i< timeSeriesElements.size();i++){
								
								if(timeSeriesElements.get(i).elementBelongsTo.equals("Ratio")){
									
									for(int j=0; j<PlottingSettings.listOfRatioInstances.size();j++ ){
										
										if(PlottingSettings.listOfRatioInstances.get(j).ratioInstanceName.equals(timeSeriesElements.get(i).elementName)){
											
											tempMTS.ratiosUsedForMultioleTimeSeries.add(PlottingSettings.listOfRatioInstances.get(j));
											
											PlottingSettings.listOfRatioInstances.get(j).isSelectedForMultipleTimeSeries = true;
											
										}
										
										
									}
									
									
									
								}else{
									
									for(int j=0; j < PlottingSettings.listOfAgentsVariableInstances.size();j++){
										
										if(PlottingSettings.listOfAgentsVariableInstances.get(j).agentName.equals(timeSeriesElements.get(i).elementBelongsTo)){
											
											for(int k=0; k<PlottingSettings.listOfAgentsVariableInstances.get(j).listOfVariableInstances.size();k++){
												
												if(PlottingSettings.listOfAgentsVariableInstances.get(j).listOfVariableInstances.get(k).instanceName.equals(timeSeriesElements.get(i).elementName)){
													
													PlottingSettings.listOfAgentsVariableInstances.get(j).listOfVariableInstances.get(k).isSelectedForMultipleTimeSeries = true;
													
													tempMTS.variableInstancesUsedForMultioleTimeSeries.add(PlottingSettings.listOfAgentsVariableInstances.get(j).listOfVariableInstances.get(k));
													
													
													
													
												}
												
												
												
											}
											
											
											
										}
										
									}
									
									
									
								}
								
								
								
							}
							
							
							PlottingSettings.listOfMultipleTimeSeries.add(tempMTS);
							
							removeTimeSeries.setEnabled(true);
							
							addTimeSeriesDialog.dispose();
							addTimeSeriesDialog.setVisible(false);
							
							drawTable();
							
						}else{
							
							JOptionPane.showMessageDialog(null,"At least two time series required!"); 
							
						}

						}
	   						
	   				});
	   				
	   				discardButton.addActionListener(new ActionListener(){

						public void actionPerformed(ActionEvent e) {

							addTimeSeriesDialog.dispose();
							addTimeSeriesDialog.setVisible(false);
							
							drawTable();
							
						}
	   					
	   						
	   				});
					
	   				addTimeSeriesDialog.setVisible(true);
				
					
				}
		
				
			});
		
		
		
		
		
		editTimeSeries = new JMenuItem("Edit Time Series");
		multipleTimeSeries.add(editTimeSeries);
		
		if(PlottingSettings.listOfMultipleTimeSeries.size()==0)
			editTimeSeries.setEnabled(false);
		
		editTimeSeries.addActionListener(new ActionListener(){

			JDialog editMTS;
			JList mtsList;
			DefaultListModel dlmMTS;
			
			JDialog addTimeSeriesDialog;
			GridBagConstraints fil;
			DefaultListModel selectedAgentListModel;
			JList selectedAgentList;
			DefaultListModel variableListModel;
			JList variableList;
			DefaultListModel multipleTimeSeriesModel;
			JList multipleTimeSeries;
			JButton okayButton;
			String tempAgentType;
			
			int indexOfEditedMTS;
			
			ArrayList <TimeSeriesElement> timeSeriesElements;
			
			
			PlottingSettings.MultipleTimeSeries oldMTS;
		
			public void actionPerformed(ActionEvent arg0) {
				
				
				editMTS = new JDialog();
				editMTS.setSize(400,400);
				editMTS.setLayout(new GridBagLayout());
				
				GridBagConstraints fA = new GridBagConstraints();
				fA.insets = new Insets( 5, 5, 5, 5);
				
				JLabel label1 = new JLabel("Select Multiple Time Series for Editing");
				fA.gridx =0;fA.gridy =0;
				editMTS.add(label1,fA);
				
				dlmMTS = new DefaultListModel();
				
				mtsList = new JList(dlmMTS){
					
					public String getToolTipText(MouseEvent evt) {
		   		        // Get item index
					int index = locationToIndex(evt.getPoint());

		   		     
	   		        
	   		        String tooltip =dlmMTS.getElementAt(index).toString();
	   		   	
		   	   		 

	   		        // Return the tool tip text
	   		        return tooltip;
					}
					
				};
				
				for(int i=0; i<PlottingSettings.listOfMultipleTimeSeries.size();i++){

						dlmMTS.addElement(PlottingSettings.listOfMultipleTimeSeries.get(i).timeSeriesName);
	
				}
				
				mtsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				mtsList.setVisibleRowCount(6);
				
				mtsList.addMouseListener(new MouseListener(){
					
					public void mouseClicked(MouseEvent e) {

						System.out.println("One Click in row");
						
						if(e.getClickCount()==2){
							
							System.out.println("Double Click in row");
							
							
							timeSeriesElements = new ArrayList <TimeSeriesElement>();
							
				   			
				   			selectedAgentListModel = new DefaultListModel();
				   			selectedAgentList = new JList(selectedAgentListModel);
				   			
				   			selectedAgentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					   			
				   			variableListModel = new DefaultListModel();
				   			variableList = new JList(variableListModel);
				   			
				   			variableList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
				   			
				   			multipleTimeSeriesModel = new DefaultListModel();
				   			multipleTimeSeries = new JList(multipleTimeSeriesModel);
							

							for(int i=0; i<PlottingSettings.listOfMultipleTimeSeries.size();i++ ){
								
								if(PlottingSettings.listOfMultipleTimeSeries.get(i).timeSeriesName.equals(mtsList.getModel().getElementAt(mtsList.locationToIndex(e.getPoint())))){
									
									
									indexOfEditedMTS =i;
							
									oldMTS = PlottingSettings.listOfMultipleTimeSeries.get(i).clone();
									
									for	(int j=0; j<PlottingSettings.listOfMultipleTimeSeries.get(i).variableInstancesUsedForMultioleTimeSeries.size();j++){
										
										
										timeSeriesElements.add(new TimeSeriesElement( PlottingSettings.listOfMultipleTimeSeries.get(i).variableInstancesUsedForMultioleTimeSeries.get(j).instanceName,
												PlottingSettings.listOfMultipleTimeSeries.get(i).variableInstancesUsedForMultioleTimeSeries.get(j).agentName));
										
										multipleTimeSeriesModel.addElement(PlottingSettings.listOfMultipleTimeSeries.get(i).variableInstancesUsedForMultioleTimeSeries.get(j).instanceName);
										
									}
									
									for	(int j=0; j<PlottingSettings.listOfMultipleTimeSeries.get(i).ratiosUsedForMultioleTimeSeries.size();j++){
										
										timeSeriesElements.add(new TimeSeriesElement( PlottingSettings.listOfMultipleTimeSeries.get(i).ratiosUsedForMultioleTimeSeries.get(j).ratioInstanceName,
												"Ratio"));
										
									}
									
									
									
								}
							
							}
								
								
								addTimeSeriesDialog = new JDialog();
								addTimeSeriesDialog.setModal(true);
									
								addTimeSeriesDialog.setSize(850,450);
								addTimeSeriesDialog.setBackground(Color.white);
						   			
									
								addTimeSeriesDialog.setTitle("Edit Multiple Time Series");
								addTimeSeriesDialog.setLayout(new GridBagLayout());
								fil = new GridBagConstraints();
					   			fil.insets = new Insets( 5, 5, 5, 5);
						   			
						   			
				
						   			
					   			/*Add selected agents*/
				   				for(int i=0; i<PlottingSettings.listOfAgentsVariableInstances.size();i++){
									
									if(PlottingSettings.listOfAgentsVariableInstances.get(i).isSelected){
										
										selectedAgentListModel.addElement(PlottingSettings.listOfAgentsVariableInstances.get(i).agentName);
										
									}
								}
				   				
				   				/*If ratios are selected in the variable list add ratios*/
								if(PlottingSettings.listOfRatioInstances.size()>0){
									
									selectedAgentListModel.addElement("Ratios");
								}
					   				
					   				
					   			selectedAgentList.addMouseListener(new MouseListener(){
										
										public void mouseClicked(MouseEvent e) {

											System.out.println("One Click in row");
											

											if(e.getClickCount()==2){
												
												System.out.println("Double Click in row");
												
												if(selectedAgentList.getModel().getElementAt(selectedAgentList.locationToIndex(e.getPoint())).equals("Ratios")){
													
													tempAgentType = "Ratios";
													variableListModel.removeAllElements();
													
													for(int i=0; i<PlottingSettings.listOfRatioInstances.size();i++){
														
														boolean alreadySelected = false; 
														
														for(int j=0; j < timeSeriesElements.size();j++)
														{
															if(timeSeriesElements.get(j).elementName.equals(PlottingSettings.listOfRatioInstances.get(i).ratioInstanceName)){
																
																alreadySelected = true;
																break;
																
															}
														}
														if(!alreadySelected)
															variableListModel.addElement(PlottingSettings.listOfRatioInstances.get(i).ratioInstanceName);
														
													}
													
												}else
												{
												
													for(int i=0; i<PlottingSettings.listOfAgentsVariableInstances.size();i++){
														
														if(PlottingSettings.listOfAgentsVariableInstances.get(i).agentName.equals(selectedAgentList.getModel().getElementAt(selectedAgentList.locationToIndex(e.getPoint())))){
															 //Create the tab pages for the agent that is selected for plotting
															
															tempAgentType = PlottingSettings.listOfAgentsVariableInstances.get(i).agentName;
															
															variableListModel.removeAllElements();
															
															for(int j=0; j < PlottingSettings.listOfAgentsVariableInstances.get(i).listOfVariableInstances.size(); j++){

																		
																		boolean alreadySelected = false;
																		
																		for(int l=0; l < timeSeriesElements.size();l++)
																		{
																			if(timeSeriesElements.get(l).elementName.equals(PlottingSettings.listOfAgentsVariableInstances.get(i).listOfVariableInstances.get(j).instanceName)){
																				
																				alreadySelected = true;
																				break;
																				
																			}
																		}
																		if(!alreadySelected)
																			variableListModel.addElement(PlottingSettings.listOfAgentsVariableInstances.get(i).listOfVariableInstances.get(j).instanceName);
																	
																	}
											
															break;	
														}
														
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
					   				

					   				JLabel label1 = new JLabel("Choose Agent:");
					   				
					   				fil.gridx =1;fil.gridy =0;
					   				addTimeSeriesDialog.add(label1,fil);
					  
					  
					   				fil.gridx =1;fil.gridy =1;
					   				JScrollPane agentListScroll = new JScrollPane(selectedAgentList);
					   				agentListScroll.setPreferredSize(new Dimension(75,150));
					   				
					   			
					   				
					   				
					   				addTimeSeriesDialog.add(agentListScroll,fil);
					   				
					   				
					   				JLabel label2 = new JLabel("Choose Variable Instance:");
					   				
					   				fil.gridx =2;fil.gridy =0;
					   				addTimeSeriesDialog.add(label2,fil);
					   			
					   				
					   				fil.gridx =2;fil.gridy =1;
					   				
					   				JScrollPane variableListScroll = new JScrollPane(variableList);
					   				variableListScroll.setPreferredSize(new Dimension(200,150));
					   				
					   				addTimeSeriesDialog.add(variableListScroll,fil);
					   				
					   				fil.gridx =1;fil.gridy =1;
					   				

									JPanel panel3 = new JPanel();
									
									panel3.setLayout(new GridBagLayout());
									GridBagConstraints fil1 = new GridBagConstraints();
						   			fil1.insets = new Insets( 5, 5, 5, 5);
									
						   			JButton add = new JButton("Add >>");
					   				JButton remove = new JButton("<<Remove");
						   			
									fil1.gridx =0;fil1.gridy =0;
									panel3.add(add,fil1);
						
									fil1.gridx =0;fil1.gridy =1;
									panel3.add(remove,fil1);
									
									fil.gridx =3;fil.gridy =1;
									addTimeSeriesDialog.add(panel3,fil);
									
									fil.gridx =4;fil.gridy =0;
									addTimeSeriesDialog.add(new JLabel("Selected Time Series:"),fil);
									
									fil.gridx =4;fil.gridy =1;
									JScrollPane multipleTimeSeriesScroll = new JScrollPane(multipleTimeSeries);
					   				multipleTimeSeriesScroll.setPreferredSize(new Dimension(200,150));
					   				
					   				addTimeSeriesDialog.add(multipleTimeSeriesScroll,fil);
					   				
					   				okayButton = new JButton("OK");
					   				JButton discardButton = new JButton("Discard");
					   				
					   				fil.gridx =5;fil.gridy =2;
					   				addTimeSeriesDialog.add(okayButton,fil);
					   				
					   				fil.gridx =6;fil.gridy =2;
					   				addTimeSeriesDialog.add(discardButton,fil);
					   	
									add.addActionListener(new ActionListener() {

										
										public void actionPerformed(ActionEvent e) {
											
											int [] selectedIndices = variableList.getSelectedIndices();
											for(int i=0; i < selectedIndices.length;i++){
												
												multipleTimeSeriesModel.addElement(variableListModel.get(selectedIndices[i]));

												timeSeriesElements.add(new TimeSeriesElement(variableListModel.get(selectedIndices[i]).toString(),tempAgentType));
												
											}
											for(int i=selectedIndices.length-1;i>=0;i--){
												
												variableListModel.remove(selectedIndices[i]);
												
											}
										}
									});
									/*remove item(s) from the list. Only add them to the variable list if the agent type is identical*/
									remove.addActionListener(new ActionListener() {

										
										public void actionPerformed(ActionEvent e) {
											
											int [] selectedIndices = multipleTimeSeries.getSelectedIndices();
										
											for(int i=0; i < selectedIndices.length;i++){
												
												for(int j= 0; j <timeSeriesElements.size();j++ ){
													
													if(multipleTimeSeriesModel.get(selectedIndices[i]).toString().equals(timeSeriesElements.get(j).elementName)){

														if(timeSeriesElements.get(j).elementBelongsTo.equals(tempAgentType))
															variableListModel.addElement(multipleTimeSeriesModel.get(selectedIndices[i]));
														
														timeSeriesElements.remove(j);
														j--;
													}
													
												}
							
											}
											for(int i=selectedIndices.length-1;i>=0;i--){
												
												multipleTimeSeriesModel.remove(selectedIndices[i]);
												
											}
										}
									});

					   				
					   				okayButton.addActionListener(new ActionListener(){

										public void actionPerformed(ActionEvent e) {
											
											String name = "mts_";
											
											for(int i=0; i< timeSeriesElements.size()-1;i++){
												
												name = name+timeSeriesElements.get(i).elementName+"_"; 
					
											}
											if(timeSeriesElements.size()-1>0){
												
												name = name+timeSeriesElements.get(timeSeriesElements.size()-1).elementName;
												
											}
											
											
											PlottingSettings.MultipleTimeSeries tempMTS = (new PlottingSettings()).new MultipleTimeSeries(name);

											
											for(int i=0; i< timeSeriesElements.size();i++){
												
												if(timeSeriesElements.get(i).elementBelongsTo.equals("Ratio")){
													
													for(int j=0; j<PlottingSettings.listOfRatioInstances.size();j++ ){
														
														if(PlottingSettings.listOfRatioInstances.get(j).ratioInstanceName.equals(timeSeriesElements.get(i).elementName)){
															
															PlottingSettings.listOfRatioInstances.get(j).isSelectedForMultipleTimeSeries = true;
															
															tempMTS.ratiosUsedForMultioleTimeSeries.add(PlottingSettings.listOfRatioInstances.get(j));
															
														}
														
														
													}
													
													
													
												}else{
													
													for(int j=0; j < PlottingSettings.listOfAgentsVariableInstances.size();j++){
														
														if(PlottingSettings.listOfAgentsVariableInstances.get(j).agentName.equals(timeSeriesElements.get(i).elementBelongsTo)){
															
															for(int k=0; k<PlottingSettings.listOfAgentsVariableInstances.get(j).listOfVariableInstances.size();k++){
																
																if(PlottingSettings.listOfAgentsVariableInstances.get(j).listOfVariableInstances.get(k).instanceName.equals(timeSeriesElements.get(i).elementName)){
																	
																	PlottingSettings.listOfAgentsVariableInstances.get(j).listOfVariableInstances.get(k).isSelectedForMultipleTimeSeries = true;
																	tempMTS.variableInstancesUsedForMultioleTimeSeries.add(PlottingSettings.listOfAgentsVariableInstances.get(j).listOfVariableInstances.get(k));
																	
																	
																	
																	
																}
																
																
																
															}
															
															
															
														}
														
													}
													
													
													
												}
												
												
												
											}
											
											
											
											
												try{
													
													PlottingSettings.listOfMultipleTimeSeries.remove(indexOfEditedMTS);
													
												}catch(Exception ex){
													
													System.out.println(ex);
													
												}
												
												
												
												/*Compare old and new MTS and reset the deleted ts if they are not uses somewhere else*/
												
												
												for(int i=0; i < oldMTS.variableInstancesUsedForMultioleTimeSeries.size();i++){
													
													boolean found = false;
													
													for(int j =0; j <tempMTS.variableInstancesUsedForMultioleTimeSeries.size();j++){
														
														if(oldMTS.variableInstancesUsedForMultioleTimeSeries.get(i).agentName.equals(tempMTS.variableInstancesUsedForMultioleTimeSeries.get(j).agentName) && 
																oldMTS.variableInstancesUsedForMultioleTimeSeries.get(i).instanceName.equals(tempMTS.variableInstancesUsedForMultioleTimeSeries.get(j).instanceName)){
															
															found = true;
															break;
											
														}
													}
														
													if(!found){
														
														
														boolean usedElsewhere = false;
														
														
														for(int j=0; j < PlottingSettings.listOfMultipleTimeSeries.size();j++){
												
															if(!usedElsewhere){	
																for(int l=0; l < PlottingSettings.listOfMultipleTimeSeries.get(j).variableInstancesUsedForMultioleTimeSeries.size();l++ ){
																	
																	
																	if(oldMTS.variableInstancesUsedForMultioleTimeSeries.get(i).agentName.equals(PlottingSettings.listOfMultipleTimeSeries.get(j).variableInstancesUsedForMultioleTimeSeries.get(l).agentName) && 
																			oldMTS.variableInstancesUsedForMultioleTimeSeries.get(i).instanceName.equals(PlottingSettings.listOfMultipleTimeSeries.get(j).variableInstancesUsedForMultioleTimeSeries.get(l).instanceName)){
																		
																		usedElsewhere = true;
																		break;
														
																	}
																	
																	
																}
																
															
															}
														}
					
														if(!usedElsewhere){
															for (int j=0; j < PlottingSettings.listOfAgentsVariableInstances.size();j++){
														
																if(PlottingSettings.listOfAgentsVariableInstances.get(j).agentName.equals(oldMTS.variableInstancesUsedForMultioleTimeSeries.get(i).agentName)){
															
																	for(int l=0; l < PlottingSettings.listOfAgentsVariableInstances.get(j).listOfVariableInstances.size();l++){
																		
																		if(PlottingSettings.listOfAgentsVariableInstances.get(j).listOfVariableInstances.get(l).instanceName.equals(oldMTS.variableInstancesUsedForMultioleTimeSeries.get(i).instanceName)){
																			
																			
																			PlottingSettings.listOfAgentsVariableInstances.get(j).listOfVariableInstances.get(l).isSelectedForMultipleTimeSeries = false;
																
																		}
																		
																		
																}
																		
															}
															
														}
												
													}
								
												}
						
											}
												
												
												
												
												
												for(int i=0; i < oldMTS.ratiosUsedForMultioleTimeSeries.size();i++){
													
													boolean found = false;
													
													for(int j =0; j <tempMTS.ratiosUsedForMultioleTimeSeries.size();j++){
														
														if(oldMTS.ratiosUsedForMultioleTimeSeries.get(i).ratioInstanceName.equals(tempMTS.ratiosUsedForMultioleTimeSeries.get(j).ratioInstanceName)){
															
															found = true;
															break;
											
														}
													}
														
													if(!found){
														
														
														boolean usedElsewhere = false;
														
														
														for(int j=0; j < PlottingSettings.listOfMultipleTimeSeries.size();j++){
												
															if(!usedElsewhere){	
																for(int l=0; l < PlottingSettings.listOfMultipleTimeSeries.get(j).ratiosUsedForMultioleTimeSeries.size();l++ ){
																	
																	
																	if(oldMTS.ratiosUsedForMultioleTimeSeries.get(i).ratioInstanceName.equals(PlottingSettings.listOfMultipleTimeSeries.get(j).ratiosUsedForMultioleTimeSeries.get(l).ratioInstanceName)){
																		
																		usedElsewhere = true;
																		break;
														
																	}
																	
																	
																}
																
															
															}
														}
					
														if(!usedElsewhere){
															for (int j=0; j < PlottingSettings.listOfRatioInstances.size();j++){
											
																if(PlottingSettings.listOfRatioInstances.get(j).ratioInstanceName.equals(oldMTS.ratiosUsedForMultioleTimeSeries.get(i).ratioInstanceName)){
																	
																	
																	PlottingSettings.listOfRatioInstances.get(j).isSelectedForMultipleTimeSeries = false;
														
																}
														
														}
												
													}
								
												}
						
											}
												
											
											PlottingSettings.listOfMultipleTimeSeries.add(tempMTS);
											
											removeTimeSeries.setEnabled(true);
											
											addTimeSeriesDialog.dispose();
											addTimeSeriesDialog.setVisible(false);
											
											editMTS.dispose();
											editMTS.setVisible(false);
											
											drawTable();
											
										}
					   					
					   						
					   				});
					   				
					   				discardButton.addActionListener(new ActionListener(){

										public void actionPerformed(ActionEvent e) {

											addTimeSeriesDialog.dispose();
											addTimeSeriesDialog.setVisible(false);
											
											
											editMTS.dispose();
											editMTS.setVisible(false);
											
											drawTable();
											
										}
					   					
					   						
					   				});
									
					   				addTimeSeriesDialog.setVisible(true);
								
									
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
				
				
				JScrollPane jsp = new JScrollPane(mtsList);
				
				jsp.setPreferredSize(new Dimension(300,300));
				
				editMTS.getContentPane().add(jsp,fA);
				
				editMTS.setModal(true);
				editMTS.setVisible(true);
				
				
				
			}
			
			
			
			
			
		});
		
		
		removeTimeSeries = new JMenuItem("Remove Time Series");
		multipleTimeSeries.add(removeTimeSeries);
		
		if(PlottingSettings.listOfMultipleTimeSeries.size()==0)
			removeTimeSeries.setEnabled(false);
		
		
		removeTimeSeries.addActionListener(new ActionListener(){

			JDialog removeMTS;
			JList mtsList;
			DefaultListModel dlmMTS;
			
			PlottingSettings.MultipleTimeSeries oldMTS;
		
			public void actionPerformed(ActionEvent arg0) {
			
				
				removeMTS = new JDialog();
				removeMTS.setSize(400,400);
				removeMTS.setLayout(new GridBagLayout());
				
				GridBagConstraints fA = new GridBagConstraints();
				fA.insets = new Insets( 5, 5, 5, 5);
				
				JLabel label1 = new JLabel("Select Multiple Time Series for Deleting");
				fA.gridx =0;fA.gridy =0;
				removeMTS.add(label1,fA);
				
				dlmMTS = new DefaultListModel();
				
				mtsList = new JList(dlmMTS){
					
					public String getToolTipText(MouseEvent evt) {
		
		   		        return getModel().getElementAt( locationToIndex(evt.getPoint())).toString();
		   		    }
					
					
					
				};
				
				for(int i=0; i<PlottingSettings.listOfMultipleTimeSeries.size();i++){

						dlmMTS.addElement(PlottingSettings.listOfMultipleTimeSeries.get(i).timeSeriesName);
	
				}
				
				mtsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				mtsList.setVisibleRowCount(6);
				
				mtsList.addMouseListener(new MouseListener(){
					
					public void mouseClicked(MouseEvent e) {

						System.out.println("One Click in row");
						
						if(e.getClickCount()==2){
							
							System.out.println("Double Click in row");
							
							for(int i=0; i<PlottingSettings.listOfMultipleTimeSeries.size();i++){
								
								if(PlottingSettings.listOfMultipleTimeSeries.get(i).timeSeriesName.equals(mtsList.getModel().getElementAt(mtsList.locationToIndex(e.getPoint())))){
									 //Create the tab pages for the agent that is selected for plotting
									
									
									oldMTS = PlottingSettings.listOfMultipleTimeSeries.get(i).clone();
									
									
									PlottingSettings.listOfMultipleTimeSeries.remove(i);
									
									removeMTS.dispose();
									removeMTS.setVisible(false);
									
									
									
									
									for(int j=0; j < oldMTS.variableInstancesUsedForMultioleTimeSeries.size();j++){
										
									
										
											boolean usedElsewhere = false;
											
											
											for(int k=0; k < PlottingSettings.listOfMultipleTimeSeries.size();k++){
									
												if(!usedElsewhere){	
													for(int l=0; l < PlottingSettings.listOfMultipleTimeSeries.get(k).variableInstancesUsedForMultioleTimeSeries.size();l++ ){
														
														
														if(oldMTS.variableInstancesUsedForMultioleTimeSeries.get(j).agentName.equals(PlottingSettings.listOfMultipleTimeSeries.get(k).variableInstancesUsedForMultioleTimeSeries.get(l).agentName) && 
																oldMTS.variableInstancesUsedForMultioleTimeSeries.get(j).instanceName.equals(PlottingSettings.listOfMultipleTimeSeries.get(k).variableInstancesUsedForMultioleTimeSeries.get(l).instanceName)){
															
															usedElsewhere = true;
															break;
											
														}
														
														
													}
													
												
												}
											}
		
											if(!usedElsewhere){
												for (int k=0; k< PlottingSettings.listOfAgentsVariableInstances.size();k++){
											
													if(PlottingSettings.listOfAgentsVariableInstances.get(k).agentName.equals(oldMTS.variableInstancesUsedForMultioleTimeSeries.get(i).agentName)){
												
														for(int l=0; l < PlottingSettings.listOfAgentsVariableInstances.get(k).listOfVariableInstances.size();l++){
															
															if(PlottingSettings.listOfAgentsVariableInstances.get(k).listOfVariableInstances.get(l).instanceName.equals(oldMTS.variableInstancesUsedForMultioleTimeSeries.get(j).instanceName)){
																
																
																PlottingSettings.listOfAgentsVariableInstances.get(k).listOfVariableInstances.get(l).isSelectedForMultipleTimeSeries = false;
													
															}
															
															
													}
															
												}
												
											}
									
										
					
									}
			
								}
									
									
									
									
									
									for(int j=0; j < oldMTS.ratiosUsedForMultioleTimeSeries.size();j++){
										
										
											
											
											boolean usedElsewhere = false;
											
											
											for(int k=0; k < PlottingSettings.listOfMultipleTimeSeries.size();k++){
									
												if(!usedElsewhere){	
													for(int l=0; l < PlottingSettings.listOfMultipleTimeSeries.get(k).ratiosUsedForMultioleTimeSeries.size();l++ ){
														
														
														if(oldMTS.ratiosUsedForMultioleTimeSeries.get(j).ratioInstanceName.equals(PlottingSettings.listOfMultipleTimeSeries.get(k).ratiosUsedForMultioleTimeSeries.get(l).ratioInstanceName)){
															
															usedElsewhere = true;
															break;
											
														}
														
														
													}
													
												
												}
											}
		
											if(!usedElsewhere){
												for (int k=0; k < PlottingSettings.listOfRatioInstances.size();k++){
								
													if(PlottingSettings.listOfRatioInstances.get(k).ratioInstanceName.equals(oldMTS.ratiosUsedForMultioleTimeSeries.get(j).ratioInstanceName)){
														
														
														PlottingSettings.listOfRatioInstances.get(k).isSelectedForMultipleTimeSeries = false;
											
													}
											
											}
									
										
					
									}
			
								}
									
									
									
									
									
									
									//If all agents are selected for plotting deactivate the addAgent menu item
									if(PlottingSettings.listOfMultipleTimeSeries.size()== 0){
										removeTimeSeries.setEnabled(false);
									}
									
									removeMTS.dispose();
									removeMTS.setVisible(false);
									
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
				
				
				JScrollPane scPa = new JScrollPane(mtsList);
				
				scPa.setPreferredSize(new Dimension(300,300));
				
				removeMTS.getContentPane().add(scPa,fA);
				
				removeMTS.setModal(true);
				removeMTS.setVisible(true);
				
				
				
			}
			
			
			
			
			
		});
		
		
		
		menuBar.add(multipleTimeSeries);
		
		
		menuSettings = new JMenu("Settings");

		
		JMenuItem defaultMenuItem = new JMenuItem("Default Settings");
		
		
		menuSettings.add(defaultMenuItem);
		
		defaultMenuItem.addActionListener(new ActionListener(){

			JTextField textfield1, textfield2, textfield3, textfield4;
			JCheckBox CBLowerBound, CBUpperBound;
			JDialog defaultSettingsDialog;

			public void actionPerformed(ActionEvent arg0) {
		

				defaultSettingsDialog = new JDialog();
				defaultSettingsDialog.setTitle("Default Settings");
				defaultSettingsDialog.setLayout(new FlowLayout());
				defaultSettingsDialog.setSize(500,250);
				defaultSettingsDialog.setBackground(Color.white);
				defaultSettingsDialog.setModal(true);
				
				defaultSettingsDialog.setLayout(new GridBagLayout());
	   			final GridBagConstraints enterDS= new GridBagConstraints();
	   			enterDS.insets = new Insets( 5, 5, 5, 5);
				
				
	   			/*tempDefaults*/
	   			
	   			JLabel label1 = new JLabel("Tmin");
	   			textfield1 = new JTextField(5);
	   			textfield1.setText(Integer.toString(PlottingSettings.defaultsMultipleTimeSeries.tmin));
	   			
	   			enterDS.gridx=0; enterDS.gridy=0;
	   			defaultSettingsDialog.add(label1,enterDS);
	   			
	   			enterDS.gridx=1; enterDS.gridy=0;
	   			defaultSettingsDialog.add(textfield1,enterDS);
	   			
	   			
	   		 /*Add ActionListener*/
	   			textfield1.addActionListener(new ActionListener(){
	  	    		
	  	    		public void actionPerformed(ActionEvent evt) {
	  	    		    
	  	    			String input = textfield1.getText();

	  	    			try {
	  	    				int integer = Integer.parseInt(input);
		    			    System.out.println("NumBatch runs: "+SimulationSettings.numBatchRuns);
		    			    textfield1.selectAll();
	  	    			}
	  	    			catch(NumberFormatException nFE) {
	  	    				JOptionPane.showMessageDialog(null,"Not an integer"); 
	  	    			}
	  	    			
	  	    		}
	  	    		
	  	    	});
	   			
	   			
	   			
	   			JLabel label2 = new JLabel("Tmax");
	   			textfield2 = new JTextField(5);
	   			textfield2.setText(Integer.toString(PlottingSettings.defaultsMultipleTimeSeries.tmax));
	   			
	   			enterDS.gridx=0; enterDS.gridy=1;
	   			defaultSettingsDialog.add(label2,enterDS);
	   			
	   			enterDS.gridx=1; enterDS.gridy=1;
	   			defaultSettingsDialog.add(textfield2,enterDS);
	   			
	   			
	   		 /*Add ActionListener*/
	   			textfield2.addActionListener(new ActionListener(){
	  	    		
	  	    		public void actionPerformed(ActionEvent evt) {
	  	    		    
	  	    			String input = textfield2.getText();
	  	    		
	  	    			try {
	  	    				int integer = Integer.parseInt(input);
		    			    System.out.println("NumBatch runs: "+SimulationSettings.numBatchRuns);
		    			    textfield2.selectAll();
	  	    			}
	  	    			catch(NumberFormatException nFE) {
	  	    				JOptionPane.showMessageDialog(null,"Not an integer"); 
	  	    			}
	  	    			
	  	    		}
	  	    		
	  	    	});
	   			

	   			
	   			JButton okay = new JButton("OK");
	   			JButton discard = new JButton("Discard");
	   			
	   			enterDS.gridx=2; enterDS.gridy=2;
	   			defaultSettingsDialog.add(okay,enterDS);
	   			
	   			enterDS.gridx=3; enterDS.gridy=2;
	   			defaultSettingsDialog.add(discard,enterDS);
	   			
	   			okay.addActionListener(new ActionListener(){
	  	    		
	  	    		public void actionPerformed(ActionEvent evt) {
	  	    			
	  	    			
	  	    			try {
	  	    				PlottingSettings.defaultsMultipleTimeSeries.tmin = Integer.parseInt(textfield1.getText());
	  	    				PlottingSettings.defaultsMultipleTimeSeries.tmax = Integer.parseInt(textfield2.getText());
	  	    				
	  	    				
	  	    			/*	for(int i=0; i < PlottingSettings.listOfMultipleTimeSeries.size();i++){
	  	    					
	  	    					PlottingSettings.listOfMultipleTimeSeries.get(i).tmin = PlottingSettings.defaultsMultipleTimeSeries.tmin;
	  	    					PlottingSettings.listOfMultipleTimeSeries.get(i).tmax = PlottingSettings.defaultsMultipleTimeSeries.tmax;
	  	    				}*/
	  	    				
	  	    				
	  	    				drawTable();
	  	    				
	  	    				defaultSettingsDialog.dispose();
		  	    			defaultSettingsDialog.setVisible(false);
		  	    			
		  	    		
	  	    			}
	  	    			catch(NumberFormatException nFE) {
	  	    				JOptionPane.showMessageDialog(null,"Not an integer!"); 
	  	    				
	  	    	
	  	    			}

	  	    		}
	  	    		
	  	    	});
	   			
	   			discard.addActionListener(new ActionListener(){
	  	    		
	  	    		public void actionPerformed(ActionEvent evt) {
	  	    		
	  	    			defaultSettingsDialog.dispose();
	  	    			defaultSettingsDialog.setVisible(false);
	  	    			
	  	    		}
	  	    		
	  	    	});
	   			
	   			
	   			defaultSettingsDialog.setVisible(true);
				
			}
			
			
			
			
		});
		
		
		menuBar.add(menuSettings);
		
		
		  
		add(menuBar, BorderLayout.NORTH);
		
		scrollMultipleTimeSeriesTable = new  JScrollPane();
		
		drawTable();
		

		setVisible(true);
		
	}
	
	
	
	private void drawTable(){
		
		/*Redraw the table*/
		container.remove(scrollMultipleTimeSeriesTable);
		container.validate();
		

		
		multipleTimeSeriesTable = new  MultipleTimeSeriesTable();
		
		scrollMultipleTimeSeriesTable = multipleTimeSeriesTable.listScrollVariables;
		scrollMultipleTimeSeriesTable.setPreferredSize(new Dimension(700, 400)); 
		ts.gridx=0; 
		ts.gridy=1;
		container.add(scrollMultipleTimeSeriesTable,ts);
		container.validate();
		add(container);

		validate();
		
		
	}
	
	
	
	private class MultipleTimeSeriesTable extends JScrollPane{
		
		
		MultipleTimeSeriesTableModel multipleTimeSeriesTableModel;
		JTable multipleTimeSeriesTable;
		
		JScrollPane listScrollVariables;  
		

		MultipleTimeSeriesTable(){
			

			
			String[] colHeadersT2 = {"Multiple Time Series Name" ,"Tmin","Tmax", "Components" , "Lower Limit", "", "Upper Limit","" };
			
	
			
	
			
			
			multipleTimeSeriesTableModel = new MultipleTimeSeriesTableModel(colHeadersT2 );
			multipleTimeSeriesTable = new JTable(multipleTimeSeriesTableModel);

			
			multipleTimeSeriesTable.setRowHeight(20);
			
			multipleTimeSeriesTable.getColumnModel().getColumn(0).setCellEditor(new CellEditor());
			multipleTimeSeriesTable.getColumnModel().getColumn(0).getCellEditor().addCellEditorListener(new EditorListener(){
				 
				 
				 public void editingStopped(ChangeEvent e) {
					 
					 System.out.println("Col 2 editing stopped");
					 
					 int editedrow = ((CellEditor) multipleTimeSeriesTable.getColumnModel().getColumn(0).getCellEditor()).getRow();
		    
		    	      for(int i=0; i<PlottingSettings.listOfMultipleTimeSeries.size();i++){
							 
							 if(PlottingSettings.listOfMultipleTimeSeries.get(i).timeSeriesName.equals(multipleTimeSeriesTableModel.getValueAt(editedrow, 0))){
								 
						
							 
								 PlottingSettings.listOfMultipleTimeSeries.get(i).timeSeriesName = multipleTimeSeriesTable.getColumnModel().getColumn(0).getCellEditor().getCellEditorValue().toString();
										
					 
									 break;
							 }
					 
							 }
				 
		    	      
		    	      multipleTimeSeriesTableModel.changeValueAt(multipleTimeSeriesTable.getColumnModel().getColumn(0).getCellEditor().getCellEditorValue().toString(), editedrow, 0);
					 
				 }
				 
				 
			});
			

/*Column 2*/
			
			
			multipleTimeSeriesTable.getColumnModel().getColumn(1).setPreferredWidth(140);
			multipleTimeSeriesTable.getColumnModel().getColumn(1).setCellEditor(new CellEditor());
			
			
			
			multipleTimeSeriesTable.getColumnModel().getColumn(1).getCellEditor().addCellEditorListener(new EditorListener(){
				 
				 
				 public void editingStopped(ChangeEvent e) {
					 
					 System.out.println("Col 2 editing stopped");
					 
					 int editedrow = ((CellEditor) multipleTimeSeriesTable.getColumnModel().getColumn(1).getCellEditor()).getRow();
		    
					 for(int i=0; i<PlottingSettings.listOfMultipleTimeSeries.size();i++){
			    	    	
							 if(PlottingSettings.listOfMultipleTimeSeries.get(i).timeSeriesName.equals(multipleTimeSeriesTableModel.getValueAt(editedrow, 0)) )
							 {
								 PlottingSettings.listOfMultipleTimeSeries.get(i).tmin = Integer.parseInt(multipleTimeSeriesTable.getColumnModel().getColumn(1).getCellEditor().getCellEditorValue().toString());
								 break;
								 
							 }
			
					 }
		    	      
					 multipleTimeSeriesTableModel.changeValueAt(multipleTimeSeriesTable.getColumnModel().getColumn(1).getCellEditor().getCellEditorValue().toString(), editedrow, 1);
					 
				 }
				 
				 
			});
			
			/*Column 3*/
			

			multipleTimeSeriesTable.getColumnModel().getColumn(2).setPreferredWidth(140);
			multipleTimeSeriesTable.getColumnModel().getColumn(2).setCellEditor(new CellEditor());
			
			
			
			multipleTimeSeriesTable.getColumnModel().getColumn(2).getCellEditor().addCellEditorListener(new EditorListener(){
				 
				 
				 public void editingStopped(ChangeEvent e) {
					 
					 System.out.println("Col 2 editing stopped");
					 
					 int editedrow = ((CellEditor) multipleTimeSeriesTable.getColumnModel().getColumn(2).getCellEditor()).getRow();
		    
					 for(int i=0; i<PlottingSettings.listOfMultipleTimeSeries.size();i++){
			    	    	
					    	
						
							 
							 if(PlottingSettings.listOfMultipleTimeSeries.get(i).timeSeriesName.equals(multipleTimeSeriesTableModel.getValueAt(editedrow, 0)) )
							 {
								 PlottingSettings.listOfMultipleTimeSeries.get(i).tmax = Integer.parseInt(multipleTimeSeriesTable.getColumnModel().getColumn(2).getCellEditor().getCellEditorValue().toString());
								 break;
								 
							 }
							 
							 
						 
	  	  
					 }
		    	      
					 multipleTimeSeriesTableModel.changeValueAt(multipleTimeSeriesTable.getColumnModel().getColumn(2).getCellEditor().getCellEditorValue().toString(), editedrow, 2);
					 
				 }
				 
				 
			});

			
			
			/*Column 4: Partitioning*/
			
			
			
			multipleTimeSeriesTable.getColumnModel().getColumn(3).setPreferredWidth(140);
			multipleTimeSeriesTable.getColumnModel().getColumn(3).setCellEditor(new CellEditor());
		
			
			multipleTimeSeriesTable.getColumnModel().getColumn(3).setCellRenderer(new SpecificComboBoxRenderer());
			multipleTimeSeriesTable.getColumnModel().getColumn(3).setCellEditor(new SpecificComboBoxEditor());
		    	      
			
				
			
			
			
			
			/*Column 4*/
			
			multipleTimeSeriesTable.getColumnModel().getColumn(4).setPreferredWidth(140);
			multipleTimeSeriesTable.getColumnModel().getColumn(4).setCellEditor(new CellEditor());
			
			
			
			multipleTimeSeriesTable.getColumnModel().getColumn(4).getCellEditor().addCellEditorListener(new EditorListener(){
				 
				 
				 public void editingStopped(ChangeEvent e) {
					 
					 System.out.println("Col 2 editing stopped");
					 
					 int editedrow = ((CellEditor) multipleTimeSeriesTable.getColumnModel().getColumn(4).getCellEditor()).getRow();
		    
					 for(int i=0; i<PlottingSettings.listOfMultipleTimeSeries.size();i++){
			    	    	
					    	
						 if(PlottingSettings.listOfMultipleTimeSeries.get(i).timeSeriesName.equals(multipleTimeSeriesTableModel.getValueAt(editedrow, 0)) )
						 {
							
							 
						
								 try{
								 PlottingSettings.listOfMultipleTimeSeries.get(i).lowerBound = Double.parseDouble(multipleTimeSeriesTable.getColumnModel().getColumn(4).getCellEditor().getCellEditorValue().toString());
								 }catch(Exception ex){
									 
									 
									 JOptionPane.showMessageDialog(null,"Parameter must be a numeric expression!");
									 
								 }
								 
						 }
						 
						 
	  	  
					 }
		    	      
		    	      multipleTimeSeriesTableModel.changeValueAt(multipleTimeSeriesTable.getColumnModel().getColumn(3).getCellEditor().getCellEditorValue().toString(), editedrow, 4);
					 
				 }
				 
				 
			});
			
			
			/*Column 5 Check box*/
			

			 final JCheckBox check = new JCheckBox();
			 
			 multipleTimeSeriesTable.getColumnModel().getColumn(5).setPreferredWidth(40);
			
			 multipleTimeSeriesTable.getColumnModel().getColumn(5).setCellEditor(new JCheckBoxCellEditor(check));
			 multipleTimeSeriesTable.getColumnModel().getColumn(5).getCellEditor().getClass();
			 multipleTimeSeriesTable.getColumnModel().getColumn(5).getCellEditor().addCellEditorListener(new EditorListener(){
				 
				 
				 public void editingStopped(ChangeEvent e) {
					 
					 
					 int editedrow = ((JCheckBoxCellEditor) multipleTimeSeriesTable.getColumnModel().getColumn(5).getCellEditor()).getRow();
					 
					 if( ((JCheckBoxCellEditor) multipleTimeSeriesTable.getColumnModel().getColumn(5).getCellEditor()).getCellEditorValue().equals(true))
					 {
						 /*Set the check box*/
						 multipleTimeSeriesTableModel.changeValueAt("true",editedrow,5 );

						 for(int i=0; i<PlottingSettings.listOfMultipleTimeSeries.size();i++){
				    	    	
						    	
					
								 
								 if(PlottingSettings.listOfMultipleTimeSeries.get(i).timeSeriesName.equals(multipleTimeSeriesTableModel.getValueAt(editedrow, 0)))
								 {
									 PlottingSettings.listOfMultipleTimeSeries.get(i).lowerBoundEnabled = true;
									 break;
									 
								 }
								 
								 
							 
		  	  
						 }
						 
						 multipleTimeSeriesTableModel.updatetable();
				
						 
						 
					 }else{
						 /*Set the check box*/
						 multipleTimeSeriesTableModel.changeValueAt("false",((JCheckBoxCellEditor) multipleTimeSeriesTable.getColumnModel().getColumn(5).getCellEditor()).getRow(),5 );

						 for(int i=0; i<PlottingSettings.listOfMultipleTimeSeries.size();i++){
				    	    	
						    	
						
								 
								 if(PlottingSettings.listOfMultipleTimeSeries.get(i).timeSeriesName.equals(multipleTimeSeriesTableModel.getValueAt(editedrow, 0)))
								 {
									 PlottingSettings.listOfMultipleTimeSeries.get(i).lowerBoundEnabled = false;
									 break;
									 
								 }
								 
								 
							 
		  	  
						 }
						 
					
						
					 }
					 
				    }
				 
				 
				 
			 });
			 
			 
			 multipleTimeSeriesTable.getColumnModel().getColumn(5).setCellRenderer(new DefaultTableCellRenderer() {
				 public Component getTableCellRendererComponent(JTable table,
				 Object value, boolean isSelected, boolean hasFocus, int row, int
				 column) {
					 
					 System.out.println(value);
				 check.setSelected(((Boolean)value).booleanValue()) ;
				 return check;
				 }
				 
				 
				 
				 });
			 
			
			
			
			
	/*Column 6*/
			
			 multipleTimeSeriesTable.getColumnModel().getColumn(6).setPreferredWidth(140);
			 multipleTimeSeriesTable.getColumnModel().getColumn(6).setCellEditor(new CellEditor());
			
			
			
			 multipleTimeSeriesTable.getColumnModel().getColumn(6).getCellEditor().addCellEditorListener(new EditorListener(){
				 
				 
				 public void editingStopped(ChangeEvent e) {
					 
					 System.out.println("Col 2 editing stopped");
					 
					 int editedrow = ((CellEditor) multipleTimeSeriesTable.getColumnModel().getColumn(6).getCellEditor()).getRow();
		    
					 for(int i=0; i<PlottingSettings.listOfMultipleTimeSeries.size();i++){
			    	    	
					    	
					
							 
							 if(PlottingSettings.listOfMultipleTimeSeries.get(i).timeSeriesName.equals(multipleTimeSeriesTableModel.getValueAt(editedrow, 0)) )
							 {
								 try{
								 PlottingSettings.listOfMultipleTimeSeries.get(i).upperBound = Double.parseDouble(multipleTimeSeriesTable.getColumnModel().getColumn(6).getCellEditor().getCellEditorValue().toString());
								 }catch(Exception ex){
									 
									 JOptionPane.showMessageDialog(null,"Parameter must be a numeric expression!");
									 
								 }
								 
								 break;
								 
							 }
							 
							 
						 
	  	  
					 }
		    	      
		    	      multipleTimeSeriesTableModel.changeValueAt(multipleTimeSeriesTable.getColumnModel().getColumn(6).getCellEditor().getCellEditorValue().toString(), editedrow, 3);
					 
				 }
				 
				 
			});
			
			
			/*Column 7 Check box*/
			

			 final JCheckBox check2 = new JCheckBox();
			 
			multipleTimeSeriesTable.getColumnModel().getColumn(7).setPreferredWidth(40);
			
			multipleTimeSeriesTable.getColumnModel().getColumn(7).setCellEditor(new JCheckBoxCellEditor(check2));
			multipleTimeSeriesTable.getColumnModel().getColumn(7).getCellEditor().getClass();
			multipleTimeSeriesTable.getColumnModel().getColumn(7).getCellEditor().addCellEditorListener(new EditorListener(){
				 
				 
				 public void editingStopped(ChangeEvent e) {
					 
					 
					 int editedrow = ((JCheckBoxCellEditor) multipleTimeSeriesTable.getColumnModel().getColumn(7).getCellEditor()).getRow();
					 
					 if( ((JCheckBoxCellEditor) multipleTimeSeriesTable.getColumnModel().getColumn(7).getCellEditor()).getCellEditorValue().equals(true))
					 {
						 /*Set the check box*/
						 multipleTimeSeriesTableModel.changeValueAt("true",editedrow,7 );

						 for(int i=0; i<PlottingSettings.listOfMultipleTimeSeries.size();i++){
				    	    	
						    	
							
								 
								 if(PlottingSettings.listOfMultipleTimeSeries.get(i).timeSeriesName.equals(multipleTimeSeriesTableModel.getValueAt(editedrow, 0)) )
								 {
									 PlottingSettings.listOfMultipleTimeSeries.get(i).upperBoundEnabled = true;
									 break;
									 
								 }
								 
								 
							 
		  	  
						 }
						 
						 multipleTimeSeriesTableModel.updatetable();
				
						 
						 
					 }else{
						 /*Set the check box*/
						 multipleTimeSeriesTableModel.changeValueAt("false",((JCheckBoxCellEditor) multipleTimeSeriesTable.getColumnModel().getColumn(7).getCellEditor()).getRow(),7 );

						 for(int i=0; i<PlottingSettings.listOfMultipleTimeSeries.size();i++){
				    	    	
						    	
							
								 
								 if(PlottingSettings.listOfMultipleTimeSeries.get(i).timeSeriesName.equals(multipleTimeSeriesTableModel.getValueAt(editedrow, 0)) )
								 {
									 PlottingSettings.listOfMultipleTimeSeries.get(i).upperBoundEnabled = false;
									 break;
									 
								 }
								 
								 
							 
		  	  
						 }
						 
						 multipleTimeSeriesTableModel.updatetable();
						
					 }
					 
				    }
				 
				 
				 
			 });
			 
			 
			multipleTimeSeriesTable.getColumnModel().getColumn(7).setCellRenderer(new DefaultTableCellRenderer() {
				 public Component getTableCellRendererComponent(JTable table,
				 Object value, boolean isSelected, boolean hasFocus, int row, int
				 column) {
					 
					 System.out.println(value);
				 check2.setSelected(((Boolean)value).booleanValue()) ;
				 return check2;
				 }
				 
				 
				 
				 });
			
			
			

			
			
		
			
			
			multipleTimeSeriesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	
			
			
			System.out.println("draw table");
			multipleTimeSeriesTableModel.updatetable();
			
			listScrollVariables = new JScrollPane(multipleTimeSeriesTable);
			
			
		}
		
		
		
		public class SpecificComboBoxEditor extends DefaultCellEditor {
			
			int editedRow;
			int editedCol;
			
			JComboBox cComboBox;
			

		    public SpecificComboBoxEditor() {
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
		    	
		    	cComboBox.addItem("List of Time Series");
		    	
	    	
		    	for(int i=0; i<PlottingSettings.listOfMultipleTimeSeries.size();i++){

					 if(PlottingSettings.listOfMultipleTimeSeries.get(i).timeSeriesName.equals(multipleTimeSeriesTableModel.getValueAt(editedRow, 0)) )
					 {
							for(int j=0; j<PlottingSettings.listOfMultipleTimeSeries.get(i).variableInstancesUsedForMultioleTimeSeries.size();j++){
								
								cComboBox.addItem(PlottingSettings.listOfMultipleTimeSeries.get(i).variableInstancesUsedForMultioleTimeSeries.get(j).instanceName);
	
							}
							for(int j=0; j<PlottingSettings.listOfMultipleTimeSeries.get(i).ratiosUsedForMultioleTimeSeries.size();j++){
								
								cComboBox.addItem(PlottingSettings.listOfMultipleTimeSeries.get(i).ratiosUsedForMultioleTimeSeries.get(j).ratioInstanceName);
			
							}

						 break;
						 
					 }

		    	}
		    	
		    	
		    
		        System.out.println("value here "+value);
		        
		       for(int i=0; i<cComboBox.getItemCount();i++ )
		        {
		    	  
		        	if(cComboBox.getItemAt(i).toString().equals(multipleTimeSeriesTableModel.getValueAt(editedRow, editedCol))){

		        		 cComboBox.setSelectedIndex(i);
		        		
		        	}
		        	try{
			        	if(multipleTimeSeriesTableModel.getValueAt(editedRow, editedCol).equals("")){

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
		

		
		
		public class SpecificComboBoxRenderer extends JComboBox implements TableCellRenderer {
			
		    public SpecificComboBoxRenderer() {
		        super();
		    }

		    public Component getTableCellRendererComponent(JTable table, Object value,
		            boolean isSelected, boolean hasFocus, int row, int column) {
		    	
		    	super.addItem("List of Time Series");
		    	super.setEditable(false);
		    	
		     	for(int i=0; i<PlottingSettings.listOfMultipleTimeSeries.size();i++){

					 if(PlottingSettings.listOfMultipleTimeSeries.get(i).timeSeriesName.equals(multipleTimeSeriesTableModel.getValueAt(row, 0)) )
					 {
							for(int j=0; j<PlottingSettings.listOfMultipleTimeSeries.get(i).variableInstancesUsedForMultioleTimeSeries.size();j++){
								
								super.addItem(PlottingSettings.listOfMultipleTimeSeries.get(i).variableInstancesUsedForMultioleTimeSeries.get(j).instanceName);
	
							}
							for(int j=0; j<PlottingSettings.listOfMultipleTimeSeries.get(i).ratiosUsedForMultioleTimeSeries.size();j++){
								
								super.addItem(PlottingSettings.listOfMultipleTimeSeries.get(i).ratiosUsedForMultioleTimeSeries.get(j).ratioInstanceName);
			
							}

						 break;
						 
					 }

		    	}

		     	
		    
		        return this;
		    }
		    
		}
		
		
		
	}
	


public class MultipleTimeSeriesTableModel extends AbstractTableModel{
	
	JCheckBox checkBox = new JCheckBox();
	String [] columnName;
	
	
	
	
	
	int numFilters;
	boolean partitioningSelected , filterSelected;
	
	
	PlottingSettings.Agent variableInstances;
	
	
	MultipleTimeSeriesTableModel(String[] colName){


		columnName = colName;
	
		
	}
	
	
	
	public int getRowCount()
   {
	
	return PlottingSettings.listOfMultipleTimeSeries.size();
	
 }
	
	
   public int getColumnCount()
   {
   return 8;
 }
   public Object getValueAt( int row, int col)
   {
	   
	
	   
	  
		 //  System.out.println("getValueAt timeSeriesSelection.get(i).isntancesOfvariable.size()"+timeSeriesSelection.get(i).isntancesOfvariable.size());
		
				   if(col==0)
					   return PlottingSettings.listOfMultipleTimeSeries.get(row).timeSeriesName;
				   else if(col==1)
					   return PlottingSettings.listOfMultipleTimeSeries.get(row).tmin;
				   else if(col==2)
					   return PlottingSettings.listOfMultipleTimeSeries.get(row).tmax;
				   else if(col==3)
					   return "List of Time Series";
				   else if(col==4){
						if( PlottingSettings.listOfMultipleTimeSeries.get(row).lowerBoundEnabled)
							 return Double.toString(PlottingSettings.listOfMultipleTimeSeries.get(row).lowerBound);
				   		else
				   			return "";
				   }else if(col==5){
					   return PlottingSettings.listOfMultipleTimeSeries.get(row).lowerBoundEnabled;
				   }else if(col==6){
					   if( PlottingSettings.listOfMultipleTimeSeries.get(row).upperBoundEnabled)
							 return Double.toString(PlottingSettings.listOfMultipleTimeSeries.get(row).upperBound);
				   		else
				   			return "";
				   }else if(col==7){
					   return PlottingSettings.listOfMultipleTimeSeries.get(row).upperBoundEnabled;
				   }else{
					   return null;
				   }
				  
			   
			
   }
	   
	   
	   
	   
	   
	   
   
   
   public String getColumnName(int column) {  
       return columnName[column]; 
   }
   
   public Class<?> getColumnClass(int col) {  
	     if (col == 5 || col ==7) {  
	        return Boolean.class;  
	     }  
	     return getValueAt(0, col).getClass();  
	  }  
   
   
   public void changeValueAt(String value, int row, int col) {  
       
	   System.out.println("changeValueAt");
	  

	   if(col==0)
		   PlottingSettings.listOfMultipleTimeSeries.get(row).timeSeriesName=value;
	   if(col==1)
		   PlottingSettings.listOfMultipleTimeSeries.get(row).tmin=Integer.parseInt(value);
	   if(col==2)
		   PlottingSettings.listOfMultipleTimeSeries.get(row).tmax=Integer.parseInt(value);
	   if(col==4){
		   PlottingSettings.listOfMultipleTimeSeries.get(row).lowerBound =   Double.parseDouble(value);;
	   }else if(col==5){
		   if(value.equals("true"))
			   PlottingSettings.listOfMultipleTimeSeries.get(row).lowerBoundEnabled = true;
		   else
			   PlottingSettings.listOfMultipleTimeSeries.get(row).lowerBoundEnabled = false;
	   }else if(col==6){
		   PlottingSettings.listOfMultipleTimeSeries.get(row).upperBound =   Double.parseDouble(value);;
	   }else if(col==7){
		   if(value.equals("true"))
			   PlottingSettings.listOfMultipleTimeSeries.get(row).upperBoundEnabled = true;
		   else
			   PlottingSettings.listOfMultipleTimeSeries.get(row).upperBoundEnabled = false;
		   
	   }

	 
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
      

   		return true;
   	
   }
   

}
   
	

	
	private class TimeSeriesElement {
		
		String elementName;
		String elementBelongsTo;
		
		TimeSeriesElement(String name, String type){
			
			elementName = name;
			elementBelongsTo = type;
		}
		
		
	}
	

}
