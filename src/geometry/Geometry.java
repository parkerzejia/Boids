package geometry;

import processing.core.PVector;

/**
 * Miscellaneous geometric computations.
 */
public class Geometry {

	/**
	 * Find the t value of the nearest point on the segment (p1,p2) to p.
	 * 
	 * @param p
	 *          point
	 * @param p1
	 *          starting point of segment
	 * @param p2
	 *          ending point of segment
	 * @return t value of the nearest point on the segment to p (0 <= t <= 1 means
	 *         that the nearest point is on the segment)
	 */
	public static float getNearestT ( PVector p, PVector p1, PVector p2 ) {
		// nearest point is based on a perpendicular to the segment
		PVector line = PVector.sub(p2,p1);
		PVector point = PVector.sub(p,p1);
		float t = PVector.dot(point,line) / line.magSq();
		return t;
	}

	/**
	 * Find the nearest point on the line (p1,p2) to p.
	 * 
	 * @param p1
	 *          start point of segment defining the line
	 * @param p2
	 *          end point of segment defining the line
	 * @param p
	 *          the point
	 * @return nearest point on the line (p1,p2) to p
	 */
	public static PVector getNearestPoint ( PVector p, PVector p1, PVector p2 ) {
		// nearest point is based on a perpendicular to the segment
		PVector line = PVector.sub(p2,p1);
		PVector point = PVector.sub(p,p1);
		float t = PVector.dot(point,line) / line.magSq();
		return PVector.add(p1,PVector.mult(line,t));
	}

	/**
	 * Find the t values of the intersection point(s) between a line and a circle.
	 * 
	 * @param p1
	 *          start point of segment defining the line
	 * @param p2
	 *          end point of segment defining the line
	 * @param c
	 *          center of circle
	 * @param r
	 *          radius of circle
	 * @return t value of the intersection point(s) between the line and the
	 *         circle; 0 <= t <= 1 means the intersection point is on the line
	 *         segment
	 */
	public static float[] getIntersectionTs ( PVector p1, PVector p2,
	                                          PVector center, float r ) {
		float dx = p2.x - p1.x, dy = p2.y - p1.y;
		float a = dx * dx + dy * dy,
		    b = 2 * (dx * (p1.x - center.x) + dy * (p1.y - center.y)),
		    c = (p1.x - center.x) * (p1.x - center.x)
		        + (p1.y - center.y) * (p1.y - center.y) - r * r;

		// float discriminant = r * r * dr * dr - det * det;
		float discriminant = b * b - 4 * a * c;
		if ( discriminant < 0 ) {
			return new float[] {};

		} else if ( discriminant == 0 ) {
			return new float[] { -b / (2 * a) };

		} else {
			return new float[] { (-b + (float) Math.sqrt(discriminant)) / (2 * a),
			                     (-b - (float) Math.sqrt(discriminant)) / (2 * a) };
		}
	}

	/**
	 * Find the intersection point between two line segments (p1,p2) and (p3,p4).
	 * 
	 * @param p1
	 *          segment endpoint
	 * @param p2
	 *          segment endpoint
	 * @param p3
	 *          segment endpoint
	 * @param p4
	 *          segment endpoint
	 * @return intersection point, or null if the segments do not intersect or are
	 *         collinear
	 */
	public static PVector intersectLines ( PVector p1, PVector p2, PVector p3,
	                                       PVector p4 ) {
		float denom = (p1.x - p2.x) * (p3.y - p4.y) - (p1.y - p2.y) * (p3.x - p4.x);
		if ( denom == 0 ) {
			return null;
		}
		float t =
		    ((p1.x - p3.x) * (p3.y - p4.y) - (p1.y - p3.y) * (p3.x - p4.x)) / denom;
		float u = -((p1.x - p2.x) * (p1.y - p3.y) - (p1.y - p2.y) * (p1.x - p3.x))
		    / denom;
		if ( 0 <= t && t <= 1 && 0 <= u && u <= 1 ) {
			return PVector.add(p1,PVector.mult(PVector.sub(p2,p1),t));
		} else {
			return null;
		}
	}
}
