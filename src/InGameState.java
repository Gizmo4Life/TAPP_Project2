import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

/**
 * Class to contain the in-game state of the program, contains the World which 
 * can be instantiated once this state is entered.
 *
 */
public class InGameState extends BasicGameState {
	private World gameWorld;
	
	/**
	 * Constructor, takes no args, does not initialize the contained World.
	 *
	 */
	public InGameState() {
		gameWorld = null;
	}
	
	/**
	 * Method that's called once this state is entered. Instantiates the contained 
	 * World object.
	 *
	 * @param gc The GameContainer needed for entry
	 * @param sbg The StateBasedGame that contains this GameState
	 */
	@Override
	public void enter(GameContainer gc, StateBasedGame sbg) {
		gameWorld = new World();
	}
	
	/**
	 * Method that initializes this GameState
	 *
	 * @param gc The GameContainer object needed for initialization
	 * @param sbg The StateBasedGame that contains this GameState
	 */
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		
	}
	
	/**
	 * Method that updates this GameState, updates the contained World.
	 *
	 * @param gc The GameContainer object needed for updating
	 * @param sbg The StateBasedGame that contains this GameState
	 * @param i The time, in milliseconds, since this method was last called
	 */
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
		gameWorld.update(gc, i);
		if (gameWorld.shipHP() < 1)
			sbg.enterState(0); // go to menustate
	}
	
	/**
	 * Method that renders this GameState, renders the contained World.
	 *
	 * @param gc The GameContainer object needed for rendering
	 * @param sbg The StateBasedGame that contains this GameState
	 * @param g The Graphics object needed for rendering
	 */
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		gameWorld.render(gc, g);
	}
	
	/**
	 * Method that returns this GameState's unique identifier
	 *
	 * @return The unique ID of this GameState in an int
	 */
	@Override
	public int getID() {
		return 1;
	}
}