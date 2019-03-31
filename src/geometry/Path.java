package geometry;

import core.Renderable;
import core.World;
import processing.core.PVector;

/**
 * A path is defined by some sort of central curve. It extends by a fixed radius
 * on either side of the central curve.
 */
public abstract class Path implements Renderable {

	protected World world_;
	protected float radius_; // path radius

	/**
	 * Create a path with the specified radius.
	 * 
	 * @param radius
	 *          path radius
	 */
	protected Path ( World world, float radius ) {
		world_ = world;
		radius_ = radius;
	}

	/**
	 * Find the nearest point on the path to p.
	 * 
	 * @param p
	 *          a point
	 * @return the nearest point on the path to p, or null if p is off the end of
	 *         the path
	 */
	public abstract PVector getNearestPoint ( PVector p );

	/**
	 * Find the point the specified distance ahead of the nearest point to p on
	 * the path. "Ahead" is in the direction of the boid's current orientation.
	 * 
	 * @param p
	 *          point
	 * @param orientation
	 *          current boid orientation
	 * @param dist
	 *          distance ahead
	 * @return the point dist ahead of the nearest point to p on the path, or null
	 *         if that would be off the end of the path
	 */
	public abstract PVector getAheadPoint ( PVector p, PVector orientation,
	                                        float dist );

	/**
	 * Get the path radius.
	 * 
	 * @return path radius
	 */
	public float getRadius () {
		return radius_;
	}

	/**
	 * Draw path.
	 */
	public abstract void render ();

}
