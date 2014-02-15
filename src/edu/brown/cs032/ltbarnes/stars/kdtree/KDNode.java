package edu.brown.cs032.ltbarnes.stars.kdtree;

public interface KDNode {
	
	public void setLeftChild(KDNode left);
	
	public void setRightChild(KDNode right);
	
	public KDNode getLeftChild();
	
	public KDNode getRightChild();

}
