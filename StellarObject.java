/**
 * StellarObject.java
 *
 * A base class for a generic StellarObject.
 * Functionalities include
 *    - collision detection (see CollisionImage.java)
 *    - graphical representation (see GraphicalImage.java)
 *
 * Author: Wesley Gydé
 */

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public abstract class StellarObject{

	private Physics phys;

	//------------------
	//--| 'structors |--
	//------------------

	/**
	 * Constructor
	 *
	 * @param phys A Physics object used to maintain all of the physical properties of this. Ideally, this will be constructed and passed in directly from each StellarObject subclass via "super(myPhys);"
	 */
	public StellarObject(Physics phys){
		this.phys = phys;
	}

	/** Destroys this, removing it from any/all push-notification systems */
	public void destroy(){
		phys.destroy();
	}

	//---------------
	//--| Updates |--
	//---------------
	
	/** Performs framewise updates; should be propegated from the base Slick2D game object */
	public void update(GameContainer gc, int time_passed_ms){
		phys.update(gc, time_passed_ms);
	}

	/** Performs graphical updates; should be propegated from the base Slick2D game object */
	public void render(GameContainer gc, Graphics g) throws SlickException{
		phys.render(gc, g);
	}
	
	/** Returns phys, as it was passed to the constructor */
	public Physics getPhys(){return phys;}

}
