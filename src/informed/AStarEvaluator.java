package informed;
import solver.Heuristic;
import solver.StateEvaluator;

/**
 * A* evaluation function f(n) = g(n)+h(n).
 */
public class AStarEvaluator implements StateEvaluator {

	// heuristic to use
	private Heuristic heuristic_;

	/**
	 * Create an A* evaluation function using the specified heuristic for h(n).
	 * 
	 * @param heuristic
	 *          h(n)
	 */
	public AStarEvaluator ( Heuristic heuristic ) {
		super();
		heuristic_ = heuristic;
	}

	@Override
	public double f ( PQState state ) {
		return state.g() + heuristic_.h(state);
	}

	@Override
	public String name () {
		return heuristic_.name();
	}

}
