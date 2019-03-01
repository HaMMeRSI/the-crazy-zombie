package com.sagi.zombie.flower;

/**
 * My Flower :)
 * @author sagi
 * @param <T>
 */
public class Flower {	
	Node firstLevel; // base Level 
	public int size; // amount of objects
	public Caster<Object> caster; // cast object to extract int to store
	
	/**
	 * Ctor
	 * @param caster
	 */
	public Flower(Caster<Object> caster)
	{
		firstLevel = new Node();
		size = 0;
		this.caster = caster;
	}
	
	/**
	 * insert Helper recursion func
	 * @param num
	 */
	public void insert(Object obj)
	{
		insert(firstLevel, caster.extractInt(obj));
		size++;
	}	
	private void insert(Node node, int num)
	{
		// get Ahadot
		byte t = (byte)Math.abs(num % 10);
		// if num is 1 digit
		if(num / 10 == 0)
		{
			// if not exists such num array create
			if(node.digs[t] == null)
				node.digs[t] = new Node();
			// choose neg or pos counter
			if(num < 0) 
				node.digs[t].negCounter++;
			else node.digs[t].posCounter++;
			return;
		}
		// create sub array nodes
		if(node.digs[t] == null)
			node.digs[t] = new Node();
		insert(node.digs[t], num / 10);
	}
	
	/**
	 * remove Helper recursion func
	 * @param num
	 */
	public void remove(Object num)
	{
		remove(firstLevel, caster.extractInt(num));
	}
	private void remove(Node node, int num)
	{
		// get Ahadot
		byte t = (byte)Math.abs(num % 10);
		// if such num does not exists exit
		if(node.digs[t] == null) 
			return;

		// if num is 1 digit check for neg or pos
		if(num / 10 == 0)
		{
			if(num >= 0 && node.digs[t].posCounter > 0) 
			{
				node.digs[t].posCounter--;
				size--;
				return;
			}
			else if(num < 0 && node.digs[t].negCounter > 0)
			{
				node.digs[t].negCounter--;
				size--;
				return;
			}
		}
		remove(node.digs[t], num / 10); 

		// clean up sub nodes
		if(node.digs[t].negCounter == 0 && node.digs[t].posCounter == 0 && isAlone(node.digs[t]))
			node.digs[t] = null;
	}
	
	/**
	 * check if there are nothing in arrays
	 * @param node to delete node
	 * @return is Alone
	 */
	private boolean isAlone(Node node)
	{
		for(int i = 0; i < 10; i++)
		{
			if(node.digs[i] != null)
				return false;
		}
		return true;
	}
	
	/**
	 * search for num in the flower
	 * @param obj searched for object
	 * @return boolean
	 */
	public boolean contains(Object obj)
	{
		return contains(firstLevel, caster.extractInt(obj));
	}	
	private boolean contains(Node node, int num)
	{
		byte t = (byte)Math.abs(num % 10);
		if(node.digs[t] == null) 
			return false;
		
		if(num / 10 == 0)
		{
			if(num % 10 >= 0 && node.digs[t].posCounter > 0)
				return true;
			else if(num % 10 < 0 && node.digs[t].negCounter > 0)
				return true;
		}
		return contains(node.digs[t], num / 10); 
	}
	
	/**
	 * clear folwer
	 */
	public void clear()
	{
		clear(firstLevel, 0);
		size = 0;
	}
	private void clear(Node node, int done)
	{
		for(int i = 0; i < 10; i++)
		{
			if(node.digs[i] != null)
			{
				clear(node.digs[i], 0);
				node.digs[i] = null;
			}
			else done++;
		}
		if(done == 10)
			return;
		node = null;
	}
	
	/**
	 * print folower 
	 */
	public void print()
	{
		print(firstLevel, 0, 1);
	}
	private void print(Node node, int n, int level)
	{
		boolean printed = false;
		for(int i = 0; i < 10; i++)
		{
			if(node.digs[i] != null)
			{
				print(node.digs[i], i*level + n, level * 10);
			}
			else if((node.posCounter > 0 || node.negCounter > 0) && !printed)
			{
				for(int j = 0; j < node.posCounter; j++)
					System.out.println(n);
				for(int j = 0; j < node.negCounter; j++)
					System.out.println(-n);
				printed = true;
			}
		}
	}
	
}
