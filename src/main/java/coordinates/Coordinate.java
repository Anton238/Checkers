package coordinates;

public class Coordinate {

    private int x; //x coordinate
    private int y; //y coordinate


    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setCoordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coordinate that = (Coordinate) o;

        return x == that.x && y == that.y;
    }

    /**
     * Compare this and (that + x y coordinates) Coordinate objects
     */
    public boolean compare(Coordinate that, int xMove, int yMove) {
        return x == that.getX() + xMove
                && y == that.getY() + yMove;
    }
}
