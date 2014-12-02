import java.util.*;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics.*;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.*;
import org.newdawn.slick.fills.*;
import org.newdawn.slick.*;

/**
 * Class to manage the Asteroid objects of the containing World
 *
 */
public class DebrisManager {
	private Collection<Asteroid> asteroids;
	private CollisionLayer cl;
	private int timeSince;
	private Random rand;
	
	private static final int MAX_ROIDS      = 30;   // max amount of asteroids to track
	private static final int INTERVAL_MS    = 250;  // ~0.25 seconds
	private static final float MAX_DIST_SQ  = 800f * 800f; // max distance before asteroids get wrecked
	
	/**
	 * Constructor, takes a CollisionLayer to register Asteroids with
	 *
	 * @param cl The collisionlayer to register Asteroids with
	 */
	public DebrisManager(CollisionLayer cl) {
		this.cl = cl;
		this.asteroids = new ArrayList<>(MAX_ROIDS);
		this.timeSince = 0;
		this.rand = new Random();
	}
	
	/**
	 * The method to update the internal Asteroids and (de)spawn Asteroids. If 
	 * contained Asteroids are too far away from the player's ship, they are 
	 * despawned. If there are not enough Asteroids currently existing, more are 
	 * generated according to the position/angle of the player's ship.
	 *
	 * @param gc The GameContainer object needed for updating
	 * @param delta The time, in milliseconds, since this method was last called
	 * @param pos The Vector2f representing the location of the main player's ship
	 * @param angle The angle of the main player's ship as a float
	 */
	public void update(GameContainer gc, int delta, Vector2f pos, float angle) throws SlickException {
		timeSince += delta;
		if (timeSince > INTERVAL_MS) { // checking every INTERVAL_MS milliseconds...
			timeSince = 0;
			if (asteroids.size() < MAX_ROIDS) {
				spawnInRange(pos, angle);
			}
		}
		List<Asteroid> toDestroy = new LinkedList<>();
		for (Asteroid a : asteroids) {
			Vector2f apos = a.getPhys().getPosition();
			if (apos.distanceSquared(pos) > MAX_DIST_SQ || a.getHP() < 1) {
				toDestroy.add(a);
			}
			a.update(gc, delta);
		}
		for (Asteroid a : toDestroy){
			asteroids.remove(a);
			a.destroy();
		}
	}
	
	/**
	 * Method to render the contained Asteroids
	 *
	 * @param gc The GameContainer object used to render
	 * @param g The Graphics object used to render
	 */
	public void render(GameContainer gc, Graphics g) throws SlickException {
		for (Asteroid a : asteroids) {
			a.render(gc, g);
		}
	}
	
	/**
	 * Method to return the amount of spawned Asteroids
	 *
	 * @return The amount of asteroids, as an int
	 */
	public int count() { 
		return asteroids.size(); 
	}
	
	/** 
	 * Returns theta as an angle in the range [0,2p] 
	 *
	 * @param theta The angle to restrict to our range
	 * @return The restricted angle, as a float
	 */
	private float restrictAngle(float theta){
		while (theta > float2pi) {
			theta -= float2pi;
		}
		while (theta < 0) {
			theta += float2pi;
		}
		return theta;
	}
	private static final float float2pi = (float)Math.PI * 2f;

	/**
	 * Method used to spawn new Asteroids with a given position and angle. 
	 * Asteroids are spawned according to the passed position and angle such that 
	 * they should appear in front of the player. Asteroids are given a random 
	 * velocity on spawning.
	 *
	 * @param pos The position to spawn asteroids in front of
	 * @param angle The angle that the ship is flying at
	 */
	private void spawnInRange(Vector2f pos, float angle) {
		float rand_coef = rand.nextFloat();
		float distance = 400f + 350f * rand_coef;
		rand_coef = rand.nextFloat();
		float theta = angle + (float) Math.PI * ((rand_coef - 0.5f) / 1.5f);
		theta = restrictAngle(theta);
		float x = pos.x + distance * (float) Math.cos(theta);
		float y = pos.y + distance * (float) Math.sin(theta);
		Asteroid result = Asteroid.makeAsteroid(x, y);
		float rand_angle = float2pi * rand.nextFloat();
		float rand_speed = 2f * rand.nextFloat();
		result.getPhys().accelerate((float) Math.cos(rand_angle) * rand_speed, (float) Math.sin(rand_angle) * rand_speed);
		result.getPhys().getCImg().addTo(cl);
		asteroids.add(result);
	}
}