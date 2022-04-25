package lighting;
import primitives.Color;
import primitives.Double3;
/**
 * AmbientLight class represents the light of the environment
 * @author Adi
 *
 */
public class AmbientLight
{
	Color intensity;  
	
	////////////// ctor /////////////////////
	public AmbientLight() //default
	{
		intensity= Color.BLACK;
	}
	
	public AmbientLight(Color ia, Double3 ka)
	{
		intensity=ia.scale(ka);
	}
	
	public Color getIntensity() 
	{
		return intensity;
	}
 
}