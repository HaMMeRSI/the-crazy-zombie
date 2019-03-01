package com.sagi.zombie.handlers;

import java.util.ArrayList;

import com.sagi.zombie.tiles.ZombieGenerator;

public class GeneratorHandler {
	ArrayList<ZombieGenerator> generators; // Zombie Generators
	private int allDone; // if all Generators are done creating
	public GeneratorHandler()
	{
		generators = new ArrayList<>();
		allDone = 0;
	}
	
	public void add(ZombieGenerator zombieGenerator)
	{
		generators.add(zombieGenerator);
	}
	
	public ArrayList<ZombieGenerator> getGenerator()
	{
		return this.generators;
	}
	
	public void clear()
	{
		generators.clear();
		allDone = 0;
	}
	
	public boolean areAllDone()
	{
		return allDone == generators.size() - 1;
	}
	
	public void update(ZombieHandler zombHandler)
	{
		for(ZombieGenerator zg : generators)
			if(!zg.isMax())
			{
				// if ready add new Zombie
				if(zg.isReady())
					zombHandler.addZombie(zg.getGeneratedZombie());
				// if generator reached max after last adding cancel timers and notify
				if(zg.isMax())
				{
					zg.cancelTimer();
					allDone++;
				}
			}
	}
}
