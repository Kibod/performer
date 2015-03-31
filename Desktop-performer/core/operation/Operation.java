/**
 * Interface Operation represents an operation that can be executed. You can also get a priority of the specified operation.
 * @author Bojan Marjanovic
 */

package core.operation;

public interface Operation {
	
	void execute();
	
	Priority getPriority();

}
