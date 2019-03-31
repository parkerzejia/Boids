package uninformed;
import java.util.List;

import core.Move;
import core.RubiksCube;
import solver.Pruner;

/**
 * Iterative deepening.
 */
public class SolverIDS extends SolverDFS {

	/**
	 * Create a solver.
	 * 
	 * @param history
	 *          which option for nodes to check for repeats
	 * @param pruner
	 *          pruner to use
	 */
	public SolverIDS ( History history, Pruner pruner ) {
		super(history,pruner);
	}

	@Override
	public List<Move> solve ( RubiksCube problem ) {
		long start = System.currentTimeMillis();
		init();
		for ( int depth = 0 ; true ; depth++ ) {
			if ( shutdown_ ) { return null; }

			alldiscovered_.clear();
			List<Move> solution = solve(problem,depth);
			if ( solution != null ) {
				long end = System.currentTimeMillis();
				time_ = end - start;
				return solution;
			}
		}
	}

	@Override
	public String name () {
		return "ids";
	}

}
