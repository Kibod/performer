package core.tests.src.testovi;

import java.util.Scanner;

public class Tester {
	private String result = "";
	protected String app_path = null;
	protected String[] arguments = null;

	// By default time limit for applicaton will be 30ms
	private int time_limit = 30000;

	/*-------------------------------------------------*/
	private final boolean error_test = true;

	// Gives Process ID
	private boolean pid_test = false;
	// Compare if output is corect
	private boolean output_test = false;
	// Calculates CPU time for application
	private boolean cpu_time_test = false;
	// Calculates Elapsed time for application
	private boolean elapsed_time_test = false;
	// Private memory of applicaton
	private boolean memory_test = false;
	// Caculate Thread number of application
	private boolean thd_test = false;
	// Caculates Handlers for application
	private boolean hnd_test = false;
	private String output = "";

	private String pid = "";
	private String thd = "";
	private String hnd = "";
	private String memory = "";
	private String cpu_time = "";
	private String elapsed_time = "";
	private String pri = "";
	private String has_error = "";
	private String output_check = " ";
	private String output_ident;

	/*-----------------------------------------------*/
	public Tester(String path) {
		app_path = path;
	}

	public Tester(String path, String[] argv) {
		app_path = path;
		arguments = argv;
	}

	/**
	 * If true Tester will start test that mesure CPU time that application used
	 */
	public void doCpu_time_test(boolean true_false) {
		this.cpu_time_test = true_false;
	}

	/**
	 * If true Tester will start test that messure elasped time since
	 * application is started
	 */
	public void doElapsed_time_test(boolean true_false) {
		this.elapsed_time_test = true_false;
	}

	/** If true Tester will start test that caculates number of handlers */
	public void doHnd_test(boolean true_false) {
		this.hnd_test = true_false;
	}

	/** If true Tester will start test that messures memory consumsion */
	public void doMemory_test(boolean true_false) {
		this.memory_test = true_false;
	}

	/** Set maximum time that applicaton can run, default is 30s */
	public void setTime_limit(int time_limit) {
		this.time_limit = time_limit;
	}

	/**
	 * If true Tester will start test that calculate number of thread running in
	 * application
	 */
	public void doThd_test(boolean true_false) {
		this.thd_test = true_false;
	}

	/** If true Tester will give Process ID */
	public void doPid_test(boolean true_false) {
		this.pid_test = true_false;
	}

	

	/**
	 * If true Tester will compare given string with output that application
	 * gives
	 */
	public void doOutput_test(boolean true_false, String expected_output) {
		this.output_test = true_false;
		output_check = expected_output;
	}

	/** Start all tests */
	public void start() {

		if (error_test) {
			Test error = new ErrorTest(app_path, arguments, time_limit);
			error.execute();
			has_error = error.getResult();
			result += "<error_test>" + has_error + "</error_test>\n";
		}
		if (has_error == "false") {
			if (output_test) {
				Test out_t = new CorrectOutputTest(app_path, arguments,
						output_check);
				out_t.execute();
				output_ident = out_t.getResult();
				output = ((CorrectOutputTest) out_t).getOutput();
				result += "<output_test>" + output_ident + "</output_test>\n";
			}

			// Pid Pri Thd Hnd Priv CPU Time Elapsed Time uredjivanje4
			// 1864 8 1 23 712 0:00:00.202 0:00:00.188

			if (cpu_time_test || elapsed_time_test || thd_test || pid_test
					|| memory_test || hnd_test) {
				Test test = new ProcessInformation(app_path,
						arguments);
				test.execute();
				String ss = test.getResult();
				Scanner s = new Scanner(ss);
				pid = s.next();
				pri = s.next();
				thd = s.next();
				hnd = s.next();
				memory = s.next();
				cpu_time = s.next();
				elapsed_time = s.next();
				s.close();
			}

			if (pid_test) {
				result += "<pid_test>" + pid + "</pid_test>\n";
			}

			if (cpu_time_test) {
				result += "<cpu_time_tes>" + cpu_time + "</cpu_time_tes>\n";
			}

			if (elapsed_time_test) {
				result += "<elapsed_time_test>" + elapsed_time
						+ "</elapsed_time_test>\n";
			}

			if (thd_test) {
				result += "<thd_test>" + thd + "</thd_test>\n";
			}

			if (memory_test) {
				result += "<memory_test>" + memory + "</memory_test>\n";
			}

			if (hnd_test) {
				result += "<hnd_test>" + hnd + "</hnd_test>\n";
			}

		}

	}

	public String getCpu_time() {
		return cpu_time;
	}

	public String getElapsed_time() {
		return elapsed_time;
	}

	public String getHas_error() {
		return has_error;
	}

	public String getHnd() {
		return hnd;
	}

	public String getMemory() {
		return memory;
	}

	public String getOutput() {
		return output;
	}

	public String getPid() {
		return pid;
	}

	public String getPri() {
		return pri;
	}

	public String getResult() {
		return result;
	}

	public String getThd() {
		return thd;
	}

}
