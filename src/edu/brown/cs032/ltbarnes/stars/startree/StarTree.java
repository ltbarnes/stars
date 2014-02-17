package edu.brown.cs032.ltbarnes.stars.startree;

import java.util.List;

import edu.brown.cs032.ltbarnes.stars.kdtree.GenericKDTree;
import edu.brown.cs032.ltbarnes.stars.kdtree.KDTree;

public class StarTree extends GenericKDTree<Star> implements KDTree<Star> {

	public StarTree(List<Star> elements) {
		super(elements, 3);
	}

}
