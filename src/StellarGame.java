import org.newdawn.slick.*;
import org.newdawn.slick.geom.*;
import org.newdawn.slick.fills.*;
import org.newdawn.slick.state.*;

/**
 * Contains the gamestates used to run the StellarFugitive application.
 *
 */
public class StellarGame extends StateBasedGame {
	private MenuState mainMenu;
	private InGameState ingame;
	
	/**
	 * Constructor taking a String for the window title.
	 * 
	 * @param gamename The String used as for the title of the OS-generated window.
	 */
	public StellarGame(String gamename) {
		super(gamename);
	}
	
	/**
	 * Adds the game-states that are switched between during the application's 
	 * runtime. Enters the main menu's state.
	 *
	 * @param gc The GameContainer needed for initialization.
	 */
	@Override
	public void initStatesList(GameContainer gc) {
		this.mainMenu = new MenuState();
		this.ingame = new InGameState();
		addState(mainMenu);
		addState(ingame);
		enterState(mainMenu.getID());
	}
}