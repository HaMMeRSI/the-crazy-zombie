package com.sagi.zombie.AStar;

import java.util.Comparator;

import com.sagi.zombie.CustomLinkedList.LList;
import com.sagi.zombie.addons.ColorTranslator;
import com.sagi.zombie.addons.ObjectId;
import com.sagi.zombie.addons.Vector2;
import com.sagi.zombie.flower.Caster;
import com.sagi.zombie.flower.Flower;
import com.sagi.zombie.window.Game;

/**
 * ASTAR :)
 * @author sagi
 */
public class AStar {	
	private final int maxChecks = 300; // max neighbors checks
	private Flower closedList;         // already checked Nodes list
	private LList openList;            // needed to check list orderd by final cost
	private LList path;                // path from A to B
	private int counter, x, xi, y, yi; // current checks , neighbors loop helpers
	private double gCost, hCost;       // step cost to here, Heuristics from end
	private Tile t;					   // current Tile CPU save
	private Node current, tempOnOL;    // current checked Nodes
	/**
	 * fcost comparator for the order
	 */
	Comparator<Object> fCostComparator = new Comparator<Object>() {
		public int compare(Object o1, Object o2) {
			return (int)(((Node)o1).fCost - ((Node)o2).fCost);
		}
	};
	/**
	 * Vector2 Comparator
	 */
	Comparator<Object> vector2Comparator = new Comparator<Object>() {
		public int compare(Object o1, Object o2) {
			if(((Node)o1).loc.equals(((Node)o2).loc)) return 0;
			return -1;
		}
	};
	/**
	 * Node caster to int
	 */
	Caster<Object> caster = new Caster<Object>() {
		public int extractInt(Object object) {
			Node n = (Node)object;
			return (int)(n.loc.getX() * 128 + n.loc.getY());
		}
	};
	
	/**
	 * Ctor
	 */
	public AStar()
	{
		closedList = new Flower(caster);
		openList = new LList(fCostComparator);
		path = new LList(null);
		gCost = hCost = counter = x = xi = yi = y = 0;
	}
	
	/**
	 * Astar search Function
	 * @param start from
	 * @param end to
	 * @return path
	 */
	public LList findPath(Vector2 start, Vector2 end)
	{
		current = new Node(start, null, 0, getDistanceManhattan(start, end)); // init current node

		// init
		closedList.clear();
		openList.clear();
		path = new LList(null);
		gCost = hCost = counter = x = xi = yi = y = 0;
		
		counter = 0;
		openList.insertFirst(current);
		closedList.insert(current);

		while(openList.size > 0)
		{
			// check for max checks
			if(counter++ >= maxChecks)
				return null;
			// get current node to check from open List
			current = (Node)openList.remove(0);
			
			// check if reached the end
			if(current.loc.equals(end))
			{
				// restore path
				while(current.previous != null)
				{
					path.insertFirst(current);
					current = current.previous;
				}
				// clear
				openList.clear();
				closedList.clear();
				return path;
			}
			// insert to closed List
			closedList.insert(current);
			x = (int)(current.loc.getX());
			y = (int)(current.loc.getY());

			// check for neighbors
			for(int i = 0; i < 9; i++)
			{
				// if current
				if(i == 4) continue;
				// get The neighbor loc
				xi = i % 3 - 1;
				yi = i / 3 - 1;
				// get the Tile
				t = getTile(x + xi, y + yi);
				// if exists or is block
				if(t == null) continue;
				if(t.objId == ObjectId.Block || t.objId == ObjectId.ShootThroughBlock) continue;
				// check for blocked corner -_
				if(i % 2 == 0)
					if(getTile(x + xi, y).objId == ObjectId.Block ||
					   getTile(x, y + yi).objId == ObjectId.Block) continue;
					else if(getTile(x + xi, y).objId == ObjectId.ShootThroughBlock ||
					   getTile(x, y + yi).objId == ObjectId.ShootThroughBlock) continue;

				// Create neighbor Node
				// claculate Gcost
				gCost = current.gCost + (getDistance1(current.loc, t.loc));
				// claculate Fcost
				hCost = getDistanceManhattan(t.loc, end);
				Node node = new Node(t.loc, current, gCost, hCost);
				// check if that node is in the closedList
				if(closedList.contains(node)) continue;
				// if already in OpenList check if its Gcost is lower if so update 
				if((tempOnOL = (Node)openList.remove(node, vector2Comparator)) != null) 
				{
					if(tempOnOL.gCost > node.gCost)
						openList.insertOrderd(node);
					else openList.insertOrderd(tempOnOL);
				}
				else openList.insertOrderd(node);
			}
		}
		// clear
		closedList.clear();
		openList.clear();
		return null;
	}
	
	/**
	 * get tile from map image
	 * @param x loc x
	 * @param y loc y
	 * @return return tile
	 */
	public Tile getTile(int x, int y)
	{
		if(x < 0 || y < 0 || x >= Game.level.getWidth() || y >= Game.level.getHeight())
			return null;
		return new Tile(new Vector2(x, y), ColorTranslator.colorToObjID(Game.mapDesign[x][y]));
	}
	
	// get distance 1 for H - manhattan
	public double getDistanceManhattan(Vector2 v1, Vector2 v2)
	{
		double dx = Math.abs(v1.getX() - v2.getX());
		double dy = Math.abs(v1.getY() - v2.getY());
		return 12 * (dx + dy);
	}
	// get distance for G euclides no root
	public int getDistance1(Vector2 v1, Vector2 v2)
	{
		double dx = v1.getX() - v2.getX();
		double dy = v1.getY() - v2.getY();
		return 10 * (int)((dx * dx + dy * dy));
	}

}
