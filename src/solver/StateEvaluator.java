package solver;
import informed.PQState;

/**
 * Evaluation function.
 */
public interface StateEvaluator {

	/**
	 * Compute the value of the evaluation function f for the specified state.
	 * 
	 * @param state
	 *          state to evaluate
	 * @return f(state)
	 */
	public double f ( PQState state );

	/**
	 * Name of this evaluation function, for display purposes.
	 * 
	 * @return evaluator's name
	 */
	public String name ();

}
