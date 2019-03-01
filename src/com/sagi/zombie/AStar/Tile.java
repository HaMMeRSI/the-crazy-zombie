package com.sagi.zombie.AStar;

import com.sagi.zombie.addons.ObjectId;
import com.sagi.zombie.addons.Vector2;

/**
 * tile
 * @author sagi
 *
 */
public class Tile {
	public Vector2 loc;    // tile loc
	public ObjectId objId; // tile id
	
	/**
	 * Ctor
	 * @param loc
	 * @param objId
	 */
	public Tile(Vector2 loc, ObjectId objId)
	{
		this.loc = loc;
		this.objId = objId;
	}
}
