package geometries;

import java.util.List;

import primitives.Point;
import primitives.Ray;
import primitives.*;

public class Triangle extends Polygon
{
	/**
	 * Constructor that receives 3 points and calls to the constructor of the base class
	 * 
	 * @param p1 Point3D
	 * @param p2 Point3D
	 * @param p3 Point3D
	 * @throws Exception 
	 * */
	public Triangle(Point p,Point p1,Point p2)
	{
		super(p,p1,p2);
	}
	
	/**
	 * @param ray
	 * @return a list of GeoPoints- intersections of the ray with the triangle, and this triangle
	 */
	@Override
	public List<GeoPoint> findGeoIntersections(Ray ray)
	{

		List<GeoPoint> intersections = plane.findGeoIntersections(ray);//find intersections with the plane
		//(triangle extends polygon and polygon contains plane)
		if (intersections == null) 
			return null;//if no intersections with plane

		Point p0 = ray.getP0();
		Vector v = ray.getDir();
		
		
		//we are creating a kind of pyramid by 3 vectors 
		Vector v1 = vertices.get(0).subtract(p0).normalize();
		Vector v2 = vertices.get(1).subtract(p0).normalize();
		Vector v3 = vertices.get(2).subtract(p0).normalize();
     
		//if the ray lays on the 'Pea'
		//so the intersection is on the edge of the triangle therefore we don't count it as an intersection 
		double s1 = v.dotProduct(v1.crossProduct(v2));//[v1.crossProduct(v2)=normal of v1 and v2
		if ((s1)==0) return null;
		double s2 = v.dotProduct(v2.crossProduct(v3));//[v2.crossProduct(v3)=normal of v2 and v3
		if ((s2)==0) return null;
		double s3 = v.dotProduct(v3.crossProduct(v1));//[v3.crossProduct(v1)=normal of v3 and v1
		if ((s3)==0) return null;

		if ((s1 > 0 && s2 > 0 && s3 > 0) || (s1 < 0 && s2 < 0 && s3 < 0))//the point is inside the triangle so the ray intersects the triangle 
		{
			for (GeoPoint geo : intersections) //for each geoPoint in intersections change the geometry to be 'this'
            {
                geo.geometry = this;//triangle and not plane
            }
			return intersections;//return the intersection
		}
		else
			return null;//the ray is on the plane but outside the triangle
	}
	
	
	@Override
	public String toString() 
	{
		return "Triangle: "+super.toString();
	}

}
