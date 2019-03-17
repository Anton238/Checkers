package game;

import board.Board;
import board.DisplayBoard;
import checks.CheckOnWin;
import color.COLOR;
import logger.ApplicationLogger;
import player.AbstractPlayer;
import player.AutoPlayer;
import player.HumanPlayer;
import statistics.Statistics;

public class CheckersGame {

    private Board board;
    //color used to understand which player(Human or Auto) is playing. Human is white, Auto is black
    private COLOR color;
    private AbstractPlayer humanPlayer;
    private DisplayBoard display;
    private CheckOnWin checkOnWin;
    private Statistics statistics;

    public CheckersGame(Board board) {
        statistics = new Statistics(board.getCheckers().size());
        this.board = board;
        humanPlayer = new HumanPlayer(board, statistics);
        color = COLOR.WHITE;
        display = new DisplayBoard(board);
        checkOnWin = new CheckOnWin(statistics, board);
    }

    /**
     * Main gaming process.
     */
    public void start() {
        while (checkOnWin.checkOnWin()) {

            ApplicationLogger.logger.info("\n\n" + color + " player turn.\n");

            //take a move
            takeMove();

            //redraw a board
            display.drawBoard();

            //change player COLOR
            changeColor();
        }
    }

    /**
     * Change player color from white to black and again.
     */
    private void changeColor() {
        if (color == COLOR.WHITE)
            color = COLOR.BLACK;
        else
            color = COLOR.WHITE;
    }

    /**
     * Take a move by human player or auto player
     */
    private void takeMove() {
        if (color == COLOR.WHITE) {
            HumanPlayer humanPlayer = new HumanPlayer(board, statistics);
            humanPlayer.move();
        } else {
            AutoPlayer autoPlayer = new AutoPlayer(board, statistics, COLOR.BLACK);
            autoPlayer.move();
        }
    }
}
