package geometries;

import java.util.List;

import org.junit.internal.runners.statements.Fail;

import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

public class Sphere extends Geometry
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
		//if(center.distance(p)!=radius)
			//throw new IllegalArgumentException("out of the sphere");
		return center.subtract(p).normalize();
	}

	
	@Override
	public String toString() 
	{
		return "Sphere: center=" + center + ", radius=" + radius;
	}

	@Override
	protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray)
	{//point and vector of ray
    	Point p0 = ray.getP0();//ray point
		Vector v = ray.getDir();//ray vector
		//check if ray point is the center,there'll be only 1 intersection found by the dir and radius
		if(p0.equals(center))     
			return List.of(new GeoPoint(this,ray.getPoint(radius)));//return the intersection point
		Vector u=center.subtract(p0);// the vector between center and ray
		double tm=v.dotProduct(u); //the scale for the ray in order to get parallel to the sphere center
		double d=Math.sqrt(u.lengthSquere()-tm*tm);//get the distance between the ray and the sphere center
		//check if d is bigger then radius-the ray doesn't cross the sphere
	   	if (d>radius)
			return null;
		double th=Math.sqrt(radius*radius-d*d);//according to Pitagoras
		double t1=tm+th;
		double t2=tm-th;
		if(t1>0 && t2>0 &&! Util.isZero(ray.getPoint(t1).subtract(center).dotProduct(v)) && !Util.isZero(ray.getPoint(t2).subtract(center).dotProduct(v))) //if orthogonal -> no intersection
			return List.of(new GeoPoint(this,ray.getPoint(t1)),new GeoPoint(this,ray.getPoint(t2)));
		else if(t1>0 && !Util.isZero(ray.getPoint(t1).subtract(center).dotProduct(v))) //if only t1 is not orthogonal and positive
			return List.of(new GeoPoint(this,ray.getPoint(t1)));
		else if(t2>0&&! Util.isZero(ray.getPoint(t2).subtract(center).dotProduct(v))) //if only t2 is not orthogonal and positive
		 	return List.of(new GeoPoint(this,ray.getPoint(t2)));
		else
			return null;//no intersections
	}

	@Override
	protected void CreateBoundingBox() {
		// TODO Auto-generated method stub
		
	}
}
