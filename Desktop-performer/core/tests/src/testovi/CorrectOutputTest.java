package core.tests.src.testovi;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/*
 *  Test that compare expected output and ouput of app.
 *  @author Stefan Dragisic - 91stefan@gmail.com 
 * 	Example of usage:
 * 
 * 	Test test = new CorrectOutputTest("C:/test.exe", "RADI","arg");
 *	test.execute();
 *	System.out.print(test.resultXML());
 * 
 * 
 */

/**Result of this test is string "true" or "false, depending does expected output equals app output*/
public class CorrectOutputTest extends Test {
	private String output = "";
	private String expected = "";

	/** As param give absolute path to file that need to be tested.End expected output */
	public CorrectOutputTest(String path,String expect) {
		app_path=path;
		expected=expect;
	}
	
	/**As param give absolute path to file that will be tested,expected output and arguments wich will app use. */
	public CorrectOutputTest(String path,String[] argv,String expect) {
		app_path=path;
		expected=expect;
		arguments=argv;
	}

	
	
	public void execute() {
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
				       output+=line;
				      }
				      bri.close();
				      while ((line = bre.readLine()) != null) {
				    	 
				      }
				      bre.close();
				      p.waitFor();
				      
			
				      
				      
		} catch (IOException e) {} catch (InterruptedException e) {}
	    
	    if(expected.equals(output)) result="true"; else result="false"; 
	}
	
	
	
	
	/**Return output of tested app*/
	public String getOutput() {
		return output;
	}

}//class
