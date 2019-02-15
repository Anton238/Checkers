package automove.candidate;

public enum MoveProbability {

    NULL(0), MOVE(1), EAT(3), BE_QUEEN(3), WILL_BE_EATEN(0);

    private final int value;

    MoveProbability(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
