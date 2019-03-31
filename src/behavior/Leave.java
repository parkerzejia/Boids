package behavior;

import core.Boid;
import core.Target;
import core.World;
import processing.core.PApplet;
import processing.core.PVector;

/**
 * Leave behavior - within a certain radius, move away from target with speed
 * proportional to closeness to target.
 */
public class Leave extends TargetBehavior {

	private float leaveradius_; // scale speed within leave radius

	/**
	 * Create a behavior to leave the specified target. Within the leave radius,
	 * the boid moves.
	 * 
	 * @param target
	 *          leave target
	 * @param leaveradius
	 *          leave radius (scale speed within this radius)
	 * @param c
	 *          color to display steering vector (for debug mode)
	 */
	public Leave ( Target target, float leaveradius, int c ) {
		super(target,c);
		leaveradius_ = leaveradius;
	}

	/**
	 * Get the leave steering force for the specified boid.
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
		if ( d > leaveradius_ ) {
			speed = 0;
		} else {
			// scale the distance boid is from the target into the range maxspeed-0
			speed = PApplet.map(d,0,leaveradius_,boid.getMaxSpeed(),0);
		}

		PVector desired = PVector.sub(boid.getPosition(),targetpos);
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