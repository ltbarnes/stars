package edu.brown.cs032.ltbarnes.kdtree;

/**
 * An element used by {@link GenericKDTree} to keep track of children nodes and
 * the depth of an element within the tree.
 * 
 * @author ltbarnes
 * 
 * @param <T> the type of {@link KDElement}s in the tree
 */
public class KDNode<T extends KDElement> {

	private KDNode<T> leftChild_;
	private KDNode<T> rightChild_;
	private int depth_;
	private T element;

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
		this.leftChild_ = left;
	}

	public void setRightChild(KDNode<T> right) {
		this.rightChild_ = right;
	}

	public KDNode<T> getLeftChild() {
		return leftChild_;
	}

	public KDNode<T> getRightChild() {
		return rightChild_;
	}
	
	@Override
	public int hashCode() {
		return element.hashCode();
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
		return String.format("(%s: L:%s R:%s)", element.value, leftChild_, rightChild_);
	}

}
