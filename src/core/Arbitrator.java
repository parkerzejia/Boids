package core;

import processing.core.PVector;

/**
 * The navigation module resolves steering forces from competing behaviors into
 * a single net steering force.
 */
public interface Arbitrator {

	/**
	 * Get the steering force for the specified boid.
	 * 
	 * @param boid
	 *          the boid
	 * @param world
	 *          the world containing the boid
	 * @return steering force for the specified boid
	 */
	public PVector getNetSteeringForce ( Boid boid, World world );
}