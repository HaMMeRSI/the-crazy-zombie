package com.sagi.zombie.objects;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.sagi.zombie.addons.ObjectId;
import com.sagi.zombie.addons.Vector2;

/**
 * abstract Class
 * General Object to be Extended by all game objects
 * @author sagi
 *
 */
public abstract class GameObject {
	// position of the object
	protected Vector2 objPos;
	// vector unit to add to the position - simulates velocity
	protected Vector2 unitToAddPos;
	// size of the object
	protected Dimension objDim;
	// the player angle
	protected double angle = 0;
	// object velocity
	protected Vector2 velocity;
	// object ID
	protected ObjectId objId;
	
	protected int hp;
	
	protected Rectangle objRec;
	
	/**
	 * ctor
	 * @param velocity object velocity
	 * @param objDim size of the object
	 * @param objPos position of the object
	 * @param objId object ID
	 */
	public GameObject(Vector2 velocity, Dimension objDim, Vector2 objPos, ObjectId objId)
	{
		this.angle = 0;
		this.objPos = objPos;
		this.objDim = objDim;
		this.unitToAddPos = new Vector2();
		this.velocity = velocity;
		this.objId = objId;
		this.objRec = new Rectangle(0, 0, (int)objDim.getWidth(), (int)objDim.getHeight());
	}
	
	public abstract void update();
	public abstract void render(Graphics g);
	public void hit(int damage) {}
	/**
	 * set location of object relative to the form
	 * set angle of object
	 * @param g Graphics Object
	 */
	public void translateObject(Graphics g)
	{
		Graphics2D g2d = (Graphics2D)g;
		// set the position 
		g2d.translate(objPos.getX(), objPos.getY());
		// rotate the transform
		g2d.rotate(angle);
		// set transform origin of rotation
		g2d.translate(-objDim.width / 2, -objDim.height / 2);
	}
	
	/**
	 * Compare with another Game Object
	 */
	public boolean equals(Object obj)
	{
		if(obj == null || !(obj instanceof GameObject))
			return false;
		GameObject go = (GameObject)obj;
		return (go.getObjPos().equals(this.getObjPos()) && go.getObjDim().equals(this.getObjDim()) && go.getObjId() == objId);
	}
	
	public void clear()
	{
		velocity = null;
		objDim = null;
		unitToAddPos = null;
		objPos = null;
	}
	
	/**********************************/
	/** start Of Getters And Setters **/
	/**********************************/
	public Vector2 getObjPos() {
		return objPos;
	}
	public void setObjPos(Vector2 objPos) {
		this.objPos = objPos;
	}
	public Vector2 getUnitToAddPos() {
		return unitToAddPos;
	}
	public void setUnitToAddPos(Vector2 unitToAddPos) {
		this.unitToAddPos = unitToAddPos;
	}
	public Dimension getObjDim() {
		return objDim;
	}
	public void setObjDim(Dimension objDim) {
		this.objDim = objDim;
	}
	public double getAngle() {
		return angle;
	}
	public void setAngle(double angle) {
		this.angle = angle;
	}
	public Vector2 getVelocity() {
		return velocity;
	}
	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}
	public ObjectId getObjId() {
		return objId;
	}
	public int getHp()
	{
		return this.hp;
	}
	public void setHp(int hp) {
		this.hp = hp;
	}

	/********************************/
	/** End Of Getters And Setters **/
	/********************************/
	
	/************************/
	/** start Of getBounds **/
	/************************/
	public Rectangle getObjRectangle()
	{
		return objRec;
	}
	public Rectangle getTopBound()
	{
		return new Rectangle((int)(objPos.getX() + 10), (int)objPos.getY(), (int)(objDim.getWidth() - 20) , (int)(objDim.getHeight() - objDim.getHeight() / 2));
	}
	public Rectangle getBottomBound()
	{
		return new Rectangle((int)(objPos.getX() + 10), (int)(objPos.getY() + objDim.getHeight() / 2), (int)(objDim.getWidth() - 20), (int)(objDim.getHeight() - objDim.getHeight() / 2));
	}
	public Rectangle getLeftBound()
	{
		return new Rectangle((int)objPos.getX(), (int)(objPos.getY() + 5), (int)objDim.getWidth() / 2, (int)objDim.getHeight() - 10);
	}
	public Rectangle getRightBound()
	{
		return new Rectangle(((int)objPos.getX() + (int)objDim.getWidth() / 2), (int)(objPos.getY() + 5), (int)objDim.getWidth() / 2, (int)objDim.getHeight() - 10);
	}
	/**********************/
	/** end Of getBounds **/
	/**********************/

}
