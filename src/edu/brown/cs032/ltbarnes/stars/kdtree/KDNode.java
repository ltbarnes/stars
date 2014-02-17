package edu.brown.cs032.ltbarnes.stars.kdtree;

public class KDNode<T extends KDElement> {

	private KDNode<T> left_;
	private KDNode<T> right_;
	private int depth_;
	private final T element;
	
	public KDNode(T element) {
		this.element = element;
	}

	public KDNode(T element, int currDepth) {
		this.element = element;
		this.depth_ = currDepth;
	}

	public T getElement() {
		return element;
	}

	public double getDimension(int dim) {
		return element.coordinates.get(dim);
	}

	public void setDepth(int depth) {
		this.depth_ = depth;
	}

	public int getDepth() {
		return depth_;
	}

	public void setLeftChild(KDNode<T> left) {
		this.left_ = left;
	}

	public void setRightChild(KDNode<T> right) {
		this.right_ = right;
	}

	public KDNode<T> getLeftChild() {
		return left_;
	}

	public KDNode<T> getRightChild() {
		return right_;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof KDNode))
			return false;

		return ((KDNode<?>) o).getElement().equals(element);
	}

	@Override
	public String toString() {
		return String.format("(%s: L:%s R:%s)", element.value, left_, right_);
	}

}
