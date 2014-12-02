import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

/**
 * Class to contain the Main method of this Java program. 
 *
 */
public class Launch {

	/**
	 * Main function to contain the game
	 *
	 * @param args The array of String arguments passed by the calling program
	 */
	public static void main(String[] args) {
		try {
			AppGameContainer agc = new AppGameContainer(
				new StellarGame("Stellar Fugitive")
				);
			agc.setDisplayMode(640, 480, false);
			agc.setTargetFrameRate(60);
			agc.setShowFPS(false);
			agc.start();
		} catch (SlickException ex) {
			ex.printStackTrace();
		}
	}
}