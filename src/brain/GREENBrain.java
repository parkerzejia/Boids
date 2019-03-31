package brain;

import arbitrator.WeightedAverage;
import behavior.Alignment;
import behavior.AvoidObstacle;
import behavior.AvoidObstacles;
import behavior.Cohesion;
import behavior.Flee;
import behavior.Forward;
import behavior.Leave;
import behavior.Seek;
import behavior.Separation;
import behavior.Wander;
import core.Arbitrator;
import core.Behavior;
import core.Boid;
import core.Brain;
import core.Renderable;
import core.World;
import processing.core.PVector;
import target.BoidTarget;
import target.FixedTarget;

/**
 * A simple boid brain - carries out a single action.
 */
public class GREENBrain implements Brain {


	/*
	 * (non-Javadoc)
	 * @see core.Brain#getNetSteeringForce(core.Boid, core.World)
	 */
	@Override
	public PVector getNetSteeringForce ( Boid boid, World world) {
		// go though all the things
		for ( Renderable other : world.getThings() ) {
			if( other != boid && boid.isNeighborThing(other)&& other.getNumber()==1 ) {
				// they aviod obstacle first
			Behavior avoida = new AvoidObstacle(other, 15);
			return avoida.getSteeringForce(boid,world);
			
			}else if ( other != boid && boid.isNeighborThing(other)&& other.getNumber()==2 ) {
				//then eat food as the priority other than their habit
				Behavior seek = new Seek(new FixedTarget(other.getPosition()), 15);
				return seek.getSteeringForce(boid,world);
		
				
			}else{
				for ( Boid other1 : world.getBoids() ) {
					if ( other1 != boid && boid.isNeighbor(other1) ) {
						if (other1.getNumber_()!=2) {
							// school behaviors, they love to group, and they like to school to avoid red fish
							Behavior separation = new Separation(0);
							Behavior cohesion = new Cohesion(0);
							Behavior alignment = new Alignment(0);
							Behavior forward = new Forward(0);
							WeightedAverage arbitrator = new WeightedAverage();
							arbitrator.addBehavior(separation,5.0f);
							arbitrator.addBehavior(cohesion,5.0f);
							arbitrator.addBehavior(forward,1.0f);
							arbitrator.addBehavior(alignment,4.0f);
							return arbitrator.getNetSteeringForce(boid,world);
						}else if (other1.getNumber_()!=1) {
							//they are scared about the red fish becausse they are scary
							Behavior flee = new Flee(new BoidTarget(other1), world.getApplet().color(155,0,255));
							return flee.getSteeringForce(boid,world);
						}else {
							WeightedAverage wander = new WeightedAverage();
							return wander.getNetSteeringForce(boid,world);
						}
					}
				}
				
				/*
			else if(!boid.isNeighborThing(other)&&other != boid ){
				
				Behavior wander= new Wander(0);
				Behavior forward = new Forward(0);
				WeightedAverage arbitrator = new WeightedAverage();
				arbitrator.addBehavior(forward,1.0f);
				arbitrator.addBehavior(wander,2.0f);
				return arbitrator.getNetSteeringForce(boid,world);
             */
			}
			
		}
		//WeightedAverage wander = new WeightedAverage();
		//return wander.getNetSteeringForce(boid,world);
		// school behaviors, they love to group, and they like to school to avoid red fish
		Behavior separation = new Separation(0);
		Behavior cohesion = new Cohesion(0);
		Behavior alignment = new Alignment(0);
		Behavior forward = new Forward(0);
		WeightedAverage arbitrator = new WeightedAverage();
		arbitrator.addBehavior(separation,5.0f);
		arbitrator.addBehavior(cohesion,5.0f);
		arbitrator.addBehavior(forward,1.0f);
		arbitrator.addBehavior(alignment,4.0f);
		return arbitrator.getNetSteeringForce(boid,world);
	}
	
}
