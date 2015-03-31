package core.engine;

import core.operation.*;

public abstract class OperationEngine implements Runnable {
	
	private CompositeOperation cOperation;
	
	public OperationEngine() {
		this(10);
	}
	
	public OperationEngine(int capacity) {
		cOperation = new CompositeOperation(capacity);
	}
		
	public CompositeOperation getCompositeOperation() {
		return cOperation;
	}
	
}
