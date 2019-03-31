package solver;

/**
 * Heuristic function.
 * 
 * @author ssb
 */
public interface Heuristic {

	/**
	 * Compute h(n).
	 * 
	 * @param state
	 * @return h(n)
	 */
	public double h ( State state );

	/**
	 * Heuristic's name.
	 */
	public String name ();

}
