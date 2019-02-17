import java.util.List;
import java.util.ArrayList;

public class Pawn extends Piece {
    public Pawn(Player p) {
        super("Pawn", p);
    }

    public boolean isMoveValid(Coordinate startpos, Coordinate destination, Board board) {
        if (!destination.validCoordinate()) {
            return false;
        }
        if (destination.equal(startpos)) {
            return false;
        }

        if (startpos.getX() == destination.getX()) {
            if (!checkPath(startpos, destination, board)) {
                return false;
            }
            return true;
        } else {
            if (!checkDiagonalMove(startpos, destination, board)) {
                return false;
            }
            return true;
        }
    }

    private boolean checkDiagonalMove(Coordinate startpos, Coordinate endpos, Board board) {
        if (Math.abs(startpos.getX() - endpos.getX()) != 1) {
            return false;
        }
        Piece bishop = new Bishop(this.getPlayer());
        if (!bishop.isMoveValid(startpos, endpos, board)) {
            return false;
        }
        return true;
    }

    public boolean checkPath(Coordinate startpos, Coordinate endpos, Board board) {
        boolean color; //White will represent false and black true.
        int dir = 1;
        if (Math.abs(startpos.getY() - endpos.getY()) > 2) {
            return false;
        }
        if (board.getSquare(startpos.getX(), startpos.getY()).getPiece().getPlayer().retPlayerTeam().equals("white")) {
            color = false;
        } else {
            color = true;
        }
        if (Math.abs(startpos.getY() - endpos.getY()) == 2) {
            if (getHasMoved()) {
                return false;
            } else {
                if (color) {
                    dir = -1;
                }
                for (int i = 1; i < 3; i++) {
                    if (!board.getSquare(startpos.getX(), startpos.getY() + (i * dir)).isEmpty()) {
                        return false;
                    }
                }
            }
        } else {
            if (color) {
                if (startpos.getY() < endpos.getY()) {
                    return false;
                }
            } else {
                if (startpos.getY() > endpos.getY()) {
                    return false;
                }
            }
        }
        return true;
    }

    public List<Coordinate> attackedSquares(Coordinate pos, Board board) {
        List<Coordinate> squares = new ArrayList<>();
        int x = pos.getX(), y = pos.getY();
        if (this.getPlayer().retPlayerTeam().equals("white")) {
            if (y != 8) {
                if (x == 0) {
                    squares.add(new Coordinate(x + 1, y + 1));
                } else if (x == 7) {
                    squares.add(new Coordinate(x - 1, y + 1));
                } else {
                    squares.add(new Coordinate(x - 1, y + 1));
                    squares.add(new Coordinate(x + 1, y + 1));
                }
            }
        } else {
            if (y != 0) {
                if (x == 0) {
                    squares.add(new Coordinate(x + 1, y - 1));
                } else if (x == 7) {
                    squares.add(new Coordinate(x - 1, y - 1));
                } else {
                    squares.add(new Coordinate(x - 1, y - 1));
                    squares.add(new Coordinate(x + 1, y - 1));
                }
            }
        }
        return squares;
    }

    public char display() {
        return getPlayer().retPlayerTeam().equals("white") ? '♙' : '♟';
    }
}
