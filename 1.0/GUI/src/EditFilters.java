import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;


public class EditFilters extends JDialog{
	
	
	JDialog enterValue;
	String value;
	ArrayList<PlottingSettings.Filter> tempFilters;
	private JDialog filterDialog;
	private ArrayList<Agent> tempAgentSettingList;
		
	private JPanel upperPanel;
	private JPanel lowerPanel = new JPanel();
	
	SortedListModel sortedModel;
	private DefaultListModel dlmFilter, dlm2Filter;
	private JList unSelectFilterVar,selectFilterVar;
	JTextField filterValue;
	

	
	EditFilters(final String agentName){
		
		
		filterDialog = new JDialog();
		filterDialog.setTitle("Filter for Agent "+agentName);
		filterDialog.setLayout(new GridBagLayout());
		final GridBagConstraints fil = new GridBagConstraints();
			fil.insets = new Insets( 5, 5, 5, 5);
		
		filterDialog.setSize(700,400);
		filterDialog.setBackground(Color.white);
		filterDialog.setModal(true);
		
		upperPanel = new JPanel();
		upperPanel.setLayout(new GridBagLayout());
		final GridBagConstraints upP = new GridBagConstraints();
		upP.insets = new Insets( 5, 5, 5, 5);
	
		
		/*Initialize temFilter*/
		
		tempAgentSettingList = AuxFunctions.deepCopyAgentSettingsList(AgentSettings.agents);
		
		tempFilters = new ArrayList<PlottingSettings.Filter>();
		
		for(int i=0; i < PlottingSettings.listOfAgentsVariableInstances.size();i++){
			
			if(PlottingSettings.listOfAgentsVariableInstances.get(i).agentName.equals(agentName)){
				
				for(int j=0; j< PlottingSettings.listOfAgentsVariableInstances.get(i).filter.size();j++){
				
					tempFilters.add(PlottingSettings.listOfAgentsVariableInstances.get(i).filter.get(j).clone());
					
				}
				
			}
			
		}
		

    	fil.gridx=0; fil.gridy =0;
    	filterDialog.add(upperPanel,fil);
    	
    	
    	lowerPanel = new JPanel();
    	lowerPanel.setLayout(new GridBagLayout());
		final GridBagConstraints lowerP = new GridBagConstraints();
		lowerP.insets = new Insets( 5, 5, 5, 5);
    	
		
		dlmFilter = new DefaultListModel();
			unSelectFilterVar = new JList(dlmFilter){
				

		public String getToolTipText(MouseEvent evt) {
		        // Get item index
				int index = locationToIndex(evt.getPoint());

		        // Get item
		        Object item = getModel().getElementAt(index);
		        
		        String tooltip = "";
		        
   		     for(int i=0; i < tempAgentSettingList.size();i++){
   		        	
   		        	if(tempAgentSettingList.get(i).agentName.equals(agentName)){
   		        		
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
			
			
			
			
		 sortedModel = new SortedListModel(dlmFilter,SortedListModel.SortOrder.UNORDERED);
		unSelectFilterVar.setModel(sortedModel);
		
		
		unSelectFilterVar.addMouseListener(new MouseListener(){
		
		public void mouseClicked(MouseEvent e) {
		
			System.out.println("Right click");
			
			if(SwingUtilities.isRightMouseButton(e)){
				
				System.out.println("Right click");
				
				
				if(sortedModel.sortOrderOfList==SortedListModel.SortOrder.ASCENDING ){
					
												
					sortedModel.setSortOrder(SortedListModel.SortOrder.DESCENDING);
					unSelectFilterVar.setModel(sortedModel);
		
				}else if(sortedModel.sortOrderOfList==SortedListModel.SortOrder.DESCENDING){
					
					sortedModel.setSortOrder(SortedListModel.SortOrder.UNORDERED);
					unSelectFilterVar.setModel(sortedModel);
					
				}else if(sortedModel.sortOrderOfList==SortedListModel.SortOrder.UNORDERED){
					
					sortedModel.setSortOrder(SortedListModel.SortOrder.ASCENDING);
					unSelectFilterVar.setModel(sortedModel);
					
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
			
			
			dlm2Filter = new DefaultListModel();
			selectFilterVar = new JList(dlm2Filter){
				

		public String getToolTipText(MouseEvent evt) {
		        // Get item index
			String tooltip = "";
			
			try{
				int index = locationToIndex(evt.getPoint());

		        // Get item
		        Object item = getModel().getElementAt(index);
		
		        
		        for(int j = 0; j<tempFilters.size();j++)
		        {
		        
		        	if(tempFilters.get(j).variableName.equals(item.toString())){
		        		
		        		tooltip = "Filter value: "+tempFilters.get(j).filterValue;
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
        	
        	if(tempAgentSettingList.get(i).agentName.equals(agentName)){
        		
        		for(int j=0; j< tempAgentSettingList.get(i).variableList.size();j++){
        			
        		
        			try{
        			for(int k=0; k<tempFilters.size();k++)
	   				{
		   				if(tempAgentSettingList.get(i).variableList.get(j).name.equals(tempFilters.get(k).variableName)){
		   				
		   					dlm2Filter.addElement(tempFilters.get(k).filterName);
		   					
		   				}	
		   				
	   				}
        			}catch(Exception ex){
        				
        				System.out.println("No filters found\n");
        				
        			}
        	
	   					dlmFilter.addElement(tempAgentSettingList.get(i).variableList.get(j).name);
      
        			}
        			
        			
        		}
        		
        		
        	}
        	
        	
  
			

			unSelectFilterVar.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			unSelectFilterVar.setVisibleRowCount(10);
			
			selectFilterVar.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			selectFilterVar.setVisibleRowCount(10);

		JScrollPane listScrollUnSelectFilterVar = new JScrollPane(unSelectFilterVar); 
		listScrollUnSelectFilterVar.setPreferredSize(new Dimension(200, 200)); 
		
		JScrollPane listScrollSelectFilterVar = new JScrollPane(selectFilterVar); 
		listScrollSelectFilterVar.setPreferredSize(new Dimension(200, 200)); 
		
		lowerP.gridx=0; lowerP.gridy =0;
		lowerPanel.add(listScrollUnSelectFilterVar,lowerP);
		
		JPanel middlePanel = new JPanel();
		
		middlePanel.setLayout(new GridBagLayout());
			final GridBagConstraints midP= new GridBagConstraints();
			midP.insets = new Insets( 5, 5, 5, 5);
			
			
			JButton addButton = new JButton("Add");
			JButton removeButton = new JButton("Remove");
			
			addButton.addActionListener(new ActionListener(){
				
				String selectedFilterMethod;
				String[] filterMethods={"=",">","<","!="};
				JComboBox filterMethod;

			public void actionPerformed(ActionEvent arg0) {
				
				enterValue = new JDialog();
				enterValue.setTitle("Select Filter");
				enterValue.setLayout(new FlowLayout());
				enterValue.setSize(400,100);
				enterValue.setBackground(Color.white);
				enterValue.setModal(true);
				
				JLabel text1 = new JLabel("Enter filter value:");
				enterValue.add(text1);
				
				filterValue = new JTextField(4);
				enterValue.add(filterValue);
				
				
				JLabel text2 = new JLabel("Select filter method:");
				enterValue.add(text2);
				

				filterMethod = new  JComboBox(filterMethods);
				
				filterMethod.setSelectedIndex(0);
				
				enterValue.add(filterMethod);
				
				selectedFilterMethod = filterMethods[0];
				
				
				filterMethod.addActionListener( new ActionListener(){

			
					public void actionPerformed(ActionEvent e) {
					
						selectedFilterMethod = filterMethods[filterMethod.getSelectedIndex()];
						
					}
					
					
				});
				
				

			    /*Add ActionListener*/
				filterValue.addActionListener(new ActionListener(){
					
					
			    		
			    		public void actionPerformed(ActionEvent evt) {
			    		    
			    			String input = filterValue.getText();
			    			
			    				value = input;
			    			    enterValue.setVisible(false);
			    			    enterValue.dispose();
	  
			    			    String filName = dlmFilter.get(sortedModel.toUnsortedModelIndex(unSelectFilterVar.getSelectedIndex())).toString()+"("+selectedFilterMethod+" "+value+")";
			    			    
			    			    dlm2Filter.addElement(filName);
								tempFilters.add((new PlottingSettings()).new Filter(filName,dlmFilter.get(sortedModel.toUnsortedModelIndex(unSelectFilterVar.getSelectedIndex())).toString(),value,selectedFilterMethod));
								
								
								
			    			    
			    	
			    		}
			    		
			    	});
				
				enterValue.setVisible(true);
				
			}
				
				
				
			});
			
			
			
			removeButton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg1) {
				
				
				try{
				int index = selectFilterVar.getSelectedIndex();
				

				for(int i=0; i< tempFilters.size();i++){
					
					if(tempFilters.get(i).filterName.equals(dlm2Filter.get(index).toString())){
						
						
						/*Check whether this filter is used elsewhere*/
						
						boolean used;
						
						
						used = checkIfUsed(tempFilters.get(i).filterName);
						
						if(used){
							
							Object text2 = "The filter "+tempFilters.get(i).filterName+" is used for other applications. Delete anyhow?";
							
							int choiceIsRatio = JOptionPane.showConfirmDialog(null, text2,  "",JOptionPane.YES_NO_OPTION);
							
							if(choiceIsRatio == 0){
								
								removeFilters(tempFilters.get(i).filterName);
								
								tempFilters.remove(index);
								break;
								
							}else{
								
								
								break;
								
							}
							
							
						}else{
							
							tempFilters.remove(index);
							break;
							
							
						}
						
						
					}
					
				}
				dlm2Filter.remove(index);
				
				}catch(Exception ex1){
					
					System.out.println(ex1);
					
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
		lowerPanel.add(listScrollSelectFilterVar,lowerP);
		
		
		
		
		JButton getContinued = new JButton("Apply");
		lowerP.gridx=3; lowerP.gridy =1;
		lowerPanel.add(getContinued,lowerP);
		
		
		fil.gridx=0; fil.gridy =1;
    	filterDialog.add(lowerPanel,fil);
		
		getContinued.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {

				for(int i=0; i < PlottingSettings.listOfAgentsVariableInstances.size();i++){
					
					if(PlottingSettings.listOfAgentsVariableInstances.get(i).agentName.equals(agentName)){
						
						PlottingSettings.listOfAgentsVariableInstances.get(i).filter = tempFilters;
						
					}

				}
				
				filterDialog.setVisible(false);
				filterDialog.dispose();

		
				
			}
			
			
			
		});
	
		
		filterDialog.setVisible(true);
		
		
		
		
		
		
	}
	
	
	
	
	boolean checkIfUsed(String filterName){
		

		
		for(int i=0; i < PlottingSettings.listOfAgentsVariableInstances.size();i++){
			
			for(int j=0;j <  PlottingSettings.listOfAgentsVariableInstances.get(i).listOfVariableInstances.size(); j++){
				
			
				if(PlottingSettings.listOfAgentsVariableInstances.get(i).listOfVariableInstances.get(j).whichFilter1.equals(filterName))
					return true;
				
				if(PlottingSettings.listOfAgentsVariableInstances.get(i).listOfVariableInstances.get(j).whichFilter2.equals(filterName))
					return true;
			}
			
		}
		
		for(int i=0; i < PlottingSettings.listOfBoxplots.size();i++){
			
			if(PlottingSettings.listOfBoxplots.get(i).Filter1.equals(filterName))
				return true;
			
			if(PlottingSettings.listOfBoxplots.get(i).Filter2.equals(filterName))
				return true;
		
		}
		
		for(int i=0; i < PlottingSettings.listOfCorrelation.size();i++){
			
			if(PlottingSettings.listOfCorrelation.get(i).Filter1.equals(filterName))
				return true;
			
			if(PlottingSettings.listOfCorrelation.get(i).Filter2.equals(filterName))
				return true;
		
		}
		
		
	for(int i=0; i < PlottingSettings.listOfHeatmaps.size();i++){
			
			if(PlottingSettings.listOfHeatmaps.get(i).Filter1.equals(filterName))
				return true;
			
			if(PlottingSettings.listOfHeatmaps.get(i).Filter2.equals(filterName))
				return true;
		
		}
	
	
	for(int i=0; i < PlottingSettings.listOfHeatmaps2V.size();i++){
		
		if(PlottingSettings.listOfHeatmaps2V.get(i).Filter1.equals(filterName))
			return true;
		
		if(PlottingSettings.listOfHeatmaps2V.get(i).Filter2.equals(filterName))
			return true;
	
	}
	
	
	for(int i=0; i < PlottingSettings.listOfHistograms.size();i++){
		
		if(PlottingSettings.listOfHistograms.get(i).Filter1.equals(filterName))
			return true;
		
		if(PlottingSettings.listOfHistograms.get(i).Filter2.equals(filterName))
			return true;
	
	}
		
		
		return false;
		
	}
	
	
	void removeFilters(String filterName){
		
		
for(int i=0; i < PlottingSettings.listOfAgentsVariableInstances.size();i++){
			
			for(int j=0;j <  PlottingSettings.listOfAgentsVariableInstances.get(i).listOfVariableInstances.size(); j++){
				
			
				if(PlottingSettings.listOfAgentsVariableInstances.get(i).listOfVariableInstances.get(j).whichFilter1.equals(filterName))
					PlottingSettings.listOfAgentsVariableInstances.get(i).listOfVariableInstances.get(j).whichFilter1 = "No Filter";
					
				
				if(PlottingSettings.listOfAgentsVariableInstances.get(i).listOfVariableInstances.get(j).whichFilter2.equals(filterName))
					PlottingSettings.listOfAgentsVariableInstances.get(i).listOfVariableInstances.get(j).whichFilter2 = "No Filter";
			}
			
		}
		
		for(int i=0; i < PlottingSettings.listOfBoxplots.size();i++){
			
			if(PlottingSettings.listOfBoxplots.get(i).Filter1.equals(filterName))
				PlottingSettings.listOfBoxplots.get(i).Filter1 = "No Filter";
			
			if(PlottingSettings.listOfBoxplots.get(i).Filter2.equals(filterName))
				PlottingSettings.listOfBoxplots.get(i).Filter2 = "No Filter";
		
		}
		
		for(int i=0; i < PlottingSettings.listOfCorrelation.size();i++){
			
			if(PlottingSettings.listOfCorrelation.get(i).Filter1.equals(filterName))
				PlottingSettings.listOfCorrelation.get(i).Filter1 = "No Filter";
			
			if(PlottingSettings.listOfCorrelation.get(i).Filter2.equals(filterName))
				PlottingSettings.listOfCorrelation.get(i).Filter2 = "No Filter";
		
		}
		
		
	for(int i=0; i < PlottingSettings.listOfHeatmaps.size();i++){
			
			if(PlottingSettings.listOfHeatmaps.get(i).Filter1.equals(filterName))
				PlottingSettings.listOfHeatmaps.get(i).Filter1 = "No Filter";
			
			if(PlottingSettings.listOfHeatmaps.get(i).Filter2.equals(filterName))
				PlottingSettings.listOfHeatmaps.get(i).Filter2 = "No Filter";
		
		}
	
	
	for(int i=0; i < PlottingSettings.listOfHeatmaps2V.size();i++){
		
		if(PlottingSettings.listOfHeatmaps2V.get(i).Filter1.equals(filterName))
			PlottingSettings.listOfHeatmaps2V.get(i).Filter1 = "No Filter";
		
		if(PlottingSettings.listOfHeatmaps2V.get(i).Filter2.equals(filterName))
			PlottingSettings.listOfHeatmaps2V.get(i).Filter2 = "No Filter";
	
	}
	
	
	for(int i=0; i < PlottingSettings.listOfHistograms.size();i++){
		
		if(PlottingSettings.listOfHistograms.get(i).Filter1.equals(filterName))
			PlottingSettings.listOfHistograms.get(i).Filter1 = "No Filter";
		
		if(PlottingSettings.listOfHistograms.get(i).Filter2.equals(filterName))
			PlottingSettings.listOfHistograms.get(i).Filter2 = "No Filter";
	
	}
		
		
	}
	

}
