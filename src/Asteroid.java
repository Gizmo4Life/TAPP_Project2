import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.*;
import org.newdawn.slick.*;
import org.newdawn.slick.fills.*;

/**
 * Class to represent the Asteroids that players must dodge in-game, extends
 * StellarObject.
 *
 */
public class Asteroid extends StellarObject {
	/**
	 * Constructor taking a Physics object
	 *
	 * @param phys The Physics object which the object will contain
	 */
	public Asteroid(Physics phys) {
		super(phys);
	}
	
	/**
	 * Method to generate Asteroids at a given point.
	 *
	 * @param x The x-coordinate of the generated Asteroid
	 * @param y The y-coordinate of the generated Asteroid
	 * @return The generated Asteroid object
	 */
	public static Asteroid makeAsteroid(float x, float y) {
		//make centroid
		Vector2f centroid = new Vector2f(x, y);

		//make cimg
		_AsteroidCollider ac = new _AsteroidCollider();
		Circle cir = new Circle(x, y, 20f);
		
		CollisionImage cimg = new CollisionImage(cir, ac);
		GraphicalImage gimg = new GraphicalImage(cir, new GradientFill(0.0f, 0.0f, Color.darkGray, 1.0f, 1.0f, Color.darkGray));

		//make the Asteroid
		Asteroid as = new Asteroid(new _AsteroidPhysics(centroid, cimg, gimg));

		//close circular references
		ac.ast = as;

		return as;
	}
	
	/**
	 * Class for the Physics objects that Asteroids will contain
	 *
	 */
	private static class _AsteroidPhysics extends Physics {
		/** 
		 * Constructor taking a Vector2f, CollisionImage and GraphicalImage
		 *
		 * @param centroid A vector for the center of the new Asteroid
		 * @param cimg The CollisionImage for this object to contain
		 * @param gimg The GraphicalImage for this object to contain
		 */
		public _AsteroidPhysics(
		Vector2f centroid,
		CollisionImage cimg,
		GraphicalImage gimg
		) {
			super(centroid, 0f, cimg, gimg);
		}
	}
	
	/**
	 * Class for the Collider objects that Asteroids will contain
	 *
	 */
	private static class _AsteroidCollider extends Collider{
		public Asteroid ast;
		
		/**
		 * Method that's called when this collides with another object, inflicts 
		 * damage on the other object
		 *
		 * @param c The collider of the other object being collided with
		 */
		@Override
		public void collide(Collider c) { 
			c.inflictDamage(1); // inflicts damage on object it collides with
		}
		
		/**
		 * Method that's called by other objects to inflict damage on this 
		 * Asteroid object
		 *
		 * @param dam The damage inflicted on this Asteroid
		 */
		@Override
		public void inflictDamage(int dam) {
			ast.HP -= dam;
		}
	}
}