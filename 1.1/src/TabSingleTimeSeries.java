
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

	import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
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
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;


public class TabSingleTimeSeries extends JPanel {
	
	



		
		
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

		private	JTabbedPane tabbedPane, plottingTabbedPane;
		private	ArrayList<AgentTab> agentJPanelList;

		private JDialog setNewVariables;
		

		private RatioTab ratioTab;

		
		
		JPanel lowerArea;
		
		JLabel singleRunAnalyisLabel, batchRunAnalyisLabel, parameterAnalyisLabel;
		JCheckBox singleRunAnalyisCheckBox, batchRunAnalyisCheckBox, parameterAnalyisCheckBox,makeLegend, colored;
		JTextField transitionPhase;
		JComboBox fileTypePlots;
		 
		//GridBagConstraints fA = new GridBagConstraints();
		
		TabSingleTimeSeries(){
			
			
		

			

		   // this.setSize(700, 1100);
		   // fA.insets = new Insets( 5, 5, 5, 5);
		    
		    menuBar = new JMenuBar();
		    
		    setLayout(new BorderLayout());
		    
		    

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
		   		        
		   		        for(int i=0; i < AgentSettings.agents.size();i++){
		   		        	
		   		        	if(AgentSettings.agents.get(i).agentName.equals(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.agentName)){
		   		        		
		   		        		for(int j=0; j< AgentSettings.agents.get(i).variableList.size();j++){
		   		        			
		   		        			if(AgentSettings.agents.get(i).variableList.get(j).name.equals(item)){
		   		        				
		   		        				tooltip = AgentSettings.agents.get(i).variableList.get(j).name +" ("
		   		   		        		+AgentSettings.agents.get(i).variableList.get(j).type+") "+
		   		   		        		AgentSettings.agents.get(i).variableList.get(j).description;
		   		       
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
		   	   		        
		   	   		        
		   	   		        
		   	   		  for(int i=0; i < AgentSettings.agents.size();i++){
		   		        	
		   		        	if(AgentSettings.agents.get(i).agentName.equals(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.agentName)){
		   		        		
		   		        		for(int j=0; j< AgentSettings.agents.get(i).variableList.size();j++){
		   		        			
		   		        			if(AgentSettings.agents.get(i).variableList.get(j).name.equals(item)){
		   		        				
		   		        				tooltip = AgentSettings.agents.get(i).variableList.get(j).name +" ("
		   		   		        		+AgentSettings.agents.get(i).variableList.get(j).type+") "+
		   		   		        		AgentSettings.agents.get(i).variableList.get(j).description;
		   		       
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
		   	   			
		   	   		 for(int i=0; i < AgentSettings.agents.size();i++){
		   		        	
		   		        	if(AgentSettings.agents.get(i).agentName.equals(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.agentName)){
		   		        		
		   		        		for(int j=0; j< AgentSettings.agents.get(i).variableList.size();j++){
		   		        			
		   		        			if(!AgentSettings.agents.get(i).variableList.get(j).isSelectedForPlotting){
		   		        				
		   		        				dlmParameter.addElement(AgentSettings.agents.get(i).variableList.get(j).name);
		   		       
		   		        			}else{
		   		        				
		   		        				dlm2Parameter.addElement(AgentSettings.agents.get(i).variableList.get(j).name);
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
			    	    					
			    	    					for(int j=0; j < AgentSettings.agents.size();j++){
			    	    	   		        	
			    	    	   		        	if(AgentSettings.agents.get(j).agentName.equals(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.agentName)){
			    	    	   		        		
			    	    	   		        		for(int k=0; k< AgentSettings.agents.get(j).variableList.size();k++){
			    	    	   		        			if(AgentSettings.agents.get(j).variableList.get(k).name.equals(dlmParameter.get(i).toString()))
			    	    	   		        			{
			    	    	   		        				AgentSettings.agents.get(j).variableList.get(k).isSelectedForPlotting= false;
			    	    	   		        				
			    	    	   		        				
			    	    	   		        				
			    	    	   		        				/*TODO Check if this time series is used for other time series (e.g. multiple time series)*/
			    	    	   		        				
			    	    	   		        				for(int l = 0; l<PlottingSettings.listOfSingleTimeSeries.size();l++ ){
			    	    	   		        					
			    	    	   		        					if(PlottingSettings.listOfSingleTimeSeries.get(l).variableInstance.variable.equals(AgentSettings.agents.get(j).variableList.get(k))){
			    	    	   		        						
			    	    	   		        						
			    	    	   		        						PlottingSettings.listOfSingleTimeSeries.remove(l);
			    	    	   		        						break;
			    	    	   		        						
			    	    	   		        						
			    	    	   		        					}
			    	    	   		        					
			    	    	   		        					
			    	    	   		        					
			    	    	   		        					
			    	    	   		        					
			    	    	   		        					
			    	    	   		        					
			    	    	   		        				}
			    	    	   		        				
			    	    	   		        				
			    	    	   		        				
			    	    	   		        				
			    	    	   		        				
			    	    	   		        				
			    	    	   		        				break;
			    	    	   		        			}
			    	    	   		        			
			    	    	   		        		}
			    	    	   		        		break;
			    	    	   		        	}
			    	    	   		        }
			    	    					
			    	    				
											
												
												
											}
			    	    				
			    	    
			
			    	    			for(int i=0; i<dlm2Parameter.getSize(); i++ ){   
		
				    	    			  for(int j=0; j < AgentSettings.agents.size();j++){
			    	    	   		        	
			    	    	   		        	if(AgentSettings.agents.get(j).agentName.equals(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.agentName)){
			    	    	   		        		
			    	    	   		        		for(int k=0; k< AgentSettings.agents.get(j).variableList.size();k++){
			    	    	   		        			
			    	    	   		        			if(AgentSettings.agents.get(j).variableList.get(k).name.equals(dlm2Parameter.get(i).toString()))
			    	    	   		        			{
			    	    	   		        			
				    	    	   		        			AgentSettings.agents.get(j).variableList.get(k).isSelectedForPlotting= true;
				    	    	   		        			
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
							    											AgentSettings.agents.get(j).variableList.get(k),
							    											agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.defaultSettings.defaultMethod,
							    											agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.defaultSettings.defaultFilter1,
							    											agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.defaultSettings.defaultFilter2,
							    											agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.defaultSettings.defaultPartitioning,
							    											"No");
							    									
							    										agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.listOfVariableInstances.add(aR);
							    										
							    										
							    										/*add single time series*/
							    										
							    										PlottingSettings.listOfSingleTimeSeries.add((new PlottingSettings()).new SingleTimeSeries(aR));
							    										
							    				
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
		    					
		    					for(int j=0; j < AgentSettings.agents.size();j++){
		    	   		        	
		    	   		        	if(AgentSettings.agents.get(j).agentName.equals(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.agentName)){
		    	   		        		
		    	   		        		for(int k=0; k< AgentSettings.agents.get(j).variableList.size();k++){
		    	   		        			if(AgentSettings.agents.get(j).variableList.get(k).name.equals(dlmParameter.get(i).toString()))
		    	   		        			{
		    	   		        				AgentSettings.agents.get(j).variableList.get(k).isSelectedForPlotting= false;
		    	   		        				break;
		    	   		        			}
		    	   		        			
		    	   		        		}
		    	   		        		break;
		    	   		        	}
		    	   		        }
		    					
		    				
								
									
									
								}
		    				
		    

		    			for(int i=0; i<dlm2Parameter.getSize(); i++ ){   

	    	    			  for(int j=0; j < AgentSettings.agents.size();j++){
		    	   		        	
		    	   		        	if(AgentSettings.agents.get(j).agentName.equals(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.agentName)){
		    	   		        		
		    	   		        		for(int k=0; k< AgentSettings.agents.get(j).variableList.size();k++){
		    	   		        			
		    	   		        			if(AgentSettings.agents.get(j).variableList.get(k).name.equals(dlm2Parameter.get(i).toString()))
		    	   		        			{
		    	   		        			
	    	    	   		        			AgentSettings.agents.get(j).variableList.get(k).isSelectedForPlotting= true;
	    	    	   		        			
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
				    											AgentSettings.agents.get(j).variableList.get(k),
				    											agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.defaultSettings.defaultMethod,
				    											agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.defaultSettings.defaultFilter1,
				    											agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.defaultSettings.defaultFilter2,
				    											agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.defaultSettings.defaultPartitioning,
				    											"No");
				    									
				    										agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.listOfVariableInstances.add(aR);
				    										
				    										
				    										
				    										PlottingSettings.listOfSingleTimeSeries.add((new PlottingSettings()).new SingleTimeSeries(aR));
				    										
				    										
				    				
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
			
			
			
	
			
		

			
			menuBar.add(menuVariables);
			
			
			
			menuSettings = new JMenu("Settings");
			
			editFilter = new JMenuItem("Filter"); 
			menuSettings.add(editFilter);
			editFilter.addActionListener( new ActionListener(){

				//boolean tempApplyFilters = agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.plottingSelections.filters.applyFilters;
				public void actionPerformed(ActionEvent arg0) {
		
					
					new EditFilters(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.agentName);
				
					agentJPanelList.get(tabbedPane.getSelectedIndex()).reDrawTable();

					
				}

			});
					
					
		    
			defaultSettings = new JMenuItem("Default Settings");
			menuSettings.add(defaultSettings);
			defaultSettings.addActionListener( new ActionListener(){


					JComboBox methodCB, filter1CB, filter2CB, partitioningCB;
					JDialog defaultSettingsDialog;
					JTextField textfieldTMin, textfieldTMax;

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
								String [] lM = new String[7+agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.weighting.size()];
								
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
						
						
							
							

					    	
					    	JLabel labelTMin = new JLabel("Tmin");
					    	textfieldTMin = new JTextField(5);
				   			textfieldTMin.setText(Integer.toString(PlottingSettings.defaultsSingleTimeSeries.tmin));
				   			
				   			enterDS.gridx=0; enterDS.gridy=3;
				   			defaultSettingsDialog.add(labelTMin,enterDS);
				   			
				   			enterDS.gridx=1; enterDS.gridy=3;
				   			defaultSettingsDialog.add(textfieldTMin,enterDS);
				   			
				   			
				   		 /*Add ActionListener*/
				   			textfieldTMin.addActionListener(new ActionListener(){
				  	    		
				  	    		public void actionPerformed(ActionEvent evt) {
				  	    		    
				  	    			String input = textfieldTMin.getText();

				  	    			try {
				  	    				int integer = Integer.parseInt(input);
					    			    System.out.println("NumBatch runs: "+SimulationSettings.numBatchRuns);
					    			    textfieldTMin.selectAll();
				  	    			}
				  	    			catch(NumberFormatException nFE) {
				  	    				JOptionPane.showMessageDialog(null,"Not an integer"); 
				  	    			}
				  	    			
				  	    		}
				  	    		
				  	    	});
				   			
				   			
				   			
				   			JLabel labelTMax = new JLabel("Tmax");
				   			textfieldTMax = new JTextField(5);
				   			textfieldTMax.setText(Integer.toString(PlottingSettings.defaultsSingleTimeSeries.tmax));
				   			
				   			enterDS.gridx=0; enterDS.gridy=4;
				   			defaultSettingsDialog.add(labelTMax,enterDS);
				   			
				   			enterDS.gridx=1; enterDS.gridy=4;
				   			defaultSettingsDialog.add(textfieldTMax,enterDS);
				   			
				   			
				   		 /*Add ActionListener*/
				   			textfieldTMax.addActionListener(new ActionListener(){
				  	    		
				  	    		public void actionPerformed(ActionEvent evt) {
				  	    		    
				  	    			String input = textfieldTMax.getText();
				  	    		
				  	    			try {
				  	    				int integer = Integer.parseInt(input);
					    			    System.out.println("NumBatch runs: "+SimulationSettings.numBatchRuns);
					    			    textfieldTMax.selectAll();
				  	    			}
				  	    			catch(NumberFormatException nFE) {
				  	    				JOptionPane.showMessageDialog(null,"Not an integer"); 
				  	    			}
				  	    			
				  	    		}
				  	    		
				  	    	});
							
							
							
							
							
							
							
				    	
				    	JButton ok = new JButton("OK");
				    	
				    	enterDS.gridx=4;
				    	enterDS.gridy=5;
				    	defaultSettingsDialog.add(ok,enterDS);
				    	
				    	ok.addActionListener(new ActionListener(){

							public void actionPerformed(ActionEvent e) {
								
								System.out.println("methodCB.getSelectedItem().toString()  "+methodCB.getSelectedItem().toString());
								
								agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.defaultSettings.defaultMethod = methodCB.getSelectedItem().toString();
								agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.defaultSettings.defaultFilter1 = filter1CB.getSelectedItem().toString();
								agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.defaultSettings.defaultFilter2 = filter2CB.getSelectedItem().toString();
								
								
								PlottingSettings.defaultsSingleTimeSeries.tmin = Integer.parseInt(textfieldTMin.getText());
								PlottingSettings.defaultsSingleTimeSeries.tmax = Integer.parseInt(textfieldTMax.getText());
								
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
		   		        
		   		     for(int i=0; i < AgentSettings.agents.size();i++){
		   		        	
		   		        	if(AgentSettings.agents.get(i).agentName.equals(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.agentName)){
		   		        		
		   		        		for(int j=0; j< AgentSettings.agents.get(i).variableList.size();j++){
		   		        			
		   		        			if(AgentSettings.agents.get(i).variableList.get(j).name.equals(item)){
		   		        				
		   		        				tooltip = AgentSettings.agents.get(i).variableList.get(j).name +" ("
		   		   		        		+AgentSettings.agents.get(i).variableList.get(j).type+") "+
		   		   		        		AgentSettings.agents.get(i).variableList.get(j).description;
		   		       
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
		   		        
			   		     for(int i=0; i < AgentSettings.agents.size();i++){
			   		        	
			   		        	if(AgentSettings.agents.get(i).agentName.equals(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.agentName)){
			   		        		
			   		        		for(int j=0; j< AgentSettings.agents.get(i).variableList.size();j++){
			   		        			
			   		        			if(AgentSettings.agents.get(i).variableList.get(j).name.equals(item)){
			   		        				
			   		        				tooltip = AgentSettings.agents.get(i).variableList.get(j).name +" ("
			   		   		        		+AgentSettings.agents.get(i).variableList.get(j).type+") "+
			   		   		        		AgentSettings.agents.get(i).variableList.get(j).description;
			   		       
			   		        			}
			   		        		}
			   		        	}
			   		        }

		   		        // Return the tool tip text
		   		        return tooltip;
		   		    }
		   			};
		   			
		   			
		   			for(int i=0; i < AgentSettings.agents.size();i++){
			        	
			        	if(AgentSettings.agents.get(i).agentName.equals(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.agentName)){
			        		
			        		for(int j=0; j< AgentSettings.agents.get(i).variableList.size();j++){
			        			
			        			
			        				
			        				boolean found = false;
			        				
			        				for(int k=0; k<tempWeighting.size();k++)
					   				{
						   				if(AgentSettings.agents.get(i).variableList.get(j).name.equals(tempWeighting.get(k).weightingVariable)){
						   				
						   					dlm2Weighting.addElement(tempWeighting.get(k).weightingVariable);
						   					found = true;
						   					break;
						   				
						   				}	
						   				
					   				}
					   				if(!found)
					   					dlmWeighting.addElement(AgentSettings.agents.get(i).variableList.get(j).name);
			        				
			        			
			        			
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
		    
			add(menuBar, BorderLayout.NORTH);
			
	


			// Create a tabbed pane
			tabbedPane = new JTabbedPane();
			agentJPanelList = new ArrayList<AgentTab> ();
			
			for(int i=0; i<PlottingSettings.listOfAgentsVariableInstances.size();i++){
				
				// Create the tab pages for each agent that is selected for plotting
				//if(tempAgentList.get(i).isSelected)
				{
					agentJPanelList.add(new AgentTab(PlottingSettings.listOfAgentsVariableInstances.get(i)));
					tabbedPane.addTab(PlottingSettings.listOfAgentsVariableInstances.get(i).agentName ,agentJPanelList.get(agentJPanelList.size()-1));
				}
				
			}
			
			
			if(PlottingSettings.listOfRatioInstances.size()>0){
				
				
				ratioTab = new RatioTab(PlottingSettings.listOfRatioInstances);
				
				tabbedPane.addTab("Ratios" , ratioTab);
				
			}
			
			
			/*Check if certain menu items have to be disabled*/
			
			
			/*if(agentJPanelList.size()== tempAgentList.size() && agentJPanelList.size()!=0){
				addAgent.setEnabled(false);
			}else if(agentJPanelList.size()==0){
				
				removeAgent.setEnabled(false);
				menuSettings.setEnabled(false);
				menuVariables.setEnabled(false);
				
			}*/
			
			
			
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
			
			add( tabbedPane, BorderLayout.CENTER );
			
			setVisible(true);
			

			
			
			
			
			
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
						
						if(tempVarInst.isSelectedForCrossCorrelation || tempVarInst.isSelectedForMultipleBandpassFilteredTimeSeries || tempVarInst.isSelectedForMultipleTimeSeries || tempVarInst.isSelectedForScatterplots || tempVarInst.selectedForSingleBandpassFilteredTimeSeries ){
							
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
							for(int k=0; k<PlottingSettings.listOfRatioInstances.size();k++){
								
								/*If denominator is the selected variable*/
								if(PlottingSettings.listOfRatioInstances.get(k).numerator.instanceContainsVariable(variableName)){

				
									boolean found = false;
									
									for(int l=0; l< PlottingSettings.listOfRatioInstances.size();l++){
										
										if(PlottingSettings.listOfRatioInstances.get(l).numerator.equals(PlottingSettings.listOfRatioInstances.get(k).denominator) || PlottingSettings.listOfRatioInstances.get(l).denominator.equals(PlottingSettings.listOfRatioInstances.get(k).denominator)){
								
											
											System.out.println("Num: k "+k+"   l"+l);
											
											if(l!=k){
												
												System.out.println("In");
												found = true;
												break;
											}
										}
									}
									
										
									if(!found){
										
										for(int l1 =0; l1 < PlottingSettings.listOfAgentsVariableInstances.size(); l1++){
											
											
											System.out.println(PlottingSettings.listOfAgentsVariableInstances.get(l1).agentName+"     "+ PlottingSettings.listOfRatioInstances.get(k).denominator.agentName);
											
											if(PlottingSettings.listOfAgentsVariableInstances.get(l1).agentName.equals(PlottingSettings.listOfRatioInstances.get(k).denominator.agentName))
											{
												for(int l=0; l <PlottingSettings.listOfAgentsVariableInstances.get(l1).listOfVariableInstances.size();l++){
								
													if(PlottingSettings.listOfAgentsVariableInstances.get(l1).listOfVariableInstances.get(l).instanceName.equals(PlottingSettings.listOfRatioInstances.get(k).denominator.instanceName)){
														
											
														PlottingSettings.listOfAgentsVariableInstances.get(l1).listOfVariableInstances.get(l).isSelectedForRatios = false;
														
												
														break;
														
													}
													
												}
												
												
												break;
												
											}
										}
										
									}
									
									PlottingSettings.listOfRatioInstances.remove(k);
									k--;
									
									
									
								}else if(PlottingSettings.listOfRatioInstances.get(k).denominator.instanceContainsVariable(variableName)){
							
									boolean found = false;
									
									for(int l=0; l< PlottingSettings.listOfRatioInstances.size();l++){
										
										if(PlottingSettings.listOfRatioInstances.get(l).numerator.equals(PlottingSettings.listOfRatioInstances.get(k).numerator) || PlottingSettings.listOfRatioInstances.get(l).denominator.equals(PlottingSettings.listOfRatioInstances.get(k).numerator)){
								
											System.out.println("Den: k "+k+"   l"+l);
											
											if(l!=k){
												
												System.out.println("In");
												found = true;
												break;
											}
										}
									}
									
									if(!found){
										
										for(int l1 =0; l1 < PlottingSettings.listOfAgentsVariableInstances.size(); l1++){
											
											if(PlottingSettings.listOfAgentsVariableInstances.get(l1).agentName.equals(PlottingSettings.listOfRatioInstances.get(k).numerator.agentName))
											{
												for(int l=0; l <PlottingSettings.listOfAgentsVariableInstances.get(l1).listOfVariableInstances.size();l++){
													
													if(PlottingSettings.listOfAgentsVariableInstances.get(l1).listOfVariableInstances.get(l).instanceName.equals(PlottingSettings.listOfRatioInstances.get(k).numerator.instanceName)){
														
														System.out.println("Set");
														
														PlottingSettings.listOfAgentsVariableInstances.get(l1).listOfVariableInstances.get(l).isSelectedForRatios = false;
														break;
														
													}
													
												}
												
											}
										}
										
									}
									
									PlottingSettings.listOfRatioInstances.remove(k);
									k--;
					
								}
							}
							
						
						}
						

				
					
						if( listOfVariableInstances.get(j).isSelectedForCrossCorrelation ){
							
							for(int k=0; k <PlottingSettings.listOfCrossCorrelation.size(); k++){
								
								boolean found = false;
								
								if(PlottingSettings.listOfCrossCorrelation.get(k).firstComponent.isVariableInstance){
									
									if(PlottingSettings.listOfCrossCorrelation.get(k).firstComponent.variableInstance.isVariable){
										
										
										if(PlottingSettings.listOfCrossCorrelation.get(k).firstComponent.variableInstance.variable.name.equals(variableName)){
											
											PlottingSettings.listOfCrossCorrelation.remove(k);
											k--;
											found = true;
											
										}
								
									}
									
									
									
									
									
								}else{
									
								
										if(PlottingSettings.listOfCrossCorrelation.get(k).firstComponent.ratioInstance.numerator.variable.name.equals(variableName)|| PlottingSettings.listOfCrossCorrelation.get(k).firstComponent.ratioInstance.denominator.variable.name.equals(variableName)){
											
											PlottingSettings.listOfCrossCorrelation.remove(k);
											k--;
											found = true;
											
										}
								
									
									
									
									
								}
								
								
								if(!found){
									
									if(PlottingSettings.listOfCrossCorrelation.get(k).secondComponent.isVariableInstance){
										
										if(PlottingSettings.listOfCrossCorrelation.get(k).secondComponent.variableInstance.isVariable){
											
											
											if(PlottingSettings.listOfCrossCorrelation.get(k).secondComponent.variableInstance.variable.name.equals(variableName)){
												
												PlottingSettings.listOfCrossCorrelation.remove(k);
												k--;
												found = true;
												
											}
									
										}
										
										
										
										
										
									}else{
										
									
											if(PlottingSettings.listOfCrossCorrelation.get(k).secondComponent.ratioInstance.numerator.variable.name.equals(variableName)|| PlottingSettings.listOfCrossCorrelation.get(k).firstComponent.ratioInstance.denominator.variable.name.equals(variableName)){
												
												PlottingSettings.listOfCrossCorrelation.remove(k);
												k--;
												found = true;
												
											}
								
									}
									
									
								}
								
								
							}
								
								
							
							
						}
							
							
						if( listOfVariableInstances.get(j).isSelectedForMultipleBandpassFilteredTimeSeries){
							
							for(int k=0; k <PlottingSettings.listOfMultipleBandpassFilteredTimeSeries.size(); k++){
								
								for(int l=0; l < PlottingSettings.listOfMultipleBandpassFilteredTimeSeries.get(k).variableInstancesUsedForMultioleTimeSeries.size();l++){
									
									if(PlottingSettings.listOfMultipleBandpassFilteredTimeSeries.get(k).variableInstancesUsedForMultioleTimeSeries.get(l).isVariable){
									
										if(PlottingSettings.listOfMultipleBandpassFilteredTimeSeries.get(k).variableInstancesUsedForMultioleTimeSeries.get(l).variable.name.equals(variableName)){
										
											PlottingSettings.listOfMultipleBandpassFilteredTimeSeries.get(k).variableInstancesUsedForMultioleTimeSeries.remove(l);
											l--;
										}
										}
										
								}
								
								for(int l=0; l < PlottingSettings.listOfMultipleBandpassFilteredTimeSeries.get(k).ratiosUsedForMultioleTimeSeries.size();l++){
									
									if(variableName.equals(PlottingSettings.listOfMultipleBandpassFilteredTimeSeries.get(k).ratiosUsedForMultioleTimeSeries.get(l).denominator.variable.name) || variableName.equals(PlottingSettings.listOfMultipleBandpassFilteredTimeSeries.get(k).ratiosUsedForMultioleTimeSeries.get(l).numerator.variable.name)){
										
										PlottingSettings.listOfMultipleBandpassFilteredTimeSeries.get(k).ratiosUsedForMultioleTimeSeries.remove(l);
										l--;
									
								}
									
									
								}
								
								
							
								
								if(PlottingSettings.listOfMultipleBandpassFilteredTimeSeries.get(k).variableInstancesUsedForMultioleTimeSeries.size()==0 &&  PlottingSettings.listOfMultipleBandpassFilteredTimeSeries.get(k).ratiosUsedForMultioleTimeSeries.size()==0){
									
									PlottingSettings.listOfMultipleBandpassFilteredTimeSeries.remove(k);
									
								}
								
								
							}
							
							
						}
							
						if( listOfVariableInstances.get(j).isSelectedForMultipleTimeSeries ){
							
						
							for(int k=0; k <PlottingSettings.listOfMultipleTimeSeries.size(); k++){
								
								for(int l=0; l < PlottingSettings.listOfMultipleTimeSeries.get(k).variableInstancesUsedForMultioleTimeSeries.size();l++){
									
									if(PlottingSettings.listOfMultipleTimeSeries.get(k).variableInstancesUsedForMultioleTimeSeries.get(l).isVariable){
									
										if(PlottingSettings.listOfMultipleTimeSeries.get(k).variableInstancesUsedForMultioleTimeSeries.get(l).variable.name.equals(variableName)){
										
											PlottingSettings.listOfMultipleTimeSeries.get(k).variableInstancesUsedForMultioleTimeSeries.remove(l);
											l--;
											
										}
											
										}
										
								}
								
								for(int l=0; l < PlottingSettings.listOfMultipleTimeSeries.get(k).ratiosUsedForMultioleTimeSeries.size();l++){
									
									if(variableName.equals(PlottingSettings.listOfMultipleTimeSeries.get(k).ratiosUsedForMultioleTimeSeries.get(l).denominator.variable.name) || variableName.equals(PlottingSettings.listOfMultipleTimeSeries.get(k).ratiosUsedForMultioleTimeSeries.get(l).numerator.variable.name)){
										PlottingSettings.listOfMultipleTimeSeries.get(k).ratiosUsedForMultioleTimeSeries.remove(l);
										l--;
										
									
								}
									
									
								}
								
								
							
								
								if(PlottingSettings.listOfMultipleTimeSeries.get(k).variableInstancesUsedForMultioleTimeSeries.size()==0 &&  PlottingSettings.listOfMultipleTimeSeries.get(k).ratiosUsedForMultioleTimeSeries.size()==0){
									
									PlottingSettings.listOfMultipleTimeSeries.remove(k);
									
								}
								
								
							}
							
								
						}	
						if( listOfVariableInstances.get(j).isSelectedForScatterplots){
							
							for(int k=0; k <PlottingSettings.listOfScatterPlots.size(); k++){
								
								boolean found = false;
								
								if(PlottingSettings.listOfScatterPlots.get(k).firstComponent.isVariableInstance){
									
									if(PlottingSettings.listOfScatterPlots.get(k).firstComponent.variableInstance.isVariable){
										
										
										if(PlottingSettings.listOfScatterPlots.get(k).firstComponent.variableInstance.variable.name.equals(variableName)){
											
											PlottingSettings.listOfScatterPlots.remove(k);
											k--;
											found = true;
											
										}
								
									}
									
									
									
									
									
								}else{
									
								
										if(PlottingSettings.listOfScatterPlots.get(k).firstComponent.ratioInstance.numerator.variable.name.equals(variableName)|| PlottingSettings.listOfCrossCorrelation.get(k).firstComponent.ratioInstance.denominator.variable.name.equals(variableName)){
											
											PlottingSettings.listOfScatterPlots.remove(k);
											k--;
											found = true;
											
										}
								
									
									
									
									
								}
								
								
								if(!found){
									
									if(PlottingSettings.listOfScatterPlots.get(k).secondComponent.isVariableInstance){
										
										if(PlottingSettings.listOfScatterPlots.get(k).secondComponent.variableInstance.isVariable){
											
											
											if(PlottingSettings.listOfScatterPlots.get(k).secondComponent.variableInstance.variable.name.equals(variableName)){
												
												PlottingSettings.listOfScatterPlots.remove(k);
												k--;
												found = true;
												
											}
									
										}
										
										
										
										
										
									}else{
										
									
											if(PlottingSettings.listOfScatterPlots.get(k).secondComponent.ratioInstance.numerator.variable.name.equals(variableName)|| PlottingSettings.listOfCrossCorrelation.get(k).firstComponent.ratioInstance.denominator.variable.name.equals(variableName)){
												
												PlottingSettings.listOfScatterPlots.remove(k);
												k--;
												found = true;
												
											}
								
									}
									
									
								}
								
								
							}
							
							
							
							
						}
									
									
						if(  listOfVariableInstances.get(j).selectedForSingleBandpassFilteredTimeSeries  )
						{
							
							for(int k=0; k <PlottingSettings.listOfSingleBandpassFilteredTimeSeries.size(); k++){
								
								
								if(PlottingSettings.listOfSingleBandpassFilteredTimeSeries.get(k).isVariableInstance){
									
									if(variableName.equals(PlottingSettings.listOfSingleBandpassFilteredTimeSeries.get(k).variableInstance.variable.name)){
										
										PlottingSettings.listOfSingleBandpassFilteredTimeSeries.remove(k);
										k--;
										
									}
									
						
						
								}else{
									
										if(variableName.equals(PlottingSettings.listOfSingleBandpassFilteredTimeSeries.get(k).ratioInstance.denominator.variable.name) || variableName.equals(PlottingSettings.listOfSingleBandpassFilteredTimeSeries.get(k).ratioInstance.numerator.variable.name)){
										
											PlottingSettings.listOfSingleBandpassFilteredTimeSeries.remove(k);
										k--;
										
									}
									
									
								}
								
								
								
							}
							
						}
						if( listOfVariableInstances.get(j).selectedForSingleTimeSeries){
							
							for(int k=0; k <PlottingSettings.listOfSingleTimeSeries.size(); k++){
								
								
								if(PlottingSettings.listOfSingleTimeSeries.get(k).isVariableInstance){
									
									if(PlottingSettings.listOfSingleTimeSeries.get(k).variableInstance.isVariable){
									
										if(variableName.equals(PlottingSettings.listOfSingleTimeSeries.get(k).variableInstance.variable.name)){
											
											PlottingSettings.listOfSingleTimeSeries.remove(k);
											k--;
											
										}
									}else{
										
										
										if(variableName.equals(PlottingSettings.listOfSingleTimeSeries.get(k).variableInstance.agentRatio.ratioName)){
											
											PlottingSettings.listOfSingleTimeSeries.remove(k);
											k--;
											
										}
										
										
									}
									
						
						
								}else{
									
										if(variableName.equals(PlottingSettings.listOfSingleTimeSeries.get(k).ratioInstance.denominator.variable.name) || variableName.equals(PlottingSettings.listOfSingleTimeSeries.get(k).ratioInstance.numerator.variable.name)){
										
										PlottingSettings.listOfSingleTimeSeries.remove(k);
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
		
		
		

		/*This function redraws the ratio tab*/
		public void redrawRatioTab(){
			

			if(PlottingSettings.listOfRatioInstances.size()>0){
				
				
				/*Check if the variables are still there*/
				
				boolean denominatorFound;
				boolean numeratorFound;
				
				for(int i=0; i<PlottingSettings.listOfRatioInstances.size();i++ ){
					
					numeratorFound = false;
					denominatorFound = false;
					
					for(int j=0; j<PlottingSettings.listOfAgentsVariableInstances.size();j++){
						
						
						if(PlottingSettings.listOfRatioInstances.get(i).denominator.agentName.equals(PlottingSettings.listOfAgentsVariableInstances.get(j).agentName)){
							
							if(PlottingSettings.listOfAgentsVariableInstances.get(j).isSelected){
			
								for(int k=0; k<PlottingSettings.listOfAgentsVariableInstances.get(j).listOfVariableInstances.size();k++){
									
									
									
										if(PlottingSettings.listOfAgentsVariableInstances.get(j).listOfVariableInstances.get(k).instanceName.equals(PlottingSettings.listOfRatioInstances.get(i).denominator.instanceName)){
											
											denominatorFound =true;
											break;
										}
										
								
								}
					
							}else{
							break;	
							}
						}
					}
						
					for(int j=0; j<PlottingSettings.listOfAgentsVariableInstances.size();j++){
						
						
						if(PlottingSettings.listOfRatioInstances.get(i).numerator.agentName.equals(PlottingSettings.listOfAgentsVariableInstances.get(j).agentName)){
							
							if(PlottingSettings.listOfAgentsVariableInstances.get(j).isSelected){
			
								for(int k=0; k<PlottingSettings.listOfAgentsVariableInstances.get(j).listOfVariableInstances.size();k++){
									
									
									
										if(PlottingSettings.listOfAgentsVariableInstances.get(j).listOfVariableInstances.get(k).instanceName.equals(PlottingSettings.listOfRatioInstances.get(i).numerator.instanceName)){
											
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
		
						PlottingSettings.listOfRatioInstances.remove(i);
						i--;
						
					}
					
					
				}
		
				
				tabbedPane.remove(ratioTab);
				
				if(PlottingSettings.listOfRatioInstances.size()>0){
				
				ratioTab = new RatioTab(PlottingSettings.listOfRatioInstances);
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
		
		
		void redrawAllTables(){
			
			for(int i=0; i< agentJPanelList.size();i++){
				
				
				agentJPanelList.get(i).reDrawTable();
				
				
				
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
				
				agent= agentAtTab;

		
				setSize(700, 600);
				setLayout(new GridBagLayout());
				
				
				agentPane.insets = new Insets( 5, 5, 5, 5);
				
				JLabel lab = new JLabel("Time Series for Agent "+agent.agentName);
				agentPane.gridx =0;agentPane.gridy =0;
				add(lab,agentPane);
				
				VariableTable variableTable = new  VariableTable(agent);
					
				scrollVariableTable = variableTable.listScrollVariables;
				scrollVariableTable.setPreferredSize(new Dimension(700, 400)); 
				agentPane.gridx =0;agentPane.gridy =1;
				add(scrollVariableTable,agentPane);
				
				JPanel lowerPanel = new JPanel();
				
				lowerPanel.setLayout(new GridBagLayout());
				
				GridBagConstraints lowerPa = new GridBagConstraints();
				lowerPa.insets = new Insets( 5, 5, 5, 5);
				
				addInstance = new JButton("Add modified Time Series");
				removeInstance = new JButton("Remove modified Time Series");
				
				addInstance.setEnabled(false);
				removeInstance.setEnabled(false);
				
				if(agent.listOfVariableInstances.size()>0){
					agent.isSelected=true;
				}else{
					agent.isSelected=false;
				}
				
				
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
							 
							 
							 for(int i=0; i < AgentSettings.agents.size();i++){
				   		        	
				   		        	if(AgentSettings.agents.get(i).agentName.equals(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.agentName)){
				
				   		        		for(int j=0; j< AgentSettings.agents.get(i).variableList.size();j++){
				   		        			
				   		        			if( AgentSettings.agents.get(i).variableList.get(j).name.equals(tabvariableModel.getValueAt(tabVariables.getSelectedRow(), 0).toString())){
				   		        				
				   		        			 PlottingSettings.VariableInstance aR = (new PlottingSettings()).new  VariableInstance(name,
				   								 	agent.agentName,
				   								 	AgentSettings.agents.get(i).variableList.get(j),
				   									agent.defaultSettings.defaultMethod,
				   									agent.defaultSettings.defaultFilter1,
				   									agent.defaultSettings.defaultFilter2,
				   									agent.defaultSettings.defaultPartitioning,
				   									"No");
				   						 
				   						 agent.listOfVariableInstances.add(indexOfLastOccurence+1,aR);
				   						 
				   						 
				   						PlottingSettings.listOfSingleTimeSeries.add((new PlottingSettings()).new SingleTimeSeries(aR));
				   		        				
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
								 
								 for(int j=0; j < AgentSettings.agents.size();j++){
					   		        	
					   		        	if(AgentSettings.agents.get(j).agentName.equals(agent.agentName)){
					   		        		
					   		        		for(int k=0; k< AgentSettings.agents.get(j).variableList.size();k++){
							
					   		        			if(AgentSettings.agents.get(j).variableList.get(k).name.equals(varName)) {
										
					   		        				AgentSettings.agents.get(j).variableList.get(k).isSelectedForPlotting = false;
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
				
				/*Set a flag to indicate whether theagent can be selected for multiple time series plotting*/
				if(agent.listOfVariableInstances.size()>0){
					agent.isSelected=true;
				}else{
					agent.isSelected=false;
				}
				
				VariableTable variableTable = new  VariableTable(agent);
				
				scrollVariableTable = variableTable.listScrollVariables;
				scrollVariableTable.setPreferredSize(new Dimension(700, 400)); 
				agentPane.gridx=0; 
				agentPane.gridy=1;
				add(scrollVariableTable,agentPane);

				validate();
				
				
			}
			
			
			
			private class VariableTable extends JScrollPane{
				
			
				JScrollPane listScrollVariables;  
				

				VariableTable(PlottingSettings.Agent agentAtTab){
					
		
					
					String[] colHeadersT2 = {"Variable" ,"Name", "Method", "Filter 1", "Filter 2", "Further settings" };
					
					int agentIndex = 0;
					
					for(int i=0; i<PlottingSettings.listOfAgentsVariableInstances.size();i++ ){
						
						if(PlottingSettings.listOfAgentsVariableInstances.get(i).agentName.equals(agent.agentName)){
							
							agentIndex=i;
							break;
						}	
					}
					
					
					tabvariableModel = new VariableListTableModel(colHeadersT2, PlottingSettings.listOfAgentsVariableInstances.get(agentIndex) );
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
					
					String partitioning = "+"; 
					
				
					tabVariables.getColumnModel().getColumn(5).setCellEditor(new SpecificButtonEditor(new JCheckBox()));
					tabVariables.getColumnModel().getColumn(5).setCellRenderer(new SpecificButtonRenderer(partitioning));
					
					
					
					tabVariables.getColumnModel().getColumn(5).getCellEditor().addCellEditorListener(new EditorListener(){
						 
						 
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
				
				
				
				public class SpecificButtonEditor extends DefaultCellEditor {
					  protected JButton button;
					  private String    label;
					  private boolean   isPushed;
					  private  ArrayList<String> instanceName;
					  
					  public SpecificButtonEditor(JCheckBox checkBox) {
					    super(checkBox);
					    button = new JButton();
					    button.setOpaque(true);
					    button.addActionListener(new ActionListener() {
					      public void actionPerformed(ActionEvent e) {
					        fireEditingStopped();
					      }
					    });
					  }
					  
					  public Component getTableCellEditorComponent(JTable table, Object value,
					                   boolean isSelected, int row, int column) {
					    if (isSelected) {
					      button.setForeground(table.getSelectionForeground());
					      button.setBackground(table.getSelectionBackground());
					    } else{
					      button.setForeground(table.getForeground());
					      button.setBackground(table.getBackground());
					    }
					    instanceName = (ArrayList<String>) value;
					    label = "-";
					    button.setText( label );
					    isPushed = true;
					    return button;
					  }
					  
						JTextField textfield1, textfield2, textfield3, textfield4;
						JCheckBox CBLowerBound, CBUpperBound;
						JDialog defaultSettingsDialog;

					  
					  public Object getCellEditorValue() {
					    if (isPushed)  {
					     
					    	defaultSettingsDialog = new JDialog();
							defaultSettingsDialog.setTitle("Further Settings");
							defaultSettingsDialog.setLayout(new FlowLayout());
							defaultSettingsDialog.setSize(500,250);
							defaultSettingsDialog.setBackground(Color.white);
							defaultSettingsDialog.setModal(true);
							
							defaultSettingsDialog.setLayout(new GridBagLayout());
				   			final GridBagConstraints enterDS= new GridBagConstraints();
				   			enterDS.insets = new Insets( 5, 5, 5, 5);
							
							
				   			/*tempDefaults*/
				   			
				   			for(int i = 0; i<PlottingSettings.listOfSingleTimeSeries.size() ; i++){
				   				
				   				if(PlottingSettings.listOfSingleTimeSeries.get(i).instanceName.equals(instanceName.get(0))&&PlottingSettings.listOfSingleTimeSeries.get(i).variableInstance.agentName.equals(instanceName.get(1))){
				   					
				   					JLabel label1 = new JLabel("Tmin");
						   			textfield1 = new JTextField(5);
						   			
						   			
						   			textfield1.setText(Integer.toString(PlottingSettings.listOfSingleTimeSeries.get(i).tmin));
						   			
						   			enterDS.gridx=0; enterDS.gridy=0;
						   			defaultSettingsDialog.add(label1,enterDS);
						   			
						   			enterDS.gridx=2; enterDS.gridy=0;
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
						   			textfield2.setText(Integer.toString(PlottingSettings.listOfSingleTimeSeries.get(i).tmax));
						   			
						   			enterDS.gridx=0; enterDS.gridy=1;
						   			defaultSettingsDialog.add(label2,enterDS);
						   			
						   			enterDS.gridx=2; enterDS.gridy=1;
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
						   			
						   			
						   			
						   			JLabel label3= new JLabel("Lower Bound");
						   			textfield3 = new JTextField(5);
						   			
						   			CBLowerBound = new JCheckBox();
						   			
						   			
						   			
						   			CBLowerBound.setSelected(PlottingSettings.listOfSingleTimeSeries.get(i).lowerBoundEnabled);
						   			textfield3.setEnabled(PlottingSettings.listOfSingleTimeSeries.get(i).lowerBoundEnabled);
						   			
						   			
						   			final int l = i;
						   			
						   			CBLowerBound.addActionListener(new ActionListener(){
						  	    		
						  	    		public void actionPerformed(ActionEvent evt) {
						  	    			
						  	    			PlottingSettings.listOfSingleTimeSeries.get(l).lowerBoundEnabled = CBLowerBound.isSelected();
						  	    			textfield3.setEnabled(PlottingSettings.listOfSingleTimeSeries.get(l).lowerBoundEnabled);
						  	    			
						  	    			
						  	    		}});
						   			
						   				
						   		
						   			
						   			
						   			textfield3.setText(Double.toString(PlottingSettings.listOfSingleTimeSeries.get(i).lowerBound));
						   			
						   			
						   			textfield3.addActionListener(new ActionListener(){
						  	    		
						  	    		public void actionPerformed(ActionEvent evt) {
						  	    		    
						  	    			String input = textfield3.getText();
						  	    		
						  	    			try {
						  	    				double doub= Double.parseDouble(input);
							    			    System.out.println("NumBatch runs: "+SimulationSettings.numBatchRuns);
							    			    textfield3.selectAll();
						  	    			}
						  	    			catch(NumberFormatException nFE) {
						  	    				JOptionPane.showMessageDialog(null,"Not a number"); 
						  	    			}
						  	    			
						  	    		}
						  	    		
						  	    	});
						   			
						   			
						   			
						   			
						   			enterDS.gridx=0; enterDS.gridy=3;
						   			defaultSettingsDialog.add(label3,enterDS);
						   			
						   			enterDS.gridx=1; enterDS.gridy=3;
						   			defaultSettingsDialog.add(CBLowerBound,enterDS);
						   			
						   			enterDS.gridx=2; enterDS.gridy=3;
						   			defaultSettingsDialog.add(textfield3,enterDS);
						   			
						   			
						   			
						   			JLabel label4= new JLabel("Upper Bound");
						   			textfield4 = new JTextField(5);
						   			
						   			CBUpperBound = new JCheckBox();
						   			
						   			
						   			
						   			CBUpperBound.setSelected(PlottingSettings.listOfSingleTimeSeries.get(i).upperBoundEnabled);
						   			textfield4.setEnabled(PlottingSettings.listOfSingleTimeSeries.get(i).upperBoundEnabled);
						   			
						   			
						   		
						   			
						   			CBUpperBound.addActionListener(new ActionListener(){
						  	    		
						  	    		public void actionPerformed(ActionEvent evt) {
						  	    			
						  	    			PlottingSettings.listOfSingleTimeSeries.get(l).upperBoundEnabled = CBUpperBound.isSelected();
						  	    			textfield4.setEnabled(PlottingSettings.listOfSingleTimeSeries.get(l).upperBoundEnabled);
						  	    			
						  	    			
						  	    		}});
						   			
						   				
						   		
						   			
						   			
						   			textfield4.setText(Double.toString(PlottingSettings.listOfSingleTimeSeries.get(i).upperBound));
						   			
						   			
						   			textfield4.addActionListener(new ActionListener(){
						  	    		
						  	    		public void actionPerformed(ActionEvent evt) {
						  	    		    
						  	    			String input = textfield4.getText();
						  	    		
						  	    			try {
						  	    				double doub= Double.parseDouble(input);
							    			    System.out.println("NumBatch runs: "+SimulationSettings.numBatchRuns);
							    			    textfield4.selectAll();
						  	    			}
						  	    			catch(NumberFormatException nFE) {
						  	    				JOptionPane.showMessageDialog(null,"Not a number"); 
						  	    			}
						  	    			
						  	    		}
						  	    		
						  	    	});
						   			
						   			
						   			
						   			
						   			enterDS.gridx=0; enterDS.gridy=4;
						   			defaultSettingsDialog.add(label4,enterDS);
						   			
						   			enterDS.gridx=1; enterDS.gridy=4;
						   			defaultSettingsDialog.add(CBUpperBound,enterDS);
						   			
						   			enterDS.gridx=2; enterDS.gridy=4;
						   			defaultSettingsDialog.add(textfield4,enterDS);
						   			

						   			JButton okay = new JButton("OK");
						   			JButton discard = new JButton("Discard");
						   			
						   			enterDS.gridx=4; enterDS.gridy=6;
						   			defaultSettingsDialog.add(okay,enterDS);
						   			
						   			enterDS.gridx=5; enterDS.gridy=6;
						   			defaultSettingsDialog.add(discard,enterDS);
						   			
						   			final int k = i;
						   			
						   			okay.addActionListener(new ActionListener(){
						  	    		
						  	    		public void actionPerformed(ActionEvent evt) {
						  	    			
						  	    			
						  	    			try {
						  	    				
						  	    				
						  	    				PlottingSettings.listOfSingleTimeSeries.get(k).tmin = Integer.parseInt(textfield1.getText());
						  	    				PlottingSettings.listOfSingleTimeSeries.get(k).tmax = Integer.parseInt(textfield2.getText());
						  	    				
						  	    				
						  	    				PlottingSettings.listOfSingleTimeSeries.get(k).lowerBoundEnabled = CBLowerBound.isSelected();
						  	    				PlottingSettings.listOfSingleTimeSeries.get(k).lowerBound = Double.parseDouble(textfield3.getText());
						  	    				
						  	    				PlottingSettings.listOfSingleTimeSeries.get(k).upperBoundEnabled = CBUpperBound.isSelected();
						  	    				PlottingSettings.listOfSingleTimeSeries.get(k).upperBound = Double.parseDouble(textfield4.getText());
						  	    				
						  	    				
						  	    				//for(int i=0; i < tempSingleTimesSeries.size();i++){
						  	    					
						  	    			//		tempSingleTimesSeries.get(i).tmin = PlottingSettings.defaultsSingleTimeSeries.tmin;
						  	    				//	tempSingleTimesSeries.get(i).tmax = PlottingSettings.defaultsSingleTimeSeries.tmax;
						  	    				//}
						  	    				
						  	    				/*Redraw table*/
						    	    			
						  	    				for(int j=0; j < tabbedPane.getTabCount();j++){
						  	    					
						    	    			
							    	    			if(tabbedPane.getTitleAt(j).equals("Ratio")){
							    	    				
							    	    			//	ratioTab.reDrawTable();
							    	    				
							    	    			}else{
							    	    				
							    	    				agentJPanelList.get(j).reDrawTable();
							    	    				
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
				   				
				   				
				   				
				   				
				   			}
				   			
				   			
				   			
				   			
				   			
					    	
					    	
					    	
					    }
					    isPushed = false;
					    return new String( label ) ;
					  }
					    
					  public boolean stopCellEditing() {
					    isPushed = false;
					    return super.stopCellEditing();
					  }
					  
					  protected void fireEditingStopped() {
					    super.fireEditingStopped();
					  }
					}
				
				
				
				
				
				public class SpecificButtonEditor2 extends AbstractCellEditor implements TableCellEditor,
	            ActionListener {
					
					   JTable table;
					    JButton button = new JButton();
					
					    public SpecificButtonEditor2(JTable table) {
					        this.table = table;
					        button.addActionListener(this);
					        
					    }

						@Override
						public Object getCellEditorValue() {
							// TODO Auto-generated method stub
							return null;
						}
						
						JTextField textfield1, textfield2, textfield3, textfield4;
						JCheckBox CBLowerBound, CBUpperBound;
						JDialog defaultSettingsDialog;

						@Override
						
						
						
						
						
						public void actionPerformed(ActionEvent e) {
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
				  	    				
				  	    				
				  	    				//for(int i=0; i < tempSingleTimesSeries.size();i++){
				  	    					
				  	    			//		tempSingleTimesSeries.get(i).tmin = PlottingSettings.defaultsSingleTimeSeries.tmin;
				  	    				//	tempSingleTimesSeries.get(i).tmax = PlottingSettings.defaultsSingleTimeSeries.tmax;
				  	    				//}
				  	    				
				  	    				/*Redraw table*/
				    	    			
				  	    				for(int i=0; i < tabbedPane.getTabCount();i++){
				  	    					
				    	    			
					    	    			if(tabbedPane.getTitleAt(i).equals("Ratio")){
					    	    				
					    	    			//	ratioTab.reDrawTable();
					    	    				
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

						@Override
						public Component getTableCellEditorComponent(JTable arg0,
								Object arg1, boolean arg2, int arg3, int arg4) {
							// TODO Auto-generated method stub
							return null;
						}
				    
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
				
				
				
		public class SpecificButtonRenderer extends JButton implements TableCellRenderer {
					
				    public SpecificButtonRenderer(String item) {
				        super(item);
				    }

				    public Component getTableCellRendererComponent(JTable table, Object value,
				            boolean isSelected, boolean hasFocus, int row, int column) {
				    	
				    	
				    	
				    	
				    	JButton button = new JButton(value.toString());
			            button.addActionListener(new ActionListener() {
			                @Override
			                public void actionPerformed(ActionEvent arg0) {
			                    System.out.println("Clicked !");
			                }
			            });

				    	
				        return this;
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

	}

