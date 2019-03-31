package informed;
import solver.Heuristic;
import solver.StateEvaluator;

/**
 * Greedy evaluator: f(n) = h(n)
 */

public class GreedyEvaluator implements StateEvaluator {

	private Heuristic heuristic_;

	/**
	 * Create a greedy evaluator using the specified heuristic.
	 * 
	 * @param heuristic
	 */
	public GreedyEvaluator ( Heuristic heuristic ) {
		super();
		heuristic_ = heuristic;
	}

	@Override
	public double f ( PQState state ) {
		return heuristic_.h(state);
	}

	@Override
	public String name () {
		return heuristic_.name();
	}

}
