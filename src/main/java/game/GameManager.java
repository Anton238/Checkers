package game;

import board.Board;

public class GameManager {
    public static void main(String[] args) {
        Board board = new Board();
        //initialization of the playing field (Board), checkers, etc.
        InitializeBoard initBoard = new InitializeBoard(board);
        initBoard.initialize();

        //start game
        CheckersGame game = new CheckersGame(board);
        game.start();
    }
}
