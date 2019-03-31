package core;

import processing.core.PVector;

/**
 * A boid brain takes in information from the environment and produces an action
 * in the form of a net steering force.
 */
public interface Brain {

	/**
	 * Get the net steering force for the specified boid.
	 * 
	 * @param boid
	 *          the boid
	 * @param world
	 *          the world containing the boid
	 * @return net steering force for the specified boid
	 */
	public PVector getNetSteeringForce ( Boid boid, World world );
}