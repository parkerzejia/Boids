# Boids
A processing project with basic machine learning knowledge.

CPSC 444 Project 1: Boids
Professor Brigeman
Parker Zejia Guo

	This is a single person project and it simulates a living environment in a large water tank, in which my main class 
  named as WaterTank.java and it contains the starting main. This project use boids to show the complex behavior that 
  the automats agent can behave.

	There are captive ornamental fish in the water tank. We can place fish in the water tank by left clicking on the screen
  anywhere and it would automatic generate fishes that are either color red or color green. There are two kind of fish,
  red fish and blue fish. Red fish is rare and solitary animal and but like to leave other red fish if they see one. Blue
  fish is social animal and would like schooling and shoaling with others. They are also scared about the red fish if they 
  sees one they are more likely to run away because the red fish are big, rare and aggressive, but if they form a school
  already, they are not likely to be scared and not to avoid the red fish.

	We also place some piece of food in the tank regularly for fish to eat and it would be first priority that fish does no 
  matter what kind of fish it is. However, for the physics principle they have to avoid the objects that has been put in the 
  water tank first.  They both need food to survive, thus, they want to eat the food that place into the tank. They both
  consider eating is the most common behavior to do, thus they forget about their living habit and turn to the food first
  if they see the food in the water tank under the condition that they avoid the obstacle.
  The states I have in the program: 
  For Red Fish: Avoid Obstacle(most priority), Eat Food(Second), Leave their own kind, Wander around the Water Tank(default).
  For Green Fish: Avoid Obstacle(most priority), Eat Food(Second), School, Flee from the red fish(when along), Wander around 
  the Water Tank(default).
  The fishes are represented by the boids by giving program that they all in boid shape but with different color. They have
  their own speed and direction, can perceive (only) current position and velocity of other boids – can only see nearby
  things (and have a blind spot). When they detect there are other Reenable things in the sight they would like to do
  the state behavior by themselves, and if the object are not in their sight, then they are not likely to do the behavior 
  and follow the default behavior which is the last behavior they would like to do and the most common one. 

  Why I chose the decision making process I chose is because it is simple to understand in the real life and always makes 
  sense to have two different kind of fishes in the water tank. In this way, I can show  that they contains different habits
  as different kind of fish in order to present the core of artificial intelligence, that they make their own decision on
  which one to do.

  I have implemented and working two brain with 3-4 intentions at least at one brian of mine. I have two kind of brain: 
  one for red fish and other one Is green to green fish. This includes the other elements specified: a priority of behaviors
  and a simple last-task memory that the boid can return to after an interruption that they would go back to wander if there
  is no other interruption.There are a combination of multiple steering behaviors (like schooling/flocking) for the green fish,
  they are social animals and they like to be together. Also there are two substantially different brains (two kind of fish)
  with a total of 6-8 intentions between them. They flee or leave from eachother, they had many behavior that control’s their
  motion. The water tank have a coherent theme, which is an ecosystem simulation in a captive ornamental fish pool. There is 
  persistence of a target of focus, that when green fish forms school that they don’t scare the red fish unless they are alone.
  The brain check if there are any boid around them and then check if there is any things around them, thus,
  the second alternative should be tried if the first fails.








