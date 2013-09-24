package cs2114.mazesolver;

import android.widget.TextView;
import sofia.graphics.OvalShape;
import sofia.graphics.Color;
import sofia.graphics.RectangleShape;
import sofia.app.ShapeScreen;

// -------------------------------------------------------------------------
/**
 * the screen that represents the maze. Green cells are walls, black cells are
 * empty, yellow oval is start, red oval is goal, blue cells are part of the
 * solution
 *
 * @author dmoore09
 * @version 2012.24.03
 */
public class MazeSolverScreen
    extends ShapeScreen
{
    // ~ Fields ................................................................
    // maze to call operations on the model
    private Maze      maze;
    private float     mazeSize;

    // Booleans to represent what operation will be done
    private boolean   draw;
    private boolean   erase;
    private boolean   start;

    // ovals to represent start and goal locations
    private OvalShape startMarker;
    private OvalShape goalMarker;

    //field for text view
    private TextView infoLabel;


    // ~ Public methods.........................................................

    /**
     * create a maze for data manipulation and fill the shapeView with tiles
     */
    public void initialize()
    {
        // initialize new maze
        maze = new Maze(7);

        // find side length of the cell grid
        mazeSize = Math.min(this.getWidth(), this.getHeight());
        mazeSize = mazeSize / 7;

        // fill view with cells
        for (int i = 0; i < 7; i++)
        {
            for (int j = 0; j < 7; j++)
            {
                RectangleShape cell =
                    new RectangleShape(mazeSize * i, mazeSize * j, mazeSize
                        * (i + 1), mazeSize * (j + 1));
                cell.setColor(Color.lime);
                cell.setFillColor(Color.black);
                cell.setFilled(true);
                this.add(cell);
            }
        }

        // create and add start and goal markers
        startMarker = new OvalShape(mazeSize / 2, mazeSize / 2, 12);
        startMarker.setFillColor(Color.yellow);
        startMarker.setColor(Color.yellow);
        startMarker.setFilled(true);

        goalMarker =
            new OvalShape(mazeSize * 6 + (mazeSize / 2), mazeSize * 6
                + (mazeSize / 2), 12);
        goalMarker.setFillColor(Color.red);
        goalMarker.setFilled(true);

        this.add(startMarker);
        this.add(goalMarker);
    }


    // ----------------------------------------------------------
    /**
     * for testing purposes
     * @return maze
     */
    public IMaze getMaze()
    {
        return maze;
    }


    /**
     * get pixel locations and decide what to to do to the maze cell/cells at
     * this location when user swipes the screen
     *
     * @param x
     *            position of user touch
     * @param y
     *            position of user touch
     */
    public void onTouchMove(float x, float y)
    {
        this.processTouch(x, y);
    }


    /**
     * get pixel locations and decide what to to do to the maze cell at this
     * location when user taps the screen
     *
     * @param x
     *            position of user touch
     * @param y
     *            position of user touch
     */
    public void onTouchDown(float x, float y)
    {
        this.processTouch(x, y);
    }

    /**
     * process user touch input
     * @param x position of touch
     * @param y position of touch
     */
    public void processTouch(float x, float y)
    {
        //get the location of the cell in relation to the maze
        int xLoc = (int)(x / mazeSize);
        int yLoc = (int)(y / mazeSize);

        // create wall at specified cell
        if (draw)
        {
            // change the cell's color to green
            RectangleShape cell =
                getShapes().locatedAt(x, y).withClass(RectangleShape.class)
                    .front();
            cell.setFillColor(Color.lime);

            // change cell in maze to wall
            Location newWall = new Location(xLoc, yLoc);
            maze.setCell(newWall, MazeCell.WALL);

        }
        else if (erase)
        {
            // change cell's color to black
            RectangleShape cell =
                getShapes().locatedAt(x, y).withClass(RectangleShape.class)
                    .front();
            cell.setFillColor(Color.black);

            // change cell in maze to unexplored
            Location destroyWall = new Location(xLoc, yLoc);
            maze.setCell(destroyWall, MazeCell.UNEXPLORED);

        }
        else if (start)
        {
            //remove old start Marker
            this.remove(startMarker);

            //add new start Marker
            RectangleShape cell =
                getShapes().locatedAt(x, y).withClass(RectangleShape.class)
                    .front();

            OvalShape newStart = new OvalShape(cell.getX(),
                cell.getY(), 12);
            newStart.setFilled(true);
            newStart.setColor(Color.yellow);
            startMarker = newStart;
            this.add(startMarker);

            //set start location of maze
            Location startLoc = new Location(xLoc, yLoc);
            maze.setStartLocation(startLoc);

        }
        else
        {
            //remove old goal Marker
            this.remove(goalMarker);

            //add new goal Marker
            RectangleShape cell =
                getShapes().locatedAt(x, y).withClass(RectangleShape.class)
                    .front();

            OvalShape newGoal = new OvalShape(cell.getX(),
                cell.getY(), 12);
            newGoal.setFilled(true);
            newGoal.setColor(Color.red);
            goalMarker = newGoal;
            this.add(goalMarker);

            //set goal location of maze
            Location goalLoc = new Location(xLoc, yLoc);
            maze.setGoalLocation(goalLoc);
        }
    }


    /**
     * set erase and other fields to false so the user can draw walls
     */
    public void drawWallsClicked()
    {
        draw = true;
        erase = false;
        start = false;
    }


    /**
     * set draw and other fields to false so the user can erase walls
     */
    public void eraseWallsClicked()
    {
        erase = true;
        draw = false;
        start = false;
    }


    /**
     * set start to true so that the user can set the start location, will
     * move current start object
     */
    public void setStartClicked()
    {
        start = true;
        draw = false;
        erase = false;
    }


    /**
     * sets all fields to false so that the user can set the goal location, will
     * move current goal object
     */
    public void setGoalClicked()
    {
        start = false;
        draw = false;
        erase = false;
    }

    /**
     * update the text view with the solution, and change the color of the
     * cells
     */
    public void solveClicked()
    {
        //solve the maze
        String solution = maze.solve();

        //update text field
        if (solution == null)
        {
            infoLabel.setText("No solution was possible");
        }
        else
        {
            infoLabel.setText(solution);
        }


        //change cells
        for (int i = 0; i < 7; i++)
        {
            for (int j = 0; j < 7; j++)
            {
                Location loc = new Location(i, j);
                if (maze.getCell(loc).equals(MazeCell.CURRENT_PATH) ||
                    maze.getCell(loc).equals(MazeCell.FAILED_PATH))
                {
                    //find pixels of cell
                    float x = (i * mazeSize + 1);
                    float y = (j * mazeSize + 1);

                    //change cell color
                    RectangleShape cell = getShapes().locatedAt(x, y)
                        .withClass(RectangleShape.class).front();
                    cell.setFillColor(Color.blue);
                }
            }
        }



    }

}
