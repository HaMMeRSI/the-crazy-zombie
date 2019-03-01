package com.sagi.zombie.QTree;

import java.awt.Rectangle;
import java.util.ArrayList;

import com.sagi.zombie.objects.GameObject;

/**
 * Quad Tree stores all static objects super efficient :)
 * @author sagi
 *
 */
//    0  |  1
//  ___________
//
//    2  |  3

public class QTree {
	private final int maxObjects = 8; // max objects per square
	private final int maxLevel = 90;  // max Tree Depth
	
	private int level; // current level
	private ArrayList<GameObject> objs; // the Objects in the node
	private Rectangle Bounds; // Rectangle Bounds
	private QTree nodes[]; // 4 nodes
	private int sizeofNodes; // count of objects in node
	private int sizeofObjects; // count of objects in node
	
	/**
	 * Ctor
	 * @param level 0
	 * @param Bounds Level Bounds
	 */
	public QTree(int level, Rectangle Bounds)
	{
		this.level = level;
		this.Bounds = Bounds;
		this.sizeofNodes = 4;
		this.sizeofObjects = 0;
		this.objs = new ArrayList<>();
		this.nodes = new QTree[4];
	}
	
	/**
	 * get the bounds
	 * @return this.Bounds
	 */
	public Rectangle getBounds()
	{
		return this.Bounds;
	}
	
	/**
	 * get the node
	 * @param i required node
	 * @return  required node
	 */
	public QTree getNodes(int i)
	{
		return nodes[i];
	}
	
	/**
	 * get the size
	 * @return size
	 */
	public int size()
	{
		return this.sizeofObjects;
	}
	
	public int sizeofNodes()
	{
		return this.sizeofNodes;
	}
	/**
	 * get the objects
	 * @return objs
	 */
	public ArrayList<GameObject> getObjs()
	{
		return objs;
	}

	/**
	 * get spesific obj
	 * @param i required obj
	 * @return  required obj
	 */
	public GameObject getObj(int i)
	{
		return objs.get(i);
	}
	
	/**
	 * split Tree increase depth
	 */
	public void split()
	{
		int newWidth = (int) Bounds.getWidth() / 2;
		int newHeight = (int) Bounds.getHeight () / 2;
		
		nodes[0] = new QTree(this.level + 1, new Rectangle((int)Bounds.getX() + newWidth, (int)Bounds.getY(), newWidth, newHeight));
		nodes[1] = new QTree(this.level + 1, new Rectangle((int)Bounds.getX(), (int)Bounds.getY(), newWidth, newHeight));
		nodes[2] = new QTree(this.level + 1, new Rectangle((int)Bounds.getX(), (int)Bounds.getY() + newHeight, newWidth, newHeight));
		nodes[3] = new QTree(this.level + 1, new Rectangle((int)Bounds.getX() + newWidth, (int)Bounds.getY() + newHeight, newWidth, newHeight));
		this.level++;
		this.sizeofNodes += 4;
	}

	/**
	 * get Object Node Position 
	 * @param obj the required obj
	 * @return the required Node index
	 */
	public int getIndex(GameObject obj)
	{
		int index = -1;
		int midVer = (int)(Bounds.getX() + (Bounds.getWidth() / 2));  // meUnah  |
		int midHor = (int)(Bounds.getY() + (Bounds.getHeight() / 2)); // meUzan ---
		boolean TopQ = (obj.getObjRectangle().getY() < midHor && obj.getObjRectangle().getY() + obj.getObjRectangle().getHeight() < midHor);
		boolean BotQ = (obj.getObjRectangle().getY() > midHor);
		
		// if Left top and Right top to the left of |
		if(obj.getObjRectangle().getX() < midVer && obj.getObjRectangle().getX() + obj.getObjRectangle().getWidth() < midVer)
		{
			if(TopQ)
				index = 1;
			else if (BotQ)
				index = 2;
		}
		// if Left top right to the left of |
		else if(obj.getObjRectangle().getX() > midVer)
		{
			if(TopQ)
				index = 0;
			else if (BotQ)
				index = 3;
		}
		return index;
	}

	/**
	 * insert to the tree
	 * @param obj required obj to insert
	 */
	public void insert(GameObject obj)
	{
		int index;
		// continue while there are nodes
		if(nodes[0] != null)
		{
			index = getIndex(obj);
			if(index != -1)
			{
				nodes[index].insert(obj);
				return;
			}
		}

		// if reached we are at the required insert node
		objs.add(obj);
		sizeofObjects++;
		
		// check if split required
		if(objs.size() > maxObjects && level < maxLevel)
		{
			if(nodes[0] == null)
				split();
			int i = 0;
	
			while(i < objs.size())
			{
				index = getIndex(objs.get(i));
				if(index != -1)
					nodes[index].insert(objs.remove(i));
				else i++;
			}
		}
	}

	/**
	 * delete object
	 * @param obj the required delete obj
	 */
	public GameObject deleteObj(GameObject obj)
	{
		int index = getIndex(obj);
		GameObject Removed = null;
		
		if(index != -1 && nodes[0] != null)
			Removed = nodes[index].deleteObj(obj);
		
		// if reached we found the node to delete from
		for(int i = 0; i < objs.size(); i++)
			if(objs.get(i).equals(obj))
			{
				Removed = objs.remove(i);	
				sizeofObjects--;
			}
		
		// check if deSplit is required
		if(nodes[0] != null && sibIsNull())
			if(nodes[0].objs.size() == 0 && nodes[1].objs.size() == 0
					&& nodes[2].objs.size() == 0 && nodes[3].objs.size() == 0)
				deSplit();

		return Removed;
	}

	/**
	 * decrease level
	 */
	public void deSplit()
	{
		for(int i = 0; i < 4; i++)
			nodes[i] = null;
		this.level--;
		this.sizeofNodes -= 4;
	}
	
	public void clear()
	{
		objs.clear();
		clearIn();
	}
	
	private void clearIn()
	{
		if(nodes[0] == null)
			deSplit();
		else
		{
			for(int i = 0; i < 4; i++)
				nodes[i].clear();
			deSplit();
		}
	}
	
	/**
	 * check if 4 nodes are null in c
	 * @return boolean
	 */
	public boolean sibIsNull()
	{
		for(int i = 0; i < 4; i++)
			if(nodes[i].nodes[0] != null)
				return false;
		return true;
	}

	/**
	 * retrieve recs from node
	 * @param lis store retrieved recs here
	 * @param obj to search obj collision with
	 * @return array list of potential collisions
	 */
	public ArrayList<GameObject> retriveRecs(ArrayList<GameObject> lis, GameObject obj)
	{
		int index = getIndex(obj);
		if(index != -1 && nodes[0] != null)
			nodes[index].retriveRecs(lis, obj);
		lis.addAll(objs);
		return lis;
	}
}
