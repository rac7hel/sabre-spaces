package r7.sabre.spaces;

import java.util.ArrayList;

import edu.uky.cs.nil.sabre.Problem;

/**
 * A story space maintains a list of {@link StoryPlan story plans} for a given {@link Problem problem}.
 * 
 * @author Rachelyn Farrell
 */
public class StorySpace {

	/** The planning problem **/
	protected Problem problem;
	
	/** The list of story plans that solve the problem **/
	protected ArrayList<StoryPlan> stories;
	
	/**
	 * Creates a new story space for a given problem.
	 * 
	 * @param problem the problem
	 */
	public StorySpace(Problem problem) {
		this.problem = problem;
		this.stories = new ArrayList<StoryPlan>();
	}

	/**
	 * Returns the number of {@link StoryPlan stories} in the list.
	 * 
	 * @return the number of stories
	 */
	public int size() {
		return stories.size();
	}
	
	/**
	 * Adds a new {@link StoryPlan story plan} to the list.
	 * 
	 * @param story the story to add
	 */
	public void add(StoryPlan story) {
		this.stories.add(story);
	}
	
	/**
	 * Returns the {@link StoryPlan story} at the given index in the list.
	 * 
	 * @param i the index
	 * @return the story at index i
	 */
	public StoryPlan get(int i) {
		return stories.get(i);
	}
	
	/**
	 * Returns the list of {@link StoryPlan stories}.
	 * 
	 * @return the list of stories
	 */
	public ArrayList<StoryPlan> getStories(){
		return stories;
	}
		
}
