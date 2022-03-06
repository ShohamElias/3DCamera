/**
 * 
 */
package unittest;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;

import primitives.Point;

/**
 * @author shoham
 *
 */
class PointTests {

	/**
	 * Test method for {@link primitives.Point#subtract(primitives.Point)}.
	 */
	@Test
	void testSubtract() 
	{
		Point p=new Point(1, 2, 3);
		
		assertEquals(new primitives.Vector(1, 1, 1), new Point(2, 3, 4).subtract(p),"substruct test fail");
		
	}

	/**
	 * Test method for {@link primitives.Point#add(primitives.Vector)}.
	 */
	@Test
	void testAdd()
	{
		Point p=new Point(1, 2, 3);
		assertEquals(p.add(new primitives.Vector(-1, -2, -3)), new Point(0, 0, 0),"add test fail");
	}

	/**
	 * Test method for {@link primitives.Point#distanceSquared(primitives.Point)}.
	 */
	@Test
	void testDistanceSquared() 
	{
		Point p=new Point(1,2,3);
		assertEquals(p.distanceSquared(new Point(1, 1, 1)), 5,"distance squere between disserent ponts fail");
		assertEquals(p.distanceSquared(p), 0,"distance squared between same point fail");
		
	}

	/**
	 * Test method for {@link primitives.Point#distance(primitives.Point)}.
	 */
	@Test
	void testDistance()
	{
		Point p=new Point(4,6,8);
		assertEquals(p.distance(new Point(0, 6, -1)), 5,"distance between 2 Points fail");
		assertEquals(p.distance(p), 0,"distanc between same point fail");
	}

}
