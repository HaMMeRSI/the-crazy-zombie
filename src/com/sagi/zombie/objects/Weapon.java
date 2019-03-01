package com.sagi.zombie.objects;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import com.sagi.zombie.addons.ObjectId;
import com.sagi.zombie.addons.Sound;
import com.sagi.zombie.addons.Vector2;
import com.sagi.zombie.handlers.ObjectHandler;
import com.sagi.zombie.handlers.ZombieHandler;
import com.sagi.zombie.weapons.BaseOfWeapon;
import com.sagi.zombie.weapons.DefalutPistol;
import com.sagi.zombie.window.Game;

/**
 * Players Weapon Class
 * @author sagi
 */
public class Weapon {
	private boolean readyToShoot; // if Ready to shot
	// array to store the to be shooted projectiles position
	private ArrayList<Projectile> projectiles; // store bullets here
	private Projectile testedProjectile; // temp to save CPU
	private GameObject tempGameObject;   // temp to save CPU
	private ObjectHandler objHandler;
	private ZombieHandler zombieHandler;
	private ArrayList<GameObject> retrivedCollision; // retrieve collision blocks
	private float velFactor;  // spread X
	private float velFactor1; // spread Y
	private int currentAmmo;  // Ammo
	private BaseOfWeapon usedWeapon; // the weapon type
	private Random rnd;
	private ScheduledExecutorService scheduler;
	private Runnable rn;
	private boolean toBreakLoopAfterDelete;
	public static boolean toSound = false;
	/**
	 * Ctor
	 * @param objHandler
	 * @param zombieHandler
	 * @param weap
	 */

	public Weapon(ObjectHandler objHandler, ZombieHandler zombieHandler, BaseOfWeapon weap)
	{
		this.readyToShoot = false;
		this.projectiles = new ArrayList<>();
		this.retrivedCollision = new ArrayList<>();
		this.objHandler = objHandler;
		this.zombieHandler = zombieHandler;
		this.usedWeapon = weap;
		this.currentAmmo = usedWeapon.ammunition;
		this.rnd = new Random();
		this.scheduler = Executors.newSingleThreadScheduledExecutor(new MyThreadFactory());
		this.rn = new Runnable() {
			public void run() {
				readyToShoot = true;
			}
		};
		activeTimer();
	}

	/**
	 * Weapon Timer shoot per interval
	 */
	private void activeTimer()
	{
		scheduler.schedule(rn, usedWeapon.weaponInterval, TimeUnit.MILLISECONDS);
	}

	/**
	 * shoot 1 bullet
	 * @param shootFrom 
	 * @param shootTo
	 */
	public void shootOneBullet(Vector2 shootFrom, Vector2 shootTo)
	{
		if(readyToShoot)
		{
			if(currentAmmo > 0)
			{
				Sound.playSfx(usedWeapon.soundPath);
				for(int i = 0; i < usedWeapon.bulletsPerShot; i++)
				{
					Vector2 velocity = Vector2.Sub(shootTo, shootFrom);
					velocity.Norm();

					velocity.Mul(usedWeapon.weaponSpeed);
					velFactor  = (rnd.nextInt() % 2 == 0) ? -rnd.nextFloat() : rnd.nextFloat();
					velFactor1 = (rnd.nextInt() % 2 == 0) ? -rnd.nextFloat() : rnd.nextFloat();
					velocity.Add(new Vector2(velFactor * usedWeapon.spread, velFactor1 * usedWeapon.spread));
					projectiles.add(new Projectile(velocity, usedWeapon.weaponDim, Vector2.Sub(shootFrom.Clone(), usedWeapon.weaponDim.height / 2), usedWeapon.weaponDamage, ObjectId.Projectile));
					velocity = null;
				}
				currentAmmo--;
			} else setWeapon(new DefalutPistol());
			readyToShoot = false;
			activeTimer();
		}
	}

	/**
	 * Update Projectiles
	 */
	public void update()
	{
		for(int i = 0; i < projectiles.size(); i++)
		{
			testedProjectile = projectiles.get(i);
			testedProjectile.update();
			// check View collision
			if(testedProjectile.getObjPos().getX() >= Game.cameraView.getX() + Game.cameraView.width || testedProjectile.getObjPos().getY() >= Game.cameraView.getY() + Game.cameraView.height 
					|| testedProjectile.getObjPos().getX() <= Game.cameraView.getX() 		 				 || testedProjectile.getObjPos().getY() <= Game.cameraView.getY())
				projectiles.remove(i);

			else 
			{
				// check static object collision
				objHandler.getObjects().retriveRecs(retrivedCollision, testedProjectile);
				for(int j = 0; j < retrivedCollision.size(); j++)
				{
					if(checkCollision(ObjectId.Block, retrivedCollision.get(j)))
					{
						projectiles.remove(i);
						toBreakLoopAfterDelete = true;
						break;
					}
				}
				retrivedCollision.clear();
				if(toBreakLoopAfterDelete)
				{
					toBreakLoopAfterDelete = false;
					i--;
					continue;
				}
				// check zombie collision
				for(int j = 0; j < zombieHandler.getZombies().size(); j++)
				{
					tempGameObject = zombieHandler.getZombies().get(j);
					if(checkCollision(ObjectId.Zombie, tempGameObject))
					{
						// if not a blast, hit 1
						if(true)
						{
							tempGameObject.hit(testedProjectile.getDamage());
							// if zombie dead
							if(tempGameObject.getHp() <= 0)
							{
								zombieHandler.removeZombie(j);
								Sound.playSfx(((Zombie)tempGameObject).getDeathSoundPath());
								Game.player.addExp(((Zombie)tempGameObject).getGrantedExp());
								j--;
							}
						} 
						// else { if blast, in comments below! }
						// remove projectile unless its a laser
						if(!usedWeapon.laser)
						{
							projectiles.remove(i);
							i--;
							toBreakLoopAfterDelete = true;
							break; 
						}
					} // end if collided
				} // end zombie loop
			} // end else if in camera view
		} // end projectile loop
	}

	/**
	 * check collision with required ids
	 * @param objId
	 * @param testWithTile
	 * @return is collided
	 */
	public boolean checkCollision(ObjectId objId, GameObject testWithTile)
	{
		return (testedProjectile.getObjRectangle().intersects(testWithTile.getObjRectangle()) && testWithTile.objId == objId);
	}

	/**
	 * get current ammo
	 * @return current ammo
	 */
	public int getAmmo()
	{
		return this.currentAmmo;
	}

	/**
	 * get total ammo
	 * @return total ammo
	 */
	public int getMagazineAmmo()
	{
		return usedWeapon.ammunition;
	}

	/**
	 * get weapon name
	 * @return weapon name
	 */
	public String getName()
	{
		return usedWeapon.name;
	}

	/**
	 * set Weapon
	 * @param pickedWeapon the change to weapon
	 */
	public void setWeapon(BaseOfWeapon pickedWeapon)
	{
		this.usedWeapon = pickedWeapon;
		this.currentAmmo = pickedWeapon.ammunition;
	}

	/**
	 * set ammo / decrease / increase ammo
	 * @param amount set / decrease / increase
	 */
	public void setAmmo(int amount)
	{
		this.currentAmmo += amount;
	}

	/**
	 * render Projectiles
	 * @param g Graphics
	 */
	public void render(Graphics g)
	{
		g.setColor(usedWeapon.color);
		for(int i = 0; i < projectiles.size(); i++)
			projectiles.get(i).render(g);
	}
}
/*else*/
/*{
	for(int z = 0; z < zombieHandler.getZombies().size();)
	{
		tempGameObject = zombieHandler.getZombies().get(z);
		if((new Rectangle((int)testedProjectile.getObjPos().getX() - 50, (int)testedProjectile.getObjPos().getY() - 50, 100, 100)
		    .intersects(tempGameObject.getObjRectangle())))
		{
			tempGameObject.hit(testedProjectile.getDamage());
			if(tempGameObject.getHp() <= 0) 
			{
				zombieHandler.getZombies().remove(z);
				Sound.playSound(((Zombie)tempGameObject).getDeathSoundPath());
				Game.player.addExp(((Zombie)tempGameObject).getGrantedExp());
			}
		}
		else z++;
	}
}*/

class MyThreadFactory implements ThreadFactory
{
	public Thread newThread(Runnable r) {
		return new Thread(r, "scheduled Weapon");
	}
}
