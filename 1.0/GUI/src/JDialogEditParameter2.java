import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;


public class JDialogEditParameter2 extends JDialog{
	
	
	DefaultListModel dlmParameter;
	JList ParameterList;
	SortedListModel sortedModel;
	JTextField textfield;
	 DefaultListModel dlm2Values;
	 JList	valueList;
	 SimulationSettings.SelectedModelParameter parameter1;
	 SortedListModel sortedModelValues;
	
	JDialogEditParameter2(){
		
		
	parameter1 = SimulationSettings.PARAMETER_1.clone();
	
	setTitle("Choose the second Parameter");
	/*Disable Button of GUI 1 and 2*/
	
	setSize(600,500);

	
	/*Action when costum close: reactivate the buttons*/
	setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	 


	
	/*GUI settings*/
	setLayout(new GridBagLayout());
	GridBagConstraints c = new GridBagConstraints();
	c.insets = new Insets( 5, 5, 5, 5);



    
	dlmParameter = new DefaultListModel();
	
	ParameterList = new JList(dlmParameter){
			

			public String getToolTipText(MouseEvent evt) {
  		        // Get item index
					int index = locationToIndex(evt.getPoint());

  		        // Get item
  		        Object item = getModel().getElementAt(index);
  		        
  		        String tooltip = "";
  		        
  		        for(int i=0; i< ModelParameterSettings.modelParameters.size();i++)
		{
		
			if(ModelParameterSettings.modelParameters.get(i).name.equals(item)){
				
				 tooltip = ModelParameterSettings.modelParameters.get(i).name+" ("+ModelParameterSettings.modelParameters.get(i).type+") "+ModelParameterSettings.modelParameters.get(i).description;	
			}
		}
			
	
				

  		        // Return the tool tip text
  		        return tooltip;
  		    }
  			};
  			
  			
  			
	
	for(int i=0; i < ModelParameterSettings.modelParameters.size(); i++){
		
		dlmParameter.addElement(ModelParameterSettings.modelParameters.get(i).name);
		
	}
	
	
	 sortedModel = new SortedListModel(dlmParameter,SortedListModel.SortOrder.UNORDERED);
	 ParameterList.setModel(sortedModel);
	 
	 

	 ParameterList.addMouseListener(new MouseListener(){
			
			public void mouseClicked(MouseEvent e) {
		
				System.out.println("Right click");
				
				if(SwingUtilities.isRightMouseButton(e)){
					
					System.out.println("Right click");
					
					
					if(sortedModel.sortOrderOfList==SortedListModel.SortOrder.ASCENDING ){
						
													
						sortedModel.setSortOrder(SortedListModel.SortOrder.DESCENDING);
						ParameterList.setModel(sortedModel);
			
					}else if(sortedModel.sortOrderOfList==SortedListModel.SortOrder.DESCENDING){
						
						sortedModel.setSortOrder(SortedListModel.SortOrder.UNORDERED);
						ParameterList.setModel(sortedModel);
						
					}else if(sortedModel.sortOrderOfList==SortedListModel.SortOrder.UNORDERED){
						
						sortedModel.setSortOrder(SortedListModel.SortOrder.ASCENDING);
						ParameterList.setModel(sortedModel);
						
					}
					
					
					for(int i=0; i< ParameterList.getModel().getSize();i++){
						
						if(ParameterList.getModel().getElementAt(i).equals(parameter1.name))
						{
							System.out.println("Matched");
							ParameterList.setSelectedIndex(i);
						}
					}
					
					if(!ParameterList.isSelectionEmpty())
						ParameterList.ensureIndexIsVisible(Math.max(0, sortedModel.toUnsortedModelIndex(ParameterList.getSelectedIndex()))-3);
					
			
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
	
	
	ParameterList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
	ParameterList.setVisibleRowCount(6);
	
	/*Set pre selected item*/
	
	
	
	for(int i=0; i< ParameterList.getModel().getSize();i++){
	
		if(ParameterList.getModel().getElementAt(i).equals(parameter1.name))
		{
			System.out.println("Matched");
			ParameterList.setSelectedIndex(i);
		}
	}
	
	System.out.println(ParameterList.getSelectedIndex()+" xxx");
	


	
	c.gridx= 0;	
	c.gridy= 0;
	
    getContentPane().add(new JScrollPane(ParameterList),c);
    
    if(!ParameterList.isSelectionEmpty())
    	ParameterList.ensureIndexIsVisible(Math.max(0, sortedModel.toUnsortedModelIndex(ParameterList.getSelectedIndex()))-3);
    
    
    ParameterList.addMouseListener(new MouseListener(){
		
		public void mouseClicked(MouseEvent e) {

			System.out.println("One Click in row");
			
			if(SwingUtilities.isLeftMouseButton(e)){
		
			
				for(int i=0; i< ModelParameterSettings.modelParameters.size();i++)
				{
					
					
					if(ModelParameterSettings.modelParameters.get(i).name.equals(dlmParameter.get(sortedModel.toUnsortedModelIndex(ParameterList.getSelectedIndex())))){
					
						
						System.out.println(parameter1.name);
				
						 
						 boolean paramterChanged = true;
						 try{
						 if(parameter1.name.equals(dlmParameter.get(sortedModel.toUnsortedModelIndex(ParameterList.getSelectedIndex()))))
							{
								paramterChanged = false;
							}
						 }catch(Exception ex){
							 
							 System.out.println(ex);
						 }
							
							 parameter1.name = ModelParameterSettings.modelParameters.get(i).name;
							 parameter1.type= ModelParameterSettings.modelParameters.get(i).type;
							 
							 System.out.println("parameter Changed? "+paramterChanged);
							 
							 
							 if(parameter1.type.equals("int")){
								 
							
								 
								 sortedModelValues.comparator =  (new AuxFunctions()).new MyStringAsIntegerComparable();
								 
								 System.out.println("int");
								 
							 }else{
								 
								 
								 sortedModelValues.comparator = (new AuxFunctions()).new MyStringAsDoubleComparable();
			
								
							 }
							 
							 
							 
							 System.out.println( dlmParameter.get(sortedModel.toUnsortedModelIndex(ParameterList.getSelectedIndex())));
								System.out.println(parameter1.name = ModelParameterSettings.modelParameters.get(i).name);
							 
							 /*If parameter has changed delete the entries*/
							 if(paramterChanged)
							 {
								 for(int j=0; j<parameter1.values.size();j++){
									 
									 parameter1.values.remove(j);
									 j--;
									 
								 }
								 
								 for(int j=0;j < dlm2Values.getSize();j++ ){
									 dlm2Values.removeElementAt(j);
									 j--;
								 }
								 
							 }
						 
						 System.out.println(parameter1.values.size());
						

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
	
    
    
    

	

	
	JPanel panel1 = new JPanel();	
	panel1.setLayout(new GridBagLayout());
	
	
	c.gridx= 0;	
	c.gridy= 0;
	
	JPanel panel2 = new JPanel();	
	panel2.setLayout(new GridBagLayout());
	
	
	textfield = new JTextField(10);
	
	textfield.addActionListener(new ActionListenerAddValue());
	
	panel1.add(textfield,c);

	JButton add = new JButton("Add");
	
	add.addActionListener(new ActionListenerAddValue());
	
	JButton remove = new JButton("Remove");
    
	c.gridx= 0;	
	c.gridy= 0;
	
	panel2.add(add,c);
	
	c.gridx= 0;	
	c.gridy= 1;
   
	panel2.add(remove,c);
	
	
	c.gridx= 1;	
	c.gridy= 0;
	
	panel1.add(panel2,c);
	
	
	
	
	 dlm2Values = new DefaultListModel();
	 valueList = new JList(dlm2Values);
	 
	 
	 if(parameter1.type.equals("int")){
		 sortedModelValues = new SortedListModel(dlm2Values,SortedListModel.SortOrder.DESCENDING, (new AuxFunctions()).new MyStringAsIntegerComparable());
		 valueList.setModel(sortedModelValues);
		 
		 for(int i= 0; i < parameter1.values.size();i++){
			 

			 dlm2Values.addElement(Integer.parseInt(parameter1.values.get(i).value));
			 
		 }
		 
		 
	 }else{
		 sortedModelValues = new SortedListModel(dlm2Values,SortedListModel.SortOrder.DESCENDING, (new AuxFunctions()).new MyStringAsDoubleComparable());
		 valueList.setModel(sortedModelValues); 
		 
		 
		 for(int i= 0; i < parameter1.values.size();i++){
			 

			 dlm2Values.addElement(Double.parseDouble(parameter1.values.get(i).value));
			 
		 }
		 
	 }
	 

	 
	 
	 remove.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				
				 int indices[] = valueList.getSelectedIndices();
				
					for(int j=indices.length-1; j>=0;j--)
					{
						dlm2Values.removeElementAt(indices[j]);
					}
			}
		
		});

	 valueList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	 valueList.setVisibleRowCount(10);
	 
	JScrollPane listScrollSelectVar = new JScrollPane(valueList); 
	listScrollSelectVar.setPreferredSize(new Dimension(75, 150)); 
	
	c.gridx=2; c.gridy =0;
	panel1.add(listScrollSelectVar,c);
	
	
	c.gridx= 0;	
	c.gridy= 1;
	
	add(panel1,c);
	
	
	JButton ok = new JButton("OK");
	
	ok.addActionListener(new ActionListener(){

		public void actionPerformed(ActionEvent arg0) {
			
			
			for(int i=0; i < parameter1.values.size();i++){
				
				 parameter1.values.remove(i);
				 i--;
				
			}
		

			for(int i=0;i < dlm2Values.getSize(); i++ ){
				
				parameter1.values.add((new SimulationSettings().new Value(dlm2Values.getElementAt(i).toString())));
				
			}
			
			SimulationSettings.PARAMETER_1 = parameter1;
			
			dispose();
			setVisible(false);
			
		}

		
	});
	
	
	JButton discard = new JButton("Discard");
	
	
	discard.addActionListener(new ActionListener(){

		public void actionPerformed(ActionEvent arg0) {
			
			dispose();
			setVisible(false);
			
		}

		
	});
	
	c.gridx=1; c.gridy =2;
	add(ok,c);
	
	c.gridx=2; c.gridy =2;
	add(discard,c);
   
	setModal(true);
	getContentPane().setBackground(Color.white);
    setVisible(true);
    
   
    
	}
	
	
	class ActionListenerAddValue implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			
			String input;
			
			/*Check if there is a comma*/
			
			input = textfield.getText().toString();
			textfield.setText("");
			
			System.out.println(input);
			
			if(input.contains(",")){
				
				if(parameter1.type.equals("int")){
					
					do{
						
						String addToList = input.substring(0, input.indexOf(","));
						input = input.substring( input.indexOf(",")+1);
						
						/*TODO Add sorted list*/
		
						
	
							try{
								Integer.parseInt(addToList);
								dlm2Values.addElement(Integer.parseInt(addToList));
								
							}catch(Exception e1){
								
								JOptionPane.showMessageDialog(null, addToList +" is not an integer!");	
							}

						
					}while(input.contains(","));
					
					try{
						Integer.parseInt(input);
						dlm2Values.addElement(Integer.parseInt(input));
						
					}catch(Exception e1){
						
						JOptionPane.showMessageDialog(null, input +" is not an integer!");	
					}

				}
				else{
					do{
						
						String addToList = input.substring(0, input.indexOf(","));
						input = input.substring( input.indexOf(",")+1);
						
						/*TODO Add sorted list*/
		
						
							try{
								
								dlm2Values.addElement(Double.parseDouble(addToList));
								
							}catch(Exception e1){
								
								JOptionPane.showMessageDialog(null, addToList +" is not a numeric expression!");
							}	
						
						
						
					}while(input.contains(","));
					
					
					try{
						
						dlm2Values.addElement(Double.parseDouble(input));
						
					}catch(Exception e1){
						
						JOptionPane.showMessageDialog(null, input +" is not a numeric expression!");
					}	
					
				
				
				}
				
				
			}else{
				
				if(parameter1.type.equals("int")){

					try{
						Integer.parseInt(input);
						dlm2Values.addElement(Integer.parseInt(input));
						
					}catch(Exception e1){
						
						JOptionPane.showMessageDialog(null, input +" is not an integer!");	
					}

				}else{	
				try{
						
						Double.parseDouble(input);
						System.out.println("xxx");
						
						dlm2Values.addElement(Double.parseDouble(input));
						
				}catch(Exception e1){
						
					JOptionPane.showMessageDialog(null, input +" is not a numeric expression!");
					}	
				}
				
			}
			
			
			textfield.selectAll();
			
			
			
			
		}

	}


}
