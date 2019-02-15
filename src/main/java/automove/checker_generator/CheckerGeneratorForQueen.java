package automove.checker_generator;

import automove.candidate.Candidate;
import automove.candidate.DIRECTION;
import board.Board;
import checks.ChecksForGenerator;
import color.COLOR;
import coordinates.Coordinate;

public class CheckerGeneratorForQueen {
    private boolean check;
    private int up;
    private int d;
    private Coordinate secondCoordinate;
    private int firstPref;
    private int secPref;
    private ChecksForGenerator genCheck;
    private COLOR color;

    public CheckerGeneratorForQueen(Board board, COLOR color) {
        genCheck = new ChecksForGenerator(board);
        up = DIRECTION.UP.getValue();
        d = DIRECTION.DOWN.getValue();
        firstPref = 0;
        secPref = 0;
        check = true;
        this.color = color;
    }

    public void takeEatingProbability(Candidate candidate) {
        if (check) {
            check = false;
            secondCoordinate = new Coordinate(candidate.getX(), candidate.getY());
        }

        if (genCheck.checkOnQueenEat(secondCoordinate, up, up, color) && checkDirection(candidate.getCoordinate(), firstPref, secPref, up, up)) {
            takeProb(up, up, candidate);
        } else if (genCheck.checkOnQueenEat(secondCoordinate, d, up, color) && checkDirection(candidate.getCoordinate(), firstPref, secPref, d, up)) {
            takeProb(d, up, candidate);
        } else if (genCheck.checkOnQueenEat(secondCoordinate, up, d, color) && checkDirection(candidate.getCoordinate(), firstPref, secPref, up, d)) {
            takeProb(up, d, candidate);
        } else if (genCheck.checkOnQueenEat(secondCoordinate, d, d, color) && checkDirection(candidate.getCoordinate(), firstPref, secPref, d, d)) {
            takeProb(d, d, candidate);
        } else
            return;

        if (genCheck.checkQueenEatForEvery(secondCoordinate, color))
            takeEatingProbability(candidate);
    }

    private void takeProb(int x, int y, Candidate candidate) {
        secondCoordinate = getQueenCoordForEat(secondCoordinate, x, y);
        candidate.willEat();
        firstPref = x;
        secPref = y;
    }

    private boolean checkDirection(Coordinate initialChecker, int x, int y, int x2, int y2) {
        x = -x;
        y = -y;

        if (!checkOnEatingInitialCell(getQueenCoordForEat(secondCoordinate, x2, y2), initialChecker))
            return x != x2 || y != y2;

        return false;
    }

    private boolean checkOnEatingInitialCell(Coordinate cell, Coordinate checker) {
        return cell.equals(new Coordinate(checker.getX() + 1, checker.getY() + 1))
                || cell.equals(new Coordinate(checker.getX() - 1, checker.getY() + 1))
                || cell.equals(new Coordinate(checker.getX() + 1, checker.getY() - 1))
                || cell.equals(new Coordinate(checker.getX() - 1, checker.getY() - 1));
    }

    private Coordinate getQueenCoordForEat(Coordinate checker, int x, int y) {
        while (x != 0) {
            if (genCheck.checkOnQueenEatForOne(checker, new Coordinate(checker.getX() + x, checker.getY() + y), color))
                return new Coordinate(checker.getX() + x, checker.getY() + y);

            x = x > 0 ? --x : ++x;
            y = y > 0 ? --y : ++y;
        }
        return new Coordinate(0, 0);
    }
}
