package com.sagi.zombie.maps;
/**
 * base level stores the parameters all levels inherits this class
 * @author sagi
 *
 */
public class BaseOfLevels {
	public String levelDesign;
	public String backGround;
	public BaseOfLevels nextLevel;
	
	/**
	 * Ctor
	 * @param levelDesign
	 * @param backGround
	 * @param nextLevel
	 */
	public BaseOfLevels(String levelDesign, String backGround, BaseOfLevels nextLevel)
	{
		this.levelDesign = levelDesign;
		this.backGround = backGround;
		this.nextLevel = nextLevel;
	}
}
