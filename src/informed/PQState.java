package informed;
import java.util.List;

import core.Cube;
import core.Move;
import solver.Solver;
import solver.State;
import solver.StateEvaluator;

/**
 * A node in the search space, for PQ/best-first-based algorithms.
 */
public class PQState extends State {

	private StateEvaluator evaluator_;
	private double f_; // f(n), cached for efficiency

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
	public PQState ( Cube cube, Solver.History history,
	                        StateEvaluator evaluator ) {
		super(cube,history);
		evaluator_ = evaluator;
		f_ = -1;
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
	public PQState ( Cube cube, int depth, Move move, State prev,
	                        List<String> path, StateEvaluator evaluator ) {
		super(cube,depth,move,prev,path);
		evaluator_ = evaluator;
		f_ = -1;
	}

	/**
	 * f(n)
	 * 
	 * @return f(n)
	 */
	public double f () {
		// lazy calculation
		if ( f_ < 0 ) {
			f_ = evaluator_.f(this);
		}
		return f_;
	}

	/**
	 * g(n)
	 * 
	 * @return g(n)
	 */
	public int g () {
		return getDepth();
	}

}
