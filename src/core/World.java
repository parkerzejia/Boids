package core;

import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;
import processing.core.PVector;

/**
 * The world.
 */
public class World {

	private List<Boid> boids_; // boids in the world
	private List<Renderable> things_; // non-boids in the world

	private PApplet applet_; // applet

	public static final int DEBUG_NONE = 0, DEBUG_BOID = 4, DEBUG_BEHAVIOR = 1,
	    DEBUG_ARBITRATOR = 2;
	private int debug_; // debug status

	/**
	 * Create an empty world.
	 */
	public World ( PApplet applet ) {
		boids_ = new ArrayList<Boid>();
		things_ = new ArrayList<Renderable>();
		applet_ = applet;
		debug_ = DEBUG_NONE;
	}

	/**
	 * Get the applet containing this world.
	 * 
	 * @return the applet containing this world
	 */
	public PApplet getApplet () {
		return applet_;
	}

	/**
	 * Add a boid to the world.
	 * 
	 * @param boid
	 *          boid to add
	 */
	public void addBoid ( Boid boid ) {
		boids_.add(boid);
	}

	/**
	 * Get the boids in the world.
	 * 
	 * @return the boids in the world
	 */
	public Iterable<Boid> getBoids () {
		return boids_;
	}

	/**
	 * Add a thing to the world.
	 * 
	 * @param thing
	 *          thing to add
	 */
	public void addThing ( Renderable thing ) {
		things_.add(thing);
	}
	/**
	 * remove a thing to the world.
	 * 
	 * @param thing
	 *          thing to add
	 */
	public void removeThing ( Renderable thing ) {
		things_.remove(thing);
	}

	/**
	 * Get the things in the world.
	 * 
	 * @return the things in the world
	 */
	public Iterable<Renderable> getThings () {
		return things_;
	}

	/**
	 * Get the debug status.
	 * 
	 * @return true if specified debug flag is set, false otherwise
	 */
	public boolean getDebug ( int flag ) {
		return (debug_ & flag) > 0;
	}

	/**
	 * Set the debug status.
	 * 
	 * @param flag
	 *          to set
	 * @param on
	 *          turn flag on if true, false if not
	 */
	public void setDebug ( int flag, boolean on ) {
		if ( on ) {
			debug_ |= flag;
		} else {
			debug_ &= ~flag;
		}
	}

	public void debugVector ( int flag, PVector pos, PVector v, float scale,
	                          int color, float weight ) {
		if ( !getDebug(flag) ) {
			return;
		}

		PVector n = PVector.mult(v,scale);

		applet_.stroke(color);
		applet_.strokeWeight(weight);
		applet_.line(pos.x,pos.y,pos.x + n.x,pos.y + n.y);

		applet_.strokeWeight(1);
	}

	public void debugLine ( int flag, PVector pos1, PVector pos2, int color,
	                        float weight ) {
		if ( !getDebug(flag) ) {
			return;
		}

		applet_.stroke(color);
		applet_.strokeWeight(weight);
		applet_.line(pos1.x,pos1.y,pos2.x,pos2.y);

		applet_.strokeWeight(1);
	}

	public void debugPoint ( int flag, PVector pos, int color, float size ) {
		if ( !getDebug(flag) ) {
			return;
		}

		applet_.stroke(color);
		applet_.fill(color);
		applet_.ellipse(pos.x,pos.y,size,size);
	}

	public void debugCircle ( int flag, PVector pos, float radius, int color ) {
		if ( !getDebug(flag) ) {
			return;
		}

		applet_.stroke(color);
		applet_.noFill();
		applet_.ellipseMode(PApplet.CENTER);
		applet_.ellipse(pos.x,pos.y,radius * 2,radius * 2);
	}
}