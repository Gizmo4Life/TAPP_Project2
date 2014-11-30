import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 * Class to abstract the StellarObjects of the game.
 *
 */
public abstract class StellarObject{
	private Physics phys;
	protected int HP;

	/**
	 * Constructor taking a Physics object. 
	 *
	 * @param phys A Physics object for the constructed StellarObject.
	 */
	public StellarObject(Physics phys){
		this.phys = phys;
		HP = 1;
	}
	
	/** 
	 * Updates the Physics object contained by the StellarObject.
	 *
	 * @param gc The GameContainer needed for updating.
	 * @param time_passed_ms The time, in milliseconds, since the last update call.
	 */
	public void update(GameContainer gc, int time_passed_ms) {
		phys.update(gc, time_passed_ms);
	}

	/** 
	 * Performs graphical updates; should be propegated from the base Slick2D game object 
	 * 
	 * @param gc The GameContainer needed for rendering.
	 * @param g The Graphics object needed for rendering.
	 * @throws SlickException
	 */
	public void render(GameContainer gc, Graphics g) throws SlickException{
		phys.render(gc, g);
	}

	/**
	 * Destroys the object.
	 * 
	 */
	public void destroy(){
		phys.destroy();
	}
	
	/** 
	 * Returns the Physics object for the StellarObject.
	 * 
	 */
	public Physics getPhys() { 
		return phys; 
	}
	
	/**
	 * Returns an int representing the HP of the StellarObject
	 *
	 * @return An int for the HP of the ship.
	 */
	public int getHP() { 
		return HP; 
	}
}
