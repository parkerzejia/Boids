package behavior;

import core.Boid;
import core.Target;
import core.World;
import processing.core.PVector;

/**
 * Seek - match position with a target, with the boid traveling at max speed.
 */
public class Seek extends TargetBehavior {

	/**
	 * Create a seek behavior with the specified target.
	 * 
	 * @param target
	 *          seek target
	 * @param c
	 *          color to display steering vector (for debug mode)
	 */
	public Seek ( Target target, int c ) {
		super(target,c);
	}



	/**
	 * Get the seek steering force for the specified boid.
	 * 
	 * @param boid
	 *          the boid
	 * @param world
	 *          the world containing the boid
	 * @return the steering force for the specified boid
	 */
	public PVector getSteeringForce ( Boid boid, World world ) {
		// desired velocity is to go towards target at max speed
		PVector desired = PVector.sub(target_.getPosition(),boid.getPosition());
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
