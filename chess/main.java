import java.util.Scanner;
/**
 * Used to play and try the game until a GUI is in place.
 */
public class main {

    public static void main(String[] args) {
        Scanner move = new Scanner(System.in);
        boolean runGame = true;
        GameManager game = new GameManager();
        String command;
        while (runGame) {
            clrscreen();
            game.displayBoard();
            command = move.nextLine();
            if (command.equals("quit")) {
                runGame = !runGame;
                break;
            }
            game.move(command);
        }
    }

    public static void clrscreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
