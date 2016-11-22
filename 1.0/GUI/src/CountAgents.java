
	import java.io.*;
	import java.util.ArrayList;

	import org.w3c.dom.*;


import javax.swing.JOptionPane;
import javax.xml.parsers.*;

	public class CountAgents {

		CountAgents(){

			try{
			
				
				File zeroXMLFile = new File(SimulationSettings.ZERO_XML_FILE);
				
				System.out.println(SimulationSettings.ZERO_XML_FILE+"   "+zeroXMLFile.exists());
				
	        	/*For exception handling: if file is not found*/
	        	if(!zeroXMLFile.exists()){
	        		
	        		zeroXMLFile=null;
	        		
	        	}
	        	
	        	
	        	DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
	        	DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
	        	Document doc = docBuilder.parse( zeroXMLFile);
	        	
	        
				
				
			}
			catch(Exception ex1){
	        	
	       	 JOptionPane.showMessageDialog(null, "Set the path to the initial data file (0.xml)");
	       	 
	       	  FileChooserFromMenuList chooseFile = new FileChooserFromMenuList(SimulationSettings.ZERO_XML_FILE,false, true,"xml", false);
	   		  chooseFile.openFileChooser();
	   		SimulationSettings.ZERO_XML_FILE = chooseFile.getDirectoryOrFile();
	   		  
	   		  try{
	   			  
	   			  File zeroXMLFile = new File(SimulationSettings.ZERO_XML_FILE);
	  			
	  			System.out.println(SimulationSettings.ZERO_XML_FILE+"   "+zeroXMLFile.exists());
	  			
	          	/*For exception handling: if file is not found*/
	          	if(!zeroXMLFile.exists()){
	          		
	          		zeroXMLFile=null;
	        
	          		
	          	}
	          	
	          	
	          	DocumentBuilderFactory docFactory2 = DocumentBuilderFactory.newInstance();
	          	DocumentBuilder docBuilder2 = docFactory2.newDocumentBuilder();
	          	Document doc2 = docBuilder2.parse( zeroXMLFile);
	          	
	          
	   			  
	   		  }catch(Exception ex2){
	   			  
	   			  return;
		  
	   		  }

	       }
			   

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
		 		
		        
		        	File zeroXMLFile = new File(SimulationSettings.ZERO_XML_FILE);
		        	
		        
		        	
		       
			    	DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			        Document doc = docBuilder.parse( zeroXMLFile);
		
			        doc.getDocumentElement().normalize();
			        
			        NodeList nList = doc.getElementsByTagName("xagent");
			        
			        SimulationSettings.agentNumbers = new ArrayList<SimulationSettings.AgentNumber>();
			        

			        for(int i=0; i< AgentSettings.agents.size();i++){
			        	
			        	 SimulationSettings.agentNumbers.add((new SimulationSettings()).new AgentNumber(AgentSettings.agents.get(i).agentName,0));
			    
			        	  
			        	  System.out.println("xxxxxxxxxxxxxxxxxxxxxxx"+nList.getLength());
			        
			        	  for(int j =0; j < nList.getLength();j++){
			        		  
			        		  Node agentNode = nList.item(j);
						       Element agentElement = (Element) agentNode;
			        		  
			        		  if(getTagValue("name", agentElement).equals(AgentSettings.agents.get(i).agentName)){
			        			  
			        			  SimulationSettings.agentNumbers.get(i).numberOfAgents++;
			        			  
			        		  }
			        		  
			        		  
			        	  }
			        	  
			        }
			        
			        
			        
			        System.out.println("xxxxxxxxxxxxxxxxxxxxxxx"+ SimulationSettings.agentNumbers.get(0).numberOfAgents);
			        
	        	
			}catch(Exception ex){
				
				
			}
		
		}
		

		
	private static String getTagValue(String sTag, Element eElement) {
			
			NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
		 
		        Node nValue = (Node) nlList.item(0);
		        
		   
			      //  System.out.println("Check:   "+sTag+"   "+nValue);
			        
			        //nValue.equals(null);
			 
			        if( nValue == null)
			        {
			        	return "";
			        }else
			        {
			        	return nValue.getNodeValue();
			        }
		        }

		
	}


