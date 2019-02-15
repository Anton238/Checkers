package player;

import board.Board;
import board.DisplayBoard;
import checks.CheckForHumanPlayer;
import checks.CheckOnCorrectMove;
import checks.ChecksForGenerator;
import checks.EnterData;
import color.COLOR;
import coordinates.Coordinate;
import coordinates.CoordinateAccess;
import logger.ApplicationLogger;
import moving.MovingLogic;
import statistics.Statistics;

public class HumanPlayer extends AbstractPlayer {

    private EnterData enter;
    private CheckOnCorrectMove checkOnMove;
    private int ateBlackSize;

    public HumanPlayer(Board board, Statistics statistics) {
        enter = new EnterData();
        super.board = board;
        this.statistics = statistics;
    }

    @Override
    public void move() {
        CheckForHumanPlayer humanCheck;
        ateBlackSize = statistics.getAteBlackSize();

        do { //will be cyclic asking to enter data if entered data is incorrect
            enterData();

            humanCheck = new CheckForHumanPlayer(checkerCoordinate, cellCoordinate, checkOnMove, board);

            if (!humanCheck.checkCanYouMoveOnCell())
                ApplicationLogger.logger.info("\nYou can`t move on this cell by with checker");
        }
        while (!humanCheck.checkCanYouMoveOnCell());

        MovingLogic movingLogic = new MovingLogic(checkerCoordinate, cellCoordinate, board, checkOnMove, statistics);
        movingLogic.move();


        if (checkOnNextEat())
            nextMoves();
    }

    private void nextMoves() {
        CheckForHumanPlayer humanCheck;
        checkerCoordinate = new Coordinate(cellCoordinate.getX(), cellCoordinate.getY());

        do {
            ApplicationLogger.logger.info("\nEnter cell number for eating next cell: ");
            cellCoordinate = enter.enterCoordinates();

            checkOnMove = new CheckOnCorrectMove(checkerCoordinate, cellCoordinate, board);
            humanCheck = new CheckForHumanPlayer(checkerCoordinate, cellCoordinate, checkOnMove, board);

            if (!humanCheck.checkCanYouMoveOnCell())
                ApplicationLogger.logger.info("\nYou can`t move on this cell");

        } while (!humanCheck.checkCanYouMoveOnCell());

        MovingLogic movingLogic = new MovingLogic(checkerCoordinate, cellCoordinate, board, checkOnMove, statistics);
        movingLogic.move();

        if (checkOnNextEat()) {
            DisplayBoard display = new DisplayBoard(board);
            display.drawBoard();
            nextMoves();
        }
    }

    private boolean checkOnNextEat() {
        ChecksForGenerator genCheck = new ChecksForGenerator(board);
        CoordinateAccess coordAccess = new CoordinateAccess(board);

        if (ateBlackSize < statistics.getAteBlackSize())
            return (coordAccess.getCheckerByItsCoordinate(cellCoordinate).isQueen()
                    && genCheck.checkQueenEatForEvery(cellCoordinate, COLOR.WHITE))

                    || genCheck.checkOnEating(cellCoordinate, COLOR.WHITE);

        return false;
    }

    /**
     * Enter coordinates for cell on that to be moved and the checker that is being moved
     */
    @Override
    public void enterData() {
        ApplicationLogger.logger.info("\n\nEnter checker number: ");
        checkerCoordinate = enter.enterCoordinates();
        ApplicationLogger.logger.info("\nEnter cell number: ");
        cellCoordinate = enter.enterCoordinates();
        checkOnMove = new CheckOnCorrectMove(checkerCoordinate, cellCoordinate, board);
    }
}
