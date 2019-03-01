package com.sagi.zombie.window;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.Set;

import com.sagi.zombie.AStar.AStar;
import com.sagi.zombie.QTree.QTree;
import com.sagi.zombie.addons.GameValues;
import com.sagi.zombie.addons.MapLoader;
import com.sagi.zombie.addons.MiniMap;
import com.sagi.zombie.addons.Vector2;
import com.sagi.zombie.events.KeyBoardEvents;
import com.sagi.zombie.events.MouseActionEvents;
import com.sagi.zombie.events.MouseMovementEvents;
import com.sagi.zombie.handlers.GeneratorHandler;
import com.sagi.zombie.handlers.ObjectHandler;
import com.sagi.zombie.handlers.ZombieHandler;
import com.sagi.zombie.imagesHandler.BufferedImageLoader;
import com.sagi.zombie.imagesHandler.Texture;
import com.sagi.zombie.maps.BaseOfLevels;
import com.sagi.zombie.maps.Level1;
import com.sagi.zombie.objects.Player;
/**
 * Handle the game
 * @author SaGI HuMMeR
 */
public class Game extends Canvas implements Runnable {
	
	private static final long serialVersionUID = 1L;
	public static int width ; // frame width
	public static int height ; // frame height
	public static Rectangle cameraView;
	public static Player player; // player object
	public static BufferedImage level = null;
	public static Texture texture; // game texture Object
	public static boolean gameWon = false; // if player reached the spaceCraft
	public static AStar AI = new AStar();
	
	public static int[][] mapDesign; // mapImage as array of colors
	
	Thread mainThread; // Game Main Thread
	boolean running = false; // is game is running
	boolean loaded = false;
	Font gameFont;
	ObjectHandler objHandler; 
	KeyBoardEvents kbe; // KeyBoard Events
	MouseActionEvents mae; // mouse action events
	MouseMovementEvents mme; // mouse Motion Events
	AffineTransform beforePlayer; // stores current transform
	AffineTransform beforeCamera; // stores the state before camera transform
	Camera cam; // camera object
	Vector2 relativeMousePosition = new Vector2(); // fix for mouse location and the camera 
	BufferedImage bg = null; // backGround Image
	BufferedImage playerI = null; // player Image
	BufferedImage zombieI = null; // player Image
	//Dimension levelDim; // level Dimension
	BufferStrategy bs; // buffer strategy triple or double
	
	ZombieHandler zombHandler; // zomibie Handler
	// ArrayList<ZombieGenerator> generator; // Zombie Generators
	GeneratorHandler generatorHandler;
	BaseOfLevels currentLevel; // current played Level
	MiniMap miniMap; // the minimap
	Thread t = new Thread();
	
	public void start()
	{
		if(running)
			return;

		running = true;
		mainThread = new Thread(this, "Main Thread");
		mainThread.start();
	}
	
	public void run() {
		initGame();
		this.requestFocus();
		gameLoop();
	}
	
	/**
	 * Initial the game
	 * set values to properties
	 * load Images
	 * add Events
	 * set the camera
	 */
	public void initGame()
	{
		gameFont = new Font("Serif", Font.BOLD, 22);
		// create new image Reader
		BufferedImageLoader bfl = new BufferedImageLoader();
		// init camera
		this.cam = new Camera(new Vector2());
		// set new Texture Handler
		texture = new Texture();
		// get frame width and height
		width = getWidth();
		height = getHeight();
		
		/***** Init Map for the First time *****/
		currentLevel = new Level1();
		level = bfl.loadImage(currentLevel.levelDesign);
		zombHandler = new ZombieHandler();
		generatorHandler = new GeneratorHandler();
		objHandler = new ObjectHandler(level.getWidth() * GameValues.tileSize, level.getHeight() * GameValues.tileSize);

		loadNextMap(bfl);
		miniMap = new MiniMap(20);
				
		// Create mouse + KeyBoard Event class
		kbe = new KeyBoardEvents();
		mae = new MouseActionEvents();
		mme = new MouseMovementEvents();
		// add  mouse + KeyBoard listeners by the classes
		this.addKeyListener(kbe);
		this.addMouseListener(mae);
		this.addMouseMotionListener(mme);
	}
	
	/**
	 * responsible for game logic update per period of time
	 */
	public void tick()
	{
		width = getWidth();
		height = getHeight();
		// check if the game Won
		if(gameWon)
		{
			BufferedImageLoader bfl = new BufferedImageLoader();
			currentLevel = currentLevel.nextLevel;
			loadNextMap(bfl);
			gameWon = false;
		}

		// handle events
		keyHandler();
		mouseHandler();

		// update the objects --- not needed ---
		// objHandler.update();
		
		// update the player
		player.update();
		// calculate current player angle by player point to mouse point
		player.setAngle((Math.atan2(relativeMousePosition.getY() - player.getObjPos().getY(), relativeMousePosition.getX() - player.getObjPos().getX()) - Math.PI / 2));

		// update Generators
		if(!generatorHandler.areAllDone())
			generatorHandler.update(zombHandler);
				
		// update zombies
		zombHandler.update();
		
		// fix mouse position with camera
		relativeMousePosition.setCords(mme.getMousePos().getX() - cam.getX(), mme.getMousePos().getY() - cam.getY());

		// update camera view location according to the player
		cam.tick();
		// update camera view
		cameraView.x = (int)-cam.getX() - 100;
		cameraView.y = (int)-cam.getY() - 100;
		cameraView.width =   getWidth() + 100;
		cameraView.height = getHeight() + 100;
		// cameraView = new Rectangle((int)-cam.getX() - 100, (int)-cam.getY() - 100, width + 100, height + 100);
		
		// update mini map
		miniMap.update();
		loaded = true;
	}
	
	/**
	 * responsible for game graphics and lookOut
	 */
	public void render()
	{
		if(loaded)
		{
			// init buffer Strategy
		    bs = this.getBufferStrategy();
		    // if its null create a Triple buffer and exit
			if(bs == null) {
				this.createBufferStrategy(3);
				return;
			}
			// convert the Strategy to graphics object
			Graphics g = bs.getDrawGraphics();
			// convert the graphics object to 2d graphics object
			Graphics2D g2d = (Graphics2D) g;
			g.fillRect(0, 0, getWidth(), getHeight());
			// draw the backGround aligned to the camera
			for(int i = 0; i < level.getWidth() * (GameValues.tileSize + 1) ; i += bg.getWidth())
				for(int j = 0; j < level.getHeight() * (GameValues.tileSize + 1); j += bg.getHeight())
					g.drawImage(bg, (int)(cam.getX() + i - width / 2), (int)(cam.getY() + j - height / 2), null);
	
			beforeCamera = g2d.getTransform();
			// translate the form by camera location
			g2d.translate(cam.getX(), cam.getY());
	
			// draw all static (stand still) objects 
			objHandler.render(g);
			// store the old transform state
			beforePlayer = g2d.getTransform();
			// set player location and rotate it by need
			player.translateObject(g2d);
			//draw player
			player.render(g);

			// Restores the transform to original state before the rotation for other objects
			g2d.setTransform(beforePlayer);

			g2d.setColor(Color.white);
			zombHandler.render(g);
			player.renderBullets(g);
			
			// drawTree(g2d, objHandler.getObjects());
			// restore initial Transform
			g2d.setTransform(beforeCamera);
			
			miniMap.render(g);
			
			renderTexts(g2d);
			// dispose of graphic object to save memory
			g.dispose();
			// draw the buffers
			bs.show();
		}
	}
	
	/**
	 * to delete draws the quad Tree
	 * @param g2d
	 * @param node
	 */
	public void drawTree(Graphics2D g2d, QTree node)
	{
		if(node == null)
			return;
		g2d.setColor(Color.red);
		g2d.draw(node.getBounds());
		for(int i = 0; i < 4; i++)
		{
			drawTree(g2d, node.getNodes(i));
		}
	}
	
	/**
	 * draws the text
	 * @param g2d graphics object
	 */
	public void renderTexts(Graphics2D g2d)
	{
		g2d.setFont(gameFont);
		g2d.setColor(Color.white);
		g2d.drawString("Player hp: " + player.getHp(), 10, 20);
		g2d.drawString("Level: " + player.getLevel() + " : " + player.getExp() + " / " + player.getLevelExp() + " exp", 10, 50);
		g2d.drawString("Ammo: " + player.getUsedWeapon().getAmmo(), 10, 80);
		g2d.drawString("Weapon: " + player.getUsedWeapon().getName(), 10, 110);
	}

	/**
	 * load map
	 * init handlers to map
	 * @param bfl image loader helper
	 */
	public void loadNextMap(BufferedImageLoader bfl)
	{
		// Init cam
		cam.setX(0);
		cam.setY(0);
		bg = bfl.loadImage(currentLevel.backGround);
		level = bfl.loadImage(currentLevel.levelDesign);		
		objHandler.reCreate(level.getWidth() * GameValues.tileSize, level.getHeight() * GameValues.tileSize);
		zombHandler.clear();
		generatorHandler.clear();
		
		new MapLoader(currentLevel.levelDesign, objHandler, zombHandler, generatorHandler);
		cameraView = new Rectangle((int)(player.getObjRectangle().getX() - getWidth() / 2 + player.getObjRectangle().getWidth()  *2),(int)player.getObjPos().getY() - height / 2,width, height);
	}

	/**
	 * handle the key Board event
	 */
	public void keyHandler()
	{
		// fix if mouse position equals to player location
		if(relativeMousePosition.GetDistance(player.getObjPos()) >= 5)
			if(kbe.getPressedKeys().contains(KeyEvent.VK_W))
				player.move(relativeMousePosition, 1);
		
		// if key array has the key S move backwards to mouse position
		if(kbe.getPressedKeys().contains(KeyEvent.VK_S))
			player.move(relativeMousePosition, -1);
		
		// if key array has the key Shift boost speed
		if(kbe.getPressedKeys().contains(KeyEvent.VK_SHIFT))
			player.activeBooster();
		else player.deactiveBooster();
		
	    if(kbe.getPressedKeys().contains(KeyEvent.VK_F1))
	    {
	    	System.gc();
	    	System.out.println("GC");
	    }
	    if(kbe.getPressedKeys().contains(KeyEvent.VK_F2))
	    {
	    	if(!pr)
	    	{
		    	Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
		    	Thread[] threadArray = threadSet.toArray(new Thread[threadSet.size()]);	    
		    	for(int i = 0; i < threadArray.length; i++)
		    	{
		    		System.out.println(threadArray[i].getName());
		    	}
		    	System.out.println("Threads: " + threadArray.length);
	    	}
	    }
	}	
	boolean pr = false;

	/**
	 * handle the Mouse Events
	 */
	public void mouseHandler()
	{
		// shoot bullet 
		if(mae.isMouseDown())
			player.shootOneBullet(relativeMousePosition);
	}

	public void gameLoop()
	{
		int updates = 0;
		int frames = 0;
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1){
				tick();
				updates++;
				delta--;
			}
			render();
			frames++;
					
			if(System.currentTimeMillis() - timer > 1000){
				timer += 1000;
				if(Window.frame != null)
					Window.frame.setTitle("FPS: " + frames + " TICKS: " + updates);
				frames = 0;
				updates = 0;
			}
		}
	}
}