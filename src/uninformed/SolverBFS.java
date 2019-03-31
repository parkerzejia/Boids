package uninformed;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import core.Cube;
import core.Move;
import core.RubiksCube;
import solver.Pruner;
import solver.Solver;
import solver.State;

/**
 * Breadth-first search.
 */
public class SolverBFS extends Solver {

	/**
	 * Create a solver.
	 * 
	 * @param history
	 *          which option for nodes to check for repeats
	 * @param pruner
	 *          pruner to use
	 */
	public SolverBFS ( History history, Pruner pruner ) {
		super(history,pruner);
	}

	@Override
	public List<Move> solve ( RubiksCube problem ) {
		long start = System.currentTimeMillis();
		init();

		Queue<State> discovered = new LinkedList<State>();
		discovered.add(new State(problem.getCube(),history_));
		if ( history_ == History.DISCOVERED ) {
			alldiscovered_.add(problem.getCube().toString());
		}
		numdiscovered_++;

		int[] counts = new int[50];
		for ( int ctr = 0 ; ctr < counts.length ; ctr++ ) {
			counts[ctr] = 0;
		}

		for ( ; !discovered.isEmpty() ; ) {
			if ( shutdown_ ) { return null; }
			
			maxdiscovered_ = Math.max(maxdiscovered_,discovered.size());

			State current = discovered.remove();
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
					discovered.add(new State(newcube,current.getDepth() + 1,move,current,
					                         current.getHistory()));
					counts[current.getDepth() + 1]++;
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

	@Override
	public String name () {
		return "bfs";
	}

}
