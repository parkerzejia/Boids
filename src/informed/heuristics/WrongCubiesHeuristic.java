package informed.heuristics;
import core.Cube;
import solver.Heuristic;
import solver.State;

/**
 * Heuristic which simply counts the number of incorrect cubies.
 */
public class WrongCubiesHeuristic implements Heuristic {

	@Override
	public double h ( State b ) {
		int wrongcount = 0;
		Cube cube = b.getCube();
		for ( int ctr = 0 ; ctr < cube.numCubies() ; ctr++ ) {
			if ( !cube.getCubie(ctr).equals(cube.getSolvedCubie(ctr)) ) {
				wrongcount++;
			}
		}
		// divide by 4 because one turn can put up to four wrong cubies in the right
		// place
		return wrongcount / 4.0;
	}

	@Override
	public String name () {
		return "wrongcubies";
	}

}
