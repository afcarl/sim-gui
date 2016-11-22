import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;



public class WriteEnvironmentXMLFile {
	
	
	
	
	
String pathToWorkingDirectory;
	
WriteEnvironmentXMLFile(String pathWD){
	
	
		pathToWorkingDirectory = pathWD+"/environment.xml";
		
		
		
		
		try{
			 
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
	 
			// root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("environment");
			doc.appendChild(rootElement);
			
			
			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(pathToWorkingDirectory));
	 
			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);
	 
			transformer.transform(source, result);
	 
			System.out.println("File saved!");
			
		
			
	}
	 catch (Exception e) {
         System.out.println(e);
	 }

	}
	
	
	void writeParametersToFile(ArrayList<ModelParameter> modelParameters){
		
		
		

		try {
		
				DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
				System.out.println(pathToWorkingDirectory);
				
				
				
				Document doc;
				
				try{
					doc = docBuilder.parse("environment.xml");
				}catch(Exception ex){
					
					doc = docBuilder.parse("file:///"+pathToWorkingDirectory);
				}
			
				// Get the root element
				NodeList environmentList = doc.getElementsByTagName("environment");
				Node environment = environmentList.item(0);
				Element elEnvironment = (Element) environment;
				
				
				for(int i= 0; i <  modelParameters.size();i++)
				{
					
					if(modelParameters.get(i).value!=null && !modelParameters.get(i).value.equals("") ){
						
						Element parName = doc.createElement(modelParameters.get(i).name);
						parName.appendChild(doc.createTextNode(modelParameters.get(i).value));
						elEnvironment.appendChild(parName);
						
					}
					
					
				}
				
		 
				// write the content into xml file
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				DOMSource source = new DOMSource(doc);
				StreamResult result = new StreamResult(new File(pathToWorkingDirectory));
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
	
	
	void writeImportsToFile(String pathToOXML){
		

		
		try {
			
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			System.out.println(pathToWorkingDirectory);
			
			Document doc;
			try{
				doc = docBuilder.parse(pathToWorkingDirectory);
			}catch(MalformedURLException ex){
				
				doc = docBuilder.parse("file:///"+pathToWorkingDirectory);
			}
			// Get the root element
			NodeList globalSettingsList = doc.getElementsByTagName("states");
			Node states = globalSettingsList.item(0);
			Element elStates = (Element) states;
			
			Element imports = doc.createElement("imports");
					
			Element import1 = doc.createElement("import");
			
			Element location = doc.createElement("location");
			location.appendChild(doc.createTextNode(pathToOXML));
			import1.appendChild(location);
			
			Element format = doc.createElement("format");
			format.appendChild(doc.createTextNode("xml"));
			import1.appendChild(format);
			
			Element type = doc.createElement("type");
			type.appendChild(doc.createTextNode("environment"));
			import1.appendChild(type);
			
			imports.appendChild(import1);
			
			
			Element import2 = doc.createElement("import");
			
			Element location2 = doc.createElement("location");
			location2.appendChild(doc.createTextNode(pathToOXML));
			import2.appendChild(location2);
			
			Element format2 = doc.createElement("format");
			format2.appendChild(doc.createTextNode("xml"));
			import2.appendChild(format2);
			
			Element type2 = doc.createElement("type");
			type2.appendChild(doc.createTextNode("agent"));
			import2.appendChild(type2);

			imports.appendChild(import2);
			
			
			Element import3 = doc.createElement("import");
			
			Element location3 = doc.createElement("location");
			location3.appendChild(doc.createTextNode("../specific.xml"));
			import3.appendChild(location3);
			
			
			Element format3 = doc.createElement("format");
			format3.appendChild(doc.createTextNode("xml"));
			import3.appendChild(format3);
			
			Element type3 = doc.createElement("type");
			type3.appendChild(doc.createTextNode("environment"));
			import3.appendChild(type3);
			
			imports.appendChild(import3);	
				
			
			elStates.appendChild(imports);
			
		
			
			
			
			
			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(pathToWorkingDirectory));
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

}
