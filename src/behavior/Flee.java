package behavior;

import core.Boid;
import core.Target;
import core.World;
import processing.core.PVector;

/**
 * Flee - move away from the target at max speed.
 */
public class Flee extends TargetBehavior {

	/**
	 * Create a flee behavior with the specified target.
	 * 
	 * @param target
	 *          flee target
	 * @param c
	 *          color to display steering vector (for debug mode)
	 */
	public Flee ( Target target, int c ) {
		super(target,c);
	}

	/**
	 * Get the flee steering force for the specified boid.
	 * 
	 * @param boid
	 *          the boid
	 * @param world
	 *          the world containing the boid
	 * @return the steering force for the specified boid
	 */
	public PVector getSteeringForce ( Boid boid, World world ) {
		// desired velocity is to go away from target at max speed
		PVector desired = PVector.sub(boid.getPosition(),target_.getPosition());
		desired.setMag(boid.getMaxSpeed());

		// steer to match desired velocity
		PVector steering = PVector.sub(desired,boid.getVelocity());
		steering.limit(boid.getMaxForce());

		world.debugVector(World.DEBUG_BEHAVIOR,boid.getPosition(),desired,
		                  50 / boid.getMaxSpeed(),color_,1);
		world.debugVector(World.DEBUG_BEHAVIOR,boid.getPosition(),steering,
		                  50 / boid.getMaxForce(),color_,3);

		return steering;
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
