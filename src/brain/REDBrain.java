package brain;

import arbitrator.SimplePrioritized;
import arbitrator.WeightedAverage;
import behavior.AvoidObstacle;
import behavior.AvoidObstacles;
import behavior.Evade;
import behavior.Forward;
import behavior.Leave;
import behavior.PathFollowing;
import behavior.Seek;
import behavior.Separation;
import behavior.Wander;
import core.Arbitrator;
import core.Behavior;
import core.Boid;
import core.Brain;
import core.Renderable;
import core.World;
import geometry.Obstacle;
import geometry.Path;
import geometry.PolylinePath;
import processing.core.PVector;
import target.BoidTarget;
import target.FixedTarget;
import target.MouseTarget;

/**
 * A simple boid brain - carries out a single action.
 */
public class REDBrain implements Brain {

	/*
	 * (non-Javadoc)
	 * @see core.Brain#getNetSteeringForce(core.Boid, core.World)
	 */
	
	@Override
	public PVector getNetSteeringForce ( Boid boid, World world ) {

		
		/*

		 */
		//avoid obstacle first then turn to eat food first
		for ( Renderable other : world.getThings() ) {
		if( other != boid && boid.isNeighborThing(other)&& other.getNumber()==1 ) {
			//avoid obstacle first
				Behavior avoida = new AvoidObstacle(other, 15);
				return avoida.getSteeringForce(boid,world);
			}else if ( other != boid && boid.isNeighborThing(other)&& other.getNumber()==2 ) {
				//then eat food
				Behavior seek = new Seek(new FixedTarget(other.getPosition()), 15);
				return seek.getSteeringForce(boid,world);
			}
		//then avoid other red fish
		for ( Boid other1 : world.getBoids() ) {
			if ( other1 != boid && boid.isNeighbor(other1) ) {
				if (other1.getNumber_()!=1) {
					Behavior leave = new Leave(new BoidTarget(other1), 2.0f,world.getApplet().color(155,0,255));
					return leave.getSteeringForce(boid,world);
				}else if (other1.getNumber_()!=2) {
					//they don't care blue fish and just wander
					Behavior wander= new Wander(0);
					Behavior separation = new Separation(25);
					WeightedAverage arbitrator = new WeightedAverage();
					arbitrator.addBehavior(separation,2.0f);
					arbitrator.addBehavior(wander,2.0f);
					//arbitrator.addBehavior(wander,15f);
					return arbitrator.getNetSteeringForce(boid,world);
				}else {
					//they don't care anything just wander
					Behavior wander= new Wander(0);
					Behavior separation = new Separation(25);
					WeightedAverage arbitrator = new WeightedAverage();
					arbitrator.addBehavior(separation,2.0f);
					arbitrator.addBehavior(wander,2.0f);
					//arbitrator.addBehavior(wander,15f);
					return arbitrator.getNetSteeringForce(boid,world);
				}
			}
		}
		}
		
		//one behavior that red fish doesn't like to be close to other fish
		WeightedAverage arbitrator = new WeightedAverage();
		Behavior separation = new Separation(25);
		Behavior forward = new Forward(15);
		arbitrator.addBehavior(forward,3);
		arbitrator.addBehavior(separation,2);
		return arbitrator.getNetSteeringForce(boid,world);

	}	
}





