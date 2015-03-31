package core.operation;

import java.util.*;

/**
 * Class CompositeOperation represents a priority queue of Operation instances that are waiting to be executed.
 * @author Bojan Marjanovic
 */
public class CompositeOperation implements Operation {
	
	private PriorityQueue<Operation> operationQueue;
	private final Priority priority = Priority.NORMAL;
	
	public CompositeOperation() {
		this(10);
	}
	
	public CompositeOperation(int queueCapacity) {
		OperationComparator oComparator = new OperationComparator();
		operationQueue = new PriorityQueue<Operation>(queueCapacity, oComparator);
	}
	
	@Override
	public synchronized void execute() {
		try {
			while (operationQueue.isEmpty())
				wait();
		} catch (InterruptedException ie) {
			System.out.println(ie);
		}
		while (!operationQueue.isEmpty()) {
			operationQueue.poll().execute();
		}
	}
	
	@Override
	public Priority getPriority() {
		return priority;
	}
	
	public synchronized void addOperation(Operation operation) {
		operationQueue.offer(operation);
		notifyAll();
	}
	
	public PriorityQueue<Operation> getOperationQueue() {
		return operationQueue;
	}

}
