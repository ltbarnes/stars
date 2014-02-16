package edu.brown.cs032.ltbarnes.stars.startree;


class StarNode {

	private StarNode left_;
	private StarNode right_;
	private int depth_;
	private final Star star;

	public StarNode(Star star) {
		this.star = star;
	}
	
	public StarNode(Star star, int depth) {
		this.depth_ = depth;
		this.star = star;
	}
	
	public Star getStar() {
		return star;
	}
	
	public double getDimension(int dim) {
		return star.coordinates.get(dim);
	}
	
	public void setDepth(int depth) {
		this.depth_ = depth;
	}
	
	public int getDepth() {
		return depth_;
	}

	public void setLeftChild(StarNode left) {
		this.left_ = left;
	}

	public void setRightChild(StarNode right) {
		this.right_ = right;
	}

	public StarNode getLeftChild() {
		return left_;
	}

	public StarNode getRightChild() {
		return right_;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof StarNode))
			return false;
		
		return ((StarNode)o).getStar().equals(star);
	}
	
	@Override
	public String toString() {
		return String.format("(%s: L:%s R:%s)", star.value, left_, right_);
	}

}
