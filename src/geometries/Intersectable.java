package geometries;
import java.util.List;

import primitives.*;
import static primitives.Util.*;

public abstract class Intersectable
{
	public List<Point> findIntersections(Ray ray)
	{
	    var geoList = findGeoIntersections(ray);
	    return geoList == null ? null
	                           : geoList.stream().map(gp -> gp.point).toList();
	}

	public List<GeoPoint> findGeoIntersections(Ray ray)
	{
		return findGeoIntersectionsHelper(ray);
	}

	protected abstract  List<GeoPoint> findGeoIntersectionsHelper(Ray ray);
	
	public static class GeoPoint 
	{
	    public  Geometry geometry;
	    public  Point point;
	    
	    /**
		 * a constructor that gets two parameters and generates them
		 * 
		 * @param gmtry
		 * @param pnt
		 */
	    public GeoPoint(Geometry gmtry, Point pnt)
	    {
	    	geometry=gmtry;
	    	point=pnt;
	    }
	    
	    @Override
		public String toString() {
			return "GP{" + "G=" + geometry + ", P=" + point + '}';
		}

		/**
		 * an  function that checks if the objects are equal
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			GeoPoint other = (GeoPoint) obj;
			if (geometry == null) {
				if (other.geometry != null)
					return false;
			} else if (!geometry.equals(other.geometry))
				return false;
			if (point == null) {
				if (other.point != null)
					return false;
			} else if (!point.equals(other.point))
				return false;
			return true;
		}
	}

}
