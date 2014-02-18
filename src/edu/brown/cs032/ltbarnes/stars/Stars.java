package edu.brown.cs032.ltbarnes.stars;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.brown.cs032.ltbarnes.stars.kdtree.KDTree;
import edu.brown.cs032.ltbarnes.stars.startree.Star;

public class Stars {

	public static final String CMD_USAGE = "\t usage:  [neighbors | radius] n [\"Star name\"| x y z]";
	private KDTree<Star> tree_;

	public Stars(KDTree<Star> tree) {
		this.tree_ = tree;
	}

	public List<String> parseInput(String line) {
		List<String> words;
		String name = null;
		int quote1, quote2;
		if ((quote1 = line.indexOf('\"')) >= 0 && (quote2 = line.indexOf('\"', quote1 + 1)) >= 0) {
			words = new ArrayList<>(Arrays.asList(line.substring(0, quote1).split("\\s+")));
			name = line.substring(quote1 + 1, quote2);
			words.add(name);
			words.addAll(Arrays.asList(line.substring(quote2 + 1).split("\\s+")));
		} else {
			words = new ArrayList<>(Arrays.asList(line.split("\\s+")));
		}
		while (words.contains(""))
			words.remove("");
		if (words.size() == 3 && name == null)
			words.remove(2);
		return words;
	}

	public Command checkInput(List<String> words) {
		if (!words.get(0).equals("neighbors") && !words.get(0).equals("radius")) {
			System.err.println("ERROR: Unrecognized command");
			System.err.println(CMD_USAGE);
			return null;
		}
		Command cmd = null;
		if (words.size() == 3) {
			try {
				int n = Integer.parseInt(words.get(1));
				String starName = words.get(2);
				Star s = null;
				for (Star star : tree_)
					if (star.name.equals(starName)) {
						s = star;
						break;
					}
				if (s == null) {
					System.out.println("ERROR: Star \"" + words.get(2) + "\" not found (case sensitive)");
					System.err.println(CMD_USAGE);
				} else
					cmd = new Command(words.get(0).equals("radius"), n, s);
			} catch (NumberFormatException nfe) {
				System.err.println("ERROR: Number " + words.get(1) + " not recognized");
				System.err.println(CMD_USAGE);
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
				System.err.println("ERROR: Coordinates must be 3 numbers");
				System.err.println(CMD_USAGE);
				return null;
			}
		} else {
			System.err.println("ERROR: Inproper use of parameters");
			System.err.println(CMD_USAGE);
		}
		if (cmd != null && cmd.n < 0) {
			System.err.println("ERROR: The 'n' parameter cannot be negative");
			System.err.println(CMD_USAGE);
			return null;
		}
		return cmd;
	}

	public List<Star> executeCommand(Command cmd) {
		List<Star> stars;
		if (cmd.radiusCmd)
			stars = tree_.kNNSearchWithRadius(cmd.star, cmd.n);
		else
			stars = tree_.kNNSearch(cmd.star, cmd.n);
		return stars;
	}

	public void printStars(List<Star> stars) {
		for (Star star : stars)
			System.out.println(star.value);
		System.out.println();
	}

	public static class Command {
		public final boolean radiusCmd;
		public final int n;
		public final Star star;

		public Command(boolean radiusCmd, int n, double x, double y, double z) {
			this.radiusCmd = radiusCmd;
			this.n = n;
			this.star = new Star("", "", x, y, z);
		}

		public Command(boolean radiusCmd, int n, Star star) {
			this.radiusCmd = radiusCmd;
			this.n = n;
			this.star = star;
		}
	}

}
