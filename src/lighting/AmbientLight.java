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
	public AmbientLight(Color ia, Double3 ka) {
		super(ia.scale(ka));
	}
	
 
}