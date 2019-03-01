package com.sagi.zombie.addons;
/**
 * Static class for all final Game Values
 * @author sagi
 */
public class GameValues {
	public static final int tileSize = 64;
	
	public static class GameObjects
	{
		public static class Player
		{
			public static final Vector2 velocity = new Vector2(5,5); 
			public static final int boosterSpeed = 5;
			public static final float levelExpFactor = .1f;
			public static final boolean toDrawPlayerPath = false;
		}
		public static class ZombieGenerators
		{
			public static class bonus
			{
				public static final Vector2 Velocity = new Vector2(1, 1);
				public static final int spawnInterval = 1;
				public static final int spawnDamage = 1;
				public static final int spawnHP = 50;
				public static final int spawnEXP = 10;
				public static final int maxGenerated = 20;
				public static final boolean smart = false;
				public static final String deathSoundPath = "Sounds\\zombiedeath.wav";
			}
			public static class Weak
			{
				public static final Vector2 Velocity = new Vector2(2, 2);
				public static final int spawnInterval = 200;
				public static final int spawnDamage = 50;
				public static final int spawnHP = 100;
				public static final int spawnEXP = 5;
				public static final int maxGenerated = 15;
				public static final boolean smart = true;
				public static final String deathSoundPath = "Sounds\\zombiedeath.wav";
			}
			public static class Medium
			{
				public static final Vector2 Velocity = new Vector2(1, 1);
				public static final int spawnInterval = 200;
				public static final int spawnDamage = 10;
				public static final int spawnHP = 150;
				public static final int spawnEXP = 8;
				public static final int maxGenerated = 10;
				public static final boolean smart = true;
				public static final String deathSoundPath = "Sounds\\zombiedeath.wav";
			}
			public static class Hard
			{
				public static final Vector2 Velocity = new Vector2(1, 1);
				public static final int spawnInterval = 200;
				public static final int spawnDamage = 20;
				public static final int spawnHP = 500;
				public static final int spawnEXP = 40;
				public static final int maxGenerated = 15;
				public static final boolean smart = true;
				public static final String deathSoundPath = "Sounds\\zombiedeath.wav";
			}
			public static class HardCore
			{
				public static final Vector2 Velocity = new Vector2(2, 2);
				public static final int spawnInterval = 200;
				public static final int spawnDamage = 30;
				public static final int spawnHP = 900;
				public static final int spawnEXP = 80;
				public static final int maxGenerated = 10;
				public static final boolean smart = true;
				public static final String deathSoundPath = "Sounds\\zombiedeath.wav";
			}
			public static class ninjaZombie
			{
				public static final Vector2 Velocity = new Vector2(6, 6);
				public static final int spawnInterval = 300;
				public static final int spawnDamage = 6;
				public static final int spawnHP = 90;
				public static final int spawnEXP = 10;
				public static final int maxGenerated = 9;
				public static final boolean smart = true;
				public static final String deathSoundPath = "Sounds\\zombiedeath.wav";
			}
			public static class Boss
			{
				public static final Vector2 Velocity = new Vector2(1, 1);
				public static final int spawnInterval = 120000;
				public static final int spawnDamage = 35;
				public static final int spawnHP = 1000;
				public static final int spawnEXP = 200;
				public static final int maxGenerated = 3;
				public static final boolean smart = true;
				public static final String deathSoundPath = "Sounds\\bossdeath.wav";
			}
		}
	}
	
	public static class GameImages
	{
		public static String Player 			= "Images\\playerAndEnemys\\Player.png";
		public static String ZombieWeakPath 	= "Images\\playerAndEnemys\\zombieWeak.png";
		public static String ZombieMediumPath 	= "Images\\playerAndEnemys\\zombieMedium.png";
		public static String ZombieHardPath 	= "Images\\playerAndEnemys\\zombieHard.png";
		public static String ZombieHardCorePath = "Images\\playerAndEnemys\\zombieHardCore.png";
		public static String BonusPath 			= "Images\\playerAndEnemys\\zombiBonus.png";
		public static String IdoBossPath 		= "Images\\playerAndEnemys\\BossIdo.png";
		public static String NinjaPath 			= "Images\\playerAndEnemys\\zombieNinja.png";
		public static String MainScreenBG 		= "Images\\mainFrameImage.png";
	}
}
