package core;

import java.util.Random;

/**
 * An instance of a Rubik's cube problem, containing an instance of a cube and
 * the allowed moves.
 */
public class RubiksCube {

	private long instance_; // seed used for shuffling - identifies problem
	                        // instance

	private Cube cube_; // the cube to solve
	private Move[] moves_; // the allowed moves

	/**
	 * Create a problem instance with the specified components.
	 * 
	 * @param cube
	 *          the cube to solve
	 * @param moves
	 *          the allowed moves
	 */
	public RubiksCube ( Cube cube, Move[] moves ) {
		super();
		cube_ = cube;
		moves_ = moves;
		instance_ = -1;
	}

	@Override
	public RubiksCube clone () {
		return new RubiksCube(cube_.clone(),moves_);
	}

	@Override
	public String toString () {
		return cube_.toString();
	}

	public Cube getCube () {
		return cube_;
	}

	public Move[] getMoves () {
		return moves_;
	}

	/**
	 * Choose a random move from the collected of allowed moves. The move is only
	 * selected, not applied to the cube.
	 * 
	 * @return a random move
	 */
	private Move random ( Random random ) {
		return moves_[random.nextInt(moves_.length)];
	}

	/**
	 * Shuffle the cube by applying n randomly-selected moves.
	 * 
	 * @param n
	 *          the number of moves to make
	 */
	public void shuffle ( int n ) {
		Random random = new Random();
		shuffle(n,random.nextLong());
	}

	/**
	 * Shuffle the cube by applying n randomly-selected moves.
	 * 
	 * @param n
	 *          the number of moves to make
	 * @param seed
	 *          seed for random move generation - using the same seed value will
	 *          generate the same set of moves in the shuffle
	 */
	public void shuffle ( int n, long seed ) {
		instance_ = seed;
		Random random = new Random(seed);
		for ( int ctr = 0 ; ctr < n ; ctr++ ) {
			Move move = random(random);
			cube_.rotate(move);
		}
	}

	/**
	 * Get the seed used for the last shuffle.
	 * 
	 * @return the seed used for the last shuffle
	 */
	public long getInstance () {
		return instance_;
	}
}
