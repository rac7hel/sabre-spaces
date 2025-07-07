package r7.sabre.spaces.distance;

import edu.uky.cs.nil.sabre.Solution;

/**
 * A distance metric defines the distance between any two {@link Solution solutions} 
 * (stories) as a numeric value.
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
	 * Returns a decimal value for the distance between two stories.
	 * 
	 * @param storyA a solution
	 * @param storyB another solution
	 * @return the distance between storyA and storyB
	 */
	public abstract double getDistance(Solution<?> storyA, Solution<?> storyB);
		
}
