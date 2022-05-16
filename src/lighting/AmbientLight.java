package lighting;
import primitives.Color;
import primitives.Double3;
/**
 * AmbientLight class represents the light of the environment
 * @author Adi
 *
 */
public class AmbientLight extends Light
{
	
	////////////// ctor /////////////////////
	
	public AmbientLight() {
		super(Color.BLACK);
	}
	/**
	 * sends to the constructor of 'light' -there it places the value of intensity
	 * @param ia
	 * @param ka
	 */
	public AmbientLight(Color ia, Double ka) {
		super(ia.scale(ka));
	}
	public AmbientLight(Color color, Double3 double3) {
		super(color.scale(double3));
		// TODO Auto-generated constructor stub
	}
	
 
}