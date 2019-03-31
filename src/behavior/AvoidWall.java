package behavior;

import java.util.ArrayList;
import java.util.List;

import core.Behavior;
import core.Boid;
import core.World;
import geometry.Wall;
import processing.core.PApplet;
import processing.core.PVector;

/**
 * Wall avoidance behavior - steers away from the specified wall if it is in the
 * boid's path.
 */
public class AvoidWall extends Behavior {

	private Wall wall_; // wall to avoid
	private float whiskerlen_; // whisker length
	private int whiskerconfig_; // whisker configuration

	/**
	 * Create a wall avoidance behavior with center and side whiskers of length
	 * 50.
	 * 
	 * @param wall
	 *          wall to avoid
	 * @param c
	 *          color to display steering vector (for debug mode)
	 */
	public AvoidWall ( Wall wall, int c ) {
		this(wall,Boid.WHISKER_EQUAL,50,c);
	}

	/**
	 * Create a wall avoidance behavior.
	 * 
	 * @param wall
	 *          wall to avoid
	 * @param config
	 *          whisker configuration (WHISKER_CENTERONLY, WHISKER_SIDEONLY,
	 *          WHISKER_EQUAL, WHISKER_SHORT)
	 * @param length
	 *          whisker length (in time units)
	 * @param c
	 *          color to display steering vector (for debug mode)
	 */
	public AvoidWall ( Wall wall, int config, float length, int c ) {
		super(c);
		wall_ = wall;
		whiskerconfig_ = config;
		whiskerlen_ = length;
	}

	/**
	 * Get the wall avoidance steering force for the specified boid.
	 * 
	 * @param boid
	 *          the boid
	 * @param world
	 *          the world containing the boid
	 * @return the steering force for the specified boid
	 */
	public PVector getSteeringForce ( Boid boid, World world ) {
		// if a whisker hits the wall, steering force is in the direction of the
		// surface normal with magnitude proportional to how far the whisker
		// penetrates the wall

		PVector nose =
		    PVector.add(boid.getPosition(),
		                PVector.mult(boid.getOrientation(),boid.getLength() / 2));

		List<PVector[]> whiskers = new ArrayList<PVector[]>();

		switch ( whiskerconfig_ ) {
		case Boid.WHISKER_CENTERONLY: {
			PVector whisker1 = PVector.mult(boid.getVelocity(),whiskerlen_);
			whisker1.setMag(Math.max(whisker1.mag(),boid.getLength()));
			whiskers.add(new PVector[] { nose, PVector.add(nose,whisker1) });

			break;
		}
		case Boid.WHISKER_SIDEONLY: {
			PVector whisker1 = PVector.mult(boid.getVelocity(),whiskerlen_);
			whisker1.setMag(Math.max(whisker1.mag(),boid.getLength()));
			whisker1.rotate(PApplet.PI / 6);
			whiskers.add(new PVector[] { nose, PVector.add(nose,whisker1) });

			PVector whisker2 = PVector.mult(boid.getVelocity(),whiskerlen_);
			whisker2.setMag(Math.max(whisker2.mag(),boid.getLength()));
			whisker2.rotate(-PApplet.PI / 6);
			whiskers.add(new PVector[] { nose, PVector.add(nose,whisker2) });

			break;
		}
		case Boid.WHISKER_EQUAL: {
			PVector whisker1 = PVector.mult(boid.getVelocity(),whiskerlen_);
			whisker1.setMag(Math.max(whisker1.mag(),boid.getLength()));
			whisker1.rotate(PApplet.PI / 6);
			whiskers.add(new PVector[] { nose, PVector.add(nose,whisker1) });

			PVector whisker2 = PVector.mult(boid.getVelocity(),whiskerlen_);
			whisker2.setMag(Math.max(whisker2.mag(),boid.getLength()));
			whisker2.rotate(-PApplet.PI / 6);
			whiskers.add(new PVector[] { nose, PVector.add(nose,whisker2) });

			PVector whisker3 = PVector.mult(boid.getVelocity(),whiskerlen_);
			whisker1.setMag(Math.max(whisker3.mag(),boid.getLength()));
			whiskers.add(new PVector[] { nose, PVector.add(nose,whisker3) });

			break;
		}
		case Boid.WHISKER_SHORT: {
			PVector whisker1 = PVector.mult(boid.getVelocity(),whiskerlen_ / 3);
			whisker1.setMag(Math.max(whisker1.mag(),boid.getLength()));
			whisker1.rotate(PApplet.PI / 6);
			whiskers.add(new PVector[] { nose, PVector.add(nose,whisker1) });

			PVector whisker2 = PVector.mult(boid.getVelocity(),whiskerlen_ / 3);
			whisker2.setMag(Math.max(whisker2.mag(),boid.getLength()));
			whisker2.rotate(-PApplet.PI / 6);
			whiskers.add(new PVector[] { nose, PVector.add(nose,whisker2) });

			PVector whisker3 = PVector.mult(boid.getVelocity(),whiskerlen_);
			whisker1.setMag(Math.max(whisker3.mag(),boid.getLength()));
			whiskers.add(new PVector[] { nose, PVector.add(nose,whisker3) });

			break;
		}
		}

		for ( PVector[] whisker : whiskers ) {
			world.debugLine(World.DEBUG_BEHAVIOR,whisker[0],whisker[1],color_,1);
		}

		PVector contact = null; // point of contact between whisker and wall
		PVector normal = null; // surface normal at point of intersection
		float strength = 0;

		for ( PVector[] whisker : whiskers ) {
			// intersect whisker with wall
			PVector hit = wall_.getIntersection(whisker[0],whisker[1]);
			if ( hit == null ) {
				continue;
			}
			world.debugPoint(World.DEBUG_BEHAVIOR,hit,color_,2);

			// only update if better
			if ( contact == null ) {
				contact = hit;

				normal = wall_.getNormalAtIntersection(whisker[0],whisker[1]);
				world.debugLine(World.DEBUG_BEHAVIOR,contact,
				                PVector.add(contact,normal),color_,1);

				strength =
				    (PApplet.dist(whisker[0].x,whisker[0].y,whisker[1].x,whisker[1].y)
				        - PApplet.dist(whisker[0].x,whisker[0].y,contact.x,contact.y))
				        / boid.getVelocity().mag();
			}
		}

		if ( contact == null ) {
			return new PVector(0,0);
		}

		// steering is in the direction of the
		// surface normal with magnitude proportional to how far the whisker
		// penetrates the wall
		PVector steering = normal.copy();
		steering.setMag(strength * boid.getMaxForce());

		world.debugVector(World.DEBUG_BEHAVIOR,boid.getPosition(),steering,
		                  50 / boid.getMaxForce(),color_,3);

		return steering;
	}

}