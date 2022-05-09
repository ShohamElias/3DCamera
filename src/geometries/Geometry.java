package geometries;
import primitives.*;
import scene.Scene;
public  abstract class  Geometry extends Intersectable
{
	protected Color emission= Color.BLACK; 
	public abstract Vector getNormal(Point p);
	private Material material=new Material();//the material of the geometry- with special features.

	/**
	 * @return the material
	 */
	public Material getMaterial()
	{
		return material;
	}
	
	/**
	 * a getter function that returns the emmission color
	 * @return Color
	 */
	public Color getEmission()
	{
		return emission;
	}
	
	/**
	 * set material and return the object itself
	 * @param emission
	 */
	public Geometry setMaterial(Material material)
	{
		this.material = material;
		return this;

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
