package com.sagi.zombie.objects;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.sagi.zombie.AStar.Node;
import com.sagi.zombie.CustomLinkedList.LList;
import com.sagi.zombie.addons.ColorTranslator;
import com.sagi.zombie.addons.GameValues;
import com.sagi.zombie.addons.ObjectId;
import com.sagi.zombie.addons.Vector2;
import com.sagi.zombie.handlers.ZombieHandler;
import com.sagi.zombie.window.Game;

/**
 * Zombie class
 * @author sagi
 *
 */
public class Zombie extends GameObject{
	private int damage;
	private int  fullHP;
	private byte toGoX = 0;
	private byte toGoY = 0;
	private BufferedImage zombieI;
	private Random rnd; //  for random movement
	private boolean toRender = false;
	private boolean readyToFind = false;
	private boolean randomMove = false;
	private int readyToChangeDir = 1;
	private LList path; // Linked List for path cords
	private Vector2 tileToGo; 
	private Vector2 oldTile;
	private int grantedExp; // zombie exp
	private boolean smart; // needed AI?
	private String deathSoundPath; // path to the death sound
	
	/**
	 * Ctor
	 * @param zombieI
	 * @param pos
	 * @param vel
	 * @param hp
	 * @param grantedExp
	 * @param damage
	 * @param smart
	 */
	public Zombie(BufferedImage zombieI, Vector2 pos,Vector2 vel, int hp, int grantedExp, int damage, boolean smart, String deathSoundPath)
	{
		super(vel, new Dimension(zombieI.getWidth(), zombieI.getHeight()), pos, ObjectId.Zombie);
		this.zombieI = zombieI;
		this.hp = hp;
		this.grantedExp = grantedExp;
		this.fullHP = hp;
		this.damage = damage;
		this.smart = smart;
		this.deathSoundPath = deathSoundPath;
		this.angle = 0;
		this.rnd = new Random();
		this.tileToGo = pos.Clone();
		this.tileToGo.Div(64);
		this.oldTile = pos.Clone();
	}
		
	/**
	 * get damage
	 */
	public void hit(int damage)
	{
		this.hp -= damage;
	}
	
	/**
	 * get Exp
	 * @return Exp
	 */
	public int getGrantedExp() {
		return grantedExp;
	}

	/**
	 * get Death Sound Path
	 * @return String deathSoundPath
	 */
	public String getDeathSoundPath()
	{
		return this.deathSoundPath;
	}
	
	/** get tile from Map
	 * @param x 
	 * @param y
	 * @return ObjID
	 */
	public ObjectId getTile(int x, int y)
	{
		if(x < 0 || y < 0 || x >= Game.level.getWidth() || y >= Game.level.getHeight())
			return null;
		return ColorTranslator.colorToObjID(Game.level.getRGB(x, y));
	}

	/**
	 * updeate the zombie
	 */
	public void update() 
	{
		this.objRec.x = (int)(objPos.getX());
		this.objRec.y = (int)(objPos.getY());

		if(ZombieHandler.zombieReadyForUpdate)
		{
			if(path != null)
				path.clear();
			readyToFind = true;
			readyToChangeDir++;
		}

		// save CPU not to render all zombs and update as required
		if(Game.cameraView.contains(objPos.getX(), objPos.getY()))
		{
			toRender = true;
			// hit player
			// if distance from tile is lower then tile size or intersectin damage player
			if((Game.player.getObjRectangle().intersects(this.objRec)) || 
				Game.player.getObjPos().GetDistance(this.objPos) <= 64) 
			{
				if(!Game.player.isHitted()) 
					Game.player.hitPlayer(damage);
				randomMove = false;
			}
			
			else 
			{
				// calculate AI Astar
				if(readyToFind && smart)
				{
					path = Game.AI.findPath(Vector2.Div(objPos, GameValues.tileSize).getVecAsInt(),Vector2.Div(Game.player.objPos, GameValues.tileSize).getVecAsInt());
					readyToFind = false;
				}
				
				// Follow path if exists
				if(path != null && path.get(0) != null)
				{
					randomMove = false;
					if(path.size > 0 && oldTile != null)
					{
						if(!tileToGo.equals(((Node)path.get(0)).loc))
						{
							oldTile = objPos.Clone();
							oldTile.Div(64);
							oldTile = oldTile.getVecAsInt();
							tileToGo = ((Node)path.get(0)).loc;
						}
						objPos.setX(objPos.getX() + (tileToGo.getX() - oldTile.getX()) * getVelocity().getX());
						objPos.setY(objPos.getY() + (tileToGo.getY() - oldTile.getY()) * getVelocity().getY());
						
						//GameValues.tileSize as ObjDim - astar uses tileSize for grid
					    /*if(objPos.getX() + (GameValues.tileSize / 4) < (tileToGo.getX() * GameValues.tileSize + 32)) objPos.setX(objPos.getX() + (tileToGo.getX() - oldTile.getX()) * getVelocity().getX());
				   else if(objPos.getX() + (GameValues.tileSize / 4) > (tileToGo.getX() * GameValues.tileSize + 32)) objPos.setX(objPos.getX() + (tileToGo.getX() - oldTile.getX()) * getVelocity().getX());
					    if(objPos.getY() + (GameValues.tileSize / 4) < (tileToGo.getY() * GameValues.tileSize + 32)) objPos.setY(objPos.getY() + (tileToGo.getY() - oldTile.getY()) * getVelocity().getY());
				   else if(objPos.getY() + (GameValues.tileSize / 4) > (tileToGo.getY() * GameValues.tileSize + 32)) objPos.setY(objPos.getY() + (tileToGo.getY() - oldTile.getY()) * getVelocity().getY());
	*/
					} else randomMove = true;
				} else randomMove = true;
			}
		} // end of - if in camera range
		else 
			{
				randomMove = true;
				toRender = false;
			}
		// create random move incase of AStar failed or not in View screen
		if(randomMove)
		{
			if((getTile((int)(objPos.getX() / GameValues.tileSize) + toGoX, (int)(objPos.getY() / GameValues.tileSize) + toGoY) == ObjectId.Block)
			|| (getTile((int)(objPos.getX() / GameValues.tileSize) + toGoX, (int)(objPos.getY() / GameValues.tileSize) + toGoY) == ObjectId.ShootThroughBlock))
				setUnitToAddPos(new Vector2(toGoX *= -1, toGoY *= -1));

			if(readyToChangeDir % 10 == 0)
			{
				toGoX = (byte)(rnd.nextInt() % 2);
				toGoY = (toGoX == 0) ? (byte)(rnd.nextInt() % 2) : 0;
				this.unitToAddPos.setCords(toGoX, toGoY);
				readyToChangeDir = 1;
			}
			objPos.Add(this.unitToAddPos);
		}
	}
	
	/**
	 * render zombie
	 */
	public void render(Graphics g) 
	{
		/*
		//Astar Path drawer
 		Node t;
		if(path != null)
		{
			for(int i = 0; i < path.size; i++)
			{
				if((t = ((Node)path.get(i))) == null)
					break;
				g.fillRect((int)t.loc.getX() * GameValues.tileSize, (int)t.loc.getY() * GameValues.tileSize, GameValues.tileSize, GameValues.tileSize);
			}
		}*/
		
		if(toRender)
		{
			g.drawImage(this.zombieI, (int)this.objPos.getX(), (int)this.objPos.getY(), null);
			// Hp bar
			g.fillRect((int)this.objPos.getX(), (int)this.objPos.getY() - 15, (int)(objDim.getWidth() * ((float)hp / (float)fullHP)), 10);
		}
	}

	public void clear()
	{
		this.zombieI = null;
		this.rnd = null; //  for random movement
		this.path = null; // Linked List for path cords
		this.tileToGo = null; 
		this.oldTile = null;
	}
}
