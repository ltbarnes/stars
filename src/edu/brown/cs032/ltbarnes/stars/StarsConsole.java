package edu.brown.cs032.ltbarnes.stars;

import java.util.List;
import java.util.Scanner;

import edu.brown.cs032.ltbarnes.stars.Stars.Command;
import edu.brown.cs032.ltbarnes.stars.kdtree.KDTree;
import edu.brown.cs032.ltbarnes.stars.startree.Star;

public class StarsConsole {

	private Stars stars_;

	public StarsConsole(KDTree<Star> tree) {
		stars_ = new Stars(tree);

		startReadWriteLoop();
	}

	/**
	 * Reads from the command line, sends the input to a {@link KDTree} class
	 * for to search for neighbors, then prints the nearest neighbors.
	 */
	public void startReadWriteLoop() {
		Scanner keyboard = new Scanner(System.in);

		// while EOF hasn't occurred
		while (keyboard.hasNextLine()) {
			// get user input
			String line = keyboard.nextLine();
			List<String> words = stars_.parseInput(line);

			// return on blank line
			if (words.size() == 0 || line.length() == 0)
				break;

			// clean and separate input if valid
			Command cmd;
			if ((cmd = stars_.checkInput(words)) == null) {
				continue;
			}

			List<Star> stars = stars_.executeCommand(cmd);

			stars_.printStars(stars);
		}
		keyboard.close();
	}

}
