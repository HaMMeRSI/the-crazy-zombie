package com.sagi.zombie.window;

import com.sagi.zombie.addons.Vector2;

public class Camera {
	// store camera position
	private Vector2 camPos;
	// ctor
	public Camera(Vector2 camPos) {
		this.camPos = camPos;
	}
	/**
	 * Update Camera View Point
	 * @param player the Player to get Location
	 */
	public void tick()
	{
		camPos.setX((int)(-Game.player.getObjPos().getX() + Game.width / 2));
		camPos.setY((int)(-Game.player.getObjPos().getY() + Game.height / 2));
	}
	
	public float getX()
	{
		return camPos.getX();
	}
	public float getY()
	{
		return camPos.getY();
	}
	public void setX(float x)
	{
		camPos.setX(x);
	}
	public void setY(float y)
	{
		camPos.setY(y);
	}
	public Vector2 getPos()
	{
		return camPos;
	}
	public String toString()
	{
		return "(" + getX() + ", " + getY() + ")";
	}
}
