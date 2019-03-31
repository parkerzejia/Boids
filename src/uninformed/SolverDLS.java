package uninformed;
import java.util.List;

import core.Move;
import core.RubiksCube;
import solver.Pruner;

/**
 * Depth-limited search.
 */
public class SolverDLS extends SolverDFS {

	private int limit_;

	/**
	 * Create a solver.
	 * 
	 * @limit depth limit for search
	 * @param history
	 *          which option for nodes to check for repeats
	 * @param pruner
	 *          pruner to use
	 */
	public SolverDLS ( int limit, History history, Pruner pruner ) {
		super(history,pruner);
		limit_ = limit;
	}

	@Override
	public List<Move> solve ( RubiksCube problem ) {
		long start = System.currentTimeMillis();
		init();
		List<Move> solution = solve(problem,limit_);
		long end = System.currentTimeMillis();
		time_ = end - start;
		return solution;
	}

	@Override
	public String name () {
		return "dls";
	}

}
