package player;

import automove.cell_generator.CellCoordinateGenerator;
import automove.checker_generator.CheckerCoordinateGenerator;
import board.Board;
import checks.CheckOnCorrectMove;
import color.COLOR;
import coordinates.Coordinate;
import moving.MovingLogic;
import statistics.Statistics;

import java.util.ArrayList;

public class AutoPlayer extends AbstractPlayer {

    private ArrayList<Coordinate> cellsForEating;
    private Statistics statistics;
    private COLOR color;

    //auto player playing black checkers
    public AutoPlayer(Board board, Statistics statistics, COLOR color) {
        this.board = board;
        this.statistics = statistics;
        this.color = color;
    }


    @Override
    public void move() {
        //get cell and checker coordinates
        enterData();

        for (int i = 0; i < cellsForEating.size(); i++) {
            if (i == 0) {
                checkOnMove = new CheckOnCorrectMove(checkerCoordinate, cellsForEating.get(0), board);
                MovingLogic movingLogic = new MovingLogic(checkerCoordinate, cellsForEating.get(0), board, checkOnMove, statistics);
                movingLogic.move();
            } else {
                checkOnMove = new CheckOnCorrectMove(cellsForEating.get(i - 1), cellsForEating.get(i), board);
                MovingLogic movingLogic = new MovingLogic(cellsForEating.get(i - 1), cellsForEating.get(i), board, checkOnMove, statistics);
                movingLogic.move();
            }
        }
    }

    @Override
    public void enterData() {
        //generate checkerCoordinate
        CheckerCoordinateGenerator checkerGen = new CheckerCoordinateGenerator(board, color, statistics);
        checkerCoordinate = checkerGen.getMovingCheckerCoordinate();

        //generate cellCoordinate or cellCoordinates(for eating some checkers)
        CellCoordinateGenerator cellGen = new CellCoordinateGenerator(board, checkerCoordinate, color);
        cellsForEating = cellGen.getCellsForMoveCoordinates();
    }
}