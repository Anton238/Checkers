package board;

import color.COLOR;
import logger.ApplicationLogger;

public class DisplayBoard {
    private Board board;

    public DisplayBoard(Board board) {
        this.board = board;
    }

    /**
     * Draw a board with checkers
     */
    public void drawBoard() {
        ApplicationLogger.logger.info("\n");
        for (int i = 0; i < 64; i++) {
            if (i % 8 == 0) //draw checkers coordinates on the board (8..1)
                ApplicationLogger.logger.info("\n" + Math.abs((i / 8) - 8) + " |");

            for (int j = 0; j < board.getCheckers().size(); j++)
                if (equalsCoordinates(i, j)) { //if checker position = field pos
                    ApplicationLogger.logger.info(fillFieldWithChecker(board.getCheckers().get(j)));
                    break; //need to not to draw extra " |"
                } else if (j == board.getCheckers().size() - 1) //draw " |" if not drawn "w|" or "b|"
                    ApplicationLogger.logger.info(" |");
        }
        //draw checkers coordinates on the board (a..b)
        ApplicationLogger.logger.info("\n   a b c d e f g h\n");
    }


    /**
     * Returns true if the coordinate of the Cell
     * is equal to the coordinates of the Checker.
     */
    private boolean equalsCoordinates(int i, int j) {
        return board.getCells().get(i).getCoordinate().equals(board.getCheckers().get(j).getCoordinate());
    }

    /**
     * Returns  "w|", "b|", "W|", or "B|" depending on the Checker()
     */
    private String fillFieldWithChecker(Checker checker) {

        String chess = " ";

        if (checker.getColor() == COLOR.WHITE)
            chess = "w|";
        else if (checker.getColor() == COLOR.BLACK)
            chess = "b|";

        if (checker.isQueen()) //if checker is a queen return enlarged version of checker
            return chess.toUpperCase();
        else
            return chess;
    }
}
