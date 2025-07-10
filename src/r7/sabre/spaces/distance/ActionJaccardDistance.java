package r7.sabre.spaces.distance;

import java.util.HashSet;

import edu.uky.cs.nil.sabre.Action;
import edu.uky.cs.nil.sabre.Signature;
import edu.uky.cs.nil.sabre.Solution;
import edu.uky.cs.nil.sabre.comp.CompiledAction;
import r7.sabre.spaces.StoryPlan;

/**
 * Action Jaccard distance is a {@link DistanceMetric distance metric} that measures the
 * distance between two {@link Solution solutions} as 1 minus the Jaccard index of the 
 * sets of {@link CompiledAction actions} they contain. The Jaccard index defines the 
 * similarity of any two sets as the size of their {@link 
 * ActionJaccardDistance#intersect(HashSet, HashSet) intersection} divided by the size 
 * of their {@link ActionJaccardDistance#union(HashSet, HashSet) union}. 
 * 
 * @author Rachelyn Farrell
 */
public class ActionJaccardDistance extends DistanceMetric {
	
	/**
	 * Constructs a new Action Jaccard distance metric.
	 */
	public ActionJaccardDistance() {
		super("jaccard");
	}
	
	/**
	 * Returns the Jaccard distance between two sets of {@link 
	 * CompiledAction actions}, those in story A and those in story B.
	 * 
	 * @param storyA a solution
	 * @param storyB another solution
	 * @return the Action Jaccard distance between the given solutions
	 */
	@Override
	public double getDistance(StoryPlan storyA, StoryPlan storyB) {
		HashSet<Signature> actionsA = new HashSet<Signature>();
		HashSet<Signature> actionsB = new HashSet<Signature>();
		for(int i=0; i<storyA.size(); i++)
			actionsA.add(storyA.get(i).getAction().signature);
		for(int i=0; i<storyB.size(); i++)
			actionsB.add(storyB.get(i).getAction().signature);
		return 1.0 - ((double) intersect(actionsA, actionsB).size()) / union(actionsA, actionsB).size();
	}

	/**
	 * Returns the intersection of two sets: the set of elements which they have in common.
	 * 
	 * @param <E> The type of element in the sets
	 * @param setA a set
	 * @param setB another set
	 * @return the set of elements contained in both the given sets
	 */
	public static <E> HashSet<E> intersect(HashSet<E> setA, HashSet<E> setB){
		HashSet<E> intersect = new HashSet<>();
		for(E element : setA)
			if(setB.contains(element))
				intersect.add(element);
		return intersect;
	}

	/**
	 * Returns the union of two sets: all the elements contained within either set.
	 * 
	 * @param <E> The type of element in the sets
	 * @param setA a set
	 * @param setB another set
	 * @return the set of elements contained in either of the given sets
	 */
	public static <E> HashSet<E> union(HashSet<E> setA, HashSet<E> setB){
		HashSet<E> union = new HashSet<>();
		for(E element : setA)
			union.add(element);
		for(E action : setB)
			union.add(action);
		return union;
	}

	
}
