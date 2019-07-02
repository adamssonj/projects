import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MListener extends MouseAdapter {
    private int fromX;
    private int fromY;
    private int toX;
    private int toY;
    private MoveHelper m;
    public MListener(MoveHelper mhelp){
        fromX = -1;
        fromY = -1;
        m = mhelp;
    }
    public void mousePressed(MouseEvent e){
        fromX = e.getX()/50;
        fromY = e.getY()/50;
        //System.out.println(e.getX()+ " " +  e.getX() /50);
        m.setMove("");
    }
    public void mouseReleased(MouseEvent e){
        toX = e.getX()/50;
        toY= e.getY()/50;
        String mv = Integer.toString(fromX) + Integer.toString(fromY) + " " + Integer.toString(toX) + Integer.toString(toY);

        m.setMove(mv);
    }
}
