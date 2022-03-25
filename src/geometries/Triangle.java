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
	
	@Override
	public List<Point> findIntersections(Ray ray) 
	{
		List<Point> rayPoints = plane.findIntersections(ray);
		if (rayPoints == null)
			return null;
		
		//check if the point in out or on the triangle:
		Vector v1 = vertices.get(0).subtract(ray.getP0());
		Vector v2 = vertices.get(1).subtract(ray.getP0());
		Vector v3 = vertices.get(2).subtract(ray.getP0());
		
		Vector n1 = v1.crossProduct(v2).normalize();
		Vector n2 = v2.crossProduct(v3).normalize();
		Vector n3 = v3.crossProduct(v1).normalize();

		
		//The point is inside if all 𝒗 ∙ 𝑵𝒊 have the same sign (+/-)
		
		if (Util.alignZero(n1.dotProduct(ray.getDir())) > 0 &&Util.alignZero(n2.dotProduct(ray.getDir())) > 0 &&Util.alignZero(n3.dotProduct(ray.getDir())) > 0)
		{
			return rayPoints;
		}
		else if (Util.alignZero(n1.dotProduct(ray.getDir())) < 0 && Util.alignZero(n2.dotProduct(ray.getDir())) < 0 && Util.alignZero(n3.dotProduct(ray.getDir())) < 0)
		{
			return rayPoints;
		}
		if (Util.isZero(n1.dotProduct(ray.getDir())) || Util.isZero(n2.dotProduct(ray.getDir())) || Util.isZero(n3.dotProduct(ray.getDir())))
			return null; //there is no instruction point
		return null;
	}
	@Override
	public String toString() 
	{
		return "Triangle: "+super.toString();
	}

}
