package r7.sabre.spaces;

import java.util.ArrayList;

import edu.uky.cs.nil.sabre.Problem;

/**
 * A story space maintains a list of {@link StoryPlan story plans} for a given {@link Problem problem}.
 * 
 * @author Rachelyn Farrell
 */
public class StorySpace {

	protected Problem problem;
	
	protected ArrayList<StoryPlan> stories;
		
	public StorySpace(Problem problem) {
		this.problem = problem;
		this.stories = new ArrayList<StoryPlan>();
	}

	public int size() {
		return stories.size();
	}
	
	public void add(StoryPlan story) {
		this.stories.add(story);
	}
	
	public StoryPlan get(int i) {
		return stories.get(i);
	}
	
	public ArrayList<StoryPlan> getStories(){
		return stories;
	}
	
}
