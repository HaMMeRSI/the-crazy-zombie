package com.sagi.zombie.events;

/**
 * Handle The KeyBoard Events
 * @author sagi
 *
 */
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class KeyBoardEvents extends KeyAdapter {
	// Array List to store Pressed Keys
	private ArrayList<Integer> pressedKeys = new ArrayList<>();

	/**
	 * if Key Pressed and not in the array add it
	 */
	public void keyPressed(KeyEvent e) {
		super.keyPressed(e);
		if(!pressedKeys.contains(e.getKeyCode()))
			pressedKeys.add(new Integer(e.getKeyCode()));
	}
 
	/**
	 * if key Released remove him
	 */
	public void keyReleased(KeyEvent e) {
		super.keyReleased(e);
		pressedKeys.remove(new Integer(e.getKeyCode()));
	}
	
	/**
	 * get the Array List
	 * @return pressedKeys
	 */
	public ArrayList<Integer> getPressedKeys() {
		return pressedKeys;
	}
}

