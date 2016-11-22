import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Panel;
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

import javax.swing.ButtonGroup;
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
import javax.swing.JRadioButton;
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

public class Heatmaps2VMenu extends JDialog{
	
	JScrollPane mainCrollPane;


	String nameElement1, nameElement2;
	
	JPanel contentPanel, container, panel1, panel2, panel3, panel4, panel, panel5;

	JButton okayButton, discardButton;
	
	/*Variables for menu bar:*/
	private JMenuBar menuBar;
	
	private JMenu menuPlotting;
	private JMenuItem addAgent, removeAgent, applyChanges, exit;
	
	
	private JMenu menuVariables;
	private JMenuItem addHeatmap2V, removeHeatmap2V;
	
	private JMenu menuSettings;
	private JMenuItem editFilter,  defaultSettings ;
	
	private JList agentList;
	private JList removeAgentList;
	private JDialog addAgentDialog , removeAgentDialog;

	
Variable variable1, variable2;
PlottingSettings.AgentRatio agentRatio1, agentRatio2;
	
	private	JTabbedPane tabbedPane;
	private	ArrayList<AgentTab> agentJPanelList;
	
	JScrollPane heatmapSelectionTable;


	private ArrayList<PlottingSettings.Heatmaps2V> tempHeatmaps2VList;
	private ArrayList<PlottingSettings.Agent> tempAgentList;
	private ArrayList<Agent> tempAgentSettingList;
	
	
	Heatmaps2VMenu(){
		
		
		tempHeatmaps2VList = new ArrayList<PlottingSettings.Heatmaps2V>();
		
		for(int i= 0; i< PlottingSettings.listOfHeatmaps2V.size();i++){
			
			tempHeatmaps2VList.add(PlottingSettings.listOfHeatmaps2V.get(i).clone());
			
		}
		
		tempAgentList = new ArrayList<PlottingSettings.Agent>();
		
		for(int i= 0; i< PlottingSettings.listOfAgentsVariableInstances.size();i++){
			
			tempAgentList.add(AuxFunctions.DeepCopyAgentVariableInstance(PlottingSettings.listOfAgentsVariableInstances.get(i)));
			
		}
		
		tempAgentSettingList = AuxFunctions.deepCopyAgentSettingsList(AgentSettings.agents);
		
		contentPanel = new JPanel();
		
		/*Dialog settings*/	
	    setTitle("");
	    setSize(1200,400);
	    setBackground( Color.white );
	    contentPanel.setSize(1200,400);
	    contentPanel.setBackground( Color.white );
		
		mainCrollPane = new JScrollPane();
		
		mainCrollPane.setPreferredSize(new Dimension(1150,400));

	   // setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		contentPanel.setLayout(new GridBagLayout());
	    
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
	    			resetSelectedeFlags();
	    			PlottingSettings.listOfHeatmaps2V = tempHeatmaps2VList;
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
					
					if(!tempAgentList.get(i).heatmap2VSelected){
						
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
									tempAgentList.get(i).heatmap2VSelected = true;
									
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
					
					if(tempAgentList.get(i).heatmap2VSelected){
						
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
											
											tempAgentList.get(j).heatmap2VSelected = false;
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
				
				resetSelectedeFlags();
				PlottingSettings.listOfHeatmaps2V = tempHeatmaps2VList;
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
	    			resetSelectedeFlags();
	    			PlottingSettings.listOfHeatmaps2V = tempHeatmaps2VList;
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
		
		
		menuVariables = new JMenu("Heat Map");
		
		addHeatmap2V = new JMenuItem("Add Heat Map") ;
		
		addHeatmap2V.addActionListener(new ActionListener(){
			
			JScrollPane scrollPane ;
			
			ButtonGroup radioButtonGroup1;
			JRadioButton variable1radioButton;
			JRadioButton ratio1radioButton;
			
			ButtonGroup radioButtonGroup2;
			JRadioButton variable2radioButton;
			JRadioButton ratio2radioButton;
			
		
			
			JLabel labelVar1, labelRatio1, labelVar2, labelRatio2;
			
			
			JDialog heatmapDialog;
		
			SortedListModel sortedModel1, sortedModel2;
			
			GridBagConstraints fil;
	
			
	
			
			@Override
			public void actionPerformed(ActionEvent arg0) {

				container = new JPanel();
				container.setSize(800,500);

				heatmapDialog = new JDialog();
				heatmapDialog.setModal(true);	
	   			//heatmapDialog.setSize(800,500);
	   			heatmapDialog.setBackground(Color.white);
				heatmapDialog.setTitle("Select variables for Correlation Heat Maps");
				
				container.setLayout(new GridBagLayout());
				fil = new GridBagConstraints();
	   			fil.insets = new Insets( 5, 5, 5, 5);
	   			
	   		   	scrollPane = new JScrollPane(container);
	   	      	scrollPane.setPreferredSize(new Dimension(790, 1190));
	   	      	heatmapDialog.setSize(1000, 600);
	   	      	
	   	      	
	   	      	nameElement1 = "";
	   	      	nameElement2 = "";
	   	      	
	   	      	
	   	      	/*******Panel ******/
	   	      	
	   	      	panel = new JPanel();
	   	      	panel.setLayout(new GridBagLayout());
	   	      	
	   	      	/*******Panel 1******/
	   	      	
	   	      	panel1 = new JPanel();
	   	      	panel1.setLayout(new GridBagLayout());

		   	 	radioButtonGroup1 = new ButtonGroup();
				variable1radioButton = new JRadioButton();
				ratio1radioButton = new JRadioButton();
				
				
				
		
				radioButtonGroup1.add(variable1radioButton);
				radioButtonGroup1.add(ratio1radioButton);
				
				variable1radioButton.setSelected(true);
	   	      	
				labelVar1 = new JLabel("Variable"); 
				labelRatio2 = new JLabel("Agent Ratio");
				
				fil.gridx = 0  ;fil.gridy =0;
				panel1.add(labelVar1,fil);
				
				fil.gridx = 1  ;fil.gridy =0;
				panel1.add(variable1radioButton,fil);
				
				fil.gridx = 0  ;fil.gridy =1;
				panel1.add(labelRatio2,fil);
				
				fil.gridx = 1  ;fil.gridy =1;
				panel1.add(ratio1radioButton,fil);
				
				fil.gridx = 0  ;fil.gridy =0;
				panel.add(panel1,fil);
	   	      	
	   	      	/*******Panel 2******/
	   	      	
	   	      	panel2 = new JPanel();
	   	      	
	   	      	
	   	     	panel2 = new JPanel();
	   	      	panel2.setLayout(new GridBagLayout());

		   	 	radioButtonGroup2 = new ButtonGroup();
				variable2radioButton = new JRadioButton();
				ratio2radioButton = new JRadioButton();
				
				radioButtonGroup2.add(variable2radioButton);
				radioButtonGroup2.add(ratio2radioButton);
				
				
				
		
				variable2radioButton.setSelected(true);
				
				labelVar2 = new JLabel("Variable"); 
				labelRatio2 = new JLabel("Agent Ratio");
				
				fil.gridx = 0  ;fil.gridy =0;
				panel2.add(labelVar2,fil);
				
				fil.gridx = 1  ;fil.gridy =0;
				panel2.add(variable2radioButton,fil);
				
				fil.gridx = 0  ;fil.gridy =1;
				panel2.add(labelRatio2,fil);
				
				fil.gridx = 1  ;fil.gridy =1;
				panel2.add(ratio2radioButton,fil);
				
				fil.gridx = 0  ;fil.gridy =1;
				panel.add(panel2,fil);
	   	      	
	   	      	
 			 	/*******Panel 3******/
	   	      	
	   	      	panel3 = new JPanel();
	   	      	
	   	      	if(variable1radioButton.isSelected()){
	
		   	      	PanelVariable panel31 = new PanelVariable(1);
		   	      	variable1 = panel31.variable;
		   	      	panel3 = panel31;
		   	      	agentRatio1 = null;
		   	      	
		   	      	//System.out.println("variable1 "+variable1.name);
		   	      	
	   	      		
	   	      	}else{

		   	      	PanelRatio panel41 = new PanelRatio(1);
		   	      	agentRatio2 = panel41.agentRatio;
		   	      	panel3 = panel41;
		   	      	variable1 = null;
	   	      
	   	      	}

		   	 	fil.gridx = 1  ;fil.gridy =0;
				panel.add(panel3,fil);
	   	      	
	   	      	
	   	      	/*******Panel 4******/
	   	      	
	   	      	panel4 = new JPanel();
	   	      	
		   	   	if(variable2radioButton.isSelected()){
		   	 	
		   	      	PanelVariable panel41 = new PanelVariable(2);
		   	      	variable2 = panel41.variable;
		   	      	panel4 = panel41;
		   	      	agentRatio2 = null;
		   	      	
	   	      		
	   	      	}else{
	
		   	      	PanelRatio panel41 = new PanelRatio(2);
		   	      	agentRatio2 = panel41.agentRatio;
		   	      	panel4 = panel41;
		   	      	variable2 = null;
		   	  
	   	      	}
	   	      	
		   	 	fil.gridx = 1  ;fil.gridy =1;
				panel.add(panel4,fil);
				
				fil.gridx = 0  ;fil.gridy =0;
				container.add(panel,fil);

				
				/*******Panel 5******/
	   	      	
	   	      	panel5 = new JPanel();
	   
	   	     HeatMapTable heatmapTable = new HeatMapTable(nameElement1,nameElement2);
	   	      
				heatmapSelectionTable = heatmapTable.listScrollFraction;
				heatmapSelectionTable.setPreferredSize(new Dimension(400, 60)); 
				
				variable1radioButton.addItemListener(new ItemListener(){

					public void itemStateChanged(ItemEvent e) {
						
						panel.remove(panel3);
				
						if(variable1radioButton.isSelected()){
							
				   	      	PanelVariable panel31 = new PanelVariable(1);
				   	      	variable1 = panel31.variable;
				   	      	panel3 = panel31;
				   	      	agentRatio1 = null;
				   	      	
			   	      		
			   	      	}else{
		
				   	      	PanelRatio panel41 = new PanelRatio(1);
				   	      	agentRatio2 = panel41.agentRatio;
				   	      	panel3 = panel41;
				   	      	variable1=null;
			   	      
			   	      	}
						
						
						heatmapDialog.validate();
						
						fil.gridx = 1  ;fil.gridy =0;
						panel.add(panel3,fil);
						
						System.out.println("Item listener");
						heatmapDialog.validate();
					}
				});
				
				variable2radioButton.addItemListener(new ItemListener(){

					public void itemStateChanged(ItemEvent e) {
						
						
						panel.remove(panel4);
						
						if(variable2radioButton.isSelected()){
							
							
					   	 	
				   	      	PanelVariable panel41 = new PanelVariable(2);
				   	      	variable2 = panel41.variable;
				   	      	panel4 = panel41;
				   	      	agentRatio2 = null;
				   	      	
			   	      		
			   	      	}else{
			
				   	      	PanelRatio panel41 = new PanelRatio(2);
				   	      	agentRatio2 = panel41.agentRatio;
				   	      	panel4 = panel41;
				   	      	variable2=null;
				   	  
			   	      	}
						
						heatmapDialog.validate();
						fil.gridx = 1  ;fil.gridy =1;
						panel.add(panel4,fil);
						heatmapDialog.validate();
					}
				});
				
	
				fil.gridx =0;fil.gridy =0;
				panel5.add(heatmapSelectionTable,fil);
	
		   	  	fil.gridx = 0  ;fil.gridy =1;
		   	  	container.add(panel5,fil);
		   	  	
		   	  	
		   	  	okayButton = new JButton("OK");
				okayButton.setEnabled(false);
				
				discardButton = new JButton("Discard");
				
				okayButton.addActionListener(new ActionListener(){

				public void actionPerformed(ActionEvent e) {
					
					
					System.out.println("aaa");
					
					if(!nameElement1.equals("")&&!nameElement2.equals("")){
					
						System.out.println("bbb");
						
						if(variable1!=null && variable2!=null)
							tempHeatmaps2VList.add((new PlottingSettings()).new Heatmaps2V("cor_heat_maps_"+variable1.name+"_"+variable2.name,variable1, variable2, agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.agentName));
						else if(variable1==null && variable2!=null)
							tempHeatmaps2VList.add((new PlottingSettings()).new Heatmaps2V("cor_heat_maps_"+agentRatio1.ratioName+"_"+variable2.name,agentRatio1, variable2, agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.agentName));
						else if(variable1!=null && variable2==null)
							tempHeatmaps2VList.add((new PlottingSettings()).new Heatmaps2V("cor_heat_maps_"+variable1.name+"_"+agentRatio2.ratioName,variable1, agentRatio2, agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.agentName));
						else if(variable1==null && variable2==null)
							tempHeatmaps2VList.add((new PlottingSettings()).new Heatmaps2V("cor_heat_maps_"+agentRatio1.ratioName+"_"+agentRatio2.ratioName,agentRatio1, agentRatio2, agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.agentName));
					
						agentJPanelList.get(tabbedPane.getSelectedIndex()).reDrawTable();
	
						heatmapDialog.setVisible(false);
						heatmapDialog.dispose();
					
					}
					

					}
	
						
				});
				
				
				
				discardButton.addActionListener(new ActionListener(){

				public void actionPerformed(ActionEvent e) {

					heatmapDialog.dispose();
					heatmapDialog.setVisible(false);
					
				}
					
						
				});
				
				
				fil.gridx =4;fil.gridy =4;
				container.add(okayButton,fil);
				
				fil.gridx =5;fil.gridy =4;
				container.add(discardButton,fil);
		   	  	
		   	  	
	 
	   	      	/*Add Scroll pane to JFrame*/
	   	      	heatmapDialog.add(scrollPane);
	   	      	heatmapDialog.setVisible(true);
				
			}
		
		});
		
		removeHeatmap2V = new JMenuItem("Remove Heat Map") ;
		
		removeHeatmap2V.addActionListener(new ActionListener(){

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
				ratioList = new JList(ratioListModel){
				

		public String getToolTipText(MouseEvent evt) {
		        // Get item index
				int index = locationToIndex(evt.getPoint());

		        // Get item
		        Object item = getModel().getElementAt(index);
		        
		        String tooltip = item.toString();
		        
		       
		        return tooltip;
		    }
			};
	   			
				ratioList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	   				
   				for(int i=0; i<tempHeatmaps2VList.size();i++){
					

   						ratioListModel.addElement(tempHeatmaps2VList.get(i).name);
		
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
							
							for(int j=0; j<tempHeatmaps2VList.size();j++){
								
			   					if(tempHeatmaps2VList.get(j).name.equals(ratioListModel.get(selectedIndices[i]))){
			   						ratioListModel.addElement(tempHeatmaps2VList.get(j).name);
			   						tempHeatmaps2VList.remove(j);
		
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
		
		
		menuVariables.add(addHeatmap2V);
		menuVariables.add(removeHeatmap2V);
		
		menuBar.add(menuVariables);
		
		
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
				
		
		
		defaultSettings = new JMenuItem("Default Settings");
		menuSettings.add(defaultSettings);
		defaultSettings.addActionListener( new ActionListener(){


				JComboBox methodCB, filter1CB, filter2CB, partitioningCB;
				JDialog defaultSettingsDialog;
				JTextField textfield1, textfield2,textfield3,textfield4, textfield5, textfield31, textfield42;
				JCheckBox CBLowerBoundX, CBUpperBoundX,CBLowerBoundY, CBUpperBoundY ;

				public void actionPerformed(ActionEvent arg0) {

					defaultSettingsDialog = new JDialog();
					defaultSettingsDialog.setTitle("Default Settings");
					defaultSettingsDialog.setLayout(new FlowLayout());
					defaultSettingsDialog.setSize(500,500);
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
			    	
			    	
			    	
			    	
			    	
			    	
			    	/*tempDefaults*/
		   			
		   			JLabel label1 = new JLabel("Tmin");
		   			textfield1 = new JTextField(5);
		   			textfield1.setText(Integer.toString(PlottingSettings.defaultsHeatmaps2V.tmin));
		   			
		   			enterDS.gridx=0; enterDS.gridy=3;
		   			defaultSettingsDialog.add(label1,enterDS);
		   			
		   			enterDS.gridx=1; enterDS.gridy=3;
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
		   			textfield2.setText(Integer.toString(PlottingSettings.defaultsHeatmaps2V.tmax));
		   			
		   			enterDS.gridx=0; enterDS.gridy=4;
		   			defaultSettingsDialog.add(label2,enterDS);
		   			
		   			enterDS.gridx=1; enterDS.gridy=4;
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
		   			

		   			JLabel label5 = new JLabel("Bins");
		   			textfield5 = new JTextField(5);
		   			textfield5.setText(Integer.toString(PlottingSettings.defaultsHeatmaps2V.bins));
		   			
		   			enterDS.gridx=0; enterDS.gridy=9;
		   			defaultSettingsDialog.add(label5,enterDS);
		   			
		   			enterDS.gridx=1; enterDS.gridy=9;
		   			defaultSettingsDialog.add(textfield5,enterDS);
		   			
		   			
			    	JButton ok = new JButton("OK");
			    	
			    	enterDS.gridx=4;
			    	enterDS.gridy=10;
			    	defaultSettingsDialog.add(ok,enterDS);
			    	
			    	ok.addActionListener(new ActionListener(){

						public void actionPerformed(ActionEvent e) {
			
							agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.defaultSettings.defaultFilter1 = filter1CB.getSelectedItem().toString();
							agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.defaultSettings.defaultFilter2 = filter2CB.getSelectedItem().toString();
							
							
							PlottingSettings.defaultsHeatmaps2V.tmin = Integer.parseInt(textfield1.getText().toString()); 
							PlottingSettings.defaultsHeatmaps2V.tmax = Integer.parseInt(textfield2.getText().toString());
							
							PlottingSettings.defaultsHeatmaps2V.bins = Integer.parseInt(textfield5.getText().toString());
							

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
		contentPanel.add( topPanel );

	
		// Create a tabbed pane
		tabbedPane = new JTabbedPane();
		agentJPanelList = new ArrayList<AgentTab> ();
		
		for(int i=0; i<tempAgentList.size();i++){
			
			// Create the tab pages for each agent that is selected for plotting
			if(tempAgentList.get(i).heatmap2VSelected)
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
		
		
		contentPanel.add( topPanel );
		
      	/*Add GUI container to Scroll pane*/
		mainCrollPane = new JScrollPane(contentPanel);
		mainCrollPane.setPreferredSize(new Dimension(790, 1190));
      	
      	/*Add Scroll pane to JFrame*/
      	add(mainCrollPane);
		
		
		setModal(true);
		setVisible(true);

	}
	
	class AgentTab extends  JPanel
	{
		PlottingSettings.Agent agent;
		GridBagConstraints agentPane = new GridBagConstraints();
		
		private TabModel tabvariableModel;
		private JTable tabVariables;
		
		
		private JScrollPane scrollVariableTable;
		
		AgentTab (PlottingSettings.Agent agentAtTab){
			
			agent = agentAtTab;

	
			setSize(1100, 200);
			setLayout(new GridBagLayout());
			
			
			agentPane.insets = new Insets( 5, 5, 5, 5);
			
			JLabel lab = new JLabel("Correlation Heat Map for Agent "+agent.agentName);
			agentPane.gridx =0;agentPane.gridy =0;
			add(lab,agentPane);
			
			VariableTable variableTable = new  VariableTable(agent);
				
			scrollVariableTable = variableTable.listScrollVariables;
			scrollVariableTable.setPreferredSize(new Dimension(1100, 179)); 
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
			scrollVariableTable.setPreferredSize(new Dimension(1100, 179)); 
			agentPane.gridx=0; 
			agentPane.gridy=1;
			add(scrollVariableTable,agentPane);

			validate();
			
			
		}
		
		
		
		private class VariableTable extends JScrollPane{
			
		
			JScrollPane listScrollVariables;  
			

			VariableTable(PlottingSettings.Agent agentAtTab){
				
	
				
				String[] colHeadersT2 = {"Name", "Variable 1" , "Upper Limit", "", "Lower Limit","","Variable 2", "Upper Limit", "", "Lower Limit","", "Filter 1", "Filter 2", "Tmin", "Tmax","Bins"};
				
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
				
				
				tabVariables.getColumnModel().getColumn(0).getCellEditor().addCellEditorListener(new EditorListener(){
					 
					 
					 public void editingStopped(ChangeEvent e) {
						 
						 System.out.println("Col 2 editing stopped");
						 
				
						 int editedrow = ((CellEditor) tabVariables.getColumnModel().getColumn(0).getCellEditor()).getRow();
						
								 for(int i=0; i< tempHeatmaps2VList.size();i++){
									 
									 if(tempHeatmaps2VList.get(i).name.equals(tabvariableModel.getValueAt(editedrow, 0))){
										 
										 tempHeatmaps2VList.get(i).name = tabVariables.getColumnModel().getColumn(0).getCellEditor().getCellEditorValue().toString();
										 tabvariableModel.changeValueAt(tabVariables.getColumnModel().getColumn(0).getCellEditor().getCellEditorValue().toString(), editedrow, 0);
										 break;
									 }
									 
								 }
							
						
					 }
						 
			
					 
				});
				

				/*Column 2*/
				
				
				tabVariables.getColumnModel().getColumn(1).setPreferredWidth(140);
				tabVariables.getColumnModel().getColumn(1).setCellEditor(new CellEditor());
				
				

				tabVariables.getColumnModel().getColumn(2).setPreferredWidth(140);
				tabVariables.getColumnModel().getColumn(2).setCellEditor(new CellEditor());
				
				
				
				tabVariables.getColumnModel().getColumn(2).getCellEditor().addCellEditorListener(new EditorListener(){
					 
					 
					 public void editingStopped(ChangeEvent e) {
						 
						 System.out.println("Col 2 editing stopped");
						 
						 int editedrow = ((CellEditor) tabVariables.getColumnModel().getColumn(2).getCellEditor()).getRow();
			    
						 
						 for(int i=0; i< tempHeatmaps2VList.size();i++){
							 
							 if(tempHeatmaps2VList.get(i).name.equals(tabvariableModel.getValueAt(editedrow, 0))){
								 
								 try{
									 tempHeatmaps2VList.get(i).lowerBoundX = Double.parseDouble(tabVariables.getColumnModel().getColumn(2).getCellEditor().getCellEditorValue().toString());
									 tabvariableModel.changeValueAt(tabVariables.getColumnModel().getColumn(2).getCellEditor().getCellEditorValue().toString(), editedrow, 2);
									
								 	}catch(Exception ex){
									 
								 		 JOptionPane.showMessageDialog(null,"Parameter must be a numeric expression!");
									 
								 	}
								 	 break;
								 
							 }
							 
						 }
					 }
						 
					 });
				

				/*Column 5 Check box*/
				

				 final JCheckBox check = new JCheckBox();
				 
				tabVariables.getColumnModel().getColumn(3).setPreferredWidth(40);
				
				tabVariables.getColumnModel().getColumn(3).setCellEditor(new JCheckBoxCellEditor(check));
				tabVariables.getColumnModel().getColumn(3).getCellEditor().getClass();
				tabVariables.getColumnModel().getColumn(3).getCellEditor().addCellEditorListener(new EditorListener(){
					 
					 
					 public void editingStopped(ChangeEvent e) {
						 
						 
						 int editedrow = ((JCheckBoxCellEditor) tabVariables.getColumnModel().getColumn(3).getCellEditor()).getRow();
						 
						 if( ((JCheckBoxCellEditor) tabVariables.getColumnModel().getColumn(3).getCellEditor()).getCellEditorValue().equals(true))
						 {
							 /*Set the check box*/
							 tabvariableModel.changeValueAt("true",editedrow,3);

							 for(int i=0; i< tempHeatmaps2VList.size();i++){
								 
								 if(tempHeatmaps2VList.get(i).name.equals(tabvariableModel.getValueAt(editedrow, 0))){
									 
									 
										 tempHeatmaps2VList.get(i).lowerBoundEnabledX = true;
										
										
									 	
									 	 break;
									 
								 }
								 
							 }
							 
							 tabvariableModel.updatetable();
					
							 
							 
						 }else{
							 tabvariableModel.changeValueAt("false",editedrow,3 );

							 for(int i=0; i< tempHeatmaps2VList.size();i++){
								 
								 if(tempHeatmaps2VList.get(i).name.equals(tabvariableModel.getValueAt(editedrow, 0))){
									 
									 
										 tempHeatmaps2VList.get(i).lowerBoundEnabledX = false;
										
										
									 	
									 	 break;
									 
								 }
								 
							 }
							 
							 tabvariableModel.updatetable();
							
						 }
						 
					    }
					 
					 
					 
				 });
				 
				 
				tabVariables.getColumnModel().getColumn(3).setCellRenderer(new DefaultTableCellRenderer() {
					 public Component getTableCellRendererComponent(JTable table,
					 Object value, boolean isSelected, boolean hasFocus, int row, int
					 column) {
						 
						 System.out.println(value);
					 check.setSelected(((Boolean)value).booleanValue()) ;
					 return check;
					 }
					 
					 
					 
					 });
				 
				
				
				tabVariables.getColumnModel().getColumn(4).setPreferredWidth(140);
				tabVariables.getColumnModel().getColumn(4).setCellEditor(new CellEditor());
				
				
				
				tabVariables.getColumnModel().getColumn(4).getCellEditor().addCellEditorListener(new EditorListener(){
					 
					 
					 public void editingStopped(ChangeEvent e) {
						 
						 System.out.println("Col 2 editing stopped");
						 
						 int editedrow = ((CellEditor) tabVariables.getColumnModel().getColumn(4).getCellEditor()).getRow();
			    
						 
						 for(int i=0; i< tempHeatmaps2VList.size();i++){
							 
							 if(tempHeatmaps2VList.get(i).name.equals(tabvariableModel.getValueAt(editedrow, 0))){
								 
								 try{
									 tempHeatmaps2VList.get(i).upperBoundX = Double.parseDouble(tabVariables.getColumnModel().getColumn(4).getCellEditor().getCellEditorValue().toString());
									 tabvariableModel.changeValueAt(tabVariables.getColumnModel().getColumn(4).getCellEditor().getCellEditorValue().toString(), editedrow, 4);
									
								 	}catch(Exception ex){
									 
								 		 JOptionPane.showMessageDialog(null,"Parameter must be a numeric expression!");
									 
								 	}
								 	 break;
								 
							 }
							 
						 }
					 }
						 
					 });
				

				/*Column 5 Check box*/
				

				 final JCheckBox check2 = new JCheckBox();
				 
				tabVariables.getColumnModel().getColumn(5).setPreferredWidth(40);
				
				tabVariables.getColumnModel().getColumn(5).setCellEditor(new JCheckBoxCellEditor(check2));
				tabVariables.getColumnModel().getColumn(5).getCellEditor().getClass();
				tabVariables.getColumnModel().getColumn(5).getCellEditor().addCellEditorListener(new EditorListener(){
					 
					 
					 public void editingStopped(ChangeEvent e) {
						 
						 
						 int editedrow = ((JCheckBoxCellEditor) tabVariables.getColumnModel().getColumn(5).getCellEditor()).getRow();
						 
						 if( ((JCheckBoxCellEditor) tabVariables.getColumnModel().getColumn(5).getCellEditor()).getCellEditorValue().equals(true))
						 {
							 /*Set the check box*/
							 tabvariableModel.changeValueAt("true",editedrow,5 );

							 for(int i=0; i< tempHeatmaps2VList.size();i++){
								 
								 if(tempHeatmaps2VList.get(i).name.equals(tabvariableModel.getValueAt(editedrow, 0))){
									 
									 
										 tempHeatmaps2VList.get(i).upperBoundEnabledX = true;
										
										
									 	
									 	 break;
									 
								 }
								 
							 }
							 
							 tabvariableModel.updatetable();
					
							 
							 
						 }else{
							 tabvariableModel.changeValueAt("false",editedrow,5 );

							 for(int i=0; i< tempHeatmaps2VList.size();i++){
								 
								 if(tempHeatmaps2VList.get(i).name.equals(tabvariableModel.getValueAt(editedrow, 0))){
									 
									 
										 tempHeatmaps2VList.get(i).upperBoundEnabledX = false;
					
									 	 break;
									 
								 }
								 
							 }
							 
							 tabvariableModel.updatetable();
							
						 }
						 
					    }
					 
					 
					 
				 });
				 
				 
				tabVariables.getColumnModel().getColumn(5).setCellRenderer(new DefaultTableCellRenderer() {
					 public Component getTableCellRendererComponent(JTable table,
					 Object value, boolean isSelected, boolean hasFocus, int row, int
					 column) {
						 
						 System.out.println(value);
					 check2.setSelected(((Boolean)value).booleanValue()) ;
					 return check2;
					 }
					 
					 
					 
					 });
				 
				
				
				
				tabVariables.getColumnModel().getColumn(6).setPreferredWidth(140);
				tabVariables.getColumnModel().getColumn(6).setCellEditor(new CellEditor());
				
				
				
				
				tabVariables.getColumnModel().getColumn(7).setPreferredWidth(140);
				tabVariables.getColumnModel().getColumn(7).setCellEditor(new CellEditor());
				
				
				
				tabVariables.getColumnModel().getColumn(7).getCellEditor().addCellEditorListener(new EditorListener(){
					 
					 
					 public void editingStopped(ChangeEvent e) {
						 
						 System.out.println("Col 2 editing stopped");
						 
						 int editedrow = ((CellEditor) tabVariables.getColumnModel().getColumn(7).getCellEditor()).getRow();
			    
						 
						 for(int i=0; i< tempHeatmaps2VList.size();i++){
							 
							 if(tempHeatmaps2VList.get(i).name.equals(tabvariableModel.getValueAt(editedrow, 0))){
								 
								 try{
									 tempHeatmaps2VList.get(i).lowerBoundY = Double.parseDouble(tabVariables.getColumnModel().getColumn(2).getCellEditor().getCellEditorValue().toString());
									 tabvariableModel.changeValueAt(tabVariables.getColumnModel().getColumn(7).getCellEditor().getCellEditorValue().toString(), editedrow, 7);
									
								 	}catch(Exception ex){
									 
								 		 JOptionPane.showMessageDialog(null,"Parameter must be a numeric expression!");
									 
								 	}
								 	 break;
								 
							 }
							 
						 }
					 }
						 
					 });
				

				/*Column 5 Check box*/
				

				 final JCheckBox check3 = new JCheckBox();
				 
				tabVariables.getColumnModel().getColumn(8).setPreferredWidth(40);
				
				tabVariables.getColumnModel().getColumn(8).setCellEditor(new JCheckBoxCellEditor(check3));
				tabVariables.getColumnModel().getColumn(8).getCellEditor().getClass();
				tabVariables.getColumnModel().getColumn(8).getCellEditor().addCellEditorListener(new EditorListener(){
					 
					 
					 public void editingStopped(ChangeEvent e) {
						 
						 
						 int editedrow = ((JCheckBoxCellEditor) tabVariables.getColumnModel().getColumn(8).getCellEditor()).getRow();
						 
						 if( ((JCheckBoxCellEditor) tabVariables.getColumnModel().getColumn(8).getCellEditor()).getCellEditorValue().equals(true))
						 {
							 /*Set the check box*/
							 tabvariableModel.changeValueAt("true",editedrow,8);

							 for(int i=0; i< tempHeatmaps2VList.size();i++){
								 
								 if(tempHeatmaps2VList.get(i).name.equals(tabvariableModel.getValueAt(editedrow, 0))){
									 
									 
										 tempHeatmaps2VList.get(i).lowerBoundEnabledY = true;
										
										
									 	
									 	 break;
									 
								 }
								 
							 }
							 
							 tabvariableModel.updatetable();
					
							 
							 
						 }else{
							 tabvariableModel.changeValueAt("false",editedrow,8 );

							 for(int i=0; i< tempHeatmaps2VList.size();i++){
								 
								 if(tempHeatmaps2VList.get(i).name.equals(tabvariableModel.getValueAt(editedrow, 0))){
									 
									 
										 tempHeatmaps2VList.get(i).lowerBoundEnabledY = false;
										
										
									 	
									 	 break;
									 
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
					 check.setSelected(((Boolean)value).booleanValue()) ;
					 return check3;
					 }
					 
					 
					 
					 });
				 
				
				
				tabVariables.getColumnModel().getColumn(9).setPreferredWidth(140);
				tabVariables.getColumnModel().getColumn(9).setCellEditor(new CellEditor());
				
				
				
				tabVariables.getColumnModel().getColumn(9).getCellEditor().addCellEditorListener(new EditorListener(){
					 
					 
					 public void editingStopped(ChangeEvent e) {
						 
						 System.out.println("Col 2 editing stopped");
						 
						 int editedrow = ((CellEditor) tabVariables.getColumnModel().getColumn(9).getCellEditor()).getRow();
			    
						 
						 for(int i=0; i< tempHeatmaps2VList.size();i++){
							 
							 if(tempHeatmaps2VList.get(i).name.equals(tabvariableModel.getValueAt(editedrow, 0))){
								 
								 try{
									 tempHeatmaps2VList.get(i).upperBoundY = Double.parseDouble(tabVariables.getColumnModel().getColumn(9).getCellEditor().getCellEditorValue().toString());
									 tabvariableModel.changeValueAt(tabVariables.getColumnModel().getColumn(9).getCellEditor().getCellEditorValue().toString(), editedrow, 9);
									
								 	}catch(Exception ex){
									 
								 		 JOptionPane.showMessageDialog(null,"Parameter must be a numeric expression!");
									 
								 	}
								 	 break;
								 
							 }
							 
						 }
					 }
						 
					 });
				

				/*Column 5 Check box*/
				

				 final JCheckBox check4 = new JCheckBox();
				 
				tabVariables.getColumnModel().getColumn(10).setPreferredWidth(40);
				
				tabVariables.getColumnModel().getColumn(10).setCellEditor(new JCheckBoxCellEditor(check4));
				tabVariables.getColumnModel().getColumn(10).getCellEditor().getClass();
				tabVariables.getColumnModel().getColumn(10).getCellEditor().addCellEditorListener(new EditorListener(){
					 
					 
					 public void editingStopped(ChangeEvent e) {
						 
						 
						 int editedrow = ((JCheckBoxCellEditor) tabVariables.getColumnModel().getColumn(10).getCellEditor()).getRow();
						 
						 if( ((JCheckBoxCellEditor) tabVariables.getColumnModel().getColumn(10).getCellEditor()).getCellEditorValue().equals(true))
						 {
							 /*Set the check box*/
							 tabvariableModel.changeValueAt("true",editedrow,10);

							 for(int i=0; i< tempHeatmaps2VList.size();i++){
								 
								 if(tempHeatmaps2VList.get(i).name.equals(tabvariableModel.getValueAt(editedrow, 0))){
									 
									 
										 tempHeatmaps2VList.get(i).upperBoundEnabledY = true;
										
										
									 	
									 	 break;
									 
								 }
								 
							 }
							 
							 tabvariableModel.updatetable();
					
							 
							 
						 }else{
							 tabvariableModel.changeValueAt("false",editedrow,10);

							 for(int i=0; i< tempHeatmaps2VList.size();i++){
								 
								 if(tempHeatmaps2VList.get(i).name.equals(tabvariableModel.getValueAt(editedrow, 0))){
									 
									 
										 tempHeatmaps2VList.get(i).upperBoundEnabledY = false;
					
									 	 break;
									 
								 }
								 
							 }
							 
							 tabvariableModel.updatetable();
							
						 }
						 
					    }
					 
					 
					 
				 });
				 
				 
				tabVariables.getColumnModel().getColumn(10).setCellRenderer(new DefaultTableCellRenderer() {
					 public Component getTableCellRendererComponent(JTable table,
					 Object value, boolean isSelected, boolean hasFocus, int row, int
					 column) {
						 
						 System.out.println(value);
					 check4.setSelected(((Boolean)value).booleanValue()) ;
					 return check4;
					 }
					 
					 
					 
					 });
				 
				

			
				/*Column 4: Filter 1*/
				
				tabVariables.getColumnModel().getColumn(11).setPreferredWidth(140);
				
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
				
				tabVariables.getColumnModel().getColumn(11).setCellEditor(new SpecificComboBoxEditor(filter));
				tabVariables.getColumnModel().getColumn(11).setCellRenderer(new SpecificComboBoxRenderer(filter));
				
				
				
				tabVariables.getColumnModel().getColumn(11).getCellEditor().addCellEditorListener(new EditorListener(){
					 
					 
					 public void editingStopped(ChangeEvent e) {
						 
						 System.out.println("Editing ended!");
						 
					 }
					 
					 
				});

				
				/*Column 5: Filter 2*/
				
				tabVariables.getColumnModel().getColumn(12).setPreferredWidth(140);
				
				tabVariables.getColumnModel().getColumn(12).setCellEditor(new SpecificComboBoxEditor(filter));
				tabVariables.getColumnModel().getColumn(12).setCellRenderer(new SpecificComboBoxRenderer(filter));
				
				
				
				tabVariables.getColumnModel().getColumn(4).getCellEditor().addCellEditorListener(new EditorListener(){
					 
					 
					 public void editingStopped(ChangeEvent e) {
						 
						 System.out.println("Editing ended!");
						 
					 }
					 
					 
				});
				
				
				/*+++++++++++++++++++++++++++++++++++++++++++++++*/
				
				
				
				
				/*Column 5*/
				
				
				tabVariables.getColumnModel().getColumn(13).setPreferredWidth(140);
				tabVariables.getColumnModel().getColumn(13).setCellEditor(new CellEditor());
				
				
				
				tabVariables.getColumnModel().getColumn(13).getCellEditor().addCellEditorListener(new EditorListener(){
					 
					 
					 public void editingStopped(ChangeEvent e) {
						 
						 System.out.println("Col 2 editing stopped");
						 
						 int editedrow = ((CellEditor) tabVariables.getColumnModel().getColumn(5).getCellEditor()).getRow();
			    
						 
						 for(int i=0; i< tempHeatmaps2VList.size();i++){
							 
							 if(tempHeatmaps2VList.get(i).name.equals(tabvariableModel.getValueAt(editedrow, 0))){
								 
								 try{
									 tempHeatmaps2VList.get(i).tmin = Integer.parseInt(tabVariables.getColumnModel().getColumn(13).getCellEditor().getCellEditorValue().toString());
									 tabvariableModel.changeValueAt(tabVariables.getColumnModel().getColumn(13).getCellEditor().getCellEditorValue().toString(), editedrow, 13);
									
								 	}catch(Exception ex){
									 
									 JOptionPane.showMessageDialog(null,"Parameter must be an integer!");
									 
								 	}
								 	 break;
								 
							 }
							 
						 }
					 }
						 
					 });
						 
						 
						 
			    
				/*Column 3*/
				
				tabVariables.getColumnModel().getColumn(14).setPreferredWidth(140);
				tabVariables.getColumnModel().getColumn(14).setCellEditor(new CellEditor());
				
				
				
				tabVariables.getColumnModel().getColumn(14).getCellEditor().addCellEditorListener(new EditorListener(){
					 
					 
					 public void editingStopped(ChangeEvent e) {
						 
						 System.out.println("Col 2 editing stopped");
						 
						 int editedrow = ((CellEditor) tabVariables.getColumnModel().getColumn(14).getCellEditor()).getRow();
			    
						 for(int i=0; i< tempHeatmaps2VList.size();i++){
							 
							 if(tempHeatmaps2VList.get(i).name.equals(tabvariableModel.getValueAt(editedrow, 0))){
								 
								 try{
									 tempHeatmaps2VList.get(i).tmax = Integer.parseInt(tabVariables.getColumnModel().getColumn(14).getCellEditor().getCellEditorValue().toString());
									 tabvariableModel.changeValueAt(tabVariables.getColumnModel().getColumn(14).getCellEditor().getCellEditorValue().toString(), editedrow, 14);
									
								 	}catch(Exception ex){
									 
									 JOptionPane.showMessageDialog(null,"Parameter must be an integer!");
									 
								 	}
								 	 break;
								 
							 }
							 
						 }
					 }
						 
					 });
				
				
				
				
				/*Column 4*/
				
				
/*Column 3*/
				
				tabVariables.getColumnModel().getColumn(15).setPreferredWidth(140);
				tabVariables.getColumnModel().getColumn(15).setCellEditor(new CellEditor());
				
				
				
				tabVariables.getColumnModel().getColumn(15).getCellEditor().addCellEditorListener(new EditorListener(){
					 
					 
					 public void editingStopped(ChangeEvent e) {
						 
						 System.out.println("Col 2 editing stopped");
						 
						 int editedrow = ((CellEditor) tabVariables.getColumnModel().getColumn(15).getCellEditor()).getRow();
			    
						 for(int i=0; i< tempHeatmaps2VList.size();i++){
							 
							 if(tempHeatmaps2VList.get(i).name.equals(tabvariableModel.getValueAt(editedrow, 0))){
								 
								 try{
									 tempHeatmaps2VList.get(i).bins = Integer.parseInt(tabVariables.getColumnModel().getColumn(15).getCellEditor().getCellEditorValue().toString());
									 tabvariableModel.changeValueAt(tabVariables.getColumnModel().getColumn(15).getCellEditor().getCellEditorValue().toString(), editedrow, 15);
									
								 	}catch(Exception ex){
									 
									 JOptionPane.showMessageDialog(null,"Parameter must be an integer!");
									 
								 	}
								 	 break;
								 
							 }
							 
						 }
					 }
						 
					 });
				
			
				
				
				tabVariables.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
				
				System.out.println("xx "+tabVariables.getColumnCount());

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
							 
							 
		
							 
							 for(int i=0; i< tempHeatmaps2VList.size();i++){
								 
								 if(tempHeatmaps2VList.get(i).name.equals(tabvariableModel.getValueAt(editedRow, 0))){
									 
									 /*Column 3 => method*/
									 if(editedCol==2){

										 tempHeatmaps2VList.get(i).Filter1= cComboBox.getSelectedItem().toString();
									 }else if(editedCol==3){
										 /*Column 5 => Filter 2*/
										 tempHeatmaps2VList.get(i).Filter2 = cComboBox.getSelectedItem().toString();
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
	
	
	
	
	
	
	
	private class HeatMapTable extends JScrollPane{
		
		
		JScrollPane listScrollFraction;
		

		HeatMapTable(String numerator, String denominator){
			
			
	
			
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
			       return "Heat Map for Variables:"; 
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
	
	
	ArrayList<PlottingSettings.Heatmaps2V> heatmaps2V = new ArrayList<PlottingSettings.Heatmaps2V>();
	
	
	TabModel(String[] colName, String agentName){

		
				for(int k=0; k<tempHeatmaps2VList.size();k++){
				
				
					
						
						if(tempHeatmaps2VList.get(k).histBelongsTo.equals(agentName)){
							
							
							heatmaps2V.add(tempHeatmaps2VList.get(k));
							
							
						}

		}
		
		System.out.println("heatmaps2V"+heatmaps2V.size()+"  tempHeatmaps2VList.size()   "+tempHeatmaps2VList.size());

		columnName = colName;
	
		
	}
	
	
	
	public int getRowCount()
   {
	
	return heatmaps2V.size();
	
 }
	
	
   public int getColumnCount()
   {
   return 16;
 }
   public Object getValueAt( int row, int col)
   {
	   
	
	   
	  
		 //  System.out.println("getValueAt timeSeriesSelection.get(i).isntancesOfvariable.size()"+timeSeriesSelection.get(i).isntancesOfvariable.size());
	   				if(col==0)
	   					return heatmaps2V.get(row).name;
	   				else if(col==1)
					   return heatmaps2V.get(row).getName1();
	   				else if(col==2)
	   				{
					   if( heatmaps2V.get(row).lowerBoundEnabledX)
							 return  heatmaps2V.get(row).lowerBoundX;
				 		else
				 			return "";
					   
					   }
				   else if(col==3)
					   return heatmaps2V.get(row).lowerBoundEnabledX;
				   else if(col==4)
					   if( heatmaps2V.get(row).upperBoundEnabledX)
							 return  heatmaps2V.get(row).upperBoundX;
				 		else
				 			return "";
				   else if(col==5)
					   return heatmaps2V.get(row).upperBoundEnabledX;
	   				
	   			
				   else if(col==6)
					   return heatmaps2V.get(row).getName2();
				   else if(col==7)
	   				{
					   if( heatmaps2V.get(row).lowerBoundEnabledY)
							 return  heatmaps2V.get(row).lowerBoundY;
				 		else
				 			return "";
					   
					   }
				   else if(col==8)
					   return heatmaps2V.get(row).lowerBoundEnabledY;
				   else if(col==9)
					   if( heatmaps2V.get(row).upperBoundEnabledY)
							 return  heatmaps2V.get(row).upperBoundY;
				 		else
				 			return "";
				   else if(col==10)
					   return heatmaps2V.get(row).upperBoundEnabledY;
				  
				   else if(col==11)
					   return heatmaps2V.get(row).Filter1;
				   else if(col==12)
					   return heatmaps2V.get(row).Filter2;
				   else if(col==13)
					   return heatmaps2V.get(row).tmin;
				   else if(col==14)
					   return heatmaps2V.get(row).tmax;
				   else if(col==15)
					   return heatmaps2V.get(row).bins;
				  
				   else
					   return null;
			   
			
   }
	   
	   
	   
	   
	   
	   
   
   
   public String getColumnName(int column) {  
       return columnName[column]; 
   }
   
   
   public void changeValueAt(String value, int row, int col) {  
       
	   System.out.println("changeValueAt");

	   if(col==0)
		   heatmaps2V.get(row).name=value;
	   
	   
	   
	   
	   else if(col==2)
		   heatmaps2V.get(row).lowerBoundX=Double.parseDouble(value);
	   else if(col==3)
		   heatmaps2V.get(row).lowerBoundEnabledX=returnBoolean(value);
	   else if(col==4)
		   heatmaps2V.get(row).upperBoundX=Double.parseDouble(value);
	   else if(col==5)
		   heatmaps2V.get(row).upperBoundEnabledX=returnBoolean(value);
	   
	   else if(col==7)
		   heatmaps2V.get(row).lowerBoundY=Double.parseDouble(value);
	   else if(col==8)
		   heatmaps2V.get(row).lowerBoundEnabledY=returnBoolean(value);
	   else if(col==9)
		   heatmaps2V.get(row).upperBoundY=Double.parseDouble(value);
	   else if(col==10)
		   heatmaps2V.get(row).upperBoundEnabledY=returnBoolean(value);
	   
	
	   else if (col==11)
		   heatmaps2V.get(row).Filter1=value;
	   else if(col==12)
		   heatmaps2V.get(row).Filter2=value;
	   else if(col==12)
		   heatmaps2V.get(row).tmin =Integer.parseInt(value);
	   else if(col==14)
		    heatmaps2V.get(row).tmax=Integer.parseInt(value);
	   else if(col==15)
		   heatmaps2V.get(row).bins=Integer.parseInt(value);
	 
       this.fireTableDataChanged();  
         
         
   }  
   
   public boolean returnBoolean(String input){
	   
	   if(input.equals("true"))
		   return true;
	   else
		   return false;
	   
   }
   
   
   
 public void RemoveRowAt(int row) {  
       
	   
	 heatmaps2V.remove(row);
	   	   
	   this.fireTableDataChanged();  
	   
        

}
   
   
   public void updatetable() {  
       
       this.fireTableDataChanged();    
         
   }  
   
   public boolean isCellEditable(int row, int column){ 
      
	   if(column==1 || column ==2){
   		return false;
   	}else {
   		
   		return true;
   	}
   }
  
		
}


public class PanelVariable extends JPanel{
	
	DefaultListModel variableListModel1;
	JList variableList1;
	SortedListModel sortedModel1;
	
	int variableNo;
	Variable variable;
	
	GridBagConstraints fil;
	
	PanelVariable(int VarNo){
		
		super();
		
		variableNo = VarNo;
		
		
		variable= null;
		
		fil = new GridBagConstraints();
		
		variableListModel1 = new DefaultListModel();
			variableList1 = new JList(variableListModel1){
				

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
					
					String variableStr = variableListModel1.get(sortedModel1.toUnsortedModelIndex(variableList1.getSelectedIndex())).toString();
					
					 for(int i=0; i < tempAgentSettingList.size();i++){
				        	
				        	if(tempAgentSettingList.get(i).agentName.equals(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.agentName)){
				        		
				        		for(int j=0; j< tempAgentSettingList.get(i).variableList.size();j++){

				        			if(tempAgentSettingList.get(i).variableList.get(j).name.equals(variableStr)){
				        				
				        				
				        				variable = tempAgentSettingList.get(i).variableList.get(j);
				        				
				        				panel5.remove(heatmapSelectionTable);
				        				panel5.validate();
				        				
				        				HeatMapTable heatmapTable;
				        				if(variableNo==1){

				        					nameElement1 = variable.name;
				        					variable1 = variable;
				        					
				        				}else{
				        					
				        					nameElement2 = variable.name;	
				        					variable2 = variable;
				        				}
				        				
				        				
				        				if(!nameElement1.equals("") && !nameElement2.equals("")){
											
											okayButton.setEnabled(true);
											
										}
				        			
				        				heatmapTable = new HeatMapTable(nameElement1,nameElement2);
				        				
				        				heatmapSelectionTable = heatmapTable.listScrollFraction;
				        				heatmapSelectionTable.setPreferredSize(new Dimension(400, 60)); 
					        				
				   		 	   			fil.gridx =3;fil.gridy =3;
				   		 	   			panel5.add(heatmapSelectionTable,fil);
					        				
				   		 	   			panel5.validate();
				   		   				
				   		   				
									
				        				
				        			}
				 
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
			

			JLabel label11 = new JLabel("Variable "+variableNo +":");
			
			fil.gridx =0;fil.gridy =1;
			add(label11,fil);


			fil.gridx =2;fil.gridy =1;
			
			JScrollPane variableListScroll = new JScrollPane(variableList1);
			variableListScroll.setPreferredSize(new Dimension(200,150));
			
			add(variableListScroll,fil);
		
		
	}
	
	
	
	
}



private class PanelRatio extends JPanel{
	
	DefaultListModel variableListModel1;
	JList variableList1;
	SortedListModel sortedModel1, sortedModel2;
	GridBagConstraints fil;
	JScrollPane scrollFractionTable;
	
	DefaultListModel variableListModel2;
	JList variableList2 ;
	
	int variableNo;
	PlottingSettings.AgentRatio agentRatio;
	
	String var1, var2;
	
	PanelRatio(int VarNo){
		
	
		var1 = "";
		var2 = "";
		
		
		
		variableNo = VarNo;
		
		
		agentRatio= null;	
	
	
		fil = new GridBagConstraints();
		fil.insets = new Insets( 5, 5, 5, 5);
		
		
		variableListModel1 = new DefaultListModel();
		variableList1 = new JList(variableListModel1){
			

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
				
				var1 = variableListModel1.get(sortedModel1.toUnsortedModelIndex(variableList1.getSelectedIndex())).toString();
				

				/*Redraw the table*/
				remove(scrollFractionTable);
				validate();
				
				
				FractionTable fractionTable = new FractionTable(var1,var2, "Ratio of");
	   				
 	   				scrollFractionTable = fractionTable.listScrollFraction;
 	   				scrollFractionTable.setPreferredSize(new Dimension(200, 60)); 
    				
	 	   			fil.gridx =3;fil.gridy =3;
	   				add(scrollFractionTable,fil);
    				
	   				validate();
	   				
	   				if(!var1.equals("")&&!var2.equals("")){
	   					
	   					Variable numerator = null;
	   					Variable denominator= null;
	   					
	   					
	   					for(int i=0; i < tempAgentSettingList.size();i++){
				        	
				        	if(tempAgentSettingList.get(i).agentName.equals(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.agentName)){
				        		
				        		for(int j=0; j< tempAgentSettingList.get(i).variableList.size();j++){

				        			if(tempAgentSettingList.get(i).variableList.get(j).name.equals(var1)){
				        				
				        				
				        				numerator = tempAgentSettingList.get(i).variableList.get(j);
				        				
				        			}
				        			if(tempAgentSettingList.get(i).variableList.get(j).name.equals(var2)){
				        				
				        				
				        				denominator = tempAgentSettingList.get(i).variableList.get(j);
				        				
				        			}
				 
				        		}
				        	}
				        }
	   					
	   					
	   					agentRatio = (new PlottingSettings()).new AgentRatio( numerator, denominator);
	   					
	   					if(variableNo == 1){
	   						
	   						nameElement1 = agentRatio.ratioName;
	   						agentRatio1 = agentRatio;
	   						
	   					}else{
	   						
	   						nameElement2 = agentRatio.ratioName;
	   						agentRatio2 = agentRatio;
	   						
	   					}
	   					
	   					
	   					if(!nameElement1.equals("") && !nameElement2.equals("")){
							
							okayButton.setEnabled(true);
							
						}
	   				
        				
        				panel5.remove(heatmapSelectionTable);
        				panel5.validate();
        				
 
        			
        				HeatMapTable heatmapTable = new HeatMapTable(nameElement1,nameElement2);
        				
        				heatmapSelectionTable = heatmapTable.listScrollFraction;
        				heatmapSelectionTable.setPreferredSize(new Dimension(400, 60)); 
	        				
   		 	   			fil.gridx =3;fil.gridy =3;
   		 	   			panel5.add(heatmapSelectionTable,fil);
	        				
   		 	   			panel5.validate();
	   					
	   					
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
		

		
		
	JLabel label11 = new JLabel("Numerator "+variableNo+":");
	
	fil.gridx =0;fil.gridy =1;
	add(label11,fil);


	fil.gridx =2;fil.gridy =1;
	
	JScrollPane variableListScroll = new JScrollPane(variableList1);
	variableListScroll.setPreferredSize(new Dimension(200,150));
	
	add(variableListScroll,fil);


	
	variableListModel2 = new DefaultListModel();
	variableList2 = new JList(variableListModel2){
			

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
		
		
		sortedModel2 = new SortedListModel(variableListModel2,SortedListModel.SortOrder.UNORDERED);
		variableList2.setModel(sortedModel2);
	
	
	variableList2.addMouseListener(new MouseListener(){
	
	public void mouseClicked(MouseEvent e) {
	
		System.out.println("Right click");
		
		if(SwingUtilities.isRightMouseButton(e)){
			
			System.out.println("Right click");
			
			
			if(sortedModel2.sortOrderOfList==SortedListModel.SortOrder.ASCENDING ){
				
											
				sortedModel2.setSortOrder(SortedListModel.SortOrder.DESCENDING);
				variableList2.setModel(sortedModel1);
	
			}else if(sortedModel2.sortOrderOfList==SortedListModel.SortOrder.DESCENDING){
				
				sortedModel2.setSortOrder(SortedListModel.SortOrder.UNORDERED);
				variableList2.setModel(sortedModel1);
				
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
				
				
				var2 = variableListModel1.get(sortedModel2.toUnsortedModelIndex(variableList2.getSelectedIndex())).toString();
	   		        				
        				/*Redraw the table*/
        				remove(scrollFractionTable);
        				validate();
        				
        				
        				FractionTable fractionTable = new FractionTable(var1,var2, "Ratio of");
 	   				
	 	   				scrollFractionTable = fractionTable.listScrollFraction;
	 	   				scrollFractionTable.setPreferredSize(new Dimension(200, 60)); 
        				
		 	   			fil.gridx =3;fil.gridy =3;
		   				add(scrollFractionTable,fil);
        				
		   				validate();
		   				
   		   			if(!var1.equals("")&&!var2.equals("")){
   						Variable numerator = null;
	   					Variable denominator= null;
	   					
	   					
	   					for(int i=0; i < tempAgentSettingList.size();i++){
				        	
				        	if(tempAgentSettingList.get(i).agentName.equals(agentJPanelList.get(tabbedPane.getSelectedIndex()).agent.agentName)){
				        		
				        		for(int j=0; j< tempAgentSettingList.get(i).variableList.size();j++){

				        			if(tempAgentSettingList.get(i).variableList.get(j).name.equals(var1)){
				        				
				        				
				        				numerator = tempAgentSettingList.get(i).variableList.get(j);
				        				
				        			}
				        			if(tempAgentSettingList.get(i).variableList.get(j).name.equals(var2)){
				        				
				        				
				        				denominator = tempAgentSettingList.get(i).variableList.get(j);
				        				
				        			}
				 
				        		}
				        	}
				        }
	   					
	   					
	   					agentRatio = (new PlottingSettings()).new AgentRatio( numerator, denominator);
	   					
	   					
						if(variableNo == 1){
	   						
	   						nameElement1 = agentRatio.ratioName;
	   						agentRatio1 =agentRatio;
	   						
	   					}else{
	   						
	   						nameElement2 = agentRatio.ratioName;
	   						agentRatio2 =agentRatio;
	   						
	   					}
						
						panel5.remove(heatmapSelectionTable);
        				panel5.validate();
        				
        				HeatMapTable heatmapTable;
        				
        				if(!nameElement1.equals("") && !nameElement2.equals("")){
							
							okayButton.setEnabled(true);
							
						}
        			
        			
        				heatmapTable = new HeatMapTable(nameElement1,nameElement2);
        				
        				heatmapSelectionTable = heatmapTable.listScrollFraction;
        				heatmapSelectionTable.setPreferredSize(new Dimension(400, 60));  
	        				
   		 	   			fil.gridx =3;fil.gridy =3;
   		 	   			panel5.add(heatmapSelectionTable,fil);
	        				
   		 	   			panel5.validate();
	   					
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
		

		JLabel label21 = new JLabel("Denominator "+variableNo+":");
		
		fil.gridx =0;fil.gridy =4;
		add(label21,fil);
				
		
		fil.gridx =2;fil.gridy =4;
		JScrollPane variableListScroll2 = new JScrollPane(variableList2);
		variableListScroll2.setPreferredSize(new Dimension(200,150));
		add(variableListScroll2,fil);
		
		/*XXX*/
		
		FractionTable fractionTable = new FractionTable("","","Ratio of");
		
		scrollFractionTable = fractionTable.listScrollFraction;
		scrollFractionTable.setPreferredSize(new Dimension(200, 60)); 

	
		
		fil.gridx =3;fil.gridy =3;
		add(scrollFractionTable,fil);
	
	
}


private class FractionTable extends JScrollPane{
		
		
		JScrollPane listScrollFraction;
		

		FractionTable(String numerator, String var1, String colnames){
			
			
			
			
			FractionTableModel tabvariableModel = new FractionTableModel(numerator,var1, colnames );
			JTable table = new JTable(tabvariableModel);

			
			table.setRowHeight(20);
			
			/*Column 1*/
			table.getColumnModel().getColumn(0).setPreferredWidth(140);
			
			

			
			listScrollFraction = new JScrollPane(table);
		}
			
		}
	
	
		

private class FractionTableModel extends AbstractTableModel{
		
	String numerator, variable1, colName;
	

	FractionTableModel(String num, String den,String colNa){
		
		numerator = num;
		variable1 = den;
		colName = colNa;
		
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
				
				return variable1;
				
			}
			
		}
		
		
		  public String getColumnName(int column) {  
		       return colName; 
		   }
		
	
		  
	   public boolean isCellEditable(int row, int column){ 
		      

	   			return false;
	   	
	   }
		
		
	}
	
	

}




public void resetSelectedeFlags(){
	
	
	for(int i =0; i < tempAgentSettingList.size();i++){
		
		for(int j=0; j< tempAgentSettingList.get(i).variableList.size();j++){
			
			tempAgentSettingList.get(i).variableList.get(j).isSelectedForHeatmaps2V = false;
			
			
		}
	}
	
	
		
	for(int i=0; i<tempHeatmaps2VList.size();i++){
		
		for(int j=0; j< tempAgentSettingList.size();j++){
			
			if(tempHeatmaps2VList.get(i).histBelongsTo.equals(tempAgentSettingList.get(j).agentName)){
				
				
				if(tempHeatmaps2VList.get(i).isVariable1){
					
					
					for(int k=0; k< tempAgentSettingList.get(j).variableList.size();k++){
						
						if(tempHeatmaps2VList.get(i).variable1.name.equals(tempAgentSettingList.get(j).variableList.get(k).name)){
						
							tempAgentSettingList.get(j).variableList.get(k).isSelectedForHeatmaps2V = true;
							break;
						
						}
					
					}
					
					
					
				}else{
					
					for(int k=0; k< tempAgentSettingList.get(j).variableList.size();k++){
						
						if(tempHeatmaps2VList.get(i).agentRatio1.numerator.name.equals(tempAgentSettingList.get(j).variableList.get(k).name)){
						
							tempAgentSettingList.get(j).variableList.get(k).isSelectedForHeatmaps2V = true;
							break;
						
						}
					
					}
					
					
					for(int k=0; k< tempAgentSettingList.get(j).variableList.size();k++){
						
						if(tempHeatmaps2VList.get(i).agentRatio1.denominator.name.equals(tempAgentSettingList.get(j).variableList.get(k).name)){
						
							tempAgentSettingList.get(j).variableList.get(k).isSelectedForHeatmaps2V = true;
							break;
						
						}
					
					}
				}
				
			
				
				
				if(tempHeatmaps2VList.get(i).isVariable2){
					
					
					for(int k=0; k< tempAgentSettingList.get(j).variableList.size();k++){
						
						if(tempHeatmaps2VList.get(i).variable2.name.equals(tempAgentSettingList.get(j).variableList.get(k).name)){
						
							tempAgentSettingList.get(j).variableList.get(k).isSelectedForHeatmaps2V = true;
							break;
						
						}
					
					}
					
					
					
				}else{
					
					for(int k=0; k< tempAgentSettingList.get(j).variableList.size();k++){
						
						if(tempHeatmaps2VList.get(i).agentRatio2.numerator.name.equals(tempAgentSettingList.get(j).variableList.get(k).name)){
						
							tempAgentSettingList.get(j).variableList.get(k).isSelectedForHeatmaps2V = true;
							break;
						
						}
					
					}
					
					
					for(int k=0; k< tempAgentSettingList.get(j).variableList.size();k++){
						
						if(tempHeatmaps2VList.get(i).agentRatio2.denominator.name.equals(tempAgentSettingList.get(j).variableList.get(k).name)){
						
							tempAgentSettingList.get(j).variableList.get(k).isSelectedForHeatmaps2V = true;
							break;
						
						}
					
					}
				}

				
		
		}
	}

		
		
		
	
	
	
	
	
	}
}


		

}


