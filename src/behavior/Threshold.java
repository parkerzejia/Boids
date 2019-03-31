package behavior;

import core.Behavior;
import core.Boid;
import core.Target;
import core.World;
import processing.core.PApplet;
import processing.core.PVector;

/**
 * Threshold behavior - apply a behavior only within a certain threshold of a
 * target.
 */
public class Threshold extends Behavior {

	private Behavior behavior_; // behavior to apply
	private Target target_; // target
	private float threshold_; // the threshold

	/**
	 * Create a threshold behavior. This is the preferred constructor if the
	 * behavior itself involves a target, as the same target will be used for the
	 * thresholding.
	 * 
	 * @param behavior
	 *          behavior to apply
	 * @param threshold
	 *          threshold (apply behavior within threshold distance of target)
	 * @param c
	 *          color to display steering vector (for debug mode)
	 */
	public Threshold ( TargetBehavior behavior, float threshold, int c ) {
		super(c);
		behavior_ = behavior;
		target_ = behavior.getTarget();
		threshold_ = threshold;
	}

	/**
	 * Create a threshold behavior.
	 * 
	 * @param behavior
	 *          behavior to apply
	 * @param target
	 *          target
	 * @param threshold
	 *          threshold (apply behavior within threshold distance of target)
	 * @param c
	 *          color to display steering vector (for debug mode)
	 */
	public Threshold ( Behavior behavior, Target target, float threshold,
	                   int c ) {
		super(c);
		behavior_ = behavior;
		target_ = target;
		threshold_ = threshold;
	}

	/**
	 * Get the steering force for the specified boid.
	 * 
	 * @param boid
	 *          the boid
	 * @param world
	 *          the world containing the boid
	 * @return the steering force for the specified boid
	 */
	public PVector getSteeringForce ( Boid boid, World world ) {
		PVector targetpos = target_.getPosition();

		world.debugCircle(World.DEBUG_BEHAVIOR,boid.getPosition(),threshold_,color_);

		// distance to target
		float d = PApplet.dist(boid.getPosition().x,boid.getPosition().y,
		                       targetpos.x,targetpos.y);
		if ( d > threshold_ ) {
			return behavior_.getSteeringForce(boid,world);
		} else {
			return new PVector(0,0);
		}

	}

}