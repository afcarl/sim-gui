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










public class TabPlotting extends JPanel{
	
	
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
	
	TabSingleTimeSeries tabSingleTimeSeries;
	TabMultipleTimeSeries tabMultipleTimeSeries;
	TabDistributions tabDistributions;
	JScrollPane plottingScrollPane, plottingScrollPane2, plottingScrollPane3;
	 
	//GridBagConstraints fA = new GridBagConstraints();
	
	TabPlotting(){
		
		 setLayout(new BorderLayout());
		
		
		tabSingleTimeSeries = new TabSingleTimeSeries();
		
		plottingTabbedPane = new JTabbedPane();
		
		tabMultipleTimeSeries = new TabMultipleTimeSeries();
		
		
		tabDistributions =new TabDistributions();

	    //this.setSize(700, 1100);
	 //  fA.insets = new Insets( 5, 5, 5, 5);
	    
	   
      	
      	
      	 plottingScrollPane = new JScrollPane(tabSingleTimeSeries);
      	plottingScrollPane.setPreferredSize(new Dimension(700, 600));
      	
      	 plottingScrollPane2 = new JScrollPane(tabMultipleTimeSeries);
       	plottingScrollPane2.setPreferredSize(new Dimension(700, 600));
       	
        plottingScrollPane3 = new JScrollPane(tabDistributions);
      	plottingScrollPane3.setPreferredSize(new Dimension(700, 600));
      	
      	
      	plottingTabbedPane.addTab("Time Series",plottingScrollPane);
      	plottingTabbedPane.addTab("Multiple Time Series",plottingScrollPane2);
      	plottingTabbedPane.addTab("Agent Distributions",plottingScrollPane3);
      	
      	
      	plottingTabbedPane.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
            	
            	int selectedIndex = plottingTabbedPane.getSelectedIndex();
            	
            	if(selectedIndex==0){
            		
            		tabSingleTimeSeries.redrawAllTables();
            		
            	}else if(selectedIndex==2){
            		
            		tabDistributions.redrawAllTables();
            		
            	}
            	
            }
          });
	    
	    add(plottingTabbedPane, BorderLayout.NORTH);
		
		panel2();
		
		
		
		
		
		
		
	}
	
	
	
	
	
	
	void panel2(){
	
	lowerArea = new JPanel();
    lowerArea.setBorder( BorderFactory.createLineBorder( Color.black ) );
	
    lowerArea.setSize(800,200);
    lowerArea.setBackground(Color.white);
	
    lowerArea.setLayout(new GridBagLayout());
	GridBagConstraints h = new GridBagConstraints();
	h.insets = new Insets( 5, 25, 5, 25);
	
	JPanel checkBoxArea = new JPanel();
	checkBoxArea.setLayout(new GridBagLayout());
	
	GridBagConstraints h1 = new GridBagConstraints();
	h1.insets = new Insets( 5, 5, 5, 5);
	
	singleRunAnalyisLabel= new JLabel("Single Run Analysis");
	batchRunAnalyisLabel= new JLabel("Batch Run Analysis");
	parameterAnalyisLabel= new JLabel("Parameter Analysis");

	singleRunAnalyisCheckBox = new  JCheckBox (); 
	batchRunAnalyisCheckBox = new  JCheckBox (); 
	parameterAnalyisCheckBox = new  JCheckBox (); 
	
	
	
	/*Default values:*/
	
	if(PlottingSettings.singleRunAnalyis){
		
		singleRunAnalyisCheckBox.setSelected(true);
	}
	
	
	if(PlottingSettings.batchRunAnalyis){
			
		batchRunAnalyisCheckBox.setSelected(true);
	}

	if(PlottingSettings.parameterAnalyis){
		
		parameterAnalyisCheckBox.setSelected(true);
	}
	
	
	/*Checkbox listener: Set values */
	
	singleRunAnalyisCheckBox.addActionListener(new ActionListener(){
		
		public void actionPerformed(ActionEvent evt) {
			
			if(singleRunAnalyisCheckBox.isSelected()){
				
				PlottingSettings.singleRunAnalyis = true;
				
			}else{
				
				PlottingSettings.singleRunAnalyis = false;
				
			}
			
		}
	});
	
	batchRunAnalyisCheckBox.addActionListener(new ActionListener(){
		
		public void actionPerformed(ActionEvent evt) {
			
			if(batchRunAnalyisCheckBox.isSelected()){
				
				PlottingSettings.batchRunAnalyis = true;
				
			}else{
				
				PlottingSettings.batchRunAnalyis = false;
				
			}
			
		}
	});
	
	
	parameterAnalyisCheckBox.addActionListener(new ActionListener(){
		
		public void actionPerformed(ActionEvent evt) {
			
			if(parameterAnalyisCheckBox.isSelected()){
				
				PlottingSettings.parameterAnalyis = true;
				
			}else{
				
				PlottingSettings.parameterAnalyis = false;
				
			}
			
		}
	});

	
	/*Add textfields and check boxes to the GUI*/
	h1.gridx = 0;
	h1.gridy=0;
	checkBoxArea.add(singleRunAnalyisLabel,h1);
	h1.gridx = 1;
	h1.gridy=0;
	checkBoxArea.add(singleRunAnalyisCheckBox,h1);
	
	
	h1.gridx = 0;
	h1.gridy=1;
	checkBoxArea.add(batchRunAnalyisLabel,h1);
	h1.gridx = 1;
	h1.gridy=1;
	checkBoxArea.add(batchRunAnalyisCheckBox,h1);
	
	
	h1.gridx = 0;
	h1.gridy=2;
	checkBoxArea.add(parameterAnalyisLabel,h1);
	h1.gridx = 1;
	h1.gridy=2;
	checkBoxArea.add(parameterAnalyisCheckBox,h1);
	
	h.gridx = 0;
	h.gridy=0;
	lowerArea.add(checkBoxArea,h);
	

	
	JPanel plottingSelectionArea = new JPanel();
	plottingSelectionArea.setLayout(new GridBagLayout());
	
	GridBagConstraints h2 = new GridBagConstraints();
	h2.insets = new Insets( 5, 5, 5, 25);
	
	/*Time Series*/
	
	h2.gridx = 0;
	h2.gridy=0;
	
	JLabel label1 = new JLabel("Transition Phase:");
	plottingSelectionArea.add(label1,h2);
	
	h2.gridx = 1;
	h2.gridy=0;
	transitionPhase = new JTextField(6);
	transitionPhase.setText(Integer.toString(PlottingSettings.transitionPhase));
	
	plottingSelectionArea.add(transitionPhase,h2);
	
	/*Add listener: launching new Window*/
	
	transitionPhase.addActionListener(new ActionListener() {
		
			public void actionPerformed(ActionEvent e) {
				
				String input = transitionPhase.getText();
				
				try {
					PlottingSettings.transitionPhase = Integer.parseInt(input);
    			  
					transitionPhase.selectAll();
    			}
    			catch(NumberFormatException nFE) {
    				JOptionPane.showMessageDialog(null,"Not an integer"); 
    			}
					
			}		
	});
	

	
	JLabel label2 = new JLabel("Add Legends:");
	
	h2.gridx = 0;
	h2.gridy=1;
	
	plottingSelectionArea.add(label2,h2);
	
	h2.gridx = 1;
	h2.gridy=1;
	makeLegend = new JCheckBox();
	
	
	if(PlottingSettings.addLegend)
		makeLegend.setSelected(true);
	
	plottingSelectionArea.add(makeLegend,h2);
	
	
	
	makeLegend.addActionListener(new ActionListener() {
		
		
		
		public void actionPerformed(ActionEvent e) {
			
			if(makeLegend.isSelected())
				PlottingSettings.addLegend=true;
			else
				PlottingSettings.addLegend=false;
			
		}
		

			
});
	

	
	
	JLabel label3 = new JLabel("Colored Plots:");
	
	h2.gridx = 0;
	h2.gridy=2;
	
	plottingSelectionArea.add(label3,h2);
	
	h2.gridx = 1;
	h2.gridy=2;
	colored = new JCheckBox();
	
	
	if(PlottingSettings.coloured)
		colored.setSelected(true);
	
	plottingSelectionArea.add(colored,h2);
	
	
	
	colored.addActionListener(new ActionListener() {
		
		
		
		public void actionPerformed(ActionEvent e) {
			
			if(colored.isSelected())
				PlottingSettings.coloured=true;
			else
				PlottingSettings.coloured=false;
			
		}
		

			
});
	
	
	JLabel label4 = new JLabel("File Type of Plots:");
	
	h2.gridx = 0;
	h2.gridy=3;
	
	plottingSelectionArea.add(label4,h2);
	
	h2.gridx = 1;
	h2.gridy=3;
	
	
	String[] fileTypes = { "pdf", "eps", "png"};
	
	fileTypePlots = new JComboBox(fileTypes);
	
	if(PlottingSettings.fileTypePlots.equals("pdf")){
		fileTypePlots.setSelectedIndex(0);
	}else if(PlottingSettings.fileTypePlots.equals("eps")){
		
		fileTypePlots.setSelectedIndex(1);
		
	}else{
		
		fileTypePlots.setSelectedIndex(2);
		
	}
	
	
	
	plottingSelectionArea.add(fileTypePlots,h2);
	
	
	
	fileTypePlots.addActionListener(new ActionListener() {
		
		
		
		public void actionPerformed(ActionEvent e) {
			
			JComboBox cb = (JComboBox)e.getSource();
			PlottingSettings.fileTypePlots = (String)cb.getSelectedItem();
			
		}
		

			
});
	
	
	
	
	

	h.gridx = 2;
	h.gridy=0;
	lowerArea.add(plottingSelectionArea,h);
	
	lowerArea.setPreferredSize(new Dimension(this.getSize().width-20,150));
	
	//fA.gridx=0; 
  	//fA.gridy=0;
  	add(lowerArea,BorderLayout.SOUTH);
  	
  	
  	
	
	JPanel topPanel = new JPanel();
	topPanel.setLayout( new BorderLayout() );
	add( topPanel );
	

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

	
			setSize(700, 200);
			setLayout(new GridBagLayout());
			
			
			agentPane.insets = new Insets( 5, 5, 5, 5);
			
			JLabel lab = new JLabel("Time Series for Agent "+agent.agentName);
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
			scrollVariableTable.setPreferredSize(new Dimension(700, 179)); 
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
