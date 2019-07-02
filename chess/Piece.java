import java.awt.image.BufferedImage;
import java.lang.Math;
import java.util.List;

/**
 * An abstract class for representing a chess Piece
 */
public abstract class Piece {
    private String pieceType;
    private Player player;
    private boolean hasmoved;

    public Piece(String type, Player p) {
        pieceType = type;
        player = p;
        hasmoved = false;
    }

    /**
     * Checks if the player makes a generally correct move for the pieceType, e.g the bishop can only move diagonally.
     *
     * @param startpos    start position of piece
     * @param destination
     * @param the         current board with all pieces
     */
    protected abstract boolean isMoveValid(Coordinate startpos, Coordinate destination, Board board);
/**
 * This is used to check the path for collisions
 * @param  startpos start position of piece
 * @param  endpos   end position
 * @param  board    current board
 * @return          true if it does not collide with anything otherwise false.
 */
    protected abstract boolean checkPath(Coordinate startpos, Coordinate endpos, Board board);
/**
 * As of now it returns the unicode character of the piece.
 * @return a unicode character, e.g. '♖'
 */
    protected abstract BufferedImage display();

    /**
     * Returns a List of coordinates that the specific piece is threatening.
     *
     * @param pos   the position of the piece
     * @param board the current board
     * @return the list of coordinates.
     */
    protected abstract List<Coordinate> attackedSquares(Coordinate pos, Board board);
    //-------------------------------------------------------------------------------------------------------------------

    /**
     * Getters and setters
     */
    public String getPieceType() {
        return pieceType;
    }

    public Player getPlayer() {
        return player;
    }

    public void hasmoved() {
        hasmoved = true;
    }

    public boolean getHasMoved() {
        return hasmoved;
    }
}
