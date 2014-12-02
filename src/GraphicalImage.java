import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.ShapeFill;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.lwjgl.opengl.GL11;

/**
 * Class to represent the appearance of a StellarObject
 *
 */
public class GraphicalImage {
	private Shape shapeView;
	private ShapeFill fillup;
	
	/**
	 * Constructor, takes a Shape to use when drawing the object with a default blue fill
	 *
	 * @param shape The Shape object that the containing StellarObject should look like
	 */
	public GraphicalImage(Shape shape) {
		this.shapeView = shape;
		this.fillup = new GradientFill(0.0f, 0.0f, Color.blue, 1.0f, 1.0f, Color.blue); // default patriotic shapefill
	}
	
	/**
	 * Constructor, takes a Shape and ShapeFill to use when drawing the object
	 *
	 * @param shape The Shape object that the containing StellarObject should look like
	 * @param fill The ShapeFill object that the containing StellarObject should be colored as
	 */
	public GraphicalImage(Shape shape, ShapeFill fill) {
		this.shapeView = shape;
		this.fillup = fill;
	}
	
	/**
	 * Draws the containing StellarObject
	 * 
	 * @param gc The GameContainer object needed for rendering
	 * @param g The Graphics object needed for rendering
	 */
	public void render(GameContainer gc, Graphics g) {
		g.fill(shapeView, fillup);
	}
	
	/**
	 * Updates by translating the contained shape
	 *
	 * @param t The Transform object to use when creating a new Shape for drawing the StellarObject
	 */
	public void transform(Transform t) {
		shapeView = shapeView.transform(t);
	}
	
	/**
	 * Returns the x-coordinate of the center of the contained Shape
	 *
	 * @return The x-coordinate of the center of the contained Shape as a float
	 */
	public float getMidX() {
		return shapeView.getCenterX();
	}
	
	/**
	 * Returns the y-coordinate of the center of the contained Shape
	 *
	 * @return The y-coordinate of the center of the contained Shape as a float
	 */
	public float getMidY() {
		return shapeView.getCenterY();
	}
}