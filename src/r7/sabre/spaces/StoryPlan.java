package r7.sabre.spaces;

import edu.uky.cs.nil.sabre.Solution;

/**
 * A story plan is a sequence of {@link StoryAction story actions}.
 * 
 * @author Rachelyn Farrell
 */
public class StoryPlan {
	
	protected StoryAction[] actions;	
	
	/**
	 * Creates an array of {@link StoryAction story actions} given a 
	 * {@link Solution solution} found by the planner.
	 * 
	 * @param solution the solution
	 */
	public StoryPlan(Solution<?> solution) {
		actions = new StoryAction[solution.size()];
		for(int i=0; i<actions.length; i++) {
			actions[i] = new StoryAction(solution);
			solution = solution.next();
		}
		// TODO: for each action, set its causal ancestors 
	}
	
	public StoryAction get(int i) {
		return actions[i];
	}
	
	public int size() {
		return actions.length;
	}
}
