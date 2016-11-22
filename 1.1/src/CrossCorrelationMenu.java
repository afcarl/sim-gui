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
import javax.swing.table.TableCellRenderer;

public class CrossCorrelationMenu extends JDialog{
	
	JMenuBar menuBar;
	JMenu crossCorrelation, menuSettings;
	JMenuItem addCrossCorrelation ,removeCrossCorrelation, applyChanges, exit, defaults;
	
	ArrayList<PlottingSettings.CrossCorrelation> tempCrossCorrelation;
	ArrayList<PlottingSettings.Agent> tempAgentInstanceVariable;
	ArrayList<PlottingSettings.RatioInstance>  tempRatioInstanceVariable;
	
	JScrollPane scrollScattertable;
	CrossCorrelationTable crossCorrelationTable;
	
	JScrollPane scrollCrossCorrelationTable;

	GridBagConstraints ts;
	
	boolean tempRatiosSelectedForCrossCorrelation;
	
	PlottingSettings.DefaultSettingsCrossCorrelation tempDefaults;

	CrossCorrelationMenu(){
		

		tempCrossCorrelation = new ArrayList<PlottingSettings.CrossCorrelation>();

		for(int i=0; i< PlottingSettings.listOfCrossCorrelation.size();i++){
			
			
			tempCrossCorrelation.add(PlottingSettings.listOfCrossCorrelation.get(i).clone());
			
		}
		
		tempAgentInstanceVariable = new ArrayList<PlottingSettings.Agent> ();
		
		for(int i=0; i< PlottingSettings.listOfAgentsVariableInstances.size();i++){
			
			
			tempAgentInstanceVariable.add(AuxFunctions.DeepCopyAgentVariableInstance(PlottingSettings.listOfAgentsVariableInstances.get(i)));
			
		}
		
		tempRatioInstanceVariable = new ArrayList<PlottingSettings.RatioInstance>();
		
		
		for(int i=0; i< PlottingSettings.listOfRatioInstances.size();i++){
			
			
			tempRatioInstanceVariable.add(AuxFunctions.DeepCopyRatioInstance(PlottingSettings.listOfRatioInstances.get(i)));
			
		}
		
		tempDefaults = PlottingSettings.defaultsCrossCorrelation.clone();

		
		/*Dialog settings*/	
	    setTitle("Time Series Cross Correlation");
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

	    			PlottingSettings.listOfCrossCorrelation = tempCrossCorrelation;
	    			PlottingSettings.listOfAgentsVariableInstances = tempAgentInstanceVariable;
	    			PlottingSettings.listOfRatioInstances = tempRatioInstanceVariable;
	    		
	    			setVisible(false);
	    			dispose();
	    			
	    			
	    		}else if(choice==1){
	    			
	    			/*Choice is no*/
	    			
	    			PlottingSettings.defaultsCrossCorrelation = tempDefaults;
	   
	    			setVisible(false);
	    			dispose();
	    			
	    		}
	   
    			
			}
			});
		
		crossCorrelation = new JMenu("Cross Correlation Plot");
		
		
		addCrossCorrelation = new JMenuItem("Add");
		
		crossCorrelation.add(addCrossCorrelation);
		
	
		
		addCrossCorrelation.addActionListener(new ActionListener(){

			JDialog ratioDialog;
			GridBagConstraints fil;
			DefaultListModel selectedAgentListModel1, selectedAgentListModel2;
			JList selectedAgentList1,selectedAgentList2;
			DefaultListModel variableListModel1,variableListModel2;
			JList variableList1,variableList2;
			JButton okayButton;
			JScrollPane scrollScatterplotSelectionTable;
			
			String agent1, agent2;
			
			PlottingSettings.CrossCorrelationComponent tempVariable1;
			PlottingSettings.CrossCorrelationComponent tempVariable2;
			
			public void actionPerformed(ActionEvent arg0) {
				
				
				tempVariable1 = null;
				tempVariable2 = null;
				
				ratioDialog = new JDialog();
				ratioDialog.setModal(true);
				
	   			ratioDialog.setSize(800,600);
	   			ratioDialog.setBackground(Color.white);
	   			
				
				ratioDialog.setTitle("Variable Selection for Cross Correlation Plot");
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
												
												tempVariable1 = (new PlottingSettings()).new CrossCorrelationComponent( tempAgentInstanceVariable.get(i).listOfVariableInstances.get(j));
												
												
											}
										}
									}
								}
								}else{
									
									for(int i=0; i <tempRatioInstanceVariable.size(); i++){
										
										if(variableList1.getSelectedValue().toString().equals(tempRatioInstanceVariable.get(i).ratioInstanceName)){
											
											tempVariable1 = (new PlottingSettings()).new CrossCorrelationComponent( tempRatioInstanceVariable.get(i));
											
											
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
												
												tempVariable2 = (new PlottingSettings()).new CrossCorrelationComponent( tempAgentInstanceVariable.get(i).listOfVariableInstances.get(j));
												
												
											}
										}
									}
								}
								}else{
									
									for(int i=0; i <tempRatioInstanceVariable.size(); i++){
										
										if(variableList2.getSelectedValue().toString().equals(tempRatioInstanceVariable.get(i).ratioInstanceName)){
											
											tempVariable2 = (new PlottingSettings()).new CrossCorrelationComponent( tempRatioInstanceVariable.get(i));
											
											
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
							
						
							tempCrossCorrelation.add((new PlottingSettings()).new CrossCorrelation(tempVariable1, tempVariable2));
							
							
							if(tempVariable1.isVariableInstance)
								tempVariable1.variableInstance.isSelectedForCrossCorrelation=true;
							else
								tempVariable1.ratioInstance.isSelectedForCrossCorrelation=true;
							
							if(tempVariable2.isVariableInstance)
								tempVariable2.variableInstance.isSelectedForCrossCorrelation=true;
							else
								tempVariable2.ratioInstance.isSelectedForCrossCorrelation=true;
							
						
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
		
		
		
		removeCrossCorrelation = new JMenuItem("Remove");
		
		removeCrossCorrelation.addActionListener(new ActionListener(){
			
			JDialog removeMTS;
			JList mtsList;
			DefaultListModel dlmMTS;
			PlottingSettings.CrossCorrelation tempSP;
		
		
			public void actionPerformed(ActionEvent arg0) {
				

					removeMTS = new JDialog();
					removeMTS.setSize(300,300);
					removeMTS.setLayout(new GridBagLayout());
					
					GridBagConstraints fA = new GridBagConstraints();
					fA.insets = new Insets( 5, 5, 5, 5);
					
					JLabel label1 = new JLabel("Select Cross Correlation Plot for Deleting");
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
					
					for(int i=0; i<tempCrossCorrelation.size();i++){

							dlmMTS.addElement(tempCrossCorrelation.get(i).firstComponent.getName()+"  "+tempCrossCorrelation.get(i).secondComponent.getName());
		
					}
					
					mtsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					mtsList.setVisibleRowCount(6);
					
					mtsList.addMouseListener(new MouseListener(){
						
						public void mouseClicked(MouseEvent e) {

							System.out.println("One Click in row");
							
							if(e.getClickCount()==2){
								
								System.out.println("Double Click in row");
								
								for(int i=0; i<tempCrossCorrelation.size();i++){
									
									if((tempCrossCorrelation.get(i).firstComponent.getName()+"  "+tempCrossCorrelation.get(i).secondComponent.getName()).equals(mtsList.getModel().getElementAt(mtsList.locationToIndex(e.getPoint())))){
										 //Create the tab pages for the agent that is selected for plotting
										
										tempSP = tempCrossCorrelation.get(i).clone();
										
										tempCrossCorrelation.remove(i);
										
										
										boolean comp1Used = false;
										
										for(int j=0; j < tempCrossCorrelation.size(); j++){
											
											if(tempCrossCorrelation.get(j).firstComponent.getName().equals(tempSP.firstComponent.getName())){
												
												comp1Used = true;
												break;
											
											}
									
										}
										
										boolean comp2Used = false;
										
										for(int j=0; j < tempCrossCorrelation.size(); j++){
											
											if(tempCrossCorrelation.get(j).secondComponent.getName().equals(tempSP.secondComponent.getName())){
												
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
										if(tempCrossCorrelation.size()== 0){
											removeCrossCorrelation.setEnabled(false);
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
		
		crossCorrelation.add(removeCrossCorrelation);
		
		
		applyChanges= new JMenuItem("Apply Changes");
		
		applyChanges.addActionListener(new ActionListener(){

			
			public void actionPerformed(ActionEvent arg0) {
				
				PlottingSettings.listOfCrossCorrelation = tempCrossCorrelation;
    			PlottingSettings.listOfAgentsVariableInstances = tempAgentInstanceVariable;
    			PlottingSettings.listOfRatioInstances = tempRatioInstanceVariable;
				
			}
			
			
			
			
		});
		
		crossCorrelation.add(applyChanges);
		
		
		exit = new JMenuItem("Exit");
		
		
		exit.addActionListener(new ActionListener(){

			
			public void actionPerformed(ActionEvent arg0) {
			
				
			Object text = "Apply Changes? \n";
	    		
	    		int choice = JOptionPane.showConfirmDialog(null, text,  "Exit GUI",JOptionPane.YES_NO_CANCEL_OPTION);
	   
	    		if(choice==0){
	 
	    			/*Save Settings*/

	    			PlottingSettings.listOfCrossCorrelation = tempCrossCorrelation;
	    			PlottingSettings.listOfAgentsVariableInstances = tempAgentInstanceVariable;
	    			PlottingSettings.listOfRatioInstances = tempRatioInstanceVariable;
					
	    			setVisible(false);
	    			dispose();
	    			
	    			
	    		}else if(choice==1){
	    			
	    			/*Choice is no*/
	    			PlottingSettings.defaultsCrossCorrelation = tempDefaults;
	    			setVisible(false);
	    			dispose();
	    			
	    		}
				
			}
			
			
			
			
			
		});
		
		crossCorrelation.add(exit);
		
		menuBar.add(crossCorrelation);
		
		
		
		
		menuSettings = new JMenu("Settings");
		
		defaults = new JMenuItem("Default Values");
		menuSettings.add(defaults);
		
		defaults.addActionListener(new ActionListener(){

			JTextField textfield1;
			JComboBox combo1;
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
	   			textfield1.setText(Integer.toString(PlottingSettings.defaultsCrossCorrelation.timeLags));
	   			
	   			enterDS.gridx=0; enterDS.gridy=0;
	   			defaultSettingsDialog.add(label1,enterDS);
	   			
	   			enterDS.gridx=1; enterDS.gridy=0;
	   			defaultSettingsDialog.add(textfield1,enterDS);
	   			
	   			JLabel label2 = new JLabel("Aggretaion");
	   			String methods[] = {"No", "Sum", "Mean"}; 
	   			
	   			combo1 = new JComboBox(methods);
	   			
	   			
	   			combo1.setSelectedItem(PlottingSettings.defaultsCrossCorrelation.aggregationMethod);
	   			
	   			enterDS.gridx=0; enterDS.gridy=1;
	   			defaultSettingsDialog.add(label2,enterDS);
	   			
	   			enterDS.gridx=1; enterDS.gridy=1;
	   			defaultSettingsDialog.add(combo1,enterDS);
	   			
	   			
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
	  	    				PlottingSettings.defaultsCrossCorrelation.timeLags = Integer.parseInt(textfield1.getText());
	  	    				
	  	    				PlottingSettings.defaultsCrossCorrelation.aggregationMethod = combo1.getSelectedItem().toString();
	  	    				
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
		
		
		
		setVisible(true);
		

	}
	
	
	private void drawTable(){
		
		/*Redraw the table*/
		remove(scrollScattertable);
		validate();
		

		
		crossCorrelationTable = new  CrossCorrelationTable();
		
		scrollScattertable = crossCorrelationTable.listScrollVariables;
		scrollScattertable.setPreferredSize(new Dimension(700, 179)); 
		ts.gridx=0; 
		ts.gridy=1;
		add(scrollScattertable,ts);

		validate();
		
		
	}
	
	

	private class CrossCorrelationTable extends JScrollPane{
		
		
		ScatterplotTableModel crossCorrelationTableModel;
		JTable crossCorrelationTable;
		
		JScrollPane listScrollVariables;  
		

		CrossCorrelationTable(){
			

			
			String[] colHeadersT2 = {"Component 1", "Aggregation", "Component 2","Aggregation", "Lag" };
			
	
			
	
			
			
			crossCorrelationTableModel = new ScatterplotTableModel(colHeadersT2 );
			crossCorrelationTable = new JTable(crossCorrelationTableModel);

			
			crossCorrelationTable.setRowHeight(20);
			
			crossCorrelationTable.getColumnModel().getColumn(0).setPreferredWidth(140);
			crossCorrelationTable.getColumnModel().getColumn(0).setCellEditor(new CellEditor());
			
			String [] selChoice = {"No", "Sum", "Mean"};

			crossCorrelationTable.getColumnModel().getColumn(1).setCellEditor(new SpecificComboBoxEditor(selChoice));
			crossCorrelationTable.getColumnModel().getColumn(1).setCellRenderer(new SpecificComboBoxRenderer(selChoice));
			
			
			
			crossCorrelationTable.getColumnModel().getColumn(1).getCellEditor().addCellEditorListener(new EditorListener(){
				 
				 
				 public void editingStopped(ChangeEvent e) {
					 
					 System.out.println("Editing ended!");
					 
				 }
				 
				 
			});
			
			
			
			
			crossCorrelationTable.getColumnModel().getColumn(2).setPreferredWidth(140);
			crossCorrelationTable.getColumnModel().getColumn(2).setCellEditor(new CellEditor());
			
			
			
			String [] selChoice2 = {"No", "Sum", "Mean"};

			crossCorrelationTable.getColumnModel().getColumn(3).setCellEditor(new SpecificComboBoxEditor(selChoice2));
			crossCorrelationTable.getColumnModel().getColumn(3).setCellRenderer(new SpecificComboBoxRenderer(selChoice2));
			
			
			
			crossCorrelationTable.getColumnModel().getColumn(3).getCellEditor().addCellEditorListener(new EditorListener(){
				 
				 
				 public void editingStopped(ChangeEvent e) {
					 
					 System.out.println("Editing ended!");
					 
				 }
				 
				 
			});
			

			crossCorrelationTable.getColumnModel().getColumn(4).setPreferredWidth(70);
			crossCorrelationTable.getColumnModel().getColumn(4).setCellEditor(new CellEditor());
			
			
			
			crossCorrelationTable.getColumnModel().getColumn(4).getCellEditor().addCellEditorListener(new EditorListener(){
				 
				 
				 public void editingStopped(ChangeEvent e) {
					 
					 System.out.println("Col 2 editing stopped");
					 
					 int editedrow = ((CellEditor) crossCorrelationTable.getColumnModel().getColumn(4).getCellEditor()).getRow();
		    
					 for(int i=0; i<tempCrossCorrelation.size();i++){
			    	    	
					    	
						
							 
							 if(tempCrossCorrelation.get(i).firstComponent.getName().equals(crossCorrelationTableModel.getValueAt(editedrow, 0)) &&tempCrossCorrelation.get(i).secondComponent.getName().equals(crossCorrelationTableModel.getValueAt(editedrow, 1)) )
							 {
								 try{
								 tempCrossCorrelation.get(i).timeLag = Integer.parseInt(crossCorrelationTable.getColumnModel().getColumn(4).getCellEditor().getCellEditorValue().toString());
								 break;
								 }catch(Exception ew){
									 
									   JOptionPane.showMessageDialog(null,"Not an integer"); 
									 
								 }
								 
							 }
							 
							 
						 
	  	  
					 }
		    	      
					 crossCorrelationTableModel.changeValueAt(crossCorrelationTable.getColumnModel().getColumn(4).getCellEditor().getCellEditorValue().toString(), editedrow, 4);
					 
				 }
				 
				 
			});

			
			

			
			
		
			
			
			crossCorrelationTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	
			
			
			System.out.println("draw table");
			crossCorrelationTableModel.updatetable();
			
			listScrollVariables = new JScrollPane(crossCorrelationTable);
	
   


			
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
		    
		        	if(cComboBox.getItemAt(i).toString().equals(crossCorrelationTableModel.getValueAt(editedRow, editedCol))){

		        		
		        		 cComboBox.setSelectedIndex(i);
		        		
		        	}
		        	try{
			        	if(crossCorrelationTableModel.getValueAt(editedRow, editedCol).equals("")){

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
		    	       
		    	    	crossCorrelationTableModel.changeValueAt(cComboBox.getSelectedItem().toString(), editedRow, editedCol);
		    	    	 crossCorrelationTableModel.updatetable();
						 
						 

						 for(int i=0; i<tempCrossCorrelation.size();i++){
				    	    	
						    	
							
								 
								 if(tempCrossCorrelation.get(i).firstComponent.getName().equals(crossCorrelationTableModel.getValueAt(editedRow, 0)) &&tempCrossCorrelation.get(i).secondComponent.getName().equals(crossCorrelationTableModel.getValueAt(editedRow, 1)) )
								 {
									
									 if(editedCol==1){
											
										 tempCrossCorrelation.get(i).firstComponent.aggregationMethod = cComboBox.getSelectedItem().toString();
									 }else if(editedCol==3){
										 /*Column 4 => Filter 1*/
										 tempCrossCorrelation.get(i).secondComponent.aggregationMethod = cComboBox.getSelectedItem().toString();
									 }
									 
									 
								 }
								 
								 
							 
		  	  
						 }
		    	    	 
		    	    	 
	
						
							 
						 
						
							
						 
	
						 crossCorrelationTableModel.updatetable();
		    	    	
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
	
	return tempCrossCorrelation.size();
	
 }
	
	
   public int getColumnCount()
   {
   return 5;
 }
   public Object getValueAt( int row, int col)
   {
	   
	
	   
	  
		 //  System.out.println("getValueAt timeSeriesSelection.get(i).isntancesOfvariable.size()"+timeSeriesSelection.get(i).isntancesOfvariable.size());
		
				   if(col==0)
					   return tempCrossCorrelation.get(row).firstComponent.getName();
				   else if(col==1)
					   return tempCrossCorrelation.get(row).firstComponent.aggregationMethod;
				   else if(col==2)
					   return tempCrossCorrelation.get(row).secondComponent.getName();
				   else if(col==3)
				   return tempCrossCorrelation.get(row).secondComponent.aggregationMethod;
				   else if(col==4)
				   return tempCrossCorrelation.get(row).timeLag;
				   else
					   return "";
				 
				  
			   
			
   }
	   
	   
	   
	   
	   
	   
   
   
   public String getColumnName(int column) {  
       return columnName[column]; 
   }
   
  
   public void changeValueAt(String value, int row, int col) {  
       
	   System.out.println("changeValueAt");
	  

	   if(col==4){
		   
		   try{
		   tempCrossCorrelation.get(row).timeLag = Integer.parseInt(value);
		   }catch(Exception ex){
			   
			   JOptionPane.showMessageDialog(null,"Not an integer"); 
			   
		   }
	  
	   }		 
	   else if(col==1)
		  tempCrossCorrelation.get(row).firstComponent.aggregationMethod = value;
	   
	   else if(col==3)
	     tempCrossCorrelation.get(row).secondComponent.aggregationMethod = value;
	   

	 
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
      
	   if(column==1)
   		return true;
	   else if(column==3){
		   return true;
		   
	   }else if(column==4){
		   
		   return true;
	   }
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
			       return "Cross Correlation"; 
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
	
			
		
	



