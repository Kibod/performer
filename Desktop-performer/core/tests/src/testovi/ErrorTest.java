package core.tests.src.testovi;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class ErrorTest extends Test {
	private int timelimit = 120000;
	
	 
	public ErrorTest(String path,int tl) {
		app_path=path;
		timelimit=tl;
	}
	
	public ErrorTest(String path,String[] argv,int tl) {
		app_path=path;
		arguments=argv;
		timelimit=tl;
	}
	


	public static boolean isProcessRunning(String serviceName)  {
		String TASKLIST = "tasklist";
	String KILL = "taskkill /F /IM ";
	 Process p=null;
	try {
		p = Runtime.getRuntime().exec(TASKLIST);
	} catch (IOException e) {}
	 BufferedReader reader = new BufferedReader(new InputStreamReader(
	   p.getInputStream()));
	 String line;
	 try {
		while ((line = reader.readLine()) != null) {

		
		  if (line.contains(serviceName)) {
		   return true;
		  }
		 }
	} catch (IOException e) {}

	 return false;

	}

	public static void killProcess(String serviceName) {
		String TASKLIST = "tasklist";
		String KILL = "taskkill /F /IM ";
	  try {
		Runtime.getRuntime().exec(KILL + serviceName);
	} catch (IOException e) {}

	 }
		
		public void run() {
			  String[] pth = { "\""+app_path + "\""};
			    String[] cmd = concat(pth, arguments);
			     Process p; 
			    String line = "";
			   
			    try {
					p = Runtime.getRuntime().exec(cmd);
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
						      
						      p.destroy();
						      
						     // System.out.append(" " + p.exitValue()); 
						          
				} catch (IOException e) {} catch (InterruptedException e) {} 
		}//run
	
	
	
	public void execute() {
		
		Set<Callable<String>> callables = new HashSet<Callable<String>>();

		callables.add(new Callable<String>() {
			public String call() throws Exception {
		run();
    	result="false";
        return "false";
			}
		});
		
		result="true";
		ExecutorService executor = Executors.newSingleThreadExecutor();
		try {
			executor.invokeAll(callables, timelimit, TimeUnit.MILLISECONDS);
			
		} catch (InterruptedException e) {} finally {
			
			String processName =app_path.substring(app_path.lastIndexOf('/')+1) ;
		if (isProcessRunning(processName)) { killProcess(processName); }
		
		}
		executor.shutdown();
	
	}//execute
	
	protected void finalize() throws Throwable {
			
		String processName = app_path.substring(3);
		if (isProcessRunning(processName)) { killProcess(processName); }
		super.finalize();
	}
	

}//class

	