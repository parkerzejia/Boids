package core;

import processing.core.PVector;

/**
 * A renderable item is something that can be drawn.
 */
public interface Renderable {



	/**
	 * Draw this thing on the screen.
	 */
	public void render ();

	/**
	 * @return
	 */
	public PVector getPosition ();

	/**
	 * @return
	 */
	public int getNumber ();


}
