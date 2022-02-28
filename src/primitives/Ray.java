package primitives;

public class Ray
{
  private Point p0;
  private Vector dir;
  
  public Ray(Point p, Vector v)
  {
	  p0=p;
	  dir=v.normalize();
  }

}
