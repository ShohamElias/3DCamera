package geometries;

import java.util.List;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

public class Plane implements Geometry
{
	private Point p0;
	private Vector normal;
	public Plane(Point p, Vector v)
	{
		p0=p;
		normal=v.normalize();
	}
	
	public Plane(Point p,Point p2,Point p3)
	{
		if(p.equals(p3)||p.equals(p2)||p2.equals(p3))
			throw new IllegalArgumentException("same points");
		p0=p;
		Vector v=new Vector(p.subtract(p2).getXyz());
		Vector v2=new Vector(p.subtract(p3).getXyz());
		
		if(v.getXyz().equals(v2.getXyz()))
			throw new IllegalArgumentException("same line");
		
		normal=v.crossProduct(v2).normalize();
	}
	@Override
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

	@Override
	public List<Point> findIntersections(Ray ray)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
