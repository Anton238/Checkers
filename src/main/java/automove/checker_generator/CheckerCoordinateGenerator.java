package automove.checker_generator;

import automove.candidate.Candidate;
import board.Board;
import board.Checker;
import checks.ChecksForGenerator;
import color.COLOR;
import coordinates.Coordinate;
import coordinates.CoordinateAccess;
import logger.ApplicationLogger;
import statistics.Statistics;

import java.util.ArrayList;
import java.util.Random;

public class CheckerCoordinateGenerator {
    private Board board;
    private ArrayList<Candidate> checkerCandidates;
    private ChecksForGenerator genCheck;
    private CheckerGeneratorForQueen checkerGenQueen;
    private CheckerGeneratorForUsual checkerGenUsual;
    private COLOR color;
    private Statistics statistics;


    public CheckerCoordinateGenerator(Board board, COLOR color, Statistics statistics) {
        this.board = board;
        checkerCandidates = new ArrayList<>();
        genCheck = new ChecksForGenerator(board);
        checkerGenQueen = new CheckerGeneratorForQueen(board, color);
        this.color = color;
        this.statistics = statistics;
    }

    /**
     * Get Coordinate of Checker with better moving variant
     */
    public Coordinate getMovingCheckerCoordinate() {
        initializeCandidates();
        initMovingProbability();

        return getBestCandidate().getCoordinate();
    }

    /**
     * Check if one player blocked all checkers of another player.
     * If it happened, generator don`t choose any checker
     */
    private void checkOnBlockedMoving() {
        //if generator don`t choose any checker, the checkerCandidates size = 0
        if(checkerCandidates.size() == 0 && color == COLOR.BLACK) {
            ApplicationLogger.logger.info("WHITE player blocked BLACK player`s checkers. " +
                    "\nWHITE player win!");
            statistics.showStatistics();
            System.exit(0);
        } else if(checkerCandidates.size() == 0 && color == COLOR.WHITE) {
            ApplicationLogger.logger.info("BLACK player blocked WHITE player`s checkers. " +
                    "\nBLACK player win!");
            statistics.showStatistics();
            System.exit(0);
        }
    }

    /**
     * Return candidate with best move probability
     */
    private Candidate getBestCandidate() {
        checkOnBlockedMoving();

        //get first candidate as best
        Candidate candidate = checkerCandidates.get(0);
        ArrayList<Candidate> bestCandidates = new ArrayList<>();

        //find best candidate
        for (int i = 1; i < checkerCandidates.size(); i++)
            if (checkerCandidates.get(i).getMoveProbability() > candidate.getMoveProbability())
                candidate = checkerCandidates.get(i);


        //check, if there are candidates this the same moveProbability like best candidate`s
        for (Candidate checkerCandidate : checkerCandidates)
            if (candidate.getMoveProbability() == checkerCandidate.getMoveProbability())
                bestCandidates.add(checkerCandidate);

        //get random candidate if there are few candidates this same move probability
        Random random = new Random();
        return bestCandidates.get(random.nextInt(bestCandidates.size()));
    }

    /**
     * Take moving probability to every candidate
     */
    private void initMovingProbability() {
        CoordinateAccess coordAccess = new CoordinateAccess(board);
        for (Candidate checkerCandidate : checkerCandidates) {
            //for queen checker
            if (coordAccess.getCheckerByItsCoordinate(checkerCandidate.getCoordinate()).isQueen()) {
                if (genCheck.checkQueenEatForEvery(checkerCandidate.getCoordinate(), color))
                    checkerGenQueen.takeEatingProbability(checkerCandidate);

                if (genCheck.checkQueenMoveForEvery(checkerCandidate.getCoordinate()))
                    checkerCandidate.willMove();
                //for usual checker
            } else {
                if (genCheck.checkOnEating(checkerCandidate.getCoordinate(), color)) {
                    checkerGenUsual = new CheckerGeneratorForUsual(board, color);
                    checkerGenUsual.addEatingProbability(checkerCandidate);
                }

                if (genCheck.checkOnMoving(checkerCandidate.getCoordinate()))
                    checkerCandidate.willMove();
                if (genCheck.checkOnQueen(checkerCandidate.getCoordinate()))
                    checkerCandidate.willBeQueen();
            }
        }
    }

    /**
     * Add all checkers with possible moves to list
     */
    private void initializeCandidates() {
        for (int i = 0; i < board.getCheckers().size(); i++)
            if (checkForInitialize(board.getCheckers().get(i)))
                checkerCandidates.add(new Candidate(board.getCheckers().get(i).getCoordinate()));
    }

    /**
     * Check, can checker be added to list of candidates
     */
    private boolean checkForInitialize(Checker checker) {
        CoordinateAccess coordAccess = new CoordinateAccess(board);
        if (checker.getColor() == color) {
            if (coordAccess.getCheckerByItsCoordinate(checker.getCoordinate()).isQueen())
                return genCheck.checkQueenEatForEvery(checker.getCoordinate(), color)
                        || genCheck.checkQueenMoveForEvery(checker.getCoordinate());

            return genCheck.checkOnEating(checker.getCoordinate(), color)
                    || genCheck.checkOnMoving(checker.getCoordinate());
        }
        return false;
    }
}
