import javax.swing.JFrame;
import java.awt.*;


public class GUI extends JFrame implements Runnable{

    GameManager game;

    public GUI(String title, int width, int height){
      super(title);
      setPreferredSize(new Dimension(width,height));
      pack();
      Dimension truesize = getContentPane().getSize();

      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      game = new GameManager(width, height);
      add(game);

    }
    public void run(){
      setVisible(true);
    }
}
