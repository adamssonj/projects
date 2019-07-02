import javax.imageio.ImageIO;
import java.util.List;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
public class Rook extends Piece {

  private BufferedImage whiteImg;
  private BufferedImage blackImg;
    public Rook(Player p) {
        super("Rook", p);
        try{
        whiteImg = ImageIO.read(new File("bilder/whiteRook.png"));
        blackImg = ImageIO.read(new File("bilder/blackRook.png"));
      }catch(IOException e){}
    }


    public boolean isMoveValid(Coordinate startpos, Coordinate destination, Board board) {
        //Om vi anger knäppa koordinater.
        if (!destination.validCoordinate()) {
            return false;
        }
        if (destination.equals(startpos)) {
            return false;
        }
        if (startpos.xEqual(destination) || startpos.yEqual(destination)) {
            if (!checkPath(startpos, destination, board)) {
                return false;
            }
            return true;
        }
        return false;
    }

    public boolean checkPath(Coordinate startpos, Coordinate endpos, Board board) {
        int steps = 0, min = 0;
        if (startpos.xEqual(endpos)) { //If we've moved in the y-dir
            steps = Math.abs(endpos.getY() - startpos.getY());
            min = Math.min(endpos.getY(), startpos.getY());
            for (int i = 1; i < steps; i++) {
                if (!board.getSquare(startpos.getX(), min + i).isEmpty()) { //If anything is in the way (collision)
                    return false;
                }
            }
        } else {
            steps = Math.abs(endpos.getX() - startpos.getX());
            min = Math.min(endpos.getX(), startpos.getX());
            for (int i = 1; i < steps; i++) {
                if (!board.getSquare(min + i, startpos.getY()).isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    public List<Coordinate> attackedSquares(Coordinate pos, Board board) {
        List<Coordinate> squares = new ArrayList<>();
        int x = pos.getX(), y = pos.getY();

        for (int i = x; i < 8; i++) {
            if (board.getSquare(i, y).isEmpty() || (pos.getX() == i)) {
                squares.add(new Coordinate(i, y));
            } else {
                squares.add(new Coordinate(i, y));
                break;
            }
        }
        for (int i = x; i >= 0; i--) {
            if (board.getSquare(i, y).isEmpty() || (pos.getX() == i)) {
                squares.add(new Coordinate(i, y));
            } else {
                squares.add(new Coordinate(i, y));
                break;
            }
        }
        for (int i = y; i < 8; i++) {
            if (board.getSquare(x, i).isEmpty()|| (pos.getY() == i)) {
                squares.add(new Coordinate(x, i));
            } else {
                squares.add(new Coordinate(x, i));
                break;
            }
        }
        for (int i = y; i >= 0; i--) {
            if (board.getSquare(x, i).isEmpty()|| (pos.getY() == i)) {
                squares.add(new Coordinate(x, i));
            } else {
                squares.add(new Coordinate(x, i));
                break;
            }
        }
        squares.add(pos);
        return squares;
    }

    public BufferedImage display() {
      return getPlayer().retPlayerTeam().equals("white") ? whiteImg : blackImg;
    }
}
