package r7.sabre.spaces;

/**
 * A salience vector describes a story in terms of five numeric arrays that represent
 * the relative salience of different entities in the story: namely its characters,
 * places, times, actions, and character goals.
 * 
 * @author Rachelyn Farrell
 */
public class SalienceVector {

	/** The salience of each character in the story **/
	public double[] characters;
	
	/** The salience of each place in the story **/
	public double[] places;
	
	/** The salience of each time frame in the story **/
	public double[] times;
	
	/** The salience of each action in the story **/
	public double[] actions;
	
	/** the salience of each character goal in the story **/
	public double[] goals;

	/**
	 * Creates a new salience vector with the given numbers of characters, places,
	 * times, actions, and goals. Initializes all salience values to zero.
	 * 
	 * @param characters the number of {@link Character characters} in the story
	 * @param places the number of places
	 * @param times the number of time frames 
	 * @param actions the number of actions
	 * @param goals the number of {@link CharacterGoal character goals} 
	 */
	public SalienceVector(int characters, int places, int times, int actions, int goals) {
		this.characters = new double[characters];
		for(int i=0; i<characters; i++)
			this.characters[i] = 0;
		this.places = new double[places];
		for(int i=0; i<places; i++)
			this.places[i] = 0;
		this.times = new double[times];
		for(int i=0; i<times; i++)
			this.times[i] = 0;
		this.actions = new double[actions];
		for(int i=0; i<actions; i++)
			this.actions[i] = 0;
		this.goals = new double[goals];
		for(int i=0; i<goals; i++)
			this.goals[i] = 0;
	}
	
	@Override
	public String toString() {
		String s = "";
		for(double d : characters)
			s += d + ",";
		for(double d : places)
			s += d + ",";
		for(double d : times)
			s += d + ",";
		for(double d : actions)
			s += d + ",";
		for(double d : goals)
			s += d + ",";
		return s.substring(0, s.length()-1);
	}
}
