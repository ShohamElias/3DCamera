package geometries;

import java.util.List;

import primitives.Point;
import primitives.Ray;

public class Triangle extends Polygon
{
	public Triangle(Point p,Point p1,Point p2)
	{
		super(p,p1,p2);
	}
	
	@Override
	public List<Point> findIntersections(Ray ray) 
	{
		// TODO Auto-generated method stub
		return null;
	}
}
