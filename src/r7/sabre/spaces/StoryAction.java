package r7.sabre.spaces;

import edu.uky.cs.nil.sabre.Character;
import edu.uky.cs.nil.sabre.Entity;
import edu.uky.cs.nil.sabre.Solution;
import edu.uky.cs.nil.sabre.comp.CompiledAction;

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
	
	protected StoryAction[] ancestors;
	
	protected Character[] others;
	
	protected Entity[] objects;
	
	protected Entity[] places;
	
	protected Entity[] times;
		
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
}
