package com.sagi.zombie.handlers;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import com.sagi.zombie.objects.Zombie;

public class ZombieHandler {
	private ArrayList<Zombie> zombHandler;
	public static Timer zombieTimer = new Timer("static Zombie timer for all to update");
	public static boolean zombieReadyForUpdate = false;

	public ZombieHandler()
	{
		zombHandler = new ArrayList<>();
		zombieTimer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				zombieReadyForUpdate = true;
			}
		}, 0, 100);
	}
	
	public void addZombie(Zombie zomb)
	{
		this.zombHandler.add(zomb);
	}
	
	public Zombie removeZombie(int index)
	{
		Zombie z = zombHandler.remove(index);
		z.clear();
		return z;
	}
	public ArrayList<Zombie> getZombies()
	{
		return this.zombHandler;
	}
	public void clear()
	{
		this.zombHandler.clear();
	}

	public void update()
	{
		for(int i = 0; i < zombHandler.size(); i++)
			zombHandler.get(i).update();
		zombieReadyForUpdate = false;
	}
	
	public void render(Graphics g)
	{
		for(int i = 0; i < zombHandler.size(); i++)
			zombHandler.get(i).render(g);
	}
}
