package informed.heuristics;
import java.util.ArrayList;
import java.util.List;

import solver.Heuristic;
import solver.State;

/**
 * Heuristic whose value is the maximum of the component heuristics.
 */
public class CompoundHeuristic implements Heuristic {

	private List<Heuristic> heuristics_;
	private String name_;

	/**
	 * Create a compound heuristic with no components.
	 * 
	 * @param name
	 *          this heuristic's name
	 */
	public CompoundHeuristic ( String name ) {
		heuristics_ = new ArrayList<Heuristic>();
		name_ = name;
	}

	/**
	 * Add a component heuristic.
	 * 
	 * @param heuristic
	 *          component heuristic
	 */
	public void add ( Heuristic heuristic ) {
		heuristics_.add(heuristic);
	}

	@Override
	public double h ( State b ) {
		double maxh = 0;
		for ( Heuristic heuristic : heuristics_ ) {
			maxh = Math.max(maxh,heuristic.h(b));
		}
		return maxh;
	}

	@Override
	public String name () {
		return name_;
	}

}
