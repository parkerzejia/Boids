package informed;
import java.util.List;

import core.Cube;
import core.Move;
import solver.Solver;
import solver.State;
import solver.StateEvaluator;

/**
 * State for RBFS.
 */
public class RBFSState extends PQState {

	// stored F value
	private double F_;

	/**
	 * Create a new state.
	 * 
	 * @param cube
	 *          current cube configuration
	 * @param history
	 *          what nodes to check for repeats
	 * @param evaluator
	 *          evaluation function
	 */

	public RBFSState ( Cube cube, Solver.History history, StateEvaluator evaluator ) {
		super(cube,history,evaluator);
		F_ = f();
	}

	/**
	 * Create a new state.
	 * 
	 * @param cube
	 *          current cube configuration
	 * @param depth
	 *          depth of this node
	 * @param move
	 *          the move leading to this state
	 * @param prev
	 *          the previous state
	 * @param path
	 *          the path to this state (use null if the path should not be stored)
	 * @param evaluator
	 *          evaluation function
	 */
	public RBFSState ( Cube cube, int depth, Move move, State prev,
	                   List<String> path, StateEvaluator evaluator ) {
		super(cube,depth,move,prev,path,evaluator);
		F_ = f();
	}

	/**
	 * Stored F value.
	 */
	public double F () {
		return F_;
	}

	/**
	 * Set stored F value.
	 */
	public void setF ( double F ) {
		F_ = F;
	}

}
