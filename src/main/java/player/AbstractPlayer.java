package player;

import board.Board;
import checks.CheckOnCorrectMove;
import coordinates.Coordinate;
import statistics.Statistics;

public abstract class AbstractPlayer {

    protected CheckOnCorrectMove checkOnMove;
    protected Coordinate checkerCoordinate;
    protected Coordinate cellCoordinate;
    protected Board board;
    protected Statistics statistics;

    /**
     * Take a move
     */
    public abstract void move();

    /**
     * Get checkerCoordinate(checker coordinate that will move) and cellCoordinate(coordinate on which the checker will move)
     */
    public abstract void enterData();
}
