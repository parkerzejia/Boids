package informed.heuristics;
import solver.Heuristic;
import solver.State;

/**
 * Evaluate f(n) using a pattern database.
 */

public class PatternDBHeuristic implements Heuristic {

	private PatternDB db_;

	/**
	 * Construct a heuristic using the specific pattern DB.
	 * 
	 * @param db
	 */
	public PatternDBHeuristic ( PatternDB db ) {
		db_ = db;
	}

	@Override
	public double h ( State b ) {
		return db_.h(b.getCube());
	}

	@Override
	public String name () {
		return db_.name();
	}

}
