package renderer;

import geometries.Intersectable.GeoPoint;
import primitives.*;
import primitives.Double3;
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
	/**
	 * a constant for size moving first rays for shading rays
	 */
	private static final double DELTA = 0.01;

	/**
	 * Two constants for stopping conditions
	 *  in the recursion of transparencies / reflections
	 */
	/**
	 * recursion stop condition - the maximum number of colors
	 */
	private static final int MAX_CALC_COLOR_LEVEL = 10;
	/**
	 * recursion stop condition -
	 */
	private static final double MIN_CALC_COLOR_K = 0.001;
	
	private boolean isImprovement=true;//a boolean field that tells us if we want to display the image with improvement or not

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

	private Color calcColor(GeoPoint geoPoint, Ray ray) 
	{
		return calcColor(geoPoint, ray, MAX_CALC_COLOR_LEVEL, new Double3(Ray.getDeltha())).add(scene.ambientLight.getIntensity());
			
	}
	
	/**
	 * calculates the color of the point that the ray intersect it 
	 * (we already get here the closest intersection point)
	 * @param point
	 * @param the ray
	 * @param level of recursion- goes down each time till it gets to 1
	 * @param k- mekadem of reflection and refraction so far
	 * @return the color 
	 */
	private Color calcColor(GeoPoint intersection, Ray ray, int level, Double3 k) 
	{
		//in the recursive calls to calcColor we might get that intersections=null.
		//if so- return black. no adding color and light.
		if(intersection==null)
			return Color.BLACK;

		Color color = intersection.geometry.getEmission();//the color of the object itself
		color = color.add(calcLocalEffects(intersection, ray,  k));//diffuse, specular, and shadow.
		return 1 == level ? color : color.add(calcGlobalEffects(intersection, ray, level, k));//reflection and refraction- may cause recursion
	}
	
	/**
	 * A function that calculate the globals effects of the color
	 * 
	 * @author Tamar Gavrieli & Odeya Sadoun
	 * @param intersection GeoPoint value
	 * @param ray Ray value
	 * @param level int value
	 * @param k double value
	 * @param n Vector value
	 * @return Color
	 * */
	private Color calcGlobalEffects(GeoPoint intersection, Ray ray, int level, Double3 k )
	{
		Vector n = intersection.geometry.getNormal(intersection.point);//normal to geometry in point --need this parameter for construct reflected\refracted ray

		if (level == 1 || k.lowerThan(MIN_CALC_COLOR_K) )
		{
			return Color.BLACK;
		}
		Color color = Color.BLACK;
		Material material = intersection.geometry.getMaterial();
		Double3 kr = material.kr;
		Double3 kkr = k .product(kr) ;
		if (!kkr .lowerThan(MIN_CALC_COLOR_K) ) 
		{
			Ray reflectedRay = constructReflectedRay(n, intersection.point, ray);
			GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);
			if (reflectedPoint != null)
				color = color.add(calcColor(reflectedPoint, reflectedRay, level - 1, kkr).scale(kr));
			else
				color = color.add(scene.background.scale(kr));
		}
		Double3 kt = material.kt;
		Double3 kkt =  k .product(kt);
		if (!kkt.lowerThan(MIN_CALC_COLOR_K) ) 
		{
			Ray refractedRay = constructRefractedRay(n, intersection.point, ray);
			GeoPoint refractedPoint = findClosestIntersection(refractedRay);
			if (refractedPoint != null)
				color = color.add(calcColor(refractedPoint, refractedRay, level - 1, kkt).scale(kt));
			else
				color = color.add(scene.background.scale(kt));
		}
		return color;
	}
	private Color calcLocalEffects(GeoPoint intersection, Ray ray, Double3 k) 
	{
		Vector v = ray.getDir().normalize();
		Vector n = intersection.geometry.getNormal(intersection.point);
		double nv = alignZero(n.dotProduct(v));
		if (nv == 0) //לא רואים את הנקודה עליה האור משפיע מחזיר שחור
			return Color.BLACK;
		//רוצים לבדוק את ההשפעה של האור עלי לפי סוג החומר ממנו הגוף עשוי
		Material material = intersection.geometry.getMaterial();
		int nShininess = material.nShininess;
		Double3 kd = material.kD;
		Double3 ks = material.kS;
		Color color = Color.BLACK; //עוד לא יודעים השפעות
		for (LightSource lightSource : scene.lights) //עוברים כעל כל מקור אור בסצנה ובודקים איך הוא משפיע על הצבע בנקודה המסויימת
		{
			Vector l = lightSource.getL(intersection.point);//וקטור ממקור אור עד לנקודה
			double nl = alignZero(n.dotProduct(l));//רוצים לדעת שאני באותו כיוון כי אם לא לא רואים את ההשפעות
			if (nl * nv > 0) 
			{ 
				Double3 ktr = transparency(lightSource, l, n, intersection);
				//if(unshaded(lightSource, l, n, intersection)) {
				if (!(ktr.product(k)).lowerThan(MIN_CALC_COLOR_K)) 
				{
				// sign(nl) == sing(nv)
					

					Color lightIntensity = lightSource.getIntensity(intersection.point).scale(ktr);;
					color = color.add(calcDiffusive(kd, l, n, lightIntensity),//diffusive
							calcSpecular(ks, l, n, v, nShininess, lightIntensity));				}
			}
		//	}
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
	
	private Double3 transparency(LightSource ls, Vector l, Vector norm, GeoPoint intersection) {
		Vector lightDirection = l.scale(-1); // from point to light source
		Ray lightRay = new Ray(intersection.point, lightDirection, norm);//taking care of----
		double lightDistance = ls.getDistance(intersection.point); //the distance of the point of Point from the light source
		var intersections = scene.geometries.findGeoIntersections(lightRay);
		if (intersections == null) return Double3.ONE;

		Double3 ktr =Double3.ONE;//transparency initial

		for (GeoPoint gp : intersections) //move on all the geometries in the way
		{
			//if there are geometr between the point to the light- calculate the transparency 
			//in order to know how much shadow there is on the point
			if (Util.alignZero(gp.point.distance(intersection.point)-lightDistance) <= 0) 
			{
				ktr =ktr.product(gp.geometry.getMaterial().kt);//add this transparency to ktr
				if (ktr.lowerThan(MIN_CALC_COLOR_K)) //stop the loop- shadow "atum", black. very small transparency
					return Double3.ZERO;
			}
		}
		return ktr;
	}

	
	/**
	 * Non-shading test operation between point and light source
	 * @param lS -the current light source 
	 * @param l a vector from the light source to the point 
	 * @param norm
	 * @param gp
	 * @return if the point has no shade return true (and then it'll get light)
	 */
	private boolean unshaded(LightSource lS,Vector l, Vector norm, GeoPoint gp) {

		Vector lightDirection = l.scale(-1); // from point to light source
		Vector delta = norm.scale(norm.dotProduct(lightDirection) > 0 ? DELTA : - DELTA);//if the result is greater than 0 delta=DELTA else delta=-DELTA
		//there are cases that points in the object can falsely be shaded by the object itself-->  so we used the delta to up lift
		//the point a-bit to avoid that
		Point point = gp.point.add(delta);//we add it to the point by the normal direction,

		Ray lightRay = new Ray(point, lightDirection);
		List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);
		if (intersections == null)//if there are no intersections with the light ray
			return true;//the point is unshaded- no one makes shadow on the point
		//else- there are intersections with the light ray
		double lightDistance = lS.getDistance(gp.point);//the distance of the point of geoPoint from the light source
		for (GeoPoint geop : intersections) 
		{
			if(geop.geometry.getMaterial().nShininess==0.0)//only if the material of the geo is "atum"- it makes shadow
			{
				//if there is an intersection closer to beginning of ray than our intersection
				//of geopoint that we got- return false. 
				//it means, there is something shadowing our intersection of geopoint.
				if (Util.alignZero(geop.point.distance(gp.point)-lightDistance) <= 0)
					return false;
			}
		}
		//else- if we didn't find an intersection that is closer to the head of the ray from the distance
		//between the point to the light source:
		return true;	
	}
	
	/**
	 * A function that calculates the reflected rays.
	 * 
	 * @param normal Vector value
	 * @param point Point3D value
	 * @param ray Ray value
	 * @return ray for reflected
	 * */
	private Ray constructReflectedRay(Vector normal, Point point, Ray ray) //השתקפות
	{
		// 𝒓 = 𝒗 − 𝟐 ∙ (𝒗 ∙ 𝒏) ∙ n
		Vector v = ray.getDir().normalize();
		double nv = alignZero(normal.dotProduct(v));
		if (isZero(nv))
			return null;
		Vector r = v.subtract(normal.scale(nv*2)).normalize();
		return new Ray(point.add(normal.scale(DELTA)), r, normal);
	}
	
	/**
	 * A function that calculates the refracted rays.
	 * 
	 * @param normal Vector value
	 * @param point Point3D value
	 * @param ray Ray value
	 * @return ray for refracted
	 * */
	private Ray constructRefractedRay(Vector normal, Point point, Ray ray) //שקיפות
	{
	    Vector v = ray.getDir().normalize();
		return new Ray(point, v ,normal.normalize());
	}

	/**
	 * A function that find the most closet point to the ray
	 * 
	 * @param ray Ray value
	 * @return the closet point
	 * */
	private GeoPoint findClosestIntersection(Ray ray)
	{
		List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
		if(intersections == null)
			return  null;
		return ray.findClosestGeoPoint(intersections);
	}
	
}