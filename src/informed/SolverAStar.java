package informed;
import solver.Heuristic;
import solver.Pruner;

/**
 * A* search.
 */

public class SolverAStar extends SolverPQ {

	/**
	 * A* search.
	 * 
	 * @param history
	 *          which option for nodes to check for repeats
	 * @param pruner
	 *          pruner to use
	 * @param heuristic
	 *          heuristic to use
	 */
	public SolverAStar ( History history, Pruner pruner, Heuristic heuristic ) {
		super(history,pruner,new AStarEvaluator(heuristic));
	}

	@Override
	public String name () {
		return "A*-" + evaluator_.name();
	}

}
