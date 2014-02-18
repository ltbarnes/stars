package edu.brown.cs032.ltbarnes.stars.engine;

import edu.brown.cs032.ltbarnes.stars.startree.Star;

public class Command {

	private boolean radiusCmd;
	private int neighbors;
	private double radius;
	private Star star;

	public Command(boolean radiusCmd) {
		this.radiusCmd = radiusCmd;
	}

	public boolean isRadiusCmd() {
		return radiusCmd;
	}

	public int getNeighbors() {
		return neighbors;
	}

	public void setNeighbors(int neighbors) throws NegativeNumberException {
		if (neighbors < 0)
			throw new NegativeNumberException();
		this.neighbors = neighbors;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) throws NegativeNumberException {
		if (radius < 0)
			throw new NegativeNumberException();
		this.radius = radius;
	}

	public Star getStar() {
		return star;
	}

	public void setStar(Star star) {
		this.star = star;
	}

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
