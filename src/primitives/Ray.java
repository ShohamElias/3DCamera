package primitives;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.IconifyAction;

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
	return Util.isZero(t)? p0: p0.add(dir.scale(t));
}
@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	Ray other = (Ray) obj;
	if (dir == null) {
		if (other.dir != null)
			return false;
	} else if (!dir.equals(other.dir))
		return false;
	if (p0 == null) {
		if (other.p0 != null)
			return false;
	} else if (!p0.equals(other.p0))
		return false;
	return true;
}
}
