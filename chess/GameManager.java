import java.util.*;

/**
 * This class ties everything together, keeps tracks of the game basically.
 */
public class GameManager {

    private Board board;
    private Player white;
    private Player black;
    private Player currentPlayer;
    private ArrayList<Move> movesMade;
    private Set<Coordinate> whiteThreats;
    private Set<Coordinate> blackThreats;
    private boolean enPassant;

    public GameManager() {
        white = new Player("white");
        black = new Player("black");
        board = new Board(white, black);
        currentPlayer = white;
        movesMade = new ArrayList<>();
        whiteThreats = new HashSet<>();
        blackThreats = new HashSet<>();
        enPassant = false;
    }

    public Board getBoard() {
        return board;
    }

    private void switchTurn() {
        if (currentPlayer.equal(white)) {
            currentPlayer = black;
        } else {
            currentPlayer = white;
        }
    }

    public void displayBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (!board.getSquare(j, i).isEmpty()) {
                    System.out.print(board.getSquare(j, i).getPiece().display());
                } else {
                    System.out.print(' ');
                }
                System.out.print(' ');
            }
            System.out.println();
        }
    }

    public boolean move(String move) {
        Coordinate[] movePositions = coordParser(move); //movePositions[0] = startpos, movePosition[1] = endpos.
        Piece piece = board.getSquare(movePositions[0]).getPiece();
        Move tryMove = new Move(movePositions[0], movePositions[1], piece);
        boolean skipCheck = false;

        if (!tryMove.checkMove(movePositions, currentPlayer, board)) {
            return false;
        }
        if(tryMove.isInCheck(currentPlayer,board,currentPlayerthreats())){
          if(tryMove.isInCheckAfterMove(currentPlayer,board)){
            return false;
          }
          else{ // Then they got out of the check
              skipCheck = true;
          }
        }
        if(tryMove.isInCheckAfterMove(currentPlayer,board) && !skipCheck)
        {
          return false;
        }
        //Check for capture, enpassant, castle.
        if (piece instanceof Pawn) {
            if (!tryMove.pawnCheck(movesMade, board)) { //Check for weird pawn moves, can't capture forward etc.
                return false;
            }
            if (tryMove.isEnpassant()) {
                doEnpassant(tryMove);
                return true;
            }
        }
        if (piece instanceof King) { //Check castle.
            if (tryMove.isCastle(whiteThreats, blackThreats, board)) {
                doCastle(movePositions, tryMove);
                return true;
            } else {
                if (tryMove.badKing()) { //Can't go two steps if not castle...
                    return false;
                }
            }
        } else if (tryMove.isCapture(board)) {
            board.getSquare(movePositions[1]).killPiece();
        }
        //Make move
        board.move(movePositions[0], movePositions[1]);
        board.getSquare(movePositions[1]).getPiece().hasmoved();
        movesMade.add(tryMove);
        switchTurn();
        updateAttackedSquares();
        return true;
    }

    /**
     * Updates the sets of attacked squares
     */
    private void updateAttackedSquares() {
        whiteThreats.clear();
        blackThreats.clear();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece p = board.getSquare(i, j).getPiece();
                if (p != null) {
                    if (p.getPlayer().equal(white)) {
                        whiteThreats.addAll(p.attackedSquares(new Coordinate(i, j), board));
                    } else {
                        blackThreats.addAll(p.attackedSquares(new Coordinate(i, j), board));
                    }
                }
            }
        }
    }
    private Set<Coordinate> currentPlayerthreats(){
      if(currentPlayer.equal(white)){
        return blackThreats;
      }
      return whiteThreats;
    }

    /**
     * Executes the en passant move.
     *
     * @param coords The startposition and
     * @param piece  [description]
     */
    private void doEnpassant(Move moveMade) {
        Move lastMove = movesMade.get(movesMade.size() - 1);
        board.getSquare(lastMove.getTo()).killPiece();
        board.move(moveMade.getFrom(), moveMade.getTo());
        movesMade.add(moveMade);
        moveMade.enPassantDone();
        switchTurn();
        updateAttackedSquares();
    }

    private void doCastle(Coordinate[] coords, Move tryMove) {
        Coordinate rookStart = tryMove.assignRook();
        Coordinate rookEnd = null;
        Piece rook = board.getSquare(rookStart).getPiece();
        if (rookStart.getX() > coords[0].getX()) { //If the rook was to the right of the king, it should now be on the left
            rookEnd = new Coordinate(coords[1].getX() - 1, coords[1].getY());
        } else {                                    //Vice versa.
            rookEnd = new Coordinate(coords[1].getX() + 1, coords[1].getY());
        }
        board.move(tryMove.getFrom(), tryMove.getTo()); //This moves the king
        board.move(rookStart, rookEnd); //This moves the rook
        movesMade.add(tryMove);
        movesMade.add(new Move(rookStart, rookEnd, rook));
        switchTurn();
        updateAttackedSquares();
    }

    private Coordinate[] coordParser(String s) { //Varje move ska vara på formen [0-7][0-7] ' ' [0-7][0-7];
        s = s.replaceAll("\\s", "");
        String[] array = s.split("");
        Coordinate[] coords = new Coordinate[2];
        coords[0] = new Coordinate(Integer.parseInt(array[0]), Integer.parseInt(array[1]));
        coords[1] = new Coordinate(Integer.parseInt(array[2]), Integer.parseInt(array[3]));
        return coords;
    }
}
