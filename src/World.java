import org.newdawn.slick.*;
import org.newdawn.slick.geom.*;
import org.newdawn.slick.fills.*;
import java.util.*;

/**
 * Class to encapsulate the environment which all objects interact in. 
 * 
 */
public class World {
	private PlayerShip mainShip;
	private CollisionLayer cl;
	private DebrisManager debris;
	private AIManager aiships;
	private static boolean DEBUG_DISP = false;
	
	/**
	 * Parameter-less constructor, instantiates the managers, collisionlayer, 
	 * and the player's ship.
	 * 
	 */
	public World() {
		this.cl       = new CollisionLayer();
		this.debris   = new DebrisManager(cl);
		this.mainShip = PlayerShip.makeShip(0f, 0f, cl);
		this.aiships  = new AIManager(cl, mainShip);		
		mainShip.getPhys().getCImg().addTo(cl);	
	}
	
	/**
	 * Updates the World.
	 * <p>
	 * The update methods of the managers and the player's ship are called. 
	 * The collisionlayer notifies its objects if there's a collision. It also 
	 * checks if the keys to debug or exit the game are pressed.
	 * 
	 * @param gc The GameContainer object needed for updating
	 * @param i The amount of time (milliseconds) passed since the last update 
	 * @throws SlickException
	 */
	public void update(GameContainer gc, int i) throws SlickException {
		mainShip.update(gc, i);
		debris.update(gc, i, mainShip.getPhys().getPosition(), mainShip.getPhys().getVelAngle());
		aiships.update(gc, i, mainShip.getPhys().getPosition());
		cl.notifyCollisions();
		
		if (gc.getInput().isKeyPressed(Input.KEY_D)) {
			DEBUG_DISP = !DEBUG_DISP;
		}
		
		if (gc.getInput().isKeyPressed(Input.KEY_ESCAPE)) {
			gc.exit();
		}
	}
		
	/**
	 * Renders the World.
	 * <p>
	 * The render methods of the managers and the player's ship are called. 
	 * All objects are drawn relative to where the player's ship is. But the 
	 * basic instructions along with the player's HP are also drawn. If the 
	 * debugging mode is active, that's also drawn onto the screen.
	 * 
	 * @param gc The GameContainer object needed to render
	 * @param g The Graphics object needed to render
	 * @throws SlickException
	 */
	public void render(GameContainer gc, Graphics g) throws SlickException { 
		float midx = mainShip.getPhys().getGImg().getMidX();
		float midy = mainShip.getPhys().getGImg().getMidY();
		
		g.translate(-midx + 320f, -midy + 240f); // translate to the ship's position
		
		debris.render(gc, g);
		aiships.render(gc, g);
		
		mainShip.render(gc, g);
		
		g.translate(midx - 320f, midy - 240f); // undo the translation
		
		g.drawString("Use the arrow keys to move.", 10f, 10f);
		g.drawString("Use the space key to fire!", 10f, 30f);
		g.drawString("HP: " + mainShip.getHP(), 10, 460);
		
		if (DEBUG_DISP) { // display debugging information
			g.drawString("X: " + midx + ", Y: " + midy, 10, 400);
			g.drawString("Asteroids: " + debris.count(), 10, 420);
			g.drawString("AI Ships:  " + aiships.count(), 10, 440);
			g.drawString("FPS: " + gc.getFPS(), 570, 460);
		}
	}
	
	/**
	 * Returns the HP of the player's ship.
	 * 
	 * @return An int representing the health of the ship.
	 */
	public int shipHP() { 
		return mainShip.getHP(); 
	}
}