package edu.brown.cs032.ltbarnes.stars.kdtree;

import java.util.List;

public interface KDTree<T extends Graphable> {

	/**
	 * Adds an element to the tree.
	 * 
	 * @param t
	 */
	public void addElement(T e);

	public boolean removeElement(T e);

	public List<? extends KDElement> kNNSearch(T e, int numNearest);

	public List<? extends KDElement> kNNSearchWithRadius(T e, int radius);

}
