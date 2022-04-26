package geometries;

import java.util.List;

import primitives.Point;
import primitives.Vector;
import primitives.Ray;
import primitives.Util;

public class Tube extends Geometry
{
	Ray axisray;
	double radius;
	public Tube(Ray ax, double rad)
	{
		super();
		if(Util.isZero(rad) || rad < 0)
	        throw new IllegalArgumentException("Zero or negative radius");
		axisray=ax;
		radius=rad;
	}
	@Override
	public String toString() 
	{
		return "Tube: radius=" + radius + ", axisray=" + axisray;
	}
	@Override
	public Vector getNormal(Point p)
	{
	    Point rayP = axisray.getP0();
	    Vector rayV = axisray.getDir();

	    //get point on the same level as the given point
	    double t = rayV.dotProduct(p.subtract(rayP));

	    //if the point is not on the same level then get the point
	    //and return the normal
	    if(!Util.isZero(t)){
	        Point o = rayP.add(rayV.scale(t));
	        return p.subtract(o).normalize();
	    }

	    //if the point is on the same level then return normal
	    return p.subtract(axisray.getP0()).normalize();

	}
	
	
	@Override
	protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
		// TODO Auto-generated method stub
		return null;
	}
}
