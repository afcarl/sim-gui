import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SortOrder;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.util.Comparator;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;
import javax.swing.ListModel;
import javax.swing.event.EventListenerList;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;


import java.text.Collator;




public class AgentPlottingVariableList extends JDialog{
	
	private JList notChosenVariableList;
	private DefaultListModel dlmParameter; 
	private DefaultListModel dlm2Parameter ;
	private JList chosenVariableList ;
	private JButton  saveVariables, cancelVariables ;
	
	private DefaultListModel  dlmWeighting,  dlm2Weighting;
	private JList  unSelectWeightingVar,  selectWeightingVar;

	private JTextField  partitioningValue;
	private JDialog enterValue;

	
	/*Variables for menu bar:*/
	private JMenuBar menuBar;
	
	private JMenu menuPlotting;
	private JMenuItem addAgent, removeAgent, applyChanges, exit;
	
	
	private JMenu menuVariables;
	private JMenu addRemoveAgentRatio;
	private JMenuItem addRemoveVariables, addAgentRatio, removeAgentRatio ;
	
	private JMenu menuRatios;
	private JMenuItem addRatio, removeRatio;
	
	private JMenu menuSettings;
	private JMenuItem editFilter, editPartitioning, defaultSettings, weighting ;
	
	private JList agentList;
	private JList removeAgentList;
	private JDialog addAgentDialog , removeAgentDialog;

	private	JTabbedPane tabbedPane;
	private	ArrayList<AgentTab> agentJPanelList;
	private JDialog setNewVariables;
	

	private RatioTab ratioTab;
	private ArrayList<PlottingSettings.RatioInstance> tempRatioListForPlotting;
	private ArrayList<PlottingSettings.Agent> tempAgentList;
	private ArrayList<Agent> tempAgentSettingList;
	private ArrayList <PlottingSettings.SingleTimeSeries> tempListOfSingleTimeSeries;
	private ArrayList <PlottingSettings.MultipleTimeSeries> tempListOfMultipleTimeSeries;
	private ArrayList <PlottingSettings.SingleBandpassFilteredTimeSeries> tempListOfSingleBandpassFilteredTimeSeries;
	private ArrayList <PlottingSettings.MultipleBandpassFilteredTimeSeries> tempListOfMultipleBandpassFilteredTimeSeries;
	private ArrayList <PlottingSettings.ScatterPlots> tempListOfScatterPlots;
	private ArrayList <PlottingSettings.CrossCorrelation> tempListOfCrossCorrelation;
	
	
	AgentPlottingVariableList(ArrayList<Agent> agentsPassed){
		
		//agentsReturn = agentsPassed;
		
		
		/*Clone of tempRatioListForPlotting*/
		tempRatioListForPlotting = new ArrayList<PlottingSettings.RatioInstance>();
		
		for(int i= 0; i< PlottingSettings.listOfRatioInstances.size();i++){
			
			tempRatioListForPlotting.add(AuxFunctions.DeepCopyRatioInstance(PlottingSettings.listOfRatioInstances.get(i)));
			
		}
		
		tempAgentList = new ArrayList<PlottingSettings.Agent>();
		
		for(int i= 0; i< PlottingSettings.listOfAgentsVariableInstances.size();i++){
			
			tempAgentList.add(AuxFunctions.DeepCopyAgentVariableInstance(PlottingSettings.listOfAgentsVariableInstances.get(i)));
			
		}
		
		tempAgentSettingList = AuxFunctions.deepCopyAgentSettingsList(AgentSettings.agents);
		
		
		
		
		tempListOfSingleTimeSeries = new ArrayList <PlottingSettings.SingleTimeSeries>();
		for(int i= 0; i< PlottingSettings.listOfSingleTimeSeries.size();i++){
			
			tempListOfSingleTimeSeries.add(AuxFunctions.DeepCopyTimeSeriesList(PlottingSettings.listOfSingleTimeSeries.get(i)));
			
		}
	
		tempListOfMultipleTimeSeries = new ArrayList <PlottingSettings.MultipleTimeSeries>();
		for(int i= 0; i< PlottingSettings.listOfMultipleTimeSeries.size();i++){
			
			tempListOfMultipleTimeSeries.add(AuxFunctions.DeepCopyMultipleTimeSeriesList(PlottingSettings.listOfMultipleTimeSeries.get(i)));
			
		}

		tempListOfSingleBandpassFilteredTimeSeries = new ArrayList <PlottingSettings.SingleBandpassFilteredTimeSeries>();
		for(int i= 0; i< PlottingSettings.listOfSingleBandpassFilteredTimeSeries.size();i++){
			
			tempListOfSingleBandpassFilteredTimeSeries.add(AuxFunctions.DeepCopySingleBandpassFilteredTimeSeriesList(PlottingSettings.listOfSingleBandpassFilteredTimeSeries.get(i)));
			
		}

		tempListOfMultipleBandpassFilteredTimeSeries = new ArrayList <PlottingSettings.MultipleBandpassFilteredTimeSeries>();
		for(int i= 0; i< PlottingSettings.listOfMultipleBandpassFilteredTimeSeries.size();i++){
			
			tempListOfMultipleBandpassFilteredTimeSeries.add(AuxFunctions.DeepCopyMultipleBandpassFilteredTimeSeriesList(PlottingSettings.listOfMultipleBandpassFilteredTimeSeries.get(i)));
			
		}

		tempListOfScatterPlots = new ArrayList <PlottingSettings.ScatterPlots>();
		for(int i= 0; i< PlottingSettings.listOfScatterPlots.size();i++){
			
			tempListOfScatterPlots.add(PlottingSettings.listOfScatterPlots.get(i).clone());
			
		}

		tempListOfCrossCorrelation = new ArrayList <PlottingSettings.CrossCorrelation>();
		for(int i= 0; i< PlottingSettings.listOfCrossCorrelation.size();i++){
			
			tempListOfCrossCorrelation.add(PlottingSettings.listOfCrossCorrelation.get(i).clone());
			
		}

		/*Dialog settings*/	
	    setTitle("Time Series");
		setSize(800,400);
		setBackground( Color.gray );

	   // setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
	    setLayout(new GridBagLayout());
	    
		GridBagConstraints ts = new GridBagConstraints();
		ts.insets = new Insets( 5, 5, 5, 5);
			
		
		/*Menus*/
	
		
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

	    			PlottingSettings.listOfAgentsVariableInstances = tempAgentList;
	    			PlottingSettings.listOfRatioInstances = tempRatioListForPlotting;
	    			AgentSettings.agents = tempAgentSettingList ;
	    			
	    			PlottingSettings.listOfSingleTimeSeries = tempListOfSingleTimeSeries;
	    			PlottingSettings.listOfMultipleTimeSeries = tempListOfMultipleTimeSeries ;
	    			PlottingSettings.listOfSingleBandpassFilteredTimeSeries =tempListOfSingleBandpassFilteredTimeSeries ;
	    			PlottingSettings.listOfMultipleBandpassFilteredTimeSeries = tempListOfMultipleBandpassFilteredTimeSeries  ;
	    			PlottingSettings.listOfScatterPlots = tempListOfScatterPlots  ;
	    			PlottingSettings.listOfCrossCorrelation	= tempListOfCrossCorrelation;
	    			
	    			setVisible(false);
	    			dispose();
	    			
	    			
	    		}else if(choice==1){
	    			
	    			/*Choice is no*/
	    			setVisible(false);
	    			dispose();
	    			
	    		}

			}
			});
		
		menuPlotting = new JMenu("Agent");
		
		addAgent= new JMenuItem("Add Agent");
		addAgent.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {

				addAgentDialog = new JDialog();
				addAgentDialog.setSize(400,400);
				addAgentDialog.setLayout(new GridBagLayout());
				
				GridBagConstraints fA = new GridBagConstraints();
				fA.insets = new Insets( 5, 5, 5, 5);
				
				JLabel label1 = new JLabel("Select Agent for Plotting:");
				fA.gridx =0;fA.gridy =0;
				addAgentDialog.add(label1,fA);
				
				DefaultListModel dlmAgents = new DefaultListModel();
				
				agentList = new JList(dlmAgents);
				
				for(int i=0; i<tempAgentList.size();i++){
					
					if(!tempAgentList.get(i).isSelected){
						
						dlmAgents.addElement(tempAgentList.get(i).agentName);

					}
					
				}
				
				agentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				agentList.setVisibleRowCount(6);
				
				agentList.addMouseListener(new MouseListener(){
					
					public void mouseClicked(MouseEvent e) {

						System.out.println("One Click in row");
						
						if(e.getClickCount()==2){
							
							System.out.println("Double Click in row");
							
							for(int i=0; i<tempAgentList.size();i++){
								
								if(tempAgentList.get(i).agentName.equals(agentList.getModel().getElementAt(agentList.locationToIndex(e.getPoint())))){
									 //Create the tab pages for the agent that is selected for plotting
									
									
									agentJPanelList.add(new AgentTab(tempAgentList.get(i)));
									tabbedPane.insertTab(tempAgentList.get(i).agentName, null ,agentJPanelList.get(agentJPanelList.size()-1),null,agentJPanelList.size()-1);
									tempAgentList.get(i).isSelected = true;
									
									addAgentDialog.dispose();
									addAgentDialog.setVisible(false);
									
									//If all agents are selected for plotting deactivate the addAgent menu item
									if(agentJPanelList.size()== tempAgentList.size()){
										addAgent.setEnabled(false);
									}
										
									removeAgent.setEnabled(true);
									menuSettings.setEnabled(true);
									menuVariables.setEnabled(true);
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
				
				addAgentDialog.getContentPane().add(new JScrollPane(agentList),fA);
				
				addAgentDialog.setModal(true);
				addAgentDialog.setVisible(true);
				
			}
		});
		menuPlotting.add(addAgent);
		
		removeAgent= new JMenuItem("Remove Agent");
		removeAgent.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {

				removeAgentDialog = new JDialog();
				removeAgentDialog.setSize(300,300);
				removeAgentDialog.setLayout(new GridBagLayout());
				
				GridBagConstraints fA = new GridBagConstraints();
				fA.insets = new Insets( 5, 5, 5, 5);
				
				JLabel label1 = new JLabel("Delete Agent from Plotting:");
				fA.gridx =0;fA.gridy =0;
				removeAgentDialog.add(label1,fA);
				
				DefaultListModel dlmAgentsToremove = new DefaultListModel();
				
				removeAgentList = new JList(dlmAgentsToremove);
				
				for(int i=0; i<tempAgentList.size();i++){
					
					if(tempAgentList.get(i).isSelected){
						
						dlmAgentsToremove.addElement(tempAgentList.get(i).agentName);
						
					}
					
				}
				
				removeAgentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				removeAgentList.setVisibleRowCount(6);
				
				removeAgentList.addMouseListener(new MouseListener(){
					
					public void mouseClicked(MouseEvent e) {

						System.out.println("One Click in row");
						
						if(e.getClickCount()==2){
							
							System.out.println("Double Click in row");
							
							for(int i=0; i<agentJPanelList.size();i++){
								
								if(agentJPanelList.get(i).agent.agentName.equals(removeAgentList.getModel().getElementAt(removeAgentList.locationToIndex(e.getPoint())))){
									 //Create the tab pages for the agent that is selected for plotting
					
									for(int j=0; j< tempAgentList.size();j++){
										
										if(agentJPanelList.get(i).agent.agentName.equals(tempAgentList.get(j).agentName)){
											
											tempAgentList.get(j).isSelected = false;
											break;
										}
									}

									agentJPanelList.remove(i);
									tabbedPane.remove(i);
			
									removeAgentDialog.dispose();
									removeAgentDialog.setVisible(false);
									
									/*Redraw ratio tab*/
									redrawRatioTab();
									
									//If all agents are selected for plotting deactivate the addAgent menu item
									
									addAgent.setEnabled(true);
									
									if(agentJPanelList.size()==0){
										
										removeAgent.setEnabled(false);
										menuSettings.setEnabled(false);
										menuVariables.setEnabled(false);
										
									}
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
				
				removeAgentDialog.getContentPane().add(new JScrollPane(removeAgentList),fA);
				
				removeAgentDialog.setModal(true);
				removeAgentDialog.setVisible(true);
			}
		});	
	
		menuPlotting.add(removeAgent);

		applyChanges = new JMenuItem("Apply Changes");
		applyChanges.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {

				PlottingSettings.listOfAgentsVariableInstances = tempAgentList; 
				PlottingSettings.listOfRatioInstances = tempRatioListForPlotting;
				AgentSettings.agents = tempAgentSettingList ;
				
				PlottingSettings.listOfSingleTimeSeries = tempListOfSingleTimeSeries;
    			PlottingSettings.listOfMultipleTimeSeries = tempListOfMultipleTimeSeries ;
    			PlottingSettings.listOfSingleBandpassFilteredTimeSeries =tempListOfSingleBandpassFilteredTimeSeries ;
    			PlottingSettings.listOfMultipleBandpassFilteredTimeSeries = tempListOfMultipleBandpassFilteredTimeSeries  ;
    			PlottingSettings.listOfScatterPlots = tempListOfScatterPlots  ;
    			PlottingSettings.listOfCrossCorrelation	= tempListOfCrossCorrelation;
				
			}
		});
		menuPlotting.add(applyChanges);
		
		exit = new JMenuItem("Exit");
		exit.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {

				Object text = "Apply Changes? \n";
	    		
	    		int choice = JOptionPane.showConfirmDialog(null, text,  "Exit GUI",JOptionPane.YES_NO_CANCEL_OPTION);
	   
	    		if(choice==0){
	 
	    			/*Save Settings*/

	    			PlottingSettings.listOfAgentsVariableInstances = tempAgentList;
	    			PlottingSettings.listOfRatioInstances = tempRatioListForPlotting;
	    			AgentSettings.agents = tempAgentSettingList ;
	    			
	    			PlottingSettings.listOfSingleTimeSeries = tempListOfSingleTimeSeries;
	    			PlottingSettings.listOfMultipleTimeSeries = tempListOfMultipleTimeSeries ;
	    			PlottingSettings.listOfSingleBandpassFilteredTimeSeries =tempListOfSingleBandpassFilteredTimeSeries ;
	    			PlottingSettings.listOfMultipleBandpassFilteredTimeSeries = tempListOfMultipleBandpassFilteredTimeSeries  ;
	    			PlottingSettings.listOfScatterPlots = tempListOfScatterPlots  ;
	    			PlottingSettings.listOfCrossCorrelation	= tempListOfCrossCorrelation;
	    			setVisible(false);
	    			dispose();
	    			
	    			
	    		}else if(choice==1){
	    			
	    			/*Choice is no*/
	    			setVisible(false);
	    			dispose();
	    			
	    		}
			}
		});
		menuPlotting.add(exit);
		menuBar.add(menuPlotting);
		
		
		menuVariables = new JMenu("Time Series");
		
		addRemoveVariables = new JMenuItem("Add/Remove Variables") ;
		
		addRemoveVariables.addActionListener(new ActionListener(){
			
			SortedListModel sortedModel, sortedModel2 ;

			public void actionPerformed(ActionEvent arg0) {
				
				setNewVariables = new JDialog();
	    		setNewVariables.setTitle("Add/Remove Variables");
	    		
	    		
	    		JPanel setNewVariablesPanel = new JPanel();
	    		JScrollPane setNewVariablesScrollPane;
	    		
	    		setNewVariablesPanel.setLayout(new GridBagLayout());
	   			GridBagConstraints ts = new GridBagConstraints();
	   			ts.insets = new Insets( 5, 5, 5, 5);
	   			
	   			JPanel panelA = new JPanel();
	   			panelA.setSize(200, 800);
	   			
	   			dlmParameter = new DefaultListModel();
	   			notChosenVariableList = new JList(dlmParameter){
	   				

				public String getToolTipText(MouseEvent evt) {
	   		        // Get item index
						int index = locationToIndex(evt.getPoint());

	   		        // Get item
	   		        Object item = getModel().getElementAt(index);
	   		        
	   		        String tooltip = "";
	   		        
	   		        for(int i=0; i < tempAgentSettingList.size();i++){
	   		        	
	   		        	if(tempAgentSettingList.get(i).agentName.equals(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.agentName)){
	   		        		
	   		        		for(int j=0; j< tempAgentSettingList.get(i).variableList.size();j++){
	   		        			
	   		        			if(tempAgentSettingList.get(i).variableList.get(j).name.equals(item)){
	   		        				
	   		        				tooltip = tempAgentSettingList.get(i).variableList.get(j).name +" ("
	   		   		        		+tempAgentSettingList.get(i).variableList.get(j).type+") "+
	   		   		        		tempAgentSettingList.get(i).variableList.get(j).description;
	   		       
	   		        			}	
	   		        		}
	   		        	}
	   		        }
	   		        
	   		        
	   		        // Return the tool tip text
	   		        return tooltip;
	   		    }
	   			};
	   			
	   			
	   			 sortedModel = new SortedListModel(dlmParameter,SortedListModel.SortOrder.UNORDERED);
	   			 notChosenVariableList.setModel(sortedModel);
	   			
	   			
	   			 notChosenVariableList.addMouseListener(new MouseListener(){
					
					public void mouseClicked(MouseEvent e) {
	   			
						System.out.println("Right click");
						
						if(SwingUtilities.isRightMouseButton(e)){
							
							System.out.println("Right click");
							
							
							if(sortedModel.sortOrderOfList==SortedListModel.SortOrder.ASCENDING ){
								
															
								sortedModel.setSortOrder(SortedListModel.SortOrder.DESCENDING);
								notChosenVariableList.setModel(sortedModel);
					
							}else if(sortedModel.sortOrderOfList==SortedListModel.SortOrder.DESCENDING){
								
								sortedModel.setSortOrder(SortedListModel.SortOrder.UNORDERED);
								notChosenVariableList.setModel(sortedModel);
								
							}else if(sortedModel.sortOrderOfList==SortedListModel.SortOrder.UNORDERED){
								
								sortedModel.setSortOrder(SortedListModel.SortOrder.ASCENDING);
								notChosenVariableList.setModel(sortedModel);
								
							}
						
							
					
						}
						
					}

					@Override
					public void mouseEntered(MouseEvent arg0) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mouseExited(MouseEvent arg0) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mousePressed(MouseEvent arg0) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mouseReleased(MouseEvent arg0) {
						// TODO Auto-generated method stub
						
					}
	   			});
	   			
	   			dlm2Parameter = new DefaultListModel();
	   			chosenVariableList = new JList(dlm2Parameter){
	   				

	   				public String getToolTipText(MouseEvent evt) {
	   					
	   					String tooltip = "";
	   					
	   					try{
	   	   		        // Get item index
	   						int index = locationToIndex(evt.getPoint());

	   	   		        // Get item
	   	   		        Object item = getModel().getElementAt(index);
	   	   		        
	   	   		        
	   	   		        
	   	   		  for(int i=0; i < tempAgentSettingList.size();i++){
	   		        	
	   		        	if(tempAgentSettingList.get(i).agentName.equals(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.agentName)){
	   		        		
	   		        		for(int j=0; j< tempAgentSettingList.get(i).variableList.size();j++){
	   		        			
	   		        			if(tempAgentSettingList.get(i).variableList.get(j).name.equals(item)){
	   		        				
	   		        				tooltip = tempAgentSettingList.get(i).variableList.get(j).name +" ("
	   		   		        		+tempAgentSettingList.get(i).variableList.get(j).type+") "+
	   		   		        		tempAgentSettingList.get(i).variableList.get(j).description;
	   		       
	   		        			}
	   		        		}
	   		        	}
	   		        }
	   	   		  
	   	   	 return tooltip;
	   					}catch(Exception e){
	   						
	   						return null;
	   					}
	   	   		        // Return the tool tip text
	   	   		       
	   	   		    }

	   	   			};
	   	   			
	   	   			
	   	   			
	   	   		sortedModel2 = new SortedListModel(dlm2Parameter,SortedListModel.SortOrder.UNORDERED);
	   	   		chosenVariableList.setModel(sortedModel2);
	   	   		
	   	   		
	   	   		chosenVariableList.addMouseListener(new MouseListener(){
					
					public void mouseClicked(MouseEvent e) {
	   			
						System.out.println("Right click");
						
						if(SwingUtilities.isRightMouseButton(e)){
							
							System.out.println("Right click");
							
							
							if(sortedModel2.sortOrderOfList==SortedListModel.SortOrder.ASCENDING ){
								
															
								sortedModel2.setSortOrder(SortedListModel.SortOrder.DESCENDING);
								chosenVariableList.setModel(sortedModel2);
					
							}else if(sortedModel2.sortOrderOfList==SortedListModel.SortOrder.DESCENDING){
								
								sortedModel2.setSortOrder(SortedListModel.SortOrder.UNORDERED);
								chosenVariableList.setModel(sortedModel2);
								
							}else if(sortedModel2.sortOrderOfList==SortedListModel.SortOrder.UNORDERED){
								
								sortedModel2.setSortOrder(SortedListModel.SortOrder.ASCENDING);
								chosenVariableList.setModel(sortedModel2);
								
							}
						
							
					
						}
						
					}

					@Override
					public void mouseEntered(MouseEvent arg0) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mouseExited(MouseEvent arg0) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mousePressed(MouseEvent arg0) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mouseReleased(MouseEvent arg0) {
						// TODO Auto-generated method stub
						
					}
	   			});
	   	   			
	   	   		 for(int i=0; i < tempAgentSettingList.size();i++){
	   		        	
	   		        	if(tempAgentSettingList.get(i).agentName.equals(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.agentName)){
	   		        		
	   		        		for(int j=0; j< tempAgentSettingList.get(i).variableList.size();j++){
	   		        			
	   		        			if(!tempAgentSettingList.get(i).variableList.get(j).isSelectedForPlotting){
	   		        				
	   		        				dlmParameter.addElement(tempAgentSettingList.get(i).variableList.get(j).name);
	   		       
	   		        			}else{
	   		        				
	   		        				dlm2Parameter.addElement(tempAgentSettingList.get(i).variableList.get(j).name);
	   		        			}
	   		        		}
	   		        	}
	   		        }
	   	   			
	   
				
				notChosenVariableList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
				notChosenVariableList.setVisibleRowCount(20);
				
				chosenVariableList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
				chosenVariableList.setVisibleRowCount(20);
				
				JScrollPane listScrollNotChosenVariableTable = new JScrollPane(notChosenVariableList); 
				listScrollNotChosenVariableTable.setPreferredSize(new Dimension(200, 600)); 
				
				JScrollPane listScrollChosenVariableTable = new JScrollPane(chosenVariableList); 
				listScrollChosenVariableTable.setPreferredSize(new Dimension(200, 600));
				
				panelA.add(listScrollNotChosenVariableTable);
				
				JPanel panelB = new JPanel();
				panelB.setSize(200, 800);
				panelB.setLayout(new GridBagLayout());
	   			GridBagConstraints tt = new GridBagConstraints();
	   			tt.insets = new Insets( 5, 5, 5, 5);
	   			
				JButton add = new JButton("Add >>");
				
				add.addActionListener(new ActionListener() {

					
					public void actionPerformed(ActionEvent e) {
						
						int [] selectedIndices = notChosenVariableList.getSelectedIndices();
						for(int i=0; i < selectedIndices.length;i++){
							
							dlm2Parameter.addElement(dlmParameter.get(sortedModel.toUnsortedModelIndex(selectedIndices[i])));
							
							
						}
						for(int i=selectedIndices.length-1;i>=0;i--){
							
							dlmParameter.remove(sortedModel.toUnsortedModelIndex(selectedIndices[i]));
							
						}
					}
				});
				
				
				tt.gridx= 0;	
				tt.gridy= 0;
				panelB.add(add,tt);
				
				JButton remove = new JButton("<< Remove");
				tt.gridx= 0;	
				tt.gridy= 1;
				panelB.add(remove,tt);
				
				remove.addActionListener(new ActionListener() {

					
					public void actionPerformed(ActionEvent e) {
						
						int [] selectedIndices = chosenVariableList.getSelectedIndices();
						for(int i=0; i < selectedIndices.length;i++){
						
							dlmParameter.addElement(dlm2Parameter.get(sortedModel2.toUnsortedModelIndex(selectedIndices[i])));
						}
						for(int i=selectedIndices.length-1;i>=0;i--){
							
							dlm2Parameter.remove(sortedModel2.toUnsortedModelIndex(selectedIndices[i]));
						}
					}
				});
				
				
				JPanel panelC = new JPanel();
				
				panelC.setLayout(new GridBagLayout());
	   	
				panelC.add(listScrollChosenVariableTable);
				
				
				ts.gridx= 0;	
				ts.gridy= 0;
				setNewVariablesPanel.add(panelA,ts);
				
				ts.gridx= 1;	
				ts.gridy= 0;
				
				setNewVariablesPanel.add(panelB,ts);
				
				ts.gridx= 2;	
				ts.gridy= 0;
				
				setNewVariablesPanel.add(panelC,ts);
				
				JPanel panelD = new JPanel();
				
				
		    	saveVariables = new JButton("Apply Changes"); 
		    	
		    	panelD.add(saveVariables); 
		    	
		    	cancelVariables = new JButton("Cancel"); ;
		    	
		    
		    	panelD.add(cancelVariables); 
		    	ts.gridx= 3;	
				ts.gridy= 1;
				
		    	
				setNewVariablesPanel.add(panelD,ts);
	   			
		    	setNewVariables.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	   			setNewVariables.addWindowListener( new WindowAdapter(){
	    			
	    			public void windowClosing(WindowEvent e)
	    			{

	    				Object text = "Continue with changes? \n";
	    	    		
	    	    		int choice = JOptionPane.showConfirmDialog(null, text,  "Confirm Changes",JOptionPane.YES_NO_CANCEL_OPTION);
	    	    		
	    	    		if(choice == 0){
	    	    			
	    	    			
	    	    			
	    
		    	    				for(int i=0; i<dlmParameter.getSize(); i++ ){ 
		    	    					
		    	    					
		    	    					updateVariableSettings(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.listOfVariableInstances, dlmParameter.get(i).toString(), false);
		    	    					
		    	    					for(int j=0; j < tempAgentSettingList.size();j++){
		    	    	   		        	
		    	    	   		        	if(tempAgentSettingList.get(j).agentName.equals(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.agentName)){
		    	    	   		        		
		    	    	   		        		for(int k=0; k< tempAgentSettingList.get(j).variableList.size();k++){
		    	    	   		        			if(tempAgentSettingList.get(j).variableList.get(k).name.equals(dlmParameter.get(i).toString()))
		    	    	   		        			{
		    	    	   		        				tempAgentSettingList.get(j).variableList.get(k).isSelectedForPlotting= false;
		    	    	   		        				break;
		    	    	   		        			}
		    	    	   		        			
		    	    	   		        		}
		    	    	   		        		break;
		    	    	   		        	}
		    	    	   		        }
		    	    					
		    	    				
										
											
											
										}
		    	    				
		    	    
		
		    	    			for(int i=0; i<dlm2Parameter.getSize(); i++ ){   
	
			    	    			  for(int j=0; j < tempAgentSettingList.size();j++){
		    	    	   		        	
		    	    	   		        	if(tempAgentSettingList.get(j).agentName.equals(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.agentName)){
		    	    	   		        		
		    	    	   		        		for(int k=0; k< tempAgentSettingList.get(j).variableList.size();k++){
		    	    	   		        			
		    	    	   		        			if(tempAgentSettingList.get(j).variableList.get(k).name.equals(dlm2Parameter.get(i).toString()))
		    	    	   		        			{
		    	    	   		        			
			    	    	   		        			tempAgentSettingList.get(j).variableList.get(k).isSelectedForPlotting= true;
			    	    	   		        			
			    	    	   		        			boolean found = false;
						    							
						    							for(int l=0; l<agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.listOfVariableInstances.size();l++){
						    								
						    								if(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.listOfVariableInstances.get(l).instanceContainsVariable(dlm2Parameter.get(i).toString())){
						    									
						    									found = true;
						    									break;
						    								}
						    						
						    							}	
						    								
						    							if(!found){
						    									
						    									PlottingSettings.VariableInstance aR = (new PlottingSettings()).new  VariableInstance(dlm2Parameter.get(i).toString(),
						    											agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.agentName,
						    											tempAgentSettingList.get(j).variableList.get(k),
						    											agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.defaultSettings.defaultMethod,
						    											agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.defaultSettings.defaultFilter1,
						    											agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.defaultSettings.defaultFilter2,
						    											agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.defaultSettings.defaultPartitioning,
						    											"No");
						    									
						    										agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.listOfVariableInstances.add(aR);
						    										
						    				
						    								}
						    								
						    							}
			    	    	  
		    	    	   		        			}
		    	    	   		        		}
		    	    	   		        	}
		    	    	   		        }
			
	    	    			/*Redraw table*/
	    	    			agentJPanelList.get(tabbedPane.getSelectedIndex()).reDrawTable();
	    	    			/*Redraw ratio tab*/
	    	    			redrawRatioTab();
	    	    			
	    	    			setNewVariables.setVisible(false);
	    	    			setNewVariables.dispose();
	    	    			
	    	    			
	    	    		}else if(choice == 1)
	    				{
	    			
	    	    			/*Choice is no*/
	    	    			setNewVariables.setVisible(false);
	    	    			setNewVariables.dispose();
	  
	    				}

	    			}
	    			});
	   		
	   			saveVariables.addActionListener(new ActionListener(){
		    		
		    		public void actionPerformed(ActionEvent evt) {
		    		    

		    			for(int i=0; i<dlmParameter.getSize(); i++ ){ 
	    					
		    				updateVariableSettings(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.listOfVariableInstances, dlmParameter.get(i).toString(), false);
	    					
	    					for(int j=0; j < tempAgentSettingList.size();j++){
	    	   		        	
	    	   		        	if(tempAgentSettingList.get(j).agentName.equals(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.agentName)){
	    	   		        		
	    	   		        		for(int k=0; k< tempAgentSettingList.get(j).variableList.size();k++){
	    	   		        			if(tempAgentSettingList.get(j).variableList.get(k).name.equals(dlmParameter.get(i).toString()))
	    	   		        			{
	    	   		        				tempAgentSettingList.get(j).variableList.get(k).isSelectedForPlotting= false;
	    	   		        				break;
	    	   		        			}
	    	   		        			
	    	   		        		}
	    	   		        		break;
	    	   		        	}
	    	   		        }
	    					
	    				
							
								
								
							}
	    				
	    

	    			for(int i=0; i<dlm2Parameter.getSize(); i++ ){   

    	    			  for(int j=0; j < tempAgentSettingList.size();j++){
	    	   		        	
	    	   		        	if(tempAgentSettingList.get(j).agentName.equals(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.agentName)){
	    	   		        		
	    	   		        		for(int k=0; k< tempAgentSettingList.get(j).variableList.size();k++){
	    	   		        			
	    	   		        			if(tempAgentSettingList.get(j).variableList.get(k).name.equals(dlm2Parameter.get(i).toString()))
	    	   		        			{
	    	   		        			
    	    	   		        			tempAgentSettingList.get(j).variableList.get(k).isSelectedForPlotting= true;
    	    	   		        			
    	    	   		        			boolean found = false;
			    							
			    							for(int l=0; l<agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.listOfVariableInstances.size();l++){
			    								
			    								if(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.listOfVariableInstances.get(l).instanceContainsVariable(dlm2Parameter.get(i).toString())){
			    									
			    									found = true;
			    									break;
			    								}
			    						
			    							}	
			    								
			    							if(!found){
			    									
			    									PlottingSettings.VariableInstance aR = (new PlottingSettings()).new  VariableInstance(dlm2Parameter.get(i).toString(),
			    											agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.agentName,
			    											tempAgentSettingList.get(j).variableList.get(k),
			    											agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.defaultSettings.defaultMethod,
			    											agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.defaultSettings.defaultFilter1,
			    											agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.defaultSettings.defaultFilter2,
			    											agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.defaultSettings.defaultPartitioning,
			    											"No");
			    									
			    										agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.listOfVariableInstances.add(aR);
			    										
			    				
			    								}
			    								
			    							}
    	    	  
	    	   		        			}
	    	   		        		}
	    	   		        	}
	    	   		        }

    			/*Redraw table*/
    			agentJPanelList.get(tabbedPane.getSelectedIndex()).reDrawTable();
    			/*Redraw ratio tab*/
    			redrawRatioTab();
    			
    			setNewVariables.setVisible(false);
    			setNewVariables.dispose();
		    		}
		    		
		    		
		    		/*redraw the tab.*/

		    		
		    	});
	   			
	   			
	   			cancelVariables.addActionListener(new ActionListener(){
		    		
		    		public void actionPerformed(ActionEvent evt) {
		    		  
		    			setNewVariables.setVisible(false);
		    			setNewVariables.dispose();
		    		}
		    			
		    		
		    	});
	   			
	   			
	   			setNewVariablesPanel.setSize(900,800);
	   			
	   			
		   		Toolkit toolkit =  Toolkit.getDefaultToolkit ();
		 	    Dimension dim = toolkit.getScreenSize();
		 	    
		 	    int dimy = Math.min(dim.height,800);
		 	    int dimx = Math.min(dim.width,900);
		 		
		 	    setNewVariables.setSize(dimx,dimy);
		 	    
		 	    setNewVariablesScrollPane = new JScrollPane(setNewVariablesPanel);  
		 	    setNewVariablesScrollPane.setPreferredSize(new Dimension(820, 860)); 
	   			
		 	    setNewVariables.add(setNewVariablesScrollPane);
	   			
	   			setNewVariables.setBackground(Color.white);
	   			setNewVariables.setModal(true);
	   			setNewVariables.setVisible(true);
	    			
				
			}
					
		});
		
		menuVariables.add(addRemoveVariables);
		
		
		
		addRemoveAgentRatio = new JMenu("Agent Ratios") ;
		
		menuVariables.add(addRemoveAgentRatio);
		
		addAgentRatio = new JMenuItem ("Add Agent Ratio");
		addRemoveAgentRatio.add(addAgentRatio);
		
		removeAgentRatio = new JMenuItem ("Remove Agent Ratio");
		addRemoveAgentRatio.add(addAgentRatio);
		addRemoveAgentRatio.add(removeAgentRatio);
		
		

		
		menuBar.add(menuVariables);
		
		
		addAgentRatio.addActionListener(new ActionListener(){
			
			JDialog ratioDialog;
			GridBagConstraints fil;
		
			DefaultListModel variableListModel1,variableListModel2;
			JList variableList1,variableList2;
			JButton okayButton;
		
			String tempNumerator;
			String tempDenominator;
			
			JScrollPane scrollFractionTable;
			
			SortedListModel sortedModel1, sortedModel2;

			public void actionPerformed(ActionEvent arg0) {
				
					tempNumerator="";
					tempDenominator="";
					ratioDialog = new JDialog();
					ratioDialog.setModal(true);
					
		   			ratioDialog.setSize(800,500);
		   			ratioDialog.setBackground(Color.white);
		   			
					
					ratioDialog.setTitle("Define Rations of two variables of a single agent");
					ratioDialog.setLayout(new GridBagLayout());
					fil = new GridBagConstraints();
		   			fil.insets = new Insets( 5, 5, 5, 5);
		   			
		   			
		   			variableListModel1 = new DefaultListModel();
		   			variableList1 = new JList(variableListModel1);
		   			
		   			
		   			
		   			sortedModel1 = new SortedListModel(variableListModel1,SortedListModel.SortOrder.UNORDERED);
		   			variableList1.setModel(sortedModel1);
		   			
		   			
		   			variableList1.addMouseListener(new MouseListener(){
						
						public void mouseClicked(MouseEvent e) {
		   			
							System.out.println("Right click");
							
							if(SwingUtilities.isRightMouseButton(e)){
								
								System.out.println("Right click");
								
								
								if(sortedModel1.sortOrderOfList==SortedListModel.SortOrder.ASCENDING ){
									
																
									sortedModel1.setSortOrder(SortedListModel.SortOrder.DESCENDING);
									variableList1.setModel(sortedModel1);
						
								}else if(sortedModel1.sortOrderOfList==SortedListModel.SortOrder.DESCENDING){
									
									sortedModel1.setSortOrder(SortedListModel.SortOrder.UNORDERED);
									variableList1.setModel(sortedModel1);
									
								}else if(sortedModel1.sortOrderOfList==SortedListModel.SortOrder.UNORDERED){
									
									sortedModel1.setSortOrder(SortedListModel.SortOrder.ASCENDING);
									variableList1.setModel(sortedModel1);
									
								}
							
								
						
							}
							
						}

						@Override
						public void mouseEntered(MouseEvent arg0) {
							// TODO Auto-generated method stub
							
						}

						@Override
						public void mouseExited(MouseEvent arg0) {
							// TODO Auto-generated method stub
							
						}

						@Override
						public void mousePressed(MouseEvent arg0) {
							// TODO Auto-generated method stub
							
						}

						@Override
						public void mouseReleased(MouseEvent arg0) {
							// TODO Auto-generated method stub
							
						}
		   			});
		   			
		   			
		   			variableList1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		   			
		   				
			   		 for(int i=0; i < tempAgentSettingList.size();i++){
		   		        	
		   		        	if(tempAgentSettingList.get(i).agentName.equals(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.agentName)){
		   		        		
		   		        		for(int j=0; j< tempAgentSettingList.get(i).variableList.size();j++){
		
		   		        			variableListModel1.addElement(tempAgentSettingList.get(i).variableList.get(j).name);
		   		 
		   		        		}
		   		        	}
		   		        }
	   				
	   				
	   				variableList1.addMouseListener(new MouseListener(){
						
						public void mouseClicked(MouseEvent e) {
							
							if(e.getClickCount()==2){

								System.out.println("Two Clicks in row");
								
								try{
								
									tempNumerator = variableList1.getSelectedValue().toString();
									
									/*Redraw the table*/
			        				ratioDialog.remove(scrollFractionTable);
			        				ratioDialog.validate();
			        				
			        				
			        				FractionTable fractionTable = new FractionTable(tempNumerator,tempDenominator);
				 	   				
		   		 	   				scrollFractionTable = fractionTable.listScrollFraction;
		   		 	   				scrollFractionTable.setPreferredSize(new Dimension(200, 60)); 
				        				
			   		 	   			fil.gridx =3;fil.gridy =3;
			   		   				ratioDialog.add(scrollFractionTable,fil);
				        				
			   		   				ratioDialog.validate();
			   		   				
			   		   				if(!tempNumerator.equals("")&&!tempDenominator.equals("")){
			   		   					okayButton.setEnabled(true);
			   		   				}
		   		   				
								}catch(Exception ex){
									
									
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
	   				

	   				
	   				
	   				JLabel label11 = new JLabel("Numerator:");
	   				
	   				fil.gridx =0;fil.gridy =1;
	   				ratioDialog.add(label11,fil);
	  

	   				fil.gridx =2;fil.gridy =1;
	   				
	   				JScrollPane variableListScroll = new JScrollPane(variableList1);
	   				variableListScroll.setPreferredSize(new Dimension(200,150));
	   				
	   				ratioDialog.add(variableListScroll,fil);

	   
		   			
		   			variableListModel2 = new DefaultListModel();
		   			variableList2 = new JList(variableListModel2);
		   			
		   			
		   			sortedModel2 = new SortedListModel(variableListModel2,SortedListModel.SortOrder.UNORDERED);
		   			variableList2.setModel(sortedModel2);
		   			
		   			
		   			variableList2.addMouseListener(new MouseListener(){
						
						public void mouseClicked(MouseEvent e) {
		   			
							System.out.println("Right click");
							
							if(SwingUtilities.isRightMouseButton(e)){
								
								System.out.println("Right click");
								
								
								if(sortedModel2.sortOrderOfList==SortedListModel.SortOrder.ASCENDING ){
									
																
									sortedModel2.setSortOrder(SortedListModel.SortOrder.DESCENDING);
									variableList2.setModel(sortedModel2);
						
								}else if(sortedModel2.sortOrderOfList==SortedListModel.SortOrder.DESCENDING){
									
									sortedModel2.setSortOrder(SortedListModel.SortOrder.UNORDERED);
									variableList2.setModel(sortedModel2);
									
								}else if(sortedModel2.sortOrderOfList==SortedListModel.SortOrder.UNORDERED){
									
									sortedModel2.setSortOrder(SortedListModel.SortOrder.ASCENDING);
									variableList2.setModel(sortedModel2);
									
								}
							
								
						
							}
							
						}

						@Override
						public void mouseEntered(MouseEvent arg0) {
							// TODO Auto-generated method stub
							
						}

						@Override
						public void mouseExited(MouseEvent arg0) {
							// TODO Auto-generated method stub
							
						}

						@Override
						public void mousePressed(MouseEvent arg0) {
							// TODO Auto-generated method stub
							
						}

						@Override
						public void mouseReleased(MouseEvent arg0) {
							// TODO Auto-generated method stub
							
						}
		   			});
		   			
		   			variableList2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		   			
		    		 for(int i=0; i < tempAgentSettingList.size();i++){
		   		        	
		   		        	if(tempAgentSettingList.get(i).agentName.equals(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.agentName)){
		   		        		
		   		        		for(int j=0; j< tempAgentSettingList.get(i).variableList.size();j++){
		
		   		        			variableListModel2.addElement(tempAgentSettingList.get(i).variableList.get(j).name);
		   		 
		   		        		}
		   		        	}
		   		        }	
	   				
	   				
	   				variableList2.addMouseListener(new MouseListener(){
						
						public void mouseClicked(MouseEvent e) {
							
							
							if(e.getClickCount()==2){
							

								System.out.println("One Click in row");
								
								try{
								
									tempDenominator = variableList2.getSelectedValue().toString();
					   		        				
	   		        				/*Redraw the table*/
	   		        				ratioDialog.remove(scrollFractionTable);
	   		        				ratioDialog.validate();
	   		        				
	   		        				
	   		        				FractionTable fractionTable = new FractionTable(tempNumerator,tempDenominator);
	   		 	   				
		   		 	   				scrollFractionTable = fractionTable.listScrollFraction;
		   		 	   				scrollFractionTable.setPreferredSize(new Dimension(200, 60)); 
	   		        				
			   		 	   			fil.gridx =3;fil.gridy =3;
			   		   				ratioDialog.add(scrollFractionTable,fil);
	   		        				
			   		   				ratioDialog.validate();
			   		   				
				   		   			if(!tempNumerator.equals("")&&!tempDenominator.equals("")){
			   		   					okayButton.setEnabled(true);
			   		   				}
	   		        				
	   		        			}catch(Exception ex){
	   		        				
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
	   				

	   				JLabel label21 = new JLabel("Denominator:");
	   				
	   				fil.gridx =0;fil.gridy =4;
	   				ratioDialog.add(label21,fil);
	   						
	   				
	   				fil.gridx =2;fil.gridy =4;
	   				JScrollPane variableListScroll2 = new JScrollPane(variableList2);
	   				variableListScroll2.setPreferredSize(new Dimension(200,150));
	   				ratioDialog.add(variableListScroll2,fil);
	   				
	   				/*XXX*/
	   				
	   				FractionTable fractionTable = new FractionTable("","");
	   				
	   				scrollFractionTable = fractionTable.listScrollFraction;
	   				scrollFractionTable.setPreferredSize(new Dimension(200, 60)); 
	   		
	   			
	   				
	   				fil.gridx =3;fil.gridy =3;
	   				ratioDialog.add(scrollFractionTable,fil);
	   				

	   				okayButton = new JButton("OK");
	   				okayButton.setEnabled(false);
	   				
	   				JButton discardButton = new JButton("Discard");
	   				
	   				okayButton.addActionListener(new ActionListener(){

						public void actionPerformed(ActionEvent e) {
							
							Variable numerator = null;
							Variable denumerator = null;

							
							if(!tempNumerator.equals("")&&!tempDenominator.equals("")){
								
								for(int i=0; i < tempAgentSettingList.size();i++){
				   		        	
				   		        	if(tempAgentSettingList.get(i).agentName.equals(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.agentName)){
				   		        		
				   		        		for(int j=0; j< tempAgentSettingList.get(i).variableList.size();j++){
				
				   		        			if(tempNumerator.equals((tempAgentSettingList.get(i).variableList.get(j).name))){
				   		        				
				   		        				numerator = tempAgentSettingList.get(i).variableList.get(j);
				   		        				break;
				   		        				
				   		        			}
				   		 
				   		        		}
				   		        		break;
				   		        	}
				   		        }
								
								
								for(int i=0; i < tempAgentSettingList.size();i++){
				   		        	
				   		        	if(tempAgentSettingList.get(i).agentName.equals(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.agentName)){
				   		        		
				   		        		for(int j=0; j< tempAgentSettingList.get(i).variableList.size();j++){
				
				   		        			if(tempDenominator.equals((tempAgentSettingList.get(i).variableList.get(j).name))){
				   		        				
				   		        				denumerator = tempAgentSettingList.get(i).variableList.get(j);
				   		        				break;
				   		        				
				   		        			}
				   		 
				   		        		}
				   		        		break;
				   		        	}
				   		        }
								
								try{
									
									PlottingSettings.AgentRatio tempAgentRatio = (new PlottingSettings()).new AgentRatio(numerator,denumerator);
									
									PlottingSettings.VariableInstance aR = (new PlottingSettings()).new  VariableInstance(tempAgentRatio.ratioName,
											agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.agentName,
											tempAgentRatio,
											agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.defaultSettings.defaultMethod,
											agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.defaultSettings.defaultFilter1,
											agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.defaultSettings.defaultFilter2,
											agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.defaultSettings.defaultPartitioning,
											"No");
									
											agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.listOfVariableInstances.add(aR);
											
											agentJPanelList.get(tabbedPane.getSelectedIndex()).reDrawTable();
					    	    			/*Redraw ratio tab*/
					    	    			redrawRatioTab();
					    	    			
					    	    			ratioDialog.setVisible(false);
					    	    			ratioDialog.dispose();
									
									
								}catch(Exception ex){
									
									
								}
								
								
								
								
								
								
							}
							
								
							
							
							
					
						}
	   					
	   						
	   				});
	   				
	   				
	   				
	   				discardButton.addActionListener(new ActionListener(){

						public void actionPerformed(ActionEvent e) {

							ratioDialog.dispose();
							ratioDialog.setVisible(false);
							
						}
	   					
	   						
	   				});
	   				
	   				
	   				fil.gridx =4;fil.gridy =4;
	   				ratioDialog.add(okayButton,fil);
	   				
	   				fil.gridx =5;fil.gridy =4;
	   				ratioDialog.add(discardButton,fil);
					
	   				ratioDialog.setVisible(true);
				
				
				
				
				
			}
			
		});
		
		
		
		removeAgentRatio.addActionListener(new ActionListener(){

			DefaultListModel ratioListModel;
			JList ratioList;
			JButton removeButton;
			JButton	discardButton;
			JDialog ratioRemoveDialog;
			
			public void actionPerformed(ActionEvent arg0) {
				
				ratioRemoveDialog = new JDialog();
				ratioRemoveDialog.setModal(true);
				
				ratioRemoveDialog.setSize(700,400);
				ratioRemoveDialog.setBackground(Color.white);
	   			
				
				ratioRemoveDialog.setTitle("Remove Ratios");
				ratioRemoveDialog.setLayout(new GridBagLayout());
				
				GridBagConstraints fil = new GridBagConstraints();
	   			fil.insets = new Insets( 5, 5, 5, 5);
				
				
				ratioListModel = new DefaultListModel();
				ratioList = new JList(ratioListModel);
	   			
				ratioList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	   				
   				for(int i=0; i<agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.listOfVariableInstances.size();i++){
					
   					if(!agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.listOfVariableInstances.get(i).isVariable)
   						ratioListModel.addElement(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.listOfVariableInstances.get(i).instanceName);
		
				}
   				
   				fil.gridx = 0;fil.gridy = 0; 
   				
   				
   				JScrollPane ratioListScroll = new JScrollPane(ratioList);
   				ratioListScroll.setPreferredSize(new Dimension(200,150));
   				
   				ratioRemoveDialog.add(ratioListScroll,fil);
   				
   				removeButton = new JButton("Remove");
   				discardButton = new JButton("Discard");
   				
   				fil.gridx = 1;fil.gridy = 1; 
   				ratioRemoveDialog.add(removeButton,fil);
   				
   				fil.gridx = 2;fil.gridy = 1; 
   				ratioRemoveDialog.add(discardButton,fil);

   				/*delete the selected items from the Ratio list, and close the dialog*/
   				removeButton.addActionListener(new ActionListener(){

	
					public void actionPerformed(ActionEvent arg0) {
					
						int[] selectedIndices = ratioList.getSelectedIndices();
				
						
						
						for(int i=0; i < selectedIndices.length;i++){
							
							
							
							updateVariableSettings(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.listOfVariableInstances, ratioListModel.get(selectedIndices[i]).toString(), true);
							
							for(int j=0; j<agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.listOfVariableInstances.size();j++){
								
								
			   					if(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.listOfVariableInstances.get(j).instanceName.equals(ratioListModel.get(selectedIndices[i]))){
			   						ratioListModel.addElement(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.listOfVariableInstances.get(j).instanceName);
			   						agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.listOfVariableInstances.remove(j);
			   						
			   						
			   						break;
			   					}
							}
							
							
							
							/*TODO*/
						
						

					}
						
						
						ratioRemoveDialog.dispose();
						ratioRemoveDialog.setVisible(false);
						
						agentJPanelList.get(tabbedPane.getSelectedIndex()).reDrawTable();
    	    			/*Redraw ratio tab*/
    	    			redrawRatioTab();
   					
					}
   				
   				});
   				
   				/*Close the window*/
   				discardButton.addActionListener(new ActionListener(){

   					
					public void actionPerformed(ActionEvent arg0) {

						ratioRemoveDialog.dispose();
						ratioRemoveDialog.setVisible(false);

					}
   				
   				});
   				
   				
   				ratioRemoveDialog.setVisible(true);
				
			}
				
			
		});
		
		
		
		menuSettings = new JMenu("Settings");
	
		editFilter = new JMenuItem("Filter"); 
		menuSettings.add(editFilter);
		editFilter.addActionListener( new ActionListener(){

			//boolean tempApplyFilters = agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.plottingSelections.filters.applyFilters;
			public void actionPerformed(ActionEvent arg0) {
	
				
				new EditFilters(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.agentName);
				/*Redraw table*/
				
				for(int i=0; i < agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.filter.size();i++)
				{
				
					agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.filter.remove(i);
					i--;
				
				}
				
				for(int i=0; i < PlottingSettings.listOfAgentsVariableInstances.size();i++){
					
					if(PlottingSettings.listOfAgentsVariableInstances.get(i).agentName.equals(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.agentName)){
						
						for(int j=0; j< PlottingSettings.listOfAgentsVariableInstances.get(i).filter.size();j++){
						
							agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.filter.add(PlottingSettings.listOfAgentsVariableInstances.get(i).filter.get(j).clone());
							
						}
						
					}
					
				}
				
				
				 
				
				agentJPanelList.get(tabbedPane.getSelectedIndex()).reDrawTable();

				
			}

		});
				
				
				

		
		editPartitioning = new JMenuItem("Partitioning");
		menuSettings.add(editPartitioning);
		editPartitioning.addActionListener( new ActionListener(){
		

				ArrayList<PlottingSettings.Partitioning> tempPartitionings ;
				private JDialog partitioningDialog;
				private JPanel upperPanel;
				private JPanel lowerPanel = new JPanel();
				private  DefaultListModel dlmPartitioning; 
	   			private JList unSelectPartitioningVar;
	   			private  DefaultListModel dlm2Partitioning;
	   			private JList selectPartitioningVar;
	   			private JCheckBox withTotal ;
	   			private DefaultListModel dlmAddPartition;
	   			private JList addPartition;
	   			private ArrayList<String> values;
	   			private String partitioningName;
	   			
	   			SortedListModel sortedModel;
	   			
				public void actionPerformed(ActionEvent arg0) {
					
					tempPartitionings = agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.partitioning;

					partitioningDialog = new JDialog();
					partitioningDialog.setTitle("Partitioning for Agent "+agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.agentName);
					partitioningDialog.setLayout(new GridBagLayout());
					final GridBagConstraints part = new GridBagConstraints();
		   			part.insets = new Insets( 5, 5, 5, 5);
					
		   			partitioningDialog.setSize(700,400);
		   			partitioningDialog.setBackground(Color.white);
		   			partitioningDialog.setModal(true);
					
					upperPanel = new JPanel();
					upperPanel.setLayout(new GridBagLayout());
					final GridBagConstraints upP = new GridBagConstraints();
					upP.insets = new Insets( 5, 5, 5, 5);

			    	part.gridx=0; part.gridy =0;
			    	partitioningDialog.add(upperPanel,part);
			    	
			    	
			    	lowerPanel = new JPanel();
			    	lowerPanel.setLayout(new GridBagLayout());
					final GridBagConstraints lowerP = new GridBagConstraints();
					lowerP.insets = new Insets( 5, 5, 5, 5);
			    	
					
					dlmPartitioning = new DefaultListModel();
		   			unSelectPartitioningVar = new JList(dlmPartitioning){
		   				

					public String getToolTipText(MouseEvent evt) {
		   		        // Get item index
							int index = locationToIndex(evt.getPoint());

		   		        // Get item
		   		        Object item = getModel().getElementAt(index);
		   		        
		   		        String tooltip = "";
		   		        
		   		        for(int i=0; i < tempAgentSettingList.size();i++){
		   		        	
		   		        	if(tempAgentSettingList.get(i).agentName.equals(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.agentName)){
		   		        		
		   		        		for(int j=0; j< tempAgentSettingList.get(i).variableList.size();j++){
		   		        			
		   		        			if(tempAgentSettingList.get(i).variableList.get(j).name.equals(item)){
		   		        				
		   		        				tooltip = tempAgentSettingList.get(i).variableList.get(j).name +" ("
		   		   		        		+tempAgentSettingList.get(i).variableList.get(j).type+") "+
		   		   		        		tempAgentSettingList.get(i).variableList.get(j).description;
		   		       
		   		        			}	
		   		        		}
		   		        	}
		   		        }

		   		        // Return the tool tip text
		   		        return tooltip;
		   		    }
		   			};
		   			
		   			
		   			
		   		 sortedModel = new SortedListModel(dlmPartitioning,SortedListModel.SortOrder.UNORDERED);
		   		unSelectPartitioningVar.setModel(sortedModel);
		   			
		   			
		   		unSelectPartitioningVar.addMouseListener(new MouseListener(){
						
						public void mouseClicked(MouseEvent e) {
		   			
							System.out.println("Right click");
							
							if(SwingUtilities.isRightMouseButton(e)){
								
								System.out.println("Right click");
								
								
								if(sortedModel.sortOrderOfList==SortedListModel.SortOrder.ASCENDING ){
									
																
									sortedModel.setSortOrder(SortedListModel.SortOrder.DESCENDING);
									unSelectPartitioningVar.setModel(sortedModel);
						
								}else if(sortedModel.sortOrderOfList==SortedListModel.SortOrder.DESCENDING){
									
									sortedModel.setSortOrder(SortedListModel.SortOrder.UNORDERED);
									unSelectPartitioningVar.setModel(sortedModel);
									
								}else if(sortedModel.sortOrderOfList==SortedListModel.SortOrder.UNORDERED){
									
									sortedModel.setSortOrder(SortedListModel.SortOrder.ASCENDING);
									unSelectPartitioningVar.setModel(sortedModel);
									
								}
							
								
						
							}
							
						}

						@Override
						public void mouseEntered(MouseEvent arg0) {
							// TODO Auto-generated method stub
							
						}

						@Override
						public void mouseExited(MouseEvent arg0) {
							// TODO Auto-generated method stub
							
						}

						@Override
						public void mousePressed(MouseEvent arg0) {
							// TODO Auto-generated method stub
							
						}

						@Override
						public void mouseReleased(MouseEvent arg0) {
							// TODO Auto-generated method stub
							
						}
		   			});
		   			
		   			
		   			
		   			dlm2Partitioning = new DefaultListModel();
		   			selectPartitioningVar = new JList(dlm2Partitioning){
		   				

					public String getToolTipText(MouseEvent evt) {
		   		        // Get item index
						String tooltip = "";
						
						try{
							int index = locationToIndex(evt.getPoint());

		   		        // Get item
		   		        Object item = getModel().getElementAt(index);
		   		        
		   		        
		   		        
		   		        System.out.println(item);
		   		        
		   		     System.out.println(tempPartitionings.size());
		   		        
		   		        for(int j = 0; j<tempPartitionings.size();j++)
		   		        {
		   		        	
		   		       System.out.println("partitioningName"+tempPartitionings.get(j).partitioningName);
		   		        	if(tempPartitionings.get(j).partitioningName.equals(item.toString())){
		   		 
		   		        		tooltip = tempPartitionings.get(j).partitioningName+"  (values: ";
		   		        		
		   		        		for(int i=0; i<tempPartitionings.get(j).partitioningValues.size();i++){
		   		        			
		   		        			tooltip = tooltip+tempPartitionings.get(j).partitioningValues.get(i);
		   		        			if(i<tempPartitionings.get(j).partitioningValues.size()-1)
		   		        				tooltip = tooltip+",";
		   		        			else
		   		        				tooltip = tooltip+")";
		   		        		}
		   		        		break;
		   		        	}
		   		        		
		   		        	
		    	
		   		        }

						}catch(Exception e){
							
							System.out.println(e+" in getToolTipText" );
							
						}
		   		        // Return the tool tip text
		   		        return tooltip;
		   		    }
		   			};
		   			
		   			
		   		  for(int i=0; i < tempAgentSettingList.size();i++){
			        	
			        	if(tempAgentSettingList.get(i).agentName.equals(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.agentName)){
			        		
			        		for(int j=0; j< tempAgentSettingList.get(i).variableList.size();j++){
			        			
			        			if(tempAgentSettingList.get(i).variableList.get(j).type.equals("int")){
			        				
			        				boolean found = false;
			        				
			        				for(int k=0; k<tempPartitionings.size();k++)
					   				{
						   				if(tempAgentSettingList.get(i).variableList.get(j).name.equals(tempPartitionings.get(k).variableName)){
						   				
						   					dlm2Partitioning.addElement(tempPartitionings.get(k).partitioningName);
						   					found = true;
						   					break;
						   				
						   				}	
						   				
					   				}
					   				if(!found)
					   					dlmPartitioning.addElement(tempAgentSettingList.get(i).variableList.get(j).name);
			        				
			        			}
			        			
			        		}
			        		
			        	}
		   		  	}
		   			
		   			

		   			unSelectPartitioningVar.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		   			unSelectPartitioningVar.setVisibleRowCount(10);
		   			
		   			selectPartitioningVar.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		   			selectPartitioningVar.setVisibleRowCount(10);

					JScrollPane listScrollUnSelectPartitioningVar = new JScrollPane(unSelectPartitioningVar); 
					listScrollUnSelectPartitioningVar.setPreferredSize(new Dimension(200, 200)); 
					
					JScrollPane listScrollSelectPartitioningVar = new JScrollPane(selectPartitioningVar); 
					listScrollSelectPartitioningVar.setPreferredSize(new Dimension(200, 200)); 
					
					lowerP.gridx=0; lowerP.gridy =0;
					lowerPanel.add(listScrollUnSelectPartitioningVar,lowerP);
					
					JPanel middlePanel = new JPanel();
					
					middlePanel.setLayout(new GridBagLayout());
		   			final GridBagConstraints midP= new GridBagConstraints();
		   			midP.insets = new Insets( 5, 5, 5, 5);
		   			
		   			
		   			JButton addButton = new JButton("Add");
		   			JButton removeButton = new JButton("Remove");
		   			
		   			
		   			addButton.addActionListener(new ActionListener(){
		   				
		   				JTextField enterName;

						public void actionPerformed(ActionEvent arg0) {
					
							enterValue = new JDialog();
							enterValue.setTitle("Select Partitioning");
							enterValue.setLayout(new FlowLayout());
							enterValue.setSize(500,250);
							enterValue.setBackground(Color.white);
							enterValue.setModal(true);
							
							enterValue.setLayout(new GridBagLayout());
				   			final GridBagConstraints enterV= new GridBagConstraints();
				   			enterV.insets = new Insets( 5, 5, 5, 5);
				   			
				   			
				   			JLabel enterNameLabel = new JLabel("Enter Partitioning Name:");
				   			enterName = new JTextField(10);
				   			enterName.setText(dlmPartitioning.getElementAt(sortedModel.toUnsortedModelIndex(unSelectPartitioningVar.getSelectedIndex())).toString());
				   			enterName.selectAll();
				   			partitioningName = enterName.getText().toString();
				   			
				   			enterName.addActionListener(new ActionListener(){
					    		
					    		public void actionPerformed(ActionEvent evt) {
					    			
					    			enterName.selectAll();
					    			
					    			/*Check if the name is already used*/
					    			boolean nameUsed = false;
					    			
					    			for(int i= 0; i<dlm2Partitioning.getSize(); i++ ){
					    				
					    				System.out.println( enterName.getText().toString() +"   "+dlm2Partitioning.getElementAt(i).toString());
					    				
					    				if(enterName.getText().toString().equals(dlm2Partitioning.getElementAt(i).toString())){
					    					
					    					JOptionPane.showMessageDialog(null, "Identifier already used!");
					    					nameUsed = true;
					    					enterName.selectAll();
					    					
					    				}
					    				
					    			}
					    			
					    			if(!nameUsed);
					    				partitioningName = enterName.getText().toString();

					    		}
					    		
					    	});
				   			
				   			
				   			
				   			enterV.gridx=0;enterV.gridy=0;
				   			enterValue.add(enterNameLabel,enterV);
				   			
				   			enterV.gridx=1;enterV.gridy=0;
				   			enterValue.add(enterName,enterV);
				   			
				   			
							JLabel withTotalLabel = new JLabel("With Total");
							enterV.gridx=2;enterV.gridy=0;
				   			enterValue.add(withTotalLabel,enterV);
				   	
							withTotal = new JCheckBox();
							enterV.gridx=3;enterV.gridy=0;
				   			enterValue.add(withTotal,enterV);
				   			
				   			dlmAddPartition = new DefaultListModel();
				   			addPartition = new JList(dlmAddPartition);
				   			
				   	
				   			
				   			withTotal.addItemListener(new ItemListener(){

								public void itemStateChanged(ItemEvent arg0) {
									
									if(withTotal.isSelected()){
										
										try{
								
											for(int i=0; i<dlmAddPartition.getSize();i++){
												System.out.println("i: "+i+" c"+(dlmAddPartition.getSize()-i));
												dlmAddPartition.addElement(dlmAddPartition.get(dlmAddPartition.getSize()-i));
											}
										
										
										
										}catch(Exception e){
											
											System.out.println("List empty");
											
										}
										
										
											dlmAddPartition.add(0,"Total");
										
										System.out.println("  "+dlmAddPartition.getSize());
										
									}else{
										
										dlmAddPartition.remove(0);
										
									}
									
									
								}
				   				
				   			});
				   			
				   			addPartition.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
				   			addPartition.setVisibleRowCount(10);
				   			
				   			addPartition.setVisibleRowCount(10);
				   			
				   			if(withTotal.isSelected()){
				   				dlmAddPartition.addElement("Total");
				   			}
							
							JLabel text1 = new JLabel("Enter Partitioning Values:");
							enterV.gridx=0;enterV.gridy=1;
							enterValue.add(text1,enterV);
							
							partitioningValue = new JTextField(4);
							enterV.gridx=1;enterV.gridy=1;
							enterValue.add(partitioningValue,enterV);
						    /*Add ActionListener*/
							partitioningValue.addActionListener(new ActionListener(){
						    		
						    		public void actionPerformed(ActionEvent evt) {
						    			
						    			try{
						    				Integer.parseInt(partitioningValue.getText());
							    			dlmAddPartition.addElement(partitioningValue.getText().toString());
							    			partitioningValue.setText("");
							    			
						    			}catch(Exception e){
						    				
						    				JOptionPane.showMessageDialog(null, "Not an integer!");
						    				partitioningValue.setText("");
						    				
						    			}
						    			
						    		}
						    		
						    	});
							
							
							
							JScrollPane listScrolladdPartition = new JScrollPane(addPartition); 
							listScrolladdPartition.setPreferredSize(new Dimension(50, 100)); 
							
							enterV.gridx=2;enterV.gridy=1;
				   			enterValue.add(listScrolladdPartition,enterV);
				   			
				   			JButton removeButton = new JButton("Remove");
				   			
				   			enterV.gridx=3;enterV.gridy=1;
				   			enterValue.add(removeButton,enterV);
				   			
				   			removeButton.addActionListener(new ActionListener(){

								
								public void actionPerformed(ActionEvent arg0) {
								
									if(addPartition.getSelectedIndex()> 0 || (!withTotal.isSelected()&&addPartition.getSelectedIndex()==0)){
										
										dlmAddPartition.remove(addPartition.getSelectedIndex());
										
									}
									
								}
				   				
				
				   			});
				   			
				   			JButton applyButton = new JButton("Apply");
				   			
				   			enterV.gridx=3;enterV.gridy=2;
				   			enterValue.add(applyButton,enterV);
				   			
				   			
				   			applyButton.addActionListener(new ActionListener(){

								public void actionPerformed(ActionEvent arg0) {

								values = new ArrayList<String>();
									
								for(int i=0; i <dlmAddPartition.getSize();i++ ){
									
									values.add(dlmAddPartition.getElementAt(i).toString()); 
									
									
								}
								
								
								/*Check names:*/
								
								boolean nameUsed = false;
				    			
				    			for(int i= 0; i<dlm2Partitioning.getSize(); i++ ){
				    				
				    				System.out.println( enterName.getText().toString() +"   "+dlm2Partitioning.getElementAt(i).toString());
				    				
				    				if(enterName.getText().toString().equals(dlm2Partitioning.getElementAt(i).toString())){
				    					enterName.selectAll();
				    					JOptionPane.showMessageDialog(null, "Identifier already used!");
				    					nameUsed = true;
				    					
				    				}
				    				
				    			}
				    			
				    			
								
								
				    			if(!nameUsed){
								
								
									/*Apply only if the list is not empty*/
									if((values.size()>1 && values.get(0).equals("Total"))||(values.size()>0&& !values.get(0).equals("Total") ))
									{
										tempPartitionings.add( (new PlottingSettings()).new Partitioning(enterName.getText().toString(), dlmPartitioning.getElementAt(unSelectPartitioningVar.getSelectedIndex()).toString(),values));
										
										System.out.println(partitioningName);
							   			dlm2Partitioning.addElement( enterName.getText().toString());
							   			
							   			enterValue.dispose();
										enterValue.setVisible(false);
								   		
									}else
									{
										enterValue.dispose();
										enterValue.setVisible(false);
									}
				    			}
				    			
								}
				   			});
				   			
				   			
				   			

							enterValue.setVisible(true);
							
						}
		   				
		   				
		   				
		   			});
		   			
		   			
		   			
		   			removeButton.addActionListener(new ActionListener(){

						public void actionPerformed(ActionEvent arg1) {
							
							try{
							
							int index = selectPartitioningVar.getSelectedIndex();

						
							for(int i=0; i< tempPartitionings.size();i++){
								
								if(tempPartitionings.get(i).variableName.equals(dlm2Partitioning.get(index).toString())){
									
									tempPartitionings.remove(index);
									break;
								}
								
							}
							dlm2Partitioning.remove(index);
							
							}catch(Exception ex){
								
								System.out.println(ex);
								
							}
						
						}
		   				
		   				
		   				
		   			});
		   			
		   			midP.gridx=0; midP.gridy =0;
		   			middlePanel.add(addButton,midP);
		   			midP.gridx=0; midP.gridy =1;
		   			middlePanel.add(removeButton,midP);
		   			
		   		
					
					
		   			lowerP.gridx=1; lowerP.gridy =0;
					lowerPanel.add(middlePanel,lowerP);
					
					
					lowerP.gridx=2; lowerP.gridy =0;
					lowerPanel.add(listScrollSelectPartitioningVar,lowerP);
					
					
					
					
					JButton getContinued = new JButton("Continue");
					lowerP.gridx=3; lowerP.gridy =1;
					lowerPanel.add(getContinued,lowerP);
					
					
					part.gridx=0; part.gridy =1;
					partitioningDialog.add(lowerPanel,part);
					
					getContinued.addActionListener(new ActionListener(){

						public void actionPerformed(ActionEvent arg0) {
							
							agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.partitioning = tempPartitionings;
							
							
					
							/*Redraw table*/
							agentJPanelList.get(tabbedPane.getSelectedIndex()).reDrawTable();
			    			/*Close window*/
							partitioningDialog.setVisible(false);
							partitioningDialog.dispose();
					
							
						}
						
						
						
					});
				
					
					partitioningDialog.setVisible(true);
			
				
				
			}
			
		});
		
		
		
		defaultSettings = new JMenuItem("Default Settings");
		menuSettings.add(defaultSettings);
		defaultSettings.addActionListener( new ActionListener(){


				JComboBox methodCB, filter1CB, filter2CB, partitioningCB;
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
					
					JLabel labelDefaultMethod = new JLabel("Default Settings:");
					
					enterDS.gridx=0;
					enterDS.gridy=0;
			    	defaultSettingsDialog.add(labelDefaultMethod,enterDS);
					
			    	String[] listMethods;
			    	
			    	System.out.println(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.weighting.size());
			    		
			    		try{
							String [] lM = new String[6+agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.weighting.size()];
							
							lM[0] =  "Mean";
							
							for(int i=0;i<agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.weighting.size();i++){
								
								lM[i+1]  = "Mean ("+agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.weighting.get(i).weightingVariable+")";
								
							}
							lM[agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.weighting.size()+1]  = "Median";
							lM[agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.weighting.size()+2]  = "Standard Deviation";
							lM[agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.weighting.size()+3]  = "Standard Deviation percent";
							lM[agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.weighting.size()+4]  = "Sum";
							lM[agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.weighting.size()+5]  = "Minimum";
							lM[agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.weighting.size()+6]  = "Maximum";
							listMethods = lM;
						
						}catch(NullPointerException eNP){
							
							String [] lM =  {"Mean", "Median","Standard Deviation","Standard Deviation percent","Sum","Minimum","Maximum"};
							listMethods = lM;
						}
			    		
			    		
			    		
			    	
			    	methodCB = new JComboBox(listMethods);	
			    	methodCB.setSelectedItem(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.defaultSettings.defaultMethod);
			    	 
			    	
			    	enterDS.gridx=1;
					enterDS.gridy=0;
			    	defaultSettingsDialog.add(methodCB,enterDS);
				
			    	String [] filter ; 
					try{
					
						filter = new String [agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.filter.size()+1];
					}catch(Exception e){
					
						
						filter = new String [agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.filter.size()+1];
					}
					filter[0] = "No Filter";
					
					for(int i= 1; i<agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.filter.size()+1; i++){
						
						filter[i] = agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.filter.get(i-1).filterName;
						
					}
					
					JLabel labelDefaultFilter1 = new JLabel("Default Filter 1:");
					
					enterDS.gridx=0;
					enterDS.gridy=1;
			    	defaultSettingsDialog.add(labelDefaultFilter1,enterDS);
					
					filter1CB = new JComboBox(filter);
					
					filter1CB.setSelectedItem(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.defaultSettings.defaultFilter1);
					
					enterDS.gridx=1;
					enterDS.gridy=1;
			    	defaultSettingsDialog.add(filter1CB,enterDS);
					
					JLabel labelDefaultFilter2 =  new JLabel("Default Filter 2:");
					
					enterDS.gridx=0;
					enterDS.gridy=2;
			    	defaultSettingsDialog.add(labelDefaultFilter2,enterDS);
					
					filter2CB = new JComboBox(filter);
					filter2CB.setSelectedItem(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.defaultSettings.defaultFilter2);
					enterDS.gridx=1;
					enterDS.gridy=2;
			    	defaultSettingsDialog.add(filter2CB,enterDS);
					
					
					String [] partitioning ; 
					try{
					
						partitioning = new String [agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.partitioning.size()+1];
					}catch(Exception e){
					
						
						partitioning = new String [1];
					}
					partitioning[0] = "No Partitioning";
					
					System.out.println("agent.plottingSelections.partitioning.Partitioning.size()"+agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.partitioning.size());
					
						for(int i= 1; i<agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.partitioning.size()+1; i++){
							
							partitioning[i] = agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.partitioning.get(i-1).variableName;
							
						}
				
					
					JLabel labelDefaultPartitioning =  new JLabel("Default Partitioning:");
					
					enterDS.gridx=0;
					enterDS.gridy=3;
			    	defaultSettingsDialog.add(labelDefaultPartitioning,enterDS);
					
					
					partitioningCB = new JComboBox(partitioning);
					partitioningCB.setSelectedItem(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.defaultSettings.defaultPartitioning);
					
					enterDS.gridx=1;
					enterDS.gridy=3;
			    	defaultSettingsDialog.add(partitioningCB,enterDS);
			    	
			    	JButton ok = new JButton("OK");
			    	
			    	enterDS.gridx=4;
			    	enterDS.gridy=4;
			    	defaultSettingsDialog.add(ok,enterDS);
			    	
			    	ok.addActionListener(new ActionListener(){

						public void actionPerformed(ActionEvent e) {
							
							System.out.println("methodCB.getSelectedItem().toString()  "+methodCB.getSelectedItem().toString());
							
							agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.defaultSettings.defaultMethod = methodCB.getSelectedItem().toString();
							agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.defaultSettings.defaultFilter1 = filter1CB.getSelectedItem().toString();
							agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.defaultSettings.defaultFilter2 = filter2CB.getSelectedItem().toString();
							agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.defaultSettings.defaultPartitioning = partitioningCB.getSelectedItem().toString();
							
			    			defaultSettingsDialog.setVisible(true);
			    			defaultSettingsDialog.dispose();
							
						}
			    		
			    		
			    	});
			     
					
			    	defaultSettingsDialog.setVisible(true);

				
			}
			
			
		});
		
		
		
		weighting = new JMenuItem("Select Weighting Factors for Mean");
		menuSettings.add(weighting);
		weighting.addActionListener( new ActionListener(){
			
			JDialog weightingDialog;
			JPanel upperPanel,lowerPanel;
			SortedListModel sortedModel;  
			
			ArrayList<PlottingSettings.Weighting> tempWeighting = new ArrayList<PlottingSettings.Weighting>();

			public void actionPerformed(ActionEvent arg0) {
				
				
				weightingDialog = new JDialog();
				weightingDialog.setTitle("Weighting factors for Agent "+agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.agentName);
				weightingDialog.setLayout(new GridBagLayout());
				final GridBagConstraints fil = new GridBagConstraints();
	   			fil.insets = new Insets( 5, 5, 5, 5);
				
	   			weightingDialog.setSize(700,400);
	   			weightingDialog.setBackground(Color.white);
	   			weightingDialog.setModal(true);
				
				upperPanel = new JPanel();
				upperPanel.setLayout(new GridBagLayout());
				final GridBagConstraints upP = new GridBagConstraints();
				upP.insets = new Insets( 5, 5, 5, 5);
			
		    	/*Initialize tempWeighting*/
				
				tempWeighting = agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.weighting;
		    	
		    	fil.gridx=0; fil.gridy =0;
		    	weightingDialog.add(upperPanel,fil);
		    	
		    	
		    	lowerPanel = new JPanel();
		    	lowerPanel.setLayout(new GridBagLayout());
				final GridBagConstraints lowerP = new GridBagConstraints();
				lowerP.insets = new Insets( 5, 5, 5, 5);
		    	
				
				dlmWeighting = new DefaultListModel();
	   			unSelectWeightingVar = new JList(dlmWeighting){
	   				
	   				public String getToolTipText(MouseEvent evt) {
	   		        // Get item index
						int index = locationToIndex(evt.getPoint());

	   		        // Get item
	   		        Object item = getModel().getElementAt(index);
	   		        
	   		        String tooltip = "";
	   		        
	   		     for(int i=0; i < tempAgentSettingList.size();i++){
	   		        	
	   		        	if(tempAgentSettingList.get(i).agentName.equals(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.agentName)){
	   		        		
	   		        		for(int j=0; j< tempAgentSettingList.get(i).variableList.size();j++){
	   		        			
	   		        			if(tempAgentSettingList.get(i).variableList.get(j).name.equals(item)){
	   		        				
	   		        				tooltip = tempAgentSettingList.get(i).variableList.get(j).name +" ("
	   		   		        		+tempAgentSettingList.get(i).variableList.get(j).type+") "+
	   		   		        		tempAgentSettingList.get(i).variableList.get(j).description;
	   		       
	   		        			}
	   		        		}
	   		        	}
	   		        }

	   		        // Return the tool tip text
	   		        return tooltip;
	   		    }
	   			};
	   			
	   			
	   			
	   			
		   		 sortedModel = new SortedListModel(dlmWeighting,SortedListModel.SortOrder.UNORDERED);
		   		unSelectWeightingVar.setModel(sortedModel);
	   			
	   			
		   		unSelectWeightingVar.addMouseListener(new MouseListener(){
					
					public void mouseClicked(MouseEvent e) {
	   			
						System.out.println("Right click");
						
						if(SwingUtilities.isRightMouseButton(e)){
							
							System.out.println("Right click");
							
							
							if(sortedModel.sortOrderOfList==SortedListModel.SortOrder.ASCENDING ){
								
															
								sortedModel.setSortOrder(SortedListModel.SortOrder.DESCENDING);
								unSelectWeightingVar.setModel(sortedModel);
					
							}else if(sortedModel.sortOrderOfList==SortedListModel.SortOrder.DESCENDING){
								
								sortedModel.setSortOrder(SortedListModel.SortOrder.UNORDERED);
								unSelectWeightingVar.setModel(sortedModel);
								
							}else if(sortedModel.sortOrderOfList==SortedListModel.SortOrder.UNORDERED){
								
								sortedModel.setSortOrder(SortedListModel.SortOrder.ASCENDING);
								unSelectWeightingVar.setModel(sortedModel);
								
							}
						
							
					
						}
						
					}

					@Override
					public void mouseEntered(MouseEvent arg0) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mouseExited(MouseEvent arg0) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mousePressed(MouseEvent arg0) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mouseReleased(MouseEvent arg0) {
						// TODO Auto-generated method stub
						
					}
	   			});
	   				

	   			dlm2Weighting = new DefaultListModel();
	   			selectWeightingVar = new JList(dlm2Weighting)	{
	   				
	   				public String getToolTipText(MouseEvent evt) {
	   		        // Get item index
						int index = locationToIndex(evt.getPoint());

	   		        // Get item
	   		        Object item = getModel().getElementAt(index);
	   		        
	   		        String tooltip = "";
	   		        
		   		     for(int i=0; i < tempAgentSettingList.size();i++){
		   		        	
		   		        	if(tempAgentSettingList.get(i).agentName.equals(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.agentName)){
		   		        		
		   		        		for(int j=0; j< tempAgentSettingList.get(i).variableList.size();j++){
		   		        			
		   		        			if(tempAgentSettingList.get(i).variableList.get(j).name.equals(item)){
		   		        				
		   		        				tooltip = tempAgentSettingList.get(i).variableList.get(j).name +" ("
		   		   		        		+tempAgentSettingList.get(i).variableList.get(j).type+") "+
		   		   		        		tempAgentSettingList.get(i).variableList.get(j).description;
		   		       
		   		        			}
		   		        		}
		   		        	}
		   		        }

	   		        // Return the tool tip text
	   		        return tooltip;
	   		    }
	   			};
	   			
	   			
	   			for(int i=0; i < tempAgentSettingList.size();i++){
		        	
		        	if(tempAgentSettingList.get(i).agentName.equals(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.agentName)){
		        		
		        		for(int j=0; j< tempAgentSettingList.get(i).variableList.size();j++){
		        			
		        			
		        				
		        				boolean found = false;
		        				
		        				for(int k=0; k<tempWeighting.size();k++)
				   				{
					   				if(tempAgentSettingList.get(i).variableList.get(j).name.equals(tempWeighting.get(k).weightingVariable)){
					   				
					   					dlm2Weighting.addElement(tempWeighting.get(k).weightingVariable);
					   					found = true;
					   					break;
					   				
					   				}	
					   				
				   				}
				   				if(!found)
				   					dlmWeighting.addElement(tempAgentSettingList.get(i).variableList.get(j).name);
		        				
		        			
		        			
		        		}
		        		
		        	}
	   		  	}
	   
	   		

	   			unSelectWeightingVar.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	   			unSelectWeightingVar.setVisibleRowCount(10);
	   			
	   			selectWeightingVar.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	   			selectWeightingVar.setVisibleRowCount(10);

				JScrollPane listScrollUnSelectWeightingVar = new JScrollPane(unSelectWeightingVar); 
				listScrollUnSelectWeightingVar.setPreferredSize(new Dimension(200, 200)); 
				
				JScrollPane listScrollSelectWeightingVar = new JScrollPane(selectWeightingVar); 
				listScrollSelectWeightingVar.setPreferredSize(new Dimension(200, 200)); 
				
				lowerP.gridx=0; lowerP.gridy =0;
				lowerPanel.add(listScrollUnSelectWeightingVar,lowerP);
				
				JPanel middlePanel = new JPanel();
				
				middlePanel.setLayout(new GridBagLayout());
	   			final GridBagConstraints midP= new GridBagConstraints();
	   			midP.insets = new Insets( 5, 5, 5, 5);
	   			
	   			
	   			JButton addButton = new JButton("Add");
	   			JButton removeButton = new JButton("Remove");
				
	   			
   				addButton.addActionListener(new ActionListener(){
   				
					public void actionPerformed(ActionEvent arg0) {
						
						int index = sortedModel.toUnsortedModelIndex(unSelectWeightingVar.getSelectedIndex());
	   			    
		    			dlm2Weighting.addElement(dlmWeighting.get(index).toString());		    			    
		    			tempWeighting.add( (new PlottingSettings()).new Weighting(dlmWeighting.get(unSelectWeightingVar.getSelectedIndex()).toString()));    
		    			dlmWeighting.remove(index);
			    			
			    	}
				    					    				
   				});
 				
		  			removeButton.addActionListener(new ActionListener(){
		
						public void actionPerformed(ActionEvent arg1) {
							
							try{
							
							int index = selectWeightingVar.getSelectedIndex();
							
							
							
							dlmWeighting.addElement(dlm2Weighting.get(index));
		
							for(int i=0; i< tempWeighting.size();i++){
								
								if(tempWeighting.get(i).weightingVariable.equals(dlm2Weighting.get(index).toString())){
									
									tempWeighting.remove(index);
									break;
								}
								
							}
							dlm2Weighting.remove(index);
							
							}catch(Exception ex){
								
								System.out.println(ex);
								
							}
						
						}
			   				
		   				
		   				
		   			});
		   			
		   			midP.gridx=0; midP.gridy =0;
		   			middlePanel.add(addButton,midP);
		   			midP.gridx=0; midP.gridy =1;
		   			middlePanel.add(removeButton,midP);
		   			
		   			
		   			

		   			lowerP.gridx=1; lowerP.gridy =0;
					lowerPanel.add(middlePanel,lowerP);
					
					
					lowerP.gridx=2; lowerP.gridy =0;
					lowerPanel.add(listScrollSelectWeightingVar,lowerP);
					
					
					
					
					JButton getContinued = new JButton("Apply");
					lowerP.gridx=3; lowerP.gridy =1;
					lowerPanel.add(getContinued,lowerP);
					
					
					fil.gridx=0; fil.gridy =1;
					weightingDialog.add(lowerPanel,fil);
					
					getContinued.addActionListener(new ActionListener(){

						public void actionPerformed(ActionEvent arg0) {
							
							agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.weighting = tempWeighting;
						
							
							weightingDialog.setVisible(false);
							weightingDialog.dispose();
							
							/*Redraw table*/
							agentJPanelList.get(tabbedPane.getSelectedIndex()).reDrawTable();
					
							
						}
						
						
						
					});
				
					
					weightingDialog.setVisible(true);
		   			
	   			
	   			
				
				
			}
			
		});
		
		menuBar.add(menuSettings);
		
		
		
		menuRatios = new JMenu("Ratios");
		addRatio = new JMenuItem("Add Ratio");
		
		
		addRatio.addActionListener(new ActionListener(){

			JDialog ratioDialog;
			GridBagConstraints fil;
			DefaultListModel selectedAgentListModel1, selectedAgentListModel2;
			JList selectedAgentList1,selectedAgentList2;
			JPanel panel1;
			DefaultListModel variableListModel1,variableListModel2;
			JList variableList1,variableList2;
			JButton okayButton;
			JScrollPane scrollFractionTable;
			
			String temAgentNumerator, tempAgentDenominator;
			
			PlottingSettings.VariableInstance tempNumerator;
			PlottingSettings.VariableInstance tempDenominator;
			
			public void actionPerformed(ActionEvent arg0) {
				
				
				tempNumerator = null;
				tempDenominator = null;
				
				ratioDialog = new JDialog();
				ratioDialog.setModal(true);
				
	   			ratioDialog.setSize(800,600);
	   			ratioDialog.setBackground(Color.white);
	   			
				
				ratioDialog.setTitle("Define Rations of two variable Instances");
				ratioDialog.setLayout(new GridBagLayout());
				fil = new GridBagConstraints();
	   			fil.insets = new Insets( 5, 5, 5, 5);
	   			
	   			
	   			
	   			selectedAgentListModel1 = new DefaultListModel();
	   			selectedAgentList1 = new JList(selectedAgentListModel1);
	   			
	   			selectedAgentList1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	   			
	   			variableListModel1 = new DefaultListModel();
	   			variableList1 = new JList(variableListModel1);
	   			
	   			variableList1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	   			
	   				
   				for(int i=0; i<tempAgentList.size();i++){
					
					if(tempAgentList.get(i).isSelected){
						
						selectedAgentListModel1.addElement(tempAgentList.get(i).agentName);
						
					}
					
				}
   				
   				/*XXX*/
   				
   				selectedAgentList1.addMouseListener(new MouseListener(){
					
					public void mouseClicked(MouseEvent e) {

						System.out.println("One Click in row");
						

						if(e.getClickCount()==2){
							
							System.out.println("Double Click in row");
					
							for(int i=0; i<tempAgentList.size();i++){

								if(tempAgentList.get(i).agentName.equals(selectedAgentList1.getModel().getElementAt(selectedAgentList1.locationToIndex(e.getPoint())))){
									
									variableListModel1.removeAllElements();
									
									temAgentNumerator = selectedAgentList1.getModel().getElementAt(selectedAgentList1.locationToIndex(e.getPoint())).toString();
									
									
									for(int j=0; j < tempAgentList.get(i).listOfVariableInstances.size(); j++){
											
										variableListModel1.addElement(tempAgentList.get(i).listOfVariableInstances.get(j).instanceName);
												
									}
						
									break;	
								}
							
							
							}
							
								
							
							
							if(tempDenominator!=null && tempNumerator!= null){
	   		   					okayButton.setEnabled(true);
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
   				
   				
   				variableList1.addMouseListener(new MouseListener(){
					
					public void mouseClicked(MouseEvent e) {

						System.out.println("One Click in row");
						
					
						
						
						if(e.getClickCount()==2){
							

							System.out.println("One Click in row");
							
							
								for(int i=0; i< tempAgentList.size();i++){
									
									if(temAgentNumerator.equals(tempAgentList.get(i).agentName)){
										
										for(int j=0; j<tempAgentList.get(i).listOfVariableInstances.size();j++ ){
											
											if(variableList1.getSelectedValue().toString().equals(tempAgentList.get(i).listOfVariableInstances.get(j).instanceName)){
												
												tempNumerator = tempAgentList.get(i).listOfVariableInstances.get(j);
												
												
											}
										}
									}
								}
				   		        				
   		        				/*Redraw the table*/
   		        				ratioDialog.remove(scrollFractionTable);
   		        				ratioDialog.validate();

   		        				FractionTable fractionTable;
   		        				
   		        				try{
   		        					
   		        					fractionTable = new FractionTable(tempNumerator.instanceName, tempDenominator.instanceName);
   		        					
   		        					
   		        				}catch(NullPointerException ex1){
   		        					
   		        					try{
   		        					fractionTable = new FractionTable("", tempDenominator.instanceName);
   		        					}catch(NullPointerException ex2){
   		        						
   		        						try{
   		   		        						fractionTable = new FractionTable(tempNumerator.instanceName, "");
   		   		        					}catch(NullPointerException ex3){
   		   		        						
   		   		        						fractionTable = new FractionTable("", "");
   		   		        						
   		   		        					}
   		        					}
   		        					
   		        				}
   		        				
   		        				
	   		 	   				scrollFractionTable = fractionTable.listScrollFraction;
	   		 	   				scrollFractionTable.setPreferredSize(new Dimension(200, 60)); 
   		        				
	   		 	   				fil.gridx =3;fil.gridy =2;
		   		   				ratioDialog.add(scrollFractionTable,fil);
   		        				
		   		   				ratioDialog.validate();
		   		   				
			   		   			
   		        				
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
   				ratioDialog.add(label1,fil);
   				
   				
   				JLabel label11 = new JLabel("Numerator:");
   				
   				fil.gridx =0;fil.gridy =1;
   				ratioDialog.add(label11,fil);
  
   				fil.gridx =1;fil.gridy =1;
   				JScrollPane agentListScroll = new JScrollPane(selectedAgentList1);
   				agentListScroll.setPreferredSize(new Dimension(75,150));
   				
   			
   				
   				
   				ratioDialog.add(agentListScroll,fil);
   				
   				
   				JLabel label2 = new JLabel("Choose Variable Instance:");
   				
   				fil.gridx =2;fil.gridy =0;
   				ratioDialog.add(label2,fil);
   			
   				
   				fil.gridx =2;fil.gridy =1;
   				
   				JScrollPane variableListScroll = new JScrollPane(variableList1);
   				variableListScroll.setPreferredSize(new Dimension(200,150));
   				
   				ratioDialog.add(variableListScroll,fil);

   				selectedAgentListModel2 = new DefaultListModel();
	   			selectedAgentList2 = new JList(selectedAgentListModel2);
	   			
	   			selectedAgentList2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	   			
	   			variableListModel2 = new DefaultListModel();
	   			variableList2 = new JList(variableListModel2);
	   			
	   			variableList2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	   			
	   				
   				for(int i=0; i<tempAgentList.size();i++){
					
					if(tempAgentList.get(i).isSelected){
						
						selectedAgentListModel2.addElement(tempAgentList.get(i).agentName);
						
					}
					
				}
   				
   				
   				selectedAgentList2.addMouseListener(new MouseListener(){
					
					public void mouseClicked(MouseEvent e) {

						System.out.println("One Click in row");
						
						if(e.getClickCount()==2){
							
							System.out.println("Double Click in row");
							
							for(int i=0; i<tempAgentList.size();i++){

								if(tempAgentList.get(i).agentName.equals(selectedAgentList2.getModel().getElementAt(selectedAgentList2.locationToIndex(e.getPoint())))){
									
									variableListModel2.removeAllElements();
									
									tempAgentDenominator = selectedAgentList2.getModel().getElementAt(selectedAgentList2.locationToIndex(e.getPoint())).toString();
									
									
									for(int j=0; j < tempAgentList.get(i).listOfVariableInstances.size(); j++){
											
										variableListModel2.addElement(tempAgentList.get(i).listOfVariableInstances.get(j).instanceName);
												
									}
						
									break;	
								}
							
							
							}
							
								
							}
							
						if(!variableList2.isSelectionEmpty() && !variableList1.isSelectionEmpty()){
							okayButton.setEnabled(true);
							
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
   				
   				
   				variableList2.addMouseListener(new MouseListener(){
					
					public void mouseClicked(MouseEvent e) {

						System.out.println("One Click in row");
						
						
						
						if(e.getClickCount()==2){
							

							System.out.println("One Click in row");
							
							
							for(int i=0; i< tempAgentList.size();i++){
								
								if(tempAgentDenominator.equals(tempAgentList.get(i).agentName)){
									
									for(int j=0; j<tempAgentList.get(i).listOfVariableInstances.size();j++ ){
										
										if(variableList2.getSelectedValue().toString().equals(tempAgentList.get(i).listOfVariableInstances.get(j).instanceName)){
											
											tempDenominator = tempAgentList.get(i).listOfVariableInstances.get(j);
											
											
										}
									}
								}
							}
				   		        				
   		        				/*Redraw the table*/
   		        				ratioDialog.remove(scrollFractionTable);
   		        				ratioDialog.validate();
   		        				
   		        				FractionTable fractionTable;
   		        				
   		        				
   		        				
   		        				try{
   		        					
   		        					fractionTable = new FractionTable(tempNumerator.instanceName, tempDenominator.instanceName);
   		        					
   		        					
   		        				}catch(NullPointerException ex1){
   		        					
   		        					try{
   		        					fractionTable = new FractionTable("", tempDenominator.instanceName);
   		        					}catch(NullPointerException ex2){
   		        						
   		        						try{
   		   		        						fractionTable = new FractionTable(tempNumerator.instanceName, "");
   		   		        					}catch(NullPointerException ex3){
   		   		        						
   		   		        						fractionTable = new FractionTable("", "");
   		   		        						
   		   		        					}
   		        					}
   		        					
   		        				}
   		        				

	   		 	   				scrollFractionTable = fractionTable.listScrollFraction;
	   		 	   				scrollFractionTable.setPreferredSize(new Dimension(200, 60)); 
   		        				
	   		 	   				fil.gridx =3;fil.gridy =2;
		   		   				ratioDialog.add(scrollFractionTable,fil);
   		        				
		   		   				ratioDialog.validate();
		   		   				
		   		   				if(tempDenominator!=null && tempNumerator!= null){
		   		   					okayButton.setEnabled(true);
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
   				

   				JLabel label21 = new JLabel("Denominator:");
   				
   				fil.gridx =0;fil.gridy =4;
   				ratioDialog.add(label21,fil);
   				
   				label1 = new JLabel("Choose Agent:");
   				
   	
   				
   				fil.gridx =1;fil.gridy =3;
   				ratioDialog.add(label1,fil);
   				
   				fil.gridx =1;fil.gridy =4;
   				JScrollPane agentListScroll2 = new JScrollPane(selectedAgentList2);
   				agentListScroll2.setPreferredSize(new Dimension(75,150));
   				ratioDialog.add(agentListScroll2,fil);
   				
   				
   				label2 = new JLabel("Choose Variable Instance:");
   				
   				fil.gridx =2;fil.gridy =3;
   				ratioDialog.add(label2,fil);
   				
   				fil.gridx =2;fil.gridy =4;
   				JScrollPane variableListScroll2 = new JScrollPane(variableList2);
   				variableListScroll2.setPreferredSize(new Dimension(200,150));
   				ratioDialog.add(variableListScroll2,fil);
   				
   				
   				FractionTable fractionTable = new FractionTable("","");
   				
   				scrollFractionTable = fractionTable.listScrollFraction;
   				scrollFractionTable.setPreferredSize(new Dimension(200, 60)); 
   		
   			
   				
   				fil.gridx =3;fil.gridy =2;
   				ratioDialog.add(scrollFractionTable,fil);
   				

   				okayButton = new JButton("OK");
   				okayButton.setEnabled(false);
   				
   				JButton discardButton = new JButton("Discard");
   				
   				okayButton.addActionListener(new ActionListener(){

					public void actionPerformed(ActionEvent e) {

						if(tempDenominator!=null && tempNumerator!= null){
							
						PlottingSettings.RatioInstance ratioInstance = (new PlottingSettings()).new  RatioInstance(tempNumerator.instanceName+"_"+tempDenominator.instanceName+"_ratio",tempNumerator,tempDenominator );
							
						
						tempDenominator.isSelectedForRatios = true;
						tempNumerator.isSelectedForRatios = true;
						
						tempRatioListForPlotting.add(ratioInstance);
						ratioDialog.dispose();
						ratioDialog.setVisible(false);
						
						tabbedPane.remove( ratioTab);

						ratioTab = new RatioTab(tempRatioListForPlotting);
						
						tabbedPane.addTab("Ratios" , ratioTab);
						}
						
						System.out.println("XXXX");
						
					}
   					
   						
   				});
   				
   				discardButton.addActionListener(new ActionListener(){

					public void actionPerformed(ActionEvent e) {

						ratioDialog.dispose();
						ratioDialog.setVisible(false);
						
					}
   					
   						
   				});
   				
   				
   				fil.gridx =4;fil.gridy =5;
   				ratioDialog.add(okayButton,fil);
   				
   				fil.gridx =5;fil.gridy =5;
   				ratioDialog.add(discardButton,fil);
				
   				ratioDialog.setVisible(true);
			
				
			}
	
			
		});
		
		menuRatios.add(addRatio);
		
		removeRatio = new JMenuItem("Remove Ratio");
		
		removeRatio.addActionListener(new ActionListener(){

			DefaultListModel ratioListModel;
			JList ratioList;
			JButton removeButton;
			JButton	discardButton;
			JDialog ratioRemoveDialog;
			
			public void actionPerformed(ActionEvent arg0) {
				
				ratioRemoveDialog = new JDialog();
				ratioRemoveDialog.setModal(true);
				
				ratioRemoveDialog.setSize(700,400);
				ratioRemoveDialog.setBackground(Color.white);
	   			
				
				ratioRemoveDialog.setTitle("Remove Ratios");
				ratioRemoveDialog.setLayout(new GridBagLayout());
				
				GridBagConstraints fil = new GridBagConstraints();
	   			fil.insets = new Insets( 5, 5, 5, 5);
				
				
				ratioListModel = new DefaultListModel();
				ratioList = new JList(ratioListModel);
	   			
				ratioList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	   				
   				for(int i=0; i<tempRatioListForPlotting.size();i++){
					
   					ratioListModel.addElement(tempRatioListForPlotting.get(i).ratioInstanceName);
		
				}
   				
   				fil.gridx = 0;fil.gridy = 0; 
   				
   				
   				JScrollPane ratioListScroll = new JScrollPane(ratioList);
   				ratioListScroll.setPreferredSize(new Dimension(200,150));
   				
   				ratioRemoveDialog.add(ratioListScroll,fil);
   				
   				removeButton = new JButton("Remove");
   				discardButton = new JButton("Discard");
   				
   				fil.gridx = 1;fil.gridy = 1; 
   				ratioRemoveDialog.add(removeButton,fil);
   				
   				fil.gridx = 2;fil.gridy = 1; 
   				ratioRemoveDialog.add(discardButton,fil);

   				/*delete the selected items from the Ratio list, and close the dialog*/
   				removeButton.addActionListener(new ActionListener(){

	
					public void actionPerformed(ActionEvent arg0) {
					
						int[] selectedIndices = ratioList.getSelectedIndices();

						for(int i=0; i < selectedIndices.length;i++){
							
							for(int j=0; j < tempRatioListForPlotting.size();j++)
							{
								if(tempRatioListForPlotting.get(j).ratioInstanceName.equals(ratioListModel.get(selectedIndices[i]))){
									
									
									
									boolean remove = updateRatioSettings(tempRatioListForPlotting.get(j).ratioInstanceName);
									
									
									if(remove){
										boolean denominatorFound = false;
										boolean numeratorFound = false;
										
										
										for(int k=0; k < tempRatioListForPlotting.size();k++){
											
											
											if(tempRatioListForPlotting.get(j).numerator.equals(tempRatioListForPlotting.get(k).numerator)){
												
												if(j!=k)
													numeratorFound = true;
												
											}else if(tempRatioListForPlotting.get(j).numerator.equals(tempRatioListForPlotting.get(k).denominator)){
												if(j!=k)
													numeratorFound = true;
	
											
											}
											
											if(tempRatioListForPlotting.get(j).denominator.equals(tempRatioListForPlotting.get(k).denominator)){
												
												if(j!=k)
													denominatorFound = true;
	
											}else if(tempRatioListForPlotting.get(j).denominator.equals(tempRatioListForPlotting.get(k).numerator)){
												
												if(j!=k)
													denominatorFound = true;
	
											
											}
											
											
											if(denominatorFound && numeratorFound)
												break;
											
										}
										
										if(!numeratorFound)
										{
											for(int k =0; k<tempAgentList.size();k++){
												
												if(tempAgentList.get(k).agentName.equals(tempRatioListForPlotting.get(j).numerator.agentName)){
													
													for(int l=0; l< tempAgentList.get(k).listOfVariableInstances.size();l++){
														
														if(tempAgentList.get(k).listOfVariableInstances.get(l).equals(tempRatioListForPlotting.get(j).numerator)){
		   					
															tempAgentList.get(k).listOfVariableInstances.get(l).isSelectedForRatios = false;
															break;
		   					
		   												}
														
													}
													
													break;
					
												}
								
											}
										}
										
										if(!denominatorFound)
										{
											for(int k =0; k< tempAgentList.size();k++){
												
												if(tempAgentList.get(k).agentName.equals(tempRatioListForPlotting.get(j).denominator.agentName)){
													
													for(int l=0; l< tempAgentList.get(k).listOfVariableInstances.size();l++){													
														if(tempAgentList.get(k).listOfVariableInstances.get(l).equals(tempRatioListForPlotting.get(j).denominator)){
		   					
															tempAgentList.get(k).listOfVariableInstances.get(l).isSelectedForRatios = false;
															break;
		   					
		   												}
														
													}
													
													break;
					
												}
								
											}
										}
										
										
										
										tempRatioListForPlotting.remove(j);
										
							
										break;
									}
									
								}
	
							}

					}
						
						redrawRatioTab();
						ratioRemoveDialog.dispose();
						ratioRemoveDialog.setVisible(false);
						
   					
   					
					}
   				
   				});
   				
   				/*Close the window*/
   				discardButton.addActionListener(new ActionListener(){

   					
					public void actionPerformed(ActionEvent arg0) {

						ratioRemoveDialog.dispose();
						ratioRemoveDialog.setVisible(false);

					}
   				
   				});
   				
   				
   				ratioRemoveDialog.setVisible(true);
				
			}
				
			
		});
		

		menuRatios.add(removeRatio);
		
		menuBar.add(menuRatios);
		
		setJMenuBar(menuBar);
		
		
		/*Set the array for methods:*/
		
		
		
		/*Set top Panel*/
		
		JPanel topPanel = new JPanel();
		topPanel.setLayout( new BorderLayout() );
		getContentPane().add( topPanel );

	
		// Create a tabbed pane
		tabbedPane = new JTabbedPane();
		agentJPanelList = new ArrayList<AgentTab> ();
		
		for(int i=0; i<tempAgentList.size();i++){
			
			// Create the tab pages for each agent that is selected for plotting
			if(tempAgentList.get(i).isSelected)
			{
				agentJPanelList.add(new AgentTab(tempAgentList.get(i)));
				tabbedPane.addTab(tempAgentList.get(i).agentName ,agentJPanelList.get(agentJPanelList.size()-1));
			}
			
		}
		
		
		if(tempRatioListForPlotting.size()>0){
			
			
			ratioTab = new RatioTab(tempRatioListForPlotting);
			
			tabbedPane.addTab("Ratios" , ratioTab);
			
		}
		
		
		/*Check if certain menu items have to be disabled*/
		
		
		if(agentJPanelList.size()== tempAgentList.size() && agentJPanelList.size()!=0){
			addAgent.setEnabled(false);
		}else if(agentJPanelList.size()==0){
			
			removeAgent.setEnabled(false);
			menuSettings.setEnabled(false);
			menuVariables.setEnabled(false);
			
		}
		
		
		
		tabbedPane.addChangeListener( new ChangeListener() {
		      public void stateChanged(ChangeEvent changeEvent) {
		    	  
		    	  System.out.println("Changed tab to Tab"+tabbedPane.getSelectedIndex());
		    	  System.out.println("agentJPanelList.size()+1"+(agentJPanelList.size()+1));
		    	 
		    	  
		    	  if(agentJPanelList.size()==tabbedPane.getSelectedIndex()){
		    		  
		    		  menuVariables.setEnabled(false);
		    		  
		    	  }else{
		    		  
		    		  menuVariables.setEnabled(true);
		    		  
		    	  }
		    	  
		    		
		        }
		      });
		
		topPanel.add( tabbedPane, BorderLayout.CENTER );
		
		
		
		setModal(true);
		setVisible(true);

	}
	
	
	
	

	
	/*This function redraws the ratio tab*/
	public void redrawRatioTab(){
		

		if(tempRatioListForPlotting.size()>0){
			
			
			/*Check if the variables are still there*/
			
			boolean denominatorFound;
			boolean numeratorFound;
			
			for(int i=0; i<tempRatioListForPlotting.size();i++ ){
				
				numeratorFound = false;
				denominatorFound = false;
				
				for(int j=0; j<tempAgentList.size();j++){
					
					
					if(tempRatioListForPlotting.get(i).denominator.agentName.equals(tempAgentList.get(j).agentName)){
						
						if(tempAgentList.get(j).isSelected){
		
							for(int k=0; k<tempAgentList.get(j).listOfVariableInstances.size();k++){
								
								
								
									if(tempAgentList.get(j).listOfVariableInstances.get(k).instanceName.equals(tempRatioListForPlotting.get(i).denominator.instanceName)){
										
										denominatorFound =true;
										break;
									}
									
							
							}
				
						}else{
						break;	
						}
					}
				}
					
				for(int j=0; j<tempAgentList.size();j++){
					
					
					if(tempRatioListForPlotting.get(i).numerator.agentName.equals(tempAgentList.get(j).agentName)){
						
						if(tempAgentList.get(j).isSelected){
		
							for(int k=0; k<tempAgentList.get(j).listOfVariableInstances.size();k++){
								
								
								
									if(tempAgentList.get(j).listOfVariableInstances.get(k).instanceName.equals(tempRatioListForPlotting.get(i).numerator.instanceName)){
										
										numeratorFound =true;
										break;
									}
									
							
							}
				
						}else{
						break;	
						}
					}
				}
				
				System.out.println("XXaaasd");
				
				
				if(numeratorFound==false || denominatorFound==false){
	
					tempRatioListForPlotting.remove(i);
					i--;
					
				}
				
				
			}
	
			
			tabbedPane.remove(ratioTab);
			
			if(tempRatioListForPlotting.size()>0){
			
			ratioTab = new RatioTab(tempRatioListForPlotting);
			tabbedPane.addTab("Ratios" , ratioTab);
			}
		}else{
			
			
			tabbedPane.remove(ratioTab);
			
		}
		
		
	}
	
	
	
	class RatioTab extends JPanel{
		
		GridBagConstraints ratioPane = new GridBagConstraints();
		private JScrollPane scrollRatioTable;
		
		ArrayList<PlottingSettings.RatioInstance> ratioList = new ArrayList<PlottingSettings.RatioInstance>();
		JTable ratioTable;
		RatioTableModel ratioTableModel;
		
		RatioTab(ArrayList<PlottingSettings.RatioInstance> ratios){
			
			setSize(700, 200);
			setLayout(new GridBagLayout());
			ratioPane.insets = new Insets( 5, 5, 5, 5);
			
			
			JLabel lab = new JLabel("Ratios of two Variable Instances");
			ratioPane.gridx =0;ratioPane.gridy =0;
			add(lab,ratioPane);
			
			RatioTable variableTable = new  RatioTable();
			
			
			scrollRatioTable = variableTable.listScrollRatio;
			scrollRatioTable.setPreferredSize(new Dimension(700, 179)); 
			ratioPane.gridx =0;ratioPane.gridy =1;
			add(scrollRatioTable,ratioPane);
			
			ratioList = ratios;

		}
		
		
		private class RatioTable extends JScrollPane{
		
			JScrollPane listScrollRatio;
			
			RatioTable(){
				
				
				
				String[] colHeadersT2 = {"Ratio Name","Numerator","Denominator" };
				
				
				ratioTableModel = new RatioTableModel(colHeadersT2);
				ratioTable = new JTable(ratioTableModel){    
				    //Implement table cell tool tips.
				    public String getToolTipText(MouseEvent e) {
				        String tip = null;
				        java.awt.Point p = e.getPoint();
				        int rowIndex = rowAtPoint(p);
				        int colIndex = columnAtPoint(p);
				    
				            tip = getValueAt(rowIndex, colIndex).toString();

				        return tip;
				        }
				    };
				
				
				
				ratioTable.getColumnModel().getColumn(0).setCellEditor(new CellEditor());
				ratioTable.getColumnModel().getColumn(0).getCellEditor().addCellEditorListener(new EditorListener(){
					 
					 
					 public void editingStopped(ChangeEvent e) {
						 
						 System.out.println("Col 2 editing stopped");
						 
						 int editedrow = ((CellEditor) ratioTable.getColumnModel().getColumn(0).getCellEditor()).getRow();
			    
			    	      for(int i=0; i<ratioList.size();i++){
								 
								 if(ratioList.get(i).ratioInstanceName.equals(ratioTableModel.getValueAt(editedrow, 0))){
									 
							
								 
									 ratioList.get(i).ratioInstanceName = ratioTable.getColumnModel().getColumn(0).getCellEditor().getCellEditorValue().toString();
											
						 
										 break;
								 }
						 
								 }
					 
			    	      
			    	      ratioTableModel.setValueAt(ratioTable.getColumnModel().getColumn(0).getCellEditor().getCellEditorValue().toString(), editedrow, 0);
						 
					 }
					 
					 
				});

				

				
				listScrollRatio = new JScrollPane(ratioTable);
	
			}
		
		
		}
		
		
		private class RatioTableModel extends AbstractTableModel{
			
			String [] columnName;
			
			RatioTableModel(String[] colName){
				
				columnName = colName;
				
			}

		
			public int getColumnCount() {
				
				return 3;
			}

		
			public int getRowCount() {
			
				return ratioList.size();
			}

		
			public Object getValueAt(int row, int column) {
				
				
				System.out.println(ratioList.get(row).ratioInstanceName);
				
				if(column==0){
					
					return ratioList.get(row).ratioInstanceName;
					
				}else if(column == 1){
					
					return ratioList.get(row).numerator.instanceName;
					
				}else if(column == 2){
					
					return ratioList.get(row).denominator.instanceName;
					
				}else
					return null;
			}
			
			
		  public String getColumnName(int column) {  
		       return columnName[column]; 
		   }
			  
		   public boolean isCellEditable(int row, int column){ 
			      
			   	if(column==0)
			   		return true;
		   		else
		   			return false;
		   	
		   }
			
			
		}
		
		
	}
	
	
	class AgentTab extends  JPanel
	{
		PlottingSettings.Agent agent;
		GridBagConstraints agentPane = new GridBagConstraints();
		
		private VariableListTableModel tabvariableModel;
		private JTable tabVariables;
		
		private JButton addInstance;
		private JButton removeInstance;
		private JScrollPane scrollVariableTable;
		
		AgentTab (PlottingSettings.Agent agentAtTab){
			
			agent = agentAtTab;

	
			setSize(700, 200);
			setLayout(new GridBagLayout());
			
			
			agentPane.insets = new Insets( 5, 5, 5, 5);
			
			JLabel lab = new JLabel("Plotting Settings for Agent "+agent.agentName);
			agentPane.gridx =0;agentPane.gridy =0;
			add(lab,agentPane);
			
			VariableTable variableTable = new  VariableTable(agent);
				
			scrollVariableTable = variableTable.listScrollVariables;
			scrollVariableTable.setPreferredSize(new Dimension(700, 179)); 
			agentPane.gridx =0;agentPane.gridy =1;
			add(scrollVariableTable,agentPane);
			
			JPanel lowerPanel = new JPanel();
			
			lowerPanel.setLayout(new GridBagLayout());
			
			GridBagConstraints lowerPa = new GridBagConstraints();
			lowerPa.insets = new Insets( 5, 5, 5, 5);
			
			addInstance = new JButton("Add new Instance of Variable");
			removeInstance = new JButton("Remove Instance of Variable");
			
			addInstance.setEnabled(false);
			removeInstance.setEnabled(false);
			
			lowerPa.gridx =0;lowerPa.gridy =0;
			lowerPanel.add(addInstance,lowerPa);
			
			lowerPa.gridx =1;lowerPa.gridy =0;
			lowerPanel.add(removeInstance,lowerPa);
			
			addInstance.addActionListener(new ActionListener(){

				public void actionPerformed(ActionEvent arg0) {
					
			
					addInstance.setEnabled(false);
					
					String name ="";
					int counter =0;
					int indexOfLastOccurence =0;
					
					
					
					if(!tabvariableModel.getValueAt(tabVariables.getSelectedRow(), 0).toString().endsWith("_agent_ratio")){
		
						for(int i=0; i<agent.listOfVariableInstances.size();i++ ){
							
							if(agent.listOfVariableInstances.get(i).instanceContainsVariable(tabvariableModel.getValueAt(tabVariables.getSelectedRow(), 0).toString())){
								
								counter++;
								name = agent.listOfVariableInstances.get(i).getName();
								indexOfLastOccurence=i;
								
							}
		
						}
						
						 name = name+"_"+counter;
						 
						 
						 for(int i=0; i < tempAgentSettingList.size();i++){
			   		        	
			   		        	if(tempAgentSettingList.get(i).agentName.equals(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.agentName)){
			
			   		        		for(int j=0; j< tempAgentSettingList.get(i).variableList.size();j++){
			   		        			
			   		        			if( tempAgentSettingList.get(i).variableList.get(j).name.equals(tabvariableModel.getValueAt(tabVariables.getSelectedRow(), 0).toString())){
			   		        				
			   		        			 PlottingSettings.VariableInstance aR = (new PlottingSettings()).new  VariableInstance(name,
			   								 	agent.agentName,
			   								 	tempAgentSettingList.get(i).variableList.get(j),
			   									agent.defaultSettings.defaultMethod,
			   									agent.defaultSettings.defaultFilter1,
			   									agent.defaultSettings.defaultFilter2,
			   									agent.defaultSettings.defaultPartitioning,
			   									"No");
			   						 
			   						 agent.listOfVariableInstances.add(indexOfLastOccurence+1,aR);
			   		        				
			   		        				break;
			   		        			}
			   		        			
			   		        		}
			   		        		
			   		        	}
			   		        }
					
					}else{
		
						
						Variable numerator = null;
						Variable denominator = null;
						
						for(int i=0; i<agent.listOfVariableInstances.size();i++ ){
							if(!agent.listOfVariableInstances.get(i).isVariable)
								if(agent.listOfVariableInstances.get(i).agentRatio.ratioName.equals(tabvariableModel.getValueAt(tabVariables.getSelectedRow(), 0).toString())){
									
									counter++;
									name = agent.listOfVariableInstances.get(i).agentRatio.ratioName;
									indexOfLastOccurence=i;
									
									numerator = agent.listOfVariableInstances.get(i).agentRatio.numerator;
									denominator = agent.listOfVariableInstances.get(i).agentRatio.denominator;
									
								}
		
						}
						
						
						 name = name+"_"+counter;
						 
						 
						try{
   		        			 PlottingSettings.VariableInstance aR = (new PlottingSettings()).new  VariableInstance(name,
   								 	agent.agentName,
   								 	(new PlottingSettings()).new AgentRatio(numerator,denominator),  
   									agent.defaultSettings.defaultMethod,
   									agent.defaultSettings.defaultFilter1,
   									agent.defaultSettings.defaultFilter2,
   									agent.defaultSettings.defaultPartitioning,
   									"No");
   						 
   		        			 agent.listOfVariableInstances.add(indexOfLastOccurence+1,aR);
   		        			 
        				}catch(Exception ex){
        					
        					System.out.println(ex);
        					
        				}
						
						

					}
							
	
			
					 reDrawTable();	 
							 
							
					 
					
				}

			});
			
			
			
			removeInstance.addActionListener(new ActionListener(){

				public void actionPerformed(ActionEvent arg0) {
					
					
					System.out.println("tabVariables.getSelectedRow() "+tabVariables.getSelectedRow());
					
					int counter =0;
					String varName = tabvariableModel.getValueAt(tabVariables.getSelectedRow(), 0).toString();
			
					
					updateVariableSettings(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.listOfVariableInstances, tabvariableModel.getValueAt(tabVariables.getSelectedRow(), 1).toString(), true);
			
					
					if(!varName.endsWith("_agent_ratio")){
					
						for(int i=0; i< agent.listOfVariableInstances.size();i++){
							
							if(agent.listOfVariableInstances.get(i).variable.name.equals(varName)){
								counter++;
							}
							
						}
					}
						
						 /*If last item set false*/
						 if( counter==0 ){
							 
							 for(int j=0; j < tempAgentSettingList.size();j++){
				   		        	
				   		        	if(tempAgentSettingList.get(j).agentName.equals(agent.agentName)){
				   		        		
				   		        		for(int k=0; k< tempAgentSettingList.get(j).variableList.size();k++){
						
				   		        			if(tempAgentSettingList.get(j).variableList.get(k).name.equals(varName)) {
									
				   		        				tempAgentSettingList.get(j).variableList.get(k).isSelectedForPlotting = false;
				   		        				break;
				   		        			}
				   		        		}
				   		        		break;
				   		        	}
						
						
							 }
						 }
						 

					 
					 reDrawTable();
					 redrawRatioTab();
			
					 addInstance.setEnabled(false);
					 removeInstance.setEnabled(false);
			
					}	 
							 
							
					 
					
				

			});
			
			
			
			agentPane.gridx =0;agentPane.gridy =2;
			add(lowerPanel,agentPane);
		
		}
		
		JPanel getPanel(){
		
			return this;
		}
		
		
		private void reDrawTable(){
			
			/*Redraw the table*/
			remove(scrollVariableTable);
			validate();
			
			System.out.println("reDrawTable()"+agent.agentName);
			
			VariableTable variableTable = new  VariableTable(agent);
			
			scrollVariableTable = variableTable.listScrollVariables;
			scrollVariableTable.setPreferredSize(new Dimension(700, 179)); 
			agentPane.gridx=0; 
			agentPane.gridy=1;
			add(scrollVariableTable,agentPane);

			validate();
			
			
		}
		
		
		
		private class VariableTable extends JScrollPane{
			
		
			JScrollPane listScrollVariables;  
			

			VariableTable(PlottingSettings.Agent agentAtTab){
				
	
				
				String[] colHeadersT2 = {"Variable" ,"Name", "Method", "Filter 1", "Filter 2", "Partitioning", "Growth Rate" };
				
				int agentIndex = 0;
				
				for(int i=0; i<tempAgentList.size();i++ ){
					
					if(tempAgentList.get(i).agentName.equals(agent.agentName)){
						
						agentIndex=i;
						break;
					}	
				}
				
				
				tabvariableModel = new VariableListTableModel(colHeadersT2, tempAgentList.get(agentIndex) );
				tabVariables = new JTable(tabvariableModel){    
				    //Implement table cell tool tips.
				    public String getToolTipText(MouseEvent e) {
				        String tip = null;
				        java.awt.Point p = e.getPoint();
				        int rowIndex = rowAtPoint(p);
				        int colIndex = columnAtPoint(p);
				    
				            tip = getValueAt(rowIndex, colIndex).toString();

				        return tip;
				        }
				    };

				
				tabVariables.setRowHeight(20);
				
				/*Column 1*/
				tabVariables.getColumnModel().getColumn(0).setPreferredWidth(140);
				tabVariables.getColumnModel().getColumn(0).setCellEditor(new CellEditor());
				

				/*Column 2*/
				
				
				tabVariables.getColumnModel().getColumn(1).setPreferredWidth(140);
				tabVariables.getColumnModel().getColumn(1).setCellEditor(new CellEditor());
				
				
				
				tabVariables.getColumnModel().getColumn(1).getCellEditor().addCellEditorListener(new EditorListener(){
					 
					 
					 public void editingStopped(ChangeEvent e) {
						 
						 System.out.println("Col 2 editing stopped");
						 
				
						 int editedrow = ((CellEditor) tabVariables.getColumnModel().getColumn(1).getCellEditor()).getRow();
						
								 for(int i=0; i< agent.listOfVariableInstances.size();i++){
									 
									 if(agent.listOfVariableInstances.get(i).instanceName.equals(tabvariableModel.getValueAt(editedrow, 1))){
										 
										 agent.listOfVariableInstances.get(i).instanceName = tabVariables.getColumnModel().getColumn(1).getCellEditor().getCellEditorValue().toString();
										 tabvariableModel.changeValueAt(tabVariables.getColumnModel().getColumn(1).getCellEditor().getCellEditorValue().toString(), editedrow, 1);
										 break;
									 }
									 
								 }
							
						
					 }
						 
			
					 
				});
				

				
				/*Column 3: Method*/
				
				tabVariables.getColumnModel().getColumn(2).setPreferredWidth(140);
		
				String [] method;

				
					try{
						String [] method1 = new String[7+agent.weighting.size()];
						
						method1[0] =  "Mean";
						
						for(int i=0;i<agent.weighting.size();i++){
							
							method1[i+1] = "Mean ("+agent.weighting.get(i).weightingVariable+")";
							
						}
						method1[agent.weighting.size()+1]  = "Median";
						method1[agent.weighting.size()+2]  = "Standard Deviation";
						method1[agent.weighting.size()+3]  = "Standard Deviation percent";
						method1[agent.weighting.size()+4]  = "Sum";
						method1[agent.weighting.size()+5]  = "Minimum";
						method1[agent.weighting.size()+6]  = "Maximum";
						method = method1;
					
					}catch(NullPointerException eNP){
						
						String [] method1 =  {"Mean","Median", "Standard Deviation","Standard Deviation percent","Sum","Minimum","Maximum"};
						method = method1;
					}
					
				
				
				System.out.println("method "+method[0]);
				
				tabVariables.getColumnModel().getColumn(2).setCellEditor(new SpecificComboBoxEditor(method));
				tabVariables.getColumnModel().getColumn(2).setCellRenderer(new SpecificComboBoxRenderer(method));
				
				
				
				tabVariables.getColumnModel().getColumn(2).getCellEditor().addCellEditorListener(new EditorListener(){
					 
					 
					 public void editingStopped(ChangeEvent e) {
						 
						 System.out.println("Editing ended!");
						 
						 
			    			
					 }
					 
					 
				});
				
				
				/*Column 4: Filter 1*/
				
				tabVariables.getColumnModel().getColumn(3).setPreferredWidth(140);
				
				String [] filter ; 
				try{
				
					filter = new String [agent.filter.size()+1];
				}catch(Exception e){
				
					
					filter = new String [agent.filter.size()+1];
				}
				filter[0] = "No Filter";
				
				for(int i= 1; i<agent.filter.size()+1; i++){
					
					filter[i] = agent.filter.get(i-1).filterName;
					
				}
				
				System.out.println("filter "+filter[0]);
				
				tabVariables.getColumnModel().getColumn(3).setCellEditor(new SpecificComboBoxEditor(filter));
				tabVariables.getColumnModel().getColumn(3).setCellRenderer(new SpecificComboBoxRenderer(filter));
				
				
				
				tabVariables.getColumnModel().getColumn(3).getCellEditor().addCellEditorListener(new EditorListener(){
					 
					 
					 public void editingStopped(ChangeEvent e) {
						 
						 System.out.println("Editing ended!");
						 
					 }
					 
					 
				});

				
				/*Column 5: Filter 2*/
				
				tabVariables.getColumnModel().getColumn(4).setPreferredWidth(140);
				
				tabVariables.getColumnModel().getColumn(4).setCellEditor(new SpecificComboBoxEditor(filter));
				tabVariables.getColumnModel().getColumn(4).setCellRenderer(new SpecificComboBoxRenderer(filter));
				
				
				
				tabVariables.getColumnModel().getColumn(4).getCellEditor().addCellEditorListener(new EditorListener(){
					 
					 
					 public void editingStopped(ChangeEvent e) {
						 
						 System.out.println("Editing ended!");
						 
					 }
					 
					 
				});
				
				
				/*Column 6: Partitioning*/
				
				tabVariables.getColumnModel().getColumn(5).setPreferredWidth(140);
				
				String [] partitioning ; 
				try{
				
					partitioning = new String [agent.partitioning.size()+1];
				}catch(Exception e){
				
					
					partitioning = new String [1];
				}
				partitioning[0] = "No Partitioning";
				
				System.out.println("agent.plottingSelections.partitioning.Partitioning"+agent.partitioning.size());
				if(partitioning.length>1)
				{
					for(int i= 1; i<agent.partitioning.size()+1; i++){
						
						partitioning[i] = agent.partitioning.get(i-1).partitioningName;
						
					}
				}
				tabVariables.getColumnModel().getColumn(5).setCellEditor(new SpecificComboBoxEditor(partitioning));
				tabVariables.getColumnModel().getColumn(5).setCellRenderer(new SpecificComboBoxRenderer(partitioning));
				
				
				
				tabVariables.getColumnModel().getColumn(5).getCellEditor().addCellEditorListener(new EditorListener(){
					 
					 
					 public void editingStopped(ChangeEvent e) {
						 
						 System.out.println("Editing ended!");
						 
					 }
					 
					 
				});
				
				
				
				/*Column 7: Growth rates*/
				
				tabVariables.getColumnModel().getColumn(6).setPreferredWidth(140);
		
			
				String [] growthRate =  {"No", "Annual", "Quarterly","Monthly"};
					
		
				
				tabVariables.getColumnModel().getColumn(6).setCellEditor(new SpecificComboBoxEditor(growthRate));
				tabVariables.getColumnModel().getColumn(6).setCellRenderer(new SpecificComboBoxRenderer(growthRate));
				
				
				
				tabVariables.getColumnModel().getColumn(6).getCellEditor().addCellEditorListener(new EditorListener(){
					 
					 
					 public void editingStopped(ChangeEvent e) {
						 
						 System.out.println("Editing ended!");
						 
						 
			    			
					 }
					 
					 
				});
				
				
				tabVariables.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
				
				System.out.println("xx "+tabVariables.getColumnCount());
				
				
				
				tabVariables.addMouseListener(new MouseAdapter() {
					public void mouseClicked(final MouseEvent e) {
						
						tabVariables.setRowSelectionAllowed(true);
						
						System.out.println("xxtabVariables.getSelectedRow() "+tabVariables.getSelectedRow());
						System.out.println("xxtabbedPane.getSelectedIndex()"+tabVariables.getRowCount());
						
						
						System.out.println("xxtabVariables.getSelectedRow() "+agent.agentName);
						addInstance.setEnabled(true);
						removeInstance.setEnabled(true);
						
						
						
		         }
				});
				System.out.println("draw table");
				tabvariableModel.updatetable();
				
				listScrollVariables = new JScrollPane(tabVariables);
				
				
			}
			
			public class SpecificComboBoxEditor extends DefaultCellEditor {
				
				int editedRow;
				int editedCol;
				
				JComboBox cComboBox;
				
			    public SpecificComboBoxEditor(String[] items) {
			        
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
			    
			        	if(cComboBox.getItemAt(i).toString().equals(tabvariableModel.getValueAt(editedRow, editedCol))){
	
			        		
			        		 cComboBox.setSelectedIndex(i);
			        		
			        	}
			        	try{
				        	if(tabvariableModel.getValueAt(editedRow, editedCol).equals("")){

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
			    	       
			    	    	 tabvariableModel.changeValueAt(cComboBox.getSelectedItem().toString(), editedRow, editedCol);
							 tabvariableModel.updatetable();
							 
							 
		
							 
							 for(int i=0; i< agent.listOfVariableInstances.size();i++){
								 
								 if(agent.listOfVariableInstances.get(i).instanceName.equals(tabvariableModel.getValueAt(editedRow, 1))){
									 
									 /*Column 3 => method*/
									 if(editedCol==2){
										
										 agent.listOfVariableInstances.get(i).instanceMethod = cComboBox.getSelectedItem().toString();
									 }else if(editedCol==3){
										 /*Column 4 => Filter 1*/
										 agent.listOfVariableInstances.get(i).whichFilter1 = cComboBox.getSelectedItem().toString();
									 }else if(editedCol==4){
										 /*Column 5 => Filter 2*/
										 agent.listOfVariableInstances.get(i).whichFilter2 = cComboBox.getSelectedItem().toString();
									 }else if(editedCol==5){
										 /*Column 6 => partitioning*/
										 agent.listOfVariableInstances.get(i).whichPartitioning = cComboBox.getSelectedItem().toString();
									 }else if(editedCol==6){
										 
										 agent.listOfVariableInstances.get(i).growthRate = cComboBox.getSelectedItem().toString();
										 
									 }
									
								 }
								 
							 }
							
								
							 
		
							 tabvariableModel.updatetable();
			    	    	
			    	    }
			    	   
			    	   
			    	   
			       });
			        
			        return cComboBox;
			      
			        
			    }
			    
			    
			    
			    public int getRow (){
			    	
			    	System.out.println("rowIndex "+editedRow);
			    	
			    	return editedRow;
			    	
			    }
			    
			    
			      
			        
			    
			    
			}
			
			
			public class SpecificComboBoxRenderer extends JComboBox implements TableCellRenderer {
				
			    public SpecificComboBoxRenderer(String[] items) {
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
		
	}
	
	
	
	
	
	
	
	private class FractionTable extends JScrollPane{
		
		
		JScrollPane listScrollFraction;
		

		FractionTable(String numerator, String denominator){
			
			
	
			
			FractionTableModel tabvariableModel = new FractionTableModel(numerator,denominator );
			JTable table = new JTable(tabvariableModel);

			
			table.setRowHeight(20);
			
			/*Column 1*/
			table.getColumnModel().getColumn(0).setPreferredWidth(140);
			
			

			
			listScrollFraction = new JScrollPane(table);
		}
			
		}
	
	
	private class FractionTableModel extends AbstractTableModel{
			
		String numerator, denominator;

		FractionTableModel(String num, String den){
			
			numerator = num;
			denominator = den;
			
		}
		
			public int getColumnCount() {
				
				return 1;
			}

		
			public int getRowCount() {
			
				return 2;
			}

		
			public Object getValueAt(int row, int column) {
				
				
				if(row==0){
					
					return numerator;
					
				}else{
					
					return denominator;
					
				}
				
			}
			
			
			  public String getColumnName(int column) {  
			       return "Ratio of:"; 
			   }
			
		
			  
		   public boolean isCellEditable(int row, int column){ 
			      

		   			return false;
		   	
		   }
			
			
		}
	
	
	
	
	
	public void updateVariableSettings(ArrayList<PlottingSettings.VariableInstance> listOfVariableInstances, String variableName, boolean singleInstance){
		

			for(int j=0; j<listOfVariableInstances.size();j++){
				
				boolean start = false;
				
				
				if(!singleInstance){
					
					if(listOfVariableInstances.get(j).isVariable)
					{
						if(listOfVariableInstances.get(j).variable.name.equals(variableName) )
						start = true;
					}else{
						
						if(listOfVariableInstances.get(j).instanceName.equals(variableName))
							start = true;
						
					}
				}else{
					
					if(listOfVariableInstances.get(j).instanceName.equals(variableName))
						start = true;
					
				}
				
				
				
				
				
				if(start){
					
					boolean removevariable = true;
					
					
					if(listOfVariableInstances.get(j).isSelectedForRatios)
					{
						Object text2 = "The variable "+variableName+" is used for Time Series ratios. Delete anyhow?";
						
						int choiceIsRatio = JOptionPane.showConfirmDialog(null, text2,  "",JOptionPane.YES_NO_OPTION);
						
						if(choiceIsRatio == 0){
							
							removevariable = true;
							
						}else{
							
							removevariable = false;
							break;
							
						}
						
					}
					
					if(removevariable){
						
						PlottingSettings.VariableInstance tempVarInst = listOfVariableInstances.get(j);
						
						if(tempVarInst.isSelectedForCrossCorrelation || tempVarInst.isSelectedForMultipleBandpassFilteredTimeSeries || tempVarInst.isSelectedForMultipleTimeSeries || tempVarInst.isSelectedForScatterplots || tempVarInst.selectedForSingleBandpassFilteredTimeSeries || tempVarInst.selectedForSingleTimeSeries){
							
							Object text3 = "The variable "+variableName+" is used for plotting Time Series. Delete anyhow?";
							
							int choiceIsRatio = JOptionPane.showConfirmDialog(null, text3,  "",JOptionPane.YES_NO_OPTION);
							
							if(choiceIsRatio == 0){
								
								removevariable = true;
								
							}else{
								
								removevariable = false;
								break;
								
							}
							
						}
						
						
						
						
					}
					

					if(removevariable){
						
						if(listOfVariableInstances.get(j).isSelectedForRatios){
		
							/*Check the ratio instance list*/
							for(int k=0; k<tempRatioListForPlotting.size();k++){
								
								/*If denominator is the selected variable*/
								if(tempRatioListForPlotting.get(k).numerator.instanceContainsVariable(variableName)){

				
									boolean found = false;
									
									for(int l=0; l< tempRatioListForPlotting.size();l++){
										
										if(tempRatioListForPlotting.get(l).numerator.equals(tempRatioListForPlotting.get(k).denominator) || tempRatioListForPlotting.get(l).denominator.equals(tempRatioListForPlotting.get(k).denominator)){
								
											
											System.out.println("Num: k "+k+"   l"+l);
											
											if(l!=k){
												
												System.out.println("In");
												found = true;
												break;
											}
										}
									}
									
										
									if(!found){
										
										for(int l1 =0; l1 < tempAgentList.size(); l1++){
											
											
											System.out.println(tempAgentList.get(l1).agentName+"     "+ tempRatioListForPlotting.get(k).denominator.agentName);
											
											if(tempAgentList.get(l1).agentName.equals(tempRatioListForPlotting.get(k).denominator.agentName))
											{
												for(int l=0; l <tempAgentList.get(l1).listOfVariableInstances.size();l++){
								
													if(tempAgentList.get(l1).listOfVariableInstances.get(l).instanceName.equals(tempRatioListForPlotting.get(k).denominator.instanceName)){
														
											
														tempAgentList.get(l1).listOfVariableInstances.get(l).isSelectedForRatios = false;
														
												
														break;
														
													}
													
												}
												
												
												break;
												
											}
										}
										
									}
									
									tempRatioListForPlotting.remove(k);
									k--;
									
									
									
								}else if(tempRatioListForPlotting.get(k).denominator.instanceContainsVariable(variableName)){
							
									boolean found = false;
									
									for(int l=0; l< tempRatioListForPlotting.size();l++){
										
										if(tempRatioListForPlotting.get(l).numerator.equals(tempRatioListForPlotting.get(k).numerator) || tempRatioListForPlotting.get(l).denominator.equals(tempRatioListForPlotting.get(k).numerator)){
								
											System.out.println("Den: k "+k+"   l"+l);
											
											if(l!=k){
												
												System.out.println("In");
												found = true;
												break;
											}
										}
									}
									
									if(!found){
										
										for(int l1 =0; l1 < tempAgentList.size(); l1++){
											
											if(tempAgentList.get(l1).agentName.equals(tempRatioListForPlotting.get(k).numerator.agentName))
											{
												for(int l=0; l <tempAgentList.get(l1).listOfVariableInstances.size();l++){
													
													if(tempAgentList.get(l1).listOfVariableInstances.get(l).instanceName.equals(tempRatioListForPlotting.get(k).numerator.instanceName)){
														
														System.out.println("Set");
														
														tempAgentList.get(l1).listOfVariableInstances.get(l).isSelectedForRatios = false;
														break;
														
													}
													
												}
												
											}
										}
										
									}
									
									tempRatioListForPlotting.remove(k);
									k--;
					
								}
							}
							
						
						}
						
	
				
					
						if( listOfVariableInstances.get(j).isSelectedForCrossCorrelation ){
							
							for(int k=0; k <tempListOfCrossCorrelation.size(); k++){
								
								boolean found = false;
								
								if(tempListOfCrossCorrelation.get(k).firstComponent.isVariableInstance){
									
									if(tempListOfCrossCorrelation.get(k).firstComponent.variableInstance.isVariable){
										
										
										if(tempListOfCrossCorrelation.get(k).firstComponent.variableInstance.variable.name.equals(variableName)){
											
											tempListOfCrossCorrelation.remove(k);
											k--;
											found = true;
											
										}
								
									}
									
									
									
									
									
								}else{
									
								
										if(tempListOfCrossCorrelation.get(k).firstComponent.ratioInstance.numerator.variable.name.equals(variableName)|| tempListOfCrossCorrelation.get(k).firstComponent.ratioInstance.denominator.variable.name.equals(variableName)){
											
											tempListOfCrossCorrelation.remove(k);
											k--;
											found = true;
											
										}
								
									
									
									
									
								}
								
								
								if(!found){
									
									if(tempListOfCrossCorrelation.get(k).secondComponent.isVariableInstance){
										
										if(tempListOfCrossCorrelation.get(k).secondComponent.variableInstance.isVariable){
											
											
											if(tempListOfCrossCorrelation.get(k).secondComponent.variableInstance.variable.name.equals(variableName)){
												
												tempListOfCrossCorrelation.remove(k);
												k--;
												found = true;
												
											}
									
										}
										
										
										
										
										
									}else{
										
									
											if(tempListOfCrossCorrelation.get(k).secondComponent.ratioInstance.numerator.variable.name.equals(variableName)|| tempListOfCrossCorrelation.get(k).firstComponent.ratioInstance.denominator.variable.name.equals(variableName)){
												
												tempListOfCrossCorrelation.remove(k);
												k--;
												found = true;
												
											}
								
									}
									
									
								}
								
								
							}
								
								
							
							
						}
							
							
						if( listOfVariableInstances.get(j).isSelectedForMultipleBandpassFilteredTimeSeries){
							
							for(int k=0; k <tempListOfMultipleBandpassFilteredTimeSeries.size(); k++){
								
								for(int l=0; l < tempListOfMultipleBandpassFilteredTimeSeries.get(k).variableInstancesUsedForMultioleTimeSeries.size();l++){
									
									if(tempListOfMultipleBandpassFilteredTimeSeries.get(k).variableInstancesUsedForMultioleTimeSeries.get(l).isVariable){
									
										if(tempListOfMultipleBandpassFilteredTimeSeries.get(k).variableInstancesUsedForMultioleTimeSeries.get(l).variable.name.equals(variableName)){
										
											tempListOfMultipleBandpassFilteredTimeSeries.get(k).variableInstancesUsedForMultioleTimeSeries.remove(l);
											l--;
										}
										}
										
								}
								
								for(int l=0; l < tempListOfMultipleBandpassFilteredTimeSeries.get(k).ratiosUsedForMultioleTimeSeries.size();l++){
									
									if(variableName.equals(tempListOfMultipleBandpassFilteredTimeSeries.get(k).ratiosUsedForMultioleTimeSeries.get(l).denominator.variable.name) || variableName.equals(tempListOfMultipleBandpassFilteredTimeSeries.get(k).ratiosUsedForMultioleTimeSeries.get(l).numerator.variable.name)){
										
										tempListOfMultipleBandpassFilteredTimeSeries.get(k).ratiosUsedForMultioleTimeSeries.remove(l);
										l--;
									
								}
									
									
								}
								
								
							
								
								if(tempListOfMultipleBandpassFilteredTimeSeries.get(k).variableInstancesUsedForMultioleTimeSeries.size()==0 &&  tempListOfMultipleBandpassFilteredTimeSeries.get(k).ratiosUsedForMultioleTimeSeries.size()==0){
									
									tempListOfMultipleBandpassFilteredTimeSeries.remove(k);
									
								}
								
								
							}
							
							
						}
							
						if( listOfVariableInstances.get(j).isSelectedForMultipleTimeSeries ){
							
						
							for(int k=0; k <tempListOfMultipleTimeSeries.size(); k++){
								
								for(int l=0; l < tempListOfMultipleTimeSeries.get(k).variableInstancesUsedForMultioleTimeSeries.size();l++){
									
									if(tempListOfMultipleTimeSeries.get(k).variableInstancesUsedForMultioleTimeSeries.get(l).isVariable){
									
										if(tempListOfMultipleTimeSeries.get(k).variableInstancesUsedForMultioleTimeSeries.get(l).variable.name.equals(variableName)){
										
											tempListOfMultipleTimeSeries.get(k).variableInstancesUsedForMultioleTimeSeries.remove(l);
											l--;
											
										}
											
										}
										
								}
								
								for(int l=0; l < tempListOfMultipleTimeSeries.get(k).ratiosUsedForMultioleTimeSeries.size();l++){
									
									if(variableName.equals(tempListOfMultipleTimeSeries.get(k).ratiosUsedForMultioleTimeSeries.get(l).denominator.variable.name) || variableName.equals(tempListOfMultipleTimeSeries.get(k).ratiosUsedForMultioleTimeSeries.get(l).numerator.variable.name)){
										tempListOfMultipleTimeSeries.get(k).ratiosUsedForMultioleTimeSeries.remove(l);
										l--;
										
									
								}
									
									
								}
								
								
							
								
								if(tempListOfMultipleTimeSeries.get(k).variableInstancesUsedForMultioleTimeSeries.size()==0 &&  tempListOfMultipleTimeSeries.get(k).ratiosUsedForMultioleTimeSeries.size()==0){
									
									tempListOfMultipleTimeSeries.remove(k);
									
								}
								
								
							}
							
								
						}	
						if( listOfVariableInstances.get(j).isSelectedForScatterplots){
							
							for(int k=0; k <tempListOfScatterPlots.size(); k++){
								
								boolean found = false;
								
								if(tempListOfScatterPlots.get(k).firstComponent.isVariableInstance){
									
									if(tempListOfScatterPlots.get(k).firstComponent.variableInstance.isVariable){
										
										
										if(tempListOfScatterPlots.get(k).firstComponent.variableInstance.variable.name.equals(variableName)){
											
											tempListOfScatterPlots.remove(k);
											k--;
											found = true;
											
										}
								
									}
									
									
									
									
									
								}else{
									
								
										if(tempListOfScatterPlots.get(k).firstComponent.ratioInstance.numerator.variable.name.equals(variableName)|| tempListOfCrossCorrelation.get(k).firstComponent.ratioInstance.denominator.variable.name.equals(variableName)){
											
											tempListOfScatterPlots.remove(k);
											k--;
											found = true;
											
										}
								
									
									
									
									
								}
								
								
								if(!found){
									
									if(tempListOfScatterPlots.get(k).secondComponent.isVariableInstance){
										
										if(tempListOfScatterPlots.get(k).secondComponent.variableInstance.isVariable){
											
											
											if(tempListOfScatterPlots.get(k).secondComponent.variableInstance.variable.name.equals(variableName)){
												
												tempListOfScatterPlots.remove(k);
												k--;
												found = true;
												
											}
									
										}
										
										
										
										
										
									}else{
										
									
											if(tempListOfScatterPlots.get(k).secondComponent.ratioInstance.numerator.variable.name.equals(variableName)|| tempListOfCrossCorrelation.get(k).firstComponent.ratioInstance.denominator.variable.name.equals(variableName)){
												
												tempListOfScatterPlots.remove(k);
												k--;
												found = true;
												
											}
								
									}
									
									
								}
								
								
							}
							
							
							
							
						}
									
									
						if(  listOfVariableInstances.get(j).selectedForSingleBandpassFilteredTimeSeries  )
						{
							
							for(int k=0; k <tempListOfSingleBandpassFilteredTimeSeries.size(); k++){
								
								
								if(tempListOfSingleBandpassFilteredTimeSeries.get(k).isVariableInstance){
									
									if(variableName.equals(tempListOfSingleBandpassFilteredTimeSeries.get(k).variableInstance.variable.name)){
										
										tempListOfSingleBandpassFilteredTimeSeries.remove(k);
										k--;
										
									}
									
						
						
								}else{
									
										if(variableName.equals(tempListOfSingleBandpassFilteredTimeSeries.get(k).ratioInstance.denominator.variable.name) || variableName.equals(tempListOfSingleBandpassFilteredTimeSeries.get(k).ratioInstance.numerator.variable.name)){
										
											tempListOfSingleBandpassFilteredTimeSeries.remove(k);
										k--;
										
									}
									
									
								}
								
								
								
							}
							
						}
						if( listOfVariableInstances.get(j).selectedForSingleTimeSeries){
							
							for(int k=0; k <tempListOfSingleTimeSeries.size(); k++){
								
								
								if(tempListOfSingleTimeSeries.get(k).isVariableInstance){
									
									if(tempListOfSingleTimeSeries.get(k).variableInstance.isVariable){
									
										if(variableName.equals(tempListOfSingleTimeSeries.get(k).variableInstance.variable.name)){
											
											tempListOfSingleTimeSeries.remove(k);
											k--;
											
										}
									}else{
										
										
										if(variableName.equals(tempListOfSingleTimeSeries.get(k).variableInstance.agentRatio.ratioName)){
											
											tempListOfSingleTimeSeries.remove(k);
											k--;
											
										}
										
										
									}
									
						
						
								}else{
									
										if(variableName.equals(tempListOfSingleTimeSeries.get(k).ratioInstance.denominator.variable.name) || variableName.equals(tempListOfSingleTimeSeries.get(k).ratioInstance.numerator.variable.name)){
										
										tempListOfSingleTimeSeries.remove(k);
										k--;
										
									}
									
									
								}
								
								
								
							}
							
							
							
						}
					
					
						listOfVariableInstances.remove(j);
						j--;
					
					}
					
					
					
						
				}

			}
			
	
		
		

		}
	
	
	
	
	
	
	
	
	public boolean updateRatioSettings(String ratioName){
		

		
		boolean removevariable = true;
		
		for(int j=0; j<tempRatioListForPlotting.size();j++){
			

			if(tempRatioListForPlotting.get(j).ratioInstanceName.equals(ratioName)){
				

			
					PlottingSettings.RatioInstance tempVarInst = tempRatioListForPlotting.get(j);
					
					if(tempVarInst.isSelectedForCrossCorrelation || tempVarInst.isSelectedForMultipleBandpassFilteredTimeSeries || tempVarInst.isSelectedForMultipleTimeSeries || tempVarInst.isSelectedForScatterplots || tempVarInst.selectedForSingleBandpassFilteredTimeSeries || tempVarInst.selectedForSingleTimeSeries){
						
						Object text3 = "The variable "+ratioName+" is used for plotting Time Series. Delete anyhow?";
						
						int choiceIsRatio = JOptionPane.showConfirmDialog(null, text3,  "",JOptionPane.YES_NO_OPTION);
						
						if(choiceIsRatio == 0){
							
							removevariable = true;
							
						}else{
							
							removevariable = false;
							break;
							
						}
						
					}
					
					
					
					
				
				

				if(removevariable){
		
				
					if( tempRatioListForPlotting.get(j).isSelectedForCrossCorrelation ){
						
						for(int k=0; k <tempListOfCrossCorrelation.size(); k++){
							
							boolean found = false;
							
							if(!tempListOfCrossCorrelation.get(k).firstComponent.isVariableInstance){
								
					
								if(tempListOfCrossCorrelation.get(k).firstComponent.ratioInstance.ratioInstanceName.equals(ratioName)){
									
									tempListOfCrossCorrelation.remove(k);
									k--;
									found = true;
									
								}
						
							}
				
							if(!found){
								
								if(!tempListOfCrossCorrelation.get(k).secondComponent.isVariableInstance){
									
									if(tempListOfCrossCorrelation.get(k).secondComponent.variableInstance.isVariable){
										
										
										if(tempListOfCrossCorrelation.get(k).secondComponent.variableInstance.variable.name.equals(ratioName)){
											
											tempListOfCrossCorrelation.remove(k);
											k--;
											found = true;
											
										}
								
									}
									
					
							}
							
							
						}
							
							
						
						
					}
							
					}
						
					
						
						
					if( tempRatioListForPlotting.get(j).isSelectedForMultipleBandpassFilteredTimeSeries){
						
						for(int k=0; k <tempListOfMultipleBandpassFilteredTimeSeries.size(); k++){
							
							for(int l=0; l < tempListOfMultipleBandpassFilteredTimeSeries.get(k).ratiosUsedForMultioleTimeSeries.size();l++){
								
								if(ratioName.equals(tempListOfMultipleBandpassFilteredTimeSeries.get(k).ratiosUsedForMultioleTimeSeries.get(l).ratioInstanceName)){
									
									tempListOfMultipleBandpassFilteredTimeSeries.get(k).ratiosUsedForMultioleTimeSeries.remove(l);
									l--;
								
							}
								
								
							}
							
							
						
							
							if(tempListOfMultipleBandpassFilteredTimeSeries.get(k).variableInstancesUsedForMultioleTimeSeries.size()==0 &&  tempListOfMultipleBandpassFilteredTimeSeries.get(k).ratiosUsedForMultioleTimeSeries.size()==0){
								
								tempListOfMultipleBandpassFilteredTimeSeries.remove(k);
								
							}
							
							
						}
						
						
					}
						
					if( tempRatioListForPlotting.get(j).isSelectedForMultipleTimeSeries ){
						
					
						for(int k=0; k <tempListOfMultipleTimeSeries.size(); k++){
							
						
							
							for(int l=0; l < tempListOfMultipleTimeSeries.get(k).ratiosUsedForMultioleTimeSeries.size();l++){
								
								if(ratioName.equals(tempListOfMultipleTimeSeries.get(k).ratiosUsedForMultioleTimeSeries.get(l).ratioInstanceName)){
									
									tempListOfMultipleTimeSeries.get(k).ratiosUsedForMultioleTimeSeries.remove(l);
									l--;
								
							}
								
								
							}
							
							
						
							
							if(tempListOfMultipleTimeSeries.get(k).variableInstancesUsedForMultioleTimeSeries.size()==0 &&  tempListOfMultipleTimeSeries.get(k).ratiosUsedForMultioleTimeSeries.size()==0){
								
								tempListOfMultipleTimeSeries.remove(k);
								
							}
							
							
						}
						
							
					}	
					if( tempRatioListForPlotting.get(j).isSelectedForScatterplots){
						
						for(int k=0; k <tempListOfScatterPlots.size(); k++){
							
							boolean found = false;
							
							if(!tempListOfScatterPlots.get(k).firstComponent.isVariableInstance){
								
							
									
									
									if(tempListOfScatterPlots.get(k).firstComponent.ratioInstance.ratioInstanceName.equals(ratioName)){
										
										tempListOfScatterPlots.remove(k);
										k--;
										found = true;
										
									}
							
								}
							
						
								
								
							
							
							if(!found){
								
								if(!tempListOfScatterPlots.get(k).secondComponent.isVariableInstance){
				
									if(tempListOfScatterPlots.get(k).secondComponent.ratioInstance.ratioInstanceName.equals(ratioName)){
										
										tempListOfScatterPlots.remove(k);
										k--;
										found = true;
										
									}
			
							}
		
						}
	
					}
						
					}
								
								
					if(  tempRatioListForPlotting.get(j).selectedForSingleBandpassFilteredTimeSeries  )
					{
						
						for(int k=0; k <tempListOfSingleBandpassFilteredTimeSeries.size(); k++){
							
							
							if(!tempListOfSingleBandpassFilteredTimeSeries.get(k).isVariableInstance){
								
								if(ratioName.equals(tempListOfSingleBandpassFilteredTimeSeries.get(k).ratioInstance.ratioInstanceName)){
									
									tempListOfSingleBandpassFilteredTimeSeries.remove(k);
									k--;
									
								}
								
					
					
							}
							
							
							
						}
						
					}
					
					
					
					
					if( tempRatioListForPlotting.get(j).selectedForSingleTimeSeries){
						
						
						
						System.out.println("NDD "+ratioName);
						
						for(int k=0; k <tempListOfSingleTimeSeries.size(); k++){
							
							
							if(!tempListOfSingleTimeSeries.get(k).isVariableInstance){
								
								
								System.out.println("NDD "+ratioName +" "+tempListOfSingleTimeSeries.get(k).ratioInstance.ratioInstanceName);
				
									if(ratioName.equals(tempListOfSingleTimeSeries.get(k).ratioInstance.ratioInstanceName)){
										
										tempListOfSingleTimeSeries.remove(k);
										k--;
										
									}
								}
			
							}
							
						}
						
						
						
					}
			
				
				}
				
				
				
					
			}

		
		
		return removevariable;

		}
	

}


