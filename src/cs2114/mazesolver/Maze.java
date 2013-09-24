package cs2114.mazesolver;

import java.util.Stack;

/**
 * // -------------------------------------------------------------------------
/**
 *  The maze in the game represented by a 2-D array
 *
 *  @author dmoore09
 *  @version Feb 28, 2013
 */
public class Maze
    implements IMaze
{
    private MazeCell[][] mazeGame;
    private int size;

    //locations to represent the goal and start
    private Location start;
    private Location goal;

    //stack to be used to track the solution
    private Stack<Location> solution;
    private Stack<Location> finalSolution;




    /**
     * create the maze by specifying it's size. Sets start and end locations
     * in the cell to (0, 0) and (sideLength - 1), (sideLength - 1). Sets all
     * cells to equal unexplored
     * @param sideLength the length of every side
     */
    public Maze(int sideLength)
    {

        mazeGame = new MazeCell[sideLength][sideLength];

        solution = new Stack<Location>();
        finalSolution = new Stack<Location>();

        start = new Location(0, 0);
        goal = new Location(sideLength - 1, sideLength - 1);



        //fill maze with unexplored cells
        for (int i = 0; i < sideLength; i++)
        {
            for (int j = 0; j < sideLength; j++)
            {
                mazeGame[i][j] = MazeCell.UNEXPLORED;
            }
        }

        size = sideLength;
    }


    /**
     * get the cell at the specified location
     * @param loc the location of the cell
     * @return MazeCell at location
     */
    @Override
    public MazeCell getCell(ILocation loc)
    {
        //get cell at specified location
        int xLoc = loc.x();
        int yLoc = loc.y();

        /**
         * make sure cell is not out of bounds
         */
        if ( (xLoc < 0) || (xLoc > size - 1) || (yLoc < 0) || (yLoc > size - 1))
        {
            return MazeCell.INVALID_CELL;
        }

        MazeCell desiredCell = mazeGame[xLoc][yLoc];

        return desiredCell;
    }

    /**
     * @return the end of the maze
     */
    @Override
    public ILocation getGoalLocation()
    {
        return goal;
    }

    /**
     * @return the start location of the maze
     */
    @Override
    public ILocation getStartLocation()
    {
        return start;
    }


    /**
     * sets cell type wont do anything if cell is start, goal, or wall
     * @param loc location to be set
     * @param type what maze cell should be set to
     */
    @Override
    public void setCell(ILocation loc, MazeCell type)
    {
        int xLoc = loc.x();
        int yLoc = loc.y();

        //if location is start, goal, or a wall don't do anything
        if (type.equals(MazeCell.WALL))
        {
            if (!(xLoc == start.x() && yLoc == start.y()) &&
                !(xLoc == goal.x() && yLoc == goal.y()))
            {
                //set cell to a wall
                mazeGame[xLoc][yLoc] = type;
            }
        }
        else
        {
            //set cell to a wall
            mazeGame[xLoc][yLoc] = type;
        }

    }


    /**
     * set the start location of the maze
     * @param loc the new goal location
     */
    @Override
    public void setGoalLocation(ILocation loc)
    {
        int xLoc = loc.x();
        int yLoc = loc.y();

        MazeCell newStart = mazeGame[xLoc][yLoc];

        if (newStart.equals(MazeCell.WALL))
        {
            mazeGame[xLoc][yLoc] = MazeCell.UNEXPLORED;
        }

        goal = (Location)loc;

    }


    /**
     * set the start location of the maze
     * @param loc the new start location
     */
    @Override
    public void setStartLocation(ILocation loc)
    {
        int xLoc = loc.x();
        int yLoc = loc.y();

        MazeCell newStart = mazeGame[xLoc][yLoc];

        if (newStart.equals(MazeCell.WALL))
        {
            mazeGame[xLoc][yLoc] = MazeCell.UNEXPLORED;
        }

        mazeGame[xLoc][yLoc] = MazeCell.UNEXPLORED;

        start = (Location)loc;

    }


    /**
     * the number of cells in the maze
     * @return the length of one size of the maze
     */
    @Override
    public int size()
    {
        return size;
    }


    /**
     * will return the steps that need to be taken to solve the mazw
     * @return the solution in the form of a string
     */
    @Override
    public String solve()
    {


        solution.push(start);

        while (!solution.empty())
        {
            //get current location
            Location current = solution.peek();
            this.setCell(start, MazeCell.CURRENT_PATH);


            //check to see if location is the goal
            if (current.x() == goal.x() && current.y() == goal.y())
            {
                int size1 = solution.size();
                //put locations onto another stack
                for (int i = 0; i < size1; i++)
                {
                    finalSolution.push(solution.pop());
                }

                String solved = "";
                int size2 = finalSolution.size();
                //pop off and print out
                for (int i = 0; i < size2; i++)
                {
                    Location loc = finalSolution.pop();
                    solved = solved + loc.toString() + " ";
                }

                return solved;
            }

            //get all surrounding locations
            Location east = (Location)current.east();
            Location south = (Location)current.south();
            Location west = (Location)current.west();
            Location north = (Location)current.north();

            //find next unexplored cell
            if (this.getCell(east).equals(MazeCell.UNEXPLORED))
            {
                //push next location on and set cell to current
                solution.push(east);
                this.setCell(east, MazeCell.CURRENT_PATH);
            }
            else if (this.getCell(south).equals(MazeCell.UNEXPLORED))
            {
                //push next location on and set cell to current
                solution.push(south);
                this.setCell(south, MazeCell.CURRENT_PATH);
            }
            else if (this.getCell(west).equals(MazeCell.UNEXPLORED))
            {
                //push next location on and set cell to current
                solution.push(west);
                this.setCell(west, MazeCell.CURRENT_PATH);
            }
            else if (this.getCell(north).equals(MazeCell.UNEXPLORED))
            {
                //push next location on and set cell to current
                solution.push(north);
                this.setCell(north, MazeCell.CURRENT_PATH);
            }
            else
            {
                this.setCell(current, MazeCell.FAILED_PATH);
                solution.pop();
            }
        }

        return null;
    }

}
