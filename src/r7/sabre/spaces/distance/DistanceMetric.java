package r7.sabre.spaces.distance;

import r7.sabre.spaces.StoryPlan;
import r7.sabre.spaces.StorySpace;

/**
 * A distance metric defines the distance between any two {@link StoryPlan story plans} 
 * as a numeric value.
 * 
 * @author Rachelyn Farrell
 */
public abstract class DistanceMetric {
	
	/** A name for the distance metric **/
	public String name;

	/**
	 * Constructs a new distance metric with the given name
	 * 
	 * @param name the name of the distance metric
	 */
	public DistanceMetric(String name) {
		this.name = name;
	}

	/**
	 * Returns a decimal value for the distance between two {@link StoryPlan stories}.
	 * 
	 * @param storyA a story 
	 * @param storyB another story 
	 * @return the distance between storyA and storyB
	 */
	public abstract double getDistance(StoryPlan storyA, StoryPlan storyB);
	
	/**
	 * Creates a {@link DistanceMatrix distance matrix} for the given {@link StorySpace 
	 * stories} using this distance metric for comparison.
	 * 
	 * @param stories the stories to compare
	 * @return a matrix containing pairwise distance values for all the given stories
	 */
	public DistanceMatrix getMatrix(StorySpace stories) {
		return new DistanceMatrix(stories, this);
	}
	
	/**
	 * Returns the name of this distance metric
	 * 
	 * @return the name of the metric
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Initializes the distance metric for a specific set of {@link StorySpace solutions}.
	 * 
	 * @param storySpace the solutions with which to initialize the metric
	 */
	public void initialize(StorySpace storySpace) {}
}
