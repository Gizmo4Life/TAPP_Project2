import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.*;
import org.newdawn.slick.*;
import org.newdawn.slick.fills.*;

/**
 * Class to represent a Bullet that can be fired at other objects. Extends StellarObject.
 *
 */
public class Bullet extends StellarObject {
	private int life_ms; // gonna default 3s lifetime

	/**
	 * Private constructor, takes a Physics object
	 *
	 * @param phys The Physics object to be contained by the Bullet
	 */
	private Bullet(Physics phys) {
		this(phys, 3000);
	}
	
	/**
	 * Private constructor, takes a Physics object and time-to-live
	 *
	 * @param phys The Physics object to be contained by the Bullet
	 * @param life_ms The time, in milliseconds, that this bullet should exist for
	 */
	private Bullet(Physics phys, int life_ms) {
		super(phys);
		HP = 1;
		this.life_ms = life_ms;
	}
	
	/**
	 * Method to update this object
	 *
	 * @param gc The GameContainer needed to update
	 * @param delta_ms The time, in milliseconds, since this method was last called
	 */
	@Override
	public void update(GameContainer gc, int delta_ms) {
		life_ms -= delta_ms;
		if (life_ms <= 0)
			HP -= 1;
		super.update(gc, delta_ms);
	}
	
	/**
	 * Method to generate bullets at a given position, angle, and speed.
	 *
	 * @param x The x-position to generate the bullet at
	 * @param y The y-position to generate the bullet at
	 * @param angle The angle at which the bullet should travel
	 * @param speed The speed at which the bullet should travel
	 * @return A new Bullet object
	 */
	public static Bullet makeBullet(float x, float y, float angle, float speed) {
		Vector2f centroid = new Vector2f(x, y);
		
		_BulletCollider bc = new _BulletCollider();
		Circle cir = new Circle(x, y, 5f);
		
		CollisionImage cimg = new CollisionImage(cir, bc);
		GraphicalImage gimg = new GraphicalImage(cir, new GradientFill(0.0f, 0.0f, Color.green, 1.0f, 1.0f, Color.green));
		
		Bullet b = new Bullet(new _BulletPhysics(centroid, cimg, gimg));
		float dx = (float) Math.cos(angle);
		float dy = (float) Math.sin(angle);
		b.getPhys().accelerate(speed * dx, speed * dy);
		
		bc.bst = b;
		
		return b;
	}
	
	/**
	 * Class for the Physics of each Bullet, extends Physics
	 *
	 */
	private static class _BulletPhysics extends Physics {
		/**
		 * Constructor, takes a Vector2f position, CollisionImage, and GraphicalImage
		 * to create a Physics object.
		 *
		 * @param centroid The vector for the center of the mass of the Bullet
		 * @param cimg The CollisionImage for this Physics object to contain
		 * @param gimg The GraphicalImage for this Physics object to contain
		 */
		public _BulletPhysics(
		Vector2f centroid,
		CollisionImage cimg,
		GraphicalImage gimg
		) {
			super(centroid, 0f, cimg, gimg);
		}
	}
	
	/**
	 * Class for the Collider of each Bullet, extends Collider
	 *
	 */
	private static class _BulletCollider extends Collider {
		public Bullet bst;
		
		/**
		 * Method to be executed when colliding with another object.
		 *
		 * @param c The collider of the other object being collided with
		 */
		@Override
		public void collide(Collider c) { 
			c.inflictDamage(1); // inflicts damage on object it collides with
		}
		
		/**
		 * Method to be called when damage is inflicted on this object.
		 *
		 * @param dam The amount of damage inflicted on this object upon collision
		 */
		@Override
		public void inflictDamage(int dam) {
			bst.HP -= dam;
		}
	}
}