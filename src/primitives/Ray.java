package primitives;
import java.util.List;

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

/**
 * @param lst_point 
 * @return point3D ,the closest point to the ray
 */
public Point findClosestPoint (List<Point> lst_point) 
{
	if (lst_point.isEmpty()) 
		return null;
	double min_dis=p0.distance(lst_point.get(0));//we assumed that the first point is the closest= resetting
	double dis;
	Point target=lst_point.get(0);//as above...
	for(int i=1;i<lst_point.size();i++) 
	{
		//moves through the points of the given list and compares the distances between the 
		//current point to the starting point of the ray 
		dis=p0.distance(lst_point.get(i));
		if(dis<min_dis) //if its closer:
		{
			min_dis=dis;
			target=lst_point.get(i);//saving the point
		}
	}
	return target;//returning the closest point
}

}
