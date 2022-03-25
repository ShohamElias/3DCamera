package geometries;

import java.util.List;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

public class Cylinder extends Tube
{	private double height;

	public Cylinder(Ray ax, double rad,double h)
	{
		super(ax, rad);
        height=h;
    }


	@Override
	public Vector getNormal(Point p)
	{
		return null;
	}

	@Override
	public List<Point> findIntersections(Ray ray) {
		// TODO Auto-generated method stub
		return null;
	}
}
