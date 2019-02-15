package board;

import coordinates.Coordinate;

public class Cell {

    private boolean isBusy; //shows does the field is occupied with the checker
    private Coordinate coordinate; //show field coordinate

    public Cell(boolean isBusy, int x, int y) {
        coordinate = new Coordinate(x, y);
        this.isBusy = isBusy;
    }

    public Cell() {}

    public boolean isBusy() {
        return isBusy;
    }

    public void setBusy(boolean isBusy) {
        this.isBusy = isBusy;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }
}