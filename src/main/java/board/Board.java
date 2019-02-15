package board;

import java.util.LinkedList;
import java.util.List;

public class Board {

    private List<Cell> cells; //list with 64 Fields()
    private List<Checker> checkers; //list with Checkers(), whose number falls from 24 to 0

    public Board() {
        cells = new LinkedList<>();
        checkers = new LinkedList<>();
    }

    public List<Cell> getCells() {
        return cells;
    }

    public List<Checker> getCheckers() {
        return checkers;
    }
}
