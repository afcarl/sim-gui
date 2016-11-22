import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.JTextField;


public class WorkspaceLauncher extends JDialog{
	
	JTextField textfieldPath;
	JButton buttonBrowse, okay, discard;
	GridBagConstraints ts;
	
	public static String workspacePath;
	
	WorkspaceLauncher(){
		
		 setTitle("GUI Launcher: Select Experiment as Workspace");
		 setSize(600,300);
		 
		 setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			addWindowListener( new WindowAdapter(){
				
				public void windowClosing(WindowEvent e)
				{
		    		Object text = "Exit GUI? \n";
		    		
		    		int choice = JOptionPane.showConfirmDialog(null, text,  "Exit GUI",JOptionPane.YES_NO_OPTION);
		   
		    		if(choice==0){
		    			
		    			System.exit(-1);
		    			
		    		}
		   
				}
				});
			
			
		    setLayout(new GridBagLayout());
		    
			ts = new GridBagConstraints();
			ts.insets = new Insets( 5, 5, 5, 5);
			
			
			JLabel label1 = new JLabel("Experiment:");
		 
			ts.gridx = 0; ts.gridy = 0;
			add(label1, ts);
			
			
			textfieldPath = new JTextField(20);
			
			workspacePath = readLastWorkspacePathFromFile();
			textfieldPath.setText(workspacePath);
			textfieldPath.selectAll();
			
			ts.gridx = 1; ts.gridy = 0;
			add(textfieldPath, ts);
			
			//textfieldPath.setHorizontalAlignment(JTextField.RIGHT);
			
			textfieldPath.selectAll();
			
			//textfieldPath.setCaretPosition(workspacePath.length());
			
			
			textfieldPath.selectAll();
			
			
			buttonBrowse = new JButton("Browse ...");
			
			ts.gridx = 2; ts.gridy = 0;
			add(buttonBrowse, ts);
			
			buttonBrowse.addActionListener(new ActionListener(){

			
				public void actionPerformed(ActionEvent arg0) {

					FileChooserFromMenuList chooseFile = new FileChooserFromMenuList(SimulationSettings.WORKING_DIRECTORY,true, false,"", false);
	    			chooseFile.openFileChooser();
	    			
	    		
		    			workspacePath = chooseFile.getDirectoryOrFile();
		    			
		    			if(chooseFile.approved){
			    			setVisible(false);
							dispose();
							new MultiGUI();
		    			}
					
				}
				
				
				
				
			});
			
			
			okay = new JButton("OK");
			
			ts.gridx = 2; ts.gridy = 1;
			add(okay, ts);
			

			okay.addActionListener(new ActionListener(){

			
				public void actionPerformed(ActionEvent arg0) {

					workspacePath = textfieldPath.getText();
					
					
					 File file=new File(textfieldPath.getText());
							  boolean exists = file.exists();
							  if (!exists) {
								JOptionPane.showMessageDialog(null,"The specified workspace directory does not exist");
							  
							  }else{
							 
								 setVisible(false);
								dispose();
								new MultiGUI();
								  
							  }
					
					
		
				}
				
				
				
				
			});
			
			discard = new JButton("Cancel");
			
			ts.gridx = 3; ts.gridy = 1;
			add(discard, ts);
			
			discard.addActionListener(new ActionListener(){

			
				public void actionPerformed(ActionEvent arg0) {

					System.exit(-1);
					
				}
				
				
				
				
			});
		
		 setLocationRelativeTo(null);
		 setModal(true);
		 setVisible(true);

		
		
		
		
	}
	
	
	
	String readLastWorkspacePathFromFile(){
		
		String line;
		BufferedReader in;
		try {
		in = new BufferedReader(new FileReader("PathToWorkspace.txt"));
		line = in.readLine();
		in.close();
		
		return line;

		} catch (FileNotFoundException e) {
		System.out.println(e);
		return "";
		} catch (IOException e) {
			System.out.println(e);
		return "";
		}
		
	}

}
