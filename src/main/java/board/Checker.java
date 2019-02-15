package board;
import color.COLOR;
import coordinates.Coordinate;

public class Checker {
    private COLOR checkerColor;
    private Coordinate coordinate; //show checker coordinate
    private boolean isQueen; //show is the checker queen

    public Checker(COLOR checkerColor, int xCoordinate, int yCoordinate) {
        this.checkerColor = checkerColor;
        coordinate = new Coordinate(xCoordinate, yCoordinate);
        isQueen = false;
    }

    public Checker() {}

    public COLOR getColor() {
        return checkerColor;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public boolean isQueen() {
        return isQueen;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate.setCoordinates(coordinate.getX(), coordinate.getY());
    }

    public void setQueen() {
        isQueen = true;
    }
}