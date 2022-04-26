package renderer;

import geometries.Intersectable.GeoPoint;

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
		List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
		if (intersections == null)
			return scene.background;
		GeoPoint closestPoint = ray.findClosestGeoPoint(intersections);
		return calcColor(closestPoint);
	}

	private Color calcColor(GeoPoint pointAndGeo) 
	{
		Color result= scene.ambientLight.getIntensity().add(pointAndGeo.geometry.getEmission());
		//The code that adds the effect of the light sources on the point for which the color is calculated 
		//according to the simple phong model
		return result;		
	}

	
	
}
