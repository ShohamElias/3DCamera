package primitives;

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
		int x, y, c;
	}
	
	//public Vector Substruct(Point p) 
	//{
		
	//}

}
