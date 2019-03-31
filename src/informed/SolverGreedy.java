package informed;
import solver.Heuristic;
import solver.Pruner;

/**
 * Greedy best first search.
 */

public class SolverGreedy extends SolverPQ {

	/**
	 * Greedy best-first search.
	 * 
	 * @param history
	 *          which option for nodes to check for repeats
	 * @param pruner
	 *          pruner to use
	 * @param heuristic
	 *          heuristic to use
	 */

	public SolverGreedy ( History history, Pruner pruner, Heuristic heuristic ) {
		super(history,pruner,new GreedyEvaluator(heuristic));
	}

	@Override
	public String name () {
		return "greedy-" + evaluator_.name();
	}

}
