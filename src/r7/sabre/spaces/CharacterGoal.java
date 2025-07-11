package r7.sabre.spaces;

import edu.uky.cs.nil.sabre.Character;
import edu.uky.cs.nil.sabre.logic.Expression;

/**
 * A character goal maps a character to a goal expression. 
 * 
 * @author Rachelyn Farrell
 */
public class CharacterGoal {
	
	public Character character;
	public Expression goal;
	
	public CharacterGoal(Character character, Expression goal){
		this.character = character;
		this.goal = goal;
	}
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof CharacterGoal))
			return false;
		CharacterGoal g = ((CharacterGoal)o);
		return g.character.equals(this.character) && g.goal.equals(this.goal);
	}
}
