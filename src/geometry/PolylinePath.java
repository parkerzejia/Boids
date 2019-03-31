package geometry;

import java.util.ArrayList;
import java.util.List;

import core.World;
import processing.core.PApplet;
import processing.core.PVector;

/**
 * A path consisting of a series of straight line segments.
 */
public class PolylinePath extends Path {

	private List<PVector> path_;

	public PolylinePath ( World world, float x1, float y1, float x2, float y2,
	                      float radius ) {
		this(world,radius);
		addVertex(x1,y1);
		addVertex(x2,y2);
	}

	public PolylinePath ( World world, float radius ) {
		super(world,radius);
		path_ = new ArrayList<PVector>();
	}

	public void addVertex ( float x, float y ) {
		path_.add(new PVector(x,y));
	}

	/**
	 * Find the nearest point on the path to p.
	 * 
	 * @param p
	 *          a point
	 * @return the nearest point on the path to p, or null if p is off the end of
	 *         the path
	 */
	public PVector getNearestPoint ( PVector p ) {
		PVector nearest = null;
		float dist = Float.MAX_VALUE;
		for ( int i = 1 ; i < path_.size() ; i++ ) {
			float t = getT(p,path_.get(i - 1),path_.get(i));
			if ( t < 0 ) {
				t = 0;
			} else if ( t > 1 ) {
				t = 1;
			}
			PVector current = getPoint(t,path_.get(i - 1),path_.get(i));
			float curdist = PApplet.dist(p.x,p.y,current.x,current.y);
			if ( curdist < dist ) {
				nearest = current;
				dist = curdist;
			}
		}

		return nearest;
	}

	/**
	 * Find the point the specified distance ahead of the nearest point to p on
	 * the path. For a one-way path, "ahead" is along the path in the proper
	 * direction. For a bidirectional path, "ahead" is in the direction of the
	 * boid's current orientation.
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
	public PVector getAheadPoint ( PVector p, PVector orientation, float dist ) {
		// find nearest point on path
		PVector nearest = null;
		float neardist = Float.MAX_VALUE;
		int seg = -1;
		for ( int i = 1 ; i < path_.size() ; i++ ) {
			float t = getT(p,path_.get(i - 1),path_.get(i));
			if ( t < 0 ) {
				t = 0;
			} else if ( t > 1 ) {
				t = 1;
			}
			PVector current = getPoint(t,path_.get(i - 1),path_.get(i));
			float curdist = PApplet.dist(p.x,p.y,current.x,current.y);
			if ( curdist < neardist ) {
				nearest = current;
				neardist = curdist;
				seg = i - 1;
			}
		}

		if ( nearest == null ) {
			return null;
		}

		// TODO doesn't consider if this would move into the next segment
		PVector dir = PVector.sub(path_.get(seg + 1),path_.get(seg));
		dir.normalize();
		if ( PVector.dot(dir,orientation) > 0 ) {
			return PVector.add(nearest,PVector.mult(dir,dist));
		} else {
			return PVector.add(nearest,PVector.mult(dir,-dist));
		}
	}

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
	private float getT ( PVector p, PVector p1, PVector p2 ) {
		// nearest point is based on a perpendicular to the segment
		PVector line = PVector.sub(p2,p1);
		PVector point = PVector.sub(p,p1);
		float t = PVector.dot(point,line) / line.magSq();
		return t;
	}

	/**
	 * Get the point the specified fraction along the segment.
	 * 
	 * @param t
	 *          fraction (0-1 to be within the segment)
	 * @param p1
	 * @param p2
	 * @return
	 */
	private PVector getPoint ( float t, PVector p1, PVector p2 ) {
		PVector line = PVector.sub(p2,p1);
		return PVector.add(p1,PVector.mult(line,t));
	}

	/*
	 * (non-Javadoc)
	 * @see geometry.Path#render()
	 */
	@Override
	public void render () {
		PApplet parent = world_.getApplet();
		parent.stroke(230);
		parent.strokeWeight(radius_ * 2);
		for ( int i = 1 ; i < path_.size() ; i++ ) {
			PVector p1 = path_.get(i - 1), p2 = path_.get(i);
			parent.line(p1.x,p1.y,p2.x,p2.y);
		}

		parent.stroke(0);
		parent.strokeWeight(1);
		for ( int i = 1 ; i < path_.size() ; i++ ) {
			PVector p1 = path_.get(i - 1), p2 = path_.get(i);
			parent.line(p1.x,p1.y,p2.x,p2.y);
		}
	}

	/* (non-Javadoc)
	 * @see core.Renderable#getPosition()//this could not return the postion
	 */
	@Override
	public PVector getPosition () {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * @return the number_
	 */
	@Override
	public int getNumber() {
		return 1;
	}

}
