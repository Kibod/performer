package core.engine;

import core.operation.*;

public class StudentOperationEngine extends OperationEngine {
	
	public StudentOperationEngine() {
		this(10);
	}
	
	public StudentOperationEngine(int capacity) {
		super(capacity);
	}
	
	public void submitOperation(IGlobal operation) {
		getCompositeOperation().addOperation(operation);
	}
	
	public void run() {
		getCompositeOperation().execute();
	}

}
