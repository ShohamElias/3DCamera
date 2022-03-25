package primitives;

public class Ray
{
  private final Point p0;
  private final Vector dir;
  
  public Ray(Point p, Vector v)
  {
	  p0=p;
	  dir=v.normalize();
  }

public Point getP0() {
	return p0;
}

public Vector getDir() {
	return dir;
}

public Point getPoint(double t) 
{
	return p0.add(dir.scale(t));
}
}
