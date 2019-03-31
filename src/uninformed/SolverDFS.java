package uninformed;
import java.util.List;
import java.util.Stack;

import core.Cube;
import core.Move;
import core.RubiksCube;
import solver.Pruner;
import solver.Solver;
import solver.State;

/**
 * Depth-first search.
 */
public class SolverDFS extends Solver {

	/**
	 * Create a solver.
	 * 
	 * @param undiscoveredonly
	 *          true if nodes should be added to the discovered list only if they
	 *          have not already been discovered, false to add all nodes
	 * @param pruner
	 *          the pruner to use
	 */
	public SolverDFS ( History history, Pruner pruner ) {
		super(history,pruner);
	}

	protected List<Move> solve ( RubiksCube problem, int maxdepth ) {
		Stack<State> discovered = new Stack<State>();
		discovered.push(new State(problem.getCube(),history_));
		if ( history_ == History.DISCOVERED ) {
			alldiscovered_.add(problem.getCube().toString());
		}
		numdiscovered_++;

		for ( ; !discovered.isEmpty() ; ) {
			if ( shutdown_ ) { return null; }

			maxdiscovered_ = Math.max(maxdiscovered_,discovered.size());

			State current = discovered.pop();
			maxdepth_ = Math.max(maxdepth_,current.getDepth());

			if ( current.getCube().isSolved() ) {
				soldepth_ = current.getDepth();
				return current.getSolutionPath();
			}
			if ( current.getDepth() == maxdepth ) {
				continue;
			}

			for ( Move move : problem.getMoves() ) {
				if ( pruner_.prune(current,move) ) {
					continue;
				}
				Cube newcube = current.getCube().clone();
				newcube.rotate(move);
				if ( !pruneHistory(current,newcube) ) {
					discovered.push(new State(newcube,current.getDepth() + 1,move,
					                          current,current.getHistory()));
					if ( history_ == History.DISCOVERED ) {
						alldiscovered_.add(newcube.toString());
					}
					numdiscovered_++;
				}
			}
		}

		return null;
	}

	@Override
	public List<Move> solve ( RubiksCube problem ) {
		long start = System.currentTimeMillis();
		init();
		List<Move> solution = solve(problem,-1);
		long end = System.currentTimeMillis();
		time_ = end - start;
		return solution;
	}

	@Override
	public String name () {
		return "dfs";
	}

}
