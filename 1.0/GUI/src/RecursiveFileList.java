import java.io.File;
import java.io.FilenameFilter;
import java.util.Collection;
import java.util.Vector;


public class RecursiveFileList {
	
	
	public static File[] listFilesAsArray(
			File directory,
			FilenameFilter filter,
			boolean recurse)
	{
		Collection<File> files = listFiles(directory,
				filter, recurse);
		
		File[] arr = new File[files.size()];
		return files.toArray(arr);
	}

	public static Collection<File> listFiles(
	
			File directory,
			FilenameFilter filter,
			boolean recurse)
	{
		// List of files / directories
		Vector<File> files = new Vector<File>();
	
		
		// Get files / directories in the directory
		File[] entries = directory.listFiles();
		
		// Go over entries
		for (File entry : entries)
		{

			// If there is no filter or the filter accepts the 
			// file / directory, add it to the list
			if (filter == null || filter.accept(directory, entry.getName()))
			{
				files.add(entry);
			}
			
			// If the file is a directory and the recurse flag
			// is set, recurse into the directory
			if (recurse && entry.isDirectory())
			{
				files.addAll(listFiles(entry, filter, recurse));
			}
		}
		
		// Return collection of files
		return files;		
	}

}
