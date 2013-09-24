package cs2114.mazesolver;

import student.TestCase;
/**
 * // -------------------------------------------------------------------------
/**
 *  test maze Methods
 *
 *  @author dmoore09
 *  @version Mar 5, 2013
 */
public class MazeTest
    extends TestCase
{
    private Location testLoc;
    private Maze maze;

    /**
     * initialize fields
     */
    public void setUp()
    {
        maze = new Maze(2);
        testLoc = new Location(0, 0);
    }

    /**
     * make sure it returns specified cell
     */
    public void testGetCell()
    {
        assertEquals(MazeCell.UNEXPLORED, maze.getCell(testLoc));

    }

    /**
     * make sure it returns null
     */
    public void testGetCellBounds()
    {
        Location outBounds = new Location(-1, -1);
        assertEquals(MazeCell.INVALID_CELL, maze.getCell(outBounds));

    }

    /**
     * try and set the cell at start location
     */
    public void testSetCellStart()
    {
        maze.setCell(testLoc, MazeCell.WALL);

        assertEquals(MazeCell.UNEXPLORED, maze.getCell(testLoc));
    }

    /**
     * test set cell on a valid cell
     */
    public void testSetCellNotStart()
    {
        Location valid = new Location(1, 0);
        maze.setCell(valid, MazeCell.WALL);

        assertEquals(MazeCell.WALL, maze.getCell(valid));
    }

    /**
     * make sure the the goal is moved to the right location
     */
    public void testSetGoalLocationNoWall()
    {
        Location valid = new Location(1, 0);
        maze.setGoalLocation(valid);
        Location newGoal = (Location)maze.getGoalLocation();
        assertEquals(1, newGoal.x());
        assertEquals(0, newGoal.y());
    }

    /**
     * make sure the the goal is moved to the right location when there is a
     * wall at the goal
     */
    public void testSetGoalLocationWall()
    {
        Location valid = new Location(1, 0);
        maze.setCell(valid, MazeCell.WALL);
        maze.setGoalLocation(valid);
        Location newGoal = (Location)maze.getGoalLocation();
        assertEquals(1, newGoal.x());
        assertEquals(0, newGoal.y());
    }

    /**
     * make sure start location is moved
     */
    public void testSetStartLocation()
    {
        Location valid = new Location(1, 0);
        maze.setCell(valid, MazeCell.WALL);
        maze.setStartLocation(valid);
        Location newStart = (Location)maze.getStartLocation();
        assertEquals(1, newStart.x());
        assertEquals(0, newStart.y());
    }

    /**
     * test size method to make sure correct size is returned
     */
    public void testSize()
    {
        assertEquals(2, maze.size());
    }

    /**
     * test solution with 2*2 maze and one wall at (1, 0)
     */
    public void testSolve1()
    {
        Location valid = new Location(1, 0);
        maze.setCell(valid, MazeCell.WALL);

        assertEquals("(0, 0) (0, 1) (1, 1) ", maze.solve());
    }

    /**
     * test solution with 2*2 maze and one wall at (0, 1)
     */
    public void testSolve2()
    {
        Location valid = new Location(0, 1);
        maze.setCell(valid, MazeCell.WALL);

        assertEquals("(0, 0) (1, 0) (1, 1) ", maze.solve());
    }

    /**
     * test solution when no path is possible
     */
    public void testSolve3()
    {
        Location wall1 = new Location(0, 1);
        Location wall2 = new Location(1, 0);
        Location goal = new Location(0, 0);
        Location start = new Location(1, 1);
        maze.setStartLocation(start);
        maze.setGoalLocation(goal);
        maze.setCell(wall1, MazeCell.WALL);
        maze.setCell(wall2, MazeCell.WALL);


        assertNull(maze.solve());
    }

    /**
     * test solution with 2*2 maze and one wall at (0, 1) and the start and
     * goal reversed
     */
    public void testSolve4()
    {
        Location wall1 = new Location(0, 1);
        Location wall2 = new Location(1, 0);
        Location goal = new Location(0, 0);
        Location start = new Location(1, 1);
        maze.setStartLocation(start);
        maze.setGoalLocation(goal);
        maze.setCell(wall1, MazeCell.WALL);
        maze.setCell(wall2, MazeCell.WALL);


        assertNull(maze.solve());
    }

    /**
     * test solve on larger maze
     */
    public void testSolve5()
    {
        Maze bigMaze = new Maze(3);
        Location wall1 = new Location(1, 0);
        Location wall2 = new Location(1, 1);
        bigMaze.setCell(wall1, MazeCell.WALL);
        bigMaze.setCell(wall2, MazeCell.CURRENT_PATH);

        assertEquals("(0, 0) (0, 1) (0, 2) (1, 2) (2, 2) ", bigMaze.solve());
    }

    /**
     * test solve on different starting location
     */
    public void testSolve6()
    {
        Maze bigMaze = new Maze(3);
        Location start = new Location(2, 0);
        Location wall1 = new Location(2, 1);
        bigMaze.setStartLocation(start);
        bigMaze.setCell(wall1, MazeCell.WALL);

        assertEquals("(2, 0) (1, 0) (1, 1) (1, 2) (2, 2) ", bigMaze.solve());
    }



}
