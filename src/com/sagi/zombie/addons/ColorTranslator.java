package com.sagi.zombie.addons;

import java.awt.Dimension;

import com.sagi.zombie.tiles.Block;

/**
 * translate image pixel to block id
 * @author sagi
 *
 */
public class ColorTranslator {
	/**
	 * get color id
	 * @param rgb clor
	 * @return tile ID
	 */
	public static ObjectId colorToObjID(int rgb)
	{
		int blue = rgb & 0xff;
		int green = rgb >> 8 & 0xff;
		int red = rgb >> 16 & 0xff;
					
		// wall
		if(blue == 0 && red == 0 && green == 0)
			return ObjectId.Blank;
		
		// block
		else if(blue == 255 && red == 255 && green == 255)
			return ObjectId.Block;
		
		// Shoot Through Block
		if(blue == 255 && red == 30 && green == 30)
			return ObjectId.ShootThroughBlock;
		
		// water 
		else if(blue == 255 && red == 0 && green == 0)
			return ObjectId.Block;
		
		// water no collision
		else if(blue == 100 && red == 0 && green == 0)
			return ObjectId.Road;
		
		// floor
		else if(blue == 0 && red == 255 && green == 0)
			return ObjectId.Road;
		
		// EmptyWeaponHolder
		else if(blue == 0 && red == 0 && green == 255)
			return ObjectId.Weapon;
		
		// BricWweaponBrickHolder
		else if(blue == 0 && red == 0 && green == 200)
			return ObjectId.Weapon;
		
		// MudWeaponHolder
		else if(blue == 0 && red == 0 && green == 100)
			return ObjectId.Weapon;
		
		// mud
		else if(blue == 0 && red == 100 && green == 0)
			return ObjectId.Road;
		
		// zombie Generators
		else if(blue == 150 && red == 150 && green == 150)
			return ObjectId.ZombieGenerator;

		//player
		else if(blue == 100 && red == 100 && green == 100)
			return ObjectId.Player;
		
		return null;
	}
}
