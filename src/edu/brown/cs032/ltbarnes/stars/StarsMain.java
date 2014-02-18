package edu.brown.cs032.ltbarnes.stars;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.brown.cs032.ltbarnes.kdtree.GenericKDTree;
import edu.brown.cs032.ltbarnes.kdtree.KDTree;
import edu.brown.cs032.ltbarnes.stars.startree.Star;

/**
 * The main class for the stars program containing the {@code main} method where the program starts.
 * This class is responsible for parsing the command line arguments and beginning the read/write
 * console loop.
 * 
 * @author ltbarnes
 * 
 */
public class StarsMain {

	public static void main(String[] args) {
		List<Star> stars;
		if ((stars = StarsMain.parseInput(args)) == null)
			return;
		StarsMain.runStars(stars);
	}

	/**
	 * Checks the input arguments and creates a list of {@link Star}s from the input file if one is
	 * specified.
	 * 
	 * @param args
	 *            - the arguments passed to the program
	 * @return a list of {@link Star}s
	 */
	public static List<Star> parseInput(String[] args) {
		if (args.length == 0) {
			System.err.println("ERROR: No filename given\n");
			return null;
		}
		// only use the first file given
		if (args.length != 1) {
			System.err.println("ERROR: Only using filename '" + args[0] + "'");
		}
		// only use .csv files
		if (!args[0].endsWith(".csv")) {
			System.err.println("ERROR: File must be a .csv file");
			return null;
		}

		List<Star> stars = new ArrayList<>();
		Scanner file = null;
		try {
			file = new Scanner(new File(args[0]), "UTF-8");

			// move scanner past the labels
			file.nextLine();

			// while there is still more to read in the file
			while (file.hasNextLine()) {

				// split star data into array
				String words[] = file.nextLine().split(",");
				String name;
				double x, y, z;

				// read contents and add star to list
				try {
					name = words[1];
					x = Double.parseDouble(words[2]);
					y = Double.parseDouble(words[3]);
					z = Double.parseDouble(words[4]);

					Star s = new Star(words[0], name, x, y, z);
					stars.add(s);
				} catch (NumberFormatException nfe) {
					System.err.println("ERROR: can't read coordinates of star " + words[0]);
				}
			}
		} catch (FileNotFoundException e) {
			System.err.println("ERROR: " + args[0] + " not found\n");
			return null;
		} finally {
			if (file != null)
				file.close();
		}
		// return list of stars
		return stars;
	}

	/**
	 * Creates a {@link KDTree} from the supplied list of {@link Star}s and passes it to a
	 * {@link StarsConsole} object to begin the read/write loop.
	 * 
	 * @param stars
	 *            - the list of {@link Star} objects
	 */
	public static void runStars(List<Star> stars) {
		KDTree<Star> st = new GenericKDTree<>(stars, 3);
		new StarsConsole(st);
	}

}
