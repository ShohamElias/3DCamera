package primitives;

import javax.sql.rowset.JoinRowSet;

public class Vector extends Point
{
	public Vector(double d, double d2, double d3)
	{
		super(d,d2,d3);
	}
	
	public Vector(Double3 d)
	{
		super(d);
	}
	
	public Vector normalize()
	{
		double len=length();
		if(len==1)
			return this;
		return new Vector(xyz.reduce(len));
	}
	
	public Vector add(Vector v)
	{
	  return new Vector(this.xyz.add((v.xyz)));	
	}
	
	public Vector scale(double d)
	{
		return new Vector(xyz.scale(d));
	}
	
	public double dotProduct(Vector v) 
	{
		Double3 double3=xyz.product(v.xyz);
		return double3.d1+double3.d2+double3.d3;
	}
	public Vector crossProducr(Vector v)
	{
		double d3=xyz.d1*v.xyz.d2-xyz.d2*v.xyz.d1;
		double d1=xyz.d2*v.xyz.d3-v.xyz.d2*xyz.d3;
		double d2=xyz.d1*v.xyz.d3-v.xyz.d1*xyz.d3;
		Double3 double3=new Double3(d1, d2, d3);
		return new Vector(double3);

	}
	public double lengthSquere()
	{
		return xyz.d1*xyz.d1+xyz.d2*xyz.d2+xyz.d3*xyz.d3;
	}
	public double length()
	{
		return Math.sqrt(lengthSquere());
	}
}
