package com.sagi.zombie.imagesHandler;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Class To Load Images as Buffered Images
 * @author sagi
 *
 */
public class BufferedImageLoader {
	BufferedImage image;
	
	/**
	 * Ctor
	 * @param path Sub Path of the Image
	 * @return Loaded Image Object
	 */
	public BufferedImage loadImage(String path)
	{
		try {
			image = ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}
}
