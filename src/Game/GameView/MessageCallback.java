package Game.GameView;
import Game.GameView.Units.Players.Player;
public interface MessageCallback {
     void abilityCast(String s);
     String onDeath(String name);
     void combat(String s);
     String levelUp(String s);
     String gameTickDesc(String s);
     void gameStartOutPut();
     void choosePlayerSelection(String s);
     void printBoard(String bord);
     String playerMoveSelection();
     void describe(String s);
    String combatResult(String s);
}
