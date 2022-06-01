package primitives;
import java.util.List;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.IconifyAction;
import geometries.Intersectable.GeoPoint; 
public class Ray
{
  private final Point p0;
  private final Vector dir;
  
  /**
	 * A constant for the size of moving first rays for shading rays
	 * */
	private static final double DELTA = 0.1;
	
	public static double getDeltha() {
		return DELTA;
	}
  public Ray(Point p, Vector v)
  {
	  p0=p;
	  dir=v.normalize();
  }

public Point getP0() {
	return p0;
}
/**
 * Let us note that building a beam with moving a point - this is an operation that is repeated three times already, 
 * so it is worthwhile to lower it to a separate function.
 * The location of the function according to RDD is the fund department.
 * Since this is a new fund construction (according to the original point,
 * the direction of the fund, the normal vector (on the line it defines the point of the fund head must be moved))
 * - we will add the function as another fund builder and update In all relevant places the code so that it will use the new builder of the fund. 
 * Of course the permanent DELTA we defined earlier will be transferred to the Ray class
 * @param point
 * @param lightDirection
 * @param norm
 */
public Ray(Point point, Vector lightDirection, Vector norm) {

	this.dir = lightDirection.normalize();
	double nV = norm.dotProduct(lightDirection);
	Vector delta = norm.scale(nV >= 0 ? DELTA : -DELTA);
	this.p0 = point.add(delta);


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
public GeoPoint findClosestGeoPoint(List<GeoPoint> intersections) {
	if(intersections == null)
		return null;
	GeoPoint closet = intersections.get(0);
	for (GeoPoint geoPoint : intersections) 
	{
		if(geoPoint.point.distance(p0) < closet.point.distance(p0))
			closet= geoPoint;
		
	}
	return closet;
}
}
