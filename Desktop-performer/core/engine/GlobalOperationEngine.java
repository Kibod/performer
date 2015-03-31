package core.engine;

import core.operation.*;

public class GlobalOperationEngine extends OperationEngine {
	
	public GlobalOperationEngine() {
		this(10);
	}
	
	public GlobalOperationEngine(int capacity) {
		super(capacity);
	}
	
	public void submitOperation(IGlobal operation) {
		getCompositeOperation().addOperation(operation);
	}
	
	public void run() {
		getCompositeOperation().execute();
	}

}
