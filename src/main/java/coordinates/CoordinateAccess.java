package coordinates;

import board.Board;
import board.Cell;
import board.Checker;

public class CoordinateAccess {
    private Board board;

    public CoordinateAccess(Board board) {
        this.board = board;
    }

    /**
     * Returns a Checker with certain coordinate.
     * If there are no Checker with such coordinate return new Checker().
     */
    public Checker getCheckerByItsCoordinate(Coordinate coordinate) {
        for (int i = 0; i < board.getCheckers().size(); i++) {
            if (board.getCheckers().get(i).getCoordinate().equals(coordinate))
                return board.getCheckers().get(i);
        }
        return new Checker();
    }

    /**
     * Returns a Checker with certain coordinate.
     * If there are no Checker with such coordinates return new Checker().
     */
    public Checker getCheckerByItsCoordinate(int x, int y) {
        for (int i = 0; i < board.getCheckers().size(); i++) {
            if (board.getCheckers().get(i).getCoordinate().equals(new Coordinate(x, y)))
                return board.getCheckers().get(i);
        }
        return new Checker();
    }

    /**
     * Returns true if there are Checker with certain coordinate.
     * If there are no Checker with such coordinates return false.
     */
    public boolean isCheckerOnThisPosition(int x, int y) {
        for (int i = 0; i < board.getCheckers().size(); i++) {
            if (board.getCheckers().get(i).getCoordinate().equals(new Coordinate(x, y)))
                return true;
        }
        return false;
    }

    /**
     * Returns a Cell with certain coordinate.
     * If there are no Cell with such coordinate return new Cell().
     */
    public Cell getCellByItsCoordinate(Coordinate coordinate) {
        for (int i = 0; i < board.getCells().size(); i++) {
            if (board.getCells().get(i).getCoordinate().equals(coordinate))
                return board.getCells().get(i);
        }
        return new Cell();
    }
}
