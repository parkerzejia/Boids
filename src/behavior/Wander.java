package behavior;

import core.Behavior;
import core.Boid;
import core.World;
import processing.core.PApplet;
import processing.core.PVector;

/**
 * Wander behavior.
 */
public class Wander extends Behavior {

	private float strength_; // wander strength (radius of the wander circle)
	private float ahead_; // time units to center of the wander circle
	private float rate_; // wander rate (max displacement amount)

	private PVector prevtarget_; // previous wander target

	/**
	 * Create a wander behavior with ahead 30, strength 30, and rate 10.
	 * 
	 * @param c
	 *          color to display steering vector (for debug mode)
	 */
	public Wander ( int c ) {
		this(30,30,10,c);
	}

	/**
	 * Create a wander behavior with the specified ahead distance, strength, and
	 * rate.
	 * 
	 * @param ahead
	 *          amount (in time units) that the center of the wander circle is
	 *          ahead of the boid
	 * @param strength
	 *          wander strength (radius of the wander circle)
	 * @param rate
	 *          wander rate (max displacement)
	 * @param c
	 *          color to display steering vector (for debug mode)
	 */
	public Wander ( float ahead, float strength, float rate, int c ) {
		super(c);
		ahead_ = ahead;
		strength_ = strength;
		rate_ = rate;
		prevtarget_ = null;
	}

	/**
	 * Get the wander steering force for the specified boid.
	 * 
	 * @param boid
	 *          the boid
	 * @param world
	 *          the world containing the boid
	 * @return the steering force for the specified boid
	 */
	public PVector getSteeringForce ( Boid boid, World world ) {
		PApplet applet = world.getApplet();

		PVector center =
		    PVector.add(boid.getPosition(),PVector.mult(boid.getVelocity(),ahead_));
		PVector target;

		if ( prevtarget_ == null ) { // no previous target, pick random target on
		                             // wander circle
			float angle = applet.random(0,2 * PApplet.PI);
			target = new PVector(center.x + strength_ * PApplet.cos(angle),
			                     center.y + strength_ * PApplet.sin(angle));

		} else {
			// displace target
			PVector displaced =
			    PVector.add(prevtarget_,PVector.mult(PVector.random2D(),rate_));
			world.debugCircle(World.DEBUG_BEHAVIOR,prevtarget_,rate_,color_);
			world.debugPoint(World.DEBUG_BEHAVIOR,displaced,color_,2);

			// project back onto wander circle
			PVector v = PVector.sub(displaced,center);
			v.setMag(strength_);
			target = PVector.add(center,v);
		}

		world.debugCircle(World.DEBUG_BEHAVIOR,center,strength_,color_);
		world.debugPoint(World.DEBUG_BEHAVIOR,target,color_,4);

		PVector desired = PVector.sub(target,boid.getPosition());
		desired.setMag(boid.getMaxSpeed());

		// steer to match desired velocity
		PVector steering = PVector.sub(desired,boid.getVelocity());
		steering.limit(boid.getMaxForce());

		world.debugVector(World.DEBUG_BEHAVIOR,boid.getPosition(),desired,
		                  50 / boid.getMaxSpeed(),color_,1);
		world.debugVector(World.DEBUG_BEHAVIOR,boid.getPosition(),steering,
		                  50 / boid.getMaxForce(),color_,3);

		prevtarget_ = target;
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