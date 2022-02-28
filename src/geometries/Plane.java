package geometries;

import primitives.Point;
import primitives.Vector;

public class Plane implements Geometry
{
	private Point p0;
	private Vector normal;
	public Plane(Point p, Vector v)
	{
		setP0(p);
		normal=v;
	}
	
	public Plane(Point p,Point p2,Point p3)
	{
		setP0(p);
		normal=null;
	}
	
	public Vector getNormal(Point p)
	{
		return normal;
	}

	public Point getP0() {
		return p0;
	}

	public void setP0(Point p0) {
		this.p0 = p0;
	}
}
