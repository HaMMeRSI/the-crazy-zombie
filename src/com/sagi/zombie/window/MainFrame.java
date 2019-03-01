package com.sagi.zombie.window;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.sagi.zombie.addons.GameValues;
import com.sagi.zombie.imagesHandler.BufferedImageLoader;

/**
 * main game frame
 * @author sagi
 *
 */
public class MainFrame {
	BufferedImage bg;
	
	/**
	 * ctor
	 */
	public MainFrame()
	{
		BufferedImageLoader Il = new BufferedImageLoader();
		bg = Il.loadImage(GameValues.GameImages.MainScreenBG);
		JFrame frame = new JFrame("Play Crazy Zombie");
		mainPanel mp = new mainPanel();
		frame.add(mp);
		mp.repaint();
		mp.setLayout(null);
		frame.setFocusable(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// set the Window to Max Screen
		frame.setSize(bg.getWidth(), bg.getHeight());
		frame.setVisible(true);
	}
	
	/**
	 * panel helper
	 * @author sagi
	 *
	 */
	@SuppressWarnings("serial")
	public class mainPanel extends JPanel implements ActionListener
	{
		private JButton playGame;
		private JButton ExitGame;
		private JButton instGame;

		/**
		 * ctor
		 * create 3 buttins and add them an action Listener
		 */
		public mainPanel()
		{
			playGame = new JButton("Play");
			playGame.setBounds(bg.getWidth() / 2 - 50, 140, 100, 55);
			playGame.setBorder(null);
			playGame.setForeground(Color.white);
			playGame.setFont(new Font(getName(), PROPERTIES, 50));
			playGame.setBorderPainted(false); 
			playGame.setContentAreaFilled(false); 
			this.add(playGame);
			playGame.addActionListener(this);
			
			instGame = new JButton("Instructions");
			instGame.setBounds(bg.getWidth() / 2 - 135, 200, 270, 55);
			instGame.setBorder(null);
			instGame.setForeground(Color.white);
			instGame.setFont(new Font(getName(), PROPERTIES, 50));
			instGame.setBorderPainted(false); 
			instGame.setContentAreaFilled(false); 
			this.add(instGame);
			instGame.addActionListener(this);
			
			ExitGame = new JButton("Exit");
			ExitGame.setBounds(bg.getWidth() / 2 - 50, 260, 100, 55);
			ExitGame.setBorder(null);
			ExitGame.setForeground(Color.white);
			ExitGame.setFont(new Font(getName(), PROPERTIES, 50));
			ExitGame.setBorderPainted(false); 
			ExitGame.setContentAreaFilled(false); 
			this.add(ExitGame);
			ExitGame.addActionListener(this);
		}
		/**
		 * paint the background
		 */
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			g.drawImage(bg, 0, 0, null);
		}

		/**
		 * check which button pressed and does according to it meaning
		 */
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals("Play"))
				new Window(1000, 700, "Crazy Zombie Game", new Game());	
			//	new Window("Crazy Zombie Game", new Game());	
			if(e.getActionCommand().equals("Exit"))
				System.exit(1);
			if(e.getActionCommand().equals("Instructions"))
				new InstructionsWindow();
		}
	}
	
}
