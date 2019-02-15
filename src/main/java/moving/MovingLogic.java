package moving;

import board.Board;
import checks.CheckOnCorrectMove;
import checks.CheckOnQueen;
import coordinates.Coordinate;
import coordinates.CoordinateAccess;
import logger.ApplicationLogger;
import statistics.Statistics;

public class MovingLogic {
    private Coordinate checkerCoordinate;
    private Coordinate cellCoordinate;
    private Board board;
    private CoordinateAccess coordinateAccess;
    private CheckOnCorrectMove checkMove;
    private Statistics stat;
    private int numHelpConvertCoordinate;


    public MovingLogic(Coordinate checkerCoordinate, Coordinate cellCoordinate, Board board, CheckOnCorrectMove checkMove, Statistics stat) {
        this.checkerCoordinate = checkerCoordinate;
        this.cellCoordinate = cellCoordinate;
        this.board = board;
        coordinateAccess = new CoordinateAccess(board);
        this.checkMove = checkMove;
        this.stat = stat;
        numHelpConvertCoordinate = 96;
    }

    public void move() {
        //check if we can eat the checker. if yes, eat
        if (checkMove.checkCanEatChecker()) {
            EatingLogic eatingLogic = new EatingLogic(this.board, checkerCoordinate, cellCoordinate, stat);
            eatingLogic.eatChecker();
        }

        //show statistics about moving
        ApplicationLogger.logger.info(showLittleStatistics());

        //freeing the place where the checker stood
        coordinateAccess.getCellByItsCoordinate(coordinateAccess.getCheckerByItsCoordinate(checkerCoordinate).getCoordinate()).setBusy(false);

        //move the checker to the selected position
        coordinateAccess.getCheckerByItsCoordinate(checkerCoordinate).setCoordinate(cellCoordinate);

        //makes the cell busy
        coordinateAccess.getCellByItsCoordinate(cellCoordinate).setBusy(true);

        //check if checker become a queen after moving and make checker queen if necessary
        CheckOnQueen checkOnQueen = new CheckOnQueen(board);
        checkOnQueen.checkAndTakeQueen();
    }

    /**
     * Show statistics about moving
     */
    private String showLittleStatistics() {
        return "\n" + coordinateAccess.getCheckerByItsCoordinate(checkerCoordinate).getColor()
                + " checker moved from '" + (char) (checkerCoordinate.getX() + numHelpConvertCoordinate) + checkerCoordinate.getY()
                + "' coordinate to '" + (char) (cellCoordinate.getX() + numHelpConvertCoordinate) + cellCoordinate.getY()
                + "' coordinate";
    }
}
