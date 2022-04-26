package geometries;
import primitives.*;
import scene.Scene;
public  abstract class  Geometry extends Intersectable
{
	protected Color emission= Color.BLACK; 
	public abstract Vector getNormal(Point p);
	
	/**
	 * a getter function that returns the emmission color
	 * @return Color
	 */
	public Color getEmission()
	{
		return emission;
	}
	
	/**
	 * gets a color and set's it in the Geometry emission.
	 * we are using the Builder design pattern,and that's why we returned the Geometry itself
	 * @param emission
	 * @return Geometry
	 */
	public Geometry setEmission(Color emission) 
	{
		this.emission = emission;
		return this;
	}
}
