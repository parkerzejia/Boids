package arbitrator;

import java.util.PriorityQueue;

import core.Arbitrator;
import core.Behavior;
import core.Boid;
import core.World;
import processing.core.PVector;

/**
 * Combine behaviors using prioritized acceleration allocation - behaviors are
 * considered in order of priority and added until the boid's maximum force is
 * reached.
 */

public class PrioritizedAccelAlloc implements Arbitrator {

	private PriorityQueue<PriorityBehavior> behaviors_; // applicable behaviors

	/**
	 * Combine behaviors using prioritized acceleration allocation - behaviors are
	 * considered in order of priority and added until all of the boid's
	 * acceleration has been allocated.
	 */
	public PrioritizedAccelAlloc () {
		behaviors_ = new PriorityQueue<PriorityBehavior>();
	}

	/**
	 * Add a behavior with the specified priority and maximum allocation fraction. Higher
	 * values mean a higher priority.
	 * 
	 * @param behavior
	 *          behavior to add
	 * @param priority
	 *          priority for the behavior (higher values = higher priority)
	 * @param maxalloc
	 *          allocate at most this much of the boid's acceleration to this
	 *          behavior (range is 0-1)
	 */
	public void addBehavior ( Behavior behavior, int priority, float maxalloc ) {
		behaviors_.add(new PriorityBehavior(behavior,priority,maxalloc));
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
		PVector total = new PVector(0,0);
		float left = boid.getMaxForce();

		for ( PriorityBehavior behavior : behaviors_ ) {
			PVector steering = behavior.getSteeringForce(boid,world);

			world.debugVector(World.DEBUG_ARBITRATOR,boid.getPosition(),steering,
			                  50 / boid.getMaxForce(),world.getApplet()
			                      .color(behavior.behavior_.getColor(),50),
			                  5);

			float allocation =
			    Math.min(left,behavior.getAllocation() * boid.getMaxForce());
			steering.limit(allocation);

			world.debugVector(World.DEBUG_ARBITRATOR,boid.getPosition(),steering,
			                  50 / boid.getMaxForce(),behavior.behavior_.getColor(),
			                  1);

			total.add(steering);
			left -= steering.mag();

			if ( left <= 0 ) {
				break;
			}
		}

		total.limit(boid.getMaxForce());
		world.debugVector(World.DEBUG_ARBITRATOR,boid.getPosition(),total,
		                  50 / boid.getMaxForce(),world.getApplet().color(0),2);
		return total;
	}

	/**
	 * Associate a weight with a behavior.
	 */
	class PriorityBehavior implements Comparable<PriorityBehavior> {
		int priority_; // priority of this behavior
		float alloc_; // allocation for this behavior's steering vector (0-1)
		Behavior behavior_; // the behavior

		/**
		 * Create a PriorityBehavior with the specified behavior, priority, and
		 * allocation.
		 * 
		 * @param behavior
		 * @param priority
		 * @param allocation
		 */
		PriorityBehavior ( Behavior behavior, int priority, float allocation ) {
			behavior_ = behavior;
			priority_ = priority;
			alloc_ = allocation;
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
		 * Get the allocation for this behavior.
		 * 
		 * @return allocation
		 */
		float getAllocation () {
			return alloc_;
		}

		/**
		 * @return the behavior
		 */
		Behavior getBehavior () {
			return behavior_;
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
