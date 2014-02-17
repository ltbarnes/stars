package edu.brown.cs032.ltbarnes.stars.startree;

import edu.brown.cs032.ltbarnes.stars.kdtree.KDNode;

class StarNode extends KDNode<Star> {

	public StarNode(Star star) {
		super(star);
	}

	public StarNode(Star star, int depth) {
		super(star, depth);
	}

	public Star getStar() {
		return (Star) super.getElement();
	}

}
