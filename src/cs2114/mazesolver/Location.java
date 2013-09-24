package cs2114.mazesolver;
/**
 * // -------------------------------------------------------------------------
/**
 *  represents the (x, y) coordinates of a location inside of the maze
 *
 *  @author dmoore09
 *  @version Feb 26, 2013
 */
public class Location
    implements ILocation
{
    //represent the (x, y) coordinate pair of the location respectively
    private int x;
    private int y;


    /**
     * constructor to initialize fields
     * @param xCoord is the x coordinate of the location
     * @param yCoord is the y coordinate of the location
     */
    public Location(int xCoord, int yCoord)
    {
        x = xCoord;
        y = yCoord;
    }

    /**
     * create new location one x coordinate to the east
     * @return location of (x + 1, y)
     */
    @Override
    public ILocation east()
    {
        Location eastLocation = new Location(x + 1, y);
        return eastLocation;
    }


    /**
     * create new location one y coordinate to the north
     * @return location of (x, y - 1)
     */
    @Override
    public ILocation north()
    {
        Location northLocation = new Location(x, y - 1);
        return northLocation;
    }


    /**
     * create new location one y coordinate to the south
     * @return location of (x, y + 1)
     */
    @Override
    public ILocation south()
    {
        Location northLocation = new Location(x, y + 1);
        return northLocation;
    }


    /**
     *  create new location one y coordinate to the west
     *  @return location of (x - 1, y)
     */
    @Override
    public ILocation west()
    {
        Location westLocation = new Location(x - 1, y);
        return westLocation;
    }


    /**
     * get x coordinate
     * @return x location
     */
    @Override
    public int x()
    {
        return x;
    }

    /**
     * get y coordinate
     * @return y location
     */
    @Override
    public int y()
    {
        return y;
    }

    /**
     * check to see if two locations equal each other
     * @return true if same (x, y), false if not same
     * @param location is the location to be changed
     */
    public boolean equals(Object location)
    {

        if (this.toString().equals(location.toString()) && location
            instanceof Location)
        {
            return true;
        }

        return false;
    }

    /**
     * return x and y coordinates
     * @return "(x, y)"
     */
    public String toString()
    {
        return "(" + this.x() + ", " + this.y() + ")";
    }

}
