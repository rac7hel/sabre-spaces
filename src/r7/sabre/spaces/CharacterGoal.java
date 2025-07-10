package r7.sabre.spaces;

import edu.uky.cs.nil.sabre.Character;
import edu.uky.cs.nil.sabre.logic.Expression;

/**
 * A character goal maps a character to a goal expression. 
 * 
 * @author Rachelyn Farrell
 */
public class CharacterGoal {
	
	protected Character character;
	protected Expression goal;
	
	public CharacterGoal(Character character, Expression goal){
		this.character = character;
		this.goal = goal;
	}
}
