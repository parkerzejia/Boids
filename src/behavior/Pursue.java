package behavior;

import core.Boid;
import core.Target;
import core.World;
import processing.core.PApplet;
import processing.core.PVector;

/**
 * Pursue - catch a moving target.
 */
public class Pursue extends TargetBehavior {

	/**
	 * Create a pursue behavior with the specified target.
	 * 
	 * @param target
	 *          pursue target
	 * @param c
	 *          color to display steering vector (for debug mode)
	 */
	public Pursue ( Target target, int c ) {
		super(target,c);
	}

	/**
	 * Get the pursue steering force for the specified boid.
	 * 
	 * @param boid
	 *          the boid
	 * @param world
	 *          the world containing the boid
	 * @return the steering force for the specified boid
	 */
	public PVector getSteeringForce ( Boid boid, World world ) {
		PVector targetpos = target_.getPosition(),
		    targetvel = target_.getVelocity();

		PVector pos = boid.getPosition();
		float t =
		    PApplet.dist(targetpos.x,targetpos.y,pos.x,pos.y) / boid.getMaxSpeed();
		PVector futurepos = PVector.add(targetpos,PVector.mult(targetvel,t));

		world.debugPoint(World.DEBUG_BEHAVIOR,futurepos,color_,3);

		// desired velocity is to seek target's future position
		PVector desired = PVector.sub(futurepos,boid.getPosition());
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
