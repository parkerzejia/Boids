package solver;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import core.Cube;
import core.Move;
import core.RubiksCube;

/**
 * Implementation of a search algorithm.
 */
public abstract class Solver {

	/**
	 * Which nodes to check for repeats - check nothing, check nodes along the
	 * search path, check all previously-discovered nodes.
	 */
	public enum History {
		NONE("none"), PATH("path"), DISCOVERED("discovered");
		private String name_;

		private History ( String name ) {
			name_ = name;
		}

		@Override
		public String toString () {
			return name_;
		}
	}

	// stats computed by the solver
	protected int maxdepth_, soldepth_, numdiscovered_, maxdiscovered_;
	protected long time_;
	protected Set<String> alldiscovered_;

	// algorithm configuration options
	protected History history_;
	protected Pruner pruner_;

	protected boolean shutdown_;

	/**
	 * Create a solver.
	 * 
	 * @param history
	 *          nodes to check for repeats
	 * @param pruner
	 *          the pruner to use
	 */
	public Solver ( History history, Pruner pruner ) {
		history_ = history;
		pruner_ = pruner;
		alldiscovered_ = new HashSet<String>();
		shutdown_ = false;
	}

	/**
	 * Print out statistics about the last solve. Only meaningful if called after
	 * solve().
	 */
	public void print () {
		if ( shutdown_ ) {
			System.out.println("[" + name() + "-" + history_ + "-" + pruner_.name()
			    + "] shutdown");
		} else {
			System.out.println("[" + name() + "-" + history_ + "-" + pruner_.name()
			    + "] time / solution depth / max depth / num discovered / max discovered: "
			    + time_ + " / " + soldepth_ + " / " + maxdepth_ + " / "
			    + numdiscovered_ + " / " + maxdiscovered_);
		}
	}

	protected void init () {
		maxdepth_ = 0;
		soldepth_ = -1;
		numdiscovered_ = 0;
		maxdiscovered_ = 0;
		time_ = 0;
		alldiscovered_.clear();
		shutdown_ = false;
	}

	/**
	 * Solve the problem, returning the solution.
	 * 
	 * @param problem
	 *          problem to solve
	 * @return the moves to make to solve the puzzle, in order
	 */
	public abstract List<Move> solve ( RubiksCube problem );

	/**
	 * Descriptive name for the search algorithm, for display purposes.
	 */
	public abstract String name ();

	/**
	 * Determine if node should be pruned because it is a repeat.
	 * 
	 * @param state
	 *          current state
	 * @param cube
	 *          cube configuration being checked
	 * @return true if cube should be pruned (because it has already been visited)
	 */
	public boolean pruneHistory ( State state, Cube cube ) {
		switch ( history_ ) {
		case PATH:
			if ( state.inHistory(cube) ) {
				return true;
			}
			break;
		case DISCOVERED:
			if ( alldiscovered_.contains(cube.toString()) ) {
				return true;
			}
			break;
		}
		return false;
	}

	/**
	 * Request that the solver stop. Statistics will be invalid if the solver is
	 * stopped.
	 */
	public void stop () {
		shutdown_ = true;
	}
}
