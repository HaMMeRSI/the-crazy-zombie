package com.sagi.zombie.objects;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.sagi.zombie.addons.GameValues;
import com.sagi.zombie.addons.ObjectId;
import com.sagi.zombie.addons.Vector2;
import com.sagi.zombie.handlers.ObjectHandler;
import com.sagi.zombie.handlers.ZombieHandler;
import com.sagi.zombie.tiles.WeaponBlock;
import com.sagi.zombie.weapons.BaseOfWeapon;
import com.sagi.zombie.weapons.DefalutPistol;
import com.sagi.zombie.window.Game;

public class Player extends GameObject{
	private Weapon usedWeapon;        // current used weapon
	private BufferedImage playerI;    // player Image
	private ObjectHandler objHandler; // Object handler - all game objects
	private int hp;					  // player HP
	private boolean hitted = false;   // if player already hitted
	private int hittedTime = 1;       // hit counter, simulate timer by Update Func
	private boolean Booster;          // player booster DE / active
	private ArrayList<GameObject> retrivedCollision; // potential collision list
	private Rectangle tempGORec;      // current worked Rectangle
	private GameObject tempGO;        // temp gameObject CPU save
	private static int level = 1;     // player level
	private int levelExp;             // level Exp
	private int exp; 				  // current Exp
	private Vector2 oldPos;
	private Vector2 startPos;
	/**
	 * Ctor
	 * @param playerI
	 * @param velocity
	 * @param objDim
	 * @param startPos
	 * @param defaultWeap
	 * @param objHandler
	 * @param zombieHandler
	 * @param objId
	 */
	public Player(BufferedImage playerI, Vector2 velocity, Dimension objDim, Vector2 startPos, BaseOfWeapon defaultWeap, ObjectHandler objHandler,ZombieHandler zombieHandler, ObjectId objId)
	{
		super(velocity, objDim, startPos, objId);
		this.startPos = objPos.Clone();
		this.playerI = playerI;
		this.objHandler = objHandler;
		this.usedWeapon = new Weapon(objHandler, zombieHandler, defaultWeap);
		this.retrivedCollision = new ArrayList<>();
		this.hp = 100;
		this.levelExp = 20;
		this.exp = 0;
		this.oldPos = new Vector2(objPos.getX(), objPos.getY());
		this.Booster = false;
	}
	
	/**
	 * Update player logic
	 * checks for collision
	 */
	public void update() 
	{
		this.objRec.x = (int)(objPos.getX() - objDim.getWidth() / 2);
		this.objRec.y = (int)(objPos.getY() - objDim.getHeight() / 2);
		
		if(!GameValues.GameObjects.Player.toDrawPlayerPath)
			Game.mapDesign[(int)oldPos.getX() / 64][(int)oldPos.getY() / 64] = 0;

		// draw player at minimap
		Game.mapDesign[(int)objPos.getX() / 64][(int)objPos.getY() / 64] = 0x33dd55;
		

		if(hitted)
			hittedTime++;
		
		if(hittedTime % 60 == 0)
		{
			hittedTime = 1;
			hitted = false;
		}
		usedWeapon.update();
		
		objHandler.getObjects().retriveRecs(retrivedCollision, this);
		for(int i = 0; i < retrivedCollision.size(); i++)
		{
			tempGO = retrivedCollision.get(i);
			tempGORec = tempGO.getObjRectangle();
			// intersection With Block - collision Response
			if(tempGORec.intersects(this.objRec) && (tempGO.getObjId() == ObjectId.Block || tempGO.getObjId() == ObjectId.ShootThroughBlock))
			{
				if(this.getBottomBound().intersects(tempGO.getTopBound()))
					objPos.setY((float)(tempGO.getObjPos().getY() - (int)objDim.getHeight() / 2));
				
				else if(this.getTopBound().intersects(tempGO.getBottomBound()))
					objPos.setY((float)(tempGO.getObjPos().getY() + tempGO.getObjDim().getHeight() + objDim.getHeight() / 2));
				
				else if(this.getRightBound().intersects(tempGO.getLeftBound()))
					objPos.setX((float)(tempGO.getObjPos().getX() - objDim.getWidth() / 2));
				
				else if(this.getLeftBound().intersects(tempGO.getRightBound()))
					objPos.setX((float)(tempGO.getObjPos().getX() + tempGO.getObjDim().getWidth() + objDim.getWidth() / 2));
			}
			
			// intersection with ammoPack
			else if(tempGORec.intersects(this.objRec) && tempGO.getObjId() == ObjectId.AmmoPack)
			{
				usedWeapon.setAmmo(usedWeapon.getMagazineAmmo());
				objHandler.removeObject(tempGO);
			}
			
			// intersection with HealthPach
			else if(tempGORec.intersects(this.objRec) && tempGO.getObjId() == ObjectId.HealthPack && hp < 100)
			{
				hp = (hp + 20 >= 100 ) ? 100 : hp + 20;
				objHandler.removeObject(tempGO);
			}
			
			// intersection with WeaponBlock
			else if(tempGORec.intersects(this.objRec) && tempGO.getObjId() == ObjectId.Weapon)
			{
				usedWeapon.setWeapon(((WeaponBlock)tempGO).getWeapon());
				objHandler.removeObject(tempGO);
			}
			
			// intersection with the End
			else if(tempGORec.intersects(this.objRec) && tempGO.getObjId() == ObjectId.End)
			{
				Game.gameWon = true;
				System.out.println("Level Completed!!!");
			}
		}
		retrivedCollision.clear();
		
	}
	/**
	 * draw the player
 	 * @param g Graphics
 	 */
	public void render(Graphics g) {
		g.drawImage(playerI, 0, 0, null);
	}	
	/**
	 * draw Bullets
	 * @param g Graphics
	 */
	public void renderBullets(Graphics g)
	{
		usedWeapon.render(g);
	}
	
	/**
	 * move function get Unit vector for direction
	 * @param moveTo Mouse or keyboard acceleration
	 * @param dir negative positive 1
	 */
	public void move(Vector2 moveTo, int dir)
	{
		unitToAddPos = Vector2.Sub(moveTo, objPos);
		unitToAddPos.Norm();
		unitToAddPos = Vector2.Mul(unitToAddPos, Vector2.Mul(Vector2.Add(velocity,(Booster) ? GameValues.GameObjects.Player.boosterSpeed : 0), dir));
		oldPos.setCords(objPos.getX(), objPos.getY());
		objPos.Add(unitToAddPos);
	}
	/*public void toDeleteVisualizePlayerInterSection(Graphics2D g2d)
	{
		objHandler.getObjects().retriveRecs(retrivedCollision, this);
		for(int i = 0; i < retrivedCollision.size(); i++)
		{
			curR = retrivedCollision.get(i).getObjRectangle();
			if(curR.intersects(getObjRectangle()) && retrivedCollision.get(i).getObjId() == ObjectId.Block)
				g2d.fill(curR);
		}
		retrivedCollision.clear();
	}*/
	
	public int getHp()
	{
		return this.hp;
	}
	public void shootOneBullet(Vector2 shootTo)
	{
		usedWeapon.shootOneBullet(objPos.Clone(), shootTo.Clone());
	}
	public void activeBooster()
	{
		this.Booster = true;
	}
	public void deactiveBooster()
	{
		this.Booster = false;
	}
	/**
	 * hit the player
	 * @param damage
	 */
	public void hitPlayer(int damage)
	{
		// if ready to hit
		if(!this.hitted)
		{
			this.hp -= damage;
			this.hitted = true;
		}
		// kill player
		if(this.hp <= 0)
		{
			// player is dead restart Level
			kill();
		}
	}
	public void kill()
	{
		objPos.setCords(startPos);
		if(getLevel() > 1)
		{
			levelExp = (int)Math.ceil(getLevelExp() * .9f);
			level -= 1;
		}
		this.hp = 100;
		this.exp = 0;
		this.usedWeapon.setWeapon(new DefalutPistol());
	}
	public int getLevel()
	{
		return level;
	}
	public int getExp()
	{
		return this.exp;
	}
	public int getLevelExp()
	{
		return this.levelExp; 
	}
	public void setLevelExp(int levelExp)
	{
		this.levelExp = levelExp;
	}
	/**
	 * add exp
	 * @param toAdd added exp
	 */
	public void addExp(int toAdd)
	{
		if(exp + toAdd < this.levelExp)
			this.exp += toAdd;
		else
		{
			level++;
			toAdd -= (this.levelExp - this.exp);
			this.exp = 0;
			this.levelExp = (int)(this.levelExp * (1 + GameValues.GameObjects.Player.levelExpFactor));
			while(toAdd >= this.levelExp)
			{
				toAdd -= this.levelExp;
				this.levelExp = (int)(this.levelExp * (1 + GameValues.GameObjects.Player.levelExpFactor));
				level++;
			}
			this.exp = toAdd;
		}
	}
	
	public void setLevel(int lvl)
	{
		level = lvl;
	}
	
	/**********************************/
	/** start Of Getters And Setters **/
	/**********************************/
	public Weapon getUsedWeapon() {
		return usedWeapon;
	}
	public void setUsedWeapon(Weapon usedWeapon) {
		this.usedWeapon = usedWeapon;
	}
	public void setLocation(Vector2 loc)
	{
		this.objPos = loc;
	}
	public boolean isHitted()
	{
		return this.hitted;
	}
	/********************************/
	/** End Of Getters And Setters **/
	/********************************/

	/************************/
	/** start Of getBounds **/
	/************************/
	public Rectangle getTopBound()
	{
		int recWidth  = (int)(objDim.getWidth() - 10);
		int recHeight = (int)(objDim.getHeight() - objDim.getHeight() / 2);
		int recX      = (int)(getObjRectangle().getX() + (int)objDim.getWidth() / 2 - recWidth / 2);
		int recY      = (int)(getObjRectangle().getY());
		return new Rectangle(recX, recY, recWidth,recHeight );
	}
	public Rectangle getBottomBound()
	{
		int recWidth  = (int)(objDim.getWidth() - 10);
		int recHeight = (int)(objDim.getHeight() - objDim.getHeight() / 2);
		int recX      = (int)(getObjRectangle().getX() + (int)objDim.getWidth() / 2 - recWidth / 2);
		int recY      = (int)(objPos.getY() + objDim.getHeight() / 2)  - (int)objDim.getHeight() / 2;
		return new Rectangle(recX, recY, recWidth,recHeight );
	}
	public Rectangle getLeftBound()
	{
		int recWidth  = (int)objDim.getWidth() / 2;
		int recHeight = (int)objDim.getHeight() - 10;
		int recX      = (int)getObjRectangle().getX();
		int recY      = (int)(objPos.getY())  - (int)objDim.getHeight() / 2 + 5;
		return new Rectangle(recX, recY, recWidth,recHeight );
	}
	public Rectangle getRightBound()
	{
		int recWidth  = (int)objDim.getWidth() / 2;
		int recHeight = (int)objDim.getHeight() - 10;
		int recX      = (int)getObjRectangle().getX() + (int)objDim.getWidth() / 2;
		int recY      = (int)(objPos.getY() - (int)objDim.getHeight() / 2 + 5);
		return new Rectangle(recX, recY, recWidth,recHeight );
	}
	/**********************/
	/** end Of getBounds **/
	/**********************/
}
