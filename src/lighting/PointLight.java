package lighting;

import primitives.*;

/**
 * pointy light source, spreads light to all around. like a lamp.
 * 
 * @author adi
 */

public class PointLight extends Light implements LightSource 
{
	private Point position;		//the position of the point light
	private double Kc=1;				//mekadem kc
	private double Kl=0;				//mekadem kl
	private double Kq=0;				//mekadem kq


	/* ********* Constructors ***********/

	

	/**
	 * a new point light
	 * @param color the color of the light
	 * @param position the position of the light source
	 */
	public PointLight(Color color, Point position) 
	{
		super(color);
		this.position = new Point(position.getXyz());
	}

	/* ************* Getters & setters *******/
	/**
	 * get light intensity
	 * @param p the point
	 * @return light
	 */
	@Override
	public Color getIntensity(Point p) 
	{
		double distance2 = Util.alignZero(p.distanceSquared(position));
		double distance = Util.alignZero(p.distance(position));
		return getIntensity().scale(1/(Kc +Kl*distance + Kq*distance2));
	}

	/**
	 * get vector from light
	 * @param p the point
	 * @return vector
	 */
	@Override
	public Vector getL(Point p)
	{
		if (p.equals(position)) 
			return null;
		return p.subtract(position).normalize();
	}

	/**
	 * Get light position
	 * @return point
	 */
	public Point getPosition() 
	{
		return position;
	}

	/**
	 * set Kc mekadem
	 * @param Kc
	 * @return the PointLight itself to allow design pattern of builder- to concatenate calls to setters.
	 */
	public PointLight setKc(double Kc) 
	{
		this.Kc=Kc;
		return this;
	}

	/**
	 * set kl mekadem
	 * @param kl
	 * @return the PointLight itself to allow design pattern of builder- to concatenate calls to setters.
	 */
	public PointLight setKl(double kl) 
	{
		this.Kl=kl;
		return this;
	}

	/**
	 * set Kq mekadem
	 * @param Kq
	 * @return the PointLight itself to allow design pattern of builder- to concatenate calls to setters.
	 */
	public PointLight setKq(double Kq) 
	{
		this.Kq=Kq;
		return this;
	}

	@Override
	public double getDistance(Point point) 
	{
		return position.distance(point);
	}
}
