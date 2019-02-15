package checks;

import board.Board;
import color.COLOR;

public class CheckOnQueen {

    private Board board;

    public CheckOnQueen(Board board) {
        this.board = board;
    }

    /**
     * Checks whether the checker came to the "ladies border".
     * If yes, call the method makeQueen() that makes checker queen
     */
    private boolean checkOnQueen(int i) {
        if (board.getCheckers().get(i).getCoordinate().getY() == 1
                && board.getCheckers().get(i).getColor() == COLOR.WHITE)
            return true;

        else return board.getCheckers().get(i).getCoordinate().getY() == 8
                && board.getCheckers().get(i).getColor() == COLOR.BLACK;
    }

    /**
     * Loops through all the checkers and checks if the checker should become a queen
     */
    public void checkAndTakeQueen() {
        for (int i = 0; i < board.getCheckers().size(); i++)
            if (checkOnQueen(i) && !board.getCheckers().get(i).isQueen())
                board.getCheckers().get(i).setQueen();
    }
}
