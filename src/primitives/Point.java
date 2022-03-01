package primitives;

public class Point 
{
	 Double3  xyz;
	
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
		Vector vector=new Vector(xyz.subtract(p.xyz)); //##################
		return vector.lengthSquere();
	}
	public double distance(Point p)
	{
		return Math.sqrt(distanceSquared(p));

	}
}
