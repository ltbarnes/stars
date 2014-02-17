package edu.brown.cs032.ltbarnes.stars;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.brown.cs032.ltbarnes.stars.startree.Star;
import edu.brown.cs032.ltbarnes.stars.startree.StarTree;

public class StarsMain {

	public static void main(String[] args) {
		List<Star> stars;
		if ((stars = StarsMain.parseInput(args)) == null)
			return;
		StarsMain.runStars(stars);
	}

	public static List<Star> parseInput(String[] args) {
		if (args.length == 0) {
			System.err.println("ERROR: No filename given\n");
			return null;
		}
		if (args.length != 1) {
			System.err.println("ERROR: Only using filename '" + args[0] + "'");
		}

		List<Star> stars = new ArrayList<>();
		Scanner file = null;
		try {
			file = new Scanner(new File(args[0]), "UTF-8");
//			file = new Scanner(new File("data/startest.csv"), "UTF-8"); // for debugging

			// while there is still more to read in the file
			file.nextLine(); // move scanner past first line
			while (file.hasNextLine()) {
				// split star data into array
				String words[] = file.nextLine().split(",");
				String name;
				double x, y, z;

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

		return stars;
	}

	public static void runStars(List<Star> stars) {
		StarTree st = new StarTree(stars);
		new StarsConsole(st);
	}

}
