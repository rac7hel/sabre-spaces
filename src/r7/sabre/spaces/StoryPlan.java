package r7.sabre.spaces;

import edu.uky.cs.nil.sabre.HeadPlan;
import edu.uky.cs.nil.sabre.Solution;

/**
 * A story plan is a sequence of {@link StoryAction story actions}.
 * 
 * @author Rachelyn Farrell
 */
public class StoryPlan {
	
	/** The story actions **/
	protected StoryAction[] actions;	
	
	/**
	 * Creates a story plan out of a {@link Solution solution} found by the planner.
	 * 
	 * @param solution the solution this story plan represents
	 */
	public StoryPlan(Solution<?> solution) {
		// Create a new {@link StoryAction story action} for each step in the solution
		actions = new StoryAction[solution.size()];
		for(int i=0; i<actions.length; i++) {
			actions[i] = new StoryAction(solution);
			solution = solution.next();
		}
		// Determine the causal ancestors of each action
		HeadPlan plan = HeadPlan.EMPTY;
		for(int i=actions.length-1; i>=0; i--) 
			plan = plan.prepend(actions[i].action);
		for(int i=0; i<actions.length; i++) {
			for(int j=0; j<i; j++) {
				if(Causality.causalAncestor(actions[j].action, actions[i].action, plan))
					actions[i].ancestors.add(actions[j]);
			}
		}
	}
	
	/**
	 * Returns the action at the given index of this plan.
	 * 
	 * @param i the index
	 * @return the action at index i
	 */
	public StoryAction get(int i) {
		return actions[i];
	}
	
	/**
	 * Returns the number of actions in this plan.
	 * 
	 * @return the number of actions
	 */
	public int size() {
		return actions.length;
	}
}
