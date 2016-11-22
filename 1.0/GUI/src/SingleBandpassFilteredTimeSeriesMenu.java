import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
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
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;


public class SingleBandpassFilteredTimeSeriesMenu  extends JDialog{
	

	private JList notChosenVariableList;
	private DefaultListModel dlmParameter; 
	private DefaultListModel dlm2Parameter ;
	private JList chosenVariableList ;
	private JButton  saveVariables, cancelVariables ;

	/*Variables for menu bar:*/
	private JMenuBar menuBar;
	
	private JMenu menuTimeSeries;
	private JMenuItem addAgent, removeAgent, applyChanges, exit;
	
	
	private JMenu menuVariables;
	private JMenuItem addRemoveVariables;
	private JMenuItem addRemoveRatios;
	
	private JMenu menuSettings;
	
	private JList agentList;
	private JList removeAgentList;
	private JDialog addAgentDialog , removeAgentDialog;

	private	JTabbedPane tabbedPane;
	private	ArrayList<AgentTab> agentJPanelList;
	private JDialog setNewVariables;
	
	ArrayList<PlottingSettings.SingleBandpassFilteredTimeSeries> tempSingleBandpassFilteredTimeSeries;
	ArrayList<PlottingSettings.Agent> tempAgentInstanceVariable;
	ArrayList<PlottingSettings.RatioInstance>  tempRatioInstanceVariable;
	
	PlottingSettings.DefaultSettingsSingleBandpassFilteredTimeSeries tempDefaults;
	
	boolean tempRatiosSelectedForSingleTimeSeries;
	
	private RatioTab ratioTab;

	SingleBandpassFilteredTimeSeriesMenu(){
		
		//agentsReturn = agentsPassed;
		
		tempSingleBandpassFilteredTimeSeries = new ArrayList<PlottingSettings.SingleBandpassFilteredTimeSeries>();

		for(int i=0; i< PlottingSettings.listOfSingleBandpassFilteredTimeSeries.size();i++){
			
			
			tempSingleBandpassFilteredTimeSeries.add(AuxFunctions.DeepCopySingleBandpassFilteredTimeSeriesList(PlottingSettings.listOfSingleBandpassFilteredTimeSeries.get(i)));
			
		}
		
		tempAgentInstanceVariable = new ArrayList<PlottingSettings.Agent> ();
		
		for(int i=0; i< PlottingSettings.listOfAgentsVariableInstances.size();i++){
			
			
			tempAgentInstanceVariable.add(AuxFunctions.DeepCopyAgentVariableInstance(PlottingSettings.listOfAgentsVariableInstances.get(i)));
			
		}
		
		tempRatioInstanceVariable = new ArrayList<PlottingSettings.RatioInstance>();
		
		
		for(int i=0; i< PlottingSettings.listOfRatioInstances.size();i++){
			
			
			tempRatioInstanceVariable.add(AuxFunctions.DeepCopyRatioInstance(PlottingSettings.listOfRatioInstances.get(i)));
			
		}
		
		tempRatiosSelectedForSingleTimeSeries = PlottingSettings.ratiosSelectedForSingleTimeSeries;

		tempDefaults = PlottingSettings.defaultsSingleBandpassFilteredTimeSeries.clone();
		
		
		/*Dialog settings*/	
	    setTitle("Single bandpass filtered Time Series");
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

	    			PlottingSettings.listOfSingleBandpassFilteredTimeSeries = tempSingleBandpassFilteredTimeSeries;
	    			PlottingSettings.listOfAgentsVariableInstances = tempAgentInstanceVariable;
	    			PlottingSettings.listOfRatioInstances = tempRatioInstanceVariable;
	    			PlottingSettings.ratiosSelectedForSingleTimeSeries = tempRatiosSelectedForSingleTimeSeries;
	    			setVisible(false);
	    			dispose();
	    			
	    			
	    		}else if(choice==1){
	    			
	    			/*Choice is no*/
	    			PlottingSettings.defaultsSingleBandpassFilteredTimeSeries = tempDefaults;
	    			setVisible(false);
	    			dispose();
	    			
	    		}
	   
    			
			}
			});
		
		menuTimeSeries = new JMenu("Agent");
		
		addAgent= new JMenuItem("Add Agent");
		addAgent.addActionListener(new ActionListener(){
			
			
			DefaultListModel dlmAgents;

			public void actionPerformed(ActionEvent arg0) {

				addAgentDialog = new JDialog();
				addAgentDialog.setSize(300,300);
				addAgentDialog.setLayout(new GridBagLayout());
				
				GridBagConstraints fA = new GridBagConstraints();
				fA.insets = new Insets( 5, 5, 5, 5);
				
				JLabel label1 = new JLabel("Select Agent for Plotting:");
				fA.gridx =0;fA.gridy =0;
				addAgentDialog.add(label1,fA);
				
				dlmAgents = new DefaultListModel();
				
				agentList = new JList(dlmAgents);
				
				for(int i=0; i<tempAgentInstanceVariable.size();i++){
					
					if(tempAgentInstanceVariable.get(i).isSelected && !tempAgentInstanceVariable.get(i).singleBandpassFilteredTimeSeriesSelected){
						
						dlmAgents.addElement(tempAgentInstanceVariable.get(i).agentName);
						
					}
				}
				
				if(tempRatioInstanceVariable.size()>0){
					
					if(tempRatiosSelectedForSingleTimeSeries==false)
						dlmAgents.addElement("Ratios");	
				}
				
				agentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				agentList.setVisibleRowCount(6);
				
				agentList.addMouseListener(new MouseListener(){
					
					public void mouseClicked(MouseEvent e) {

						System.out.println("One Click in row");
						
						if(e.getClickCount()==2){
							
							System.out.println("Double Click in row");
							
							
							if(	agentList.getModel().getElementAt(agentList.locationToIndex(e.getPoint())).equals("Ratios")){
							
								ratioTab = new RatioTab(tempRatioInstanceVariable);
								
								tabbedPane.addTab("Ratio",ratioTab);
							
	
								System.out.println("agentList.locationToIndex(e.getPoint())  "+agentList.locationToIndex(e.getPoint()));
								System.out.println("agentList "+dlmAgents.size());

								tempRatiosSelectedForSingleTimeSeries = true;
				
							
							}else{
							
								for(int i=0; i<tempAgentInstanceVariable.size();i++){
									
									if(tempAgentInstanceVariable.get(i).agentName.equals(agentList.getModel().getElementAt(agentList.locationToIndex(e.getPoint())))){
										 //Create the tab pages for the agent that is selected for plotting
										
										
										agentJPanelList.add(new AgentTab(tempAgentInstanceVariable.get(i)));
										
										tabbedPane.insertTab(tempAgentInstanceVariable.get(i).agentName, null ,agentJPanelList.get(agentJPanelList.size()-1),null,agentJPanelList.size()-1);
										
										
										tempAgentInstanceVariable.get(i).singleBandpassFilteredTimeSeriesSelected = true;
								

										break;	
									}
									
								}
							}
							
							//If all agents are selected for plotting deactivate the addAgent menu item
							if(dlmAgents.size()== 1){
								addAgent.setEnabled(false);
							}
								
							removeAgent.setEnabled(true);
							menuSettings.setEnabled(true);
							menuVariables.setEnabled(true);
							
							
							addAgentDialog.dispose();
							addAgentDialog.setVisible(false);
							
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
		menuTimeSeries.add(addAgent);
		
		removeAgent= new JMenuItem("Remove Agent");
		removeAgent.addActionListener(new ActionListener(){
			
			DefaultListModel dlmAgentsToremove;

			public void actionPerformed(ActionEvent arg0) {

				removeAgentDialog = new JDialog();
				removeAgentDialog.setSize(300,300);
				removeAgentDialog.setLayout(new GridBagLayout());
				
				GridBagConstraints fA = new GridBagConstraints();
				fA.insets = new Insets( 5, 5, 5, 5);
				
				JLabel label1 = new JLabel("Delete Agent from Plotting Bandpass filtered Time Series:");
				fA.gridx =0;fA.gridy =0;
				removeAgentDialog.add(label1,fA);
				
				dlmAgentsToremove = new DefaultListModel();
				
				removeAgentList = new JList(dlmAgentsToremove);
				
				for(int i=0; i<tempAgentInstanceVariable.size();i++){
					
					if(tempAgentInstanceVariable.get(i).isSelected && tempAgentInstanceVariable.get(i).singleBandpassFilteredTimeSeriesSelected){
						
						dlmAgentsToremove.addElement(tempAgentInstanceVariable.get(i).agentName);
						
					}
					
				}
				
				if(tempRatioInstanceVariable.size()>0){
					
					if(tempRatiosSelectedForSingleTimeSeries==true)
						dlmAgentsToremove.addElement("Ratios");	
				}
				
				removeAgentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				removeAgentList.setVisibleRowCount(6);
				
				removeAgentList.addMouseListener(new MouseListener(){
					
					public void mouseClicked(MouseEvent e) {

						System.out.println("One Click in row");
						
						if(e.getClickCount()==2){
							
							System.out.println("Double Click in row");
							
							
							if(	removeAgentList.getModel().getElementAt(removeAgentList.locationToIndex(e.getPoint())).equals("Ratios")){
								
					
								
								tabbedPane.remove(ratioTab);
							
								removeAgentDialog.dispose();
								removeAgentDialog.setVisible(false);
								
								tempRatiosSelectedForSingleTimeSeries = false;

	
							}else{
							
								for(int i=0; i<agentJPanelList.size();i++){
									
									if(agentJPanelList.get(i).agent.agentName.equals(removeAgentList.getModel().getElementAt(removeAgentList.locationToIndex(e.getPoint())))){
										 //Create the tab pages for the agent that is selected for plotting
						
										for(int j=0; j< tempAgentInstanceVariable.size();j++){
											
											if(agentJPanelList.get(i).agent.agentName.equals(tempAgentInstanceVariable.get(j).agentName)){
												
												tempAgentInstanceVariable.get(j).singleBandpassFilteredTimeSeriesSelected = false;
												break;
											}
											
											
										}
										
										
										agentJPanelList.remove(i);
										tabbedPane.remove(i);

										removeAgentDialog.dispose();
										removeAgentDialog.setVisible(false);
										
										//If all agents are selected for plotting deactivate the addAgent menu item
										
										addAgent.setEnabled(true);
										
								
										
										
										break;	
									}
									
								}
							}
							
							
							if(dlmAgentsToremove.size()==1){
								
								removeAgent.setEnabled(false);
								menuSettings.setEnabled(false);
								menuVariables.setEnabled(false);
								
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
	
		menuTimeSeries.add(removeAgent);
		
		
		
		applyChanges = new JMenuItem("Apply Changes");
		applyChanges.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {

				PlottingSettings.listOfSingleBandpassFilteredTimeSeries = tempSingleBandpassFilteredTimeSeries;
    			PlottingSettings.listOfAgentsVariableInstances = tempAgentInstanceVariable;
    			PlottingSettings.listOfRatioInstances = tempRatioInstanceVariable;
    			PlottingSettings.ratiosSelectedForSingleTimeSeries = tempRatiosSelectedForSingleTimeSeries;
			}
		});
		menuTimeSeries.add(applyChanges);
		
		exit = new JMenuItem("Exit");
		exit.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {

				Object text = "Apply Changes? \n";
	    		
	    		int choice = JOptionPane.showConfirmDialog(null, text,  "Exit GUI",JOptionPane.YES_NO_CANCEL_OPTION);
	   
	    		if(choice==0){
	 
	    			/*Save Settings*/

	    			PlottingSettings.listOfSingleBandpassFilteredTimeSeries = tempSingleBandpassFilteredTimeSeries;
	    			PlottingSettings.listOfAgentsVariableInstances = tempAgentInstanceVariable;
	    			PlottingSettings.listOfRatioInstances = tempRatioInstanceVariable;
	    			PlottingSettings.ratiosSelectedForSingleTimeSeries = tempRatiosSelectedForSingleTimeSeries;
	    			
	    			setVisible(false);
	    			dispose();
	    			
	    			
	    		}else if(choice==1){
	    			
	    			/*Choice is no*/
	    			
	    			PlottingSettings.defaultsSingleBandpassFilteredTimeSeries = tempDefaults; 
	    			
	    			setVisible(false);
	    			dispose();
	    			
	    		}
	   
				
			}
		});
		menuTimeSeries.add(exit);
		menuBar.add(menuTimeSeries);
		
		
		menuVariables = new JMenu("Time Series");
		
		addRemoveVariables = new JMenuItem("Add/Remove Variables") ;
		
		addRemoveVariables.addActionListener(new ActionListener(){
		
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

	   		     
	   		        
	   		        String tooltip = "";
	   		   	
		   	   		  try{
		   	   			  
		   	   		   // Get item
			   		        Object item = getModel().getElementAt(index);
		   	   			  
		   		        for(int j = 0; j<agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.listOfVariableInstances.size();j++)
		   		        {
		   		        	
			   		        	if(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.listOfVariableInstances.get(j).instanceName.equals(item)){
			   		        		
			   		        		tooltip = agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.listOfVariableInstances.get(j).instanceName+
			   		        		":  Variable: "+agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.listOfVariableInstances.get(j).getName()+
			   		        		"  Method:"+agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.listOfVariableInstances.get(j).instanceMethod  
			   		        				+"   Filter 1: "+ agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.listOfVariableInstances.get(j).whichFilter1+
			   		        				"  Filter 2: "+agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.listOfVariableInstances.get(j).whichFilter2+
			   		        				"  Partitioning "+agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.listOfVariableInstances.get(j).whichPartitioning ;
			   		        		break;
		   		        	}
		   		        	
		   		        	
		   		        }
				}catch(Exception e){
					System.out.println(e);
				}

	   		        // Return the tool tip text
	   		        return tooltip;
	   		    }
	   			};
	   			
	   			
	   			
	   			notChosenVariableList.addMouseListener(new MouseListener(){
					
					public void mouseClicked(MouseEvent e) {
	   			
						System.out.println("Right click");
						
						if(SwingUtilities.isRightMouseButton(e)){
							
							System.out.println("Right click");
							
							SortedListModel sortedModel = new SortedListModel(dlmParameter);
							notChosenVariableList.setModel(sortedModel);
							
							
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
	   	   		        // Get item index
	   						int index = locationToIndex(evt.getPoint());

	   	   		        // Get item
	   						
	   						String tooltip = "";
	   					try{	
	   						
		   	   		        Object item = getModel().getElementAt(index);

		   	   		  for(int j = 0; j<agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.listOfVariableInstances.size();j++)
		   		        {
		   		        	
			   		        	if(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.listOfVariableInstances.get(j).instanceName.equals(item)){
			   		        		
			   		        		tooltip = agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.listOfVariableInstances.get(j).instanceName+
			   		        		":  Variable: "+agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.listOfVariableInstances.get(j).getName()+
			   		        		"  Method:"+agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.listOfVariableInstances.get(j).instanceMethod  
			   		        				+"   Filter 1: "+ agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.listOfVariableInstances.get(j).whichFilter1+
			   		        				"  Filter 2: "+agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.listOfVariableInstances.get(j).whichFilter2+
			   		        				"  Partitioning "+agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.listOfVariableInstances.get(j).whichPartitioning ;
			   		        		break;
		   		        	}
		   		        	
		   		        	
		   		        }
			   		        	
			   		        
	   	   		        }catch(Exception e){
	   	   		        	
	   	   		        	System.out.println(e);
	   	   		        	
	   	   		        }

	   	   		        // Return the tool tip text
	   	   		        return tooltip;
	   	   		    }
	   				
	   				
	   				
	   	   			};
	   	   			
	   	   			/*Setup lists:*/
	   	   			
	   	   			
				for(int i=0; i < agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.listOfVariableInstances.size();i++)
				{
					
					
						if(!agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.listOfVariableInstances.get(i).selectedForSingleBandpassFilteredTimeSeries)
						{
							dlmParameter.addElement(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.listOfVariableInstances.get(i).instanceName);
						}else
						{
							dlm2Parameter.addElement(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.listOfVariableInstances.get(i).instanceName);
						
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
							
							dlm2Parameter.addElement(dlmParameter.get(selectedIndices[i]));
				
						}
						for(int i=selectedIndices.length-1;i>=0;i--){
							
							dlmParameter.remove(selectedIndices[i]);
							
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
							
							dlmParameter.addElement(dlm2Parameter.get(selectedIndices[i]));
				
						}
						for(int i=selectedIndices.length-1;i>=0;i--){

							dlm2Parameter.remove(selectedIndices[i]);
							
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
	    	    				for(int j=0; j<agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.listOfVariableInstances.size();j++){
					
									if(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.listOfVariableInstances.get(j).instanceName.equals(dlmParameter.get(i))){									
										
										agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.listOfVariableInstances.get(j).selectedForSingleBandpassFilteredTimeSeries = false;
										
										/*Delete from temp TS list:*/
										
										for(int k=0; k<tempSingleBandpassFilteredTimeSeries.size();k++){
											
											if(tempSingleBandpassFilteredTimeSeries.get(k).isVariableInstance && tempSingleBandpassFilteredTimeSeries.get(k).instanceName.equals(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.listOfVariableInstances.get(j).instanceName)){
												
												tempSingleBandpassFilteredTimeSeries.remove(k);
												k--;
												
											}
											
										}
										

										break;
									}
									
								}	
	    	    			
	    	    			}
	    	    			
	    	    			for(int i=0; i<dlm2Parameter.getSize(); i++ ){    	    			
	    	    				for(int j=0; j<agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.listOfVariableInstances.size();j++){
									
	    							
									
									if(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.listOfVariableInstances.get(j).instanceName.equals(dlm2Parameter.get(i))){
										
										if(!agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.listOfVariableInstances.get(j).selectedForSingleBandpassFilteredTimeSeries){
										
										agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.listOfVariableInstances.get(j).selectedForSingleBandpassFilteredTimeSeries = true;
										
										/*Add to temp TS list:*/
										tempSingleBandpassFilteredTimeSeries.add((new PlottingSettings()).new SingleBandpassFilteredTimeSeries(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.listOfVariableInstances.get(j)));					
										break;
										}
									}
								}
			    			
			    			}
	    	
	    	    			/*Redraw table*/
	    	    			agentJPanelList.get(tabbedPane.getSelectedIndex()).reDrawTable();
	    	    			
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
    	    				for(int j=0; j<agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.listOfVariableInstances.size();j++){
				
								if(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.listOfVariableInstances.get(j).instanceName.equals(dlmParameter.get(i))){									
									
									agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.listOfVariableInstances.get(j).selectedForSingleBandpassFilteredTimeSeries = false;
									
									/*Delete from temp TS list:*/
									
									for(int k=0; k<tempSingleBandpassFilteredTimeSeries.size();k++){
										
										if(tempSingleBandpassFilteredTimeSeries.get(k).isVariableInstance && tempSingleBandpassFilteredTimeSeries.get(k).instanceName.equals(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.listOfVariableInstances.get(j).instanceName)){
											
											tempSingleBandpassFilteredTimeSeries.remove(k);
											k--;
											
										}
										
									}
									

									break;
								}
								
							}	
    	    			
    	    			}
    	    			
    	    			for(int i=0; i<dlm2Parameter.getSize(); i++ ){    	    			
    	    				for(int j=0; j<agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.listOfVariableInstances.size();j++){
								
    							
								
								if(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.listOfVariableInstances.get(j).instanceName.equals(dlm2Parameter.get(i))){
									
									if(!agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.listOfVariableInstances.get(j).selectedForSingleBandpassFilteredTimeSeries){
									
									agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.listOfVariableInstances.get(j).selectedForSingleBandpassFilteredTimeSeries = true;
									
									/*Add to temp TS list:*/
									tempSingleBandpassFilteredTimeSeries.add((new PlottingSettings()).new SingleBandpassFilteredTimeSeries(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.listOfVariableInstances.get(j)));					
									break;
									}
								}
							}
		    			
		    			}
    	
    	    			/*Redraw table*/
    	    			agentJPanelList.get(tabbedPane.getSelectedIndex()).reDrawTable();
    	    			
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
		
		
		
		
		
		
		
		addRemoveRatios = new JMenuItem("Add/Remove Ratios") ;
		
		addRemoveRatios.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				
				setNewVariables = new JDialog();
	    		setNewVariables.setTitle("Add/Remove Ratios");
	    		
	    		
	    		JPanel setNewVariablesPanel = new JPanel();
	    		JScrollPane setNewVariablesScrollPane;
	    		
	    		setNewVariablesPanel.setLayout(new GridBagLayout());
	   			GridBagConstraints ts = new GridBagConstraints();
	   			ts.insets = new Insets( 5, 5, 5, 5);
	   			
	   			JPanel panelA = new JPanel();
	   			panelA.setSize(200, 800);
	   			
	   			dlmParameter = new DefaultListModel();
	   			notChosenVariableList = new JList(dlmParameter);
	   			
	   			dlm2Parameter = new DefaultListModel();
	   			chosenVariableList = new JList(dlm2Parameter);
	   	   			
	   	   			/*Setup lists:*/
	   	   			
				for(int i=0; i < tempRatioInstanceVariable.size();i++)
				{

						if(!tempRatioInstanceVariable.get(i).selectedForSingleBandpassFilteredTimeSeries)
						{
							dlmParameter.addElement( tempRatioInstanceVariable.get(i).ratioInstanceName);
						}else
						{
							dlm2Parameter.addElement( tempRatioInstanceVariable.get(i).ratioInstanceName);
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
							
							dlm2Parameter.addElement(dlmParameter.get(selectedIndices[i]));
							
						}
						for(int i=selectedIndices.length-1;i>=0;i--){
							
							dlmParameter.remove(selectedIndices[i]);
							
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
							
							dlmParameter.addElement(dlm2Parameter.get(selectedIndices[i]));
				
							
						}
						for(int i=selectedIndices.length-1;i>=0;i--){
							
							dlm2Parameter.remove(selectedIndices[i]);
							
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
	    	    				for(int j=0; j<tempRatioInstanceVariable.size();j++){
	    	    					
	    	    				
			    						if(tempRatioInstanceVariable.get(j).ratioInstanceName.equals(dlmParameter.get(i))){
			    							
			    							tempRatioInstanceVariable.get(j).selectedForSingleBandpassFilteredTimeSeries = false;
			    							
			    	
											
											/*Delete from temp TS list:*/
											
											for(int k=0; k<tempSingleBandpassFilteredTimeSeries.size();k++){
												
												if(!tempSingleBandpassFilteredTimeSeries.get(k).isVariableInstance && tempSingleBandpassFilteredTimeSeries.get(k).instanceName.equals(tempRatioInstanceVariable.get(j).ratioInstanceName)){
													
													tempSingleBandpassFilteredTimeSeries.remove(k);
													k--;
													
												}
												
											}
			    				
			    							break;
			    						}
	    	    					
	    						
	    	    				}
	    	    			
	    	    			}
	    	    			
	    	    			for(int i=0; i<dlm2Parameter.getSize(); i++ ){    	    			
	    	    				for(int j=0; j<tempRatioInstanceVariable.size();j++){
	    	    					
	    	    					
			    						if(tempRatioInstanceVariable.get(j).ratioInstanceName.equals(dlm2Parameter.get(i)) && tempRatioInstanceVariable.get(j).selectedForSingleBandpassFilteredTimeSeries==false){
		    							
			    							tempRatioInstanceVariable.get(j).selectedForSingleBandpassFilteredTimeSeries = true;
			    							
			    							tempSingleBandpassFilteredTimeSeries.add((new PlottingSettings()).new SingleBandpassFilteredTimeSeries(tempRatioInstanceVariable.get(j)));
			    		
			    							break;
		    							}
	    	    					}
								
			    				
			    			
			    			}
	    	
	    	    			/*Redraw table*/
	    	    			
	    	    			tabbedPane.remove(ratioTab);
	    	    			
	    	    			ratioTab = new RatioTab(tempRatioInstanceVariable);
	    	    			
	    	    			ratioTab.reDrawTable();
	    	    			
	    	    			System.out.println(tempRatioInstanceVariable.size());
	    	    			
	    	    			if(dlm2Parameter.getSize()>0)
	    	    				tabbedPane.addTab("Ratio",ratioTab);
	    	    			
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
    	    				for(int j=0; j<tempRatioInstanceVariable.size();j++){
    	    					
    	    				
		    						if(tempRatioInstanceVariable.get(j).ratioInstanceName.equals(dlmParameter.get(i))){
		    							
		    							tempRatioInstanceVariable.get(j).selectedForSingleBandpassFilteredTimeSeries = false;
		    							
		    	
										
										/*Delete from temp TS list:*/
										
										for(int k=0; k<tempSingleBandpassFilteredTimeSeries.size();k++){
											
											if(!tempSingleBandpassFilteredTimeSeries.get(k).isVariableInstance && tempSingleBandpassFilteredTimeSeries.get(k).instanceName.equals(tempRatioInstanceVariable.get(j).ratioInstanceName)){
												
												tempSingleBandpassFilteredTimeSeries.remove(k);
												k--;
												
											}
											
										}
		    				
		    							break;
		    						}
    	    					
    						
    	    				}
    	    			
    	    			}
    	    			
    	    			for(int i=0; i<dlm2Parameter.getSize(); i++ ){    	    			
    	    				for(int j=0; j<tempRatioInstanceVariable.size();j++){
    	    					
    	    					
		    						if(tempRatioInstanceVariable.get(j).ratioInstanceName.equals(dlm2Parameter.get(i))){
	    							
		    							tempRatioInstanceVariable.get(j).selectedForSingleBandpassFilteredTimeSeries = true;
		    							
		    							tempSingleBandpassFilteredTimeSeries.add((new PlottingSettings()).new SingleBandpassFilteredTimeSeries(tempRatioInstanceVariable.get(j)));
		    		
		    							break;
	    							}
    	    					}
							
		    				
		    			
		    			}
    	
    	    			/*Redraw table*/
    	    			
    	    			tabbedPane.remove(ratioTab);
    	    			
    	    			ratioTab = new RatioTab(tempRatioInstanceVariable);
    	    			
    	    			ratioTab.reDrawTable();
    	    			
    	    			System.out.println(tempRatioInstanceVariable.size());
    	    			
    	    			if(dlm2Parameter.getSize()>0)
    	    				tabbedPane.addTab("Ratio",ratioTab);
    	    			
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
		
		menuVariables.add(addRemoveRatios);
		menuBar.add(menuVariables);
		
		
		
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
	   			textfield1.setText(Integer.toString(PlottingSettings.defaultsSingleBandpassFilteredTimeSeries.tmin));
	   			
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
	   			textfield2.setText(Integer.toString(PlottingSettings.defaultsSingleBandpassFilteredTimeSeries.tmax));
	   			
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
	   			
	   			
	   			combo1.setSelectedItem(PlottingSettings.defaultsSingleBandpassFilteredTimeSeries.aggregation);
	   			
	   			enterDS.gridx=0; enterDS.gridy=5;
	   			defaultSettingsDialog.add(label44,enterDS);
	   			
	   			enterDS.gridx=1; enterDS.gridy=5;
	   			defaultSettingsDialog.add(combo1,enterDS);
	   			
	   			
	   			JLabel label42 = new JLabel("Correlation");
	   			enterDS.gridx=0; enterDS.gridy=6;
	   			defaultSettingsDialog.add(label42,enterDS);
	   			
	   			CBUpperBound2 = new JCheckBox(); 
	   			
	   			if(PlottingSettings.defaultsSingleBandpassFilteredTimeSeries.correlation)
	   				CBUpperBound2.setSelected(true);
	   			
	   			enterDS.gridx=1; enterDS.gridy=6;
	   			defaultSettingsDialog.add(CBUpperBound2,enterDS);
	   			
	   			
	   			
	   			JLabel label43 = new JLabel("Logaritmic");
	   			enterDS.gridx=0; enterDS.gridy=7;
	   			defaultSettingsDialog.add(label43,enterDS);
	   			
	   			CBUpperBound3 = new JCheckBox(); 
	   			
	   			if(PlottingSettings.defaultsSingleBandpassFilteredTimeSeries.logarithmic)
	   				CBUpperBound3.setSelected(true);
	   			
	   			enterDS.gridx=1; enterDS.gridy=7;
	   			defaultSettingsDialog.add(CBUpperBound3,enterDS);
	   			
	   			
	   			
	   			
	   			
	   			JLabel label5 = new JLabel("Low");
	   			textfield5 = new JTextField(5);
	   			textfield5.setText(Integer.toString(PlottingSettings.defaultsSingleBandpassFilteredTimeSeries.low));
	   			
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
	   			textfield6.setText(Integer.toString(PlottingSettings.defaultsSingleBandpassFilteredTimeSeries.high));
	   			
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
	   			textfield7.setText(Integer.toString(PlottingSettings.defaultsSingleBandpassFilteredTimeSeries.Nfix));
	   			
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
	   			
	   			if(PlottingSettings.defaultsSingleBandpassFilteredTimeSeries.drift)
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
	  	    				PlottingSettings.defaultsSingleBandpassFilteredTimeSeries.tmin = Integer.parseInt(textfield1.getText());
	  	    				PlottingSettings.defaultsSingleBandpassFilteredTimeSeries.tmax = Integer.parseInt(textfield2.getText());
	  	    				
	  	    				PlottingSettings.defaultsSingleBandpassFilteredTimeSeries.low = Integer.parseInt(textfield5.getText());
	  	    				PlottingSettings.defaultsSingleBandpassFilteredTimeSeries.high = Integer.parseInt(textfield6.getText());
	  	    				PlottingSettings.defaultsSingleBandpassFilteredTimeSeries.Nfix = Integer.parseInt(textfield7.getText());
	  	    				
	  	    				defaultSettingsDialog.dispose();
		  	    			defaultSettingsDialog.setVisible(false);
		  	    			
	  
	  	    				
	  	    			}
	  	    			catch(NumberFormatException nFE) {
	  	    				JOptionPane.showMessageDialog(null,"Not an integer!"); 
	  	    				
	  	    			
	  	    			}
	  	    			
	  	    		
		  	    		
	  	    			
		  	    		
	  	    			
	  	    			PlottingSettings.defaultsSingleBandpassFilteredTimeSeries.aggregation = combo1.getSelectedItem().toString();
	  	    			
	  	    			PlottingSettings.defaultsSingleBandpassFilteredTimeSeries.correlation = CBUpperBound2.isSelected();
	  	    			PlottingSettings.defaultsSingleBandpassFilteredTimeSeries.logarithmic = CBUpperBound3.isSelected();
	  	    			PlottingSettings.defaultsSingleBandpassFilteredTimeSeries.drift = CBUpperBound4.isSelected();
	  	
	  	    			
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
		
		
		/*Set the array for methods:*/
		
		
		
		/*Set top Panel*/
		
		JPanel topPanel = new JPanel();
		topPanel.setLayout( new BorderLayout() );
		getContentPane().add( topPanel );

	
		// Create a tabbed pane
		
		tabbedPane = new JTabbedPane();
		agentJPanelList = new ArrayList<AgentTab> ();
		
		for(int i=0; i<tempAgentInstanceVariable.size();i++){
			
			// Create the tab pages for each agent that is selected for plotting
			if(tempAgentInstanceVariable.get(i).singleBandpassFilteredTimeSeriesSelected)
			{
				agentJPanelList.add(new AgentTab(tempAgentInstanceVariable.get(i)));
				tabbedPane.addTab(tempAgentInstanceVariable.get(i).agentName ,agentJPanelList.get(agentJPanelList.size()-1));
			}
			
		}
		
		if(tempRatiosSelectedForSingleTimeSeries){
			
			ratioTab = new RatioTab(tempRatioInstanceVariable);
			
			tabbedPane.addTab("Ratio",ratioTab);
			
		}
		
		/*Check if certain menu items have to be disabled*/
		
		
		if(agentJPanelList.size()== tempAgentInstanceVariable.size()){
			addAgent.setEnabled(false);
		}else if(agentJPanelList.size()==0){
			
			removeAgent.setEnabled(false);
			menuSettings.setEnabled(false);
			menuVariables.setEnabled(false);
			
		}
		
		
		
		tabbedPane.addChangeListener( new ChangeListener() {
		      public void stateChanged(ChangeEvent changeEvent) {
		    	  
		    	  System.out.println("Changed tab to Tab"+tabbedPane.getSelectedIndex());
		    	 // agentJPanelList.get(tabbedPane.getSelectedIndex()).reDrawTable();
		    	  
		    	  /*Here we disable the add ratio/ add ratio menu items if required*/
		
		    	  if(tabbedPane.getComponentCount()==agentJPanelList.size()+1 && tabbedPane.getSelectedIndex()== agentJPanelList.size()){
		
		    			addRemoveVariables.setEnabled(false);
		    			addRemoveRatios.setEnabled(true);
		    		  
		    	  }else{
		    		  
		    		  addRemoveVariables.setEnabled(true);
		    		  addRemoveRatios.setEnabled(false);
		    		  
		    		  
		    	  }
		    	  
		        }
		      });
		
		topPanel.add( tabbedPane, BorderLayout.CENTER );
		
		setModal(true);
		setVisible(true);

	}
	
	

	
	
	
	class AgentTab extends  JPanel
	{
		PlottingSettings.Agent agent;
		GridBagConstraints agentPane = new GridBagConstraints();
		
		private TableModelAgent tabvariableModel;
		private JTable tabVariables;
		
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
			
			//Agent agent;
			JScrollPane listScrollVariables;  
		
			
			VariableTable(PlottingSettings.Agent agentAtTab){
				

				

				String[] colHeadersT2 = {"Variable Instance" , "Tmin", "Tmax", "Lower Limit", "", "Upper Limit","", "Correlation", "Log","Aggregation","Low", "High", "Nfix", "Drift" };
				
				tabvariableModel = new TableModelAgent(colHeadersT2, agentAtTab );
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
				tabVariables.getColumnModel().getColumn(0).setPreferredWidth(180);
				tabVariables.getColumnModel().getColumn(0).setCellEditor(new CellEditor());
				

				/*Column 2*/
				
				
				tabVariables.getColumnModel().getColumn(1).setPreferredWidth(60);
				tabVariables.getColumnModel().getColumn(1).setCellEditor(new CellEditor());
				
				
				
				tabVariables.getColumnModel().getColumn(1).getCellEditor().addCellEditorListener(new EditorListener(){
					 
					 
					 public void editingStopped(ChangeEvent e) {
						 
						 System.out.println("Col 2 editing stopped");
						 
						 int editedrow = ((CellEditor) tabVariables.getColumnModel().getColumn(1).getCellEditor()).getRow();
			    
						 for(int i=0; i<tempSingleBandpassFilteredTimeSeries.size();i++){
				    	    	
						    	
							 if(tempSingleBandpassFilteredTimeSeries.get(i).isVariableInstance){
								 
								 if(tempSingleBandpassFilteredTimeSeries.get(i).variableInstance.instanceName.equals(tabvariableModel.getValueAt(editedrow, 0)) && tempSingleBandpassFilteredTimeSeries.get(i).variableInstance.agentName.equals(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.agentName) )
								 {
									 tempSingleBandpassFilteredTimeSeries.get(i).tmin = Integer.parseInt(tabVariables.getColumnModel().getColumn(1).getCellEditor().getCellEditorValue().toString());
									 break;
									 
								 }
								 
								 
							 }
		  	  
						 }
			    	      
			    	      tabvariableModel.changeValueAt(tabVariables.getColumnModel().getColumn(1).getCellEditor().getCellEditorValue().toString(), editedrow, 1);
						 
					 }
					 
					 
				});
				
				/*Column 3*/
				
				tabVariables.getColumnModel().getColumn(2).setPreferredWidth(60);
				tabVariables.getColumnModel().getColumn(2).setCellEditor(new CellEditor());
				
				
				
				tabVariables.getColumnModel().getColumn(2).getCellEditor().addCellEditorListener(new EditorListener(){
					 
					 
					 public void editingStopped(ChangeEvent e) {
						 
						 System.out.println("Col 2 editing stopped");
						 
						 int editedrow = ((CellEditor) tabVariables.getColumnModel().getColumn(2).getCellEditor()).getRow();
			    
						 for(int i=0; i<tempSingleBandpassFilteredTimeSeries.size();i++){
				    	    	
						    	
							 if(tempSingleBandpassFilteredTimeSeries.get(i).isVariableInstance){
								 
								 if(tempSingleBandpassFilteredTimeSeries.get(i).variableInstance.instanceName.equals(tabvariableModel.getValueAt(editedrow, 0)) && tempSingleBandpassFilteredTimeSeries.get(i).variableInstance.agentName.equals(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.agentName) )
								 {
									 tempSingleBandpassFilteredTimeSeries.get(i).tmax = Integer.parseInt(tabVariables.getColumnModel().getColumn(2).getCellEditor().getCellEditorValue().toString());
									 break;
									 
								 }
								 
								 
							 }
		  	  
						 }
			    	      
			    	      tabvariableModel.changeValueAt(tabVariables.getColumnModel().getColumn(2).getCellEditor().getCellEditorValue().toString(), editedrow, 2);
						 
					 }
					 
					 
				});
				
				
				
				
				
				
				
				
				/*Column 4*/
				
				tabVariables.getColumnModel().getColumn(3).setPreferredWidth(140);
				tabVariables.getColumnModel().getColumn(3).setCellEditor(new CellEditor());
				
				
				
				tabVariables.getColumnModel().getColumn(3).getCellEditor().addCellEditorListener(new EditorListener(){
					 
					 
					 public void editingStopped(ChangeEvent e) {
						 
						 System.out.println("Col 2 editing stopped");
						 
						 int editedrow = ((CellEditor) tabVariables.getColumnModel().getColumn(3).getCellEditor()).getRow();
			    
						 for(int i=0; i<tempSingleBandpassFilteredTimeSeries.size();i++){
				    	    	
						    	
							 if(tempSingleBandpassFilteredTimeSeries.get(i).isVariableInstance){
								 
								 if(tempSingleBandpassFilteredTimeSeries.get(i).variableInstance.instanceName.equals(tabvariableModel.getValueAt(editedrow, 0)) && tempSingleBandpassFilteredTimeSeries.get(i).variableInstance.agentName.equals(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.agentName))
								 {
									 try{
									 tempSingleBandpassFilteredTimeSeries.get(i).lowerBound = Double.parseDouble(tabVariables.getColumnModel().getColumn(3).getCellEditor().getCellEditorValue().toString());
									 }catch(Exception ex){
										 
										 
										 JOptionPane.showMessageDialog(null,"Parameter must be a numeric expression!");
										 
									 }
									 
									 break;
									 
								 }
								 
								 
							 }
		  	  
						 }
			    	      
			    	      tabvariableModel.changeValueAt(tabVariables.getColumnModel().getColumn(3).getCellEditor().getCellEditorValue().toString(), editedrow, 3);
						 
					 }
					 
					 
				});
				
				
				/*Column 5 Check box*/
				

				 final JCheckBox check = new JCheckBox();
				 
				tabVariables.getColumnModel().getColumn(4).setPreferredWidth(40);
				
				tabVariables.getColumnModel().getColumn(4).setCellEditor(new JCheckBoxCellEditor(check));
				tabVariables.getColumnModel().getColumn(4).getCellEditor().getClass();
				tabVariables.getColumnModel().getColumn(4).getCellEditor().addCellEditorListener(new EditorListener(){
					 
					 
					 public void editingStopped(ChangeEvent e) {
						 
						 
						 int editedrow = ((JCheckBoxCellEditor) tabVariables.getColumnModel().getColumn(4).getCellEditor()).getRow();
						 
						 if( ((JCheckBoxCellEditor) tabVariables.getColumnModel().getColumn(4).getCellEditor()).getCellEditorValue().equals(true))
						 {
							 /*Set the check box*/
							 tabvariableModel.changeValueAt("true",editedrow,4 );

							 for(int i=0; i<tempSingleBandpassFilteredTimeSeries.size();i++){
					    	    	
							    	
								 if(tempSingleBandpassFilteredTimeSeries.get(i).isVariableInstance){
									 
									 if(tempSingleBandpassFilteredTimeSeries.get(i).variableInstance.instanceName.equals(tabvariableModel.getValueAt(editedrow, 0)) && tempSingleBandpassFilteredTimeSeries.get(i).variableInstance.agentName.equals(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.agentName))
									 {
										 tempSingleBandpassFilteredTimeSeries.get(i).lowerBoundEnabled = true;
										 break;
										 
									 }
									 
									 
								 }
			  	  
							 }
							 
							 tabvariableModel.updatetable();
					
							 
							 
						 }else{
							 /*Set the check box*/
							 tabvariableModel.changeValueAt("false",((JCheckBoxCellEditor) tabVariables.getColumnModel().getColumn(4).getCellEditor()).getRow(),4 );

							 for(int i=0; i<tempSingleBandpassFilteredTimeSeries.size();i++){
					    	    	
							    	
								 if(tempSingleBandpassFilteredTimeSeries.get(i).isVariableInstance){
									 
									 if(tempSingleBandpassFilteredTimeSeries.get(i).variableInstance.instanceName.equals(tabvariableModel.getValueAt(editedrow, 0)) && tempSingleBandpassFilteredTimeSeries.get(i).variableInstance.agentName.equals(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.agentName))
									 {
										 tempSingleBandpassFilteredTimeSeries.get(i).lowerBoundEnabled = false;
										 break;
										 
									 }
									 
									 
								 }
			  	  
							 }
							 
							 tabvariableModel.updatetable();
							
						 }
						 
					    }
					 
					 
					 
				 });
				 
				 
				tabVariables.getColumnModel().getColumn(4).setCellRenderer(new DefaultTableCellRenderer() {
					 public Component getTableCellRendererComponent(JTable table,
					 Object value, boolean isSelected, boolean hasFocus, int row, int
					 column) {
						 
						 System.out.println(value);
					 check.setSelected(((Boolean)value).booleanValue()) ;
					 return check;
					 }
					 
					 
					 
					 });
				 
				
				
				
				
		/*Column 6*/
				
				tabVariables.getColumnModel().getColumn(5).setPreferredWidth(140);
				tabVariables.getColumnModel().getColumn(5).setCellEditor(new CellEditor());
				
				
				
				tabVariables.getColumnModel().getColumn(5).getCellEditor().addCellEditorListener(new EditorListener(){
					 
					 
					 public void editingStopped(ChangeEvent e) {
						 
						 System.out.println("Col 2 editing stopped");
						 
						 int editedrow = ((CellEditor) tabVariables.getColumnModel().getColumn(5).getCellEditor()).getRow();
			    
						 for(int i=0; i<tempSingleBandpassFilteredTimeSeries.size();i++){
				    	    	
						    	
							 if(tempSingleBandpassFilteredTimeSeries.get(i).isVariableInstance){
								 
								 if(tempSingleBandpassFilteredTimeSeries.get(i).variableInstance.instanceName.equals(tabvariableModel.getValueAt(editedrow, 0)) && tempSingleBandpassFilteredTimeSeries.get(i).variableInstance.agentName.equals(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.agentName))
								 {
									 try{
									 tempSingleBandpassFilteredTimeSeries.get(i).upperBound = Double.parseDouble(tabVariables.getColumnModel().getColumn(5).getCellEditor().getCellEditorValue().toString());
									 }catch(Exception ex){
										 
										 JOptionPane.showMessageDialog(null,"Parameter must be a numeric expression!");
										 
									 }
									 
									 break;
									 
								 }
								 
								 
							 }
		  	  
						 }
			    	      
			    	      tabvariableModel.changeValueAt(tabVariables.getColumnModel().getColumn(5).getCellEditor().getCellEditorValue().toString(), editedrow, 5);
						 
					 }
					 
					 
				});
				
				
				/*Column 7 Check box*/
				

				 final JCheckBox check2 = new JCheckBox();
				 
				tabVariables.getColumnModel().getColumn(6).setPreferredWidth(40);
				
				tabVariables.getColumnModel().getColumn(6).setCellEditor(new JCheckBoxCellEditor(check2));
				tabVariables.getColumnModel().getColumn(6).getCellEditor().getClass();
				tabVariables.getColumnModel().getColumn(6).getCellEditor().addCellEditorListener(new EditorListener(){
					 
					 
					 public void editingStopped(ChangeEvent e) {
						 
						 
						 int editedrow = ((JCheckBoxCellEditor) tabVariables.getColumnModel().getColumn(6).getCellEditor()).getRow();
						 
						 if( ((JCheckBoxCellEditor) tabVariables.getColumnModel().getColumn(6).getCellEditor()).getCellEditorValue().equals(true))
						 {
							 /*Set the check box*/
							 tabvariableModel.changeValueAt("true",editedrow,6 );

							 for(int i=0; i<tempSingleBandpassFilteredTimeSeries.size();i++){
					    	    	
							    	
								 if(tempSingleBandpassFilteredTimeSeries.get(i).isVariableInstance){
									 
									 if(tempSingleBandpassFilteredTimeSeries.get(i).variableInstance.instanceName.equals(tabvariableModel.getValueAt(editedrow, 0)) && tempSingleBandpassFilteredTimeSeries.get(i).variableInstance.agentName.equals(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.agentName))
									 {
										 tempSingleBandpassFilteredTimeSeries.get(i).upperBoundEnabled = true;
										 break;
										 
									 }
									 
									 
								 }
			  	  
							 }
							 
							 tabvariableModel.updatetable();
					
							 
							 
						 }else{
							 /*Set the check box*/
							 tabvariableModel.changeValueAt("false",((JCheckBoxCellEditor) tabVariables.getColumnModel().getColumn(6).getCellEditor()).getRow(),6 );

							 for(int i=0; i<tempSingleBandpassFilteredTimeSeries.size();i++){
					    	    	
							    	
								 if(tempSingleBandpassFilteredTimeSeries.get(i).isVariableInstance){
									 
									 if(tempSingleBandpassFilteredTimeSeries.get(i).variableInstance.instanceName.equals(tabvariableModel.getValueAt(editedrow, 0)) && tempSingleBandpassFilteredTimeSeries.get(i).variableInstance.agentName.equals(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.agentName))
									 {
										 tempSingleBandpassFilteredTimeSeries.get(i).upperBoundEnabled = false;
										 break;
										 
									 }
									 
									 
								 }
			  	  
							 }
							 
							 tabvariableModel.updatetable();
							
						 }
						 
					    }

					 
				 });
				 
				 
				tabVariables.getColumnModel().getColumn(6).setCellRenderer(new DefaultTableCellRenderer() {
					 public Component getTableCellRendererComponent(JTable table,
					 Object value, boolean isSelected, boolean hasFocus, int row, int
					 column) {
						 
						 System.out.println(value);
					 check2.setSelected(((Boolean)value).booleanValue()) ;
					 return check2;
					 }
					 
					 
					 
					 });
				
				
				 final JCheckBox check3 = new JCheckBox();
				 
					tabVariables.getColumnModel().getColumn(7).setPreferredWidth(40);
					
					tabVariables.getColumnModel().getColumn(7).setCellEditor(new JCheckBoxCellEditor(check3));
					tabVariables.getColumnModel().getColumn(7).getCellEditor().getClass();
					tabVariables.getColumnModel().getColumn(7).getCellEditor().addCellEditorListener(new EditorListener(){
						 
						 
						 public void editingStopped(ChangeEvent e) {
							 
							 
							 int editedrow = ((JCheckBoxCellEditor) tabVariables.getColumnModel().getColumn(7).getCellEditor()).getRow();
							 
							 if( ((JCheckBoxCellEditor) tabVariables.getColumnModel().getColumn(7).getCellEditor()).getCellEditorValue().equals(true))
							 {
								 /*Set the check box*/
								 tabvariableModel.changeValueAt("true",editedrow,7 );

								 for(int i=0; i<tempSingleBandpassFilteredTimeSeries.size();i++){
						    	    	
								    	
									 if(tempSingleBandpassFilteredTimeSeries.get(i).isVariableInstance){
										 
										 if(tempSingleBandpassFilteredTimeSeries.get(i).variableInstance.instanceName.equals(tabvariableModel.getValueAt(editedrow, 0)) && tempSingleBandpassFilteredTimeSeries.get(i).variableInstance.agentName.equals(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.agentName))
										 {
											 tempSingleBandpassFilteredTimeSeries.get(i).correlation = true;
											 break;
											 
										 }
										 
										 
									 }
				  	  
								 }
								 
								 tabvariableModel.updatetable();
						
								 
								 
							 }else{
								 /*Set the check box*/
								 tabvariableModel.changeValueAt("false",((JCheckBoxCellEditor) tabVariables.getColumnModel().getColumn(7).getCellEditor()).getRow(),7 );

								 for(int i=0; i<tempSingleBandpassFilteredTimeSeries.size();i++){
						    	    	
								    	
									 if(tempSingleBandpassFilteredTimeSeries.get(i).isVariableInstance){
										 
										 if(tempSingleBandpassFilteredTimeSeries.get(i).variableInstance.instanceName.equals(tabvariableModel.getValueAt(editedrow, 0)) && tempSingleBandpassFilteredTimeSeries.get(i).variableInstance.agentName.equals(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.agentName))
										 {
											 tempSingleBandpassFilteredTimeSeries.get(i).correlation = false;
											 break;
											 
										 }
										 
										 
									 }
				  	  
								 }
								 
								 tabvariableModel.updatetable();
								
							 }
							 
						    }
						 
						 
						 
					 });
					 
					 
					tabVariables.getColumnModel().getColumn(7).setCellRenderer(new DefaultTableCellRenderer() {
						 public Component getTableCellRendererComponent(JTable table,
						 Object value, boolean isSelected, boolean hasFocus, int row, int
						 column) {
							 
							 System.out.println(value);
						 check3.setSelected(((Boolean)value).booleanValue()) ;
						 return check3;
						 }
						 
						 
						 
						 });
					
					
					
					
					 final JCheckBox check4 = new JCheckBox();
					 
						tabVariables.getColumnModel().getColumn(8).setPreferredWidth(40);
						
						tabVariables.getColumnModel().getColumn(8).setCellEditor(new JCheckBoxCellEditor(check4));
						tabVariables.getColumnModel().getColumn(8).getCellEditor().getClass();
						tabVariables.getColumnModel().getColumn(8).getCellEditor().addCellEditorListener(new EditorListener(){
							 
							 
							 public void editingStopped(ChangeEvent e) {
								 
								 
								 int editedrow = ((JCheckBoxCellEditor) tabVariables.getColumnModel().getColumn(8).getCellEditor()).getRow();
								 
								 if( ((JCheckBoxCellEditor) tabVariables.getColumnModel().getColumn(8).getCellEditor()).getCellEditorValue().equals(true))
								 {
									 /*Set the check box*/
									 tabvariableModel.changeValueAt("true",editedrow,8 );

									 for(int i=0; i<tempSingleBandpassFilteredTimeSeries.size();i++){
							    	    	
									    	
										 if(tempSingleBandpassFilteredTimeSeries.get(i).isVariableInstance){
											 
											 if(tempSingleBandpassFilteredTimeSeries.get(i).variableInstance.instanceName.equals(tabvariableModel.getValueAt(editedrow, 0)) && tempSingleBandpassFilteredTimeSeries.get(i).variableInstance.agentName.equals(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.agentName))
											 {
												 tempSingleBandpassFilteredTimeSeries.get(i).logarithmic = true;
												 break;
												 
											 }
											 
											 
										 }
					  	  
									 }
									 
									 tabvariableModel.updatetable();
							
									 
									 
								 }else{
									 /*Set the check box*/
									 tabvariableModel.changeValueAt("false",((JCheckBoxCellEditor) tabVariables.getColumnModel().getColumn(8).getCellEditor()).getRow(),8 );

									 for(int i=0; i<tempSingleBandpassFilteredTimeSeries.size();i++){
							    	    	
									    	
										 if(tempSingleBandpassFilteredTimeSeries.get(i).isVariableInstance){
											 
											 if(tempSingleBandpassFilteredTimeSeries.get(i).variableInstance.instanceName.equals(tabvariableModel.getValueAt(editedrow, 0)) && tempSingleBandpassFilteredTimeSeries.get(i).variableInstance.agentName.equals(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.agentName))
											 {
												 tempSingleBandpassFilteredTimeSeries.get(i).logarithmic = false;
												 break;
												 
											 }
											 
											 
										 }
					  	  
									 }
									 
									 tabvariableModel.updatetable();
									
								 }
								 
							    }
							 
							 
							 
						 });
						 
						 
						tabVariables.getColumnModel().getColumn(8).setCellRenderer(new DefaultTableCellRenderer() {
							 public Component getTableCellRendererComponent(JTable table,
							 Object value, boolean isSelected, boolean hasFocus, int row, int
							 column) {
								 
								 System.out.println(value);
							 check4.setSelected(((Boolean)value).booleanValue()) ;
							 return check4;
							 }
							 
							 
							 
							 });
						
						
						
						
						
						
						
				
						String [] selChoice2 = {"No", "Sum", "Mean"};

						tabVariables.getColumnModel().getColumn(9).setCellEditor(new SpecificComboBoxEditor(selChoice2));
						tabVariables.getColumnModel().getColumn(9).setCellRenderer(new SpecificComboBoxRenderer(selChoice2));
						
						
						
						tabVariables.getColumnModel().getColumn(9).getCellEditor().addCellEditorListener(new EditorListener(){
							 
							 
							 public void editingStopped(ChangeEvent e) {
								 
								 System.out.println("Editing ended!");
								 
							 }
							 
							 
						});
						
						
						tabVariables.getColumnModel().getColumn(10).setPreferredWidth(140);
						tabVariables.getColumnModel().getColumn(10).setCellEditor(new CellEditor());
						
						
						
						tabVariables.getColumnModel().getColumn(10).getCellEditor().addCellEditorListener(new EditorListener(){
							 
							 
							 public void editingStopped(ChangeEvent e) {
								 
								 System.out.println("Col 10 editing stopped");
								 
								 int editedrow = ((CellEditor) tabVariables.getColumnModel().getColumn(10).getCellEditor()).getRow();
					    
								 for(int i=0; i<tempSingleBandpassFilteredTimeSeries.size();i++){
						    	    	
								    	
									 if(tempSingleBandpassFilteredTimeSeries.get(i).isVariableInstance){
										 
										 if(tempSingleBandpassFilteredTimeSeries.get(i).variableInstance.instanceName.equals(tabvariableModel.getValueAt(editedrow, 0)) && tempSingleBandpassFilteredTimeSeries.get(i).variableInstance.agentName.equals(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.agentName) )
										 {
											 try{
											 tempSingleBandpassFilteredTimeSeries.get(i).low = Integer.parseInt(tabVariables.getColumnModel().getColumn(10).getCellEditor().getCellEditorValue().toString());
											 break;
											 }catch(Exception exp){
												 
												 JOptionPane.showMessageDialog(null,"Parameter must be an integer!");
												 
											 }
											 
										 }
										 
										 
									 }
				  	  
								 }
					    	      
					    	      tabvariableModel.changeValueAt(tabVariables.getColumnModel().getColumn(10).getCellEditor().getCellEditorValue().toString(), editedrow, 10);
								 
							 }
							 
							 
						});
						
						
						
						tabVariables.getColumnModel().getColumn(11).setPreferredWidth(140);
						tabVariables.getColumnModel().getColumn(11).setCellEditor(new CellEditor());
						
						
						
						tabVariables.getColumnModel().getColumn(11).getCellEditor().addCellEditorListener(new EditorListener(){
							 
							 
							 public void editingStopped(ChangeEvent e) {
								 
								 System.out.println("Col 11 editing stopped");
								 
								 int editedrow = ((CellEditor) tabVariables.getColumnModel().getColumn(11).getCellEditor()).getRow();
					    
								 for(int i=0; i<tempSingleBandpassFilteredTimeSeries.size();i++){
						    	    	
								    	
									 if(tempSingleBandpassFilteredTimeSeries.get(i).isVariableInstance){
										 
										 if(tempSingleBandpassFilteredTimeSeries.get(i).variableInstance.instanceName.equals(tabvariableModel.getValueAt(editedrow, 0)) && tempSingleBandpassFilteredTimeSeries.get(i).variableInstance.agentName.equals(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.agentName) )
										 {
											 try{
											 tempSingleBandpassFilteredTimeSeries.get(i).high = Integer.parseInt(tabVariables.getColumnModel().getColumn(11).getCellEditor().getCellEditorValue().toString());
											 break;
											 }catch(Exception exp){
												 
												 JOptionPane.showMessageDialog(null,"Parameter must be an integer!");
												 
											 }
											 
										 }
										 
										 
									 }
				  	  
								 }
					    	      
					    	      tabvariableModel.changeValueAt(tabVariables.getColumnModel().getColumn(11).getCellEditor().getCellEditorValue().toString(), editedrow, 11);
								 
							 }
							 
							 
						});
						
						
						
						tabVariables.getColumnModel().getColumn(12).setPreferredWidth(140);
						tabVariables.getColumnModel().getColumn(12).setCellEditor(new CellEditor());
						
						
						
						tabVariables.getColumnModel().getColumn(12).getCellEditor().addCellEditorListener(new EditorListener(){
							 
							 
							 public void editingStopped(ChangeEvent e) {
								 
								 System.out.println("Col 11 editing stopped");
								 
								 int editedrow = ((CellEditor) tabVariables.getColumnModel().getColumn(12).getCellEditor()).getRow();
					    
								 for(int i=0; i<tempSingleBandpassFilteredTimeSeries.size();i++){
						    	    	
								    	
									 if(tempSingleBandpassFilteredTimeSeries.get(i).isVariableInstance){
										 
										 if(tempSingleBandpassFilteredTimeSeries.get(i).variableInstance.instanceName.equals(tabvariableModel.getValueAt(editedrow, 0)) && tempSingleBandpassFilteredTimeSeries.get(i).variableInstance.agentName.equals(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.agentName) )
										 {
											 try{
											 tempSingleBandpassFilteredTimeSeries.get(i).Nfix = Integer.parseInt(tabVariables.getColumnModel().getColumn(12).getCellEditor().getCellEditorValue().toString());
											 break;
											 }catch(Exception exp){
												 
												 JOptionPane.showMessageDialog(null,"Parameter must be an integer!");
												 
											 }
											 
										 }
										 
										 
									 }
				  	  
								 }
					    	      
					    	      tabvariableModel.changeValueAt(tabVariables.getColumnModel().getColumn(12).getCellEditor().getCellEditorValue().toString(), editedrow, 12);
								 
							 }
							 
							 
						});
						
						
						
						
						 final JCheckBox check5 = new JCheckBox();
						 
							tabVariables.getColumnModel().getColumn(13).setPreferredWidth(40);
							
							tabVariables.getColumnModel().getColumn(13).setCellEditor(new JCheckBoxCellEditor(check5));
							tabVariables.getColumnModel().getColumn(13).getCellEditor().getClass();
							tabVariables.getColumnModel().getColumn(13).getCellEditor().addCellEditorListener(new EditorListener(){
								 
								 
								 public void editingStopped(ChangeEvent e) {
									 
									 
									 int editedrow = ((JCheckBoxCellEditor) tabVariables.getColumnModel().getColumn(13).getCellEditor()).getRow();
									 
									 if( ((JCheckBoxCellEditor) tabVariables.getColumnModel().getColumn(13).getCellEditor()).getCellEditorValue().equals(true))
									 {
										 /*Set the check box*/
										 tabvariableModel.changeValueAt("true",editedrow,13 );

										 for(int i=0; i<tempSingleBandpassFilteredTimeSeries.size();i++){
								    	    	
										    	
											 if(tempSingleBandpassFilteredTimeSeries.get(i).isVariableInstance){
												 
												 if(tempSingleBandpassFilteredTimeSeries.get(i).variableInstance.instanceName.equals(tabvariableModel.getValueAt(editedrow, 0)) && tempSingleBandpassFilteredTimeSeries.get(i).variableInstance.agentName.equals(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.agentName))
												 {
													 tempSingleBandpassFilteredTimeSeries.get(i).drift = true;
													 break;
													 
												 }
												 
												 
											 }
						  	  
										 }
										 
										 tabvariableModel.updatetable();
								
										 
										 
									 }else{
										 /*Set the check box*/
										 tabvariableModel.changeValueAt("false",((JCheckBoxCellEditor) tabVariables.getColumnModel().getColumn(13).getCellEditor()).getRow(),13 );

										 for(int i=0; i<tempSingleBandpassFilteredTimeSeries.size();i++){
								    	    	
										    	
											 if(tempSingleBandpassFilteredTimeSeries.get(i).isVariableInstance){
												 
												 if(tempSingleBandpassFilteredTimeSeries.get(i).variableInstance.instanceName.equals(tabvariableModel.getValueAt(editedrow, 0)) && tempSingleBandpassFilteredTimeSeries.get(i).variableInstance.agentName.equals(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.agentName))
												 {
													 tempSingleBandpassFilteredTimeSeries.get(i).drift = false;
													 break;
													 
												 }
												 
												 
											 }
						  	  
										 }
										 
										 tabvariableModel.updatetable();
										
									 }
									 
								    }
								 
								 
								 
							 });
							 
							 
							tabVariables.getColumnModel().getColumn(13).setCellRenderer(new DefaultTableCellRenderer() {
								 public Component getTableCellRendererComponent(JTable table,
								 Object value, boolean isSelected, boolean hasFocus, int row, int
								 column) {
									 
									 System.out.println(value);
								 check5.setSelected(((Boolean)value).booleanValue()) ;
								 return check5;
								 }
								 
								 
								 
								 });
				

				
				tabVariables.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				
				tabVariables.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
				
				System.out.println("xx "+tabVariables.getColumnCount());
				
				
				
				tabVariables.addMouseListener(new MouseAdapter() {
					public void mouseClicked(final MouseEvent e) {
						
						tabVariables.setRowSelectionAllowed(true);
						
						System.out.println("xxtabVariables.getSelectedRow() "+tabVariables.getSelectedRow());
						System.out.println("xxtabbedPane.getSelectedIndex()"+tabVariables.getRowCount());
						
						
						System.out.println("xxtabVariables.getSelectedRow() "+agent.agentName);
				
						
						
		         }
				});
				System.out.println("draw table");
				tabvariableModel.updatetable();
				
				listScrollVariables = new JScrollPane(tabVariables);
				
				
			}
			

		}
		
		private class SpecificComboBoxEditor extends DefaultCellEditor {
			
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
		        

		        System.out.println("editedCol "+editedCol);
		        
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
						 
						 
		    	    	 for(int i=0; i<tempSingleBandpassFilteredTimeSeries.size();i++){
				    	    	
						    	
							 if(tempSingleBandpassFilteredTimeSeries.get(i).isVariableInstance){
								 
								 if(tempSingleBandpassFilteredTimeSeries.get(i).variableInstance.instanceName.equals(tabvariableModel.getValueAt(editedRow, 0)) && tempSingleBandpassFilteredTimeSeries.get(i).variableInstance.agentName.equals(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.agentName))
								 {
										
									 if(editedCol==9){
											
										 tempSingleBandpassFilteredTimeSeries.get(i).aggregation = cComboBox.getSelectedItem().toString();
										 
										 System.out.println("tempSingleBandpassFilteredTimeSeries.get(i).aggregation "+tempSingleBandpassFilteredTimeSeries.get(i).aggregation);
										 
									 break;
									 }
									 
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
		
		
		private class SpecificComboBoxRenderer extends JComboBox implements TableCellRenderer {
			
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
	
	
	
	

public class TableModelAgent extends AbstractTableModel{
	
	JCheckBox checkBox = new JCheckBox();
	String [] columnName;
	

	ArrayList <VariableInstanceTimeSeriesPlotting> plottedInstances = new ArrayList<VariableInstanceTimeSeriesPlotting>();
	
	PlottingSettings.Agent agent;
	
	int numFilters;
	boolean partitioningSelected , filterSelected;
	
	
	TableModelAgent(String[] colName, PlottingSettings.Agent ag){
		
		agent = ag;

		for(int i=0; i<tempSingleBandpassFilteredTimeSeries.size();i++){
			
				if(tempSingleBandpassFilteredTimeSeries.get(i).isVariableInstance){
				
					if(tempSingleBandpassFilteredTimeSeries.get(i).variableInstance.agentName.equals(agent.agentName)){
						
						plottedInstances.add(new VariableInstanceTimeSeriesPlotting(tempSingleBandpassFilteredTimeSeries.get(i).variableInstance.instanceName,
								tempSingleBandpassFilteredTimeSeries.get(i).tmin,tempSingleBandpassFilteredTimeSeries.get(i).tmax,
								tempSingleBandpassFilteredTimeSeries.get(i).lowerBound,tempSingleBandpassFilteredTimeSeries.get(i).upperBound,
								tempSingleBandpassFilteredTimeSeries.get(i).lowerBoundEnabled,tempSingleBandpassFilteredTimeSeries.get(i).upperBoundEnabled,tempSingleBandpassFilteredTimeSeries.get(i).correlation, tempSingleBandpassFilteredTimeSeries.get(i).logarithmic,
								 tempSingleBandpassFilteredTimeSeries.get(i).aggregation,
									tempSingleBandpassFilteredTimeSeries.get(i).low,tempSingleBandpassFilteredTimeSeries.get(i).high,
									 tempSingleBandpassFilteredTimeSeries.get(i).Nfix,tempSingleBandpassFilteredTimeSeries.get(i).drift));
					}
					
					
				}
		}
		
				columnName = colName;
	
		
	}
	
	
	
	public int getRowCount()
   {
		
			return plottedInstances.size();
			
			}
			
 
	
	
	   public int getColumnCount()
	   {
	   return 14;
	 }
	  
	   
	   public Object getValueAt( int row, int col)
	   {
		   
		   if(col==0){
			   return plottedInstances.get(row).instanceName;
		   }else if(col==1){
			   
			   return plottedInstances.get(row).tmin;
		   }else if(col==2){
			   return plottedInstances.get(row).tmax;
			   
		   }else if(col==3){
			   
				if( plottedInstances.get(row).lowerBoundEnabled)
					 return Double.toString(plottedInstances.get(row).lowerBound);
		   		else
		   			return "";
		   }else if(col==4){
			   return plottedInstances.get(row).lowerBoundEnabled;
		   }else if(col==5){
			   if( plottedInstances.get(row).upperBoundEnabled)
					 return Double.toString(plottedInstances.get(row).upperBound);
		   		else
		   			return "";
		   }else if(col==6){
			   return plottedInstances.get(row).upperBoundEnabled;
		  }else if(col==7){
			   return plottedInstances.get(row).correlation;
		  }else if(col==8){
			   return plottedInstances.get(row).logarithmic;
		  }else if(col==9){
			   return plottedInstances.get(row).aggregation;
		  }else if(col==10){
			   return plottedInstances.get(row).low;
		   }else if(col==11){
			   return plottedInstances.get(row).high;
		   }else if(col==12){
			   return plottedInstances.get(row).Nfix;
		   }else if(col==13){
			   return plottedInstances.get(row).drift;
		   }else{
			   return null;
		   }
			   
		   
		   
		   
	   }
	   
	   public String getColumnName(int column) {  
	       return columnName[column]; 
	   }
	   
	   public Class<?> getColumnClass(int col) {  
		     if (col == 4 || col==6 ||col==7 ||col==8 ||col==13) {  
		        return Boolean.class;  
		     }  
		     return getValueAt(0, col).getClass();  
		  }  
	   
	   
	   public void changeValueAt(String value, int row, int col) {  

		   if(col==1)
			   plottedInstances.get(row).tmin=Integer.parseInt(value);
		   if(col==2)
			   plottedInstances.get(row).tmax=Integer.parseInt(value);
		   else if(col==3){
			   plottedInstances.get(row).lowerBound =   Double.parseDouble(value);;
		   }else if(col==4){
			   if(value.equals("true"))
				   plottedInstances.get(row).lowerBoundEnabled = true;
			   else
				   plottedInstances.get(row).lowerBoundEnabled = false;
		   }else if(col==5){
			   plottedInstances.get(row).upperBound =   Double.parseDouble(value);;
		   }else if(col==6){
			   if(value.equals("true"))
				   plottedInstances.get(row).upperBoundEnabled = true;
			   else
				   plottedInstances.get(row).upperBoundEnabled = false;
		   }
			   
			   else if(col==7){
				   if(value.equals("true"))
					   plottedInstances.get(row).correlation = true;
				   else
					   plottedInstances.get(row).correlation = false;
			  }else if(col==8){
				  if(value.equals("true"))
					  plottedInstances.get(row).logarithmic= true;
				  else
					  plottedInstances.get(row).logarithmic = false;
			  }else if(col==9){
				  plottedInstances.get(row).aggregation = value;
			  }else if(col==10){
				  plottedInstances.get(row).low=Integer.parseInt(value);;
			   }else if(col==11){
				   plottedInstances.get(row).high=Integer.parseInt(value);;
			   }else if(col==12){
				   plottedInstances.get(row).Nfix=Integer.parseInt(value);;
			   }else if(col==13){
				   if(value.equals("true"))
					   plottedInstances.get(row).drift=true;
				   else
					   plottedInstances.get(row).drift=false;
			   }
			   
		   
		   
	       this.fireTableDataChanged();  
	         
	   }
   
   public void updatetable() {  
       
       this.fireTableDataChanged();    
         
   }  
   
   public boolean isCellEditable(int row, int column){ 
      
	   if(column==0){
   		return false;
   	}else  if(column==1){
   		
   		return true;
   	}else if(column==2){
   		
   		return true;
   	}else if(column==3){
   		if( plottedInstances.get(row).lowerBoundEnabled)
   			return true;
   		else
   			return false;
   	}else if(column==4){
   		
   		return true;
   	}else if(column==5){
   		if( plottedInstances.get(row).upperBoundEnabled)
   			return true;
   		else
   			return false;
   	}else if(column==6){
   		
   		return true;
   		
   	}else{
   		
   		return true;
   	}
   }
   
   private class VariableInstanceTimeSeriesPlotting{
	   
	   
	   String instanceName;
	   int tmin;
	   int tmax;
	   
	 
		double lowerBound;
		double upperBound;
		boolean lowerBoundEnabled;
		boolean upperBoundEnabled;boolean correlation;
		boolean logarithmic;
		String aggregation;
		int low;
		int high;
		int Nfix;
		boolean drift;
	   
		VariableInstanceTimeSeriesPlotting(String name, int tmi, int tma, double loBo, double upBo, boolean loBoEnabled, boolean upBoEnabled, boolean cor, boolean log, String agg, int lo, int hi, int nf, boolean dr ){
		   
		   instanceName = name;
		   tmin = tmi;
		   tmax = tma;
		   lowerBound = loBo;
		   upperBound = upBo;
			lowerBoundEnabled = loBoEnabled;
			upperBoundEnabled = upBoEnabled;
			correlation = cor;
			logarithmic = log;
			aggregation = agg;
			low = lo;
			high = hi;
			Nfix = nf;
			drift= dr;
		   
	   
	   
   }
   
   

    
}



}








class RatioTab extends  JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	ArrayList<PlottingSettings.RatioInstance> ratio;
	GridBagConstraints agentPane = new GridBagConstraints();
	
	private TableModelRatio tabvariableModel;
	private JTable tabVariables;
	
	private JScrollPane scrollVariableTable;
	
	RatioTab (ArrayList<PlottingSettings.RatioInstance> ratioAtTab){
		
		ratio = ratioAtTab;


		setSize(700, 200);
		setLayout(new GridBagLayout());
		
		
		agentPane.insets = new Insets( 5, 5, 5, 5);
		
		JLabel lab = new JLabel("Plotting Settings for Ratios");
		agentPane.gridx =0;agentPane.gridy =0;
		add(lab,agentPane);
		
		VariableTable variableTable = new  VariableTable(ratio);
			
		scrollVariableTable = variableTable.listScrollVariables;
		scrollVariableTable.setPreferredSize(new Dimension(700, 179)); 
		agentPane.gridx =0;agentPane.gridy =1;
		add(scrollVariableTable,agentPane);
		
		JPanel lowerPanel = new JPanel();
		
		lowerPanel.setLayout(new GridBagLayout());
		
		GridBagConstraints lowerPa = new GridBagConstraints();
		lowerPa.insets = new Insets( 5, 5, 5, 5);
		

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
		
	
		System.out.println("BP1");
		
		VariableTable variableTable = new  VariableTable(ratio);
		
		scrollVariableTable = variableTable.listScrollVariables;
		scrollVariableTable.setPreferredSize(new Dimension(700, 179)); 
		agentPane.gridx=0; 
		agentPane.gridy=1;
		
		System.out.println("BP2");
		
		add(scrollVariableTable,agentPane);

		validate();
		System.out.println("BP3");
		
	}

	private class VariableTable extends JScrollPane{
		
		//Agent agent;
		JScrollPane listScrollVariables;  
		
		ArrayList<PlottingSettings.RatioInstance> ratios ;
		
		VariableTable(ArrayList<PlottingSettings.RatioInstance> tempRratio){
			
			ratios = tempRratio;
			
			
			
			
			String[] colHeadersT2 = {"Variable Instance" , "Tmin", "Tmax", "Lower Limit", "", "Upper Limit","", "Correlation", "Log","Aggregation","Low", "High", "Nfix", "Drift" };
			
			tabvariableModel = new TableModelRatio(colHeadersT2, tempRratio );
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
		    
					 for(int i=0; i<tempSingleBandpassFilteredTimeSeries.size();i++){
			    	    	
					    	
						 if(!tempSingleBandpassFilteredTimeSeries.get(i).isVariableInstance){
							 
							 if(tempSingleBandpassFilteredTimeSeries.get(i).ratioInstance.ratioInstanceName.equals(tabvariableModel.getValueAt(editedrow, 0)) )
							 {
								 tempSingleBandpassFilteredTimeSeries.get(i).tmin = Integer.parseInt(tabVariables.getColumnModel().getColumn(1).getCellEditor().getCellEditorValue().toString());
								 break;
								 
							 }
							 
							 
						 }
	  	  
					 }
		    	      
		    	      tabvariableModel.changeValueAt(tabVariables.getColumnModel().getColumn(1).getCellEditor().getCellEditorValue().toString(), editedrow, 1);
					 
				 }
				 
				 
			});
			
			/*Column 3*/
			
			tabVariables.getColumnModel().getColumn(2).setPreferredWidth(140);
			tabVariables.getColumnModel().getColumn(2).setCellEditor(new CellEditor());
			
			
			
			tabVariables.getColumnModel().getColumn(2).getCellEditor().addCellEditorListener(new EditorListener(){
				 
				 
				 public void editingStopped(ChangeEvent e) {
					 
					 System.out.println("Col 2 editing stopped");
					 
					 int editedrow = ((CellEditor) tabVariables.getColumnModel().getColumn(2).getCellEditor()).getRow();
		    
					 for(int i=0; i<tempSingleBandpassFilteredTimeSeries.size();i++){
			    	    	
					    	
						 if(!tempSingleBandpassFilteredTimeSeries.get(i).isVariableInstance){
							 
							 if(tempSingleBandpassFilteredTimeSeries.get(i).ratioInstance.ratioInstanceName.equals(tabvariableModel.getValueAt(editedrow, 0)) )
							 {
								 tempSingleBandpassFilteredTimeSeries.get(i).tmax = Integer.parseInt(tabVariables.getColumnModel().getColumn(2).getCellEditor().getCellEditorValue().toString());
								 break;
								 
							 }
							 
							 
						 }
	  	  
					 }
		    	      
		    	      tabvariableModel.changeValueAt(tabVariables.getColumnModel().getColumn(2).getCellEditor().getCellEditorValue().toString(), editedrow, 2);
					 
				 }
				 
				 
			});
			
			
			
			
			
			
			
			
			/*Column 4*/
			
			tabVariables.getColumnModel().getColumn(3).setPreferredWidth(140);
			tabVariables.getColumnModel().getColumn(3).setCellEditor(new CellEditor());
			
			
			
			tabVariables.getColumnModel().getColumn(3).getCellEditor().addCellEditorListener(new EditorListener(){
				 
				 
				 public void editingStopped(ChangeEvent e) {
					 
					 System.out.println("Col 2 editing stopped");
					 
					 int editedrow = ((CellEditor) tabVariables.getColumnModel().getColumn(3).getCellEditor()).getRow();
		    
					 for(int i=0; i<tempSingleBandpassFilteredTimeSeries.size();i++){
			    	    	
					    	
						 if(!tempSingleBandpassFilteredTimeSeries.get(i).isVariableInstance){
							 
							 if(tempSingleBandpassFilteredTimeSeries.get(i).ratioInstance.ratioInstanceName.equals(tabvariableModel.getValueAt(editedrow, 0)))
							 {
								 try{
								 tempSingleBandpassFilteredTimeSeries.get(i).lowerBound = Double.parseDouble(tabVariables.getColumnModel().getColumn(3).getCellEditor().getCellEditorValue().toString());
								 }catch(Exception ex){
									 
									 
									 JOptionPane.showMessageDialog(null,"Parameter must be a numeric expression!");
									 
								 }
								 
								 break;
								 
							 }
							 
							 
						 }
	  	  
					 }
		    	      
		    	      tabvariableModel.changeValueAt(tabVariables.getColumnModel().getColumn(3).getCellEditor().getCellEditorValue().toString(), editedrow, 3);
					 
				 }
				 
				 
			});
			
			
			/*Column 5 Check box*/
			

			 final JCheckBox check = new JCheckBox();
			 
			tabVariables.getColumnModel().getColumn(4).setPreferredWidth(40);
			
			tabVariables.getColumnModel().getColumn(4).setCellEditor(new JCheckBoxCellEditor(check));
			tabVariables.getColumnModel().getColumn(4).getCellEditor().getClass();
			tabVariables.getColumnModel().getColumn(4).getCellEditor().addCellEditorListener(new EditorListener(){
				 
				 
				 public void editingStopped(ChangeEvent e) {
					 
					 
					 int editedrow = ((JCheckBoxCellEditor) tabVariables.getColumnModel().getColumn(4).getCellEditor()).getRow();
					 
					 if( ((JCheckBoxCellEditor) tabVariables.getColumnModel().getColumn(4).getCellEditor()).getCellEditorValue().equals(true))
					 {
						 /*Set the check box*/
						 tabvariableModel.changeValueAt("true",editedrow,4 );

						 for(int i=0; i<tempSingleBandpassFilteredTimeSeries.size();i++){
				    	    	
						    	
							 if(!tempSingleBandpassFilteredTimeSeries.get(i).isVariableInstance){
								 
								 if(tempSingleBandpassFilteredTimeSeries.get(i).ratioInstance.ratioInstanceName.equals(tabvariableModel.getValueAt(editedrow, 0)))
								 {
									 tempSingleBandpassFilteredTimeSeries.get(i).lowerBoundEnabled = true;
									 break;
									 
								 }
								 
								 
							 }
		  	  
						 }
						 
						 tabvariableModel.updatetable();
				
						 
						 
					 }else{
						 /*Set the check box*/
						 tabvariableModel.changeValueAt("false",((JCheckBoxCellEditor) tabVariables.getColumnModel().getColumn(4).getCellEditor()).getRow(),4 );

						 for(int i=0; i<tempSingleBandpassFilteredTimeSeries.size();i++){
				    	    	
						    	
							 if(!tempSingleBandpassFilteredTimeSeries.get(i).isVariableInstance){
								 
								 if(tempSingleBandpassFilteredTimeSeries.get(i).ratioInstance.ratioInstanceName.equals(tabvariableModel.getValueAt(editedrow, 0)))
								 {
									 tempSingleBandpassFilteredTimeSeries.get(i).lowerBoundEnabled = false;
									 break;
									 
								 }
								 
								 
							 }
		  	  
						 }
						 
						 tabvariableModel.updatetable();
						
					 }
					 
				    }
				 
				 
				 
			 });
			 
			 
			tabVariables.getColumnModel().getColumn(4).setCellRenderer(new DefaultTableCellRenderer() {
				 public Component getTableCellRendererComponent(JTable table,
				 Object value, boolean isSelected, boolean hasFocus, int row, int
				 column) {
					 
					 System.out.println(value);
				 check.setSelected(((Boolean)value).booleanValue()) ;
				 return check;
				 }
				 
				 
				 
				 });
			 
			
			
			
			
	/*Column 6*/
			
			tabVariables.getColumnModel().getColumn(5).setPreferredWidth(140);
			tabVariables.getColumnModel().getColumn(5).setCellEditor(new CellEditor());
			
			
			
			tabVariables.getColumnModel().getColumn(5).getCellEditor().addCellEditorListener(new EditorListener(){
				 
				 
				 public void editingStopped(ChangeEvent e) {
					 
					 System.out.println("Col 2 editing stopped");
					 
					 int editedrow = ((CellEditor) tabVariables.getColumnModel().getColumn(5).getCellEditor()).getRow();
		    
					 for(int i=0; i<tempSingleBandpassFilteredTimeSeries.size();i++){
			    	    	
					    	
						 if(!tempSingleBandpassFilteredTimeSeries.get(i).isVariableInstance){
							 
							 if(tempSingleBandpassFilteredTimeSeries.get(i).ratioInstance.ratioInstanceName.equals(tabvariableModel.getValueAt(editedrow, 0)))
							 {
								 try{
								 tempSingleBandpassFilteredTimeSeries.get(i).upperBound = Double.parseDouble(tabVariables.getColumnModel().getColumn(5).getCellEditor().getCellEditorValue().toString());
								 }catch(Exception ex){
									 
									 JOptionPane.showMessageDialog(null,"Parameter must be a numeric expression!");
									 
								 }
								 
								 break;
								 
							 }
							 
							 
						 }
	  	  
					 }
		    	      
		    	      tabvariableModel.changeValueAt(tabVariables.getColumnModel().getColumn(5).getCellEditor().getCellEditorValue().toString(), editedrow, 5);
					 
				 }
				 
				 
			});
			
			
			/*Column 7 Check box*/
			

			 final JCheckBox check2 = new JCheckBox();
			 
			tabVariables.getColumnModel().getColumn(6).setPreferredWidth(40);
			
			tabVariables.getColumnModel().getColumn(6).setCellEditor(new JCheckBoxCellEditor(check2));
			tabVariables.getColumnModel().getColumn(6).getCellEditor().getClass();
			tabVariables.getColumnModel().getColumn(6).getCellEditor().addCellEditorListener(new EditorListener(){
				 
				 
				 public void editingStopped(ChangeEvent e) {
					 
					 
					 int editedrow = ((JCheckBoxCellEditor) tabVariables.getColumnModel().getColumn(6).getCellEditor()).getRow();
					 
					 if( ((JCheckBoxCellEditor) tabVariables.getColumnModel().getColumn(6).getCellEditor()).getCellEditorValue().equals(true))
					 {
						 /*Set the check box*/
						 tabvariableModel.changeValueAt("true",editedrow,6 );

						 for(int i=0; i<tempSingleBandpassFilteredTimeSeries.size();i++){
				    	    	
						    	
							 if(!tempSingleBandpassFilteredTimeSeries.get(i).isVariableInstance){
								 
								 if(tempSingleBandpassFilteredTimeSeries.get(i).ratioInstance.ratioInstanceName.equals(tabvariableModel.getValueAt(editedrow, 0)))
								 {
									 tempSingleBandpassFilteredTimeSeries.get(i).upperBoundEnabled = true;
									 break;
									 
								 }
								 
								 
							 }
		  	  
						 }
						 
						 tabvariableModel.updatetable();
				
						 
						 
					 }else{
						 /*Set the check box*/
						 tabvariableModel.changeValueAt("false",((JCheckBoxCellEditor) tabVariables.getColumnModel().getColumn(6).getCellEditor()).getRow(),6 );

						 for(int i=0; i<tempSingleBandpassFilteredTimeSeries.size();i++){
				    	    	
						    	
							 if(!tempSingleBandpassFilteredTimeSeries.get(i).isVariableInstance){
								 
								 if(tempSingleBandpassFilteredTimeSeries.get(i).ratioInstance.ratioInstanceName.equals(tabvariableModel.getValueAt(editedrow, 0)))
								 {
									 tempSingleBandpassFilteredTimeSeries.get(i).upperBoundEnabled = false;
									 break;
									 
								 }
								 
								 
							 }
		  	  
						 }
						 
						 tabvariableModel.updatetable();
						
					 }
					 
				    }

				 
			 });
			 
			 
			tabVariables.getColumnModel().getColumn(6).setCellRenderer(new DefaultTableCellRenderer() {
				 public Component getTableCellRendererComponent(JTable table,
				 Object value, boolean isSelected, boolean hasFocus, int row, int
				 column) {
					 
					 System.out.println(value);
				 check2.setSelected(((Boolean)value).booleanValue()) ;
				 return check2;
				 }
				 
				 
				 
				 });
			
			
			 final JCheckBox check3 = new JCheckBox();
			 
				tabVariables.getColumnModel().getColumn(7).setPreferredWidth(40);
				
				tabVariables.getColumnModel().getColumn(7).setCellEditor(new JCheckBoxCellEditor(check3));
				tabVariables.getColumnModel().getColumn(7).getCellEditor().getClass();
				tabVariables.getColumnModel().getColumn(7).getCellEditor().addCellEditorListener(new EditorListener(){
					 
					 
					 public void editingStopped(ChangeEvent e) {
						 
						 
						 int editedrow = ((JCheckBoxCellEditor) tabVariables.getColumnModel().getColumn(7).getCellEditor()).getRow();
						 
						 if( ((JCheckBoxCellEditor) tabVariables.getColumnModel().getColumn(7).getCellEditor()).getCellEditorValue().equals(true))
						 {
							 /*Set the check box*/
							 tabvariableModel.changeValueAt("true",editedrow,7 );

							 for(int i=0; i<tempSingleBandpassFilteredTimeSeries.size();i++){
					    	    	
							    	
								 if(!tempSingleBandpassFilteredTimeSeries.get(i).isVariableInstance){
									 
									 if(tempSingleBandpassFilteredTimeSeries.get(i).ratioInstance.ratioInstanceName.equals(tabvariableModel.getValueAt(editedrow, 0)) )
									 {
										 tempSingleBandpassFilteredTimeSeries.get(i).correlation = true;
										 break;
										 
									 }
									 
									 
								 }
			  	  
							 }
							 
							 tabvariableModel.updatetable();
					
							 
							 
						 }else{
							 /*Set the check box*/
							 tabvariableModel.changeValueAt("false",((JCheckBoxCellEditor) tabVariables.getColumnModel().getColumn(7).getCellEditor()).getRow(),7 );

							 for(int i=0; i<tempSingleBandpassFilteredTimeSeries.size();i++){
					    	    	
							    	
								 if(!tempSingleBandpassFilteredTimeSeries.get(i).isVariableInstance){
									 
									 if(tempSingleBandpassFilteredTimeSeries.get(i).ratioInstance.ratioInstanceName.equals(tabvariableModel.getValueAt(editedrow, 0)))
									 {
										 tempSingleBandpassFilteredTimeSeries.get(i).correlation = false;
										 break;
										 
									 }
									 
									 
								 }
			  	  
							 }
							 
							 tabvariableModel.updatetable();
							
						 }
						 
					    }
					 
					 
					 
				 });
				 
				 
				tabVariables.getColumnModel().getColumn(7).setCellRenderer(new DefaultTableCellRenderer() {
					 public Component getTableCellRendererComponent(JTable table,
					 Object value, boolean isSelected, boolean hasFocus, int row, int
					 column) {
						 
						 System.out.println(value);
					 check3.setSelected(((Boolean)value).booleanValue()) ;
					 return check3;
					 }
					 
					 
					 
					 });
				
				
				
				
				 final JCheckBox check4 = new JCheckBox();
				 
					tabVariables.getColumnModel().getColumn(8).setPreferredWidth(40);
					
					tabVariables.getColumnModel().getColumn(8).setCellEditor(new JCheckBoxCellEditor(check4));
					tabVariables.getColumnModel().getColumn(8).getCellEditor().getClass();
					tabVariables.getColumnModel().getColumn(8).getCellEditor().addCellEditorListener(new EditorListener(){
						 
						 
						 public void editingStopped(ChangeEvent e) {
							 
							 
							 int editedrow = ((JCheckBoxCellEditor) tabVariables.getColumnModel().getColumn(8).getCellEditor()).getRow();
							 
							 if( ((JCheckBoxCellEditor) tabVariables.getColumnModel().getColumn(8).getCellEditor()).getCellEditorValue().equals(true))
							 {
								 /*Set the check box*/
								 tabvariableModel.changeValueAt("true",editedrow,8 );

								 for(int i=0; i<tempSingleBandpassFilteredTimeSeries.size();i++){
						    	    	
								    	
									 if(!tempSingleBandpassFilteredTimeSeries.get(i).isVariableInstance){
										 
										 if(tempSingleBandpassFilteredTimeSeries.get(i).ratioInstance.ratioInstanceName.equals(tabvariableModel.getValueAt(editedrow, 0)))
										 {
											 tempSingleBandpassFilteredTimeSeries.get(i).logarithmic = true;
											 break;
											 
										 }
										 
										 
									 }
				  	  
								 }
								 
								 tabvariableModel.updatetable();
						
								 
								 
							 }else{
								 /*Set the check box*/
								 tabvariableModel.changeValueAt("false",((JCheckBoxCellEditor) tabVariables.getColumnModel().getColumn(8).getCellEditor()).getRow(),8 );

								 for(int i=0; i<tempSingleBandpassFilteredTimeSeries.size();i++){
						    	    	
								    	
									 if(!tempSingleBandpassFilteredTimeSeries.get(i).isVariableInstance){
										 
										 if(tempSingleBandpassFilteredTimeSeries.get(i).ratioInstance.ratioInstanceName.equals(tabvariableModel.getValueAt(editedrow, 0)))
										 {
											 tempSingleBandpassFilteredTimeSeries.get(i).logarithmic = false;
											 break;
											 
										 }
										 
										 
									 }
				  	  
								 }
								 
								 tabvariableModel.updatetable();
								
							 }
							 
						    }
						 
						 
						 
					 });
					 
					 
					tabVariables.getColumnModel().getColumn(8).setCellRenderer(new DefaultTableCellRenderer() {
						 public Component getTableCellRendererComponent(JTable table,
						 Object value, boolean isSelected, boolean hasFocus, int row, int
						 column) {
							 
							 System.out.println(value);
						 check4.setSelected(((Boolean)value).booleanValue()) ;
						 return check4;
						 }
						 
						 
						 
						 });
					
					
					
					
					
					
					
			
					String [] selChoice2 = {"No", "Sum", "Mean"};

					tabVariables.getColumnModel().getColumn(9).setCellEditor(new SpecificComboBoxEditor(selChoice2));
					tabVariables.getColumnModel().getColumn(9).setCellRenderer(new SpecificComboBoxRenderer(selChoice2));
					
					
					
					tabVariables.getColumnModel().getColumn(9).getCellEditor().addCellEditorListener(new EditorListener(){
						 
						 
						 public void editingStopped(ChangeEvent e) {
							 
							 System.out.println("Editing ended!");
							 
						 }
						 
						 
					});
					
					
					tabVariables.getColumnModel().getColumn(10).setPreferredWidth(140);
					tabVariables.getColumnModel().getColumn(10).setCellEditor(new CellEditor());
					
					
					
					tabVariables.getColumnModel().getColumn(10).getCellEditor().addCellEditorListener(new EditorListener(){
						 
						 
						 public void editingStopped(ChangeEvent e) {
							 
							 System.out.println("Col 10 editing stopped");
							 
							 int editedrow = ((CellEditor) tabVariables.getColumnModel().getColumn(10).getCellEditor()).getRow();
				    
							 for(int i=0; i<tempSingleBandpassFilteredTimeSeries.size();i++){
					    	    	
							    	
								 if(!tempSingleBandpassFilteredTimeSeries.get(i).isVariableInstance){
									 
									 if(tempSingleBandpassFilteredTimeSeries.get(i).ratioInstance.ratioInstanceName.equals(tabvariableModel.getValueAt(editedrow, 0)) )
									 {
										 try{
										 tempSingleBandpassFilteredTimeSeries.get(i).low = Integer.parseInt(tabVariables.getColumnModel().getColumn(10).getCellEditor().getCellEditorValue().toString());
										 break;
										 }catch(Exception exp){
											 
											 JOptionPane.showMessageDialog(null,"Parameter must be an integer!");
											 
										 }
										 
									 }
									 
									 
								 }
			  	  
							 }
				    	      
				    	      tabvariableModel.changeValueAt(tabVariables.getColumnModel().getColumn(10).getCellEditor().getCellEditorValue().toString(), editedrow, 10);
							 
						 }
						 
						 
					});
					
					
					
					tabVariables.getColumnModel().getColumn(11).setPreferredWidth(140);
					tabVariables.getColumnModel().getColumn(11).setCellEditor(new CellEditor());
					
					
					
					tabVariables.getColumnModel().getColumn(11).getCellEditor().addCellEditorListener(new EditorListener(){
						 
						 
						 public void editingStopped(ChangeEvent e) {
							 
							 System.out.println("Col 11 editing stopped");
							 
							 int editedrow = ((CellEditor) tabVariables.getColumnModel().getColumn(11).getCellEditor()).getRow();
				    
							 for(int i=0; i<tempSingleBandpassFilteredTimeSeries.size();i++){
					    	    	
							    	
								 if(!tempSingleBandpassFilteredTimeSeries.get(i).isVariableInstance){
									 
									 if(tempSingleBandpassFilteredTimeSeries.get(i).ratioInstance.ratioInstanceName.equals(tabvariableModel.getValueAt(editedrow, 0)) )
									 {
										 try{
										 tempSingleBandpassFilteredTimeSeries.get(i).high = Integer.parseInt(tabVariables.getColumnModel().getColumn(11).getCellEditor().getCellEditorValue().toString());
										 break;
										 }catch(Exception exp){
											 
											 JOptionPane.showMessageDialog(null,"Parameter must be an integer!");
											 
										 }
										 
									 }
									 
									 
								 }
			  	  
							 }
				    	      
				    	      tabvariableModel.changeValueAt(tabVariables.getColumnModel().getColumn(11).getCellEditor().getCellEditorValue().toString(), editedrow, 11);
							 
						 }
						 
						 
					});
					
					
					
					tabVariables.getColumnModel().getColumn(12).setPreferredWidth(140);
					tabVariables.getColumnModel().getColumn(12).setCellEditor(new CellEditor());
					
					
					
					tabVariables.getColumnModel().getColumn(12).getCellEditor().addCellEditorListener(new EditorListener(){
						 
						 
						 public void editingStopped(ChangeEvent e) {
							 
							 System.out.println("Col 11 editing stopped");
							 
							 int editedrow = ((CellEditor) tabVariables.getColumnModel().getColumn(12).getCellEditor()).getRow();
				    
							 for(int i=0; i<tempSingleBandpassFilteredTimeSeries.size();i++){
					    	    	
							    	
								 if(!tempSingleBandpassFilteredTimeSeries.get(i).isVariableInstance){
									 
									 if(tempSingleBandpassFilteredTimeSeries.get(i).ratioInstance.ratioInstanceName.equals(tabvariableModel.getValueAt(editedrow, 0)) )
									 {
										 try{
										 tempSingleBandpassFilteredTimeSeries.get(i).Nfix = Integer.parseInt(tabVariables.getColumnModel().getColumn(12).getCellEditor().getCellEditorValue().toString());
										 break;
										 }catch(Exception exp){
											 
											 JOptionPane.showMessageDialog(null,"Parameter must be an integer!");
											 
										 }
										 
									 }
									 
									 
								 }
			  	  
							 }
				    	      
				    	      tabvariableModel.changeValueAt(tabVariables.getColumnModel().getColumn(12).getCellEditor().getCellEditorValue().toString(), editedrow, 12);
							 
						 }
						 
						 
					});
					
					
					
					
					 final JCheckBox check5 = new JCheckBox();
					 
						tabVariables.getColumnModel().getColumn(13).setPreferredWidth(40);
						
						tabVariables.getColumnModel().getColumn(13).setCellEditor(new JCheckBoxCellEditor(check5));
						tabVariables.getColumnModel().getColumn(13).getCellEditor().getClass();
						tabVariables.getColumnModel().getColumn(13).getCellEditor().addCellEditorListener(new EditorListener(){
							 
							 
							 public void editingStopped(ChangeEvent e) {
								 
								 
								 int editedrow = ((JCheckBoxCellEditor) tabVariables.getColumnModel().getColumn(13).getCellEditor()).getRow();
								 
								 if( ((JCheckBoxCellEditor) tabVariables.getColumnModel().getColumn(13).getCellEditor()).getCellEditorValue().equals(true))
								 {
									 /*Set the check box*/
									 tabvariableModel.changeValueAt("true",editedrow,13 );

									 for(int i=0; i<tempSingleBandpassFilteredTimeSeries.size();i++){
							    	    	
									    	
										 if(!tempSingleBandpassFilteredTimeSeries.get(i).isVariableInstance){
											 
											 if(tempSingleBandpassFilteredTimeSeries.get(i).ratioInstance.ratioInstanceName.equals(tabvariableModel.getValueAt(editedrow, 0)) )
											 {
												 tempSingleBandpassFilteredTimeSeries.get(i).drift = true;
												 break;
												 
											 }
											 
											 
										 }
					  	  
									 }
									 
									 tabvariableModel.updatetable();
							
									 
									 
								 }else{
									 /*Set the check box*/
									 tabvariableModel.changeValueAt("false",((JCheckBoxCellEditor) tabVariables.getColumnModel().getColumn(13).getCellEditor()).getRow(),13 );

									 for(int i=0; i<tempSingleBandpassFilteredTimeSeries.size();i++){
							    	    	
									    	
										 if(!tempSingleBandpassFilteredTimeSeries.get(i).isVariableInstance){
											 
											 if(tempSingleBandpassFilteredTimeSeries.get(i).ratioInstance.ratioInstanceName.equals(tabvariableModel.getValueAt(editedrow, 0)) )
											 {
												 tempSingleBandpassFilteredTimeSeries.get(i).drift = false;
												 break;
												 
											 }
											 
											 
										 }
					  	  
									 }
									 
									 tabvariableModel.updatetable();
									
								 }
								 
							    }
							 
							 
							 
						 });
						 
						 
						tabVariables.getColumnModel().getColumn(13).setCellRenderer(new DefaultTableCellRenderer() {
							 public Component getTableCellRendererComponent(JTable table,
							 Object value, boolean isSelected, boolean hasFocus, int row, int
							 column) {
								 
								 System.out.println(value);
							 check5.setSelected(((Boolean)value).booleanValue()) ;
							 return check5;
							 }
							 
							 
							 
							 });
			

			
			tabVariables.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	
			
			System.out.println("xx "+tabVariables.getColumnCount());
			
			
			
			tabVariables.addMouseListener(new MouseAdapter() {
				public void mouseClicked(final MouseEvent e) {
					
					tabVariables.setRowSelectionAllowed(true);
					
					System.out.println("xxtabVariables.getSelectedRow() "+tabVariables.getSelectedRow());
					System.out.println("xxtabbedPane.getSelectedIndex()"+tabVariables.getRowCount());
					
					
				
			
					
					
	         }
			});
			System.out.println("draw table");
			tabvariableModel.updatetable();
			
			listScrollVariables = new JScrollPane(tabVariables);
			
			
		}
		private class SpecificComboBoxEditor extends DefaultCellEditor {
				
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
							 
							 
			    	    	 for(int i=0; i<tempSingleBandpassFilteredTimeSeries.size();i++){
					    	    	
							    	
								 if(!tempSingleBandpassFilteredTimeSeries.get(i).isVariableInstance){
									 
									 if(tempSingleBandpassFilteredTimeSeries.get(i).ratioInstance.ratioInstanceName.equals(tabvariableModel.getValueAt(editedRow, 0)))
									 {
											
										 if(editedCol==9){
												
											 tempSingleBandpassFilteredTimeSeries.get(i).aggregation = cComboBox.getSelectedItem().toString();
										 break;
										 }
										 
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
			
			
		private class SpecificComboBoxRenderer extends JComboBox implements TableCellRenderer {
				
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
	




public class TableModelRatio extends AbstractTableModel{
	
	JCheckBox checkBox = new JCheckBox();
	String [] columnName;
	
	
	ArrayList<RatioTimeSeriesPlotting> selectedRatioList;
	
	
	
	
	
	
	int numFilters;
	boolean partitioningSelected , filterSelected;
	
	
	TableModelRatio(String[] colName, ArrayList<PlottingSettings.RatioInstance> ratioList){
		
		selectedRatioList = new ArrayList<RatioTimeSeriesPlotting>();

		
		for(int i=0; i<tempSingleBandpassFilteredTimeSeries.size();i++){
	    	
	    	
			 if(!tempSingleBandpassFilteredTimeSeries.get(i).isVariableInstance){
				 
			
				 selectedRatioList.add(new RatioTimeSeriesPlotting(tempSingleBandpassFilteredTimeSeries.get(i).instanceName, 
						 tempSingleBandpassFilteredTimeSeries.get(i).tmin,tempSingleBandpassFilteredTimeSeries.get(i).tmax,
						 tempSingleBandpassFilteredTimeSeries.get(i).lowerBound,tempSingleBandpassFilteredTimeSeries.get(i).upperBound,
						 tempSingleBandpassFilteredTimeSeries.get(i).lowerBoundEnabled,tempSingleBandpassFilteredTimeSeries.get(i).upperBoundEnabled,
						 tempSingleBandpassFilteredTimeSeries.get(i).correlation, tempSingleBandpassFilteredTimeSeries.get(i).logarithmic,
						 tempSingleBandpassFilteredTimeSeries.get(i).aggregation,
						tempSingleBandpassFilteredTimeSeries.get(i).low,tempSingleBandpassFilteredTimeSeries.get(i).high,
						 tempSingleBandpassFilteredTimeSeries.get(i).Nfix,tempSingleBandpassFilteredTimeSeries.get(i).drift));
						 
				 
			 }

		 }
				
		columnName = colName;
	
		
	}
	
	

	public int getRowCount()
   {
		
			return selectedRatioList.size();
			
			}
			
 
	
	
   public int getColumnCount()
   {
   return 14;
 }
  
   
   public Object getValueAt( int row, int col)
   {
	   
	   if(col==0){
		   return selectedRatioList.get(row).instanceName;
	   }else if(col==1){
		   
		   return selectedRatioList.get(row).tmin;
	   }else if(col==2){
		   return selectedRatioList.get(row).tmax;
		   
	   }else if(col==3){
		   
			if( selectedRatioList.get(row).lowerBoundEnabled)
				 return Double.toString(selectedRatioList.get(row).lowerBound);
	   		else
	   			return "";
	   }else if(col==4){
		   return selectedRatioList.get(row).lowerBoundEnabled;
	   }else if(col==5){
		   if( selectedRatioList.get(row).upperBoundEnabled)
				 return Double.toString(selectedRatioList.get(row).upperBound);
	   		else
	   			return "";
	   }else if(col==6){
		   return selectedRatioList.get(row).upperBoundEnabled;
	  }else if(col==7){
		   return selectedRatioList.get(row).correlation;
	  }else if(col==8){
		   return selectedRatioList.get(row).logarithmic;
	  }else if(col==9){
		   return selectedRatioList.get(row).aggregation;
	  }else if(col==10){
		   return selectedRatioList.get(row).low;
	   }else if(col==11){
		   return selectedRatioList.get(row).high;
	   }else if(col==12){
		   return selectedRatioList.get(row).Nfix;
	   }else if(col==13){
		   return selectedRatioList.get(row).drift;
	   }else{
		   return null;
	   }
		   
	   
	   
	   
   }
   
   public String getColumnName(int column) {  
       return columnName[column]; 
   }
   
   public Class<?> getColumnClass(int col) {  
	   if (col == 4 || col==6 ||col==7 ||col==8 ||col==13)  {  
	        return Boolean.class;  
	     }  
	     return getValueAt(0, col).getClass();  
	  }  
   
   
   public void changeValueAt(String value, int row, int col) {  

	   if(col==1)
		   selectedRatioList.get(row).tmin=Integer.parseInt(value);
	   if(col==2)
		   selectedRatioList.get(row).tmax=Integer.parseInt(value);
	   else if(col==3){
		   selectedRatioList.get(row).lowerBound =   Double.parseDouble(value);;
	   }else if(col==4){
		   if(value.equals("true"))
			   selectedRatioList.get(row).lowerBoundEnabled = true;
		   else
			   selectedRatioList.get(row).lowerBoundEnabled = false;
	   }else if(col==5){
		   selectedRatioList.get(row).upperBound =   Double.parseDouble(value);;
	   }else if(col==6){
		   if(value.equals("true"))
			   selectedRatioList.get(row).upperBoundEnabled = true;
		   else
			   selectedRatioList.get(row).upperBoundEnabled = false;
	   } else if(col==7){
			   if(value.equals("true"))
				   selectedRatioList.get(row).correlation = true;
			   else
				   selectedRatioList.get(row).correlation = false;
		  }else if(col==8){
			  if(value.equals("true"))
				  selectedRatioList.get(row).logarithmic= true;
			  else
				  selectedRatioList.get(row).logarithmic = false;
		  }else if(col==9){
			   selectedRatioList.get(row).aggregation = value;
		  }else if(col==10){
			   selectedRatioList.get(row).low=Integer.parseInt(value);;
		   }else if(col==11){
			   selectedRatioList.get(row).high=Integer.parseInt(value);;
		   }else if(col==12){
			   selectedRatioList.get(row).Nfix=Integer.parseInt(value);;
		   }else if(col==13){
			   if(value.equals("true"))
				   selectedRatioList.get(row).drift=true;
			   else
				   selectedRatioList.get(row).drift=false;
		   }
		   
	   
	   
       this.fireTableDataChanged();  
         
   }

   
   public void updatetable() {  
       
       this.fireTableDataChanged();    
         
   }  
   
   public boolean isCellEditable(int row, int column){ 
      
	   if(column==0){
   		return false;
   	}else  if(column==1){
   		
   		return true;
   	}else if(column==2){
   		
   		return true;
   	}else if(column==3){
   		if( selectedRatioList.get(row).lowerBoundEnabled)
   			return true;
   		else
   			return false;
   	}else if(column==4){
   		
   		return true;
   	}else if(column==5){
   		if( selectedRatioList.get(row).upperBoundEnabled)
   			return true;
   		else
   			return false;
   	}else if(column==6){
   		
   		return true;
   		
   	}else{
   		
   		return true;
   	}
   }
   
   
   private class RatioTimeSeriesPlotting{
	   
	   
	   String instanceName;
	   int tmin;
	   int tmax;
		double lowerBound;
		double upperBound;
		boolean lowerBoundEnabled;
		boolean upperBoundEnabled;
		boolean correlation;
		boolean logarithmic;
		String aggregation;
		int low;
		int high;
		int Nfix;
		boolean drift;
	   
	   RatioTimeSeriesPlotting(String name, int tmi, int tma, double loBo, double upBo, boolean loBoEnabled, boolean upBoEnabled, boolean cor, boolean log, String agg, int lo, int hi, int nf, boolean dr ){
		   
		   instanceName = name;
		   tmin = tmi;
		   tmax = tma;
		   lowerBound = loBo;
		   upperBound = upBo;
			lowerBoundEnabled = loBoEnabled;
			upperBoundEnabled = upBoEnabled;
			correlation = cor;
			logarithmic = log;
			aggregation = agg;
			low = lo;
			high = hi;
			Nfix = nf;
			drift= dr;
		   
	   }
	   


   }

   }		
	
}


