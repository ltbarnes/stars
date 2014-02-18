package edu.brown.cs032.ltbarnes.stars.startree;

import java.util.List;

import edu.brown.cs032.ltbarnes.kdtree.GenericKDTree;
import edu.brown.cs032.ltbarnes.kdtree.KDTree;

public class StarTree extends GenericKDTree<Star> implements KDTree<Star> {

	public StarTree(List<Star> elements) {
		super(elements, 3);
	}

}
