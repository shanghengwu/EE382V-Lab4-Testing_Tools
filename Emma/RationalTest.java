import junit.framework.TestCase;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class RationalTest extends TestCase {

    protected Rational HALF;
    protected Rational THIRD;

    protected void setUp() {
      HALF = new Rational( 1, 2 );
      THIRD = new Rational( 1, 3 );
    }

    // Create new test
    public RationalTest (String name) {
        super(name);
    }

    public void testEquality() {
        assertEquals(new Rational(1,3), new Rational(1,3));
        assertEquals(new Rational(1,3), new Rational(2,6));
        assertEquals(new Rational(3,3), new Rational(1,1));
        assertEquals(new Rational( 1, 2), new Rational(-1,-2));
        assertEquals(new Rational(-1, 2), new Rational( 1,-2));
        assertEquals(new Rational( 1,-2), new Rational(-1, 2));
        assertEquals(new Rational(-1,-2), new Rational( 1, 2));
        assertEquals(new Rational(-Integer.MIN_VALUE,-Integer.MIN_VALUE), new Rational( 3, 3));
/*
        Rational s1 = new Rational( 1, 3 );
        Rational s1Root = null;
        try{
            s1Root = s1.root();
        }catch(Exception e){}

        assertEquals(new Rational( 1, 9 ), s1Root);
*/
    }

    // Test for multiplication
    public void testDivides(){
        assertEquals(new Rational(1, 2).divides(new Rational(5, 3)), new Rational( 3, 10));
        assertEquals(new Rational(1, 2).divides(new Rational(3, 7)), new Rational( 7, 6));
        assertEquals(new Rational(1, -Integer.MIN_VALUE).divides(new Rational(1, Integer.MIN_VALUE)), new Rational( -1, 1));
    }

    @Test(expected = IllegalArgumentToSquareRootException.class)
    public void testIllegalArgumentToSquareRootException() throws IllegalArgumentToSquareRootException{
        Rational s = new Rational( -1, 3 );
        Rational sRoot = null;
        try {
            sRoot = s.root();
        } catch (IllegalArgumentToSquareRootException e) {
            e.printStackTrace();
        }
    }

    public void testTimes(){
        assertEquals(new Rational(1, 2).times(new Rational(5, 3)), new Rational( 5, 6));
        assertEquals(new Rational(1, 2).times(new Rational(3, 7)), new Rational( 3, 14));
        assertEquals(new Rational( 1, Integer.MAX_VALUE )
                        .times(new Rational( 1, 2 ))
                        .times(new Rational( 2, 1 )), 
                     new Rational(1, Integer.MAX_VALUE ));
        //assertEquals(new Rational( 1, Integer.MAX_VALUE ).times(new Rational( 1, 2 )), new Rational( 1, Integer.MAX_VALUE ).divides(new Rational( 2, 1 )));     
    }

    public void testNegativeTimes(){
        assertEquals(new Rational(1, -2).times(new Rational(5, 3)), new Rational( -5, 6));
        assertEquals(new Rational(1, 2).times(new Rational(5, -3)), new Rational( -5, 6));
        assertEquals(new Rational(1, -2).times(new Rational(-5, 3)), new Rational(  5, 6));
        assertEquals(new Rational(1, -2).times(new Rational(-5, 3)), new Rational(  5, 6));

        assertEquals(new Rational(1, -2).times(new Rational(-5, 3)), new Rational(  5, 6));
    }

    public void testPlus() {
        assertEquals(new Rational(1, 3).plus(new Rational(1, 2)), new Rational( 5, 6));
        assertEquals(new Rational(2, 3).plus(new Rational(4, 7)), new Rational(26, 21));
        assertEquals(new Rational(1, 150).plus(new Rational(1, 300)), new Rational(1, 100));
        assertEquals(new Rational(33, 7).plus(new Rational(7, 33)), new Rational(33*33+49, 231));
        assertEquals(new Rational(3, 11).plus(new Rational(20, 33)), new Rational(29, 33));
        //assertEquals(new Rational(0, 11).plus(new Rational(0, 33)), new Rational(0, 11));

        assertEquals(new Rational(1, 2).plus(new Rational(1, -3)), new Rational(1, 6));
    }

    public void testMinus() {
		System.out.println("-MAX: "+(-Integer.MAX_VALUE));
		System.out.println("MIN: "+(Integer.MIN_VALUE));
        assertEquals( new Rational(Integer.MIN_VALUE, 2)
						.minus(new Rational(Integer.MIN_VALUE, 1))
					, new Rational(-Integer.MIN_VALUE,2) );
    }
	
    public void testNegativeMinus() {
        assertEquals( new Rational(3, 2).minus(new Rational(-7, 2))
					, new Rational(10, 2) );
    }
	
    public void testIsLessThan(){
        assertTrue(new Rational(1,Integer.MAX_VALUE).isLessThan( new Rational(20,1)) );
	}

    public void testAbs(){
		System.out.println("\nTEST ABS()");
		System.out.println("MIN: "+(Integer.MIN_VALUE));
		System.out.println("-MIN: "+(-1*Integer.MIN_VALUE));
		//Integer.MIN_VALUE is negative
        assertFalse(	new Rational(Integer.MIN_VALUE,5).abs().equals( 
						new Rational(Integer.MIN_VALUE,5)) );
    }
	
	
    public void testNegativePlus() {
        assertEquals(new Rational(1,-3).plus(new Rational(1,-2)), new Rational(5,-6));
        assertEquals(new Rational(-1,3).plus(new Rational(-1,2)), new Rational(5,-6));
        assertEquals(new Rational(1,-3).plus(new Rational(-1,2)), new Rational(5,-6));
    }

    // Test for nonequality
    public void testNonEquality() {
        assertFalse(new Rational(2,3).equals(new Rational(1,3)));
        assertFalse(new Rational(-2,3).equals(new Rational(4,9)));
        assertFalse(new Rational(Integer.MIN_VALUE,1).equals(new Rational(Integer.MIN_VALUE,-1)));
         
    }

    public void testAccessors() {
    	assertEquals(new Rational(3,9).numerator(), 1);
    	assertEquals(new Rational(0,-3).numerator(), 0);
    	assertEquals(new Rational(2,3).numerator(), 2);
    	assertEquals(new Rational(2,3).denominator(), 3);
    }

    public void testRoot() {
        Rational s = new Rational( 1, 4 );
        Rational sRoot = null;
        try {
            sRoot = s.root();
        } catch (IllegalArgumentToSquareRootException e) {
            e.printStackTrace();
        }

        assertTrue( sRoot.isLessThan( HALF.plus( Rational.getTolerance() ) ) 
                        && HALF.minus( Rational.getTolerance() ).isLessThan( sRoot ) );
    }

    public void testRoot2() {
        Rational s = new Rational( 1, 9 );
        Rational sRoot = null;
        try {
            sRoot = s.root();
        } catch (IllegalArgumentToSquareRootException e) {
            e.printStackTrace();
        }

        assertEquals(sRoot.times(sRoot), new Rational( 1, 9));
    }

    public static void main(String args[]) {
        String[] testCaseName = 
            { RationalTest.class.getName() };
        // junit.swingui.TestRunner.main(testCaseName);
        junit.textui.TestRunner.main(testCaseName);
    }
}