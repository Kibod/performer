package core.operation;

import gui.performer_main.Assignment;

import java.util.*;

/**
 * Class TestStudentAssignment represents an operation that loads an array of student assignments and submits them for testing.
 * @author Bojan Marjanovic
 */
public class TestStudentAssignment extends CompositeOperation implements ITest {
	
	//Operation priority.
	private final Priority priority = Priority.NORMAL; 
	//Array of student assignments.
	private ArrayList<Assignment> assignmentList;
	//List of test files.
	private ArrayList<String> testList;
	
	private boolean allTests;
	
	/**
	 * Creates a new TestStudentAssignment instance.
	 * @param assignment - An array of type Assignment that represent student assignments.
	 */
	public TestStudentAssignment(ArrayList<Assignment> assignment) {
		assignmentList = new ArrayList<Assignment>(20);
		allTests = true;
		for (Assignment assig : assignment) {
			assignmentList.add(assig);
		}
	}
	
	/**
	 * Creates a new TestStudentAssignment instance.
	 * @param assignment - An array of type Assignment that represent student assignments.
	 * @param test - An array of String objects that represent XML test file paths.
	 */
	public TestStudentAssignment(List<Assignment> assignment, String ... test) {
		assignmentList = new ArrayList<Assignment>(20);
		testList = new ArrayList<String>();
		allTests = false;
		for (Assignment assig : assignment) {
			assignmentList.add(assig);
		}
		for (String tFile : test) {
			testList.add(tFile);
		}
	}
	
	//Adds the array of student assignments to the priority queue for testing.
	private void loadStudentAssignments() {
		Iterator<Assignment> itrA = assignmentList.iterator();
		String[] tests = new String[10];
		while (itrA.hasNext()) {
			if (allTests) {
				TestingOperation testOp = new TestingOperation(itrA.next());
				this.getOperationQueue().add(testOp);
			} else {
				TestingOperation testOp = new TestingOperation(itrA.next(), testList.toArray(tests));
				this.getOperationQueue().add(testOp);
			}
		}
	}
	
	/**
	 * Execute the operation.
	 */
	@Override
	public void execute() {
		this.loadStudentAssignments();
		while(!this.getOperationQueue().isEmpty()) {
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
