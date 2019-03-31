import java.util.Random;

import core.Cube222;
import core.Move;
import core.RubiksCube;
import informed.SolverAStar;
import informed.SolverGreedy;
import informed.SolverIDAStar;
import informed.SolverRBFS;
import informed.heuristics.CompoundHeuristic;
import informed.heuristics.ManhattanHeuristic;
import informed.heuristics.PatternDB;
import informed.heuristics.PatternDBBuilder;
import informed.heuristics.PatternDBHeuristic;
import informed.heuristics.WrongCubiesHeuristic;
import pruning.DoublePruner;
import pruning.TrivialPruner;
import solver.Heuristic;
import solver.Pruner;
import solver.Solver;
import solver.SolverRunnable;

public class MainInformed {

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

		// pruners
		Pruner nopruner = new TrivialPruner();
		Pruner pruner = new DoublePruner();

		// heuristics
		Heuristic wrong = new WrongCubiesHeuristic(); // # wrong cubies
		Heuristic manhattan = new ManhattanHeuristic(); // sum of manhattan
		                                                // distances

		System.out.println("building pattern database");
		PatternDBBuilder builder = new PatternDBBuilder(); // opposite corners 0 and
		                                                   // 6 are the subproblem
		PatternDB oppcornersdb = new PatternDB(new int[] { 0, 6 });
		builder.addDB(oppcornersdb);
		builder.build(new Cube222(),QUARTER_MOVES,6);
		Heuristic oppcorners = new PatternDBHeuristic(oppcornersdb);
		System.out.println("pattern database complete");

		// take max of the other heuristics
		CompoundHeuristic combo = new CompoundHeuristic("combo");
		combo.add(wrong);
		combo.add(manhattan);
		// combo.add(oppcorners);

		// search algorithm / pruner combinations to try for each problem instance
		// generated
		// TODO: not all combinations are practical or make sense - remove those!
		Solver[] solver = {
		                    // informed search
		                    new SolverGreedy(Solver.History.NONE,nopruner,wrong),
		                    new SolverGreedy(Solver.History.NONE,nopruner,
		                                     manhattan),
		                    new SolverGreedy(Solver.History.NONE,nopruner,
		                                     oppcorners),
		                    new SolverGreedy(Solver.History.NONE,nopruner,combo),
		                    new SolverGreedy(Solver.History.PATH,nopruner,wrong),
		                    new SolverGreedy(Solver.History.PATH,nopruner,
		                                     manhattan),
		                    new SolverGreedy(Solver.History.PATH,nopruner,
		                                     oppcorners),
		                    new SolverGreedy(Solver.History.PATH,nopruner,combo),
		                    new SolverGreedy(Solver.History.DISCOVERED,nopruner,
		                                     wrong),
		                    new SolverGreedy(Solver.History.DISCOVERED,nopruner,
		                                     manhattan),
		                    new SolverGreedy(Solver.History.DISCOVERED,nopruner,
		                                     oppcorners),
		                    new SolverGreedy(Solver.History.DISCOVERED,nopruner,
		                                     combo),
		                    new SolverGreedy(Solver.History.NONE,pruner,wrong),
		                    new SolverGreedy(Solver.History.NONE,pruner,manhattan),
		                    new SolverGreedy(Solver.History.NONE,pruner,oppcorners),
		                    new SolverGreedy(Solver.History.NONE,pruner,combo),
		                    new SolverGreedy(Solver.History.PATH,pruner,wrong),
		                    new SolverGreedy(Solver.History.PATH,pruner,manhattan),
		                    new SolverGreedy(Solver.History.PATH,pruner,oppcorners),
		                    new SolverGreedy(Solver.History.PATH,pruner,combo),
		                    new SolverGreedy(Solver.History.DISCOVERED,pruner,
		                                     wrong),
		                    new SolverGreedy(Solver.History.DISCOVERED,pruner,
		                                     manhattan),
		                    new SolverGreedy(Solver.History.DISCOVERED,pruner,
		                                     oppcorners),
		                    new SolverGreedy(Solver.History.DISCOVERED,pruner,
		                                     combo),
		                    new SolverAStar(Solver.History.NONE,nopruner,wrong),
		                    new SolverAStar(Solver.History.NONE,nopruner,manhattan),
		                    new SolverAStar(Solver.History.NONE,nopruner,
		                                    oppcorners),
		                    new SolverAStar(Solver.History.NONE,nopruner,combo),
		                    new SolverAStar(Solver.History.PATH,nopruner,wrong),
		                    new SolverAStar(Solver.History.PATH,nopruner,manhattan),
		                    new SolverAStar(Solver.History.PATH,nopruner,
		                                    oppcorners),
		                    new SolverAStar(Solver.History.PATH,nopruner,combo),
		                    new SolverAStar(Solver.History.DISCOVERED,nopruner,
		                                    wrong),
		                    new SolverAStar(Solver.History.DISCOVERED,nopruner,
		                                    manhattan),
		                    new SolverAStar(Solver.History.DISCOVERED,nopruner,
		                                    oppcorners),
		                    new SolverAStar(Solver.History.DISCOVERED,nopruner,
		                                    combo),
		                    new SolverAStar(Solver.History.NONE,pruner,wrong),
		                    new SolverAStar(Solver.History.NONE,pruner,manhattan),
		                    new SolverAStar(Solver.History.NONE,pruner,oppcorners),
		                    new SolverAStar(Solver.History.NONE,pruner,combo),
		                    new SolverAStar(Solver.History.PATH,pruner,wrong),
		                    new SolverAStar(Solver.History.PATH,pruner,manhattan),
		                    new SolverAStar(Solver.History.PATH,pruner,oppcorners),
		                    new SolverAStar(Solver.History.PATH,pruner,combo),
		                    new SolverAStar(Solver.History.DISCOVERED,pruner,wrong),
		                    new SolverAStar(Solver.History.DISCOVERED,pruner,
		                                    manhattan),
		                    new SolverAStar(Solver.History.DISCOVERED,pruner,
		                                    oppcorners),
		                    new SolverAStar(Solver.History.DISCOVERED,pruner,combo),
		                    new SolverIDAStar(Solver.History.NONE,nopruner,wrong),
		                    new SolverIDAStar(Solver.History.NONE,nopruner,
		                                      manhattan),
		                    new SolverIDAStar(Solver.History.NONE,nopruner,
		                                      oppcorners),
		                    new SolverIDAStar(Solver.History.NONE,nopruner,combo),
		                    new SolverIDAStar(Solver.History.PATH,nopruner,wrong),
		                    new SolverIDAStar(Solver.History.PATH,nopruner,
		                                      manhattan),
		                    new SolverIDAStar(Solver.History.PATH,nopruner,
		                                      oppcorners),
		                    new SolverIDAStar(Solver.History.PATH,nopruner,combo),
		                    new SolverIDAStar(Solver.History.DISCOVERED,nopruner,
		                                      wrong),
		                    new SolverIDAStar(Solver.History.DISCOVERED,nopruner,
		                                      manhattan),
		                    new SolverIDAStar(Solver.History.DISCOVERED,nopruner,
		                                      oppcorners),
		                    new SolverIDAStar(Solver.History.DISCOVERED,nopruner,
		                                      combo),
		                    new SolverIDAStar(Solver.History.NONE,pruner,wrong),
		                    new SolverIDAStar(Solver.History.NONE,pruner,manhattan),
		                    new SolverIDAStar(Solver.History.NONE,pruner,
		                                      oppcorners),
		                    new SolverIDAStar(Solver.History.NONE,pruner,combo),
		                    new SolverIDAStar(Solver.History.PATH,pruner,wrong),
		                    new SolverIDAStar(Solver.History.PATH,pruner,manhattan),
		                    new SolverIDAStar(Solver.History.PATH,pruner,
		                                      oppcorners),
		                    new SolverIDAStar(Solver.History.PATH,pruner,combo),
		                    new SolverIDAStar(Solver.History.DISCOVERED,pruner,
		                                      wrong),
		                    new SolverIDAStar(Solver.History.DISCOVERED,pruner,
		                                      manhattan),
		                    new SolverIDAStar(Solver.History.DISCOVERED,pruner,
		                                      oppcorners),
		                    new SolverIDAStar(Solver.History.DISCOVERED,pruner,
		                                      combo),
		                    new SolverRBFS(Solver.History.NONE,nopruner,wrong),
		                    new SolverRBFS(Solver.History.NONE,nopruner,manhattan),
		                    new SolverRBFS(Solver.History.NONE,nopruner,oppcorners),
		                    new SolverRBFS(Solver.History.NONE,nopruner,combo),
		                    new SolverRBFS(Solver.History.PATH,nopruner,wrong),
		                    new SolverRBFS(Solver.History.PATH,nopruner,manhattan),
		                    new SolverRBFS(Solver.History.PATH,nopruner,oppcorners),
		                    new SolverRBFS(Solver.History.PATH,nopruner,combo),
		                    new SolverRBFS(Solver.History.DISCOVERED,nopruner,
		                                   wrong),
		                    new SolverRBFS(Solver.History.DISCOVERED,nopruner,
		                                   manhattan),
		                    new SolverRBFS(Solver.History.DISCOVERED,nopruner,
		                                   oppcorners),
		                    new SolverRBFS(Solver.History.DISCOVERED,nopruner,
		                                   combo),
		                    new SolverRBFS(Solver.History.NONE,pruner,wrong),
		                    new SolverRBFS(Solver.History.NONE,pruner,manhattan),
		                    new SolverRBFS(Solver.History.NONE,pruner,oppcorners),
		                    new SolverRBFS(Solver.History.NONE,pruner,combo),
		                    new SolverRBFS(Solver.History.PATH,pruner,wrong),
		                    new SolverRBFS(Solver.History.PATH,pruner,manhattan),
		                    new SolverRBFS(Solver.History.PATH,pruner,oppcorners),
		                    new SolverRBFS(Solver.History.PATH,pruner,combo),
		                    new SolverRBFS(Solver.History.DISCOVERED,pruner,wrong),
		                    new SolverRBFS(Solver.History.DISCOVERED,pruner,
		                                   manhattan),
		                    new SolverRBFS(Solver.History.DISCOVERED,pruner,
		                                   oppcorners),
		                    new SolverRBFS(Solver.History.DISCOVERED,pruner,combo)

		};

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
				// algorithm/pruner combos - this allows direct comparisons for specific
				// problem instances
				for ( int ctr = 0 ; ctr < solver.length ; ctr++ ) {

					// generate a problem instance with the specified cube and allowed
					// moves
					RubiksCube cube = new RubiksCube(new Cube222(),QUARTER_MOVES);
					// shuffle the cube
					cube.shuffle(maxsize,seeds[rep]);

					// uncomment to print the problem instance being solved
					// System.out.println(cube);

					// solve!
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
					System.out.print(maxsize+" "+cube.getInstance() + " ");
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
