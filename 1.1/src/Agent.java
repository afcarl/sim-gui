import java.io.File;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class Agent implements Cloneable {
    

	
	
	/****************************************/
	
	public Agent clone()  {
        try {
			return (Agent) super.clone();
		} catch (CloneNotSupportedException e) {
			
			e.printStackTrace();
			return null;
		}
		
        
    }
	
	
	
	String agentName;
	DataStorageSettings dataStorageSettings;

	ArrayList<Variable> variableList;
	 
	
	Agent(String name){
		
		agentName = name;
		
		/*Default:*/
		dataStorageSettings = new DataStorageSettings(false, "20" ,  "0");
		
		variableList = getVariables();
		
		System.out.println("XX variableList.size() "+variableList.size());
		
		
	
	}
	
	
	public ArrayList<File> getFileListDirectlyFromEuraceModelXML() 
	{
		
		ArrayList<File> files =new  ArrayList<File>();
		
		System.out.println("Starting Files from eurace_model.xml");
		 try {
			 
			 System.out.println(SimulationSettings.EURACE_MODEL_XML);
			 
			 File euraceModelXML = new File(SimulationSettings.EURACE_MODEL_XML);
			 
			 if(euraceModelXML.exists())
			 {
				 	/*Add eurace_model.xml to file list*/
				 	files.add( euraceModelXML);
				 
				 	/*read pathes to model.xml files from eurace_model.xml*/
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
			        
			        NodeList nList = doc.getElementsByTagName("model");
			        
			        System.out.println("nList.getLength()"+nList.getLength());
			        
			        for (int temp = 0; temp < nList.getLength(); temp++) {
			        
			        Node nNode = nList.item(temp);
					   if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						   
						   Element eElement = (Element) nNode;
						   
						   
						 if(getTagValue("enabled",eElement).equals("true")){
							 
							 
							 files.add( new File(euraceModelXML.getParent()+"/"+getTagValue("file",eElement))); 
	
						 }
						   
					   }
			        }
			 	
			 }else
			 {
				 JOptionPane.showMessageDialog(null, "Model.xml file not found!\n  \t Check if the path is correct!");
					FileChooserFromMenuList chooseFile = new FileChooserFromMenuList(SimulationSettings.EURACE_MODEL_XML,true, false,"", false);
	    			
					chooseFile.openFileChooser();
					SimulationSettings.EURACE_MODEL_XML = chooseFile.getDirectoryOrFile();
					
					 getFileListDirectlyFromEuraceModelXML();
					
					
			 }
		  } catch (Exception e) {
			e.printStackTrace();
		  }
		  
		  return files;
	}
	
	
	/*This function returns the full list of variables (filtered by int and double)*/
	public ArrayList<Variable> getVariables(){
		
		ArrayList<Variable> variables = new ArrayList<Variable> ();
		
		ArrayList<File> files = getFileListDirectlyFromEuraceModelXML();
			
			for (int i =0; i < files.size();i++)
			{
				try {
					
				
					/*read pathes to model.xml files from eurace_model.xml*/
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
			        Document doc = docBuilder.parse(files.get(i));
			        
			        System.out.println("parse file: "+files.get(i));
			        
			        doc.getDocumentElement().normalize();
			    
			        NodeList xagentList = doc.getElementsByTagName("xagent");
			        
			        
			        try{
			        	
				        for(int j=0; j<xagentList.getLength();j++ )
				        {
			        	
				        	Node nd = (Node) xagentList.item(j);
			        	
				        	Element eElement = (Element) nd;
			        	 
			        	 
				        	if(getTagValue("name",eElement).equals(agentName)){
			        		 
			        		 
			        		  NodeList nList2 = eElement.getElementsByTagName("memory");
			        		  
			        		  Node ndMemory = nList2.item(0);
			        		  
			        		  Element elMemory = (Element) ndMemory;
			        		  
			        		  NodeList nList3 = elMemory.getElementsByTagName("variable");
			        		  
			        		 // System.out.println("length "+nList3.getLength());
			        		  
			        		  for(int k=0; k<nList3.getLength();k++){
			        			  
			        			
			        			  
			        			  Node nodeVar= nList3.item(k);
			        			  
			     
			        		       
			        			  if(getTagValue("type",(Element)nList3.item(k)).equals("int")||getTagValue("type",(Element)nList3.item(k)).equals("double")){
			        				  
			        				  /*Set the default values:
			        				   * 
			        				   * 1. time series
			        				   * */
			        				  
			        				  Variable var = new Variable(getTagValue("name",(Element)nList3.item(k))
			        						  ,getTagValue("type",(Element)nList3.item(k))
			        						  ,getTagValue("description",(Element)nList3.item(k)),false);
			        				  
			        		
			    
			        				  
			        				  /*Add to the variable list:*/
			        				  variables.add(var);
			        			  }
			      	        	
			      	        }
			        		 
			        		 
			        		 
			        		 
			        	 }
			        	
			        		
			    	}   
			        }catch(Exception exc){
			        	
			        	System.out.println(agentName+" not found in this model.xml file");
			        	System.out.println(exc);
			        	
			        }

			        
			      
			        
				}catch (Exception e) {
					e.printStackTrace();
			}
		}
			
			System.out.println("variables sitze"+ variables.size());
			
		return variables;
			
	}
	
	
	private static String getTagValue(String sTag, Element eElement) {
		NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
	 
        Node nValue = (Node) nlList.item(0);
        
   
	        //System.out.println("Check:   "+sTag+"   "+nValue);
        
        //nValue.equals(null);
 
        if( nValue == null)
        {
        	return "";
        }else
        {
        	return nValue.getNodeValue();
        }
    }

	/*with this function you can set the DataStorageSettings variable from outside*/
	void setDataStorageSettings(boolean agentIsSelected,String agentPeriod , String agentPhase){
		
		dataStorageSettings = new DataStorageSettings(agentIsSelected, agentPeriod ,  agentPhase);
		
		
	}

	
	/*This sets up a new variable list*/
	void setupVariables(){
		
		variableList = new  ArrayList<Variable> ();
	}
	
	/*This adds a new variable to the list*/
	void addVariableToList(Variable variable){
		
		variableList.add(variable);
	}
	
	
	
	
	public class DataStorageSettings implements Cloneable {
	    

		
		
		/****************************************/
		
		public DataStorageSettings clone()  {
	        try {
				return (DataStorageSettings) super.clone();
			} catch (CloneNotSupportedException e) {
				
				e.printStackTrace();
				return null;
			}
			
	        
	    }

		
		boolean isSelected;
		String period;
		String phase;
		
		DataStorageSettings(boolean agentIsSelected,String agentPeriod , String agentPhase){
			
			isSelected = agentIsSelected;
			period = agentPeriod;
			phase = agentPhase;
			
		}
		
		void setSelected(boolean input)
		{
			if(input)
				isSelected= true;	
			else
				isSelected= false;
		}
		
		
		
	}
	
	

}


