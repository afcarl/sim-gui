
import java.io.*;
import java.util.ArrayList;
import java.util.Vector;
import org.w3c.dom.*;
import javax.swing.JOptionPane;
import javax.xml.parsers.*;

public class ReadModelParameter {
	
	
	File[] fileList ;
	Vector<File> files = new Vector<File>();
	
	ArrayList<ModelParameter> modelParameter = new ArrayList<ModelParameter>();
	
	/*Constructor*/
	public ReadModelParameter(){
		
		
	}
	
	
	public void getFilteredFileList()
	{
		File pathWorkingDirectory = new File(SimulationSettings.EURACE_MODEL_XML);
		System.out.println(SimulationSettings.EURACE_MODEL_XML);
		
		FilenameFilter filterName= new ModelXmlFilter();
		fileList =  RecursiveFileList.listFilesAsArray(pathWorkingDirectory, filterName, true);
		
		System.out.println(fileList[0]);
		
	}
	
	public void getFIleListDirectlyFromEuraceModelXML() 
	{
		
		System.out.println("Starting Files from eurace_model.xml");
		 try {
			 
			 
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
				 JOptionPane.showMessageDialog(null, "No model.xml File set!");
			 }
		  } catch (Exception e) {
			e.printStackTrace();
		  }
	}
	
	
	public ArrayList<ModelParameter> GetModelParameterFromModelXMLFiles(){
		
		
		for (int i=0; i < files.size();i++)
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
		        
		        
		        doc.getDocumentElement().normalize();
		        
		        Node testNode = doc.getElementsByTagName("constants").item(0);
		        
		        //System.out.println("first eleemtn of constants"+testNode+"testNode.equals(null)");
		        
		        if(testNode!=null)
		        {
		        NodeList nList = doc.getElementsByTagName("constants").item(0).getChildNodes();
		        
		        
		       // System.out.println((Node) nList.item(1));
		        //System.out.println(nList.item(2));
		       // System.out.println(nList.item(3));
		        //System.out.println(nList.item(4));
		        
		        
		        for (int temp = 0; temp < nList.getLength(); temp++) {
			        
			        Node nNode = nList.item(temp);
					   if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						   
						   Element eElement = (Element) nNode;
						   
						   System.out.println("getTagValue(type,eElement)"+getTagValue("type",eElement)+"  "+getTagValue("name",eElement)+"  "+getTagValue("description",eElement));
						   
						   ModelParameter par = new ModelParameter(getTagValue("type",eElement),getTagValue("name",eElement),getTagValue("description",eElement));
						   
						   modelParameter.add(par);
						    
					   }
			        }
		        }
			}catch (Exception e) {
				e.printStackTrace();
			  }
		}
		
		return modelParameter;
		
	}
	


private static String getTagValue(String sTag, Element eElement) {
	NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
 
        Node nValue = (Node) nlList.item(0);
        
   
	        System.out.println("Check:   "+sTag+"   "+nValue);
	        
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

class ModelXmlFilter implements FilenameFilter {
    public boolean accept(File dir, String name) {
        return (name.equals("model.xml"));
    }
}
