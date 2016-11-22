import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.lang.reflect.Method;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;



public class ReadXMLFile {
	
	String pathToEuraceModelXML = SimulationSettings.EURACE_MODEL_XML;
	
	String parameter , pathFile;
	String parameterValue ;
	ArrayList<ModelParameter> modelParameter;
	

	
	ReadXMLFile( String pathToFile){
		
		
		pathFile = pathToFile;
	}
	
	
	
	public String readGlobalParameterfromXML(String parameterName)
	{
		
		parameterValue = "";
		
    try {
    	
    	parameter = parameterName;
    	 
    	SAXParserFactory factory = SAXParserFactory.newInstance();
    	SAXParser saxParser = factory.newSAXParser();
     
    	DefaultHandler handler = new DefaultHandler() {
     
    		boolean isParameter = false;
     
    	public void startElement(String uri, String localName,String qName, 
                    Attributes attributes) throws SAXException {
    
     
    		if (qName.equalsIgnoreCase(parameter)) {
    			isParameter = true;
    		}
     
     
    	}
     
    	public void endElement(String uri, String localName,
    		String qName) throws SAXException {
     
   
    	}
     
    	public void characters(char ch[], int start, int length) throws SAXException {
     
    		if (isParameter) {
    			parameterValue = new String(ch, start, length);
    			isParameter = false;
    		}
     
    	}
     
         };
     
         try{
           saxParser.parse(pathFile, handler);
         }catch(Exception exc){
        	 System.out.println(exc);
         }
     
         } catch (Exception e) {
           e.printStackTrace();
         }
         
         return parameterValue;
     
       }
	
	
	
	
	
	
	
	boolean returnsTrueOrFalseAsBoolean(String input){
		
		if(input.equals("true"))
			return true;
		else
			return false;
		
		
	}
	
	
	
	private static String getTagValue(String sTag, Element eElement) {
		
		NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
	 
	        Node nValue = (Node) nlList.item(0);
	        
	   
		      //  System.out.println("Check:   "+sTag+"   "+nValue);
		        
		        //nValue.equals(null);
		 
		        if( nValue == null || nValue.equals(""))
		        {
		        	return "";
		        }else
		        {
		        	return nValue.getNodeValue();
		        }
	        }
		

public class ReadClassFromXML {
	
	Document doc;
	
	
	Object toplevelClassInstance; 
	Class<?> toplevelClass;
	
ReadClassFromXML( Object object , String rootElement){
	
	
	try{
		
		
		toplevelClass = object.getClass();
		
		System.out.println(toplevelClass);
		
		toplevelClassInstance = object;
		
		
		
		/*read pathes to model.xml files from eurace_model.xml*/
 		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
 		
 		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.parse(new File(pathFile));
	 	

        doc.getDocumentElement().normalize();
        
        NodeList nList = doc.getElementsByTagName(rootElement);
        Node nNode = nList.item(0);
        Element nElement = (Element) nNode;
        
  
		
			
			
			Field[] variables = toplevelClass.getDeclaredFields();
				
				
				for(int i=0; i< variables.length;i++){
					
					if(variables[i].getType().isPrimitive()){
					
						
						if(variables[i].getType().getName().toString().equals("boolean")){
							
							
							if(Modifier.isStatic(variables[i].getModifiers()))
								variables[i].setBoolean(null, returnsTrueOrFalseAsBoolean(getTagValue(variables[i].getName(),nElement)));
							else
								variables[i].setBoolean(toplevelClassInstance, returnsTrueOrFalseAsBoolean(getTagValue(variables[i].getName(),nElement)));
						}else if(variables[i].getType().getSimpleName().toString().equals("int")){
						
							if(Modifier.isStatic(variables[i].getModifiers()))
									variables[i].set(null, Integer.parseInt(getTagValue(variables[i].getName(),nElement)));
							else
								variables[i].set(toplevelClassInstance, Integer.parseInt(getTagValue(variables[i].getName(),nElement)));
							
							}else if(variables[i].getType().getSimpleName().toString().equals("double")){
								
								if(Modifier.isStatic(variables[i].getModifiers()))
									variables[i].set(null, Double.parseDouble(getTagValue(variables[i].getName(),nElement)));
								else
									variables[i].set(toplevelClassInstance, Double.parseDouble(getTagValue(variables[i].getName(),nElement)));
								
							}else{
								
								if(Modifier.isStatic(variables[i].getModifiers()))
									variables[i].set(null, (getTagValue(variables[i].getName(),nElement)));
								else
									variables[i].set(toplevelClassInstance,(getTagValue(variables[i].getName(),nElement)));
								
							}
								
					}
				}
						
	
				
				
				
				
				for(int i=0; i< variables.length;i++){
					
					if(!variables[i].getType().isPrimitive()){
				
						
						if(variables[i].getType().getSimpleName().toString().equals("ArrayList")){
							
							
							
							
							
							
							
							
							String fullName = variables[i].getGenericType().toString();
				        	
				        	String name = fullName.substring(variables[i].getGenericType().toString().indexOf("<")+1, variables[i].getGenericType().toString().indexOf(">"));
				  
				        	
				        	String javaName = name;
				        	
				        	name = name.replace("$", ".");
				        	
				        	int index = name.indexOf(".");
				        	
				        	String name2;
				        	
				        	if(index!=(-1)){
					        	do{
					        		
					        		index = name.indexOf(".", index);
					        		
					        	}while(index==(-1));
					        	
					        	name2 = name.substring(index+1);
					        	
					        	}else{
					        		
					        		name2 = name;
					        	}
				   
						 NodeList arrayListNodelist = nElement.getElementsByTagName(name);
						 
					 	 Class<?> arList = (Class<?>) Class.forName("java.util.ArrayList");
			        	 
			        	 Object arrayList = arList.getConstructor().newInstance();
						 
						 
						 for(int j=0; j<arrayListNodelist.getLength();j++ ){
							 

				      
				        		 Method method = arList.getMethod("add",Object.class);
				        		 method.invoke(arrayList,  getArrayListItemOfType(Class.forName(javaName),name,nElement,j ));
				        
							 
						        if(Modifier.isStatic(variables[i].getModifiers()))
						        	variables[i].set(null, arrayList);
						        else
						        	variables[i].set(toplevelClassInstance, arrayList);
							 
							 
						 }
						 
						 
						 
			
							
						}else if(variables[i].getType().getSimpleName().toString().equals("String")) {
							
							if(Modifier.isStatic(variables[i].getModifiers()))
								variables[i].set(null, getTagValue(variables[i].getName(),nElement));
							else
								variables[i].set(toplevelClassInstance, getTagValue(variables[i].getName(),nElement));
							
							
						}else{
							
							
							if(!variables[i].getName().toString().equals("this$0")){
								
								if(Modifier.isStatic(variables[i].getModifiers()))
									variables[i].set(null, getArrayListItemOfType(variables[i].getType(),variables[i].getName().toString(),nElement,0));
								else
									variables[i].set(toplevelClassInstance, getArrayListItemOfType(variables[i].getType(),variables[i].getName().toString(),nElement,0 ));
							}
			
						}

						
					}

					
				}
		
	
}catch(Exception e){
	
	
	
	System.out.println(e);
	
	
}
	
	
	
}

Object getArrayListItemOfType( Class<?> classOfItem, String nodeName, Element element, int index ){
	
	


	try{
		
		
	
    	 
    //	 Object object = objClass.getConstructor().newInstance();
		
		nodeName = nodeName.replace("$", ".");

		 NodeList nList = element.getElementsByTagName(nodeName);
	        Node nNode = nList.item(index);
	        Element nElement = (Element) nNode;
	
		
	        if(nElement==null)
	        {
	        	
	        	/*break;*/
	        	return null;
	        	
	        }
		
	
		Object object1 = null;
		
		Constructor<?>[] constrList= classOfItem.getDeclaredConstructors();
		
		
		for(int i=0; i <constrList.length;i++ ){
			
			if(i==0){
				
				Object[] ob = new Object[constrList[i].getParameterTypes().length];
				
				
				
				
				for(int j=0; j <ob.length;j++){

					
					if(constrList[i].getParameterTypes()[j].toString().equals(toplevelClass.toString())){
						
						ob[j] = toplevelClassInstance;
						
					}else if (constrList[i].getParameterTypes()[j].toString().equals("boolean")){
						ob[j]=false;
					}else if(constrList[i].getParameterTypes()[j].isPrimitive()){
						ob[j]=null;
					}else if(constrList[i].getParameterTypes()[j].getSimpleName().toString().equals("String")){
						
						ob[j]=null;
						
					}else{
						
						ob[j] = returnInitialicedInstanceOfClassWithNull(constrList[i].getParameterTypes()[j].getName().toString());
						
						
						
					}
				}
				
				
				
				object1 = classOfItem.getDeclaredConstructors()[i].newInstance(ob);
				
				break;
				
			}
			
			
		}
		
				Field[] variables = classOfItem.getDeclaredFields();
					
					
					for(int i=0; i< variables.length;i++){
						
						if(variables[i].getType().isPrimitive()){
						
							
							if(variables[i].getType().getName().toString().equals("boolean")){
				
								
								variables[i].setBoolean(object1, returnsTrueOrFalseAsBoolean(getTagValue(variables[i].getName(),nElement)));
					
								
							}else{
							
							
								if(variables[i].getType().getName().equals("int"))
									variables[i].set(object1, Integer.parseInt(getTagValue(variables[i].getName(),nElement)));
								else if(variables[i].getType().getName().equals("double"))
									variables[i].set(object1, Double.parseDouble(getTagValue(variables[i].getName(),nElement)));
		
		
							}
						

							
						}

						
					}
		
					for(int i=0; i< variables.length;i++){
						
						if(!variables[i].getType().isPrimitive()){
					
							
							if(variables[i].getType().getSimpleName().toString().equals("ArrayList")){
								
					
								String fullName = variables[i].getGenericType().toString();
					        	
					        	String name = fullName.substring(variables[i].getGenericType().toString().indexOf("<")+1, variables[i].getGenericType().toString().indexOf(">"));
					   
					        	String javaName = name;
					        	
					        	name = name.replace("$", ".");
					        	
					        	int indexOfName = name.indexOf(".");

					        	String name2;
					        	
					        	
					        	if(indexOfName!=(-1)){
						        	do{
						        		
						        		indexOfName = name.indexOf(".", indexOfName);
						        		
						        	}while(indexOfName==(-1));
						        	
						        	name2 = name.substring(indexOfName+1);
						        	
						        	}else{
						        		
						        		name2 = name;
						        	}
					        	
					        	
					        	
					        	name2 = name.substring(indexOfName+1);
				
					        	
							 NodeList arrayListNodelist = nElement.getElementsByTagName(name);
							 
						 	 Class<?> arList = (Class<?>) Class.forName("java.util.ArrayList");
				        	 
				        	 Object arrayList = arList.getConstructor().newInstance();
							 
							 
							 for(int j=0; j<arrayListNodelist.getLength();j++ ){

					        		 Method method = arList.getMethod("add",Object.class);
					        		 method.invoke(arrayList,  getArrayListItemOfType(Class.forName(javaName),name,nElement,j ));
	
							        variables[i].set(object1, arrayList);
		 
							 }
							 
							 
							 
				
								
							}else if(variables[i].getType().getSimpleName().toString().equals("String")) {
	
								variables[i].set(object1, getTagValue(variables[i].getName(),nElement));
								
								
							}else{
	;
								if(!variables[i].getName().toString().equals("this$0"))
									variables[i].set(object1,  getArrayListItemOfType(variables[i].getType(),variables[i].getName().toString(),nElement,0) );
							
								
				
							}

							
						}
						
					}
	

		return object1;
		
	}catch(Exception ex2){
		
		System.out.println(ex2);
		return null;
		
	}
	
	

	
}

public Object returnInitialicedInstanceOfClassWithNull(String className){
	
	Object object = null;
	
	try{

	
	Class<?> classOfItem = Class.forName(className);
	
	Constructor<?>[] constrList= classOfItem.getDeclaredConstructors();
	
	
	for(int i=0; i <constrList.length;i++ ){

		
		if(i==0){
			
			Object[] ob = new Object[constrList[i].getParameterTypes().length];
			
			
			for(int j=0; j <ob.length;j++){
	
				
				if(constrList[i].getParameterTypes()[j].toString().equals(toplevelClass.toString())){
					
					ob[j] = toplevelClassInstance;
					
				}else if (constrList[i].getParameterTypes()[j].toString().equals("boolean")){
					ob[j]=false;
				}else if(constrList[i].getParameterTypes()[j].isPrimitive()){
					ob[j]=null;
				}else if(constrList[i].getParameterTypes()[j].getSimpleName().toString().equals("String")){
					
					ob[j]=null;
					
				}else{
					
					ob[j] = returnInitialicedInstanceOfClassWithNull(constrList[i].getParameterTypes()[j].getName().toString());
					
					
					
				}
			}
			
			
			
			object = classOfItem.getDeclaredConstructors()[i].newInstance(ob);
			
			break;
			
		}
		
		
	}
	
	}catch(Exception ex2){
		
		System.out.println(ex2);
		
	}
	return object;
}

	
}

	  
	
	
	
}
	



