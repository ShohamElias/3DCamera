package renderer;

import geometries.Intersectable.GeoPoint;

import static primitives.Util.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import primitives.Color;
import primitives.Double3;
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
		return calcColor(closestPoint, ray);
	}

	private Color calcColor(GeoPoint pointAndGeo, Ray ray) 
	{
		Color result= scene.ambientLight.getIntensity();
	    Color color = result.add(calcLocalEffects(pointAndGeo, ray,1));
		return color.add(pointAndGeo.geometry.getEmission());
			
	}
	
	private Color calcLocalEffects(GeoPoint intersection, Ray ray, int li) 
	{
		
		Vector v = ray.getDir ();
		Vector n = intersection.geometry.getNormal(intersection.point);
		double nv = alignZero(n.dotProduct(v));
		if (nv == 0) 
			return Color.BLACK;
		int nShininess = intersection.geometry.getMaterial().nShininess;
		Double3 kd = intersection.geometry.getMaterial().kD, 
			   ks = intersection.geometry.getMaterial().kS;
		Color color = Color.BLACK;
		for (LightSource lightSource : scene.lights) 
		{
			Vector l = lightSource.getL(intersection.point).normalize(); 
			double nl = alignZero(n.dotProduct(l)); 
			if (nl * nv > 0) 
			{ 
				double ktr = transparency(lightSource, l, n, intersection);
					
					Color lightIntensity = lightSource.getIntensity(intersection.point).scale(ktr);//ktr=transparency- for shadow
					;
					color = color.add(calcDiffusive(kd, l, n, lightIntensity),//diffusive
							calcSpecular(ks, l, n, v, nShininess, lightIntensity));//specular
			}
		}
		return color;
	}

	private Color calcDiffusive(Double3 kd, Vector l, Vector n, Color ip) 
	{
		double nl = Util.alignZero(n.dotProduct(l));//the light normal

		if (nl < 0) 
			nl = -nl;
		return ip.scale(kd.scale(nl));
	}
	
	private Color calcSpecular(Double3 ks, Vector l, Vector n, Vector v, int nShininess, Color ip) 
	{
		double p = Util.alignZero(nShininess);

		double nl = Util.alignZero(n.dotProduct(l));//the light normal

		Vector R = l.subtract(n.scale(2 * nl)).normalize(); // nl must not be zero!
		double minusVR = -Util.alignZero(R.dotProduct(v));
		if (minusVR <= 0) 
		{
			return Color.BLACK; // view from direction opposite to r vector
		}
		return ip.scale(ks.scale( Math.pow(minusVR, p)));
	}
	
	private double transparency(LightSource ls, Vector l, Vector norm, GeoPoint intersection) {
		Vector lightDirection = l.scale(-1); // from point to light source
		Ray lightRay = new Ray(intersection.point, norm);//taking care of----
		var intersections = scene.geometries.findGeoIntersections(lightRay);
		if (intersections == null) return 1.0;

		double ktr = 1.0;//transparency initial

		for (GeoPoint gp : intersections) //move on all the geometries in the way
		{
			//if there are geometr between the point to the light- calculate the transparency 
			//in order to know how much shadow there is on the point
			
				//ktr *= gp.geometry.getMaterial().kt;//add this transparency to ktr
				
		}
		return ktr;
	}
	
}
