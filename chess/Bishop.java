import java.util.List;
import java.util.ArrayList;

public class Bishop extends Piece {

    public Bishop(Player player) {
        super("Bishop", player);
    }

    public boolean isMoveValid(Coordinate startpos, Coordinate destination, Board board) {
        //If we enter incorrect coordinates
        if (!destination.validCoordinate()) {
            return false;
        }
        if (destination.equal(startpos)) {
            return false;
        }
        if (Math.abs((startpos.getX() - destination.getX())) == (Math.abs(startpos.getY() - destination.getY()))) {
            if (!checkPath(startpos, destination, board)) {
                return false;
            }
            return true;
        }
        return false;
    }
/**
 * This function basically checks whether or not we collide with anything enroute to the destination
 * @param  startpos start coordinate
 * @param  endpos   end coordinate
 * @param  board    the current board
 * @return          false if collision else true
 */
    public boolean checkPath(Coordinate startpos, Coordinate endpos, Board board) {
        int steps = Math.abs(startpos.getX() - endpos.getX()), dir = 1;
        //We can move right diagonally or left diagonally. If startposX < endposX we've moved right diagonally.
        if (startpos.getX() > endpos.getX()) { //Left diagonally
            if (startpos.getY() > endpos.getY()) {
                dir = -1;
            }
            for (int i = 1; i < steps; i++) {
                if (!board.getSquare(startpos.getX() - i, startpos.getY() + (i * dir)).isEmpty()) {
                    return false;
                }
            }
        } else { //Right
            if (startpos.getY() > endpos.getY()) {
                dir = -1;
            }
            for (int i = 1; i < steps; i++) {
                if (!board.getSquare(startpos.getX() + i, startpos.getY() + (i * dir)).isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    public List<Coordinate> attackedSquares(Coordinate pos, Board board) {
        List<Coordinate> squares = new ArrayList<>();
        int x = pos.getX(), y = pos.getY();
        int steps = Math.abs(x - 8);

        for (int i = 0; i < steps; i++) {
            if (board.getSquare(x + i, y + i).isEmpty()) {
                squares.add(new Coordinate(x + i, y + i));
            } else {
                squares.add(new Coordinate(x + i, y + i));
                break;
            }
        }
        for (int i = 0; i < steps; i++) {
            if (board.getSquare(x + i, y - i).isEmpty()) {
                squares.add(new Coordinate(x + i, y - i));
            } else {
                squares.add(new Coordinate(x + i, y - i));
                break;
            }
        }
        steps = Math.abs(8 - x);
        for (int i = 0; i < steps; i++) {
            if (board.getSquare(x - i, y - i).isEmpty()) {
                squares.add(new Coordinate(x - i, y - i));
            } else {
                squares.add(new Coordinate(x - i, y - i));
                break;
            }
        }
        for (int i = 0; i < steps; i++) {
            if (board.getSquare(x - i, y + i).isEmpty()) {
                squares.add(new Coordinate(x - i, y + i));
            } else {
                squares.add(new Coordinate(x - i, y + i));
                break;
            }
        }
        return squares;
    }

    public char display() {
        return getPlayer().retPlayerTeam().equals("white") ? '♗' : '♝';
    }
}
