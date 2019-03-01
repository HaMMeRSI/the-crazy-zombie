package com.sagi.zombie.tiles;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Random;

import com.sagi.zombie.addons.ObjectId;
import com.sagi.zombie.addons.Vector2;
import com.sagi.zombie.imagesHandler.Texture;
import com.sagi.zombie.objects.GameObject;
import com.sagi.zombie.weapons.*;
import com.sagi.zombie.window.Game;

/**
 * Weapon block class extends game object
 * @author sagi
 */
public class WeaponBlock extends GameObject{
	private Texture tex = Game.texture; // Textures
	private int type; // Texture Type
	public static final int numOfWeapons = 17; // number of Weapons
	private final WeaponsHolderClass weapons = new WeaponsHolderClass();
	/**
	 * Ctor
	 * @param dimension
	 * @param startPos
	 * @param type
	 * @param objId
	 */
	public WeaponBlock(Dimension dimension, Vector2 startPos, int type, ObjectId objId) {
		super(new Vector2(), dimension, startPos, objId);
		this.objRec.x = (int)startPos.getX();
		this.objRec.y = (int)startPos.getY();
		this.type = 3;
	}

	public void update() {

	}
	
	/**
	 * get Weapon according to player level
	 * @return weapon
	 */
	public BaseOfWeapon getWeapon()
	{
		BaseOfWeapon t;
		while((t = getTWeap()).levelReq > Game.player.getLevel());
		return t;
	}
	
	/**
	 * get Weapon from Block
	 * @return Specific weapon
	 */
	private BaseOfWeapon getTWeap()
	{
		Random rnd = new Random();
		switch (rnd.nextInt(numOfWeapons))
		{ 
			case 0  : return weapons.AK47();
			case 1  : return weapons.DoubleBarret();
			case 2  : return weapons.M4A1();
			case 3  : return weapons.MachineGun();
			case 4  : return weapons.ShotGun15();
			case 5  : return weapons.ShotGun5();
			case 6  : return weapons.SuperLaserGun();
			case 7  : return weapons.OverPowerGun();
			case 8  : return weapons.Glock();
			case 9  : return weapons.DEagle();
			case 10 : return weapons.ShotGun30();
			case 11 : return weapons.Tar21();
			case 12 : return weapons.SuperDead__X_X();
			case 13 : return weapons.Aug();
			case 14 : return weapons.G36K();
			case 15 : return weapons.Awp();
			case 16 : return weapons.BubleGun();
		}
		return null;
	}
	
	/**
	 * render Block
	 */
	public void render(Graphics g) {
		g.drawImage(tex.blocks[type], (int)objPos.getX(), (int)objPos.getY(), (int)objDim.getWidth(), (int)objDim.getHeight(), null);
	}

}