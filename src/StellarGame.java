/**
 * StellarGame.java
 *
 * A class to contain the actual game we are making
 * 
 * Functionalities include:
 *    - game creation
 *    - game state updates
 *    - graphical representation
 * 
 * Author:       Erik Steringer
 * Last Updated: 2014-Nov-9 by Erik Steringer
 */

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class StellarGame extends BasicGame {
	/// fields
	private World gameWorld;
	
	/// constructors
	/**
	 * Constructor, takes a string for the game's name
	 * "initializes" gameWorld so init can load all the stuff we need
	 */
	public FugitiveGame(String gamename) {
		super(gamename);
		this.gameWorld = null;
	}
	
	/// methods
	
	/**
	 * Initiates the game
	 * Loads media or objects needed for the game to run
	 * Throws a SlickException
	 */
	@Override
	public void init(GameContainer gc) throws SlickException { }
	
	/**
	 * Updates our game's state
	 * Throws a SlickException
	 */
	@Override
	public void update(GameContainer gc, int i) throws SlickException { }
	
	/**
	 * Renders our game onto the screen
	 * Throws a SlickException
	 */
	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException { }
	
}