package edu.brown.cs032.ltbarnes.stars;

import java.util.List;
import java.util.Scanner;

import edu.brown.cs032.ltbarnes.kdtree.KDTree;
import edu.brown.cs032.ltbarnes.stars.engine.Command;
import edu.brown.cs032.ltbarnes.stars.engine.StarEngine;
import edu.brown.cs032.ltbarnes.stars.startree.Star;

/**
 * The class responsible for reading input from and printing output to the console.
 * 
 * @author ltbarnes
 * 
 */
public class StarsConsole {

	private StarEngine stars_;

	public StarsConsole(KDTree<Star> tree) {
		stars_ = new StarEngine(tree);

		startReadWriteLoop();
	}

	/**
	 * Reads from the command line, sends the input to a {@link KDTree} class for to search for
	 * neighbors, then prints the nearest neighbors.
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

			// run the valid command
			List<Star> stars = stars_.executeCommand(cmd);

			// print the result
			for (Star star : stars)
				System.out.println(star.value);
			System.out.println();
		}
		keyboard.close();
	}

}
