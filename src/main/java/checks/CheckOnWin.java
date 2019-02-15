package checks;

import board.Board;
import color.COLOR;
import logger.ApplicationLogger;
import statistics.Statistics;

public class CheckOnWin {

    private Statistics statistics;
    private Board board;

    public CheckOnWin(Statistics statistics, Board board) {
        this.statistics = statistics;
        this.board = board;
    }

    /**
     * Check, is some player ate 12 checkers of another player.
     * If he did, stop the game and show statistics
     */
    public boolean checkOnWin() {
        if (statistics.getAteWhiteSize() >= statistics.getSize()) {
            ApplicationLogger.logger.info("\nBLACK player win!");
            statistics.showStatistics();
            return false;
        } else if (statistics.getAteBlackSize() >= statistics.getSize()) {
            ApplicationLogger.logger.info("\nWHITE player win!");
            statistics.showStatistics();
            return false;
        } else if(checkOnDraw()) {
            ApplicationLogger.logger.info("\nDraw!");
            statistics.showStatistics();
            return false;
        }

        return true;

    }

    private boolean checkOnDraw() {
        int numOfBlackQueen = 0;
        int numOfWhiteQueen = 0;

        if(board.getCheckers().size() == 3)
            for(int i = 0; i < 3; i++) {
                if (!board.getCheckers().get(i).isQueen())//if there are non-queen return false
                    return false;

                if(board.getCheckers().get(i).getColor() == COLOR.WHITE)
                    numOfWhiteQueen++;
                else if(board.getCheckers().get(i).getColor() == COLOR.BLACK)
                    numOfBlackQueen++;
            }

            //Check on the rule "two ladies against one"
        return (numOfBlackQueen == 2 && numOfWhiteQueen == 1) || (numOfBlackQueen == 1 && numOfWhiteQueen == 2);
    }
}
