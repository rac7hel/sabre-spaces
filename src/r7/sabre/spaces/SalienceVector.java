package r7.sabre.spaces;

public class SalienceVector {

	public double[] characters;
	public double[] places;
	public double[] times;
	public double[] actions;
	public double[] goals;

	public SalienceVector(int characters, int places, int times, int actions, int goals) {
		this.characters = new double[characters];
		this.places = new double[places];
		this.times = new double[times];
		this.actions = new double[actions];
		this.goals = new double[goals];
	}
	
	@Override
	public String toString() {
		String s = "\nCharacters: ";
		for(double d : characters)
			s += d + " ";
		s += "\nPlaces: ";
		for(double d : places)
			s += d + " ";
		s += "\nTimes: ";
		for(double d : times)
			s += d + " ";
		s += "\nActions: ";
		for(double d : actions)
			s += d + " ";
		s += "\nGoals: ";
		for(double d : goals)
			s += d + " ";
		return s;
	}
}
