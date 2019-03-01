package com.sagi.zombie.addons;

import java.awt.Color;
import java.awt.Graphics;

import com.sagi.zombie.window.Game;

/**
 * miniMap
 * @author sagi
 */
public class MiniMap {
	private int zoom;    				// spread by
	private int fixerX = 0, fixerY = 0; // fixing for cube size
	private final int tileSize = 6; 	// size of each cube
	private Vector2 loc; 				// minimap location
	
	/**
	 * Ctor
	 * @param loc
	 * @param zoom
	 */
	public MiniMap(int zoom)
	{
		this.zoom = zoom;
		this.loc = new Vector2(0, 10);
	}

	public void update()
	{
		// game width for right draw, -10 to pad left 
		// zoom * 2 for both ways, * tilesize to change to pixel size
		this.loc.setX(Game.width - 10 - tileSize * zoom * 2);
	}

	/**
	 * render the miniMap
	 * @param g
	 */
	public void render(Graphics g)
	{
		fixerX = 0;
		fixerY = 0;
		for(int xx = (int)Game.player.getObjPos().getX() / 64 - zoom; xx < (int)Game.player.getObjPos().getX() / 64 + zoom; xx++)
		{
			for(int yy = (int)Game.player.getObjPos().getY() / 64 - zoom; yy < (int)Game.player.getObjPos().getY() / 64 + zoom; yy++)
			{
				if(!(xx < 0 || yy < 0 || xx >= Game.level.getWidth() || yy >= Game.level.getHeight()))
					g.setColor(new Color(Game.mapDesign[xx][yy]));
				else g.setColor((Color.black));
				g.fillRect((int)this.loc.getX() + fixerX, (int)this.loc.getY() + fixerY, tileSize, tileSize);
				fixerY += tileSize;
			}
			fixerX += tileSize;
			fixerY = 0;
		}
	}
}
