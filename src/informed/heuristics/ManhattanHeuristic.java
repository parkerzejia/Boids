package informed.heuristics;
import core.Cube;
import solver.Heuristic;
import solver.State;

/**
 * Heuristic: value is sum of manhattan distance for each cubie, divided by 4
 * because on move can put up to four corners in order.
 * 
 * @author ssb
 */
public class ManhattanHeuristic implements Heuristic {

	@Override
	public double h ( State b ) {
		// sum # of moves to put each cubie in right position (orientation is
		// ignored)
		Cube cube = b.getCube();
		int sum = 0;
		for ( int pos = 0 ; pos < cube.numCubies() ; pos++ ) {
			sum += cube.getNumMoves(pos,cube.getCubie(pos).id());
		}
		return sum / 4.0; // four cubies into position with one move
	}

	@Override
	public String name () {
		return "manhattan";
	}

}
