import java.awt.BorderLayout;
import java.awt.Color;

import java.awt.Dimension;

import java.awt.Component;
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
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
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


public class SingleTimeSeriesMenu  extends JDialog{
	

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
	
	ArrayList<PlottingSettings.SingleTimeSeries> tempSingleTimesSeries;
	ArrayList<PlottingSettings.Agent> tempAgentInstanceVariable;
	ArrayList<PlottingSettings.RatioInstance>  tempRatioInstanceVariable;
	
	PlottingSettings.DefaulSingleTimeSeriesSettings tempDefaults;
	
	boolean tempRatiosSelectedForSingleTimeSeries;
	
	private RatioTab ratioTab;

	SingleTimeSeriesMenu(){
		
		//agentsReturn = agentsPassed;

		
		tempSingleTimesSeries = new ArrayList<PlottingSettings.SingleTimeSeries>();

		for(int i=0; i< PlottingSettings.listOfSingleTimeSeries.size();i++){
			
			
			tempSingleTimesSeries.add(AuxFunctions.DeepCopyTimeSeriesList(PlottingSettings.listOfSingleTimeSeries.get(i)));
			
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
		
		
		tempDefaults = PlottingSettings.defaultsSingleTimeSeries.clone();
		
		
		/*Dialog settings*/	
	    setTitle("Single Time Series Plotting");
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

	    			PlottingSettings.listOfSingleTimeSeries = tempSingleTimesSeries;
	    			PlottingSettings.listOfAgentsVariableInstances = tempAgentInstanceVariable;
	    			PlottingSettings.listOfRatioInstances = tempRatioInstanceVariable;
	    			PlottingSettings.ratiosSelectedForSingleTimeSeries = tempRatiosSelectedForSingleTimeSeries;
	    			setVisible(false);
	    			dispose();
	    			
	    			
	    		}else if(choice==1){
	    			
	    			/*Choice is no*/
	    			PlottingSettings.defaultsSingleTimeSeries = tempDefaults;
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
				
				JLabel label1 = new JLabel("Select Agent for Plotting Time Series:");
				fA.gridx =0;fA.gridy =0;
				addAgentDialog.add(label1,fA);
				
				dlmAgents = new DefaultListModel();
				
				agentList = new JList(dlmAgents);
				
				for(int i=0; i<tempAgentInstanceVariable.size();i++){
					
					if(tempAgentInstanceVariable.get(i).isSelected && !tempAgentInstanceVariable.get(i).singleTimeSeriesSelected){
						
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
										
										
										tempAgentInstanceVariable.get(i).singleTimeSeriesSelected = true;
								

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
				
				JLabel label1 = new JLabel("Delete Agent from Plotting Time Series:");
				fA.gridx =0;fA.gridy =0;
				removeAgentDialog.add(label1,fA);
				
				dlmAgentsToremove = new DefaultListModel();
				
				removeAgentList = new JList(dlmAgentsToremove);
				
				for(int i=0; i<tempAgentInstanceVariable.size();i++){
					
					if(tempAgentInstanceVariable.get(i).isSelected && tempAgentInstanceVariable.get(i).singleTimeSeriesSelected){
						
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
												
												tempAgentInstanceVariable.get(j).singleTimeSeriesSelected = false;
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

				PlottingSettings.listOfSingleTimeSeries = tempSingleTimesSeries;
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

	    			PlottingSettings.listOfSingleTimeSeries = tempSingleTimesSeries;
	    			PlottingSettings.listOfAgentsVariableInstances = tempAgentInstanceVariable;
	    			PlottingSettings.listOfRatioInstances = tempRatioInstanceVariable;
	    			PlottingSettings.ratiosSelectedForSingleTimeSeries = tempRatiosSelectedForSingleTimeSeries;
	    			
	    			setVisible(false);
	    			dispose();
	    			
	    			
	    		}else if(choice==1){
	    			
	    			/*Choice is no*/
	    			
	    			PlottingSettings.defaultsSingleTimeSeries = tempDefaults; 
	    			
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
					
					
						if(!agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.listOfVariableInstances.get(i).selectedForSingleTimeSeries)
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
										
										agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.listOfVariableInstances.get(j).selectedForSingleTimeSeries = false;
										
										/*Delete from temp TS list:*/
										
										for(int k=0; k<tempSingleTimesSeries.size();k++){
											
											if(tempSingleTimesSeries.get(k).isVariableInstance && tempSingleTimesSeries.get(k).instanceName.equals(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.listOfVariableInstances.get(j).instanceName)){
												
												tempSingleTimesSeries.remove(k);
												k--;
												
											}
											
										}
										

										break;
									}
									
								}	
	    	    			
	    	    			}
	    	    			
	    	    			for(int i=0; i<dlm2Parameter.getSize(); i++ ){    	    			
	    	    				for(int j=0; j<agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.listOfVariableInstances.size();j++){
									
	    							/*XXX/*/
									
									if(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.listOfVariableInstances.get(j).instanceName.equals(dlm2Parameter.get(i))){
										
										if(!agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.listOfVariableInstances.get(j).selectedForSingleTimeSeries){
										
										agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.listOfVariableInstances.get(j).selectedForSingleTimeSeries = true;
										
										/*Add to temp TS list:*/
										tempSingleTimesSeries.add((new PlottingSettings()).new SingleTimeSeries(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.listOfVariableInstances.get(j)));					
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
									
									agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.listOfVariableInstances.get(j).selectedForSingleTimeSeries = false;
									
									/*Delete from temp TS list:*/
									
									for(int k=0; k<tempSingleTimesSeries.size();k++){
										
										if(tempSingleTimesSeries.get(k).isVariableInstance && tempSingleTimesSeries.get(k).instanceName.equals(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.listOfVariableInstances.get(j).instanceName)){
											
											tempSingleTimesSeries.remove(k);
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
									
									if(!agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.listOfVariableInstances.get(j).selectedForSingleTimeSeries){
									
									agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.listOfVariableInstances.get(j).selectedForSingleTimeSeries = true;
									
									/*Add to temp TS list:*/
									tempSingleTimesSeries.add((new PlottingSettings()).new SingleTimeSeries(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.listOfVariableInstances.get(j)));					
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

						if(!tempRatioInstanceVariable.get(i).selectedForSingleTimeSeries)
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
			    							
			    							tempRatioInstanceVariable.get(j).selectedForSingleTimeSeries = false;
			    							
			    	
											
											/*Delete from temp TS list:*/
											
											for(int k=0; k<tempSingleTimesSeries.size();k++){
												
												if(!tempSingleTimesSeries.get(k).isVariableInstance && tempSingleTimesSeries.get(k).instanceName.equals(tempRatioInstanceVariable.get(j).ratioInstanceName)){
													
													tempSingleTimesSeries.remove(k);
													k--;
													
												}
												
											}
			    				
			    							break;
			    						}
	    	    					
	    						
	    	    				}
	    	    			
	    	    			}
	    	    			
	    	    			for(int i=0; i<dlm2Parameter.getSize(); i++ ){    	    			
	    	    				for(int j=0; j<tempRatioInstanceVariable.size();j++){
	    	    					
	    	    					
			    						if(tempRatioInstanceVariable.get(j).ratioInstanceName.equals(dlm2Parameter.get(i)) && tempRatioInstanceVariable.get(j).selectedForSingleTimeSeries==false){
		    							
			    							tempRatioInstanceVariable.get(j).selectedForSingleTimeSeries = true;
			    							
			    							tempSingleTimesSeries.add((new PlottingSettings()).new SingleTimeSeries(tempRatioInstanceVariable.get(j)));
			    		
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
		    							
		    							tempRatioInstanceVariable.get(j).selectedForSingleTimeSeries = false;
		    							
		    	
										
										/*Delete from temp TS list:*/
										
										for(int k=0; k<tempSingleTimesSeries.size();k++){
											
											if(!tempSingleTimesSeries.get(k).isVariableInstance && tempSingleTimesSeries.get(k).instanceName.equals(tempRatioInstanceVariable.get(j).ratioInstanceName)){
												
												tempSingleTimesSeries.remove(k);
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
	    							
		    							tempRatioInstanceVariable.get(j).selectedForSingleTimeSeries = true;
		    							
		    							tempSingleTimesSeries.add((new PlottingSettings()).new SingleTimeSeries(tempRatioInstanceVariable.get(j)));
		    		
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
	   			textfield1.setText(Integer.toString(PlottingSettings.defaultsSingleTimeSeries.tmin));
	   			
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
	   			textfield2.setText(Integer.toString(PlottingSettings.defaultsSingleTimeSeries.tmax));
	   			
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
	   			
	   			enterDS.gridx=4; enterDS.gridy=4;
	   			defaultSettingsDialog.add(okay,enterDS);
	   			
	   			enterDS.gridx=5; enterDS.gridy=4;
	   			defaultSettingsDialog.add(discard,enterDS);
	   			
	   			okay.addActionListener(new ActionListener(){
	  	    		
	  	    		public void actionPerformed(ActionEvent evt) {
	  	    			
	  	    			
	  	    			try {
	  	    				
	  	    				
	  	    				PlottingSettings.defaultsSingleTimeSeries.tmin = Integer.parseInt(textfield1.getText());
	  	    				PlottingSettings.defaultsSingleTimeSeries.tmax = Integer.parseInt(textfield2.getText());
	  	    				
	  	    				
	  	    				for(int i=0; i < tempSingleTimesSeries.size();i++){
	  	    					
	  	    					tempSingleTimesSeries.get(i).tmin = PlottingSettings.defaultsSingleTimeSeries.tmin;
	  	    					tempSingleTimesSeries.get(i).tmax = PlottingSettings.defaultsSingleTimeSeries.tmax;
	  	    				}
	  	    				
	  	    				/*Redraw table*/
	    	    			 
	  	    				for(int i=0; i < tabbedPane.getTabCount();i++){
	  	    					
	    	    			
		    	    			if(tabbedPane.getTitleAt(i).equals("Ratio")){
		    	    				
		    	    				ratioTab.reDrawTable();
		    	    				
		    	    			}else{
		    	    				
		    	    				agentJPanelList.get(i).reDrawTable();
		    	    				
		    	    			}
	  	    				}
	    	    			
	    	    				  	    				
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
			if(tempAgentInstanceVariable.get(i).singleTimeSeriesSelected)
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
				

				
				String[] colHeadersT2 = {"Variable Instance" , "Tmin", "Tmax", "Lower Limit", "", "Upper Limit","" };
				
				
				tabvariableModel = new TableModelAgent(colHeadersT2, agent );
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
			    
			    	      for(int i=0; i<tempSingleTimesSeries.size();i++){
			    	    	
			    	
								 if(tempSingleTimesSeries.get(i).isVariableInstance){
									 
									 if(tempSingleTimesSeries.get(i).variableInstance.instanceName.equals(tabvariableModel.getValueAt(editedrow, 0)) && tempSingleTimesSeries.get(i).variableInstance.agentName.equals(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.agentName))
									 {
										 try{
										 tempSingleTimesSeries.get(i).tmin = Integer.parseInt(tabVariables.getColumnModel().getColumn(1).getCellEditor().getCellEditorValue().toString());
									 	}catch(Exception ex){
										 
										 JOptionPane.showMessageDialog(null,"Parameter must be an integer!");
										 
									 }
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
			    
						 for(int i=0; i<tempSingleTimesSeries.size();i++){
				    	    	
						    	
							 if(tempSingleTimesSeries.get(i).isVariableInstance){
								 
								 if(tempSingleTimesSeries.get(i).variableInstance.instanceName.equals(tabvariableModel.getValueAt(editedrow, 0)) && tempSingleTimesSeries.get(i).variableInstance.agentName.equals(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.agentName))
								 {
									 try{
									 tempSingleTimesSeries.get(i).tmax = Integer.parseInt(tabVariables.getColumnModel().getColumn(2).getCellEditor().getCellEditorValue().toString());
									 }catch(Exception ex){
										 
										 JOptionPane.showMessageDialog(null,"Parameter must be an integer!");
										 
									 }
									 
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
			    
						 for(int i=0; i<tempSingleTimesSeries.size();i++){
				    	    	
						    	
							 if(tempSingleTimesSeries.get(i).isVariableInstance){
								 
								 if(tempSingleTimesSeries.get(i).variableInstance.instanceName.equals(tabvariableModel.getValueAt(editedrow, 0)) && tempSingleTimesSeries.get(i).variableInstance.agentName.equals(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.agentName))
								 {
									 try{
									 tempSingleTimesSeries.get(i).lowerBound = Double.parseDouble(tabVariables.getColumnModel().getColumn(3).getCellEditor().getCellEditorValue().toString());
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

							 for(int i=0; i<tempSingleTimesSeries.size();i++){
					    	    	
							    	
								 if(tempSingleTimesSeries.get(i).isVariableInstance){
									 
									 if(tempSingleTimesSeries.get(i).variableInstance.instanceName.equals(tabvariableModel.getValueAt(editedrow, 0)) && tempSingleTimesSeries.get(i).variableInstance.agentName.equals(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.agentName))
									 {
										 tempSingleTimesSeries.get(i).lowerBoundEnabled = true;
										 break;
										 
									 }
									 
									 
								 }
			  	  
							 }
							 
							 tabvariableModel.updatetable();
					
							 
							 
						 }else{
							 /*Set the check box*/
							 tabvariableModel.changeValueAt("false",((JCheckBoxCellEditor) tabVariables.getColumnModel().getColumn(4).getCellEditor()).getRow(),4 );

							 for(int i=0; i<tempSingleTimesSeries.size();i++){
					    	    	
							    	
								 if(tempSingleTimesSeries.get(i).isVariableInstance){
									 
									 if(tempSingleTimesSeries.get(i).variableInstance.instanceName.equals(tabvariableModel.getValueAt(editedrow, 0)) && tempSingleTimesSeries.get(i).variableInstance.agentName.equals(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.agentName))
									 {
										 tempSingleTimesSeries.get(i).lowerBoundEnabled = false;
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
			    
						 for(int i=0; i<tempSingleTimesSeries.size();i++){
				    	    	
						    	
							 if(tempSingleTimesSeries.get(i).isVariableInstance){
								 
								 if(tempSingleTimesSeries.get(i).variableInstance.instanceName.equals(tabvariableModel.getValueAt(editedrow, 0)) && tempSingleTimesSeries.get(i).variableInstance.agentName.equals(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.agentName))
								 {
									 try{
									 tempSingleTimesSeries.get(i).upperBound = Double.parseDouble(tabVariables.getColumnModel().getColumn(5).getCellEditor().getCellEditorValue().toString());
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

							 for(int i=0; i<tempSingleTimesSeries.size();i++){
					    	    	
							    	
								 if(tempSingleTimesSeries.get(i).isVariableInstance){
									 
									 if(tempSingleTimesSeries.get(i).variableInstance.instanceName.equals(tabvariableModel.getValueAt(editedrow, 0)) && tempSingleTimesSeries.get(i).variableInstance.agentName.equals(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.agentName))
									 {
										 tempSingleTimesSeries.get(i).upperBoundEnabled = true;
										 break;
										 
									 }
									 
									 
								 }
			  	  
							 }
							 
							 tabvariableModel.updatetable();
					
							 
							 
						 }else{
							 /*Set the check box*/
							 tabvariableModel.changeValueAt("false",((JCheckBoxCellEditor) tabVariables.getColumnModel().getColumn(6).getCellEditor()).getRow(),6 );

							 for(int i=0; i<tempSingleTimesSeries.size();i++){
					    	    	
							    	
								 if(tempSingleTimesSeries.get(i).isVariableInstance){
									 
									 if(tempSingleTimesSeries.get(i).variableInstance.instanceName.equals(tabvariableModel.getValueAt(editedrow, 0)) && tempSingleTimesSeries.get(i).variableInstance.agentName.equals(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.agentName))
									 {
										 tempSingleTimesSeries.get(i).upperBoundEnabled = false;
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

		for(int i=0; i<tempSingleTimesSeries.size();i++){
			
				if(tempSingleTimesSeries.get(i).isVariableInstance){
				
					if(tempSingleTimesSeries.get(i).variableInstance.agentName.equals(agent.agentName)){
						
						plottedInstances.add(new VariableInstanceTimeSeriesPlotting(tempSingleTimesSeries.get(i).variableInstance.instanceName,
								tempSingleTimesSeries.get(i).tmin,tempSingleTimesSeries.get(i).tmax,
								tempSingleTimesSeries.get(i).lowerBound,tempSingleTimesSeries.get(i).upperBound,
								tempSingleTimesSeries.get(i).lowerBoundEnabled,tempSingleTimesSeries.get(i).upperBoundEnabled));
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
   return 7;
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
	   }else{
		   return null;
	   }
		   
	   
	   
	   
   }
   
   public String getColumnName(int column) {  
       return columnName[column]; 
   }
   
   public Class<?> getColumnClass(int col) {  
	     if (col == 4 || col ==6) {  
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
		boolean upperBoundEnabled;
	   
		VariableInstanceTimeSeriesPlotting(String name, int tmi, int tma, double loBo, double upBo, boolean loBoEnabled, boolean upBoEnabled ){
		   
		   instanceName = name;
		   tmin = tmi;
		   tmax = tma;
		   lowerBound = loBo;
		   upperBound = upBo;
			lowerBoundEnabled = loBoEnabled;
			upperBoundEnabled = upBoEnabled;
	  
	   
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
			
			String[] colHeadersT2 = {"Variable Instance" , "Tmin", "Tmax", "Lower Limit", "", "Upper Limit","" };
			
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
		    
					 for(int i=0; i<tempSingleTimesSeries.size();i++){
			    	    	
					    	
						 if(!tempSingleTimesSeries.get(i).isVariableInstance){
							 
							 if(tempSingleTimesSeries.get(i).ratioInstance.ratioInstanceName.equals(tabvariableModel.getValueAt(editedrow, 0)) )
							 {
								 tempSingleTimesSeries.get(i).tmin = Integer.parseInt(tabVariables.getColumnModel().getColumn(1).getCellEditor().getCellEditorValue().toString());
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
		    
					 for(int i=0; i<tempSingleTimesSeries.size();i++){
			    	    	
					    	
						 if(!tempSingleTimesSeries.get(i).isVariableInstance){
							 
							 if(tempSingleTimesSeries.get(i).ratioInstance.ratioInstanceName.equals(tabvariableModel.getValueAt(editedrow, 0)) )
							 {
								 tempSingleTimesSeries.get(i).tmax = Integer.parseInt(tabVariables.getColumnModel().getColumn(2).getCellEditor().getCellEditorValue().toString());
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
		    
					 for(int i=0; i<tempSingleTimesSeries.size();i++){
			    	    	
					    	
						 if(!tempSingleTimesSeries.get(i).isVariableInstance){
							 
							 if(tempSingleTimesSeries.get(i).variableInstance.instanceName.equals(tabvariableModel.getValueAt(editedrow, 0)) && tempSingleTimesSeries.get(i).variableInstance.agentName.equals(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.agentName))
							 {
								 try{
								 tempSingleTimesSeries.get(i).lowerBound = Double.parseDouble(tabVariables.getColumnModel().getColumn(3).getCellEditor().getCellEditorValue().toString());
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

						 for(int i=0; i<tempSingleTimesSeries.size();i++){
				    	    	
						    	
							 if(!tempSingleTimesSeries.get(i).isVariableInstance){
								 
								 if(tempSingleTimesSeries.get(i).variableInstance.instanceName.equals(tabvariableModel.getValueAt(editedrow, 0)) && tempSingleTimesSeries.get(i).variableInstance.agentName.equals(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.agentName))
								 {
									 tempSingleTimesSeries.get(i).lowerBoundEnabled = true;
									 break;
									 
								 }
								 
								 
							 }
		  	  
						 }
						 
						 tabvariableModel.updatetable();
				
						 
						 
					 }else{
						 /*Set the check box*/
						 tabvariableModel.changeValueAt("false",((JCheckBoxCellEditor) tabVariables.getColumnModel().getColumn(4).getCellEditor()).getRow(),4 );

						 for(int i=0; i<tempSingleTimesSeries.size();i++){
				    	    	
						    	
							 if(!tempSingleTimesSeries.get(i).isVariableInstance){
								 
								 if(tempSingleTimesSeries.get(i).variableInstance.instanceName.equals(tabvariableModel.getValueAt(editedrow, 0)) && tempSingleTimesSeries.get(i).variableInstance.agentName.equals(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.agentName))
								 {
									 tempSingleTimesSeries.get(i).lowerBoundEnabled = false;
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
		    
					 for(int i=0; i<tempSingleTimesSeries.size();i++){
			    	    	
					    	
						 if(!tempSingleTimesSeries.get(i).isVariableInstance){
							 
							 if(tempSingleTimesSeries.get(i).variableInstance.instanceName.equals(tabvariableModel.getValueAt(editedrow, 0)) && tempSingleTimesSeries.get(i).variableInstance.agentName.equals(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.agentName))
							 {
								 try{
								 tempSingleTimesSeries.get(i).upperBound = Double.parseDouble(tabVariables.getColumnModel().getColumn(5).getCellEditor().getCellEditorValue().toString());
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

						 for(int i=0; i<tempSingleTimesSeries.size();i++){
				    	    	
						    	
							 if(!tempSingleTimesSeries.get(i).isVariableInstance){
								 
								 if(tempSingleTimesSeries.get(i).variableInstance.instanceName.equals(tabvariableModel.getValueAt(editedrow, 0)) && tempSingleTimesSeries.get(i).variableInstance.agentName.equals(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.agentName))
								 {
									 tempSingleTimesSeries.get(i).upperBoundEnabled = true;
									 break;
									 
								 }
								 
								 
							 }
		  	  
						 }
						 
						 tabvariableModel.updatetable();
				
						 
						 
					 }else{
						 /*Set the check box*/
						 tabvariableModel.changeValueAt("false",((JCheckBoxCellEditor) tabVariables.getColumnModel().getColumn(6).getCellEditor()).getRow(),6 );

						 for(int i=0; i<tempSingleTimesSeries.size();i++){
				    	    	
						    	
							 if(!tempSingleTimesSeries.get(i).isVariableInstance){
								 
								 if(tempSingleTimesSeries.get(i).variableInstance.instanceName.equals(tabvariableModel.getValueAt(editedrow, 0)) && tempSingleTimesSeries.get(i).variableInstance.agentName.equals(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.agentName))
								 {
									 tempSingleTimesSeries.get(i).upperBoundEnabled = false;
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

		
		for(int i=0; i<tempSingleTimesSeries.size();i++){
	    	
	    	
			 if(!tempSingleTimesSeries.get(i).isVariableInstance){
				 
			
				 selectedRatioList.add(new RatioTimeSeriesPlotting(tempSingleTimesSeries.get(i).instanceName, 
						 tempSingleTimesSeries.get(i).tmin,tempSingleTimesSeries.get(i).tmax,
						 tempSingleTimesSeries.get(i).lowerBound,tempSingleTimesSeries.get(i).upperBound,
						 tempSingleTimesSeries.get(i).lowerBoundEnabled,tempSingleTimesSeries.get(i).upperBoundEnabled));
				 
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
   return 7;
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
	   }else{
		   return null;
	   }
		   
	   
	   
	   
   }
   
   public String getColumnName(int column) {  
       return columnName[column]; 
   }
   
   public Class<?> getColumnClass(int col) {  
	     if (col == 4  || col==6) {  
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
	   
	   RatioTimeSeriesPlotting(String name, int tmi, int tma, double loBo, double upBo, boolean loBoEnabled, boolean upBoEnabled ){
		   
		   instanceName = name;
		   tmin = tmi;
		   tmax = tma;
		   lowerBound = loBo;
		   upperBound = upBo;
			lowerBoundEnabled = loBoEnabled;
			upperBoundEnabled = upBoEnabled;
		   
	   }
	   


   }

   }		
	
}


