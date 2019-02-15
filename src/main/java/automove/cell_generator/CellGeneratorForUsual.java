package automove.cell_generator;

import automove.candidate.Candidate;
import board.Board;
import checks.ChecksForGenerator;
import color.COLOR;
import coordinates.Coordinate;

import java.util.ArrayList;
import java.util.Collections;

public class CellGeneratorForUsual {
    private Coordinate checkerCoordinate;
    private ArrayList<Coordinate> coordinatesForEat;
    private ArrayList<Candidate> cellCandidates;
    private ChecksForGenerator genCheck;
    private COLOR color;

    public CellGeneratorForUsual(Board board, Coordinate checkerCoordinate, COLOR color) {
        this.checkerCoordinate = checkerCoordinate;
        cellCandidates = new ArrayList<>();
        coordinatesForEat = new ArrayList<>();
        genCheck = new ChecksForGenerator(board);
        this.color = color;
    }

    public ArrayList<Coordinate> coordinatesForEat() {
        initCandidates();
        initializeMovingProbability();

        if (coordinatesForEat.size() > 0)
            return coordinatesForEat;

        else {
            Candidate cellCandidate = cellCandidates.get(0);
            for (Candidate cellCandidate1 : cellCandidates) {
                if (cellCandidate.getMoveProbability() < cellCandidate1.getMoveProbability())
                    cellCandidate = cellCandidate1;
            }

            return new ArrayList<>(Collections.singletonList(cellCandidate.getCoordinate()));
        }
    }

    private void initCandidates() {
        //init candidates with usual move
        if (checkOnMove(1, 1))
            cellCandidates.add(getCandidateForInit(1, 1));
        if (checkOnMove(-1, 1))
            cellCandidates.add(getCandidateForInit(-1, 1));
        if (checkOnMove(1, -1))
            cellCandidates.add(getCandidateForInit(1, -1));
        if (checkOnMove(-1, -1))
            cellCandidates.add(getCandidateForInit(-1, -1));

        //init candidates with eat-move
        if (checkOnEatSomeCheckers(2, 2))
            cellCandidates.add(getCandidateForInit(2, 2));
        if (checkOnEatSomeCheckers(-2, 2))
            cellCandidates.add(getCandidateForInit(-2, 2));
        if (checkOnEatSomeCheckers(2, -2))
            cellCandidates.add(getCandidateForInit(2, -2));
        if (checkOnEatSomeCheckers(-2, -2))
            cellCandidates.add(getCandidateForInit(-2, -2));
    }

    /**
     * Take movingProbability to everyone
     */
    private void initializeMovingProbability() {
        for (Candidate cellCandidate : cellCandidates) {
            //check, can checker eat one or few checkers
            if (genCheck.checkOnEatForOne(checkerCoordinate, cellCandidate.getCoordinate(), color)) {
                takeEatProbability(checkerCoordinate, cellCandidate.getCoordinate());

                //check, can checker move
            } else if (genCheck.checkOnMoveForOne(checkerCoordinate, cellCandidate.getCoordinate()))
                cellCandidate.willMove();

            //check, can checker be a queen
            if (genCheck.checkOnQueenForOne(cellCandidate.getY()))
                cellCandidate.willBeQueen();

            //check, can checker be eaten after move
            if (genCheck.checkOnEatAfterMove(cellCandidate))
                cellCandidate.willBeAte();
        }
    }

    /**
     * Take eat probability to every candidate
     */
    private void takeEatProbability(Coordinate checkerCoordinate, Coordinate cellCoordinate) {
        if (genCheck.checkOnEatForOne(checkerCoordinate, cellCoordinate, color)) {
            coordinatesForEat.add(cellCoordinate);

            if (checkOnEatSomeCheckers(cellCoordinate, 2, 2))
                takeEatProbability(cellCoordinate, genCheck.getCoordinateForProb(cellCoordinate, 2, 2));
            else if (checkOnEatSomeCheckers(cellCoordinate, -2, 2))
                takeEatProbability(cellCoordinate, genCheck.getCoordinateForProb(cellCoordinate, -2, 2));
            else if (checkOnEatSomeCheckers(cellCoordinate, 2, -2))
                takeEatProbability(cellCoordinate, genCheck.getCoordinateForProb(cellCoordinate, 2, -2));
            else if (checkOnEatSomeCheckers(cellCoordinate, -2, -2))
                takeEatProbability(cellCoordinate, genCheck.getCoordinateForProb(cellCoordinate, -2, -2));
        }
    }

    /**
     * Check really checker can eat coordinate and check doesn`t cell coordinate is already in the list
     */
    private boolean checkOnEatSomeCheckers(Coordinate checker, int x, int y) {
        return genCheck.checkOnEatForOne(checker, new Coordinate(checker.getX() + x, checker.getY() + y), color)
                && !checkOnEqualsSomeCoordinates(new Coordinate(checker.getX() + x, checker.getY() + y));
    }

    /**
     * It is need for the algorithm does not find
     * the cell from the previous move for the next move.
     */
    private boolean checkOnEqualsSomeCoordinates(Coordinate cellCoordinate) {
        for (Coordinate someCoordinate : coordinatesForEat)
            if (someCoordinate.equals(cellCoordinate))
                return true;

        return false;
    }

    /**
     * Check is candidate really can move on coordinate
     */
    private boolean checkOnMove(int x, int y) {
        return genCheck.checkOnMoveForOne(checkerCoordinate, new Coordinate(checkerCoordinate.getX() + x, checkerCoordinate.getY() + y));
    }

    /**
     * Find and return cell candidate for move.
     */
    private Candidate getCandidateForInit(int x, int y) {
        return new Candidate(new Coordinate(checkerCoordinate.getX() + x, checkerCoordinate.getY() + y));
    }

    /**
     * Check is candidate really can eat and move on coordinate
     */
    private boolean checkOnEatSomeCheckers(int x, int y) {
        return genCheck.checkOnEatForOne(checkerCoordinate, new Coordinate(checkerCoordinate.getX() + x, checkerCoordinate.getY() + y), color);
    }
}
