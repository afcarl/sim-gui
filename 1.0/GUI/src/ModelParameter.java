
public class ModelParameter implements Cloneable {
    

	
	/****************************************/
	
	public ModelParameter clone()  {
        try {
			return (ModelParameter) super.clone();
		} catch (CloneNotSupportedException e) {
			
			e.printStackTrace();
			return null;
		}
		
        
    }
	
	String type;
	String name;
	String description;
	String value;
	
	ModelParameter(String ty, String na, String descr){
		
		type = ty;
		name = na;
		description = descr;
	}
	
	public String getType(){
		
		return type;
	}
	
	public String getName(){
		
		return name;
	}

	public String getDescription(){
		
		return description;
	}

}
