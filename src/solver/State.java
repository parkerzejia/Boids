package solver;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import core.Cube;
import core.Move;

/**
 * A node in the search space.
 */
public class State {

	private Cube cube_; // the current cube configuration
	private int depth_; // depth of this state in the search tree
	private State prev_; // the previous state
	private Move move_; // the move that resulted in this configuration, from the
	                    // previous state

	private List<String> path_; // nodes on this search path

	/**
	 * Initial state - root of the search tree. Depth is 0, and the previous state
	 * and move are both null.
	 * 
	 * @param cube
	 *          cube configuration
	 * @param history
	 *          which option for nodes to check for repeats
	 */

	public State ( Cube cube, Solver.History history ) {
		cube_ = cube;
		depth_ = 0;
		move_ = null;
		prev_ = null;
		if ( history == Solver.History.PATH ) {
			path_ = new ArrayList<String>();
			path_.add(cube.toString());
		} else {
			path_ = null;
		}
	}

	/**
	 * Create a new state.
	 * 
	 * @param cube
	 *          cube configuration
	 * @param depth
	 *          depth of this state in the search tree
	 * @param move
	 *          the move that resulted in this configuration, from the previous
	 *          state
	 * @param prev
	 *          the previous state
	 * @param path
	 *          solution path to this node
	 */

	public State ( Cube cube, int depth, Move move, State prev, List<String> path ) {
		cube_ = cube;
		depth_ = depth;
		move_ = move;
		prev_ = prev;
		if ( path == null ) {
			path_ = null;
		} else {
			path_ = new ArrayList<String>();
			path_.addAll(path);
			path_.add(cube.toString());
		}
	}

	public Cube getCube () {
		return cube_;
	}

	public int getDepth () {
		return depth_;
	}

	public Move getMove () {
		return move_;
	}

	public State getPrev () {
		return prev_;
	}

	public List<String> getHistory () {
		return path_;
	}

	/**
	 * Check if the cube configuration is in this state's history.
	 * 
	 * @param cube
	 *          the cube
	 * @return true if the cube configuration is in this state's history, false
	 *         otherwise
	 */
	public boolean inHistory ( Cube cube ) {
		if ( path_ == null ) {
			return false;
		} else {
			return path_.contains(cube.toString());
		}
	}

	/**
	 * Get the path leading to this state.
	 * 
	 * @return path leading to this state
	 */
	public List<Move> getSolutionPath () {
		LinkedList<Move> path = new LinkedList<Move>();
		for ( State current = this ; current.getMove() != null ; current =
		    current.getPrev() ) {
			path.addFirst(current.getMove());
		}
		return path;
	}

}