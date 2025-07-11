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
 * of a specific story using information obtained from the solution.
 * (e.g. which goals were used to explain the action)
 * 
 * @author Rachelyn Farrell
 */
public class StoryAction {
	
	protected CompiledAction action;
	
	protected Character[] consenting;
	
	protected CharacterGoal[] goals;
	
	protected ArrayList<StoryAction> ancestors = new ArrayList<>();
	
	protected Character[] others;
	
	protected Entity[] objects;
		
	/**
	 * Creates a story action using the first step of the given solution
	 * 
	 * @param solution the {@link Solution solution} to which this action belongs
	 */
	public StoryAction(Solution<?> solution) {
		this.action = ((CompiledAction)solution.get(0));
		consenting = new Character[action.consenting.size()];
		for(int i=0; i<consenting.length; i++)
			consenting[i] = action.consenting.get(i);
		goals = new CharacterGoal[consenting.length];
		for(int i=0; i<goals.length; i++)
			goals[i] = new CharacterGoal(consenting[i], solution.getExplanation(consenting[i]).getGoal());
		
	}
	
	public CompiledAction getAction() {
		return action;
	}
	
	public CharacterGoal[] getGoals() {
		return goals;
	}
	
	public boolean salient(Character character) {
		return action.consenting.contains(character);
	}
	
	public boolean salient(CharacterGoal goal) {
		for(CharacterGoal g : goals) {
			if(g.equals(goal))
				return true;
		}
		return false;
	}
	
	public boolean salient(Action action) {

		return false;
	}
		
	public boolean salient(Entity entity) {
		for(Parameter p : action.signature.arguments) {
			if(p.equals(entity))
				return true;
		}
		return false;
	}	
	
}
