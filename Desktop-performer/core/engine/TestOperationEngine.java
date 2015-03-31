package core.engine;

import core.operation.*;

public class TestOperationEngine extends OperationEngine {
	
	public TestOperationEngine() {
		this(10);
	}
	
	public TestOperationEngine(int capacity) {
		super(capacity);
	}
	
	public void submitOperation(IGlobal operation) {
		getCompositeOperation().addOperation(operation);
	}
	
	public void run() {
		getCompositeOperation().execute();
	}

}
