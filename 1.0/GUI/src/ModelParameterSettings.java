import java.util.ArrayList;


public class ModelParameterSettings {
	
	public ModelParameterSettings clone()  {
        try {
			return (ModelParameterSettings) super.clone();
		} catch (CloneNotSupportedException e) {
			
			e.printStackTrace();
			return null;
		}
		
        
    }
	
	
	public static ArrayList<ModelParameter> modelParameters = new ArrayList<ModelParameter>();
	
	

}
