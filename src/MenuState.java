import org.newdawn.slick.*;
import org.newdawn.slick.geom.*;
import org.newdawn.slick.state.*;

/**
 * The class to represent the Main Menu's GameState
 *
 */
public class MenuState extends BasicGameState {
	private Rectangle r1, r2;
	private boolean startGame, quit;
	
	/**
	 * Constructor, instantiates the Rectangles used for representing the buttons 
	 * of this menu
	 *
	 */
	public MenuState() {
		r1 = new Rectangle(220, 250, 200, 70);
		r2 = new Rectangle(220, 350, 200, 70);
	}
	
	/**
	 * Method that's called when this state is entered, resets boolean vars and 
	 * releases the mouse for the user.
	 *
	 * @param gc The GameContainer object needed for entry
	 * @param sbg The StateBasedGame that contains this GameState
	 */
	@Override
	public void enter(GameContainer gc, StateBasedGame sbg) {
		startGame = false;
		quit = false;
		gc.setMouseGrabbed(false);
	}
	
	/**
	 * Method that's called when this state is exited, grabs mouse from the user.
	 *
	 * @param The GameContainer object needed for exiting.
	 * @param The StateBasedGame that contains this GameState
	 */
	@Override
	public void leave(GameContainer gc, StateBasedGame sbg) {
		gc.setMouseGrabbed(true);
	}
	
	/**
	 * Method to initiate this GameState
	 *
	 * @param gc The GameContainer object needed for initialization
	 * @param sbg The StateBasedGame that contains this GameState
	 */
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		
	}
	
	/**
	 * Method to update this GameState, enters the game or exits the program if 
	 * the appropriate flags are set.
	 *
	 * @param gc The GameContainer object needed for updating
	 * @param sbg The StateBasedGame that contains this GameState
	 * @param i The time, in milliseconds, since the last update call
	 */
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
		if (startGame) {
			sbg.enterState(1); // enter game state
		} else if (quit) {
			gc.exit();
		}
	}
	
	/**
	 * Method to render the contents of this GameState
	 *
	 * @param gc The GameContainer object needed for rendering
	 * @param sbg The StateBasedGame that contains this GameState
	 * @param g The Graphics object needed to draw the contents
	 */
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		// draw title - can replace with image later
		g.setColor(Color.white);
		g.drawString("WELCOME TO STELLAR FUGITIVE!", 200, 100);
		g.drawString("PRESS THE START BUTTON TO PLAY!", 190, 120);
		
		// draw buttons
		g.setColor(Color.blue);
		g.draw(r1);
		g.draw(r2);
		
		// draw labels of buttons
		g.setColor(Color.white);
		g.drawString("START", 295, 280);
		g.drawString("QUIT", 300, 380);
	}
	
	/**
	 * Method that's called when the mouse clicks on-screen
	 *
	 * @param mouseNum An integer representing the mouse button used to click
	 * @param x The x-position of the click
	 * @param y The y-position of the click
	 * @param clickCount The amount of clicks at the position
	 */
	@Override
	public void mouseClicked(int mouseNum, int x, int y, int clickCount) {
		if (mouseNum == Input.MOUSE_LEFT_BUTTON) {
			if (r1.contains(x, y)) {
				// mark to start game on next update
				startGame = true;
			} else if (r2.contains(x, y)) {
				// mark to leave program on next update
				quit = true;
			}
		}
	}
	
	/**
	 * Method to get the ID number of this GameState
	 *
	 * @return The int that uniquely identifies this object
	 */
	@Override
	public int getID() {
		return 0;
	}
}