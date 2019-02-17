/**
 * A basic coordinate class
 */
public class Coordinate {

    private int ypos;
    private int xpos;

    public Coordinate(int x, int y) {
        xpos = x;
        ypos = y;
    }

    public int getX() {
        return xpos;
    }

    /**
     * Setters and getters for x and y coordinates.
     */
    public void setX(int x) {
        xpos = x;
    }

    public int getY() {
        return ypos;
    }

    public void setY(int y) {
        ypos = y;
    }
    //--------------------------------------------------------------------------------------------

    /**
     * Check if valid coordinate and if two coordinates equal each other.
     */
    public boolean validCoordinate(Coordinate coord) {
        return (coord.getX() >= 0 && getX() <= 8 &&
                coord.getY() >= 0 && getY() <= 8);
    }

    public boolean validCoordinate() {
        return (xpos >= 0 && xpos <= 8 &&
                ypos >= 0 && ypos <= 8);
    }

    public boolean equal(Coordinate coord) {
        return (this.getX() == coord.getX() && this.getY() == coord.getY());
    }

    public boolean xEqual(Coordinate coord) {
        return (this.getX() == coord.getX());
    }

    public boolean yEqual(Coordinate coord) {
        return (this.getY() == coord.getY());
    }

    public String toString() {
        return ("x: " + xpos + " y: " + ypos);
    }
}
