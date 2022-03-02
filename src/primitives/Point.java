package primitives;

import java.util.Objects;

public class Point 
{
	 Double3 xyz;
	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Point other = (Point) obj;
		return Objects.equals(xyz, other.xyz);
	}
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
		return new Vector(xyz.subtract(p.xyz));
	}
	public Point add(Vector v)
	{
		return new Point(xyz.add(v.xyz));
	}
	
	public double distanceSquared(Point p)
	{
		Double3 double3= xyz.subtract(p.xyz); //##################
		return double3.d1*double3.d1+double3.d2*double3.d2+double3.d3*double3.d3;
	}
	public double distance(Point p)
	{
		return Math.sqrt(distanceSquared(p));

	}
}
