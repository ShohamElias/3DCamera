package renderer;

import static primitives.Util.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;
import scene.Scene;
import lighting.*;

public class RayTracerBasic extends RayTracerBase
{
	public RayTracerBasic(Scene scene) 
	{
		super(scene);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Color traceRay(Ray ray) 
	{
		List<Point> pointAndGeo=scene.geometries.findIntsersections(ray);//calls the function of findinf the closest intersection- the func is in this class 
		if(pointAndGeo!=null)
		{//if there is an intersection point- calc it's color 
			Point pnt=ray.findClosestPoint(pointAndGeo);
			return calcColor(pnt);
		}
			
		else							//if the ray doesn't intersect anything- return the background color
			return scene.background;
	}

	private Color calcColor(Point pointAndGeo) 
	{
		// TODO Auto-generated method stub
	   return scene.ambientLight.getIntensity();
	}

	
	
}
