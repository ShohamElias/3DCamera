/**
 * 
 */
package unittest;
import primitives.Point;
import primitives.Util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import primitives.Vector;

/**
 * @author Shoham
 *
 */
class VectorTests {

	/**
	 * Test method for {@link primitives.Vector#normalize()}.
	 */
	@Test
	void testNormalize() 
	{
		Vector v=new Vector(1, 2, 3);
		assertEquals(v.normalize().length(), 1,"test normalize fail");				
	}

	/**
	 * Test method for {@link primitives.Vector#add(primitives.Vector)}.
	 */
	@Test
	void testAddVector()
	{
		Vector v=new Vector(1, 2, 3);
		Point p=new Point(1, 0, 0);
		assertEquals(v.add(new primitives.Vector(-1, -2, -3)), p.subtract(new Point(1, 0, 0)),"add test fail");
		}

	/**
	 * Test method for {@link primitives.Vector#scale(double)}.
	 */
	@Test
	void testScale() 
	{
		Vector v=new Vector(1, 2, 3);
		assertEquals(v.scale(3), new Vector(3, 6, 9),"scale test fail");
		Point p=new Point(1, 0, 0);
		assertEquals(v.scale(0), p.subtract(new Point(1, 0, 0)),"zero scale test fail");
		
		}

	/**
	 * Test method for {@link primitives.Vector#dotProduct(primitives.Vector)}.
	 */
	@Test
	void testDotProduct() {
		Vector v1 = new Vector(1, 2, 3);
		Vector v2 = new Vector(-2, -4, -6);
		Vector v3 = new Vector(0, 3, -2);
		assertTrue(primitives.Util.isZero(v1.dotProduct(v3)),"dot product for orthogonal fail");
		assertEquals(v1.dotProduct(v2), -28,"dot product fail");

	}

	/**
	 * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
	 */
	@Test
	void testCrossProduct()
	{
		Vector v1 = new Vector(1, 2, 3);

        // ============ Equivalence Partitions Tests ==============
        Vector v2 = new Vector(0, 3, -2);
        Vector vr = v1.crossProduct(v2);

        // TC01: Test that length of cross-product is proper (orthogonal vectors taken
        // for simplicity)
        assertEquals( v1.length() * v2.length(), vr.length(), 0.00001,"crossProduct() wrong result length");

        // TC02: Test cross-product result orthogonality to its operands
        assertTrue(primitives.Util.isZero(vr.dotProduct(v1)),"crossProduct() result is not orthogonal to 1st operand");
        assertTrue( primitives.Util.isZero(vr.dotProduct(v2)),"crossProduct() result is not orthogonal to 2nd operand");

        // =============== Boundary Values Tests ==================
        // TC11: test zero vector from cross-productof co-lined vectors
        Vector v3 = new Vector(-2, -4, -6);
        assertThrows(IllegalArgumentException.class, ()-> v1.crossProduct(v3),"crossProduct() for parallel vectors does not throw an exception");
	}

	/**
	 * Test method for {@link primitives.Vector#lengthSquere()}.
	 */
	@Test
	void testLengthSquere() 
	{
		Vector v1 = new Vector(1, 2, 3);
		assertEquals(v1.lengthSquere(), 14,"length squere test fail");
	}

	/**
	 * Test method for {@link primitives.Vector#length()}.
	 */
	@Test
	void testLength()
	{
       Vector v=new Vector(0, 3, 4);
       assertEquals(v.length(), 5, "vector length test fail");
	}

}
