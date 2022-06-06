package primitives;

import java.util.Objects;

public class Point 
{
	 Double3 xyz;

	public Point(double x,double y,double z) 
	{
		xyz=new Double3(x, y, z);
	}
	
	public Point(Double3 d)
	{
		xyz=new Double3(d.d1,d.d2,d.d3);
	}
	
	public Vector subtract(Point p)
	{
		return new Vector(getXyz().subtract(p.getXyz()));
	}
	
	public Point add(Vector v)
	{
		return new Point(getXyz().add(v.getXyz()));
	}
	
	public double distanceSquared(Point p)
	{
		Double3 double3= getXyz().subtract(p.getXyz()); //##################
		return double3.d1*double3.d1+double3.d2*double3.d2+double3.d3*double3.d3;
	}
	
	public double distance(Point p)
	{
		return Math.sqrt(distanceSquared(p));
	}
	
	public Double3 getXyz() 
	{
		return xyz;
	}
	
	public double getX()
	{
	  return xyz.d1;
	}
	public double getY()
	{
	  return xyz.d2;
	}
	public double getZ()
	{
	  return xyz.d3;
	}
	@Override
	public String toString() 
	{
		return "Point: ("+ xyz.d1 + ","+ xyz.d2+"," + xyz.d3+")";
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Point other = (Point) obj;
		return Objects.equals(getXyz(), other.getXyz());
	}
}
