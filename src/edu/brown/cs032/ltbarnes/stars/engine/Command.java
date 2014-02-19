package edu.brown.cs032.ltbarnes.stars.engine;

import edu.brown.cs032.ltbarnes.kdtree.KDTree;
import edu.brown.cs032.ltbarnes.stars.startree.Star;

/**
 * A class used for organizing the required inputs needed to execute an search through a
 * {@link KDTree}.
 * 
 * @author ltbarnes
 * 
 */
public class Command {

	private boolean radiusCmd_;
	private int neighbors_;
	private double radius_;
	private Star star_;

	public Command(boolean radiusCmd) {
		this.radiusCmd_ = radiusCmd;
	}

	public boolean isRadiusCmd() {
		return radiusCmd_;
	}

	public int getNeighbors() {
		return neighbors_;
	}

	public void setNeighbors(int neighbors) throws NegativeNumberException {
		if (neighbors < 0)
			throw new NegativeNumberException();
		this.neighbors_ = neighbors;
	}

	public double getRadius() {
		return radius_;
	}

	public void setRadius(double radius) throws NegativeNumberException {
		if (radius < 0)
			throw new NegativeNumberException();
		this.radius_ = radius;
	}

	public Star getStar() {
		return star_;
	}

	public void setStar(Star star) {
		this.star_ = star;
	}

	/**
	 * An {@link Exception} thrown if a negative number is used for a non-negative parameter.
	 * 
	 * @author ltbarnes
	 * 
	 */
	public static class NegativeNumberException extends Exception {

		/**
		 * 
		 */
		private static final long serialVersionUID = 5992445958069338942L;

		public NegativeNumberException() {}

		public NegativeNumberException(String message) {
			super(message);
		}

	}
}
