package pruning;
import core.Move;
import solver.Pruner;
import solver.State;

/**
 * A trivial pruner which prunes nothing.
 */
public class TrivialPruner extends Pruner {

	@Override
	public boolean prune ( State current, Move next ) {
		return false;
	}

	@Override
	public String name () {
		return "trivial";
	}

}
