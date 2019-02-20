import java.util.List;
import java.util.ArrayList;

public class King extends Piece {

    public King(Player p) {
        super("King", p);
    }

    public boolean isMoveValid(Coordinate startpos, Coordinate destination, Board board) {
        if (!destination.validCoordinate()) {
            return false;
        }
        if (startpos.equals(destination)) {
            return false;
        }
        int diffX = Math.abs(startpos.getX() - destination.getX());
        int diffY = Math.abs(startpos.getY() - destination.getY());
        if ((diffX + diffY) == 1) {
            return true;
        }
        if (diffX == 2 && diffY == 0) {
            return true;
        }
        return false;
    }

    public boolean checkPath(Coordinate startpos, Coordinate endpos, Board board) {
        return true;
    }

    public List<Coordinate> attackedSquares(Coordinate start, Board board) {
        //TODO
        return new ArrayList<Coordinate>();
    }

    public char display() {
        return getPlayer().retPlayerTeam().equals("white") ? '♔' : '♚';
    }
}
