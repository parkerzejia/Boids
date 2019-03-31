package geometry;

import core.Renderable;
import core.World;
import processing.core.PVector;

/**
 * A wall.
 */
public abstract class Wall implements Renderable {

	protected World world_;

	/**
	 * Create a wall.
	 */
	protected Wall ( World world ) {
		world_ = world;
	}

	/**
	 * Intersect the line segment (p1,p2) with the wall.
	 * 
	 * @param p1
	 *          line segment endpoint
	 * @param p2
	 *          other line segment endpoint
	 * @return the intersection point of the line segment with the wall, or null
	 *         if there is no intersection
	 */
	public abstract PVector getIntersection ( PVector p1, PVector p2 );

	/**
	 * Get the outward-pointing surface normal at the intersection point between
	 * line segment (p1,p2) and the wall. * @param p1 line segment endpoint
	 * 
	 * @param p2
	 *          other line segment endpoint
	 * @return the surface normal at the intersection point or null if there is no
	 *         intersection
	 */
	public abstract PVector getNormalAtIntersection ( PVector p1, PVector p2 );

}
