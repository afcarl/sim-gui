import java.util.ArrayList;


public class AgentSettings {
	
	public AgentSettings clone()  {
        try {
			return (AgentSettings) super.clone();
		} catch (CloneNotSupportedException e) {
			
			e.printStackTrace();
			return null;
		}
		
        
    }
	
	
	public static ArrayList<Agent> agents = new ArrayList<Agent>();

}
