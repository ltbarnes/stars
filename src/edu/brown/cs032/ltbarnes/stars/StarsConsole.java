package edu.brown.cs032.ltbarnes.stars;

import java.util.List;
import java.util.Scanner;

import edu.brown.cs032.ltbarnes.stars.kdtree.KDTree;
import edu.brown.cs032.ltbarnes.stars.startree.StarTree;

public class StarsConsole {

	private Stars stars_;

	public StarsConsole(StarTree tree) {
		stars_ = new Stars(tree);

		System.out.println("Ready");

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
			String next = keyboard.nextLine();

			// return on blank line
			if (next.length() == 0)
				break;

			// clean and separate input if valid
			List<String> line;
			if ((line = stars_.parseInput(next)) == null) {
				System.err.println("Error invalid input");
				continue;
			}

			stars_.executeCommand(line);

			// print out the list of corrections and a blank line after
			// for (String str : toPrint)
			// System.out.println(str);
			// System.out.println();
		}
		keyboard.close();
	}

}
