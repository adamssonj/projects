import java.util.List;
import java.util.ArrayList;

public class Knight extends Piece {

    public Knight(Player player) {
        super("Knight", player);
    }

    public boolean isMoveValid(Coordinate startpos, Coordinate destination, Board board) {
        if (!destination.validCoordinate()) {
            return false;
        }
        if (destination.equals(startpos)) {
            return false;
        }
        int diffX = Math.abs(startpos.getX() - destination.getX());
        int diffY = Math.abs(startpos.getY() - destination.getY());
        if ((diffX + diffY) == 3) {
            return true;
        }
        return false;
    }
/**
 * The knight can't collide.
 */
    public boolean checkPath(Coordinate startpos, Coordinate endpos, Board board) {
        return true;
    }
/**
 * This is not a beautiful method, but it does the job
 * @param  pos   the position of the knight.
 * @param  board the current board
 * @return       Returns a List of Coordinates that the Knight is attacking
 */
    public List<Coordinate> attackedSquares(Coordinate pos, Board board) {
        List<Coordinate> squares = new ArrayList<>();
        int x = pos.getX(), y = pos.getY();
        if (new Coordinate(x + 1, y + 2).validCoordinate()) {
            squares.add(new Coordinate(x + 1, y + 2));
        }
        if (new Coordinate(x + 1, y - 2).validCoordinate()) {
            squares.add(new Coordinate(x + 1, y - 2));
        }
        if (new Coordinate(x - 1, y + 2).validCoordinate()) {
            squares.add(new Coordinate(x - 1, y + 2));
        }
        if (new Coordinate(x - 1, y - 2).validCoordinate()) {
            squares.add(new Coordinate(x - 1, y - 2));
        }
        if (new Coordinate(x + 2, y + 1).validCoordinate()) {
            squares.add(new Coordinate(x + 2, y + 1));
        }
        if (new Coordinate(x + 2, y - 1).validCoordinate()) {
            squares.add(new Coordinate(x + 2, y - 1));
        }
        if (new Coordinate(x - 2, y + 1).validCoordinate()) {
            squares.add(new Coordinate(x - 2, y + 1));
        }
        if (new Coordinate(x - 2, y - 1).validCoordinate()) {
            squares.add(new Coordinate(x - 2, y - 1));
        }
        return squares;
    }

    public char display() {
        return getPlayer().retPlayerTeam().equals("white") ? '♘' : '♞';
    }
}
