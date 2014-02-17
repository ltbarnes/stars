package edu.brown.cs032.ltbarnes.stars;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.brown.cs032.ltbarnes.stars.startree.Star;
import edu.brown.cs032.ltbarnes.stars.startree.StarTree;

public class Stars {

	private StarTree tree_;

	public Stars(StarTree tree) {
		this.tree_ = tree;
	}

	public List<String> parseInput(String line) {
		List<String> words;
		String name = null;;
		int quote1, quote2;
		if ((quote1 = line.indexOf('\"')) >= 0) {
			String beginning = line.substring(0, quote1);
			words = new ArrayList<>(Arrays.asList(beginning.split("\\s+")));
			if ((quote2 = line.indexOf('\"', quote1 + 1)) >= 0) {
				name = line.substring(quote1 + 1, quote2);
				words.add(name);
			}
		} else {
			words = new ArrayList<>(Arrays.asList(line.split("\\s+")));
		}
		if (words.size() > 0 && words.get(0).length() == 0)
			words.remove(0);
		if (words.size() == 3 && name == null)
			words.remove(2);
		return words;
	}

	public Command checkInput(List<String> words) {
		if (!words.get(0).equals("neighbors") && !words.get(0).equals("radius")) {
			System.err.println(StarsConsole.CMD_ERR);
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
				if (s == null)
					System.out.println("ERROR: Star \"" + words.get(2) + "\" not found (case sensitive)");
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
		if (cmd != null && cmd.n < 0) {
			System.err.println("ERROR: The 'n' parameter cannot be negative");
			return null;
		}
		return cmd;
	}

	public List<Star> executeCommand(Command cmd) {
		List<Star> stars;
		if (cmd.radiusCmd)
			stars = tree_.kNNSearch(cmd.star, cmd.n);
		else
			stars = tree_.kNNSearchWithRadius(cmd.star, cmd.n);
		return stars;
	}

	public static class Command {
		public final boolean radiusCmd;
		public final int n;
		public final Star star;

		public Command(boolean radiusCmd, int n, double x, double y, double z) {
			this.radiusCmd = radiusCmd;
			this.n = n;
			this.star = new Star("","",x, y, z);
		}

		public Command(boolean radiusCmd, int n, Star star) {
			this.radiusCmd = radiusCmd;
			this.n = n;
			this.star = star;
		}
	}

}
