import java.util.ArrayList;


public class ShadowModelXML {
	
	
	public static ArrayList<xagent> agents= new ArrayList<xagent>();
	
	

	ShadowModelXML(){
		
	
		
	}


	public class xagent {
		
		String name;
		ArrayList<variable> memory;
		
		
		xagent(String na){
			
			name = na;
			memory = new ArrayList<variable>();
		}
	
	}

	public class variable{
		
		
		String name;
		
		variable(String na){
			
			name = na;
		}

	}
	
	
	

}
