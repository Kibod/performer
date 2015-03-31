package core.tests.src.testovi;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.Date;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;


/*
 * Test that caculates spent CPU time of an application
 *  @author Stefan Dragisic - 91stefan@gmail.com
 *  Example of usage:
 * 
 *  TimeTest Test = new TimeTest("C:/application.exe", "argv1,arg2,arg3");
 *	Test.execute();
 *	System.out.print(Test.resultXML());	
 */
  
 
public class TimeTest extends Test{
	
	//Result contains time in miliseconds
	//private String result = "";
	
	/*--------------Settings of TimeTest----------*/	
	
	//In how many iterations to mesure time before geting average time
	//More iteration accurate test, but much slower program
	private int num_iterations=10;
		
	
	//Self adjusting time delay for the minimum time deviation
	private static int time_delay = 0;
	//Time of last delay mesure - using for some type of timer
	private static long td_last_mesure = 0;
	//5 min = 300 000 ms
	//Time delay fuction will be executed every 10min by default
	private static int timer_time = 300000; 
	
	/*-------------------------------------------*/

	
	/** As param give absolute path to file that need to be tested. */
	public TimeTest(String path){
		app_path=path;
	}
	
	/**As param give absolute path to file that will be tested, and arguments wich will app use. */
	public TimeTest(String path,String[] argv){
		app_path=path;
		arguments=argv;
	}
	
	
	
	/**Executes the test.After execution, result can be obitated.*/
	public void execute(){try {
		caculateDelay(timer_time);
		
		 
	      String[] pth = { "\""+app_path + "\"" };
	      String[] cmd = concat(pth, arguments);
	     long stime = 0;
	     
	 for(int i = 0; i<num_iterations ; i++){
		
	      Process p;
		String line = null;
	      p = Runtime.getRuntime().exec(cmd);
	       long start = System.nanoTime();
	      
	      /*Cler buffer or else stucks*/
	      BufferedReader bri = new BufferedReader
			        (new InputStreamReader(p.getInputStream()));
			      BufferedReader bre = new BufferedReader
			        (new InputStreamReader(p.getErrorStream()));
			    
			      while ((line = bri.readLine()) != null) {
			      
			      }
			      bri.close();  
			      while ((line = bre.readLine()) != null) {
			    	 
			      }
			      bre.close();
	      
	    
	      p.waitFor();
	      long end = System.nanoTime();
	      
	      long elapsedTime = end - start;
	      elapsedTime*=1.0e-6;
	    stime+=(elapsedTime-time_delay);
	    // System.out.print(elapsedTime-time_delay + "|"); 
	    Thread.sleep(400); 
	     }
	    
	     //Divane with round up
	     stime= (stime + num_iterations-1)/num_iterations;
	     
	      result += Math.abs(stime);
	     
	      
		} catch (InterruptedException | IOException e) {}
		
	}
	
	
	
	private static void setTime_delay(int time_delay) {
		TimeTest.time_delay = time_delay;
	}
	
	public static int getTime_delay() {
		return time_delay;
	}
	
	public static long getTd_last_mesure() {
		return td_last_mesure;
	}
	
	private static void setTd_last_mesure(long td_last_mesure) {
		TimeTest.td_last_mesure = td_last_mesure;
	}
	
	/**Caculating avrage time delay that need to be sustracted from total time.As param pass time (in ms) on wich will funcion activate. Example 10min -> caculateDelay(600000) */
	private void caculateDelay(int dt){
		
		long cur_time = new Date().getTime();
		if(getTd_last_mesure() == 0) setTd_last_mesure(cur_time);
		
		if( getTd_last_mesure()+dt <= cur_time || time_delay==0 ){
			int tk = time_delay();
			setTime_delay(tk);
			setTd_last_mesure(new Date().getTime());
			
		}
		
	}
	
	//Magic of precision leys here
	private int time_delay(){
		String path = "";
		
		  URI uri = null;
	        URI exe = null;

	        try {
				uri = getJarURI();
			} catch (URISyntaxException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	        try {
				exe = getFile(uri, "SelfTime.exe");
			} catch (ZipException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	        path=exe.toString();
	        path=path.substring(6);
	      
		
		
		int result = 0;
		String line = null;
		for(int i=0; i<15; i++){
		
			try {
				
				int dt = 200;
				//int dt = (int) (100 + Math.random()*(200+100));
				String[] cmd = { "\""+path + "\"", ""+dt };
				Process p = Runtime.getRuntime().exec(cmd);
				long start = System.nanoTime();
				 BufferedReader bri = new BufferedReader
					        (new InputStreamReader(p.getInputStream()));
					    BufferedReader bre = new BufferedReader
					        (new InputStreamReader(p.getErrorStream()));
					    
					      while ((line = bri.readLine()) != null) {
					      
					      }
					      bri.close();  
					      while ((line = bre.readLine()) != null) {
					    	 
					      }
					      bre.close();
			      p.waitFor();
			      long end = System.nanoTime();
			      long elapsedTime = end - start;
			      elapsedTime*=1.0e-6;
			      result +=Math.abs( (int) (elapsedTime - dt));
				
			} catch (IOException e) {} catch (InterruptedException e) {} 
		}
		result/=15; 
		return result;
	}
	private static URI getJarURI()
	        throws URISyntaxException
	    {
	        final ProtectionDomain domain;
	        final CodeSource       source;
	        final URL              url;
	        final URI              uri;

	        domain = TimeTest.class.getProtectionDomain();
	        source = domain.getCodeSource();
	        url    = source.getLocation();
	        uri    = url.toURI();

	        return (uri);
	    }

	    private static URI getFile(final URI    where,
	                               final String fileName)
	        throws ZipException,
	               IOException
	    {
	        final File location;
	        final URI  fileURI;

	        location = new File(where);

	        // not in a JAR, just return the path on disk
	        if(location.isDirectory())
	        {
	            fileURI = URI.create(where.toString() + fileName);
	        }
	        else
	        {
	            final ZipFile zipFile;

	            zipFile = new ZipFile(location);

	            try
	            {
	                fileURI = extract(zipFile, fileName);
	            }
	            finally
	            {
	                zipFile.close();
	            }
	        }

	        return (fileURI);
	    }

	    private static URI extract(final ZipFile zipFile,
	                               final String  fileName)
	        throws IOException
	    {
	        final File         tempFile;
	        final ZipEntry     entry;
	        final InputStream  zipStream;
	        OutputStream       fileStream;

	        tempFile = File.createTempFile(fileName, ".exe");
	        tempFile.deleteOnExit();
	        entry    = zipFile.getEntry(fileName);

	        if(entry == null)
	        {
	            throw new FileNotFoundException("cannot find file: " + fileName + " in archive: " + zipFile.getName());
	        }

	        zipStream  = zipFile.getInputStream(entry);
	        fileStream = null;

	        try
	        {
	            final byte[] buf;
	            int          i;

	            fileStream = new FileOutputStream(tempFile);
	            buf        = new byte[1024];
	            i          = 0;

	            while((i = zipStream.read(buf)) != -1)
	            {
	                fileStream.write(buf, 0, i);
	            }
	        }
	        finally
	        {
	            close(zipStream);
	            close(fileStream);
	        }

	        return (tempFile.toURI());
	    }

	    private static void close(final Closeable stream)
	    {
	        if(stream != null)
	        {
	            try
	            {
	                stream.close();
	            }
	            catch(final IOException ex)
	            {
	                ex.printStackTrace();
	            }
	        }
	    }
	
	
}//class