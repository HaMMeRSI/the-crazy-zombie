package com.sagi.zombie.tiles;

import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

import com.sagi.zombie.addons.Vector2;
import com.sagi.zombie.objects.Zombie;

public class ZombieGenerator {
	private Vector2 loc;
	private Vector2 vel;
	private Timer timer;
	private boolean isReady = false;
	private int zombieCounter = 0;
	private int maxZombies;
	private BufferedImage zombieI;
	private int hp;
	private int zDamage;
	private boolean smart;
	private int grantedExp;
	private String deathSoundPath;

	public  ZombieGenerator(int interval, Vector2 location, Vector2 velocity, int hp, int grantedExp, int zDamage, boolean smart, String deathSoundPath, int maxZombiesGenerated, BufferedImage zombieI)
	{
		this.loc = location;
		this.vel = velocity;
		this.isReady  = false;
		this.hp = hp;
		this.grantedExp = grantedExp;
		this.zDamage = zDamage;
		this.smart = smart;
		this.deathSoundPath = deathSoundPath;
		this.zombieI = zombieI;
		this.maxZombies = maxZombiesGenerated;
		timer = new Timer("create Zombie per time timer");
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				isReady = true;
			}
		}, 0, interval);
	}
	
	public boolean isReady()
	{
		return isReady;
	}
	
	public void cancelTimer()
	{
		timer.cancel();
		timer.purge();
		timer = null;
	}
	
	public boolean isMax()
	{
		return zombieCounter >= maxZombies;
	}
	
	public Zombie getGeneratedZombie()
	{
		isReady = false;
		zombieCounter++;
		return new Zombie(zombieI, loc.Clone(), vel, hp, grantedExp, zDamage, smart, deathSoundPath);
	}
}
