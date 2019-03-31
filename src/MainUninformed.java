import java.util.Random;

import core.Cube222;
import core.Move;
import core.RubiksCube;
import pruning.TrivialPruner;
import solver.Pruner;
import solver.Solver;
import solver.SolverRunnable;
import uninformed.SolverBFS;
import uninformed.SolverDFS;
import uninformed.SolverDLS;
import uninformed.SolverIDS;

public class MainUninformed {

	public static void main ( String[] args ) {

		// set of all quarter-turn moves
		final Move[] QUARTER_MOVES =
		    { Move.LEFTUP, Move.LEFTDOWN, Move.RIGHTUP, Move.RIGHTDOWN,
		      Move.BOTTOMLEFT, Move.BOTTOMRIGHT, Move.TOPLEFT, Move.TOPRIGHT };

		// set of all quarter-turn and half-turn moves
		final Move[] HALF_MOVES =
		    { Move.LEFTUP, Move.LEFTDOWN, Move.RIGHTUP, Move.RIGHTDOWN,
		      Move.BOTTOMLEFT, Move.BOTTOMRIGHT, Move.TOPLEFT, Move.TOPRIGHT,
		      Move.LEFTHALF, Move.RIGHTHALF, Move.TOPHALF, Move.BOTTOMHALF };

		// number of moves to make when shuffling the cube
		// also then the upper bound on the length of the solution path for that
		// cube
		final int MAXPROBSIZE = 10;

		// number of trials to run - this many problem instances are generated
		final int NUMREPS = 20;

		// how long to wait for a trial to end (ms)
		final int WAITPERIOD = 30 * 1000;

		// pruner
		Pruner pruner = new TrivialPruner();

		// search algorithm / pruner combinations to try for each problem instance
		// generated
		// TODO: not all combinations are practical or make sense - remove those!
		Solver[] solver = {
		                    // uninformed search
		                    new SolverDFS(Solver.History.NONE,pruner),
		                    new SolverDFS(Solver.History.PATH,pruner),
		                    new SolverDFS(Solver.History.DISCOVERED,pruner),

		                    new SolverDLS(MAXPROBSIZE,Solver.History.NONE,pruner),
		                    new SolverDLS(MAXPROBSIZE,Solver.History.PATH,pruner),
		                    new SolverDLS(MAXPROBSIZE,Solver.History.DISCOVERED,
		                                  pruner),

		                    new SolverIDS(Solver.History.NONE,pruner),
		                    new SolverIDS(Solver.History.PATH,pruner),
		                    new SolverIDS(Solver.History.DISCOVERED,pruner),

		                    new SolverBFS(Solver.History.NONE,pruner),
		                    new SolverBFS(Solver.History.PATH,pruner),
		                    new SolverBFS(Solver.History.DISCOVERED,pruner) };

		// generate seeds for cube shuffling so each algorithm is run on the
		// same set of problems
		long[] seeds = new long[NUMREPS];
		Random random = new Random();
		for ( int rep = 0 ; rep < NUMREPS ; rep++ ) {
			seeds[rep] = Math.abs(random.nextLong());
		}

		// run!
		for ( int maxsize = 1 ; maxsize < MAXPROBSIZE ; maxsize++ ) {
			for ( int rep = 0 ; rep < NUMREPS ; rep++ ) {
				// solve the problem instance using each of the search algorithm /
				// pruner combinations
				// note that the same problem instance is solved by each of the
				// algorithm/pruner combos - this allows direct comparisons for
				// specific problem instances
				for ( int ctr = 0 ; ctr < solver.length ; ctr++ ) {
					// generate a problem instance with the specified cube and allowed
					// moves
					RubiksCube cube = new RubiksCube(new Cube222(),QUARTER_MOVES);
					// shuffle the cube
					cube.shuffle(maxsize,seeds[rep]);

					// uncomment to print the problem instance being solved
					// System.out.println(cube);

					// solve!
					// List<Move> solution = solver[ctr].solve(cube);
					SolverRunnable runnable = new SolverRunnable(solver[ctr],cube);
					Thread thread = new Thread(runnable);
					thread.start();
					try {
						thread.join(WAITPERIOD);
					} catch ( InterruptedException e ) {}
					// if thread exceeds the allotted time, shut it down
					if ( thread.isAlive() ) {
						runnable.shutdown();
					}
					// wait for thread to actually finish
					for ( ; thread.isAlive() ; ) {
						try {
							thread.join();
						} catch ( InterruptedException e ) {}
					}
					// print info about the solving process
					System.out.print(maxsize + " " + cube.getInstance() + " ");
					solver[ctr].print();
					// uncomment to print the series of moves in the solution
					// if ( solution != null ) {
					// for ( Move move : solution ) { System.out.println(move); }
					// }
				}
			}
		}
	}
}
