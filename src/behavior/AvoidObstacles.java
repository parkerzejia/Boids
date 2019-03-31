package behavior;

import core.Behavior;
import core.Boid;
import core.Renderable;
import core.World;
import geometry.Geometry;
import geometry.Obstacle;
import processing.core.PApplet;
import processing.core.PVector;

/**
 * Obstacle avoidance behavior - steers away from most threatening obstacle
 * within the boid's lookahead range. Based on the obstacle's bounding circle.
 */
public class AvoidObstacles extends Behavior {

	private float lookahead_; // how far ahead of boid to consider

	/**
	 * Create an obstacle avoidance behavior with a lookahead of 50.
	 * 
	 * @param c
	 *          color to display steering vector (for debug mode)
	 */
	public AvoidObstacles ( int c ) {
		this(50,c);
	}

	/**
	 * Create an obstacle avoidance behavior.
	 * 
	 * @param lookahead
	 *          how far ahead of boid to consider obstacles (in time units)
	 * @param c
	 *          color to display steering vector (for debug mode)
	 */
	public AvoidObstacles ( float lookahead, int c ) {
		super(c);
		lookahead_ = lookahead;
	}

	/**
	 * Get the obstacle avoidance steering force for the specified boid.
	 * 
	 * @param boid
	 *          the boid
	 * @param world
	 *          the world containing the boid
	 * @return the steering force for the specified boid
	 */
	public PVector getSteeringForce ( Boid boid, World world ) {
		// find most threatening object, if any
		Obstacle threat = null;
		float tmin = Float.MAX_VALUE;

		PVector axis = PVector.mult(boid.getVelocity(),lookahead_);
		PVector axisend = PVector.add(boid.getPosition(),axis);

		world.debugLine(World.DEBUG_BEHAVIOR,boid.getPosition(),axisend,color_,1);

		for ( Renderable thing : world.getThings() ) {
			if ( !(thing instanceof Obstacle) ) {
				continue;
			}

			Obstacle obstacle = (Obstacle) thing;
			// skip if boid is inside obstacle
			/*
			 * if ( PApplet.dist(boid.getPosition().x,boid.getPosition().y,
			 * obstacle.getCenter().x, obstacle.getCenter().y) <= obstacle.getRadius()
			 * + boid.getWidth() / 2 ) { continue; }
			 */

			world.debugCircle(World.DEBUG_BEHAVIOR,obstacle.getCenter(),
			                  obstacle.getRadius() + boid.getWidth() / 2,color_);

			// find nearest point to obstacle center on axis line
			PVector p = Geometry.getNearestPoint(obstacle.getCenter(),
			                                     boid.getPosition(),axisend);
			// compute distance d to nearest point on axis line
			float d = PVector.dist(obstacle.getCenter(),p);

			world.debugLine(World.DEBUG_BEHAVIOR,p,obstacle.getCenter(),color_,1);

			// discard if d > obstacle's radius + region's radius
			if ( d > obstacle.getRadius() + boid.getWidth() / 2 ) {
				continue;
			}

			// intersect axis line with expanded circle and pick the closest such
			// intersection
			// within the lookahead distance
			float[] ts = Geometry
			    .getIntersectionTs(boid.getPosition(),axisend,obstacle.getCenter(),
			                       obstacle.getRadius() + boid.getWidth() / 2);
			for ( int i = 0 ; i < ts.length ; i++ ) {
				world.debugPoint(
				                 World.DEBUG_BEHAVIOR,PVector
				                     .add(boid.getPosition(),PVector.mult(axis,ts[i])),
				                 color_,3);
				if ( ts[i] >= 0 && ts[i] < tmin && ts[i] <= 1 ) {
					threat = obstacle;
					tmin = ts[i];
				}
			}
		}

		if ( threat == null ) {
			return new PVector(0,0);
		}

		world.debugPoint(World.DEBUG_BEHAVIOR,threat.getCenter(),color_,5);

		float scale = PApplet.map(tmin,0,1,boid.getMaxForce(),0);
		PVector braking = PVector.mult(boid.getVelocity(),-scale);
		PVector lateral = PVector
		    .mult(PVector.sub(
		                      Geometry.getNearestPoint(threat.getCenter(),
		                                               boid.getPosition(),axisend),
		                      threat.getCenter()),
		          scale);

		// steering is sum of braking and lateral forces
		PVector steering = PVector.add(braking,lateral);
		steering.limit(boid.getMaxForce());

		world.debugVector(World.DEBUG_BEHAVIOR,boid.getPosition(),braking,
		                  50 / boid.getMaxSpeed(),color_,2);
		world.debugVector(World.DEBUG_BEHAVIOR,boid.getPosition(),lateral,
		                  50 / boid.getMaxSpeed(),color_,2);
		world.debugVector(World.DEBUG_BEHAVIOR,boid.getPosition(),steering,
		                  50 / boid.getMaxForce(),color_,3);

		return steering;
	}

}