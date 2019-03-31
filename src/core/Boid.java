package core;

import processing.core.PApplet;
import processing.core.PVector;

/**
 * An individual boid.
 */
public class Boid {

	public static final int WHISKER_CENTERONLY = 2, // single center ray
	    WHISKER_SIDEONLY = 3, // angled side whiskers only
	    WHISKER_EQUAL = 4, // center and side whiskers, equal length
	    WHISKER_SHORT = 5; // center and side whiskers, with short side whiskers
	// simple vehicle model parameters
	private PVector position_;
	private float mass_;
	// orientation is a unit vector in direction of velocity (it is to deal with
	// cases of 0 velocity)
	private PVector velocity_, orientation_;
	private float maxforce_;
	private float maxspeed_;
  
	// parameters defining the neighborhood
	private float neighborRadius_;
	private float neighborAngle_;

	// action selection
	private Brain brain_;

	private World world_; // world the boid belongs to

	private int color_; // boid's color
	private int number_;


	/**
	 * Create a new boid with the specified parameters and a default color.
	 * 
	 * @param world
	 *          the world containing the boid
	 * @param position
	 *          boid's position
	 * @param mass
	 *          boid's mass
	 * @param velocity
	 *          boid's velocity
	 * @param maxforce
	 *          boid's maximum acceleration force
	 * @param maxspeed
	 *          boid's maximum speed
	 * @param neighborRadius
	 *          radius of boid's neighborhood
	 * @param neighborAngle
	 *          boid's field of view
	 * @param brain
	 *          boid's brain (for selecting/combining behaviors)
	 * @param number
	 * 					to identify different kind of fish that has their own number
	 *          green-1, red-2
	 */
	public Boid ( World world, PVector position, float mass, PVector velocity,
	              float maxforce, float maxspeed, float neighborRadius,
	              float neighborAngle, Brain brain, int number ) {
		this(world,position,mass,velocity,maxforce,maxspeed,neighborRadius,
		     neighborAngle,brain,world.getApplet().color(51,21,168), number);
	}

	/**
	 * Create a new boid with the specified parameters.
	 *
	 * @param world
	 *          the world containing the boid
	 * @param position
	 *          boid's position
	 * @param mass
	 *          boid's mass
	 * @param velocity
	 *          boid's velocity
	 * @param maxforce
	 *          boid's maximum acceleration force
	 * @param maxspeed
	 *          boid's maximum speed
	 * @param neighborRadius
	 *          radius of boid's neighborhood
	 * @param neighborAngle
	 *          boid's field of view
	 * @param brain
	 *          boid's brain (for selecting/combining behaviors)
	 * @param color
	 *          boid's color
	 */
	public Boid ( World world, PVector position, float mass, PVector velocity,
	              float maxforce, float maxspeed, float neighborRadius,
	              float neighborAngle, Brain brain, int color, int number ) {
		world_ = world;

		position_ = position;
		mass_ = mass;
		velocity_ = velocity;
		number_=number;
		if ( velocity_.mag() > 0 ) {
			velocity_ = velocity;
			orientation_ = velocity_.copy();
			orientation_.normalize();
		} else {
			orientation_ = PVector.random2D();
		}
		maxforce_ = maxforce;
		maxspeed_ = maxspeed;

		neighborRadius_ = neighborRadius;
		neighborAngle_ = neighborAngle;

		brain_ = brain;
		color_ = color;
	}

	/**
	 * Get the boid's position.
	 * 
	 * @return boid's current position
	 */
	public PVector getPosition () {
		return position_;
	}

	/**
	 * Get the boid's mass.
	 * 
	 * @return boid's mass
	 */
	public float getMass () {
		return mass_;
	}

	/**
	 * Get the boid's velocity.
	 * 
	 * @return boid's current velocity
	 */
	public PVector getVelocity () {
		return velocity_;
	}

	/**
	 * Get the boid's orientation.
	 * 
	 * @return boid's current orientation
	 */
	public PVector getOrientation () {
		return orientation_;
	}

	/**
	 * Get the boid's max acceleration force.
	 * 
	 * @return boid's max acceleration force
	 */
	public float getMaxForce () {
		return maxforce_;
	}

	/**
	 * Get the boid's max speed.
	 * 
	 * @return boid's max speed
	 */
	public float getMaxSpeed () {
		return maxspeed_;
	}

	/**
	 * Get the radius of the boid's neighborhood.
	 * 
	 * @return radius of boid's neighborhood
	 */
	public float getNeighborRadius () {
		return neighborRadius_;
	}

	/**
	 * Get the boid's field of view.
	 * 
	 * @return boid's field of view
	 */
	public float getNeighborAngle () {
		return neighborAngle_;
	}

	/**
	 * Determine if 'other' is a neighbor of this boid (i.e. is in this boid's
	 * neighborhood)
	 * 
	 * @param other
	 *          other boid
	 * @return true if 'other' is a neighbor of this boid, false if 'other' is not
	 *         in the neighborhood or if 'other' is the same boid (a boid is not
	 *         its own neighbor)
	 */
	public boolean isNeighbor ( Boid other ) {
		if ( other == this ) {
			return false;
		}

		float dist = PVector.dist(position_,other.position_);
		if ( dist > neighborRadius_ ) {
			return false;
		}

		float angle =
		    // PVector.angleBetween(velocity_,PVector.sub(other.position_,position_));
		    PVector.angleBetween(orientation_,
		                         PVector.sub(other.position_,position_));
		return PApplet.abs(angle) <= neighborAngle_;
	}

	
	
	
	/**
	 * Determine if 'other' is a neighbor of this boid (i.e. is in this boid's
	 * neighborhood)
	 * 
	 * @param other
	 *          other boid
	 * @return true if 'other' is a neighbor of this boid, false if 'other' is not
	 *         in the neighborhood or if 'other' is the same boid (a boid is not
	 *         its own neighbor)
	 */
	public boolean isNeighborThing ( Renderable other ) {
		if ( other == this ) {
			return false;
		}

		float dist = PVector.dist(position_,other.getPosition());
		if ( dist > neighborRadius_ ) {
			return false;
		}else {
		return true;
		}
		//return PApplet.abs(angle) <= neighborAngle_;
	}
	
	
	/**
	 * Move the boid according to its behaviors. Also displays debugging info if
	 * debug mode is on.
	 */
	public void update () {
		if ( world_.getDebug(World.DEBUG_BOID) ) {
			PApplet parent = world_.getApplet();

			// draw the neighborhood
			parent.ellipseMode(PApplet.CENTER);
			parent.stroke(0);
			parent.fill(0,10);

			parent.pushMatrix();
			parent.translate(position_.x,position_.y);
			// parent_.rotate(PApplet.atan2(velocity_.y,velocity_.x));
			parent.rotate(PApplet.atan2(orientation_.y,orientation_.x));
			parent.arc(0,0,neighborRadius_ * 2,neighborRadius_ * 2,-neighborAngle_,
			           neighborAngle_,PApplet.PIE);
			parent.popMatrix();

			// draw the boid's current velocity
			parent.stroke(color_);
			PVector vel = velocity_.copy();
			vel.normalize();
			parent.line(position_.x,position_.y,
			            position_.x + velocity_.mag() / maxspeed_ * vel.x * 50,
			            position_.y + velocity_.mag() / maxspeed_ * vel.y * 50);
		}

		// compute and apply the net steering force
		PVector total = brain_.getNetSteeringForce(this,world_);
		PVector accel = PVector.div(total,mass_);
		velocity_.add(accel);
		velocity_.limit(maxspeed_);
		if ( velocity_.mag() > 0 ) {
			orientation_ = velocity_.copy();
			orientation_.normalize();
		}

		position_.add(velocity_);
	}

	/**
	 * Draw the boid on the screen, using its current position and orientation.
	 */
	public void render () {
		PApplet parent = world_.getApplet();

		// parent_.stroke(51,21,168);
		// parent_.fill(51,21,168);
		parent.stroke(color_);
		parent.fill(color_);

		parent.pushMatrix();
		parent.translate(position_.x,position_.y);
		parent.rotate(PApplet.atan2(orientation_.y,orientation_.x));
		// parent_.rotate(PApplet.atan2(velocity_.y,velocity_.x));
		parent.triangle(10,0,-10,-5,-10,5);
		parent.popMatrix();
	}
	/**
	 * Get the boid's width.
	 * 
	 * @return boid's width
	 */
	public float getWidth () {
		return 10;
	}

	/**
	 * Get the boid's length.
	 * 
	 * @return boid's length
	 */
	public float getLength () {
		return 20;
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
}
