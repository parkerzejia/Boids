package geometry;

import java.awt.geom.Line2D;

import core.World;
import processing.core.PApplet;
import processing.core.PVector;

/**
 * A straight line segment wall.
 */
public class StraightWall extends Wall {

	private PVector p1_, p2_; // wall endpoints

	public StraightWall ( World world, PVector p1, PVector p2 ) {
		super(world);
		p1_ = p1;
		p2_ = p2;
	}

	public StraightWall ( World world, float x1, float y1, float x2, float y2 ) {
		this(world,new PVector(x1,y1),new PVector(x2,y2));
	}

	/*
	 * (non-Javadoc)
	 * @see core.Renderable#render()
	 */
	@Override
	public void render () {
		PApplet applet = world_.getApplet();
		applet.noFill();
		applet.stroke(50);
		applet.strokeWeight(5);
		applet.line(p1_.x,p1_.y,p2_.x,p2_.y);
		applet.strokeWeight(1);
	}

	/*
	 * (non-Javadoc)
	 * @see geometry.Wall#getIntersection(processing.core.PVector,
	 * processing.core.PVector)
	 */
	@Override
	public PVector getIntersection ( PVector p1, PVector p2 ) {
		return Geometry.intersectLines(p1,p2,p1_,p2_);
	}

	/*
	 * (non-Javadoc)
	 * @see geometry.Wall#getNormalAtIntersection(processing.core.PVector,
	 * processing.core.PVector)
	 */
	@Override
	public PVector getNormalAtIntersection ( PVector p1, PVector p2 ) {
		// the intersection point doesn't matter as long as there is one
		PVector p = getIntersection(p1,p2);
		if ( p == null ) {
			return null;
		}

		// use p1 to determine the outside of the wall
		float dx = p2_.x - p1_.x, dy = p2_.y - p1_.y;
		PVector n1 = new PVector(-dy,dx), n2 = new PVector(dy,-dx);
		n1.normalize();
		n2.normalize();

		int ccw1 = Line2D.relativeCCW(p1_.x,p1_.y,p2_.x,p2_.y,p1.x,p1.y);
		int ccwn1 =
		    Line2D.relativeCCW(p1_.x,p1_.y,p2_.x,p2_.y,p.x + n1.x,p.y + n1.y);
		if ( ccw1 == ccwn1 ) {
			return n1;
		} else if ( ccw1 == -ccwn1 ) {
			return n2;
		}

		// ccw1 must be 0 - go by p2
		int ccw2 = Line2D.relativeCCW(p1_.x,p1_.y,p2_.x,p2_.y,p2.x,p2.y);
		if ( ccw2 == ccwn1 ) {
			return n2;
		} else if ( ccw2 == -ccwn1 ) {
			return n1;
		}

		// if we're here, both p1 and p2 are on the wall
		return n1;
	}

	/* (non-Javadoc)
	 * @see core.Renderable#getPosition() doesn't return position
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
		return 4;
	}

}
