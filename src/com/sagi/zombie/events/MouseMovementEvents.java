package com.sagi.zombie.events;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import com.sagi.zombie.addons.Vector2;

/**
 * Handle The Mouse Motion Events
 * @author sagi
 *
 */
public class MouseMovementEvents extends MouseMotionAdapter{
	// Current Mouse Position
	private Vector2 mousePos;
	private boolean mouseIsDown;
	/**
	 * Ctor
	 */
	public MouseMovementEvents()
	{
		mousePos = new Vector2();
		mouseIsDown = false;
	}

	/**
	 * set Mouse Position while Moving
	 */
	public void mouseMoved(MouseEvent e) {
		super.mouseMoved(e);
		mousePos.setCords(e.getX(), e.getY());
	}
	/**
	 * set Mouse Position while Dragging
	 */
	public void mouseDragged(MouseEvent e) {
		super.mouseDragged(e);
		this.mousePos.setCords(e.getX(), e.getY());
		this.mouseIsDown = true;
	}
	
	public Vector2 getMousePos() {
		return mousePos;
	}
	public boolean isMouseDown() {
		return mouseIsDown;
	}
}
