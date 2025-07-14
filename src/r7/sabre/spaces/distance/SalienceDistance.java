package r7.sabre.spaces.distance;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.LinkedHashSet;

import edu.uky.cs.nil.sabre.Action;
import edu.uky.cs.nil.sabre.Character;
import edu.uky.cs.nil.sabre.Entity;
import edu.uky.cs.nil.sabre.Problem;
import edu.uky.cs.nil.sabre.Type;
import edu.uky.cs.nil.sabre.comp.CompiledAction;
import r7.sabre.spaces.CharacterGoal;
import r7.sabre.spaces.SalienceVector;
import r7.sabre.spaces.StoryAction;
import r7.sabre.spaces.StoryPlan;
import r7.sabre.spaces.StorySpace;

/**
 * The salience distance between two stories measures the distance between their 
 * respective {@link SalienceVector salience vectors}. Salience vectors have five
 * indices, representing the story's characters, places, times, actions, and goals. 
 * The overall distance between two salience vectors is a weighted sum of the
 * individual Euclidean distances between each corresponding pair of partial vectors. 
 * 
 * @author Rachelyn Farrell
 */
public class SalienceDistance extends DistanceMetric {

	/** The default weights of the five salience indices: character, place, time, causality, intentionality **/
	protected final double[] DEFAULT_INDEX_WEIGHTS = new double[] { 0.2, 0.2, 0.2, 0.2, 0.2 };
	
	/** The factor by which salience decays at each step **/
	protected final double DECAY_CONSTANT = 0.5;
	
	/** The list of {@link Character characters} defined in the planning problem **/
	protected Character[] characters;
	
	/** The list of {@link CompiledAction actions} that appear in the {@link StorySpace solutions} **/
	protected CompiledAction[] actions;

	/** The list of places defined in the panning problem **/
	protected ArrayList<Entity> places = new ArrayList<>();
	
	/** The list of time frames defined in the planning problem **/
	protected ArrayList<Entity> times = new ArrayList<>();
	
	/** The list of character goals that appear in the explanations of actions in the solution **/
	protected CharacterGoal[] goals;

	/**
	 * Creates a new salience distance metric for a given problem. 
	 * 
	 * @param problem the problem
	 */
	public SalienceDistance(Problem problem) {
		super("salience");
		characters = new Character[problem.universe.characters.size()];
		int j=0;
		for(Character c : problem.universe.characters) {
			characters[j] = c;
			j++;
		}
		Type place = problem.universe.getType("place");
		Type time = problem.universe.getType("time");
		for(Entity entity : problem.universe.entities) {
			if(entity.is(place))
				places.add(entity);
			else if(entity.is(time))
				times.add(entity);				
		}
	}

	/** Populates the lists of goals and actions that appear in the {@link StorySpace story space} **/
	@Override
	public void initialize(StorySpace storySpace) {
		LinkedHashSet<CompiledAction> uniqueActions = new LinkedHashSet<>();
		LinkedHashSet<CharacterGoal> uniqueGoals = new LinkedHashSet<>();
		for(StoryPlan story : storySpace.getStories()) {
			for(int i=0; i<story.size(); i++) {
				uniqueActions.add(story.get(i).getAction());
				for(CharacterGoal g : story.get(i).getGoals())
					uniqueGoals.add(g);
			}
		}
		goals = new CharacterGoal[uniqueGoals.size()];
		int i=0;
		for(CharacterGoal g : uniqueGoals) {
			goals[i] = g;
			i++;
		}
		actions = new CompiledAction[uniqueActions.size()];
		i=0;
		for(CompiledAction a : uniqueActions) {
			actions[i] = a;
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
	 * Returns the salience distance between two story plans using the given weights
	 * for the five indices (character, place, time, causality, intentionality).
	 * 
	 * @param storyA a story
	 * @param storyB another story
	 * @param weights the relative weights for the five indices
	 * @return the salience distance between the given stories
	 */
	public double getDistance(StoryPlan storyA, StoryPlan storyB, double[] weights) {
		SalienceVector vectorA = getSalienceVector(storyA);
		SalienceVector vectorB = getSalienceVector(storyB);
		return getDistance(vectorA, vectorB, weights);
	}
	
	/**
	 * Returns the weighted sum of the individual Euclidean distances between the 
	 * corresponding components of the two given {@link SalienceVector salience vectors}.
	 * 
	 * @param salienceVecA the salience vector for a story
	 * @param salienceVecB the salience vector for another story
	 * @param weights the relative weights for the five salience indices
	 * @return the salience distance between the two vectors
	 */
	protected double getDistance(SalienceVector salienceVecA, SalienceVector salienceVecB, double[] weights) {
		double d0 = normSqrdEuclideanDistance(salienceVecA.characters, salienceVecB.characters);
		double d1 = normSqrdEuclideanDistance(salienceVecA.places, salienceVecB.places);
		double d2 = normSqrdEuclideanDistance(salienceVecA.times, salienceVecB.times);
		double d3 = normSqrdEuclideanDistance(salienceVecA.actions, salienceVecB.actions);
		double d4 = normSqrdEuclideanDistance(salienceVecA.goals, salienceVecB.goals);
		return (weights[0] * d0) + (weights[1] * d1) + (weights[2] * d2) + (weights[3] * d3) + (weights[4] * d4);
	}
	
	/**
	 * Returns the normalized squared Euclidean distance between two vectors, i.e. 
	 * their directions are meaningful but their magnitudes are not.
	 * 
	 * @param vectorA a numeric vector
	 * @param vectorB another numeric vector
	 * @return the normalized squared Euclidean distance between the two vectors
	 */
	protected double normSqrdEuclideanDistance(double[] vectorA, double[] vectorB) {
		if(vectorA.length != vectorB.length)
			throw new RuntimeException("Vector sizes are not equal but should be.");
		double sum = 0.0;
        for(int i=0; i<vectorA.length; i++)
           sum = sum + Math.pow((vectorA[i]-vectorB[i]),2.0);
        return 0.5 * Math.sqrt(sum);
	}

	/**
	 * Returns a new {@link SalienceVector salience vector} for the given {@link StoryPlan story plan}.
	 * 
	 * @param story the story 
	 * @return the salience vector for the story
	 */
	protected SalienceVector getSalienceVector(StoryPlan story) {
		SalienceVector salienceVector = new SalienceVector(characters.length, places.size(), times.size(), actions.length, goals.length);
		for(int i=0; i<story.size(); i++) {
			StoryAction action = story.get(i);
			// character
			for(int j=0; j<characters.length; j++) {
				if(action.hasConsentingCharacter(characters[j]))
					salienceVector.characters[j] = 1;
				else
					salienceVector.characters[j] *= DECAY_CONSTANT;
			}
			// place
			for(int j=0; j<places.size(); j++) {
				if(action.involvesEntity(places.get(j)))
					salienceVector.places[j] = 1;
				else
					salienceVector.places[j] *= DECAY_CONSTANT;
			}
			// time
			for(int j=0; j<times.size(); j++) {
				if(action.involvesEntity(times.get(j)))
					salienceVector.times[j] = 1;
				else
					salienceVector.times[j] *= DECAY_CONSTANT;
			}
			// causality
			for(int j=0; j<actions.length; j++) {
				if(action.hasCausalAncestor(actions[j]))
					salienceVector.actions[j] = 1;
				else
					salienceVector.actions[j] *= DECAY_CONSTANT;
			}
			// intentionality
			for(int j=0; j<goals.length; j++) {
				if(action.isExplainedBy(goals[j]))
					salienceVector.goals[j] = 1;
				else
					salienceVector.goals[j] *= DECAY_CONSTANT;
			}
		}
		return salienceVector;
	}
	
	/**
	 * Writes a CSV file of the salience vectors for the stories in the given {@link StorySpace story space}. 
	 * 
	 * @param stories the story space
	 * @param path where to create the file
	 * @throws FileNotFoundException if the file cannot be created
	 */
	public void printVectors(StorySpace stories, String path) throws FileNotFoundException {
		PrintStream csvStream = new PrintStream(new File(path + "/salience-vectors.csv"));
		csvStream.print("Story ID," + getVectorColumns());
		for(int i=0; i<stories.size(); i++)
			csvStream.print("\n" + i + "," + getSalienceVector(stories.get(i)));
		csvStream.close();
	}
	
	/**
	 * Returns the salience vector column names as a string.
	 * 
	 * @return the string of column names
	 */
	public String getVectorColumns() {
		String s = "";
		for(Character c : characters)
			s += c + ",";
		for(Entity place : places)
			s += place + ",";
		for(Entity time : times)
			s += time + ",";
		for(Action action : actions)
			s += action.toString().replaceAll(","," ") + ",";
		for(CharacterGoal goal : goals)
			s += goal.toString().replaceAll(","," ") + ",";
		return s.substring(0, s.length()-1);
	}
}
