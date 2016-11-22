import java.io.IOException;
import java.io.InputStream;


public class InputStreamHandler extends Thread{

	/**
	  * Stream being read
	  */
			
	 private InputStream m_stream;
			
	 /**
	  * The StringBuffer holding the captured output
	  */
			
	 private StringBuffer m_captureBuffer;
			
	 /**
	  * Constructor. 
	  * 
	  * @param 
	  */
			
	 InputStreamHandler( StringBuffer captureBuffer, InputStream stream )
	 {
	  m_stream = stream;
	  m_captureBuffer = captureBuffer;
	  start();
	 }
			
	 /**
	  * Stream the data.
	  */
			
	 public void run()
	 {
	  try
	  {
	   int nextChar;
	   while( (nextChar = m_stream.read()) != -1 )
	   {
	    m_captureBuffer.append((char)nextChar);
	   }
	  }
	  catch( IOException ioe )
	  {
	  }
	 }
	
	
}
