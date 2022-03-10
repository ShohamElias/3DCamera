package geometries;

import primitives.Point;
import primitives.Vector;

public class Sphere implements Geometry
{
	private Point center;
	private double radius;
	
	@Override
	public Vector getNormal(Point p)
	{
		if(center.distance(p)!=radius)
			throw new IllegalArgumentException("out of the sphere");
		Vector vector=new Vector(center.subtract(p).getXyz());
		return vector.normalize();
	}
}
