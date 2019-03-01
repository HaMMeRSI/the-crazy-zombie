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

import com.sagi.zombie.imagesHandler.BufferedImageLoader;
/**
 * Instructions Window
 * @author sagi
 *
 */
@SuppressWarnings("serial")
public class InstructionsWindow extends JPanel{
	
	BufferedImage bg;
	JFrame frame;
	/**
	 * ctor 
	 * loads the Image 
	 * creates new Frame
	 * attach the custom panel
	 */
	public InstructionsWindow()
	{
			BufferedImageLoader Il = new BufferedImageLoader();
			bg = Il.loadImage("Images\\Instructions.png");
			frame = new JFrame("Play Crazy Zombie");
			frame.setFocusable(true);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			// set the Window to Max Screen
			frame.setSize(bg.getWidth() + 15, bg.getHeight() + 100);
			frame.setVisible(true);
			mainPanel mp = new mainPanel();
			frame.add(mp);
			mp.repaint();
			mp.setLayout(null);
	}
	/**
	 * custom panel to handle background and button
	 * @author sagi
	 *
	 */
	public class mainPanel extends JPanel implements ActionListener
	{
		private JButton ExitGame;
		/**
		 * ctor
		 * define exit button
		 */
		public mainPanel()
		{	
			ExitGame = new JButton("Exit");
			ExitGame.setBounds(bg.getWidth() / 2 - 50, 480, 100, 55);
			ExitGame.setBorder(null);
			ExitGame.setFont(new Font(getName(), PROPERTIES, 50));
			ExitGame.setBorderPainted(false); 
			ExitGame.setContentAreaFilled(false); 
			this.add(ExitGame);
			ExitGame.addActionListener(this);
		}
		/**
		 * paint backGround
		 */
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			setBackground(Color.white);
			g.drawImage(bg, 0, 0, null);
		}

		/**
		 * hide frame at click
		 */
		@SuppressWarnings("deprecation")
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals("Exit"))
				frame.hide();
		}
	}
}
