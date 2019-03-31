package solver;

import core.Move;

/**
 * A pruner implements a condition for deciding whether or not to prune a node.
 */
public abstract class Pruner {

	/**
	 * Prune the next branch?
	 * 
	 * @param current
	 *          the current configuration
	 * @param next
	 *          the move to be made next
	 * @return true if this branch should be pruned (i.e. the configuration
	 *         resulting from applying 'next' to 'current' should not be added to
	 *         the discovered list), false otherwise
	 */
	public abstract boolean prune ( State current, Move next );

	/**
	 * A descriptive name for the pruner, for display purposes.
	 */
	public abstract String name ();

}
