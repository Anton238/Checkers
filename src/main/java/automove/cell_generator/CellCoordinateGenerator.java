package automove.cell_generator;

import board.Board;
import checks.ChecksForGenerator;
import color.COLOR;
import coordinates.CoordinateAccess;
import coordinates.Coordinate;

import java.util.ArrayList;

public class CellCoordinateGenerator {
    private Coordinate checkerCoordinate;
    private ArrayList<Coordinate> someCoordinatesForUsual;
    private ArrayList<Coordinate> someCoordinatesForQueen;
    private Board board;
    private CoordinateAccess coordAccess;
    private COLOR color;

    public CellCoordinateGenerator(Board board, Coordinate checkerCoordinate, COLOR color) {
        this.checkerCoordinate = checkerCoordinate;
        someCoordinatesForUsual = new ArrayList<>();
        someCoordinatesForQueen = new ArrayList<>();
        this.board = board;
        coordAccess = new CoordinateAccess(board);
        this.color = color;
    }

    /**
     * Return cellCoordinate for move
     */
    public ArrayList<Coordinate> getCellsForMoveCoordinates() {
        initializeCandidates();

        if (coordAccess.getCheckerByItsCoordinate(checkerCoordinate).isQueen())
            return someCoordinatesForQueen;

        else
            return someCoordinatesForUsual;
    }


    /**
     * Find and add cell candidates to list
     */
    private void initializeCandidates() {
        if (coordAccess.getCheckerByItsCoordinate(checkerCoordinate).isQueen()) {
            CellGeneratorForQueen cellQueenGen = new CellGeneratorForQueen(board, checkerCoordinate, color);
            someCoordinatesForQueen = cellQueenGen.coordinatesForEat();

        } else {
            CellGeneratorForUsual cellUsualGen = new CellGeneratorForUsual(board, checkerCoordinate, color);
            someCoordinatesForUsual = cellUsualGen.coordinatesForEat();
        }
    }
}
