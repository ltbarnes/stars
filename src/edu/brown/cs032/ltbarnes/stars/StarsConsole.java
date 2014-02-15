package edu.brown.cs032.ltbarnes.stars;

import edu.brown.cs032.ltbarnes.stars.kdtree.KDTree;
import edu.brown.cs032.ltbarnes.stars.startree.Star;
import edu.brown.cs032.ltbarnes.stars.startree.StarTree;

public class StarsConsole {
	
	KDTree<Star> tree;
	
	public StarsConsole(StarTree tree) {
		this.tree = tree;
	}

}
