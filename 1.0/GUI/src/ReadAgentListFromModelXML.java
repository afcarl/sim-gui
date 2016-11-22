import java.io.File;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/*This is to read a list of all agents from the euarce_model.xml file and the subsequent xml files*/
public class ReadAgentListFromModelXML {
	
	
	String euraceModelXML ;
	ArrayList<String>  agentList;
	
	
	
	ReadAgentListFromModelXML(String pathModelXML){
		
		euraceModelXML  = pathModelXML;
		
	}
	
	
	
	public ArrayList<String> GetAgentListModelXMLFiles(){
		
		agentList = new ArrayList<String>();
		
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
        Document doc = docBuilder.parse(new File(euraceModelXML));
        
        doc.getDocumentElement().normalize();
        
        NodeList nList = doc.getElementsByTagName("xagent");
        
        
        for (int temp = 0; temp < nList.getLength(); temp++) {
	        
	        Node nNode = nList.item(temp);
			   if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				   
				   Element eElement = (Element) nNode;
				   
				   agentList.add(getTagValue("name",eElement));
				   
				   //System.out.println("read agent witht name:"+getTagValue("name",eElement));
						    
			   }
	    	}
        
        
        
        
		}catch(Exception e)
		{
			System.out.println(e);
			
			JOptionPane.showMessageDialog(null,"model.xml file not found. Set correct path to model.xml file!");
		}
		
		
		
		return agentList;
		
	}
	
	
	
	private static String getTagValue(String sTag, Element eElement) {
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
		
	  
	
	

}
