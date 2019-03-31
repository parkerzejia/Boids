package informed.heuristics;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import core.Cube;
import core.Move;
import solver.Solver;
import solver.State;

/**
 * Construct one or more pattern DBs.
 * 
 * @author ssb
 */
public class PatternDBBuilder {

	private List<PatternDB> dbs_;

	public PatternDBBuilder () {
		dbs_ = new ArrayList<PatternDB>();
	}

	/**
	 * Add a DB to be constructed.
	 * 
	 * @param db
	 */
	public void addDB ( PatternDB db ) {
		dbs_.add(db);
	}

	/**
	 * Build the specified pattern DBs.
	 * 
	 * @param solved
	 *          the solved cube
	 * @param moves
	 *          the allowed moves
	 * @param limit
	 *          depth limit for exploring in order to generate pattern DB
	 */
	public void build ( Cube solved, Move[] moves, int limit ) {
		// do breadth-first search to build pattern db
		Queue<State> discovered = new LinkedList<State>();
		discovered.add(new State(solved,Solver.History.NONE));

		for ( ; !discovered.isEmpty() ; ) {
			State current = discovered.remove();
			for ( PatternDB db : dbs_ ) {
				db.store(current.getCube(),current.getDepth());
			}

			if ( current.getDepth() == limit ) {
				continue;
			}

			for ( Move move : moves ) {
				Cube newcube = current.getCube().clone();
				newcube.rotate(move);
				discovered.add(new State(newcube,current.getDepth() + 1,move,current,
				                         current.getHistory()));
			}
		}
	}

}
