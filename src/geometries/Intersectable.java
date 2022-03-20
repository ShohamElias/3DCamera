package geometries;
import java.util.List;

import primitives.*;
import static primitives.Util.*;

public interface Intersectable
{
	public List<Point> findIntersections(Ray ray);
}
