package automove.cell_generator;

import automove.candidate.Candidate;
import automove.candidate.DIRECTION;
import board.Board;
import checks.ChecksForGenerator;
import color.COLOR;
import coordinates.Coordinate;

import java.util.ArrayList;
import java.util.Collections;

public class CellGeneratorForQueen {
    private int fPref;
    private int sPref;
    private Coordinate checkerCoordinate;
    private ArrayList<Coordinate> coordinatesForMove;
    private ChecksForGenerator genCheck;
    private ArrayList<Candidate> candidatesMove;
    private COLOR color;
    private int up;
    private int d;

    public CellGeneratorForQueen(Board board, Coordinate checkerCoordinate, COLOR color) {
        this.checkerCoordinate = checkerCoordinate;
        coordinatesForMove = new ArrayList<>();
        fPref = 0;
        sPref = 0;
        genCheck = new ChecksForGenerator(board);
        candidatesMove = new ArrayList<>();
        this.color = color;
        up = DIRECTION.UP.getValue();
        d = DIRECTION.DOWN.getValue();
    }

    public ArrayList<Coordinate> coordinatesForEat() {
        if (genCheck.checkQueenEatForEvery(checkerCoordinate, color))
            initCoordinatesForEat(checkerCoordinate);
        else {
            initCoordinateForMove();
            takeProbability();
            getBestCandidateForMove();
        }


        return coordinatesForMove;
    }

    private void initCoordinatesForEat(Coordinate checker) {
        if (genCheck.checkOnQueenEat(checker, up, up, color) && checkDirection(checker, fPref, sPref, up, up))
            takeCell(up, up, checker);
        else if (genCheck.checkOnQueenEat(checker, d, up, color) && checkDirection(checker, fPref, sPref, d, up))
            takeCell(d, up, checker);
        else if (genCheck.checkOnQueenEat(checker, up, d, color) && checkDirection(checker, fPref, sPref, up, d))
            takeCell(up, d, checker);
        else if (genCheck.checkOnQueenEat(checker, d, d, color) && checkDirection(checker, fPref, sPref, d, d))
            takeCell(d, d, checker);
        else
            return;

        if (genCheck.checkQueenEatForEvery(coordinatesForMove.get(coordinatesForMove.size() - 1), color))
            initCoordinatesForEat(coordinatesForMove.get(coordinatesForMove.size() - 1));
    }

    private void takeCell(int x, int y, Coordinate checker) {
        coordinatesForMove.add(getQueenCoordForEat(checker, x, y));
        fPref = x;
        sPref = y;
    }

    private boolean checkDirection(Coordinate checker, int x, int y, int x2, int y2) {
        x = -x;
        y = -y;

        if (!checkOnEatingInitialCell(getQueenCoordForEat(checker, x2, y2)))
            return x != x2 || y != y2;

        return false;
    }

    private boolean checkOnEatingInitialCell(Coordinate checker) {
        return checker.equals(new Coordinate(checkerCoordinate.getX() + 1, checkerCoordinate.getY() + 1))
                || checker.equals(new Coordinate(checkerCoordinate.getX() - 1, checkerCoordinate.getY() + 1))
                || checker.equals(new Coordinate(checkerCoordinate.getX() + 1, checkerCoordinate.getY() - 1))
                || checker.equals(new Coordinate(checkerCoordinate.getX() - 1, checkerCoordinate.getY() - 1));
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


    private void initCoordinateForMove() {
        if (genCheck.checkOnQueenMove(checkerCoordinate, up, up))
            candidatesMove.add(getCandForMove(checkerCoordinate, up, up));

        else if (genCheck.checkOnQueenMove(checkerCoordinate, d, up))
            candidatesMove.add(getCandForMove(checkerCoordinate, d, up));

        else if (genCheck.checkOnQueenMove(checkerCoordinate, up, d))
            candidatesMove.add(getCandForMove(checkerCoordinate, up, d));

        else if (genCheck.checkOnQueenMove(checkerCoordinate, d, d))
            candidatesMove.add(getCandForMove(checkerCoordinate, d, d));
    }

    private Candidate getCandForMove(Coordinate checker, int x, int y) {
        while (x != 0) {
            if (genCheck.checkOnQueenMoveForOne(checker, new Coordinate(checker.getX() + x, checker.getY() + y)))
                return new Candidate(new Coordinate(checker.getX() + x, checker.getY() + y));

            x = x > 0 ? --x : ++x;
            y = y > 0 ? --y : ++y;
        }
        return new Candidate(new Coordinate(0, 0));
    }

    private void takeProbability() {
        for (Candidate candidatesMove : candidatesMove) {
            if (genCheck.checkOnQueenMoveForOne(checkerCoordinate, candidatesMove.getCoordinate()))
                candidatesMove.willMove();

            if (genCheck.checkOnEatAfterMove(candidatesMove))
                candidatesMove.willBeAte();
        }
    }

    private void getBestCandidateForMove() {
        Candidate cellCandidate = candidatesMove.get(0);
        for (Candidate candidateMove : candidatesMove) {
            if (cellCandidate.getMoveProbability() < candidateMove.getMoveProbability())
                cellCandidate = candidateMove;
        }
        coordinatesForMove = new ArrayList<>(Collections.singletonList(cellCandidate.getCoordinate()));
    }
}
