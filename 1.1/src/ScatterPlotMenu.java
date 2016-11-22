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


public class ScatterPlotMenu extends JDialog{
	
	JMenuBar menuBar;
	JMenu scatterPlot, menuSettings;
	JMenuItem addScatterPlot ,removeScatterPlot, applyChanges, exit, defaults;
	
	ArrayList<PlottingSettings.ScatterPlots> tempScatterPlots;
	ArrayList<PlottingSettings.Agent> tempAgentInstanceVariable;
	ArrayList<PlottingSettings.RatioInstance>  tempRatioInstanceVariable;
	
	JScrollPane scrollScattertable;
	ScatterPlotTable scatterplotTable;
	
	JScrollPane scrollScatterPlotTable;

	GridBagConstraints ts;
	
	boolean tempRatiosSelectedForScatterPlot;
	
	PlottingSettings.DefaultSettingsScatterPlots tempDefaults;

	ScatterPlotMenu(){
		

		tempScatterPlots = new ArrayList<PlottingSettings.ScatterPlots>();

		for(int i=0; i< PlottingSettings.listOfScatterPlots.size();i++){
			
			
			tempScatterPlots.add(PlottingSettings.listOfScatterPlots.get(i).clone());
			
		}
		
		tempAgentInstanceVariable = new ArrayList<PlottingSettings.Agent> ();
		
		for(int i=0; i< PlottingSettings.listOfAgentsVariableInstances.size();i++){
			
			
			tempAgentInstanceVariable.add(AuxFunctions.DeepCopyAgentVariableInstance(PlottingSettings.listOfAgentsVariableInstances.get(i)));
			
		}
		
		tempRatioInstanceVariable = new ArrayList<PlottingSettings.RatioInstance>();
		
		
		for(int i=0; i< PlottingSettings.listOfRatioInstances.size();i++){
			
			
			tempRatioInstanceVariable.add(AuxFunctions.DeepCopyRatioInstance(PlottingSettings.listOfRatioInstances.get(i)));
			
		}
		
		tempDefaults = PlottingSettings.defaultsScatterPlots.clone();

		
		/*Dialog settings*/	
	    setTitle("Time Scatter Plots");
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

	    			PlottingSettings.listOfScatterPlots = tempScatterPlots;
	    			PlottingSettings.listOfAgentsVariableInstances = tempAgentInstanceVariable;
	    			PlottingSettings.listOfRatioInstances = tempRatioInstanceVariable;
	    		
	    			setVisible(false);
	    			dispose();
	    			
	    			
	    		}else if(choice==1){
	    			
	    			/*Choice is no*/
	    			
	    			PlottingSettings.defaultsScatterPlots = tempDefaults;
	   
	    			setVisible(false);
	    			dispose();
	    			
	    		}
	   
    			
			}
			});
		
		scatterPlot = new JMenu("Time Scatter Plot");
		
		
		addScatterPlot = new JMenuItem("Add Time Scatter Plot");
		
		scatterPlot.add(addScatterPlot);
		
	
		
		addScatterPlot.addActionListener(new ActionListener(){

			JDialog ratioDialog;
			GridBagConstraints fil;
			DefaultListModel selectedAgentListModel1, selectedAgentListModel2;
			JList selectedAgentList1,selectedAgentList2;
			JPanel panel1;
			DefaultListModel variableListModel1,variableListModel2;
			JList variableList1,variableList2;
			JButton okayButton;
			JScrollPane scrollScatterplotSelectionTable;
			
			String agent1, agent2;
			
			PlottingSettings.ScatterPlotComponent tempVariable1;
			PlottingSettings.ScatterPlotComponent tempVariable2;
			
			public void actionPerformed(ActionEvent arg0) {
				
				
				tempVariable1 = null;
				tempVariable2 = null;
				
				ratioDialog = new JDialog();
				ratioDialog.setModal(true);
				
	   			ratioDialog.setSize(800,600);
	   			ratioDialog.setBackground(Color.white);
	   			
				
				ratioDialog.setTitle("Variable Selection for Time Scatter Plot");
				ratioDialog.setLayout(new GridBagLayout());
				fil = new GridBagConstraints();
	   			fil.insets = new Insets( 5, 5, 5, 5);
	   			
	   			
	   			
	   			selectedAgentListModel1 = new DefaultListModel();
	   			selectedAgentList1 = new JList(selectedAgentListModel1);
	   			
	   			selectedAgentList1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	   			
	   			variableListModel1 = new DefaultListModel();
	   			variableList1 = new JList(variableListModel1){
	   				
	   				
	   				public String getToolTipText(MouseEvent evt) {
	   		        // Get item index
						int index = locationToIndex(evt.getPoint());
						
						 Object item = getModel().getElementAt(index);

  
	   		        String tooltip = "";
	   		        
	   		        System.out.println("xxxxx"+item+"    "+agent1);
	   		        
	   		        if(!agent1.equals("Ratios")){
						
						for(int i=0; i< tempAgentInstanceVariable.size();i++){
							
							if(agent1.equals(tempAgentInstanceVariable.get(i).agentName)){
								
								for(int j=0; j<tempAgentInstanceVariable.get(i).listOfVariableInstances.size();j++ ){
									
									if(item.equals(tempAgentInstanceVariable.get(i).listOfVariableInstances.get(j).instanceName)){
										
										
										tooltip = returnVariableDescription(tempAgentInstanceVariable.get(i).listOfVariableInstances.get(j));
								}
							}
						}
						}
						}else{
							
							for(int i=0; i <tempRatioInstanceVariable.size(); i++){
								
								if(item.equals(tempRatioInstanceVariable.get(i).ratioInstanceName)){
									
									tooltip = "Ratio:  Numerator: "+returnVariableDescription(tempRatioInstanceVariable.get(i).numerator)+"\n Denominator: "+returnVariableDescription(tempRatioInstanceVariable.get(i).denominator);
									
									
								}
										
							}
							
							
							
						}
	   		        
	   		        
	   		        // Return the tool tip text
	   		        return tooltip;
	   		    }
	   			};
	   			
	   			variableList1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	   			
	   				
   				for(int i=0; i<tempAgentInstanceVariable.size();i++){
					
					if(tempAgentInstanceVariable.get(i).isSelected){
						
						selectedAgentListModel1.addElement(tempAgentInstanceVariable.get(i).agentName);
						
					}
					
				}
   				
   				/*If ratios are selected in the variable list add ratios*/
				if(tempRatioInstanceVariable.size()>0){
					
					selectedAgentListModel1.addElement("Ratios");
				}
   				
   				/*XXX*/
   				
   				selectedAgentList1.addMouseListener(new MouseListener(){
					
					public void mouseClicked(MouseEvent e) {

						System.out.println("One Click in row");
						

						if(e.getClickCount()==2){
							
							System.out.println("Double Click in row");
							
							if(!selectedAgentList1.getModel().getElementAt(selectedAgentList1.locationToIndex(e.getPoint())).equals("Ratios"))
							{
								for(int i=0; i<tempAgentInstanceVariable.size();i++){
	
									if(tempAgentInstanceVariable.get(i).agentName.equals(selectedAgentList1.getModel().getElementAt(selectedAgentList1.locationToIndex(e.getPoint())))){
										
										variableListModel1.removeAllElements();
										
										agent1 = selectedAgentList1.getModel().getElementAt(selectedAgentList1.locationToIndex(e.getPoint())).toString();
										
										
										for(int j=0; j < tempAgentInstanceVariable.get(i).listOfVariableInstances.size(); j++){
												
											variableListModel1.addElement(tempAgentInstanceVariable.get(i).listOfVariableInstances.get(j).instanceName);
													
										}
							
										break;	
									}
								
								
								}
							
							}else{
								
								
								variableListModel1.removeAllElements();
								
								for(int i=0; i <tempRatioInstanceVariable.size(); i++){
									
									variableListModel1.addElement(tempRatioInstanceVariable.get(i).ratioInstanceName);
									
									agent1 = "Ratios";
											
								}
								
							}
							
							
							if(tempVariable1!=null && tempVariable2!= null){
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
							
								if(!agent1.equals("Ratios")){
								
								for(int i=0; i< tempAgentInstanceVariable.size();i++){
									
									if(agent1.equals(tempAgentInstanceVariable.get(i).agentName)){
										
										for(int j=0; j<tempAgentInstanceVariable.get(i).listOfVariableInstances.size();j++ ){
											
											if(variableList1.getSelectedValue().toString().equals(tempAgentInstanceVariable.get(i).listOfVariableInstances.get(j).instanceName)){
												
												tempVariable1 = (new PlottingSettings()).new ScatterPlotComponent( tempAgentInstanceVariable.get(i).listOfVariableInstances.get(j));
												
												
											}
										}
									}
								}
								}else{
									
									for(int i=0; i <tempRatioInstanceVariable.size(); i++){
										
										if(variableList1.getSelectedValue().toString().equals(tempRatioInstanceVariable.get(i).ratioInstanceName)){
											
											tempVariable1 = (new PlottingSettings()).new ScatterPlotComponent( tempRatioInstanceVariable.get(i));
											
											
										}
												
									}
									
									
									
								}
				   		        				
   		        				/*Redraw the table*/
   		        				ratioDialog.remove(scrollScatterplotSelectionTable);
   		        				ratioDialog.validate();

   		        				ScatterplotSelectionTable fractionTable;
   		        				
   		        				try{
   		        					
   		        					fractionTable = new ScatterplotSelectionTable(tempVariable1.getName(), tempVariable2.getName());
   		        					
   		        					
   		        				}catch(NullPointerException ex1){
   		        					
   		        					try{
   		        					fractionTable = new ScatterplotSelectionTable("",tempVariable2.getName());
   		        					}catch(NullPointerException ex2){
   		        						
   		        						try{
   		   		        						fractionTable = new ScatterplotSelectionTable(tempVariable1.getName(), "");
   		   		        					}catch(NullPointerException ex3){
   		   		        						
   		   		        						fractionTable = new ScatterplotSelectionTable("", "");
   		   		        						
   		   		        					}
   		        					}
   		        					
   		        				}
   		        				
   		        				if(tempVariable1!=null && tempVariable2!= null){
   		   		   					okayButton.setEnabled(true);
   		   		   				}
   		        				
   		        				
	   		 	   				scrollScatterplotSelectionTable = fractionTable.listScrollFraction;
	   		 	   				scrollScatterplotSelectionTable.setPreferredSize(new Dimension(200, 60)); 
   		        				
	   		 	   				fil.gridx =3;fil.gridy =2;
		   		   				ratioDialog.add(scrollScatterplotSelectionTable,fil);
   		        				
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
   				
   				
   				JLabel label11 = new JLabel("Variable 1:");
   				
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
	   			variableList2 = new JList(variableListModel2){
	   				
	   				
	   				public String getToolTipText(MouseEvent evt) {
	   		        // Get item index
						int index = locationToIndex(evt.getPoint());

						 Object item = getModel().getElementAt(index);
  
	   		        String tooltip = "";
	   		        
	   		        if(!agent2.equals("Ratios")){
						
						for(int i=0; i< tempAgentInstanceVariable.size();i++){
							
							if(agent2.equals(tempAgentInstanceVariable.get(i).agentName)){
								
								for(int j=0; j<tempAgentInstanceVariable.get(i).listOfVariableInstances.size();j++ ){
									
									if(item.equals(tempAgentInstanceVariable.get(i).listOfVariableInstances.get(j).instanceName)){
										
										
										tooltip = returnVariableDescription(tempAgentInstanceVariable.get(i).listOfVariableInstances.get(j));
								}
							}
						}
						}
						}else{
							
							for(int i=0; i <tempRatioInstanceVariable.size(); i++){
								
								if(item.equals(tempRatioInstanceVariable.get(i).ratioInstanceName)){
									
									tooltip = "Ratio:  Numerator: "+returnVariableDescription(tempRatioInstanceVariable.get(i).numerator)+"\n Denominator: "+returnVariableDescription(tempRatioInstanceVariable.get(i).denominator);
									
									
								}
										
							}
							
							
							
						}
	   		        
	   		        
	   		        // Return the tool tip text
	   		        return tooltip;
	   		    }
	   			};
	   			
	   			variableList2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	   			
	   				
   				for(int i=0; i<tempAgentInstanceVariable.size();i++){
					
					if(tempAgentInstanceVariable.get(i).isSelected){
						
						selectedAgentListModel2.addElement(tempAgentInstanceVariable.get(i).agentName);
						
					}
					
				}
   				
   				/*If ratios are selected in the variable list add ratios*/
				if(tempRatioInstanceVariable.size()>0){
					
					selectedAgentListModel2.addElement("Ratios");
				}
   				
   				
   				selectedAgentList2.addMouseListener(new MouseListener(){
					
					public void mouseClicked(MouseEvent e) {

						System.out.println("One Click in row");
						
						if(e.getClickCount()==2){
							
							System.out.println("Double Click in row");
							
							if(!selectedAgentList2.getModel().getElementAt(selectedAgentList2.locationToIndex(e.getPoint())).equals("Ratios"))
							{
								for(int i=0; i<tempAgentInstanceVariable.size();i++){
	
									if(tempAgentInstanceVariable.get(i).agentName.equals(selectedAgentList2.getModel().getElementAt(selectedAgentList2.locationToIndex(e.getPoint())))){
										
										variableListModel2.removeAllElements();
										
										agent2 = selectedAgentList2.getModel().getElementAt(selectedAgentList2.locationToIndex(e.getPoint())).toString();
										
										
										for(int j=0; j < tempAgentInstanceVariable.get(i).listOfVariableInstances.size(); j++){
												
											variableListModel2.addElement(tempAgentInstanceVariable.get(i).listOfVariableInstances.get(j).instanceName);
													
										}
							
										break;	
									}
								
								
								}
							
							}else{
								
								
								variableListModel2.removeAllElements();
								
								for(int i=0; i <tempRatioInstanceVariable.size(); i++){
									
									variableListModel2.addElement(tempRatioInstanceVariable.get(i).ratioInstanceName);
									
									agent2 = "Ratios";
											
								}
								
							}
							
							
							if(tempVariable1!=null && tempVariable2!= null){
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
   				
   				
   				variableList2.addMouseListener(new MouseListener(){
					
					public void mouseClicked(MouseEvent e) {

						System.out.println("One Click in row");
						
					
						
						
						if(e.getClickCount()==2){
							

							System.out.println("One Click in row");
							
								if(!agent2.equals("Ratios")){
								
								for(int i=0; i< tempAgentInstanceVariable.size();i++){
									
									if(agent2.equals(tempAgentInstanceVariable.get(i).agentName)){
										
										for(int j=0; j<tempAgentInstanceVariable.get(i).listOfVariableInstances.size();j++ ){
											
											if(variableList2.getSelectedValue().toString().equals(tempAgentInstanceVariable.get(i).listOfVariableInstances.get(j).instanceName)){
												
												tempVariable2 = (new PlottingSettings()).new ScatterPlotComponent( tempAgentInstanceVariable.get(i).listOfVariableInstances.get(j));
												
												
											}
										}
									}
								}
								}else{
									
									for(int i=0; i <tempRatioInstanceVariable.size(); i++){
										
										if(variableList2.getSelectedValue().toString().equals(tempRatioInstanceVariable.get(i).ratioInstanceName)){
											
											tempVariable2 = (new PlottingSettings()).new ScatterPlotComponent( tempRatioInstanceVariable.get(i));
											
											
										}
												
									}
									
									
									
								}
				   		        				
   		        				/*Redraw the table*/
   		        				ratioDialog.remove(scrollScatterplotSelectionTable);
   		        				ratioDialog.validate();

   		        				ScatterplotSelectionTable fractionTable;
   		        				
   		        				try{
   		        					
   		        					fractionTable = new ScatterplotSelectionTable(tempVariable1.getName(), tempVariable2.getName());
   		        					
   		        					
   		        				}catch(NullPointerException ex1){
   		        					
   		        					try{
   		        					fractionTable = new ScatterplotSelectionTable("",tempVariable2.getName());
   		        					}catch(NullPointerException ex2){
   		        						
   		        						try{
   		   		        						fractionTable = new ScatterplotSelectionTable(tempVariable1.getName(), "");
   		   		        					}catch(NullPointerException ex3){
   		   		        						
   		   		        						fractionTable = new ScatterplotSelectionTable("", "");
   		   		        						
   		   		        					}
   		        					}
   		        					
   		        				}
   		        				
   		        				if(tempVariable1!=null && tempVariable2!= null){
   		   		   					okayButton.setEnabled(true);
   		   		   				}
   		        				
   		        				
	   		 	   				scrollScatterplotSelectionTable = fractionTable.listScrollFraction;
	   		 	   				scrollScatterplotSelectionTable.setPreferredSize(new Dimension(200, 60)); 
   		        				
	   		 	   				fil.gridx =3;fil.gridy =2;
		   		   				ratioDialog.add(scrollScatterplotSelectionTable,fil);
   		        				
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
   				

   				JLabel label21 = new JLabel("Variable 2:");
   				
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
   				
   				
   				ScatterplotSelectionTable fractionTable = new ScatterplotSelectionTable("","");
   				
   				scrollScatterplotSelectionTable = fractionTable.listScrollFraction;
   				scrollScatterplotSelectionTable.setPreferredSize(new Dimension(200, 60)); 
   		
   			
   				
   				fil.gridx =3;fil.gridy =2;
   				ratioDialog.add(scrollScatterplotSelectionTable,fil);
   				

   				okayButton = new JButton("OK");
   				okayButton.setEnabled(false);
   				
   				JButton discardButton = new JButton("Discard");
   				
   				okayButton.addActionListener(new ActionListener(){

					public void actionPerformed(ActionEvent e) {

						if(tempVariable1!=null && tempVariable2!= null){
							
						
							tempScatterPlots.add((new PlottingSettings()).new ScatterPlots(tempVariable1, tempVariable2));
							
							if(tempVariable1.isVariableInstance)
								tempVariable1.variableInstance.isSelectedForScatterplots=true;
							else
								tempVariable1.ratioInstance.isSelectedForScatterplots=true;
							
							if(tempVariable2.isVariableInstance)
								tempVariable2.variableInstance.isSelectedForScatterplots=true;
							else
								tempVariable2.ratioInstance.isSelectedForScatterplots=true;
							
							
						ratioDialog.dispose();
						ratioDialog.setVisible(false);

						drawTable();
						
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
		
		
		
		removeScatterPlot = new JMenuItem("Remove Scatter Plot");
		
		removeScatterPlot.addActionListener(new ActionListener(){
			
			JDialog removeMTS;
			JList mtsList;
			DefaultListModel dlmMTS;
			PlottingSettings.ScatterPlots tempSP;
		
		
			public void actionPerformed(ActionEvent arg0) {
				

					removeMTS = new JDialog();
					removeMTS.setSize(300,300);
					removeMTS.setLayout(new GridBagLayout());
					
					GridBagConstraints fA = new GridBagConstraints();
					fA.insets = new Insets( 5, 5, 5, 5);
					
					JLabel label1 = new JLabel("Select Scatter Plot for Deleting");
					fA.gridx =0;fA.gridy =0;
					removeMTS.add(label1,fA);
					
					dlmMTS = new DefaultListModel();
					
					mtsList = new JList(dlmMTS){
						
						public String getToolTipText(MouseEvent evt) {
		   		        // Get item index
							int index = locationToIndex(evt.getPoint());

		   		        // Get item
						String tooltip = getModel().getElementAt(index).toString();
		   		       
		   		        
		   		        // Return the tool tip text
		   		        return tooltip;
		   		    }
		   			};
					
					for(int i=0; i<tempScatterPlots.size();i++){

							dlmMTS.addElement(tempScatterPlots.get(i).firstComponent.getName()+"  "+tempScatterPlots.get(i).secondComponent.getName());
		
					}
					
					mtsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					mtsList.setVisibleRowCount(6);
					
					mtsList.addMouseListener(new MouseListener(){
						
						public void mouseClicked(MouseEvent e) {

							System.out.println("One Click in row");
							
							if(e.getClickCount()==2){
								
								System.out.println("Double Click in row");
								
								for(int i=0; i<tempScatterPlots.size();i++){
									
									if((tempScatterPlots.get(i).firstComponent.getName()+"  "+tempScatterPlots.get(i).secondComponent.getName()).equals(mtsList.getModel().getElementAt(mtsList.locationToIndex(e.getPoint())))){
										 //Create the tab pages for the agent that is selected for plotting
										
										
										
										tempSP = tempScatterPlots.get(i).clone();
										tempScatterPlots.remove(i);
										
										
										boolean comp1Used = false;
										
										for(int j=0; j < tempScatterPlots.size(); j++){
											
											if(tempScatterPlots.get(j).firstComponent.getName().equals(tempSP.firstComponent.getName())){
												
												comp1Used = true;
												break;
											
											}
									
										}
										
										boolean comp2Used = false;
										
										for(int j=0; j < tempScatterPlots.size(); j++){
											
											if(tempScatterPlots.get(j).secondComponent.getName().equals(tempSP.secondComponent.getName())){
												
												comp2Used = true;
												break;
											
											}
									
										}
										
										
										if(!comp1Used){
								
											if(tempSP.firstComponent.isVariableInstance){
												
												for(int j=0; j < tempAgentInstanceVariable.size();j++){
													
													if(tempAgentInstanceVariable.get(j).agentName.equals(tempSP.firstComponent.variableInstance.agentName)){
														
														
														for(int k=0; k< tempAgentInstanceVariable.get(j).listOfVariableInstances.size();k++){
															
															if(tempAgentInstanceVariable.get(j).listOfVariableInstances.get(k).instanceName.equals(tempSP.firstComponent.variableInstance.instanceName)){
																
																tempAgentInstanceVariable.get(j).listOfVariableInstances.get(k).isSelectedForScatterplots = false;
																
																break;
															}
															
															
														}
														
														
														break;
													}
													
													
													
												}
												
												
											}else{
												
												
												
												for(int j=0; j < tempRatioInstanceVariable.size();j++){
													
													
											
															if(tempRatioInstanceVariable.get(j).ratioInstanceName.equals(tempSP.firstComponent.ratioInstance.ratioInstanceName)){
																
																tempRatioInstanceVariable.get(j).isSelectedForScatterplots = false;
																
																break;
															}
										
												}
												
											}
										
										}
										
										
										if(!comp2Used){
											
											if(tempSP.secondComponent.isVariableInstance){
												
												for(int j=0; j < tempAgentInstanceVariable.size();j++){
													
													if(tempAgentInstanceVariable.get(j).agentName.equals(tempSP.secondComponent.variableInstance.agentName)){
														
														
														for(int k=0; k< tempAgentInstanceVariable.get(j).listOfVariableInstances.size();k++){
															
															if(tempAgentInstanceVariable.get(j).listOfVariableInstances.get(k).instanceName.equals(tempSP.secondComponent.variableInstance.instanceName)){
																
																tempAgentInstanceVariable.get(j).listOfVariableInstances.get(k).isSelectedForScatterplots = false;
																
																break;
															}
													
														}
											
														break;
													}
											
												}
												
												
											}else{
												
												
												
												for(int j=0; j < tempRatioInstanceVariable.size();j++){
					
														if(tempRatioInstanceVariable.get(j).ratioInstanceName.equals(tempSP.secondComponent.ratioInstance.ratioInstanceName)){
															
															tempRatioInstanceVariable.get(j).isSelectedForScatterplots = false;
															
															break;
														}
								
												}
												
											}
										
										}
										
										removeMTS.dispose();
										removeMTS.setVisible(false);
										
										//If all agents are selected for plotting deactivate the addAgent menu item
										if(tempScatterPlots.size()== 0){
											removeScatterPlot.setEnabled(false);
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
					
					
					JScrollPane scrollPane = new JScrollPane(mtsList);
					
					scrollPane.setPreferredSize(new Dimension(160,160));
					
					removeMTS.getContentPane().add(scrollPane,fA);
					
					removeMTS.setModal(true);
					removeMTS.setVisible(true);
					
					
					
				
				
				
				
				
				
		
				
			}
		});
		
		scatterPlot.add(removeScatterPlot);
		
		
		applyChanges= new JMenuItem("Apply Changes");
		
		applyChanges.addActionListener(new ActionListener(){

			
			public void actionPerformed(ActionEvent arg0) {
				
				PlottingSettings.listOfScatterPlots = tempScatterPlots;
    			PlottingSettings.listOfAgentsVariableInstances = tempAgentInstanceVariable;
    			PlottingSettings.listOfRatioInstances = tempRatioInstanceVariable;
				
			}
			
			
			
			
		});
		
		scatterPlot.add(applyChanges);
		
		
		exit = new JMenuItem("Exit");
		
		
		exit.addActionListener(new ActionListener(){

			
			public void actionPerformed(ActionEvent arg0) {
			
				
			Object text = "Apply Changes? \n";
	    		
	    		int choice = JOptionPane.showConfirmDialog(null, text,  "Exit GUI",JOptionPane.YES_NO_CANCEL_OPTION);
	   
	    		if(choice==0){
	 
	    			/*Save Settings*/

	    			PlottingSettings.listOfScatterPlots = tempScatterPlots;
	    			PlottingSettings.listOfAgentsVariableInstances = tempAgentInstanceVariable;
	    			PlottingSettings.listOfRatioInstances = tempRatioInstanceVariable;
					
	    			setVisible(false);
	    			dispose();
	    			
	    			
	    		}else if(choice==1){
	    			
	    			/*Choice is no*/
	    			PlottingSettings.defaultsScatterPlots = tempDefaults;
	    			setVisible(false);
	    			dispose();
	    			
	    		}
				
			}
			
			
			
			
			
		});
		
		scatterPlot.add(exit);
		
		menuBar.add(scatterPlot);
		
		
		
		
		menuSettings = new JMenu("Settings");
		
		defaults = new JMenuItem("Default Values");
		menuSettings.add(defaults);
		
		defaults.addActionListener(new ActionListener(){

			JTextField textfield1, textfield2, textfield3, textfield4;
			JCheckBox CBLowerBound, CBUpperBound;
			JDialog defaultSettingsDialog;

			
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
	   			
	   			JLabel label1 = new JLabel("Lags");
	   			textfield1 = new JTextField(5);
	   			textfield1.setText(Integer.toString(PlottingSettings.defaultsScatterPlots.timeLags));
	   			
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
	   			

	   			JButton okay = new JButton("OK");
	   			JButton discard = new JButton("Discard");
	   			
	   			enterDS.gridx=2; enterDS.gridy=2;
	   			defaultSettingsDialog.add(okay,enterDS);
	   			
	   			enterDS.gridx=3; enterDS.gridy=2;
	   			defaultSettingsDialog.add(discard,enterDS);
	   			
	   			okay.addActionListener(new ActionListener(){
	  	    		
	  	    		public void actionPerformed(ActionEvent evt) {
	  	    			
	  	    			
	  	    			try {
	  	    				PlottingSettings.defaultsScatterPlots.timeLags = Integer.parseInt(textfield1.getText());
	  	    				
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
		
		scrollScattertable = new  JScrollPane();
		
		drawTable();
		
		
		setModal(true);
		setVisible(true);
		

	}
	
	
	private void drawTable(){
		
		/*Redraw the table*/
		remove(scrollScattertable);
		validate();
		

		
		scatterplotTable = new  ScatterPlotTable();
		
		scrollScattertable = scatterplotTable.listScrollVariables;
		scrollScattertable.setPreferredSize(new Dimension(700, 179)); 
		ts.gridx=0; 
		ts.gridy=1;
		add(scrollScattertable,ts);

		validate();
		
		
	}
	
	

	private class ScatterPlotTable extends JScrollPane{
		
		
		ScatterplotTableModel scatterplotTableModel;
		JTable scatterplotTable;
		
		JScrollPane listScrollVariables;  
		

		ScatterPlotTable(){
			

			
			String[] colHeadersT2 = {"Component 1", "Component 2", "Lag" };
			
	
			
	
			
			
			scatterplotTableModel = new ScatterplotTableModel(colHeadersT2 );
			scatterplotTable = new JTable(scatterplotTableModel);

			
			scatterplotTable.setRowHeight(20);
			
			scatterplotTable.getColumnModel().getColumn(0).setPreferredWidth(140);
			scatterplotTable.getColumnModel().getColumn(0).setCellEditor(new CellEditor());
			

/*Column 2*/
			
			
			scatterplotTable.getColumnModel().getColumn(1).setPreferredWidth(140);
			scatterplotTable.getColumnModel().getColumn(1).setCellEditor(new CellEditor());
			
			
			
			/*Column 3*/
			

			scatterplotTable.getColumnModel().getColumn(2).setPreferredWidth(70);
			scatterplotTable.getColumnModel().getColumn(2).setCellEditor(new CellEditor());
			
			
			
			scatterplotTable.getColumnModel().getColumn(2).getCellEditor().addCellEditorListener(new EditorListener(){
				 
				 
				 public void editingStopped(ChangeEvent e) {
					 
					 System.out.println("Col 2 editing stopped");
					 
					 int editedrow = ((CellEditor) scatterplotTable.getColumnModel().getColumn(2).getCellEditor()).getRow();
		    
					 for(int i=0; i<tempScatterPlots.size();i++){
			    	    	
					    	
						
							 
							 if(tempScatterPlots.get(i).firstComponent.getName().equals(scatterplotTableModel.getValueAt(editedrow, 0)) &&tempScatterPlots.get(i).secondComponent.getName().equals(scatterplotTableModel.getValueAt(editedrow, 1)) )
							 {
								 try{
								 tempScatterPlots.get(i).timeLag = Integer.parseInt(scatterplotTable.getColumnModel().getColumn(2).getCellEditor().getCellEditorValue().toString());
								 break;
								 }catch(Exception ew){
									 
									   JOptionPane.showMessageDialog(null,"Not an integer"); 
									 
								 }
								 
							 }
							 
							 
						 
	  	  
					 }
		    	      
					 scatterplotTableModel.changeValueAt(scatterplotTable.getColumnModel().getColumn(2).getCellEditor().getCellEditorValue().toString(), editedrow, 2);
					 
				 }
				 
				 
			});

			
			

			
			
		
			
			
			scatterplotTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	
			
			
			System.out.println("draw table");
			scatterplotTableModel.updatetable();
			
			listScrollVariables = new JScrollPane(scatterplotTable);
	
   


			
			}
	}
public class ScatterplotTableModel extends AbstractTableModel{
	
	JCheckBox checkBox = new JCheckBox();
	String [] columnName;
	
	
	
	
	
	int numFilters;
	boolean partitioningSelected , filterSelected;
	
	
	PlottingSettings.Agent variableInstances;
	
	
	ScatterplotTableModel(String[] colName){


		columnName = colName;
	
		
	}
	
	
	
	public int getRowCount()
   {
	
	return tempScatterPlots.size();
	
 }
	
	
   public int getColumnCount()
   {
   return 3;
 }
   public Object getValueAt( int row, int col)
   {
	   
	
	   
	  
		 //  System.out.println("getValueAt timeSeriesSelection.get(i).isntancesOfvariable.size()"+timeSeriesSelection.get(i).isntancesOfvariable.size());
		
				   if(col==0)
					   return tempScatterPlots.get(row).firstComponent.getName();
				   else if(col==1)
					   return tempScatterPlots.get(row).secondComponent.getName();
				   else if(col==2)
					   return tempScatterPlots.get(row).timeLag;
				   else
					   return "";
				 
				  
			   
			
   }
	   
	   
	   
	   
	   
	   
   
   
   public String getColumnName(int column) {  
       return columnName[column]; 
   }
   
  
   public void changeValueAt(String value, int row, int col) {  
       
	   System.out.println("changeValueAt");
	  

	   if(col==2){
		   
		   try{
		   tempScatterPlots.get(row).timeLag = Integer.parseInt(value);
		   }catch(Exception ex){
			   
			   JOptionPane.showMessageDialog(null,"Not an integer"); 
			   
		   }
	  
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
      
	   if(column==2)
   		return true;
	   else
		   return false;
   	
   }
   

}
        

  
private class ScatterplotSelectionTable extends JScrollPane{
		
		
		JScrollPane listScrollFraction;
		

		ScatterplotSelectionTable(String component1, String component2){
			
			
	
			
			ScatterplotSelectionTableModel tabvariableModel = new ScatterplotSelectionTableModel(component1,component2 );
			JTable table = new JTable(tabvariableModel);

			
			table.setRowHeight(20);
			
			/*Column 1*/
			table.getColumnModel().getColumn(0).setPreferredWidth(140);
			
			

			
			listScrollFraction = new JScrollPane(table);
		}
			
		}
	
	
	private class ScatterplotSelectionTableModel extends AbstractTableModel{
			
		String component1, component2;

		ScatterplotSelectionTableModel(String num, String den){
			
			component1 = num;
			component2 = den;
			
		}
		
			public int getColumnCount() {
				
				return 1;
			}

		
			public int getRowCount() {
			
				return 2;
			}

		
			public Object getValueAt(int row, int column) {
				
				
				if(row==0){
					
					return component1;
					
				}else{
					
					return component2;
					
				}
				
			}
			
			
			  public String getColumnName(int column) {  
			       return "Time Scatter Plot"; 
			   }
			
		
			  
		   public boolean isCellEditable(int row, int column){ 
			      

		   			return false;
		   	
		   }
			
			
		}
	
	
	String returnVariableDescription(PlottingSettings.VariableInstance input){
		
		String description;
		
		if(input.isVariable)
			description = "Variable "+input.variable.name+" ("+input.instanceMethod+", "
				+"Filter 1: "+input.whichFilter1+", "
				+"Filter 2: "+input.whichFilter2+", "
				+"Partitioning: "+input.whichPartitioning+", "
				+input.growthRate+" Growth rate)";
		else	
			description = "Agent ratio:  Numerator: "+input.agentRatio.numerator.name+"    Denominator: "+ input.agentRatio.denominator.name +" ("+input.instanceMethod+", "
					+"Filter 1: "+input.whichFilter1+", "
					+"Filter 2: "+input.whichFilter2+", "
					+"Partitioning: "+input.whichPartitioning+", "
					+input.growthRate+" Growth rate)";

		
	
		return description;
	}

	
	
}
	
			
		
	
