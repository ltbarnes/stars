package edu.brown.cs032.ltbarnes.stars;

import java.util.Arrays;
import java.util.List;

import edu.brown.cs032.ltbarnes.stars.kdtree.KDTree;
import edu.brown.cs032.ltbarnes.stars.startree.Star;

public class Stars {

	private KDTree<Star> tree_;

	public Stars(KDTree<Star> tree) {
		this.tree_ = tree;
	}

	public List<String> parseInput(String line) {
		List<String> list = Arrays.asList(line.split("//s+"));
		// TODO: error check and set to null if invalid
		return list;
	}

	public boolean executeCommand(List<String> command) {
//		if
		tree_.kNNSearch(null, 1);
		return false;
	}
	
	
//	private static class Command {
//		command
//	}

}
