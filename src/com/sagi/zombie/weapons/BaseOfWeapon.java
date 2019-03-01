package com.sagi.zombie.weapons;

import java.awt.Color;
import java.awt.Dimension;
/**
 * the basic Weapon stats and parameters
 * all weapons inherits this class
 * @author sagi
 */
public class BaseOfWeapon {
	public int ammunition;
	public int weaponInterval;
	public int weaponSpeed;
	public int weaponDamage;
	public int bulletsPerShot;
	public Dimension weaponDim;
	public float spread;
	public boolean laser;
	public String name;
	public Color color;
	public int levelReq;
	public String soundPath;
	
	/**
	 * Ctor
	 * @param levelReq
	 * @param ammunition
	 * @param weaponInterval
	 * @param weaponSpeed
	 * @param weaponDamage
	 * @param bulletsPerShot
	 * @param weaponDim
	 * @param spread
	 * @param laser
	 * @param name
	 * @param color
	 */
	public BaseOfWeapon(int levelReq, int ammunition, int weaponInterval, int weaponSpeed, int weaponDamage, int bulletsPerShot, 
			Dimension weaponDim, float spread, boolean laser, String name, Color color, String soundPath)
	{
		this.levelReq = levelReq;
		this.ammunition = ammunition;
		this.weaponInterval = weaponInterval;
		this.weaponSpeed = weaponSpeed;
		this.weaponDamage = weaponDamage;
		this.bulletsPerShot = bulletsPerShot;
		this.weaponDim = weaponDim;
		this.spread = spread;
		this.laser = laser;
		this.name = name;
		this.color = color;
		this.soundPath = soundPath;
	}
}