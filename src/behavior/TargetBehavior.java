package behavior;

import core.Behavior;
import core.Target;

/**
 * A behavior involving a target.
 */
public abstract class TargetBehavior extends Behavior {

	protected Target target_; // target to arrive at

	/**
	 * Create a behavior involving the specified target.
	 * 
	 * @param target
	 *          target
	 * @param c
	 *          color to display steering vector (for debug mode)
	 */
	public TargetBehavior ( Target target, int c ) {
		super(c);
		target_ = target;
	}

	/**
	 * Get this behavior's target.
	 * 
	 * @return the target
	 */
	public Target getTarget () {
		return target_;
	}
}