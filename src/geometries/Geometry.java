package geometries;

import primitives.Vector;
import primitives.Point;
public  interface  Geometry extends Intersectable
{
	public Vector getNormal(Point p);
}
