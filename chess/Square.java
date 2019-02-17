public class Square {
    private Coordinate coordinate;
    private Piece piece;

    public Square(Coordinate coords) {
        coordinate = coords;
        piece = null;
    }

    public Square() {
        piece = null;
        coordinate = null;
    }

    public void killPiece() {
        piece = null;
    }

    public Piece getPiece() {
        return piece;
    }

    /**
     * Setters and getters
     */
    public void setPiece(Piece p) {
        piece = p;
    }

    public Coordinate getCoord() {
        return coordinate;
    }

    //-----------------------------------------------------------------------------------

    /**
     * Checks
     */
    public boolean isEmpty() {
        return piece == null ? true : false;
    }

    public boolean squareSameTeam(Piece otherPiece) {
        return this.piece.getPlayer().equal(otherPiece.getPlayer());
    }

}
