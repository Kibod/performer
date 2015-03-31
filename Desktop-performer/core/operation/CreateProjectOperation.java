package core.operation;

import java.util.LinkedList;
import java.io.File;

public class CreateProjectOperation extends CompositeOperation implements IGlobal {
	
	//Operation priority.
	private final Priority priority = Priority.NORMAL;
	
	//Name of the new project.
	private String projectName;
	private String projectRoothFolder;
	private String studentFolder;
	private String fileType;
	private LinkedList<String> testList;
	private TableReportFile reportFile;
	
	private SaveProjectOperation saveProject;
	private LoadProjectOperation loadProject;
	
	public CreateProjectOperation(String projectName, String projectPath, String studentFolder, String fileType, String ... testList) {
		super(2);
		this.testList = new LinkedList<String>();
		this.projectName = projectName;
		this.projectRoothFolder = projectPath;
		this.studentFolder = studentFolder;
		this.fileType = fileType;
		for (String test : testList) {
			this.testList.add(test);
		}
		reportFile = new TableReportFile(projectPath + "\\Izvestaj");
	}
	
	private void createProject() {
	
		String fullProjectPath = projectRoothFolder + projectName;
		String[] allTests = new String[10];
		
		(new File(projectRoothFolder)).mkdirs();
		(new File(studentFolder)).mkdirs();
		
		reportFile.createFile(testList);
		
		saveProject = new SaveProjectOperation(fullProjectPath, studentFolder, fileType, testList.toArray(allTests));
		this.getOperationQueue().add(saveProject);
		
		loadProject = new LoadProjectOperation(fullProjectPath);
		this.getOperationQueue().add(loadProject);
		
	}
	
	/**
	 * Execute the operation.
	 */
	@Override
	public void execute() {
		this.createProject();
		while (!this.getOperationQueue().isEmpty()) {
			this.getOperationQueue().poll().execute();
		}
	}
	
	/**
	 * Return the priority of the operation.
	 * @return a Priority value
	 */
	@Override
	public Priority getPriority() {
		return priority;
	}

}
