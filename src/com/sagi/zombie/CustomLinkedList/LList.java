package com.sagi.zombie.CustomLinkedList;

import java.util.Comparator;

/**
 * simple 1 direction linked list
 * @author sagi
 *
 */
public class LList {
	public Node head;
	public int size;
	public Comparator<Object> comp;
	
	public LList(Comparator<Object> comp)
	{
		head = null;
		size = 0;
		this.comp = comp;
	}
	
	public void insertFirst(Object obj)
	{
		size++;
		Node t = head;
		head = new Node(obj);		
		head.next = t;
	}
	
	public void insertLast(Object obj)
	{
		size++;
		if(head == null)
		{
			head = new Node(obj);
			return;
		}
		
		Node t;
		for(t = head; t.next != null; t = t.next);
		t.next = new Node(obj);
	}
	
	public void insertOrderd(Object obj)
	{
		if(head == null)
		{
			insertFirst(obj);
			return;
		}
		
		if(comp.compare(head.val, obj) > 0)
		{
			insertFirst(obj);
			return;
		}
		
		size++;
		Node prev = head;
		for(Node current = head.next; current != null; current = current.next)
		{
			if(comp.compare(current.val, obj) > 0)
			{
				Node t2 = current;
				prev.next = new Node(obj);
				prev.next.next = t2;
				return;
			}
			prev = current;
		}
		prev.next = new Node(obj);
	}
	
	public Object remove(Object obj, Comparator<Object> compa)
	{
		if(head == null)
			return null;
		
		Object temp;
		if(compa.compare(head.val, obj) == 0)
		{
			temp = head.val;
			head = head.next;
			size--;
			return temp;
		}
		Node prev = head;
		for(Node current = head.next; current != null; current = current.next)
		{
			if(compa.compare(current.val, obj) == 0)
			{
				size--;
				temp = current.val;
				prev.next = current.next;
				return temp;
			}
			prev = current;
		}

		return null;
	}

	public Object remove(int index)
	{
		if(head == null || size - 1 < index)
			return null;
		
		if(index == 0)
		{
			Object o = head.val;
			head = head.next;
			size--;
			return o;
		}

		Node t = head;
		for(int i = 0; i < index - 1; i++) t = t.next;
		Node tt = t.next;
		t.next = tt.next;
		size--;
		return tt.val;
	}
		
	public Object get(int index)
	{
		if(head == null)
			return null;
		
		if(index == 0)
			return head.val;

		Node t = head;
		for(int i = 0; i < index - 1; i++) t = t.next;
		return t.val;

	}
	
	public boolean contains(Object obj, Comparator<Object> compa)
	{
		if(head == null)
			return false;
		for(Node t = head; t.next != null; t = t.next)
			if(compa.compare(t.val, obj) == 0)
				return true;
		return false;
	}
	
	public void clear()
	{
		head = null;
		size = 0;
	}

	public void print()
	{
		System.out.print("Size: " + size + "---");
		//for(Node t = head; t != null; t = t.next) System.out.print((int)((com.sagi.AStar.Node)t.val).fCost + " ");
		System.out.println();
	}
}
