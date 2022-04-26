package primitives;
import java.util.List;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.IconifyAction;
import geometries.Intersectable.GeoPoint; 
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
public Point findClosestPoint(List<Point> points) 
{
    return points == null || points.isEmpty() ? null
           : findClosestGeoPoint(points.stream().map(p -> new GeoPoint(null, p)).toList()).point;
}


/**
 * @param lst_point 
 * @return GeoPoint ,the closest point to the ray
 */
public GeoPoint findClosestGeoPoint (List<GeoPoint> lst_point) {
	if (lst_point==null)//if the list is empty
		return null;
	double min_dis=p0.distance(lst_point.get(0).point);//for the sake of programming we assumed that the first point is the closest 
	double dis;
	GeoPoint target=lst_point.get(0);//as above...
	for(int i=1;i<lst_point.size();i++) {//this loop moves through the points of the given list and compares the distances between the 
		//current point to the starting point of the ray 
		dis=p0.distance(lst_point.get(i).point);
		if(dis<min_dis) {
			min_dis=dis;
			target=lst_point.get(i);
		}
	}
	return target;

}
}
