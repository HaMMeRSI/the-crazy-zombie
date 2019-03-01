package com.sagi.zombie.Trees;

import com.sagi.zombie.objects.GameObject;

public class Node {
	private Node parent;
	private Node leftNode;
	private Node rightNode;
	private GameObject data;
	private int keyL;
	private int keyR;
	private int height;
	
	public Node(GameObject data, int keyL, int keyR, Node parent)
	{
		this.parent = parent;
		this.leftNode = null;
		this.rightNode = null;
		this.data = data;
		this.keyL = keyL;
		this.keyR = keyR;
		this.height = 0;
	}
	
	public Node getRightNode()
	{
		return rightNode;
	}
	public Node getLeftNode() {
		return leftNode;
	}
	public Node getParent()
	{
		return parent;
	}
	public void setLeftNode(Node leftNode) {
		this.leftNode = leftNode;
	}
	public void setRightNode(Node rightNode) {
		this.rightNode = rightNode;
	}
	public void setParent(Node newNode)
	{
		this.parent = newNode;
	}
	public GameObject getData() {
		return data;
	}
	public int getKeyL() {
		return keyL;
	}

	public int getKeyR() {
		return keyR;
	}

	public int getHeight() {
		return height;
	}

	public void addLeftNode(GameObject data, int keyL, int keyR)
	{
		leftNode = new Node(data, keyL, keyR, this);
	}
	public void addRightNode(GameObject data, int keyL, int keyR)
	{
		rightNode = new Node(data, keyL, keyR, this);
	}
	public boolean isLeaf()
	{
		return (getLeftNode() == null && getRightNode() == null);
	}
	public void setHeight(int height)
	{
		this.height = height;
	}
	public void setHeight(){
		int lHeight = -1, rHeight = -1;
		if( leftNode != null )
		        lHeight = leftNode.height;
		if( rightNode != null )
		        rHeight = rightNode.height;
		height = Math.max( lHeight, rHeight ) + 1;
    }

	public boolean equals(Object obj)
	{
		if(obj instanceof Node)
		{
			Node node = (Node)obj;
			return (keyL == node.getKeyL());
		}
		return false;
	}
	public String toString()
	{
		return "(" + this.data.getObjPos().getX() + ", " + this.data.getObjPos().getY() + ")" + " : " + this.height + " =:= " + this.keyL;
	}
}
