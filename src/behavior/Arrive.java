package behavior;

import core.Boid;
import core.Target;
import core.World;
import processing.core.PApplet;
import processing.core.PVector;
import target.MouseTarget;

/**
 * Arrive behavior - match position with a target, ending with the boid at rest.
 */
public class Arrive extends TargetBehavior {

	private float stopradius_; // begin slowing within stopping radius

	/**
	 * Create a behavior to arrive at the specified target.
	 * 
	 * @param target
	 *          arrival target
	 * @param stopradius
	 *          stopping radius (boid begins to slow within this distance of the
	 *          target)
	 * @param c
	 *          color to display steering vector (for debug mode)
	 */
	public Arrive ( Target target, float stopradius, int c ) {
		super(target,c);
		stopradius_ = stopradius;
	}

	/**
	 * @param mouseTarget
	 * @param color
	 */


	/**
	 * Get the arrival steering force for the specified boid.
	 * 
	 * @param boid
	 *          the boid
	 * @param world
	 *          the world containing the boid
	 * @return the steering force for the specified boid
	 */
	public PVector getSteeringForce ( Boid boid, World world ) {
		PVector targetpos = target_.getPosition();

		float speed; // scale factor for desired velocity
		// distance to target
		float d = PApplet.dist(boid.getPosition().x,boid.getPosition().y,
		                       targetpos.x,targetpos.y);
		if ( d > stopradius_ ) {
			speed = boid.getMaxSpeed();
		} else {
			// scale the distance boid is from the target into the range 0-maxspeed
			speed = PApplet.map(d,0,stopradius_,0,boid.getMaxSpeed());
		}

		PVector desired = PVector.sub(targetpos,boid.getPosition());
		desired.setMag(speed);

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