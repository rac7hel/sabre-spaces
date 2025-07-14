package r7.sabre.spaces;

import edu.uky.cs.nil.sabre.Action;
import edu.uky.cs.nil.sabre.HeadPlan;
import edu.uky.cs.nil.sabre.comp.CompiledAction;
import edu.uky.cs.nil.sabre.logic.Assignment;
import edu.uky.cs.nil.sabre.logic.Clause;
import edu.uky.cs.nil.sabre.logic.Effect;
import edu.uky.cs.nil.sabre.logic.Precondition;
import edu.uky.cs.nil.sabre.logic.Value;

/**
 * Functions for reasoning about causality between story events.
 * 
 * @author Rachelyn Farrell
 */
public class Causality {

	/**
	 * Determines whether one {@link CompiledAction action} is a causal ancestor of another action
	 * in a given plan. This is true if any of its {@link Effect effects} achieve a {@link Precondition precondition} 
	 * of the later action, or achieve a precondition of some action in between them that in turn enables 
	 * the later action, and so on. 
	 * 
	 * @param prior the earlier action
	 * @param current the later action
	 * @param story the plan containing the two actions
	 * @return true if the given prior action is a causal ancestor of the current action 
	 */
	public static boolean causalAncestor(CompiledAction prior, CompiledAction current, HeadPlan<CompiledAction> story) {
		// Return true if the prior action satisfies a precondition of the current 
		// action, and no action in between them negates that precondition.
		for(Effect effect : prior.effect.toEffect()) {
			for(Clause<Precondition> preconditions : current.precondition.toPrecondition()) {
				for(Precondition p : preconditions) {
					if(achievesPrecondition(effect, p)) {
						if(!negates(getPlanBetween(prior, current, story), p))
							return true;
					}
				}
			}
		}
		// Return true if any action in between them achieves a precondition of the current 
		// action, and the prior action is a causal ancestor of that action.
		CompiledAction previous = getPrevious(story, current);
		while(previous != null) {
			for(Effect effect : previous.effect.toEffect()) {
				for(Clause<Precondition> preconditions : current.precondition.toPrecondition()) {
					for(Precondition p : preconditions) {
						if(achievesPrecondition(effect, p)) {
							if(causalAncestor(prior, previous, story))
								return true;
						}
					}
				}
			}
			previous = getPrevious(story, previous);
		}
		return false;
	}
	
	/**
	 * Tests whether a given {@link Assignment assignment} achieves a given {@link Precondition precondition}.
	 * 
	 * @param assignment the assignment
	 * @param precondition the precondition
	 * @return true if the assignment sets a value that causes the precondition to hold
	 */
	public static boolean achievesPrecondition(Assignment assignment, Precondition precondition) { 
		return precondition.left.equals(assignment.fluent) && precondition.operator.test((Value)assignment.value, (Value)precondition.right);
	}
	
	/**
	 * Returns a {@link HeadPlan plan} comprising all the steps in between two given {@link CompiledAction actions} in a plan.
	 * 
	 * @param prior the earlier action
	 * @param current the later action
	 * @param original the original plan
	 * @return a new plan containing only the actions after prior and before current 
	 */
	private static HeadPlan<Action> getPlanBetween(CompiledAction prior, CompiledAction current, HeadPlan<CompiledAction> original) {
		HeadPlan<Action> planBetween = HeadPlan.EMPTY;
		boolean between = false;
		while(!original.equals(HeadPlan.EMPTY)) {
			if(original.first.equals(current))
				break;
			if(between)
				planBetween.prepend(original.first);
			else if(original.first.equals(prior))
				between = true;
			original = original.rest;
		}
		return planBetween;
	}
	
	/**
	 * Returns true if any effect in the given {@link HeadPlan plan} causes the negation of the given 
	 * {@link Precondition precondition} to hold. 
	 * 
	 * @param plan the plan
	 * @param precondition the precondition
	 * @return true if the plan negates the precondition
	 */
	private static boolean negates(HeadPlan<Action> plan, Precondition precondition) {
		while(!plan.equals(HeadPlan.EMPTY)) {
			for(Effect e : plan.first.effect.toEffect()) {
				if(precondition.left.equals(e.fluent) && precondition.operator.negate().test((Value)e.value, (Value)precondition.right))
					return true;
			}
			plan = plan.rest;
		}
		return false;
	}
	
	/**
	 * Returns the action immediately before a given action in the given plan.
	 * 
	 * @param plan the plan
	 * @param current the current action
	 * @return the action that precedes the current action in the plan
	 */
	private static CompiledAction getPrevious(HeadPlan<CompiledAction> plan, Action current) {
		CompiledAction previous = null;
		while(!plan.equals(HeadPlan.EMPTY)) {
			if(plan.first.equals(current))
				break;
			previous = plan.first;
			plan = plan.rest;
		}
		return previous;
	}
	
}
