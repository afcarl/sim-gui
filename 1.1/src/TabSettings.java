import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;


public class TabSettings extends JPanel{
	
	
	JScrollPane listScrollAgentTable;
	private AgentTableModel tabAgentsModel;
	VeryTopArea veryTopArea;
	TopArea topArea;
	MiddleArea middleArea;
	LowerArea lowArea;
	JPanel leftPanel, rightPanel, lcRightPanel;
	JLabel topAreaHeader, labelNumBatchRuns, labelNumIterations, labelNumProcessors;
	JTextField fieldNumBatchRuns, fieldNumIterations;
	JRadioButton doRun, doNotRun, justBatchRuns, parameterVariationOnePars, doCompressKeepOriginal, doCompressRemoveOriginal, doNotCompress,removeOriginal, decompress,rbYesStoreAll, rbNoStoreAll;
	JButton changeInitialParameterSetup, buttonActivateParameter1;
	JComboBox cbNumProcessors;
	ButtonGroup Compress, Run, expSetup, soreAllvariables;
	JTable table,tableP2;
	private JScrollPane listScroll;
					
	private TableColumn colP2;
	ParameterTableModel tabModel;
	
	 GridBagConstraints d = new GridBagConstraints();
	
	 GridBagConstraints rp = new GridBagConstraints();
	 GridBagConstraints g = new GridBagConstraints();
	 GridBagConstraints luP = new GridBagConstraints();
	 private TableColumn col;
	
	private static String[] proc = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24"};
	
	GridBagConstraints fA ;
	
	
	
	TabSettings (){
		
	
	   
	      
	      
		
		fA = new GridBagConstraints();
		setLayout(new GridBagLayout());
	    setSize(700, 700);
	    fA.insets = new Insets( 5, 5, 5, 5);
	    
	    
	    veryTopArea =new VeryTopArea(0,0);
	    
	    
	    fA.gridx=0; 
      	fA.gridy=0;
      	add(veryTopArea,fA);	
	    
	    topArea = new TopArea(0,1);
	    
	    fA.gridx=0; 
      	fA.gridy=1;
      	add(topArea,fA);
	    
      	middleArea = new MiddleArea(0,2);
		
      	 fA.gridx=0; 
       	fA.gridy=2;
       	add(middleArea,fA);
	    
       	lowArea = new LowerArea(0,3);
       	
        fA.gridx=0; 
       	fA.gridy=3;
       	add(lowArea,fA);
       	
       	setVisible(true);
	    
		
	}
	
	
	void drawTableParemters(){
		
		middleArea.drawParameterTables();
		
	}
	
	class VeryTopArea extends JPanel{ 
		
		
		VeryTopArea(int x, int y){
	
		
		
		setBorder( BorderFactory.createLineBorder( Color.black ) );
	
		
	  	
    	doRun = new JRadioButton("Run Simulations"); 
    	doNotRun = new JRadioButton("Do not run Simulations");
    	
    	Run = new ButtonGroup();
    	

    	setLayout(new GridBagLayout());
		 GridBagConstraints c = new GridBagConstraints();
		 c.insets = new Insets( 5, 5, 5, 5);
		 setSize(800,200);
		 setBackground(Color.white);

    	
    	Run.add(doRun);
    	Run.add(doNotRun);
    	
    	if(SimulationSettings.DO_RUN==1){
    		
    		doRun.setSelected(true);
    		
    	}else{
    		
    		doNotRun.setSelected(true);
    		
    	}
    	
    	
    	   
	      c.gridx = 0;
	      c.gridy= 0;
	      doRun.addItemListener(new itemHandler());
	      
	      doNotRun.addItemListener(new itemHandler()); 
	      add(doRun,c);
	      
	      
	      c.gridx = 1;
	      c.gridy= 0;
	      
	      add(doNotRun,c);
    	

	      setPreferredSize(new Dimension(this.getSize().width-20,60));
	      
	      	
		
		
	}
	}
	
	
	class TopArea extends JPanel{
	
	
	TopArea(int x, int y){
		
		
		

	    /********Upper Area: general Settings**************/
	    
	    setBorder( BorderFactory.createLineBorder( Color.black ) );
	    topAreaHeader = new JLabel("General Settings:");
	    topAreaHeader.setFont(new Font("Arial",Font.BOLD,16));


	    
    
	fieldNumBatchRuns = new JTextField(10);
	fieldNumBatchRuns.setText(Integer.toString(SimulationSettings.numBatchRuns));
	labelNumBatchRuns = new JLabel("Number of batch Runs");

    	
    	fieldNumIterations = new JTextField(10);
    	fieldNumIterations.setText(Integer.toString(SimulationSettings.numIterations));
    	labelNumIterations = new JLabel("Number of Iterations");
    	
    	cbNumProcessors = new JComboBox(proc);
    	//cbNumProcessors.setVisibleRowCount(4);
    	labelNumProcessors = new JLabel("Number of Processes");
    	
    
    	
    	changeInitialParameterSetup = new JButton("Change Parameter Setup");
	    	
        /*Add ActionListener*/
    	changeInitialParameterSetup.addActionListener(new ActionListener(){
	    		
	    		public void actionPerformed(ActionEvent evt) {
	    		    
	    			new JDialogParameterSetup(ModelParameterSettings.modelParameters);

	    		}
	    		
	    	});
    	
  
     
    	setLayout(new GridBagLayout());
		 GridBagConstraints c = new GridBagConstraints();
		 c.insets = new Insets( 5, 5, 5, 5);
		setSize(800,200);
		 setBackground(Color.white);
	      

		 c.gridx = 1;
	     c.gridy= 0;
	      
	     
		 c.gridx = 0;
	      c.gridy= 1;
	      add(labelNumBatchRuns,c);
	      
	      c.gridx = 1;
	      c.gridy= 1;
	      add(fieldNumBatchRuns,c);
	      
	      /*Add ActionListener*/
	      fieldNumBatchRuns.addActionListener(new ActionListener(){
	    		
	    		public void actionPerformed(ActionEvent evt) {
	    		    
	    			String input = fieldNumBatchRuns.getText();
	    		
	    			try {
	    				SimulationSettings.numBatchRuns = Integer.parseInt(input);
	    			    System.out.println("NumBatch runs: "+SimulationSettings.numBatchRuns);
	    			    fieldNumBatchRuns.selectAll();
	    			}
	    			catch(NumberFormatException nFE) {
	    				JOptionPane.showMessageDialog(null,"Not an integer"); 
	    			}
	    			
	    		}
	    		
	    	});
	      
	      c.gridx = 0;
	      c.gridy= 2;			 
	      add(labelNumIterations,c);
	      
	      c.gridx = 1;
	      c.gridy= 2;
	      add(fieldNumIterations,c);
	      
	      /*Add ActionListener*/
	      fieldNumIterations.addActionListener(new ActionListener(){
	    		
	    		public void actionPerformed(ActionEvent evt) {
	    		    
	    			String input = fieldNumIterations.getText();
	    			
	    			try {
	    					
	    				SimulationSettings.numIterations = Integer.parseInt(input);
	    				fieldNumIterations.selectAll();
	    			    System.out.println("numIterations: "+SimulationSettings.numIterations);
	    			    
	    			    for(int i=0; i<PlottingSettings.listOfSingleTimeSeries.size();i++){
	    	
	    	
	    			    		PlottingSettings.listOfSingleTimeSeries.get(i).tmax = (int) Math.floor(SimulationSettings.numIterations);
	    			    
	    		
	    			    }
	    			    
	    			    for(int i=0; i<PlottingSettings.listOfMultipleTimeSeries.size();i++){
	    		
	    			    		
	    			    		PlottingSettings.listOfMultipleTimeSeries.get(i).tmax = (int) Math.floor(SimulationSettings.numIterations);
	    			   
	    			    }
	    			    
	    			    for(int i=0; i<PlottingSettings.listOfMultipleBandpassFilteredTimeSeries.size();i++){
	    		    		
    			    		
    			    		PlottingSettings.listOfMultipleBandpassFilteredTimeSeries.get(i).tmax = (int) Math.floor(SimulationSettings.numIterations);
    			   
    			    }
	    			    
	    			    
	    			    for(int i=0; i<PlottingSettings.listOfSingleBandpassFilteredTimeSeries.size();i++){
	    		    		
    			    		
    			    		PlottingSettings.listOfSingleBandpassFilteredTimeSeries.get(i).tmax = (int) Math.floor(SimulationSettings.numIterations);
    			   
    			    }
	    			    
	    			    
	    			    for(int i=0; i<PlottingSettings.listOfHeatmaps.size();i++){
	    		    		
    			    		
    			    		PlottingSettings.listOfHeatmaps.get(i).tmax = (int) Math.floor(SimulationSettings.numIterations);
    			   
	    			    }
	    			    
	    			    for(int i=0; i<PlottingSettings.listOfHeatmaps2V.size();i++){
	    		    		
    			    		
    			    		PlottingSettings.listOfHeatmaps2V.get(i).tmax = (int) Math.floor(SimulationSettings.numIterations);
    			   
	    			    }
	    			    
	    			    
	    	
	    			    
	    			    boolean above = false;
	    			    
	    			    for(int i =0; i < PlottingSettings.defaultsBoxplots.iterations.size();i++){
	    			    	
	    			    	if(  Integer.parseInt(PlottingSettings.defaultsBoxplots.iterations.get(i).iteration )>(int) Math.floor(SimulationSettings.numIterations)){
	    			    		
	    			    		if(!above)
	    			    			PlottingSettings.defaultsBoxplots.iterations.get(i).iteration = Integer.toString((int) Math.floor(SimulationSettings.numIterations));
	    			    		else
	    			    		{
	    			    			PlottingSettings.defaultsBoxplots.iterations.remove(i);
	    			    			i--;
	    			    		}
	    			    		above = true;
	    			    		
	    			    	}
	    			    	
	    			    }
	    			    
	    			    
	    			    boolean above2 = false;
	    			    
	    			    for(int i =0; i < PlottingSettings.defaultsHistogram.iterations.size();i++){
	    			    	
	    			    	if(  Integer.parseInt(PlottingSettings.defaultsHistogram.iterations.get(i).iteration )>(int) Math.floor(SimulationSettings.numIterations)){
	    			    		
	    			    		if(!above2)
	    			    			PlottingSettings.defaultsHistogram.iterations.get(i).iteration = Integer.toString((int) Math.floor(SimulationSettings.numIterations));
	    			    		else
	    			    		{
	    			    			PlottingSettings.defaultsHistogram.iterations.remove(i);
	    			    			i--;
	    			    		}
	    			    		above2 = true;
	    			    		
	    			    	}
	    			    	
	    			    }
	    			    
	    			    
	    			    
	    			    for(int i=0; i<PlottingSettings.listOfHeatmaps.size();i++){
	    			    	
	    			    	
    			    		PlottingSettings.listOfHeatmaps.get(i).tmax = (int) Math.floor(SimulationSettings.numIterations);
    			    
    		
    			    
	    			    	}
	    			    
	    			    PlottingSettings.defaultsHeatmaps.tmax = (int) Math.floor(SimulationSettings.numIterations);
	    			    PlottingSettings.defaultsHeatmaps2V.tmax = (int) Math.floor(SimulationSettings.numIterations);
	    			    PlottingSettings.defaultsSingleTimeSeries.tmax = (int) Math.floor(SimulationSettings.numIterations);
	    			    PlottingSettings.defaultsMultipleTimeSeries.tmax = (int) Math.floor(SimulationSettings.numIterations);
	    			    PlottingSettings.defaultsMultipleBandpassFilteredTimeSeries.tmax = (int) Math.floor(SimulationSettings.numIterations);
	    			    PlottingSettings.defaultsSingleBandpassFilteredTimeSeries.tmax = (int) Math.floor(SimulationSettings.numIterations);	
	    			    
	    			    
	    			}
	    			catch(NumberFormatException nFE) {
	    				JOptionPane.showMessageDialog(null,"Not an integer"); 
	    			}
	    			
	    		}
	    		
	    	});
	      
	      
	      
	      
	      c.gridx = 0;
	      c.gridy= 3;			 
	      add(labelNumProcessors,c);
	      
	      c.gridx = 1;
	      c.gridy= 3;
	      cbNumProcessors.setSelectedIndex(5);
	      add(new JScrollPane(cbNumProcessors),c);
	       
	      cbNumProcessors.addItemListener(new itemHandlerNumProcs());
	      
	      
	      SimulationSettings.numProcessors=Integer.parseInt(cbNumProcessors.getSelectedItem().toString());
	         
	      
	     
	      
	      c.gridx = 2;
	      c.gridy= 3;
	      
	      add(changeInitialParameterSetup,c);
	      
	  
	      setPreferredSize(new Dimension(this.getSize().width-20,140));
	      
	      		
		
		
	}
	
	
	
	
	}
	
	
	class MiddleArea extends JPanel{
	
	MiddleArea(int x, int y){
		
		/********Center Area: Experiment design**************/
      	
		
		setBorder( BorderFactory.createLineBorder( Color.black ) );
		
		setSize(800,300);
		setBackground(Color.white);
		
		setLayout(new GridBagLayout());
		  //GridBagConstraints d = new GridBagConstraints();
		
		  d.insets = new Insets( 5, 5, 5, 5);
		
		justBatchRuns = new JRadioButton("Run only one Batch");
    	parameterVariationOnePars = new JRadioButton("Parameter Variation with one Parameter");
	   
	   
	    /*here starts area 2*/
	    
	    leftPanel = new JPanel();
	    leftPanel.setLayout(new BoxLayout(leftPanel,BoxLayout.Y_AXIS));
		
	    leftPanel.setSize(400,300);
	    leftPanel.setBackground(Color.white);
	   // leftPanel.setLayout(new GridBagLayout());

		   
	  expSetup = new ButtonGroup() ;
	
	  expSetup.add(justBatchRuns);
	  expSetup.add(parameterVariationOnePars);
	
      
	  if(SimulationSettings.numParameters==1){
		  
		  parameterVariationOnePars.setSelected(true);    		  
		  
	  }else{
		  
		  justBatchRuns.setSelected(true);
		  
	  }
	 
     
      justBatchRuns.addItemListener(new itemHandler2());
      leftPanel.add(justBatchRuns);
     
      System.out.println("justBatchRuns"+justBatchRuns.isSelected());
      
     
      parameterVariationOnePars.addItemListener(new itemHandler2());
    
      Dimension minSize = new Dimension(5, 10);
      Dimension prefSize = new Dimension(5, 10);
      Dimension maxSize = new Dimension(Short.MAX_VALUE, 10);
      leftPanel.add(new Box.Filler(minSize, prefSize, maxSize));
      
      //leftPanel.add(Box.createHorizontalGlue());
      leftPanel.add(parameterVariationOnePars );
     // parameterVariationOnePars.LEFT_ALIGNMENT;
      leftPanel.add(new Box.Filler(minSize, prefSize, maxSize));
     
      
      
      d.gridx = 0;
      d.gridy= 0;
      
      add(leftPanel,d);

	  /*tables*/
	  
	  rightPanel= new JPanel();
	  rightPanel.setSize(400,300);
	  rightPanel.setBackground(Color.white);
	  rightPanel.setLayout(new GridBagLayout());
	    rp.insets = new Insets( 5, 5, 5, 5);
	    
	    /*Here starts the setting of Parameter 1*/    
	    
		   buttonActivateParameter1 = new JButton("Edit Parameter 1");
		    
		   rp.gridx = 0;
		   rp.gridy= 0;
		    
		   rightPanel.add(buttonActivateParameter1,rp);
		    
		    
		    
		   
		    /*Here starts the list of listeners*/
		    
		    buttonActivateParameter1.addActionListener(new ActionListener() {
		
				public void actionPerformed(ActionEvent e) {
				
					new JDialogEditParameter1();
					drawParameterTables();
				}		
		    });
		    
      
      
      /*Create empty tables or initialize them:*/
      
  
		    drawParameterTables();
	   
	   
	   setPreferredSize(new Dimension(this.getSize().width-20,250));
	   
	
		
		
		
	}
	
	

	
void drawParameterTables(){
		
		
		
		try{
    	rightPanel.remove(listScroll);
    	
    	rightPanel.validate();
		}catch(NullPointerException ex){
			
			System.out.println("Start");
			
		}
		
		 if(SimulationSettings.numParameters==1){
   		  
   		  parameterVariationOnePars.setSelected(true);   
   	
   		justBatchRuns.setSelected(false);
   		  
		 
   		  
		 }else{
   		  
   		  justBatchRuns.setSelected(true);
  
   		  parameterVariationOnePars.setSelected(false);
   		  
		 }
		
		
		if(parameterVariationOnePars.isSelected() )
	    {	
	    	tabModel = new ParameterTableModel(SimulationSettings.PARAMETER_1.name,SimulationSettings.PARAMETER_1.values,true);
	    	table = new JTable(tabModel);
	    	table.getColumnModel().getColumn(0).setHeaderValue(SimulationSettings.PARAMETER_1.name);
	    	
	    	System.out.println("SimulationSettings.PARAMETER_1.name   "+SimulationSettings.PARAMETER_1.name );
	    	
	    	col = table.getColumnModel().getColumn(0);
	    	
	    	col.setCellEditor(new CellEditor());
	    	

	    
	
	    	buttonActivateParameter1.setEnabled(true);
	   
	    	
	    	
	    }else{
	    	
	    	
	    	
	    	
	       	buttonActivateParameter1.setEnabled(false);
	    	

	    }
		
		

	    
	  
      
	    listScroll = new JScrollPane(table);  
	    listScroll.setPreferredSize(new Dimension(130, 150));  
	    rp.gridx = 0;
	    rp.gridy= 1;
	    rightPanel.add(listScroll,rp);
      
	   
	    
	    rightPanel.validate();
      
  	/*Add to panel upperCenterArea*/
	    d.gridx = 1;
	    d.gridy= 0;
	    add(rightPanel,d);
	   
		
		
		
	}

	
	
	
	}
	
	
	
	
	class LowerArea extends JPanel{
		
		JPanel panelBB, lcLeftPanel;
		DrawStoreOptionTable tabAgents;
	
	LowerArea(int x, int y){
		
		
		
	    /*LowerupperCenterArea*/
	    /*This is to set the store settings*/
    	
		
		Compress = new ButtonGroup();
    	
    	JPanel panelAA = new JPanel();
	


    	setBorder( BorderFactory.createLineBorder( Color.black ) );
    	
    	
    	

		
		setSize(800,200);
		
	      
		
 
    	setBackground(Color.white);
	    
	    
    	setLayout(new GridBagLayout());
    	luP.insets = new Insets( 5, 5, 5, 5);

	    panelAA.setLayout(new FlowLayout());
	    
	    lcLeftPanel = new JPanel();
	    lcLeftPanel.setSize(400,200);
	    lcLeftPanel.setBackground(Color.white);
	    
	    lcLeftPanel.setLayout(new BoxLayout(lcLeftPanel,BoxLayout.Y_AXIS));
	    
	    doNotCompress = new JRadioButton("Do not compress"); 
	    doCompressKeepOriginal= new JRadioButton("Compress Databases and keep Original"); 
    	doCompressRemoveOriginal = new JRadioButton("Compress Databases and remove Original"); 
    	
    	

    	removeOriginal= new JRadioButton("Remove decompressed Database"); 
    	decompress= new JRadioButton("Decompress Database"); 
    	
    	
    	Compress.add(doNotCompress);
    	Compress.add(doCompressKeepOriginal);
    	Compress.add(doCompressRemoveOriginal);
    	
    	
      	Compress.add(removeOriginal);
    	Compress.add(decompress);
    
    
    	
    	if(SimulationSettings.DO_COMPRESS_KEEP_ORIGINAL==1)
		{
    		System.out.println("BP1");
			doCompressKeepOriginal.setSelected(true);
			doCompressRemoveOriginal.setSelected(false);
			doNotCompress.setSelected(false);
			removeOriginal.setSelected(false); 
	    	decompress.setSelected(false); 
			
		}else if(SimulationSettings.DO_COMPRESS_REMOVE_ORIGINAL==1){
			
			System.out.println("BP2");
			
			doCompressKeepOriginal.setSelected(false);
			doCompressRemoveOriginal.setSelected(true);
			doNotCompress.setSelected(false);
			removeOriginal.setSelected(false); 
	    	decompress.setSelected(false);
		}else if(SimulationSettings.DO_REMOVE_DB==1){
				
				System.out.println("BP2");
				
				doCompressKeepOriginal.setSelected(false);
				doCompressRemoveOriginal.setSelected(false);
				doNotCompress.setSelected(false);
				removeOriginal.setSelected(true); 
		    	decompress.setSelected(false);
	    	
		}else if(SimulationSettings.DO_DECOMPRESS==1){
			
			System.out.println("BP2");
			
			doCompressKeepOriginal.setSelected(false);
			doCompressRemoveOriginal.setSelected(false);
			doNotCompress.setSelected(false);
			removeOriginal.setSelected(false); 
	    	decompress.setSelected(false);
    	
		
	
		}else {
			
			System.out.println("BP3");
			
			doCompressKeepOriginal.setSelected(false);
			doCompressRemoveOriginal.setSelected(false);
			doNotCompress.setSelected(true);
			removeOriginal.setSelected(false); 
	    	decompress.setSelected(false);
		}
    	
    	
    	

    	doCompressKeepOriginal.addItemListener(new itemHandler3());
        doNotCompress.addItemListener(new itemHandler3());
        doCompressRemoveOriginal.addItemListener(new itemHandler3());
    	removeOriginal.addItemListener(new itemHandler3());
    	decompress.addItemListener(new itemHandler3());
        

		  Dimension minSize = new Dimension(5, 10);
	      Dimension prefSize = new Dimension(5, 10);
	      Dimension maxSize = new Dimension(Short.MAX_VALUE, 10);
        
	        lcLeftPanel.add(new Box.Filler(minSize, prefSize, maxSize));
	        lcLeftPanel.add(doNotCompress );
	        
	      
        lcLeftPanel.add(new Box.Filler(minSize, prefSize, maxSize));  
        lcLeftPanel.add(doCompressRemoveOriginal );
      
        
       lcLeftPanel.add(new Box.Filler(minSize, prefSize, maxSize));
        lcLeftPanel.add(doCompressKeepOriginal );
        

        lcLeftPanel.add(new Box.Filler(minSize, prefSize, maxSize));
        lcLeftPanel.add(removeOriginal );
        
      lcLeftPanel.add(new Box.Filler(minSize, prefSize, maxSize));
        lcLeftPanel.add(decompress );
      
        panelAA.add( lcLeftPanel);
	    
	    lcRightPanel = new JPanel();
	    
	    
		
	    lcRightPanel.setLayout(new GridBagLayout());
		GridBagConstraints g = new GridBagConstraints();
		g.insets = new Insets( 5, 5, 5, 5);
		
		/*Draw table with options*/
		
	
		
		String[] colHeaders = {"Agent", "Record","Period","Phase"}; 
		tabAgentsModel = new AgentTableModel(colHeaders,AgentSettings.agents);
		tabAgents = new DrawStoreOptionTable(tabAgentsModel, colHeaders);
		
		/*XXX*/
		 
		listScrollAgentTable = new JScrollPane(tabAgents);  
		listScrollAgentTable.setPreferredSize(new Dimension(280, 179)); 
	    g.gridx = 0;
	    g.gridy= 0;
	    lcRightPanel.add(listScrollAgentTable,g);
	    lcRightPanel.validate();
	    
	    
	    
	    panelAA.add( lcRightPanel);
	    
	    luP.gridx =0 ; luP.gridy=0;
	    add(panelAA, luP);
	    
	    panelBB = new JPanel();
	    
	    JLabel lab = new JLabel("Write all agent variables to Database");
	    
	    
	    
	    rbYesStoreAll = new JRadioButton("Yes"); 
	    rbNoStoreAll = new JRadioButton("No");
	    
	    rbYesStoreAll.addItemListener(new itemHandlerSaveAllvars());
	    rbNoStoreAll.addItemListener(new itemHandlerSaveAllvars());
	    soreAllvariables = new ButtonGroup();
	    
	    
	    if(SimulationSettings.saveAllAgentVariables){
	    	
	    	rbYesStoreAll.setSelected(true);
	    	
	    }else{
	    	
	    	rbNoStoreAll.setSelected(true);
	    	
	    }
    	
	    soreAllvariables.add(rbYesStoreAll);
	    soreAllvariables.add(rbNoStoreAll);
	    
	    panelBB.add(lab);
	    panelBB.add(rbYesStoreAll);
	    panelBB.add(rbNoStoreAll);
	    
	    luP.gridx =0 ; luP.gridy=1;
	    add(panelBB, luP);
	    
	    setPreferredSize(new Dimension(this.getSize().width-20,300));
		//setVisible(true);
		
		
		
		
		
	}
	
	void enableElements(boolean enable){
		
		if(enable){
			
			for(int i=0; i< lcLeftPanel.getComponentCount();i++){
				
				lcLeftPanel.getComponent(i).setEnabled(true);
				
			}
			
			
			
			for(int i=0; i< panelBB.getComponentCount();i++){
				
				panelBB.getComponent(i).setEnabled(true);
				
			}
			
			tabAgents.setRowSelectionAllowed(false);
			tabAgents.setColumnSelectionAllowed(false);
			
		}else{
			
			for(int i=0; i< lcLeftPanel.getComponentCount();i++){
				
				lcLeftPanel.getComponent(i).setEnabled(false);
				
			}
			
			
			
			for(int i=0; i< panelBB.getComponentCount();i++){
				
				panelBB.getComponent(i).setEnabled(false);
				
			}
			
			
		}
		
	}
	
	
	}
	
	private class itemHandler implements ItemListener {

		public void itemStateChanged(ItemEvent e) {
			
			/*GUI 1*/
			
			System.out.println(doRun.isSelected());
			
			if(doRun.isSelected())
			{
				SimulationSettings.DO_RUN = 1;
				System.out.println("DO_RUN:"+SimulationSettings.DO_RUN);
				
				for(int i=0; i< topArea.getComponentCount(); i++){
					
					topArea.getComponent(i).setEnabled(true);
					
				}
				
				for(int i=0; i< leftPanel.getComponentCount(); i++){
					
					
					
					
					leftPanel.getComponent(i).setEnabled(true);
								
							}
				
				
				
				if(SimulationSettings.numParameters==1){
					
					for (int i=0; i < rightPanel.getComponentCount();i++){
						
						rightPanel.getComponent(i).setEnabled(true);
					
						
					}
					
					
					
				}
				
			
				lowArea.enableElements(true);
				
			}else if(doNotRun.isSelected())
			{
				
				
				SimulationSettings.DO_RUN=0;
				System.out.println("DO_RUN:"+SimulationSettings.DO_RUN);
				
				for(int i=0; i< topArea.getComponentCount(); i++){
					
					topArea.getComponent(i).setEnabled(false);
					
				}
				
				
for(int i=0; i< leftPanel.getComponentCount(); i++){
	
	leftPanel.getComponent(i).setEnabled(false);
				
			}
				
lowArea.enableElements(false);
				
if(SimulationSettings.numParameters==1){
	
	for (int i=0; i < rightPanel.getComponentCount();i++){
		
		rightPanel.getComponent(i).setEnabled(false);


		
		
	}
	
	
	
}
				
			}
		
		}
		
		
	}
	
	

	private class itemHandlerNumProcs implements ItemListener {

		
		public void itemStateChanged(ItemEvent e) {
			
			SimulationSettings.numProcessors=Integer.parseInt(cbNumProcessors.getSelectedItem().toString());
		}
		
		
	}
	
	
	
	

	
	private class itemHandler2 implements ItemListener {

		public void itemStateChanged(ItemEvent e) {
			
			/*GUI 1*/
			
			ArrayList<SimulationSettings.Value>  emptyArrayList = new ArrayList<SimulationSettings.Value>();
			
			if(justBatchRuns.isSelected())
		    {
				
				SimulationSettings.numParameters =0;
				
		    	buttonActivateParameter1.setEnabled(false);
		    	
		    	
		    	//rightPanel.repaint();
		    	rightPanel.remove(listScroll);
		    
		    	//upperCenterArea.repaint();

		    	rightPanel.validate();
		    	
		    	tabModel = new ParameterTableModel("",emptyArrayList,false);
		    	table = new JTable(tabModel);
		    	table.getColumnModel().getColumn(0).setHeaderValue("");
		    	listScroll = new JScrollPane(table);  
			    listScroll.setPreferredSize(new Dimension(130, 150));  
			    rp.gridx = 0;
			    rp.gridy= 1;
			    rightPanel.add(listScroll,rp);
			   
			    
			    System.out.println("justBatchRuns.isSelected()");
			   
			    
		
			    
			    rightPanel.validate();
			    
		    	
		    	
		    	
		    } else if(parameterVariationOnePars.isSelected())
		    {
		    	
		    	SimulationSettings.numParameters = 1;
		    	
		    	rightPanel.remove(listScroll);
		    	
		    	rightPanel.validate();
		    	
		    	tabModel = new ParameterTableModel(SimulationSettings.PARAMETER_1.name,SimulationSettings.PARAMETER_1.values,true);
		    	table = new JTable(tabModel);
		    	table.getColumnModel().getColumn(0).setHeaderValue(SimulationSettings.PARAMETER_1.name);
		    	listScroll = new JScrollPane(table);  
			    listScroll.setPreferredSize(new Dimension(130, 150));  
			    rp.gridx = 0;
			    rp.gridy= 1;
			    rightPanel.add(listScroll,rp);
			   
			    col = table.getColumnModel().getColumn(0);
		    	col.setCellEditor(new CellEditor());	
		    	col.getCellEditor().addCellEditorListener(new EditorListener(){

		    		public void editingStopped(ChangeEvent e) {

		    			/*If Textfield is empty remove this row!*/
		    			if(col.getCellEditor().getCellEditorValue().toString().equals("") && tabModel.getRowCount()>1 )
		    			{
		    				tabModel.delRow(((CellEditor) col.getCellEditor()).getRow());
		    				
		    			}else if(col.getCellEditor().getCellEditorValue().toString().equals("") && tabModel.getRowCount()<=1)
		    			{
		    				//Do noting
		    			}else{
			    	        if(SimulationSettings.PARAMETER_1.type.equals("int"))
			    			{
			    				try{
			    					
			    					Integer.parseInt(col.getCellEditor().getCellEditorValue().toString());
			    					tabModel.setValueAt(col.getCellEditor().getCellEditorValue().toString(), ((CellEditor) col.getCellEditor()).getRow(), 0);
						    	    tabModel.updatetable();
			    					
			    				}catch(Exception ex)
			    				{
			    					JOptionPane.showMessageDialog(null,"Parameter must be an integer!");
			    					
			    				}		
			    				
			    			}else
			    			{
			    				try{
			    					Double.parseDouble(col.getCellEditor().getCellEditorValue().toString());
			    					tabModel.setValueAt(col.getCellEditor().getCellEditorValue().toString(), ((CellEditor) col.getCellEditor()).getRow(), 0);
						    	    tabModel.updatetable();
			    					
			    				}catch(Exception ex)
			    				{
			    					JOptionPane.showMessageDialog(null,"Parameter must be a numeric expression!");
			    					
			    				}
			    				
			    			}	
		    	        
		    			}

		    	    }
		    		

		    	    /**
		    	     * Listens for cells where editing has been canceled (cell data has not been
		    	     * changed).
		    	     */
		    	    public void editingCanceled(ChangeEvent e) {
		    	        System.out.println("Editing of a cell has been canceled.");
		    	    }

		    	});
			  
			    
			    
		
			    
			    System.out.println("parameterVariationOnePars.isSelected()");
			    rightPanel.validate();
			    
		    	buttonActivateParameter1.setEnabled(true);
		 
		    	
		    } 
			
		
		}
		
		
	}
	
	



private class itemHandler3 implements ItemListener {

	public void itemStateChanged(ItemEvent e) {
		
		

		
			
			
			if(doCompressKeepOriginal.isSelected())
			{
				SimulationSettings.DO_COMPRESS_KEEP_ORIGINAL=1;
				SimulationSettings.DO_COMPRESS_REMOVE_ORIGINAL=0;
				SimulationSettings.DO_REMOVE_DB = 0;
				SimulationSettings.DO_DECOMPRESS =0;
				
			}else if(doCompressRemoveOriginal.isSelected()){
				
				SimulationSettings.DO_COMPRESS_KEEP_ORIGINAL=0;
				SimulationSettings.DO_COMPRESS_REMOVE_ORIGINAL=1;
				SimulationSettings.DO_REMOVE_DB = 0;
				SimulationSettings.DO_DECOMPRESS =0;
				
			}else if(doNotCompress.isSelected()){
				
				SimulationSettings.DO_COMPRESS_KEEP_ORIGINAL=0;
				SimulationSettings.DO_COMPRESS_REMOVE_ORIGINAL=0;
				SimulationSettings.DO_REMOVE_DB = 0;
				SimulationSettings.DO_DECOMPRESS =0;
			}else if(removeOriginal.isSelected()){
				
				SimulationSettings.DO_COMPRESS_KEEP_ORIGINAL=0;
				SimulationSettings.DO_COMPRESS_REMOVE_ORIGINAL=0;
				SimulationSettings.DO_REMOVE_DB = 1;
				SimulationSettings.DO_DECOMPRESS =0;
				
			}else if(decompress.isSelected()){
				
				SimulationSettings.DO_COMPRESS_KEEP_ORIGINAL=0;
				SimulationSettings.DO_COMPRESS_REMOVE_ORIGINAL=0;
				SimulationSettings.DO_REMOVE_DB = 0;
				SimulationSettings.DO_DECOMPRESS =1;
				
			}
			
			
			
			
	
	}
	
	
}






private class itemHandlerSaveAllvars implements ItemListener {

	public void itemStateChanged(ItemEvent e) {
		
		/*GUI 1*/
		
		
		
		if(rbYesStoreAll.isSelected()){
			
			SimulationSettings.saveAllAgentVariables = true;
			
		}else{
			
			SimulationSettings.saveAllAgentVariables = false;
		}
		
			
	
	}
	
	
}



}
