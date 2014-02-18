package edu.brown.cs032.ltbarnes.stars.engine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.brown.cs032.ltbarnes.kdtree.KDTree;
import edu.brown.cs032.ltbarnes.stars.engine.Command.NegativeNumberException;
import edu.brown.cs032.ltbarnes.stars.startree.Star;

public class StarEngine {

	public static final String CMD_USAGE = "\t usage:  [neighbors | radius] n [\"Star name\"| x y z]";
	private KDTree<Star> tree_;

	public StarEngine(KDTree<Star> tree) {
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
		Command cmd = checkCommandAndNumber(words.get(0), words.get(1));
		Star star = null;
		if (words.size() == 3) {
			star = checkStar(words.get(2));
		} else if (words.size() == 5) {
			star = checkCoordinates(words.get(2), words.get(3), words.get(4));
		} else {
			System.err.println("ERROR: Inproper use of parameters");
			System.err.println(CMD_USAGE);
			return null;
		}
		if (cmd == null || star == null)
			return null;

		cmd.setStar(star);

		return cmd;
	}

	private Command checkCommandAndNumber(String command, String number) {
		Command cmd;
		if (command.equals("neighbors")) {
			cmd = new Command(false);
			try {
				int n = Integer.parseInt(number);
				cmd.setNeighbors(n);
			} catch (NumberFormatException nfe) {
				System.err.println(String.format("ERROR: Neighbors '%s' must be an integer", number));
				System.err.println(CMD_USAGE);
				cmd = null;
			} catch (NegativeNumberException nne) {
				System.err.println(String.format("ERROR: Neighbors '%s' must be positive", number));
				System.err.println(CMD_USAGE);
				cmd = null;
			}
		} else if (command.equals("radius")) {
			cmd = new Command(true);
			try {
				double radius = Double.parseDouble(number);
				cmd.setRadius(radius);
			} catch (NumberFormatException nfe) {
				System.err.println(String.format("ERROR: Radius '%s' must be a number", number));
				System.err.println(CMD_USAGE);
				cmd = null;
			} catch (NegativeNumberException nne) {
				System.err.println(String.format("ERROR: Radius '%s' must be positive", number));
				System.err.println(CMD_USAGE);
				cmd = null;
			}
		} else {
			System.err.println("ERROR: Unrecognized command");
			System.err.println(CMD_USAGE);
			cmd = null;
		}
		return cmd;
	}

	private Star checkStar(String starName) {
		Star s = null;

		for (Star star : tree_)
			if (star.name.equals(starName)) {
				s = star;
				break;
			}
		if (s == null) {
			System.out.println("ERROR: Star \"" + starName + "\" not found (case sensitive)");
			System.err.println(CMD_USAGE);
		}
		return s;
	}

	private Star checkCoordinates(String x, String y, String z) {
		Star s = null;
		try {
			double ex = Double.parseDouble(x);
			double why = Double.parseDouble(y);
			double zee = Double.parseDouble(z);
			s = new Star("", "", ex, why, zee);
		} catch (NumberFormatException nfe) {
			System.err.println("ERROR: Coordinates must be numbers");
			System.err.println(CMD_USAGE);
		}
		return s;
	}

	public List<Star> executeCommand(Command cmd) {
		List<Star> stars;
		if (cmd.isRadiusCmd())
			stars = tree_.kNNSearchWithRadius(cmd.getStar(), cmd.getRadius());
		else
			stars = tree_.kNNSearch(cmd.getStar(), cmd.getNeighbors());
		return stars;
	}

	public void printStars(List<Star> stars) {
		for (Star star : stars)
			System.out.println(star.value);
		System.out.println();
	}

}
