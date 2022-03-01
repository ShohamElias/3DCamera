package geometries;

import primitives.Point;
import primitives.Vector;

public class Plane implements Geometry
{
	private Point p0;
	private Vector normal;
	public Plane(Point p, Vector v)
	{
		p0=p;
		normal=v;
	}
	
	public Plane(Point p,Point p2,Point p3)
	{
		p0=p;
		normal=null;
	}
	
	public Vector getNormal(Point p)
	{
		return normal;
	}

	public Point getP0() {
		return p0;
	}
	
	public Vector getNormal() {
		return normal;
	}

}
