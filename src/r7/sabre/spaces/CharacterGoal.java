package r7.sabre.spaces;

import java.util.Objects;

import edu.uky.cs.nil.sabre.Character;
import edu.uky.cs.nil.sabre.logic.Expression;

/**
 * A character goal links a {@link Character character} to one of its goals.
 * 
 * @author Rachelyn Farrell
 */
public class CharacterGoal {
	
	/** The character whose goal is being represented **/
	public Character character;
	
	/** The goal expression **/
	public Expression goal;
	
	/**
	 * Create a mapping of a character to a {@link Expression goal expression}.
	 * 
	 * @param character the character
	 * @param goal the expression
	 */
	public CharacterGoal(Character character, Expression goal){
		this.character = character;
		this.goal = goal;
	}
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof CharacterGoal))
			return false;
		CharacterGoal g = ((CharacterGoal)o);
		return g.character.equals(this.character) && g.goal.simplify().equals(this.goal.simplify());
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(character, goal);
	}
	
	@Override
	public String toString() {
		return character + ": " + goal;
	}
}
