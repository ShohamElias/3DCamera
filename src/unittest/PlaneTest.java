package unittest;
import geometries.*;
import primitives.*;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * @author Adi
 *
 */
public class PlaneTest
{
	@Test
	public void testfindIntersections() 
	{
			Plane myPlane = new Plane(new Point(0,5,0), new Point(-5,0,0), new Point(0,0,3));
			// =============== Boundary Values Tests ==================
			
			//Ray is parallel to the plane
			// TC01: the ray included in the plane
			Ray myRay= new Ray(new Point(0,5,0), new Vector(-5,0,0));//the plane include this ray
			assertNull( myPlane.findIntersections(myRay),"An included ray has zero intersection points");
			// TC02: the ray not included in the plane
			myRay= new Ray(new Point(0,-5,0), new Vector(5,0,0));//the plane included this ray
			assertNull( myPlane.findIntersections(myRay), "An un-included ray has zero intersection points");
			
			//Ray is orthogonal to the plane
			// TC03:נ�‘ƒ0 before the plane
			myRay= new Ray(new Point(2,4,0), new Vector(-3,3,5));//the ray is orthogonal to the plane
			assertEquals(1, myPlane.findIntersections(myRay).size(), "Ray is orthogonal to the plane and starts before the plane");
			// TC04:נ�‘ƒ0 at the plane
			myRay= new Ray(new Point(-5,0,0), new Vector(-3,3,5));//the ray is orthogonal to the plane
			assertNull( myPlane.findIntersections(myRay), "Ray is orthogonal to the plane and starts at the plane");
			// TC05:נ�‘ƒ0 after the plane
			myRay= new Ray(new Point(-7,2,4), new Vector(-3,3,5));//the ray is orthogonal to the plane
			assertNull(myPlane.findIntersections(myRay), "Ray is orthogonal to the plane and starts after the plane");
			
			//Ray is neither orthogonal nor parallel to and begins at the plane
			// TC06:
			myRay= new Ray(new Point(-1,-1,0), new Vector(1,0,0));//the ray isn't orthogonal or parallel to the plane
			assertNull(myPlane.findIntersections(myRay), "Ray is neither orthogonal nor parallel to and begins at reference point in the plane");
			
			//Ray is neither orthogonal nor parallel to the plane and begins in
			//the same point which appears as reference point in the plane
			// TC07:
			myRay= new Ray(new Point(0,0,3), new Vector(-5,4,-3));//the ray isn't orthogonal or parallel to the plane but not intersects the plane
			assertNull(myPlane.findIntersections(myRay), "Ray is neither orthogonal nor parallel to and begins at the plane");
			
			// ============ Equivalence Partitions Tests ================
			// TC08: The Ray must be neither orthogonal nor parallel to the plane
			//Ray does not intersect the plane
			myRay= new Ray(new Point(1,2,0), new Vector(-3,-7,0));
			assertNull( myPlane.findIntersections(myRay), "Ray is neither orthogonal nor parallel but doesnt intersects the plane");
			
			// TC09:
			// Ray intersects the plane
			myRay= new Ray(new Point(4,3,0), new Vector(-5.75,3.57,0));//the ray isn't orthogonal or parallel to the plane and intersects the plane
			assertEquals(1, myPlane.findIntersections(myRay).size(), "Ray is neither orthogonal nor parallel and intersects the plane");
	}
}
