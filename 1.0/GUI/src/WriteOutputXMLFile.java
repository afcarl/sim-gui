import java.io.File;
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


public class WriteOutputXMLFile {
	
	String pathToWorkingDirectory;
	
	WriteOutputXMLFile(String pathWD){
		
		System.out.println(pathWD);
		

			pathToWorkingDirectory = pathWD+"/output.xml";
			
			
		
		
		
		try{
			 
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
	 
			// root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("states");
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
	
	
	void writeOutputToFile(ArrayList<Agent> agents){
		
		
		

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
				
				Element outputes = doc.createElement("outputs");
				elStates.appendChild(outputes);
			
					
				Element outputSN = doc.createElement("output");
				
				Element typeSN = doc.createElement("type");
				typeSN.appendChild(doc.createTextNode("snapshot"));
				outputSN.appendChild(typeSN);
				
				Element nameSN = doc.createElement("name");
				nameSN.appendChild(doc.createTextNode(""));
				outputSN.appendChild(nameSN);
				
				
				Element locationSN = doc.createElement("location");
				locationSN.appendChild(doc.createTextNode("./"));
				outputSN.appendChild(locationSN);
				
				Element formatSN = doc.createElement("format");
				formatSN.appendChild(doc.createTextNode("xml"));
				outputSN.appendChild(formatSN);
				
				
				Element timeSN = doc.createElement("time");
				
				
				
				
				if(SimulationSettings.SNAPSHOTS){
					
					Element periodSN = doc.createElement("period");
					periodSN.appendChild(doc.createTextNode(Integer.toString(SimulationSettings.SNAPSHOTS_FREQUENCY)));
					timeSN.appendChild(periodSN);
					
					Element phase = doc.createElement("phase");
					phase.appendChild(doc.createTextNode("1"));
					timeSN.appendChild(phase);
					
					outputSN.appendChild(timeSN);
				}else{
					
					Element periodSN = doc.createElement("period");
					periodSN.appendChild(doc.createTextNode(Integer.toString(100000)));
					timeSN.appendChild(periodSN);
					
					Element phase = doc.createElement("phase");
					phase.appendChild(doc.createTextNode("0"));
					timeSN.appendChild(phase);
					
					outputSN.appendChild(timeSN);

					
				}
				
				
				outputes.appendChild(outputSN);
				
				
				
				
				for(int i=0; i< agents.size();i++)
				{
					if(agents.get(i).dataStorageSettings.isSelected)
					{
					
						Element output = doc.createElement("output");
		
						Element type = doc.createElement("type");
					
						type.appendChild(doc.createTextNode("agent"));
						output.appendChild(type);
						
						Element name = doc.createElement("name");
						
						name.appendChild(doc.createTextNode(agents.get(i).agentName));
						output.appendChild(name);
					
						Element location = doc.createElement("location");
						location.appendChild(doc.createTextNode("./"));
						output.appendChild(location);
						
						Element format = doc.createElement("format");
						format.appendChild(doc.createTextNode("xml"));
						output.appendChild(format);
						
						
						Element time = doc.createElement("time");
						
						Element period = doc.createElement("period");
						period.appendChild(doc.createTextNode(agents.get(i).dataStorageSettings.period));
						time.appendChild(period);
						
						Element phase = doc.createElement("phase");
						phase.appendChild(doc.createTextNode(agents.get(i).dataStorageSettings.phase));
						time.appendChild(phase);
						
						output.appendChild(time);
						
						outputes.appendChild(output);
						
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
			location3.appendChild(doc.createTextNode("../environment.xml"));
			import3.appendChild(location3);
			
			
			Element format3 = doc.createElement("format");
			format3.appendChild(doc.createTextNode("xml"));
			import3.appendChild(format3);
			
			Element type3 = doc.createElement("type");
			type3.appendChild(doc.createTextNode("environment"));
			import3.appendChild(type3);
			
			imports.appendChild(import3);
			
			
			Element import4 = doc.createElement("import");
			
			Element location4 = doc.createElement("location");
			location4.appendChild(doc.createTextNode("../specific.xml"));
			import4.appendChild(location4);
			
			
			Element format4 = doc.createElement("format");
			format4.appendChild(doc.createTextNode("xml"));
			import4.appendChild(format4);
			
			Element type4 = doc.createElement("type");
			type4.appendChild(doc.createTextNode("environment"));
			import4.appendChild(type4);
			
			imports.appendChild(import4);	
				
			
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
