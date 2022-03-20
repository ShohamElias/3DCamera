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
	
	public Sphere(double d, Point point) 
	{
		// TODO Auto-generated constructor stub
		radius =d;
		center =point;
		
	}

	@Override
	public Vector getNormal(Point p)
	{
		if(center.distance(p)!=radius)
			throw new IllegalArgumentException("out of the sphere");
		Vector vector=new Vector(center.subtract(p).getXyz());
		return vector.normalize();
	}

	@Override
	public List<Point> findIntersections(Ray ray) {
		// TODO Auto-generated method stub
		return null;
	}
}
