package com.sagi.zombie.Trees;

import java.awt.Graphics2D;

import com.sagi.zombie.objects.GameObject;
import com.sagi.zombie.addons.Vector2;

public class buTree {
	public Node treeRoot;

	public int size()
	{
		return size(treeRoot);
	}
	
	public Node getTreeRoot() {
		return treeRoot;
	}

	public int size(Node node)
	{
		  if (node == null) return(0); 
		  else { 
		    return(size(node.getLeftNode()) + 1 + size(node.getRightNode())); 
		  } 
	}
	public int combaine2Nums(Vector2 dataVec) 
	{
		return (int)(dataVec.getX() * 30000 + dataVec.getY());
	}

	public void insert(GameObject data)
	{
		if(treeRoot == null)
			treeRoot = new Node(data, combaine2Nums(data), combaine2Nums(Vector2.Add(data.getObjPos(), 64)), null);
		else insert(treeRoot, data);
	}
	private void insert(Node currentNode, GameObject data)
	{
		// if(currentNode.getData().getX() < data.getX())
		if(currentNode.getKeyL() > combaine2Nums(data))
		{
			if(currentNode.getRightNode() == null)
				currentNode.addRightNode(data, combaine2Nums(data),combaine2Nums(Vector2.Add(data.getObjPos(), 64)));
			else 
				insert(currentNode.getRightNode(), data);
		} else {
			if(currentNode.getLeftNode() == null)
				currentNode.addLeftNode(data, combaine2Nums(data),combaine2Nums(Vector2.Add(data.getObjPos(), 64)));
			else insert(currentNode.getLeftNode(), data);
		}
		checkAvl(currentNode);
	}

	public boolean searchTree(GameObject data)
	{
		if(treeRoot == null)
			return false;
		return searchTree(treeRoot, data, new Compares());
	}
	private boolean searchTree(Node currentNode, GameObject data, CompareI cmp)
	{		
		if(currentNode == null)
			return false;
		
		if(currentNode.getData().equals(data))
			return true;
		
		else if(cmp.compare(currentNode.getKeyL(), combaine2Nums(data)) >= 0)
			return searchTree(currentNode.getLeftNode(), data, cmp);
		
		else return searchTree(currentNode.getRightNode(), data, cmp);
	}

	public GameObject checkPlayerIntersection(GameObject data)
	{
		return checkPlayerIntersection(treeRoot, data);
	}
	int pL,pR;
	private GameObject checkPlayerIntersection(Node currentNode, GameObject data)
	{		

		if(currentNode == null)
			return null;
		pL = combaine2Nums(data);
		pR = combaine2Nums(Vector2.Add(data.getObjPos(), 64));
		
		if(currentNode.getKeyL() < pL && currentNode.getKeyR() > pR)
			return currentNode.getData();
		
		return null;
	}

	public int combaine2Nums(GameObject data) 
	{
		return (int)(data.getObjRectangle().getX() * 30000 + data.getObjRectangle().getY());
	}

	private int getNHeight(Node node)
	{
		return ((node != null) ? node.getHeight() : -1); 
	}
	
	private Node getNode(Node node)
	{
		return ((node != null) ? node : new Node(null, 0, 0, null));
	}
	
	private void checkAvl(Node node)
	{
		
		int leftHeight = getNHeight(node.getLeftNode());
		int rightHeight = getNHeight(node.getRightNode());
		
		if(Math.abs(leftHeight - rightHeight) <= 1)
			node.setHeight(Math.max(leftHeight, rightHeight) + 1);
		else
			fixAvl(node, leftHeight, rightHeight);
	}
	
	private void fixAvl(Node node, int lHeight, int rHeight)
	{
		if(lHeight > rHeight)
		{
			int leftLeftNode = getNHeight(node.getLeftNode().getLeftNode());
			int leftRightNode = getNHeight(node.getLeftNode().getRightNode());
			
			if(leftLeftNode > leftRightNode)
				rightRotation(node);
			else 
			{
				leftRotation(node.getLeftNode());
				rightRotation(node);
			}
		}
		else if(lHeight < rHeight)
		{
			int rightRightNode = getNHeight(node.getRightNode().getRightNode());
			int rightLeftNode = getNHeight(node.getRightNode().getLeftNode());

			if(rightRightNode > rightLeftNode)
				leftRotation(node);
			else 
			{
				rightRotation(node.getRightNode());
				leftRotation(node);
			}
		}
	}
	
	public void leftRotation(Node node)
	{
		// Store Right child of -unBalanced- Node
		Node nodeRightChild = node.getRightNode();
		
		// check if the unBalanced Node is the root
		// if yes, set treeRoot as the unBalanced Node -> right child
		if(treeRoot.equals(node))
			treeRoot = nodeRightChild;
		
		// check if the unBalanced Node parent left / right node is the unBalanced Node
		// if yes set the parent left / right child as the unBalanced Node -> right child
		else if(getNode(node.getParent().getRightNode()).equals(node))
			node.getParent().setRightNode(nodeRightChild);
		else if(getNode(node.getParent().getLeftNode()).equals(node))
			node.getParent().setLeftNode(nodeRightChild);
		
		// set unBalanced Node right child - parent, to the unBalanced Node parent
		nodeRightChild.setParent(node.getParent());
		// set unBalanced Node -> right child - to unBalanced Node -> right child -> left child
		// to save the left childs of the unBalanced Node right child
		node.setRightNode(nodeRightChild.getLeftNode());
		// set the left node of unBalanced Node -> right child to the unBalanced Node
		nodeRightChild.setLeftNode(node);
		// set the parent of unBalanced Node to unBalanced Node -> right child
		node.setParent(nodeRightChild);
		
		// update the nodes height
		node.setHeight();
		nodeRightChild.setHeight();
	}
	public void rightRotation(Node node)
	{
		// store the left node pf the unBalanced Node  
		Node nodeLeftChild = node.getLeftNode();
		// check if the unBalanced Node is the root
		// if yes, set treeRoot as the unBalanced Node -> right child
		if(treeRoot.equals(node))
			treeRoot = nodeLeftChild;
		// check if the unBalanced Node parent left / right node is the unBalanced Node
		// if yes set the parent left / right child as the unBalanced Node -> left child
		else if(getNode(node.getParent().getRightNode()).equals(node))
			node.getParent().setRightNode(nodeLeftChild);
		else if(getNode(node.getParent().getLeftNode()).equals(node))
			node.getParent().setLeftNode(nodeLeftChild);
		// set the parent of unBalanced Node -> left child -:- to unBalanced Node -> parent
		nodeLeftChild.setParent(node.getParent());
		// set the unBalanced Node -> left child -:- to unBalanced Node -> left child -> right child
		// to save the left childs of the unBalanced Node right child
		node.setLeftNode(nodeLeftChild.getRightNode());
		// set unBalanced Node -> left child -> right child -:- to unBalanced Node
		nodeLeftChild.setRightNode(node);
		// set unBalanced Node -> parent -:- to unBalanced Node -> left child
		node.setParent(nodeLeftChild);
		
		// Update Nodes Heights
		node.setHeight();
		nodeLeftChild.setHeight();

	}
	
	public void printTree()
	{
		printTree(treeRoot);
	}
	public void printTree(Node node)
	{
		if(node != null)
		{
			System.out.println(node.getData());
			printTree(node.getLeftNode());
			printTree(node.getRightNode());
		}
	}
	public void drawAll(Graphics2D g)
	{
		drawAll(treeRoot, g);
	}
	public void drawAll(Node node, Graphics2D g)
	{
		if(node != null)
		{
			g.drawString("RecKeyL: " + node.getKeyL(), (int)node.getData().getObjRectangle().getX() - 50, (int)node.getData().getObjRectangle().getY() - 20);
			g.fill(node.getData().getObjRectangle());
			drawAll(node.getLeftNode(), g);
			drawAll(node.getRightNode(), g);
		}
	}

	public static void printBinaryTree(Node root, int level){
	    if(root==null)
	         return;
	    printBinaryTree(root.getRightNode(), level+1);
	    if(level!=0){
	        for(int i=0;i<level-1;i++)
	            System.out.print("|\t");
	            System.out.println("|-------"+(int)root.getData().getObjPos().getX());
	    }
	    else
	        System.out.println((int)root.getData().getObjPos().getX());
	    printBinaryTree(root.getLeftNode(), level+1);
	}   
}
