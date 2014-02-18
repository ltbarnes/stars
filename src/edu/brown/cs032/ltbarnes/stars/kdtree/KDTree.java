package edu.brown.cs032.ltbarnes.stars.kdtree;

import java.util.Collection;
import java.util.List;

/**
 * A multidimensional tree. Capable of quickly storing and finding neighboring
 * elements in multidimensional space.
 * 
 * @author ltbarnes
 * 
 * @param <T>
 *            the type of {@link KDElement}s in the tree
 */
public interface KDTree<T extends KDElement> extends Iterable<T>, Collection<T> {

	/**
	 * Adds an element to the tree.
	 * 
	 * @param t
	 */
	public void addElement(T e);

	/**
	 * Removes an element from the tree.
	 * 
	 * @param e
	 *            - the {@link KDElement} to be added to the tree
	 * @return true if the element was removed, false if the element did not
	 *         exist in the tree
	 */
	public boolean removeElement(T e);

	/**
	 * Searches the tree for the {@code numNearest} closest neighbors
	 * surrounding the {@link KDElement} element specified.
	 * 
	 * @param e
	 *            - the element around which neighbors will be found
	 * @param numNearest
	 *            - the number of neighboring element to find
	 * @return a list of neighboring nodes ordered from closes to farthest
	 */
	public List<T> kNNSearch(T e, int numNearest);

	/**
	 * Searches the tree for the all elements within the boundary specified by
	 * the center point at {@code e} and the radius {@code radius}.
	 * 
	 * @param e
	 *            - the center point element
	 * @param radius
	 *            - the distance from the element to search
	 * @return a list of neighboring nodes ordered from closes to farthest
	 */
	public List<T> kNNSearchWithRadius(T e, double radius);

}
