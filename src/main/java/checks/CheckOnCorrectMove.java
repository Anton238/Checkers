package checks;

import board.Board;
import color.COLOR;
import coordinates.Coordinate;
import coordinates.CoordinateAccess;

public class CheckOnCorrectMove {

    private CoordinateAccess coordAccess;
    private Coordinate checkerCoordinate;
    private Coordinate cellCoordinate;
    private CheckOnCorrectQueenMove checkQueen;
    private COLOR checkerColor;

    public CheckOnCorrectMove(Coordinate checkerCoordinate, Coordinate cellCoordinate, Board board) {
        coordAccess = new CoordinateAccess(board);
        this.checkerCoordinate = checkerCoordinate;
        this.cellCoordinate = cellCoordinate;
        checkQueen = new CheckOnCorrectQueenMove(coordAccess, checkerCoordinate, cellCoordinate);
    }

    /**
     * Check, if the checker is out of the board
     */
    public boolean checkOnNotMovingOut() {
        return !(cellCoordinate.getY() > 8 || cellCoordinate.getX() > 8
                || cellCoordinate.getY() < 1 || cellCoordinate.getX() < 1);
    }

    /**
     * Checks if the cell on what player want to move is busy
     */
    public boolean checkIfCellForMoveIsNotBusy() {
        return !coordAccess.getCellByItsCoordinate(cellCoordinate).isBusy();
    }

    /**
     * Checks if player can eat checker
     */
    public boolean checkCanEatChecker() {
        //check is checker and queen and can queen eat checker
        if (coordAccess.getCheckerByItsCoordinate(checkerCoordinate).isQueen()
                && checkQueen.checkCanQueenEatChecker())
            return true;


        //For usual checker. Check, really ate checker is in position
        return (checkOnCheckerCorrectPlace(1, 1)
                || checkOnCheckerCorrectPlace(-1, 1)
                || checkOnCheckerCorrectPlace(-1, -1)
                || checkOnCheckerCorrectPlace(1, -1));
    }

    /**
     * Check, is there is really checker that we want to eat
     */
    private boolean checkOnCheckerCorrectPlace(int x, int y) {
        return coordAccess.isCheckerOnThisPosition(checkerCoordinate.getX() + x, checkerCoordinate.getY() + y)

                && !coordAccess.isCheckerOnThisPosition(checkerCoordinate.getX() + x * 2, checkerCoordinate.getY() + y * 2)

                && cellCoordinate.getX() == checkerCoordinate.getX() + x * 2
                && cellCoordinate.getY() == checkerCoordinate.getY() + y * 2

                && checkIsAteCheckerCorrectColor(x, y, checkerCoordinate);
    }

    /**
     * Compare the color of the eaten checker and the checker who move. If they are not equal, return true
     */
    private boolean checkIsAteCheckerCorrectColor(int x, int y, Coordinate checkerCoordinate) {
        setCheckerColorByPos();
        return coordAccess.getCheckerByItsCoordinate(checkerCoordinate.getX() + x, checkerCoordinate.getY() + y).getColor()
                != checkerColor;
    }


    /**
     * Checks if player chose the cell correctly(player can only move the checker by 1 position without eating)
     */
    public boolean checkOnHowManyCellsPlayerMoveUsualChecker() {
        if (coordAccess.getCheckerByItsCoordinate(checkerCoordinate).getColor() == COLOR.WHITE) {
            return cellCoordinate.compare(checkerCoordinate, -1, -1)
                    || cellCoordinate.compare(checkerCoordinate, 1, -1);

        } else if (coordAccess.getCheckerByItsCoordinate(checkerCoordinate).getColor() == COLOR.BLACK)
            return cellCoordinate.compare(checkerCoordinate, 1, 1)
                    || cellCoordinate.compare(checkerCoordinate, -1, 1);

        return false;
    }

    /**
     * Need to get color by checker coordinate if coordinate is real
     */
    private void setCheckerColorByPos() {
        if(coordAccess.getCheckerByItsCoordinate(checkerCoordinate).getColor() != null)
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
