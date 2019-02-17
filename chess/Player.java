/**
 * Represents a player 
 */
public class Player {
    private String team;

    public Player(String color) {
        team = color;
    }

    public String retPlayerTeam() {
        return team;
    }

    public boolean equal(Player p) {
        return this.retPlayerTeam() == p.retPlayerTeam();
    }

}
