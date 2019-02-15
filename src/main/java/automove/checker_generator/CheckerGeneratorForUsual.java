package automove.checker_generator;

import automove.candidate.Candidate;
import board.Board;
import checks.ChecksForGenerator;
import color.COLOR;
import coordinates.Coordinate;

import java.util.ArrayList;

public class CheckerGeneratorForUsual {
    private boolean check;
    private Coordinate secondCoordinate;
    private ChecksForGenerator genCheck;
    private ArrayList<Coordinate> someCoordinates;
    private COLOR color;

    public CheckerGeneratorForUsual(Board board, COLOR color) {
        someCoordinates = new ArrayList<>();
        genCheck = new ChecksForGenerator(board);
        check = true;
        this.color = color;
    }

    /**
     * Take eatingProbability for eating one or some checkers
     */
    public void addEatingProbability(Candidate candidate) {
        if (check) {
            check = false;
            secondCoordinate = new Coordinate(candidate.getX(), candidate.getY());
            someCoordinates.add(secondCoordinate);
        }

        if (genCheck.checkOnEating(secondCoordinate, color)) {
            candidate.willEat();

            if (checkOnEat(secondCoordinate, 2, 2))
                addProbability(candidate, 2, 2);
            else if (checkOnEat(secondCoordinate, -2, 2))
                addProbability(candidate, -2, 2);
            else if (checkOnEat(secondCoordinate, 2, -2))
                addProbability(candidate, 2, -2);
            else if (checkOnEat(secondCoordinate, -2, -2))
                addProbability(candidate, -2, -2);

        }
    }

    private void addProbability(Candidate candidate, int x, int y) {
        takeCoordinateForNextEat(secondCoordinate, x, y);
        addEatingProbability(candidate);
    }

    /**
     * Need to get the coordinates of the next eating for eating a few checkers
     */
    private void takeCoordinateForNextEat(Coordinate checker, int x, int y) {
        secondCoordinate = new Coordinate(checker.getX() + x, checker.getY() + y);
        someCoordinates.add(secondCoordinate);
    }

    /**
     * Check, really checker can be eaten
     */
    private boolean checkOnEat(Coordinate checker, int x, int y) {
        return genCheck.checkOnEatForOne(checker, new Coordinate(checker.getX() + x, checker.getY() + y), color)
                && !checkOnEqualsSomeCoordinates(new Coordinate(checker.getX() + x, checker.getY() + y));
    }

    /**
     * It is need for the algorithm does not find
     * the cell from the previous move for the next move.
     */
    private boolean checkOnEqualsSomeCoordinates(Coordinate cellCoordinate) {
        for (Coordinate someCoordinate : someCoordinates)
            if (someCoordinate.equals(cellCoordinate))
                return false;

        return true;
    }
}
