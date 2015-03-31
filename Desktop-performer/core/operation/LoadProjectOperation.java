package core.operation;

/**
 * Class LoadProjectOperation represents an operation that loads one project into the application.
 * @author Bojan Marjanovic
 *
 */
public class LoadProjectOperation extends CompositeOperation implements IGlobal {
	
	//Operation priority.
	private final Priority priority = Priority.NORMAL;
	
	//Full path of the project that is to be loaded.
	private String projectPath;
	
	public LoadProjectOperation(String projectPath) {
		this.projectPath = projectPath;
	}
	
	/**
	 * Return the name of the project template.
	 * @return a String object
	 */
	public String getProjectName() {
		return projectPath;
	}
	
	/*
	 * This method sets the active project.
	 * Static method of the TestingOperation class is used and the projectPath is given as the path to the XML project file.
	 */
	private void loadProject() {
		TestingOperation.setXMLProjectFile(projectPath);
	}
	
	/**
	 * Execute the operation.
	 */
	@Override
	public void execute() {
		this.loadProject();
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
