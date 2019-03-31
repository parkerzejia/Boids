package behavior;

import core.Behavior;
import core.Boid;
import core.World;
import processing.core.PVector;

/**
 * Alignment - steer to match velocity with neighbors.
 */
public class Alignment extends Behavior {

	/**
	 * Create an alignment behavior.
	 * 
	 * @param c
	 *          color to display steering vector (for debug mode)
	 */
	public Alignment ( int c ) {
		super(c);
	}

	/**
	 * Get the alignment steering force for the specified boid.
	 * 
	 * @param boid
	 *          the boid
	 * @param world
	 *          the world containing the boid
	 * @return the steering force for the specified boid
	 */
	public PVector getSteeringForce ( Boid boid, World world ) {
		PVector total = new PVector();
		int numneighbors = 0;

		for ( Boid other : world.getBoids() ) {
			if ( other != boid && boid.isNeighbor(other) ) {
				total.add(other.getVelocity());
				numneighbors++;
			}
		}

		if ( numneighbors == 0 ) {
			return total;
		} else {
			PVector desired = PVector.div(total,numneighbors);
			// steer to match desired velocity
			PVector steering = PVector.sub(desired,boid.getVelocity());
			steering.limit(boid.getMaxForce());

			world.debugVector(World.DEBUG_BEHAVIOR,boid.getPosition(),desired,
			                  50 / boid.getMaxSpeed(),color_,1);
			world.debugVector(World.DEBUG_BEHAVIOR,boid.getPosition(),steering,
			                  50 / boid.getMaxForce(),color_,3);

			return steering;
		}
	}

	/**
	 * Get the magnitude of the maximum steering force that can result from this
	 * behavior.
	 * 
	 * @param boid
	 *          the boid
	 * @param world
	 *          the world containing the boid
	 * @return the magnitude of the maximum steering force that can result from
	 *         this behavior
	 */
	public float getMaxSteeringForce ( Boid boid, World world ) {
		return 2 * boid.getMaxSpeed();
	}

}