package game;

import board.Board;
import board.Cell;
import board.Checker;
import board.DisplayBoard;
import color.COLOR;

public class InitializeBoard {

    private Board board;

    public InitializeBoard(Board board) {
        this.board = board;
    }

    /**
     * Initialize initial board
     */
    public void initialize() {
        fillCells();
        fillCheckers();
        DisplayBoard display = new DisplayBoard(board);
        display.drawBoard();
    }

    /**
     * Fill list by 64 Cell()
     */
    private void fillCells() {
        for (int y = 8; y > 0; y--)
            for (int x = 1; x < 9; x++) {
                if ((y == 2 || y == 6 || y == 8) && x % 2 != 0)
                    board.getCells().add(new Cell(true, x, y));
                else if ((y == 1 || y == 3 || y == 7) && x % 2 == 0)
                    board.getCells().add(new Cell(true, x, y));
                else
                    board.getCells().add(new Cell(false, x, y));
            }
    }

    /**
     * Fill list by 24 Checker()
     */
    private void fillCheckers() {
        for (int y = 1; y < 9; y++)
            for (int x = 1; x < 9; x++) {
                if ((y == 1 || y == 3) && x % 2 == 0)
                    board.getCheckers().add(new Checker(COLOR.BLACK, x, y));
                else if (y == 2 && x % 2 != 0)
                    board.getCheckers().add(new Checker(COLOR.BLACK, x, y));
                else if ((y == 6 || y == 8) && x % 2 != 0)
                    board.getCheckers().add(new Checker(COLOR.WHITE, x, y));
                else if (y == 7 && x % 2 == 0)
                    board.getCheckers().add(new Checker(COLOR.WHITE, x, y));
            }
    }
}

