package pruning;
import core.Move;
import solver.Pruner;
import solver.State;

/**
 * A pruner which prunes nodes resulting from a repeated move.
 */
public class DoublePruner extends Pruner {

	@Override
	public boolean prune ( State current, Move next ) {
		return (current.getMove() == next);
	}

	@Override
	public String name () {
		return "double";
	}

}
