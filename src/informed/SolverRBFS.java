package informed;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import core.Cube;
import core.Move;
import core.RubiksCube;
import solver.Heuristic;
import solver.Pruner;

/**
 * RBFS search.
 */
public class SolverRBFS extends SolverPQ {

	private int curdiscovered_;

	/**
	 * Create an RBFS solver.
	 * 
	 * @param history
	 *          which option for nodes to check for repeats
	 * @param pruner
	 *          pruner to use
	 * @param heuristic
	 *          heuristic to use
	 */
	public SolverRBFS ( History history, Pruner pruner, Heuristic heuristic ) {
		super(history,pruner,new AStarEvaluator(heuristic));
	}

	@Override
	protected void init () {
		super.init();
		curdiscovered_ = 0;
	}

	@Override
	public List<Move> solve ( RubiksCube problem ) {
		long start = System.currentTimeMillis();
		init();

		numdiscovered_++;
		maxdiscovered_++;
		if ( history_ == History.DISCOVERED ) {
			alldiscovered_.add(problem.getCube().toString());
		}
		RBFSState result =
		    solve(problem,new RBFSState(problem.getCube(),history_,evaluator_),
		          Double.MAX_VALUE);
		if ( result == null ) { return null; }

		if ( result.getCube().isSolved() ) {
			soldepth_ = result.getDepth();
			long end = System.currentTimeMillis();
			time_ = end - start;
			return result.getSolutionPath();
		} else {
			soldepth_ = -1;
			long end = System.currentTimeMillis();
			time_ = end - start;
			return null;
		}
	}

	private RBFSState solve ( RubiksCube problem, RBFSState state, double bound ) {
		if ( shutdown_ ) { return null; }

		maxdepth_ = Math.max(maxdepth_,state.getDepth());

		if ( state.f() > bound ) {
			return state;
		}
		if ( state.getCube().isSolved() ) {
			return state;
		}

		PriorityQueue<RBFSState> children =
		    new PriorityQueue<RBFSState>(1,new FComparator());
		for ( Move move : problem.getMoves() ) {
			if ( pruner_.prune(state,move) ) {
				continue;
			}
			Cube newcube = state.getCube().clone();
			newcube.rotate(move);
			if ( !pruneHistory(state,newcube) ) {
				RBFSState child =
				    new RBFSState(newcube,state.getDepth() + 1,move,state,
				                  state.getHistory(),evaluator_);
				children.add(child);
				if ( history_ == History.DISCOVERED ) {
					alldiscovered_.add(newcube.toString());
				}
				numdiscovered_++;
			}
		}
		curdiscovered_ += children.size();
		maxdiscovered_ = Math.max(maxdiscovered_,curdiscovered_ + state.getDepth());

		for ( ; !children.isEmpty() ; ) {
			RBFSState best = children.remove();
			if ( best.F() > bound ) {
				curdiscovered_ -= children.size();
				return best;
			}

			RBFSState result =
			    solve(problem,best,Math.min(bound,children.peek().F()));
			if ( result == null ) { return null; }
			
			if ( result.getCube().isSolved() ) {
				curdiscovered_ -= children.size();
				return result;
			}
			best.setF(result.F());
			children.add(best);
		}

		// terminal node
		curdiscovered_ -= children.size();
		return state;
	}

	@Override
	public String name () {
		return "RBFS-" + evaluator_.name();
	}

	class FComparator implements Comparator<RBFSState> {
		@Override
		public int compare ( RBFSState obj, RBFSState other ) {
			double f = obj.F(), otherf = other.F();
			if ( f < otherf ) {
				return -1;
			} else if ( f > otherf ) {
				return 1;
			} else {
				return 0;
			}
		}
	}

}
