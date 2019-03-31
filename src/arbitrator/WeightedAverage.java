package arbitrator;

import java.util.ArrayList;
import java.util.List;

import core.Arbitrator;
import core.Behavior;
import core.Boid;
import core.World;
import processing.core.PVector;

/**
 * Weighted sum - the net steering force is a weighted average of individual
 * steering forces.
 */

public class WeightedAverage implements Arbitrator {

	private List<WeightedBehavior> behaviors_; // applicable behaviors

	/**
	 * Weighted sum - the net steering force is a weighted average of individual
	 * steering forces.
	 */
	public WeightedAverage () {
		behaviors_ = new ArrayList<WeightedBehavior>();
	}

	/**
	 * Add a behavior with the specified weight.
	 * 
	 * @param behavior
	 *          behavior to add
	 * @param weight
	 *          weight for the behavior
	 */
	public void addBehavior ( Behavior behavior, float weight ) {
		behaviors_.add(new WeightedBehavior(behavior,weight));
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
		for ( WeightedBehavior behavior : behaviors_ ) {
			PVector steering = behavior.getSteeringForce(boid,world);
			steering.mult(behavior.weight_);

			world.debugVector(World.DEBUG_ARBITRATOR,boid.getPosition(),steering,
			                  50 / boid.getMaxForce(),behavior.behavior_.getColor(),
			                  1);

			total.add(steering);
		}
		total.limit(boid.getMaxForce());

		world.debugVector(World.DEBUG_ARBITRATOR,boid.getPosition(),total,
		                  50 / boid.getMaxForce(),world.getApplet().color(0),2);

		return total;
	}

	/**
	 * Associate a weight with a behavior.
	 */
	class WeightedBehavior {
		float weight_; // weight applied to this behavior steering force
		Behavior behavior_; // the behavior

		/**
		 * Create a WeightedBehavior with the specified behavior and weight.
		 * 
		 * @param behavior
		 * @param weight
		 */
		WeightedBehavior ( Behavior behavior, float weight ) {
			behavior_ = behavior;
			weight_ = weight;
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
			return PVector.mult(behavior_.getSteeringForce(boid,world),weight_);
		}
	}
}
