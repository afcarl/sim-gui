import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener; 
import java.awt.event.ItemEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.EventListener;

import javax.swing.JButton; 
import javax.swing.event.ChangeEvent;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class MultiGUI extends JFrame{
	
	JScrollPane settingScrollPane, plottingScrollPane;
	
	
	/*Tabs*/
	
	private	JTabbedPane mainTabPane;
	
	/*Menu bar*/
	
	private JPanel  buttomArea, guiContainer;
	private JMenuBar menuBar;
	private JMenu menuExperiment;
	private JMenuItem loadExperiment, newExperiment, saveExperiment, saveExperimentAs,  exitGUI;
	private JMenu menuSettings, setPathes;
	private JMenuItem setPathModelXML,setExecutable, setZeroXMLFile, setPathRScripts;
	
	
	private JMenu menuImportExport, menuimportPlottingSections, menuimportParameterSections;
	private JMenuItem importPlottingSettings, exportPlottingSettings, importParameterSettings, exportParameterSettings;
	
	
	private JMenu plottingSettings,timeSeries,normalTimeSeries, bandpassFilter,distributions,correlations;
	
	private JMenuItem selectVariableList, singleTimeSeries, multipleTimeSeries, singleBandpassFilter, multipleBandpassFilter, timeScatterPlots,crossCorrelation, histrogramms, boxplots, heatmaps, correlationCoefficient, heatmaps2V ;
	
	JScrollPane globalScrollPane;
	
	private JMenuItem runBatchExperiments ;
	
	GridBagConstraints GlobalGrid ;
	

	
	
	
    ButtonGroup soreAllvariables;
	private JRadioButton justBatchRuns;
	
	private ButtonGroup expSetup;
	public static boolean variableRemoved;
	
	
	
	/*Vars GUI 2*/
	

	private JScrollPane listScrollAgentTable;
	private AgentTableModel tabAgentsModel;
	
	
	TabSettings settingContainer;
	
	TabPlotting plottingContainer;
	
	private JPanel lcRightPanel ;
	
	/*Plotting:*/
	
	private JTextField transitionPhase;
	private JCheckBox makeLegend;
	private JCheckBox colored;

	
	/*Buttom panel*/
	
	private BuildExperiment buttonBuild;
	private JButton buttonRun;
	
	
	
	
	ParameterTableModel tabModel,tabModelP2;
	JTable table,tableP2 ;
	
	 String[] parameterType ;
	 String[] parameterNames ;
	 String[] parameterDescription ;
	 
	String parameterName, parameterTy;
	
	/*List holding all information about the model parameters*/

	
	/*List of agent names which is read from the eurace model xml file*/
	ArrayList<String>  agentList ;
	

	
	public MultiGUI(){
		
		super("Eurace@Unibi GUI");
		
		
		guiContainer = new JPanel();
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener( new WindowAdapter(){
			
			public void windowClosing(WindowEvent e)
			{

	    		Object text = "Do you want to save the settings before quitting? \n";
	    		
	    		int choice = JOptionPane.showConfirmDialog(null, text,  "Exit GUI",JOptionPane.YES_NO_CANCEL_OPTION);
	   
	    		if(choice==0){
	 
	    			/*Save Settings*/
	    			SaveSettings();
	    			
	    			setVisible(false);
	    			dispose();
	    			
	    			System.exit(-1);
	    			
	    		}else if(choice==1){
	    			
	    			/*Choice is no*/
	    			setVisible(false);
	    			dispose();
	    			
	    		}
	   
    			
			}
			});
			
		
		
		/*Read generic settings*/
		
		/*Check if the file is there*/

		 new SimulationSettings();
		 
		 SimulationSettings.WORKING_DIRECTORY = WorkspaceLauncher.workspacePath;
		
		LoadSettings(SimulationSettings.WORKING_DIRECTORY+"/GlobalSettings.xml");
		
		
		
		/*Draw tabs*/
		
		settingContainer = new TabSettings();
		
		plottingContainer = new TabPlotting();
		
		
		/********Menu Bar**************/
		menuBar = new JMenuBar();
		menuExperiment = new JMenu("Experiment");
		
		menuBar.add(menuExperiment);
		
		newExperiment = new JMenuItem("New");
		loadExperiment = new JMenuItem("Load");
		saveExperiment = new JMenuItem("Save");
		saveExperimentAs = new JMenuItem("Save as...");
		
		exitGUI = new JMenuItem("Exit");
		
		menuExperiment.add(newExperiment);
		
		newExperiment.addActionListener(new ActionListener(){
	    		
    		public void actionPerformed(ActionEvent evt) {
    		    
    			FileChooserFromMenuList chooseFile = new FileChooserFromMenuList(SimulationSettings.WORKING_DIRECTORY,true, false,"", false);
    			chooseFile.openFileChooser();
    			SimulationSettings.WORKING_DIRECTORY = chooseFile.getDirectoryOrFile();
    			
    			/*Set up agent list*/
    			//AgentSettings.agents = new ArrayList<Agent>();
    			
    			SimulationSettings.experimentBuilt = false;
    			buttonRun.setEnabled(SimulationSettings.experimentBuilt);
    			
    			
    		    	
    		}
    		
    	});
		
		
		
		menuExperiment.add(loadExperiment);
		
		loadExperiment.addActionListener(new ActionListener(){
    		
    		public void actionPerformed(ActionEvent evt) {
    		    
    			FileChooserFromMenuList chooseFile = new FileChooserFromMenuList(SimulationSettings.WORKING_DIRECTORY,true, false,"", false);
    			chooseFile.openFileChooser();
    			SimulationSettings.WORKING_DIRECTORY = chooseFile.getDirectoryOrFile();
    			//menuExperiment.add(chooseFile);
    			
    			
    			/*Read specific experiment settings*/
    			
    			File file = new File(SimulationSettings.WORKING_DIRECTORY+"/GlobalSettings.xml");
    			
    			FileReader fr;
    			
    			String testString;
    		    
    			try {
    				
    				fr = new FileReader(file);
    				BufferedReader br = new BufferedReader(fr);
    				
    				testString = br.readLine();
    				System.out.println(testString);
    				
    				boolean isEmpty = false;
    				
    				if(testString == null)
    				{
    					isEmpty = true;
    				}
    				
    				System.out.println(isEmpty+testString);
    				
    				br.close();
    				
    				/*Read Settings*/
    				LoadSettings(SimulationSettings.WORKING_DIRECTORY+"/GlobalSettings.xml");
    				
    				
    				drawAgentTable();
    				settingContainer.drawTableParemters();
    				
    		    	if(SimulationSettings.DO_COMPRESS_KEEP_ORIGINAL==1)
    				{
    		    		settingContainer.doCompressKeepOriginal.setSelected(true);
    		    		settingContainer.doCompressRemoveOriginal.setSelected(false);
    		    		settingContainer.doNotCompress.setSelected(false);
    					
    				}else if(SimulationSettings.DO_COMPRESS_REMOVE_ORIGINAL==1){
    					
    					settingContainer.doCompressKeepOriginal.setSelected(false);
    					settingContainer.doCompressRemoveOriginal.setSelected(true);
    					settingContainer.doNotCompress.setSelected(false);
    					
    				}else {
    					
    					settingContainer.doCompressKeepOriginal.setSelected(false);
    					settingContainer.doCompressRemoveOriginal.setSelected(false);
    					settingContainer.doNotCompress.setSelected(true);
    				}
    				
    				
    			} catch (IOException e1) {

    				JOptionPane.showMessageDialog(null,"XML File with saved settings not found. Please enter the settings manually"); 
    			}
    		}
    		
    	});
		
		
		/*Save Experiment:*/
		
		menuExperiment.add(saveExperiment);
		
		saveExperiment.addActionListener(new ActionListener(){
    		
    		public void actionPerformed(ActionEvent evt) {
    		    	
    			/*Save Settings*/
    			SaveSettings();
    		}
    		
    	});
		
		
		
/*Save as...Experiment:*/
		
		menuExperiment.add(saveExperimentAs);
		
		saveExperimentAs.addActionListener(new ActionListener(){
    		
    		public void actionPerformed(ActionEvent evt) {
    			
    			
    			FileChooserFromMenuList chooseFile = new FileChooserFromMenuList(SimulationSettings.WORKING_DIRECTORY,true, false,"", false);
    			chooseFile.openFileChooser();
    			SimulationSettings.WORKING_DIRECTORY = chooseFile.getDirectoryOrFile();
    		    
    			
    			/*Save Settings*/
    			SaveSettings();
    			
    		}
    		
    	});
		
		
		runBatchExperiments = new JMenuItem("Run Batch");
		
		menuExperiment.add(runBatchExperiments);
		
		runBatchExperiments.addActionListener(new ActionListener(){
    		
    		public void actionPerformed(ActionEvent evt) {
    			
    			new JDialogBatchExperiments();
    			
    			
    		}
    		
    	});
		
		menuExperiment.add(runBatchExperiments);

		
		menuExperiment.add(exitGUI);
		exitGUI.addActionListener(new ActionListener(){
    		
    	public void actionPerformed(ActionEvent evt) {
    		
    		
    		
    		Object text = "Do you want to save the settings before quitting? \n";
    		
    		int choice = JOptionPane.showConfirmDialog(null, text,  "Exit GUI",JOptionPane.YES_NO_CANCEL_OPTION);
   
    		if(choice==0){
    			
    			/*Choice is yes*/
    			String PathToFile = new String(SimulationSettings.WORKING_DIRECTORY+"/SimParameter.sh");
    			
    			System.out.println(SimulationSettings.WORKING_DIRECTORY);
    			System.out.println(PathToFile);
    		    
    			/*Safe the settings to XML file*/
    			SaveSettings();
    			
    			setVisible(false);
    			dispose();
    			
    			System.exit(-1);
    			
    			
    		}else if(choice==1){
    			
    			/*Choice is no*/
    			setVisible(false);
    			dispose();
    			
    			System.out.println("agents:   "+AgentSettings.agents.get(0).agentName);
    			
    			System.exit(-1);
    			
    		}
   
    		}
    	});
		
		menuSettings = new JMenu("Settings");
		menuBar.add(menuSettings);
		
		
		setPathes = new JMenu("Set Pathes");
		
		setPathModelXML = new JMenuItem("Set Path to Model.xml file");
		setPathModelXML.setToolTipText("Current path: "+SimulationSettings.EURACE_MODEL_XML);
		setExecutable = new JMenuItem("Set Model Executable");
		setExecutable.setToolTipText("Current Executable: "+SimulationSettings.MAIN_EXECUTABLE);
		setZeroXMLFile = new JMenuItem("Set initial Data File (0.xml)");
		setZeroXMLFile.setToolTipText("Current initial Data File: "+SimulationSettings.ZERO_XML_FILE);
		setPathRScripts = new JMenuItem("Set path to R Scripts"); 
		setPathRScripts.setToolTipText("Current path to R Scripts: "+SimulationSettings.PATH_TO_RSCRIPTS);
		
		setPathModelXML.addActionListener(new ActionListener(){
    		
    		public void actionPerformed(ActionEvent evt) {
    			
    			String pathBefore;

    		    
    		    FileChooserFromMenuList chooseFile = new FileChooserFromMenuList(SimulationSettings.EURACE_MODEL_XML,false, true,"xml", false);
    		    chooseFile.openFileChooser();
    		    
    		    pathBefore = SimulationSettings.EURACE_MODEL_XML;
    		    
    		    SimulationSettings.EURACE_MODEL_XML = chooseFile.getDirectoryOrFile();
    		    setPathModelXML.setToolTipText("Current path: "+SimulationSettings.EURACE_MODEL_XML);
    		    
    		    if(!pathBefore.equals(SimulationSettings.EURACE_MODEL_XML))
    		    {
	    		    /*Set agent list*/
	    		    agentList = ReadAgentListFromModelXML();
	    			AgentSettings.agents = new ArrayList<Agent>();
	    			
	    			PlottingSettings.listOfAgentsVariableInstances = new ArrayList <PlottingSettings.Agent>();

	    			/*List of ratio Instances*/
	    			PlottingSettings.listOfRatioInstances = new ArrayList <PlottingSettings.RatioInstance>();
	    			
	    			/*List of time series*/
	    			PlottingSettings.listOfSingleTimeSeries = new ArrayList <PlottingSettings.SingleTimeSeries>();
	    			PlottingSettings.defaultsSingleTimeSeries = (new PlottingSettings()).new  DefaulSingleTimeSeriesSettings();
	    			
	    			/*List of multiple time series*/
	    			PlottingSettings.listOfMultipleTimeSeries = new ArrayList <PlottingSettings.MultipleTimeSeries>();
	    			PlottingSettings.defaultsMultipleTimeSeries = (new PlottingSettings()).new  DefaulMultipleTimeSeriesSettings();
	    			
	    			
	    			/*List of multiple time series*/
	    			PlottingSettings.listOfSingleBandpassFilteredTimeSeries = new ArrayList <PlottingSettings.SingleBandpassFilteredTimeSeries>();
	    			PlottingSettings.defaultsSingleBandpassFilteredTimeSeries = (new PlottingSettings()).new  DefaultSettingsSingleBandpassFilteredTimeSeries();

	    			
	    			/*List of multiple time series*/
	    			PlottingSettings.listOfMultipleBandpassFilteredTimeSeries = new ArrayList <PlottingSettings.MultipleBandpassFilteredTimeSeries>();
	    			PlottingSettings.defaultsMultipleBandpassFilteredTimeSeries = (new PlottingSettings()).new  DefaultSettingsMultipleBandpassFilteredTimeSeries();

	    			
	    			/*List of multiple time series*/
	    			PlottingSettings.listOfHistograms = new ArrayList <PlottingSettings.Histogram>();
	    			PlottingSettings.defaultsHistogram = (new PlottingSettings()).new  DefaultSettingsHistogram();
	    			
	    			/*List of multiple time series*/
	    			PlottingSettings.listOfBoxplots = new ArrayList <PlottingSettings.Boxplots>();
	    			PlottingSettings.defaultsBoxplots = (new PlottingSettings()).new  DefaultSettingsBoxplots();


	    			/*List of multiple time series*/
	    			PlottingSettings.listOfHeatmaps = new ArrayList <PlottingSettings.Heatmaps>();
	    			PlottingSettings.defaultsHeatmaps = (new PlottingSettings()).new  DefaultSettingsHeatmaps();
	    			
	    			
	    			
	    			/*List of multiple time series*/
	    			PlottingSettings.listOfScatterPlots = new ArrayList <PlottingSettings.ScatterPlots>();
	    			PlottingSettings.defaultsScatterPlots = (new PlottingSettings()).new  DefaultSettingsScatterPlots();
	    			
	    			
	    			/*List of multiple time series*/
	    			PlottingSettings.listOfCrossCorrelation = new ArrayList <PlottingSettings.CrossCorrelation>();
	    			PlottingSettings.defaultsCrossCorrelation = (new PlottingSettings()).new  DefaultSettingsCrossCorrelation();
	    			
	    			/*List of multiple time series*/
	    			PlottingSettings.listOfCorrelation = new ArrayList <PlottingSettings.Correlation>();
	    			PlottingSettings.defaultsCorrelation = (new PlottingSettings()).new  DefaultSettingsCorrelation();
	    			
	    			/*List of multiple time series*/
	    			PlottingSettings.listOfHeatmaps2V = new ArrayList <PlottingSettings.Heatmaps2V>();
	    			PlottingSettings.defaultsHeatmaps2V = (new PlottingSettings()).new  DefaultSettingsHeatmaps2V();
	    			
	    			for(int i=0; i<agentList.size();i++)
	    			{
	    				
	    				AgentSettings.agents.add(new Agent(agentList.get(i)));
	    				PlottingSettings.Agent temAgent =  (new PlottingSettings()).new Agent(agentList.get(i));
	    				PlottingSettings.listOfAgentsVariableInstances.add(temAgent);
	    				
	    			}
	    			
	    			/*Read Model parameters*/	
	    			 ReadModelParameter modelXML = new ReadModelParameter();
	    			 modelXML.getFIleListDirectlyFromEuraceModelXML();
	    			 ModelParameterSettings.modelParameters = modelXML.GetModelParameterFromModelXMLFiles();
	    			 
	    			     			
	    		    plottingContainer = new TabPlotting();
	    			settingContainer = new TabSettings();
	    		    
	    			plottingContainer.validate();
	    			settingContainer.validate();
	    			

	    	      	/*Add GUI container to Scroll pane*/
	    	      	settingScrollPane = new JScrollPane(settingContainer);
	    	      	settingScrollPane.setPreferredSize(new Dimension(800, 1200));
	    	      	
	    	      	
	    	      	
	    	      	
	    	      	plottingScrollPane = new JScrollPane(plottingContainer);
	    	      	plottingScrollPane.setPreferredSize(new Dimension(800, 1200));
	    	      	
	    	      	/*Add Scroll pane to JFrame*/
	    	      	
	    	      	//add(guiScrollPane);
	    	      	mainTabPane.remove(1);


				mainTabPane.remove(0);
	    	      	 
	    	    	 mainTabPane.add(settingScrollPane,"Simulation Settings");
	    	      	 
	    	      	 mainTabPane.add(plottingScrollPane,"Plotting Settings");
	    			
	    			
	    			validate();
	    			
				
	    		    
	    			validate();
    		    }
			    
    		}
    	});
		
		
		setExecutable.addActionListener(new ActionListener(){
    		
    		public void actionPerformed(ActionEvent evt) {
    			FileChooserFromMenuList chooseFile ;
    			
    			if(SimulationSettings.MAIN_EXECUTABLE.equals("."))
    				chooseFile = new FileChooserFromMenuList(SimulationSettings.EURACE_MODEL_XML,false, false,"", false);
    			else
    				chooseFile = new FileChooserFromMenuList(SimulationSettings.MAIN_EXECUTABLE,false, false,"", false);
    		    
    		    
    
    		    chooseFile.openFileChooser();
    		    SimulationSettings.MAIN_EXECUTABLE = chooseFile.getDirectoryOrFile();
    		    setExecutable.setToolTipText("Current Executable: "+SimulationSettings.MAIN_EXECUTABLE);
			    
    		}
    	});
		
		
		setZeroXMLFile.addActionListener(new ActionListener(){
    		
    		public void actionPerformed(ActionEvent evt) {
    			
    			FileChooserFromMenuList chooseFile;
    			
    			if(SimulationSettings.ZERO_XML_FILE.equals("0.xml"))
    				chooseFile = new FileChooserFromMenuList(SimulationSettings.EURACE_MODEL_XML,false, true,"xml", false);
    			else
    				chooseFile = new FileChooserFromMenuList(SimulationSettings.ZERO_XML_FILE,false, true,"xml", false);
    		    
    		    chooseFile.openFileChooser();
    		    SimulationSettings.ZERO_XML_FILE = chooseFile.getDirectoryOrFile();
    		    setExecutable.setToolTipText("Current Executable: "+SimulationSettings.ZERO_XML_FILE);
			    
    		}
    	});
		
		
		setPathRScripts.addActionListener(new ActionListener(){
    		
    		public void actionPerformed(ActionEvent evt) {
    		    
    		    FileChooserFromMenuList chooseFile = new FileChooserFromMenuList(SimulationSettings.PATH_TO_RSCRIPTS,true, false,"", false);
    		    chooseFile.openFileChooser();
    		    SimulationSettings.PATH_TO_RSCRIPTS = chooseFile.getDirectoryOrFile();
    		    setPathRScripts.setToolTipText("Current pat: "+SimulationSettings.PATH_TO_RSCRIPTS);
			    
    		}
    	});
		
		
		setPathes.add(setPathModelXML);
		setPathes.add(setExecutable);
		setPathes.add(setZeroXMLFile);
		setPathes.add(setPathRScripts);
		
		menuSettings.add(setPathes);
		
		
		/*menu to import and export settings: plotting settings and initial parameters*/
		menuImportExport = new JMenu("Import/Export");
		
		
		menuimportPlottingSections = new JMenu("Plotting Settings");
		
		importPlottingSettings = new JMenuItem("Import Plotting Settings");
		exportPlottingSettings = new JMenuItem("Export Plotting Settings");
		
		
		importPlottingSettings.addActionListener(new ActionListener(){

			
			public void actionPerformed(ActionEvent e) {
				

				try {
					
				
					FileChooserFromMenuList chooseFile = new FileChooserFromMenuList(SimulationSettings.WORKING_DIRECTORY,false, true,"xml", false);
	    			chooseFile.openFileChooser();
	    			
	    			String file = chooseFile.getDirectoryOrFile();
	   
	    			File settingsFile = new File(chooseFile.getDirectoryOrFile());
	
					
					if(!settingsFile.exists()){
						
						throw new FileNotFoundException();
						
					}
					
					ReadXMLFile defValues = new ReadXMLFile(file);
					defValues.new ReadClassFromXML(new PlottingSettings(),"plottingSettings");
					
					
					for(int i= 0; i < PlottingSettings.listOfAgentsVariableInstances.size();i++){
						
						
						for(int j=0; j < AgentSettings.agents.size();j++){
							
							
							
							if(PlottingSettings.listOfAgentsVariableInstances.get(i).agentName.equals(AgentSettings.agents.get(j).agentName)){
								
								/*Check if variable is there:*/
								
								for(int k=0; k < PlottingSettings.listOfAgentsVariableInstances.get(i).listOfVariableInstances.size();k++){
									
									
									if(PlottingSettings.listOfAgentsVariableInstances.get(i).listOfVariableInstances.get(k).isVariable){
									
										boolean  found = false;
										
										for(int l=0; l < AgentSettings.agents.get(j).variableList.size();l++){
											
											AgentSettings.agents.get(j).variableList.get(l).isSelectedForBoxplots = false;
											
											if(PlottingSettings.listOfAgentsVariableInstances.get(i).listOfVariableInstances.get(k).variable.name.equals(AgentSettings.agents.get(j).variableList.get(l).name)){
												
												AgentSettings.agents.get(j).variableList.get(l).isSelectedForBoxplots = true;
												found = true;
												break;
											}
							
											
										}
										
										if(!found){
											
											PlottingSettings.listOfAgentsVariableInstances.get(i).listOfVariableInstances.remove(k);
											k--;
											
										}
									
									}else{
									/*If agent ratio*/
									
										boolean numeratorFound = false;
										boolean denominatorFound = false;
										
										for(int l=0; l < AgentSettings.agents.get(j).variableList.size();l++){
											
												if(PlottingSettings.listOfAgentsVariableInstances.get(i).listOfVariableInstances.get(k).agentRatio.numerator.equals(AgentSettings.agents.get(j).variableList.get(l).name)){
													numeratorFound = true;
												}
												else if(PlottingSettings.listOfAgentsVariableInstances.get(i).listOfVariableInstances.get(k).agentRatio.denominator.equals(AgentSettings.agents.get(j).variableList.get(l).name)){
													
													denominatorFound = true;
													
												}
											
											}
										
										/*If either numerator or denominator not found remove agent ratio*/
											if(!numeratorFound || !denominatorFound){
												
												PlottingSettings.listOfAgentsVariableInstances.get(i).listOfVariableInstances.remove(k);
												k--;
												
											}
											
											
										}
									
									
									
								}
								
							}
							
						}
						
						
						
					}
					
					
					/*Check histograms*/
					
					for(int i=0; i < PlottingSettings.listOfHistograms.size();i++){
						
						for(int j=0; j < AgentSettings.agents.size();j++){
							
							if(PlottingSettings.listOfHistograms.get(i).histBelongsTo.equals(AgentSettings.agents.get(j).agentName)){
								
								/*Check if variable is there:*/
								
								if(PlottingSettings.listOfHistograms.get(i).isVariable){
									
									boolean  found = false;
									
									for(int l=0; l < AgentSettings.agents.get(j).variableList.size();l++){
										
										AgentSettings.agents.get(j).variableList.get(l).isSelectedForHistograms = false;
										
										if(PlottingSettings.listOfHistograms.get(i).variable.name.equals(AgentSettings.agents.get(j).variableList.get(l).name)){
											
											AgentSettings.agents.get(j).variableList.get(l).isSelectedForHistograms = true;
											found = true;
											break;
										}
						
										
									}
									
									if(!found){
										
										PlottingSettings.listOfHistograms.remove(i);
										i--;
										
									}
								
								}else{
								/*If agent ratio*/
								
									boolean numeratorFound = false;
									boolean denominatorFound = false;
									
									for(int l=0; l < AgentSettings.agents.get(j).variableList.size();l++){
										
											if(PlottingSettings.listOfHistograms.get(i).agentRatio.numerator.equals(AgentSettings.agents.get(j).variableList.get(l).name)){
												numeratorFound = true;
											}
											else if(PlottingSettings.listOfHistograms.get(i).agentRatio.denominator.equals(AgentSettings.agents.get(j).variableList.get(l).name)){
												
												denominatorFound = true;
												
											}
										
										}
									
									/*If either numerator or denominator not found remove agent ratio*/
										if(!numeratorFound || !denominatorFound){
											
											PlottingSettings.listOfHistograms.remove(i);
											i--;
											
										}
										
										
									}
									
								
								}
							
							}
							

						
						
					}
					
					
					/*Check Boxplots*/
					
					for(int i=0; i < PlottingSettings.listOfBoxplots.size();i++){
						
						for(int j=0; j < AgentSettings.agents.size();j++){
							
							if(PlottingSettings.listOfBoxplots.get(i).histBelongsTo.equals(AgentSettings.agents.get(j).agentName)){
								
								/*Check if variable is there:*/
								
								if(PlottingSettings.listOfBoxplots.get(i).isVariable){
									
									boolean  found = false;
									
									for(int l=0; l < AgentSettings.agents.get(j).variableList.size();l++){
										
										AgentSettings.agents.get(j).variableList.get(l).isSelectedForBoxplots = false;
										
										if(PlottingSettings.listOfBoxplots.get(i).variable.name.equals(AgentSettings.agents.get(j).variableList.get(l).name)){
											
											AgentSettings.agents.get(j).variableList.get(l).isSelectedForBoxplots = true;
											found = true;
											break;
										}
						
										
									}
									
									if(!found){
										
										PlottingSettings.listOfBoxplots.remove(i);
										i--;
										
									}
								
								}else{
								/*If agent ratio*/
								
									boolean numeratorFound = false;
									boolean denominatorFound = false;
									
									for(int l=0; l < AgentSettings.agents.get(j).variableList.size();l++){
										
											if(PlottingSettings.listOfBoxplots.get(i).agentRatio.numerator.equals(AgentSettings.agents.get(j).variableList.get(l).name)){
												numeratorFound = true;
											}
											else if(PlottingSettings.listOfBoxplots.get(i).agentRatio.denominator.equals(AgentSettings.agents.get(j).variableList.get(l).name)){
												
												denominatorFound = true;
												
											}
										
										}
									
									/*If either numerator or denominator not found remove agent ratio*/
										if(!numeratorFound || !denominatorFound){
											
											PlottingSettings.listOfBoxplots.remove(i);
											i--;
											
										}
										
										
									}
									
								
								}
							
							}
							

						
						
					}
					
					/*Check heatmaps*/
					
					
					
				for(int i=0; i < PlottingSettings.listOfHeatmaps.size();i++){
						
						for(int j=0; j < AgentSettings.agents.size();j++){
							
							if(PlottingSettings.listOfHeatmaps.get(i).histBelongsTo.equals(AgentSettings.agents.get(j).agentName)){
								
								/*Check if variable is there:*/
								
								if(PlottingSettings.listOfHeatmaps.get(i).isVariable){
									
									boolean  found = false;
									
									for(int l=0; l < AgentSettings.agents.get(j).variableList.size();l++){
										
										AgentSettings.agents.get(j).variableList.get(l).isSelectedForHeatmaps = false;
										
										if(PlottingSettings.listOfHeatmaps.get(i).variable.name.equals(AgentSettings.agents.get(j).variableList.get(l).name)){
											
											AgentSettings.agents.get(j).variableList.get(l).isSelectedForHeatmaps = true;
											found = true;
											break;
										}
						
										
									}
									
									if(!found){
										
										PlottingSettings.listOfHeatmaps.remove(i);
										i--;
										
									}
								
								}else{
								/*If agent ratio*/
								
									boolean numeratorFound = false;
									boolean denominatorFound = false;
									
									int indFound1, indFound2;
									
									indFound1=0;
									indFound2=0;
									
									for(int l=0; l < AgentSettings.agents.get(j).variableList.size();l++){
										
											if(PlottingSettings.listOfHeatmaps.get(i).agentRatio.numerator.equals(AgentSettings.agents.get(j).variableList.get(l).name)){
												numeratorFound = true;
												indFound1 = l; 
											}
											else if(PlottingSettings.listOfHeatmaps.get(i).agentRatio.denominator.equals(AgentSettings.agents.get(j).variableList.get(l).name)){
												
												denominatorFound = true;
												indFound2 = l;
												
											}
										
										}
									
									/*If either numerator or denominator not found remove agent ratio*/
										if(!numeratorFound || !denominatorFound){
											
											PlottingSettings.listOfHeatmaps.remove(i);
											i--;
											
										}else{
											
											
											AgentSettings.agents.get(j).variableList.get(indFound1).isSelectedForHeatmaps = true;
											AgentSettings.agents.get(j).variableList.get(indFound2).isSelectedForHeatmaps = true;
											
										}
										
										
									}
									
								
								}
							
							}
							

						
						
					}
				
				
				/*Check correlation coeff*/
				
				for(int i=0; i < PlottingSettings.listOfCorrelation.size();i++){
					
					for(int j=0; j < AgentSettings.agents.size();j++){
						
						if(PlottingSettings.listOfCorrelation.get(i).histBelongsTo.equals(AgentSettings.agents.get(j).agentName)){
							
							/*Check if variable is there:*/
							
					
								boolean  found1 = false;
								
								for(int l=0; l < AgentSettings.agents.get(j).variableList.size();l++){
									
									AgentSettings.agents.get(j).variableList.get(l).isSelectedForBoxplots = false;
									
									if(PlottingSettings.listOfCorrelation.get(i).variable1.name.equals(AgentSettings.agents.get(j).variableList.get(l).name)){
										
										AgentSettings.agents.get(j).variableList.get(l).isSelectedForCorrelation = true;
										found1 = true;
										break;
									}
									
								}
					
									boolean  found2 = false;
									
									for(int l=0; l < AgentSettings.agents.get(j).variableList.size();l++){
										
										AgentSettings.agents.get(j).variableList.get(l).isSelectedForBoxplots = false;
										
										if(PlottingSettings.listOfCorrelation.get(i).variable2.name.equals(AgentSettings.agents.get(j).variableList.get(l).name)){
											
											AgentSettings.agents.get(j).variableList.get(l).isSelectedForCorrelation = true;
											found2 = true;
											break;
										}
								
								
								if(!found1 || !found2 ){
									
									PlottingSettings.listOfCorrelation.remove(i);
									i--;
									
								}
							
							}
								
							
							}
						
						}
						
				}
				
				
				/*Check heatmaps 2V*/
				
				
				
				for(int i=0; i < PlottingSettings.listOfHeatmaps2V.size();i++){
						
						for(int j=0; j < AgentSettings.agents.size();j++){
							
							if(PlottingSettings.listOfHeatmaps2V.get(i).histBelongsTo.equals(AgentSettings.agents.get(j).agentName)){
								
								/*Check if variable is there:*/
								
								if(PlottingSettings.listOfHeatmaps2V.get(i).isVariable1){
									
									boolean  found = false;
									
									for(int l=0; l < AgentSettings.agents.get(j).variableList.size();l++){

										if(PlottingSettings.listOfHeatmaps2V.get(i).variable1.name.equals(AgentSettings.agents.get(j).variableList.get(l).name)){
											
										
											found = true;
											break;
										}
						
										
									}
									
									if(!found){
										
										PlottingSettings.listOfHeatmaps2V.remove(i);
										i--;
										break;
										
									}
								
								}else{
								/*If agent ratio*/
								
									boolean numeratorFound = false;
									boolean denominatorFound = false;
									
									for(int l=0; l < AgentSettings.agents.get(j).variableList.size();l++){
										
											if(PlottingSettings.listOfHeatmaps2V.get(i).agentRatio1.numerator.equals(AgentSettings.agents.get(j).variableList.get(l).name)){
												numeratorFound = true;
											}
											else if(PlottingSettings.listOfHeatmaps2V.get(i).agentRatio1.denominator.equals(AgentSettings.agents.get(j).variableList.get(l).name)){
												
												denominatorFound = true;
												
											}
										
										}
									
									/*If either numerator or denominator not found remove agent ratio*/
										if(!numeratorFound || !denominatorFound){
											
											PlottingSettings.listOfHeatmaps2V.remove(i);
											i--;
											break;
											
										}
										
										
									}
								
								
								
								if(PlottingSettings.listOfHeatmaps2V.get(i).isVariable2){
									
									boolean  found = false;
									
									for(int l=0; l < AgentSettings.agents.get(j).variableList.size();l++){
										
										AgentSettings.agents.get(j).variableList.get(l).isSelectedForHeatmaps2V = false;
										
										if(PlottingSettings.listOfHeatmaps2V.get(i).variable1.name.equals(AgentSettings.agents.get(j).variableList.get(l).name)){
											
											AgentSettings.agents.get(j).variableList.get(l).isSelectedForHeatmaps2V = true;
											found = true;
											break;
										}
						
										
									}
									
									if(!found){
										
										PlottingSettings.listOfHeatmaps2V.remove(i);
										i--;
										break;
										
									}
								
								}else{
								/*If agent ratio*/
								
									boolean numeratorFound = false;
									boolean denominatorFound = false;
									
									for(int l=0; l < AgentSettings.agents.get(j).variableList.size();l++){
										
											if(PlottingSettings.listOfHeatmaps2V.get(i).agentRatio2.numerator.equals(AgentSettings.agents.get(j).variableList.get(l).name)){
												numeratorFound = true;
											}
											else if(PlottingSettings.listOfHeatmaps2V.get(i).agentRatio2.denominator.equals(AgentSettings.agents.get(j).variableList.get(l).name)){
												
												denominatorFound = true;
												
											}
										
										}
									
									/*If either numerator or denominator not found remove agent ratio*/
										if(!numeratorFound || !denominatorFound){
											
											PlottingSettings.listOfHeatmaps2V.remove(i);
											i--;
											break;
											
										}
										
										
									}
								
								
									
								
								}
							
							}
							

						
						
					}
				
				
				for(int i =0; i < AgentSettings.agents.size();i++){
					
					for(int j=0; j< AgentSettings.agents.get(i).variableList.size();j++){
						
						AgentSettings.agents.get(i).variableList.get(j).isSelectedForHeatmaps2V = false;
						
						
					}
				}
				
				
					
				for(int i=0; i<PlottingSettings.listOfHeatmaps2V.size();i++){
					
					for(int j=0; j< AgentSettings.agents.size();j++){
						
						if(PlottingSettings.listOfHeatmaps2V.get(i).histBelongsTo.equals(AgentSettings.agents.get(j).agentName)){
							
							
							if(PlottingSettings.listOfHeatmaps2V.get(i).isVariable1){
								
								
								for(int k=0; k< AgentSettings.agents.get(j).variableList.size();k++){
									
									if(PlottingSettings.listOfHeatmaps2V.get(i).variable1.name.equals(AgentSettings.agents.get(j).variableList.get(k).name)){
									
										AgentSettings.agents.get(j).variableList.get(k).isSelectedForHeatmaps2V = true;
										break;
									
									}
								
								}
								
								
								
							}else{
								
								for(int k=0; k< AgentSettings.agents.get(j).variableList.size();k++){
									
									if(PlottingSettings.listOfHeatmaps2V.get(i).agentRatio1.numerator.name.equals(AgentSettings.agents.get(j).variableList.get(k).name)){
									
										AgentSettings.agents.get(j).variableList.get(k).isSelectedForHeatmaps2V = true;
										break;
									
									}
								
								}
								
								
								for(int k=0; k< AgentSettings.agents.get(j).variableList.size();k++){
									
									if(PlottingSettings.listOfHeatmaps2V.get(i).agentRatio1.denominator.name.equals(AgentSettings.agents.get(j).variableList.get(k).name)){
									
										AgentSettings.agents.get(j).variableList.get(k).isSelectedForHeatmaps2V = true;
										break;
									
									}
								
								}
							}
							
						
							
							
							if(PlottingSettings.listOfHeatmaps2V.get(i).isVariable2){
								
								
								for(int k=0; k< AgentSettings.agents.get(j).variableList.size();k++){
									
									if(PlottingSettings.listOfHeatmaps2V.get(i).variable2.name.equals(AgentSettings.agents.get(j).variableList.get(k).name)){
									
										AgentSettings.agents.get(j).variableList.get(k).isSelectedForHeatmaps2V = true;
										break;
									
									}
								
								}
								
								
								
							}else{
								
								for(int k=0; k< AgentSettings.agents.get(j).variableList.size();k++){
									
									if(PlottingSettings.listOfHeatmaps2V.get(i).agentRatio2.numerator.name.equals(AgentSettings.agents.get(j).variableList.get(k).name)){
									
										AgentSettings.agents.get(j).variableList.get(k).isSelectedForHeatmaps2V = true;
										break;
									
									}
								
								}
								
								
								for(int k=0; k< AgentSettings.agents.get(j).variableList.size();k++){
									
									if(PlottingSettings.listOfHeatmaps2V.get(i).agentRatio2.denominator.name.equals(AgentSettings.agents.get(j).variableList.get(k).name)){
									
										AgentSettings.agents.get(j).variableList.get(k).isSelectedForHeatmaps2V = true;
										break;
									
									}
								
								}
							}

							
					
					}
				

	
				
				}
			}
					
				}catch(Exception ex){
					
					System.out.println(ex);
					
				}
				
			}
			
			
			
			
			
		});
		
		
		exportPlottingSettings.addActionListener(new ActionListener(){

		
			public void actionPerformed(ActionEvent e) {
				
				
				FileChooserFromMenuList chooseFile = new FileChooserFromMenuList(SimulationSettings.WORKING_DIRECTORY,false, true,"xml", true);
    			chooseFile.openFileChooser();
    			
    			WriteSettingsXMLFile exportPloSettings;
    			
    			if(chooseFile.getDirectoryOrFile().endsWith(".xml"))
    				exportPloSettings = new WriteSettingsXMLFile("",chooseFile.getDirectoryOrFile(),"plottingSettings");
    			else
    				exportPloSettings = new WriteSettingsXMLFile("",chooseFile.getDirectoryOrFile()+".xml","plottingSettings");
    			
    			exportPloSettings.createXMLFile();

    			exportPloSettings.new WriteClassFromXML(new PlottingSettings(),"plottingSettings", false);
				
			}
			
			
			
			
			
		});
		
		
		menuimportPlottingSections.add(importPlottingSettings);
		menuimportPlottingSections.add(exportPlottingSettings);
		
		menuImportExport.add(menuimportPlottingSections);
		
		menuimportParameterSections = new JMenu("Parameter Settings");
	
		importParameterSettings = new JMenuItem("Import Parameter Settings");
		exportParameterSettings = new JMenuItem("Export Parameter Settings");
		
		
		importParameterSettings.addActionListener(new ActionListener(){

			
			public void actionPerformed(ActionEvent e) {
				

				try {
					
				
					FileChooserFromMenuList chooseFile = new FileChooserFromMenuList(SimulationSettings.WORKING_DIRECTORY,false, true,"xml", false);
	    			chooseFile.openFileChooser();
	    			
	    			
	    			String file = chooseFile.getDirectoryOrFile();
	   
	    			File settingsFile = new File(chooseFile.getDirectoryOrFile());
	
					
					if(!settingsFile.exists()){
						
						throw new FileNotFoundException();
						
					}
					
						ReadXMLFile defValues = new ReadXMLFile(file);
					
					defValues.new ReadClassFromXML(new ModelParameterSettings(),"parameterSettings");
					
					new JDialogParameterSetup(ModelParameterSettings.modelParameters);
					
				}catch(Exception ex){
					
					System.out.println(ex);
					
				}
				
			}
			
			
			
			
			
		});
		
		
		exportParameterSettings.addActionListener(new ActionListener(){

		
			public void actionPerformed(ActionEvent e) {
				
				
				FileChooserFromMenuList chooseFile = new FileChooserFromMenuList(SimulationSettings.WORKING_DIRECTORY,false, true,"xml", true);
    			chooseFile.openFileChooser();
    			
    			WriteSettingsXMLFile exportPloSettings;
    			
    			if(chooseFile.getDirectoryOrFile().endsWith(".xml"))
    				exportPloSettings = new WriteSettingsXMLFile("",chooseFile.getDirectoryOrFile(),"parameterSettings");
    			else
    				exportPloSettings = new WriteSettingsXMLFile("",chooseFile.getDirectoryOrFile()+".xml","parameterSettings");
    			
    			exportPloSettings.createXMLFile();

    			exportPloSettings.new WriteClassFromXML(new ModelParameterSettings(),"parameterSettings", false);
				
			}
			
			
			
			
			
		});
		
		
		
		menuimportParameterSections.add(importParameterSettings);
		menuimportParameterSections.add(exportParameterSettings);
		
		
		menuImportExport.add(menuimportParameterSections);
		
		
		menuSettings.add(menuImportExport);
		
		/*menu plotting selection*/
		
		
		
		setJMenuBar(menuBar);
	
	    Container content = getContentPane();
	    content.setBackground(Color.white);
	    JDesktopPane desktop = new JDesktopPane();
	    desktop.setBackground(Color.white);
	    content.add(desktop, BorderLayout.CENTER);
	
 
	    
	    Toolkit toolkit =  Toolkit.getDefaultToolkit ();
	    Dimension dim = toolkit.getScreenSize();
	    
	    int dimy = Math.min(dim.height-50,1050);
	    int dimx = Math.min(dim.width,810);
	    
	    
	    setSize(dimx, dimy);
	    
	    
	    
	    GlobalGrid = new GridBagConstraints();
	    GlobalGrid.insets = new Insets( 5, 5, 5, 5);
	    
	    
	    guiContainer.setLayout(new GridBagLayout());
	    
	    
	    mainTabPane  = new JTabbedPane();
	    
	    

	    
	    
	   
	 

	    //plottingContainer.setLayout(new GridBagLayout());

	    
	    
	    /*Buttom area for savings settings and run simulation*/
	    
	    buttomArea = new JPanel();
		
	    buttomArea.setSize(800,200);
	    buttomArea.setBackground(Color.white);
		
	    buttomArea.setLayout(new GridBagLayout());
		GridBagConstraints buttom = new GridBagConstraints();
		buttom.insets = new Insets( 5, 5, 5, 5);
	    
	    buttonBuild = new BuildExperiment("Build Experiment");
	    
	    buttom.gridx=3;
	    buttom.gridy= 0;
	    buttomArea.add(buttonBuild,buttom);
	    
	    
	    buttonRun = new RunExperiment("Run Experiment");
	    
	    buttom.gridx=4;
	    buttom.gridy= 0;
	    buttomArea.add(buttonRun,buttom);
	    
	    /*Set disabled if no experiment has been built*/
	    buttonRun.setEnabled(SimulationSettings.experimentBuilt);
	    
	  	
      	
	   
      	
      	
      	/*Add GUI container to Scroll pane*/
      	settingScrollPane = new JScrollPane(settingContainer);
      	settingScrollPane.setPreferredSize(new Dimension(800, 780));
      	
      	
      	
      	
      	plottingScrollPane = new JScrollPane(plottingContainer);
      	plottingScrollPane.setPreferredSize(new Dimension(800, 780));
      	
      	/*Add Scroll pane to JFrame*/
      	
      	//add(guiScrollPane);
      	 mainTabPane.add(settingScrollPane,"Simulation Settings");
      	 
      	 mainTabPane.add(plottingScrollPane,"Plotting Settings");
      	 
      

 	    GlobalGrid.gridx=0; 
 	    GlobalGrid.gridy=0;
 	    
 	  
      	  
 	    mainTabPane.setPreferredSize(new Dimension(this.getSize().width-40,820));
 	   guiContainer.add(mainTabPane, GlobalGrid);
      	

      	GlobalGrid.gridx=0; 
	    GlobalGrid.gridy=1;
	    
	    
	    buttomArea.setPreferredSize(new Dimension(this.getSize().width-40,80));
	    guiContainer.add(buttomArea, GlobalGrid);
	    
	    
	   globalScrollPane = new JScrollPane(guiContainer);
	   globalScrollPane.setPreferredSize(new Dimension(this.getSize().width-20, 200));
	    
	    add(globalScrollPane);
	    //pack();
	    setVisible(true);
	 
	

	}
	

	
	
	
	
	
	
	
	private class itemHandler3 implements ItemListener {

		public void itemStateChanged(ItemEvent e) {
			
		
				if(settingContainer.doCompressKeepOriginal.isSelected())
				{
					SimulationSettings.DO_COMPRESS_KEEP_ORIGINAL=1;
					SimulationSettings.DO_COMPRESS_REMOVE_ORIGINAL=0;
					SimulationSettings.DO_REMOVE_DB = 0;
					SimulationSettings.DO_DECOMPRESS =0;
					
				}else if(settingContainer.doCompressRemoveOriginal.isSelected()){
					
					SimulationSettings.DO_COMPRESS_KEEP_ORIGINAL=0;
					SimulationSettings.DO_COMPRESS_REMOVE_ORIGINAL=1;
					SimulationSettings.DO_REMOVE_DB = 0;
					SimulationSettings.DO_DECOMPRESS =0;
					
				}else if(settingContainer.doNotCompress.isSelected()){
					
					SimulationSettings.DO_COMPRESS_KEEP_ORIGINAL=0;
					SimulationSettings.DO_COMPRESS_REMOVE_ORIGINAL=0;
					SimulationSettings.DO_REMOVE_DB = 0;
					SimulationSettings.DO_DECOMPRESS =0;
				}else if(settingContainer.removeOriginal.isSelected()){
					
					SimulationSettings.DO_COMPRESS_KEEP_ORIGINAL=0;
					SimulationSettings.DO_COMPRESS_REMOVE_ORIGINAL=0;
					SimulationSettings.DO_REMOVE_DB = 1;
					SimulationSettings.DO_DECOMPRESS =0;
					
				}else if(settingContainer.decompress.isSelected()){
					
					SimulationSettings.DO_COMPRESS_KEEP_ORIGINAL=0;
					SimulationSettings.DO_COMPRESS_REMOVE_ORIGINAL=0;
					SimulationSettings.DO_REMOVE_DB = 0;
					SimulationSettings.DO_DECOMPRESS =1;
					
				}
				
				
				
				
		
		}
		
		
	}
	
	
	
	
	void drawAgentTable(){
		
		
		try{
		
		lcRightPanel.remove(listScrollAgentTable);
		lcRightPanel.validate();
		}catch(Exception e){
			
			System.out.println();
			
		}
		String[] colHeaders = {"Agent", "Record","Period","Phase"}; 
		tabAgentsModel = new AgentTableModel(colHeaders,AgentSettings.agents);
		DrawStoreOptionTable tabAgents = new DrawStoreOptionTable(tabAgentsModel, colHeaders);
	    
	    listScrollAgentTable = new JScrollPane(tabAgents);  
		listScrollAgentTable.setPreferredSize(new Dimension(280, 179)); 
		settingContainer.g.gridx = 0;
		settingContainer.g.gridy= 0;
		settingContainer.lcRightPanel.add(listScrollAgentTable,settingContainer.g);
		settingContainer.lcRightPanel.validate();
		
	
	}
	
	
	
	
	

	
	void SaveSettings() {
		

			
			/*Safe the settings to XML file*/
			
			System.out.println("Print WORKING_DIRECTORY"+SimulationSettings.WORKING_DIRECTORY);
			writeGeneralSettingsToFile wrSet = new writeGeneralSettingsToFile("PathToWorkspace.txt",false);
			
			wrSet.writeToFile(SimulationSettings.WORKING_DIRECTORY);
	
			
			WriteSettingsXMLFile xmlFileWD = new WriteSettingsXMLFile(SimulationSettings.WORKING_DIRECTORY,"GlobalSettings.xml","StoredSettings");
			xmlFileWD.createXMLFile();
			
			
			//xmlFileWD.AddRootElement("PlottingSettings");
			//xmlFileWD.new WriteClassFromXML(new PlottingSettings(),"PlottingSettings");
			
			
			xmlFileWD.AddRootElement("PlottingSettings");
			xmlFileWD.new WriteClassFromXML(new PlottingSettings(),"PlottingSettings", false);
			
			xmlFileWD.AddRootElement("SimulationSettings");
			xmlFileWD.new WriteClassFromXML(new SimulationSettings(),"SimulationSettings", false);
			
			xmlFileWD.AddRootElement("bashExperiments");
			xmlFileWD.new WriteClassFromXML(new BatchExperiments(),"agentsSettings", false);
			
			
			xmlFileWD.AddRootElement("agentsSettings");
			xmlFileWD.new WriteClassFromXML(new AgentSettings(),"agentsSettings", false);
			
			
			
			/*Save the parameters only in the working directory*/
			xmlFileWD.AddRootElement("modelParameters");
			xmlFileWD.new WriteClassFromXML(new ModelParameterSettings(),"modelParameters", false);
			

		
			
			
			
			
		
	}
	
	/*This class loads the settings from the settings.xml files*/
	void LoadSettings(String file){

		/*Return a warning message if the eurace model xml file is not there*/

			try {
				
				File settingsFile = new File(file);

				if(!settingsFile.exists()){
					
					throw new FileNotFoundException();
					
				}
				
					ReadXMLFile defValues = new ReadXMLFile(file);
					
					System.out.println("BP0\n");
					
					defValues.new ReadClassFromXML(new SimulationSettings(),"SimulationSettings");
					
					System.out.println("BP1\n");
			
					File xmlFile = new File(SimulationSettings.EURACE_MODEL_XML);
					if(!xmlFile.exists()){
						JOptionPane.showMessageDialog(null,"Set the correct path to the model.xml file!");
						FileChooserFromMenuList chooseFile = new FileChooserFromMenuList(SimulationSettings.EURACE_MODEL_XML,false, true,"xml", false);
				  		chooseFile.openFileChooser();
				  		SimulationSettings.EURACE_MODEL_XML = chooseFile.getDirectoryOrFile();
				
					}
					
	
					/*Load list of model parameters */
				    ReadModelParameter modelXML = new ReadModelParameter();
				    modelXML.getFIleListDirectlyFromEuraceModelXML();
				    

				    /*Set the parameter values with those from saving file*/
				    ArrayList<ModelParameter> tempModelParameter= new  ArrayList<ModelParameter>(); 
				   // tempModelParameter = defValues.readModelParameters();
				    
				    tempModelParameter =  modelXML.GetModelParameterFromModelXMLFiles();

				    defValues.new ReadClassFromXML(new ModelParameterSettings(),"modelParameters");
				    
    
				    for(int i=0; i<ModelParameterSettings.modelParameters.size();i++){
				    	
				    	for(int j=0; j<tempModelParameter.size();j++ ){
				    		
				    		if(ModelParameterSettings.modelParameters.get(i).name.equals(tempModelParameter.get(j).name)){
				    			
				    			if( ModelParameterSettings.modelParameters.get(i).value!=null){
				    				
				    				tempModelParameter.get(j).value = ModelParameterSettings.modelParameters.get(i).value;
				    				
				    			}
				    		}
				
				    	}
				    	
				    }
				    
				    ModelParameterSettings.modelParameters = tempModelParameter;
	
					/*Set agentList by calling ReadAgentListFromModelXML*/
					agentList = ReadAgentListFromModelXML();
		
					defValues.new ReadClassFromXML(new AgentSettings(),"agentsSettings");
					
					
					/*Search the model.xml files: add variables if there are some new variables which are not contained in the settings; delete those
					 *  which are not contained in the model.xml file*/
					
					/*First: Retrieve the current agent list from the model.xml file*/
					
					ReadAgentListFromModelXML agentList = new ReadAgentListFromModelXML(SimulationSettings.EURACE_MODEL_XML);
					agentList.GetAgentListModelXMLFiles();
					
					variableRemoved = false;
					
					
					/*Go through the agent list*/
					
					for(int i=0; i < agentList.agentList.size();i++ ){
						
						boolean agentFound = false;
						
						for(int j=0; j < AgentSettings.agents.size();j++){
							
							if(agentList.agentList.get(i).equals(AgentSettings.agents.get(j).agentName)){
								
								agentFound = true;
								
								Agent tempAgent = new Agent(agentList.agentList.get(i));
								
								
								for(int k=0; k < AgentSettings.agents.get(j).variableList.size();k++){
									
									boolean varFound = false;
									
									for(int l =0; l < tempAgent.variableList.size();l++){
										
										/*if a variable which is contained in the settings has been found in the var list of the temp agent -> remove it from the temp */
										if(AgentSettings.agents.get(j).variableList.get(k).name.equals(tempAgent.variableList.get(l).name)){
											
											varFound = true;
											tempAgent.variableList.remove(l);
											break;
										}
										
									
										
									}
									
									/*if variable that is in the settings list has not been found in the current var list o the temp agent -> delete it from the settings list*/
									if(!varFound){
										
										AgentSettings.agents.get(j).variableList.remove(k);	
										k--;
										
										variableRemoved = true;
										
									}
									
									
								}
								
								/*Add the remaining variables which have not been found in the settings to it*/
								
								for(int l =0; l < tempAgent.variableList.size();l++){
									
									AgentSettings.agents.get(j).variableList.add(tempAgent.variableList.get(l));
									
								}
								
							}
							
							
							
							
							
						}
						
						/*if this agent is not found in the agent settings add this agent as a new item to the agent list*/
						if(!agentFound){
							
							AgentSettings.agents.add(new Agent(agentList.agentList.get(i)));
							
						}
						
						
					}
						
					defValues.new ReadClassFromXML(new PlottingSettings(),"PlottingSettings");
					
					
					if(variableRemoved){
						
						
						PlottingSettings.removeDeletedVarsFromVariableInstanceList();
						
						
						
					}
					


		} catch (Exception e1) {

			JOptionPane.showMessageDialog(null,"No settings found. Select a model.xml File!"); 
			
			
		    FileChooserFromMenuList chooseFile = new FileChooserFromMenuList(SimulationSettings.EURACE_MODEL_XML,false, true,"xml", false);
		    chooseFile.openFileChooser();
		    SimulationSettings.EURACE_MODEL_XML = chooseFile.getDirectoryOrFile();
		    
		    JOptionPane.showMessageDialog(null,"Select executable!"); 
		    
			FileChooserFromMenuList chooseFile2 ;
			
			if(SimulationSettings.MAIN_EXECUTABLE.equals("."))
				chooseFile2 = new FileChooserFromMenuList(SimulationSettings.EURACE_MODEL_XML,false, false,"", false);
			else
				chooseFile2 = new FileChooserFromMenuList(SimulationSettings.MAIN_EXECUTABLE,false, false,"", false);
		    
		    

		    chooseFile2.openFileChooser();
		    SimulationSettings.MAIN_EXECUTABLE = chooseFile2.getDirectoryOrFile();
		    
		    
		    JOptionPane.showMessageDialog(null,"Select 0.xml file!"); 
		    
		    FileChooserFromMenuList chooseFile3;
			
			if(SimulationSettings.ZERO_XML_FILE.equals("0.xml"))
				chooseFile3 = new FileChooserFromMenuList(SimulationSettings.EURACE_MODEL_XML,false, true,"xml", false);
			else
				chooseFile3 = new FileChooserFromMenuList(SimulationSettings.ZERO_XML_FILE,false, true,"xml", false);
		    
		    chooseFile3.openFileChooser();
		    SimulationSettings.ZERO_XML_FILE = chooseFile3.getDirectoryOrFile();
		    
		    JOptionPane.showMessageDialog(null,"Select path to R scripts!"); 
		    
		    FileChooserFromMenuList chooseFile4 = new FileChooserFromMenuList(SimulationSettings.PATH_TO_RSCRIPTS,true, false,"", false);
		    chooseFile4.openFileChooser();
		    SimulationSettings.PATH_TO_RSCRIPTS = chooseFile4.getDirectoryOrFile();
		    
		    
		    
		    /*Set agent list*/
		    agentList = ReadAgentListFromModelXML();
			AgentSettings.agents = new ArrayList<Agent>();
			for(int i=0; i<agentList.size();i++)
			{
				AgentSettings.agents.add(new Agent(agentList.get(i)));
				PlottingSettings.Agent temAgent =  (new PlottingSettings()).new Agent(agentList.get(i));
				PlottingSettings.listOfAgentsVariableInstances.add(temAgent);
				
			}
			
			/*Read Model parameters*/	
			 ReadModelParameter modelXML = new ReadModelParameter();
			 modelXML.getFIleListDirectlyFromEuraceModelXML();
			 ModelParameterSettings.modelParameters = modelXML.GetModelParameterFromModelXMLFiles();
			
		}
			
			
			
			
			
		
			
			
		}
		
	class RunExperiment  extends JButton{
		
		RunExperiment(String text){
			
			super(text);
			 this.addActionListener(new ActionListener(){
				 
				 public void actionPerformed(ActionEvent evt) {
					 
					
					 ExecuteSimulations exeSim = new ExecuteSimulations();
					 exeSim.start();
					 
					 
				 }
				 
			 });
		}
		
	}
	
	
	class BuildExperiment extends JButton{


		private static final long serialVersionUID = 1L;

		BuildExperiment(String text){
			
				super(text);
				
				
				
				 this.addActionListener(new ActionListener(){
			    		
			    		public void actionPerformed(ActionEvent evt) {
			    			
			    			
			    			/*Write shadow model xml file for selected data storage*/
			    			
			    			
			    			
			    			ShadowModelXML shadowFile = new ShadowModelXML();
			    			
			    			shadowFile.setFilterAndWeights();
			    			
			    			ShadowModelXML.agents.clear();
			    			
			    			for(int i=0; i<AgentSettings.agents.size();i++){
			    				
			    				boolean found = false;
			    				
			    				for(int j=0; j<AgentSettings.agents.get(i).variableList.size();j++){
			    				
			    					if(AgentSettings.agents.get(i).variableList.get(j).isSelectedForPlotting){
			    						
			    						if(!found){
			    							ShadowModelXML.agents.add((new ShadowModelXML()).new xagent(AgentSettings.agents.get(i).agentName));
			    							found =true;
			    							System.out.println(ShadowModelXML.agents.size());
										ShadowModelXML.agents.get(ShadowModelXML.agents.size()-1).memory.add((new ShadowModelXML()).new variable("id","int"));
			    						}
			    						
			    						if(!AgentSettings.agents.get(i).variableList.get(j).name.equals("id"))
			    							ShadowModelXML.agents.get(ShadowModelXML.agents.size()-1).memory.add((new ShadowModelXML()).new variable(AgentSettings.agents.get(i).variableList.get(j).name,AgentSettings.agents.get(i).variableList.get(j).type));
			    						
			    					}else if(AgentSettings.agents.get(i).variableList.get(j).isSelectedForHistograms){
			    						if(!found){
			    							ShadowModelXML.agents.add((new ShadowModelXML()).new xagent(AgentSettings.agents.get(i).agentName));
			    							found =true;
										ShadowModelXML.agents.get(ShadowModelXML.agents.size()-1).memory.add((new ShadowModelXML()).new variable("id","int"));
			    						}
			    						if(!AgentSettings.agents.get(i).variableList.get(j).name.equals("id"))
			    						ShadowModelXML.agents.get(ShadowModelXML.agents.size()-1).memory.add((new ShadowModelXML()).new variable(AgentSettings.agents.get(i).variableList.get(j).name,AgentSettings.agents.get(i).variableList.get(j).type));
			    					}else if(AgentSettings.agents.get(i).variableList.get(j).isSelectedForBoxplots){
			    						if(!found){
			    							ShadowModelXML.agents.add((new ShadowModelXML()).new xagent(AgentSettings.agents.get(i).agentName));
			    							found =true;
										ShadowModelXML.agents.get(ShadowModelXML.agents.size()-1).memory.add((new ShadowModelXML()).new variable("id","int"));
			    						}
			    						if(!AgentSettings.agents.get(i).variableList.get(j).name.equals("id"))
			    						ShadowModelXML.agents.get(ShadowModelXML.agents.size()-1).memory.add((new ShadowModelXML()).new variable(AgentSettings.agents.get(i).variableList.get(j).name,AgentSettings.agents.get(i).variableList.get(j).type));
			    					}else if(AgentSettings.agents.get(i).variableList.get(j).isSelectedForHeatmaps){
			    						if(!found){
			    							ShadowModelXML.agents.add((new ShadowModelXML()).new xagent(AgentSettings.agents.get(i).agentName));
			    							found =true;
										ShadowModelXML.agents.get(ShadowModelXML.agents.size()-1).memory.add((new ShadowModelXML()).new variable("id","int"));
			    						}
			    						if(!AgentSettings.agents.get(i).variableList.get(j).name.equals("id"))
			    							ShadowModelXML.agents.get(ShadowModelXML.agents.size()-1).memory.add((new ShadowModelXML()).new variable(AgentSettings.agents.get(i).variableList.get(j).name,AgentSettings.agents.get(i).variableList.get(j).type));
			    					}else if(AgentSettings.agents.get(i).variableList.get(j).isSelectedForHeatmaps2V){
			    						if(!found){
			    							ShadowModelXML.agents.add((new ShadowModelXML()).new xagent(AgentSettings.agents.get(i).agentName));
			    							found =true;
										ShadowModelXML.agents.get(ShadowModelXML.agents.size()-1).memory.add((new ShadowModelXML()).new variable("id","int"));
			    						}
			    						if(!AgentSettings.agents.get(i).variableList.get(j).name.equals("id"))
			    							ShadowModelXML.agents.get(ShadowModelXML.agents.size()-1).memory.add((new ShadowModelXML()).new variable(AgentSettings.agents.get(i).variableList.get(j).name,AgentSettings.agents.get(i).variableList.get(j).type));
			    					}else if(AgentSettings.agents.get(i).variableList.get(j).isSelectedForCorrelation){
			    						if(!found){
			    							ShadowModelXML.agents.add((new ShadowModelXML()).new xagent(AgentSettings.agents.get(i).agentName));
			    							found =true;
										ShadowModelXML.agents.get(ShadowModelXML.agents.size()-1).memory.add((new ShadowModelXML()).new variable("id","int"));
			    						}
			    						
			    						if(!AgentSettings.agents.get(i).variableList.get(j).name.equals("id"))
			    							ShadowModelXML.agents.get(ShadowModelXML.agents.size()-1).memory.add((new ShadowModelXML()).new variable(AgentSettings.agents.get(i).variableList.get(j).name,AgentSettings.agents.get(i).variableList.get(j).type));
			    					
			    					
			    					
			    					}else if(AgentSettings.agents.get(i).variableList.get(j).isSelectedFilter){
			    						if(!found){
			    							ShadowModelXML.agents.add((new ShadowModelXML()).new xagent(AgentSettings.agents.get(i).agentName));
			    							found =true;
										ShadowModelXML.agents.get(ShadowModelXML.agents.size()-1).memory.add((new ShadowModelXML()).new variable("id","int"));
			    						}
			    						
			    						if(!AgentSettings.agents.get(i).variableList.get(j).name.equals("id"))
			    						ShadowModelXML.agents.get(ShadowModelXML.agents.size()-1).memory.add((new ShadowModelXML()).new variable(AgentSettings.agents.get(i).variableList.get(j).name,AgentSettings.agents.get(i).variableList.get(j).type));
			    					
			    					
			    					
			    					}
			    					
			    					else if(AgentSettings.agents.get(i).variableList.get(j).isSelectedWeighted){
			    						if(!found){
			    							ShadowModelXML.agents.add((new ShadowModelXML()).new xagent(AgentSettings.agents.get(i).agentName));
			    							found =true;
										ShadowModelXML.agents.get(ShadowModelXML.agents.size()-1).memory.add((new ShadowModelXML()).new variable("id","int"));
			    						}
			    						if(!AgentSettings.agents.get(i).variableList.get(j).name.equals("id"))
			    						ShadowModelXML.agents.get(ShadowModelXML.agents.size()-1).memory.add((new ShadowModelXML()).new variable(AgentSettings.agents.get(i).variableList.get(j).name,AgentSettings.agents.get(i).variableList.get(j).type));
			    					
			    					
			    					
			    					}
			    					
			    					/*TODO add further isselected booleans*/
			    				}
			    			}
			    			
			    			
			    			new CountAgents();
			    			
			    			WriteSettingsXMLFile shadowXMLFile = new WriteSettingsXMLFile("","shadow_model.xml","xmodel");
			    			shadowXMLFile.createXMLFile();
			    			shadowXMLFile.new WriteClassFromXML(shadowFile,"xmodel",true);
			    			
			    			
			    			
			    			WriteSettingsXMLFile experiment = new WriteSettingsXMLFile("","experiment.xml","experiment");
			    			experiment.createXMLFile();
			    			
			    			BatchExperiments.Experiment tempExp = (new BatchExperiments()).new  Experiment();
			    			
			    			tempExp.path = SimulationSettings.WORKING_DIRECTORY;	
			    			
			    			if(SimulationSettings.numParameters==1)
			    				tempExp.parameter1 = SimulationSettings.PARAMETER_1;
			    			
			    			if(SimulationSettings.numParameters==2){
			    				tempExp.parameter1 = SimulationSettings.PARAMETER_1;
			    				tempExp.parameter2 = SimulationSettings.PARAMETER_2;
			    			}
			    			
			    			experiment.new WriteClassFromXML(tempExp,"experiment",false);
			    			
			    			WriteRInterface rInterface = new WriteRInterface();
			    			rInterface.writeVariableTXTFile();
			    			rInterface.writeRatioTXTFile();
			    			rInterface.writeGrowthrateTXTFile();
			    			rInterface.writeSingleTimeSeriesTXTFile();
			    			rInterface.writeMultipleTimeSeriesTXTFile();
			    			rInterface.writeHistogramTXTFile();
			    			rInterface.writeBoxplotsTXTFile();
			    			rInterface.writeHeatmapsTXTFile();
			    			rInterface.writeScatterTXTFile();
			    			
			    			rInterface.writeCrossCorrelationTXTFile();
			    			rInterface.writeCorrelationTXTFile();
			    			rInterface.writeHeatmaps2VTXTFile();
			    			rInterface.writeBandpassFilterTXTFile();
			    			
			    			rInterface.writeConfigureFile();
			    			
			    			
			    			AuxFunctions.copyfile(SimulationSettings.WORKING_DIRECTORY+"/variables.txt",SimulationSettings.PATH_TO_RSCRIPTS+"/Data_Files/variables.txt");
			    			AuxFunctions.copyfile(SimulationSettings.WORKING_DIRECTORY+"/time_series_data.txt",SimulationSettings.PATH_TO_RSCRIPTS+"/Data_Files/time_series_data.txt");
			    			AuxFunctions.copyfile(SimulationSettings.WORKING_DIRECTORY+"/multiple_time_series_data.txt",SimulationSettings.PATH_TO_RSCRIPTS+"/Data_Files/multiple_time_series_data.txt");
			    			AuxFunctions.copyfile(SimulationSettings.WORKING_DIRECTORY+"/growth_rate_data.txt",SimulationSettings.PATH_TO_RSCRIPTS+"/Data_Files/growth_rate_data.txt");
			    			AuxFunctions.copyfile(SimulationSettings.WORKING_DIRECTORY+"/ratio_data.txt",SimulationSettings.PATH_TO_RSCRIPTS+"/Data_Files/ratio_data.txt");
			    			AuxFunctions.copyfile(SimulationSettings.WORKING_DIRECTORY+"/boxplot_data.txt",SimulationSettings.PATH_TO_RSCRIPTS+"/Data_Files/boxplot_data.txt");
			    			AuxFunctions.copyfile(SimulationSettings.WORKING_DIRECTORY+"/histogram_data.txt",SimulationSettings.PATH_TO_RSCRIPTS+"/Data_Files/histogram_data.txt");
			    			AuxFunctions.copyfile(SimulationSettings.WORKING_DIRECTORY+"/heat_maps_data.txt",SimulationSettings.PATH_TO_RSCRIPTS+"/Data_Files/heat_maps_data.txt");
			    			AuxFunctions.copyfile(SimulationSettings.WORKING_DIRECTORY+"/scatter_data.txt",SimulationSettings.PATH_TO_RSCRIPTS+"/Data_Files/scatter_data.txt");
			    			AuxFunctions.copyfile(SimulationSettings.WORKING_DIRECTORY+"/bandpass_filter_data.txt",SimulationSettings.PATH_TO_RSCRIPTS+"/Data_Files/bandpass_filter_data.txt");
			    			AuxFunctions.copyfile(SimulationSettings.WORKING_DIRECTORY+"/cross_correlation_function_data.txt",SimulationSettings.PATH_TO_RSCRIPTS+"/Data_Files/cross_correlation_function.txt");
			    			AuxFunctions.copyfile(SimulationSettings.WORKING_DIRECTORY+"/correlation_distribution_data.txt",SimulationSettings.PATH_TO_RSCRIPTS+"/Data_Files/correlation_distribution_data.txt");
			    			AuxFunctions.copyfile(SimulationSettings.WORKING_DIRECTORY+"/heat_maps_data_2V.txt",SimulationSettings.PATH_TO_RSCRIPTS+"/Data_Files/heat_maps_data_2V.txt");
			    			
			    			
			    			AuxFunctions.copyfile(SimulationSettings.WORKING_DIRECTORY+"/Configure.r",SimulationSettings.PATH_TO_RSCRIPTS+"/Configure.r");

			    			
			    			
			    			AuxFunctions.copyfile("./Bash_scripts/exp_script_1_setup.sh", SimulationSettings.WORKING_DIRECTORY+"/exp_script_1_setup.sh");
			    			AuxFunctions.copyfile("./Bash_scripts/exp_script_2_specific.sh", SimulationSettings.WORKING_DIRECTORY+"/exp_script_2_specific.sh");
			    			AuxFunctions.copyfile("./Bash_scripts/create_job_list.sh", SimulationSettings.WORKING_DIRECTORY+"/create_job_list.sh");
			    			AuxFunctions.copyfile("./Bash_scripts/launch_job_list.sh", SimulationSettings.WORKING_DIRECTORY+"/launch_job_list.sh");
			    			AuxFunctions.copyfile("./Bash_scripts/run.sh", SimulationSettings.WORKING_DIRECTORY+"/run.sh");
			    			AuxFunctions.copyfile("./Bash_scripts/compress_db.sh", SimulationSettings.WORKING_DIRECTORY+"/compress_db.sh");
			    			AuxFunctions.copyfile("./Bash_scripts/create_job_list_compress.sh", SimulationSettings.WORKING_DIRECTORY+"/create_job_list_compress.sh");
			    			AuxFunctions.copyfile("./Bash_scripts/r_serial.sh", SimulationSettings.WORKING_DIRECTORY+"/r_serial.sh");
			    			
			    			
			    			
			    			if(SimulationSettings.saveAllAgentVariables)
			    				AuxFunctions.copyfile("./Bash_scripts/gendb.py", SimulationSettings.WORKING_DIRECTORY+"/gendb.py");
			    			else{
			    				AuxFunctions.copyfile("./Bash_scripts/gendb_special.py", SimulationSettings.WORKING_DIRECTORY+"/gendb.py");
			    				AuxFunctions.copyfile("shadow_model.xml", SimulationSettings.WORKING_DIRECTORY+"/shadow_model.xml");
			    			}
			    			
			    			AuxFunctions.copyfile("experiment.xml", SimulationSettings.WORKING_DIRECTORY+"/experiment.xml");
			    			
			    			
			    			
			    			String PathToFile = new String("launch.sh");
			    			
			    			
			    			writeGeneralSettingsToFile launchSH = new  writeGeneralSettingsToFile(PathToFile,true);
			    			
			    			launchSH.writeToFile("####################################################");
			    			launchSH.writeToFile("# This script is automatically generated by the GUI.");
			    			launchSH.writeToFile("# It contains the setting of the current working directory and launching of the run_exp.sh");
			    			launchSH.writeToFile("###################################################");
			    			launchSH.writeToFile("\n");
			    			launchSH.writeToFile("cd "+SimulationSettings.WORKING_DIRECTORY);
			    			launchSH.writeToFile("bash run_exp.sh");
			    			launchSH.writeToFile("\n");
			    		
			    			/*Choice is yes*/
			    			PathToFile = new String(SimulationSettings.WORKING_DIRECTORY+"/run_exp.sh");
			    			
			    			System.out.println(SimulationSettings.WORKING_DIRECTORY);
			    			System.out.println(PathToFile);
			    		    
			    			writeGeneralSettingsToFile expSettingsSHFile = new  writeGeneralSettingsToFile(PathToFile,true);
			    			
			    			expSettingsSHFile.writeToFile("####################################################");
			    			expSettingsSHFile.writeToFile("# This script is automatically generated by the GUI.");
	    					expSettingsSHFile.writeToFile("# It is for setting up and running the experiment:");
	    					expSettingsSHFile.writeToFile("# Steps:");
	    					expSettingsSHFile.writeToFile("# - 1 : experiment settings");
	    					expSettingsSHFile.writeToFile("# - 2 : creation of experiment folder hierarchy");
	    					expSettingsSHFile.writeToFile("# - 3 : creation of the specific settings xml file");
	    					expSettingsSHFile.writeToFile("# - 4 : creation of a job list");
	    					expSettingsSHFile.writeToFile("# - 5 : launching the job list");
	    					expSettingsSHFile.writeToFile("# - 6 : start plotting routines using R");
	    					expSettingsSHFile.writeToFile("	################################################################################################################");
			    			expSettingsSHFile.writeToFile("\n");
			    			
			    			
			    			
			    			
			    			expSettingsSHFile.writeToFile("#Set the base folder");
			    			expSettingsSHFile.writeToFile("export BASE='"+SimulationSettings.WORKING_DIRECTORY+"'");
			    			expSettingsSHFile.writeToFile("\n");
			    			
			       			expSettingsSHFile.writeToFile("\n");
			    			expSettingsSHFile.writeToFile("#0.xml file");
			    			expSettingsSHFile.writeToFile("export ZERO_XML_FILE='"+SimulationSettings.ZERO_XML_FILE+"'");
	    					
			    			
			    			expSettingsSHFile.writeToFile("#Export the database folder for R scripts");
			    			expSettingsSHFile.writeToFile("export DATABASE_LOCATION='"+SimulationSettings.WORKING_DIRECTORY+"'");
			    			expSettingsSHFile.writeToFile("\n");
			    			
			    			expSettingsSHFile.writeToFile("#Iterations");
			    			expSettingsSHFile.writeToFile("export ITS="+SimulationSettings.numIterations);
			    			
			    			expSettingsSHFile.writeToFile("\n");
			    			expSettingsSHFile.writeToFile("#Set number of nodes to use (only valid for parallel)");
			    			expSettingsSHFile.writeToFile("export NR_NODES=1");
			    			expSettingsSHFile.writeToFile("\n");
			    			
			    			expSettingsSHFile.writeToFile("#Set number of job processes to use (nr of job lists: job_list_$n.sh)");
			    			expSettingsSHFile.writeToFile("export NUM_PROCS="+SimulationSettings.numProcessors);
			    			
			    			expSettingsSHFile.writeToFile("\n");
			    			expSettingsSHFile.writeToFile("#Set the number of parameters to vary");
			    			if(settingContainer.parameterVariationOnePars.isSelected()){
			    				
			    				//If one parameters is selected:
			    				expSettingsSHFile.writeToFile("export NUM_PARS=1");
			    				
			    			}else
			    			{
			    				//If no parameters are selected:
			    				expSettingsSHFile.writeToFile("export NUM_PARS=0");

			    			}
			    			expSettingsSHFile.writeToFile("\n");
			    			
			    			expSettingsSHFile.writeToFile("#Set number of batch runs");
			    			expSettingsSHFile.writeToFile("export TOTAL_RUNS="+SimulationSettings.numBatchRuns);
			    			expSettingsSHFile.writeToFile("export RUNS=$(seq 1 $TOTAL_RUNS)");
			    			expSettingsSHFile.writeToFile("echo 'Batch runs:[' $RUNS ']'");
			    			
			    			expSettingsSHFile.writeToFile("\n");
			    			expSettingsSHFile.writeToFile("#Executables");
			    			expSettingsSHFile.writeToFile("export MAIN_S='"+SimulationSettings.MAIN_EXECUTABLE+"'");
	    					
			    			expSettingsSHFile.writeToFile("\n");
			    			expSettingsSHFile.writeToFile("#Location of the model XML (used to generate the SQL)");
			    			
			    			if(SimulationSettings.saveAllAgentVariables)
			    				expSettingsSHFile.writeToFile("export MODEL_XML_FILE='"+SimulationSettings.EURACE_MODEL_XML+"'");
			    			else
			    				expSettingsSHFile.writeToFile("export MODEL_XML_FILE='"+SimulationSettings.WORKING_DIRECTORY+"/shadow_model.xml'");
			    			expSettingsSHFile.writeToFile("\n");
			    			expSettingsSHFile.writeToFile("#Location of R scripts");
			    			expSettingsSHFile.writeToFile("#Parallel");
			    			expSettingsSHFile.writeToFile("export PATH_R_SCRIPTS='"+SimulationSettings.PATH_TO_RSCRIPTS+"/'");
	    					
			    			expSettingsSHFile.writeToFile("\n");
			    			expSettingsSHFile.writeToFile("#Running simulations");
			    			expSettingsSHFile.writeToFile("export DO_RUN="+SimulationSettings.DO_RUN);
			    			expSettingsSHFile.writeToFile("export CREATE_SQL=1");
	    					
			    			expSettingsSHFile.writeToFile("\n");
			    			expSettingsSHFile.writeToFile("#Compress the database, keeping the original .db file. If a pre-existing compressed file exists, overwrite and recreate it.");
			    			expSettingsSHFile.writeToFile("export DO_COMPRESS_KEEP_ORIGINAL="+SimulationSettings.DO_COMPRESS_KEEP_ORIGINAL);

			    			expSettingsSHFile.writeToFile("\n");
			    			expSettingsSHFile.writeToFile("#Compress the database, remove the original .db file. If a pre-existing compressed file exists, overwrite and recreate it.");
			    			expSettingsSHFile.writeToFile("export DO_COMPRESS_REMOVE_ORIGINAL="+SimulationSettings.DO_COMPRESS_REMOVE_ORIGINAL);

			    			expSettingsSHFile.writeToFile("\n");
			    			expSettingsSHFile.writeToFile("#Decompress the SQL database without removing the original compressed file");
			    			expSettingsSHFile.writeToFile("export DO_DECOMPRESS="+SimulationSettings.DO_DECOMPRESS);

			    			expSettingsSHFile.writeToFile("\n");
	    					expSettingsSHFile.writeToFile("#Remove the decompressed files");
	    					expSettingsSHFile.writeToFile("export DO_REMOVE_DB="+SimulationSettings.DO_REMOVE_DB);
	    					expSettingsSHFile.writeToFile("\n");
	    					
	    					
	    					

	    					expSettingsSHFile.writeToFile("echo 'Starting top-level experiment script...'");
	    					expSettingsSHFile.writeToFile("\n");
	    					expSettingsSHFile.writeToFile("######### STEP 1: EXPERIMENT SETTINGS ##################################################################");
	    					expSettingsSHFile.writeToFile(". set_exp.sh");
	    					expSettingsSHFile.writeToFile("\n");
	    					expSettingsSHFile.writeToFile("######### STEP 2: CREATION OF EXPERIMENT FOLDER HIERARCHY ");
	    					expSettingsSHFile.writeToFile("bash ./exp_script_1_setup.sh");
	    					expSettingsSHFile.writeToFile("\n");
	    					expSettingsSHFile.writeToFile("######### STEP 3: CREATION OF THE SPECIFIC SETTINGS XML FILE ");
	    					expSettingsSHFile.writeToFile("bash ./exp_script_2_specific.sh");
	    					expSettingsSHFile.writeToFile("\n");
	    					expSettingsSHFile.writeToFile("######### STEP 4: CREATING  JOB SCRIPTS ");
	    					expSettingsSHFile.writeToFile("bash ./create_job_list.sh");
	    					expSettingsSHFile.writeToFile("\n");
	    					expSettingsSHFile.writeToFile("######### STEP 5: LAUNCHING  JOB SCRIPTS ");
	    					expSettingsSHFile.writeToFile("bash ./launch_job_list.sh");
	    					expSettingsSHFile.writeToFile("\n");
	    					expSettingsSHFile.writeToFile("wait");
	    					expSettingsSHFile.writeToFile("######### STEP 6: R scripts for plotting data");
	    					expSettingsSHFile.writeToFile("# Parallel R");
	    					
	    				
	    					
	    					if(plottingContainer.singleRunAnalyisCheckBox.isSelected() || plottingContainer.batchRunAnalyisCheckBox.isSelected() || plottingContainer.parameterAnalyisCheckBox.isSelected()){
	    						expSettingsSHFile.writeToFile("bash ./r_serial.sh");
	    					}else{
	    						
	    						expSettingsSHFile.writeToFile("#Don't run r scripts");
	    						expSettingsSHFile.writeToFile("#bash ./r_serial.sh");
	    						
	    					}
	    					expSettingsSHFile.writeToFile("\n");
	    					expSettingsSHFile.writeToFile("wait");
	    					expSettingsSHFile.writeToFile("######### STEP 7: Compress dara bases");
	    					expSettingsSHFile.writeToFile("bash ./create_job_list_compress.sh");
	    					expSettingsSHFile.writeToFile("\n");
	    					expSettingsSHFile.writeToFile("bash ./launch_job_list.sh");
	    					expSettingsSHFile.writeToFile("\n");
	    					expSettingsSHFile.writeToFile("echo 'Finished top-level experiment script.'");
	    					expSettingsSHFile.writeToFile("\n");
	    					
	    					
			    			System.out.println(SimulationSettings.WORKING_DIRECTORY);
			    			System.out.println(PathToFile);
			    			
			    			
			    			
			    		    
			    			writeGeneralSettingsToFile setExpSHFile = new  writeGeneralSettingsToFile(SimulationSettings.WORKING_DIRECTORY+"/set_exp.sh", true);
			    			
			    			setExpSHFile.writeToFile("####################################################");
			    			setExpSHFile.writeToFile("# This script is automatically generated by the GUI.");
			    			setExpSHFile.writeToFile("# It contains the parameter settings for an experiment ");
			    			setExpSHFile.writeToFile("###################################################");
			    			setExpSHFile.writeToFile("\n");
	    					
			    			

			    			 if(settingContainer.parameterVariationOnePars.isSelected()){
			    				
			    				//If one parameters is selected:
			    				
			    				/*Check if settings are correct, otherwise return warning message*/
			    
			    				try{
			    					
			    					SimulationSettings.PARAMETER_1.name.equals(null);
			    	
			    						
				    					SimulationSettings.PARAMETER_1.values.get(0);
				    					
				    					setExpSHFile.writeToFile("export PARAMETER_1='"+SimulationSettings.PARAMETER_1.name+"'");
					    				
					    				String values1 ="";
					    				
					    				for(int i=0; i<SimulationSettings.PARAMETER_1.values.size();i++ ){
					    					
					    					values1 = values1+" "+SimulationSettings.PARAMETER_1.values.get(i).value;
					    					
					    				}
					    				
					    				setExpSHFile.writeToFile("export F1_values='"+values1+"'");
					    				expSettingsSHFile.writeToFile("\n");
					    				setExpSHFile.writeToFile("export F1_values_b=("+ values1+")");
					    				expSettingsSHFile.writeToFile("\n");
					    				setExpSHFile.writeToFile("for i in ${!F1_values_b[*]};do export F1_values_b_$i='${F1_values_b[$i]}';done");
				    					
				    				
			    					
			    				}catch(Exception e){
			    					JOptionPane.showMessageDialog(null,"Parameter 1 is not selected!");
			    				}
			    		
			    			}else
			    			{
			    				//If no parameters are selected:
			    				setExpSHFile.writeToFile("# No parameters selected");

			    			}
			    			

			    			WriteOutputXMLFile outputXML = new WriteOutputXMLFile(SimulationSettings.WORKING_DIRECTORY);
			    			outputXML.writeOutputToFile(AgentSettings.agents);
			    			outputXML.writeImportsToFile(SimulationSettings.ZERO_XML_FILE);
			    			
			    			WriteEnvironmentXMLFile environmentXMLFILE = new WriteEnvironmentXMLFile(SimulationSettings.WORKING_DIRECTORY);
			    			environmentXMLFILE.writeParametersToFile(ModelParameterSettings.modelParameters);
			    			SimulationSettings.experimentBuilt = true;
			    			buttonRun.setEnabled(SimulationSettings.experimentBuilt);
			    		}
			    		
			    	});
			
		}
		
		
	}



	/*This class reads the complete agent list from the eurace model xml file. Therefore the path must be correct*/

		
	ArrayList<String> ReadAgentListFromModelXML(){
			
		ArrayList<String> listOfAgents = new ArrayList<String>();
			
			try{
				
			
			/*Parse the eurace_model.xml*/
	 		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		 	
	 		docFactory.setAttribute("http://xml.org/sax/features/namespaces", true);
	 		docFactory.setAttribute("http://xml.org/sax/features/validation", false);
	 		docFactory.setAttribute("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
	 		docFactory.setAttribute("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

	 		docFactory.setNamespaceAware(true);
	 		docFactory.setIgnoringElementContentWhitespace(false);
	 		docFactory.setIgnoringComments(false);
	 		docFactory.setValidating(false);

	 		
	 		docFactory.setValidating(false);
	 		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
	 		
	 		
	        Document doc = docBuilder.parse(new File(SimulationSettings.EURACE_MODEL_XML));
	       
	        doc.getDocumentElement().normalize();
	      
	        NodeList nList = doc.getElementsByTagName("xagent");
	        
	        
	        for (int temp = 0; temp < nList.getLength(); temp++) {
		        
		        Node nNode = nList.item(temp);
				   if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					   
					   Element eElement = (Element) nNode;
					   
					   listOfAgents.add(getTagValue("name",eElement));
					   
					   //System.out.println("read agent witht name:"+getTagValue("name",eElement));
							    
				   }
		    	}
	        
	        
	        
			}catch(Exception e)
			{
				System.out.println(e);
			}
			
			
			return listOfAgents;
			
			
		}
		
		
		
		private String getTagValue(String sTag, Element eElement) {
			NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
		 
		        Node nValue = (Node) nlList.item(0);
		        
		   
			    //    System.out.println("Check:   "+sTag+"   "+nValue);
			        
			        //nValue.equals(null);
			 
			        if( nValue == null)
			        {
			        	return "No description avaiable";
			        }else
			        {
			        	return nValue.getNodeValue();
			        }
		        }
			

	
	
	
	
	
	class ExecuteSimulations extends Thread {
       
        ExecuteSimulations() {
          
        }

        public void run() {
        	
        	
        	try {
				  

				String[] args = {"/bin/bash","launch.sh"};
				
				buttonRun.setEnabled(false);
				

				 Process process = new ProcessBuilder(args).start();
				       InputStream is = process.getInputStream();
				       InputStreamReader isr = new InputStreamReader(is);
				       BufferedReader br = new BufferedReader(isr);
				       String line;

				     // System.out.printf("Output of running %s is:", 
					 //Arrays.toString(args));

				       while ((line = br.readLine()) != null) {
					 System.out.println(line);
				       }
				       
				       
				       
				       ProcessExitDetector processExitDetector = new ProcessExitDetector(process);
				       processExitDetector .addProcessListener(new ProcessListener() {
				           public void processFinished(Process process) {
				               System.out.println("The subprocess has finished.");
				               buttonRun.setEnabled(true);
				           }
				       });
				       processExitDetector.start();
	        
	      }catch (IOException e) {
	        e.printStackTrace();
	      }
            
           
        }
    }
	
	
	
	/**
	 * Detects when a process is finished and invokes the associated listeners.
	 */
	public class ProcessExitDetector extends Thread {

	    /** The process for which we have to detect the end. */
	    private Process process;
	    /** The associated listeners to be invoked at the end of the process. */
	    private ArrayList<ProcessListener> listeners = new ArrayList<ProcessListener>();

	    /**
	     * Starts the detection for the given process
	     * @param process the process for which we have to detect when it is finished
	     */
	    public ProcessExitDetector(Process process) {
	        try {
	            // test if the process is finished
	            process.exitValue();
	            throw new IllegalArgumentException("The process is already ended");
	        } catch (IllegalThreadStateException exc) {
	            this.process = process;
	        }
	    }

	    /** @return the process that it is watched by this detector. */
	    public Process getProcess() {
	        return process;
	    }

	    public void run() {
	        try {
	            // wait for the process to finish
	            process.waitFor();
	            // invokes the listeners
	            for (ProcessListener listener : listeners) {
	                listener.processFinished(process);
	            }
	        } catch (InterruptedException e) {
	        }
	    }

	    /** Adds a process listener.
	     * @param listener the listener to be added
	     */
	    public void addProcessListener(ProcessListener listener) {
	        listeners.add(listener);
	    }

	    /** Removes a process listener.
	     * @param listener the listener to be removed
	     */
	    public void removeProcessListener(ProcessListener listener) {
	        listeners.remove(listener);
	    }
	}
	
	
	public interface ProcessListener extends EventListener {
	    void processFinished(Process process);
	}
	
	
	
	/*First panel; displays the simulation settings*/
	class SimulationPanel extends  JPanel
	{
		
		
		
		
		
		
		
	}
	
	
	
	

	
	
}
		
	
	
	
	
	
	

	


