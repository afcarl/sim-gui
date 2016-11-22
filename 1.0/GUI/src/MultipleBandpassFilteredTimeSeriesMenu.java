

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


	public class MultipleBandpassFilteredTimeSeriesMenu extends JDialog{
		
		JMenuBar menuBar;
		JMenu multipleTimeSeries, menuSettings;
		JMenuItem addTimeSeries, editTimeSeries ,removeTimeSeries, applyChanges, exit;
		
		ArrayList<PlottingSettings.MultipleBandpassFilteredTimeSeries> tempMultipleTimesSeries;
		ArrayList<PlottingSettings.Agent> tempAgentInstanceVariable;
		ArrayList<PlottingSettings.RatioInstance>  tempRatioInstanceVariable;
		
		JScrollPane scrollMultipleBandpassFilteredTimeSeriesTable;
		MultipleBandpassFilteredTimeSeriesTable multipleTimeSeriesTable;
		GridBagConstraints ts;
		
		boolean tempRatiosSelectedForMultipleBandpassFilteredTimeSeries;
		
		PlottingSettings.DefaultSettingsMultipleBandpassFilteredTimeSeries tempDefaults;

		MultipleBandpassFilteredTimeSeriesMenu(){
			
			//agentsReturn = agentsPassed;
			
			tempMultipleTimesSeries = new ArrayList<PlottingSettings.MultipleBandpassFilteredTimeSeries>();

			for(int i=0; i< PlottingSettings.listOfMultipleBandpassFilteredTimeSeries.size();i++){
				
				
				tempMultipleTimesSeries.add(AuxFunctions.DeepCopyMultipleBandpassFilteredTimeSeriesList(PlottingSettings.listOfMultipleBandpassFilteredTimeSeries.get(i)));
				
			}
			
			tempAgentInstanceVariable = new ArrayList<PlottingSettings.Agent> ();
			
			for(int i=0; i< PlottingSettings.listOfAgentsVariableInstances.size();i++){
				
				
				tempAgentInstanceVariable.add(AuxFunctions.DeepCopyAgentVariableInstance(PlottingSettings.listOfAgentsVariableInstances.get(i)));
				
			}
			
			tempRatioInstanceVariable = new ArrayList<PlottingSettings.RatioInstance>();
			
			
			for(int i=0; i< PlottingSettings.listOfRatioInstances.size();i++){
				
				
				tempRatioInstanceVariable.add(AuxFunctions.DeepCopyRatioInstance(PlottingSettings.listOfRatioInstances.get(i)));
				
			}
			
			tempRatiosSelectedForMultipleBandpassFilteredTimeSeries = PlottingSettings.ratiosSelectedForMultipleBandpassFilteredTimeSeries;
			
			tempDefaults = PlottingSettings.defaultsMultipleBandpassFilteredTimeSeries.clone();

			
			/*Dialog settings*/	
		    setTitle("Multiple Time Series");
			setSize(800,400);
			setBackground( Color.gray );

		   // setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		    setLayout(new GridBagLayout());
		    
			ts = new GridBagConstraints();
			ts.insets = new Insets( 5, 5, 5, 5);
			
			
			menuBar = new JMenuBar();
			
			
			/*1. menu*/
			setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			addWindowListener( new WindowAdapter(){
				
				public void windowClosing(WindowEvent e)
				{

		    		Object text = "Apply Changes? \n";
		    		
		    		int choice = JOptionPane.showConfirmDialog(null, text,  "Exit GUI",JOptionPane.YES_NO_CANCEL_OPTION);
		   
		    		if(choice==0){
		 
		    			/*Save Settings*/

		    			PlottingSettings.listOfMultipleBandpassFilteredTimeSeries = tempMultipleTimesSeries;
		    			PlottingSettings.listOfAgentsVariableInstances = tempAgentInstanceVariable;
		    			PlottingSettings.listOfRatioInstances = tempRatioInstanceVariable;
		    			PlottingSettings.ratiosSelectedForMultipleBandpassFilteredTimeSeries = tempRatiosSelectedForMultipleBandpassFilteredTimeSeries;
		    			setVisible(false);
		    			dispose();
		    			
		    			
		    		}else if(choice==1){
		    			
		    			/*Choice is no*/
		    			PlottingSettings.defaultsMultipleBandpassFilteredTimeSeries = tempDefaults;
		    			setVisible(false);
		    			dispose();
		    			
		    		}
		   
	    			
				}
				});
			
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
	   				for(int i=0; i<tempAgentInstanceVariable.size();i++){
						
						if(tempAgentInstanceVariable.get(i).isSelected){
							
							selectedAgentListModel.addElement(tempAgentInstanceVariable.get(i).agentName);
							
						}
					}
	   				
	   				/*If ratios are selected in the variable list add ratios*/
					if(tempRatioInstanceVariable.size()>0){
						
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
										
										for(int i=0; i<tempRatioInstanceVariable.size();i++){
											
											boolean alreadySelected = false; 
											
											for(int j=0; j < timeSeriesElements.size();j++)
											{
												if(timeSeriesElements.get(j).elementName.equals(tempRatioInstanceVariable.get(i).ratioInstanceName)){
													
													alreadySelected = true;
													break;
													
												}
											}
											if(!alreadySelected)
												variableListModel.addElement(tempRatioInstanceVariable.get(i).ratioInstanceName);
											
										}
										
									}else
									{
									
										for(int i=0; i<tempAgentInstanceVariable.size();i++){
											
											if(tempAgentInstanceVariable.get(i).agentName.equals(selectedAgentList.getModel().getElementAt(selectedAgentList.locationToIndex(e.getPoint())))){
												 //Create the tab pages for the agent that is selected for plotting
												
												tempAgentType = tempAgentInstanceVariable.get(i).agentName;
												
												variableListModel.removeAllElements();
												
												for(int j=0; j < tempAgentInstanceVariable.get(i).listOfVariableInstances.size(); j++){

															
															boolean alreadySelected = false;
															
															for(int l=0; l < timeSeriesElements.size();l++)
															{
																if(timeSeriesElements.get(l).elementName.equals(tempAgentInstanceVariable.get(i).listOfVariableInstances.get(j).instanceName)){
																	
																	alreadySelected = true;
																	break;
																	
																}
															}
															if(!alreadySelected)
																variableListModel.addElement(tempAgentInstanceVariable.get(i).listOfVariableInstances.get(j).instanceName);
														
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
								
								String name = "mts_";
								
								for(int i=0; i< timeSeriesElements.size()-1;i++){
									
									name = name+timeSeriesElements.get(i).elementName+"_"; 
		
								}
								if(timeSeriesElements.size()-1>0){
									
									name = name+timeSeriesElements.get(timeSeriesElements.size()-1).elementName;
									
								}
								
								
								PlottingSettings.MultipleBandpassFilteredTimeSeries tempMTS = (new PlottingSettings()).new MultipleBandpassFilteredTimeSeries(name);

								
								for(int i=0; i< timeSeriesElements.size();i++){
									
									if(timeSeriesElements.get(i).elementBelongsTo.equals("Ratio")){
										
										for(int j=0; j<tempRatioInstanceVariable.size();j++ ){
											
											if(tempRatioInstanceVariable.get(j).ratioInstanceName.equals(timeSeriesElements.get(i).elementName)){
												
												tempMTS.ratiosUsedForMultioleTimeSeries.add(tempRatioInstanceVariable.get(j));
												tempRatioInstanceVariable.get(j).isSelectedForMultipleBandpassFilteredTimeSeries= true;
												
											}
											
											
										}
										
										
										
									}else{
										
										for(int j=0; j < tempAgentInstanceVariable.size();j++){
											
											if(tempAgentInstanceVariable.get(j).agentName.equals(timeSeriesElements.get(i).elementBelongsTo)){
												
												for(int k=0; k<tempAgentInstanceVariable.get(j).listOfVariableInstances.size();k++){
													
													if(tempAgentInstanceVariable.get(j).listOfVariableInstances.get(k).instanceName.equals(timeSeriesElements.get(i).elementName)){
														
														
														tempMTS.variableInstancesUsedForMultioleTimeSeries.add(tempAgentInstanceVariable.get(j).listOfVariableInstances.get(k));
														tempAgentInstanceVariable.get(j).listOfVariableInstances.get(k).isSelectedForMultipleBandpassFilteredTimeSeries = true;
														
														
														
														
													}
													
													
													
												}
												
												
												
											}
											
										}
										
										
										
									}
									
									
									
								}
								
								
								tempMultipleTimesSeries.add(tempMTS);
								
								removeTimeSeries.setEnabled(true);
								
								addTimeSeriesDialog.dispose();
								addTimeSeriesDialog.setVisible(false);
								
								drawTable();
								
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
			
			if(tempMultipleTimesSeries.size()==0)
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
				
				PlottingSettings.MultipleBandpassFilteredTimeSeries oldMTS;
			
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
					
					mtsList = new JList(dlmMTS);
					
					for(int i=0; i<tempMultipleTimesSeries.size();i++){

							dlmMTS.addElement(tempMultipleTimesSeries.get(i).timeSeriesName);
		
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
								

								for(int i=0; i<tempMultipleTimesSeries.size();i++ ){
									
									if(tempMultipleTimesSeries.get(i).timeSeriesName.equals(mtsList.getModel().getElementAt(mtsList.locationToIndex(e.getPoint())))){
										
										
										indexOfEditedMTS =i;
								
										oldMTS = tempMultipleTimesSeries.get(i).clone();
										
										for	(int j=0; j<tempMultipleTimesSeries.get(i).variableInstancesUsedForMultioleTimeSeries.size();j++){
											
											
											timeSeriesElements.add(new TimeSeriesElement( tempMultipleTimesSeries.get(i).variableInstancesUsedForMultioleTimeSeries.get(j).instanceName,
													tempMultipleTimesSeries.get(i).variableInstancesUsedForMultioleTimeSeries.get(j).agentName));
											
											multipleTimeSeriesModel.addElement(tempMultipleTimesSeries.get(i).variableInstancesUsedForMultioleTimeSeries.get(j).instanceName);
											
										}
										
										for	(int j=0; j<tempMultipleTimesSeries.get(i).ratiosUsedForMultioleTimeSeries.size();j++){
											
											timeSeriesElements.add(new TimeSeriesElement( tempMultipleTimesSeries.get(i).ratiosUsedForMultioleTimeSeries.get(j).ratioInstanceName,
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
					   				for(int i=0; i<tempAgentInstanceVariable.size();i++){
										
										if(tempAgentInstanceVariable.get(i).isSelected){
											
											selectedAgentListModel.addElement(tempAgentInstanceVariable.get(i).agentName);
											
										}
									}
					   				
					   				/*If ratios are selected in the variable list add ratios*/
									if(tempRatioInstanceVariable.size()>0){
										
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
														
														for(int i=0; i<tempRatioInstanceVariable.size();i++){
															
															boolean alreadySelected = false; 
															
															for(int j=0; j < timeSeriesElements.size();j++)
															{
																if(timeSeriesElements.get(j).elementName.equals(tempRatioInstanceVariable.get(i).ratioInstanceName)){
																	
																	alreadySelected = true;
																	break;
																	
																}
															}
															if(!alreadySelected)
																variableListModel.addElement(tempRatioInstanceVariable.get(i).ratioInstanceName);
															
														}
														
													}else
													{
													
														for(int i=0; i<tempAgentInstanceVariable.size();i++){
															
															if(tempAgentInstanceVariable.get(i).agentName.equals(selectedAgentList.getModel().getElementAt(selectedAgentList.locationToIndex(e.getPoint())))){
																 //Create the tab pages for the agent that is selected for plotting
																
																tempAgentType = tempAgentInstanceVariable.get(i).agentName;
																
																variableListModel.removeAllElements();
																
																for(int j=0; j < tempAgentInstanceVariable.get(i).listOfVariableInstances.size(); j++){

																			
																			boolean alreadySelected = false;
																			
																			for(int l=0; l < timeSeriesElements.size();l++)
																			{
																				if(timeSeriesElements.get(l).elementName.equals(tempAgentInstanceVariable.get(i).listOfVariableInstances.get(j).instanceName)){
																					
																					alreadySelected = true;
																					break;
																					
																				}
																			}
																			if(!alreadySelected)
																				variableListModel.addElement(tempAgentInstanceVariable.get(i).listOfVariableInstances.get(j).instanceName);
																		
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
												
												
												PlottingSettings.MultipleBandpassFilteredTimeSeries tempMTS = (new PlottingSettings()).new MultipleBandpassFilteredTimeSeries(name);

												
												for(int i=0; i< timeSeriesElements.size();i++){
													
													if(timeSeriesElements.get(i).elementBelongsTo.equals("Ratio")){
														
														for(int j=0; j<tempRatioInstanceVariable.size();j++ ){
															
															if(tempRatioInstanceVariable.get(j).ratioInstanceName.equals(timeSeriesElements.get(i).elementName)){
																
																tempMTS.ratiosUsedForMultioleTimeSeries.add(tempRatioInstanceVariable.get(j));
																tempRatioInstanceVariable.get(j).isSelectedForMultipleBandpassFilteredTimeSeries = true;
																
															}
															
															
														}
														
														
														
													}else{
														
														for(int j=0; j < tempAgentInstanceVariable.size();j++){
															
															if(tempAgentInstanceVariable.get(j).agentName.equals(timeSeriesElements.get(i).elementBelongsTo)){
																
																for(int k=0; k<tempAgentInstanceVariable.get(j).listOfVariableInstances.size();k++){
																	
																	if(tempAgentInstanceVariable.get(j).listOfVariableInstances.get(k).instanceName.equals(timeSeriesElements.get(i).elementName)){
																		
																		
																		tempMTS.variableInstancesUsedForMultioleTimeSeries.add(tempAgentInstanceVariable.get(j).listOfVariableInstances.get(k));
																		tempAgentInstanceVariable.get(j).listOfVariableInstances.get(k).isSelectedForMultipleBandpassFilteredTimeSeries = true;
																		
																		
																		
																	}
																	
																	
																	
																}
																
																
																
															}
															
														}
														
														
														
													}
													
													
													
												}
												
												
												
												
													try{
														
														tempMultipleTimesSeries.remove(indexOfEditedMTS);
														
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
															
															
															for(int j=0; j < tempMultipleTimesSeries.size();j++){
													
																if(!usedElsewhere){	
																	for(int l=0; l < tempMultipleTimesSeries.get(j).variableInstancesUsedForMultioleTimeSeries.size();l++ ){
																		
																		
																		if(oldMTS.variableInstancesUsedForMultioleTimeSeries.get(i).agentName.equals(tempMultipleTimesSeries.get(j).variableInstancesUsedForMultioleTimeSeries.get(l).agentName) && 
																				oldMTS.variableInstancesUsedForMultioleTimeSeries.get(i).instanceName.equals(tempMultipleTimesSeries.get(j).variableInstancesUsedForMultioleTimeSeries.get(l).instanceName)){
																			
																			usedElsewhere = true;
																			break;
															
																		}
																		
																		
																	}
																	
																
																}
															}
						
															if(!usedElsewhere){
																for (int j=0; j < tempAgentInstanceVariable.size();j++){
															
																	if(tempAgentInstanceVariable.get(j).agentName.equals(oldMTS.variableInstancesUsedForMultioleTimeSeries.get(i).agentName)){
																
																		for(int l=0; l < tempAgentInstanceVariable.get(j).listOfVariableInstances.size();l++){
																			
																			if(tempAgentInstanceVariable.get(j).listOfVariableInstances.get(l).instanceName.equals(oldMTS.variableInstancesUsedForMultioleTimeSeries.get(i).instanceName)){
																				
																				
																				tempAgentInstanceVariable.get(j).listOfVariableInstances.get(l).isSelectedForMultipleTimeSeries = false;
																	
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
															
															
															for(int j=0; j < tempMultipleTimesSeries.size();j++){
													
																if(!usedElsewhere){	
																	for(int l=0; l < tempMultipleTimesSeries.get(j).ratiosUsedForMultioleTimeSeries.size();l++ ){
																		
																		
																		if(oldMTS.ratiosUsedForMultioleTimeSeries.get(i).ratioInstanceName.equals(tempMultipleTimesSeries.get(j).ratiosUsedForMultioleTimeSeries.get(l).ratioInstanceName)){
																			
																			usedElsewhere = true;
																			break;
															
																		}
																		
																		
																	}
																	
																
																}
															}
						
															if(!usedElsewhere){
																for (int j=0; j < tempRatioInstanceVariable.size();j++){
												
																	if(tempRatioInstanceVariable.get(j).ratioInstanceName.equals(oldMTS.ratiosUsedForMultioleTimeSeries.get(i).ratioInstanceName)){
																		
																		
																		tempRatioInstanceVariable.get(j).isSelectedForMultipleTimeSeries = false;
															
																	}
															
															}
													
														}
									
													}
							
												}
													
													
													
												
												tempMultipleTimesSeries.add(tempMTS);
												
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
			
			if(tempMultipleTimesSeries.size()==0)
				removeTimeSeries.setEnabled(false);
			
			
			removeTimeSeries.addActionListener(new ActionListener(){

				JDialog removeMTS;
				JList mtsList;
				DefaultListModel dlmMTS;
				PlottingSettings.MultipleBandpassFilteredTimeSeries oldMTS;
			
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
			   		        // Get item index
						int index = locationToIndex(evt.getPoint());

			   		     
		   		        
		   		        String tooltip =dlmMTS.getElementAt(index).toString();
		   		   	
			   	   		 

		   		        // Return the tool tip text
		   		        return tooltip;
						}
						
					};
					mtsList.setPreferredSize(new Dimension(150,200));
					for(int i=0; i<tempMultipleTimesSeries.size();i++){

							dlmMTS.addElement(tempMultipleTimesSeries.get(i).timeSeriesName);
		
					}
					
					mtsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					mtsList.setVisibleRowCount(6);
					
					mtsList.addMouseListener(new MouseListener(){
						
						public void mouseClicked(MouseEvent e) {

							System.out.println("One Click in row");
							
							if(e.getClickCount()==2){
								
								System.out.println("Double Click in row");
								
								for(int i=0; i<tempMultipleTimesSeries.size();i++){
									
									if(tempMultipleTimesSeries.get(i).timeSeriesName.equals(mtsList.getModel().getElementAt(mtsList.locationToIndex(e.getPoint())))){
										 //Create the tab pages for the agent that is selected for plotting
										
										oldMTS = tempMultipleTimesSeries.get(i).clone();
										
										tempMultipleTimesSeries.remove(i);
										
										removeMTS.dispose();
										removeMTS.setVisible(false);
										
										//If all agents are selected for plotting deactivate the addAgent menu item
										if(tempMultipleTimesSeries.size()== 0){
											removeTimeSeries.setEnabled(false);
										}
										
										removeMTS.dispose();
										removeMTS.setVisible(false);
										
										for(int j=0; j < oldMTS.variableInstancesUsedForMultioleTimeSeries.size();j++){
											
											
											
											boolean usedElsewhere = false;
											
											
											for(int k=0; k < tempMultipleTimesSeries.size();k++){
									
												if(!usedElsewhere){	
													for(int l=0; l < tempMultipleTimesSeries.get(k).variableInstancesUsedForMultioleTimeSeries.size();l++ ){
														
														
														if(oldMTS.variableInstancesUsedForMultioleTimeSeries.get(j).agentName.equals(tempMultipleTimesSeries.get(k).variableInstancesUsedForMultioleTimeSeries.get(l).agentName) && 
																oldMTS.variableInstancesUsedForMultioleTimeSeries.get(j).instanceName.equals(tempMultipleTimesSeries.get(k).variableInstancesUsedForMultioleTimeSeries.get(l).instanceName)){
															
															usedElsewhere = true;
															break;
											
														}
														
														
													}
													
												
												}
											}
		
											if(!usedElsewhere){
												for (int k=0; k< tempAgentInstanceVariable.size();k++){
											
													if(tempAgentInstanceVariable.get(k).agentName.equals(oldMTS.variableInstancesUsedForMultioleTimeSeries.get(i).agentName)){
												
														for(int l=0; l < tempAgentInstanceVariable.get(k).listOfVariableInstances.size();l++){
															
															if(tempAgentInstanceVariable.get(k).listOfVariableInstances.get(l).instanceName.equals(oldMTS.variableInstancesUsedForMultioleTimeSeries.get(j).instanceName)){
																
																
																tempAgentInstanceVariable.get(k).listOfVariableInstances.get(l).isSelectedForMultipleTimeSeries = false;
													
															}
															
															
													}
															
												}
												
											}
									
										
					
									}
			
								}
									
									
				
									
									for(int j=0; j < oldMTS.ratiosUsedForMultioleTimeSeries.size();j++){
										
										
											
											
											boolean usedElsewhere = false;
											
											
											for(int k=0; k < tempMultipleTimesSeries.size();k++){
									
												if(!usedElsewhere){	
													for(int l=0; l < tempMultipleTimesSeries.get(k).ratiosUsedForMultioleTimeSeries.size();l++ ){
														
														
														if(oldMTS.ratiosUsedForMultioleTimeSeries.get(j).ratioInstanceName.equals(tempMultipleTimesSeries.get(k).ratiosUsedForMultioleTimeSeries.get(l).ratioInstanceName)){
															
															usedElsewhere = true;
															break;
											
														}
														
														
													}
													
												
												}
											}
		
											if(!usedElsewhere){
												for (int k=0; k < tempRatioInstanceVariable.size();k++){
								
													if(tempRatioInstanceVariable.get(k).ratioInstanceName.equals(oldMTS.ratiosUsedForMultioleTimeSeries.get(j).ratioInstanceName)){
														
														
														tempRatioInstanceVariable.get(k).isSelectedForMultipleTimeSeries = false;
											
													}
											
											}
									
										
					
									}
			
								}
										
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
			
			
			applyChanges = new JMenuItem("Apply Changes");
			
			applyChanges.addActionListener(new ActionListener(){

		
				public void actionPerformed(ActionEvent arg0) {
				
					
					PlottingSettings.listOfMultipleBandpassFilteredTimeSeries = tempMultipleTimesSeries;
	    			PlottingSettings.listOfAgentsVariableInstances = tempAgentInstanceVariable;
	    			PlottingSettings.listOfRatioInstances = tempRatioInstanceVariable;
	    			PlottingSettings.ratiosSelectedForMultipleBandpassFilteredTimeSeries = tempRatiosSelectedForMultipleBandpassFilteredTimeSeries;
					
				}
				
				
				
				
				
			});
			
			
			
			multipleTimeSeries.add(applyChanges);
			
			
			exit = new JMenuItem("Exit");
			
			exit.addActionListener(new ActionListener(){

				
				public void actionPerformed(ActionEvent arg0) {
				
					
				Object text = "Apply Changes? \n";
		    		
		    		int choice = JOptionPane.showConfirmDialog(null, text,  "Exit GUI",JOptionPane.YES_NO_CANCEL_OPTION);
		   
		    		if(choice==0){
		 
		    			/*Save Settings*/

		    			PlottingSettings.listOfMultipleBandpassFilteredTimeSeries = tempMultipleTimesSeries;
		    			PlottingSettings.listOfAgentsVariableInstances = tempAgentInstanceVariable;
		    			PlottingSettings.listOfRatioInstances = tempRatioInstanceVariable;
		    			PlottingSettings.ratiosSelectedForMultipleBandpassFilteredTimeSeries = tempRatiosSelectedForMultipleBandpassFilteredTimeSeries;
		    			setVisible(false);
		    			dispose();
		    			
		    			
		    		}else if(choice==1){
		    			
		    			/*Choice is no*/
		    			PlottingSettings.defaultsMultipleBandpassFilteredTimeSeries = tempDefaults;
		    			setVisible(false);
		    			dispose();
		    			
		    		}
					
				}
				
				
				
				
				
			});
			
			
			multipleTimeSeries.add(exit);
			
			
			menuBar.add(multipleTimeSeries);
			
			
			menuSettings = new JMenu("Settings");

			
			JMenuItem defaultMenuItem = new JMenuItem("Default Settings");
			
			
			menuSettings.add(defaultMenuItem);
			
			defaultMenuItem.addActionListener(new ActionListener(){


					JTextField textfield1, textfield2, textfield3, textfield4,textfield5, textfield6,textfield7;
					JCheckBox CBLowerBound, CBUpperBound, CBUpperBound4, CBUpperBound3, CBUpperBound2;
					JDialog defaultSettingsDialog;
					JComboBox combo1;
					
					public void actionPerformed(ActionEvent arg0) {
				

						defaultSettingsDialog = new JDialog();
						defaultSettingsDialog.setTitle("Default Settings");
						defaultSettingsDialog.setLayout(new FlowLayout());
						defaultSettingsDialog.setSize(500,600);
						defaultSettingsDialog.setBackground(Color.white);
						defaultSettingsDialog.setModal(true);
						
						defaultSettingsDialog.setLayout(new GridBagLayout());
			   			final GridBagConstraints enterDS= new GridBagConstraints();
			   			enterDS.insets = new Insets( 5, 5, 5, 5);
						
						
			   			/*tempDefaults*/
			   			
			   			JLabel label1 = new JLabel("Tmin");
			   			textfield1 = new JTextField(5);
			   			textfield1.setText(Integer.toString(PlottingSettings.defaultsMultipleBandpassFilteredTimeSeries.tmin));
			   			
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
			   			textfield2.setText(Integer.toString(PlottingSettings.defaultsMultipleBandpassFilteredTimeSeries.tmax));
			   			
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
			   			
			   			
			   		
			   			
			   			
			   			
			   			JLabel label44 = new JLabel("Aggretaion");
			   			String methods[] = {"No", "Sum", "Mean"}; 
			   			
			   			combo1 = new JComboBox(methods);
			   			
			   			
			   			combo1.setSelectedItem(PlottingSettings.defaultsMultipleBandpassFilteredTimeSeries.aggregation);
			   			
			   			enterDS.gridx=0; enterDS.gridy=5;
			   			defaultSettingsDialog.add(label44,enterDS);
			   			
			   			enterDS.gridx=1; enterDS.gridy=5;
			   			defaultSettingsDialog.add(combo1,enterDS);
			   			
			   			
			   			JLabel label42 = new JLabel("Correlation");
			   			enterDS.gridx=0; enterDS.gridy=6;
			   			defaultSettingsDialog.add(label42,enterDS);
			   			
			   			CBUpperBound2 = new JCheckBox(); 
			   			
			   			if(PlottingSettings.defaultsMultipleBandpassFilteredTimeSeries.correlation)
			   				CBUpperBound2.setSelected(true);
			   			
			   			enterDS.gridx=1; enterDS.gridy=6;
			   			defaultSettingsDialog.add(CBUpperBound2,enterDS);
			   			
			   			
			   			
			   			JLabel label43 = new JLabel("Logaritmic");
			   			enterDS.gridx=0; enterDS.gridy=7;
			   			defaultSettingsDialog.add(label43,enterDS);
			   			
			   			CBUpperBound3 = new JCheckBox(); 
			   			
			   			if(PlottingSettings.defaultsMultipleBandpassFilteredTimeSeries.logarithmic)
			   				CBUpperBound3.setSelected(true);
			   			
			   			enterDS.gridx=1; enterDS.gridy=7;
			   			defaultSettingsDialog.add(CBUpperBound3,enterDS);
			   			
			   			
			   			
			   			
			   			
			   			JLabel label5 = new JLabel("Low");
			   			textfield5 = new JTextField(5);
			   			textfield5.setText(Integer.toString(PlottingSettings.defaultsMultipleBandpassFilteredTimeSeries.low));
			   			
			   			enterDS.gridx=0; enterDS.gridy=8;
			   			defaultSettingsDialog.add(label5,enterDS);
			   			
			   			enterDS.gridx=1; enterDS.gridy=8;
			   			defaultSettingsDialog.add(textfield5,enterDS);
			   			
			   			
			   		 /*Add ActionListener*/
			   			textfield5.addActionListener(new ActionListener(){
			  	    		
			  	    		public void actionPerformed(ActionEvent evt) {
			  	    		    
			  	    			String input = textfield5.getText();
			  	    		
			  	    			try {
			  	    				int integer = Integer.parseInt(input);
				    			    System.out.println("NumBatch runs: "+SimulationSettings.numBatchRuns);
				    			    textfield5.selectAll();
			  	    			}
			  	    			catch(NumberFormatException nFE) {
			  	    				JOptionPane.showMessageDialog(null,"Not an integer"); 
			  	    			}
			  	    			
			  	    		}
			  	    		
			  	    	});
			   			
			   			
			   			
			   			
			   			JLabel label6 = new JLabel("High");
			   			textfield6 = new JTextField(5);
			   			textfield6.setText(Integer.toString(PlottingSettings.defaultsMultipleBandpassFilteredTimeSeries.high));
			   			
			   			enterDS.gridx=0; enterDS.gridy=9;
			   			defaultSettingsDialog.add(label6,enterDS);
			   			
			   			enterDS.gridx=1; enterDS.gridy=9;
			   			defaultSettingsDialog.add(textfield6,enterDS);
			   			
			   			
			   		 /*Add ActionListener*/
			   			textfield2.addActionListener(new ActionListener(){
			  	    		
			  	    		public void actionPerformed(ActionEvent evt) {
			  	    		    
			  	    			String input = textfield6.getText();
			  	    		
			  	    			try {
			  	    				int integer = Integer.parseInt(input);
				    			    System.out.println("NumBatch runs: "+SimulationSettings.numBatchRuns);
				    			    textfield6.selectAll();
			  	    			}
			  	    			catch(NumberFormatException nFE) {
			  	    				JOptionPane.showMessageDialog(null,"Not an integer"); 
			  	    			}
			  	    			
			  	    		}
			  	    		
			  	    	});
			   			
			   			
			   			
			   			JLabel label7 = new JLabel("Nfix");
			   			textfield7 = new JTextField(5);
			   			textfield7.setText(Integer.toString(PlottingSettings.defaultsMultipleBandpassFilteredTimeSeries.Nfix));
			   			
			   			enterDS.gridx=0; enterDS.gridy=10;
			   			defaultSettingsDialog.add(label7,enterDS);
			   			
			   			enterDS.gridx=1; enterDS.gridy=10;
			   			defaultSettingsDialog.add(textfield7,enterDS);
			   			
			   			
			   		 /*Add ActionListener*/
			   			textfield2.addActionListener(new ActionListener(){
			  	    		
			  	    		public void actionPerformed(ActionEvent evt) {
			  	    		    
			  	    			String input = textfield7.getText();
			  	    		
			  	    			try {
			  	    				int integer = Integer.parseInt(input);
				    			    System.out.println("NumBatch runs: "+SimulationSettings.numBatchRuns);
				    			    textfield7.selectAll();
			  	    			}
			  	    			catch(NumberFormatException nFE) {
			  	    				JOptionPane.showMessageDialog(null,"Not an integer"); 
			  	    			}
			  	    			
			  	    		}
			  	    		
			  	    	});
			   			
			   			
			   			
			   			
			   			JLabel label8 = new JLabel("Drift");
			   			enterDS.gridx=0; enterDS.gridy=11;
			   			defaultSettingsDialog.add(label8,enterDS);
			   			
			   			CBUpperBound4 = new JCheckBox(); 
			   			
			   			if(PlottingSettings.defaultsMultipleBandpassFilteredTimeSeries.drift)
			   				CBUpperBound4.setSelected(true);
			   			
			   			enterDS.gridx=1; enterDS.gridy=11;
			   			defaultSettingsDialog.add(CBUpperBound4,enterDS);
			   	
			   			
			   			

			   			JButton okay = new JButton("OK");
			   			JButton discard = new JButton("Discard");
			   			
			   			enterDS.gridx=4; enterDS.gridy=12;
			   			defaultSettingsDialog.add(okay,enterDS);
			   			
			   			enterDS.gridx=5; enterDS.gridy=12;
			   			defaultSettingsDialog.add(discard,enterDS);
			   			
			   			okay.addActionListener(new ActionListener(){
			  	    		
			  	    		public void actionPerformed(ActionEvent evt) {
			  	    			
			  	    			
			  	    			try {
			  	    				PlottingSettings.defaultsMultipleBandpassFilteredTimeSeries.tmin = Integer.parseInt(textfield1.getText());
			  	    				PlottingSettings.defaultsMultipleBandpassFilteredTimeSeries.tmax = Integer.parseInt(textfield2.getText());
			  	    				
			  	    				PlottingSettings.defaultsMultipleBandpassFilteredTimeSeries.low = Integer.parseInt(textfield5.getText());
			  	    				PlottingSettings.defaultsMultipleBandpassFilteredTimeSeries.high = Integer.parseInt(textfield6.getText());
			  	    				PlottingSettings.defaultsMultipleBandpassFilteredTimeSeries.Nfix = Integer.parseInt(textfield7.getText());
			  	    				
			  	    				
			  	    	 			defaultSettingsDialog.dispose();
				  	    			defaultSettingsDialog.setVisible(false);
			  
			  	    				
			  	    			}
			  	    			catch(NumberFormatException nFE) {
			  	    				JOptionPane.showMessageDialog(null,"Not an integer!"); 
			  	
			  	    			
			  	    			}
			  	    		
			  	    			PlottingSettings.defaultsMultipleBandpassFilteredTimeSeries.aggregation = combo1.getSelectedItem().toString();
			  	    			
			  	    			PlottingSettings.defaultsMultipleBandpassFilteredTimeSeries.correlation = CBUpperBound2.isSelected();
			  	    			PlottingSettings.defaultsMultipleBandpassFilteredTimeSeries.logarithmic = CBUpperBound3.isSelected();
			  	    			PlottingSettings.defaultsMultipleBandpassFilteredTimeSeries.drift = CBUpperBound4.isSelected();
			  	
			  	    			
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
			
			
			setJMenuBar(menuBar);
			
			scrollMultipleBandpassFilteredTimeSeriesTable = new  JScrollPane();
			
			drawTable();
			
			setModal(true);
			setVisible(true);
			
		}
		
		
		
		private void drawTable(){
			
			/*Redraw the table*/
			remove(scrollMultipleBandpassFilteredTimeSeriesTable);
			validate();
			

			
			multipleTimeSeriesTable = new  MultipleBandpassFilteredTimeSeriesTable();
			
			scrollMultipleBandpassFilteredTimeSeriesTable = multipleTimeSeriesTable.listScrollVariables;
			scrollMultipleBandpassFilteredTimeSeriesTable.setPreferredSize(new Dimension(700, 179)); 
			ts.gridx=0; 
			ts.gridy=1;
			add(scrollMultipleBandpassFilteredTimeSeriesTable,ts);

			validate();
			
			
		}
		
		
		
		private class MultipleBandpassFilteredTimeSeriesTable extends JScrollPane{
			
			
			MultipleBandpassFilteredTimeSeriesTableModel multipleTimeSeriesTableModel;
			JTable multipleTimeSeriesTable;
			
			JScrollPane listScrollVariables;  
			

			MultipleBandpassFilteredTimeSeriesTable(){
				

				
				String[] colHeadersT2 = {"Multiple Time Series Name" ,"Tmin","Tmax", "Components" , "Lower Limit", "", "Upper Limit","" , "Correlation", "Log","Aggregation","Low", "High", "Nfix", "Drift"};
				
		
				
		
				
				
				multipleTimeSeriesTableModel = new MultipleBandpassFilteredTimeSeriesTableModel(colHeadersT2 );
				multipleTimeSeriesTable = new JTable(multipleTimeSeriesTableModel);

				
				multipleTimeSeriesTable.setRowHeight(20);
				
				multipleTimeSeriesTable.getColumnModel().getColumn(0).setCellEditor(new CellEditor());
				multipleTimeSeriesTable.getColumnModel().getColumn(0).getCellEditor().addCellEditorListener(new EditorListener(){
					 
					 
					 public void editingStopped(ChangeEvent e) {
						 
						 System.out.println("Col 2 editing stopped");
						 
						 int editedrow = ((CellEditor) multipleTimeSeriesTable.getColumnModel().getColumn(0).getCellEditor()).getRow();
			    
			    	      for(int i=0; i<tempMultipleTimesSeries.size();i++){
								 
								 if(tempMultipleTimesSeries.get(i).timeSeriesName.equals(multipleTimeSeriesTableModel.getValueAt(editedrow, 0))){
									 
							
								 
									 tempMultipleTimesSeries.get(i).timeSeriesName = multipleTimeSeriesTable.getColumnModel().getColumn(0).getCellEditor().getCellEditorValue().toString();
											
						 
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
			    
						 for(int i=0; i<tempMultipleTimesSeries.size();i++){
				    	    	
								 if(tempMultipleTimesSeries.get(i).timeSeriesName.equals(multipleTimeSeriesTableModel.getValueAt(editedrow, 0)) )
								 {
									 tempMultipleTimesSeries.get(i).tmin = Integer.parseInt(multipleTimeSeriesTable.getColumnModel().getColumn(1).getCellEditor().getCellEditorValue().toString());
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
			    
						 for(int i=0; i<tempMultipleTimesSeries.size();i++){
				    	    	
						    	
							
								 
								 if(tempMultipleTimesSeries.get(i).timeSeriesName.equals(multipleTimeSeriesTableModel.getValueAt(editedrow, 0)) )
								 {
									 tempMultipleTimesSeries.get(i).tmax = Integer.parseInt(multipleTimeSeriesTable.getColumnModel().getColumn(2).getCellEditor().getCellEditorValue().toString());
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
			    
						 for(int i=0; i<tempMultipleTimesSeries.size();i++){
				    	    	
						    	
							 if(tempMultipleTimesSeries.get(i).timeSeriesName.equals(multipleTimeSeriesTableModel.getValueAt(editedrow, 0)) )
							 {
								
								 
							
									 try{
									 tempMultipleTimesSeries.get(i).lowerBound = Double.parseDouble(multipleTimeSeriesTable.getColumnModel().getColumn(4).getCellEditor().getCellEditorValue().toString());
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

							 for(int i=0; i<tempMultipleTimesSeries.size();i++){
					    	    	
							    	
						
									 
									 if(tempMultipleTimesSeries.get(i).timeSeriesName.equals(multipleTimeSeriesTableModel.getValueAt(editedrow, 0)))
									 {
										 tempMultipleTimesSeries.get(i).lowerBoundEnabled = true;
										 break;
										 
									 }
									 
									 
								 
			  	  
							 }
							 
							 multipleTimeSeriesTableModel.updatetable();
					
							 
							 
						 }else{
							 /*Set the check box*/
							 multipleTimeSeriesTableModel.changeValueAt("false",((JCheckBoxCellEditor) multipleTimeSeriesTable.getColumnModel().getColumn(5).getCellEditor()).getRow(),5 );

							 for(int i=0; i<tempMultipleTimesSeries.size();i++){
					    	    	
							    	
							
									 
									 if(tempMultipleTimesSeries.get(i).timeSeriesName.equals(multipleTimeSeriesTableModel.getValueAt(editedrow, 0)))
									 {
										 tempMultipleTimesSeries.get(i).lowerBoundEnabled = false;
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
			    
						 for(int i=0; i<tempMultipleTimesSeries.size();i++){
				    	    	
						    	
						
								 
								 if(tempMultipleTimesSeries.get(i).timeSeriesName.equals(multipleTimeSeriesTableModel.getValueAt(editedrow, 0)) )
								 {
									 try{
									 tempMultipleTimesSeries.get(i).upperBound = Double.parseDouble(multipleTimeSeriesTable.getColumnModel().getColumn(6).getCellEditor().getCellEditorValue().toString());
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

							 for(int i=0; i<tempMultipleTimesSeries.size();i++){
					    	    	
							    	
								
									 
									 if(tempMultipleTimesSeries.get(i).timeSeriesName.equals(multipleTimeSeriesTableModel.getValueAt(editedrow, 0)) )
									 {
										 tempMultipleTimesSeries.get(i).upperBoundEnabled = true;
										 break;
										 
									 }
									 
									 
								 
			  	  
							 }
							 
							 multipleTimeSeriesTableModel.updatetable();
					
							 
							 
						 }else{
							 /*Set the check box*/
							 multipleTimeSeriesTableModel.changeValueAt("false",((JCheckBoxCellEditor) multipleTimeSeriesTable.getColumnModel().getColumn(7).getCellEditor()).getRow(),7 );

							 for(int i=0; i<tempMultipleTimesSeries.size();i++){
					    	    	
							    	
								
									 
									 if(tempMultipleTimesSeries.get(i).timeSeriesName.equals(multipleTimeSeriesTableModel.getValueAt(editedrow, 0)) )
									 {
										 tempMultipleTimesSeries.get(i).upperBoundEnabled = false;
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
				
				
			
				 final JCheckBox check3 = new JCheckBox();
				 
				 	multipleTimeSeriesTable.getColumnModel().getColumn(8).setPreferredWidth(40);
					
				 	multipleTimeSeriesTable.getColumnModel().getColumn(8).setCellEditor(new JCheckBoxCellEditor(check3));
				 	multipleTimeSeriesTable.getColumnModel().getColumn(8).getCellEditor().getClass();
				 	multipleTimeSeriesTable.getColumnModel().getColumn(8).getCellEditor().addCellEditorListener(new EditorListener(){
						 
						 
						 public void editingStopped(ChangeEvent e) {
							 
							 
							 int editedrow = ((JCheckBoxCellEditor) multipleTimeSeriesTable.getColumnModel().getColumn(8).getCellEditor()).getRow();
							 
							 if( ((JCheckBoxCellEditor) multipleTimeSeriesTable.getColumnModel().getColumn(8).getCellEditor()).getCellEditorValue().equals(true))
							 {
								 /*Set the check box*/
								 multipleTimeSeriesTableModel.changeValueAt("true",editedrow,8 );

								 for(int i=0; i<tempMultipleTimesSeries.size();i++){
						    	    	
								    	
									
										 
										 if(tempMultipleTimesSeries.get(i).timeSeriesName.equals(multipleTimeSeriesTableModel.getValueAt(editedrow, 0)) )
										 {
											 tempMultipleTimesSeries.get(i).correlation = true;
											 break;
											 
										 }
										 
										 
									 
				  	  
								 }
								 
								 multipleTimeSeriesTableModel.updatetable();
						
								 
								 
							 }else{
								 /*Set the check box*/
								 multipleTimeSeriesTableModel.changeValueAt("false",((JCheckBoxCellEditor) multipleTimeSeriesTable.getColumnModel().getColumn(8).getCellEditor()).getRow(),8 );

								 for(int i=0; i<tempMultipleTimesSeries.size();i++){
						    	    	
								    	
									
										 
										 if(tempMultipleTimesSeries.get(i).timeSeriesName.equals(multipleTimeSeriesTableModel.getValueAt(editedrow, 0)) )
										 {
											 tempMultipleTimesSeries.get(i).correlation = false;
											 break;
											 
										 }
										 
										 
									 
				  	  
								 }
								 
								 multipleTimeSeriesTableModel.updatetable();
								
							 }
							 
						    }
						 
						 
						 
					 });
					 
					 
					multipleTimeSeriesTable.getColumnModel().getColumn(8).setCellRenderer(new DefaultTableCellRenderer() {
						 public Component getTableCellRendererComponent(JTable table,
						 Object value, boolean isSelected, boolean hasFocus, int row, int
						 column) {
							 
							 System.out.println(value);
						 check3.setSelected(((Boolean)value).booleanValue()) ;
						 return check3;
						 }
						 
						 
						 
						 });
					
					
					
					
					 final JCheckBox check4 = new JCheckBox();
					 
						multipleTimeSeriesTable.getColumnModel().getColumn(9).setPreferredWidth(40);
						
						multipleTimeSeriesTable.getColumnModel().getColumn(9).setCellEditor(new JCheckBoxCellEditor(check4));
						multipleTimeSeriesTable.getColumnModel().getColumn(9).getCellEditor().getClass();
						multipleTimeSeriesTable.getColumnModel().getColumn(9).getCellEditor().addCellEditorListener(new EditorListener(){
							 
							 
							 public void editingStopped(ChangeEvent e) {
								 
								 
								 int editedrow = ((JCheckBoxCellEditor) multipleTimeSeriesTable.getColumnModel().getColumn(9).getCellEditor()).getRow();
								 
								 if( ((JCheckBoxCellEditor) multipleTimeSeriesTable.getColumnModel().getColumn(9).getCellEditor()).getCellEditorValue().equals(true))
								 {
									 /*Set the check box*/
									 multipleTimeSeriesTableModel.changeValueAt("true",editedrow,9 );

									 for(int i=0; i<tempMultipleTimesSeries.size();i++){
										 
										 
										 if(tempMultipleTimesSeries.get(i).timeSeriesName.equals(multipleTimeSeriesTableModel.getValueAt(editedrow, 0)) )
										 {
											 tempMultipleTimesSeries.get(i).logarithmic  = true;
											 break;
											 
										 }
							    	    	
									    	
										
					  	  
									 }
									 
									 multipleTimeSeriesTableModel.updatetable();
							
									 
									 
								 }else{
									 /*Set the check box*/
									 multipleTimeSeriesTableModel.changeValueAt("false",((JCheckBoxCellEditor) multipleTimeSeriesTable.getColumnModel().getColumn(9).getCellEditor()).getRow(),9 );

									 for(int i=0; i<tempMultipleTimesSeries.size();i++){
							    	    	
									    	
										 if(tempMultipleTimesSeries.get(i).timeSeriesName.equals(multipleTimeSeriesTableModel.getValueAt(editedrow, 0)) )
										 {
											 tempMultipleTimesSeries.get(i).logarithmic  = false;
											 break;
											 
										 }
					  	  
									 }
									 
									 multipleTimeSeriesTableModel.updatetable();
									
								 }
								 
							    }
							 
							 
							 
						 });
						 
						 
						multipleTimeSeriesTable.getColumnModel().getColumn(9).setCellRenderer(new DefaultTableCellRenderer() {
							 public Component getTableCellRendererComponent(JTable table,
							 Object value, boolean isSelected, boolean hasFocus, int row, int
							 column) {
								 
								 System.out.println(value);
							 check4.setSelected(((Boolean)value).booleanValue()) ;
							 return check4;
							 }
							 
							 
							 
							 });
						
						
						
						
						
						
						
				
						String [] selChoice2 = {"No", "Sum", "Mean"};

						multipleTimeSeriesTable.getColumnModel().getColumn(10).setCellEditor(new SpecificComboBoxEditor2(selChoice2));
						multipleTimeSeriesTable.getColumnModel().getColumn(10).setCellRenderer(new SpecificComboBoxRenderer2(selChoice2));
						
						
						
						multipleTimeSeriesTable.getColumnModel().getColumn(10).getCellEditor().addCellEditorListener(new EditorListener(){
							 
							 
							 public void editingStopped(ChangeEvent e) {
								 
								 System.out.println("Editing ended!");
								 
							 }
							 
							 
						});
						
						
						multipleTimeSeriesTable.getColumnModel().getColumn(11).setPreferredWidth(140);
						multipleTimeSeriesTable.getColumnModel().getColumn(11).setCellEditor(new CellEditor());
						
						
						
						multipleTimeSeriesTable.getColumnModel().getColumn(11).getCellEditor().addCellEditorListener(new EditorListener(){
							 
							 
							 public void editingStopped(ChangeEvent e) {
								 
								 System.out.println("Col 10 editing stopped");
								 
								 int editedrow = ((CellEditor) multipleTimeSeriesTable.getColumnModel().getColumn(11).getCellEditor()).getRow();
					    
								 for(int i=0; i<tempMultipleTimesSeries.size();i++){
						    	    	
		
									 if(tempMultipleTimesSeries.get(i).timeSeriesName.equals(multipleTimeSeriesTableModel.getValueAt(editedrow, 0)) )
									 {
										 try{
											 tempMultipleTimesSeries.get(i).low = Integer.parseInt(multipleTimeSeriesTable.getColumnModel().getColumn(11).getCellEditor().getCellEditorValue().toString());
											 break;
											 }catch(Exception exp){
												 
												 JOptionPane.showMessageDialog(null,"Parameter must be an integer!");
												 
											 }
										 
									 }
				  	  
								 }
					    	      
					    	      multipleTimeSeriesTableModel.changeValueAt(multipleTimeSeriesTable.getColumnModel().getColumn(11).getCellEditor().getCellEditorValue().toString(), editedrow, 11);
								 
							 }
							 
							 
						});
						
						
						
						multipleTimeSeriesTable.getColumnModel().getColumn(12).setPreferredWidth(140);
						multipleTimeSeriesTable.getColumnModel().getColumn(12).setCellEditor(new CellEditor());
						
						
						
						multipleTimeSeriesTable.getColumnModel().getColumn(12).getCellEditor().addCellEditorListener(new EditorListener(){
							 
							 
							 public void editingStopped(ChangeEvent e) {
								 
								 System.out.println("Col 11 editing stopped");
								 
								 int editedrow = ((CellEditor) multipleTimeSeriesTable.getColumnModel().getColumn(12).getCellEditor()).getRow();
					    
								 for(int i=0; i<tempMultipleTimesSeries.size();i++){
						    	    	
								    	
									 if(tempMultipleTimesSeries.get(i).timeSeriesName.equals(multipleTimeSeriesTableModel.getValueAt(editedrow, 0)) )
									 {
											 try{
											 tempMultipleTimesSeries.get(i).high = Integer.parseInt(multipleTimeSeriesTable.getColumnModel().getColumn(12).getCellEditor().getCellEditorValue().toString());
											 break;
											 }catch(Exception exp){
												 
												 JOptionPane.showMessageDialog(null,"Parameter must be an integer!");
												 
											 }
											 
									 }
				  	  
								 }
					    	      
					    	      multipleTimeSeriesTableModel.changeValueAt(multipleTimeSeriesTable.getColumnModel().getColumn(12).getCellEditor().getCellEditorValue().toString(), editedrow, 12);
								 
							 }
							 
							 
						});
						
						
						
						multipleTimeSeriesTable.getColumnModel().getColumn(13).setPreferredWidth(140);
						multipleTimeSeriesTable.getColumnModel().getColumn(13).setCellEditor(new CellEditor());
						
						
						
						multipleTimeSeriesTable.getColumnModel().getColumn(13).getCellEditor().addCellEditorListener(new EditorListener(){
							 
							 
							 public void editingStopped(ChangeEvent e) {
								 
								 System.out.println("Col 11 editing stopped");
								 
								 int editedrow = ((CellEditor) multipleTimeSeriesTable.getColumnModel().getColumn(13).getCellEditor()).getRow();
					    
								 for(int i=0; i<tempMultipleTimesSeries.size();i++){
						    	    	
								    	
									 if(tempMultipleTimesSeries.get(i).timeSeriesName.equals(multipleTimeSeriesTableModel.getValueAt(editedrow, 0)) )
									 {
											 try{
											 tempMultipleTimesSeries.get(i).Nfix = Integer.parseInt(multipleTimeSeriesTable.getColumnModel().getColumn(13).getCellEditor().getCellEditorValue().toString());
											 break;
											 }catch(Exception exp){
												 
												 JOptionPane.showMessageDialog(null,"Parameter must be an integer!");
												 
											 }
											 
										 
										 
									 }
				  	  
								 }
					    	      
					    	      multipleTimeSeriesTableModel.changeValueAt(multipleTimeSeriesTable.getColumnModel().getColumn(12).getCellEditor().getCellEditorValue().toString(), editedrow, 13);
								 
							 }
							 
							 
						});
						
						
						
						
						 final JCheckBox check5 = new JCheckBox();
						 
							multipleTimeSeriesTable.getColumnModel().getColumn(14).setPreferredWidth(40);
							
							multipleTimeSeriesTable.getColumnModel().getColumn(14).setCellEditor(new JCheckBoxCellEditor(check5));
							multipleTimeSeriesTable.getColumnModel().getColumn(14).getCellEditor().getClass();
							multipleTimeSeriesTable.getColumnModel().getColumn(14).getCellEditor().addCellEditorListener(new EditorListener(){
								 
								 
								 public void editingStopped(ChangeEvent e) {
									 
									 
									 int editedrow = ((JCheckBoxCellEditor) multipleTimeSeriesTable.getColumnModel().getColumn(14).getCellEditor()).getRow();
									 
									 if( ((JCheckBoxCellEditor) multipleTimeSeriesTable.getColumnModel().getColumn(14).getCellEditor()).getCellEditorValue().equals(true))
									 {
										 /*Set the check box*/
										 multipleTimeSeriesTableModel.changeValueAt("true",editedrow,14 );

										 for(int i=0; i<tempMultipleTimesSeries.size();i++){
											 
											 
											 if(tempMultipleTimesSeries.get(i).timeSeriesName.equals(multipleTimeSeriesTableModel.getValueAt(editedrow, 0)) )
											 {
												 tempMultipleTimesSeries.get(i).drift  = true;
												 break;
												 
											 }
								    	    	
										    	
											
						  	  
										 }
										 
										 multipleTimeSeriesTableModel.updatetable();
								
										 
										 
									 }else{
										 /*Set the check box*/
										 multipleTimeSeriesTableModel.changeValueAt("false",((JCheckBoxCellEditor) multipleTimeSeriesTable.getColumnModel().getColumn(14).getCellEditor()).getRow(),14);

										 for(int i=0; i<tempMultipleTimesSeries.size();i++){
								    	    	
										    	
											 if(tempMultipleTimesSeries.get(i).timeSeriesName.equals(multipleTimeSeriesTableModel.getValueAt(editedrow, 0)) )
											 {
												 tempMultipleTimesSeries.get(i).drift  = false;
												 break;
												 
											 }
						  	  
										 }
										 
										 multipleTimeSeriesTableModel.updatetable();
										
									 }
									 
								    }
								 
								 
								 
							 });
							 
							 
							multipleTimeSeriesTable.getColumnModel().getColumn(14).setCellRenderer(new DefaultTableCellRenderer() {
								 public Component getTableCellRendererComponent(JTable table,
								 Object value, boolean isSelected, boolean hasFocus, int row, int
								 column) {
									 
									 System.out.println(value);
								 check5.setSelected(((Boolean)value).booleanValue()) ;
								 return check5;
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
			    	
		    	
			    	for(int i=0; i<tempMultipleTimesSeries.size();i++){

						 if(tempMultipleTimesSeries.get(i).timeSeriesName.equals(multipleTimeSeriesTableModel.getValueAt(editedRow, 0)) )
						 {
								for(int j=0; j<tempMultipleTimesSeries.get(i).variableInstancesUsedForMultioleTimeSeries.size();j++){
									
									cComboBox.addItem(tempMultipleTimesSeries.get(i).variableInstancesUsedForMultioleTimeSeries.get(j).instanceName);
		
								}
								for(int j=0; j<tempMultipleTimesSeries.get(i).ratiosUsedForMultioleTimeSeries.size();j++){
									
									cComboBox.addItem(tempMultipleTimesSeries.get(i).ratiosUsedForMultioleTimeSeries.get(j).ratioInstanceName);
				
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
			    	
			     	for(int i=0; i<tempMultipleTimesSeries.size();i++){

						 if(tempMultipleTimesSeries.get(i).timeSeriesName.equals(multipleTimeSeriesTableModel.getValueAt(row, 0)) )
						 {
								for(int j=0; j<tempMultipleTimesSeries.get(i).variableInstancesUsedForMultioleTimeSeries.size();j++){
									
									super.addItem(tempMultipleTimesSeries.get(i).variableInstancesUsedForMultioleTimeSeries.get(j).instanceName);
		
								}
								for(int j=0; j<tempMultipleTimesSeries.get(i).ratiosUsedForMultioleTimeSeries.size();j++){
									
									super.addItem(tempMultipleTimesSeries.get(i).ratiosUsedForMultioleTimeSeries.get(j).ratioInstanceName);
				
								}

							 break;
							 
						 }

			    	}

			     	
			    
			        return this;
			    }
			    
			}
			
			
			
private class SpecificComboBoxEditor2 extends DefaultCellEditor {
				
				int editedRow;
				int editedCol;
				
				JComboBox cComboBox;
				
			    public SpecificComboBoxEditor2(String[] items) {
			        
			    	super(new JComboBox(items));
			        cComboBox = new JComboBox(items);
			     
			    
			    }
			    
			    
			    
			 // This method is called when a cell value is edited by the user.
			    public Component getTableCellEditorComponent(JTable table, Object value,
			            boolean isSelected, int rowIndex, int vColIndex) {
			        // 'value' is value contained in the cell located at (rowIndex, vColIndex
			    	
			    	editedRow = rowIndex;
			    	editedCol = vColIndex;
			    	
			    	
			            // cell (and perhaps other cells) are selected
			        

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
			       
			       cComboBox.addActionListener(new ActionListener(){
			    	   
			    
			    	    public void actionPerformed(ActionEvent e) {
			    	       
			    	    	multipleTimeSeriesTableModel.changeValueAt(cComboBox.getSelectedItem().toString(), editedRow, editedCol);
			    	    	multipleTimeSeriesTableModel.updatetable();
							 
							 
			    	    	 for(int i=0; i<tempMultipleTimesSeries.size();i++){
					    	    	
							    	
			    	    		 if(tempMultipleTimesSeries.get(i).timeSeriesName.equals(multipleTimeSeriesTableModel.getValueAt(editedRow, 0)) )
			    	    		 {
											
										 if(editedCol==10){
												
											 tempMultipleTimesSeries.get(i).aggregation = cComboBox.getSelectedItem().toString();
										 break;
										 }
										 
									 }
									 
									 
								 
			  	  
							 }
			    	    	 
			    	    	 
		
							
								 
							 
							
								
							 
		
			    	    	 multipleTimeSeriesTableModel.updatetable();
			    	    	
			    	    }
			    	   
			    	   
			    	   
			       });
			        
			        return cComboBox;
			      
			        
			    }
			    
			    
			    
			    public int getRow (){
			    	
			    	System.out.println("rowIndex "+editedRow);
			    	
			    	return editedRow;
			    	
			    }
			    
			    
			      
			        
			    
			    
			}
			
			
		private class SpecificComboBoxRenderer2 extends JComboBox implements TableCellRenderer {
				
			    public SpecificComboBoxRenderer2(String[] items) {
			        super(items);
			    }

			    public Component getTableCellRendererComponent(JTable table, Object value,
			            boolean isSelected, boolean hasFocus, int row, int column) {

			    	
			    	System.out.println("ComboBox.getItemAt(0)"+this.getItemAt(0));
			    	
			    	
			        if (isSelected) {
			            setForeground(table.getSelectionForeground());
			            super.setBackground(table.getSelectionBackground());
			        } else {
			            setForeground(table.getForeground());
			            setBackground(table.getBackground());
			        }

			        System.out.println(" setSelectedItem(value) row" +row+ "  " + value);
			        try{
				        if(value.equals(""))
				        {
				        	value = getItemAt(0);
				        }
			        }catch(Exception e){
			        	
			        	value = getItemAt(0);
			        	
			        }
			        // Select the current value
			        setSelectedItem(value);
			        return this;
			    }
			    
			}
			
			
	
			
			
			
		}
		


	public class MultipleBandpassFilteredTimeSeriesTableModel extends AbstractTableModel{
		
		JCheckBox checkBox = new JCheckBox();
		String [] columnName;
		
		
		
		
		
		int numFilters;
		boolean partitioningSelected , filterSelected;
		
		
		PlottingSettings.Agent variableInstances;
		
		
		MultipleBandpassFilteredTimeSeriesTableModel(String[] colName){


			columnName = colName;
		
			
		}
		
		
		
		public int getRowCount()
	   {
		
		return tempMultipleTimesSeries.size();
		
	 }
		
		
	   public int getColumnCount()
	   {
	   return 15;
	 }
	   public Object getValueAt( int row, int col)
	   {
		   
		
		   
		  
			 //  System.out.println("getValueAt timeSeriesSelection.get(i).isntancesOfvariable.size()"+timeSeriesSelection.get(i).isntancesOfvariable.size());
			
					   if(col==0)
						   return tempMultipleTimesSeries.get(row).timeSeriesName;
					   else if(col==1)
						   return tempMultipleTimesSeries.get(row).tmin;
					   else if(col==2)
						   return tempMultipleTimesSeries.get(row).tmax;
					   else if(col==3)
						   return "List of Time Series";
					   else if(col==4){
							if( tempMultipleTimesSeries.get(row).lowerBoundEnabled)
								 return Double.toString(tempMultipleTimesSeries.get(row).lowerBound);
					   		else
					   			return "";
					   }else if(col==5){
						   return tempMultipleTimesSeries.get(row).lowerBoundEnabled;
					   }else if(col==6){
						   if( tempMultipleTimesSeries.get(row).upperBoundEnabled)
								 return Double.toString(tempMultipleTimesSeries.get(row).upperBound);
					   		else
					   			return "";
					   }else if(col==7){
						   return tempMultipleTimesSeries.get(row).upperBoundEnabled;
					   }else if(col==8){
						   return tempMultipleTimesSeries.get(row).correlation;
						  }else if(col==9){
							   return tempMultipleTimesSeries.get(row).logarithmic;
						  }else if(col==10){
							   return tempMultipleTimesSeries.get(row).aggregation;
						  }else if(col==11){
							   return tempMultipleTimesSeries.get(row).low;
						   }else if(col==12){
							   return tempMultipleTimesSeries.get(row).high;
						   }else if(col==13){
							   return tempMultipleTimesSeries.get(row).Nfix;
						   }else if(col==14){
							   return tempMultipleTimesSeries.get(row).drift;
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
			   tempMultipleTimesSeries.get(row).timeSeriesName=value;
		   if(col==1)
			   tempMultipleTimesSeries.get(row).tmin=Integer.parseInt(value);
		   if(col==2)
			   tempMultipleTimesSeries.get(row).tmax=Integer.parseInt(value);
		   if(col==4){
			   tempMultipleTimesSeries.get(row).lowerBound =   Double.parseDouble(value);;
		   }else if(col==5){
			   if(value.equals("true"))
				   tempMultipleTimesSeries.get(row).lowerBoundEnabled = true;
			   else
				   tempMultipleTimesSeries.get(row).lowerBoundEnabled = false;
		   }else if(col==6){
			   tempMultipleTimesSeries.get(row).upperBound =   Double.parseDouble(value);;
		   }else if(col==7){
			   if(value.equals("true"))
				   tempMultipleTimesSeries.get(row).upperBoundEnabled = true;
			   else
				   tempMultipleTimesSeries.get(row).upperBoundEnabled = false;
			   
		   }else if(col==8){
			   
			   if(value.equals("true"))
				   tempMultipleTimesSeries.get(row).correlation = true;
			   else
				   tempMultipleTimesSeries.get(row).correlation = false;
			  
			  }else if(col==9){
				  if(value.equals("true"))
					  tempMultipleTimesSeries.get(row).logarithmic = true;
				   else
					   tempMultipleTimesSeries.get(row).logarithmic = false;
				  
			  }else if(col==10){
				   tempMultipleTimesSeries.get(row).aggregation= value;
			  }else if(col==11){
				   tempMultipleTimesSeries.get(row).low = Integer.parseInt(value);
			   }else if(col==12){
				   tempMultipleTimesSeries.get(row).high = Integer.parseInt(value);
			   }else if(col==13){
				   tempMultipleTimesSeries.get(row).Nfix = Integer.parseInt(value);
			   }else if(col==14){
				   if(value.equals("true"))
					   tempMultipleTimesSeries.get(row).drift = true;
				   else
					   tempMultipleTimesSeries.get(row).drift = false;
				    
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
		
				
			
		
