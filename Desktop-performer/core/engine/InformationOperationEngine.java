package core.engine;

import core.operation.*;

public class InformationOperationEngine extends OperationEngine {
	
	public InformationOperationEngine() {
		this(10);
	}
	
	public InformationOperationEngine(int capacity) {
		super(capacity);
	}
	
	public void submitOperation(IGlobal operation) {
		getCompositeOperation().addOperation(operation);
	}
	
	public void run() {
		getCompositeOperation().execute();
	}

}
