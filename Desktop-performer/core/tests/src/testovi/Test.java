package core.tests.src.testovi;
import java.util.Arrays;


public abstract class Test {
	protected String result="";
	protected String app_path = null;
	protected String[] arguments = null;
	//public Class<?>[] klase =this.getClass().getClasses();
	/**Executes the test.After execution, result can be obitated.*/
	public abstract void execute();
	
	/**Returns test result as string. */
	public String getResult(){return result;}
	
	/**Returns test result in XML format: <"NameOfTest>Result<"/NameOfTest> */
	public String resultXML(){
		String s = "<" + this.getClass().getName() + ">" + result + "</" + this.getClass().getName() + ">" ;
		return s;
	}
	
	/**Concat two arrays*/
	protected static <T> T[] concat(T[] first, T[] second) {
		  T[] result = Arrays.copyOf(first, first.length + second.length);
		  System.arraycopy(second, 0, result, first.length, second.length);
		  return result;
		}// concat
	
	
}//class
