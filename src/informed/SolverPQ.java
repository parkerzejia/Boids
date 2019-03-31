package informed;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import core.Cube;
import core.Move;
import core.RubiksCube;
import solver.Pruner;
import solver.Solver;
import solver.StateEvaluator;

/**
 * PQ-based informed search.
 */
public abstract class SolverPQ extends Solver {

	protected StateEvaluator evaluator_;

	/**
	 * Create a solver.
	 * 
	 * @param history
	 *          which option for nodes to check for repeats
	 * @param pruner
	 *          pruner to use
	 * @param evaluator
	 *          evaluation function
	 */
	public SolverPQ ( History history, Pruner pruner,
	                         StateEvaluator evaluator ) {
		super(history,pruner);
		evaluator_ = evaluator;
	}

	@Override
	public List<Move> solve ( RubiksCube problem ) {
		long start = System.currentTimeMillis();
		init();

		PriorityQueue<PQState> discovered =
		    new PriorityQueue<PQState>(1,new FComparator());
		discovered.add(new PQState(problem.getCube(),history_,evaluator_));
		if ( history_ == History.DISCOVERED ) {
			alldiscovered_.add(problem.getCube().toString());
		}
		numdiscovered_++;

		for ( ; !discovered.isEmpty() ; ) {
			if ( shutdown_ ) { return null; }

			maxdiscovered_ = Math.max(maxdiscovered_,discovered.size());

			PQState current = discovered.remove();
			maxdepth_ = Math.max(maxdepth_,current.getDepth());

			if ( current.getCube().isSolved() ) {
				soldepth_ = current.getDepth();
				long end = System.currentTimeMillis();
				time_ = end - start;
				return current.getSolutionPath();
			}

			for ( Move move : problem.getMoves() ) {
				if ( pruner_.prune(current,move) ) {
					continue;
				}
				Cube newcube = current.getCube().clone();
				newcube.rotate(move);
				if ( !pruneHistory(current,newcube) ) {
					PQState newstate =
					    new PQState(newcube,current.getDepth() + 1,move,current,
					                       current.getHistory(),evaluator_);
					discovered.add(newstate);
					if ( history_ == History.DISCOVERED ) {
						alldiscovered_.add(newcube.toString());
					}
					numdiscovered_++;
				}
			}
		}

		long end = System.currentTimeMillis();
		time_ = end - start;
		return null;
	}

	/**
	 * For ordering within the priority queue.
	 */
	class FComparator implements Comparator<PQState> {
		@Override
		public int compare ( PQState obj, PQState other ) {
			double f = obj.f(), otherf = other.f();
			if ( f < otherf ) {
				return -1;
			} else if ( f > otherf ) {
				return 1;
			} else {
				if ( obj.getDepth() < other.getDepth() ) {
					return -1;
				} else if ( obj.getDepth() > other.getDepth() ) {
					return 1;
				} else {
					return 0;
				}
			}
		}
	}

}
