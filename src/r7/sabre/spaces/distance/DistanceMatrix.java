package r7.sabre.spaces.distance;

import r7.sabre.spaces.StorySpace;

/**
 * A distance matrix calculates the distance between each pair of stories in a 
 * given {@link StorySpace story space} according to the given 
 * {@link DistanceMetric distance metric}.
 * 
 * @author Rachelyn Farrell
 */
public class DistanceMatrix {
	
	/** A matrix for the distance values **/
	public double[][] matrix;
	
	/**
	 * Creates a new distance matrix for the given {@link StorySpace story space} with 
	 * the given {@link DistanceMetric distance metric}.
	 * 
	 * @param stories the list of solutions
	 * @param metric the distance metric with which to compare solutions
	 */
	public DistanceMatrix(StorySpace stories, DistanceMetric metric) {
		matrix = new double[stories.size()][stories.size()];
		for(int i=0; i<matrix.length; i++) {
			for(int j=0; j<matrix[i].length; j++) {
				if(j>i)
					matrix[i][j] = metric.getDistance(stories.get(i), stories.get(j));
				else if (j<i) 
					matrix[i][j] = matrix[j][i];
			}
		}
	}
	
	@Override
	public String toString() {
		String s = ",";
		for(int i=0; i<matrix.length - 1; i++)
			s += i + ","; // Column labels
		s += (matrix.length - 1) + "\n";
		for(int i=0; i<matrix.length; i++) {
			s += i + ","; // Row labels
			for(int j=0; j<matrix.length; j++) {
				s += matrix[i][j] + ","; 
			}
			s = s.substring(0, s.length()-1) + "\n";
		}
		return s;
	}	
}
