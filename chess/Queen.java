import javax.imageio.ImageIO;
import java.util.List;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Queen extends Piece {
  private BufferedImage whiteImg;
  private BufferedImage blackImg;
    public Queen(Player player) {
        super("Queen", player);
        try{
        whiteImg = ImageIO.read(new File("bilder/whiteQueen.png"));
        blackImg = ImageIO.read(new File("bilder/blackQueen.png"));
      }catch(IOException e){}
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

    public BufferedImage display() {
        return getPlayer().retPlayerTeam().equals("white") ? whiteImg : blackImg;
    }
}
