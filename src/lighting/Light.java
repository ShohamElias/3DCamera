package lighting;
import primitives.*;
/**
 * Abstract class- light (the basic for light sources)
 * this class is not a public class
 * @author adi
 *
 */
abstract class Light 
{
	//עוצמה בקרניים עצמם ששולחים
	private Color intensity;//the intensity of the light
	
	/* ************* constructors *******/
	/**
	 * A new Light
	 * @param color the color
	 */
	protected Light(Color color)
	{
		intensity = color;
	}

	/* ************* Getters *******/
	/**
	 * get Color Intensity
	 * @return color Intensity
	 */
	public Color getIntensity() 
	{
		return intensity;
	}

}
