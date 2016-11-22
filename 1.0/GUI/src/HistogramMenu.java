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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
import javax.swing.table.TableCellRenderer;

public class HistogramMenu extends JDialog{
	
	private JList notChosenVariableList;
	private DefaultListModel dlmParameter; 
	private DefaultListModel dlm2Parameter ;
	private JList chosenVariableList ;
	private JButton  saveVariables, cancelVariables ;
	

	private JDialog enterValue;
	
	/*Variables for menu bar:*/
	private JMenuBar menuBar;
	
	private JMenu menuPlotting;
	private JMenuItem addAgent, removeAgent, applyChanges, exit;
	
	
	private JMenu menuVariables;
	private JMenu addRemoveAgentRatio;
	private JMenuItem addRemoveVariables, addAgentRatio, removeAgentRatio ;
	
	private JMenu menuSettings;
	private JMenuItem editFilter,  defaultSettings, iterations ;
	
	private JList agentList;
	private JList removeAgentList;
	private JDialog addAgentDialog , removeAgentDialog;

	private	JTabbedPane tabbedPane;
	private	ArrayList<AgentTab> agentJPanelList;
	private JDialog setNewVariables;
	


	private ArrayList<PlottingSettings.Histogram> tempHistogrammList;
	private ArrayList<PlottingSettings.Agent> tempAgentList;
	private ArrayList<Agent> tempAgentSettingList;
	
	
	HistogramMenu(){
		
		
		tempHistogrammList = new ArrayList<PlottingSettings.Histogram>();
		
		for(int i= 0; i< PlottingSettings.listOfHistograms.size();i++){
			
			tempHistogrammList.add(PlottingSettings.listOfHistograms.get(i).clone());
			
		}
		
		tempAgentList = new ArrayList<PlottingSettings.Agent>();
		
		for(int i= 0; i< PlottingSettings.listOfAgentsVariableInstances.size();i++){
			
			tempAgentList.add(AuxFunctions.DeepCopyAgentVariableInstance(PlottingSettings.listOfAgentsVariableInstances.get(i)));
			
		}
		
		tempAgentSettingList = AuxFunctions.deepCopyAgentSettingsList(AgentSettings.agents);
		
		/*Dialog settings*/	
	    setTitle("Histograms");
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

	    			PlottingSettings.listOfHistograms = tempHistogrammList;
	    			PlottingSettings.listOfAgentsVariableInstances = tempAgentList;
	    			AgentSettings.agents =tempAgentSettingList;
	    			
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
				addAgentDialog.setSize(300,300);
				addAgentDialog.setLayout(new GridBagLayout());
				
				GridBagConstraints fA = new GridBagConstraints();
				fA.insets = new Insets( 5, 5, 5, 5);
				
				JLabel label1 = new JLabel("Select Agent for Plotting:");
				fA.gridx =0;fA.gridy =0;
				addAgentDialog.add(label1,fA);
				
				DefaultListModel dlmAgents = new DefaultListModel();
				
				agentList = new JList(dlmAgents);
				
				for(int i=0; i<tempAgentList.size();i++){
					
					if(!tempAgentList.get(i).histogrammSelected){
						
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
									tempAgentList.get(i).histogrammSelected = true;
									
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
					
					if(tempAgentList.get(i).histogrammSelected){
						
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
											
											tempAgentList.get(j).histogrammSelected = false;
											break;
										}
									}

									agentJPanelList.remove(i);
									tabbedPane.remove(i);
			
									removeAgentDialog.dispose();
									removeAgentDialog.setVisible(false);
									
					
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

				PlottingSettings.listOfHistograms = tempHistogrammList;
    			PlottingSettings.listOfAgentsVariableInstances = tempAgentList;
    			AgentSettings.agents =tempAgentSettingList;
				
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
	    			PlottingSettings.listOfHistograms = tempHistogrammList;
	    			PlottingSettings.listOfAgentsVariableInstances = tempAgentList;
	    			AgentSettings.agents =tempAgentSettingList;
	    			
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
		
		
		menuVariables = new JMenu("Histogram");
		
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
	   		        			
	   		        			if(!tempAgentSettingList.get(i).variableList.get(j).isSelectedForHistograms){
	   		        				
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
		    					
		    					for(int j=0; j<tempHistogrammList.size();j++){
		    						
		    						if(tempHistogrammList.get(j).variable.name.equals(dlmParameter.get(i).toString())){
		    							
		    							tempHistogrammList.remove(j);
										j--;	
										
										
										for(int k=0; k <tempAgentSettingList.size();k++ ){
				    						
			    							if(tempAgentSettingList.get(k).agentName.equals(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.agentName)){
			    								
			    								for(int l=0; l<tempAgentSettingList.get(k).variableList.size();l++){
			    									
			    									if(tempAgentSettingList.get(k).variableList.get(l).name.equals(dlmParameter.get(i).toString())){
			    										
			    										tempAgentSettingList.get(k).variableList.get(l).isSelectedForHistograms = false;
			    										
			    									}
			    									
			    									
			    								}	
											
										}
										
										
		    						}
			
		    					}
		    	    					
		    				}	
			    			}
		    				
		    				
		    				for(int i=0; i<dlm2Parameter.getSize(); i++ ){ 
		    					
		    					boolean found = false;
		    					
		    					for(int j=0; j<tempHistogrammList.size();j++){
		    						
		    						if(tempHistogrammList.get(j).variable.name.equals(dlm2Parameter.get(i).toString())){
		    							
		    							found = true;
		    							break;
		    						}
			
		    					}
		    					
		    					if(!found){
		    						
		    						
		    						
		    						for(int j=0; j <tempAgentSettingList.size();j++ ){
		    						
		    							if(tempAgentSettingList.get(j).agentName.equals(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.agentName)){
		    								
		    								for(int k=0; k<tempAgentSettingList.get(j).variableList.size();k++){
		    									
		    									if(tempAgentSettingList.get(j).variableList.get(k).name.equals(dlm2Parameter.get(i).toString())){
		    										
		    										tempAgentSettingList.get(j).variableList.get(k).isSelectedForHistograms = true;
		    										tempHistogrammList.add((new PlottingSettings()).new Histogram(tempAgentSettingList.get(j).variableList.get(k).name,tempAgentSettingList.get(j).variableList.get(k),tempAgentSettingList.get(j).agentName,PlottingSettings.defaultsHistogram.Filter1,PlottingSettings.defaultsHistogram.Filter2 ));
		    										
		    										break;
		    									}
		    									
		    									
		    								}
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
	    					
	    					for(int j=0; j<tempHistogrammList.size();j++){
	    						
	    						if(tempHistogrammList.get(j).variable.name.equals(dlmParameter.get(i).toString())){
	    							
	    							tempHistogrammList.remove(j);
									j--;	
									
									
									for(int k=0; k <tempAgentSettingList.size();k++ ){
			    						
		    							if(tempAgentSettingList.get(k).agentName.equals(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.agentName)){
		    								
		    								for(int l=0; l<tempAgentSettingList.get(k).variableList.size();l++){
		    									
		    									if(tempAgentSettingList.get(k).variableList.get(l).name.equals(dlmParameter.get(i).toString())){
		    										
		    										tempAgentSettingList.get(k).variableList.get(l).isSelectedForHistograms = false;
		    										
		    									}
		    									
		    									
		    								}	
										
									}
									
									
	    						}
		
	    					}
	    	    					
	    				}	
		    			}
	    				
	    				
	    				for(int i=0; i<dlm2Parameter.getSize(); i++ ){ 
	    					
	    					boolean found = false;
	    					
	    					for(int j=0; j<tempHistogrammList.size();j++){
	    						
	    						if(tempHistogrammList.get(j).variable.name.equals(dlm2Parameter.get(i).toString())){
	    							
	    							found = true;
	    							break;
	    						}
		
	    					}
	    					
	    					if(!found){
	    						
	    						
	    						
	    						for(int j=0; j <tempAgentSettingList.size();j++ ){
	    						
	    							if(tempAgentSettingList.get(j).agentName.equals(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.agentName)){
	    								
	    								for(int k=0; k<tempAgentSettingList.get(j).variableList.size();k++){
	    									
	    									if(tempAgentSettingList.get(j).variableList.get(k).name.equals(dlm2Parameter.get(i).toString())){
	    										
	    										tempAgentSettingList.get(j).variableList.get(k).isSelectedForHistograms = true;
	    										tempHistogrammList.add((new PlottingSettings()).new Histogram(tempAgentSettingList.get(j).variableList.get(k).name,tempAgentSettingList.get(j).variableList.get(k),tempAgentSettingList.get(j).agentName,PlottingSettings.defaultsHistogram.Filter1,PlottingSettings.defaultsHistogram.Filter2  ));
	    										
	    										break;
	    									}
	    									
	    									
	    								}
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
			
			SortedListModel sortedModel1, sortedModel2;
		
			String tempNumerator;
			String tempDenominator;
			
			JScrollPane scrollFractionTable;

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
		   			
		   			variableList1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		   			
		   	
		   			
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
									
									tempHistogrammList.add((new PlottingSettings()).new Histogram(tempAgentRatio.ratioName,tempAgentRatio,agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.agentName,PlottingSettings.defaultsHistogram.Filter1,PlottingSettings.defaultsHistogram.Filter2 ));
											
											agentJPanelList.get(tabbedPane.getSelectedIndex()).reDrawTable();
			
					    	    			
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
	   				
   				for(int i=0; i<tempHistogrammList.size();i++){
					
   					if(!tempHistogrammList.get(i).isVariable)
   						ratioListModel.addElement(tempHistogrammList.get(i).agentRatio.ratioName);
		
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
							
							for(int j=0; j<tempHistogrammList.size();j++){
								
			   					if(tempHistogrammList.get(j).agentRatio.ratioName.equals(ratioListModel.get(selectedIndices[i]))){
			   						ratioListModel.addElement(tempHistogrammList.get(j).agentRatio.ratioName);
			   						tempHistogrammList.remove(j);
		
			   						break;
			   					}
							}
							
							
						
						

					}
						
						
						ratioRemoveDialog.dispose();
						ratioRemoveDialog.setVisible(false);
						
						agentJPanelList.get(tabbedPane.getSelectedIndex()).reDrawTable();
   					
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
				
				

	
		iterations = new JMenuItem("Iterations"); 
		menuSettings.add(iterations);
		
	
		   			
		iterations.addActionListener(new ActionListener(){
		   				
		   				JTextField  iterationValue;
		   		
		
			   			private DefaultListModel dlmAddPartition;
			   			private JList addIteration;
			   			SortedListModel sortedModel;

						public void actionPerformed(ActionEvent arg0) {
					
							enterValue = new JDialog();
							enterValue.setTitle("Set Iterations for Histograms");
							enterValue.setLayout(new FlowLayout());
							enterValue.setSize(500,250);
							enterValue.setBackground(Color.white);
							enterValue.setModal(true);
							
							enterValue.setLayout(new GridBagLayout());
				   			final GridBagConstraints enterV= new GridBagConstraints();
				   			enterV.insets = new Insets( 5, 5, 5, 5);
				   			

				 
				   			
				   			dlmAddPartition = new DefaultListModel();
				   			addIteration = new JList(dlmAddPartition);
				   			
				
					   		sortedModel = new SortedListModel(dlmAddPartition,SortedListModel.SortOrder.DESCENDING,  (new AuxFunctions()).new MyStringAsIntegerComparable());
					   		addIteration.setModel(sortedModel);
				   			
				   	
				   			for(int i=0; i< PlottingSettings.defaultsHistogram.iterations.size();i++){
				 
				   				dlmAddPartition.addElement(Integer.parseInt(PlottingSettings.defaultsHistogram.iterations.get(i).iteration));
				   			}
				   			
				   			
				   			addIteration.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
				   			addIteration.setVisibleRowCount(10);
				   			
				   			addIteration.setVisibleRowCount(10);
				   			
				
							JLabel text1 = new JLabel("Enter Iteration Numbers:");
							enterV.gridx=0;enterV.gridy=1;
							enterValue.add(text1,enterV);
							
							iterationValue = new JTextField(4);
							enterV.gridx=1;enterV.gridy=1;
							enterValue.add(iterationValue,enterV);
						    /*Add ActionListener*/
							iterationValue.addActionListener(new ActionListener(){
						    		
						    		public void actionPerformed(ActionEvent evt) {
						    			
						    			try{
						    				
						    				System.out.println(iterationValue.getText());
						    				Integer.parseInt(iterationValue.getText());
							    			dlmAddPartition.addElement(Integer.parseInt(iterationValue.getText().toString()));
							    			iterationValue.setText("");
							    			
						    			}catch(Exception e){
						    				
						    				JOptionPane.showMessageDialog(null, "Not an integer!");
						    				iterationValue.setText("");
						    				
						    			}
						    			
						    		}
						    		
						    	});
							
							
							
							JScrollPane listScrolladdPartition = new JScrollPane(addIteration); 
							listScrolladdPartition.setPreferredSize(new Dimension(50, 100)); 
							
							enterV.gridx=2;enterV.gridy=1;
				   			enterValue.add(listScrolladdPartition,enterV);
				   			
				   			JButton removeButton = new JButton("Remove");
				   			
				   			enterV.gridx=3;enterV.gridy=1;
				   			enterValue.add(removeButton,enterV);
				   			
				   			removeButton.addActionListener(new ActionListener(){

								
								public void actionPerformed(ActionEvent arg0) {
					
									dlmAddPartition.remove(sortedModel.toUnsortedModelIndex(addIteration.getSelectedIndex()));
					
								}
				   				
				
				   			});
				   			
				   			JButton applyButton = new JButton("Apply");
				   			
				   			enterV.gridx=3;enterV.gridy=2;
				   			enterValue.add(applyButton,enterV);
				   			
				   			
				   			applyButton.addActionListener(new ActionListener(){

								public void actionPerformed(ActionEvent arg0) {
									
									for(int i=0; i<PlottingSettings.defaultsHistogram.iterations.size();i++){
										PlottingSettings.defaultsHistogram.iterations.remove(i);
										i--;
									}
									
									for(int i=0; i< dlmAddPartition.size();i++){
										 
										PlottingSettings.defaultsHistogram.iterations.add((new PlottingSettings()).new Iteration(dlmAddPartition.getElementAt(i).toString()));
						   				
						   			}
									
									enterValue.setVisible(false);
									enterValue.dispose();
								
								}
								
								});
				   			

							enterValue.setVisible(true);
							
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
			
				
			    	String [] filter ; 
					try{
					
						filter = new String [agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.filter.size()+1];
					}catch(Exception e){
					
						
						filter = new String [agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.filter.size()+1];
					}
					filter[0] = "No Filter";
					
					for(int i= 1; i<agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.filter.size()+1; i++){
						
						filter[i] = agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.filter.get(i-1).variableName;
						
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
					
			    	
			    	JButton ok = new JButton("OK");
			    	
			    	enterDS.gridx=4;
			    	enterDS.gridy=4;
			    	defaultSettingsDialog.add(ok,enterDS);
			    	
			    	ok.addActionListener(new ActionListener(){

						public void actionPerformed(ActionEvent e) {
			
							agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.defaultSettings.defaultFilter1 = filter1CB.getSelectedItem().toString();
							agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.defaultSettings.defaultFilter2 = filter2CB.getSelectedItem().toString();

			    			defaultSettingsDialog.setVisible(true);
			    			defaultSettingsDialog.dispose();
							
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
		
		for(int i=0; i<tempAgentList.size();i++){
			
			// Create the tab pages for each agent that is selected for plotting
			if(tempAgentList.get(i).histogrammSelected)
			{
				agentJPanelList.add(new AgentTab(tempAgentList.get(i)));
				tabbedPane.addTab(tempAgentList.get(i).agentName ,agentJPanelList.get(agentJPanelList.size()-1));
			}
			
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
	
	
	
	

	
	
	class AgentTab extends  JPanel
	{
		PlottingSettings.Agent agent;
		GridBagConstraints agentPane = new GridBagConstraints();
		
		private TabModel tabvariableModel;
		private JTable tabVariables;
		
		private JButton addInstance;
		private JButton removeInstance;
		private JScrollPane scrollVariableTable;
		
		AgentTab (PlottingSettings.Agent agentAtTab){
			
			agent = agentAtTab;

	
			setSize(700, 200);
			setLayout(new GridBagLayout());
			
			
			agentPane.insets = new Insets( 5, 5, 5, 5);
			
			JLabel lab = new JLabel("Histograms for Agent "+agent.agentName);
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
			
			addInstance = new JButton("Add new Histogramm of Variable");
			removeInstance = new JButton("Remove Histogramm of Variable");
			
			addInstance.setEnabled(false);
			removeInstance.setEnabled(false);
			
			lowerPa.gridx =0;lowerPa.gridy =0;
			lowerPanel.add(addInstance,lowerPa);
			
			lowerPa.gridx =1;lowerPa.gridy =0;
			lowerPanel.add(removeInstance,lowerPa);
			
			addInstance.addActionListener(new ActionListener(){

				public void actionPerformed(ActionEvent arg0) {
					
			
					addInstance.setEnabled(false);
					
					
					int counter =0;
					
					String name ="";
					
					int indexOfLastOccurence =0;
					
					for(int i=0; i<tempHistogrammList.size();i++ ){
						
						if(tempHistogrammList.get(i).isVariable && tempHistogrammList.get(i).variable.name.equals(tabvariableModel.getValueAt(tabVariables.getSelectedRow(), 0).toString())){
							
							counter++;
							name = tempHistogrammList.get(i).getName();
							indexOfLastOccurence=i;
							
						}else if(!tempHistogrammList.get(i).isVariable && tempHistogrammList.get(i).agentRatio.ratioName.equals(tabvariableModel.getValueAt(tabVariables.getSelectedRow(), 0).toString())){
							
							counter++;
							name = tempHistogrammList.get(i).getName();
							indexOfLastOccurence=i;
							
						}
						
						
					}
							
					 name = name+"_"+counter;
					 
					 
					 for(int i=0; i < tempHistogrammList.size();i++){
		   		        	
		   		        			
		   		        			if( tempHistogrammList.get(i).name.equals(tabvariableModel.getValueAt(tabVariables.getSelectedRow(), 1).toString())){
		   		        				
		   		        			
		   		        				PlottingSettings.Histogram hist = tempHistogrammList.get(i).clone();
		   		        				
		   		        				hist.Filter1 = PlottingSettings.defaultsHistogram.Filter1;
		   		        				hist.Filter2 = PlottingSettings.defaultsHistogram.Filter2;
		   		       
		   		        				
		   		        				tempHistogrammList.add(indexOfLastOccurence+1,hist);
		   		        				
		   		        				
		   		        				break;
		   		        			}
		   		        			
		   		        	
		   		        }
					
					
					
					
				
						
					
					 
		
			
					 reDrawTable();	 
							 
							
					 
					
				}

			});
			
			
			
			removeInstance.addActionListener(new ActionListener(){

				public void actionPerformed(ActionEvent arg0) {
					
					
					System.out.println("tabVariables.getSelectedRow() "+tabVariables.getSelectedRow());
					int choice = 0;
				
					
					if(choice==0){
						
						
					
						
						int counter =0;
						String varName = tabvariableModel.getValueAt(tabVariables.getSelectedRow(), 0).toString();
						
						for(int i=0; i< tempHistogrammList.size();i++){
							
							if(tempHistogrammList.get(i).name.equals(tabvariableModel.getValueAt(tabVariables.getSelectedRow(), 1).toString())){
							
								tempHistogrammList.remove(i);
								
				
							}else if(tempHistogrammList.get(i).variable.name.equals(varName)){
								counter++;
							}
							
						}
						
						 /*If last item set false*/
						 if( counter==0 ){
							 
							 for(int j=0; j < tempAgentSettingList.size();j++){
				   		        	
				   		        	if(tempAgentSettingList.get(j).agentName.equals(agent.agentName)){
				   		        		
				   		        		for(int k=0; k< tempAgentSettingList.get(j).variableList.size();k++){
						
				   		        			if(tempAgentSettingList.get(j).variableList.get(k).name.equals(varName)) {
									
				   		        				tempAgentSettingList.get(j).variableList.get(k).isSelectedForHistograms = false;
				   		        				break;
				   		        			}
				   		        		}
				   		        		break;
				   		        	}
						
						
							 }
					
						 }

					 
					 reDrawTable();
			
					 addInstance.setEnabled(false);
					 removeInstance.setEnabled(false);
			
					}	 
							 
							
					 
					
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
				
	
				
				String[] colHeadersT2 = {"Variable" ,"Name", "Filter 1", "Filter 2"};
				
				int agentIndex = 0;
				
				for(int i=0; i<tempAgentList.size();i++ ){
					
					if(tempAgentList.get(i).agentName.equals(agent.agentName)){
						
						agentIndex=i;
						break;
					}	
				}
				
				
				tabvariableModel = new TabModel(colHeadersT2 ,agent.agentName );
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
						
								 for(int i=0; i< tempHistogrammList.size();i++){
									 
									 if(tempHistogrammList.get(i).name.equals(tabvariableModel.getValueAt(editedrow, 1))){
										 
										 tempHistogrammList.get(i).name = tabVariables.getColumnModel().getColumn(1).getCellEditor().getCellEditorValue().toString();
										 tabvariableModel.changeValueAt(tabVariables.getColumnModel().getColumn(1).getCellEditor().getCellEditorValue().toString(), editedrow, 1);
										 break;
									 }
									 
								 }
							
						
					 }
						 
			
					 
				});
				

				
			
				/*Column 4: Filter 1*/
				
				tabVariables.getColumnModel().getColumn(2).setPreferredWidth(140);
				
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
				
				tabVariables.getColumnModel().getColumn(2).setCellEditor(new SpecificComboBoxEditor(filter));
				tabVariables.getColumnModel().getColumn(2).setCellRenderer(new SpecificComboBoxRenderer(filter));
				
				
				
				tabVariables.getColumnModel().getColumn(2).getCellEditor().addCellEditorListener(new EditorListener(){
					 
					 
					 public void editingStopped(ChangeEvent e) {
						 
						 System.out.println("Editing ended!");
						 
					 }
					 
					 
				});

				
				/*Column 5: Filter 2*/
				
				tabVariables.getColumnModel().getColumn(3).setPreferredWidth(140);
				
				tabVariables.getColumnModel().getColumn(3).setCellEditor(new SpecificComboBoxEditor(filter));
				tabVariables.getColumnModel().getColumn(3).setCellRenderer(new SpecificComboBoxRenderer(filter));
				
				
				
				tabVariables.getColumnModel().getColumn(3).getCellEditor().addCellEditorListener(new EditorListener(){
					 
					 
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
							 
							 
		
							 
							 for(int i=0; i< tempHistogrammList.size();i++){
								 
								 if(tempHistogrammList.get(i).name.equals(tabvariableModel.getValueAt(editedRow, 1))){
									 
									 /*Column 3 => method*/
									 if(editedCol==2){

										 tempHistogrammList.get(i).Filter1= cComboBox.getSelectedItem().toString();
									 }else if(editedCol==3){
										 /*Column 5 => Filter 2*/
										 tempHistogrammList.get(i).Filter2 = cComboBox.getSelectedItem().toString();
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
		
		
	
		


public class TabModel extends AbstractTableModel{
	

	JCheckBox checkBox = new JCheckBox();
	String [] columnName;
	
	
	
	
	
	int numFilters;
	boolean partitioningSelected , filterSelected;
	
	
	ArrayList<PlottingSettings.Histogram> histogramms = new ArrayList<PlottingSettings.Histogram>();
	
	
	TabModel(String[] colName, String agentName){

		
		for(int i =0; i < AgentSettings.agents.size();i++){
			
			if(agentName.equals(AgentSettings.agents.get(i).agentName)){
				
				for(int k=0; k<tempHistogrammList.size();k++){
				
					for(int j=0; j < AgentSettings.agents.get(i).variableList.size();j++){
					
						
						if(tempHistogrammList.get(k).isVariable && AgentSettings.agents.get(i).variableList.get(j).name.equals(tempHistogrammList.get(k).variable.name) && tempHistogrammList.get(k).histBelongsTo.equals(agentName)){
							
							
							histogramms.add(tempHistogrammList.get(k));
							
							
						}else if(!tempHistogrammList.get(k).isVariable && AgentSettings.agents.get(i).variableList.get(j).name.equals(tempHistogrammList.get(k).agentRatio.numerator.name) && tempHistogrammList.get(k).histBelongsTo.equals(agentName)){
							
							histogramms.add(tempHistogrammList.get(k));
							
						}
					
					}
					
				}
				
				
				
				break;
			}
			
			
		}
		
		System.out.println("histogramms"+histogramms.size()+"  tempHistogrammList.size()   "+tempHistogrammList.size());

		columnName = colName;
	
		
	}
	
	
	
	public int getRowCount()
   {
	
	return histogramms.size();
	
 }
	
	
   public int getColumnCount()
   {
   return 4;
 }
   public Object getValueAt( int row, int col)
   {
	   
	
	   
	  
		 //  System.out.println("getValueAt timeSeriesSelection.get(i).isntancesOfvariable.size()"+timeSeriesSelection.get(i).isntancesOfvariable.size());
		
				   if(col==0)
					   return histogramms.get(row).getName();
				   else if(col==1)
					   return histogramms.get(row).name;
				  
				   else if(col==2)
					   return histogramms.get(row).Filter1;
				   else if(col==3)
					   return histogramms.get(row).Filter2;
				  
				   else
					   return null;
			   
			
   }
	   
	   
	   
	   
	   
	   
   
   
   public String getColumnName(int column) {  
       return columnName[column]; 
   }
   
   
   public void changeValueAt(String value, int row, int col) {  
       
	   System.out.println("changeValueAt");

	   if(col==1)
		   histogramms.get(row).name=value;
	
	   if(col==2)
		   histogramms.get(row).Filter1=value;
	   if(col==3)
		   histogramms.get(row).Filter2=value;
	 
       this.fireTableDataChanged();  
         
         
   }  
   
   
   
 public void RemoveRowAt(int row) {  
       
	   
	 histogramms.remove(row);
	   	   
	   this.fireTableDataChanged();  
	   
        

}
   
   
   public void updatetable() {  
       
       this.fireTableDataChanged();    
         
   }  
   
   public boolean isCellEditable(int row, int column){ 
      
	   if(column==0){
   		return false;
   	}else {
   		
   		return true;
   	}
   }
   
   
	   
		
	
		
	
	

		
	
}


		

}


