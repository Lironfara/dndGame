package Game.GameView;

public interface MessageCallback {

     String abilityCast(String s);
     String onDeath(String name);
     String combatResult(String s);
     String levelUp(String name, int newLevel);

     String gameTickDesc(String s);

     void gameStartOutPut();

     void choosePlayerSelection(String s);

     void printBoard(String bord);

     String playerMoveSelection();

}
