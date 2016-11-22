
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.*;
import org.xml.sax.SAXException;





import javax.xml.parsers.*;

import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;



public class WriteSettingsXMLFile {
	
	
	String  filePath, fileRootElement;
	
	SimulationSettings.SelectedModelParameter experimentParameter;
	ModelParameter modelParameter;
	
	public WriteSettingsXMLFile(String path,String fileName, String rootElementofFile ){
		
		if(!path.equals("")){
			filePath ="file:///"+path+"/"+fileName;
		}else{
			filePath = fileName;
			//System.out.println(filePath);
		}
		
		
		fileRootElement = rootElementofFile;


		
	}
	

	public void createXMLFile(){
		
		try{
				 
				DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		 
				// root elements
				Document doc = docBuilder.newDocument();
				Element rootElement = doc.createElement(fileRootElement);
				doc.appendChild(rootElement);
				// write the content into xml file
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				DOMSource source = new DOMSource(doc);
				StreamResult result = new StreamResult(filePath);
		 
				// Output to console for testing
				// StreamResult result = new StreamResult(System.out);
		 
				transformer.transform(source, result);
		 
				System.out.println("File saved!");
				
			
				
		}
		 catch (Exception e) {
	         System.out.println(e);
		 }
		}
	
	
	public void AddRootElement(String elementName){
		
		
		try{
			 
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
	 
			// root elements
			Document doc = docBuilder.parse(filePath);
			
			NodeList rootNode = doc.getElementsByTagName(fileRootElement);
			
			Node root = rootNode.item(0);
			
			
			Element rootElement = doc.createElement(elementName);
			root.appendChild(rootElement);
	 
		
			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(filePath);
	 
			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);
	 
			transformer.transform(source, result);
	 
			System.out.println("File saved!");
			
		
			
	}
	 catch (Exception e) {
         System.out.println(e);
	 }
	}
		
		
		

	
	

public class WriteClassFromXML {
	
	Document doc;
	boolean shortClassName;

	WriteClassFromXML(Object object, String rootElement, boolean shortName ){
	
	
	try{

		shortClassName =shortName;
		
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		System.out.println(filePath);
		
		
		doc = docBuilder.parse(filePath);
		
		// Get the root element
				NodeList baseList = doc.getElementsByTagName(rootElement);
				Node baseNode = baseList.item(0);
				
				Element baseElement = (Element) baseNode;
				

				
				Class<?> class1 = object.getClass();
				
				
				Field[] variables = class1.getDeclaredFields();
				
				
				for(int i=0; i< variables.length;i++){
					
					if(variables[i].getType().isPrimitive()){

						if(variables[i].getType().getName().toString().equals("boolean")){

							Element subElement = doc.createElement(variables[i].getName());
							subElement.appendChild(doc.createTextNode(returnTrueFalseAsString(variables[i].getBoolean(object))));
							baseElement.appendChild(subElement);

							
						}else{
						
							
							Element subElement = doc.createElement(variables[i].getName());
							subElement.appendChild(doc.createTextNode(variables[i].get(object).toString()));
							baseElement.appendChild(subElement);
		
			
						}

						
					}

					
				}

				
				for(int i=0; i< variables.length;i++){
					
					if(!variables[i].getType().isPrimitive()){
				
						
						if(variables[i].getType().getSimpleName().toString().equals("ArrayList")){

						        Object obj = variables[i].get(object);
						        ArrayList<?> arrayList = new ArrayList();
						        arrayList = (ArrayList<?>) Class.forName("java.util.ArrayList").cast(obj);   
								Element subElement = doc.createElement(variables[i].getName());

					        	
					        	String fullName = variables[i].getGenericType().toString();
					        	
					        	String name = fullName.substring(variables[i].getGenericType().toString().indexOf("<")+1, variables[i].getGenericType().toString().indexOf(">"));
					        	
					        	name = name.replace("$", ".");
				
					        	if(shortClassName){
					        		
					        		if(name.contains(".")){
					        			
					        			do{
					        				
					        				int in = name.indexOf(".");
						        			
						        			name = name.substring(in+1);
					        				
					        			}while(name.contains("."));
					  	
					        		}
					        		
					        		
					        	}
	
								
						        for(int j=0; j<arrayList.size();j++ ){
						        	
						    
						        	
						        	subElement.appendChild(writeClassFromXML(arrayList.get(j),name) );

						        }
			
						        
						        baseElement.appendChild(subElement);
							
						}else if(variables[i].getType().getSimpleName().toString().equals("String")) {
	
							Element subElement = doc.createElement(variables[i].getName());	
							subElement.appendChild(doc.createTextNode(variables[i].get(object).toString()));
							baseElement.appendChild(subElement);
	
							
						}else{
		
					
							try{
							baseElement.appendChild(writeClassFromXML(variables[i].get(object),variables[i].getName().toString()) );
							}catch(NullPointerException npE){
								
								System.out.println(npE);
								
							}
			
						}

						
					}

					
				}
		
		

		// write the content into xml file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(filePath);
		transformer.transform(source, result);

	
	
}catch(Exception e){
	
	
	System.out.println(e);
	
	
	
}
	
	
	
}

Element writeClassFromXML(Object object2, String rootElement2 ){
	
	

	try{
	
		Element element2  = doc.createElement(rootElement2);
		
		Class<?> class2 = object2.getClass();
		
		
		Field[] variables2 = class2.getDeclaredFields();
		
		
		for(int i=0; i< variables2.length;i++){
			
			if(variables2[i].getType().isPrimitive()){
				
				if(variables2[i].getType().getName().toString().equals("boolean")){

					Element subElement = doc.createElement(variables2[i].getName());
					subElement.appendChild(doc.createTextNode(returnTrueFalseAsString(variables2[i].getBoolean(object2))));
					element2.appendChild(subElement);
				
					
				}else{
					
					System.out.println("BP13");
					
					Element subElement = doc.createElement(variables2[i].getName());
					subElement.appendChild(doc.createTextNode(variables2[i].get(object2).toString()));
					element2.appendChild(subElement);

	
				}

				
			}

			
		}
		
		
		
		
		for(int i=0; i< variables2.length;i++){
			
			if(!variables2[i].getType().isPrimitive()){
				
				if(variables2[i].getType().getSimpleName().toString().equals("ArrayList")){


					
				       Object obj = variables2[i].get(object2);
				        ArrayList<?> arrayList = new ArrayList();
				        arrayList = (ArrayList<?>) Class.forName("java.util.ArrayList").cast(obj);   
						Element element3 = doc.createElement(variables2[i].getName());
						
						
						
				    	System.out.println("aaa"+variables2[i].getGenericType().toString());
			        	
			        	String fullName = variables2[i].getGenericType().toString();
			        	
			        	String name = fullName.substring(variables2[i].getGenericType().toString().indexOf("<")+1, variables2[i].getGenericType().toString().indexOf(">"));
			        	
			        	name = name.replace("$", ".");
			        	
			        	System.out.println(name);
			        	
			        	
			         	if(shortClassName){
			        		
			        		if(name.contains(".")){
			        			
			        			do{
			        				
			        				int in = name.indexOf(".");
				        			
				        			name = name.substring(in+1);
			        				
			        			}while(name.contains("."));
			  	
			        		}
			        		
			        		
			        	}
					
					
					for(int j=0; j< arrayList.size();j++){
						
						
						element3.appendChild(writeClassFromXML(arrayList.get(j),name) );
						
					}
					
					element2.appendChild(element3);

					
				}else if(variables2[i].getType().getSimpleName().toString().equals("String")) {
	
					
					Element subElement = doc.createElement(variables2[i].getName());	
					subElement.appendChild(doc.createTextNode(variables2[i].get(object2).toString()));
					element2.appendChild(subElement);

				}else{

					try{

						element2.appendChild(writeClassFromXML(variables2[i].get(object2),variables2[i].getName().toString()) );
					}catch(Exception ex3){
						
						//return null;
						
					}
					
	
				}

				
			}

			
		}
		
		return element2;
		
	}catch(Exception ex2){
		
		System.out.println(ex2);
		return null;
	}
	
	
	
	
}

	
}



	
public void AddAgentSettings(ArrayList<Agent> agentList){
		
		
		try {
		
				DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
				System.out.println(filePath);
				
				Document doc = docBuilder.parse(filePath);
		 
				// Get the root element
				NodeList agentsNL = doc.getElementsByTagName("agentsSettings");
				Node agents = agentsNL.item(0);
				
				for(int i=0; i< agentList.size();i++)
				{
					Element agent = doc.createElement("agent");
	
					Element agentname = doc.createElement("name");
					agentname.appendChild(doc.createTextNode(agentList.get(i).agentName));
					agent.appendChild(agentname);
					
					/*data storage option*/
					
					Element dataStorageSelection = doc.createElement("dataStorageSelection");
					
					Element isSelected = doc.createElement("isSelected");
					if(agentList.get(i).dataStorageSettings.isSelected)
						isSelected.appendChild(doc.createTextNode("true"));
					else
						isSelected.appendChild(doc.createTextNode("false"));
					
					dataStorageSelection.appendChild(isSelected);
					
					Element agentPeriod = doc.createElement("period");
					agentPeriod.appendChild(doc.createTextNode(agentList.get(i).dataStorageSettings.period));
					dataStorageSelection.appendChild(agentPeriod);
					
					Element agentPhase = doc.createElement("phase");
					agentPhase.appendChild(doc.createTextNode(agentList.get(i).dataStorageSettings.phase));
					dataStorageSelection.appendChild(agentPhase);
					
					agent.appendChild(dataStorageSelection);
					
					/*Plotting general settings*/
					
		
					/*variables*/
					
					Element variables = doc.createElement("variables");
					
					for(int j=0; j< agentList.get(i).variableList.size();j++)
					{
				
						/*Element variable:*/
						Element variable = doc.createElement("variable");
					
						Element varName = doc.createElement("name");
						varName.appendChild(doc.createTextNode(agentList.get(i).variableList.get(j).name));
						variable.appendChild(varName);
						
						Element varType = doc.createElement("type");
						varType.appendChild(doc.createTextNode(agentList.get(i).variableList.get(j).type));
						variable.appendChild(varType);
						
						
						Element varDescription = doc.createElement("description");
						varDescription.appendChild(doc.createTextNode(agentList.get(i).variableList.get(j).description));
						variable.appendChild(varDescription);
						
						
							
						Element selected = doc.createElement("selectedForPlotting");
						if(agentList.get(i).variableList.get(j).isSelectedForPlotting)
						{
							selected.appendChild(doc.createTextNode("true"));
						}else{
							selected.appendChild(doc.createTextNode("false"));
						}
						variable.appendChild(selected);
							
			
						variables.appendChild(variable);
						
					}
					
					agent.appendChild(variables);

					agents.appendChild(agent);
				
				}
				
				

		 
				// write the content into xml file
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				DOMSource source = new DOMSource(doc);
				StreamResult result = new StreamResult(filePath);
				transformer.transform(source, result);
		 
				System.out.println("Done");
		 
			   } catch (ParserConfigurationException pce) {
				pce.printStackTrace();
			   } catch (TransformerException tfe) {
				tfe.printStackTrace();
			   } catch (IOException ioe) {
				ioe.printStackTrace();
			   } catch (SAXException sae) {
				sae.printStackTrace();
			   }
			}


			String returnTrueFalseAsString (boolean input){
				
				if(input)
					return "true";
				else 
					return "false";
			}
			
			
			
		
			
			
Element InstanceVariableOrRatio (Document doc, PlottingSettings.VariableInstance variableInstance){
				
			
				
				
				if(variableInstance.isVariable){
					
					Element variable = doc.createElement("variable");
					
					Element varName = doc.createElement("name");
					varName.appendChild(doc.createTextNode(variableInstance.variable.name));
					variable.appendChild(varName);
					
					Element varType = doc.createElement("type");
					varType.appendChild(doc.createTextNode(variableInstance.variable.type));
					variable.appendChild(varType);
							
					Element varDescription = doc.createElement("description");
					varDescription.appendChild(doc.createTextNode(variableInstance.variable.description));
					variable.appendChild(varDescription);
			
					Element selected = doc.createElement("selectedForPlotting");
					selected.appendChild(doc.createTextNode(returnTrueFalseAsString(variableInstance.variable.isSelectedForPlotting)));
					variable.appendChild(selected);
						
					
					return(variable);
				
				}else{
					
					
					Element agentRatio = doc.createElement("agentRatio");
					
					Element agentRatioName = doc.createElement("name");
					agentRatioName.appendChild(doc.createTextNode(variableInstance.agentRatio.ratioName));
					agentRatio.appendChild(agentRatioName);
					
					Element numeratorRat = doc.createElement("numerator");
					
						Element varName = doc.createElement("name");
						varName.appendChild(doc.createTextNode(variableInstance.agentRatio.numerator.name));
						numeratorRat.appendChild(varName);
						
						Element varType = doc.createElement("type");
						varType.appendChild(doc.createTextNode(variableInstance.agentRatio.numerator.type));
						numeratorRat.appendChild(varType);
								
						Element varDescription = doc.createElement("description");
						varDescription.appendChild(doc.createTextNode(variableInstance.agentRatio.numerator.description));
						numeratorRat.appendChild(varDescription);
				
						Element selected = doc.createElement("selectedForPlotting");
						selected.appendChild(doc.createTextNode(returnTrueFalseAsString(variableInstance.agentRatio.numerator.isSelectedForPlotting)));
						numeratorRat.appendChild(selected);
						
						
						agentRatio.appendChild(numeratorRat);
						
						Element denominatorRat = doc.createElement("denominator");
						
						varName = doc.createElement("name");
						varName.appendChild(doc.createTextNode(variableInstance.agentRatio.denominator.name));
						denominatorRat.appendChild(varName);
						
						varType = doc.createElement("type");
						varType.appendChild(doc.createTextNode(variableInstance.agentRatio.denominator.type));
						denominatorRat.appendChild(varType);
								
						varDescription = doc.createElement("description");
						varDescription.appendChild(doc.createTextNode(variableInstance.agentRatio.denominator.description));
						denominatorRat.appendChild(varDescription);
				
						selected = doc.createElement("selectedForPlotting");
						selected.appendChild(doc.createTextNode(returnTrueFalseAsString(variableInstance.agentRatio.denominator.isSelectedForPlotting)));
						denominatorRat.appendChild(selected);
						
						
						agentRatio.appendChild(denominatorRat);
						
					
						return(agentRatio);
					
					
				}
				
		
			}
	}

	
	


