package r7.sabre.spaces.distance;


import java.util.ArrayList;
import java.util.LinkedHashSet;

import edu.uky.cs.nil.sabre.Action;
import edu.uky.cs.nil.sabre.Character;
import edu.uky.cs.nil.sabre.Entity;
import edu.uky.cs.nil.sabre.Problem;
import edu.uky.cs.nil.sabre.Type;
import r7.sabre.spaces.CharacterGoal;
import r7.sabre.spaces.SalienceVector;
import r7.sabre.spaces.StoryAction;
import r7.sabre.spaces.StoryPlan;
import r7.sabre.spaces.StorySpace;

public class SalienceDistance extends DistanceMetric {

	protected final double[] DEFAULT_INDEX_WEIGHTS = new double[] { 0.2, 0.2, 0.2, 0.2, 0.2 };
	
	protected final double DECAY_CONSTANT = 0.5;
	
	protected Character[] characters;
	
	protected Action[] actions;

	protected ArrayList<Entity> places = new ArrayList<>();
	
	protected ArrayList<Entity> times = new ArrayList<>();
	
	protected CharacterGoal[] goals;
	
	public SalienceDistance(Problem problem) {
		super("salience");
		characters = new Character[problem.universe.characters.size()];
		int j=0;
		for(Character c : problem.universe.characters) {
			characters[j] = c;
			j++;
		}
		actions = new Action[problem.actions.size()];
		for(int i=0; i<actions.length; i++)
			actions[i] = problem.actions.get(i);
		Type place = problem.universe.getType("place");
		Type time = problem.universe.getType("time");
		for(Entity entity : problem.universe.entities) {
			if(entity.is(place))
				places.add(entity);
			else if(entity.is(time))
				times.add(entity);				
		}
	}

	@Override
	public void initialize(StorySpace storySpace) {
		LinkedHashSet<CharacterGoal> intentions = new LinkedHashSet<>();
		for(StoryPlan story : storySpace.getStories()) {
			for(int i=0; i<story.size(); i++) {
				for(CharacterGoal g : story.get(i).getGoals()) 
					intentions.add(g);
			}
		}
		goals = new CharacterGoal[intentions.size()];
		int i=0;
		for(CharacterGoal g : intentions) {
			goals[i] = g;
			i++;
		}
	}
	
	/**
	 * Returns the salience distance between two story plans using the default index weights.
	 * 
	 * @param storyA a story
	 * @param storyB another story
	 * @return the salience distance between the given stories
	 */
	@Override
	public double getDistance(StoryPlan storyA, StoryPlan storyB) {
		return getDistance(storyA, storyB, DEFAULT_INDEX_WEIGHTS);
	}

	/**
	 * Returns the salience distance between two story plans using the given index weights.
	 * @param storyA
	 * @param storyB
	 * @param weights
	 * @return
	 */
	public double getDistance(StoryPlan storyA, StoryPlan storyB, double[] weights) {
		SalienceVector vectorA = getSalienceVector(storyA);
		SalienceVector vectorB = getSalienceVector(storyB);
		return getDistance(vectorA, vectorB, weights);
	}
	
	/**
	 * Returns the salience distance between two stories given their salience vectors and index weights
	 * @param salienceVecA
	 * @param salienceVecB
	 * @param weights
	 * @return
	 */
	protected double getDistance(SalienceVector salienceVecA, SalienceVector salienceVecB, double[] weights) {
		// Sum from n=1:5 of (w_n * d_n),
		// where d_1_5 are the euclidean distances between the two character, time, place, goal, and action vectors
		double d0 = normSqrdEuclideanDistance(salienceVecA.characters, salienceVecB.characters);
		double d1 = normSqrdEuclideanDistance(salienceVecA.places, salienceVecB.places);
		double d2 = normSqrdEuclideanDistance(salienceVecA.times, salienceVecB.times);
		double d3 = normSqrdEuclideanDistance(salienceVecA.actions, salienceVecB.actions);
		double d4 = normSqrdEuclideanDistance(salienceVecA.goals, salienceVecB.goals);
		return (weights[0] * d0) + (weights[1] * d1) + (weights[2] * d2) + (weights[3] * d3) + (weights[4] * d4);
	}
	
	/* 
	 * The normalized squared euclidean distance 
	 * gives the squared distance between two vectors whose lengths have been scaled to have unit norm. 
	 * This is helpful when the direction of the vector is meaningful but the magnitude is not.
	 */
	double normSqrdEuclideanDistance(double[] v1, double[] v2) {
		if(v1.length != v2.length)
			throw new RuntimeException("Oops! Salience Vector sizes are not equal but should be");
		double sum = 0.0;
        for(int i=0; i<v1.length; i++)
           sum = sum + Math.pow((v1[i]-v2[i]),2.0);
        return 0.5 * Math.sqrt(sum);

	}
	
	protected SalienceVector getSalienceVector(StoryPlan story) {
		SalienceVector salienceVector = new SalienceVector(characters.length, places.size(), times.size(), actions.length, goals.length);
		for(int i=0; i<story.size(); i++) {
			StoryAction action = story.get(i);
			// characters
			for(int j=0; j<characters.length; j++) {
				if(action.salient(characters[j]))
					salienceVector.characters[j] = 1;
				else
					salienceVector.characters[j] *= DECAY_CONSTANT;
			}
			// places
			for(int j=0; j<places.size(); j++) {
				if(action.salient(places.get(j)))
					salienceVector.places[j] = 1;
				else
					salienceVector.places[j] *= DECAY_CONSTANT;
			}
			// times
			for(int j=0; j<times.size(); j++) {
				if(action.salient(times.get(j)))
					salienceVector.times[j] = 1;
				else
					salienceVector.times[j] *= DECAY_CONSTANT;
			}
			// actions
			for(int j=0; j<actions.length; j++) {
				if(action.salient(actions[j]))
					salienceVector.actions[j] = 1;
				else
					salienceVector.actions[j] *= DECAY_CONSTANT;
			}
			// intentions
			for(int j=0; j<goals.length; j++) {
				if(action.salient(goals[j]))
					salienceVector.goals[j] = 1;
				else
					salienceVector.goals[j] *= DECAY_CONSTANT;
			}
		}
		return salienceVector;
	}
}
