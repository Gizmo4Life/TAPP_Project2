/**
 * PlayerShip.java
 *
 * A ship, controlled by the player (via the arrow keys)
 *
 * Author: Wesley Gydé
 */

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class PlayerShip extends StellarObject{
	
	private static final float THRUSTER_ACCELERATION = .05f; //thruster acceleration in px/frame^2
	private static final float ROTATION = .05f; //rotational displacement in rad/frame

	//------------------
	//--| 'structors |--
	//------------------

	private PlayerShip(Physics phys){
		super(phys);
		HP = 1;
	}

	public static PlayerShip makeShip(float x, float y){
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
		PlayerShip ps = new PlayerShip(new _PlayerShipPhysics(centroid, cimg, gimg));

		//close circular references
		pc.ps = ps;

		return ps;
	}

	//---------------
	//--| Updates |--
	//---------------

	/** Performs framewise updates; should be propegated from the base Slick2D game object */
	@Override
	public void update(GameContainer gc, int time_passed_ms){

		if (HP <= 0){
			destroy();
		}
		
		//keyboard input
		Input in = gc.getInput();

		if (in.isKeyDown(Input.KEY_LEFT)){
			getPhys().rotate(-ROTATION);
		}

		if (in.isKeyDown(Input.KEY_RIGHT)){
			getPhys().rotate(ROTATION);
		}

		if (in.isKeyDown(Input.KEY_UP)){
			getPhys().accelerateAligned(0f, THRUSTER_ACCELERATION);
		}

		if (in.isKeyDown(Input.KEY_DOWN)){
			getPhys().setFriction(_PlayerShipPhysics.FRICTION_BRAKING);
		}else{
			getPhys().setFriction(_PlayerShipPhysics.FRICTION_DEFAULT);
		}

		super.update(gc, time_passed_ms);
	}

	//---------------
	//--| Physics |--
	//---------------

	private static class _PlayerShipPhysics extends Physics{
		private static final float FRICTION_DEFAULT = .995f;
		private static final float FRICTION_BRAKING = .950f;
		
		public _PlayerShipPhysics(Vector2f centroid, CollisionImage cimg, GraphicalImage gimg ) {
			super(centroid, (float) Math.PI / 2, cimg, gimg);

			setFriction(FRICTION_DEFAULT);
		}
	}
	
	private static class _PlayerCollider extends Collider{
		
		public PlayerShip ps;

		@Override
		public void collide(Collider c) {
			c.inflictDamage(1);
		}

		@Override
		public void inflictDamage(int dam){
			ps.HP -= dam;
		}

	}

}
