import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class FileChooserFromMenuList extends JPanel{
	
	String  currentDirectoty, endsWithFilter;
	boolean onlyDirectory, filter, saveAFile;
	boolean approved;
	
	
	FileChooserFromMenuList(String initialWorkinDirectory, boolean directoryOnly, boolean usedFilter, String filterString ,boolean saveFile){
		
		currentDirectoty = initialWorkinDirectory;
		onlyDirectory = directoryOnly;
		filter = usedFilter;
		saveAFile = saveFile;
		endsWithFilter =filterString;
	}
	
	public void openFileChooser() {
	    
		JFileChooser chooser = new JFileChooser();
	    chooser.setCurrentDirectory(new java.io.File(currentDirectoty));
	   
	    if(onlyDirectory)
	    {
	    	chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	    }else
	    {
	    	chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
	    }
	    
	    
	    if(filter)
	    chooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
			 public boolean accept(File f) {
				String name = f.getName().toLowerCase();
				return name.endsWith(endsWithFilter) || f.isDirectory();
			}
			public String getDescription() {
				return "xml file";
			}
		});
	    
	    
	    chooser.setAcceptAllFileFilterUsed(false);
	    //   
	    if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
	    
	    	approved = true;
	    	
	      
	      	if(!chooser.getSelectedFile().equals(null))
	     	{
	      		/*Check whether file or directory exists*/
	      		File file =new File(chooser.getSelectedFile().getAbsolutePath());
		     	
	      		if(saveAFile){
	      			
	      			currentDirectoty = chooser.getSelectedFile().getAbsolutePath();
	      			
	      		}else{
		      		if(file.exists()) 
			     	{
			     		currentDirectoty = chooser.getSelectedFile().getAbsolutePath();
				    }else
				    {
				    	JOptionPane.showMessageDialog(null, "File or Directory does not exist!");
				    }
	      		}   	  
		     	  System.out.println("workingDirectory : "+  currentDirectoty);	
	     	}
	    	
	      }else {
	    	  
	    	  System.out.println("canceled");
	    	  approved = false;
	    	  
	      }
	    
	    
	}
	
 	public String getDirectoryOrFile(){
  		
  		System.out.println("getCurrentDirectoty: "+currentDirectoty);
  		return currentDirectoty;
  		
  	}
	

}
