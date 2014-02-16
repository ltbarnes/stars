package edu.brown.cs032.ltbarnes.stars.kdtree;

import java.util.Set;

public interface KDTree<T extends Graphable> {

	/**
	 * Adds an element to the tree.
	 * 
	 * @param t
	 */
	public void addElement(T e);

	public boolean removeElement(T e);

	public Set<? extends KDElement> kNNSearch(T e, int numNearest);

	public Set<? extends KDElement> kNNSearchWithRadius(T e, int radius);

}
