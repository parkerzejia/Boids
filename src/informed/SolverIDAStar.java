package informed;
import java.util.List;
import java.util.PriorityQueue;

import core.Cube;
import core.Move;
import core.RubiksCube;
import solver.Heuristic;
import solver.Pruner;

/**
 * IDA*
 */
public class SolverIDAStar extends SolverPQ {

	private double nextcutoff_;

	/**
	 * Create a solver.
	 * 
	 * @param history
	 *          which option for nodes to check for repeats
	 * @param pruner
	 *          pruner to use
	 */
	public SolverIDAStar ( History history, Pruner pruner, Heuristic heuristic ) {
		super(history,pruner,new AStarEvaluator(heuristic));
	}

	@Override
	public List<Move> solve ( RubiksCube problem ) {
		long start = System.currentTimeMillis();
		init();
		for ( double cutoff = 0 ; true ; ) {
			if ( shutdown_ ) { return null; }

			List<Move> solution = solve(problem,cutoff);
			if ( solution != null ) {
				long end = System.currentTimeMillis();
				time_ = end - start;
				return solution;
			}
			cutoff = nextcutoff_;
		}
	}

	protected List<Move> solve ( RubiksCube problem, double cutoff ) {
		PriorityQueue<PQState> discovered =
		    new PriorityQueue<PQState>(1,new FComparator());
		discovered.add(new PQState(problem.getCube(),history_,evaluator_));
		if ( history_ == History.DISCOVERED ) {
			alldiscovered_.add(problem.getCube().toString());
		}
		numdiscovered_++;

		nextcutoff_ = Double.MAX_VALUE;

		for ( ; !discovered.isEmpty() ; ) {
			if ( shutdown_ ) { return null; }

			maxdiscovered_ = Math.max(maxdiscovered_,discovered.size());

			PQState current = discovered.remove();
			maxdepth_ = Math.max(maxdepth_,current.getDepth());

			if ( current.f() > cutoff ) {
				nextcutoff_ = Math.min(nextcutoff_,current.f());
				continue;
			}

			if ( current.getCube().isSolved() ) {
				soldepth_ = current.getDepth();
				return current.getSolutionPath();
			}

			for ( Move move : problem.getMoves() ) {
				if ( pruner_.prune(current,move) ) {
					continue;
				}
				Cube newcube = current.getCube().clone();
				newcube.rotate(move);
				if ( !pruneHistory(current,newcube) ) {
					discovered.add(new PQState(newcube,current.getDepth() + 1,
					                                  move,current,current.getHistory(),
					                                  evaluator_));
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
	public String name () {
		return "IDA*-" + evaluator_.name();
	}

}
