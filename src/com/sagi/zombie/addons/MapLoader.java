package com.sagi.zombie.addons;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import com.sagi.zombie.handlers.GeneratorHandler;
import com.sagi.zombie.handlers.ObjectHandler;
import com.sagi.zombie.handlers.ZombieHandler;
import com.sagi.zombie.imagesHandler.BufferedImageLoader;
import com.sagi.zombie.objects.Player;
import com.sagi.zombie.tiles.Block;
import com.sagi.zombie.tiles.WeaponBlock;
import com.sagi.zombie.tiles.ZombieGenerator;
import com.sagi.zombie.weapons.DefalutPistol;
import com.sagi.zombie.window.Game;

/**
 * load map per pixel from given image
 * @author sagi
 */
public class MapLoader {

	/**
	 * Ctor
	 * @param mapPath
	 * @param objHandler
	 * @param zombieHandler
	 * @param generator
	 */
	public MapLoader(String mapPath, ObjectHandler objHandler, ZombieHandler zombieHandler, GeneratorHandler generator)
	{
		loadMap(mapPath, objHandler, zombieHandler, generator);
	}
	
	public void loadMap(String mapPath, ObjectHandler objHandler, ZombieHandler zombieHandler, GeneratorHandler generator)
	{
		int rgb, red, blue, green;
		BufferedImageLoader bfl = new BufferedImageLoader();
		
		BufferedImage playerI 	= bfl.loadImage(GameValues.GameImages.Player);
		BufferedImage zombieWI 	= bfl.loadImage(GameValues.GameImages.ZombieWeakPath);
		BufferedImage zombieMI 	= bfl.loadImage(GameValues.GameImages.ZombieMediumPath);
		BufferedImage zombieHI 	= bfl.loadImage(GameValues.GameImages.ZombieHardPath);
		BufferedImage zombieHCI = bfl.loadImage(GameValues.GameImages.ZombieHardCorePath);
		BufferedImage bonusI 	= bfl.loadImage(GameValues.GameImages.BonusPath);
		BufferedImage IdoBossI 	= bfl.loadImage(GameValues.GameImages.IdoBossPath);
		BufferedImage NinjaI 	= bfl.loadImage(GameValues.GameImages.NinjaPath);
		BufferedImage level 	= bfl.loadImage(mapPath);
		
		Dimension levelDim = new Dimension(level.getHeight(), level.getWidth());
		
		Game.mapDesign = null;
		Game.mapDesign = new int[(int)levelDim.getWidth()][(int)levelDim.getHeight()];

		
		for(int xx = 0; xx < levelDim.getWidth(); xx++)
		{
			for(int yy = 0; yy < levelDim.getHeight(); yy++)
			{
				rgb = level.getRGB(xx, yy);
				blue = rgb & 0xff;
				green = rgb >> 8 & 0xff;
				red = rgb >> 16 & 0xff;
				
				Game.mapDesign[xx][yy] = rgb;
				
				// Block
				if(blue == 255 && red == 255 && green == 255)
					objHandler.addObject(new Block(new Dimension(GameValues.tileSize, GameValues.tileSize), new Vector2(xx * GameValues.tileSize, yy * GameValues.tileSize), 2,  ObjectId.Block));
				
				// Shoot Through Block
				if(blue == 255 && red == 30 && green == 30)
					objHandler.addObject(new Block(new Dimension(GameValues.tileSize, GameValues.tileSize), new Vector2(xx * GameValues.tileSize, yy * GameValues.tileSize), 10,  ObjectId.ShootThroughBlock));
				
				// water 
				else if(blue == 255 && red == 0 && green == 0)
					objHandler.addObject(new Block(new Dimension(GameValues.tileSize, GameValues.tileSize), new Vector2(xx * GameValues.tileSize, yy * GameValues.tileSize), 1,  ObjectId.Block));
				
				// water no collision
				else if(blue == 100 && red == 0 && green == 0)
					objHandler.addObject(new Block(new Dimension(GameValues.tileSize, GameValues.tileSize), new Vector2(xx * GameValues.tileSize, yy * GameValues.tileSize), 1,  ObjectId.Road));
				
				// floor
				else if(blue == 0 && red == 255 && green == 0)
					objHandler.addObject(new Block(new Dimension(GameValues.tileSize, GameValues.tileSize), new Vector2(xx * GameValues.tileSize, yy * GameValues.tileSize), 0,  ObjectId.Road));
				
				// EmptyWeaponHolder
				else if(blue == 0 && red == 255 && green == 255)
					objHandler.addObject(new WeaponBlock(new Dimension(GameValues.tileSize, GameValues.tileSize), new Vector2(xx * GameValues.tileSize, yy * GameValues.tileSize), 3,  ObjectId.Weapon));
				
				// BrickWeaponBrickHolder
				else if(blue == 0 && red == 0 && green == 200)
					objHandler.addObject(new WeaponBlock(new Dimension(GameValues.tileSize, GameValues.tileSize), new Vector2(xx * GameValues.tileSize, yy * GameValues.tileSize), 4,  ObjectId.Weapon));
				
				// MudWeaponHolder
				else if(blue == 0 && red == 0 && green == 100)
					objHandler.addObject(new WeaponBlock(new Dimension(GameValues.tileSize, GameValues.tileSize), new Vector2(xx * GameValues.tileSize, yy * GameValues.tileSize), 5,  ObjectId.Weapon));
				
				// mud
				else if(blue == 0 && red == 100 && green == 0)
					objHandler.addObject(new WeaponBlock(new Dimension(GameValues.tileSize, GameValues.tileSize), new Vector2(xx * GameValues.tileSize, yy * GameValues.tileSize), 6,  ObjectId.Road));
				
				// zombie Generator Weak
				else if(blue == 150 && red == 150 && green == 150)
					generator.add(new ZombieGenerator(GameValues.GameObjects.ZombieGenerators.Weak.spawnInterval, new Vector2(xx * GameValues.tileSize, yy * GameValues.tileSize), GameValues.GameObjects.ZombieGenerators.Weak.Velocity, GameValues.GameObjects.ZombieGenerators.Weak.spawnHP, GameValues.GameObjects.ZombieGenerators.Weak.spawnEXP, GameValues.GameObjects.ZombieGenerators.Weak.spawnDamage, GameValues.GameObjects.ZombieGenerators.Weak.smart, GameValues.GameObjects.ZombieGenerators.Weak.deathSoundPath, GameValues.GameObjects.ZombieGenerators.Weak.maxGenerated, zombieWI));

				// zombie Generator Medium
				else if(blue == 160 && red == 160 && green == 160)
					generator.add(new ZombieGenerator(GameValues.GameObjects.ZombieGenerators.Medium.spawnInterval, new Vector2(xx * GameValues.tileSize, yy * GameValues.tileSize), GameValues.GameObjects.ZombieGenerators.Medium.Velocity, GameValues.GameObjects.ZombieGenerators.Medium.spawnHP, GameValues.GameObjects.ZombieGenerators.Medium.spawnEXP, GameValues.GameObjects.ZombieGenerators.Medium.spawnDamage, GameValues.GameObjects.ZombieGenerators.Medium.smart, GameValues.GameObjects.ZombieGenerators.Medium.deathSoundPath, GameValues.GameObjects.ZombieGenerators.Medium.maxGenerated, zombieMI));

				// zombie Generator Hard
				else if(blue == 200 && red == 200 && green == 200)
					generator.add(new ZombieGenerator(GameValues.GameObjects.ZombieGenerators.Hard.spawnInterval, new Vector2(xx * GameValues.tileSize, yy * GameValues.tileSize), GameValues.GameObjects.ZombieGenerators.Hard.Velocity, GameValues.GameObjects.ZombieGenerators.Hard.spawnHP, GameValues.GameObjects.ZombieGenerators.Hard.spawnEXP, GameValues.GameObjects.ZombieGenerators.Hard.spawnDamage, GameValues.GameObjects.ZombieGenerators.Hard.smart, GameValues.GameObjects.ZombieGenerators.Hard.deathSoundPath, GameValues.GameObjects.ZombieGenerators.Hard.maxGenerated, zombieHI));

				// zombie Generator HardCore
				else if(blue == 210 && red == 210 && green == 210)
					generator.add(new ZombieGenerator(GameValues.GameObjects.ZombieGenerators.HardCore.spawnInterval, new Vector2(xx * GameValues.tileSize, yy * GameValues.tileSize), GameValues.GameObjects.ZombieGenerators.HardCore.Velocity, GameValues.GameObjects.ZombieGenerators.HardCore.spawnHP, GameValues.GameObjects.ZombieGenerators.HardCore.spawnEXP, GameValues.GameObjects.ZombieGenerators.HardCore.spawnDamage, GameValues.GameObjects.ZombieGenerators.HardCore.smart, GameValues.GameObjects.ZombieGenerators.HardCore.deathSoundPath, GameValues.GameObjects.ZombieGenerators.HardCore.maxGenerated, zombieHCI));

				// zombie Generator IdoBoss
				else if(blue == 170 && red == 170 && green == 170)
					generator.add(new ZombieGenerator(GameValues.GameObjects.ZombieGenerators.Boss.spawnInterval, new Vector2(xx * GameValues.tileSize, yy * GameValues.tileSize), GameValues.GameObjects.ZombieGenerators.Boss.Velocity, GameValues.GameObjects.ZombieGenerators.Boss.spawnHP, GameValues.GameObjects.ZombieGenerators.Boss.spawnEXP, GameValues.GameObjects.ZombieGenerators.Boss.spawnDamage, GameValues.GameObjects.ZombieGenerators.Boss.smart, GameValues.GameObjects.ZombieGenerators.Boss.deathSoundPath, GameValues.GameObjects.ZombieGenerators.Boss.maxGenerated, IdoBossI));
				
				// zombie Generator Ninja
				else if(blue == 180 && red == 180 && green == 180)
					generator.add(new ZombieGenerator(GameValues.GameObjects.ZombieGenerators.ninjaZombie.spawnInterval, new Vector2(xx * GameValues.tileSize, yy * GameValues.tileSize), GameValues.GameObjects.ZombieGenerators.ninjaZombie.Velocity, GameValues.GameObjects.ZombieGenerators.ninjaZombie.spawnHP, GameValues.GameObjects.ZombieGenerators.ninjaZombie.spawnEXP, GameValues.GameObjects.ZombieGenerators.ninjaZombie.spawnDamage, GameValues.GameObjects.ZombieGenerators.ninjaZombie.smart, GameValues.GameObjects.ZombieGenerators.ninjaZombie.deathSoundPath, GameValues.GameObjects.ZombieGenerators.ninjaZombie.maxGenerated, NinjaI));
				
				// zombie Generator bonus
				else if(blue == 190 && red == 190 && green == 190)
					generator.add(new ZombieGenerator(GameValues.GameObjects.ZombieGenerators.bonus.spawnInterval, new Vector2(xx * GameValues.tileSize, yy * GameValues.tileSize), GameValues.GameObjects.ZombieGenerators.bonus.Velocity, GameValues.GameObjects.ZombieGenerators.bonus.spawnHP, GameValues.GameObjects.ZombieGenerators.bonus.spawnEXP, GameValues.GameObjects.ZombieGenerators.bonus.spawnDamage, GameValues.GameObjects.ZombieGenerators.bonus.smart, GameValues.GameObjects.ZombieGenerators.bonus.deathSoundPath, GameValues.GameObjects.ZombieGenerators.bonus.maxGenerated, bonusI));

				// end
				else if(blue == 0 && red == 0 && green == 255)
					objHandler.addObject(new Block(new Dimension(GameValues.tileSize, GameValues.tileSize), new Vector2(xx * GameValues.tileSize, yy * GameValues.tileSize), 7,  ObjectId.End));
				
				// Health Pack
				else if(blue == 100 && red == 10 && green == 100)
					objHandler.addObject(new Block(new Dimension(GameValues.tileSize, GameValues.tileSize), new Vector2(xx * GameValues.tileSize, yy * GameValues.tileSize), 9,  ObjectId.HealthPack));
				
				// Ammo Pack
				else if(blue == 255 && red == 10 && green == 255)
					objHandler.addObject(new Block(new Dimension(GameValues.tileSize, GameValues.tileSize), new Vector2(xx * GameValues.tileSize, yy * GameValues.tileSize), 8,  ObjectId.AmmoPack));

				//player
				else if(blue == 100 && red == 100 && green == 100)
					Game.player = new Player(playerI, GameValues.GameObjects.Player.velocity, new Dimension(playerI.getWidth(), playerI.getHeight()), new Vector2(xx * GameValues.tileSize + playerI.getWidth(), yy * GameValues.tileSize - playerI.getHeight() / 2), new DefalutPistol() , objHandler,zombieHandler , ObjectId.Player);
			}
		}
	}
}
