package com.sagi.zombie.addons;

import java.util.ArrayList;

import com.sagi.zombie.objects.GameObject;

public class Sort {
	ArrayList<GameObject> list;
	int pivotPlace;
	GameObject t;
	
	public Sort(ArrayList<GameObject> list)
	{
		this.list = list;
		pivotPlace = 0;
	}
	
	private Integer sortPartitionByX(int start, int end, float pivot)
	{
		start--;
		end++;
		
		while(start < end)
		{
			while(list.get(++start).getObjPos().getX() < pivot);
			while(list.get(--end).getObjPos().getX() > pivot);
			t = list.get(start);
			list.set(start, list.get(end));
			list.set(end, t);
			
			if(list.get(start).getObjPos().getX() == pivot && start < end)
				start--;
			else if(list.get(end).getObjPos().getX() == pivot && start < end)
				end++;
		}
		return (list.get(start).getObjPos().getX() > pivot) ? start - 1 : start;
	}
	
	public void qSortByX(int start, int end)
	{
		if(start < end)
		{
			pivotPlace = sortPartitionByX(start, end, list.get(start).getObjPos().getX());
			qSortByX(start, pivotPlace - 1);
			qSortByX(pivotPlace + 1, end);
		}
	}

	private Integer sortPartitionByY(int start, int end, float pivot)
	{
		start--;
		end++;
		
		while(start < end)
		{
			while(list.get(++start).getObjPos().getY() < pivot);
			while(list.get(--end).getObjPos().getY() > pivot);
			t = list.get(start);
			list.set(start, list.get(end));
			list.set(end, t);
			
			if(list.get(start).getObjPos().getY() == pivot && start < end)
				start--;
			else if(list.get(end).getObjPos().getY() == pivot && start < end)
				end++;
		}
		return (list.get(start).getObjPos().getY() > pivot) ? start - 1 : start;
	}
	
	public void subQSortByY(int start, int end)
	{
		if(start < end)
		{
			pivotPlace = sortPartitionByY(start, end, list.get(start).getObjPos().getY());
			subQSortByY(start, pivotPlace - 1);
			subQSortByY(pivotPlace + 1, end);
		}
	}
	
	public void sortByY()
	{
		int start = -1, end = -1;
		boolean flag = false;

		for(int i = 0; i < list.size();)
		{
			start = i;
			for(int j = i; j < list.size() && !flag; j++)
			{
				if(list.get(start).getObjPos().getX() == list.get(j).getObjPos().getX())
				{
					end = j;
					i++;
				}
				else 
					flag = true;
			}
			flag = false;
			subQSortByY(start, end);
		}
	}
	
	public void print()	
	{
		for(GameObject i:list)
			System.out.print(i.getObjPos() + "\t");
		System.out.println();
	}
}
