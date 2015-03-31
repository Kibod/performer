package core.operation;

public class CreateStudentListOperation implements IStudent {
	
	private static Priority priority = Priority.LOW;
	
	@Override
	public void execute() {
		
	}
	
	@Override
	public Priority getPriority() {
		return priority;
	}

}
