package geometries;

import primitives.Point;
import primitives.Vector;
import primitives.Ray;

public class Tube implements Geometry
{
	Ray axisray;
	double radius;
	@Override
	public Vector getNormal(Point p)
	{
		Vector vector=new Vector(axisray.getP0().subtract(p).getXyz());
		double scalar=vector.dotProduct(axisray.getDir());
		Point point=axisray.getP0().add(vector.scale(scalar));
		
	    vector=new Vector(point.subtract(p).getXyz());
		return vector.normalize();
	}
}
