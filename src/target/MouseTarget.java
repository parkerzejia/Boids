package target;

import core.Target;
import processing.core.PApplet;
import processing.core.PVector;

/**
 * The mouse position as a target.
 */
public class MouseTarget implements Target {

	private PApplet applet_;

	/**
	 * Create a target which is the mouse position.
	 * 
	 * @param applet
	 */
	public MouseTarget ( PApplet applet ) {
		applet_ = applet;
	}

	/**
	 * Get the position of the target.
	 * 
	 * @return target position
	 */
	public PVector getPosition () {
		return new PVector(applet_.mouseX,applet_.mouseY);
	}

	/**
	 * Get the velocity of the target. This is based on the mouse movement between
	 * its previous position and its current position as recorded by Processing;
	 * these positions may not match what has been returned by the last two calls
	 * to getPosition().
	 * 
	 * @return target velocity
	 */
	public PVector getVelocity () {
		return new PVector(applet_.mouseX - applet_.pmouseX,
		                   applet_.mouseY - applet_.pmouseY);
	}

}