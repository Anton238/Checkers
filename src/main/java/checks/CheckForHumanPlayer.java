package checks;

import board.Board;
import color.COLOR;
import coordinates.Coordinate;
import coordinates.CoordinateAccess;
import logger.ApplicationLogger;

public class CheckForHumanPlayer {
    private CheckOnCorrectQueenMove checkQueen;
    private CheckOnCorrectMove checkOnMove;
    private CoordinateAccess coordAccess;
    private Coordinate checkerCoordinate;
    private Coordinate cellCoordinate;
    private Board board;

    public CheckForHumanPlayer(Coordinate checkerCoordinate, Coordinate cellCoordinate, CheckOnCorrectMove checkOnMove, Board board) {
        this.checkerCoordinate = checkerCoordinate;
        this.checkOnMove = checkOnMove;
        this.coordAccess = new CoordinateAccess(board);
        checkQueen = new CheckOnCorrectQueenMove(coordAccess, checkerCoordinate, cellCoordinate);
        this.cellCoordinate = cellCoordinate;
        this.board = board;
    }

    /**
     * Checks if a player can move on cell that he chose or eat another checker
     */
    public boolean checkCanYouMoveOnCell() {

        //check if the cell for move is free
        if (!checkOnMove.checkIfCellForMoveIsNotBusy()) {
            ApplicationLogger.logger.info("\nThis position is busy");
            return false;
        }

        //check on moving out of board
        if (!checkOnMove.checkOnNotMovingOut()) {
            ApplicationLogger.logger.info("\nThis cell is out of board");
            return false;
        }

        if (checkColor()) {
            ApplicationLogger.logger.info("\nChoose checker white color(w)");
            return false;
        }

        //check on the rule "it is necessary to eat"
        if (necessaryEat()) {
            ApplicationLogger.logger.info("\nThe rule applies: It is necessary to eat");
            return false;
        }

        //check for usual checker
        if (!coordAccess.getCheckerByItsCoordinate(checkerCoordinate).isQueen()) {
            //check if player can move 2 fields ahead and eat a checker like a usual checker
            if (checkOnMove.checkCanEatChecker())
                return true;

            else //check if player can move 1 field ahead like a usual checker
                return checkOnMove.checkOnHowManyCellsPlayerMoveUsualChecker();

            //checks for queen
        } else {
            //check can player eat checker. If not, check can player move checker
            if (checkQueen.checkCanQueenEatChecker())
                return true;
            else
                return checkQueen.checkOnQueenMoveWithoutEating();
        }
    }

    private boolean checkColor() {
        return coordAccess.getCheckerByItsCoordinate(checkerCoordinate).getColor() == COLOR.BLACK;
    }

    /**
     * Check, is checker not eat another checker for 4 probably coordinate
     */
    private boolean necessaryEat() {
        ChecksForGenerator genCheck = new ChecksForGenerator(board);

        for (int i = 0; i < board.getCheckers().size(); i++)
            if (!coordAccess.getCheckerByItsCoordinate(checkerCoordinate).isQueen())
                if (board.getCheckers().get(i).getColor() == COLOR.WHITE
                        && genCheck.checkOnEating(board.getCheckers().get(i).getCoordinate(), COLOR.WHITE))

                    return necessaryEatForOne(2, 2)
                            && necessaryEatForOne(-2, 2)
                            && necessaryEatForOne(2, -2)
                            && necessaryEatForOne(-2, -2);


        return false;
    }

    /**
     * Check, is checker not eat another checker for one coordinate
     */
    private boolean necessaryEatForOne(int x, int y) {
        return !cellCoordinate.equals(new Coordinate(checkerCoordinate.getX() + x, checkerCoordinate.getY() + y));
    }

}
