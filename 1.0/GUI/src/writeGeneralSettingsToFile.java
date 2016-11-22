import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;


public class writeGeneralSettingsToFile {
	
	private BufferedWriter bw;

	String pathToFile;
	
	writeGeneralSettingsToFile(String path, boolean withBashStatement){
		
		pathToFile = path;
		
		try{
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(pathToFile,false)));
			
			if(withBashStatement)
				{
				bw.append("#!/bin/bash");
				bw.newLine();
				}
			bw.close();
		}
		catch(Exception e)
		{
			System.out.println("Content was not written to file!");
		}
		
	}
	
	
	public void writeToFile(String toWrite){
		
		try{
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(pathToFile,true)));
			bw.append(toWrite);
			bw.newLine();
			bw.close();
		}
		catch(Exception e)
		{
			System.out.println("Content was not written to file!");
		}
		
	}
	
	
public void writeToFileWithoutNewLine(String toWrite){
		
		try{
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(pathToFile,true)));
			bw.append(toWrite);
			bw.close();
		}
		catch(Exception e)
		{
			System.out.println("Content was not written to file!");
		}
		
	}
		
		
	

}
