import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 * Class to represent the player's ship, extends StellarObject. 
 * 
 */
public class PlayerShip extends StellarObject{
	private static final float THRUSTER_ACCELERATION = .05f; //thruster acceleration in px/frame^2
	private static final float ROTATION = .05f; //rotational displacement in rad/frame
	private CollisionLayer cl;
	private BulletManager bm;

	/**
	 * Constructor, takes a physics object and collisionlayer. Instantiates 
	 * the contained BulletManager. 
	 *
	 * @param phys The Physics object for this object
	 * @param cl The collisionlayer where this object is registered
	 */
	private PlayerShip(Physics phys, CollisionLayer cl) {
		super(phys);
		this.cl = cl;
		HP = 1;
		bm = new BulletManager(cl);
	}

	/**
	 * Creates a PlayerShip object at a given position in a given CollisionLayer.
	 *
	 * @param x The x-cooridnate of the player's ship.
	 * @param y the y-cooridnate of the player's ship.
	 * @param cl The collisionlayer where this object is going to be registered.
	 * @return The generated PlayerShip object.
	 */
	public static PlayerShip makeShip(float x, float y, CollisionLayer cl) {
		//make centroid
		Vector2f centroid = new Vector2f(x, y);

		//make cimg
		_PlayerCollider pc = new _PlayerCollider();
		Polygon pol = new Polygon(new float[]{
			x   , y+7f,
			x+5f, y-5f,
			x-5f, y-5f
			});
		CollisionImage cimg = new CollisionImage(pol, pc);

		GraphicalImage gimg = new GraphicalImage(pol);

		//make the PlayerShip
		PlayerShip ps = new PlayerShip(new _PlayerShipPhysics(centroid, cimg, gimg), cl);

		//close circular references
		pc.ps = ps;

		return ps;
	}


	/** 
	 * Updates this object and its contained Physics object. Takes player's 
	 * input to decide to make calls on the contained Physics object.
	 * 
	 * @param gc The GameContainer needed for updating.
	 * @param time_passed_ms The amount of time, in milliseconds, since the last update call
	 */
	@Override
	public void update(GameContainer gc, int time_passed_ms) {

		if (HP <= 0){
			destroy();
		}
		
		//keyboard input
		Input in = gc.getInput();

		if (in.isKeyDown(Input.KEY_LEFT)) {
			getPhys().rotate(-ROTATION);
		}

		if (in.isKeyDown(Input.KEY_RIGHT)) {
			getPhys().rotate(ROTATION);
		}

		if (in.isKeyDown(Input.KEY_UP)) {
			getPhys().accelerateAligned(0f, THRUSTER_ACCELERATION);
		}

		if (in.isKeyDown(Input.KEY_DOWN)){
			getPhys().setFriction(_PlayerShipPhysics.FRICTION_BRAKING);
		} else {
			getPhys().setFriction(_PlayerShipPhysics.FRICTION_DEFAULT);
		}
		
		if (in.isKeyPressed(Input.KEY_SPACE)) {
			fire();
		}
		
		bm.update(gc, time_passed_ms);

		super.update(gc, time_passed_ms);
	}
	
	/**
	 * Renders this object.
	 *
	 * @param gc The GameContainer object needed for updating.
	 * @param g The Graphics object needed for updating.
	 * @throws SlickException
	 */
	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		super.render(gc, g);
		bm.render(gc, g);
	}
	
	/**
	 * Creates a Bullet object at a position in front of this object with a 
	 * speed relative to this object's speed.
	 *
	 */
	private void fire() {
		float x     = getPhys().getPosition().x;
		float y     = getPhys().getPosition().y;
		float angle = getPhys().getRotation();
		x += (float) 10 * Math.cos(angle);
		y += (float) 10 * Math.sin(angle);
		float speed = getPhys().getSpeed();
		
		bm.requestBullet(x, y, angle, speed + 10f);
	}

	/**
	 * Private class used to specify the physical properties of every PlayerShip.
	 * 
	 */
	private static class _PlayerShipPhysics extends Physics{
		private static final float FRICTION_DEFAULT = .995f;
		private static final float FRICTION_BRAKING = .950f;
		
		/**
		 * Constructor taking a vector, and the Collision and Graphical image 
		 * objects needed to construct the Physics object of a PlayerShip.
		 *
		 * @param centroid A Vector2f located at the center of the object
		 * @param cimg The CollisionImage for the object
		 * @param gimg The GraphicalImage for the object
		 */
		public _PlayerShipPhysics(Vector2f centroid, CollisionImage cimg, GraphicalImage gimg ) {
			super(centroid, (float) Math.PI / 2, cimg, gimg);
			setFriction(FRICTION_DEFAULT);
		}
	}
	
	/**
	 * Private class used to specify the collision properties of every PlayerShip.
	 * 
	 */
	private static class _PlayerCollider extends Collider{
		public PlayerShip ps;

		/**
		 * Called when colliding with something else in the same collisionlayer.
		 * 
		 * @param c The collider object of the other thing being collided with.
		 */
		@Override
		public void collide(Collider c) {
			c.inflictDamage(1);
		}
		
		/**
		 * Used by other object to inflict damage on this object.
		 *
		 * @param dam The amount of damage inflicted on this by the other object.
		 */
		@Override
		public void inflictDamage(int dam){
			ps.HP -= dam;
		}
	}

}
