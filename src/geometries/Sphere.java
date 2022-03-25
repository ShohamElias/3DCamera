package geometries;

import java.util.List;

import org.junit.internal.runners.statements.Fail;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

public class Sphere implements Geometry
{
	private Point center;
	private double radius;
	/**
	 * Constructor that receives point to the center and double to the radius
	 * 
	 * @param point Point
	 * @param d double
	 * */
	public Sphere(double d, Point point) 
	{
		super();
		radius =d;
		center =point;
		
	}

	@Override
	public Vector getNormal(Point p)
	{
		if(center.distance(p)!=radius)
			throw new IllegalArgumentException("out of the sphere");
		return center.subtract(p).normalize();
	}

	@Override
	public List<Point> findIntersections(Ray ray) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String toString() 
	{
		return "Sphere: center=" + center + ", radius=" + radius;
	}
}
