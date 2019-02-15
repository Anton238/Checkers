package moving;

import board.Board;
import board.Checker;
import coordinates.Coordinate;
import coordinates.CoordinateAccess;
import logger.ApplicationLogger;
import statistics.Statistics;

import java.util.List;

public class EatingLogic {
    private Board board;
    private CoordinateAccess coordAccess;
    private Coordinate checkerCoordinate;
    private Coordinate cellCoordinate;
    private Statistics statistics;
    //used to show letter part of coordinate using ASCII
    private int numHelpToShowAteCoord;

    public EatingLogic(Board board, Coordinate checkerCoordinate, Coordinate cellCoordinate, Statistics statistics) {
        this.board = board;
        coordAccess = new CoordinateAccess(this.board);
        this.checkerCoordinate = checkerCoordinate;
        this.cellCoordinate = cellCoordinate;
        this.statistics = statistics;
        numHelpToShowAteCoord = 96;
    }

    public EatingLogic(Board board, Coordinate checkerCoordinate, Coordinate cellCoordinate) {
        this.board = board;
        coordAccess = new CoordinateAccess(this.board);
        this.checkerCoordinate = checkerCoordinate;
        this.cellCoordinate = cellCoordinate;
    }

    /**
     * Delete ate checker from checkers list and free the cell
     */
    public void eatChecker() {

        cleanCell();

        showAteChecker();

        deleteAteChecker();

        //show statistics about ate checkers
        statistics.showStatistics();
    }

    /**
     * Output moving checker coordinate and ate checker coordinate
     */
    public void showAteChecker() {
        ApplicationLogger.logger.info("\nAte checker with coordinate "
                + (char) (getAteCheckerPosition().getX() + numHelpToShowAteCoord) + getAteCheckerPosition().getY());

    }

    /**
     * Delete ate checker from checkers list
     */
    private void deleteAteChecker() {
        for (int i = 0; i < getCheckers().size(); i++)
            //find needing Checker
            if (getAteCheckerPosition().equals(getCheckers().get(i).getCoordinate())) {
                statistics.addAteChecker(getCheckers().get(i));//add ate checker to statistics
                getCheckers().remove(i);
                break;
            }
    }

    /**
     * Clean the cell from ate checker. Make isBusy = false
     */
    private void cleanCell() {
        coordAccess.getCellByItsCoordinate(getAteCheckerPosition()).setBusy(false);
    }

    /**
     * Return ate checker position
     */
    private Coordinate getAteCheckerPosition() {
        if (checkOnRightPosition(1, -1))
            return new Coordinate(cellCoordinate.getX() + 1, cellCoordinate.getY() - 1);

        else if (checkOnRightPosition(-1, 1))
            return new Coordinate(cellCoordinate.getX() - 1, cellCoordinate.getY() + 1);

        else if (checkOnRightPosition(-1, -1))
            return new Coordinate(cellCoordinate.getX() - 1, cellCoordinate.getY() - 1);

        else if (checkOnRightPosition(1, 1))
            return new Coordinate(cellCoordinate.getX() + 1, cellCoordinate.getY() + 1);

        else
            return new Coordinate(0, 0);
    }

    /**
     * Check really cell and checker coordinates are right
     */
    private boolean checkOnRightPosition(int xMove, int yMove) {
        return coordAccess.isCheckerOnThisPosition(cellCoordinate.getX() + xMove, cellCoordinate.getY() + yMove)

                && coordAccess.getCheckerByItsCoordinate(checkerCoordinate).getColor()
                != coordAccess.getCheckerByItsCoordinate(cellCoordinate.getX() + xMove, cellCoordinate.getY() + yMove).getColor()

                && checkOnRightPositionForOne(xMove, yMove);
    }

    /**
     * Check really cell and checker coordinates are situated diagonally and is way free
     */
    private boolean checkOnRightPositionForOne(int x, int y) {
        int max = 8;//max num of cells between queen and checker
        int min = -8;

        if (!coordAccess.getCheckerByItsCoordinate(checkerCoordinate).isQueen())
            return cellCoordinate.getX() == checkerCoordinate.getX() - x * 2
                    && cellCoordinate.getY() == checkerCoordinate.getY() - y * 2;

        else {
            while (x < max && x > min) {
                if (cellCoordinate.getX() == checkerCoordinate.getX() - x
                        && cellCoordinate.getY() == checkerCoordinate.getY() - y)
                    return true;


                x = x > 0 ? ++x : --x;
                y = y > 0 ? ++y : --y;
            }
        }
        return false;
    }

    /**
     * Need to once again not to write board.getCheckers
     */
    private List<Checker> getCheckers() {
        return board.getCheckers();
    }
}
