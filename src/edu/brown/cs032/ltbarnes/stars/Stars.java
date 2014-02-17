package edu.brown.cs032.ltbarnes.stars;

import java.util.List;

import edu.brown.cs032.ltbarnes.stars.kdtree.KDTree;
import edu.brown.cs032.ltbarnes.stars.startree.Star;

public class Stars {

	private KDTree<Star> tree_;

	public Stars(KDTree<Star> tree) {
		this.tree_ = tree;
	}

	public Command parseInput(List<String> words) {
		if (!words.get(0).equals("neighbors") && !words.get(0).equals("radius")) {
			System.err.println(StarsConsole.CMD_ERR);
			return null;
		}
		Command cmd = null;
		if (words.size() == 3) {
			try {
				int n = Integer.parseInt(words.get(1));
				String starName = words.get(2);
				if (starName.charAt(0) != '\"' || starName.charAt(starName.length() - 1) != '\"') {
					System.err.println("ERROR: Quotes problem");
					return null;
				}
				starName = starName.substring(1, starName.length() - 1);
				Star s = null;
				for (Star star : tree_)
					if (star.name.equals(starName)) {
						s = star;
						break;
					}
				if (s == null)
					System.out.println("ERROR: Star " + words.get(2) + " not found (case sensitive)");
				else
					cmd = new Command(words.get(0).equals("radius"), n, s);
			} catch (NumberFormatException nfe) {
				System.err.println("ERROR: Number prob 3");
				return null;
			}
		} else if (words.size() == 5) {
			try {
				int n = Integer.parseInt(words.get(1));
				double x = Double.parseDouble(words.get(2));
				double y = Double.parseDouble(words.get(3));
				double z = Double.parseDouble(words.get(4));
				cmd = new Command(words.get(0).equals("radius"), n, x, y, z);
			} catch (NumberFormatException nfe) {
				System.err.println("ERROR: Number prob 5");
				return null;
			}
		} else {
			System.err.println("ERROR: Size prob: size == " + words.size());
		}
		return cmd;
	}

	public boolean executeCommand(Command cmd) {
		// tree_.kNNSearch(null, 1);
		return false;
	}

	public static class Command {
		public final boolean radiusCmd, isStar;
		public final int n;
		public final double x, y, z;

		public Command(boolean radiusCmd, int n, double x, double y, double z) {
			this.radiusCmd = radiusCmd;
			this.n = n;
			this.x = x;
			this.y = y;
			this.z = z;
			isStar = false;
		}

		public Command(boolean radiusCmd, int n, Star star) {
			this.radiusCmd = radiusCmd;
			this.n = n;
			this.x = star.coordinates.get(0);
			this.y = star.coordinates.get(1);
			this.z = star.coordinates.get(2);
			isStar = true;
		}
	}

}
