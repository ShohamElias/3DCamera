package unittest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.ArrayList;
import geometries.*;
import primitives.*;

class RayTest {

	/**
	 * Test method for {@link primitives.Ray#findClosestPoint(java.util.List)}.
	 */
@Test
public void testFindClosestPoint()
{
	// ============ Equivalence Partitions Tests ============== //
	// TC01: test of finding the closest point that is in the middle of the arrayList
	Ray r=new Ray(new Point(5, 5, 5.1),new Vector(1,1,1));
	List<Point> lst_points;
	lst_points=new ArrayList<Point>();
    for(int i=0;i<10;i++) 
    {
    lst_points.add(i,new Point(i+2,i+2,i+2));//from (2,2,2) to (11,11,11)
    }
    assertEquals(new Point(5,5,5) , r.findClosestPoint(lst_points), "did not find the right closest point to the ray -in the middle");
    
    // =============== Boundary Values Tests ================== //
    //TC02:test of empty list case-the method should return null
    List<Point> lst_NoPoints;
    lst_NoPoints=new ArrayList<Point>();
    assertEquals(null, r.findClosestPoint(lst_NoPoints), "did not return null when the array list was empty");
    
    //TC03:test of finding the closest point that is in the beginning of the arrayList
    Ray r3=new Ray(new Point(2, 2, 2.1),new Vector(1,1,1));
    assertEquals(new Point(2,2,2) , r3.findClosestPoint(lst_points), "did not find the right closest point to the ray -in the begining of the list");
    
    //TC04:test of finding the closest point that is in the end of the arrayList
    Ray r4=new Ray(new Point(11, 11, 11.1),new Vector(1,1,1));
    assertEquals(new Point(11,11,11) , r4.findClosestPoint(lst_points), "did not find the right closest point to the ray -in the end of the list");
}
}
