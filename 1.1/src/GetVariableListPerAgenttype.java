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


public class GetVariableListPerAgenttype {
	
	boolean typeFilter;
	String DirectoryEuraceModelXML;
	ArrayList<VariableList> variableListAgent;
	ArrayList<Agent> agentList;
	Vector<File> files = new Vector<File>();
	ArrayList<Variable> varList;
	
	GetVariableListPerAgenttype(ArrayList<Agent> agents, boolean filter, String path){
		
		agentList = agents;
		typeFilter = filter;
		DirectoryEuraceModelXML = path;
	}
	
	
	
		
	  
	

}
