/**
 * OperationComparator implements interface Comparator<T> and overrides its compare() method to compare the priority of 2 Operation instances.
 * @author Bojan Marjanovic 
 */

package core.operation;

import java.util.*;

public class OperationComparator implements Comparator<Operation> {
	
	@Override
	public int compare(Operation operation1, Operation operation2) {
		if (operation1.getPriority().ordinal()>operation2.getPriority().ordinal())
			return 1;
		else if (operation1.getPriority().ordinal()<operation2.getPriority().ordinal())
			return -1;
		else return 0;
	}

}
