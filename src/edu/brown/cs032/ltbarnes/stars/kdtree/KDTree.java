package edu.brown.cs032.ltbarnes.stars.kdtree;

import java.util.List;

public interface KDTree<T extends KDElement> extends Iterable<T> {

	/**
	 * Adds an element to the tree.
	 * 
	 * @param t
	 */
	public void addElement(T e);

	public boolean removeElement(T e);

	public List<T> kNNSearch(T e, int numNearest);

	public List<T> kNNSearchWithRadius(T e, int radius);

}
