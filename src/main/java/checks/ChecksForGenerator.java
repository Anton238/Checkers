package checks;

import automove.candidate.Candidate;
import automove.candidate.DIRECTION;
import board.Board;
import color.COLOR;
import coordinates.Coordinate;
import coordinates.CoordinateAccess;

public class ChecksForGenerator {
    private CheckOnCorrectMove checkMove;
    private Board board;
    private CoordinateAccess coordAccess;

    public ChecksForGenerator(Board board) {
        this.board = board;
        coordAccess = new CoordinateAccess(board);
    }

    public boolean checkOnMoving(Coordinate checker) {
        return checkOnMoveForOne(checker, new Coordinate(checker.getX() + 1, checker.getY() + 1))
                || checkOnMoveForOne(checker, new Coordinate(checker.getX() - 1, checker.getY() + 1))
                || checkOnMoveForOne(checker, new Coordinate(checker.getX() - 1, checker.getY() - 1))
                || checkOnMoveForOne(checker, new Coordinate(checker.getX() + 1, checker.getY() - 1));
    }

    public boolean checkOnMoveForOne(Coordinate checker, Coordinate cell) {
        checkMove = new CheckOnCorrectMove(checker, cell, board);

        return checkMove.checkIfCellForMoveIsNotBusy()
                && checkMove.checkOnNotMovingOut()
                && checkMove.checkOnHowManyCellsPlayerMoveUsualChecker();
    }

    public boolean checkOnEating(Coordinate checker, COLOR color) {
        return checkOnEatForOne(checker, new Coordinate(checker.getX() + 2, checker.getY() + 2), color)
                || checkOnEatForOne(checker, new Coordinate(checker.getX() - 2, checker.getY() + 2), color)
                || checkOnEatForOne(checker, new Coordinate(checker.getX() - 2, checker.getY() - 2), color)
                || checkOnEatForOne(checker, new Coordinate(checker.getX() + 2, checker.getY() - 2), color);
    }

    public boolean checkOnEatForOne(Coordinate checker, Coordinate cell, COLOR color) {
        checkMove = new CheckOnCorrectMove(checker, cell, board);
        checkMove.setCheckerColor(color);

        return checkMove.checkIfCellForMoveIsNotBusy()
                && checkMove.checkOnNotMovingOut()
                && checkMove.checkCanEatChecker();
    }

    public boolean checkOnQueen(Coordinate checker) {
        return checkOnQueenForOne(checker.getY() + 1)
                || checkOnQueenForOne(checker.getY() - 1);
    }

    public boolean checkOnQueenForOne(int cellY) {
        return checkMove.checkIfCellForMoveIsNotBusy() && checkMove.checkOnNotMovingOut() && cellY == 8;
    }

    public boolean checkOnEatAfterMove(Candidate candidate) {
        return checkOnEatAfterMoveForOne(candidate, -1, 1)
                || checkOnEatAfterMoveForOne(candidate, 1, -1)
                || checkOnEatAfterMoveForOne(candidate, 1, 1)
                || checkOnEatAfterMoveForOne(candidate, -1, -1);
    }

    private boolean checkOnEatAfterMoveForOne(Candidate candidate, int x, int y) {
        return coordAccess.isCheckerOnThisPosition(candidate.getX() + x, candidate.getY() + y)
                && coordAccess.getCheckerByItsCoordinate(candidate.getX() + x, candidate.getY() + y).getColor() == COLOR.WHITE
                && !coordAccess.isCheckerOnThisPosition(candidate.getX() - x, candidate.getY() - y);
    }

    public Coordinate getCoordinateForProb(Coordinate checker, int x, int y) {
        return new Coordinate(checker.getX() + x, checker.getY() + y);
    }

    public boolean checkQueenEatForEvery(Coordinate checker, COLOR color) {
        int up = DIRECTION.UP.getValue();
        int d = DIRECTION.DOWN.getValue();

        if (checkOnQueenEat(checker, up, up, color))
            return true;

        if (checkOnQueenEat(checker, d, up, color))
            return true;

        if (checkOnQueenEat(checker, up, d, color))
            return true;

        if (checkOnQueenEat(checker, d, d, color))
            return true;

        return false;
    }

    public boolean checkOnQueenEat(Coordinate checker, int x, int y, COLOR color) {
        while (x != 0) {
            if (checkOnQueenEatForOne(checker, new Coordinate(checker.getX() + x, checker.getY() + y), color))
                return true;

            x = x > 0 ? --x : ++x;
            y = y > 0 ? --y : ++y;
        }
        return false;
    }


    public boolean checkOnQueenEatForOne(Coordinate checker, Coordinate cell, COLOR color) {
        checkMove = new CheckOnCorrectMove(checker, cell, board);
        CheckOnCorrectQueenMove checkQueen = new CheckOnCorrectQueenMove(coordAccess, checker, cell);
        checkQueen.setCheckerColor(color);

        return checkMove.checkIfCellForMoveIsNotBusy()
                && checkMove.checkOnNotMovingOut()
                && checkQueen.checkCanQueenEatChecker();
    }

    public boolean checkOnQueenMove(Coordinate checker, int x, int y) {
        while (x != 0) {
            if (checkOnQueenMoveForOne(checker, new Coordinate(checker.getX() + x, checker.getY() + y)))
                return true;

            x = x > 0 ? --x : ++x;
            y = y > 0 ? --y : ++y;
        }
        return false;
    }

    public boolean checkOnQueenMoveForOne(Coordinate checker, Coordinate cell) {
        checkMove = new CheckOnCorrectMove(checker, cell, board);
        CheckOnCorrectQueenMove checkQueen = new CheckOnCorrectQueenMove(coordAccess, checker, cell);

        return checkMove.checkIfCellForMoveIsNotBusy()
                && checkMove.checkOnNotMovingOut()
                && checkQueen.checkOnQueenMoveWithoutEating();
    }

    public boolean checkQueenMoveForEvery(Coordinate checker) {
        int up = DIRECTION.UP.getValue();
        int d = DIRECTION.DOWN.getValue();

        if (checkOnQueenMove(checker, up, up))
            return true;

        if (checkOnQueenMove(checker, d, up))
            return true;

        if (checkOnQueenMove(checker, up, d))
            return true;

        if (checkOnQueenMove(checker, d, d))
            return true;

        return false;
    }
}
