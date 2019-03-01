package com.sagi.zombie.weapons;

import java.awt.Color;
import java.awt.Dimension;

public class WeaponsHolderClass {
	public WeaponsHolderClass() {
	}
	// level | ammunition | weaponInterval | weaponSpeed | weaponDamage | bulletsPerShot | weaponDim |spread | laser | name | Color | soundPath
	public BaseOfWeapon Glock()
	{
		return new BaseOfWeapon(1, 20, 400, 20, 35, 1, new Dimension(8, 8), .6f, false, "Glock", Color.lightGray, "Sounds\\glock.wav");
	}
	public BaseOfWeapon ShotGun5()
	{
		return new BaseOfWeapon(1, 15, 800, 20, 40, 5, new Dimension(8, 8), 3.6f, false, "Shotgun 5" , new Color(255, 148, 127), "Sounds\\shotgun3.wav");
	}
	public BaseOfWeapon AK47()
	{
		return new BaseOfWeapon(4, 30, 200, 20, 60, 1, new Dimension(8, 8), .8f, false, "AK47", Color.yellow, "Sounds\\ak47.wav");
	}
	public BaseOfWeapon Aug()
	{
		return new BaseOfWeapon(10, 35, 150, 17, 40, 1, new Dimension(8, 8), .4f, false, "Aug", Color.yellow, "Sounds\\AUG.wav");
	}
	public BaseOfWeapon M4A1()
	{
		return new BaseOfWeapon(15, 40, 150, 20, 40, 1, new Dimension(8, 8), .5f, false, "M4A1", Color.green, "Sounds\\m4a1.wav");
	}
	public BaseOfWeapon DEagle()
	{
		return new BaseOfWeapon(15, 30, 550, 20, 100, 1, new Dimension(8, 8), .8f, false, "DEagle", Color.yellow, "Sounds\\deagle.wav");
	}
	public BaseOfWeapon DoubleBarret()
	{
		return new BaseOfWeapon(19, 50, 100, 20, 40, 2, new Dimension(8, 8), 1.2f, false, "Double Barret", Color.orange, "Sounds\\gun1.wav");
	}
	// level | ammunition | weaponInterval | weaponSpeed | weaponDamage | bulletsPerShot | weaponDim |spread | laser | name | Color | soundPath
	public BaseOfWeapon ShotGun15()
	{
		return new BaseOfWeapon(23, 20, 900, 20, 20, 15, new Dimension(8, 8), 3.8f, false, "Shotgun 15", Color.pink, "Sounds\\shotgun1.wav");
	}
	public BaseOfWeapon Awp()
	{
		return new BaseOfWeapon(25, 10, 1400, 35, 240, 1, new Dimension(8, 8), .0f, false, "Awp", Color.red, "Sounds\\AWP.wav");
	}
	public BaseOfWeapon BubleGun()
	{
		return new BaseOfWeapon(25, 10, 2000, 5, 200, 1, new Dimension(200, 200), .4f, true, "BubleGun", Color.green, "Sounds\\laser.wav");
	}
	public BaseOfWeapon Tar21()
	{
		return new BaseOfWeapon(30, 50, 75, 25, 45, 1, new Dimension(8, 8), 1.3f, false, "Tar21", Color.pink, "Sounds\\m4a1.wav");
	}
	public BaseOfWeapon G36K()
	{
		return new BaseOfWeapon(35, 60, 110, 35, 40, 1, new Dimension(8, 8), .9f, false, "G36K", Color.blue, "Sounds\\G36K.wav");
	}
	public BaseOfWeapon MachineGun()
	{
		return new BaseOfWeapon(40, 400, 20, 25, 15, 1, new Dimension(8, 8), 0.9f, false, "Machine Gun", Color.magenta, "Sounds\\machinegun.wav");
	}
	// level | ammunition | weaponInterval | weaponSpeed | weaponDamage | bulletsPerShot | weaponDim |spread | laser | name | Color | soundPath
	public BaseOfWeapon ShotGun30()
	{
		return new BaseOfWeapon(45, 30, 700, 30, 20, 30, new Dimension(8, 8), 3.0f, false, "Shotgun 30", Color.BLUE, "Sounds\\shotgun2.wav");
	}
	public BaseOfWeapon SuperLaserGun()
	{
		return new BaseOfWeapon(52, 200, 10, 30, 50, 1, new Dimension(8, 8), 0f, true, "Super Laser Gun", Color.black, "Sounds\\laser.wav");
	}
	public BaseOfWeapon SuperDead__X_X()
	{
		return new BaseOfWeapon(55, 100, 400, 35, 60, 600, new Dimension(8, 8), 12.6f, false, "SuperDead__X_X" , Color.white, "Sounds\\shotgun2.wav");
	}
	public BaseOfWeapon OverPowerGun()
	{
		return new BaseOfWeapon(60, 4000, 10, 30, 85, 40, new Dimension(12, 12), 3.9f, true, "OverPowerGun", Color.CYAN, "Sounds\\laser.wav");
	}
}
