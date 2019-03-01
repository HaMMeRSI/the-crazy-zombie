package com.sagi.zombie.tiles;

import java.awt.Dimension;
import java.awt.Graphics;

import com.sagi.zombie.addons.ObjectId;
import com.sagi.zombie.addons.Vector2;
import com.sagi.zombie.imagesHandler.Texture;
import com.sagi.zombie.objects.GameObject;
import com.sagi.zombie.window.Game;

public class Block extends GameObject{
	
	// get Game Textures
	private Texture tex = Game.texture;
	// block type in spriteSheet
	private int type;
	
	/**
	 * ctor
	 * @param dimension block size
	 * @param startPos block position
	 * @param type block type
	 * @param objId block ID
	 */
	public Block(Dimension dimension, Vector2 startPos, int type, ObjectId objId) {
		super(null, dimension, startPos, objId);
		this.objRec.x = (int)startPos.getX();
		this.objRec.y = (int)startPos.getY();
		this.type = type;
	}

	public void update() {
	}

	/**
	 * draw block
	 */
	public void render(Graphics g) {
		g.drawImage(tex.blocks[type], (int)objPos.getX(), (int)objPos.getY(), (int)objDim.getWidth(), (int)objDim.getHeight(), null);
	}

}
