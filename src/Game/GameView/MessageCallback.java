package Game.GameView;

import Game.GameView.Units.Players.Player;

public interface MessageCallback {


     String abilityCast(String s);
     String onDeath(String name);
     void combat(String s);
     String levelUp(String name, int newLevel);

     String gameTickDesc(String s);

     void gameStartOutPut();

     void choosePlayerSelection(String s);

     void printBoard(String bord);

     String playerMoveSelection();

     void describe(String s);

}
