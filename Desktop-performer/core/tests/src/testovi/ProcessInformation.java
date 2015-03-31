package core.tests.src.testovi;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.sun.management.jmx.Trace;


public class ProcessInformation extends Test {
private boolean flag=true;
public String lout="";
	public ProcessInformation(String path){
		app_path=path;
	}
	
	
	public ProcessInformation(String path,String[] argv){
		app_path=path;
		arguments=argv;
	}
	
	
	public void execute() {
	    String[] pth = { "\""+app_path + "\""};
	    String[] cmd = concat(pth, arguments);
	    Process p;
	    String line = "";
	    try {
	    	
	    
	    	
			p = Runtime.getRuntime().exec(cmd);  
			
			while(flag){       getInfo(); }result=lout; 
			
			
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
	    	
			//Pid    Pri   Thd    Hnd    Priv        CPU Time       Elapsed Time          
		    //1864    8     1     23     712        0:00:00.202     0:00:00.188
				      
					result=result.substring(result.length()-63);	      
		} catch (IOException | InterruptedException e) {}
	}
	
	
	private void getInfo(){ 
		String path = "C:/pslist.exe";
		String processName = app_path.substring(app_path.lastIndexOf('/')+1,app_path.length()-4);
		String line = "";
		String output = "";
		try{
		String[] cmd = { "\""+path + "\"",processName};
		Process p = Runtime.getRuntime().exec(cmd);
		
		
		 BufferedReader bri = new BufferedReader
			        (new InputStreamReader(p.getInputStream()));
			    BufferedReader bre = new BufferedReader
			        (new InputStreamReader(p.getErrorStream()));
			    
			      while ((line = bri.readLine()) != null) {
			    	  output+=line;
			      }
			      bri.close();  
			      while ((line = bre.readLine()) != null) {
			    	 
			      }
			      bre.close();
	      p.waitFor();
		} catch (IOException e) {} catch (InterruptedException e) {} 
		
		
		
		
		if(!output.contains("was not found")){ lout=output; }
		if(output.contains("was not found")) flag=false;
		else { flag=true;}
		

	}

	

}//class
