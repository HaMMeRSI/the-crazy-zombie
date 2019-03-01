package com.sagi.zombie.window;

import javax.swing.JFrame;

public class Window {
	/**
	 * class to handle new frames that start the game
	 * @param w Width of the game frame
	 * @param h Height of the game frame
	 * @param title frame title
	 * @param game canvas component, adds it to the frame
	 */
	public static JFrame frame;

	public Window(int w, int h, String title, Game game)
	{
		frame = new JFrame(title);
		frame.add(game);
		game.start();
		frame.setFocusable(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(w, h);
		frame.setVisible(true);
	}
	/**
	 * class to handle new frames that start the game
	 * started maximized
	 * @param title frame title
	 * @param game canvas component, adds it to the frame
	 */
	public Window(String title, Game game)
	{
		frame = new JFrame(title);
		frame.add(game);
		frame.setFocusable(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// set the Window to Max Screen
		frame.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
		frame.setVisible(true);
		game.start();
	}

}
