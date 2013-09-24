package cs2114.mazesolver;

import sofia.graphics.OvalShape;
import sofia.graphics.RectangleShape;
import sofia.graphics.Color;
import android.widget.TextView;
import android.widget.Button;
import sofia.graphics.ShapeView;

//-------------------------------------------------------------------------
/**
*  Test the mazeScreen class to assure the correct changes are made to the data
*  of the maze object and the .xml
*
*  @author  dmoore09
*  @version 2013.27.03
*/
public class MazeSolverScreenTests
    extends student.AndroidTestCase<MazeSolverScreen>
{
    //~ Fields ................................................................

    // References to the widgets that you have in your layout. They
    // will be automatically filled in by the test class before your
    // tests run.
    private ShapeView shapeView;
    private Button drawWalls;
    private Button eraseWalls;
    private Button setStart;
    private Button setGoal;
    private Button solve;
    private TextView infoLabel;

    // This field will store the pixel width/height of a cell in the maze.
    private int cellSize;


    //~ Constructors ..........................................................

    // ----------------------------------------------------------
    /**
     * Test cases that extend AndroidTestCase must have a parameterless
     * constructor that calls super() and passes it the screen/activity class
     * being tested.
     */
    public MazeSolverScreenTests()
    {
        super(MazeSolverScreen.class);
    }


    //~ Public methods ........................................................

    // ----------------------------------------------------------
    /**
     * Initializes the text fixtures.
     */
    public void setUp()
    {
        float viewSize =
            Math.min(getScreen().getWidth(), getScreen().getHeight());
        cellSize = (int)(viewSize / 7);
        // TODO set cellSize to be the viewSize divided by the size of your
        // maze
        //cellSize = ...

        // TODO Add any other setup code that you need.
    }


    // TODO Add your test methods here.


    //~ Private methods .......................................................

    // ----------------------------------------------------------
    /**
     * Simulates touching down on the middle of the specified cell in the maze.
     */
    private void touchDownCell(int x, int y)
    {
        touchDown(shapeView, (x + 0.5f) * cellSize, (y + 0.5f) * cellSize);
    }


    // ----------------------------------------------------------
    /**
     * Simulates moving the finger instantaneously to the middle of the
     * specified cell in the maze.
     */
    private void touchMoveCell(int x, int y)
    {
        touchMove((x + 0.5f) * cellSize, (y + 0.5f) * cellSize);
    }


    // ----------------------------------------------------------

    /**
     * test draw walls clicked. A wall should be drawn on a cell, and the
     * cell in the maze should be changed to MazeCell.WALL
     */
    public void testDrawWallsClicked()
    {
        click(drawWalls);
        touchDownCell(2, 2);
        IMaze maze = getScreen().getMaze();
        Location loc = new Location(2, 2);

        assertEquals(MazeCell.WALL, maze.getCell(loc));
        assertEquals(Color.lime, getScreen().getShapes().locatedAt((2 + 0.5f) *
            cellSize, (2 + 0.5f) * cellSize).withClass(RectangleShape.class)
            .front().getColor());
    }

    /**
     * test erase walls clicked. A wall should be erased on a cell, and the
     * cell in the maze should be changed to MazeCell.UNEXPLORED
     */
    public void testEraseWallsClicked()
    {
        //draw a wall
        click(drawWalls);
        touchDownCell(2, 2);
        touchUp();

        //erase a wall
        click(eraseWalls);
        touchDownCell(2, 2);

        IMaze maze = getScreen().getMaze();
        Location loc = new Location(2, 2);

        assertEquals(MazeCell.UNEXPLORED, maze.getCell(loc));
        assertEquals(Color.black, getScreen().getShapes().locatedAt((2 + 0.5f) *
            cellSize, (2 + 0.5f) * cellSize).withClass(RectangleShape.class)
            .front().getFillColor());
    }

    /**
     * test setStartCLicked. Should move the location of the start oval from
     * its initial position
     */
    public void testSetStartClicked()
    {
        //draw a wall
        click(setStart);
        touchDownCell(2, 2);

        IMaze maze = getScreen().getMaze();
        Location loc = new Location(2, 2);

        assertEquals(loc, maze.getStartLocation());
        assertEquals(Color.yellow, getScreen().getShapes().locatedAt((2 + 0.5f)
            * cellSize, (2 + 0.5f) * cellSize).withClass(OvalShape.class)
            .front().getColor());
    }

    /**
     * test setStartCLicked. Should move the location of the start oval from
     * its initial position
     */
    public void testSetGoalClicked()
    {
        //draw a wall
        click(setGoal);
        touchDownCell(2, 2);
        touchMoveCell(2, 2);
        touchUp();

        IMaze maze = getScreen().getMaze();
        Location loc = new Location(2, 2);

        assertEquals(loc, maze.getGoalLocation());
        assertEquals(Color.red, getScreen().getShapes().locatedAt((2 + 0.5f)
            * cellSize, (2 + 0.5f) * cellSize).withClass(OvalShape.class)
            .front().getColor());
    }

    /**
     * test solvedClicked when a solution is possible
     */
    public void testSolvedClickedSol()
    {
        click(solve);

        assertEquals(Color.blue, getScreen().getShapes().locatedAt((2 + 0.5f)
            * cellSize, (0 + 0.5f) * cellSize).withClass(RectangleShape.class)
            .front().getFillColor());
        assertEquals("(0, 0) (1, 0) (2, 0) (3, 0) (4, 0) (5, 0) (6, 0) " +
        		"(6, 1) (6, 2) (6, 3) (6, 4) (6, 5) (6, 6) ",
        		infoLabel.getText());
    }

    /**
     * test solvedClicked when a solution is not possible
     */
    public void testSolvedClickedNoSol()
    {
        click(drawWalls);
        touchDownCell(1, 0);
        touchUp();
        touchDownCell(1, 1);
        touchUp();
        touchDownCell(0, 1);
        touchUp();

        click(solve);

        assertEquals(Color.blue, getScreen().getShapes().locatedAt((0 + 0.5f)
            * cellSize, (0 + 0.5f) * cellSize).withClass(RectangleShape.class)
            .front().getFillColor());
        assertEquals("No solution was possible", infoLabel.getText());
    }


}
