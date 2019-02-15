package checks;

import color.COLOR;
import coordinates.Coordinate;
import coordinates.CoordinateAccess;

public class CheckOnCorrectQueenMove {

    private CoordinateAccess coordAccess;
    private Coordinate cellCoordinate;
    private Coordinate checkerCoordinate;
    private COLOR checkerColor;

    public CheckOnCorrectQueenMove(CoordinateAccess coordinateAccess, Coordinate checkerCoordinate, Coordinate cellCoordinate) {
        coordAccess = coordinateAccess;
        this.cellCoordinate = cellCoordinate;
        this.checkerCoordinate = checkerCoordinate;
    }

    /**
     * Check if king can eat checker
     */
    public boolean checkCanQueenEatChecker() {
        return checkOnRightCoordinatesForEat(-1, -1) || checkOnRightCoordinatesForEat(1, 1)
                || checkOnRightCoordinatesForEat(1, -1) || checkOnRightCoordinatesForEat(-1, 1);
    }

    /**
     * Check, if cellCoordinate input right and checker can eat
     */
    private boolean checkOnRightCoordinatesForEat(int x, int y) {
        setCheckerColorByGetCheckerPos();
        return coordAccess.isCheckerOnThisPosition(cellCoordinate.getX() - x, cellCoordinate.getY() - y)

                && coordAccess.getCheckerByItsCoordinate(cellCoordinate.getX() - x, cellCoordinate.getY() - y).getColor()
                != checkerColor

                && (checkIfCellIsDiagonally(x, y, new Coordinate(cellCoordinate.getX() - x * 2, cellCoordinate.getY() - y * 2))
                || checkOneMove(x, y));
    }

    /**
     * Checks, if the queen move like a usual checker
     */
    private boolean checkOneMove(int x, int y) {
        return cellCoordinate.compare(checkerCoordinate, x * 2, y * 2);
    }

    /**
     * Check is the diagonal way free for four directions
     */
    public boolean checkOnQueenMoveWithoutEating() {
        return checkIfCellIsDiagonally(1, 1, cellCoordinate) || checkIfCellIsDiagonally(-1, 1, cellCoordinate)
                || checkIfCellIsDiagonally(1, -1, cellCoordinate) || checkIfCellIsDiagonally(-1, -1, cellCoordinate);
    }


    /**
     * Check if cell really situated diagonally and if the way is free for one direction
     */
    private boolean checkIfCellIsDiagonally(int x, int y, Coordinate cellCoordinate) {
        int minPref = -8;
        int maxPref = 8;//max num of cells between checker and cell

        while (x < maxPref && x > minPref) {
            //checks to see if there is some checker on the way
            if (coordAccess.isCheckerOnThisPosition(checkerCoordinate.getX() + x, checkerCoordinate.getY() + y))
                return false;

            //check to understand if the "check" has reached from the checker coordinate to the cell coordinate
            if (cellCoordinate.compare(checkerCoordinate, x, y))
                return true;

            x = x > 0 ? ++x : --x;
            y = y > 0 ? ++y : --y;
        }
        return false;
    }

    /**
     * Need to get color by checker coordinate if coordinate is real
     */
    private void setCheckerColorByGetCheckerPos() {
        if (coordAccess.getCheckerByItsCoordinate(checkerCoordinate).getCoordinate() != null)
            checkerColor = coordAccess.getCheckerByItsCoordinate(checkerCoordinate).getColor();
    }

    /**
     * When I check whether a checker can go to several coordinates,
     * I need to find out if the colors match the coordinate of the checkers.
     * But there are no checkers on this coordinate (because I only check, I don’t go),
     * so I manually transfer the color of the checkers
     */
    public void setCheckerColor(COLOR color) {
        checkerColor = color;
    }
}
