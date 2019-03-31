package informed.heuristics;
import java.util.HashMap;
import java.util.Map;

import core.Cube;
import core.Cubie;

/**
 * Pattern database - stores number of moves to solve subproblem.
 */
public class PatternDB {

	private int[] pattern_; // ids of cubies in the subproblem
	private Map<String,Integer> db_;
	private int max_; // value to return if pattern is not in DB

	/**
	 * Create a pattern DB for the specified pattern.
	 * 
	 * @param pattern
	 *          indexes of cubie positions included in pattern
	 */
	public PatternDB ( int[] pattern ) {
		pattern_ = pattern;
		db_ = new HashMap<String,Integer>();
		max_ = 0;
	}

	/**
	 * Compute the pattern for the cube.
	 * 
	 * @param cube
	 * @return
	 */
	private String getPattern ( Cube cube ) {
		String pattern = "-";
		for ( int pos = 0 ; pos < cube.numCubies() ; pos++ ) {
			Cubie cubie = cube.getCubie(pos);
			for ( int id : pattern_ ) {
				if ( cubie.id() == id ) {
					// add to pattern string
					pattern +=
					    ("" + cubie.updown()) + ("" + cubie.frontback())
					        + ("" + cubie.leftright());
					break;
				}
			}
			pattern += "-";
		}
		return pattern;
	}

	/**
	 * Store a result in the DB.
	 * 
	 * @param cube
	 *          configuration whose result is being stored
	 * @param moves
	 *          min number of moves to solve puzzle from this point
	 */
	public void store ( Cube cube, int moves ) {
		String pattern = getPattern(cube);
		if ( !db_.containsKey(pattern) || moves < db_.get(pattern) ) {
	//		System.out.println("storing " + pattern + " " + moves);
			db_.put(pattern,moves);
			max_ = Math.max(max_,moves);
		}
	}

	/**
	 * Get h(n) for the specified cube configuration.
	 * 
	 * @param cube
	 *          cube configuration
	 * @return h(n)
	 */
	public int h ( Cube cube ) {
		String pattern = getPattern(cube);
		return (db_.containsKey(pattern) ? db_.get(pattern) : max_);
	}

	/**
	 * Heuristic name.
	 * 
	 * @return
	 */
	public String name () {
		String name = "";
		for ( int id : pattern_ ) {
			name += "-" + id;
		}
		return name.substring(1);
	}

}
