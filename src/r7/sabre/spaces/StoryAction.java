package r7.sabre.spaces;

import java.util.ArrayList;

import edu.uky.cs.nil.sabre.Action;
import edu.uky.cs.nil.sabre.Character;
import edu.uky.cs.nil.sabre.Entity;
import edu.uky.cs.nil.sabre.Solution;
import edu.uky.cs.nil.sabre.comp.CompiledAction;
import edu.uky.cs.nil.sabre.logic.Parameter;

/**
 * A story action represents an {@link CompiledAction action} in the context
 * of a specific story, and includes information obtained from the solution
 * such as which goals explained the action and which actions enabled it.
 * 
 * @author Rachelyn Farrell
 */
public class StoryAction {
	
	/** The action **/
	protected CompiledAction action;
	
	/** The goals that motivated this action **/
	protected CharacterGoal[] goals;
	
	/** The previous actions that enabled this action **/
	protected ArrayList<StoryAction> ancestors = new ArrayList<>();
	
	/**
	 * Creates a story action for the first step of the given {@link Solution solution}.
	 * 
	 * @param solution the solution with this action as the first step.
	 */
	public StoryAction(Solution<?> solution) {
		this.action = ((CompiledAction)solution.get(0));
		goals = new CharacterGoal[action.consenting.size()];
		for(int i=0; i<goals.length; i++)
			goals[i] = new CharacterGoal(action.consenting.get(i), solution.getExplanation(action.consenting.get(i)).getGoal());
	}
	
	/**
	 * Returns the action
	 * 
	 * @return the action
	 */
	public CompiledAction getAction() {
		return action;
	}
	
	/**
	 * Returns the list of character goals found in the explanations for this action.
	 * 
	 * @return the goals that explained this action
	 */
	public CharacterGoal[] getGoals() {
		return goals;
	}

	/**
	 * Returns true if the given character participated willfully in this action.
	 * 
	 * @param character the character
	 * @return true if this is one of the action's consenting characters
	 */
	public boolean hasConsentingCharacter(Character character) {
		return action.consenting.contains(character);
	}

	/**
	 * Returns true if the given goal appears in the explanations for this action.
	 * 
	 * @param goal the goal
	 * @return true if this action was explained by this goal
	 */
	public boolean isExplainedBy(CharacterGoal goal) {
		for(CharacterGoal g : goals) {
			if(g.equals(goal))
				return true;
		}
		return false;
	}
	
	/**
	 * Returns true if the given action is a causal ancestor of this action, or is the action itself.
	 * 
	 * @param action an action to test
	 * @return true if the given action is the same as this action or one of its causal ancestors
	 */
	public boolean hasCausalAncestor(Action action) {
		if(this.action.equals(action))
			return true;
		for(StoryAction ancestor : ancestors) {
			if(ancestor.action.equals(action))
				return true;
		}
		return false;
	}
		
	/**
	 * Returns true if the given entity appears in the parameters of this action.
	 * 
	 * @param entity the entity
	 * @return true if the entity is one of this action's parameters
	 */
	public boolean involvesEntity(Entity entity) {
		for(Parameter p : action.signature.arguments) {
			if(p.equals(entity))
				return true;
		}
		return false;
	}	
	
}
