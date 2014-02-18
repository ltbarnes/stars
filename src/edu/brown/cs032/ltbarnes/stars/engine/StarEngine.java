package edu.brown.cs032.ltbarnes.stars.engine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.brown.cs032.ltbarnes.kdtree.KDTree;
import edu.brown.cs032.ltbarnes.stars.engine.Command.NegativeNumberException;
import edu.brown.cs032.ltbarnes.stars.startree.Star;

/**
 * The back end of the stars program. Parses and checks input from the command line, executes the
 * commands by searching the supplied {@link KDTree}, and prints the results to stdout.
 * 
 * @author ltbarnes
 * 
 */
public class StarEngine {

	public static final String CMD_USAGE = "\t usage:  [neighbors | radius] n [\"Star name\"| x y z]";
	private KDTree<Star> tree_;

	public StarEngine(KDTree<Star> tree) {
		this.tree_ = tree;
	}

	/**
	 * Organizes and separates the input string without error checking.
	 * 
	 * @param line
	 *            - the input string
	 * @return a list of strings for each part of the command
	 */
	public List<String> parseInput(String line) {
		List<String> words;
		String name = null;
		int quote1, quote2;

		// if there is a quoted word separate it from the string and add the entire word to the list
		if ((quote1 = line.indexOf('\"')) >= 0 && (quote2 = line.indexOf('\"', quote1 + 1)) >= 0) {
			// separate the words before the quotes
			words = new ArrayList<>(Arrays.asList(line.substring(0, quote1).split("\\s+")));
			// add the quoted word
			name = line.substring(quote1 + 1, quote2);
			words.add(name);
			// separate and add the words after the quotes
			words.addAll(Arrays.asList(line.substring(quote2 + 1).split("\\s+")));

		} else { // no quotes, simply separate the words
			words = new ArrayList<>(Arrays.asList(line.split("\\s+")));
		}
		// remove blank words
		while (words.contains(""))
			words.remove("");
		if (words.size() == 3 && name == null)
			words.remove(2);
		// return the list of words
		return words;
	}

	/**
	 * Check the input for invalid commands. If the input is valid return a {@link Command} object
	 * used to execute the request.
	 * 
	 * @param words
	 *            - the list of input words
	 * @return an {@link Command} object if the input is valid, null otherwise
	 */
	public Command checkInput(List<String> words) {

		Command cmd = null;

		// check the first and second words
		if (words.size() >= 2)
			cmd = checkCommandAndNumber(words.get(0), words.get(1));

		// check the star name or coordinates and add them to the command object
		Star star = null;
		if (words.size() == 3) {
			star = checkStar(words.get(2));
		} else if (words.size() == 5) {
			star = checkCoordinates(words.get(2), words.get(3), words.get(4));
		} else { // wrong number of input arguments
			System.err.println("ERROR: Inproper use of parameters");
			System.err.println(CMD_USAGE);
			return null;
		}
		// if an error was found return null
		if (cmd == null || star == null)
			return null;

		// set the star used to search the tree
		cmd.setStar(star);

		return cmd;
	}

	/**
	 * Checks the validity of the first and second arguments passed as input.
	 * 
	 * @param command
	 *            - the first argument
	 * @param number
	 *            - the second argument
	 * @return a {@link Command} object if the arguments are valid. otherwise null
	 */
	private Command checkCommandAndNumber(String command, String number) {
		Command cmd;

		// if "neighbors" is given then the next argument should be a non-negative integer
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

			// if the "radius" is given then the next argument should be a non-negative double
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

			// if the first word isn't "radius" or "neighbors" the command is invalid
		} else {
			System.err.println("ERROR: Unrecognized command");
			System.err.println(CMD_USAGE);
			cmd = null;
		}
		return cmd;
	}

	/**
	 * Checks to see if the supplied star name is in the {@link KDTree} {@code tree_}.
	 * 
	 * @param starName
	 *            - the name of the star to check
	 * @return a {@link Star} object if the star name is in the tree, otherwise null
	 */
	private Star checkStar(String starName) {
		Star s = null;

		// iterate through the tree checking for starName
		for (Star star : tree_)
			if (star.name.equals(starName)) {
				s = star;
				break;
			}

		// starName couldn't be found
		if (s == null) {
			System.out.println("ERROR: Star \"" + starName + "\" not found (case sensitive)");
			System.err.println(CMD_USAGE);
		}
		return s;
	}

	/**
	 * Checks to make sure the provided coordinates are numbers then creates a new star at that
	 * point.
	 * 
	 * @param x
	 *            - the supplied x coordinate
	 * @param y
	 *            - the supplied y coordinate
	 * @param z
	 *            - the supplied z coordinate
	 * @return the {@link Star} created if the coordinates are valid, otherwise null
	 */
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

	/**
	 * Executes the {@link Command} by searching the {@link KDTree} {@code tree_} with a radius
	 * parameter or nearest neighbors parameter depending on the type of command
	 * 
	 * @param cmd
	 *            - the {@link Command} to execute
	 * @return the list of {@link Star}s returned by the {@link KDTree}
	 */
	public List<Star> executeCommand(Command cmd) {
		List<Star> stars;
		if (cmd.isRadiusCmd())
			stars = tree_.kNNSearchWithRadius(cmd.getStar(), cmd.getRadius());
		else
			stars = tree_.kNNSearch(cmd.getStar(), cmd.getNeighbors());
		return stars;
	}

}
