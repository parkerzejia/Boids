package behavior;

import core.Behavior;
import core.Boid;
import core.World;
import geometry.Path;
import processing.core.PApplet;
import processing.core.PVector;

/**
 * Path following.
 */
public class PathFollowing extends Behavior {

	private Path path_; // path to follow
	private float lookahead_; // how far in the future to consider boid's position

	/**
	 * Create a path following behavior.
	 * 
	 * @param path
	 *          path to follow
	 * @param lookahead
	 *          how far in the future to consider the boid's position
	 * @param c
	 *          color to display steering vector (for debug mode)
	 */
	public PathFollowing ( Path path, float lookahead, int c ) {
		super(c);
		path_ = path;
		lookahead_ = lookahead;
	}

	/**
	 * Get the path following steering force for the specified boid.
	 * 
	 * @param boid
	 *          the boid
	 * @param world
	 *          the world containing the boid
	 * @return the steering force for the specified boid
	 */
	public PVector getSteeringForce ( Boid boid, World world ) {
		// predict boid's future position p
		PVector p = PVector.add(boid.getPosition(),
		                        PVector.mult(boid.getVelocity(),lookahead_));

		// find nearest point q on path to p
		PVector q = path_.getNearestPoint(p);
		if ( q == null ) { // p is off the end of the path
			return new PVector(0,0);
		}
		world.debugPoint(World.DEBUG_BEHAVIOR,p,getColor(),3);
		world.debugPoint(World.DEBUG_BEHAVIOR,q,getColor(),3);
		world.debugLine(World.DEBUG_BEHAVIOR,p,q,getColor(),1);

		// if p is within the path radius, no steering is required
		// otherwise seek a point ahead of q on the path
		if ( PApplet.dist(p.x,p.y,q.x,q.y) <= path_.getRadius() ) {
			return new PVector(0,0);
		} else {
			PVector target = path_.getAheadPoint(p,boid.getOrientation(),lookahead_);
			world.debugPoint(World.DEBUG_BEHAVIOR,target,getColor(),5);

			// desired velocity is to go towards target at max speed
			PVector desired = PVector.sub(target,boid.getPosition());
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