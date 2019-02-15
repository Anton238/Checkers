package statistics;

import board.Checker;
import color.COLOR;
import logger.ApplicationLogger;

import java.util.ArrayList;

public class Statistics {
    private int initialCheckersSize;
    private ArrayList<Checker> ateBlackCheckersByWhitePlayer;//used to keep ate checkers by WHITE player
    private ArrayList<Checker> ateWhiteCheckersByBlackPlayer;//used to keep ate checkers by BLACK player

    public Statistics(int size) {
        initialCheckersSize = size/2;
        ateBlackCheckersByWhitePlayer = new ArrayList<>();
        ateWhiteCheckersByBlackPlayer = new ArrayList<>();
    }

    public void addAteChecker(Checker checker) {
        if (checker.getColor() == COLOR.WHITE)
            ateWhiteCheckersByBlackPlayer.add(checker);
        else
            ateBlackCheckersByWhitePlayer.add(checker);
    }

    public int getAteWhiteSize() {
        return ateWhiteCheckersByBlackPlayer.size();
    }

    public int getAteBlackSize() {
        return ateBlackCheckersByWhitePlayer.size();
    }

    /**
     * Show statistics about ate checkers.
     */
    public void showStatistics() {
        ApplicationLogger.logger.info("\nWhite player ate " + getNumOfUsualAteBlackCheckers()
                + " usual checkers and " + getNumOfQueenAteBlackCheckers()
                + " queen checkers and has " + (12- ateWhiteCheckersByBlackPlayer.size()) + " checkers left.");

        ApplicationLogger.logger.info("\nBlack player ate " + getNumOfUsualAteWhiteCheckers()
                + " usual checkers and " + getNumOfQueenAteWhiteCheckers()
                + " queen checkers and has " + (12- ateBlackCheckersByWhitePlayer.size()) + " checkers left.");
    }

    private int getNumOfUsualAteWhiteCheckers() {
        int num = 0;
        for (Checker anAteWhiteCheckersByBlackPlayer : ateWhiteCheckersByBlackPlayer)
            if (!anAteWhiteCheckersByBlackPlayer.isQueen())
                num++;

        return num;
    }

    private int getNumOfQueenAteWhiteCheckers() {
        int num = 0;
        for (Checker anAteWhiteCheckersByBlackPlayer : ateWhiteCheckersByBlackPlayer)
            if (anAteWhiteCheckersByBlackPlayer.isQueen())
                num++;

        return num;
    }

    private int getNumOfUsualAteBlackCheckers() {
        int num = 0;
        for (Checker anAteBlackCheckersByWhitePlayer : ateBlackCheckersByWhitePlayer)
            if (!anAteBlackCheckersByWhitePlayer.isQueen())
                num++;

        return num;
    }

    private int getNumOfQueenAteBlackCheckers() {
        int num = 0;
        for (Checker anAteBlackCheckersByWhitePlayer : ateBlackCheckersByWhitePlayer)
            if (anAteBlackCheckersByWhitePlayer.isQueen())
                num++;

        return num;
    }

    public int getSize() {
        return initialCheckersSize;
    }
}
