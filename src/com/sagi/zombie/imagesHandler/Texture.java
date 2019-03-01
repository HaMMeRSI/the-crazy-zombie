package com.sagi.zombie.imagesHandler;

import java.awt.image.BufferedImage;
/**
 * Create Game Texture by 1 SpriteSheet and Slice
 * @author sagi
 *
 */
public class Texture {
	private BufferedImage tiles; // spriteSheet Image
	private SpriteSheet tilesSprite; // spriteSheet object
	public BufferedImage[] blocks; // sup sheet Images
	private final int spriteSize = 20; // items in Sheet
	
	/**
	 * Ctor
	 * loads the Sheet Image and Slice it into partitions
	 */
	public Texture()
	{
		blocks = new BufferedImage[spriteSize];
		BufferedImageLoader il = new BufferedImageLoader();
		try {
			tiles = il.loadImage("Images\\gameTiles.png");
		} catch(Exception e) {
			e.printStackTrace();
	    }
		tilesSprite = new SpriteSheet(tiles);
		initSpriteSheet();
	}
	
	/**
	 * slice the Sheet into partitions
	 */
	private void initSpriteSheet()
	{
		int cols = tiles.getWidth() / 64;
		int rows = tiles.getHeight() / 64;
		for(int i = 0; i < cols * rows; i++)
			blocks[i] = tilesSprite.getPartImage(((i / cols)), ((i % cols)), 64, 64);
	}
}
