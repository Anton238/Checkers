package automove.candidate;

public enum DIRECTION {
    UP(7), DOWN(-7);

    private final int value;

    DIRECTION(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
