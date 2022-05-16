package lighting;
import primitives.*;

/**
 * interface LightSource for all the lights with source (not ambient)
 * 
 * @author adi
 */
public interface LightSource 
{

    /**
     * get Color Intensity of a certain point
     *
     * @return color Intensity
     */
	public Color getIntensity(Point p);
	
	/**
     * get the direction of the light to a point
     *
     * @param p the point
     * @return normalized vector
     */
	public Vector getL(Point p);
	
	/**
	 * A function that return the distance between 2 points
	 * 
	 * @param point Point3D value
	 * @return double value for the distance
	 * */
	double getDistance(Point point);

}
