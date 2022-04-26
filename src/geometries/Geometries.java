package geometries;
import  primitives.Point;
import java.util.List;
import java.util.ArrayList;

import primitives.*;

public class Geometries
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
  public List<Point> findIntsersections(Ray ray)
  {
	  List<Point> intersections = new ArrayList<Point>();// new empty list of points and geometries
		for (int i = 0; i < lst.size(); i++) // move on all the geometries
		{
			if (lst.get(i).findIntersections(ray) != null) // if there are intersections to the ray with the
				// specific shape
			{
				for (int j = 0; j < lst.get(i).findIntersections(ray).size(); j++) // move on all the
					// intersections point
					// with this shape
				{
					intersections.add(lst.get(i).findIntersections(ray).get(j));// add them to general list of
					// intersections
				}
			}
		}
		if (intersections.size() == 0)
			return null;// No intersection at all
		return intersections;// return all the intersections
  }
}
