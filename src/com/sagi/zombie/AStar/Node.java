package com.sagi.zombie.AStar;

import com.sagi.zombie.addons.Vector2;

/**
 * Astar node
 * @author sagi
 */
public class Node {
	public Vector2 loc;   // node loc
	public double gCost;  // node gCost
	public double hCost;  // node hCost
	public double fCost;  // node fCost
	public Node previous; // node parent
	
	/**
	 * Ctor
	 * @param loc
	 * @param previous
	 * @param gCost
	 * @param hCost
	 */
	public Node(Vector2 loc,Node previous, double gCost, double hCost)
	{
		this.loc = loc;
		this.previous = previous;
		this.gCost = gCost;
		this.hCost = hCost;
		this.fCost = (this.gCost + this.hCost);
	}
	
	/**
	 * compare between 2 nodes 
	 */
	public boolean equals(Object obj) {
		if(!(obj instanceof Node))
			return false;
		Node n = (Node)obj;
		return n.loc.equals(loc);
	}
	
	/**
	 * clone node
	 */
	public Node clone()
	{
		return new Node(this.loc, this.previous, this.gCost, this.hCost);
	}
}
