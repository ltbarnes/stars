package edu.brown.cs032.ltbarnes.stars.startree;

import edu.brown.cs032.ltbarnes.stars.kdtree.KDNode;

class StarNode implements KDNode {

	private KDNode left_;
	private KDNode right_;
	private Star star;

	public StarNode(Star star) {
		this.star = star;
	}

	@Override
	public void setLeftChild(KDNode left) {
		this.left_ = left;
	}

	@Override
	public void setRightChild(KDNode right) {
		this.right_ = right;
	}

	@Override
	public KDNode getLeftChild() {
		return left_;
	}

	@Override
	public KDNode getRightChild() {
		return right_;
	}
	
	@Override
	public String toString() {
		return String.format("(%s: L:%s R:%s)", star.value, left_, right_);
	}

}
