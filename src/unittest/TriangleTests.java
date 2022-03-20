package unittest;
import geometries.*;
import primitives.*;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * @author Adi
 *
 */
public class TriangleTests 
{
	 @Test
	    public void testfindIntersections() 
	    {
	       
	        	Triangle triangle = new Triangle(new Point(0, 1, 0), new Point(2, 6, 0), new Point(5, 0, 0));
	        

	        // ============ Equivalence Partitions Tests ====================

	        // TC01: The ray cuts the plane In front of the side of the triangle
	        Ray ray = new Ray(new Point(6.94, -2.39, 0), new Vector(new Point(-2.68, 5.72, 0).getXyz()));
	        assertNull(triangle.findIntersections(ray), "the intersection point is out of the triangle - need 0 intersections");

	        // TC02: The ray intersects the plane on which the triangle is in front of the vertex
	        ray = new Ray(new Point(-0.93, 6.2, 0), new Vector(new Point(2.54, 2.23, 0).getXyz()));
	        assertNull(triangle.findIntersections(ray), "the intersection point is out of the triangle - need 0 intersections");

	        // TC03: The ray cuts the plane within the boundaries of the triangle
	        ray = new Ray(new Point(-2.09, 2.69, 2.3), new Vector(new Point(4.09, -0.69, -2.3).getXyz()));
	        assertEquals( 1, triangle.findIntersections(ray).size(), "the intersection point is in the triangle - need 1 intersections");
	        
	       

	        // =============== Boundary Values Tests ==================

	        // TC11: The ray intersects on the side of the triangle
	        ray = new Ray(new Point(4.26, -1.28, 2.14), new Vector(new Point(-0.15, 3.07, -2.14).getXyz()));
	        assertNull(triangle.findIntersections(ray), "on the side - need 0 intersections");
	        
	        // TC12: The ray intersects on one of the vertices of the triangle
	        ray = new Ray(new Point(3.7, -0.71, 1.44), new Vector(new Point(1.3, 0.71, -1.44).getXyz()));
	        assertNull( triangle.findIntersections(ray), "on the vertex - need 0 intersections");
	        
	        // TC13: The ray On the straight line continuing the side of the triangle
	        ray = new Ray(new Point(3.86, -4.95, 0), new Vector(new Point(2.97, 1.28, 0).getXyz()));
	        assertNull(triangle.findIntersections(ray), "the intersection point is out of the triangle - need 0 intersections");

	        

	}
}
