package com.sagi.zombie.handlers;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.sagi.zombie.QTree.QTree;
import com.sagi.zombie.objects.GameObject;
import com.sagi.zombie.window.Game;

/**
 * class to handle the game Objects in game
 * stores in a list all the objecs
 * render and updates by request
 * @author sagi
 */
public class ObjectHandler {
	// arrayList of gameObjects
	private QTree objects;
	/**
	 * Ctor
	 */
	public ObjectHandler(int width, int height)
	{
		objects = new QTree(0, new Rectangle(0,0,width,height));
	}
	/**
	 * get the List of objects
	 * @return list of objects
	 */
	public QTree getObjects() {
		return objects;
	}
	/**
	 * add an object to the list 
	 * @param object new gameObject
	 */
	public void addObject(GameObject object)
	{
		this.objects.insert(object);
	}
	/**
	 * remove gameObject from list
	 * @param object gameObject to remove
	 */
	public void removeObject(GameObject object)
	{
		this.objects.deleteObj(object).clear();
	}
	
	public void clear()
	{
		objects.clear();
	}
	public void reCreate(int width, int height)
	{
		objects.clear();
		objects = new QTree(0, new Rectangle(0,0,width,height));
	}
	/**
	 * Update all gameObjects in list
	 */
	public void update()
	{
		update(objects);
	}
	private void update(QTree node)
	{
		if(node == null)
			return;
		
		for(int j = 0; j < node.getObjs().size(); j++)
			node.getObj(j).update();

		for(int i = 0; i < 4; i++)
			update(node.getNodes(i));
	}

	/**
	 * render all gameObjects in list
	 * @param g graphic object
	 */
	public void render(Graphics g)
	{
		render(objects, g);
	}

	private void render(QTree node, Graphics g)
	{
		if(node == null)
			return;
		
		for(int j = 0; j < node.getObjs().size(); j++)
			if(Game.cameraView.contains(node.getObj(j).getObjPos().getX(), node.getObj(j).getObjPos().getY()))
				node.getObj(j).render(g);

		for(int i = 0; i < 4; i++)
			render(node.getNodes(i), g);
	}
}
