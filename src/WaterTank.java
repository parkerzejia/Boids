
/**
 * @author zg6197
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import arbitrator.PrioritizedAccelAlloc;
import arbitrator.PrioritizedDithering;
import arbitrator.SimplePrioritized;
import arbitrator.WeightedAverage;
import behavior.Alignment;
import behavior.Cohesion;
import behavior.Forward;
import behavior.Separation;
import brain.REDBrain;
import brain.GREENBrain;
import core.Behavior;
import core.Boid;
import geometry.CircularObstacle;
import geometry.Path;
import geometry.PolylinePath;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;

/**
 * Flocking.
 */
public class WaterTank extends BoidsCore {

	private boolean definepath_; // in define-a-path mode
	private PolylinePath curpath_; // current path being drawn

	protected Path path_; // the path to use
	BoidsCore core;

	public void setup () {
		super.setup();
		definepath_ = false;
		curpath_ = null;
	}

	/*
	 * (non-Javadoc)
	 * @see Boids#makeBoid()
	 */
	@Override
	public void makeBoid () {

		// a brain for action selection for the fishes
		GREENBrain fishbrain = new GREENBrain();
		REDBrain redbrain = new REDBrain();
		
		// make the red fish and the green fish and added them to the world
		Boid greenboid =
		    new Boid(world_,new PVector(mouseX,mouseY),1,PVector.random2D(),0.05f,2,
		             60,radians(125),fishbrain,color(0,255,0),1);
		Boid redboid =
		    new Boid(world_,new PVector(mouseX,mouseY),1,PVector.random2D(),0.05f,2,
		             60,radians(125),redbrain,color(255,0,0),2);
		CircularObstacle food =
		    new CircularObstacle(world_,new PVector(mouseX,mouseY),10, color(255,102,102),2);
		CircularObstacle ob =
		    new CircularObstacle(world_,new PVector(mouseX,mouseY),20, color(255,255,255),1);
	//	number >= 0.3
		// random generate fish or shark
		double number = Math.random();
		if ( number >= 0.3 && mouseButton == LEFT) {
			world_.addBoid(greenboid);
		}else if( number < 0.3 && mouseButton == LEFT){
			world_.addBoid(redboid);			
		}else if(number >= 0.5 &&mouseButton == RIGHT){
			world_.addThing(food);
		}else if(number < 0.5 &&mouseButton == RIGHT){
			world_.addThing(ob);
		}
	}

	
	//name of the screen
	public static void main ( String[] args ) {
		PApplet.main("WaterTank");
	}

	
	
	public void draw () {
		int max = 800;
		int min = 100;
		int x = (int) ((Math.random() * ((max - min) + 1)) + min);
		int y = (int) ((Math.random() * ((max - min) + 1)) + min);
		super.draw();
		fill(0,255,0);
		if ( definepath_ ) {
			stroke(255,0,0);
			strokeWeight(5);
			rect(0,0,width,height);
			strokeWeight(1);
		} 
	}

	public void mousePressed () {
		if ( definepath_ ) {
			if ( mouseButton == LEFT ) {
				if ( curpath_ == null ) {
					curpath_ = new PolylinePath(world_,20);
					world_.addThing(curpath_);
				}
				curpath_.addVertex(mouseX,mouseY);
			} else {
				definepath_ = false;
				path_ = curpath_;
				curpath_ = null;
			}
		} else {
			super.mousePressed();
		}
	}

	public void keyPressed () {
		if ( key == 'P' ) {
			if ( definepath_ ) {
				definepath_ = false;
				world_.addThing(curpath_);
				path_ = curpath_;
				curpath_ = null;
			} else {
				definepath_ = true;
				curpath_ = null;
			}
		} else {
			super.keyPressed();
		}
	}
	// public void draw () {
	// super.draw();
	// stroke(155,0,155);
	// noFill();
	// strokeWeight(5);
	// rect(5,5,20,20);
	// strokeWeight(1);
	// }

	// public void mousePressed () {
	// if ( mouseButton == RIGHT ) {
	// super.draw();
	// stroke(155,0,155);
	// noFill();
	// strokeWeight(5);
	// rect(5,5,20,20);
	// strokeWeight(1);
	// }
}
