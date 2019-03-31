package core;

/**
 * Representation of a Rubik's cube. This abstract class allows different sizes
 * of cubes to be supported.
 */
public abstract class Cube {

	/**
	 * Is the cube solved?
	 * 
	 * @return true if the cube is solved, false otherwise
	 */
	public abstract boolean isSolved ();

	/**
	 * Apply the move to the cube's current configuration.
	 * 
	 * @param move
	 *          the move to apply
	 */
	public abstract void rotate ( Move move );

	@Override
	public abstract Cube clone ();

	/**
	 * Number of cubies in the cube.
	 * 
	 * @return number of cubies in the cube
	 */
	public abstract int numCubies ();

	/**
	 * Get the cubie in the ith position in the current cube configuration.
	 * 
	 * @param i
	 * @return ith cubie
	 */
	public abstract Cubie getCubie ( int i );

	/**
	 * Get the cubie in the ith position of a solved cube of this type.
	 * 
	 * @param i
	 * @return ith cubie in solved cube
	 */
	public abstract Cubie getSolvedCubie ( int i );

	/**
	 * Get the number of moves needed to move the cubie in the source position to
	 * the target position.
	 * 
	 * @param source
	 * @param target
	 * @return number of moves needed to move the cubie in the source position to
	 *         the target position
	 */
	public abstract int getNumMoves ( int source, int target );

}
