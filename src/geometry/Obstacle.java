package geometry;

import core.Renderable;
import core.World;
import processing.core.PVector;

/**
 * An obstacle.
 */
public abstract class Obstacle implements Renderable {

	protected World world_;
 
	/**
	 * Create an obstacle.
	 */
	protected Obstacle ( World world ) {
		world_ = world;
	}

	/**
	 * Find the nearest surface point on the obstacle to p.
	 * 
	 * @param p
	 *          a point
	 * @return the nearest surface point on the obstacle, or null if p is inside
	 *         the obstacle
	 */
	public abstract PVector getNearestPoint ( PVector p );

	/**
	 * Find the surface normal at the point p.
	 * 
	 * @param p
	 *          a point on the surface of the obstacle
	 * @return surface normal at the point p
	 */
	public abstract PVector getNormal ( PVector p );

	/**
	 * Draw path.
	 */
	public abstract void render ();

	/**
	 * Get the center of the obstacle's bounding circle.
	 * 
	 * @return center of the obstacle's bounding circle
	 */
	public abstract PVector getCenter ();

	/**
	 * Get the radius of the obstacle's bounding circle.
	 * 
	 * @return radius of the obstacle's bounding circle
	 */
	public abstract float getRadius ();

}
