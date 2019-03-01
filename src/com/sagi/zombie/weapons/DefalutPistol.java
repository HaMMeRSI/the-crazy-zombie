package com.sagi.zombie.weapons;

import java.awt.Color;
import java.awt.Dimension;

public class DefalutPistol extends BaseOfWeapon{
	/*
	level = 0
    ammunition = 30;
	weaponInterval = 20;
	weaponSpeed = 20;
	weaponDamage = 15;
	bulletsPerShot = 1;
	weaponDim = new Dimension(8, 8);
	spread = .6f;
	laser = false;
	name = Lame Pistol
	Color = white
	sound = "\\Sounds\\gun.wav"
	*/
	public DefalutPistol() {
		super(0, 99999999, 300, 20, 25, 1, new Dimension(8, 8), 1.3f, false, "Lame Pistol", Color.white, "Sounds\\gun.wav");
	}

}
