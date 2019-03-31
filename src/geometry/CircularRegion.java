package geometry;

import core.World;
import processing.core.PApplet;
import processing.core.PVector;

/**
 * @author ssb
 */
public class CircularRegion extends Region {

	private PVector center_;
	private float radius_;

	public CircularRegion ( World world, PVector center, float radius ) {
		super(world);
		center_ = center;
		radius_ = radius;
	}

	/*
	 * (non-Javadoc)
	 * @see core.Renderable#render()
	 */
	@Override
	public void render () {
		PApplet applet = world_.getApplet();
		applet.noFill();
		applet.stroke(200);
		applet.strokeWeight(5);
		applet.ellipseMode(PApplet.CENTER);
		applet.ellipse(center_.x,center_.y,2 * radius_,2 * radius_);
		applet.strokeWeight(1);
	}

	/* (non-Javadoc)
	 * @see core.Renderable#getPosition()
	 */
	@Override
	public PVector getPosition () {
		// TODO Auto-generated method stub
		return center_;
	}
	/**
	 * @return the number_
	 */
	@Override
	public int getNumber() {
		return 0;
	}


}
