package geometries;

import java.util.List;

import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

public class Plane extends Geometry
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
	protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
		try {
			Vector vec=p0.subtract(ray.getP0());//creating a new vector according to the point q0 and the starting point of the ray (P0)

			double t=normal.dotProduct(vec);//dot product between the vector we created and the normal of the plane

			if(Util.isZero(normal.dotProduct(ray.getDir()))) //if the dot product equals 0 it means that the ray is parallel ,makbil, to the plane
				return null;
			t=t/(normal.dotProduct(ray.getDir()));

			if(t==0) //if the distance between the begining point of the ray and the plane so don't count it as an intersection
				return null;//no intersections
			else if(t > 0) // the ray crosses the plane
			{
				Point p=ray.getPoint(t);//get the new point on the ray, multiplied by the scalar t. p is the intersection point.
				return List.of(new GeoPoint(this,p));//if so, return the point- the intersection
			}
			else // the ray doesn't cross the plane
				return null;	
		}
		catch(Exception ex) {
			return null;
		}
	}

}
