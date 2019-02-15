package automove.candidate;

import coordinates.Coordinate;

public class Candidate {
    private Coordinate coordinate;
    private int moveProbability;

    public Candidate(Coordinate coordinate) {
        this.coordinate = coordinate;
        moveProbability = MoveProbability.NULL.getValue();
    }

    public int getMoveProbability() {
        return moveProbability;
    }

    public void willEat() {
        moveProbability += MoveProbability.EAT.getValue();
    }

    public void willBeAte() {
        moveProbability = MoveProbability.WILL_BE_EATEN.getValue();
    }

    public void willBeQueen() {
        moveProbability += MoveProbability.BE_QUEEN.getValue();
    }

    public void  willMove() {
        moveProbability += MoveProbability.MOVE.getValue();
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public int getX() {
        return coordinate.getX();
    }

    public int getY() {
        return coordinate.getY();
    }
}
