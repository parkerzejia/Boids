package arbitrator;

import java.util.PriorityQueue;

import core.Arbitrator;
import core.Behavior;
import core.Boid;
import core.World;
import processing.core.PVector;

/**
 * Combine behaviors using prioritized dithering - behaviors are considered in
 * order of priority, with a probability of being selected. The steering force
 * is the highest-priority selected behavior with a non-zero steering force.
 */

public class PrioritizedDithering implements Arbitrator {

	private PriorityQueue<PriorityBehavior> behaviors_; // applicable behaviors

	/**
	 * Combine behaviors using prioritized dithering - behaviors are considered in
	 * order of priority, with a probability of being selected. The steering force
	 * is the highest-priority selected behavior with a non-zero steering force.
	 */
	public PrioritizedDithering () {
		behaviors_ = new PriorityQueue<PriorityBehavior>();
	}

	/**
	 * Add a behavior with the specified priority and probability of being picked.
	 * Higher values mean a higher priority.
	 * 
	 * @param behavior
	 *          behavior to add
	 * @param priority
	 *          priority for the behavior
	 * @param prob
	 *          probability of selection (0-1)
	 */
	public void addBehavior ( Behavior behavior, int priority, float prob ) {
		behaviors_.add(new PriorityBehavior(behavior,priority,prob));
	}

	/**
	 * Get the net steering force for the specified boid. The magnitude of the
	 * force returned is limited by the boid's max force.
	 * 
	 * @param boid
	 *          the boid
	 * @param world
	 *          the world containing the boid
	 */
	public PVector getNetSteeringForce ( Boid boid, World world ) {
		for ( PriorityBehavior behavior : behaviors_ ) {
			if ( Math.random() > behavior.getProb() ) {
				continue;
			}

			PVector steering = behavior.getSteeringForce(boid,world);
			if ( steering.mag() > .001 ) {
				steering.limit(boid.getMaxForce());

				world.debugVector(World.DEBUG_ARBITRATOR,boid.getPosition(),steering,
				                  50 / boid.getMaxForce(),behavior.behavior_.getColor(),
				                  2);

				return steering;
			}
		}

		return new PVector(0,0);
	}

	/**
	 * Associate a weight with a behavior.
	 */
	class PriorityBehavior implements Comparable<PriorityBehavior> {
		int priority_; // priority of this behavior
		float prob_; // probability of selecting this behavior
		Behavior behavior_; // the behavior

		/**
		 * Create a PriorityBehavior with the specified behavior, priority, and
		 * probability of selection.
		 * 
		 * @param behavior
		 * @param priority
		 * @param prob
		 */
		PriorityBehavior ( Behavior behavior, int priority, float prob ) {
			behavior_ = behavior;
			priority_ = priority;
			prob_ = prob;
		}

		/**
		 * Get the steering force for the specified boid according to this behavior.
		 * 
		 * @param boid
		 *          the boid
		 * @param world
		 *          the world containing the boid
		 * @return the steering force for the specified boid, scaled according to
		 *         its weight
		 */
		PVector getSteeringForce ( Boid boid, World world ) {
			return behavior_.getSteeringForce(boid,world);
		}

		/**
		 * Get the priority of this behavior.
		 * 
		 * @return behavior priority
		 */
		int getPriority () {
			return priority_;
		}

		/**
		 * Get the probability of selecting this behavior.
		 * 
		 * @return probability of selection
		 */
		float getProb () {
			return prob_;
		}

		/*
		 * (non-Javadoc)
		 * @see java.lang.Comparable#compareTo(java.lang.Object)
		 */
		@Override
		public int compareTo ( PriorityBehavior other ) {
			return other.getPriority() - priority_;
		}
	}
}
