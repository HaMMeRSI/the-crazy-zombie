package com.sagi.zombie.objects;

import java.awt.Dimension;
import java.awt.Graphics;

import com.sagi.zombie.addons.ObjectId;
import com.sagi.zombie.addons.Vector2;

/**
 * Projectiles
 * @author sagi
 */
public class Projectile extends GameObject {
	private int damage; // projectile damage
	
	/**
	 * Ctor
	 * @param velocity
	 * @param objDim
	 * @param objPos
	 * @param damage
	 * @param objId
	 */
	public Projectile(Vector2 velocity, Dimension objDim, Vector2 objPos, int damage, ObjectId objId) 
	{
		super(velocity, objDim, objPos, objId);
		this.damage = damage;
	}

	/**
	 * update projectile
	 */
	public void update()
	{
		objPos.Add(velocity);
		this.objRec.x = (int)objPos.getX();
		this.objRec.y = (int)objPos.getY();
	}

	/**
	 * render projectile
	 */
	public void render(Graphics g) 
	{
		g.fillOval((int)objPos.getX(), (int)objPos.getY(), (int)objDim.getWidth(), (int)objDim.getHeight());
	}
	
	/**********************************/
	/** start Of Getters And Setters **/
	/**********************************/
	public int getDamage() {
		return this.damage;
	}
	/********************************/
	/** End Of Getters And Setters **/
	/********************************/

}
