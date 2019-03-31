package geometry;

import core.Renderable;
import core.World;

/**
 * A region of space.
 */
public abstract class Region implements Renderable {

	protected World world_;

	/**
	 * Create a region.
	 */
	protected Region ( World world ) {
		world_ = world;
	}

}
