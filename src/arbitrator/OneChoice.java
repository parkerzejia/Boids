package arbitrator;

import core.Arbitrator;
import core.Behavior;
import core.Boid;
import core.World;
import processing.core.PVector;

/**
 * A single behavior.
 */

public class OneChoice implements Arbitrator {

	private Behavior behavior_;

	/**
	 * A single behavior.
	 */
	public OneChoice ( Behavior behavior ) {
		behavior_ = behavior;
	}

	/**
	 * Get the net steering force for the specified boid. The magnitude of the
	 * force returned is limited by the boid's max force.
	 * 
	 * @param boid
	 *          the boid
	 * @param world
	 *          the world containing the boid
	 */
	public PVector getNetSteeringForce ( Boid boid, World world ) {
		PVector steering = behavior_.getSteeringForce(boid,world);
		steering.limit(boid.getMaxForce());
		world.debugVector(World.DEBUG_ARBITRATOR,boid.getPosition(),steering,
		                  50 / boid.getMaxForce(),behavior_.getColor(),2);
		return steering;
	}
}
