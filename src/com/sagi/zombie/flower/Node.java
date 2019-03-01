package com.sagi.zombie.flower;

/**
 * Flower Node
 * @author sagi
 */
public class Node {
	public Node [] digs;     // Nodes
	public short posCounter; 
	public short negCounter;
	
	/**
	 * Ctor
	 */
	public Node()
	{
		digs = new Node[10];
		posCounter = 0;
		negCounter = 0;
	}
}
