package com.sagi.zombie.imagesHandler;

import java.awt.image.BufferedImage;

/**
 * Stores and Creates the SpriteSheet
 * @author sagi
 *
 */
public class SpriteSheet {
	private BufferedImage image;
	
	/**
	 * Ctor
	 * @param image
	 */
	public SpriteSheet(BufferedImage image)
	{
		this.image = image;
	}
	
	/**
	 * Slice a Sheet partition
	 * @param Row row in Sheet
	 * @param Col in Sheet
	 * @param width Slice Width
	 * @param height Slice Height
	 * @return Sliced Image
	 */
	public BufferedImage getPartImage(int row, int col, int width, int height)
	{
		return image.getSubimage(col * width, row * height, width, height);
	}
}
