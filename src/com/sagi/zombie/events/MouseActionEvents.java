package com.sagi.zombie.events;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.sagi.zombie.addons.Vector2;

/**
 * Handle The Mouse Action Events
 * @author sagi
 *
 */
public class MouseActionEvents extends MouseAdapter {
	
	private Vector2 mousePos;
	private boolean mouseIsDown;
	
	// Ctor
	public MouseActionEvents()
	{
		mousePos = new Vector2();
		mouseIsDown = false;
	}
	
	/**
	 * Set Mouse Press Location
	 */
	public void mousePressed(MouseEvent e) {
		super.mousePressed(e);
		this.mousePos.setCords(e.getX(), e.getY());
		this.mouseIsDown = true;
	}
	
	public void mouseReleased(MouseEvent e) {
		super.mouseReleased(e);
		this.mousePos.setCords(e.getX(), e.getY());
		this.mouseIsDown = false;
	}
	
	public Vector2 getMousePos() {
		return mousePos;
	}
	public boolean isMouseDown() {
		return mouseIsDown;
	}
}
