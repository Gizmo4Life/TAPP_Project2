import java.util.*;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics.*;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.*;
import org.newdawn.slick.fills.*;
import org.newdawn.slick.*;

/**
 * Class to manage the Bullets of the PlayerShip
 *
 */
public class BulletManager {
	private Collection<Bullet> bullets;
	private CollisionLayer cl;
	
	protected final int MAX_BULLETS = 4; // max amount of bullets at a time
	
	/**
	 * Constructor, takes a CollisionLayer object to register bullets with
	 *
	 * @param cl The collisionlayer to (de)register bullets with
	 */
	public BulletManager(CollisionLayer cl) {
		this.cl = cl;
		this.bullets = new ArrayList<>();
	}
	
	/**
	 * Method to update the contained Bullet objects
	 *
	 * @param gc The GameContainer needed for updating
	 * @param delta The amount of time, in milliseconds, since this method was last called
	 */
	public void update(GameContainer gc, int delta) {
		List<Bullet> toDestroy = new LinkedList<>();
		for (Bullet b : bullets) {
			b.update(gc, delta);
			if (b.getHP() < 1) {
				toDestroy.add(b);
			}
		}
		for (Bullet b : toDestroy) {
			bullets.remove(b);
			b.destroy();
		}
	}
	
	/**
	 * Method to render the contained Bullet objects
	 *
	 * @param gc The GameContainer object needed for rendering
	 * @param g The Graphics object needed for rendering
	 * @throws SlickException
	 */
	public void render(GameContainer gc, Graphics g) throws SlickException {
		for (Bullet b : bullets) {
			b.render(gc, g);
		}
	}
	
	/**
	 * Method to request a new bullet, which is (if created) launched at the 
	 * given angle and position at a speed greater than the given speed.
	 *
	 * @param x The x-position of the launching object
	 * @param y The y-position of the launching object
	 * @param angle The angle at which bullets should travel
	 * @param speed The speed at which bullets need to travel at minimum
	 */
	public void requestBullet(float x, float y, float angle, float speed) {
		if (bullets.size() <= MAX_BULLETS) {
			Bullet result = Bullet.makeBullet(x, y, angle, speed);
			result.getPhys().getCImg().addTo(cl);
			bullets.add(result);
		}
	}
}