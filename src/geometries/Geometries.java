package geometries;
import java.util.List;
import java.util.ArrayList;

import primitives.*;

public class Geometries extends Intersectable
{
  public List<Intersectable> lst;
  public Geometries(Intersectable... geometries) 
  {
	  lst = new ArrayList<Intersectable>();
		for (int i = 0; i < geometries.length; i++)
			lst.add(geometries[i]);
  }
  public void add(Intersectable... geometries)
  {
	  for (int i = 0; i < geometries.length; i++)
			lst.add(geometries[i]);
  }
  
  

/**
 * @param ray
 * @return a list of intersections of the ray with all the geometries in the
 *         list. all the composite component. we are using the design pattern of
 *         composite- here in one function we collect all the intersections of
 *         our geometry shapes by using their own "findInresection" function.
 */
@Override
protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray)
{
		List<GeoPoint> intersections = new ArrayList<GeoPoint>();// new empty list of points and geometries
		for (int i = 0; i < lst.size(); i++) // move on all the geometries
		{
			if (lst.get(i).findGeoIntersections(ray) != null) // if there are intersections to the ray with the
				// specific shape
			{
				for (int j = 0; j < lst.get(i).findGeoIntersections(ray).size(); j++) // move on all the
					// intersections point
					// with this shape
				{
					intersections.add(lst.get(i).findGeoIntersections(ray).get(j));// add them to general list of
					// intersections
				}
			}
		}
		if (intersections.size() == 0)
			return null;// No intersection at all
		return intersections;// return all the intersections
	}
}
