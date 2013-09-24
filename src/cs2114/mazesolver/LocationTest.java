package cs2114.mazesolver;

import student.TestCase;
/**
 * // -------------------------------------------------------------------------
/**
 *  Test Location Methods
 *
 *  @author dmoore09
 *  @version Feb 27, 2013
 */
public class LocationTest
    extends TestCase
{
    //location to be used for tests
    private Location tester;

    /**
     * set up initial conditions before every test
     */
    public void setUp()
    {
        tester = new Location(0, 0);
    }

    /**
     * test x() method make sure correct x position is returned
     */
    public void testX()
    {
        assertEquals(0, tester.x());
    }

    /**
     * test y() method make sure correct y position is returned
     */
    public void testY()
    {
        assertEquals(0, tester.y());
    }

    /**
     * test east to make sure new location is at x + 1
     */
    public void testEast()
    {
        Location east = (Location)tester.east();

        assertEquals(1, east.x());
    }

    /**
     * test east to make sure new location is at x - 1
     */
    public void testWest()
    {
        Location west = (Location)tester.west();

        assertEquals(-1, west.x());
    }

    /**
     * test east to make sure new location is at y + 1
     */
    public void testSouth()
    {
        Location south = (Location)tester.south();

        assertEquals(1, south.y());
    }

    /**
     * test east to make sure new location is at y - 1
     */
    public void testNorth()
    {
        Location north = (Location)tester.north();

        assertEquals(-1, north.y());
    }

    /**
     * makes sure two locations can equal each other
     */
    public void testEquals()
    {
        Location tester1 = new Location(0, 0);
        assertEquals(tester1, tester);
    }

}
