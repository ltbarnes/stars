package edu.brown.cs032.ltbarnes.kdtree;

/**
 * An element used by {@link GenericKDTree} to keep track of children nodes and the depth of an
 * element within the tree.
 * 
 * @author ltbarnes
 * 
 * @param <T>
 *            the type of {@link KDElement}s in the tree
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
		final int prime = 31;
		int result = 1;
		result = prime * result + ((element == null) ? 0 : element.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KDNode<?> other = (KDNode<?>) obj;
		if (element == null) {
			if (other.element != null)
				return false;
		} else if (!element.equals(other.element))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("(%s: L:%s R:%s)", element.value.toString(), leftChild_, rightChild_);
	}

}
