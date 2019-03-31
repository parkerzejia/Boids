package behavior;

import core.Behavior;
import core.Boid;
import core.World;
import processing.core.PVector;

/**
 * Cohesion - steer to centroid of neighbors.
 */
public class Cohesion extends Behavior {

	/**
	 * Create a cohesion behavior.
	 * 
	 * @param c
	 *          color to display steering vector (for debug mode)
	 */
	public Cohesion ( int c ) {
		super(c);
	}

	/**
	 * Get the cohesion steering force for the specified boid.
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
				total.add(other.getPosition());
				numneighbors++;
			}
		}

		if ( numneighbors == 0 ) {
			return total;
		} else {
			PVector centroid = PVector.div(total,numneighbors);

			// steer towards centroid
			PVector steering = PVector.sub(centroid,boid.getPosition());
			steering.limit(boid.getMaxForce());

			world.debugPoint(World.DEBUG_BEHAVIOR,centroid,color_,3);
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
		return boid.getNeighborRadius();
	}

}