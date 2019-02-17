import java.util.List;
import java.util.ArrayList;

public class Queen extends Piece {
    public Queen(Player player) {
        super("Queen", player);
    }


    public boolean isMoveValid(Coordinate startpos, Coordinate destination, Board board) {
        if (!destination.validCoordinate()) {
            return false;
        }
        Piece r = new Rook(this.getPlayer());
        Piece b = new Bishop(this.getPlayer());
        if (!(r.isMoveValid(startpos, destination, board) || b.isMoveValid(startpos, destination, board))) {
            return false;
        }
        return true;
    }

    public boolean checkPath(Coordinate startpos, Coordinate endpos, Board board) {
        return true;
    }

    public List<Coordinate> attackedSquares(Coordinate pos, Board board) {
        Piece rook = new Rook(this.getPlayer());
        Piece bishop = new Bishop(this.getPlayer());
        List<Coordinate> squares = new ArrayList<>(rook.attackedSquares(pos, board));
        squares.addAll(bishop.attackedSquares(pos, board));
        return squares;
    }

    public char display() {
        return getPlayer().retPlayerTeam().equals("white") ? '♕' : '♛';
    }
}
