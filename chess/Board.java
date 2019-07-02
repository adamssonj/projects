import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
/**
 * This class represents a chess board.
 */
public class Board {

    private Square[][] squares = new Square[8][8];
    private Player white;
    private Player black;
    private BufferedImage boardImg;
    private int imgSide = 400;

    public Board(Player white, Player black) {
        this.white = white;
        this.black = black;
        arrangeBoard();
        setWhitePieces();
        setBlackPieces();
        initImage();
    }
    public Board(Board b){
    this.squares=(deepCopySquareMatrix(b.getsquares()));
    }
/**
 * Returns a copied version of the Square 2d array sent as input
 * @param  input A 2d array of squares
 * @return       copy of said 2d array.
 */
    public Square[][] deepCopySquareMatrix(Square[][] input) {
    if (input == null)
        return null;
    Square[][] result = new Square[input.length][];
    for (int r = 0; r < input.length; r++) {
        result[r] = input[r].clone();
    }
    return result;
  }
  public void setSquares(Square[][] inp){
        squares = deepCopySquareMatrix((inp));
  }
  private void initImage(){
    try{
    boardImg = ImageIO.read(new File("bilder/chessBoard.png"));
    }catch(IOException e){}

    BufferedImage resizedImage = new BufferedImage(imgSide,imgSide, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g = resizedImage.createGraphics();
    g.drawImage(boardImg,0,0,imgSide,imgSide,null);
    g.dispose();
    boardImg = resizedImage;
  }

    /**
     * Creates 64 empty squares with coordinates (0,0),(0,1).... (0,7);
     */
    public void arrangeBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                squares[i][j] = new Square(new Coordinate(i, j));
            }
        }
    }
    public Square[][] getsquares(){
      return squares;
    }
    public Square getSquare(int x, int y) {
        return squares[x][y];
    }
    public void nullSquare(Coordinate pos){
        squares[pos.getX()][pos.getY()] = new Square(pos);
    }

    public Square getSquare(Coordinate coord) {
        return squares[coord.getX()][coord.getY()];
    }
/**
 * Fills the correct squares with the white pieces
 */
    public void setWhitePieces() {

        for (int i = 0; i < 8; i++) {
            squares[i][1].setPiece(new Pawn(white));
        }
        squares[0][0].setPiece(new Rook(white));
        squares[7][0].setPiece(new Rook(white));

        squares[1][0].setPiece(new Knight(white));
        squares[6][0].setPiece(new Knight(white));

        squares[2][0].setPiece(new Bishop(white));
        squares[5][0].setPiece(new Bishop(white));

        squares[3][0].setPiece(new Queen(white));
        squares[4][0].setPiece(new King(white));
    }

    public void setBlackPieces() {

        for (int i = 0; i < 8; i++) {
            squares[i][6].setPiece(new Pawn(black));
        }
        squares[0][7].setPiece(new Rook(black));
        squares[7][7].setPiece(new Rook(black));

        squares[1][7].setPiece(new Knight(black));
        squares[6][7].setPiece(new Knight(black));

        squares[2][7].setPiece(new Bishop(black));
        squares[5][7].setPiece(new Bishop(black));

        squares[3][7].setPiece(new Queen(black));
        squares[4][7].setPiece(new King(black));
    }
    public boolean sameTeam(Player p, Coordinate finalpos) {
        return squares[finalpos.getX()][finalpos.getY()].getPiece().getPlayer().equal(p);
    }
/**
 * This function moves pieces or switches two squares on the board basically.
 * @param start first square coordinate
 * @param end   second square coordinate
 */
    public void move(Coordinate start, Coordinate end) {
        Square temp = new Square();
        temp = squares[start.getX()][start.getY()];
        squares[start.getX()][start.getY()] = squares[end.getX()][end.getY()];
        squares[end.getX()][end.getY()] = temp;
    }
    public BufferedImage draw(){
      return boardImg;
    }
}
