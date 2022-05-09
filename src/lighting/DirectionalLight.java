package lighting;

import primitives.*;

/**
 * class DirectionalLight that extends from class light
 * and implement the interface LightSource
 * @author ADI
 */
public class DirectionalLight extends Light implements LightSource
{

	private Vector direction;
	
	/* ********* Constructors ***********/

	/**
	 * a new Directional Light
	 *
	 * @param color color
	 * @param direction light direction
	 */
	public DirectionalLight(Color color, Vector direction) 
	{
		super(color);
		this.direction = direction.normalize();
	}

	/* ************* Getters/Setters *******/
	/**
	 * get light intensity
	 * @param p the point
	 * @return light intensity
	 */
	@Override
	public Color getIntensity(Point p) {
		return super.getIntensity();
	}

	/**
	 * get vector of the direction of the light from 'light'
	 * @param p the point
	 * @return vector
	 */
	@Override
	public Vector getL(Point p) 
	{
		return direction;
	}


}