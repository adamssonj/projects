import java.util.*;
import java.lang.Math;

/**
 * This class is responsible for representing player moves and checking for validity.
 */
public class Move {

    private Piece piece;
    private Coordinate startpos;
    private Coordinate destination;
    private boolean enPassant;

    public Move(Coordinate start, Coordinate end, Piece movee) {
        piece = movee;
        startpos = start;
        destination = end;
        enPassant = false;
    }

    //Getters
    public Coordinate getFrom() {
        return startpos;
    }

    public Coordinate getTo() {
        return destination;
    }

    public Piece getPiece() {
        return piece;
    }
    //----------------------------------------------------------------------------------------------
    //Check for valid moves

    /**
     * Check the basics of a legal move
     *
     * @param coords Coordinates of the move attempted
     * @return true if legal else false.
     */
    public boolean checkMove(Coordinate[] coords, Player currentPlayer, Board board) {
        Square startSquare = board.getSquare(startpos);
        Square endSquare = board.getSquare(destination);
        if (piece == null) {
            return false;
        }
        if (!startSquare.getPiece().getPlayer().equal(currentPlayer)) {
            return false;
        }
        if (!startSquare.getPiece().isMoveValid(startpos, destination, board)) {
            return false;
        }
        if (endSquare.getPiece() != null) {
            if (startSquare.getPiece().getPlayer().equal(endSquare.getPiece().getPlayer())) {
                return false;
            }
        }
        return true;
    }

    public boolean pawnCheck(ArrayList<Move> movesMade, Board board) {
        if (startpos.getX() == destination.getX() && isCapture(board)) { //Can't capture forward
            return false;
        }
        if (piece.getPlayer().retPlayerTeam().equals("white")) { //White can only increase y val
            if (startpos.getY() >= destination.getY()) {
                return false;
            }
        } else {
            if (startpos.getY() <= destination.getY()) { //Black can only go 'backwards' from 6->5->4->...-> 0
                return false;
            }
        }
        if (startpos.getX() != destination.getX() && !isCapture(board)) { //Can't go diagonally without captureing, only if enpassant
            if (enPassant(movesMade)) {
                enPassant = true;
                return true;
            }
            return false;
        }
        return true;
    }

    public boolean isCapture(Board board) {
        if (!board.getSquare(destination).isEmpty()) {
            return true;
        }
        return false;
    }

    public boolean isEnpassant() {
        return enPassant;
    }

    public void enPassantDone() {
        enPassant = false;
    }

    private boolean enPassant(ArrayList<Move> movesMade) {
        if (!(startpos.getY() == 3 || startpos.getY() == 4)) {
            return false;
        }
        if (movesMade.size() == 0) { //If no moves made
            return false;
        }
        Move lastMove = movesMade.get(movesMade.size() - 1); //The last move made
        if (!(lastMove.getPiece() instanceof Pawn)) { //If it was not a pawn that made the last move
            return false;
        }
        if (!(Math.abs(lastMove.getFrom().getY() - lastMove.getTo().getY()) == 2)) { //If the pawn did not make a two step move.
            return false;
        }
        if (!(Math.abs(lastMove.getTo().getX() - startpos.getX()) == 1)) { //If the pawns weren't next to each other
            return false;
        }
        if (!(Math.abs(lastMove.getTo().getY() - destination.getY()) == 1)) { //Last test
            return false;
        }
        return true;
    }

    public boolean isCastle(Set<Coordinate> whiteThreats, Set<Coordinate> blackThreats, Board board) {
        int dX = startpos.getX() - destination.getX(), dY = Math.abs(startpos.getY() - destination.getY());
        if ((dX != 2 && dX != -2) || dY != 0) {
            return false;
        }
        if (piece.getHasMoved()) { //If the king has moved befores
            return false;
        }
        Piece rookPiece = board.getSquare(assignRook()).getPiece(); //Assigns rookPiece to where it should be.
        if (rookPiece == null || !(rookPiece instanceof Rook)) {
            return false;
        }
        if (rookPiece.getHasMoved()) {
            return false;
        }
        if (!clearPath(board)) {
            return false;
        }
        if (castleSquaresAttacked(whiteThreats, blackThreats)) {
            return false;
        }
        return true;
    }

    private boolean castleSquaresAttacked(Set<Coordinate> whiteThreats, Set<Coordinate> blackThreats) {
        int x = startpos.getX();
        int dX = x - destination.getX(), y = startpos.getY();
        if (piece.getPlayer().retPlayerTeam().equals("white")) { //If it is white king, check for black threats
            if (dX == -2) { //Right
                for (int i = 1; i < 3; i++) {
                    if (blackThreats.contains(new Coordinate(x + i, y))) {
                        return true;
                    }
                }
            } else {
                for (int i = 1; i < 4; i++) {
                    if (blackThreats.contains(new Coordinate(x - i, y))) {
                        return true;
                    }
                }
            }
        } else {
            if (dX == -2) { //Right
                for (int i = 1; i < 3; i++) {
                    if (whiteThreats.contains(new Coordinate(x + i, y))) {
                        return true;
                    }
                }
            } else {
                for (int i = 1; i < 4; i++) {
                    if (whiteThreats.contains(new Coordinate(x - i, y))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean clearPath(Board board) {
        int x = startpos.getX();
        int dX = x - destination.getX(), y = startpos.getY();
        if (dX == -2) { //Right
            for (int i = 1; i < 3; i++) {
                if (!board.getSquare(x + i, y).isEmpty()) {
                    return false;
                }
            }
        } else {
            for (int i = 1; i < 4; i++) {
                if (!board.getSquare(x - i, y).isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    public Coordinate assignRook() {
        Coordinate rookCoordinate = null;
        int x = startpos.getX();
        int dX = x - destination.getX(), y = startpos.getY();
        if (startpos.getY() == 0) { //White
            if (dX == -2) { //Right
                rookCoordinate = new Coordinate(7, 0);
            } else if (dX == 2) { //Left
                rookCoordinate = new Coordinate(0, 0);
            }
        } else {
            if (dX == -2) {//Right
                rookCoordinate = new Coordinate(7, 7);
            } else if (dX == 2) {//Left
                rookCoordinate = new Coordinate(0, 7);
            }
        }
        return rookCoordinate;
    }

/**
 * Check if the current player is in check or will be in check by the move he or she makes.
 * @param  currentPlayer the player that tries to move
 * @param  board         the current board
 * @return               true if in check else false.
 */
    public boolean isInCheck(Player currentPlayer, Board board, Set<Coordinate> threatenedSquares){
        Coordinate kingCoord = null;;
        for(int i = 0; i < 8;i++){
          for(int j = 0; j< 8; j++){
            if(board.getSquare(i,j).getPiece() instanceof King && board.getSquare(i,j).getPiece().getPlayer().equal(currentPlayer)){
              kingCoord = board.getSquare(i,j).getCoord();
              break;
            }
          }
        }
        if(kingCoord == null){
          return false;
        }
        if(threatenedSquares.contains(kingCoord)){
          return true;
        }
        return false;
    }
    /**
     * Checks if player is in check after making a move
     * @param  coords            the coordinates of the move they wish to make
     * @param  currentPlayer
     * @param  board             the current board
     * @param  threatenedSquares A set of squares that the opposite player are threatening.
     * @return                   true if in check after move else false.
     */
    public boolean isInCheckAfterMove(Player currentPlayer, Board board){
        Board dummyBoard = new Board(board);
        Square[][] squares = board.deepCopySquareMatrix(board.getsquares());
        Move tryMove = new Move(startpos,destination,board.getSquare(startpos).getPiece());
        if(tryMove.isCapture(board)) {
            dummyBoard.nullSquare(destination);
        }
            dummyBoard.move(startpos,destination);
      if(isInCheck(currentPlayer, dummyBoard,updateThreats(currentPlayer, dummyBoard))){
        return true;
      }
        board.setSquares(squares);
      return false;
    }
    private Set<Coordinate> updateThreats(Player currentPlayer, Board b){
          Set<Coordinate> ret = new HashSet<>();
          for (int i = 0; i < 8; i++) {
              for (int j = 0; j < 8; j++) {
                  Piece p = b.getSquare(i, j).getPiece();

                  if (p != null) {
                      if (!p.getPlayer().equal(currentPlayer)) {
                        ret.addAll(p.attackedSquares(new Coordinate(i, j), b));
                      }
                  }
              }
          }
          return ret;
      }
    public boolean badKing() {
        int diffx = Math.abs(startpos.getX() - destination.getX());
        return diffx != 1 ? true : false;
    }
}
