package core;

import processing.core.PApplet;
import processing.core.PVector;

/**
 * @author zg6197
 *
 */
public class Food implements Renderable {
//simple vehicle model parameters
	private PVector position_;
// parameters defining the neighborhood
	private float neighborRadius_;
	private World world_; // world the food belongs to
	private int color_ ;// food color
	private int number_;
	
	
	public Food(World world, PVector position, float radius,
            int color, int number ) {
		world_ = world;
		position_ = position;
		neighborRadius_ = radius;
		color_ = color;
		number_=number;
	}
	
	
	/* (non-Javadoc)
	 * @see core.Renderable#render()
	 */
	@Override
	public void render () {
		PApplet applet = world_.getApplet();
		applet.fill(50);
		applet.stroke(50);
		applet.ellipseMode(PApplet.CENTER);
		applet.ellipse(position_.x,position_.y,2 * neighborRadius_,2 * neighborRadius_);
	}
	/**
	 * @return the number_
	 */
	public int getNumber_ () {
		return number_;
	}

	/**
	 * @param number_ the number_ to set
	 */
	public void setNumber_ ( int number_ ) {
		this.number_ = number_;
	}
	
	/*
	 * (non-Javadoc)
	 * @see geometry.Obstacle#getCenter()
	 */
	public PVector getCenter () {
		return position_;
	}

	/*
	 * (non-Javadoc)
	 * @see geometry.Obstacle#getRadius()
	 */
	public float getRadius () {
		return neighborRadius_;
	}


	/* (non-Javadoc)
	 * @see core.Renderable#getPosition()
	 */
	@Override
	public PVector getPosition () {
		// TODO Auto-generated method stub
		return null;
	}


	/* (non-Javadoc)
	 * @see core.Renderable#getNumber()
	 */
	@Override
	public int getNumber () {
		// TODO Auto-generated method stub
		return 5;
	}
	
}
