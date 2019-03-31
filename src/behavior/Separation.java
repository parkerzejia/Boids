package behavior;

import core.Behavior;
import core.Boid;
import core.World;
import processing.core.PVector;

/**
 * Separation - steer away from neighbors. (Steering force is the sum of the
 * repulsive forces from the neighbors.)
 */
public class Separation extends Behavior {

	/**
	 * Create a separation behavior.
	 * 
	 * @param c
	 *          color to display steering vector (for debug mode)
	 */
	public Separation ( int c ) {
		super(c);
	}

	/**
	 * Get the separation steering force for the specified boid.
	 * 
	 * @param boid
	 *          the boid
	 * @param world
	 *          the world containing the boid
	 * @return the steering force for the specified boid
	 */
	public PVector getSteeringForce ( Boid boid, World world ) {
		PVector total = new PVector();

		for ( Boid other : world.getBoids() ) {
			if ( other != boid && boid.isNeighbor(other) ) {//if there is somehting closer
				PVector repulsion = PVector.sub(boid.getPosition(),other.getPosition());
				if ( repulsion.mag() > .001 ) {
					repulsion.setMag(1.0f / repulsion.mag());
				}
				total.add(repulsion);
			}
		}

		total.limit(boid.getMaxForce());

		world.debugVector(World.DEBUG_BEHAVIOR,boid.getPosition(),total,
		                  50 / boid.getMaxForce(),color_,3);

		return total;
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