package core.tests.src.testovi;

import java.util.Scanner;




public class MainTester {

 //min i max utrosak memorije
	//crtanje fje
	
	public static void main(String[] args) {
		System.out.println("Krecemo:\n");
		
		String[] argvs = {"30000","1"};
		Tester test = new Tester("C:/uredjivanje4.exe",argvs);
		test.doOutput_test(true, "Stefan");
		test.doCpu_time_test(true);
		test.doElapsed_time_test(true);
		test.doThd_test(true);
		test.doHnd_test(true);
		test.doMemory_test(true);
		test.setTime_limit(20000);
		
	
		
		test.start();
	
	System.out.println(test.getResult() + " \n " + test.getOutput());

	
		
		}//main
		




}//class
