import java.util.ArrayList;


public class SelectedModelParameter {
	
	String name;
	String type;
	ArrayList<String> values = new ArrayList<String>();
	
	/*Standard constructor*/
	SelectedModelParameter(){
		
	}
	
	public void setName(String na){
		
		name = na;
		
	}
	
	public void setType(String ty){
		
		type = ty;
		
	}

	public void addValue(String value){
	
	values.add(value);
	}
	
	public void removeValue(int index){
		
		values.remove(index);
	
}
	
	
public String getName(){
		
	 	return name;
		
	}
	
	public String getType(){
		
		return type;
		
	}

	public ArrayList<String> getValue(){
	
		return values;
	
}
	

}
